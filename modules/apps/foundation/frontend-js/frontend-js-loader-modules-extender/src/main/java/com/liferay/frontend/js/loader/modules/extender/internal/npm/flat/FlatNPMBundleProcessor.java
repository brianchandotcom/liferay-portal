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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.flat;

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundleProcessor;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URL;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = JSBundleProcessor.class)
public class FlatNPMBundleProcessor implements JSBundleProcessor {

	public static final String DEPENDENCIES = "dependencies";

	public static final String MAIN = "main";

	public static final String NAME = "name";

	public static final String PEER_DEPENDENCIES = "peerDependencies";

	public static final String VERSION = "version";

	@Override
	public JSBundle process(Bundle bundle) {
		URL url = bundle.getResource("META-INF/resources/package.json");

		if (url == null) {
			return null;
		}

		FlatJSBundle flatJSBundle = new FlatJSBundle(bundle);

		if (_log.isInfoEnabled()) {
			_log.info("Processing NPM bundle: " + flatJSBundle);
		}

		_processRootPackage(flatJSBundle);

		_processNodePackages(flatJSBundle);

		return flatJSBundle;
	}

	private String _getResourceContent(
		FlatJSBundle flatJSBundle, String location) {

		URL url = flatJSBundle.getResourceURL(location);

		if (url == null) {
			return null;
		}

		try {
			return StringUtil.read(url.openStream());
		}
		catch (IOException ioe) {
			return null;
		}
	}

	private Collection<String> _parseModuleDependencies(URL url)
		throws IOException {

		String urlContent = StringUtil.read(url.openStream());

		Matcher matcher = _MODULE_DEFINITION_PATTERN.matcher(urlContent);

		if (!matcher.lookingAt()) {
			return Collections.emptyList();
		}

		String[] dependencies = matcher.group(1).split(",");

		for (int i = 0; i < dependencies.length; i++) {
			dependencies[i] = dependencies[i].trim();
			dependencies[i] = dependencies[i].replaceAll("'", "");
			dependencies[i] = dependencies[i].replaceAll("\"", "");
		}

		return Arrays.asList(dependencies);
	}

	private void _processDependencies(
		FlatJSPackage flatJSPackage, JSONObject jsonObject, String key) {

		JSONObject dependencies = jsonObject.getJSONObject(key);

		if (dependencies != null) {
			Iterator<String> dependencyNames = dependencies.keys();

			while (dependencyNames.hasNext()) {
				String dependencyName = dependencyNames.next();

				String versionConstraints = dependencies.getString(
					dependencyName);

				flatJSPackage.addJSPackageDependency(
					new JSPackageDependency(
						flatJSPackage, dependencyName, versionConstraints));
			}
		}
	}

	/**
	 * @param flatJSPackage
	 * @param location the bundle relative path of the package folder
	 * @return
	 */
	private void _processModules(FlatJSPackage flatJSPackage, String location) {
		FlatJSBundle flatJSBundle = flatJSPackage.getJSBundle();

		String nodeModulesPath = StringPool.SLASH + location + "/node_modules/";

		Enumeration<URL> urls = flatJSBundle.findEntries(
			location, "*.js", true);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			String path = url.getPath();

			if (path.startsWith(nodeModulesPath)) {
				continue;
			}

			String name = path.substring(location.length() + 2);

			name = ModuleNameUtil.toModuleName(name);

			Collection<String> dependencies;

			try {
				dependencies = _parseModuleDependencies(url);
			}
			catch (IOException ioe) {
				_log.error("Unable to read URL: " + url, ioe);

				continue;
			}

			FlatJSModule flatJSModule = new FlatJSModule(
				flatJSPackage, name, dependencies);

			if (_log.isDebugEnabled()) {
				_log.debug("Adding NPM module: " + flatJSModule);
			}

			flatJSPackage.addJSModule(flatJSModule);
		}
	}

	private void _processNodePackages(FlatJSBundle flatJSBundle) {
		Enumeration<URL> urls = flatJSBundle.findEntries(
			"META-INF/resources", "package.json", true);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			String path = url.getPath();

			if (path.equals("/META-INF/resources/package.json")) {
				continue;
			}

			String location = path.substring(1, path.length() - 13);

			_processPackage(flatJSBundle, location, false);
		}
	}

	/**
	 * @param flatJSBundle
	 * @param location the bundle relative path to a package.json file
	 * @return
	 */
	private void _processPackage(
		FlatJSBundle flatJSBundle, String location, boolean root) {

		JSONObject jsonObject = null;

		String packageJsonLocation = location + "/package.json";

		try {
			jsonObject = JSONFactoryUtil.createJSONObject(
				_getResourceContent(flatJSBundle, packageJsonLocation));
		}
		catch (JSONException jsone) {
			_log.error(
				"Unable to parse package of " + flatJSBundle + ": " +
					packageJsonLocation,
				jsone);

			return;
		}

		String main = jsonObject.getString(MAIN);
		String name = jsonObject.getString(NAME);
		String version = jsonObject.getString(VERSION);

		String mainModuleName;

		if (Validator.isNull(main)) {
			mainModuleName = "index";
		}
		else {
			mainModuleName = ModuleNameUtil.toModuleName(main);
		}

		FlatJSPackage flatJSPackage = new FlatJSPackage(
			flatJSBundle, name, version, mainModuleName, root);

		if (_log.isInfoEnabled()) {
			_log.info("Adding NPM package: " + flatJSPackage);
		}

		_processDependencies(flatJSPackage, jsonObject, DEPENDENCIES);

		_processDependencies(flatJSPackage, jsonObject, PEER_DEPENDENCIES);

		_processModules(flatJSPackage, location);

		flatJSBundle.addJSPackage(flatJSPackage);
	}

	private void _processRootPackage(FlatJSBundle flatJSBundle) {
		String location = "META-INF/resources";

		_processPackage(flatJSBundle, location, true);
	}

	private static final Pattern _MODULE_DEFINITION_PATTERN = Pattern.compile(
		"Liferay\\.Loader\\.define.*\\[(.*)\\].*function", Pattern.MULTILINE);

	private static final Log _log = LogFactoryUtil.getLog(
		FlatNPMBundleProcessor.class);

}