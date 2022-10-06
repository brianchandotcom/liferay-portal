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

package com.liferay.notification.service.impl;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationPortletKeys;
import com.liferay.notification.constants.NotificationTermContributorConstants;
import com.liferay.notification.exception.NotificationTemplateAttachmentObjectFieldIdException;
import com.liferay.notification.exception.NotificationTemplateFromException;
import com.liferay.notification.exception.NotificationTemplateNameException;
import com.liferay.notification.exception.NotificationTemplateObjectDefinitionIdException;
import com.liferay.notification.exception.NotificationTemplateTypeException;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationTemplateAttachmentLocalService;
import com.liferay.notification.service.base.NotificationTemplateLocalServiceBaseImpl;
import com.liferay.notification.service.persistence.NotificationQueueEntryPersistence;
import com.liferay.notification.service.persistence.NotificationTemplateAttachmentPersistence;
import com.liferay.notification.term.contributor.NotificationTermContributor;
import com.liferay.notification.term.contributor.NotificationTermContributorRegistry;
import com.liferay.notification.type.LegacyNotificationType;
import com.liferay.notification.util.LegacyNotificationTypeRegistry;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.EmailAddressValidatorFactory;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Gustavo Lima
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationTemplate",
	service = AopService.class
)
public class NotificationTemplateLocalServiceImpl
	extends NotificationTemplateLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationTemplate addNotificationTemplate(
			long userId, long objectDefinitionId, String bcc,
			Map<Locale, String> bodyMap, String cc, String description,
			String from, Map<Locale, String> fromNameMap, String name,
			String recipientType, Map<Locale, String> subjectMap,
			Map<Locale, String> toMap, String type,
			List<Long> attachmentObjectFieldIds)
		throws PortalException {

		_validate(
			objectDefinitionId, from, name, type, attachmentObjectFieldIds);

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		notificationTemplate.setCompanyId(user.getCompanyId());
		notificationTemplate.setUserId(user.getUserId());
		notificationTemplate.setUserName(user.getFullName());

		notificationTemplate.setObjectDefinitionId(objectDefinitionId);
		notificationTemplate.setBcc(bcc);
		notificationTemplate.setBodyMap(bodyMap);
		notificationTemplate.setCc(cc);
		notificationTemplate.setDescription(description);
		notificationTemplate.setFrom(from);
		notificationTemplate.setFromNameMap(fromNameMap);
		notificationTemplate.setName(name);
		notificationTemplate.setRecipientType(recipientType);
		notificationTemplate.setSubjectMap(subjectMap);
		notificationTemplate.setToMap(toMap);
		notificationTemplate.setType(type);

		notificationTemplate = notificationTemplatePersistence.update(
			notificationTemplate);

		_resourceLocalService.addResources(
			notificationTemplate.getCompanyId(), 0,
			notificationTemplate.getUserId(),
			NotificationTemplate.class.getName(),
			notificationTemplate.getNotificationTemplateId(), false, true,
			true);

		for (long attachmentObjectFieldId : attachmentObjectFieldIds) {
			_notificationTemplateAttachmentLocalService.
				addNotificationTemplateAttachment(
					notificationTemplate.getCompanyId(),
					notificationTemplate.getNotificationTemplateId(),
					attachmentObjectFieldId);
		}

		return notificationTemplate;
	}

	@Override
	public NotificationTemplate deleteNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		return notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationTemplate deleteNotificationTemplate(
			NotificationTemplate notificationTemplate)
		throws PortalException {

		notificationTemplate = notificationTemplatePersistence.remove(
			notificationTemplate);

		_resourceLocalService.deleteResource(
			notificationTemplate, ResourceConstants.SCOPE_INDIVIDUAL);

		List<NotificationQueueEntry> notificationQueueEntries =
			_notificationQueueEntryPersistence.findByNotificationTemplateId(
				notificationTemplate.getNotificationTemplateId());

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntries) {

			notificationQueueEntry.setNotificationTemplateId(0);

			_notificationQueueEntryPersistence.update(notificationQueueEntry);
		}

		_notificationTemplateAttachmentPersistence.
			removeByNotificationTemplateId(
				notificationTemplate.getNotificationTemplateId());

		return notificationTemplate;
	}

	@Override
	public void sendNotificationTemplate(
			long userId, long notificationTemplateId,
			String notificationTypeKey, Object object)
		throws PortalException {

		if (Validator.isBlank(notificationTypeKey)) {
			return;
		}

		LegacyNotificationType notificationType =
			_notificationTypeRegistry.getNotificationType(notificationTypeKey);

		if (notificationType == null) {
			return;
		}

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		User user = _userLocalService.getUser(userId);

		String bcc = _formatContent(
			notificationTemplate.getBcc(), user.getLocale(), null,
			notificationType, object);

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
			user.getGroupId());

		if (Validator.isNull(bcc)) {
			bcc = _formatContent(
				notificationTemplate.getBcc(), siteDefaultLocale, null,
				notificationType, object);
		}

		String body = _formatContent(
			notificationTemplate.getBody(user.getLocale()), user.getLocale(),
			null, notificationType, object);

		if (Validator.isNull(body)) {
			body = _formatContent(
				notificationTemplate.getBody(siteDefaultLocale),
				siteDefaultLocale, null, notificationType, object);
		}

		String cc = _formatContent(
			notificationTemplate.getCc(), user.getLocale(), null,
			notificationType, object);

		if (Validator.isNull(cc)) {
			cc = _formatContent(
				notificationTemplate.getCc(), siteDefaultLocale, null,
				notificationType, object);
		}

		String from = _formatContent(
			notificationTemplate.getFrom(), user.getLocale(), null,
			notificationType, object);

		if (Validator.isNull(from)) {
			from = _formatContent(
				notificationTemplate.getFrom(), siteDefaultLocale, null,
				notificationType, object);
		}

		String fromName = _formatContent(
			notificationTemplate.getFromName(user.getLocale()),
			user.getLocale(), null, notificationType, object);

		if (Validator.isNull(fromName)) {
			fromName = _formatContent(
				notificationTemplate.getFromName(siteDefaultLocale),
				siteDefaultLocale, null, notificationType, object);
		}

		String subject = _formatContent(
			notificationTemplate.getSubject(user.getLocale()), user.getLocale(),
			null, notificationType, object);

		if (Validator.isNull(subject)) {
			subject = _formatContent(
				notificationTemplate.getSubject(siteDefaultLocale),
				siteDefaultLocale, null, notificationType, object);
		}

		String to = _formatTo(
			notificationTemplate.getTo(user.getLocale()), user.getLocale(),
			notificationType, object);

		if (Validator.isNull(to)) {
			to = _formatContent(
				notificationTemplate.getTo(siteDefaultLocale),
				siteDefaultLocale,
				NotificationTermContributorConstants.RECIPIENT,
				notificationType, object);
		}

		EmailAddressValidator emailAddressValidator =
			EmailAddressValidatorFactory.getInstance();
		List<Long> fileEntryIds = _getFileEntryIds(
			user.getCompanyId(), notificationTemplateId, object);

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
							bcc, body, cc,
							notificationType.getClassName(object),
							notificationType.getClassPK(object), from, fromName,
							0, subject, emailAddressOrUserId,
							emailAddressOrUserId, fileEntryIds);

					continue;
				}
			}

			_notificationQueueEntryLocalService.addNotificationQueueEntry(
				userId, notificationTemplate.getNotificationTemplateId(), bcc,
				body, cc, notificationType.getClassName(object),
				notificationType.getClassPK(object), from, fromName, 0, subject,
				toUser.getEmailAddress(), toUser.getFullName(), fileEntryIds);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationTemplate updateNotificationTemplate(
			long notificationTemplateId, long objectDefinitionId, String bcc,
			Map<Locale, String> bodyMap, String cc, String description,
			String from, Map<Locale, String> fromNameMap, String name,
			String recipientType, Map<Locale, String> subjectMap,
			Map<Locale, String> toMap, String type,
			List<Long> attachmentObjectFieldIds)
		throws PortalException {

		_validate(
			objectDefinitionId, from, name, type, attachmentObjectFieldIds);

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		notificationTemplate.setObjectDefinitionId(objectDefinitionId);
		notificationTemplate.setBcc(bcc);
		notificationTemplate.setBodyMap(bodyMap);
		notificationTemplate.setCc(cc);
		notificationTemplate.setDescription(description);
		notificationTemplate.setFrom(from);
		notificationTemplate.setFromNameMap(fromNameMap);
		notificationTemplate.setName(name);
		notificationTemplate.setRecipientType(recipientType);
		notificationTemplate.setSubjectMap(subjectMap);
		notificationTemplate.setToMap(toMap);

		notificationTemplate = notificationTemplatePersistence.update(
			notificationTemplate);

		List<Long> oldAttachmentObjectFieldIds = new ArrayList<>();

		for (NotificationTemplateAttachment notificationTemplateAttachment :
				_notificationTemplateAttachmentPersistence.
					findByNotificationTemplateId(
						notificationTemplate.getNotificationTemplateId())) {

			if (ListUtil.exists(
					attachmentObjectFieldIds,
					attachmentObjectFieldId -> Objects.equals(
						attachmentObjectFieldId,
						notificationTemplateAttachment.getObjectFieldId()))) {

				oldAttachmentObjectFieldIds.add(
					notificationTemplateAttachment.getObjectFieldId());

				continue;
			}

			_notificationTemplateAttachmentPersistence.remove(
				notificationTemplateAttachment);
		}

		for (long attachmentObjectFieldId :
				ListUtil.remove(
					attachmentObjectFieldIds, oldAttachmentObjectFieldIds)) {

			_notificationTemplateAttachmentLocalService.
				addNotificationTemplateAttachment(
					notificationTemplate.getCompanyId(),
					notificationTemplate.getNotificationTemplateId(),
					attachmentObjectFieldId);
		}

		return notificationTemplate;
	}

	private String _formatContent(
			String content, Locale locale,
			List<NotificationTermContributor> notificationTermContributors,
			Object object, List<String> termNames)
		throws PortalException {

		for (NotificationTermContributor notificationTermContributor :
				notificationTermContributors) {

			for (String termName : termNames) {
				content = StringUtil.replace(
					content, termName,
					notificationTermContributor.getTermValue(
						locale, object, termName));
			}
		}

		return content;
	}

	private String _formatContent(
			String content, Locale locale,
			String notificationTermContributorKey,
			LegacyNotificationType notificationType, Object object)
		throws PortalException {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		List<String> termNames = new ArrayList<>();

		Matcher matcher = _pattern.matcher(content);

		while (matcher.find()) {
			termNames.add(matcher.group());
		}

		if (Validator.isNotNull(notificationTermContributorKey)) {
			content = _formatContent(
				content, locale,
				_notificationTermContributorRegistry.
					getNotificationTermContributorsByNotificationTermContributorKey(
						notificationTermContributorKey),
				object, termNames);
		}

		return _formatContent(
			content, locale,
			_notificationTermContributorRegistry.
				getNotificationTermContributorsByNotificationTypeKey(
					notificationType.getKey()),
			object, termNames);
	}

	private String _formatTo(
			String to, Locale locale, LegacyNotificationType notificationType,
			Object object)
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
			NotificationTermContributorConstants.RECIPIENT, notificationType,
			object);
	}

	private List<Long> _getFileEntryIds(
			long companyId, long notificationTemplateId, Object object)
		throws PortalException {

		if (!(object instanceof Map)) {
			return new ArrayList<>();
		}

		Group group = _groupLocalService.getCompanyGroup(companyId);

		Repository repository = _getRepository(group.getGroupId());

		if (repository == null) {
			return new ArrayList<>();
		}

		List<Long> fileEntryIds = new ArrayList<>();

		for (NotificationTemplateAttachment notificationTemplateAttachment :
				_notificationTemplateAttachmentPersistence.
					findByNotificationTemplateId(notificationTemplateId)) {

			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				notificationTemplateAttachment.getObjectFieldId());

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
				MapUtil.getLong(
					(Map<String, Object>)object, objectField.getName()));

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

	private void _validate(
			long objectDefinitionId, String from, String name, String type,
			List<Long> attachmentObjectFieldIds)
		throws PortalException {

		if (objectDefinitionId > 0) {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectDefinitionId);

			if (objectDefinition == null) {
				throw new NotificationTemplateObjectDefinitionIdException();
			}
		}

		if (!Objects.equals(
				NotificationConstants.TYPE_USER_NOTIFICATION, type) &&
			Validator.isNull(from)) {

			throw new NotificationTemplateFromException("From is null");
		}

		if (Validator.isNull(name)) {
			throw new NotificationTemplateNameException("Name is null");
		}

		if (type == null) {
			if (GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-162133"))) {

				throw new NotificationTemplateTypeException();
			}

			type = NotificationConstants.TYPE_EMAIL;
		}

		for (long attachmentObjectFieldId : attachmentObjectFieldIds) {
			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				attachmentObjectFieldId);

			if ((objectField == null) ||
				!Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
				!Objects.equals(
					objectField.getObjectDefinitionId(), objectDefinitionId)) {

				throw new NotificationTemplateAttachmentObjectFieldIdException();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationTemplateLocalServiceImpl.class);

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
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference
	private NotificationQueueEntryPersistence
		_notificationQueueEntryPersistence;

	@Reference
	private NotificationTemplateAttachmentLocalService
		_notificationTemplateAttachmentLocalService;

	@Reference
	private NotificationTemplateAttachmentPersistence
		_notificationTemplateAttachmentPersistence;

	@Reference
	private NotificationTermContributorRegistry
		_notificationTermContributorRegistry;

	@Reference
	private LegacyNotificationTypeRegistry _notificationTypeRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}