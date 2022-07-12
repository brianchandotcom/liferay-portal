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

import com.liferay.petra.string.CharPool;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.maven.artifact.versioning.ComparableVersion;

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

		return content;
	}

	private void _addResultList(
			String packageName, String cursor, CloseableHttpClient httpClient,
			SecurityAdvisoryEcosystemEnum securityAdvisoryEcosystemEnum,
			List<SecurityVulnerabilityNode> securityVulnerabilityNodes)
		throws IOException {

		if (_pageNumber == 0) {
			return;
		}

		HttpPost httpPost = new HttpPost("https://api.github.com/graphql");

		httpPost.addHeader(
			"Authorization", "bearer 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");
		httpPost.addHeader(
			"Content-Type",
			"application/json; charset=utf-8; application/graphql");

		String queryArguments = StringBundler.concat(
			"first: ", _pageNumber, ", package:\\\"", packageName,
			"\\\", ecosystem: ", securityAdvisoryEcosystemEnum.name());

		if (ListUtil.isNotNull(_severities)) {
			queryArguments = queryArguments + ", severities: " + _severities;
		}

		if (Validator.isNotNull(cursor)) {
			queryArguments += "after: \\\"" + cursor + "\\\"";
		}

		String resultArguments =
			"{nodes { advisory {summary, permalink} package {name} severity " +
				"vulnerableVersionRange } pageInfo {endCursor hasNextPage } " +
					"totalCount }";

		String query = StringBundler.concat(
			"{\"query\": \"{ securityVulnerabilities(", queryArguments, ") ",
			resultArguments, "}\" }");

		StringEntity stringEntity = new StringEntity(
			query, ContentType.APPLICATION_JSON);

		httpPost.setEntity(stringEntity);

		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);

			StatusLine statusLine = httpResponse.getStatusLine();

			if (statusLine.getStatusCode() == 200) {
				JSONObject jsonObject = new JSONObjectImpl(
					EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));

				JSONObject dataJSONObject = jsonObject.getJSONObject("data");

				JSONObject securityVulnerabilitiesJSONObject =
					dataJSONObject.getJSONObject("securityVulnerabilities");

				int totalCount = securityVulnerabilitiesJSONObject.getInt(
					"totalCount");

				if (totalCount == 0) {
					return;
				}

				JSONArray nodesJSONArray =
					securityVulnerabilitiesJSONObject.getJSONArray("nodes");

				for (Object tmpObject : nodesJSONArray) {
					JSONObject tmpJSONObject = (JSONObject)tmpObject;

					SecurityVulnerabilityNode securityVulnerabilityNode =
						new SecurityVulnerabilityNode();

					JSONObject advisoryJSONObject = tmpJSONObject.getJSONObject(
						"advisory");

					securityVulnerabilityNode.setSummary(
						advisoryJSONObject.getString("summary"));
					securityVulnerabilityNode.setPermalink(
						advisoryJSONObject.getString("permalink"));

					securityVulnerabilityNode.setVulnerableVersionRange(
						tmpJSONObject.getString("vulnerableVersionRange"));

					securityVulnerabilityNodes.add(securityVulnerabilityNode);
				}

				JSONObject pageInfoJSONObject =
					securityVulnerabilitiesJSONObject.getJSONObject("pageInfo");

				if (pageInfoJSONObject.getBoolean("hasNextPage")) {
					_addResultList(
						packageName, pageInfoJSONObject.getString("endCursor"),
						httpClient, securityAdvisoryEcosystemEnum,
						securityVulnerabilityNodes);
				}
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}
	}

	private void _checkIsContainVulnerabilities(
		String fileName, String packageName, String version) {

		List<SecurityVulnerabilityNode> securityVulnerabilityNodes =
			_queryResultMap.get(packageName);

		for (SecurityVulnerabilityNode securityVulnerabilityNode :
				securityVulnerabilityNodes) {

			if (_compareVersion(
					version,
					securityVulnerabilityNode.getVulnerableVersionRange())) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Library '", packageName, "' ", version,
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
			CloseableHttpClient httpClient,
			SecurityAdvisoryEcosystemEnum securityAdvisoryEcosystemEnum)
		throws IOException {

		if (!version.matches("(\\d|v).+")) {
			return;
		}

		if (!_queryResultMap.containsKey(packageName)) {
			_getServerData(
				packageName, httpClient, securityAdvisoryEcosystemEnum);
		}

		_checkIsContainVulnerabilities(fileName, packageName, version);
	}

	private void _checkVersionInJsonFile(
			String fileName, CloseableHttpClient httpClient,
			JSONObject jsonObject)
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
				fileName, dependencyName, version, httpClient,
				SecurityAdvisoryEcosystemEnum.NPM);
		}
	}

	private boolean _compareVersion(
		String version, String vulnerableVersionRange) {

		boolean result = false;

		int count = 1;

		ComparableVersion currentComparableVersion = new ComparableVersion(
			version);

		String[] vulnerableVersionRangeArray = vulnerableVersionRange.split(
			StringPool.COMMA);

		for (String vulnerableVersion : vulnerableVersionRangeArray) {
			vulnerableVersion = StringUtil.trim(vulnerableVersion);

			char element = vulnerableVersion.charAt(0);

			StringBundler operatorSB = new StringBundler();

			int i = 0;

			while (element != CharPool.SPACE) {
				operatorSB.append(element);
				i++;
				element = vulnerableVersion.charAt(i);
			}

			String operator = operatorSB.toString();
			String vulnerableVersionNumber = vulnerableVersion.substring(i + 1);

			boolean tmpResult = false;

			ComparableVersion vulnerableComparableVersion =
				new ComparableVersion(vulnerableVersionNumber);

			int compareResult = currentComparableVersion.compareTo(
				vulnerableComparableVersion);

			if (StringUtil.equals(StringPool.LESS_THAN, operator)) {
				tmpResult = compareResult < 0;
			}
			else if (StringUtil.equals(
						StringPool.LESS_THAN_OR_EQUAL, operator)) {

				tmpResult = compareResult <= 0;
			}
			else if (StringUtil.equals(StringPool.GREATER_THAN, operator)) {
				tmpResult = compareResult > 0;
			}
			else if (StringUtil.equals(
						StringPool.GREATER_THAN_OR_EQUAL, operator)) {

				tmpResult = compareResult >= 0;
			}
			else if (StringUtil.equals(StringPool.EQUAL, operator)) {
				tmpResult = compareResult == 0;
			}

			if (count == 1) {
				result = tmpResult;
				count++;
			}
			else {
				result = result && tmpResult;
			}
		}

		return result;
	}

	private String _getContentByPattern(String content, Pattern pattern) {
		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private synchronized void _getServerData(
			String packageName, CloseableHttpClient httpClient,
			SecurityAdvisoryEcosystemEnum securityAdvisoryEcosystemEnum)
		throws IOException {

		if (_queryResultMap.containsKey(packageName)) {
			return;
		}

		List<SecurityVulnerabilityNode> securityVulnerabilityNodes =
			new ArrayList<>();

		_addResultList(
			packageName, null, httpClient, securityAdvisoryEcosystemEnum,
			securityVulnerabilityNodes);

		_queryResultMap.put(packageName, securityVulnerabilityNodes);
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

		CloseableHttpClient httpClient = HttpClients.createDefault();

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
				fileName, group + StringPool.COLON + name, version, httpClient,
				SecurityAdvisoryEcosystemEnum.MAVEN);
		}

		try {
			httpClient.close();
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}
	}

	private void _ivyXmlLibraryVersionCheck(String fileName, String content)
		throws DocumentException, IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();

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
					fileName, org + StringPool.COLON + name, rev, httpClient,
					SecurityAdvisoryEcosystemEnum.MAVEN);
			}
		}

		try {
			httpClient.close();
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}
	}

	private void _jsonLibraryVersionCheck(String fileName, String content)
		throws IOException {

		if (Validator.isNull(content)) {
			return;
		}

		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {
			JSONObject contentJSONObject = new JSONObjectImpl(content);

			_checkVersionInJsonFile(
				fileName, httpClient,
				contentJSONObject.getJSONObject("dependencies"));

			_checkVersionInJsonFile(
				fileName, httpClient,
				contentJSONObject.getJSONObject("devDependencies"));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}
		finally {
			try {
				httpClient.close();
			}
			catch (IOException ioException) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioException);
				}
			}
		}
	}

	private void _propertiesLibraryVersionCheck(String fileName, String content)
		throws IOException {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		CloseableHttpClient httpClient = HttpClients.createDefault();

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
				fileName, dependency[1], dependency[2], httpClient,
				SecurityAdvisoryEcosystemEnum.MAVEN);
		}

		try {
			httpClient.close();
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
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
	private static final Map<String, List<SecurityVulnerabilityNode>>
		_queryResultMap = new ConcurrentHashMap<>();

	private int _pageNumber;
	private List<String> _severities;

	private static class SecurityVulnerabilityNode {

		public String getPackageEcosystem() {
			return _packageEcosystem;
		}

		public String getPackageName() {
			return _packageName;
		}

		public String getPermalink() {
			return _permalink;
		}

		public String getSummary() {
			return _summary;
		}

		public String getVulnerableVersionRange() {
			return _vulnerableVersionRange;
		}

		public void setPackageEcosystem(String packageEcosystem) {
			_packageEcosystem = packageEcosystem;
		}

		public void setPackageName(String packageName) {
			_packageName = packageName;
		}

		public void setPermalink(String permalink) {
			_permalink = permalink;
		}

		public void setSummary(String summary) {
			_summary = summary;
		}

		public void setVulnerableVersionRange(String vulnerableVersionRange) {
			_vulnerableVersionRange = vulnerableVersionRange;
		}

		private String _packageEcosystem;
		private String _packageName;
		private String _permalink;
		private String _summary;
		private String _vulnerableVersionRange;

	}

	private enum SecurityAdvisoryEcosystemEnum {

		COMPOSER, GO, MAVEN, NPM, NUGET, PIP, RUBYGEMS, RUST

	}

}