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

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.item.selector.constants.ItemSelectorPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntryContributor;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.util.GroupURLProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

		long depotEntryId = ParamUtil.getLong(
			httpServletRequest, "depotEntryId");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if ((depotEntryId == 0) && !scopeGroup.isDepot()) {
			return originalBreadcrumbEntries;
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (Objects.equals(
				ItemSelectorPortletKeys.ITEM_SELECTOR,
				portletDisplay.getPortletName()) ||
			themeDisplay.isStatePopUp()) {

			return originalBreadcrumbEntries;
		}

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		try {
			breadcrumbEntries.add(
				_getAssetLibrariesBreadcrumbEntry(
					themeDisplay.getControlPanelGroup(), httpServletRequest));

			breadcrumbEntries.add(
				_getAssetLibraryBreadcrumbEntry(
					_getDepotEntry(scopeGroup.getGroupId(), depotEntryId),
					httpServletRequest));

			if (originalBreadcrumbEntries.isEmpty() &&
				!Objects.equals(
					DepotPortletKeys.DEPOT_ADMIN,
					portletDisplay.getPortletName())) {

				breadcrumbEntries.add(
					_getPortletBreadcrumbEntry(
						httpServletRequest, scopeGroup,
						_getTitle(
							httpServletRequest, themeDisplay.getLanguageId(),
							portletDisplay)));
			}
			else if (!originalBreadcrumbEntries.isEmpty()) {
				BreadcrumbEntry breadcrumbEntry = originalBreadcrumbEntries.get(
					0);

				String title = _language.get(httpServletRequest, "home");

				if (Objects.equals(breadcrumbEntry.getTitle(), title)) {
					breadcrumbEntry.setTitle(
						portletDisplay.getPortletDisplayName());
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		breadcrumbEntries.addAll(originalBreadcrumbEntries);

		return breadcrumbEntries;
	}

	private BreadcrumbEntry _getAssetLibrariesBreadcrumbEntry(
		Group group, HttpServletRequest httpServletRequest) {

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			_language.get(httpServletRequest, "category.asset-libraries"));

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, group, DepotPortletKeys.DEPOT_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		breadcrumbEntry.setURL(portletURL.toString());

		return breadcrumbEntry;
	}

	private BreadcrumbEntry _getAssetLibraryBreadcrumbEntry(
			DepotEntry depotEntry, HttpServletRequest httpServletRequest)
		throws PortalException {

		Group group = depotEntry.getGroup();

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, group, DepotPortletKeys.DEPOT_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/depot/view_depot_dashboard");
		portletURL.setParameter(
			"depotEntryId", String.valueOf(depotEntry.getDepotEntryId()));

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			group.getDescriptiveName(_portal.getLocale(httpServletRequest)));

		breadcrumbEntry.setURL(portletURL.toString());

		return breadcrumbEntry;
	}

	private DepotEntry _getDepotEntry(long groupId, long depotEntryId)
		throws PortalException {

		if (depotEntryId == 0) {
			return _depotEntryService.getGroupDepotEntry(groupId);
		}

		return _depotEntryService.getDepotEntry(depotEntryId);
	}

	private BreadcrumbEntry _getPortletBreadcrumbEntry(
		HttpServletRequest httpServletRequest, Group scopeGroup, String title) {

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(_language.get(httpServletRequest, title));
		breadcrumbEntry.setURL(
			_groupURLProvider.getGroupURL(
				scopeGroup,
				(PortletRequest)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST)));

		return breadcrumbEntry;
	}

	private String _getTitle(
		HttpServletRequest httpServletRequest, String languageId,
		PortletDisplay portletDisplay) {

		String title = portletDisplay.getPortletDisplayName();

		if (Validator.isNotNull(title)) {
			return title;
		}

		if (Validator.isNotNull(portletDisplay.getId())) {
			title = _portal.getPortletTitle(portletDisplay.getId(), languageId);

			if (Validator.isNotNull(title)) {
				return title;
			}
		}

		return _language.get(httpServletRequest, "home");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotBreadcrumbEntryContributorImpl.class);

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private GroupURLProvider _groupURLProvider;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}