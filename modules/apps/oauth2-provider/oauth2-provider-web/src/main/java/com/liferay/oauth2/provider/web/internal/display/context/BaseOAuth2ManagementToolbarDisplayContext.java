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

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public abstract class BaseOAuth2ManagementToolbarDisplayContext {

	public BaseOAuth2ManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletURL currentURLObj) {

		this.httpServletRequest = httpServletRequest;
		this.liferayPortletRequest = liferayPortletRequest;
		this.liferayPortletResponse = liferayPortletResponse;
		this.currentURLObj = currentURLObj;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(liferayPortletRequest, "orderByCol");
	}

	public String getOrderByType() {
		return ParamUtil.getString(liferayPortletRequest, "orderByType", "asc");
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL currentSortingURL = getCurrentSortingURL();

		currentSortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return currentSortingURL;
	}

	protected PortletURL getCurrentSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLUtil.clone(
			currentURLObj, liferayPortletResponse);

		sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");

		return sortingURL;
	}

	protected List<DropdownItem> getOrderByDropdownItems(
		Map<String, String> orderColumnsMap) {

		return new DropdownItemList() {
			{
				for (Map.Entry<String, String> orderByColEntry :
						orderColumnsMap.entrySet()) {

					add(
						dropdownItem -> {
							String orderByCol = orderByColEntry.getKey();

							dropdownItem.setActive(
								orderByCol.equals(getOrderByCol()));
							dropdownItem.setHref(
								getCurrentSortingURL(), "orderByCol",
								orderByCol);

							dropdownItem.setLabel(
								LanguageUtil.get(
									httpServletRequest,
									orderByColEntry.getValue()));
						});
				}
			}
		};
	}

	protected PortletURL currentURLObj;
	protected HttpServletRequest httpServletRequest;
	protected LiferayPortletRequest liferayPortletRequest;
	protected LiferayPortletResponse liferayPortletResponse;

}