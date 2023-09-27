/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.internal.service;

import com.liferay.akismet.client.AkismetClient;
import com.liferay.akismet.internal.client.constants.AkismetConstants;
import com.liferay.akismet.internal.configuration.AkismetServiceConfiguration;
import com.liferay.akismet.model.AkismetEntry;
import com.liferay.akismet.service.AkismetEntryLocalService;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageLocalServiceWrapper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jamie Sammons
 */
@Component(
	configurationPid = "com.liferay.akismet.internal.configuration.AkismetServiceConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = ServiceWrapper.class
)
public class AkismetMBMessageLocalServiceWrapper
	extends MBMessageLocalServiceWrapper {

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			long threadId, long parentMessageId, String subject, String body,
			String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		MBMessage message = super.addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);

		if (!_isCheckSpamEnabled(userId, groupId, serviceContext)) {
			return message;
		}

		int status = _checkSpam(message.getMessageId(), serviceContext);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			super.updateMessage(
				userId, message.getMessageId(), message.getBody(),
				serviceContext);
		}
		else {
			super.updateStatus(
				userId, message.getMessageId(), status, serviceContext,
				new HashMap<>());
		}

		return message;
	}

	@Override
	public MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		return addMessage(
			userId, userName, groupId, categoryId, 0,
			MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID, subject, body, format,
			inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_akismetServiceConfiguration = ConfigurableUtil.createConfigurable(
			AkismetServiceConfiguration.class, properties);
	}

	private int _checkSpam(long messageId, ServiceContext serviceContext)
		throws PortalException {

		MBMessage message = _mbMessageLocalService.getMBMessage(messageId);

		if (_isCheckSpamEnabled(
				message.getUserId(), message.getGroupId(), serviceContext)) {

			AkismetEntry akismetEntry = _updateAkismetEntry(
				message, serviceContext);

			String content = message.getSubject() + "\n\n" + message.getBody();

			if (_akismetClient.isSpam(
					message.getUserId(), content, akismetEntry)) {

				return WorkflowConstants.STATUS_DENIED;
			}
		}

		return WorkflowConstants.STATUS_APPROVED;
	}

	private String _getPermalink(
		MBMessage message, ServiceContext serviceContext) {

		String contentURL = (String)serviceContext.getAttribute("contentURL");

		if (Validator.isNotNull(contentURL)) {
			return contentURL;
		}

		return StringBundler.concat(
			serviceContext.getPortalURL(), serviceContext.getPathMain(),
			"/message_boards/find_entry?messageId=", message.getMessageId());
	}

	private boolean _isCheckSpamEnabled(
		long userId, long groupId, ServiceContext serviceContext) {

		if (!_akismetServiceConfiguration.messageBoardsEnabled() ||
			!_akismetClient.hasRequiredInfo(
				serviceContext.getRemoteAddr(), serviceContext.getHeaders())) {

			return false;
		}

		int checkThreshold =
			_akismetServiceConfiguration.akismetCheckThreshold();

		if (checkThreshold > 0) {
			int count = super.getGroupMessagesCount(
				groupId, userId, WorkflowConstants.STATUS_APPROVED);

			if (count > checkThreshold) {
				return false;
			}
		}

		return true;
	}

	private AkismetEntry _updateAkismetEntry(
		MBMessage message, ServiceContext serviceContext) {

		String permalink = _getPermalink(message, serviceContext);

		Map<String, String> headers = serviceContext.getHeaders();

		String referer = headers.get("referer");
		String userAgent = headers.get(
			StringUtil.toLowerCase(HttpHeaders.USER_AGENT));

		String userIP = serviceContext.getRemoteAddr();

		return _akismetEntryLocalService.updateAkismetEntry(
			MBMessage.class.getName(), message.getMessageId(),
			AkismetConstants.TYPE_COMMENT, permalink, referer, userAgent,
			userIP, StringPool.BLANK);
	}

	@Reference
	private AkismetClient _akismetClient;

	@Reference
	private AkismetEntryLocalService _akismetEntryLocalService;

	private volatile AkismetServiceConfiguration _akismetServiceConfiguration;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

}