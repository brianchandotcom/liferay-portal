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

package com.liferay.notification.internal.type;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationPortletKeys;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.constants.NotificationTermContributorConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.exception.NotificationTemplateFromException;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.notification.service.NotificationQueueEntryAttachmentLocalService;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateAttachmentLocalService;
import com.liferay.notification.term.contributor.NotificationTermContributor;
import com.liferay.notification.term.contributor.NotificationTermContributorRegistry;
import com.liferay.notification.type.BaseNotificationType;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.util.LocalizedMapUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.EmailAddressValidatorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	immediate = true,
	property = "notification.type.key=" + NotificationConstants.TYPE_EMAIL,
	service = NotificationType.class
)
public class EmailNotificationType extends BaseNotificationType {

	@Override
	public List<NotificationRecipientSetting>
		createNotificationRecipientSettings(
			long notificationRecipientId, Object[] recipients, User user) {

		Map<String, Object> recipientMap = (Map<String, Object>)recipients[0];

		List<NotificationRecipientSetting> notificationRecipientSettings =
			new ArrayList<>();

		for (Map.Entry<String, Object> entry : recipientMap.entrySet()) {
			NotificationRecipientSetting notificationRecipientSetting =
				_notificationRecipientSettingLocalService.
					createNotificationRecipientSetting(
						_counterLocalService.increment());

			notificationRecipientSetting.setCompanyId(user.getCompanyId());
			notificationRecipientSetting.setUserId(user.getUserId());
			notificationRecipientSetting.setUserName(user.getFullName());

			notificationRecipientSetting.setNotificationRecipientId(
				notificationRecipientId);
			notificationRecipientSetting.setName(
				String.valueOf(entry.getKey()));

			if (entry.getValue() instanceof String) {
				notificationRecipientSetting.setValue(
					String.valueOf(entry.getValue()));
			}
			else {
				notificationRecipientSetting.setValueMap(
					LocalizedMapUtil.getLocalizedMap(
						(LinkedHashMap)entry.getValue()));
			}

			notificationRecipientSettings.add(notificationRecipientSetting);
		}

		return notificationRecipientSettings;
	}

	@Override
	public String getFromName(NotificationQueueEntry notificationQueueEntry) {
		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		Map<String, Object> notificationRecipientSettingsMap =
			notificationRecipient.getNotificationRecipientSettingsMap();

		return String.valueOf(notificationRecipientSettingsMap.get("fromName"));
	}

	@Override
	public String getRecipientSummary(
		NotificationQueueEntry notificationQueueEntry) {

		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		Map<String, Object> notificationRecipientSettingsMap =
			notificationRecipient.getNotificationRecipientSettingsMap();

		return String.valueOf(notificationRecipientSettingsMap.get("to"));
	}

	@Override
	public String getType() {
		return NotificationConstants.TYPE_EMAIL;
	}

	@Override
	public void sendNotification(NotificationContext notificationContext)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		User user = _userLocalService.getUser(notificationContext.getUserId());

		_siteDefaultLocale = _portal.getSiteDefaultLocale(user.getGroupId());
		_userLocale = user.getLocale();

		notificationContext.setFileEntryIds(
			_getFileEntryIds(user.getCompanyId(), notificationContext));

		String body = _formatLocalizedContent(
			notificationTemplate.getBodyMap(), notificationContext);
		String subject = _formatLocalizedContent(
			notificationTemplate.getSubjectMap(), notificationContext);

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		Map<String, String> notificationRecipientSettingsEvaluatedMap =
			HashMapBuilder.put(
				"bcc",
				_formatContent(
					"bcc", notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				"cc",
				_formatContent(
					"cc", notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				"from",
				_formatContent(
					"from", notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				"fromName",
				_formatLocalizedContent(
					"fromName", notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				"to",
				() -> {
					NotificationRecipientSetting notificationRecipientSetting =
						_notificationRecipientSettingLocalService.
							getNotificationRecipientSetting(
								notificationRecipient.
									getNotificationRecipientId(),
								"to");

					String to = _formatTo(
						notificationRecipientSetting.getValue(user.getLocale()),
						user.getLocale(), notificationContext);

					if (Validator.isNotNull(to)) {
						return to;
					}

					return _formatLocalizedContent(
						notificationRecipientSetting.getValue(
							_siteDefaultLocale),
						_siteDefaultLocale,
						NotificationTermContributorConstants.RECIPIENT,
						notificationContext);
				}
			).build();

		for (String emailAddressOrUserId :
				StringUtil.split(
					notificationRecipientSettingsEvaluatedMap.get("to"))) {

			User toUser = _userLocalService.fetchUser(
				GetterUtil.getLong(emailAddressOrUserId));

			if ((toUser == null) &&
				emailAddressValidator.validate(
					user.getCompanyId(), emailAddressOrUserId)) {

				toUser = _userLocalService.fetchUserByEmailAddress(
					user.getCompanyId(), emailAddressOrUserId);

				if (toUser == null) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"No user exists with email address " +
								emailAddressOrUserId);
					}

					_prepareNotificationContext(
						_userLocalService.getDefaultUser(
							CompanyThreadLocal.getCompanyId()),
						body, notificationContext,
						notificationRecipientSettingsEvaluatedMap, subject);

					_notificationQueueEntryLocalService.
						addNotificationQueueEntry(notificationContext);

					continue;
				}
			}

			_prepareNotificationContext(
				user, body, notificationContext,
				notificationRecipientSettingsEvaluatedMap, subject);

			_notificationQueueEntryLocalService.addNotificationQueueEntry(
				notificationContext);
		}
	}

	@Override
	public void sendUnsentNotifications() {
		for (NotificationQueueEntry notificationQueueEntry :
				_notificationQueueEntryLocalService.
					getUnsentNotificationEntries(
						NotificationConstants.TYPE_EMAIL)) {

			NotificationRecipient notificationRecipient =
				notificationQueueEntry.getNotificationRecipient();

			Map<String, Object> notificationRecipientSettingsMap =
				notificationRecipient.getNotificationRecipientSettingsMap();

			try {
				MailMessage mailMessage = new MailMessage(
					new InternetAddress(
						String.valueOf(
							notificationRecipientSettingsMap.get("from")),
						String.valueOf(
							notificationRecipientSettingsMap.get("fromName"))),
					new InternetAddress(
						String.valueOf(
							notificationRecipientSettingsMap.get("to")),
						String.valueOf(
							notificationRecipientSettingsMap.get("toName"))),
					notificationQueueEntry.getSubject(),
					notificationQueueEntry.getBody(), true);

				_addFileAttachments(
					mailMessage,
					notificationQueueEntry.getNotificationQueueEntryId());

				mailMessage.setBCC(
					_toInternetAddresses(
						String.valueOf(
							notificationRecipientSettingsMap.get("bcc"))));
				mailMessage.setCC(
					_toInternetAddresses(
						String.valueOf(
							notificationRecipientSettingsMap.get("cc"))));

				_mailService.sendEmail(mailMessage);

				_notificationQueueEntryLocalService.updateStatus(
					notificationQueueEntry.getNotificationQueueEntryId(),
					NotificationQueueEntryConstants.STATUS_SENT);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				notificationQueueEntry.setStatus(
					NotificationQueueEntryConstants.STATUS_FAILED);

				_notificationQueueEntryLocalService.
					updateNotificationQueueEntry(notificationQueueEntry);
			}
		}
	}

	@Override
	public Object[] toRecipients(
		List<NotificationRecipientSetting> notificationRecipientSettings) {

		Map<String, Object> recipientsMap = new HashMap<>();

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipientSettings) {

			Object value = notificationRecipientSetting.getValue();

			if (Validator.isXml(notificationRecipientSetting.getValue())) {
				value = notificationRecipientSetting.getValueMap();
			}

			recipientsMap.put(notificationRecipientSetting.getName(), value);
		}

		return new Object[] {recipientsMap};
	}

	@Override
	public void validateNotificationTemplate(
			NotificationContext notificationContext)
		throws PortalException {

		super.validateNotificationTemplate(notificationContext);

		Map<String, Object> notificationRecipientSettingsMap =
			notificationContext.getNotificationRecipientSettingsMap();

		if (Validator.isNull(notificationRecipientSettingsMap.get("from"))) {
			throw new NotificationTemplateFromException("From is null");
		}
	}

	private void _addFileAttachments(
		MailMessage mailMessage, long notificationQueueEntryId) {

		for (NotificationQueueEntryAttachment notificationQueueEntryAttachment :
				_notificationQueueEntryAttachmentLocalService.
					getNotificationQueueEntryNotificationQueueEntryAttachments(
						notificationQueueEntryId)) {

			try {
				FileEntry fileEntry =
					_portletFileRepository.getPortletFileEntry(
						notificationQueueEntryAttachment.getFileEntryId());

				mailMessage.addFileAttachment(
					FileUtil.createTempFile(fileEntry.getContentStream()),
					fileEntry.getFileName());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}
	}

	private NotificationQueueEntry _createNotificationQueueEntry(
		User user, String body, NotificationContext notificationContext,
		String subject) {

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		NotificationQueueEntry notificationQueueEntry =
			_notificationQueueEntryLocalService.createNotificationQueueEntry(
				_counterLocalService.increment());

		notificationQueueEntry.setUserId(user.getUserId());
		notificationQueueEntry.setUserId(user.getUserId());
		notificationQueueEntry.setUserName(user.getFullName());

		notificationQueueEntry.setNotificationTemplateId(
			notificationTemplate.getNotificationTemplateId());
		notificationQueueEntry.setBody(body);
		notificationQueueEntry.setClassName(notificationContext.getClassName());
		notificationQueueEntry.setClassPK(notificationContext.getClassPK());
		notificationQueueEntry.setPriority(0);
		notificationQueueEntry.setSubject(subject);
		notificationQueueEntry.setType(getType());
		notificationQueueEntry.setStatus(
			NotificationQueueEntryConstants.STATUS_UNSENT);

		return notificationQueueEntry;
	}

	private NotificationRecipient _createNotificationRecipient(
		User user, long notificationQueueEntryId) {

		NotificationRecipient notificationRecipient =
			_notificationRecipientLocalService.createNotificationRecipient(
				_counterLocalService.increment());

		notificationRecipient.setCompanyId(user.getCompanyId());
		notificationRecipient.setUserId(user.getUserId());
		notificationRecipient.setUserName(user.getFullName());

		notificationRecipient.setClassName(
			NotificationQueueEntry.class.getName());
		notificationRecipient.setClassPK(notificationQueueEntryId);

		return notificationRecipient;
	}

	private List<NotificationRecipientSetting>
		_createNotificationRecipientSettings(
			User user, long notificationRecipientId,
			Map<String, String> notificationRecipientSettingsMap) {

		List<NotificationRecipientSetting> notificationRecipientSettings =
			new ArrayList<>();

		for (Map.Entry<String, String> entry :
				notificationRecipientSettingsMap.entrySet()) {

			NotificationRecipientSetting notificationRecipientSetting =
				_notificationRecipientSettingLocalService.
					createNotificationRecipientSetting(
						_counterLocalService.increment());

			notificationRecipientSetting.setCompanyId(user.getCompanyId());
			notificationRecipientSetting.setUserId(user.getUserId());
			notificationRecipientSetting.setUserName(user.getFullName());

			notificationRecipientSetting.setNotificationRecipientId(
				notificationRecipientId);
			notificationRecipientSetting.setName(entry.getKey());
			notificationRecipientSetting.setValue(entry.getValue());

			notificationRecipientSettings.add(notificationRecipientSetting);
		}

		return notificationRecipientSettings;
	}

	private String _formatContent(
			Map<Locale, String> contentMap,
			NotificationContext notificationContext)
		throws PortalException {

		String content = contentMap.get(
			(Locale)notificationContext.getAttributeValue("userLocale"));

		content = _formatContent(
			content,
			(Locale)notificationContext.getAttributeValue("userLocale"),
			notificationContext, null);

		if (Validator.isNull(content)) {
			content = contentMap.get(
				(Locale)notificationContext.getAttributeValue(
					"siteDefaultLocale"));

			return _formatContent(
				content,
				(Locale)notificationContext.getAttributeValue(
					"siteDefaultLocale"),
				notificationContext, null);
		}

		return content;
	}

	private String _formatContent(
			String content, Locale locale,
			NotificationContext notificationContext,
			String notificationTermContributorKey)
		throws PortalException {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		List<String> termNames = new ArrayList<>();

		Matcher matcher = _termNamePattern.matcher(content);

		while (matcher.find()) {
			termNames.add(matcher.group());
		}

		List<NotificationTermContributor> notificationTermContributors = null;

		if (Validator.isNotNull(notificationTermContributorKey)) {
			notificationTermContributors =
				_notificationTermContributorRegistry.
					getNotificationTermContributorsByNotificationTermContributorKey(
						notificationTermContributorKey);
		}
		else {
			notificationTermContributors =
				_notificationTermContributorRegistry.
					getNotificationTermContributorsByNotificationTypeKey(
						notificationContext.getClassName());
		}

		for (NotificationTermContributor notificationTermContributor :
				notificationTermContributors) {

			for (String termName : termNames) {
				content = StringUtil.replace(
					content, termName,
					notificationTermContributor.getTermValue(
						locale, notificationContext.getTermValues(), termName));
			}
		}

		return content;
	}

	private String _formatLocalizedContent(
			String content, Locale locale,
			NotificationContext notificationContext,
			String notificationTermContributorKey)
		throws PortalException {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		List<String> termNames = new ArrayList<>();

		Matcher matcher = _pattern.matcher(content);

		while (matcher.find()) {
			termNames.add(matcher.group());
		}

		List<NotificationTermContributor> notificationTermContributors = null;

		if (Validator.isNotNull(notificationTermContributorKey)) {
			notificationTermContributors =
				_notificationTermContributorRegistry.
					getNotificationTermContributorsByNotificationTermContributorKey(
						notificationTermContributorKey);
		}
		else {
			notificationTermContributors =
				_notificationTermContributorRegistry.
					getNotificationTermContributorsByNotificationTypeKey(
						notificationContext.getClassName());
		}

		for (NotificationTermContributor notificationTermContributor :
				notificationTermContributors) {

			for (String termName : termNames) {
				content = StringUtil.replace(
					content, termName,
					notificationTermContributor.getTermValue(
						locale, notificationContext.getTermValues(), termName));
			}
		}

		return content;
	}

	private String _formatLocalizedContent(
			String settingName, NotificationContext notificationContext,
			long notificationTemplateRecipientId)
		throws PortalException {

		NotificationRecipientSetting notificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				getNotificationRecipientSetting(
					notificationTemplateRecipientId, settingName);

		return _formatLocalizedContent(
			notificationRecipientSetting.getValueMap(), notificationContext);
	}

	private String _formatTo(
			String to, Locale locale, NotificationContext notificationContext)
		throws PortalException {

		if (Validator.isNull(to)) {
			return StringPool.BLANK;
		}

		Set<String> emailAddresses = new HashSet<>();

		Matcher matcher = _emailAddressPattern.matcher(to);

		while (matcher.find()) {
			emailAddresses.add(matcher.group());
		}

		return _formatLocalizedContent(
			StringUtil.merge(emailAddresses), locale,
			notificationContext,
			NotificationTermContributorConstants.RECIPIENT);
	}

	private List<Long> _getFileEntryIds(
			long companyId, NotificationContext notificationContext)
		throws PortalException {

		Group group = _groupLocalService.getCompanyGroup(companyId);

		Repository repository = _getRepository(group.getGroupId());

		if (repository == null) {
			return new ArrayList<>();
		}

		List<Long> fileEntryIds = new ArrayList<>();

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		for (NotificationTemplateAttachment notificationTemplateAttachment :
				_notificationTemplateAttachmentLocalService.
					getNotificationTemplateAttachments(
						notificationTemplate.getNotificationTemplateId())) {

			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				notificationTemplateAttachment.getObjectFieldId());

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
				MapUtil.getLong(
					notificationContext.getTermValues(),
					objectField.getName()));

			if (dlFileEntry == null) {
				continue;
			}

			FileEntry fileEntry = _portletFileRepository.addPortletFileEntry(
				null, repository.getGroupId(),
				_userLocalService.getDefaultUserId(companyId),
				NotificationTemplate.class.getName(), 0,
				NotificationPortletKeys.NOTIFICATION_TEMPLATES,
				repository.getDlFolderId(), dlFileEntry.getContentStream(),
				_portletFileRepository.getUniqueFileName(
					group.getGroupId(), repository.getDlFolderId(),
					dlFileEntry.getFileName()),
				dlFileEntry.getMimeType(), false);

			fileEntryIds.add(fileEntry.getFileEntryId());
		}

		return fileEntryIds;
	}

	private Repository _getRepository(long groupId) {
		Repository repository = _portletFileRepository.fetchPortletRepository(
			groupId, NotificationPortletKeys.NOTIFICATION_TEMPLATES);

		if (repository != null) {
			return repository;
		}

		try {
			return _portletFileRepository.addPortletRepository(
				groupId, NotificationPortletKeys.NOTIFICATION_TEMPLATES,
				new ServiceContext());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private void _prepareNotificationContext(
		User user, String body, NotificationContext notificationContext,
		Map<String, String> notificationRecipientSettingsEvaluatedMap,
		String subject) {

		NotificationQueueEntry notificationQueueEntry =
			_createNotificationQueueEntry(
				user, body, notificationContext, subject);

		notificationContext.setNotificationQueueEntry(notificationQueueEntry);

		NotificationRecipient notificationQueueEntryRecipient =
			_createNotificationRecipient(
				user, notificationQueueEntry.getNotificationQueueEntryId());

		notificationContext.setNotificationRecipient(
			notificationQueueEntryRecipient);

		notificationContext.setNotificationRecipientSettings(
			_createNotificationRecipientSettings(
				user,
				notificationQueueEntryRecipient.getNotificationRecipientId(),
				notificationRecipientSettingsEvaluatedMap));
	}

	private InternetAddress[] _toInternetAddresses(String string)
		throws Exception {

		List<InternetAddress> internetAddresses = new ArrayList<>();

		for (String internetAddressString : StringUtil.split(string)) {
			internetAddresses.add(new InternetAddress(internetAddressString));
		}

		return internetAddresses.toArray(new InternetAddress[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmailNotificationType.class);

	private static final Pattern _emailAddressPattern = Pattern.compile(
		"[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@" +
			"(?:\\w(?:[\\w-]*\\w)?\\.)+(\\w(?:[\\w-]*\\w))");
	private static final Pattern _termNamePattern = Pattern.compile(
		"\\[%[^\\[%]+%\\]", Pattern.CASE_INSENSITIVE);

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private MailService _mailService;

	@Reference
	private NotificationQueueEntryAttachmentLocalService
		_notificationQueueEntryAttachmentLocalService;

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference
	private NotificationRecipientLocalService
		_notificationRecipientLocalService;

	@Reference
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	@Reference
	private NotificationTemplateAttachmentLocalService
		_notificationTemplateAttachmentLocalService;

	@Reference
	private NotificationTermContributorRegistry
		_notificationTermContributorRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

	private Locale _siteDefaultLocale;
	private Locale _userLocale;

	@Reference
	private UserLocalService _userLocalService;

}