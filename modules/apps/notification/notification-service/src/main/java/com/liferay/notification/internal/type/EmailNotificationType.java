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
import com.liferay.notification.service.NotificationTemplateAttachmentLocalService;
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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
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
				notificationRecipientSettingLocalService.
					createNotificationRecipientSetting(
						counterLocalService.increment());

			notificationRecipientSetting.setCompanyId(user.getCompanyId());
			notificationRecipientSetting.setUserId(user.getUserId());
			notificationRecipientSetting.setUserName(user.getFullName());

			notificationRecipientSetting.setNotificationRecipientId(
				notificationRecipientId);
			notificationRecipientSetting.setName(entry.getKey());

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
	public String getTypeLanguageKey() {
		return "email";
	}

	@Override
	public void sendNotification(NotificationContext notificationContext)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		User user = userLocalService.getUser(notificationContext.getUserId());

		siteDefaultLocale = portal.getSiteDefaultLocale(user.getGroupId());
		userLocale = user.getLocale();

		notificationContext.setFileEntryIds(
			_getFileEntryIds(user.getCompanyId(), notificationContext));

		String body = formatLocalizedContent(
			notificationTemplate.getBodyMap(), notificationContext);
		String subject = formatLocalizedContent(
			notificationTemplate.getSubjectMap(), notificationContext);

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		Map<String, String> notificationRecipientSettingsEvaluatedMap =
			HashMapBuilder.put(
				"bcc",
				formatContent(
					"bcc", notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				"cc",
				formatContent(
					"cc", notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				"from",
				formatContent(
					"from", notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				"fromName",
				() -> {
					NotificationRecipientSetting notificationRecipientSetting =
						notificationRecipientSettingLocalService.
							getNotificationRecipientSetting(
								notificationRecipient.
									getNotificationRecipientId(),
								"fromName");

					return formatLocalizedContent(
						notificationRecipientSetting.getValueMap(),
						notificationContext);
				}
			).put(
				"to",
				() -> {
					NotificationRecipientSetting notificationRecipientSetting =
						notificationRecipientSettingLocalService.
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

					return formatLocalizedContent(
						notificationRecipientSetting.getValue(
							siteDefaultLocale),
						siteDefaultLocale,
						NotificationTermContributorConstants.RECIPIENT,
						notificationContext);
				}
			).build();

		for (String emailAddressOrUserId :
				StringUtil.split(
					notificationRecipientSettingsEvaluatedMap.get("to"))) {

			User toUser = userLocalService.fetchUser(
				GetterUtil.getLong(emailAddressOrUserId));

			if ((toUser == null) &&
				emailAddressValidator.validate(
					user.getCompanyId(), emailAddressOrUserId)) {

				toUser = userLocalService.fetchUserByEmailAddress(
					user.getCompanyId(), emailAddressOrUserId);

				if (toUser == null) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"No user exists with email address " +
								emailAddressOrUserId);
					}

					prepareNotificationContext(
						userLocalService.getDefaultUser(
							CompanyThreadLocal.getCompanyId()),
						body, notificationContext,
						notificationRecipientSettingsEvaluatedMap, subject);

					notificationQueueEntryLocalService.
						addNotificationQueueEntry(notificationContext);

					continue;
				}
			}

			prepareNotificationContext(
				user, body, notificationContext,
				notificationRecipientSettingsEvaluatedMap, subject);

			notificationQueueEntryLocalService.addNotificationQueueEntry(
				notificationContext);
		}
	}

	@Override
	public void sendUnsentNotifications() {
		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntryLocalService.getUnsentNotificationEntries(
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

				notificationQueueEntryLocalService.updateStatus(
					notificationQueueEntry.getNotificationQueueEntryId(),
					NotificationQueueEntryConstants.STATUS_SENT);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				notificationQueueEntry.setStatus(
					NotificationQueueEntryConstants.STATUS_FAILED);

				notificationQueueEntryLocalService.updateNotificationQueueEntry(
					notificationQueueEntry);
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

		return formatLocalizedContent(
			StringUtil.merge(emailAddresses), locale,
			NotificationTermContributorConstants.RECIPIENT,
			notificationContext);
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
				userLocalService.getDefaultUserId(companyId),
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
	private NotificationTemplateAttachmentLocalService
		_notificationTemplateAttachmentLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

}