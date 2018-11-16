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

package com.liferay.site.util;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(immediate = true, service = GroupURLProvider.class)
public class GroupURLProvider {

	public String getGroupAdministrationURL(
		Group group, PortletRequest portletRequest) {

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletId = panelCategoryHelper.getFirstPortletId(
			PanelCategoryKeys.SITE_ADMINISTRATION,
			themeDisplay.getPermissionChecker(), group);

		if (Validator.isNotNull(portletId)) {
			PortletURL groupAdministrationURL =
				_portal.getControlPanelPortletURL(
					portletRequest, group, portletId, 0, 0,
					PortletRequest.RENDER_PHASE);

			if (groupAdministrationURL != null) {
				return groupAdministrationURL.toString();
			}
		}

		return null;
	}

	public String getGroupLayoutsURL(
		Group group, boolean privateLayout, PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String groupDisplayURL = group.getDisplayURL(
			themeDisplay, privateLayout);

		if (Validator.isNotNull(groupDisplayURL)) {
			return groupDisplayURL;
		}

		return null;
	}

	public String getGroupURL(Group group, PortletRequest portletRequest) {
		return getGroupURL(group, portletRequest, true);
	}

	public String getLiveGroupURL(Group group, PortletRequest portletRequest) {
		return getGroupURL(group, portletRequest, false);
	}

	protected String getGroupURL(
		Group group, PortletRequest portletRequest,
		boolean includeStagingGroup) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String groupDisplayURL = group.getDisplayURL(themeDisplay, false);

		if (Validator.isNotNull(groupDisplayURL)) {
			return _http.removeParameter(groupDisplayURL, "p_p_id");
		}

		groupDisplayURL = group.getDisplayURL(themeDisplay, true);

		if (Validator.isNotNull(groupDisplayURL)) {
			return _http.removeParameter(groupDisplayURL, "p_p_id");
		}

		if (includeStagingGroup && group.hasStagingGroup()) {
			try {
				if (GroupPermissionUtil.contains(
						themeDisplay.getPermissionChecker(), group,
						ActionKeys.VIEW_STAGING)) {

					return getGroupURL(group.getStagingGroup(), portletRequest);
				}
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to check permission on group " + group.getGroupId(),
					pe);
			}
		}

		return getGroupAdministrationURL(group, portletRequest);
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	@Reference(unbind = "-")
	protected void setPanelCategoryRegistry(
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelCategoryRegistry = panelCategoryRegistry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupURLProvider.class);

	@Reference
	private Http _http;

	private PanelAppRegistry _panelAppRegistry;
	private PanelCategoryRegistry _panelCategoryRegistry;

	@Reference
	private Portal _portal;

}