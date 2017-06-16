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
 * Provides an implementation of {@link
 * com.liferay.frontend.js.loader.modules.extender.npm.JSBundleProcessor} that
 * assumes the <code>flat</code> format for the OSGi bundles containing NPM
 * packages.
 *
 * <p>
 * See this package's summary for an explanation of the <code>flat</code>
 * format.
 * </p>
 *
 * @author Iván Zaera
 */
@Component(immediate = true, service = JSBundleProcessor.class)
public class FlatNPMBundleProcessor implements JSBundleProcessor {

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

	/**
	 * Returns the contents of a resource inside a {@link
	 * com.liferay.frontend.js.loader.modules.extender.internal.npm.flat.FlatJSBundle}.
	 *
	 * @param flatJSBundle the bundle
	 * @param location the resource's path
	 * @return the contents of the resource as a String
	 */
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

	/**
	 * Returns the dependencies of a module given its URL. The dependencies are
	 * parsed by reading the module's JavaScript code.
	 *
	 * @param url the {@link URL} of the module
	 * @return the dependencies of the module
	 */
	private Collection<String> _parseModuleDependencies(URL url)
		throws IOException {

		String urlContent = StringUtil.read(url.openStream());

		Matcher matcher = _moduleDefinitionPattern.matcher(urlContent);

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

	/**
	 * Processes the <code>dependencies</code> type entry of a
	 * <code>package.json</code> file and adds them to the {@link
	 * FlatJSPackage}.
	 *
	 * @param flatJSPackage the NPM package descriptor
	 * @param jsonObject the parsed <code>package.json</code>
	 * @param key the key of the <code>dependencies</code> type property
	 */
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
	 * Processes the modules of a package and adds them to their
	 * {@link FlatJSPackage} descriptor.
	 *
	 * @param flatJSPackage the NPM package descriptor
	 * @param location the bundle's relative path of the package folder
	 */
	private void _processModules(FlatJSPackage flatJSPackage, String location) {
		String nodeModulesPath = StringPool.SLASH + location + "/node_modules/";

		FlatJSBundle flatJSBundle = flatJSPackage.getJSBundle();

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

			Collection<String> dependencies = null;

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

	/**
	 * Processes the bundle's packages and adds them to their {@link
	 * FlatJSBundle} descriptor.
	 *
	 * @param flatJSBundle the bundle containing the node packages
	 */
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
	 * Processes a the bundle's package and adds it to its {@link FlatJSBundle}
	 * descriptor.
	 *
	 * @param flatJSBundle the bundle containing the package
	 * @param location the bundle's relative path to a <code>package.json</code>
	 *        file
	 */
	private void _processPackage(
		FlatJSBundle flatJSBundle, String location, boolean root) {

		JSONObject jsonObject = null;

		try {
			jsonObject = JSONFactoryUtil.createJSONObject(
				_getResourceContent(flatJSBundle, location + "/package.json"));
		}
		catch (JSONException jsone) {
			_log.error(
				"Unable to parse package of " + flatJSBundle + ": " + location +
					"/package.json",
				jsone);

			return;
		}

		String mainModuleName = null;

		String main = jsonObject.getString("main");

		if (Validator.isNull(main)) {
			mainModuleName = "index";
		}
		else {
			mainModuleName = ModuleNameUtil.toModuleName(main);

			if (mainModuleName.startsWith("./")) {
				mainModuleName = mainModuleName.substring(2);
			}
		}

		FlatJSPackage flatJSPackage = new FlatJSPackage(
			flatJSBundle, jsonObject.getString("name"),
			jsonObject.getString("version"), mainModuleName, root);

		if (_log.isInfoEnabled()) {
			_log.info("Adding NPM package: " + flatJSPackage);
		}

		_processDependencies(flatJSPackage, jsonObject, "dependencies");

		_processDependencies(flatJSPackage, jsonObject, "peerDependencies");

		_processModules(flatJSPackage, location);

		flatJSBundle.addJSPackage(flatJSPackage);
	}

	/**
	 * Processes the root package (i.e., the package located in the bundle's
	 * <code>META-INF/resources</code> folder, as opposed to the
	 * <code>node_modules</code> folder) of a bundle and adds it to its {@link
	 * FlatJSBundle} descriptor.
	 *
	 * @param flatJSBundle the bundle containing the root package
	 */
	private void _processRootPackage(FlatJSBundle flatJSBundle) {
		String location = "META-INF/resources";

		_processPackage(flatJSBundle, location, true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FlatNPMBundleProcessor.class);

	private static final Pattern _moduleDefinitionPattern = Pattern.compile(
		"Liferay\\.Loader\\.define.*\\[(.*)\\].*function", Pattern.MULTILINE);

}