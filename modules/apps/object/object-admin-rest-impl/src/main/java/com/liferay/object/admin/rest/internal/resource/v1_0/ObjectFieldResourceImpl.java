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

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.internal.dto.v1_0.converter.ObjectFieldDTOConverter;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldSettingUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectStateFlowUtil;
import com.liferay.object.admin.rest.internal.odata.entity.v1_0.ObjectFieldEntityModel;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-field.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, ObjectFieldResource.class}
)
public class ObjectFieldResourceImpl
	extends BaseObjectFieldResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteObjectField(Long objectFieldId) throws Exception {
		_objectFieldService.deleteObjectField(objectFieldId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@NestedField(parentClass = ObjectDefinition.class, value = "objectFields")
	@Override
	public Page<ObjectField> getObjectDefinitionObjectFieldsPage(
			Long objectDefinitionId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		com.liferay.object.model.ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.UPDATE, "postObjectDefinitionObjectField",
					com.liferay.object.model.ObjectDefinition.class.getName(),
					objectDefinitionId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectDefinitionObjectFieldsPage",
					com.liferay.object.model.ObjectDefinition.class.getName(),
					objectDefinitionId)
			).build(),
			booleanQuery -> {
			},
			filter, com.liferay.object.model.ObjectField.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setAttribute("label", search);
				searchContext.setAttribute(
					"objectDefinitionId", objectDefinitionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			sorts,
			document -> _toObjectField(
				objectDefinition,
				_objectFieldService.getObjectField(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectField getObjectField(Long objectFieldId) throws Exception {
		return _toObjectField(
			_objectFieldService.getObjectField(objectFieldId));
	}

	@Override
	public ObjectField postObjectDefinitionObjectField(
			Long objectDefinitionId, ObjectField objectField)
		throws Exception {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-152677"))) {
			return _toObjectField(
				_objectFieldService.addCustomObjectField(
					objectField.getListTypeDefinitionId(), objectDefinitionId,
					objectField.getBusinessTypeAsString(),
					ObjectFieldUtil.getDBType(
						objectField.getDBTypeAsString(),
						objectField.getTypeAsString()),
					null, objectField.getIndexed(),
					objectField.getIndexedAsKeyword(),
					objectField.getIndexedLanguageId(),
					LocalizedMapUtil.getLocalizedMap(objectField.getLabel()),
					objectField.getName(), objectField.getRequired(), false,
					transformToList(
						objectField.getObjectFieldSettings(),
						objectFieldSetting ->
							ObjectFieldSettingUtil.toObjectFieldSetting(
								objectFieldSetting,
								_objectFieldSettingLocalService))));
		}

		return _toObjectField(
			_objectFieldService.addCustomObjectField(
				objectField.getListTypeDefinitionId(), objectDefinitionId,
				objectField.getBusinessTypeAsString(),
				ObjectFieldUtil.getDBType(
					objectField.getDBTypeAsString(),
					objectField.getTypeAsString()),
				objectField.getDefaultValue(), objectField.getIndexed(),
				objectField.getIndexedAsKeyword(),
				objectField.getIndexedLanguageId(),
				LocalizedMapUtil.getLocalizedMap(objectField.getLabel()),
				objectField.getName(), objectField.getRequired(),
				objectField.getState(),
				transformToList(
					objectField.getObjectFieldSettings(),
					objectFieldSetting ->
						ObjectFieldSettingUtil.toObjectFieldSetting(
							objectFieldSetting,
							_objectFieldSettingLocalService))));
	}

	@Override
	public ObjectField putObjectField(
			Long objectFieldId, ObjectField objectField)
		throws Exception {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-152677"))) {
			return _toObjectField(
				_objectFieldService.updateObjectField(
					objectFieldId, objectField.getExternalReferenceCode(),
					objectField.getListTypeDefinitionId(),
					objectField.getBusinessTypeAsString(),
					ObjectFieldUtil.getDBType(
						objectField.getDBTypeAsString(),
						objectField.getTypeAsString()),
					null, objectField.getIndexed(),
					objectField.getIndexedAsKeyword(),
					objectField.getIndexedLanguageId(),
					LocalizedMapUtil.getLocalizedMap(objectField.getLabel()),
					objectField.getName(), objectField.getRequired(), false,
					transformToList(
						objectField.getObjectFieldSettings(),
						objectFieldSetting ->
							ObjectFieldSettingUtil.toObjectFieldSetting(
								objectFieldSetting,
								_objectFieldSettingLocalService)),
					null));
		}

		return _toObjectField(
			_objectFieldService.updateObjectField(
				objectFieldId, objectField.getExternalReferenceCode(),
				objectField.getListTypeDefinitionId(),
				objectField.getBusinessTypeAsString(),
				ObjectFieldUtil.getDBType(
					objectField.getDBTypeAsString(),
					objectField.getTypeAsString()),
				objectField.getDefaultValue(), objectField.getIndexed(),
				objectField.getIndexedAsKeyword(),
				objectField.getIndexedLanguageId(),
				LocalizedMapUtil.getLocalizedMap(objectField.getLabel()),
				objectField.getName(), objectField.getRequired(),
				objectField.getState(),
				transformToList(
					objectField.getObjectFieldSettings(),
					objectFieldSetting ->
						ObjectFieldSettingUtil.toObjectFieldSetting(
							objectFieldSetting,
							_objectFieldSettingLocalService)),
				ObjectStateFlowUtil.toObjectStateFlow(
					objectFieldId, objectField.getObjectStateFlow())));
	}

	private ObjectField _toObjectField(
			com.liferay.object.model.ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectField objectField)
		throws Exception {

		boolean updateable =
			(!objectDefinition.isApproved() && !objectDefinition.isSystem()) ||
			Objects.equals(
				objectDefinition.getExtensionDBTableName(),
				objectField.getDBTableName());

		return _objectFieldDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				false,
				HashMapBuilder.put(
					"delete",
					() -> {
						if (!updateable ||
							Validator.isNotNull(
								objectField.getRelationshipType()) ||
							objectField.isSystem()) {

							return null;
						}

						return addAction(
							ActionKeys.UPDATE, "deleteObjectField",
							com.liferay.object.model.ObjectDefinition.class.
								getName(),
							objectField.getObjectDefinitionId());
					}
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getObjectField",
						com.liferay.object.model.ObjectDefinition.class.
							getName(),
						objectField.getObjectDefinitionId())
				).put(
					"update",
					() -> {
						if (!updateable) {
							return null;
						}

						return addAction(
							ActionKeys.UPDATE, "putObjectField",
							com.liferay.object.model.ObjectDefinition.class.
								getName(),
							objectField.getObjectDefinitionId());
					}
				).build(),
				null, null, contextAcceptLanguage.getPreferredLocale(), null,
				null),
			objectField);
	}

	private ObjectField _toObjectField(
			com.liferay.object.model.ObjectField objectField)
		throws Exception {

		return _toObjectField(
			_objectDefinitionLocalService.getObjectDefinition(
				objectField.getObjectDefinitionId()),
			objectField);
	}

	private static final EntityModel _entityModel =
		new ObjectFieldEntityModel();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldDTOConverter _objectFieldDTOConverter;

	@Reference
	private ObjectFieldService _objectFieldService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}