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

package com.liferay.notification.rest.internal.resource.v1_0;

import com.liferay.notification.constants.NotificationActionKeys;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.notification.rest.dto.v1_0.NotificationTemplate;
import com.liferay.notification.rest.dto.v1_0.util.NotificationUtil;
import com.liferay.notification.rest.internal.odata.entity.v1_0.NotificationTemplateEntityModel;
import com.liferay.notification.rest.resource.v1_0.NotificationTemplateResource;
import com.liferay.notification.service.NotificationTemplateAttachmentLocalService;
import com.liferay.notification.service.NotificationTemplateService;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.notification.util.LocalizedMapUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Locale;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/notification-template.properties",
	scope = ServiceScope.PROTOTYPE, service = NotificationTemplateResource.class
)
public class NotificationTemplateResourceImpl
	extends BaseNotificationTemplateResourceImpl {

	@Override
	public void deleteNotificationTemplate(Long notificationTemplateId)
		throws Exception {

		_notificationTemplateService.deleteNotificationTemplate(
			notificationTemplateId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public NotificationTemplate getNotificationTemplate(
			Long notificationTemplateId)
		throws Exception {

		return _toNotificationTemplate(
			_notificationTemplateService.getNotificationTemplate(
				notificationTemplateId));
	}

	@Override
	public NotificationTemplate getNotificationTemplateByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		return _toNotificationTemplate(
			_notificationTemplateService.
				fetchNotificationTemplateByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId()));
	}

	@Override
	public Page<NotificationTemplate> getNotificationTemplatesPage(
			String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					NotificationActionKeys.ADD_NOTIFICATION_TEMPLATE,
					"postNotificationTemplate",
					NotificationConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getNotificationTemplatesPage",
					NotificationConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).build(),
			booleanQuery -> {
			},
			filter,
			com.liferay.notification.model.NotificationTemplate.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			sorts,
			document -> _toNotificationTemplate(
				_notificationTemplateService.getNotificationTemplate(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public NotificationTemplate postNotificationTemplate(
			NotificationTemplate notificationTemplate)
		throws Exception {

		NotificationContext notificationContext =
			NotificationUtil.toNotificationContext(
				notificationTemplate, _objectFieldLocalService);

		notificationContext.setNotificationRecipient(
			NotificationUtil.toNotificationRecipient(contextUser, 0L));
		notificationContext.setNotificationRecipientSettings(
			NotificationUtil.toNotificationRecipientSetting(
				0L,
				_notificationTypeServiceTracker.getNotificationType(
					notificationTemplate.getType()),
				notificationTemplate.getRecipients(), contextUser));
		notificationContext.setNotificationTemplate(
			NotificationUtil.toNotificationTemplate(
				0L, notificationTemplate, _objectDefinitionLocalService,
				contextUser));

		return _toNotificationTemplate(
			_notificationTemplateService.addNotificationTemplate(
				notificationContext));
	}

	@Override
	public NotificationTemplate postNotificationTemplateCopy(
			Long notificationTemplateId)
		throws Exception {

		NotificationContext notificationContext = new NotificationContext();

		com.liferay.notification.model.NotificationTemplate
			notificationTemplate =
				_notificationTemplateService.getNotificationTemplate(
					notificationTemplateId);

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		notificationContext.setNotificationRecipient(notificationRecipient);
		notificationContext.setNotificationRecipientSettings(
			notificationRecipient.getNotificationRecipientSettings());

		notificationTemplate.setName(
			StringUtil.appendParentheticalSuffix(
				notificationTemplate.getName(), "copy"));

		notificationContext.setNotificationTemplate(notificationTemplate);
		notificationContext.setType(notificationTemplate.getType());

		return _toNotificationTemplate(
			_notificationTemplateService.addNotificationTemplate(
				notificationContext));
	}

	@Override
	public NotificationTemplate putNotificationTemplate(
			Long notificationTemplateId,
			NotificationTemplate notificationTemplate)
		throws Exception {

		NotificationContext notificationContext =
			NotificationUtil.toNotificationContext(
				notificationTemplate, _objectFieldLocalService);

		NotificationRecipient notificationRecipient =
			NotificationUtil.toNotificationRecipient(
				contextUser, notificationTemplateId);

		notificationContext.setNotificationRecipient(notificationRecipient);
		notificationContext.setNotificationRecipientSettings(
			NotificationUtil.toNotificationRecipientSetting(
				notificationRecipient.getNotificationRecipientId(),
				_notificationTypeServiceTracker.getNotificationType(
					notificationTemplate.getType()),
				notificationTemplate.getRecipients(), contextUser));

		notificationContext.setNotificationTemplate(
			NotificationUtil.toNotificationTemplate(
				notificationTemplateId, notificationTemplate,
				_objectDefinitionLocalService, contextUser));

		return _toNotificationTemplate(
			_notificationTemplateService.updateNotificationTemplate(
				notificationContext));
	}

	@Override
	public NotificationTemplate putNotificationTemplateByExternalReferenceCode(
			String externalReferenceCode,
			NotificationTemplate notificationTemplate)
		throws Exception {

		com.liferay.notification.model.NotificationTemplate
			serviceBuilderNotificationTemplate =
				_notificationTemplateService.
					fetchNotificationTemplateByExternalReferenceCode(
						externalReferenceCode, contextCompany.getCompanyId());

		notificationTemplate.setExternalReferenceCode(externalReferenceCode);

		if (serviceBuilderNotificationTemplate != null) {
			return putNotificationTemplate(
				serviceBuilderNotificationTemplate.getNotificationTemplateId(),
				notificationTemplate);
		}

		return postNotificationTemplate(notificationTemplate);
	}

	private Locale _getLocale() {
		if (contextUser != null) {
			return contextUser.getLocale();
		}

		return contextAcceptLanguage.getPreferredLocale();
	}

	private NotificationTemplate _toNotificationTemplate(
		com.liferay.notification.model.NotificationTemplate
			serviceBuilderNotificationTemplate) {

		NotificationRecipient notificationRecipient =
			serviceBuilderNotificationTemplate.getNotificationRecipient();
		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(
				serviceBuilderNotificationTemplate.getType());

		return new NotificationTemplate() {
			{
				actions = HashMapBuilder.put(
					"copy",
					addAction(
						ActionKeys.UPDATE, "postNotificationTemplateCopy",
						com.liferay.notification.model.NotificationTemplate.
							class.getName(),
						serviceBuilderNotificationTemplate.
							getNotificationTemplateId())
				).put(
					"delete",
					addAction(
						ActionKeys.DELETE, "deleteNotificationTemplate",
						com.liferay.notification.model.NotificationTemplate.
							class.getName(),
						serviceBuilderNotificationTemplate.
							getNotificationTemplateId())
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getNotificationTemplate",
						com.liferay.notification.model.NotificationTemplate.
							class.getName(),
						serviceBuilderNotificationTemplate.
							getNotificationTemplateId())
				).put(
					"permissions",
					addAction(
						ActionKeys.PERMISSIONS, "patchNotificationTemplate",
						com.liferay.notification.model.NotificationTemplate.
							class.getName(),
						serviceBuilderNotificationTemplate.
							getNotificationTemplateId())
				).put(
					"update",
					addAction(
						ActionKeys.UPDATE, "putNotificationTemplate",
						com.liferay.notification.model.NotificationTemplate.
							class.getName(),
						serviceBuilderNotificationTemplate.
							getNotificationTemplateId())
				).build();
				attachmentObjectFieldERCs = transformToArray(
					_notificationTemplateAttachmentLocalService.
						getNotificationTemplateAttachments(
							serviceBuilderNotificationTemplate.
								getNotificationTemplateId()),
					notificationTemplateAttachment -> {
						ObjectField objectField =
							_objectFieldLocalService.getObjectField(
								notificationTemplateAttachment.
									getObjectFieldId());

						return objectField.getExternalReferenceCode();
					},
					String.class);
				attachmentObjectFieldIds = transformToArray(
					_notificationTemplateAttachmentLocalService.
						getNotificationTemplateAttachments(
							serviceBuilderNotificationTemplate.
								getNotificationTemplateId()),
					NotificationTemplateAttachment::getObjectFieldId,
					Long.class);
				body = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderNotificationTemplate.getBodyMap());
				dateCreated =
					serviceBuilderNotificationTemplate.getCreateDate();
				dateModified =
					serviceBuilderNotificationTemplate.getModifiedDate();
				description =
					serviceBuilderNotificationTemplate.getDescription();
				editorType = NotificationTemplate.EditorType.create(
					serviceBuilderNotificationTemplate.getEditorType());
				externalReferenceCode =
					serviceBuilderNotificationTemplate.
						getExternalReferenceCode();
				id =
					serviceBuilderNotificationTemplate.
						getNotificationTemplateId();
				name = serviceBuilderNotificationTemplate.getName();
				name_i18n = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderNotificationTemplate.getNameMap());
				objectDefinitionId =
					serviceBuilderNotificationTemplate.getObjectDefinitionId();
				recipients = notificationType.toRecipients(
					notificationRecipient.getNotificationRecipientSettings());
				recipientType =
					serviceBuilderNotificationTemplate.getRecipientType();
				subject = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderNotificationTemplate.getSubjectMap());
				type = serviceBuilderNotificationTemplate.getType();
				typeLabel = _language.get(
					_getLocale(), notificationType.getTypeLanguageKey());

				setObjectDefinitionERC(
					() -> {
						ObjectDefinition objectDefinition =
							_objectDefinitionLocalService.fetchObjectDefinition(
								GetterUtil.getLong(
									serviceBuilderNotificationTemplate.
										getObjectDefinitionId()));

						if (objectDefinition == null) {
							return StringPool.BLANK;
						}

						return objectDefinition.getExternalReferenceCode();
					});
			}
		};
	}

	private static final EntityModel _entityModel =
		new NotificationTemplateEntityModel();

	@Reference
	private Language _language;

	@Reference
	private NotificationTemplateAttachmentLocalService
		_notificationTemplateAttachmentLocalService;

	@Reference
	private NotificationTemplateService _notificationTemplateService;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}