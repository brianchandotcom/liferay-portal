/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.item.selector.criteria.group.criterion.GroupItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.search.GroupSearch;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DepotAdminGroupSearchProvider.class)
public class DepotAdminGroupSearchProvider {

	public GroupSearch getGroupSearch(
			GroupItemSelectorCriterion groupItemSelectorCriterion,
			PortletRequest portletRequest, PortletURL portletURL)
		throws PortalException {

		if (!groupItemSelectorCriterion.isIncludeAllVisibleGroups()) {
			return _getGroupConnectedDepotGroupsGroupSearch(
				portletRequest, portletURL);
		}

		return _getGroupSearch(portletRequest, portletURL);
	}

	public GroupSearch getGroupSearch(
			PortletRequest portletRequest, PortletURL portletURL)
		throws PortalException {

		return _getGroupSearch(portletRequest, portletURL);
	}

	@Activate
	protected void activate() {
		_classNameIds = new long[] {
			_portal.getClassNameId(DepotEntry.class.getName())
		};
	}

	private GroupSearch _getGroupConnectedDepotGroupsGroupSearch(
			PortletRequest portletRequest, PortletURL portletURL)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		GroupSearch groupSearch = new GroupSearch(portletRequest, portletURL);

		groupSearch.setEmptyResultsMessage(
			_language.get(
				portletRequest.getLocale(), "no-asset-libraries-were-found"));
		groupSearch.setResultsAndTotal(
			() -> {
				List<DepotEntry> depotEntries =
					_depotEntryService.getGroupConnectedDepotEntries(
						themeDisplay.getScopeGroupId(), groupSearch.getStart(),
						groupSearch.getEnd());

				List<Group> groups = new ArrayList<>();

				for (DepotEntry depotEntry : depotEntries) {
					groups.add(depotEntry.getGroup());
				}

				return groups;
			},
			_depotEntryService.getGroupConnectedDepotEntriesCount(
				themeDisplay.getScopeGroupId()));

		return groupSearch;
	}

	private GroupSearch _getGroupSearch(
			PortletRequest portletRequest, PortletURL portletURL)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		LinkedHashMap<String, Object> groupParams =
			LinkedHashMapBuilder.<String, Object>put(
				"actionId", ActionKeys.VIEW
			).put(
				"site", Boolean.FALSE
			).build();

		GroupSearch groupSearch = new GroupSearch(portletRequest, portletURL);

		groupSearch.setEmptyResultsMessage(
			_language.get(
				portletRequest.getLocale(), "no-asset-libraries-were-found"));

		String keywords = ParamUtil.getString(portletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			groupSearch.setResultsAndTotal(
				() -> _groupService.search(
					company.getCompanyId(), _classNameIds, keywords,
					groupParams, groupSearch.getStart(), groupSearch.getEnd(),
					groupSearch.getOrderByComparator()),
				_groupService.searchCount(
					company.getCompanyId(), _classNameIds, keywords,
					groupParams));
		}
		else {
			groupSearch.setResultsAndTotal(
				() -> _groupService.search(
					company.getCompanyId(), _classNameIds, keywords,
					groupParams, groupSearch.getStart(), groupSearch.getEnd(),
					groupSearch.getOrderByComparator()),
				_groupService.searchCount(
					company.getCompanyId(), _classNameIds, keywords,
					groupParams));
		}

		return groupSearch;
	}

	private long[] _classNameIds;

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private GroupService _groupService;

	@Reference
	private Language _language;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

}