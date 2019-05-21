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

import com.liferay.marketplace.app.manager.web.internal.util.AppDisplay;
import com.liferay.marketplace.app.manager.web.internal.util.AppDisplayFactoryUtil;
import com.liferay.marketplace.app.manager.web.internal.util.BundleManagerUtil;
import com.liferay.marketplace.app.manager.web.internal.util.comparator.ModuleServiceReferenceComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Pei-Jung Lan
 */
public class ViewModuleManagementToolbarDisplayContext
	extends BaseAppManagerManagementToolbarDisplayContext {

	public ViewModuleManagementToolbarDisplayContext(
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

	public Bundle getBundle() {
		String symbolicName = ParamUtil.getString(request, "symbolicName");
		String version = ParamUtil.getString(request, "version");

		return BundleManagerUtil.getBundle(symbolicName, version);
	}

	public String getPluginType() {
		return ParamUtil.getString(request, "pluginType", "components");
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_module.jsp");
		portletURL.setParameter("app", getApp());

		Bundle bundle = getBundle();

		portletURL.setParameter("symbolicName", bundle.getSymbolicName());
		portletURL.setParameter("version", String.valueOf(bundle.getVersion()));

		portletURL.setParameter("pluginType", getPluginType());
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

		String emptyResultsMessage = "no-portlets-were-found";

		String pluginType = getPluginType();

		if (pluginType.equals("components")) {
			emptyResultsMessage = "no-components-were-found";
		}

		SearchContainer searchContainer = new SearchContainer(
			liferayPortletRequest, getPortletURL(), null, emptyResultsMessage);

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		Bundle bundle = getBundle();

		BundleContext bundleContext = bundle.getBundleContext();

		List<ServiceReference<?>> serviceReferences =
			Collections.<ServiceReference<?>>emptyList();

		if (pluginType.equals("portlets")) {
			Collection<ServiceReference<Portlet>> serviceReferenceCollection =
				bundleContext.getServiceReferences(
					Portlet.class,
					"(service.bundleid=" + bundle.getBundleId() + ")");

			serviceReferences = new ArrayList<>(serviceReferenceCollection);

			serviceReferences = ListUtil.sort(
				serviceReferences,
				new ModuleServiceReferenceComparator(
					"javax.portlet.display-name", getOrderByType()));
		}
		else {
			ServiceReference<?>[] serviceReferenceArray =
				(ServiceReference<?>[])bundleContext.getServiceReferences(
					(String)null,
					"(&(component.id=*)(service.bundleid=" +
						bundle.getBundleId() + "))");

			serviceReferences = ListUtil.toList(serviceReferenceArray);

			serviceReferences = ListUtil.sort(
				serviceReferences,
				new ModuleServiceReferenceComparator(
					"component.name", getOrderByType()));
		}

		int end = searchContainer.getEnd();

		if (end > serviceReferences.size()) {
			end = serviceReferences.size();
		}

		searchContainer.setResults(
			serviceReferences.subList(searchContainer.getStart(), end));

		searchContainer.setTotal(serviceReferences.size());

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private SearchContainer _searchContainer;

}