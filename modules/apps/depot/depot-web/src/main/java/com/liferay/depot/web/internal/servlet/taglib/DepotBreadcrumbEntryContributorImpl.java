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

package com.liferay.depot.web.internal.servlet.taglib;

import com.liferay.depot.configuration.DepotConfiguration;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntryContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(service = BreadcrumbEntryContributor.class)
public class DepotBreadcrumbEntryContributorImpl
	implements BreadcrumbEntryContributor {

	@Override
	public List<BreadcrumbEntry> getBreadcrumbEntries(
		List<BreadcrumbEntry> originalBreadcrumbEntries,
		HttpServletRequest httpServletRequest) {

		if (!_depotConfiguration.isEnabled()) {
			return originalBreadcrumbEntries;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.getType() != GroupConstants.TYPE_DEPOT) {
			return originalBreadcrumbEntries;
		}

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		try {
			PortletURL portletURL = _portal.getControlPanelPortletURL(
				httpServletRequest, themeDisplay.getScopeGroup(),
				DepotPortletKeys.DEPOT_ADMIN, 0, 0,
				PortletRequest.RENDER_PHASE);

			BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

			breadcrumbEntry.setTitle(
				_language.get(httpServletRequest, "category.asset-libraries"));
			breadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(breadcrumbEntry);

			portletURL.setParameter(
				"mvcRenderCommandName", "/depot/view_depot_dashboard");

			DepotEntry depotEntry = _depotEntryService.getGroupDepotEntry(
				scopeGroup.getGroupId());

			portletURL.setParameter(
				"depotEntryId", String.valueOf(depotEntry.getDepotEntryId()));

			breadcrumbEntry = new BreadcrumbEntry();

			breadcrumbEntry.setTitle(
				scopeGroup.getDescriptiveName(
					_portal.getLocale(httpServletRequest)));
			breadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(breadcrumbEntry);
		}
		catch (PortalException portalException) {
			portalException.printStackTrace();
		}

		breadcrumbEntries.addAll(originalBreadcrumbEntries);

		return breadcrumbEntries;
	}

	@Reference
	private DepotConfiguration _depotConfiguration;

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}