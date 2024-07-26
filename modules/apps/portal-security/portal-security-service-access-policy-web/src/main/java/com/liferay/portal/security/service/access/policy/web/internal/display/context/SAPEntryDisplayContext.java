/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryServiceUtil;
import com.liferay.portal.security.service.access.policy.util.comparator.SAPEntryNameComparator;

/**
 * @author Eudaldo Alonso
 */
public class SAPEntryDisplayContext {

	public SAPEntryDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<SAPEntry> getSearchContainer() {
		if (_sapEntrySearchContainer != null) {
			return _sapEntrySearchContainer;
		}

		SearchContainer<SAPEntry> sapEntrySearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest,
				_liferayPortletResponse.createRenderURL(), null,
				"there-are-no-service-access-policies");

		boolean orderByAsc = false;

		String orderByType = ParamUtil.getString(
			_liferayPortletRequest, "orderByType", "asc");

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		sapEntrySearchContainer.setOrderByComparator(
			SAPEntryNameComparator.getInstance(orderByAsc));

		sapEntrySearchContainer.setOrderByType(orderByType);
		sapEntrySearchContainer.setResultsAndTotal(
			() -> SAPEntryServiceUtil.getCompanySAPEntries(
				_themeDisplay.getCompanyId(),
				sapEntrySearchContainer.getStart(),
				sapEntrySearchContainer.getEnd(),
				sapEntrySearchContainer.getOrderByComparator()),
			SAPEntryServiceUtil.getCompanySAPEntriesCount(
				_themeDisplay.getCompanyId()));

		_sapEntrySearchContainer = sapEntrySearchContainer;

		return _sapEntrySearchContainer;
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<SAPEntry> _sapEntrySearchContainer;
	private final ThemeDisplay _themeDisplay;

}