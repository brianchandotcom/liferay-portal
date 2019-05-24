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

package com.liferay.application.list.adapter;

import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletCategoryUtil;

import java.util.Dictionary;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Adolfo Pérez
 */
public class PortletPanelAppAdapterServiceTrackerCustomizer
	implements ServiceTrackerCustomizer<Portlet, PanelApp> {

	public PortletPanelAppAdapterServiceTrackerCustomizer(
		BundleContext bundleContext,
		Map<ServiceReference<Portlet>, ServiceRegistration<PanelApp>>
			serviceRegistrations) {

		_bundleContext = bundleContext;
		_serviceRegistrations = serviceRegistrations;
	}

	@Override
	public PanelApp addingService(ServiceReference<Portlet> serviceReference) {
		String portletId = (String)serviceReference.getProperty(
			"javax.portlet.name");

		if (Validator.isNull(portletId)) {
			return null;
		}

		String controlPanelCategory = (String)serviceReference.getProperty(
			"com.liferay.portlet.control-panel-entry-category");

		if (Validator.isNull(controlPanelCategory)) {
			return null;
		}

		PanelApp portletPanelAppAdapter = new PortletPanelAppAdapter(portletId);

		Dictionary<String, Object> panelAppProperties =
			new HashMapDictionary<>();

		panelAppProperties.put(
			"panel.category.key",
			PortletCategoryUtil.getPortletCategoryKey(controlPanelCategory));

		Integer serviceRanking = getServiceRanking(serviceReference);

		if (serviceRanking != null) {
			panelAppProperties.put("service.ranking", serviceRanking);
		}

		ServiceRegistration<PanelApp> serviceRegistration =
			_bundleContext.registerService(
				PanelApp.class, portletPanelAppAdapter, panelAppProperties);

		_serviceRegistrations.put(serviceReference, serviceRegistration);

		return portletPanelAppAdapter;
	}

	@Override
	public void modifiedService(
		ServiceReference<Portlet> serviceReference, PanelApp panelApp) {

		removedService(serviceReference, panelApp);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<Portlet> serviceReference, PanelApp panelApp) {

		ServiceRegistration<PanelApp> serviceRegistration =
			_serviceRegistrations.remove(serviceReference);

		serviceRegistration.unregister();
	}

	protected Integer getServiceRanking(
		ServiceReference<Portlet> serviceReference) {

		String controlPanelEntryWeight = (String)serviceReference.getProperty(
			"com.liferay.portlet.control-panel-entry-weight");

		if (Validator.isNotNull(controlPanelEntryWeight)) {
			return (int)Math.ceil(
				GetterUtil.getDouble(controlPanelEntryWeight) * 100);
		}

		return null;
	}

	private final BundleContext _bundleContext;
	private final Map<ServiceReference<Portlet>, ServiceRegistration<PanelApp>>
		_serviceRegistrations;

}