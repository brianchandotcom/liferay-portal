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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.web.internal.util.ObjectFieldBusinessTypeUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectDefinitionsLayoutsDisplayContext
	extends BaseObjectDefinitionsDisplayContext {

	public ObjectDefinitionsLayoutsDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission,
		ObjectFieldBusinessTypeRegistry objectFieldBusinessTypeRegistry) {

		super(httpServletRequest, objectDefinitionModelResourcePermission);

		_objectFieldBusinessTypeRegistry = objectFieldBusinessTypeRegistry;
	}

	public String getEditObjectLayoutsURL() throws Exception {
		return PortletURLBuilder.create(
			getPortletURL()
		).setMVCRenderCommandName(
			"/object_definitions/edit_object_layout"
		).setParameter(
			"objectLayoutId", "{id}"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				getEditObjectLayoutsURL(),
				hasUpdateObjectDefinitionPermission() ? "pencil" : "view",
				hasUpdateObjectDefinitionPermission() ? "edit" : "view",
				LanguageUtil.get(
					objectRequestHelper.getRequest(),
					hasUpdateObjectDefinitionPermission() ? "edit" : "view"),
				"get", null, "sidePanel"),
			new FDSActionDropdownItem(
				"/o/object-admin/v1.0/object-layouts/{id}", "trash", "delete",
				LanguageUtil.get(objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	public List<Map<String, String>> getObjectFieldBusinessTypeMaps(
		Locale locale) {

		return ObjectFieldBusinessTypeUtil.getObjectFieldBusinessTypeMaps(
			locale,
			_objectFieldBusinessTypeRegistry.getObjectFieldBusinessTypes());
	}

	@Override
	protected String getAPIURI() {
		return "/object-layouts";
	}

	@Override
	protected UnsafeConsumer<DropdownItem, Exception>
		getCreationMenuDropdownItemUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref("addObjectLayout");
			dropdownItem.setLabel(
				LanguageUtil.get(
					objectRequestHelper.getRequest(), "add-object-layout"));
			dropdownItem.setTarget("event");
		};
	}

	private final ObjectFieldBusinessTypeRegistry
		_objectFieldBusinessTypeRegistry;

}