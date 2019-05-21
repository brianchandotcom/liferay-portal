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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.marketplace.app.manager.web.internal.constants.BundleStateConstants;
import com.liferay.marketplace.app.manager.web.internal.util.AppDisplay;
import com.liferay.marketplace.app.manager.web.internal.util.AppDisplayFactoryUtil;
import com.liferay.marketplace.app.manager.web.internal.util.BundleManagerUtil;
import com.liferay.marketplace.app.manager.web.internal.util.BundleUtil;
import com.liferay.marketplace.app.manager.web.internal.util.comparator.BundleComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;

/**
 * @author Pei-Jung Lan
 */
public class ViewModulesManagementToolbarDisplayContext
	extends BaseAppManagerManagementToolbarDisplayContext {

	public ViewModulesManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest);
	}

	public String getApp() {
		return ParamUtil.getString(request, "app");
	}

	public AppDisplay getAppDisplay() {
		String app = ParamUtil.getString(request, "app");

		AppDisplay appDisplay = null;

		List<Bundle> allBundles = BundleManagerUtil.getBundles();

		if (Validator.isNumber(app)) {
			appDisplay = AppDisplayFactoryUtil.getAppDisplay(
				allBundles, GetterUtil.getLong(app));
		}

		if (appDisplay == null) {
			appDisplay = AppDisplayFactoryUtil.getAppDisplay(
				allBundles, app, request.getLocale());
		}

		return appDisplay;
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getStatusDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "status"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "order-by"));
					});
			}
		};
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_modules.jsp");
		portletURL.setParameter("app", getApp());
		portletURL.setParameter("state", getState());
		portletURL.setParameter("orderByType", getOrderByType());

		if (_searchContainer != null) {
			portletURL.setParameter(
				_searchContainer.getCurParam(),
				String.valueOf(_searchContainer.getCur()));
			portletURL.setParameter(
				_searchContainer.getDeltaParam(),
				String.valueOf(_searchContainer.getDelta()));
		}

		return portletURL;
	}

	@Override
	public SearchContainer getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer searchContainer = new SearchContainer(
			liferayPortletRequest, getPortletURL(), null,
			"no-modules-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		List<Bundle> bundles = null;

		AppDisplay appDisplay = getAppDisplay();

		bundles = appDisplay.getBundles();

		BundleUtil.filterBundles(
			bundles, BundleStateConstants.getState(getState()));

		bundles = ListUtil.sort(
			bundles, new BundleComparator(getOrderByType()));

		int end = searchContainer.getEnd();

		if (end > bundles.size()) {
			end = bundles.size();
		}

		searchContainer.setResults(
			bundles.subList(searchContainer.getStart(), end));

		searchContainer.setTotal(bundles.size());

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private SearchContainer _searchContainer;

}