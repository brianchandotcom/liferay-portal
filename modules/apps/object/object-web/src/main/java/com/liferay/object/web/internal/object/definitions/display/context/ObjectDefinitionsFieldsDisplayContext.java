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

package com.liferay.object.web.internal.object.definitions.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeServicesTracker;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.web.internal.configuration.FFBusinessTypeAttachmentConfiguration;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.object.web.internal.util.ObjectFieldBusinessTypeUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
public class ObjectDefinitionsFieldsDisplayContext {

	public ObjectDefinitionsFieldsDisplayContext(
		FFBusinessTypeAttachmentConfiguration
			ffBusinessTypeAttachmentConfiguration,
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission,
		ObjectFieldBusinessTypeServicesTracker
			objectFieldBusinessTypeServicesTracker) {

		_ffBusinessTypeAttachmentConfiguration =
			ffBusinessTypeAttachmentConfiguration;
		_objectDefinitionModelResourcePermission =
			objectDefinitionModelResourcePermission;
		_objectFieldBusinessTypeServicesTracker =
			objectFieldBusinessTypeServicesTracker;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/object-admin/v1.0/object-definitions/" +
			getObjectDefinitionId() + "/object-fields";
	}

	public CreationMenu getCreationMenu(ObjectDefinition objectDefinition)
		throws PortalException {

		CreationMenu creationMenu = new CreationMenu();

		if (objectDefinition.isSystem() ||
			!hasUpdateObjectDefinitionPermission()) {

			return creationMenu;
		}

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("addObjectField");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_objectRequestHelper.getRequest(), "add-object-field"));
				dropdownItem.setTarget("event");
			});

		return creationMenu;
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					getPortletURL()
				).setMVCRenderCommandName(
					"/object_definitions/edit_object_field"
				).setParameter(
					"objectFieldId", "{id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"view", "view",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "view"),
				"get", null, "sidePanel"),
			new FDSActionDropdownItem(
				"/o/object-admin/v1.0/object-fields/{id}", "trash", "delete",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	public long getObjectDefinitionId() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		ObjectDefinition objectDefinition =
			(ObjectDefinition)httpServletRequest.getAttribute(
				ObjectWebKeys.OBJECT_DEFINITION);

		return objectDefinition.getObjectDefinitionId();
	}

	public List<Map<String, String>> getObjectFieldBusinessTypeMaps(
		boolean includeRelationshipObjectFieldBusinessType, Locale locale) {

		List<ObjectFieldBusinessType> objectFieldBusinessTypes =
			_objectFieldBusinessTypeServicesTracker.
				getObjectFieldBusinessTypes();

		Stream<ObjectFieldBusinessType> stream =
			objectFieldBusinessTypes.stream();

		return ObjectFieldBusinessTypeUtil.getObjectFieldBusinessTypeMaps(
			locale,
			stream.filter(
				objectFieldBusinessType ->
					objectFieldBusinessType.isVisible() &&
					(!StringUtil.equals(
						objectFieldBusinessType.getName(),
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
					 _ffBusinessTypeAttachmentConfiguration.enabled()) &&
					(!StringUtil.equals(
						objectFieldBusinessType.getName(),
						ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP) ||
					 includeRelationshipObjectFieldBusinessType)
			).collect(
				Collectors.toList()
			));
	}

	public JSONObject getObjectFieldJSONObject(ObjectField objectField) {
		return JSONUtil.put(
			"businessType", objectField.getBusinessType()
		).put(
			"DBType", objectField.getDBType()
		).put(
			"id", objectField.getObjectFieldId()
		).put(
			"indexed", objectField.isIndexed()
		).put(
			"label", objectField.getLabelMap()
		).put(
			"name", objectField.getName()
		).put(
			"objectFieldSettings", _getObjectFieldSettingsJSONArray(objectField)
		).put(
			"relationshipType", objectField.getRelationshipType()
		).put(
			"required", objectField.isRequired()
		);
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_objectRequestHelper.getLiferayPortletRequest(),
				_objectRequestHelper.getLiferayPortletResponse()),
			_objectRequestHelper.getLiferayPortletResponse());
	}

	public boolean hasUpdateObjectDefinitionPermission()
		throws PortalException {

		return _objectDefinitionModelResourcePermission.contains(
			_objectRequestHelper.getPermissionChecker(),
			getObjectDefinitionId(), ActionKeys.UPDATE);
	}

	private JSONArray _getObjectFieldSettingsJSONArray(
		ObjectField objectField) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ListUtil.isNotEmptyForEach(
			objectField.getObjectFieldSettings(),
			objectFieldSetting -> jsonArray.put(
				JSONUtil.put(
					"name", objectFieldSetting.getName()
				).put(
					"required", objectFieldSetting.isRequired()
				).put(
					"value", objectFieldSetting.getValue()
				)));

		return jsonArray;
	}

	private final FFBusinessTypeAttachmentConfiguration
		_ffBusinessTypeAttachmentConfiguration;
	private final ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;
	private final ObjectFieldBusinessTypeServicesTracker
		_objectFieldBusinessTypeServicesTracker;
	private final ObjectRequestHelper _objectRequestHelper;

}