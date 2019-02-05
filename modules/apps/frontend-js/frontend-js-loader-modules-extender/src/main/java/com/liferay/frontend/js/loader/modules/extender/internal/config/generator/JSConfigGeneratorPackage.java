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

package com.liferay.frontend.js.loader.modules.extender.internal.config.generator;

import aQute.bnd.osgi.Constants;

import com.liferay.frontend.js.loader.modules.extender.internal.Details;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Carlos Sierra Andrés
 */
public class JSConfigGeneratorPackage {

	public JSConfigGeneratorPackage(
		boolean applyVersioning, Bundle bundle, String contextPath) {

		_applyVersioning = applyVersioning;
		_contextPath = contextPath;

		Version version = bundle.getVersion();

		_version = version.toString();

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities(Details.OSGI_WEBRESOURCE);

		if (bundleCapabilities.isEmpty()) {
			_name = bundle.getSymbolicName();

			return;
		}

		BundleCapability bundleCapability = bundleCapabilities.get(0);

		Map<String, Object> attributes = bundleCapability.getAttributes();

		_name = (String)attributes.get(Details.OSGI_WEBRESOURCE);

		URL url = bundle.getEntry(Details.CONFIG_JSON);

		urlToConfiguration(url, bundleWiring);

		_setJSConfigGeneratorModules();
	}

	public String getConfiguration() {
		return _configuration;
	}

	public String getContextPath() {
		return _contextPath;
	}

	public List<JSConfigGeneratorModule> getJSConfigGeneratorModules() {
		return _jsConfigGeneratorModules;
	}

	public String getName() {
		return _name;
	}

	public String getVersion() {
		return _version;
	}

	protected String generateConfiguration(
		JSONObject jsonObject, BundleWiring bundleWiring) {

		if (!_applyVersioning) {
			return jsonObject.toString();
		}

		JSONArray namesJSONArray = jsonObject.names();

		if (namesJSONArray == null) {
			return jsonObject.toString();
		}

		List<BundleWire> bundleWires = bundleWiring.getRequiredWires(
			Details.OSGI_WEBRESOURCE);

		for (int i = 0; i < namesJSONArray.length(); i++) {
			String name = (String)namesJSONArray.get(i);

			int pos = name.indexOf(StringPool.SLASH);

			if (pos == -1) {
				continue;
			}

			String moduleName = name.substring(0, pos);

			if (!moduleName.equals(getName())) {
				continue;
			}

			String modulePath = name.substring(pos);

			moduleName = StringBundler.concat(
				getName(), StringPool.AT, getVersion(), modulePath);

			JSONObject nameJSONObject = jsonObject.getJSONObject(name);

			JSONArray dependenciesJSONArray = nameJSONObject.getJSONArray(
				"dependencies");

			for (int j = 0; j < dependenciesJSONArray.length(); j++) {
				String dependency = dependenciesJSONArray.getString(j);

				int pos2 = dependency.indexOf('/');

				if (pos2 == -1) {
					continue;
				}

				String dependencyName = dependency.substring(0, pos2);
				String dependencyPath = dependency.substring(pos2);

				if (dependencyName.equals(getName())) {
					dependencyName = StringBundler.concat(
						getName(), StringPool.AT, getVersion(), dependencyPath);

					dependenciesJSONArray.put(j, dependencyName);
				}
				else {
					normalizeDependencies(
						dependencyName, dependencyPath, dependenciesJSONArray,
						j, bundleWires);
				}
			}

			jsonObject.put(name, nameJSONObject);
		}

		return jsonObject.toString();
	}

	protected String normalize(String jsonString) {
		if (jsonString.startsWith(StringPool.OPEN_CURLY_BRACE) &&
			jsonString.endsWith(StringPool.CLOSE_CURLY_BRACE)) {

			jsonString = jsonString.substring(1, jsonString.length() - 1);
		}

		return jsonString;
	}

	protected void normalizeDependencies(
		String dependencyName, String dependencyPath, JSONArray jsonArray,
		int index, List<BundleWire> bundleWires) {

		for (BundleWire bundleWire : bundleWires) {
			BundleCapability bundleCapability = bundleWire.getCapability();

			Map<String, Object> attributes = bundleCapability.getAttributes();

			String attributesDependencyName = (String)attributes.get(
				Details.OSGI_WEBRESOURCE);

			if (!attributesDependencyName.equals(dependencyName)) {
				continue;
			}

			Version version = (Version)attributes.get(
				Constants.VERSION_ATTRIBUTE);

			dependencyName = StringBundler.concat(
				dependencyName, StringPool.AT, version.toString(),
				dependencyPath);

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

			_configuration = normalize(
				generateConfiguration(jsonObject, bundleWiring));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private JSONObject _parse(String json, boolean addBraces) {
		if (addBraces) {
			json = StringBundler.concat(
				StringPool.OPEN_CURLY_BRACE, json,
				StringPool.CLOSE_CURLY_BRACE);
		}

		JSONTokener jsonTokener = new JSONTokener(new StringReader(json));

		return new JSONObject(jsonTokener);
	}

	private void _setJSConfigGeneratorModules() {
		JSONObject jsonObject = _parse(_configuration, true);

		for (Object key : jsonObject.keySet()) {
			String name = (String)key;

			JSONObject moduleJSONObject = jsonObject.getJSONObject(name);

			JSONArray dependenciesJSONArray = moduleJSONObject.getJSONArray(
				"dependencies");

			List<String> dependencies = new ArrayList<>();

			for (int i = 0; i < dependenciesJSONArray.length(); i++) {
				dependencies.add((String)dependenciesJSONArray.get(i));
			}

			_jsConfigGeneratorModules.add(
				new JSConfigGeneratorModule(
					this, ModuleNameUtil.getPackagePath(name), dependencies,
					_contextPath));
		}
	}

	private final boolean _applyVersioning;
	private String _configuration = "";
	private final String _contextPath;
	private List<JSConfigGeneratorModule> _jsConfigGeneratorModules =
		new ArrayList<>();
	private final String _name;
	private final String _version;

}