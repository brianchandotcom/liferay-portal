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

package com.liferay.site.initializer.testray.extra.java.function;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.petra.http.invoker.HttpInvoker;
import com.liferay.petra.string.StringPool;
import com.liferay.site.initializer.testray.extra.java.function.constants.TestrayConstants;
import com.liferay.site.initializer.testray.extra.java.function.http.HttpUtil;
import com.liferay.site.initializer.testray.extra.java.function.util.PropsUtil;
import com.liferay.site.initializer.testray.extra.java.function.util.PropsValues;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * @author José Abelenda
 */
public class ImportResults {

	public static void main(String[] args) {
		try {
			ImportResults importResults = new ImportResults();

			importResults._readFiles();
		}
		catch (Exception exception) {
			_logger.severe(exception.getMessage());
		}
	}

	public ImportResults() throws Exception {
		_storage = _getStorage();

		_documentBuilderFactory = DocumentBuilderFactory.newInstance();

		_documentBuilder = _documentBuilderFactory.newDocumentBuilder();
	}

	private long _addEntity(Map<String, String> bodyMap, String objectName)
		throws Exception {

		JSONObject responseJSONObject = HttpUtil.invoke(
			new JSONObject(
				bodyMap
			).toString(),
			objectName, null, null, HttpInvoker.HttpMethod.POST);

		return responseJSONObject.getLong("id");
	}

	private void _addTestrayAttachments(
			Node testcaseNode, long testrayCaseResultId)
		throws Exception {

		Element testcaseElement = (Element)testcaseNode;

		NodeList attachmentsNodeList = testcaseElement.getElementsByTagName(
			"attachments");

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

						Map<String, String> bodyMap = new HashMap<>();

						bodyMap.put("name", fileElement.getAttribute("name"));
						bodyMap.put(
							"r_caseResultToAttachments_c_caseResultId",
							String.valueOf(testrayCaseResultId));
						bodyMap.put("url", fileElement.getAttribute("url"));
						bodyMap.put("value", fileElement.getAttribute("value"));

						_addEntity(bodyMap, "attachments");
					}
				}
			}
		}
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
				testcaseNode, testrayBuildId, testrayProjectId, testrayRunId,
				testrayCasePropertiesMap);
		}
	}

	private void _addTestrayCase(
			Node testcaseNode, long testrayBuildId, long testrayProjectId,
			long testrayRunId, Map<String, Object> testrayCasePropertiesMap)
		throws Exception {

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put(
			"description",
			(String)testrayCasePropertiesMap.get(
				"testray.testcase.description"));
		bodyMap.put(
			"name",
			(String)testrayCasePropertiesMap.get("testray.testcase.name"));
		bodyMap.put(
			"priority",
			(String)testrayCasePropertiesMap.get("testray.testcase.priority"));

		String testrayCaseTypeName = (String)testrayCasePropertiesMap.get(
			"testray.case.type.name");

		long testrayCaseTypeId = _fetchOrAddTestrayCaseType(
			testrayCaseTypeName);

		bodyMap.put(
			"r_caseTypeToCases_c_caseTypeId",
			String.valueOf(testrayCaseTypeId));

		bodyMap.put(
			"r_projectToCases_c_projectId", String.valueOf(testrayProjectId));

		String testrayTeamName = (String)testrayCasePropertiesMap.get(
			"testray.team.name");

		long testrayTeamId = _fetchOrAddTestrayTeam(
			testrayProjectId, testrayTeamName);

		bodyMap.put("r_teamToCases_teamId", String.valueOf(testrayTeamId));

		String testrayComponentName = (String)testrayCasePropertiesMap.get(
			"testray.main.component.name");

		long testrayComponentId = _fetchOrAddTestrayComponent(
			testrayComponentName, testrayProjectId, testrayTeamId);

		bodyMap.put(
			"r_componentToCases_componentId",
			String.valueOf(testrayComponentId));

		long testrayCaseId = _addEntity(bodyMap, "cases");

		long testrayCaseResultId = _addTestrayCaseResult(
			testrayBuildId, testrayCaseId, testrayComponentId, testrayRunId,
			testrayCasePropertiesMap, testcaseNode);

		_addTestrayAttachments(testcaseNode, testrayCaseResultId);

		_addTestrayIssue(
			(String)testrayCasePropertiesMap.get("testray.case.issue"),
			testrayCaseResultId);
		_addTestrayIssue(
			(String)testrayCasePropertiesMap.get("testray.case.defect"),
			testrayCaseResultId);
		_addTestrayWarnings(testrayCasePropertiesMap, testrayCaseResultId);
	}

	private long _addTestrayCaseResult(
			long testrayBuildId, long testrayCaseId, long testrayComponentId,
			long testrayRunId, Map<String, Object> testrayCasePropertiesMap,
			Node testcaseNode)
		throws Exception {

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("r_buildToCaseResult_c_buildId",
			String.valueOf(testrayBuildId));
		bodyMap.put("r_caseResultToCase_c_caseId",
			String.valueOf(testrayCaseId));
		bodyMap.put("r_componentToCaseResult_c_componentId",
			String.valueOf(testrayComponentId));
		bodyMap.put("r_runToCaseResult_c_runId", String.valueOf(testrayRunId));

		String dueStatus = String.valueOf(
			TestrayConstants.TESTRAY_CASE_RESULT_STATUS_UNTESTED);

		String testrayTestcaseStatus = (String)testrayCasePropertiesMap.get(
			"testray.testcase.status");

		if (testrayTestcaseStatus.equals("in-progress")) {
			dueStatus = String.valueOf(
				TestrayConstants.TESTRAY_CASE_RESULT_STATUS_IN_PROGRESS);
		}
		else if (testrayTestcaseStatus.equals("passed")) {
			dueStatus = String.valueOf(
				TestrayConstants.TESTRAY_CASE_RESULT_STATUS_PASSED);
		}
		else if (testrayTestcaseStatus.equals("failed")) {
			dueStatus = String.valueOf(
				TestrayConstants.TESTRAY_CASE_RESULT_STATUS_FAILED);
		}
		else if (testrayTestcaseStatus.equals("blocked")) {
			dueStatus = String.valueOf(
				TestrayConstants.TESTRAY_CASE_RESULT_STATUS_BLOCKED);
		}
		else if (testrayTestcaseStatus.equals("dnr")) {
			dueStatus = String.valueOf(
				TestrayConstants.TESTRAY_CASE_RESULT_STATUS_DID_NOT_RUN);
		}
		else if (testrayTestcaseStatus.equals("test-fix")) {
			dueStatus = String.valueOf(
				TestrayConstants.TESTRAY_CASE_RESULT_STATUS_TEST_FIX);
		}

		bodyMap.put("dueStatus", dueStatus);

		Element testcaseElement = (Element)testcaseNode;

		NodeList failureNodeList = testcaseElement.getElementsByTagName(
			"failure");

		Node failureNode = failureNodeList.item(0);

		if (failureNode != null) {
			String message = _getAttributeValue(failureNode, "message");

			if (!message.isEmpty()) {
				bodyMap.put("errors", message);
			}
		}

		return _addEntity(bodyMap, "caseresults");
	}

	private long _fetchOrAddTestrayComponent(
			String testrayComponentName, long testrayProjectId,
			long testrayTeamId)
		throws Exception {

		long testrayComponentId = _fetchEntityIdByName(
			"components", testrayComponentName);

		if (testrayComponentId > 0) {
			return testrayComponentId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", testrayComponentName);
		bodyMap.put(
			"r_projectToComponents_c_projectId",
			String.valueOf(testrayProjectId));
		bodyMap.put(
			"r_teamToComponents_c_teamId", String.valueOf(testrayTeamId));

		return _addEntity(bodyMap, "components");
	}

	private void _addTestrayFactor(
			long testrayRunId, long testrayCategoryId, String factorCategoryName,
			long testrayOptionId, String factorOptionName)
		throws Exception {

		Map<String, String> bodyMap = new HashMap<>();
		
		bodyMap.put("classNameId", String.valueOf(testrayRunId));
		bodyMap.put("classPK", String.valueOf(testrayRunId));
		bodyMap.put(
			"r_factorCategoryToFactors_c_factorCategoryId",
			String.valueOf(testrayCategoryId));
		bodyMap.put("testrayFactorCategoryName", factorCategoryName);
		bodyMap.put(
			"r_optionToFactors_c_factorOptionId",
			String.valueOf(testrayOptionId));
		bodyMap.put("testrayFactorOptionName", factorOptionName);

		_addEntity(bodyMap, "factors");
	}

	private void _addTestrayIssue(String issue, long caseResultId)
		throws Exception {

		if (_isEmpty(issue)) {
			return;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", issue);

		long issueId = _addEntity(bodyMap, "issues");

		bodyMap = new HashMap<>();

		bodyMap.put("r_caseResultToCaseResultsIssues_c_caseResultId",
			String.valueOf(caseResultId));
		bodyMap.put("r_issueToCaseResultsIssues_c_issueId",
			String.valueOf(issueId));

		_addEntity(bodyMap, "caseresultsissueses");
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

		for (String warning : warningsList) {
			Map<String, String> bodyMap = new HashMap<>();

			bodyMap.put("content", warning);
			bodyMap.put(
				"r_caseResultToWarnings_c_caseResultId",
				String.valueOf(testrayCaseResultId));

			_addEntity(bodyMap, "warnings");
		}
	}

	private String _buildTestrayBuildDescription(
		Map<String, String> propertiesMap) {

		StringBuilder sb = new StringBuilder(15);

		if (propertiesMap.get("liferay.portal.git.id") != null) {
			sb.append("Portal hash: ");
			sb.append(propertiesMap.get("liferay.portal.git.id"));
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

		if (propertiesMap.get("liferay.portal.bundle") != null) {
			sb.append("Bundle: ");
			sb.append(propertiesMap.get("liferay.portal.bundle"));
			sb.append(StringPool.SEMICOLON);
		}

		return sb.toString();
	}

	private long _fetchEntityIdByName(String objectName, String entityName)
		throws Exception {

		Map<String, String> parametersMap = new HashMap<>();

		parametersMap.put("fields", "id");
		parametersMap.put("filter", "name eq '" + entityName + "'");

		JSONObject responseJSONObject = HttpUtil.invoke(
			null, objectName, null, parametersMap, HttpInvoker.HttpMethod.GET);

		JSONArray jsonArray = responseJSONObject.getJSONArray("items");

		if (!jsonArray.isEmpty()) {
			JSONObject buildJSONObject = jsonArray.getJSONObject(0);

			return buildJSONObject.getLong("id");
		}

		return 0l;
	}

	private long _fetchOrAddTestrayBuild(
			long testrayProjectId, Map<String, String> propertiesMap)
		throws Exception {

		String testrayBuildName = propertiesMap.get("testray.build.name");

		long testrayBuildId = _fetchEntityIdByName("builds", testrayBuildName);

		if (testrayBuildId > 0) {
			return testrayBuildId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put(
			"description", _buildTestrayBuildDescription(propertiesMap));
		bodyMap.put("dueDate", propertiesMap.get("testray.build.time"));
		bodyMap.put("gitHash", propertiesMap.get("git.id"));
		bodyMap.put(
			"githubCompareURLs", propertiesMap.get("liferay.compare.urls"));
		bodyMap.put("name", testrayBuildName);
		bodyMap.put(
			"r_projectToBuilds_c_projectId", String.valueOf(testrayProjectId));

		long testrayProductVersionId = _fetchOrAddTestrayProductVersion(
			testrayProjectId, propertiesMap.get("testray.product.version"));

		bodyMap.put(
			"r_productVersionToBuilds_c_productVersionId",
			String.valueOf(testrayProductVersionId));

		long testrayRoutineId = _fetchOrAddTestrayRoutine(
			testrayProjectId, propertiesMap.get("testray.build.type"));

		bodyMap.put(
			"r_routineToBuilds_c_routineId", String.valueOf(testrayRoutineId));

		return _addEntity(bodyMap, "builds");
	}

	private long _fetchOrAddTestrayCaseType(String testrayCaseTypeName)
		throws Exception {

		long testrayCaseTypeId = _fetchEntityIdByName(
			"casetypes", testrayCaseTypeName);

		if (testrayCaseTypeId > 0) {
			return testrayCaseTypeId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", testrayCaseTypeName);

		return _addEntity(bodyMap, "casetypes");
	}

	private long _fetchOrAddTestrayFactorCategory(String factorCategoryName)
		throws Exception {

		long testrayCategoryId = _fetchEntityIdByName(
			"factorcategories", factorCategoryName);

		if (testrayCategoryId > 0) {
			return testrayCategoryId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", factorCategoryName);

		return _addEntity(bodyMap, "factorcategories");
	}

	private long _fetchOrAddTestrayFactorOption(
			String factorOptionName, long testrayCategoryId)
		throws Exception {

		long testrayFactorOptionId = _fetchEntityIdByName(
			"factoroptions", factorOptionName);

		if (testrayFactorOptionId > 0) {
			return testrayFactorOptionId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", factorOptionName);
		bodyMap.put(
			"r_factorCategoryToOptions_c_categoryId",
			String.valueOf(testrayCategoryId));

		return _addEntity(bodyMap, "factoroptions");
	}

	private long _fetchOrAddTestrayRun(
			long testrayBuildId, Map<String, String> propertiesMap,
			Element element)
		throws Exception {

		String testrayRunName = propertiesMap.get("testray.run.id");

		long testrayRunId = _fetchEntityIdByName("runs", testrayRunName);

		if (testrayRunId > 0) {
			return testrayRunId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("externalReferencePK", propertiesMap.get("testray.run.id"));
		bodyMap.put(
			"externalReferenceType",
			String.valueOf(
				TestrayConstants.TESTRAY_RUN_EXTERNAL_REFERENCE_TYPE_POSHI));
		bodyMap.put("jenkinsJobKey", propertiesMap.get("jenkins.job.id"));
		bodyMap.put("name", propertiesMap.get("testray.run.id"));
		bodyMap.put(
			"r_buildToRuns_c_buildId", String.valueOf(testrayBuildId));

		testrayRunId = _addEntity(bodyMap, "runs");

		String environmentHash = _getTestrayRunEnvironmentHash(
			element, testrayRunId);

		bodyMap = new HashMap<>();

		bodyMap.put("environmentHash", environmentHash);

		HttpUtil.invoke(
			new JSONObject(
				bodyMap
			).toString(),
			"runs/" + String.valueOf(testrayRunId), null, null,
			HttpInvoker.HttpMethod.PATCH);

		return testrayRunId;
	}

	private long _fetchOrAddTestrayProductVersion(
			long testrayProjectId, String testrayProductVersion)
		throws Exception {

		long testrayProductVersionId = _fetchEntityIdByName(
			"productversions", testrayProductVersion);

		if (testrayProductVersionId > 0) {
			return testrayProductVersionId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", testrayProductVersion);
		bodyMap.put(
			"r_projectToProductVersions_c_projectId",
			String.valueOf(testrayProjectId)
		);
		
		return _addEntity(bodyMap, "productversions");
	}

	private long _fetchOrAddTestrayProject(String testrayProjectName)
		throws Exception {

		long testrayProjectId = _fetchEntityIdByName(
			"projects", testrayProjectName);

		if (testrayProjectId > 0) {
			return testrayProjectId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", testrayProjectName);

		return _addEntity(bodyMap, "projects");
	}

	private long _fetchOrAddTestrayRoutine(
			long testrayProjectId, String routineName)
		throws Exception {

		long testrayRoutineId = _fetchEntityIdByName("routines", routineName);

		if (testrayRoutineId > 0) {
			return testrayRoutineId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", routineName);
		bodyMap.put(
			"r_routineToProjects_c_projectId",
			String.valueOf(testrayProjectId));

		return _addEntity(bodyMap, "routines");
	}

	private void _fetchOrAddTestrayTask(
		long testrayBuildId, String testrayTaskName)
		throws Exception {

		long testrayTaskId =
			_fetchEntityIdByName("tasks", testrayTaskName);

		if (testrayTaskId == 0) {
			Map<String, String> bodyMap = new HashMap<>();

			bodyMap.put(
				"dueStatus",
				String.valueOf(
					TestrayConstants.TESTRAY_CASE_RESULT_STATUS_IN_PROGRESS));
			bodyMap.put("name", testrayTaskName);
			bodyMap.put("r_buildToTasks_c_buildId",
				String.valueOf(testrayBuildId));
			bodyMap.put("statusUpdateDate",
				LocalDateTime.now(ZoneOffset.UTC).toString());

			_addEntity(bodyMap, "tasks");
		}
	}

	private long _fetchOrAddTestrayTeam(
			long testrayProjectId, String testrayTeamName)
		throws Exception {

		long testrayTeamId = _fetchEntityIdByName("teams", testrayTeamName);

		if (testrayTeamId > 0) {
			return testrayTeamId;
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", testrayTeamName);
		bodyMap.put(
			"r_projectToTeams_c_projectId", String.valueOf(testrayProjectId));

		return _addEntity(bodyMap, "teams");
	}

	private String _getAttributeValue(Node node, String attributeName) {
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

	private Map<String, String> _getProperties(Element element) {
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
				_getAttributeValue(propertyNode, "name"),
				_getAttributeValue(propertyNode, "value"));
		}

		return map;
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

			String propertyName = _getAttributeValue(propertyNode, "name");

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
					propertyName, _getAttributeValue(propertyNode, "value"));
			}
		}

		return map;
	}

	private String _getTestrayRunEnvironmentHash(
			Element rootElement, long testrayRunId)
		throws Exception {

		StringBuilder stringBuilder = new StringBuilder();

		NodeList environmentNodeList = rootElement.getElementsByTagName(
			"environment");

		for (int i = 0; i < environmentNodeList.getLength(); i++) {
			Node node = environmentNodeList.item(i);

			if (!node.hasAttributes()) {
				continue;
			}

			String factorCategoryName = _getAttributeValue(node, "type");
			String factorOptionName = _getAttributeValue(node, "option");

			long testrayFactorCategoryId = _fetchOrAddTestrayFactorCategory(
				factorCategoryName);

			long testrayFactorOptionId = _fetchOrAddTestrayFactorOption(
				factorOptionName, testrayFactorCategoryId);

			_addTestrayFactor(
				testrayRunId, testrayFactorCategoryId, factorCategoryName,
				testrayFactorOptionId, factorOptionName);

			stringBuilder.append(testrayFactorCategoryId);

			stringBuilder.append(testrayFactorOptionId);
		}

		String testrayFactorsString = stringBuilder.toString();

		return String.valueOf(testrayFactorsString.hashCode());
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

	private void _processResults(Document document) throws Exception {
		Element element = document.getDocumentElement();

		Map<String, String> propertiesMap = _getProperties(element);

		String testrayProjectName = propertiesMap.get("testray.project.name");

		long testrayProjectId = _fetchOrAddTestrayProject(testrayProjectName);

		long testrayBuildId = _fetchOrAddTestrayBuild(
			testrayProjectId, propertiesMap);

		long testrayRunId = _fetchOrAddTestrayRun(
			testrayBuildId, propertiesMap, element);
		
		_addTestrayCases(
			element, testrayBuildId, testrayProjectId, testrayRunId);

		_fetchOrAddTestrayTask(
			testrayBuildId, propertiesMap.get("testray.build.name"));
	}

	private Storage _getStorage() throws Exception {
		InputStream inputStream = PropsUtil.class.getResourceAsStream(
			PropsValues.TESTRAY_URL_API_KEY);

		GoogleCredentials credentials = GoogleCredentials.fromStream(
			inputStream);

		return StorageOptions.newBuilder(
		).setProjectId(
			PropsValues.TESTRAY_BUCKET_NAME
		).setCredentials(
			credentials
		).build(
		).getService();
	}

	private void _readFiles() throws Exception {
		Page<Blob> page = _storage.list(
			PropsValues.TESTRAY_BUCKET_NAME,
			Storage.BlobListOption.prefix("inbox"));
		
		for (Blob blob : page.iterateAll()) {
			String name = blob.getName();

			if(name.equals("inbox/")) {
				continue;
			}

			_unTarGzip(blob.getContent());
			
			blob.copyTo(PropsValues.TESTRAY_BUCKET_NAME,
				"done/"+ name.replace("inbox/", ""));
    		blob.delete();
		}
	}

	private void _unTarGzip(byte[] bytes) throws Exception {
		Path pathTempFile = null;
		Path pathTempDirectory = null;

		try {
			pathTempFile = Files.createTempFile(null, null);

			Files.write(pathTempFile, bytes);

			File tempFile = pathTempFile.toFile();

			pathTempDirectory = Files.createTempDirectory(null);

			File tempDirectory = pathTempDirectory.toFile();

			Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");

			try {
				archiver.extract(tempFile, tempDirectory);
			}
			catch (IOException ioException) {
				archiver = ArchiverFactory.createArchiver("tar");

				archiver.extract(tempFile, tempDirectory);
			}

			File[] files = tempDirectory.listFiles();

			for (File file : files) {
				try {
					Document document = _documentBuilder.parse(file);

					_processResults(document);
				}
				finally {
					file.delete();
				}
			}
		}
		finally {
			if (pathTempFile != null) {
				Files.deleteIfExists(pathTempFile);
			}

			if (pathTempDirectory != null) {
				Files.deleteIfExists(pathTempDirectory);
			}
		}
	}

	private final DocumentBuilder _documentBuilder;
	private final DocumentBuilderFactory _documentBuilderFactory;
	private final Storage _storage;
	private static final Logger _logger =
		Logger.getLogger(ImportResults.class.getName());

}