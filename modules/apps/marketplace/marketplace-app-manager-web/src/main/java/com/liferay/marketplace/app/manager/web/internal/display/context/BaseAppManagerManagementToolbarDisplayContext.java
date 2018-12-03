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

package com.liferay.marketplace.app.manager.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.BaseManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.marketplace.app.manager.web.internal.constants.BundleStateConstants;
import com.liferay.marketplace.app.manager.web.internal.util.BundleManagerUtil;
import com.liferay.marketplace.app.manager.web.internal.util.MarketplaceAppManagerUtil;
import com.liferay.marketplace.model.App;
import com.liferay.marketplace.service.AppLocalServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;

/**
 * @author Pei-Jung Lan
 */
public abstract class BaseAppManagerManagementToolbarDisplayContext
	extends BaseManagementToolbarDisplayContext {

	public BaseAppManagerManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request) {

		super(liferayPortletRequest, liferayPortletResponse, request);
	}

	public String getCategory() {
		if (Validator.isNull(_category)) {
			_category = ParamUtil.getString(
				request, "category", "all-categories");
		}

		return _category;
	}

	public List<DropdownItem> getCategoryDropdownItems() {
		List<App> apps = AppLocalServiceUtil.getApps(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		List<Bundle> bundles = BundleManagerUtil.getBundles();

		String[] categories = MarketplaceAppManagerUtil.getCategories(
			apps, bundles);

		Map<String, String> categoriesMap = new LinkedHashMap<>();

		for (String category : categories) {
			String kebabCaseCategory = StringUtil.replace(
				StringUtil.toLowerCase(category), CharPool.SPACE,
				CharPool.DASH);

			String translatedCategory = LanguageUtil.get(
				request, kebabCaseCategory, category);

			categoriesMap.put(translatedCategory, category);
		}

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("resetCur", Boolean.TRUE.toString());

		return getDropdownItems(
			categoriesMap, portletURL, "category", getCategory());
	}

	@Override
	public String getOrderByCol() {
		return ParamUtil.getString(request, "orderByCol", "title");
	}

	@Override
	public abstract PortletURL getPortletURL();

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = liferayPortletResponse.createRenderURL();

		searchActionURL.setParameter("mvcPath", "/view_search_results.jsp");

		return searchActionURL.toString();
	}

	public abstract SearchContainer getSearchContainer() throws Exception;

	public String getState() {
		if (Validator.isNull(_state)) {
			_state = ParamUtil.getString(request, "state", "all-statuses");
		}

		return _state;
	}

	public List<DropdownItem> getStatusDropdownItems() {
		String[] states = {
			"all-statuses", BundleStateConstants.ACTIVE_LABEL,
			BundleStateConstants.RESOLVED_LABEL,
			BundleStateConstants.INSTALLED_LABEL
		};

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("resetCur", Boolean.TRUE.toString());

		return getDropdownItems(
			getDefaultEntriesMap(states), portletURL, "state", getState());
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"title"};
	}

	private String _category;
	private String _state;

}