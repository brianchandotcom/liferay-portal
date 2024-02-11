/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class OAuth2ConnectedApplicationsDisplayContext {

	public OAuth2ConnectedApplicationsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public SearchContainer<OAuth2Authorization> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<OAuth2Authorization> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest,
				PortletURLUtil.getCurrent(
					_liferayPortletRequest, _liferayPortletResponse),
				null, "no-connected-applications-were-found");

		searchContainer.setId("oAuth2ConnectedApplicationsSearchContainer");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByComparator(
			OrderByComparatorFactoryUtil.create(
				"OAuth2Authorization", _getOrderByCol(),
				Objects.equals(_getOrderByType(), "asc")));
		searchContainer.setOrderByType(_getOrderByType());
		searchContainer.setResultsAndTotal(
			() -> OAuth2AuthorizationServiceUtil.getUserOAuth2Authorizations(
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator()),
			OAuth2AuthorizationServiceUtil.getUserOAuth2AuthorizationsCount());
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private String _getOrderByCol() {
		return ParamUtil.getString(
			_liferayPortletRequest, "orderByCol", "createDate");
	}

	private String _getOrderByType() {
		return ParamUtil.getString(
			_liferayPortletRequest, "orderByType", "asc");
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<OAuth2Authorization> _searchContainer;

}