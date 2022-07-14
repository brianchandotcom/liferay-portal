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
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Qi Zhang
 */
public class LibraryVersionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException {

		_pageNumber = GetterUtil.getInteger(
			getAttributeValue(_QUERY_ARGUMENTS_PAGE_NUMBER, absolutePath));
		_severities = getAttributeValues(
			_QUERY_ARGUMENTS_SEVERITIES, absolutePath);

		if (fileName.endsWith(".gradle")) {
			_gradleLibraryVersionCheck(fileName, content);
		}
		else if (fileName.endsWith(".json")) {
			_jsonLibraryVersionCheck(fileName, content);
		}
		else if (fileName.endsWith(".properties")) {
			_propertiesLibraryVersionCheck(fileName, content);
		}
		else if (fileName.endsWith("ivy.xml")) {
			_ivyXmlLibraryVersionCheck(fileName, content);
		}
		else if (fileName.endsWith("pom.xml")) {
			_pomXmlLibraryVersionCheck(fileName, content);
		}

		return content;
	}

	private void _checkIsContainVulnerabilities(
		String fileName, String packageName, DefaultArtifactVersion version) {

		List<SecurityVulnerabilityNode> securityVulnerabilityNodes =
			_vulnerableVersionMap.get(packageName);

		for (SecurityVulnerabilityNode securityVulnerabilityNode :
				securityVulnerabilityNodes) {

			VersionRange versionRange =
				securityVulnerabilityNode.getVersionRange();

			if (versionRange.containsVersion(version)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Library '", packageName, "' ", version.toString(),
						" contain vulnerabilities by '",
						securityVulnerabilityNode.getSummary(),
						"', look detail in ",
						securityVulnerabilityNode.getPermalink()));

				return;
			}
		}
	}

	private void _checkIsContainVulnerabilities(
			String fileName, String packageName, String version,
			SecurityAdvisoryEcosystemEnum securityAdvisoryEcosystemEnum)
		throws IOException {

		if (!version.matches("(\\d|v).+")) {
			return;
		}

		if (!_vulnerableVersionMap.containsKey(packageName)) {
			_generateVulnerableVersionMap(
				packageName, securityAdvisoryEcosystemEnum);
		}

		_checkIsContainVulnerabilities(
			fileName, packageName, new DefaultArtifactVersion(version));
	}

	private void _checkVersionInJsonFile(String fileName, JSONObject jsonObject)
		throws IOException {

		if (jsonObject == null) {
			return;
		}

		for (String dependencyName : jsonObject.keySet()) {
			String version = jsonObject.getString(dependencyName);

			if (version.startsWith("^") || version.startsWith("~") ||
				version.startsWith("*")) {

				continue;
			}

			_checkIsContainVulnerabilities(
				fileName, dependencyName, version,
				SecurityAdvisoryEcosystemEnum.NPM);
		}
	}

	private void _generateVulnerableVersionMap(
			String packageName,
			SecurityAdvisoryEcosystemEnum securityAdvisoryEcosystemEnum)
		throws IOException {

		if (_vulnerableVersionMap.containsKey(packageName)) {
			return;
		}

		_vulnerableVersionMap.put(
			packageName,
			_getSecurityVulnerabilityNodes(
				packageName, null, securityAdvisoryEcosystemEnum));
	}

	private String _getContentByPattern(String content, Pattern pattern) {
		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private List<SecurityVulnerabilityNode> _getSecurityVulnerabilityNodes(
			String packageName, String cursor,
			SecurityAdvisoryEcosystemEnum securityAdvisoryEcosystemEnum)
		throws IOException {

		if (_pageNumber == 0) {
			return Collections.emptyList();
		}

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			HttpPost httpPost = new HttpPost("https://api.github.com/graphql");

			httpPost.addHeader(
				"Authorization",
				"bearer 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");
			httpPost.addHeader(
				"Content-Type",
				"application/json; charset=utf-8; application/graphql");

			String queryArguments = StringBundler.concat(
				"first: ", _pageNumber, ", package:\\\"", packageName,
				"\\\", ecosystem: ", securityAdvisoryEcosystemEnum.name());

			if (ListUtil.isNotNull(_severities)) {
				queryArguments =
					queryArguments + ", severities: " + _severities;
			}

			if (Validator.isNotNull(cursor)) {
				queryArguments += "after: \\\"" + cursor + "\\\"";
			}

			String resultArguments =
				"{nodes { advisory {summary, permalink} package {name} " +
					"severity vulnerableVersionRange } pageInfo {endCursor " +
						"hasNextPage } totalCount }";

			httpPost.setEntity(
				new StringEntity(
					StringBundler.concat(
						"{\"query\": \"{ securityVulnerabilities(",
						queryArguments, ") ", resultArguments, "}\" }"),
					ContentType.APPLICATION_JSON));

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject jsonObject = new JSONObjectImpl(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(), "UTF-8"));

				JSONObject dataJSONObject = jsonObject.getJSONObject("data");

				JSONObject securityVulnerabilitiesJSONObject =
					dataJSONObject.getJSONObject("securityVulnerabilities");

				int totalCount = securityVulnerabilitiesJSONObject.getInt(
					"totalCount");

				if (totalCount == 0) {
					return Collections.emptyList();
				}

				List<SecurityVulnerabilityNode> securityVulnerabilityNodes =
					new ArrayList<>();

				JSONArray nodesJSONArray =
					securityVulnerabilitiesJSONObject.getJSONArray("nodes");

				Iterator<JSONObject> iterator = nodesJSONArray.iterator();

				while (iterator.hasNext()) {
					JSONObject nodeJSONObject = iterator.next();

					SecurityVulnerabilityNode securityVulnerabilityNode =
						new SecurityVulnerabilityNode();

					JSONObject advisoryJSONObject =
						nodeJSONObject.getJSONObject("advisory");

					securityVulnerabilityNode.setPermalink(
						advisoryJSONObject.getString("permalink"));
					securityVulnerabilityNode.setSummary(
						advisoryJSONObject.getString("summary"));

					securityVulnerabilityNode.setVersionRange(
						nodeJSONObject.getString("vulnerableVersionRange"));

					securityVulnerabilityNodes.add(securityVulnerabilityNode);
				}

				JSONObject pageInfoJSONObject =
					securityVulnerabilitiesJSONObject.getJSONObject("pageInfo");

				if (pageInfoJSONObject.getBoolean("hasNextPage")) {
					securityVulnerabilityNodes.addAll(
						_getSecurityVulnerabilityNodes(
							packageName,
							pageInfoJSONObject.getString("endCursor"),
							securityAdvisoryEcosystemEnum));
				}

				if (!securityVulnerabilityNodes.isEmpty()) {
					return securityVulnerabilityNodes;
				}
			}
		}
		catch (InvalidVersionSpecificationException
					invalidVersionSpecificationException) {

			if (_log.isDebugEnabled()) {
				_log.debug(invalidVersionSpecificationException);
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return Collections.emptyList();
	}

	private void _gradleLibraryVersionCheck(String fileName, String content)
		throws IOException {

		int x = content.indexOf("dependencies {");

		if (x == -1) {
			return;
		}

		String dependencies = null;

		int y = content.indexOf("}", x);

		while (true) {
			if (y == -1) {
				return;
			}

			dependencies = content.substring(x, y + 1);

			int level = getLevel(
				dependencies, StringPool.OPEN_CURLY_BRACE,
				StringPool.CLOSE_CURLY_BRACE);

			if (level == 0) {
				break;
			}

			y = content.indexOf("}", y + 1);
		}

		for (String dependency : dependencies.split(StringPool.NEW_LINE)) {
			dependency = dependency.trim();

			if (Validator.isNull(dependency) ||
				!dependency.matches(
					"(compile|compileInclude|compileOnly|classpath" +
						"|testCompile) .+")) {

				continue;
			}

			String group = _getContentByPattern(
				dependency, _gradleGroupPattern);
			String name = _getContentByPattern(dependency, _gradleNamePattern);
			String version = _getContentByPattern(
				dependency, _gradleVersionPattern);

			if (Validator.isNull(group) || Validator.isNull(name) ||
				Validator.isNull(version)) {

				continue;
			}

			_checkIsContainVulnerabilities(
				fileName, group + StringPool.COLON + name, version,
				SecurityAdvisoryEcosystemEnum.MAVEN);
		}
	}

	private void _ivyXmlLibraryVersionCheck(String fileName, String content)
		throws DocumentException, IOException {

		if (Validator.isNull(content)) {
			return;
		}

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		for (Element dependenciesElement :
				(List<Element>)rootElement.elements("dependencies")) {

			for (Element dependencyElement :
					(List<Element>)dependenciesElement.elements("dependency")) {

				String name = dependencyElement.attributeValue("name");

				if (Validator.isNull(name)) {
					continue;
				}

				String org = dependencyElement.attributeValue("org");

				if (Validator.isNull(org)) {
					continue;
				}

				String rev = dependencyElement.attributeValue("rev");

				if (Validator.isNull(rev)) {
					continue;
				}

				_checkIsContainVulnerabilities(
					fileName, org + StringPool.COLON + name, rev,
					SecurityAdvisoryEcosystemEnum.MAVEN);
			}
		}
	}

	private void _jsonLibraryVersionCheck(String fileName, String content)
		throws IOException {

		if (Validator.isNull(content)) {
			return;
		}

		try {
			JSONObject contentJSONObject = new JSONObjectImpl(content);

			_checkVersionInJsonFile(
				fileName, contentJSONObject.getJSONObject("dependencies"));

			_checkVersionInJsonFile(
				fileName, contentJSONObject.getJSONObject("devDependencies"));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}
	}

	private void _pomXmlLibraryVersionCheck(String fileName, String content)
		throws DocumentException, IOException {

		if (Validator.isNull(content)) {
			return;
		}

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		for (Element dependenciesElement :
				(List<Element>)rootElement.elements("dependencies")) {

			for (Element dependencyElement :
					(List<Element>)dependenciesElement.elements("dependency")) {

				Element artifactIdElement = dependencyElement.element(
					"artifactId");

				if (artifactIdElement == null) {
					continue;
				}

				Element groupIdElement = dependencyElement.element("groupId");

				if (groupIdElement == null) {
					continue;
				}

				Element versionElement = dependencyElement.element("version");

				if (versionElement == null) {
					continue;
				}

				String version = versionElement.getText();

				if (version.startsWith(StringPool.DOLLAR)) {
					continue;
				}

				_checkIsContainVulnerabilities(
					fileName,
					groupIdElement.getText() + StringPool.COLON +
						artifactIdElement.getText(),
					version, SecurityAdvisoryEcosystemEnum.MAVEN);
			}
		}
	}

	private void _propertiesLibraryVersionCheck(String fileName, String content)
		throws IOException {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			String value = properties.getProperty(key);

			if (Validator.isNull(value)) {
				continue;
			}

			String[] dependency = value.split(StringPool.COLON);

			if (dependency.length < 3) {
				continue;
			}

			_checkIsContainVulnerabilities(
				fileName, dependency[1], dependency[2],
				SecurityAdvisoryEcosystemEnum.MAVEN);
		}
	}

	private static final String _QUERY_ARGUMENTS_PAGE_NUMBER =
		"queryArgumentsPageNumber";

	private static final String _QUERY_ARGUMENTS_SEVERITIES =
		"queryArgumentsSeverities";

	private static final Log _log = LogFactoryUtil.getLog(
		LibraryVersionCheck.class);

	private static final Pattern _gradleGroupPattern = Pattern.compile(
		"group: \"([^,\n\\\\)]+)\"");
	private static final Pattern _gradleNamePattern = Pattern.compile(
		"name: \"([^,\n\\\\)]+)\"");
	private static final Pattern _gradleVersionPattern = Pattern.compile(
		"version: \"([^,\n\\\\)]+)\"");

	private int _pageNumber;
	private List<String> _severities;
	private final Map<String, List<SecurityVulnerabilityNode>>
		_vulnerableVersionMap = new ConcurrentHashMap<>();

	private static class SecurityVulnerabilityNode {

		public String getPermalink() {
			return _permalink;
		}

		public String getSummary() {
			return _summary;
		}

		public VersionRange getVersionRange() {
			return _versionRange;
		}

		public void setPermalink(String permalink) {
			_permalink = permalink;
		}

		public void setSummary(String summary) {
			_summary = summary;
		}

		public void setVersionRange(String vulnerableVersionRange)
			throws InvalidVersionSpecificationException {

			if (!vulnerableVersionRange.contains(StringPool.COMMA)) {
				String[] versionArray = vulnerableVersionRange.split(
					StringPool.SPACE, 2);

				if (versionArray[0].equals(StringPool.EQUAL)) {
					_versionRange = VersionRange.createFromVersion(
						versionArray[1]);
				}
				else if (versionArray[0].equals(StringPool.LESS_THAN)) {
					_versionRange = VersionRange.createFromVersionSpec(
						"(," + versionArray[1] + ")");
				}
				else if (versionArray[0].equals(
							StringPool.LESS_THAN_OR_EQUAL)) {

					_versionRange = VersionRange.createFromVersionSpec(
						"(," + versionArray[1] + "]");
				}
				else if (versionArray[0].equals(StringPool.GREATER_THAN)) {
					_versionRange = VersionRange.createFromVersionSpec(
						"(" + versionArray[1] + ",)");
				}
				else if (versionArray[0].equals(
							StringPool.GREATER_THAN_OR_EQUAL)) {

					_versionRange = VersionRange.createFromVersionSpec(
						"[" + versionArray[1] + ",)");
				}
			}
			else {
				vulnerableVersionRange = vulnerableVersionRange.replaceAll(
					"([=<>]+.+?, )([=<>]+)(.+)", "$1$3$2");

				vulnerableVersionRange = StringUtil.replace(
					vulnerableVersionRange,
					new String[] {
						StringPool.GREATER_THAN_OR_EQUAL,
						StringPool.GREATER_THAN, StringPool.LESS_THAN_OR_EQUAL,
						StringPool.LESS_THAN
					},
					new String[] {
						StringPool.OPEN_BRACKET, StringPool.OPEN_PARENTHESIS,
						StringPool.CLOSE_BRACKET, StringPool.CLOSE_PARENTHESIS
					});

				_versionRange = VersionRange.createFromVersionSpec(
					vulnerableVersionRange);
			}
		}

		private String _permalink;
		private String _summary;
		private VersionRange _versionRange;

	}

	private enum SecurityAdvisoryEcosystemEnum {

		COMPOSER, GO, MAVEN, NPM, NUGET, PIP, RUBYGEMS, RUST

	}

}