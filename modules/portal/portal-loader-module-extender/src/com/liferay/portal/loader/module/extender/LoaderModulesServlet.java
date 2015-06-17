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

package com.liferay.portal.loader.module.extender;

import aQute.bnd.osgi.Constants;

import aQute.lib.converter.Converter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import java.net.URL;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import org.osgi.framework.Bundle;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=Loader Modules Servlet",
		"osgi.http.whiteboard.servlet.pattern=/loader_modules",
		"service.ranking:Integer=" + Details.MAX_VALUE_LESS_1K
	},
	service = {LoaderModulesServlet.class, Servlet.class}
)
public class LoaderModulesServlet extends HttpServlet
	implements Servlet, ServiceTrackerCustomizer<
		ServletContext, ServiceReference<ServletContext>> {

	public class LoaderModule {

		public LoaderModule(Bundle bundle, String contextPath) {
			_bundle = bundle;
			_contextPath = contextPath;

			Version version = _bundle.getVersion();

			_version = version.toString();

			BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

			List<BundleCapability> capabilities = bundleWiring.getCapabilities(
				Details.OSGI_WEBRESOURCE);

			if (capabilities.isEmpty()) {
				_name = _bundle.getSymbolicName();

				return;
			}

			BundleCapability bundleCapability = capabilities.get(0);

			Map<String, Object> attributes = bundleCapability.getAttributes();

			_name = (String)attributes.get(Details.OSGI_WEBRESOURCE);

			URL url = _bundle.getEntry(Details.CONFIG_JSON);

			_urlToConfig(url, bundleWiring);
		}

		public String getContextPath() {
			return _contextPath;
		}

		public String getName() {
			return _name;
		}

		public String getUnversionedConfig() {
			return _unversionedConfig;
		}

		public String getVersion() {
			return _version;
		}

		public String getVersionedConfig() {
			return _versionedConfig;
		}

		private String _generateConfiguration(
			JSONObject jsonObject, BundleWiring bundleWiring,
			boolean versionedModuleName) {

			if (!_details.loader_modules_apply_versioning()) {
				if (versionedModuleName) {
					return "";
				}

				return jsonObject.toString();
			}

			List<BundleWire> requiredWires = bundleWiring.getRequiredWires(
				Details.OSGI_WEBRESOURCE);

			JSONArray names = jsonObject.names();

			for (int i = 0; i < names.length(); i++) {
				String name = (String)names.get(i);

				int x = name.indexOf('/');

				if (x == -1) {
					continue;
				}

				String moduleName = name.substring(0, x);
				String modulePath = name.substring(x);

				if (!moduleName.equals(getName())) {
					continue;
				}

				moduleName = getName() + "@" + getVersion() + modulePath;

				JSONObject curObject = jsonObject.getJSONObject(name);

				JSONArray jsonArray = curObject.getJSONArray("dependencies");

				for (int j = 0; j < jsonArray.length(); j++) {
					String dependency = jsonArray.getString(j);

					int y = dependency.indexOf('/');

					if (y == -1) {
						continue;
					}

					String dependencyName = dependency.substring(0, y);
					String dependencyPath = dependency.substring(y);

					if (dependencyName.equals(getName())) {
						dependencyName =
							getName() + "@" + getVersion() + dependencyPath;

						jsonArray.put(j, dependencyName);
					}
					else {
						_normalizeDependencies(
							dependencyName, dependencyPath, jsonArray, j,
							requiredWires);
					}
				}

				if (versionedModuleName) {
					jsonObject.remove(name);
					jsonObject.put(moduleName, curObject);
				}
				else {
					jsonObject.put(name, curObject);
				}
			}

			return jsonObject.toString();
		}

		private String _normalize(String jsonString) {
			if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
				jsonString = jsonString.substring(1, jsonString.length() - 1);
			}

			return jsonString;
		}

		private void _normalizeDependencies(
			String dependencyName, String dependencyPath, JSONArray jsonArray,
			int index, List<BundleWire> requiredWires) {

			for (BundleWire bundleWire : requiredWires) {
				BundleCapability capability = bundleWire.getCapability();

				Map<String, Object> attributes = capability.getAttributes();

				String requiredWireName = (String)attributes.get(
					Details.OSGI_WEBRESOURCE);

				if (!requiredWireName.equals(dependencyName)) {
					continue;
				}

				Version requiredWireVersion = (Version)attributes.get(
					Constants.VERSION_ATTRIBUTE);
				dependencyName =
					dependencyName + "@" + requiredWireVersion.toString() +
						dependencyPath;

				jsonArray.put(index, dependencyName);

				return;
			}
		}

		private void _urlToConfig(URL url, BundleWiring bundleWiring) {
			if (url == null) {
				return;
			}

			try (Reader reader = new InputStreamReader(url.openStream())) {
				JSONTokener jsonTokener = new JSONTokener(reader);

				JSONObject jsonObject = new JSONObject(jsonTokener);

				_unversionedConfig = _normalize(
					_generateConfiguration(jsonObject, bundleWiring, false));

				_versionedConfig = _normalize(
					_generateConfiguration(jsonObject, bundleWiring, true));
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		private final Bundle _bundle;
		private final String _contextPath;
		private final String _name;
		private String _unversionedConfig = "";
		private final String _version;
		private String _versionedConfig = "";

	}

	@Override
	public ServiceReference<ServletContext> addingService(
		ServiceReference<ServletContext> serviceReference) {

		String contextPath = (String)serviceReference.getProperty(
			"osgi.web.contextpath");

		LoaderModule loaderModule = new LoaderModule(
			serviceReference.getBundle(), contextPath);

		_loaderModules.put(serviceReference, loaderModule);

		return serviceReference;
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceReference<ServletContext> trackedReference) {

		removedService(serviceReference, trackedReference);
		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceReference<ServletContext> trackedReference) {

		_loaderModules.remove(serviceReference);
	}

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		if (_serviceTracker != null) {
			_serviceTracker.close();
		}

		setDetails(Converter.cnv(Details.class, properties));

		Filter filter = FrameworkUtil.createFilter(
			"(&(objectClass=" + ServletContext.class.getName() +
				")(osgi.web.contextpath=*))");

		_serviceTracker = new ServiceTracker<>(
			componentContext.getBundleContext(), filter, this);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;
	}

	protected long getTrackingCount() {
		return _serviceTracker.getTrackingCount();
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		response.setContentType(Details.CONTENT_TYPE);

		PrintWriter writer = response.getWriter();

		writer.println("(function() {");
		writer.print(_details.loader_modules_global_js_variable());
		writer.println(".PATHS = {");

		Collection<LoaderModule> values = _loaderModules.values();

		Set<String> _processed = new HashSet<>();
		String deliminter = "";

		for (LoaderModule loaderModule : values) {
			String contextPath = loaderModule.getContextPath();
			String name = loaderModule.getName();
			String version = loaderModule.getVersion();

			writer.write(deliminter);
			writer.write("'");
			writer.write(name);
			writer.write('@');
			writer.write(version);
			writer.write("': '");
			writer.write(contextPath);
			writer.write("'");

			if (!_processed.contains(name)) {
				_processed.add(name);

				writer.println(",");
				writer.write("'");
				writer.write(name);
				writer.write("': '");
				writer.write(contextPath);
				writer.write("'");
			}

			deliminter = ",\n";
		}

		writer.println("\n};");
		writer.print(_details.loader_modules_global_js_variable());
		writer.println(".MODULES = {");

		_processed = new HashSet<>();
		deliminter = "";

		for (LoaderModule loaderModule : values) {
			String name = loaderModule.getName();
			String unversionedConfig = loaderModule.getUnversionedConfig();
			String versionedConfig = loaderModule.getVersionedConfig();

			if (unversionedConfig.length() == 0) {
				continue;
			}

			if (!_processed.contains(name)) {
				_processed.add(name);

				writer.write(deliminter);
				writer.write(unversionedConfig);
				deliminter = ",\n";
			}

			if (versionedConfig.length() > 0) {
				writer.write(deliminter);
				writer.write(versionedConfig);

				deliminter = ",\n";
			}
		}

		writer.println("\n};");
		writer.print(_details.loader_modules_global_js_variable());
		writer.println(".MAPS = {");

		_processed = new HashSet<>();
		deliminter = "";

		for (LoaderModule loaderModule : values) {
			String name = loaderModule.getName();
			String version = loaderModule.getVersion();

			if (_processed.contains(name)) {
				continue;
			}

			_processed.add(name);

			writer.write(deliminter);
			writer.write("'");
			writer.write(name);
			writer.write("': '");
			writer.write(name);
			writer.write('@');
			writer.write(version);
			writer.write("'");

			deliminter = ",\n";
		}

		writer.println("\n};");
		writer.println("}());");
		writer.close();
	}

	protected void setDetails(Details details) {
		_details = details;
	}

	private volatile Details _details;
	private final ConcurrentMap<ServiceReference<ServletContext>, LoaderModule>
		_loaderModules = new ConcurrentSkipListMap<>();
	private ServiceTracker<ServletContext, ServiceReference<ServletContext>>
		_serviceTracker;

}