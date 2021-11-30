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

package com.liferay.object.web.internal.object.entries.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Arrays;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class ViewObjectEntriesDisplayContext {

	public ViewObjectEntriesDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectScopeProvider objectScopeProvider,
		PortletResourcePermission portletResourcePermission,
		String restContextPath) {

		_httpServletRequest = httpServletRequest;
		_objectScopeProvider = objectScopeProvider;
		_portletResourcePermission = portletResourcePermission;

		_apiURL = "/o" + restContextPath;
		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		try {
			long groupId = _objectScopeProvider.getGroupId(_httpServletRequest);

			if (!_objectScopeProvider.isGroupAware() ||
				!_objectScopeProvider.isValidGroupId(groupId)) {

				return _apiURL;
			}

			return StringBundler.concat(_apiURL, "/scopes/", groupId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return _apiURL;
		}
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (!_portletResourcePermission.contains(
				_objectRequestHelper.getPermissionChecker(),
				_objectScopeProvider.getGroupId(
					_objectRequestHelper.getRequest()),
				ObjectActionKeys.ADD_OBJECT_ENTRY)) {

			return creationMenu;
		}

		ObjectDefinition objectDefinition = getObjectDefinition();

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.create(
						getPortletURL()
					).setMVCRenderCommandName(
						"/object_entries/edit_object_entry"
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.format(
						_objectRequestHelper.getRequest(), "add-x",
						objectDefinition.getLabel(
							_objectRequestHelper.getLocale())));
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
					"/object_entries/edit_object_entry"
				).setParameter(
					"objectEntryId", "{id}"
				).buildString(),
				"view", "view",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "view"),
				"get", null, null),
			new FDSActionDropdownItem(
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					"are-you-sure-you-want-to-delete-this-entry"),
				_apiURL + "/{id}", "trash", "delete",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"),
			new FDSActionDropdownItem(
				_getPermissionsURL(), null, "permissions",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(), "permissions"),
				"get", "permissions", "modal-permissions"));
	}

	public String getFDSId() {
		return _objectRequestHelper.getPortletId();
	}

	public ObjectDefinition getObjectDefinition() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		return (ObjectDefinition)httpServletRequest.getAttribute(
			ObjectWebKeys.OBJECT_DEFINITION);
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_objectRequestHelper.getLiferayPortletRequest(),
				_objectRequestHelper.getLiferayPortletResponse()),
			_objectRequestHelper.getLiferayPortletResponse());
	}

	private String _getPermissionsURL() throws Exception {
		ObjectDefinition objectDefinition = getObjectDefinition();

		return PortletURLBuilder.create(
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
			"modelResource", objectDefinition.getClassName()
		).setParameter(
			"modelResourceDescription",
			objectDefinition.getLabel(_objectRequestHelper.getLocale())
		).setParameter(
			"resourcePrimKey", "{id}"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewObjectEntriesDisplayContext.class);

	private final String _apiURL;
	private final HttpServletRequest _httpServletRequest;
	private final ObjectRequestHelper _objectRequestHelper;
	private final ObjectScopeProvider _objectScopeProvider;
	private final PortletResourcePermission _portletResourcePermission;

}