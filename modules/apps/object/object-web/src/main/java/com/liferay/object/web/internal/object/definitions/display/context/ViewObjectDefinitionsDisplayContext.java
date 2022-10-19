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
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerTracker;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
public class ViewObjectDefinitionsDisplayContext {

	public ViewObjectDefinitionsDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission,
		ObjectEntryManagerTracker objectEntryManagerTracker) {

		_objectDefinitionModelResourcePermission =
			objectDefinitionModelResourcePermission;
		_objectEntryManagerTracker = objectEntryManagerTracker;

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

		return Arrays.asList(
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
				"get", null, null),
			new FDSActionDropdownItem(
				ResourceURLBuilder.createResourceURL(
					_objectRequestHelper.getLiferayPortletResponse()
				).setParameter(
					"objectDefinitionId", "{id}"
				).setResourceID(
					"/object_definitions/export_object_definition"
				).buildString(),
				"export", "export",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(), "export-as-json"),
				"get", null, null),
			new FDSActionDropdownItem(
				null, "trash", "deleteObjectDefinition",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", null),
			new FDSActionDropdownItem(
				_getPermissionsURL(), null, "permissions",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(), "permissions"),
				"get", "permissions", "modal-permissions"));
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_objectRequestHelper.getLiferayPortletRequest(),
				_objectRequestHelper.getLiferayPortletResponse()),
			_objectRequestHelper.getLiferayPortletResponse());
	}

	public List<String> getStorageTypes() {
		List<String> storageTypes = TransformUtil.transform(
			_objectEntryManagerTracker.getStorageTypes(),
			objectEntryManagerStorageType -> LanguageUtil.get(
				_objectRequestHelper.getLocale(),
				objectEntryManagerStorageType));

		Collections.sort(storageTypes);

		return storageTypes;
	}

	private String _getPermissionsURL() throws Exception {
		PortletURL portletURL = PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_objectRequestHelper.getRequest(),
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_permissions.jsp"
		).setRedirect(
			_objectRequestHelper.getCurrentURL()
		).setParameter(
			"modelResource", ObjectDefinition.class.getName()
		).setParameter(
			"modelResourceDescription", "{name}"
		).setParameter(
			"resourcePrimKey", "{id}"
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			throw new PortalException(windowStateException);
		}

		return portletURL.toString();
	}

	private boolean _hasAddObjectDefinitionPermission() {
		PortletResourcePermission portletResourcePermission =
			_objectDefinitionModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			_objectRequestHelper.getPermissionChecker(), null,
			ObjectActionKeys.ADD_OBJECT_DEFINITION);
	}

	private final ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;
	private final ObjectEntryManagerTracker _objectEntryManagerTracker;
	private final ObjectRequestHelper _objectRequestHelper;

}