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

package com.liferay.portal.util;

import com.liferay.mail.model.FileAttachment;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.EscapableObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlEscapableObject;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;

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
import java.util.concurrent.Callable;

import javax.mail.internet.InternetAddress;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
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

	public void addPersistedSubscribers(String className, long classPK) {
		ObjectValuePair<String, Long> ovp = new ObjectValuePair<>(
			className, classPK);

		_persistestedSubscribersOVPs.add(ovp);
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

			for (ObjectValuePair<String, Long> ovp :
					_persistestedSubscribersOVPs) {

				String className = ovp.getKey();
				long classPK = ovp.getValue();

				List<Subscription> subscriptions =
					SubscriptionLocalServiceUtil.getSubscriptions(
						companyId, className, classPK);

				for (Subscription subscription : subscriptions) {
					try {
						notifyPersistedSubscriber(subscription);
					}
					catch (Exception e) {
						_log.error(
							"Unable to process subscription: " + subscription,
							e);
					}
				}
			}

			_persistestedSubscribersOVPs.clear();

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
						"Add " + toAddress + " to the list of users who " +
							"have received an email");
				}

				_sentEmailAddresses.add(toAddress);

				String toName = ovp.getValue();

				InternetAddress to = new InternetAddress(toAddress, toName);

				notifyRuntimeSubscriber(to, LocaleUtil.getDefault());
			}

			_runtimeSubscribersOVPs.clear();

			if (bulk) {
				Locale locale = LocaleUtil.getDefault();

				InternetAddress to = new InternetAddress(
					replaceContent(replyToAddress, locale),
					replaceContent(replyToAddress, locale));

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
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Thread currentThread = Thread.currentThread();

					_classLoader = currentThread.getContextClassLoader();

					MessageBusUtil.sendMessage(
						DestinationNames.SUBSCRIPTION_SENDER,
						SubscriptionSender.this);

					return null;
				}
			}
		);
	}

	public Object getContextAttribute(String key) {
		return _context.get(key);
	}

	public long getCurrentUserId() {
		return currentUserId;
	}

	public String getMailId() {
		return this.mailId;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link getCurrentUserId()}
	 */
	@Deprecated
	public long getUserId() {
		return getCurrentUserId();
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
			key,
			new HtmlEscapableObject<String>(String.valueOf(value), escape));
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
		this._entryTitle = entryTitle;
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
		catch (Exception e) {
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setCurrentUserId(long)}
	 */
	@Deprecated
	public void setUserId(long userId) {
		setCurrentUserId(userId);
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

	protected void notifyPersistedSubscriber(Subscription subscription)
		throws Exception {

		notifyPersistedSubscriber(subscription, _className, _classPK);
	}

	protected void notifyPersistedSubscriber(
			Subscription subscription, String className, long classPK)
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

		if (_sentEmailAddresses.contains(emailAddress)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Do not send a duplicate email to " + emailAddress);
			}

			return;
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Add " + emailAddress +
						" to the list of users who have received an email");
			}

			_sentEmailAddresses.add(emailAddress);
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
		catch (Exception e) {
			_log.error(e, e);

			return;
		}

		sendNotification(user);
	}

	protected void notifyRuntimeSubscriber(InternetAddress to, Locale locale)
		throws Exception {

		String emailAddress = to.getAddress();

		User user = UserLocalServiceUtil.fetchUserByEmailAddress(
			companyId, emailAddress);

		if (user == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"User with email address " + emailAddress +
						" does not exist for company " + companyId);
			}

			if (bulk) {
				if (_bulkAddresses == null) {
					_bulkAddresses = new ArrayList<>();
				}

				_bulkAddresses.add(to);

				return;
			}

			sendEmail(to, locale);
		}
		else {
			sendNotification(user);
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #notifyPersistedSubscriber(Subscription)}
	 */
	@Deprecated
	protected void notifySubscriber(Subscription subscription)
		throws Exception {

		notifyPersistedSubscriber(subscription, null, 0);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #notifyPersistedSubscriber(Subscription)}
	 */
	@Deprecated
	protected void notifySubscriber(
			Subscription subscription, String inferredClassName,
			long inferredClassPK)
		throws Exception {

		notifyPersistedSubscriber(
			subscription, inferredClassName, inferredClassPK);
	}

	protected void processMailMessage(MailMessage mailMessage, Locale locale)
		throws Exception {

		InternetAddress from = mailMessage.getFrom();
		InternetAddress to = mailMessage.getTo()[0];

		String processedSubject = StringUtil.replace(
			mailMessage.getSubject(),
			new String[] {
				"[$FROM_ADDRESS$]", "[$FROM_NAME$]", "[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				from.getAddress(),
				GetterUtil.getString(from.getPersonal(), from.getAddress()),
				HtmlUtil.escape(to.getAddress()),
				HtmlUtil.escape(
					GetterUtil.getString(to.getPersonal(), to.getAddress()))
			});

		processedSubject = replaceContent(processedSubject, locale, false);

		mailMessage.setSubject(processedSubject);

		String processedBody = StringUtil.replace(
			mailMessage.getBody(),
			new String[] {
				"[$FROM_ADDRESS$]", "[$FROM_NAME$]", "[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				from.getAddress(),
				GetterUtil.getString(from.getPersonal(), from.getAddress()),
				HtmlUtil.escape(to.getAddress()),
				HtmlUtil.escape(
					GetterUtil.getString(to.getPersonal(), to.getAddress()))
			});

		processedBody = replaceContent(processedBody, locale, htmlFormat);

		mailMessage.setBody(processedBody);
	}

	protected String replaceContent(String content, Locale locale)
		throws Exception {

		return replaceContent(content, locale, true);
	}

	protected String replaceContent(
			String content, Locale locale, boolean escape)
		throws Exception {

		for (Map.Entry<String, EscapableObject<String>> entry :
				_context.entrySet()) {

			String key = entry.getKey();
			EscapableObject<String> value = entry.getValue();

			String valueString = null;

			if (escape) {
				valueString = value.getEscapedValue();
			}
			else {
				valueString = value.getOriginalValue();
			}

			content = StringUtil.replace(content, key, valueString);
		}

		String portletName = StringPool.BLANK;

		if (Validator.isNotNull(portletId)) {
			portletName = PortalUtil.getPortletTitle(portletId, locale);

			content = StringUtil.replace(
				content, "[$PORTLET_NAME$]", portletName);
		}

		String portletTitle = portletName;

		if (localizedPortletTitleMap != null) {
			portletTitle = localizedPortletTitleMap.get(locale);

			if (Validator.isNull(portletTitle)) {
				Locale defaultLocale = LocaleUtil.getDefault();

				portletTitle = localizedPortletTitleMap.get(defaultLocale);

				if (Validator.isNull(portletTitle)) {
					portletTitle = portletName;
				}
			}
		}

		if (Validator.isNotNull(portletTitle)) {
			content = StringUtil.replace(
				content, "[$PORTLET_TITLE$]", portletTitle);
		}

		Company company = CompanyLocalServiceUtil.getCompany(companyId);

		content = StringUtil.replace(
			content, new String[] {"href=\"/", "src=\"/"},
			new String[] {
				"href=\"" + company.getPortalURL(groupId) + "/",
				"src=\"" + company.getPortalURL(groupId) + "/"
			});

		return content;
	}

	protected void sendEmail(InternetAddress to, Locale locale)
		throws Exception {

		InternetAddress from = new InternetAddress(
			replaceContent(fromAddress, locale),
			replaceContent(fromName, locale));

		String processedSubject = null;

		if (localizedSubjectMap != null) {
			String localizedSubject = localizedSubjectMap.get(locale);

			if (Validator.isNull(localizedSubject)) {
				Locale defaultLocale = LocaleUtil.getDefault();

				processedSubject = localizedSubjectMap.get(defaultLocale);
			}
			else {
				processedSubject = localizedSubject;
			}
		}
		else {
			processedSubject = this.subject;
		}

		String processedBody = null;

		if (localizedBodyMap != null) {
			String localizedBody = localizedBodyMap.get(locale);

			if (Validator.isNull(localizedBody)) {
				Locale defaultLocale = LocaleUtil.getDefault();

				processedBody = localizedBodyMap.get(defaultLocale);
			}
			else {
				processedBody = localizedBody;
			}
		}
		else {
			processedBody = this.body;
		}

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
				_bulkAddresses.toArray(
					new InternetAddress[_bulkAddresses.size()]));

			_bulkAddresses.clear();
		}

		if (inReplyTo != null) {
			mailMessage.setInReplyTo(inReplyTo);
		}

		mailMessage.setMessageId(mailId);

		if (replyToAddress != null) {
			InternetAddress replyTo = new InternetAddress(
				replaceContent(replyToAddress, locale),
				replaceContent(replyToAddress, locale));

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
				if (_bulkAddresses == null) {
					_bulkAddresses = new ArrayList<>();
				}

				_bulkAddresses.add(to);

				return;
			}

			sendEmail(to, user.getLocale());
		}
	}

	protected void sendNotification(User user) throws Exception {
		if (currentUserId == user.getUserId() ) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skip user " + currentUserId);
			}

			return;
		}

		sendEmailNotification(user);
		sendUserNotification(user);
	}

	protected void sendUserNotification(User user) throws Exception {
		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		notificationEventJSONObject.put("className", _className);
		notificationEventJSONObject.put("classPK", _classPK);
		notificationEventJSONObject.put("entryTitle", _entryTitle);
		notificationEventJSONObject.put("entryURL", _entryURL);
		notificationEventJSONObject.put("notificationType", _notificationType);
		notificationEventJSONObject.put("userId", currentUserId);

		if (UserNotificationManagerUtil.isDeliver(
				user.getUserId(), portletId, _notificationClassNameId,
				_notificationType,
				UserNotificationDeliveryConstants.TYPE_PUSH)) {

			UserNotificationEventLocalServiceUtil.sendUserNotificationEvents(
				user.getUserId(), portletId,
				UserNotificationDeliveryConstants.TYPE_PUSH,
				notificationEventJSONObject);
		}

		if (UserNotificationManagerUtil.isDeliver(
				user.getUserId(), portletId, _notificationClassNameId,
				_notificationType,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

			UserNotificationEventLocalServiceUtil.sendUserNotificationEvents(
				user.getUserId(), portletId,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				notificationEventJSONObject);
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

	private void readObject(ObjectInputStream objectInputStream)
		throws ClassNotFoundException, IOException {

		objectInputStream.defaultReadObject();

		String servletContextName = objectInputStream.readUTF();

		if (!servletContextName.isEmpty()) {
			_classLoader = ClassLoaderPool.getClassLoader(servletContextName);
		}
	}

	private void writeObject(ObjectOutputStream objectOutputStream)
		throws IOException {

		objectOutputStream.defaultWriteObject();

		String servletContextName = StringPool.BLANK;

		if (_classLoader != null) {
			servletContextName = ClassLoaderPool.getContextName(_classLoader);
		}

		objectOutputStream.writeUTF(servletContextName);
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
	private String _entryTitle;
	private String _entryURL;
	private boolean _initialized;
	private Object[] _mailIdIds;
	private String _mailIdPopPortletPrefix;
	private long _notificationClassNameId;
	private int _notificationType;
	private final List<ObjectValuePair<String, Long>>
		_persistestedSubscribersOVPs = new ArrayList<>();
	private final List<ObjectValuePair<String, String>>
		_runtimeSubscribersOVPs = new ArrayList<>();
	private final Set<String> _sentEmailAddresses = new HashSet<>();

}