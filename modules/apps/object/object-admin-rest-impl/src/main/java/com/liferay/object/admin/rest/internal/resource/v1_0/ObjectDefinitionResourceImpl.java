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

import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.dto.v1_0.Status;
import com.liferay.object.admin.rest.internal.configuration.activator.FFObjectDefinitionPermissionsActionConfigurationActivator;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectActionUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectLayoutUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectViewUtil;
import com.liferay.object.admin.rest.internal.odata.entity.v1_0.ObjectDefinitionEntityModel;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

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
	extends BaseObjectDefinitionResourceImpl implements EntityModelResource {

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
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectDefinitionsPage",
					ObjectConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
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

		return _toObjectDefinition(
			_objectDefinitionService.addCustomObjectDefinition(
				LocalizedMapUtil.getLocalizedMap(objectDefinition.getLabel()),
				objectDefinition.getName(), objectDefinition.getPanelAppOrder(),
				objectDefinition.getPanelCategoryKey(),
				LocalizedMapUtil.getLocalizedMap(
					objectDefinition.getPluralLabel()),
				objectDefinition.getScope(),
				transformToList(
					objectDefinition.getObjectFields(),
					objectField -> ObjectFieldUtil.toObjectField(
						objectField, _objectFieldLocalService))));
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

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionService.getObjectDefinition(
					objectDefinitionId);

		if (serviceBuilderObjectDefinition.isSystem()) {
			return _toObjectDefinition(
				_objectDefinitionService.updateTitleObjectFieldId(
					objectDefinitionId,
					objectDefinition.getTitleObjectFieldId()));
		}

		return _toObjectDefinition(
			_objectDefinitionService.updateCustomObjectDefinition(
				objectDefinitionId, 0,
				GetterUtil.get(objectDefinition.getTitleObjectFieldId(), 0),
				GetterUtil.getBoolean(objectDefinition.getActive(), true),
				LocalizedMapUtil.getLocalizedMap(objectDefinition.getLabel()),
				objectDefinition.getName(), objectDefinition.getPanelAppOrder(),
				objectDefinition.getPanelCategoryKey(),
				objectDefinition.getPortlet(),
				LocalizedMapUtil.getLocalizedMap(
					objectDefinition.getPluralLabel()),
				objectDefinition.getScope()));
	}

	private ObjectDefinition _toObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		String permissionName =
			com.liferay.object.model.ObjectDefinition.class.getName();

		return new ObjectDefinition() {
			{
				actions = HashMapBuilder.put(
					"delete",
					() -> {
						if (objectDefinition.isApproved() ||
							objectDefinition.isSystem()) {

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
					() -> {
						if (!_ffObjectDefinitionPermissionsActionConfigurationActivator.
								enabled()) {

							return null;
						}

						return addAction(
							ActionKeys.PERMISSIONS, "patchObjectDefinition",
							permissionName,
							objectDefinition.getObjectDefinitionId());
					}
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
				id = objectDefinition.getObjectDefinitionId();
				label = LocalizedMapUtil.getLanguageIdMap(
					objectDefinition.getLabelMap());
				name = objectDefinition.getShortName();
				objectActions = transformToArray(
					_objectActionLocalService.getObjectActions(
						objectDefinition.getObjectDefinitionId()),
					objectAction -> ObjectActionUtil.toObjectAction(
						null, objectAction),
					ObjectAction.class);
				objectFields = transformToArray(
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId()),
					objectField -> ObjectFieldUtil.toObjectField(
						null, objectField),
					ObjectField.class);
				objectLayouts = transformToArray(
					_objectLayoutLocalService.getObjectLayouts(
						objectDefinition.getObjectDefinitionId()),
					objectLayout -> ObjectLayoutUtil.toObjectLayout(
						null, objectLayout),
					ObjectLayout.class);
				objectViews = transformToArray(
					_objectViewLocalService.getObjectViews(
						objectDefinition.getObjectDefinitionId()),
					objectView -> ObjectViewUtil.toObjectView(null, objectView),
					ObjectView.class);
				panelCategoryKey = objectDefinition.getPanelCategoryKey();
				pluralLabel = LocalizedMapUtil.getLanguageIdMap(
					objectDefinition.getPluralLabelMap());
				portlet = objectDefinition.getPortlet();
				scope = objectDefinition.getScope();
				status = new Status() {
					{
						code = objectDefinition.getStatus();
						label = WorkflowConstants.getStatusLabel(
							objectDefinition.getStatus());
						label_i18n = LanguageUtil.get(
							LanguageResources.getResourceBundle(
								contextAcceptLanguage.getPreferredLocale()),
							WorkflowConstants.getStatusLabel(
								objectDefinition.getStatus()));
					}
				};
				system = objectDefinition.isSystem();
				titleObjectFieldId = objectDefinition.getTitleObjectFieldId();
			}
		};
	}

	private static final EntityModel _entityModel =
		new ObjectDefinitionEntityModel();

	@Reference
	private FFObjectDefinitionPermissionsActionConfigurationActivator
		_ffObjectDefinitionPermissionsActionConfigurationActivator;

	@Reference
	private ObjectActionLocalService _objectActionLocalService;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Reference
	private ObjectViewLocalService _objectViewLocalService;

}