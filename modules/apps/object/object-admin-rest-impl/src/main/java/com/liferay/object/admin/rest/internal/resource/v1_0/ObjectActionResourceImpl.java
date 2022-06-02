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
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectActionUtil;
import com.liferay.object.admin.rest.resource.v1_0.ObjectActionResource;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.service.ObjectActionService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Marco Leo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-action.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, ObjectActionResource.class}
)
public class ObjectActionResourceImpl
	extends BaseObjectActionResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteObjectAction(Long objectActionId) throws Exception {
		_objectActionService.deleteObjectAction(objectActionId);
	}

	@Override
	public ObjectAction getObjectAction(Long objectActionId) throws Exception {
		return _toObjectAction(
			_objectActionService.getObjectAction(objectActionId));
	}

	@NestedField(parentClass = ObjectDefinition.class, value = "objectActions")
	@Override
	public Page<ObjectAction> getObjectDefinitionObjectActionsPage(
			Long objectDefinitionId, String search, Pagination pagination)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.UPDATE, "postObjectDefinitionObjectAction",
					com.liferay.object.model.ObjectDefinition.class.getName(),
					objectDefinitionId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectDefinitionObjectActionsPage",
					com.liferay.object.model.ObjectDefinition.class.getName(),
					objectDefinitionId)
			).build(),
			booleanQuery -> {
			},
			null, com.liferay.object.model.ObjectAction.class.getName(), search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setAttribute(
					"objectDefinitionId", objectDefinitionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> _toObjectAction(
				_objectActionService.getObjectAction(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectAction postObjectDefinitionObjectAction(
			Long objectDefinitionId, ObjectAction objectAction)
		throws Exception {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-152180")) &&
			StringUtil.equals(
				objectAction.getObjectActionExecutorKey(),
				ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY)) {

			throw new UnsupportedOperationException();
		}

		return _toObjectAction(
			_objectActionService.addObjectAction(
				objectDefinitionId, objectAction.getActive(),
				objectAction.getConditionExpression(),
				objectAction.getDescription(), objectAction.getName(),
				objectAction.getObjectActionExecutorKey(),
				objectAction.getObjectActionTriggerKey(),
				_toParametersUnicodeProperties(objectAction.getParameters())));
	}

	@Override
	public ObjectAction putObjectAction(
			Long objectActionId, ObjectAction objectAction)
		throws Exception {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-152180")) &&
			StringUtil.equals(
				objectAction.getObjectActionExecutorKey(),
				ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY)) {

			throw new UnsupportedOperationException();
		}

		return _toObjectAction(
			_objectActionService.updateObjectAction(
				objectActionId, objectAction.getActive(),
				objectAction.getConditionExpression(),
				objectAction.getDescription(), objectAction.getName(),
				objectAction.getObjectActionExecutorKey(),
				objectAction.getObjectActionTriggerKey(),
				_toParametersUnicodeProperties(objectAction.getParameters())));
	}

	private ObjectAction _toObjectAction(
		com.liferay.object.model.ObjectAction objectAction) {

		String permissionName =
			com.liferay.object.model.ObjectDefinition.class.getName();

		return ObjectActionUtil.toObjectAction(
			HashMapBuilder.put(
				"delete",
				addAction(
					ActionKeys.DELETE, "deleteObjectAction", permissionName,
					objectAction.getObjectDefinitionId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectAction", permissionName,
					objectAction.getObjectDefinitionId())
			).put(
				"update",
				addAction(
					ActionKeys.UPDATE, "putObjectAction", permissionName,
					objectAction.getObjectDefinitionId())
			).build(),
			contextAcceptLanguage.getPreferredLocale(), objectAction);
	}

	private UnicodeProperties _toParametersUnicodeProperties(
		Map<String, ?> parameters) {

		Map<String, String> map = new HashMap<>();

		for (Map.Entry<String, ?> entry : parameters.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof ArrayList) {
				value = JSONFactoryUtil.looseSerialize(value);
			}

			map.put(entry.getKey(), value.toString());
		}

		return UnicodePropertiesBuilder.create(
			map, true
		).build();
	}

	@Reference
	private ObjectActionService _objectActionService;

}