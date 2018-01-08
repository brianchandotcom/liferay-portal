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
import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides an implementation of {@link JSBundleProcessor} that assumes the
 * <code>flat</code> format for the OSGi bundles containing NPM packages.
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

	private String _canonicalizePath(String path) {
		int parents = 0;
		String[] parts = path.split(StringPool.SLASH);
		List<String> processedParts = new ArrayList<>();

		for (int i = parts.length - 1; i >= 0; i--) {
			String part = parts[i];

			if (part.equals(".")) {
				continue;
			}
			else if (part.equals("..")) {
				parents++;
			}
			else {
				if (parents > 0) {
					parents--;
				}
				else {
					processedParts.add(part);
				}
			}
		}

		Collections.reverse(processedParts);

		StringBundler sb = new StringBundler(2 * processedParts.size() - 1);

		for (String processedPart : processedParts) {
			if (sb.length() != 0) {
				sb.append(StringPool.SLASH);
			}

			sb.append(processedPart);
		}

		return sb.toString();
	}

	private JSONObject _getJSONObject(
		FlatJSBundle flatJSBundle, String location) {

		JSONObject jsonObject = null;

		try {
			String content = _getResourceContent(flatJSBundle, location);

			if (content != null) {
				jsonObject = _jsonFactory.createJSONObject(content);
			}
		}
		catch (Exception e) {
			_log.error(
				StringBundler.concat(
					"Unable to parse ", String.valueOf(flatJSBundle), ": ",
					location),
				e);
		}

		return jsonObject;
	}

	/**
	 * Returns the contents of a resource inside a {@link FlatJSBundle}.
	 *
	 * @param  flatJSBundle the bundle
	 * @param  location the resource's path
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

	private String _normalizeModuleContent(String moduleContent) {
		moduleContent = moduleContent.replaceAll("\n", " ");

		int index = moduleContent.indexOf("Liferay.Loader.define(");

		if (index == -1) {
			return StringPool.BLANK;
		}

		moduleContent = moduleContent.substring(index);

		index = moduleContent.indexOf("function");

		if (index == -1) {
			return StringPool.BLANK;
		}

		return moduleContent.substring(0, index);
	}

	/**
	 * Returns the dependencies of a module given its URL. The dependencies are
	 * parsed by reading the module's JavaScript code.
	 *
	 * @param  url the {@link URL} of the module
	 * @return the dependencies of the module
	 */
	private Collection<String> _parseModuleDependencies(URL url)
		throws IOException {

		String urlContent = _normalizeModuleContent(
			StringUtil.read(url.openStream()));

		Matcher matcher = _moduleDefinitionPattern.matcher(urlContent);

		if (!matcher.find()) {
			return Collections.emptyList();
		}

		String group = matcher.group(1);

		String[] dependencies = group.split(",");

		if ((dependencies.length == 1) && dependencies[0].equals("")) {
			return Collections.emptyList();
		}

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

		JSONObject dependenciesJSONObject = jsonObject.getJSONObject(key);

		if (dependenciesJSONObject != null) {
			Iterator<String> dependencyNames = dependenciesJSONObject.keys();

			while (dependencyNames.hasNext()) {
				String dependencyName = dependencyNames.next();

				String versionConstraints = dependenciesJSONObject.getString(
					dependencyName);

				flatJSPackage.addJSPackageDependency(
					new JSPackageDependency(
						flatJSPackage, dependencyName, versionConstraints));
			}
		}
	}

	private void _processModuleAliases(
		FlatJSPackage flatJSPackage, String location) {

		Set<String> processedFolderPaths = new HashSet<>();

		FlatJSBundle flatJSBundle = flatJSPackage.getJSBundle();

		Enumeration<URL> urls = flatJSBundle.findEntries(
			location, "package.json", true);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			String filePath = url.getPath();

			filePath = filePath.substring(location.length() + 1);

			if (filePath.equals("/package.json")) {
				continue;
			}

			JSONObject jsonObject = _getJSONObject(flatJSBundle, url.getPath());

			if (jsonObject == null) {
				continue;
			}

			String main = jsonObject.getString("main");

			if (main != null) {
				String folderPath = filePath.substring(
					0, filePath.lastIndexOf(StringPool.SLASH));

				String alias = folderPath.substring(1);

				if (main.startsWith(StringPool.PERIOD)) {
					main = _canonicalizePath(alias + StringPool.SLASH + main);
				}

				main = ModuleNameUtil.toModuleName(main);

				JSModuleAlias jsModuleAlias = new JSModuleAlias(
					flatJSPackage, main, alias);

				flatJSPackage.addJSModuleAlias(jsModuleAlias);

				processedFolderPaths.add(folderPath);
			}
		}

		urls = flatJSBundle.findEntries(location, "index.js", true);

		if (urls != null) {
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();

				String folderPath = url.getPath();

				folderPath = folderPath.substring(location.length() + 1);

				folderPath = folderPath.substring(
					0, folderPath.lastIndexOf(StringPool.SLASH));

				if (folderPath.isEmpty()) {
					continue;
				}

				String alias = folderPath.substring(1);

				if (!processedFolderPaths.contains(folderPath)) {
					flatJSPackage.addJSModuleAlias(
						new JSModuleAlias(
							flatJSPackage, alias + "/index", alias));
				}
			}
		}
	}

	/**
	 * Processes the modules of a package and adds them to their {@link
	 * FlatJSPackage} descriptor.
	 *
	 * @param flatJSPackage the NPM package descriptor
	 * @param location the bundle's relative path of the package folder
	 */
	private void _processModules(FlatJSPackage flatJSPackage, String location) {
		String nodeModulesPath = StringPool.SLASH + location + "/node_modules/";

		FlatJSBundle flatJSBundle = flatJSPackage.getJSBundle();

		Enumeration<URL> urls = flatJSBundle.findEntries(
			location, "*.js", true);

		if (urls == null) {
			return;
		}

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

			String[] parts = location.split(StringPool.SLASH);

			String lastFolderPath = parts[parts.length - 2];

			if (lastFolderPath.equals("node_modules")) {
				_processPackage(flatJSBundle, location, false);
			}
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
			jsonObject = _jsonFactory.createJSONObject(
				_getResourceContent(flatJSBundle, location + "/package.json"));
		}
		catch (Exception e) {
			_log.error(
				StringBundler.concat(
					"Unable to parse package of ", String.valueOf(flatJSBundle),
					": ", location, "/package.json"),
				e);

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

		if (!root) {
			_processModuleAliases(flatJSPackage, location);
		}

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
		"Liferay\\.Loader\\.define.*\\[(.*)\\].*");

	@Reference
	private JSONFactory _jsonFactory;

}