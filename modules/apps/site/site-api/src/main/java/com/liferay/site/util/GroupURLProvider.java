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
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.constants.SiteWebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Julio Camarero
 */
@Component(service = GroupURLProvider.class)
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

		if (group.isDepot()) {
			String depotDashboardGroupURL = _getDepotDashboardGroupURL(
				group, portletRequest);

			if (depotDashboardGroupURL != null) {
				return depotDashboardGroupURL;
			}
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String groupDisplayURL = group.getDisplayURL(
			themeDisplay, false,
			GetterUtil.getBoolean(
				portletRequest.getAttribute(
					SiteWebKeys.GROUP_URL_PROVIDER_CONTROL_PANEL)));

		if (Validator.isNotNull(groupDisplayURL)) {
			return HttpComponentsUtil.removeParameter(
				groupDisplayURL, "p_p_id");
		}

		groupDisplayURL = group.getDisplayURL(themeDisplay, true);

		if (Validator.isNotNull(groupDisplayURL)) {
			return HttpComponentsUtil.removeParameter(
				groupDisplayURL, "p_p_id");
		}

		if (includeStagingGroup && group.hasStagingGroup()) {
			try {
				if (_groupPermission.contains(
						themeDisplay.getPermissionChecker(), group,
						ActionKeys.VIEW_STAGING)) {

					return getGroupURL(group.getStagingGroup(), portletRequest);
				}
			}
			catch (PortalException portalException) {
				_log.error(
					"Unable to check permission on group " + group.getGroupId(),
					portalException);
			}
		}

		return getGroupAdministrationURL(group, portletRequest);
	}

	private String _getDepotDashboardGroupURL(
		Group group, PortletRequest portletRequest) {

		try {
			DepotEntryLocalService depotEntryLocalService =
				_depotEntryLocalService;

			if (depotEntryLocalService == null) {
				return null;
			}

			DepotEntry depotEntry = depotEntryLocalService.getGroupDepotEntry(
				group.getGroupId());

			return PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					portletRequest, group, _DEPOT_ADMIN_PORTLET_ID, 0, 0,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/depot/view_depot_dashboard"
			).setParameter(
				"depotEntryId", depotEntry.getDepotEntryId()
			).buildString();
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return null;
		}
	}

	private static final String _DEPOT_ADMIN_PORTLET_ID =
		"com_liferay_depot_web_portlet_DepotAdminPortlet";

	private static final Log _log = LogFactoryUtil.getLog(
		GroupURLProvider.class);

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	@Reference
	private Portal _portal;

}