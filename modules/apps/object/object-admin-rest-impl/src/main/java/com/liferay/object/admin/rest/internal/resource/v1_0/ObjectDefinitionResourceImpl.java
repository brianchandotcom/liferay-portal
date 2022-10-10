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

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.dto.v1_0.Status;
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectActionUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.converter.ObjectFieldDTOConverter;
import com.liferay.object.admin.rest.internal.dto.v1_0.converter.ObjectRelationshipDTOConverter;
import com.liferay.object.admin.rest.internal.dto.v1_0.converter.ObjectViewDTOConverter;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldSettingUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectLayoutUtil;
import com.liferay.object.admin.rest.internal.odata.entity.v1_0.ObjectDefinitionEntityModel;
import com.liferay.object.admin.rest.resource.v1_0.ObjectActionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectLayoutResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectViewResource;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.exception.ObjectDefinitionStorageTypeException;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectActionService;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFilterLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.service.ObjectViewService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectDefinitionResource.class
)
public class ObjectDefinitionResourceImpl
	extends BaseObjectDefinitionResourceImpl {

	@Override
	public void create(
			Collection<ObjectDefinition> objectDefinitions,
			Map<String, Serializable> parameters)
		throws Exception {

		super.create(objectDefinitions, parameters);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			Status status = objectDefinition.getStatus();

			if ((status == null) ||
				(status.getCode() != WorkflowConstants.STATUS_APPROVED)) {

				continue;
			}

			com.liferay.object.model.ObjectDefinition
				serviceBuilderObjectDefinition =
					_objectDefinitionService.
						getObjectDefinitionByExternalReferenceCode(
							objectDefinition.getExternalReferenceCode(),
							contextCompany.getCompanyId());

			_objectDefinitionService.publishCustomObjectDefinition(
				serviceBuilderObjectDefinition.getObjectDefinitionId());
		}
	}

	@Override
	public void deleteObjectDefinition(Long objectDefinitionId)
		throws Exception {

		_objectDefinitionService.deleteObjectDefinition(objectDefinitionId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public ObjectDefinition getObjectDefinition(Long objectDefinitionId)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionService.getObjectDefinition(objectDefinitionId));
	}

	@Override
	public ObjectDefinition getObjectDefinitionByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionService.
				fetchObjectDefinitionByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId()));
	}

	@Override
	public Page<ObjectDefinition> getObjectDefinitionsPage(
			String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ObjectActionKeys.ADD_OBJECT_DEFINITION,
					"postObjectDefinition", ObjectConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"createBatch",
				addAction(
					ObjectActionKeys.ADD_OBJECT_DEFINITION,
					"postObjectDefinitionBatch", ObjectConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"deleteBatch",
				addAction(
					ActionKeys.DELETE, "deleteObjectDefinitionBatch",
					ObjectConstants.RESOURCE_NAME, null)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectDefinitionsPage",
					ObjectConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"updateBatch",
				addAction(
					ActionKeys.UPDATE, "putObjectDefinitionBatch",
					ObjectConstants.RESOURCE_NAME, null)
			).build(),
			booleanQuery -> {
			},
			filter, com.liferay.object.model.ObjectDefinition.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			sorts,
			document -> _toObjectDefinition(
				_objectDefinitionService.getObjectDefinition(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectDefinition postObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		if (!Validator.isBlank(objectDefinition.getStorageType()) &&
			!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-135430"))) {

			throw new ObjectDefinitionStorageTypeException();
		}

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionService.addCustomObjectDefinition(
					LocalizedMapUtil.getLocalizedMap(
						objectDefinition.getLabel()),
					objectDefinition.getName(),
					objectDefinition.getPanelAppOrder(),
					objectDefinition.getPanelCategoryKey(),
					LocalizedMapUtil.getLocalizedMap(
						objectDefinition.getPluralLabel()),
					objectDefinition.getScope(),
					objectDefinition.getStorageType(),
					transformToList(
						objectDefinition.getObjectFields(),
						objectField -> ObjectFieldUtil.toObjectField(
							_listTypeDefinitionLocalService, objectField,
							_objectFieldLocalService,
							_objectFieldSettingLocalService,
							_objectFilterLocalService)));

		if (!Validator.isBlank(objectDefinition.getExternalReferenceCode())) {
			serviceBuilderObjectDefinition =
				_objectDefinitionService.updateExternalReferenceCode(
					serviceBuilderObjectDefinition.getObjectDefinitionId(),
					objectDefinition.getExternalReferenceCode());
		}

		com.liferay.object.model.ObjectField serviceBuilderObjectField =
			_objectFieldLocalService.fetchObjectField(
				serviceBuilderObjectDefinition.getObjectDefinitionId(),
				objectDefinition.getTitleObjectFieldName());

		if (serviceBuilderObjectField != null) {
			serviceBuilderObjectDefinition =
				_objectDefinitionService.updateTitleObjectFieldId(
					serviceBuilderObjectDefinition.getObjectDefinitionId(),
					serviceBuilderObjectField.getObjectFieldId());
		}

		_addObjectDefinitionResources(
			objectDefinition.getObjectActions(),
			serviceBuilderObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectLayouts(),
			objectDefinition.getObjectRelationships(),
			objectDefinition.getObjectViews());

		return _toObjectDefinition(serviceBuilderObjectDefinition);
	}

	@Override
	public void postObjectDefinitionPublish(Long objectDefinitionId)
		throws Exception {

		_objectDefinitionService.publishCustomObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public ObjectDefinition putObjectDefinition(
			Long objectDefinitionId, ObjectDefinition objectDefinition)
		throws Exception {

		// TODO Move logic to service

		if (!Validator.isBlank(objectDefinition.getStorageType())) {
			throw new ObjectDefinitionStorageTypeException();
		}

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionService.getObjectDefinition(
					objectDefinitionId);

		long titleObjectFieldId = 0;

		com.liferay.object.model.ObjectField titleServiceBuilderObjectField =
			_objectFieldLocalService.fetchObjectField(
				objectDefinitionId, objectDefinition.getTitleObjectFieldName());

		if (titleServiceBuilderObjectField != null) {
			titleObjectFieldId =
				titleServiceBuilderObjectField.getObjectFieldId();
		}

		if (serviceBuilderObjectDefinition.isSystem()) {
			return _toObjectDefinition(
				_objectDefinitionService.updateSystemObjectDefinition(
					objectDefinition.getExternalReferenceCode(),
					objectDefinitionId, titleObjectFieldId));
		}

		serviceBuilderObjectDefinition =
			_objectDefinitionService.updateCustomObjectDefinition(
				objectDefinition.getExternalReferenceCode(), objectDefinitionId,
				GetterUtil.getLong(
					objectDefinition.getAccountEntryRestrictedObjectFieldId()),
				0, titleObjectFieldId,
				GetterUtil.getBoolean(
					objectDefinition.getAccountEntryRestricted()),
				GetterUtil.getBoolean(objectDefinition.getActive(), true),
				objectDefinition.getEnableCategorization(),
				objectDefinition.getEnableComments(),
				objectDefinition.getEnableObjectEntryHistory(),
				LocalizedMapUtil.getLocalizedMap(objectDefinition.getLabel()),
				objectDefinition.getName(), objectDefinition.getPanelAppOrder(),
				objectDefinition.getPanelCategoryKey(),
				objectDefinition.getPortlet(),
				LocalizedMapUtil.getLocalizedMap(
					objectDefinition.getPluralLabel()),
				objectDefinition.getScope());

		List<com.liferay.object.model.ObjectField> serviceBuilderObjectFields =
			new ArrayList<>(
				_objectFieldLocalService.getObjectFields(objectDefinitionId));

		for (ObjectField objectField : objectDefinition.getObjectFields()) {
			_objectFieldLocalService.updateObjectField(
				objectField.getExternalReferenceCode(), contextUser.getUserId(),
				objectDefinitionId, GetterUtil.getLong(objectField.getId()),
				ObjectFieldUtil.getListTypeDefinitionId(
					serviceBuilderObjectDefinition.getCompanyId(),
					_listTypeDefinitionLocalService, objectField),
				objectField.getBusinessTypeAsString(), null, null,
				objectField.getDBTypeAsString(), objectField.getDefaultValue(),
				objectField.getIndexed(), objectField.getIndexedAsKeyword(),
				objectField.getIndexedLanguageId(),
				com.liferay.portal.vulcan.util.LocalizedMapUtil.getLocalizedMap(
					objectField.getLabel()),
				objectField.getName(), objectField.getRequired(),
				GetterUtil.getBoolean(objectField.getState()),
				objectField.getSystem(),
				transformToList(
					objectField.getObjectFieldSettings(),
					objectFieldSetting ->
						ObjectFieldSettingUtil.toObjectFieldSetting(
							objectField.getBusinessTypeAsString(),
							objectFieldSetting, _objectFieldSettingLocalService,
							_objectFilterLocalService)));

			serviceBuilderObjectFields.removeIf(
				serviceBuilderObjectField -> Objects.equals(
					serviceBuilderObjectField.getName(),
					objectField.getName()));
		}

		for (com.liferay.object.model.ObjectField serviceBuilderObjectField :
				serviceBuilderObjectFields) {

			if (Objects.equals(
					serviceBuilderObjectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

				continue;
			}

			_objectFieldLocalService.deleteObjectField(
				serviceBuilderObjectField);
		}

		ObjectAction[] objectActions = objectDefinition.getObjectActions();

		if (objectActions != null) {
			_objectActionLocalService.deleteObjectActions(objectDefinitionId);
		}

		ObjectLayout[] objectLayouts = objectDefinition.getObjectLayouts();

		if (objectLayouts != null) {
			_objectLayoutLocalService.deleteObjectLayouts(objectDefinitionId);
		}

		ObjectRelationship[] objectRelationships =
			objectDefinition.getObjectRelationships();

		if (objectRelationships != null) {
			_objectRelationshipLocalService.deleteObjectRelationships(
				objectDefinitionId);
		}

		ObjectView[] objectViews = objectDefinition.getObjectViews();

		if (objectViews != null) {
			_objectViewLocalService.deleteObjectViews(objectDefinitionId);
		}

		_addObjectDefinitionResources(
			objectActions, objectDefinitionId, objectLayouts,
			objectRelationships, objectViews);

		return _toObjectDefinition(serviceBuilderObjectDefinition);
	}

	@Override
	public ObjectDefinition putObjectDefinitionByExternalReferenceCode(
			String externalReferenceCode, ObjectDefinition objectDefinition)
		throws Exception {

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionService.
					fetchObjectDefinitionByExternalReferenceCode(
						externalReferenceCode, contextCompany.getCompanyId());

		objectDefinition.setExternalReferenceCode(externalReferenceCode);

		if (serviceBuilderObjectDefinition != null) {
			return putObjectDefinition(
				serviceBuilderObjectDefinition.getObjectDefinitionId(),
				objectDefinition);
		}

		return postObjectDefinition(objectDefinition);
	}

	private void _addObjectDefinitionResources(
			ObjectAction[] objectActions, long objectDefinitionId,
			ObjectLayout[] objectLayouts,
			ObjectRelationship[] objectRelationships, ObjectView[] objectViews)
		throws Exception {

		if (objectActions != null) {
			ObjectActionResource.Builder builder =
				_objectActionResourceFactory.create();

			ObjectActionResource objectActionResource = builder.user(
				contextUser
			).build();

			for (ObjectAction objectAction : objectActions) {
				objectActionResource.postObjectDefinitionObjectAction(
					objectDefinitionId, objectAction);
			}
		}

		if (objectLayouts != null) {
			ObjectLayoutResource.Builder builder =
				_objectLayoutResourceFactory.create();

			ObjectLayoutResource objectLayoutResource = builder.user(
				contextUser
			).build();

			for (ObjectLayout objectLayout : objectLayouts) {
				objectLayoutResource.postObjectDefinitionObjectLayout(
					objectDefinitionId, objectLayout);
			}
		}

		if (objectRelationships != null) {
			ObjectRelationshipResource.Builder builder =
				_objectRelationshipResourceFactory.create();

			ObjectRelationshipResource objectRelationshipResource =
				builder.user(
					contextUser
				).build();

			for (ObjectRelationship objectRelationship : objectRelationships) {
				objectRelationshipResource.
					postObjectDefinitionObjectRelationship(
						objectDefinitionId, objectRelationship);
			}
		}

		if (objectViews != null) {
			ObjectViewResource.Builder builder =
				_objectViewResourceFactory.create();

			ObjectViewResource objectViewResource = builder.user(
				contextUser
			).build();

			for (ObjectView objectView : objectViews) {
				objectViewResource.postObjectDefinitionObjectView(
					objectDefinitionId, objectView);
			}
		}
	}

	private ObjectDefinition _toObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		if (objectDefinition == null) {
			return null;
		}

		String permissionName =
			com.liferay.object.model.ObjectDefinition.class.getName();

		String restContextPath = StringPool.BLANK;

		if (objectDefinition.isSystem()) {
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
				_systemObjectDefinitionMetadataTracker.
					getSystemObjectDefinitionMetadata(
						objectDefinition.getName());

			if (systemObjectDefinitionMetadata != null) {
				restContextPath =
					"/o/" + systemObjectDefinitionMetadata.getRESTContextPath();
			}
		}
		else {
			restContextPath = "/o" + objectDefinition.getRESTContextPath();
		}

		String finalRESTContextPath = restContextPath;

		return new ObjectDefinition() {
			{
				accountEntryRestricted =
					objectDefinition.isAccountEntryRestricted();
				accountEntryRestrictedObjectFieldId =
					objectDefinition.getAccountEntryRestrictedObjectFieldId();
				actions = HashMapBuilder.put(
					"delete",
					() -> {
						if (objectDefinition.isSystem()) {
							return null;
						}

						return addAction(
							ActionKeys.DELETE, "deleteObjectDefinition",
							permissionName,
							objectDefinition.getObjectDefinitionId());
					}
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getObjectDefinition", permissionName,
						objectDefinition.getObjectDefinitionId())
				).put(
					"permissions",
					addAction(
						ActionKeys.PERMISSIONS, "patchObjectDefinition",
						permissionName,
						objectDefinition.getObjectDefinitionId())
				).put(
					"publish",
					() -> {
						if (objectDefinition.isApproved()) {
							return null;
						}

						return addAction(
							ActionKeys.UPDATE, "postObjectDefinitionPublish",
							permissionName,
							objectDefinition.getObjectDefinitionId());
					}
				).put(
					"update",
					() -> {
						if (objectDefinition.isSystem()) {
							return null;
						}

						return addAction(
							ActionKeys.UPDATE, "putObjectDefinition",
							permissionName,
							objectDefinition.getObjectDefinitionId());
					}
				).build();
				active = objectDefinition.isActive();
				dateCreated = objectDefinition.getCreateDate();
				dateModified = objectDefinition.getModifiedDate();
				enableCategorization =
					objectDefinition.getEnableCategorization();
				enableComments = objectDefinition.getEnableComments();
				enableObjectEntryHistory =
					objectDefinition.getEnableObjectEntryHistory();
				externalReferenceCode =
					objectDefinition.getExternalReferenceCode();
				id = objectDefinition.getObjectDefinitionId();
				label = LocalizedMapUtil.getLanguageIdMap(
					objectDefinition.getLabelMap());
				name = objectDefinition.getShortName();
				objectActions = transformToArray(
					_objectActionLocalService.getObjectActions(
						objectDefinition.getObjectDefinitionId()),
					objectAction -> ObjectActionUtil.toObjectAction(
						null, contextAcceptLanguage.getPreferredLocale(),
						objectAction),
					ObjectAction.class);
				objectFields = transformToArray(
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId()),
					objectField -> _objectFieldDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							false, null, null, null,
							contextAcceptLanguage.getPreferredLocale(), null,
							null),
						objectField),
					ObjectField.class);
				objectLayouts = transformToArray(
					_objectLayoutLocalService.getObjectLayouts(
						objectDefinition.getObjectDefinitionId()),
					objectLayout -> ObjectLayoutUtil.toObjectLayout(
						null, _objectFieldLocalService, objectLayout),
					ObjectLayout.class);
				objectRelationships = transformToArray(
					_objectRelationshipLocalService.getObjectRelationships(
						objectDefinition.getObjectDefinitionId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					objectRelationship -> _objectRelationshipDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							false, null, null, null,
							contextAcceptLanguage.getPreferredLocale(), null,
							null),
						objectRelationship),
					ObjectRelationship.class);
				objectViews = transformToArray(
					_objectViewLocalService.getObjectViews(
						objectDefinition.getObjectDefinitionId()),
					objectView -> _objectViewDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							false, null, null, null,
							contextAcceptLanguage.getPreferredLocale(), null,
							null),
						objectView),
					ObjectView.class);
				panelCategoryKey = objectDefinition.getPanelCategoryKey();
				parameterRequired = finalRESTContextPath.matches(
					".*/\\{\\w+}/.*");
				pluralLabel = LocalizedMapUtil.getLanguageIdMap(
					objectDefinition.getPluralLabelMap());
				portlet = objectDefinition.getPortlet();

				if (GetterUtil.getBoolean(
						PropsUtil.get("feature.flag.LPS-152650"))) {

					restContextPath = finalRESTContextPath;
				}

				scope = objectDefinition.getScope();
				status = new Status() {
					{
						code = objectDefinition.getStatus();
						label = WorkflowConstants.getStatusLabel(
							objectDefinition.getStatus());
						label_i18n = _language.get(
							LanguageResources.getResourceBundle(
								contextAcceptLanguage.getPreferredLocale()),
							WorkflowConstants.getStatusLabel(
								objectDefinition.getStatus()));
					}
				};

				if (GetterUtil.getBoolean(
						PropsUtil.get("feature.flag.LPS-135430"))) {

					storageType = objectDefinition.getStorageType();
				}

				system = objectDefinition.isSystem();

				setTitleObjectFieldName(
					() -> {
						com.liferay.object.model.ObjectField
							serviceBuilderObjectField =
								_objectFieldLocalService.fetchObjectField(
									objectDefinition.getTitleObjectFieldId());

						if (serviceBuilderObjectField == null) {
							return null;
						}

						return serviceBuilderObjectField.getName();
					});
			}
		};
	}

	private static final EntityModel _entityModel =
		new ObjectDefinitionEntityModel();

	@Reference
	private Language _language;

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ObjectActionLocalService _objectActionLocalService;

	@Reference
	private ObjectActionResource.Factory _objectActionResourceFactory;

	@Reference
	private ObjectActionService _objectActionService;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectFieldDTOConverter _objectFieldDTOConverter;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectFilterLocalService _objectFilterLocalService;

	@Reference
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Reference
	private ObjectLayoutResource.Factory _objectLayoutResourceFactory;

	@Reference
	private ObjectRelationshipDTOConverter _objectRelationshipDTOConverter;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectRelationshipResource.Factory
		_objectRelationshipResourceFactory;

	@Reference
	private ObjectViewDTOConverter _objectViewDTOConverter;

	@Reference
	private ObjectViewLocalService _objectViewLocalService;

	@Reference
	private ObjectViewResource.Factory _objectViewResourceFactory;

	@Reference
	private ObjectViewService _objectViewService;

	@Reference
	private SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

}