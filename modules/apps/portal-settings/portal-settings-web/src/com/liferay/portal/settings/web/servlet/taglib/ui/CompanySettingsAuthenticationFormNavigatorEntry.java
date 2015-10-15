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

package com.liferay.portal.settings.web.servlet.taglib.ui;

import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.settings.web.constants.PortalSettingsWebKeys;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 * @author Philip Jones
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true, property = {"service.ranking:Integer=70"},
	service = FormNavigatorEntry.class
)
public class CompanySettingsAuthenticationFormNavigatorEntry
	extends BaseCompanySettingsFormNavigatorEntry {

	@Activate
	public void activated() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			DynamicInclude.class,
			new PortalSettingsAuthenticationDynamicIncludeTrackerCustomizer());

		_serviceTracker.open();
	}

	@Deactivate
	public void deactivated() {
		if (_serviceTracker != null) {
			_serviceTracker.close();
		}
	}

	@Override
	public String getCategoryKey() {
		return
			FormNavigatorConstants.CATEGORY_KEY_COMPANY_SETTINGS_CONFIGURATION;
	}

	@Override
	public String getKey() {
		return "authentication";
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(
			PortalSettingsWebKeys.AUTHENTICATION_DYNAMIC_INCLUDES,
			_tabs.values());

		String[] tabNamesArray = _tabs.keySet().toArray(
			new String[_tabs.size()]);

		String tabNames = StringUtil.merge(tabNamesArray);

		request.setAttribute(
			PortalSettingsWebKeys.AUTHENTICATION_TAB_NAMES, tabNames);

		super.include(request, response);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.settings.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/authentication.jsp";
	}

	private ServiceTracker<DynamicInclude, String> _serviceTracker = null;
	private final Map<String, DynamicInclude> _tabs = new ConcurrentHashMap<>();

	private class PortalSettingsAuthenticationDynamicIncludeTrackerCustomizer
		implements ServiceTrackerCustomizer<DynamicInclude, String> {

		@Override
		public String addingService(
			final ServiceReference<DynamicInclude> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			final DynamicInclude dynamicInclude = registry.getService(
				serviceReference);

			Object labelProperty = serviceReference.getProperty("tab-name");

			final String label = labelProperty != null ?
				labelProperty.toString() : dynamicInclude.getClass().getName();

			dynamicInclude.register(
				new DynamicInclude.DynamicIncludeRegistry() {
					@Override
					public void register(String key) {
						if (key.equals(
								"portal-settings-web:/authentication.jsp")) {

							_tabs.put(label, dynamicInclude);
						}
					}
				});

			return label;
		}

		@Override
		public void modifiedService(
			ServiceReference<DynamicInclude> serviceReference, String label) {

			removedService(serviceReference, label);
			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<DynamicInclude> serviceReference, String label) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_tabs.remove(label);
		}

	}

}