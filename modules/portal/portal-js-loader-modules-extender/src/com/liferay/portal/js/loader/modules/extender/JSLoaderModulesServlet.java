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

package com.liferay.portal.js.loader.modules.extender;

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
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
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
		"osgi.http.whiteboard.servlet.name=JS Loader Modules Servlet",
		"osgi.http.whiteboard.servlet.pattern=/js_loader_modules",
		"service.ranking:Integer=" + Details.MAX_VALUE_LESS_1K
	},
	service = {JSLoaderModulesServlet.class, Servlet.class}
)
public class JSLoaderModulesServlet extends HttpServlet
	implements Servlet, ServiceTrackerCustomizer
		<ServletContext, ServiceReference<ServletContext>> {

	@Override
	public ServiceReference<ServletContext> addingService(
		ServiceReference<ServletContext> serviceReference) {

		String contextPath = (String)serviceReference.getProperty(
			"osgi.web.contextpath");

		JSLoaderModule jsLoaderModule = new JSLoaderModule(
			serviceReference.getBundle(), contextPath);

		_jsLoaderModules.put(serviceReference, jsLoaderModule);

		return serviceReference;
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceReference<ServletContext> trackedServiceReference) {

		removedService(serviceReference, trackedServiceReference);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceReference<ServletContext> trackedServiceReference) {

		_jsLoaderModules.remove(serviceReference);
	}

	public class JSLoaderModule {

		public JSLoaderModule(Bundle bundle, String contextPath) {
			_bundle = bundle;
			_contextPath = contextPath;

			Version version = _bundle.getVersion();

			_version = version.toString();

			BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

			List<BundleCapability> bundleCapabilities =
				bundleWiring.getCapabilities(Details.OSGI_WEBRESOURCE);

			if (bundleCapabilities.isEmpty()) {
				_name = _bundle.getSymbolicName();

				return;
			}

			BundleCapability bundleCapability = bundleCapabilities.get(0);

			Map<String, Object> attributes = bundleCapability.getAttributes();

			_name = (String)attributes.get(Details.OSGI_WEBRESOURCE);

			URL url = _bundle.getEntry(Details.CONFIG_JSON);

			urlToConfiguration(url, bundleWiring);
		}

		public String getContextPath() {
			return _contextPath;
		}

		public String getName() {
			return _name;
		}

		public String getUnversionedConfiguration() {
			return _unversionedConfiguration;
		}

		public String getUnversionedMapsConfiguration() {
			return _unversionedMapsConfiguration;
		}

		public String getVersion() {
			return _version;
		}

		public String getVersionedConfiguration() {
			return _versionedConfiguration;
		}

		protected String generateConfiguration(
			JSONObject jsonObject, BundleWiring bundleWiring,
			boolean versionedModuleName) {

			if (!_details.applyVersioning()) {
				if (versionedModuleName) {
					return "";
				}

				return jsonObject.toString();
			}

			List<BundleWire> bundleWires = bundleWiring.getRequiredWires(
				Details.OSGI_WEBRESOURCE);

			JSONArray namesJSONArray = jsonObject.names();

			for (int i = 0; i < namesJSONArray.length(); i++) {
				String name = (String)namesJSONArray.get(i);

				int x = name.indexOf('/');

				if (x == -1) {
					continue;
				}

				String moduleName = name.substring(0, x);

				if (!moduleName.equals(getName())) {
					continue;
				}

				String modulePath = name.substring(x);

				moduleName = getName() + "@" + getVersion() + modulePath;

				JSONObject nameJSONObject = jsonObject.getJSONObject(name);

				JSONArray dependenciesJSONArray = nameJSONObject.getJSONArray(
					"dependencies");

				for (int j = 0; j < dependenciesJSONArray.length(); j++) {
					String dependency = dependenciesJSONArray.getString(j);

					int y = dependency.indexOf('/');

					if (y == -1) {
						continue;
					}

					String dependencyName = dependency.substring(0, y);
					String dependencyPath = dependency.substring(y);

					if (dependencyName.equals(getName())) {
						dependencyName =
							getName() + "@" + getVersion() + dependencyPath;

						dependenciesJSONArray.put(j, dependencyName);
					}
					else {
						normalizeDependencies(
							dependencyName, dependencyPath,
							dependenciesJSONArray, j, bundleWires);
					}
				}

				if (versionedModuleName) {
					jsonObject.remove(name);

					jsonObject.put(moduleName, nameJSONObject);
				}
				else {
					jsonObject.put(name, nameJSONObject);
				}
			}

			return jsonObject.toString();
		}

		protected String generateMapsConfiguration(String configuration) {
			JSONObject mapsConfigurationJSONObject = new JSONObject();

			JSONObject configurationJSONObject = new JSONObject(
				"{" + configuration + "}");

			JSONArray namesJSONArray = configurationJSONObject.names();

			for (int i = 0; i < namesJSONArray.length(); i++) {
				String name = (String)namesJSONArray.get(i);

				int x = name.indexOf('/');

				String moduleRootPath = name.substring(0, x + 1);

				String submodulePath = name.substring(x + 1);

				int y = submodulePath.indexOf('/');

				if (y == -1) {
					continue;
				}

				String submoduleName = submodulePath.substring(0, y);

				mapsConfigurationJSONObject.put(
					submoduleName, moduleRootPath.concat(submoduleName));
			}

			return mapsConfigurationJSONObject.toString();
		}

		protected String normalize(String jsonString) {
			if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
				jsonString = jsonString.substring(1, jsonString.length() - 1);
			}

			return jsonString;
		}

		protected void normalizeDependencies(
			String dependencyName, String dependencyPath, JSONArray jsonArray,
			int index, List<BundleWire> bundleWires) {

			for (BundleWire bundleWire : bundleWires) {
				BundleCapability bundleCapability = bundleWire.getCapability();

				Map<String, Object> attributes =
					bundleCapability.getAttributes();

				String attributesDependencyName = (String)attributes.get(
					Details.OSGI_WEBRESOURCE);

				if (!attributesDependencyName.equals(dependencyName)) {
					continue;
				}

				Version version = (Version)attributes.get(
					Constants.VERSION_ATTRIBUTE);

				dependencyName =
					dependencyName + "@" + version.toString() + dependencyPath;

				jsonArray.put(index, dependencyName);

				return;
			}
		}

		protected void urlToConfiguration(URL url, BundleWiring bundleWiring) {
			if (url == null) {
				return;
			}

			try (Reader reader = new InputStreamReader(url.openStream())) {
				JSONTokener jsonTokener = new JSONTokener(reader);

				JSONObject jsonObject = new JSONObject(jsonTokener);

				_unversionedConfiguration = normalize(
					generateConfiguration(jsonObject, bundleWiring, false));
				_versionedConfiguration = normalize(
					generateConfiguration(jsonObject, bundleWiring, true));

				_unversionedMapsConfiguration = normalize(
					generateMapsConfiguration(_unversionedConfiguration));
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		private final Bundle _bundle;
		private final String _contextPath;
		private final String _name;
		private String _unversionedConfiguration = "";
		private String _unversionedMapsConfiguration = "";
		private final String _version;
		private String _versionedConfiguration = "";

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
		throws IOException {

		response.setContentType(Details.CONTENT_TYPE);

		PrintWriter printWriter = response.getWriter();

		printWriter.println("(function() {");
		printWriter.print(_details.globalJSVariable());
		printWriter.println(".PATHS = {");

		String delimiter = "";
		Set<String> processedNames = new HashSet<>();

		Collection<JSLoaderModule> jsLoaderModules = _jsLoaderModules.values();

		for (JSLoaderModule jsLoaderModule : jsLoaderModules) {
			printWriter.write(delimiter);
			printWriter.write("'");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write('@');
			printWriter.write(jsLoaderModule.getVersion());
			printWriter.write("': '");
			printWriter.write(jsLoaderModule.getContextPath());
			printWriter.write("'");

			if (!processedNames.contains(jsLoaderModule.getName())) {
				processedNames.add(jsLoaderModule.getName());

				printWriter.println(",");
				printWriter.write("'");
				printWriter.write(jsLoaderModule.getName());
				printWriter.write("': '");
				printWriter.write(jsLoaderModule.getContextPath());
				printWriter.write("'");
			}

			delimiter = ",\n";
		}

		printWriter.println("\n};");
		printWriter.print(_details.globalJSVariable());
		printWriter.println(".MODULES = {");

		delimiter = "";
		processedNames.clear();

		for (JSLoaderModule jsLoaderModule : jsLoaderModules) {
			String unversionedConfiguration =
				jsLoaderModule.getUnversionedConfiguration();

			if (unversionedConfiguration.length() == 0) {
				continue;
			}

			if (!processedNames.contains(jsLoaderModule.getName())) {
				processedNames.add(jsLoaderModule.getName());

				printWriter.write(delimiter);
				printWriter.write(unversionedConfiguration);

				delimiter = ",\n";
			}

			String versionedConfiguration =
				jsLoaderModule.getVersionedConfiguration();

			if (versionedConfiguration.length() > 0) {
				printWriter.write(delimiter);
				printWriter.write(versionedConfiguration);

				delimiter = ",\n";
			}
		}

		printWriter.println("\n};");
		printWriter.print(_details.globalJSVariable());
		printWriter.println(".MAPS = {");

		delimiter = "";
		processedNames.clear();

		for (JSLoaderModule jsLoaderModule : jsLoaderModules) {
			if (processedNames.contains(jsLoaderModule.getName())) {
				continue;
			}

			processedNames.add(jsLoaderModule.getName());

			printWriter.write(delimiter);
			printWriter.write("'");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write("': '");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write('@');
			printWriter.write(jsLoaderModule.getVersion());
			printWriter.write("'");

			delimiter = ",\n";

			String unversionedMapsConfiguration =
				jsLoaderModule.getUnversionedMapsConfiguration();

			if (!unversionedMapsConfiguration.equals("")) {
				printWriter.write(delimiter);
				printWriter.write(unversionedMapsConfiguration);
			}
		}

		printWriter.println("\n};");
		printWriter.println("}());");

		printWriter.close();
	}

	protected void setDetails(Details details) {
		_details = details;
	}

	private volatile Details _details;
	private final Map<ServiceReference <ServletContext>, JSLoaderModule>
		_jsLoaderModules = new ConcurrentSkipListMap<>();
	private ServiceTracker<ServletContext, ServiceReference<ServletContext>>
		_serviceTracker;

}