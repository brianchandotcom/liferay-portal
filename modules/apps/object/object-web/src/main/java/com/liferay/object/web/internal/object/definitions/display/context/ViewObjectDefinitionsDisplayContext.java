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
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.web.internal.configuration.activator.FFExportImportObjectDefinitionTypeConfigurationActivator;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
public class ViewObjectDefinitionsDisplayContext {

	public ViewObjectDefinitionsDisplayContext(
		FFExportImportObjectDefinitionTypeConfigurationActivator
			ffExportImportObjectDefinitionTypeConfigurationActivator,
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission) {

		_ffExportImportObjectDefinitionTypeConfigurationActivator =
			ffExportImportObjectDefinitionTypeConfigurationActivator;
		_objectDefinitionModelResourcePermission =
			objectDefinitionModelResourcePermission;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/object-admin/v1.0/object-definitions";
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (!_hasAddObjectDefinitionPermission()) {
			return creationMenu;
		}

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("addObjectDefinition");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_objectRequestHelper.getRequest(), "add-object"));
				dropdownItem.setTarget("event");
			});

		return creationMenu;
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		LiferayPortletResponse liferayPortletResponse =
			_objectRequestHelper.getLiferayPortletResponse();

		ResourceURL resourceURL = liferayPortletResponse.createResourceURL();

		resourceURL.setParameter("objectDefinitionId", "{id}");
		resourceURL.setResourceID(
			"/object_definitions/export_object_definition");

		List<FDSActionDropdownItem> fdsActionDropdownItems = new ArrayList<>();

		fdsActionDropdownItems.add(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					getPortletURL()
				).setMVCRenderCommandName(
					"/object_definitions/edit_object_definition"
				).setParameter(
					"objectDefinitionId", "{id}"
				).buildString(),
				"view", "view",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "view"),
				"get", null, null));

		if (_ffExportImportObjectDefinitionTypeConfigurationActivator.
				enabled()) {

			fdsActionDropdownItems.add(
				new FDSActionDropdownItem(
					resourceURL.toString(), "export", "export",
					LanguageUtil.get(
						_objectRequestHelper.getRequest(), "export-as-json"),
					"get", null, null));
		}

		fdsActionDropdownItems.add(
			new FDSActionDropdownItem(
				getAPIURL() + "/{id}", "trash", "delete",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));

		return fdsActionDropdownItems;
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_objectRequestHelper.getLiferayPortletRequest(),
				_objectRequestHelper.getLiferayPortletResponse()),
			_objectRequestHelper.getLiferayPortletResponse());
	}

	private boolean _hasAddObjectDefinitionPermission() {
		PortletResourcePermission portletResourcePermission =
			_objectDefinitionModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			_objectRequestHelper.getPermissionChecker(), null,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);
	}

	private final FFExportImportObjectDefinitionTypeConfigurationActivator
		_ffExportImportObjectDefinitionTypeConfigurationActivator;
	private final ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;
	private final ObjectRequestHelper _objectRequestHelper;

}