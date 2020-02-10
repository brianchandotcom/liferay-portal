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

package com.liferay.portal.kernel.util;

import com.liferay.mail.kernel.model.FileAttachment;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.model.SMTPAccount;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.mail.kernel.template.MailTemplateContextBuilder;
import com.liferay.mail.kernel.template.MailTemplateFactoryUtil;
import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.mail.internet.InternetAddress;

/**
 * @author Brian Wing Shun Chan
 * @author Máté Thurzó
 * @author Raymond Augé
 * @author Sergio González
 * @author Roberto Díaz
 */
public class SubscriptionSender implements Serializable {

	public void addFileAttachment(File file) {
		addFileAttachment(file, null);
	}

	public void addFileAttachment(File file, String fileName) {
		if (file == null) {
			return;
		}

		if (fileAttachments == null) {
			fileAttachments = new ArrayList<>();
		}

		FileAttachment attachment = new FileAttachment(file, fileName);

		fileAttachments.add(attachment);
	}

	public <T> void addHook(Hook.Event<T> event, Hook<T> hook) {
		List<Hook<T>> hooks = _getHooks(event);

		hooks.add(hook);
	}

	public void addPersistedSubscribers(String className, long classPK) {
		addPersistedSubscribers(className, classPK, true);
	}

	public void addPersistedSubscribers(
		String className, long classPK, boolean notifyImmediately) {

		_persistedSubscribersTuples.add(
			new Tuple(className, classPK, notifyImmediately));
	}

	public void addRuntimeSubscribers(String toAddress, String toName) {
		ObjectValuePair<String, String> ovp = new ObjectValuePair<>(
			toAddress, toName);

		_runtimeSubscribersOVPs.add(ovp);
	}

	public void flushNotifications() throws Exception {
		initialize();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if ((_classLoader != null) &&
				(contextClassLoader != _classLoader)) {

				currentThread.setContextClassLoader(_classLoader);
			}

			for (Tuple tuple : _persistedSubscribersTuples) {
				String className = (String)tuple.getObject(0);
				long classPK = (long)tuple.getObject(1);
				boolean notifyImmediately = (boolean)tuple.getObject(2);

				List<Subscription> subscriptions =
					SubscriptionLocalServiceUtil.getSubscriptions(
						companyId, className, classPK);

				for (Subscription subscription : subscriptions) {
					try {
						notifyPersistedSubscriber(
							subscription, notifyImmediately);
					}
					catch (Exception exception) {
						_log.error(
							"Unable to process subscription: " + subscription,
							exception);
					}
				}
			}

			_persistedSubscribersTuples.clear();

			for (ObjectValuePair<String, String> ovp :
					_runtimeSubscribersOVPs) {

				String toAddress = ovp.getKey();

				if (Validator.isNull(toAddress)) {
					continue;
				}

				if (_sentEmailAddresses.contains(toAddress)) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Do not send a duplicate email to " + toAddress);
					}

					continue;
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Add ", toAddress,
							" to the list of users who have received an ",
							"email"));
				}

				_sentEmailAddresses.add(toAddress);

				String toName = ovp.getValue();

				InternetAddress to = new InternetAddress(toAddress, toName);

				notifyRuntimeSubscriber(to, LocaleUtil.getDefault());
			}

			_runtimeSubscribersOVPs.clear();

			if (bulk && (_bulkAddresses != null)) {
				Locale locale = LocaleUtil.getDefault();

				MailTemplateContext mailTemplateContext =
					_getBasicMailTemplateContext(locale);

				String template = replyToAddress;

				if (Validator.isNull(template)) {
					template = fromAddress;
				}

				MailTemplate replyToAddressMailTemplate =
					MailTemplateFactoryUtil.createMailTemplate(template, false);

				String processedReplyToAddress =
					replyToAddressMailTemplate.renderAsString(
						locale, mailTemplateContext);

				InternetAddress to = new InternetAddress(
					processedReplyToAddress, processedReplyToAddress);

				sendEmail(to, locale);
			}
		}
		finally {
			if ((_classLoader != null) &&
				(contextClassLoader != _classLoader)) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public void flushNotificationsAsync() {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Thread currentThread = Thread.currentThread();

				_classLoader = currentThread.getContextClassLoader();

				MessageBusUtil.sendMessage(
					DestinationNames.SUBSCRIPTION_SENDER,
					SubscriptionSender.this);

				return null;
			});
	}

	public long getCompanyId() {
		return companyId;
	}

	public Object getContextAttribute(String key) {
		return _context.get(key);
	}

	public long getCurrentUserId() {
		return currentUserId;
	}

	public String getMailId() {
		return mailId;
	}

	public ServiceContext getServiceContext() {
		return serviceContext;
	}

	public boolean hasSubscribers() {
		if (!_runtimeSubscribersOVPs.isEmpty()) {
			return true;
		}

		for (Tuple tuple : _persistedSubscribersTuples) {
			String className = (String)tuple.getObject(0);
			long classPK = (long)tuple.getObject(1);

			List<Subscription> subscriptions =
				SubscriptionLocalServiceUtil.getSubscriptions(
					companyId, className, classPK);

			if (!subscriptions.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	public void initialize() throws Exception {
		if (_initialized) {
			return;
		}

		_initialized = true;

		if ((groupId == 0) && (serviceContext != null)) {
			setScopeGroupId(serviceContext.getScopeGroupId());
		}

		Company company = CompanyLocalServiceUtil.getCompany(companyId);

		setContextAttribute("[$COMPANY_ID$]", company.getCompanyId());
		setContextAttribute("[$COMPANY_MX$]", company.getMx());
		setContextAttribute("[$COMPANY_NAME$]", company.getName());
		setContextAttribute("[$PORTAL_URL$]", company.getPortalURL(groupId));

		if (groupId > 0) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			setContextAttribute("[$SITE_NAME$]", group.getDescriptiveName());
		}

		if ((creatorUserId > 0) &&
			Validator.isNotNull(_contextCreatorUserPrefix)) {

			setContextAttribute(
				"[$" + _contextCreatorUserPrefix + "_USER_ADDRESS$]",
				PortalUtil.getUserEmailAddress(creatorUserId));
			setContextAttribute(
				"[$" + _contextCreatorUserPrefix + "_USER_NAME$]",
				PortalUtil.getUserName(creatorUserId, StringPool.BLANK));
		}

		if (uniqueMailId) {
			_mailIdIds = ArrayUtil.append(
				_mailIdIds, PortalUUIDUtil.generate());
		}

		mailId = PortalUtil.getMailId(
			company.getMx(), _mailIdPopPortletPrefix, _mailIdIds);
	}

	public boolean isBulk() {
		return bulk;
	}

	public void sendEmailNotification(long userId) throws Exception {
		sendEmailNotification(UserLocalServiceUtil.getUser(userId));
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setBulk(boolean bulk) {
		this.bulk = bulk;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setContextAttribute(String key, EscapableObject<String> value) {
		_context.put(key, value);
	}

	public void setContextAttribute(String key, Object value) {
		setContextAttribute(key, value, true);
	}

	public void setContextAttribute(String key, Object value, boolean escape) {
		setContextAttribute(
			key, new HtmlEscapableObject<>(String.valueOf(value), escape));
	}

	public void setContextAttributes(Object... values) {
		for (int i = 0; i < values.length; i += 2) {
			setContextAttribute(String.valueOf(values[i]), values[i + 1]);
		}
	}

	public void setContextCreatorUserPrefix(String contextCreatorUserPrefix) {
		_contextCreatorUserPrefix = contextCreatorUserPrefix;
	}

	public void setCreatorUserId(long creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	public void setCurrentUserId(long currentUserId) {
		this.currentUserId = currentUserId;
	}

	public void setEntryTitle(String entryTitle) {
		_entryTitle = entryTitle;
	}

	public void setEntryURL(String entryURL) {
		_entryURL = entryURL;
	}

	public void setFrom(String fromAddress, String fromName) {
		this.fromAddress = fromAddress;
		this.fromName = fromName;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public void setHtmlFormat(boolean htmlFormat) {
		this.htmlFormat = htmlFormat;
	}

	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public void setLocalizedBodyMap(Map<Locale, String> localizedBodyMap) {
		this.localizedBodyMap = localizedBodyMap;
	}

	public void setLocalizedContextAttribute(
		String key, EscapableLocalizableFunction value) {

		_localizedContext.put(key, value);
	}

	public <T extends Serializable & Function<Locale, String>> void
		setLocalizedContextAttribute(String key, T function) {

		setLocalizedContextAttribute(key, function, true);
	}

	public <T extends Serializable & Function<Locale, String>> void
		setLocalizedContextAttribute(String key, T function, boolean escape) {

		setLocalizedContextAttribute(
			key, new EscapableLocalizableFunction(function, escape));
	}

	public void setLocalizedContextAttributeWithFunction(
		String key, Function<Locale, String> function) {

		setLocalizedContextAttributeWithFunction(key, function, true);
	}

	public void setLocalizedContextAttributeWithFunction(
		String key, Function<Locale, String> function, boolean escape) {

		setLocalizedContextAttribute(
			key, new EscapableLocalizableFunction(function, escape));
	}

	public void setLocalizedPortletTitleMap(
		Map<Locale, String> localizedPortletTitleMap) {

		this.localizedPortletTitleMap = localizedPortletTitleMap;
	}

	public void setLocalizedSubjectMap(
		Map<Locale, String> localizedSubjectMap) {

		this.localizedSubjectMap = localizedSubjectMap;
	}

	public void setMailId(String popPortletPrefix, Object... ids) {
		_mailIdPopPortletPrefix = popPortletPrefix;
		_mailIdIds = ids;
	}

	public void setNotificationClassNameId(long notificationClassNameId) {
		_notificationClassNameId = notificationClassNameId;
	}

	/**
	 * @see com.liferay.portal.kernel.notifications.UserNotificationDefinition
	 */
	public void setNotificationType(int notificationType) {
		_notificationType = notificationType;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	public void setReplyToAddress(String replyToAddress) {
		this.replyToAddress = replyToAddress;
	}

	/**
	 * @see com.liferay.portal.kernel.search.BaseIndexer#getSiteGroupId(long)
	 */
	public void setScopeGroupId(long scopeGroupId) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

			if (group.isLayout()) {
				groupId = group.getParentGroupId();
			}
			else {
				groupId = scopeGroupId;
			}
		}
		catch (Exception exception) {
		}

		this.scopeGroupId = scopeGroupId;
	}

	public void setServiceContext(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	public void setSMTPAccount(SMTPAccount smtpAccount) {
		this.smtpAccount = smtpAccount;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setUniqueMailId(boolean uniqueMailId) {
		this.uniqueMailId = uniqueMailId;
	}

	public interface Hook<T> {

		public void process(T payload);

		public interface Event<S> {

			public static final Event<MailMessage> MAIL_MESSAGE_CREATED =
				new Event<MailMessage>() {
				};

			public static final Event<Subscription> PERSISTED_SUBSCRIBER_FOUND =
				new Event<Subscription>() {
				};

		}

	}

	protected void deleteSubscription(Subscription subscription)
		throws Exception {

		SubscriptionLocalServiceUtil.deleteSubscription(
			subscription.getSubscriptionId());
	}

	protected boolean hasPermission(
			Subscription subscription, String className, long classPK,
			User user)
		throws Exception {

		if (subscription.getClassName() == null) {
			return false;
		}

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		Boolean hasPermission = null;

		if (Validator.isNotNull(className)) {
			hasPermission =
				BaseModelPermissionCheckerUtil.containsBaseModelPermission(
					permissionChecker, groupId, className, classPK,
					ActionKeys.VIEW);

			if ((hasPermission == null) || !hasPermission) {
				return false;
			}
		}

		hasPermission = hasSubscribePermission(permissionChecker, subscription);

		if ((hasPermission == null) || !hasPermission) {
			return false;
		}

		return true;
	}

	protected boolean hasPermission(Subscription subscription, User user)
		throws Exception {

		return hasPermission(subscription, _className, _classPK, user);
	}

	/**
	 * @throws PortalException
	 */
	protected Boolean hasSubscribePermission(
			PermissionChecker permissionChecker, Subscription subscription)
		throws PortalException {

		ResourceAction resourceAction =
			ResourceActionLocalServiceUtil.fetchResourceAction(
				subscription.getClassName(), ActionKeys.SUBSCRIBE);

		if (resourceAction != null) {
			return BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				permissionChecker, groupId, subscription.getClassName(),
				subscription.getClassPK(), ActionKeys.SUBSCRIBE);
		}

		return Boolean.TRUE;
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	protected void notifyPersistedSubscriber(Subscription subscription)
		throws Exception {

		notifyPersistedSubscriber(subscription, true);
	}

	protected void notifyPersistedSubscriber(
			Subscription subscription, boolean notifyImmediately)
		throws Exception {

		notifyPersistedSubscriber(
			subscription, _className, _classPK, notifyImmediately);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	protected void notifyPersistedSubscriber(
			Subscription subscription, String className, long classPK)
		throws Exception {

		notifyPersistedSubscriber(subscription, _className, _classPK, true);
	}

	protected void notifyPersistedSubscriber(
			Subscription subscription, String className, long classPK,
			boolean notifyImmediately)
		throws Exception {

		User user = UserLocalServiceUtil.fetchUserById(
			subscription.getUserId());

		if (user == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Subscription " + subscription.getSubscriptionId() +
						" is stale and will be deleted");
			}

			deleteSubscription(subscription);

			return;
		}

		String emailAddress = user.getEmailAddress();

		if (notifyImmediately) {
			if (_sentEmailAddresses.contains(emailAddress)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not send a duplicate email to " + emailAddress);
				}

				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Add " + emailAddress +
						" to the list of users who have received an email");
			}

			_sentEmailAddresses.add(emailAddress);
		}
		else {
			if (_delayedSentEmailAddresses.contains(emailAddress)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not send a duplicate email to " + emailAddress);
				}

				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Add " + emailAddress +
						" to the list of users who will receive an email");
			}

			_delayedSentEmailAddresses.add(emailAddress);
		}

		if (!user.isActive()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skip inactive user " + user.getUserId());
			}

			return;
		}

		try {
			if (!hasPermission(subscription, className, classPK, user)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Skip unauthorized user " + user.getUserId());
				}

				return;
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return;
		}

		_notifyHooks(Hook.Event.PERSISTED_SUBSCRIBER_FOUND, subscription);

		sendNotification(user, notifyImmediately);
	}

	protected void notifyRuntimeSubscriber(InternetAddress to, Locale locale)
		throws Exception {

		String emailAddress = to.getAddress();

		User user = UserLocalServiceUtil.fetchUserByEmailAddress(
			companyId, emailAddress);

		if (user == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"User with email address ", emailAddress,
						" does not exist for company ", companyId));
			}

			if (bulk) {
				_addBulkAddress(to);

				return;
			}

			sendEmail(to, locale);
		}
		else {
			sendNotification(user);
		}
	}

	protected void populateNotificationEventJSONObject(
		JSONObject notificationEventJSONObject) {

		notificationEventJSONObject.put(
			"className", _className
		).put(
			"classPK", _classPK
		).put(
			"context", _context
		).put(
			"entryTitle", _entryTitle
		).put(
			"entryURL", _entryURL
		).put(
			"localizedBodyMap", localizedBodyMap
		).put(
			"localizedContext", _localizedContext
		).put(
			"localizedSubjectMap", localizedSubjectMap
		).put(
			"mailId", mailId
		).put(
			"notificationType", _notificationType
		).put(
			"portletId", portletId
		).put(
			"userId", currentUserId
		);
	}

	protected void processMailMessage(MailMessage mailMessage, Locale locale)
		throws Exception {

		InternetAddress to = mailMessage.getTo()[0];

		MailTemplateContext mailTemplateContext = _getBodyMailTemplateContext(
			locale, mailMessage.getFrom(), to);

		MailTemplate subjectMailTemplate =
			MailTemplateFactoryUtil.createMailTemplate(
				mailMessage.getSubject(), false);

		String processedSubject = subjectMailTemplate.renderAsString(
			locale, mailTemplateContext);

		mailMessage.setSubject(processedSubject);

		MailTemplate bodyMailTemplate =
			MailTemplateFactoryUtil.createMailTemplate(
				mailMessage.getBody(), true);

		String processedBody = bodyMailTemplate.renderAsString(
			locale, mailTemplateContext);

		mailMessage.setBody(processedBody);

		_notifyHooks(Hook.Event.MAIL_MESSAGE_CREATED, mailMessage);
	}

	protected void sendEmail(InternetAddress to, Locale locale)
		throws Exception {

		MailTemplateContext mailTemplateContext = _getBasicMailTemplateContext(
			locale);

		MailTemplate fromAddressMailTemplate =
			MailTemplateFactoryUtil.createMailTemplate(fromAddress, false);

		MailTemplate fromNameMailTemplate =
			MailTemplateFactoryUtil.createMailTemplate(fromName, false);

		InternetAddress from = new InternetAddress(
			fromAddressMailTemplate.renderAsString(locale, mailTemplateContext),
			fromNameMailTemplate.renderAsString(locale, mailTemplateContext));

		String processedSubject = _getLocalizedValue(
			localizedSubjectMap, locale, subject);

		String processedBody = _getLocalizedValue(
			localizedBodyMap, locale, body);

		MailMessage mailMessage = new MailMessage(
			from, to, processedSubject, processedBody, htmlFormat);

		if (fileAttachments != null) {
			for (FileAttachment fileAttachment : fileAttachments) {
				mailMessage.addFileAttachment(
					fileAttachment.getFile(), fileAttachment.getFileName());
			}
		}

		if (bulk && (_bulkAddresses != null)) {
			mailMessage.setBulkAddresses(
				_bulkAddresses.toArray(new InternetAddress[0]));

			_bulkAddresses.clear();
		}

		if (inReplyTo != null) {
			mailMessage.setInReplyTo(inReplyTo);
		}

		mailMessage.setMessageId(mailId);

		if (replyToAddress != null) {
			MailTemplate replyToAddressMailTemplate =
				MailTemplateFactoryUtil.createMailTemplate(
					replyToAddress, false);

			String processedReplyToAddress =
				replyToAddressMailTemplate.renderAsString(
					locale, mailTemplateContext);

			InternetAddress replyTo = new InternetAddress(
				processedReplyToAddress, processedReplyToAddress);

			mailMessage.setReplyTo(new InternetAddress[] {replyTo});
		}

		if (smtpAccount != null) {
			mailMessage.setSMTPAccount(smtpAccount);
		}

		processMailMessage(mailMessage, locale);

		MailServiceUtil.sendEmail(mailMessage);
	}

	protected void sendEmailNotification(User user) throws Exception {
		if (UserNotificationManagerUtil.isDeliver(
				user.getUserId(), portletId, _notificationClassNameId,
				_notificationType,
				UserNotificationDeliveryConstants.TYPE_EMAIL)) {

			InternetAddress to = new InternetAddress(
				user.getEmailAddress(), user.getFullName());

			if (bulk) {
				_addBulkAddress(to);

				return;
			}

			sendEmail(to, user.getLocale());
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	protected void sendNotification(User user) throws Exception {
		sendNotification(user, true);
	}

	protected void sendNotification(User user, boolean notifyImmediately)
		throws Exception {

		if (currentUserId == user.getUserId()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skip user " + currentUserId);
			}

			return;
		}

		if (notifyImmediately) {
			sendEmailNotification(user);
		}

		sendUserNotification(user, notifyImmediately);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	protected void sendUserNotification(User user) throws Exception {
		sendNotification(user, true);
	}

	protected void sendUserNotification(User user, boolean notifyImmediately)
		throws Exception {

		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		populateNotificationEventJSONObject(notificationEventJSONObject);

		if (UserNotificationManagerUtil.isDeliver(
				user.getUserId(), portletId, _notificationClassNameId,
				_notificationType,
				UserNotificationDeliveryConstants.TYPE_PUSH)) {

			UserNotificationEventLocalServiceUtil.sendUserNotificationEvents(
				user.getUserId(), portletId,
				UserNotificationDeliveryConstants.TYPE_PUSH, notifyImmediately,
				false, notificationEventJSONObject);
		}

		if (UserNotificationManagerUtil.isDeliver(
				user.getUserId(), portletId, _notificationClassNameId,
				_notificationType,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

			UserNotificationEventLocalServiceUtil.sendUserNotificationEvents(
				user.getUserId(), portletId,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				notifyImmediately, false, notificationEventJSONObject);
		}
	}

	protected String body;
	protected boolean bulk;
	protected long companyId;
	protected long creatorUserId;
	protected long currentUserId;
	protected List<FileAttachment> fileAttachments = new ArrayList<>();
	protected String fromAddress;
	protected String fromName;
	protected long groupId;
	protected boolean htmlFormat;
	protected String inReplyTo;
	protected Map<Locale, String> localizedBodyMap;
	protected Map<Locale, String> localizedPortletTitleMap;
	protected Map<Locale, String> localizedSubjectMap;
	protected String mailId;
	protected String portletId;
	protected String replyToAddress;
	protected long scopeGroupId;
	protected ServiceContext serviceContext;
	protected SMTPAccount smtpAccount;
	protected String subject;
	protected boolean uniqueMailId = true;

	private void _addBulkAddress(InternetAddress internetAddress) {
		if (_bulkAddresses == null) {
			_bulkAddresses = new ArrayList<>();
		}

		_bulkAddresses.add(internetAddress);
	}

	private MailTemplateContext _getBasicMailTemplateContext(Locale locale) {
		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		String portletName = _getPortletName(locale);

		mailTemplateContextBuilder.put("[$PORTLET_NAME$]", portletName);
		mailTemplateContextBuilder.put(
			"[$PORTLET_TITLE$]", _getPortletTitle(portletName, locale));

		_context.forEach(mailTemplateContextBuilder::put);
		_localizedContext.forEach(mailTemplateContextBuilder::put);

		return mailTemplateContextBuilder.build();
	}

	private MailTemplateContext _getBodyMailTemplateContext(
		Locale locale, InternetAddress from, InternetAddress to) {

		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		mailTemplateContextBuilder.put("[$FROM_ADDRESS$]", from.getAddress());
		mailTemplateContextBuilder.put(
			"[$FROM_NAME$]",
			HtmlUtil.escape(
				GetterUtil.getString(from.getPersonal(), from.getAddress())));
		mailTemplateContextBuilder.put(
			"[$TO_ADDRESS$]", HtmlUtil.escape(to.getAddress()));
		mailTemplateContextBuilder.put(
			"[$TO_NAME$]",
			HtmlUtil.escape(
				GetterUtil.getString(to.getPersonal(), to.getAddress())));

		MailTemplateContext mailTemplateContext =
			mailTemplateContextBuilder.build();

		return mailTemplateContext.aggregateWith(
			_getBasicMailTemplateContext(locale));
	}

	private <T> List<Hook<T>> _getHooks(Hook.Event<T> event) {
		return (List)_hooks.computeIfAbsent(event, key -> new ArrayList<>());
	}

	private String _getLocalizedValue(
		Map<Locale, String> localizedValueMap, Locale locale,
		String defaultValue) {

		return GetterUtil.get(
			MapUtil.getWithFallbackKey(
				localizedValueMap, locale, LocaleUtil.getDefault()),
			defaultValue);
	}

	private String _getPortletName(Locale locale) {
		if (Validator.isNull(portletId)) {
			return StringPool.BLANK;
		}

		return PortalUtil.getPortletTitle(portletId, locale);
	}

	private String _getPortletTitle(String portletName, Locale locale) {
		return _getLocalizedValue(
			localizedPortletTitleMap, locale, portletName);
	}

	private <T> void _notifyHooks(Hook.Event<T> event, T payload) {
		List<Hook<T>> hooks = _getHooks(event);

		hooks.forEach(hook -> hook.process(payload));
	}

	private void readObject(ObjectInputStream objectInputStream)
		throws ClassNotFoundException, IOException {

		objectInputStream.defaultReadObject();

		String contextName = objectInputStream.readUTF();

		if (!contextName.equals(StringPool.IS_NULL)) {
			_classLoader = ClassLoaderPool.getClassLoader(contextName);
		}
	}

	private void writeObject(ObjectOutputStream objectOutputStream)
		throws IOException {

		objectOutputStream.defaultWriteObject();

		String contextName = StringPool.IS_NULL;

		if (_classLoader != null) {
			contextName = ClassLoaderPool.getContextName(_classLoader);
		}

		objectOutputStream.writeUTF(contextName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SubscriptionSender.class);

	private List<InternetAddress> _bulkAddresses;
	private transient ClassLoader _classLoader;
	private String _className;
	private long _classPK;
	private final Map<String, EscapableObject<String>> _context =
		new HashMap<>();
	private String _contextCreatorUserPrefix;
	private final Set<String> _delayedSentEmailAddresses = new HashSet<>();
	private String _entryTitle;
	private String _entryURL;
	private final Map<Hook.Event<?>, List<Hook<?>>> _hooks = new HashMap<>();
	private boolean _initialized;
	private final Map<String, EscapableLocalizableFunction> _localizedContext =
		new HashMap<>();
	private Object[] _mailIdIds;
	private String _mailIdPopPortletPrefix;
	private long _notificationClassNameId;
	private int _notificationType;
	private final List<Tuple> _persistedSubscribersTuples = new ArrayList<>();
	private final List<ObjectValuePair<String, String>>
		_runtimeSubscribersOVPs = new ArrayList<>();
	private final Set<String> _sentEmailAddresses = new HashSet<>();

}