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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTPermission;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author David Truong
 */
public class ViewCTRemotesDisplayContext {

	public ViewCTRemotesDisplayContext(
		HttpServletRequest httpServletRequest, Language language, Portal portal,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_portal = portal;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		return "/o/change-tracking-rest/v1.0/ct-remotes";
	}

	public CreationMenu getCreationMenu() {
		if (!CTPermission.contains(
				_themeDisplay.getPermissionChecker(),
				CTActionKeys.ADD_REMOTE)) {

			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_tracking/edit_ct_remote", "redirect",
					_themeDisplay.getURLCurrent());
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "add-remote-server"));
			}
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.createRenderURL(
					_renderResponse
				).setMVCRenderCommandName(
					"/change_tracking/edit_ct_remote"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"ctRemoteId", "{id}"
				).buildString(),
				"pencil", "edit", _language.get(_httpServletRequest, "edit"),
				"get", "update", "link"),
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						_httpServletRequest,
						"com_liferay_portlet_configuration_web_portlet_" +
							"PortletConfigurationPortlet",
						ActionRequest.RENDER_PHASE)
				).setMVCPath(
					"/edit_permissions.jsp"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"modelResource", CTRemote.class.getName()
				).setParameter(
					"modelResourceDescription", "{name}"
				).setParameter(
					"resourcePrimKey", "{id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"password-policies", "permissions",
				_language.get(_httpServletRequest, "permissions"), "get",
				"permissions", "modal-permissions"),
			new FDSActionDropdownItem(
				_language.get(
					_httpServletRequest,
					"are-you-sure-you-want-to-delete-this-remote-server"),
				null, "times-circle", "delete",
				_language.get(_httpServletRequest, "delete"), "post", "delete",
				"headless"));
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}