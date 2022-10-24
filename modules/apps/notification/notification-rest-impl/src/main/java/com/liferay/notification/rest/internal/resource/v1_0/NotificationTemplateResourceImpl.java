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
import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.notification.rest.dto.v1_0.NotificationTemplate;
import com.liferay.notification.rest.internal.odata.entity.v1_0.NotificationTemplateEntityModel;
import com.liferay.notification.rest.resource.v1_0.NotificationTemplateResource;
import com.liferay.notification.service.NotificationTemplateAttachmentLocalService;
import com.liferay.notification.service.NotificationTemplateService;
import com.liferay.notification.util.LocalizedMapUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

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

		return _toNotificationTemplate(
			_notificationTemplateService.addNotificationTemplate(
				contextUser.getUserId(),
				GetterUtil.getLong(
					notificationTemplate.getObjectDefinitionId()),
				notificationTemplate.getBcc(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getBody()),
				notificationTemplate.getCc(),
				notificationTemplate.getDescription(),
				notificationTemplate.getFrom(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getFromName()),
				notificationTemplate.getName(),
				notificationTemplate.getRecipientTypeAsString(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getSubject()),
				LocalizedMapUtil.getLocalizedMap(notificationTemplate.getTo()),
				notificationTemplate.getType(),
				ListUtil.fromArray(
					notificationTemplate.getAttachmentObjectFieldIds())));
	}

	@Override
	public NotificationTemplate postNotificationTemplateCopy(
			Long notificationTemplateId)
		throws Exception {

		com.liferay.notification.model.NotificationTemplate
			notificationTemplate =
				_notificationTemplateService.getNotificationTemplate(
					notificationTemplateId);

		return _toNotificationTemplate(
			_notificationTemplateService.addNotificationTemplate(
				notificationTemplate.getUserId(),
				notificationTemplate.getObjectDefinitionId(),
				notificationTemplate.getBcc(),
				notificationTemplate.getBodyMap(), notificationTemplate.getCc(),
				notificationTemplate.getDescription(),
				notificationTemplate.getFrom(),
				notificationTemplate.getFromNameMap(),
				StringUtil.appendParentheticalSuffix(
					notificationTemplate.getName(), "copy"),
				notificationTemplate.getRecipientType(),
				notificationTemplate.getSubjectMap(),
				notificationTemplate.getToMap(), notificationTemplate.getType(),
				transform(
					_notificationTemplateAttachmentLocalService.
						getNotificationTemplateAttachments(
							notificationTemplateId),
					NotificationTemplateAttachment::getObjectFieldId)));
	}

	@Override
	public NotificationTemplate putNotificationTemplate(
			Long notificationTemplateId,
			NotificationTemplate notificationTemplate)
		throws Exception {

		return _toNotificationTemplate(
			_notificationTemplateService.updateNotificationTemplate(
				notificationTemplateId,
				GetterUtil.getLong(
					notificationTemplate.getObjectDefinitionId()),
				notificationTemplate.getBcc(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getBody()),
				notificationTemplate.getCc(),
				notificationTemplate.getDescription(),
				notificationTemplate.getFrom(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getFromName()),
				notificationTemplate.getName(),
				notificationTemplate.getRecipientTypeAsString(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getSubject()),
				LocalizedMapUtil.getLocalizedMap(notificationTemplate.getTo()),
				notificationTemplate.getType(),
				ListUtil.fromArray(
					notificationTemplate.getAttachmentObjectFieldIds())));
	}

	private NotificationTemplate _toNotificationTemplate(
		com.liferay.notification.model.NotificationTemplate
			serviceBuilderNotificationTemplate) {

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
				attachmentObjectFieldIds = transformToArray(
					_notificationTemplateAttachmentLocalService.
						getNotificationTemplateAttachments(
							serviceBuilderNotificationTemplate.
								getNotificationTemplateId()),
					NotificationTemplateAttachment::getObjectFieldId,
					Long.class);
				bcc = serviceBuilderNotificationTemplate.getBcc();
				body = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderNotificationTemplate.getBodyMap());
				cc = serviceBuilderNotificationTemplate.getCc();
				dateCreated =
					serviceBuilderNotificationTemplate.getCreateDate();
				dateModified =
					serviceBuilderNotificationTemplate.getModifiedDate();
				description =
					serviceBuilderNotificationTemplate.getDescription();
				from = serviceBuilderNotificationTemplate.getFrom();
				fromName = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderNotificationTemplate.getFromNameMap());
				id =
					serviceBuilderNotificationTemplate.
						getNotificationTemplateId();
				name = serviceBuilderNotificationTemplate.getName();
				name_i18n = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderNotificationTemplate.getNameMap());
				objectDefinitionId =
					serviceBuilderNotificationTemplate.getObjectDefinitionId();
				recipientType = NotificationTemplate.RecipientType.create(
					serviceBuilderNotificationTemplate.getRecipientType());
				subject = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderNotificationTemplate.getSubjectMap());
				to = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderNotificationTemplate.getToMap());
				type = serviceBuilderNotificationTemplate.getType();
			}
		};
	}

	private static final EntityModel _entityModel =
		new NotificationTemplateEntityModel();

	@Reference
	private NotificationTemplateAttachmentLocalService
		_notificationTemplateAttachmentLocalService;

	@Reference
	private NotificationTemplateService _notificationTemplateService;

}