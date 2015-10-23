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

package com.liferay.portal.security.sso.cas.portal.settings.web.portlet.resource.bundle;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.settings.web.constants.PortalSettingsPortletKeys;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class PortalSettingsPortletResourceBundlePublisher {

	@Activate
	protected void activated(BundleContext bundleContext) throws IOException {
		Bundle bundle = bundleContext.getBundle();

		Enumeration<URL> propertiesFiles = bundle.findEntries(
			"/content", "Language*.properties", false);

		while (propertiesFiles.hasMoreElements()) {
			registerResourceBundle(
				bundleContext, propertiesFiles.nextElement());
		}
	}

	@Deactivate
	protected void deactivated() {
		for (ServiceRegistration<ResourceBundle> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Modified
	protected void modified(BundleContext bundleContext) throws IOException {
		deactivated();

		activated(bundleContext);
	}

	protected void registerResourceBundle(
			BundleContext bundleContext, URL propertiesFile)
		throws IOException {

		PropertyResourceBundle propertyResourceBundle =
			new PropertyResourceBundle(propertiesFile.openStream());

		Dictionary<String, Object> serviceProperties = new Hashtable<>();

		String languageId = StringPool.BLANK;

		String name = propertiesFile.getFile();

		if (name.contains(StringPool.UNDERLINE)) {
			int start = name.indexOf(StringPool.UNDERLINE) + 1;

			int end = name.indexOf(".properties");

			languageId = name.substring(start, end);
		}

		serviceProperties.put(
			"javax.portlet.name", PortalSettingsPortletKeys.PORTAL_SETTINGS);

		serviceProperties.put("language.id", languageId);

		ServiceRegistration<ResourceBundle> serviceRegistration =
			bundleContext.registerService(
				ResourceBundle.class, propertyResourceBundle,
				serviceProperties);

		_serviceRegistrations.add(serviceRegistration);
	}

	private final List<ServiceRegistration<ResourceBundle>>
		_serviceRegistrations = new ArrayList<>();

}