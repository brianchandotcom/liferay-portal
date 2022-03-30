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

package com.liferay.site.initializer.testray.extra.gcp.function;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.petra.http.invoker.HttpInvoker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.File;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Brian Wing Shun Chan
 */
public class Main {

	public static void main(String[] arguments) throws Exception {
		Properties properties = new Properties();

		try (InputStream inputStream = Main.class.getResourceAsStream(
				"/application.properties")) {

			properties.load(inputStream);
		}

		Main main = new Main(
			properties.getProperty("liferay.login"),
			properties.getProperty("liferay.password"),
			properties.getProperty("liferay.url"),
			properties.getProperty("s3.api.key.path"),
			properties.getProperty("s3.bucket.name"));

		main.uploadToTestray();
	}

	public Main(
		String liferayLogin, String liferayPassword, String liferayURL,
		String s3APIKeyPath, String s3BucketName) {

		_liferayLogin = liferayLogin;
		_liferayPassword = liferayPassword;
		_liferayURL = liferayURL;
		_s3APIKeyPath = s3APIKeyPath;
		_s3BucketName = s3BucketName;
	}

	public void uploadToTestray() throws Exception {
		Storage storage = StorageOptions.newBuilder(
		).setCredentials(
			GoogleCredentials.fromStream(
				Main.class.getResourceAsStream(_s3APIKeyPath))
		).build(
		).getService();

		Page<Blob> page = storage.list(
			_s3BucketName, Storage.BlobListOption.prefix("inbox/"));

		for (Blob blob : page.iterateAll()) {
			String name = blob.getName();

			if (name.equals("inbox/")) {
				continue;
			}

			try {
				_processArchive(blob.getContent());

				blob.copyTo(
					_s3BucketName, name.replaceFirst("inbox", "processed"));
			}
			catch (Exception exception) {
				blob.copyTo(
					_s3BucketName, name.replaceFirst("inbox", "errored"));
			}

			blob.delete();
		}
	}

	private void _addTestrayAttachments(
			long testrayCaseResultId, Node testrayTestcaseNode)
		throws Exception {

		Element testcaseElement = (Element)testrayTestcaseNode;

		NodeList attachmentsNodeList = testcaseElement.getElementsByTagName(
			"attachments");

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < attachmentsNodeList.getLength(); i++) {
			Node attachmentsNode = attachmentsNodeList.item(i);

			if (attachmentsNode.getNodeType() == Node.ELEMENT_NODE) {
				Element attachmentsElement = (Element)attachmentsNode;

				NodeList fileNodeList = attachmentsElement.getElementsByTagName(
					"file");

				for (int j = 0; j < fileNodeList.getLength(); j++) {
					Node fileNode = fileNodeList.item(j);

					if (fileNode.getNodeType() == Node.ELEMENT_NODE) {
						Element fileElement = (Element)fileNode;

						jsonArray.put(
							HashMapBuilder.put(
								"name", fileElement.getAttribute("name")
							).put(
								"r_caseResultToAttachments_c_caseResultId",
								String.valueOf(testrayCaseResultId)
							).put(
								"url", fileElement.getAttribute("url")
							).put(
								"value", fileElement.getAttribute("value")
							).build());
					}
				}
			}
		}

		_postObjectEntries(jsonArray, "attachments");
	}

	private void _addTestrayCase(
			long testrayBuildId, Map<String, Object> testrayCasePropertiesMap,
			long testrayProjectId, long testrayRunId, Node testrayTestcaseNode)
		throws Exception {

		long testrayCaseTypeId = _getTestrayCaseTypeId(
			(String)testrayCasePropertiesMap.get("testray.case.type.name"));

		long testrayTeamId = _getTestrayTeamId(
			testrayProjectId,
			(String)testrayCasePropertiesMap.get("testray.team.name"));

		long testrayComponentId = _getTestrayComponentId(
			(String)testrayCasePropertiesMap.get("testray.main.component.name"),
			testrayProjectId, testrayTeamId);

		long testrayCaseId = _postObjectEntry(
			HashMapBuilder.put(
				"description",
				(String)testrayCasePropertiesMap.get(
					"testray.testcase.description")
			).put(
				"priority",
				(String)testrayCasePropertiesMap.get(
					"testray.testcase.priority")
			).put(
				"r_caseTypeToCases_c_caseTypeId",
				String.valueOf(testrayCaseTypeId)
			).put(
				"r_componentToCases_componentId",
				String.valueOf(testrayComponentId)
			).put(
				"r_projectToCases_c_projectId", String.valueOf(testrayProjectId)
			).put(
				"r_teamToCases_teamId", String.valueOf(testrayTeamId)
			).build(),
			(String)testrayCasePropertiesMap.get("testray.testcase.name"),
			"cases");

		long testrayCaseResultId = _getTestrayCaseResultId(
			testrayBuildId, testrayCaseId, testrayCasePropertiesMap,
			testrayComponentId, testrayRunId, testrayTestcaseNode);

		_addTestrayAttachments(testrayCaseResultId, testrayTestcaseNode);

		_addTestrayIssue(
			testrayCaseResultId,
			(String)testrayCasePropertiesMap.get("testray.case.issue"));
		_addTestrayIssue(
			testrayCaseResultId,
			(String)testrayCasePropertiesMap.get("testray.case.defect"));
		_addTestrayWarnings(testrayCasePropertiesMap, testrayCaseResultId);
	}

	private void _addTestrayCases(
			Element element, long testrayBuildId, long testrayProjectId,
			long testrayRunId)
		throws Exception {

		NodeList testCaseNodeList = element.getElementsByTagName("testcase");

		for (int i = 0; i < testCaseNodeList.getLength(); i++) {
			Node testcaseNode = testCaseNodeList.item(i);

			Map<String, Object> testrayCasePropertiesMap =
				_getTestrayCaseProperties((Element)testcaseNode);

			_addTestrayCase(
				testrayBuildId, testrayCasePropertiesMap, testrayProjectId,
				testrayRunId, testcaseNode);
		}
	}

	private void _addTestrayFactor(
			long testrayFactorCategoryId, String testrayFactorCategoryName,
			long testrayFactorOptionId, String testrayFactorOptionName,
			long testrayRunId)
		throws Exception {

		_postObjectEntry(
			HashMapBuilder.put(
				"classNameId", String.valueOf(testrayRunId)
			).put(
				"classPK", String.valueOf(testrayRunId)
			).put(
				"r_factorCategoryToFactors_c_factorCategoryId",
				String.valueOf(testrayFactorCategoryId)
			).put(
				"r_factorOptionToFactors_c_factorOptionId",
				String.valueOf(testrayFactorOptionId)
			).put(
				"testrayFactorCategoryName", testrayFactorCategoryName
			).put(
				"testrayFactorOptionName",
				String.valueOf(testrayFactorOptionName)
			).build(),
			null, "factors");
	}

	private void _addTestrayIssue(
			long testrayCaseResultId, String testrayIssueName)
		throws Exception {

		if (_isEmpty(testrayIssueName)) {
			return;
		}

		_postObjectEntry(
			HashMapBuilder.put(
				"r_caseResultToCaseResultsIssues_c_caseResultId",
				String.valueOf(testrayCaseResultId)
			).put(
				"r_issueToCaseResultsIssues_c_issueId",
				String.valueOf(
					_postObjectEntry(null, testrayIssueName, "issues"))
			).build(),
			null, "caseresultsissueses");
	}

	private void _addTestrayTask(long testrayBuildId, String testrayTaskName)
		throws Exception {

		long testrayTaskId = _getObjectEntryId(testrayTaskName, "tasks");

		if (testrayTaskId != 0) {
			return;
		}

		LocalDateTime currentLocalDateTime = LocalDateTime.now(ZoneOffset.UTC);

		_postObjectEntry(
			HashMapBuilder.put(
				"dueStatus",
				String.valueOf(_TESTRAY_CASE_RESULT_STATUS_IN_PROGRESS)
			).put(
				"r_buildToTasks_c_buildId", String.valueOf(testrayBuildId)
			).put(
				"statusUpdateDate", currentLocalDateTime::toString
			).build(),
			testrayTaskName, "tasks");
	}

	private void _addTestrayWarnings(
			Map<String, Object> testrayCasePropertiesMap,
			long testrayCaseResultId)
		throws Exception {

		List<String> warningsList = (List<String>)testrayCasePropertiesMap.get(
			"testray.testcase.warnings");

		if (warningsList == null) {
			return;
		}

		JSONArray jsonArray = new JSONArray();

		for (String warning : warningsList) {
			jsonArray.put(
				HashMapBuilder.put(
					"content", warning
				).put(
					"r_caseResultToWarnings_c_caseResultId",
					String.valueOf(testrayCaseResultId)
				).build());
		}

		_postObjectEntries(jsonArray, "warnings");
	}

	private String _getAttributeValue(String attributeName, Node node) {
		NamedNodeMap namedNodeMap = node.getAttributes();

		if (namedNodeMap == null) {
			return null;
		}

		Node attributeNode = namedNodeMap.getNamedItem(attributeName);

		if (attributeNode == null) {
			return null;
		}

		return attributeNode.getTextContent();
	}

	private long _getObjectEntryId(
			String name, String objectDefinitionShortName)
		throws Exception {

		JSONObject objectEntryJSONObject = _objectEntryJSONObjects.get(
			objectDefinitionShortName + "#" + name);

		if (objectEntryJSONObject != null) {
			return objectEntryJSONObject.getLong("id");
		}

		HttpInvoker.HttpResponse httpResponse = _invoke(
			null, null, HttpInvoker.HttpMethod.GET, objectDefinitionShortName,
			HashMapBuilder.put(
				"fields", "id"
			).put(
				"filter", "name eq '" + name + "'"
			).build());

		JSONObject responseJSONObject = new JSONObject(
			httpResponse.getContent());

		JSONArray jsonArray = responseJSONObject.getJSONArray("items");

		if (jsonArray.isEmpty()) {
			return 0;
		}

		// TODO Cache just the ID if we never need more

		objectEntryJSONObject = jsonArray.getJSONObject(0);

		_objectEntryJSONObjects.put(
			objectDefinitionShortName + "#" + name, objectEntryJSONObject);

		return objectEntryJSONObject.getLong("id");
	}

	private Map<String, String> _getPropertiesMap(Element element) {
		Map<String, String> map = new HashMap<>();

		NodeList propertiesNodeList = element.getElementsByTagName(
			"properties");

		Node propertiesNode = propertiesNodeList.item(0);

		Element propertiesElement = (Element)propertiesNode;

		NodeList propertyNodeList = propertiesElement.getElementsByTagName(
			"property");

		for (int i = 0; i < propertyNodeList.getLength(); i++) {
			Node propertyNode = propertyNodeList.item(i);

			if (!propertyNode.hasAttributes()) {
				continue;
			}

			map.put(
				_getAttributeValue("name", propertyNode),
				_getAttributeValue("value", propertyNode));
		}

		return map;
	}

	private String _getTestrayBuildDescription(
		Map<String, String> propertiesMap) {

		StringBundler sb = new StringBundler(15);

		if (propertiesMap.get("liferay.portal.bundle") != null) {
			sb.append("Bundle: ");
			sb.append(propertiesMap.get("liferay.portal.bundle"));
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.NEW_LINE);
		}

		if (propertiesMap.get("liferay.plugins.git.id") != null) {
			sb.append("Plugins hash: ");
			sb.append(propertiesMap.get("liferay.plugins.git.id"));
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.NEW_LINE);
		}

		if (propertiesMap.get("liferay.portal.branch") != null) {
			sb.append("Portal branch: ");
			sb.append(propertiesMap.get("liferay.portal.branch"));
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.NEW_LINE);
		}

		if (propertiesMap.get("liferay.portal.git.id") != null) {
			sb.append("Portal hash: ");
			sb.append(propertiesMap.get("liferay.portal.git.id"));
			sb.append(StringPool.SEMICOLON);
		}

		return sb.toString();
	}

	private long _getTestrayBuildId(
			Map<String, String> propertiesMap, String testrayBuildName,
			long testrayProjectId)
		throws Exception {

		long testrayBuildId = _getObjectEntryId(testrayBuildName, "builds");

		if (testrayBuildId != 0) {
			return testrayBuildId;
		}

		long testrayProductVersionId = _getTestrayProductVersionId(
			testrayProjectId, propertiesMap.get("testray.product.version"));
		long testrayRoutineId = _getTestrayRoutineId(
			testrayProjectId, propertiesMap.get("testray.build.type"));

		return _postObjectEntry(
			HashMapBuilder.put(
				"description", _getTestrayBuildDescription(propertiesMap)
			).put(
				"dueDate", propertiesMap.get("testray.build.time")
			).put(
				"gitHash", propertiesMap.get("git.id")
			).put(
				"githubCompareURLs", propertiesMap.get("liferay.compare.urls")
			).put(
				"r_productVersionToBuilds_c_productVersionId",
				String.valueOf(testrayProductVersionId)
			).put(
				"r_projectToBuilds_c_projectId",
				String.valueOf(testrayProjectId)
			).put(
				"r_routineToBuilds_c_routineId",
				String.valueOf(testrayRoutineId)
			).build(),
			testrayBuildName, "builds");
	}

	private Map<String, Object> _getTestrayCaseProperties(Element element) {
		Map<String, Object> map = new HashMap<>();

		NodeList propertiesNodeList = element.getElementsByTagName(
			"properties");

		Node propertiesNode = propertiesNodeList.item(0);

		Element propertiesElement = (Element)propertiesNode;

		NodeList propertyNodeList = propertiesElement.getElementsByTagName(
			"property");

		for (int i = 0; i < propertyNodeList.getLength(); i++) {
			Node propertyNode = propertyNodeList.item(i);

			if (!propertyNode.hasAttributes()) {
				continue;
			}

			String propertyName = _getAttributeValue("name", propertyNode);

			if (propertyName.equalsIgnoreCase("testray.testcase.warnings")) {
				List<String> warningsList = new ArrayList<>();

				NodeList warningsNodeList = propertyNode.getChildNodes();

				for (int j = 0; j < warningsNodeList.getLength(); j++) {
					Node warningdNode = warningsNodeList.item(j);

					String warning = warningdNode.getTextContent();

					if (!_isEmpty(warning)) {
						warningsList.add(warningdNode.getTextContent());
					}
				}

				map.put(propertyName, warningsList);
			}
			else {
				map.put(
					propertyName, _getAttributeValue("value", propertyNode));
			}
		}

		return map;
	}

	private long _getTestrayCaseResultId(
			long testrayBuildId, long testrayCaseId,
			Map<String, Object> testrayCasePropertiesMap,
			long testrayComponentId, long testrayRunId,
			Node testrayTestcaseNode)
		throws Exception {

		String dueStatus = String.valueOf(_TESTRAY_CASE_RESULT_STATUS_UNTESTED);

		String testrayTestcaseStatus = (String)testrayCasePropertiesMap.get(
			"testray.testcase.status");

		if (testrayTestcaseStatus.equals("in-progress")) {
			dueStatus = String.valueOf(_TESTRAY_CASE_RESULT_STATUS_IN_PROGRESS);
		}
		else if (testrayTestcaseStatus.equals("passed")) {
			dueStatus = String.valueOf(_TESTRAY_CASE_RESULT_STATUS_PASSED);
		}
		else if (testrayTestcaseStatus.equals("failed")) {
			dueStatus = String.valueOf(_TESTRAY_CASE_RESULT_STATUS_FAILED);
		}
		else if (testrayTestcaseStatus.equals("blocked")) {
			dueStatus = String.valueOf(_TESTRAY_CASE_RESULT_STATUS_BLOCKED);
		}
		else if (testrayTestcaseStatus.equals("dnr")) {
			dueStatus = String.valueOf(_TESTRAY_CASE_RESULT_STATUS_DID_NOT_RUN);
		}
		else if (testrayTestcaseStatus.equals("test-fix")) {
			dueStatus = String.valueOf(_TESTRAY_CASE_RESULT_STATUS_TEST_FIX);
		}

		Map<String, String> map = HashMapBuilder.put(
			"dueStatus", dueStatus
		).put(
			"r_buildToCaseResult_c_buildId", String.valueOf(testrayBuildId)
		).put(
			"r_caseResultToCase_c_caseId", String.valueOf(testrayCaseId)
		).put(
			"r_componentToCaseResult_c_componentId",
			String.valueOf(testrayComponentId)
		).put(
			"r_runToCaseResult_c_runId", String.valueOf(testrayRunId)
		).build();

		Element element = (Element)testrayTestcaseNode;

		NodeList nodeList = element.getElementsByTagName("failure");

		Node failureNode = nodeList.item(0);

		if (failureNode != null) {
			String message = _getAttributeValue("message", failureNode);

			if (!message.isEmpty()) {
				map.put("errors", message);
			}
		}

		return _postObjectEntry(map, null, "caseresults");
	}

	private long _getTestrayCaseTypeId(String testrayCaseTypeName)
		throws Exception {

		long testrayCaseTypeId = _getObjectEntryId(
			testrayCaseTypeName, "casetypes");

		if (testrayCaseTypeId != 0) {
			return testrayCaseTypeId;
		}

		return _postObjectEntry(null, testrayCaseTypeName, "casetypes");
	}

	private long _getTestrayComponentId(
			String testrayComponentName, long testrayProjectId,
			long testrayTeamId)
		throws Exception {

		long testrayComponentId = _getObjectEntryId(
			testrayComponentName, "components");

		if (testrayComponentId != 0) {
			return testrayComponentId;
		}

		return _postObjectEntry(
			HashMapBuilder.put(
				"r_projectToComponents_c_projectId",
				String.valueOf(testrayProjectId)
			).put(
				"r_teamToComponents_c_teamId", String.valueOf(testrayTeamId)
			).build(),
			testrayComponentName, "components");
	}

	private long _getTestrayFactorCategoryId(String testrayFactorCategoryName)
		throws Exception {

		long testrayFactorCategoryId = _getObjectEntryId(
			testrayFactorCategoryName, "factorcategories");

		if (testrayFactorCategoryId != 0) {
			return testrayFactorCategoryId;
		}

		return _postObjectEntry(
			null, testrayFactorCategoryName, "factorcategories");
	}

	private long _getTestrayFactorOptionId(
			long testrayFactorCategoryId, String testrayFactorOptionName)
		throws Exception {

		long testrayFactorOptionId = _getObjectEntryId(
			testrayFactorOptionName, "factoroptions");

		if (testrayFactorOptionId != 0) {
			return testrayFactorOptionId;
		}

		return _postObjectEntry(
			HashMapBuilder.put(
				"r_factorCategoryToOptions_c_factorCategoryId",
				String.valueOf(testrayFactorCategoryId)
			).build(),
			testrayFactorOptionName, "factoroptions");
	}

	private long _getTestrayProductVersionId(
			long testrayProjectId, String testrayProductVersionName)
		throws Exception {

		long testrayProductVersionId = _getObjectEntryId(
			testrayProductVersionName, "productversions");

		if (testrayProductVersionId != 0) {
			return testrayProductVersionId;
		}

		return _postObjectEntry(
			HashMapBuilder.put(
				"r_projectToProductVersions_c_projectId",
				String.valueOf(testrayProjectId)
			).build(),
			testrayProductVersionName, "productversions");
	}

	private long _getTestrayProjectId(String testrayProjectName)
		throws Exception {

		long testrayProjectId = _getObjectEntryId(
			testrayProjectName, "projects");

		if (testrayProjectId != 0) {
			return testrayProjectId;
		}

		return _postObjectEntry(null, testrayProjectName, "projects");
	}

	private long _getTestrayRoutineId(
			long testrayProjectId, String testrayRoutineName)
		throws Exception {

		long testrayRoutineId = _getObjectEntryId(
			testrayRoutineName, "routines");

		if (testrayRoutineId != 0) {
			return testrayRoutineId;
		}

		return _postObjectEntry(
			HashMapBuilder.put(
				"r_routineToProjects_c_projectId",
				String.valueOf(testrayProjectId)
			).build(),
			testrayRoutineName, "routines");
	}

	private String _getTestrayRunEnvironmentHash(
			Element element, long testrayRunId)
		throws Exception {

		StringBundler sb = new StringBundler();

		NodeList environmentNodeList = element.getElementsByTagName(
			"environment");

		for (int i = 0; i < environmentNodeList.getLength(); i++) {
			Node node = environmentNodeList.item(i);

			if (!node.hasAttributes()) {
				continue;
			}

			String testrayFactorCategoryName = _getAttributeValue("type", node);

			long testrayFactorCategoryId = _getTestrayFactorCategoryId(
				testrayFactorCategoryName);

			String testrayFactorOptionName = _getAttributeValue("option", node);

			long testrayFactorOptionId = _getTestrayFactorOptionId(
				testrayFactorCategoryId, testrayFactorOptionName);

			_addTestrayFactor(
				testrayFactorCategoryId, testrayFactorCategoryName,
				testrayFactorOptionId, testrayFactorOptionName, testrayRunId);

			sb.append(testrayFactorCategoryId);
			sb.append(testrayFactorOptionId);
		}

		String testrayFactorsString = sb.toString();

		return String.valueOf(testrayFactorsString.hashCode());
	}

	private long _getTestrayRunId(
			Element element, Map<String, String> propertiesMap,
			long testrayBuildId, String testrayRunName)
		throws Exception {

		long testrayRunId = _getObjectEntryId(testrayRunName, "runs");

		if (testrayRunId != 0) {
			return testrayRunId;
		}

		testrayRunId = _postObjectEntry(
			HashMapBuilder.put(
				"externalReferencePK", propertiesMap.get("testray.run.id")
			).put(
				"externalReferenceType",
				String.valueOf(_TESTRAY_RUN_EXTERNAL_REFERENCE_TYPE_POSHI)
			).put(
				"jenkinsJobKey", propertiesMap.get("jenkins.job.id")
			).put(
				"name", testrayRunName
			).put(
				"r_buildToRuns_c_buildId", String.valueOf(testrayBuildId)
			).build(),
			testrayRunName, "runs");

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"environmentHash",
			_getTestrayRunEnvironmentHash(element, testrayRunId));

		_invoke(
			jsonObject.toString(), null, HttpInvoker.HttpMethod.PATCH,
			"runs/" + testrayRunId, null);

		return testrayRunId;
	}

	private long _getTestrayTeamId(
			long testrayProjectId, String testrayTeamName)
		throws Exception {

		long testrayTeamId = _getObjectEntryId(testrayTeamName, "teams");

		if (testrayTeamId != 0) {
			return testrayTeamId;
		}

		return _postObjectEntry(
			HashMapBuilder.put(
				"r_projectToTeams_c_projectId", String.valueOf(testrayProjectId)
			).build(),
			testrayTeamName, "teams");
	}

	private HttpInvoker.HttpResponse _invoke(
			String body, Map<String, String> headers,
			HttpInvoker.HttpMethod httpMethod, String objectDefinitionShortName,
			Map<String, String> parameters)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(body, "application/json");

		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpInvoker.header(entry.getKey(), entry.getValue());
			}
		}

		httpInvoker.httpMethod(httpMethod);

		if (parameters != null) {
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}
		}

		httpInvoker.path(_liferayURL + "/o/c/" + objectDefinitionShortName);
		httpInvoker.userNameAndPassword(_liferayLogin + ":" + _liferayPassword);

		return httpInvoker.invoke();
	}

	private boolean _isEmpty(String value) {
		if (value == null) {
			return true;
		}

		String trimmedValue = value.trim();

		if (trimmedValue.isEmpty()) {
			return true;
		}

		return false;
	}

	private void _postObjectEntries(
			JSONArray jsonArray, String objectDefinitionShortName)
		throws Exception {

		_invoke(
			jsonArray.toString(), null, HttpInvoker.HttpMethod.POST,
			objectDefinitionShortName + "/batch", null);
	}

	private long _postObjectEntry(
			Map<String, String> headers, String name,
			String objectDefinitionShortName)
		throws Exception {

		JSONObject headersJSONObject = new JSONObject(
			(headers != null) ? headers : Collections.emptyMap());

		headersJSONObject.put("name", name);

		HttpInvoker.HttpResponse httpResponse = _invoke(
			headersJSONObject.toString(), null, HttpInvoker.HttpMethod.POST,
			objectDefinitionShortName, null);

		JSONObject responseJSONObject = new JSONObject(
			httpResponse.getContent());

		long id = responseJSONObject.getLong("id");

		if (id > 0) {
			_objectEntryJSONObjects.put(
				objectDefinitionShortName + "#" + name, responseJSONObject);
		}

		return id;
	}

	private void _processArchive(byte[] bytes) throws Exception {
		Path tempDirectoryPath = null;
		Path tempFilePath = null;

		try {
			tempDirectoryPath = Files.createTempDirectory(null);

			tempFilePath = Files.createTempFile(null, null);

			Files.write(tempFilePath, bytes);

			Archiver archiver = ArchiverFactory.createArchiver("tar");

			File tempDirectoryFile = tempDirectoryPath.toFile();

			archiver.extract(tempFilePath.toFile(), tempDirectoryFile);

			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			for (File file : tempDirectoryFile.listFiles()) {
				try {
					Document document = documentBuilder.parse(file);

					_processDocument(document);
				}
				finally {
					file.delete();
				}
			}
		}
		finally {
			if (tempDirectoryPath != null) {
				Files.deleteIfExists(tempDirectoryPath);
			}

			if (tempFilePath != null) {
				Files.deleteIfExists(tempFilePath);
			}
		}
	}

	private void _processDocument(Document document) throws Exception {
		Element element = document.getDocumentElement();

		Map<String, String> propertiesMap = _getPropertiesMap(element);

		String testrayProjectName = propertiesMap.get("testray.project.name");

		long testrayProjectId = _getTestrayProjectId(testrayProjectName);

		String testrayBuildName = propertiesMap.get("testray.build.name");

		long testrayBuildId = _getTestrayBuildId(
			propertiesMap, testrayBuildName, testrayProjectId);

		String testrayRunName = propertiesMap.get("testray.run.id");

		long testrayRunId = _getTestrayRunId(
			element, propertiesMap, testrayBuildId, testrayRunName);

		_addTestrayCases(
			element, testrayBuildId, testrayProjectId, testrayRunId);

		_addTestrayTask(
			testrayBuildId, propertiesMap.get("testray.build.name"));
	}

	private static final int _TESTRAY_CASE_RESULT_STATUS_BLOCKED = 4;

	private static final int _TESTRAY_CASE_RESULT_STATUS_DID_NOT_RUN = 6;

	private static final int _TESTRAY_CASE_RESULT_STATUS_FAILED = 3;

	private static final int _TESTRAY_CASE_RESULT_STATUS_IN_PROGRESS = 1;

	private static final int _TESTRAY_CASE_RESULT_STATUS_PASSED = 2;

	private static final int _TESTRAY_CASE_RESULT_STATUS_TEST_FIX = 7;

	private static final int _TESTRAY_CASE_RESULT_STATUS_UNTESTED = 0;

	private static final int _TESTRAY_RUN_EXTERNAL_REFERENCE_TYPE_POSHI = 1;

	private final String _liferayLogin;
	private final String _liferayPassword;
	private final String _liferayURL;
	private final Map<String, JSONObject> _objectEntryJSONObjects =
		new HashMap<>();
	private final String _s3APIKeyPath;
	private final String _s3BucketName;

}