/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class OAuth2ApplicationsDisplayContext {

	public OAuth2ApplicationsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<OAuth2Application> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<OAuth2Application> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest,
				PortletURLUtil.getCurrent(
					_liferayPortletRequest, _liferayPortletResponse),
				null, "no-applications-were-found");

		searchContainer.setId("oAuth2ApplicationsSearchContainer");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByComparator(
			OrderByComparatorFactoryUtil.create(
				"OAuth2Application", _getOrderByCol(),
				Objects.equals(_getOrderByType(), "asc")));
		searchContainer.setOrderByType(_getOrderByType());
		searchContainer.setResultsAndTotal(
			() -> OAuth2ApplicationServiceUtil.getOAuth2Applications(
				_themeDisplay.getCompanyId(), searchContainer.getStart(),
				searchContainer.getEnd(),
				searchContainer.getOrderByComparator()),
			OAuth2ApplicationServiceUtil.getOAuth2ApplicationsCount(
				_themeDisplay.getCompanyId()));
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private String _getOrderByCol() {
		return ParamUtil.getString(
			_liferayPortletRequest, "orderByCol", "name");
	}

	private String _getOrderByType() {
		return ParamUtil.getString(
			_liferayPortletRequest, "orderByType", "asc");
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<OAuth2Application> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}