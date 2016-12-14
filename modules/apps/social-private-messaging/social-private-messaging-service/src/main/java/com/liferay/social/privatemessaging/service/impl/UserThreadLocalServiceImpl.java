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

package com.liferay.social.privatemessaging.service.impl;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageConstants;
import com.liferay.petra.content.ContentUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FastDateFormatConstants;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.social.privatemessaging.configuration.PrivateMessagingConfiguration;
import com.liferay.social.privatemessaging.constants.PrivateMessagingPortletKeys;
import com.liferay.social.privatemessaging.model.PrivateMessagingConstants;
import com.liferay.social.privatemessaging.model.UserThread;
import com.liferay.social.privatemessaging.service.base.UserThreadLocalServiceBaseImpl;

import java.io.InputStream;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;

/**
 * @author Scott Lee
 * @author Jonathan Lee
 * @author Peter Fellwock
 */
public class UserThreadLocalServiceImpl extends UserThreadLocalServiceBaseImpl {

	@Override
	public MBMessage addPrivateMessage(
			long userId, long mbThreadId, String to, String subject,
			String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			ThemeDisplay themeDisplay)
		throws PortalException {

		long parentMBMessageId = MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID;

		List<User> recipients = null;

		if (mbThreadId != 0) {
			if (Validator.isNull(fetchUserThread(userId, mbThreadId))) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("User ");
					sb.append(userId);
					sb.append(" attempted to add a message to thread ");
					sb.append(mbThreadId);
					sb.append(" through the Private Messaging portlet");

					_log.warn(sb.toString());
				}

				throw new PrincipalException(
					"User " + userId + " cannot access thread " + mbThreadId +
						" through the Private Messaging portlet");
			}

			List<MBMessage> mbMessages =
				mbMessageLocalService.getThreadMessages(
					mbThreadId, WorkflowConstants.STATUS_ANY);

			MBMessage lastMBMessage = mbMessages.get(mbMessages.size() - 1);

			parentMBMessageId = lastMBMessage.getMessageId();
			subject = lastMBMessage.getSubject();
		}
		else {
			recipients = parseRecipients(userId, to);

			if (recipients.isEmpty()) {
				return null;
			}
		}

		return addPrivateMessage(
			userId, mbThreadId, parentMBMessageId, recipients, subject, body,
			inputStreamOVPs, themeDisplay);
	}

	@Override
	public MBMessage addPrivateMessageBranch(
			long userId, long parentMBMessageId, String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			ThemeDisplay themeDisplay)
		throws PortalException {

		long mbThreadId = 0;

		MBMessage parentMessage = mbMessageLocalService.getMBMessage(
			parentMBMessageId);

		List<User> recipients = new ArrayList<>();

		recipients.add(userLocalService.getUser(parentMessage.getUserId()));

		return addPrivateMessage(
			userId, mbThreadId, parentMBMessageId, recipients,
			parentMessage.getSubject(), body, inputStreamOVPs, themeDisplay);
	}

	@Override
	public void addUserThread(
			long userId, long mbThreadId, long topMBMessageId, boolean read,
			boolean deleted)
		throws PortalException {

		long userThreadId = counterLocalService.increment();

		User user = userLocalService.getUser(userId);

		UserThread userThread = userThreadPersistence.create(userThreadId);

		userThread.setCompanyId(user.getCompanyId());
		userThread.setUserId(userId);
		userThread.setUserName(user.getFullName());
		userThread.setCreateDate(new Date());
		userThread.setModifiedDate(new Date());
		userThread.setMbThreadId(mbThreadId);
		userThread.setTopMBMessageId(topMBMessageId);
		userThread.setRead(read);
		userThread.setDeleted(deleted);

		userThreadPersistence.update(userThread);
	}

	@Override
	public void deleteUser(long userId) throws PortalException {
		List<UserThread> userThreads = userThreadPersistence.findByUserId(
			userId);

		for (UserThread userThread : userThreads) {
			userThreadPersistence.remove(userThread.getUserThreadId());
		}
	}

	@Override
	public void deleteUserThread(long userId, long mbThreadId)
		throws PortalException {

		UserThread userThread = userThreadPersistence.findByU_M(
			userId, mbThreadId);

		userThread.setDeleted(true);

		userThreadPersistence.update(userThread);
	}

	@Override
	public UserThread fetchUserThread(long userId, long mbThreadId)
		throws PortalException {

		return userThreadPersistence.fetchByU_M(userId, mbThreadId);
	}

	@Override
	public List<UserThread> getMBThreadUserThreads(long mbThreadId) {
		return userThreadPersistence.findByMBThreadId(mbThreadId);
	}

	@Override
	public UserThread getUserThread(long userId, long mbThreadId)
		throws PortalException {

		return userThreadPersistence.findByU_M(userId, mbThreadId);
	}

	@Override
	public int getUserUserThreadCount(long userId, boolean deleted) {
		return userThreadPersistence.countByU_D(userId, deleted);
	}

	@Override
	public int getUserUserThreadCount(
		long userId, boolean read, boolean deleted) {

		return userThreadPersistence.countByU_R_D(userId, read, deleted);
	}

	@Override
	public List<UserThread> getUserUserThreads(long userId, boolean deleted) {
		return userThreadPersistence.findByU_D(userId, deleted);
	}

	@Override
	public List<UserThread> getUserUserThreads(
		long userId, boolean read, boolean deleted) {

		return userThreadPersistence.findByU_R_D(userId, read, deleted);
	}

	@Override
	public List<UserThread> getUserUserThreads(
		long userId, boolean deleted, int start, int end) {

		return userThreadPersistence.findByU_D(userId, deleted, start, end);
	}

	@Override
	public void markUserThreadAsRead(long userId, long mbThreadId)
		throws PortalException {

		UserThread userThread = userThreadPersistence.findByU_M(
			userId, mbThreadId);

		userThread.setRead(true);

		userThreadPersistence.update(userThread);
	}

	@Override
	public void markUserThreadAsUnread(long userId, long mbThreadId)
		throws PortalException {

		UserThread userThread = userThreadPersistence.findByU_M(
			userId, mbThreadId);

		userThread.setRead(false);

		userThreadPersistence.update(userThread);
	}

	@Override
	public void updateUserName(User user) {
		String userName = user.getFullName();

		List<UserThread> userThreads = userThreadPersistence.findByUserId(
			user.getUserId());

		for (UserThread userThread : userThreads) {
			if (!userName.equals(userThread.getUserName())) {
				userThread.setUserName(userName);

				userThreadPersistence.update(userThread);
			}
		}
	}

	protected MBMessage addPrivateMessage(
			long userId, long mbThreadId, long parentMBMessageId,
			List<User> recipients, String subject, String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			ThemeDisplay themeDisplay)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		Group group = groupLocalService.getCompanyGroup(user.getCompanyId());

		long categoryId =
			PrivateMessagingConstants.PRIVATE_MESSAGING_CATEGORY_ID;

		if (Validator.isNull(subject)) {
			subject = StringUtil.shorten(body, 50);
		}

		boolean anonymous = false;
		double priority = 0.0;
		boolean allowPingbacks = false;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		MBMessage mbMessage = mbMessageLocalService.addMessage(
			userId, user.getScreenName(), group.getGroupId(), categoryId,
			mbThreadId, parentMBMessageId, subject, body,
			MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, anonymous,
			priority, allowPingbacks, serviceContext);

		if (mbThreadId == 0) {
			for (User recipient : recipients) {
				if (recipient.getUserId() != userId) {
					addUserThread(
						recipient.getUserId(), mbMessage.getThreadId(),
						mbMessage.getMessageId(), false, false);
				}
			}

			addUserThread(
				userId, mbMessage.getThreadId(), mbMessage.getMessageId(), true,
				false);
		}
		else {
			List<UserThread> userThreads =
				userThreadPersistence.findByMBThreadId(mbMessage.getThreadId());

			for (UserThread userThread : userThreads) {
				userThread.setModifiedDate(new Date());

				if (userThread.getUserId() == userId) {
					userThread.setRead(true);
				}
				else {
					userThread.setRead(false);
				}

				if (userThread.isDeleted()) {
					userThread.setTopMBMessageId(mbMessage.getMessageId());
					userThread.setDeleted(false);
				}

				userThreadPersistence.update(userThread);
			}
		}

		// Email

		try {
			sendEmail(mbMessage.getMessageId(), themeDisplay);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		// Notifications

		sendNotificationEvent(mbMessage);

		return mbMessage;
	}

	protected PrivateMessagingConfiguration getPrivateMessagingConfiguration(
		long companyId) {

		if (_privateMessagingConfiguration == null) {
			try {
				_privateMessagingConfiguration =
					ConfigurationProviderUtil.getConfiguration(
						PrivateMessagingConfiguration.class,
						new CompanyServiceSettingsLocator(
							companyId,
							"com.liferay.social.privatemessaging." +
								"configuration.PrivateMessagingConfiguration"));
			}
			catch (ConfigurationException ce) {
				_log.error("Unable to get private message configuration", ce);
			}
		}

		return _privateMessagingConfiguration;
	}

	protected String getThreadURL(
			User user, long threadId, ThemeDisplay themeDisplay)
		throws Exception {

		Group group = user.getGroup();

		long plid = PortalUtil.getPlidFromPortletId(
			group.getGroupId(), true,
			PrivateMessagingPortletKeys.PRIVATE_MESSAGING);

		Layout layout = layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return StringPool.BLANK;
		}

		String privateMessageURL = PortalUtil.getLayoutFullURL(
			layout, themeDisplay, false);

		return privateMessageURL + "/-/private_messaging/thread/" + threadId;
	}

	protected List<User> parseRecipients(long userId, String to)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		String[] recipients = StringUtil.split(to);

		List<User> users = new ArrayList<>();

		for (String recipient : recipients) {
			int x = recipient.indexOf(CharPool.LESS_THAN);
			int y = recipient.indexOf(CharPool.GREATER_THAN);

			try {
				String screenName = recipient;

				if ((x != -1) && (y != -1)) {
					screenName = recipient.substring(x + 1, y);
				}

				User recipientUser = userLocalService.getUserByScreenName(
					user.getCompanyId(), screenName);

				if (!users.contains(recipientUser)) {
					users.add(recipientUser);
				}
			}
			catch (NoSuchUserException nsue) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsue, nsue);
				}
			}
		}

		return users;
	}

	protected void sendEmail(long mbMessageId, ThemeDisplay themeDisplay)
		throws Exception {

		MBMessage mbMessage = mbMessageLocalService.getMBMessage(mbMessageId);

		User sender = userLocalService.getUser(mbMessage.getUserId());

		Company company = companyLocalService.getCompany(sender.getCompanyId());

		InternetAddress from = new InternetAddress(company.getEmailAddress());

		PrivateMessagingConfiguration privateMessagingConfiguration =
			getPrivateMessagingConfiguration(themeDisplay.getCompanyId());

		String subject = ContentUtil.get(
			UserThreadLocalServiceImpl.class.getClassLoader(),
			privateMessagingConfiguration.emailSubject());

		subject = StringUtil.replace(
			subject, new String[] {"[$COMPANY_NAME$]", "[$FROM_NAME$]"},
			new String[] {company.getName(), sender.getFullName()});

		String body = ContentUtil.get(
			UserThreadLocalServiceImpl.class.getClassLoader(),
			privateMessagingConfiguration.emailBody());

		long portraitId = sender.getPortraitId();
		String tokenId = WebServerServletTokenUtil.getToken(
			sender.getPortraitId());

		String portraitURL =
			themeDisplay.getPortalURL() + themeDisplay.getPathImage() +
				"/user_" + (sender.isFemale() ? "female" : "male") +
					"_portrait?img_id=" + portraitId + "&t=" + tokenId;

		body = StringUtil.replace(
			body,
			new String[] {
				"[$BODY$]", "[$COMPANY_NAME$]", "[$FROM_AVATAR$]",
				"[$FROM_NAME$]", "[$FROM_PROFILE_URL$]", "[$SUBJECT$]"
			},
			new String[] {
				mbMessage.getBody(), company.getName(), portraitURL,
				sender.getFullName(), sender.getDisplayURL(themeDisplay),
				mbMessage.getSubject()
			});

		List<UserThread> userThreads =
			userThreadLocalService.getMBThreadUserThreads(
				mbMessage.getThreadId());

		for (UserThread userThread : userThreads) {
			if ((userThread.getUserId() == mbMessage.getUserId()) ||
				!UserNotificationManagerUtil.isDeliver(
					userThread.getUserId(),
					PrivateMessagingPortletKeys.PRIVATE_MESSAGING,
					PrivateMessagingConstants.NEW_MESSAGE, 0,
					UserNotificationDeliveryConstants.TYPE_EMAIL)) {

				continue;
			}

			User recipient = userLocalService.getUser(userThread.getUserId());

			String threadURL = getThreadURL(
				recipient, mbMessage.getThreadId(), themeDisplay);

			if (Validator.isNull(threadURL)) {
				continue;
			}

			InternetAddress to = new InternetAddress(
				recipient.getEmailAddress());

			Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
				FastDateFormatConstants.LONG, FastDateFormatConstants.SHORT,
				recipient.getLocale(), recipient.getTimeZone());

			String userThreadBody = StringUtil.replace(
				body, new String[] {"[$SENT_DATE$]", "[$THREAD_URL$]"},
				new String[] {
					dateFormatDateTime.format(mbMessage.getCreateDate()),
					threadURL
				});

			MailMessage mailMessage = new MailMessage(
				from, to, subject, userThreadBody, true);

			_mailService.sendEmail(mailMessage);
		}
	}

	protected void sendNotificationEvent(MBMessage mbMessage)
		throws PortalException {

		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		notificationEventJSONObject.put("classPK", mbMessage.getMessageId());
		notificationEventJSONObject.put("userId", mbMessage.getUserId());

		List<UserThread> userThreads =
			userThreadLocalService.getMBThreadUserThreads(
				mbMessage.getThreadId());

		for (UserThread userThread : userThreads) {
			if ((userThread.getUserId() == mbMessage.getUserId()) ||
				!UserNotificationManagerUtil.isDeliver(
					userThread.getUserId(),
					PrivateMessagingPortletKeys.PRIVATE_MESSAGING, 0,
					PrivateMessagingConstants.NEW_MESSAGE,
					UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

				continue;
			}

			userNotificationEventLocalService.sendUserNotificationEvents(
				userThread.getUserId(),
				PrivateMessagingPortletKeys.PRIVATE_MESSAGING,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				notificationEventJSONObject);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserThreadLocalServiceImpl.class);

	@BeanReference(type = MailService.class)
	private MailService _mailService;

	private PrivateMessagingConfiguration _privateMessagingConfiguration;

}