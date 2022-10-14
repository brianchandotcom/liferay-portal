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
import com.liferay.notification.exception.NotificationTemplateFromException;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.notification.service.NotificationQueueEntryAttachmentLocalService;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationTemplateAttachmentLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.term.contributor.NotificationTermContributor;
import com.liferay.notification.term.contributor.NotificationTermContributorRegistry;
import com.liferay.notification.type.BaseNotificationType;
import com.liferay.notification.type.NotificationContext;
import com.liferay.notification.type.NotificationType;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
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
	public String getType() {
		return NotificationConstants.TYPE_EMAIL;
	}

	@Override
	public void sendNotification(NotificationContext notificationContext)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.getNotificationTemplate(
				notificationContext.getNotificationTemplateId());

		User user = _userLocalService.getUser(notificationContext.getUserId());

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
			user.getGroupId());

		notificationContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				"siteDefaultLocale", siteDefaultLocale
			).put(
				"userLocale", user.getLocale()
			).build());

		String bcc = _formatContent(
			notificationTemplate.getBcc(), notificationContext);
		String body = _formatContent(
			notificationTemplate.getBodyMap(), notificationContext);
		String cc = _formatContent(
			notificationTemplate.getCc(), notificationContext);
		String from = _formatContent(
			notificationTemplate.getFrom(), notificationContext);
		String fromName = _formatContent(
			notificationTemplate.getFromNameMap(), notificationContext);
		String subject = _formatContent(
			notificationTemplate.getSubjectMap(), notificationContext);

		String to = _formatTo(
			notificationTemplate.getTo(user.getLocale()), user.getLocale(),
			notificationContext);

		if (Validator.isNull(to)) {
			to = _formatContent(
				notificationTemplate.getTo(siteDefaultLocale),
				siteDefaultLocale,
				NotificationTermContributorConstants.RECIPIENT,
				notificationContext);
		}

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();
		List<Long> fileEntryIds = _getFileEntryIds(
			user.getCompanyId(), notificationContext);

		for (String emailAddressOrUserId : StringUtil.split(to)) {
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

					User defaultUser = _userLocalService.getDefaultUser(
						CompanyThreadLocal.getCompanyId());

					_notificationQueueEntryLocalService.
						addNotificationQueueEntry(
							defaultUser.getUserId(),
							notificationTemplate.getNotificationTemplateId(),
							bcc, body, cc, notificationContext.getClassName(),
							notificationContext.getClassPK(), from, fromName, 0,
							subject, emailAddressOrUserId, emailAddressOrUserId,
							getType(), fileEntryIds);

					continue;
				}
			}

			_notificationQueueEntryLocalService.addNotificationQueueEntry(
				notificationContext.getUserId(),
				notificationTemplate.getNotificationTemplateId(), bcc, body, cc,
				notificationContext.getClassName(),
				notificationContext.getClassPK(), from, fromName, 0, subject,
				toUser.getEmailAddress(), toUser.getFullName(), getType(),
				fileEntryIds);
		}
	}

	@Override
	public void sendUnsentNotifications() {
		for (NotificationQueueEntry notificationQueueEntry :
				_notificationQueueEntryLocalService.
					getUnsentNotificationEntries(
						NotificationConstants.TYPE_EMAIL)) {

			try {
				MailMessage mailMessage = new MailMessage(
					new InternetAddress(
						notificationQueueEntry.getFrom(),
						notificationQueueEntry.getFromName()),
					new InternetAddress(
						notificationQueueEntry.getTo(),
						notificationQueueEntry.getToName()),
					notificationQueueEntry.getSubject(),
					notificationQueueEntry.getBody(), true);

				_addFileAttachments(
					mailMessage,
					notificationQueueEntry.getNotificationQueueEntryId());

				mailMessage.setBCC(
					_toInternetAddresses(notificationQueueEntry.getBcc()));
				mailMessage.setCC(
					_toInternetAddresses(notificationQueueEntry.getCc()));

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
	public void validateNotificationTemplate(
			NotificationContext notificationContext)
		throws PortalException {

		super.validateNotificationTemplate(notificationContext);

		if (Validator.isNull(
				GetterUtil.getString(
					notificationContext.getAttributeValue("from")))) {

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

	private String _formatContent(
			Map<Locale, String> contentMap,
			NotificationContext notificationContext)
		throws PortalException {

		String content = contentMap.get(
			(Locale)notificationContext.getAttributeValue("userLocale"));

		content = _formatContent(
			content,
			(Locale)notificationContext.getAttributeValue("userLocale"), null,
			notificationContext);

		if (Validator.isNull(content)) {
			content = contentMap.get(
				(Locale)notificationContext.getAttributeValue(
					"siteDefaultLocale"));

			return _formatContent(
				content,
				(Locale)notificationContext.getAttributeValue(
					"siteDefaultLocale"),
				null, notificationContext);
		}

		return content;
	}

	private String _formatContent(
			String content, Locale locale,
			String notificationTermContributorKey,
			NotificationContext notificationContext)
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

	private String _formatContent(
			String content, NotificationContext notificationContext)
		throws PortalException {

		content = _formatContent(
			content,
			(Locale)notificationContext.getAttributeValue("userLocale"), null,
			notificationContext);

		if (Validator.isNull(content)) {
			return _formatContent(
				content,
				(Locale)notificationContext.getAttributeValue(
					"siteDefaultLocale"),
				null, notificationContext);
		}

		return content;
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

		return _formatContent(
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

		for (NotificationTemplateAttachment notificationTemplateAttachment :
				_notificationTemplateAttachmentLocalService.
					getNotificationTemplateAttachments(
						notificationContext.getNotificationTemplateId())) {

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
	private static final Pattern _pattern = Pattern.compile(
		"\\[%[^\\[%]+%\\]", Pattern.CASE_INSENSITIVE);

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
	private NotificationTemplateAttachmentLocalService
		_notificationTemplateAttachmentLocalService;

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference
	private NotificationTermContributorRegistry
		_notificationTermContributorRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private UserLocalService _userLocalService;

}