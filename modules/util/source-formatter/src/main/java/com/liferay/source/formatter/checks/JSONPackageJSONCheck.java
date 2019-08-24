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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.FileUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Alan Huang
 * @author Hugo Huijser
 */
public class JSONPackageJSONCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/package.json") ||
			(!absolutePath.contains("/modules/apps/") &&
			 !absolutePath.contains("/modules/private/apps/"))) {

			return content;
		}

		JSONObject jsonObject = new JSONObject(content);

		content = _fixDependencyVersions(
			fileName, absolutePath, content, jsonObject);

		String dirName = absolutePath.substring(0, absolutePath.length() - 12);

		if (!FileUtil.exists(dirName + "build.gradle") &&
			!FileUtil.exists(dirName + "bnd.bnd")) {

			return content;
		}

		_checkIncorrectEntry(fileName, jsonObject, "devDependencies");

		if (jsonObject.isNull("scripts")) {
			return content;
		}

		JSONObject scriptsJSONObject = jsonObject.getJSONObject("scripts");

		if (absolutePath.contains("/modules/apps/frontend-theme")) {
			_checkScript(
				fileName, scriptsJSONObject, "build",
				"liferay-npm-scripts theme build", false);
		}
		else {
			_checkScript(
				fileName, scriptsJSONObject, "build",
				"liferay-npm-scripts build", false);
		}

		_checkScript(
			fileName, scriptsJSONObject, "checkFormat",
			"liferay-npm-scripts check", true);
		_checkScript(
			fileName, scriptsJSONObject, "format", "liferay-npm-scripts fix",
			true);

		return content;
	}

	private void _checkIncorrectEntry(
		String fileName, JSONObject jsonObject, String entryName) {

		if (!jsonObject.isNull(entryName)) {
			addMessage(fileName, "Entry '" + entryName + "' is not allowed");
		}
	}

	private void _checkScript(
		String fileName, JSONObject scriptsJSONObject, String key,
		String expectedValue, boolean requiredScript) {

		if (scriptsJSONObject.isNull(key)) {
			if (requiredScript) {
				addMessage(
					fileName, "Missing entry '" + key + "' in 'scripts'");
			}

			return;
		}

		String value = scriptsJSONObject.getString(key);

		if (!value.contains(expectedValue)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Value '", value, "' for entry '", key,
					"' does not contain '", expectedValue, "'"));
		}
	}

	private String _fixDependencyVersions(
			String fileName, String absolutePath, String content,
			JSONObject jsonObject)
		throws IOException {

		if (jsonObject.isNull("dependencies")) {
			return content;
		}

		Map<String, String> expectedDependencyVersionsMap =
			_getExpectedDependencyVersionsMap(absolutePath);

		JSONObject dependenciesJSONObject = jsonObject.getJSONObject(
			"dependencies");

		List<String> enforceMinorReleaseRangePackageNames = getAttributeValues(
			_ENFORCE_MINOR_RELEASE_RANGE_DEPENDENCY_NAMES, absolutePath);

		Iterator<String> keys = dependenciesJSONObject.keys();

		while (keys.hasNext()) {
			String dependencyName = keys.next();

			String actualVersion = dependenciesJSONObject.getString(
				dependencyName);
			String expectedVersion = expectedDependencyVersionsMap.get(
				dependencyName);

			if ((expectedVersion != null) &&
				!expectedVersion.equals(actualVersion)) {

				content = StringUtil.replace(
					content,
					StringBundler.concat(
						"\"", dependencyName, "\": \"", actualVersion, "\""),
					StringBundler.concat(
						"\"", dependencyName, "\": \"", expectedVersion, "\""));
			}

			if (!actualVersion.startsWith(StringPool.CARET) &&
				enforceMinorReleaseRangePackageNames.contains(dependencyName)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Version for '", dependencyName,
						"' should start with '^'"));
			}
		}

		return content;
	}

	private Map<String, String> _getDependencyVersionsMap(
			String fileName, String absolutePath, String regex)
		throws IOException {

		Map<String, String> dependencyVersionsMap = new HashMap<>();

		String content = getPortalContent(fileName, absolutePath);

		if (Validator.isNull(content)) {
			return dependencyVersionsMap;
		}

		JSONObject jsonObject = new JSONObject(content);

		JSONObject dependenciesJSONObject = jsonObject.getJSONObject(
			"dependencies");

		Iterator<String> keys = dependenciesJSONObject.keys();

		while (keys.hasNext()) {
			String dependencyName = keys.next();

			if (dependencyName.matches(regex)) {
				dependencyVersionsMap.put(
					dependencyName,
					dependenciesJSONObject.getString(dependencyName));
			}
		}

		return dependencyVersionsMap;
	}

	private synchronized Map<String, String> _getExpectedDependencyVersionsMap(
			String absolutePath)
		throws IOException {

		if (_expectedDependencyVersionsMap != null) {
			return _expectedDependencyVersionsMap;
		}

		_expectedDependencyVersionsMap = new HashMap<>();

		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-js/frontend-js-metal-web/package.json",
				absolutePath, "metal(-.*)?"));
		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-js/frontend-js-react-web/package.json",
				absolutePath, ".*"));
		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-js/frontend-js-spa-web/package.json",
				absolutePath, "senna"));
		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-taglib/frontend-taglib-clay" +
					"/package.json",
				absolutePath, "clay-.*"));
		_expectedDependencyVersionsMap.putAll(
			_getDependencyVersionsMap(
				"modules/apps/frontend-taglib/frontend-taglib-clay" +
					"/package.json",
				absolutePath, "@clayui/.*"));

		for (String workspace : _INTERNAL_LINKED_WORKSPACES_) {
			_expectedDependencyVersionsMap.put(workspace, "*");
		}

		return _expectedDependencyVersionsMap;
	}

	private static final String _ENFORCE_MINOR_RELEASE_RANGE_DEPENDENCY_NAMES =
		"enforceMinorReleaseRangeDependencyNames";

	private static final String[] _INTERNAL_LINKED_WORKSPACES_ = {
		"adaptive-media-web",
		"analytics-client-js",
		"app-builder-web",
		"asset-categories-admin-web",
		"asset-categories-selector-web",
		"asset-list-web",
		"asset-publisher-web",
		"asset-taglib",
		"asset-tags-admin-web",
		"blogs-web",
		"bookmarks-web",
		"calendar-web",
		"change-tracking-change-lists-configuration-web",
		"change-tracking-change-lists-history-web",
		"change-tracking-change-lists-indicator-web",
		"change-tracking-change-lists-web",
		"data-engine-rest-impl",
		"data-engine-taglib",
		"document-library-preview-audio",
		"document-library-preview-document",
		"document-library-preview-image",
		"document-library-preview-video",
		"document-library-web",
		"dynamic-data-mapping-form-builder",
		"dynamic-data-mapping-form-field-type",
		"dynamic-data-mapping-form-renderer",
		"dynamic-data-mapping-form-web",
		"dynamic-data-mapping-web",
		"export-import-web",
		"flags-taglib",
		"fragment-web",
		"frontend-editor-alloyeditor-web",
		"frontend-editor-ckeditor-web",
		"frontend-image-editor-capability-brightness",
		"frontend-image-editor-capability-contrast",
		"frontend-image-editor-capability-crop",
		"frontend-image-editor-capability-effects",
		"frontend-image-editor-capability-resize",
		"frontend-image-editor-capability-rotate",
		"frontend-image-editor-capability-saturation",
		"frontend-image-editor-web",
		"frontend-js-aui-web",
		"frontend-js-jquery-web",
		"frontend-js-lodash-web",
		"frontend-js-metal-web",
		"frontend-js-node-shims",
		"frontend-js-react-web",
		"frontend-js-spa-web",
		"frontend-js-web",
		"frontend-taglib-chart",
		"frontend-taglib-clay",
		"frontend-taglib",
		"frontend-theme-admin",
		"frontend-theme-classic",
		"frontend-theme-fjord",
		"frontend-theme-font-awesome-web",
		"frontend-theme-porygon",
		"frontend-theme-unstyled",
		"frontend-theme-westeros-bank",
		"hello-soy-navigation-web",
		"hello-soy-web",
		"invitation-invite-members-web",
		"item-selector-taglib",
		"journal-web",
		"layout-admin-web",
		"layout-content-page-editor-web",
		"layout-taglib",
		"map-common",
		"map-google-maps",
		"map-openstreetmap",
		"message-boards-web",
		"portal-portlet-bridge-soy-impl",
		"portal-search-admin-web",
		"portal-search-web",
		"portal-workflow-task-web",
		"portlet-configuration-css-web",
		"portlet-configuration-web",
		"product-navigation-control-menu-web",
		"product-navigation-simulation-device",
		"product-navigation-taglib",
		"segments-experiment-web",
		"segments-simulation-web",
		"segments-web",
		"sharing-web",
		"site-admin-web",
		"site-memberships-web",
		"site-my-sites-web",
		"site-navigation-admin-web",
		"site-navigation-item-selector-web",
		"site-teams-web",
		"staging-processes-web",
		"staging-taglib",
		"trash-web",
		"users-admin-web",
		"wiki-web"
	};

	private Map<String, String> _expectedDependencyVersionsMap;

}