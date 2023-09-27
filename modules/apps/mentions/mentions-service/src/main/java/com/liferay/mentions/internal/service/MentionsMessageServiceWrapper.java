/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mentions.internal.service;

import com.liferay.mentions.configuration.MentionsGroupServiceConfiguration;
import com.liferay.mentions.util.MentionsNotifier;
import com.liferay.mentions.util.MentionsUtil;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageLocalServiceWrapper;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = ServiceWrapper.class)
public class MentionsMessageServiceWrapper
	extends MBMessageLocalServiceWrapper {

	@Override
	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		MBMessage message = _mbMessageLocalService.getMessage(messageId);

		int oldStatus = message.getStatus();

		message = super.updateStatus(
			userId, messageId, status, serviceContext, workflowContext);

		if ((status != WorkflowConstants.STATUS_APPROVED) ||
			(oldStatus == WorkflowConstants.STATUS_IN_TRASH) ||
			!MentionsUtil.isMentionsEnabled(
				_portal.getSiteGroupId(message.getGroupId()))) {

			return message;
		}

		String content = message.getBody();

		if (message.isFormatBBCode()) {
			content = BBCodeTranslatorUtil.getHTML(content);
		}

		String title = message.getSubject();

		if (message.isDiscussion()) {
			title = StringUtil.shorten(_htmlParser.extractText(content), 100);
		}

		MentionsGroupServiceConfiguration mentionsGroupServiceConfiguration =
			_configurationProvider.getCompanyConfiguration(
				MentionsGroupServiceConfiguration.class,
				message.getCompanyId());

		LocalizedValuesMap subjectLocalizedValuesMap =
			mentionsGroupServiceConfiguration.commentMentionEmailSubject();
		LocalizedValuesMap bodyLocalizedValuesMap =
			mentionsGroupServiceConfiguration.commentMentionEmailBody();

		if (!message.isDiscussion()) {
			subjectLocalizedValuesMap =
				mentionsGroupServiceConfiguration.
					assetEntryMentionEmailSubject();
			bodyLocalizedValuesMap =
				mentionsGroupServiceConfiguration.assetEntryMentionEmailBody();
		}

		String contentURL = (String)serviceContext.getAttribute("contentURL");

		if (Validator.isNull(contentURL)) {
			serviceContext.setAttribute(
				"contentURL", workflowContext.get("url"));
		}
		else {
			serviceContext.setAttribute(
				"contentURL",
				HttpComponentsUtil.addParameter(
					contentURL,
					serviceContext.getAttribute("namespace") + "messageId",
					message.getMessageId()));
		}

		_mentionsNotifier.notify(
			userId, message.getGroupId(), title, content,
			message.getModelClassName(), message.getMessageId(),
			subjectLocalizedValuesMap, bodyLocalizedValuesMap, serviceContext);

		return message;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private HtmlParser _htmlParser;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MentionsNotifier _mentionsNotifier;

	@Reference
	private Portal _portal;

}