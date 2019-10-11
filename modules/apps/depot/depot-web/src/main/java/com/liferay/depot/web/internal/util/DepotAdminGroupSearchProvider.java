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

package com.liferay.depot.web.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearchTerms;

import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = DepotAdminGroupSearchProvider.class)
public class DepotAdminGroupSearchProvider {

	public GroupSearch getGroupSearch(
		PortletRequest portletRequest, PortletURL portletURL) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("site", Boolean.FALSE);

		GroupSearch groupSearch = new GroupSearch(portletRequest, portletURL);

		GroupSearchTerms searchTerms =
			(GroupSearchTerms)groupSearch.getSearchTerms();

		List results = null;

		if (searchTerms.hasSearchTerms()) {
			int total = _groupLocalService.searchCount(
				company.getCompanyId(), _classNameIds,
				searchTerms.getKeywords(), groupParams);

			groupSearch.setTotal(total);

			results = _groupLocalService.search(
				company.getCompanyId(), _classNameIds,
				searchTerms.getKeywords(), groupParams, groupSearch.getStart(),
				groupSearch.getEnd(), groupSearch.getOrderByComparator());
		}
		else {
			long groupId = ParamUtil.getLong(
				portletRequest, "groupId",
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			int total = _groupLocalService.searchCount(
				company.getCompanyId(), _classNameIds, groupId,
				searchTerms.getKeywords(), groupParams);

			groupSearch.setTotal(total);

			results = _groupLocalService.search(
				company.getCompanyId(), _classNameIds, groupId,
				searchTerms.getKeywords(), groupParams, groupSearch.getStart(),
				groupSearch.getEnd(), groupSearch.getOrderByComparator());
		}

		groupSearch.setEmptyResultsMessage(
			LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					portletRequest.getLocale(), getClass()),
				"no-repositories-were-found"));

		groupSearch.setResults(results);

		return groupSearch;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {

		_classNameIds = new long[] {
			PortalUtil.getClassNameId(DepotEntry.class.getName())
		};
	}

	private long[] _classNameIds;

	@Reference
	private GroupLocalService _groupLocalService;

}