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

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.depot.web.internal.display.context.DepotAdminViewDepotDashboardDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.util.GroupURLProvider;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
		"mvc.command.name=/depot/view_depot_dashboard"
	},
	service = MVCRenderCommand.class
)
public class ViewDepotDashboardMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				DepotAdminViewDepotDashboardDisplayContext.class.getName(),
				new DepotAdminViewDepotDashboardDisplayContext(
					_getGroup(renderRequest),
					_portal.getHttpServletRequest(renderRequest),
					_panelAppRegistry, _panelCategoryRegistry,
					_getPermissionChecker(renderRequest), _portal));

			return "/view_depot_dashboard.jsp";
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
	}

	private Group _getGroup(PortletRequest portletRequest)
		throws PortalException {

		DepotEntry depotEntry = _depotEntryService.getDepotEntry(
			ParamUtil.getLong(portletRequest, "depotEntryId"));

		return depotEntry.getGroup();
	}

	private PermissionChecker _getPermissionChecker(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPermissionChecker();
	}

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private GroupURLProvider _groupURLProvider;

	@Reference
	private Language _language;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	@Reference
	private Portal _portal;

}