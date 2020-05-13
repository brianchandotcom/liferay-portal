/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.message.boards.internal.pop;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.internal.util.MBMailMessage;
import com.liferay.message.boards.internal.util.MBMailUtil;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.pop.MessageListenerException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionCheckerUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Michael C. Han
 */
@Component(immediate = true, service = MessageListener.class)
public class MessageListenerImpl implements MessageListener {

	@Override
	public boolean accept(
		String from, List<String> recipients, Message message) {

		try {
			if (_isAutoReply(message)) {
				return false;
			}

			String messageIdString = _getMessageIdString(recipients, message);

			if (Validator.isNull(messageIdString)) {
				return false;
			}

			Company company = _getCompany(messageIdString);

			MBCategory category = _mbCategoryLocalService.getCategory(
				MBMailUtil.getCategoryId(messageIdString));

			if ((category.getCompanyId() != company.getCompanyId()) &&
				!category.isRoot()) {

				return false;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Check to see if user " + from + " exists");
			}

			String pop3User = PrefsPropsUtil.getString(
				PropsKeys.MAIL_SESSION_MAIL_POP3_USER,
				PropsValues.MAIL_SESSION_MAIL_POP3_USER);

			if (StringUtil.equalsIgnoreCase(from, pop3User)) {
				return false;
			}

			_userLocalService.getUserByEmailAddress(
				company.getCompanyId(), from);

			return true;
		}
		catch (Exception exception) {
			_log.error("Unable to process message: " + message, exception);

			return false;
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #accept(String, List, Message)}
	 */
	@Deprecated
	@Override
	public boolean accept(String from, String recipient, Message message) {
		return accept(from, ListUtil.toList(recipient), message);
	}

	@Override
	public void deliver(String from, List<String> recipients, Message message)
		throws MessageListenerException {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs = null;

		try {
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			String messageIdString = _getMessageIdString(recipients, message);

			if (Validator.isNull(messageIdString)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Cannot deliver message ", message.toString(),
							", none of the recipients contain a message ID: ",
							recipients.toString()));
				}

				return;
			}

			Company company = _getCompany(messageIdString);

			if (_log.isDebugEnabled()) {
				_log.debug("Message id " + messageIdString);
			}

			long parentMessageId = MBMailUtil.getMessageId(messageIdString);

			if (_log.isDebugEnabled()) {
				_log.debug("Parent message id " + parentMessageId);
			}

			MBMessage parentMessage = null;

			if (parentMessageId > 0) {
				parentMessage = _mbMessageLocalService.fetchMBMessage(
					parentMessageId);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Parent message " + parentMessage);
			}

			long groupId = 0;

			long categoryId = MBMailUtil.getCategoryId(messageIdString);

			MBCategory category = _mbCategoryLocalService.fetchMBCategory(
				categoryId);

			if (category == null) {
				categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

				if (parentMessage != null) {
					groupId = parentMessage.getGroupId();
				}
			}
			else {
				groupId = category.getGroupId();
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Group id " + groupId);
				_log.debug("Category id " + categoryId);
			}

			User user = _userLocalService.getUserByEmailAddress(
				company.getCompanyId(), from);

			String subject = null;

			if (parentMessage != null) {
				subject = MBMailUtil.getSubjectForEmail(parentMessage);
			}

			MBMailMessage mbMailMessage = new MBMailMessage();

			MBMailUtil.collectPartContent(message, mbMailMessage);

			inputStreamOVPs = mbMailMessage.getInputStreamOVPs();

			PermissionCheckerUtil.setThreadValues(user);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAttribute("propagatePermissions", Boolean.TRUE);

			String portletId = PortletProviderUtil.getPortletId(
				MBMessage.class.getName(), PortletProvider.Action.VIEW);

			serviceContext.setLayoutFullURL(
				_portal.getLayoutFullURL(
					groupId, portletId,
					StringUtil.equalsIgnoreCase(
						Http.HTTPS, PropsValues.WEB_SERVER_PROTOCOL)));

			serviceContext.setScopeGroupId(groupId);

			if (parentMessage == null) {
				_mbMessageService.addMessage(
					groupId, categoryId, subject, mbMailMessage.getBody(),
					MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false,
					0.0, true, serviceContext);
			}
			else {
				_mbMessageService.addMessage(
					parentMessage.getMessageId(), subject,
					mbMailMessage.getBody(), MBMessageConstants.DEFAULT_FORMAT,
					inputStreamOVPs, false, 0.0, true, serviceContext);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Delivering message takes " + stopWatch.getTime() + " ms");
			}
		}
		catch (PrincipalException principalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Prevented unauthorized post from " + from);
			}

			throw new MessageListenerException(principalException);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new MessageListenerException(exception);
		}
		finally {
			if (inputStreamOVPs != null) {
				for (ObjectValuePair<String, InputStream> inputStreamOVP :
						inputStreamOVPs) {

					try (InputStream inputStream = inputStreamOVP.getValue()) {
					}
					catch (IOException ioException) {
						if (_log.isWarnEnabled()) {
							_log.warn(ioException, ioException);
						}
					}
				}
			}

			PermissionCheckerUtil.setThreadValues(null);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #deliver(String, List, Message)}
	 */
	@Deprecated
	@Override
	public void deliver(String from, String recipient, Message message)
		throws MessageListenerException {

		deliver(from, ListUtil.toList(recipient), message);
	}

	@Override
	public String getId() {
		return MessageListenerImpl.class.getName();
	}

	private Company _getCompany(String messageIdString) throws Exception {
		int pos =
			messageIdString.indexOf(CharPool.AT) +
				PropsValues.POP_SERVER_SUBDOMAIN.length() + 1;

		if (PropsValues.POP_SERVER_SUBDOMAIN.length() > 0) {
			pos++;
		}

		int endPos = messageIdString.indexOf(CharPool.GREATER_THAN, pos);

		if (endPos == -1) {
			endPos = messageIdString.length();
		}

		String mx = messageIdString.substring(pos, endPos);

		return _companyLocalService.getCompanyByMx(mx);
	}

	private boolean _isAutoReply(Message message) throws MessagingException {
		String[] autoReply = message.getHeader("X-Autoreply");

		if (ArrayUtil.isNotEmpty(autoReply)) {
			return true;
		}

		String[] autoReplyFrom = message.getHeader("X-Autoreply-From");

		if (ArrayUtil.isNotEmpty(autoReplyFrom)) {
			return true;
		}

		String[] mailAutoReply = message.getHeader("X-Mail-Autoreply");

		if (ArrayUtil.isNotEmpty(mailAutoReply)) {
			return true;
		}

		return false;
	}

	private String _getMessageIdString(List<String> recipients, Message message)
		throws Exception {

		if (PropsValues.POP_SERVER_SUBDOMAIN.length() == 0) {
			return MBMailUtil.getParentMessageIdString(message);
		}

		for (String recipient : recipients) {
			if ((recipient != null) &&
				recipient.startsWith(
					MBMailUtil.MESSAGE_POP_PORTLET_PREFIX,
					MBMailUtil.getMessageIdStringOffset())) {

				return recipient;
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MessageListenerImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}