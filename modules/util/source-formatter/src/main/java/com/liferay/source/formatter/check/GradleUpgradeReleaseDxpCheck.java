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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.upgrade.GradleBuildFile;
import com.liferay.source.formatter.upgrade.GradleDependency;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Kevin Lee
 */
public class GradleUpgradeReleaseDxpCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION, absolutePath);

		if (Validator.isNull(upgradeToVersion)) {
			return content;
		}

		return _formatDependencies(content, upgradeToVersion);
	}

	private String _formatDependencies(
		String content, String upgradeToVersion) {

		Map<String, Set<String>> releaseDXPDependencies =
			_getReleaseDXPDependencies(upgradeToVersion);

		if (releaseDXPDependencies == null) {
			return content;
		}

		GradleBuildFile buildFile = new GradleBuildFile(content);

		List<GradleDependency> dependencies = buildFile.getDependencies();

		Iterator<GradleDependency> iterator = dependencies.iterator();

		boolean hasCompile = false;
		boolean hasTest = false;

		while (iterator.hasNext()) {
			GradleDependency dependency = iterator.next();

			Set<String> names = releaseDXPDependencies.get(
				dependency.getGroup());

			if ((names != null) && names.contains(dependency.getName())) {
				continue;
			}

			iterator.remove();

			String configuration = dependency.getConfiguration();

			if (configuration.startsWith("compile")) {
				hasCompile = true;
			}

			if (configuration.startsWith("test")) {
				hasTest = true;
			}
		}

		if (dependencies.isEmpty()) {
			return content;
		}

		buildFile.deleteDependencies(dependencies);

		if (hasCompile) {
			buildFile.insertDependency(
				"compileOnly", "com.liferay.portal", "release.dxp.api",
				upgradeToVersion);
		}

		if (hasTest) {
			buildFile.insertDependency(
				"testCompile", "com.liferay.portal", "release.dxp.api",
				upgradeToVersion);
		}

		return buildFile.getSource();
	}

	private Map<String, Set<String>> _getReleaseDXPDependencies(
		String upgradeToVersion) {

		if (Objects.equals(upgradeToVersion, _upgradeToVersion)) {
			return _releaseDXPDependencies;
		}

		try {
			URL url = new URL(_getReleaseDXPPomURL(upgradeToVersion));

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)url.openConnection();

			httpURLConnection.setConnectTimeout(10000);
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.setRequestMethod(HttpMethods.GET);

			String content = StringUtil.read(
				httpURLConnection.getInputStream());

			if (Objects.equals(content, StringPool.BLANK)) {
				return null;
			}

			_upgradeToVersion = upgradeToVersion;
			_releaseDXPDependencies = _readReleaseDXPDependencies(content);
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}

		return _releaseDXPDependencies;
	}

	private String _getReleaseDXPPomURL(String upgradeToVersion) {
		String baseURL =
			"https://repository-cdn.liferay.com/nexus/content/repositories" +
				"/liferay-public-releases/com/liferay/portal/release.dxp.bom/";

		return StringBundler.concat(
			baseURL, upgradeToVersion, "/release.dxp.bom-", upgradeToVersion,
			".pom");
	}

	private Map<String, Set<String>> _readReleaseDXPDependencies(String content)
		throws DocumentException {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		Element dependencyManagementElement = rootElement.element(
			"dependencyManagement");

		Element dependenciesElement = dependencyManagementElement.element(
			"dependencies");

		List<Element> dependencyElements = dependenciesElement.elements(
			"dependency");

		Map<String, Set<String>> dependencies = new HashMap<>();

		for (Element dependencyElement : dependencyElements) {
			Element groupIdElement = dependencyElement.element("groupId");
			Element artifactIdElement = dependencyElement.element("artifactId");

			String groupId = groupIdElement.getText();
			String artifactId = artifactIdElement.getText();

			dependencies.putIfAbsent(groupId, new HashSet<>());

			Set<String> artifactIds = dependencies.get(groupId);

			artifactIds.add(artifactId);
		}

		return dependencies;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GradleUpgradeReleaseDxpCheck.class);

	private Map<String, Set<String>> _releaseDXPDependencies;
	private String _upgradeToVersion;

}