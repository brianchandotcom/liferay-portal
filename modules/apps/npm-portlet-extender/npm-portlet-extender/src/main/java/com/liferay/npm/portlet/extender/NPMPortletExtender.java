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

package com.liferay.npm.portlet.extender;

import com.liferay.npm.portlet.extender.internal.NPMPortlet;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.namespace.extender.ExtenderNamespace;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ray Augé
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = NPMPortletExtender.class)
public class NPMPortletExtender {

	@Activate
	public void activate(BundleContext context) {
		_bundleTracker = new BundleTracker<>(
			context, Bundle.ACTIVE, _bundleTrackerCustomizer);

		_bundleTracker.open();
	}

	@Deactivate
	public void deactivate() {
		_bundleTracker.close();

		_bundleTracker = null;
	}

	private void _addServiceProperties(
		Dictionary<String, Object> properties, JSONObject jsonObject) {

		if (jsonObject == null) {
			return;
		}

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			Object value = jsonObject.get(key);

			if (value instanceof JSONObject) {
				String stringValue = value.toString();

				properties.put(key, stringValue);
			}
			else if (value instanceof JSONArray) {
				JSONArray array = (JSONArray)value;

				List<String> values = new ArrayList<>();

				for (int i = 0; i < array.length(); i++) {
					Object object = array.get(i);

					values.add(object.toString());
				}

				properties.put(key, values.toArray(new String[0]));
			}
			else {
				properties.put(key, value);
			}
		}
	}

	private boolean _optIn(Bundle bundle) {
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleWire> bundleWires = bundleWiring.getRequiredWires(
			ExtenderNamespace.EXTENDER_NAMESPACE);

		for (BundleWire bundleWire : bundleWires) {
			BundleCapability bundleCapability = bundleWire.getCapability();

			Map<String, Object> attributes = bundleCapability.getAttributes();

			Object value = attributes.get(ExtenderNamespace.EXTENDER_NAMESPACE);

			if ((value != null) && value.equals("liferay.npm.portlet")) {
				return true;
			}
		}

		return false;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		NPMPortletExtender.class);

	private BundleTracker<ServiceRegistration<?>> _bundleTracker;

	private BundleTrackerCustomizer<ServiceRegistration<?>>
		_bundleTrackerCustomizer =
			new BundleTrackerCustomizer<ServiceRegistration<?>>() {

				@Override
				public ServiceRegistration<?> addingBundle(
					Bundle bundle, BundleEvent event) {

					if (!_optIn(bundle)) {
						return null;
					}

					URL jsonURL = bundle.getEntry(
						"META-INF/resources/package.json");

					if (jsonURL == null) {
						return null;
					}

					try (InputStream inputStream = jsonURL.openStream()) {
						String jsonString = StringUtil.read(inputStream);

						JSONObject jsonObject = _jsonFactory.createJSONObject(
							jsonString);

						final String name = jsonObject.getString("name");
						final String version = jsonObject.getString("version");

						Dictionary<String, Object> properties =
							new Hashtable<>();

						properties.put("javax.portlet.name", name);

						_addServiceProperties(
							properties, jsonObject.getJSONObject("portlet"));

						BundleContext bundleContext = bundle.getBundleContext();

						ServiceRegistration<?> serviceRegistration =
							bundleContext.registerService(
								new String[] {Portlet.class.getName()},
								new NPMPortlet(name, version), properties);

						return serviceRegistration;
					}
					catch (Exception e) {
						_logger.error(
							"Unable to process package.json of " +
								bundle.getSymbolicName(),
							e);
					}

					return null;
				}

				@Override
				public void modifiedBundle(
					Bundle bundle, BundleEvent event,
					ServiceRegistration<?> registration) {
				}

				@Override
				public void removedBundle(
					Bundle bundle, BundleEvent event,
					ServiceRegistration<?> registration) {

					registration.unregister();
				}

			};

	@Reference
	private JSONFactory _jsonFactory;

}