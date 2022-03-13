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

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;

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

			importResults.readFiles("");
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public ImportResults() throws Exception {
		_storage = getStorage();

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

	private void _processResults(Document document) throws Exception {
		Element element = document.getDocumentElement();

		Map<String, String> propertiesMap = _getProperties(element);

		String testrayProjectName = propertiesMap.get("testray.project.name");

		long testrayProjectId = _fetchOrAddTestrayProject(testrayProjectName);

		long testrayBuildId = _fetchOrAddTestrayBuild(
			testrayProjectId, propertiesMap);

		long testrayRunId = _fetchOrAddTestrayRun(
			testrayBuildId, propertiesMap, element);
	}

	public void addTestrayCase(long projectId, Document document)
		throws Exception {

		String componentName = null;
		
		Map<String, String> map = new HashMap<>();

		map.put("testrayProjectId", String.valueOf(projectId));
		
		NodeList testCasesNodeList = document.getElementsByTagName(
			"testcase");

		for (int i = 0; i < testCasesNodeList.getLength(); i++) {
			Node testCaseNode = testCasesNodeList.item(i);

			Element element = (Element)testCaseNode;

			NodeList propertyNodeList = element.getElementsByTagName(
				"property");

			for (int j = 0; j < propertyNodeList.getLength(); j++) {
				Node node = propertyNodeList.item(j);

				if ((node.getNodeType() == Node.ELEMENT_NODE) &&
					!node.getNodeName(
					).equals(
						"#text"
					) &&
					(node.getAttributes(
					).getLength() > 0)) {

					String name = node.getAttributes(
					).getNamedItem(
						"name"
					).getTextContent();

					String value = null;

					if (name.equals("testray.main.component.name")) {
						componentName = node.getAttributes(
						).getNamedItem(
							"value"
						).getTextContent();
					}
					else if (name.equals("testray.team.name")) {
						value = node.getAttributes(
						).getNamedItem(
							"value"
						).getTextContent();
						
						long teamId = fetchOrAddTestrayTeam(projectId, value);
						long componentId = fetchOrAddTestrayComponent(
							projectId, teamId, componentName);

						map.put("testrayComponentId", String.valueOf(componentId));
					}
					else if (name.equals("testray.testcase.name")) {
						value = node.getAttributes(
						).getNamedItem(
							"value"
						).getTextContent();
						map.put("name", value);

						//TODO figure out what it means
						map.put("stepsType", name);
					}
					else if (name.equals("testray.testcase.priority")) {
						value = node.getAttributes(
						).getNamedItem(
							"value"
						).getTextContent();
						map.put("priority", value);
					}
				}
			}

			HttpUtil.invoke(
				new JSONObject(
					map
				).toString(),
				"testraycases", null, null, HttpInvoker.HttpMethod.POST);
		}
	}

	public long fetchOrAddTestrayComponent(long projectId, long teamId,
		String componentName) throws Exception {

		Map<String, String> parametersMap = new HashMap<>();

		parametersMap.put("filter", "name eq '" + componentName + "'");

		JSONObject responseJSONObject = HttpUtil.invoke(
			null, "testraycomponents", null, parametersMap,
			HttpInvoker.HttpMethod.GET);

		JSONArray componentsJSONArray = responseJSONObject.getJSONArray("items");

		if (!componentsJSONArray.isEmpty()) {
			JSONObject componentJSONObject = componentsJSONArray.getJSONObject(0);

			return componentJSONObject.getLong("id");
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", componentName);
		bodyMap.put("testrayProjectId", String.valueOf(projectId));
		bodyMap.put("testrayTeamId", String.valueOf(teamId));

		responseJSONObject = HttpUtil.invoke(
			new JSONObject(
				bodyMap
			).toString(),
			"testraycomponents", null, null, HttpInvoker.HttpMethod.POST);

	   	return responseJSONObject.getLong("id");
	}

	public long fetchOrAddTestrayRoutine(long projectId,
		String routineName) throws Exception {

		Map<String, String> parametersMap = new HashMap<>();

		parametersMap.put("filter", "name eq '" + routineName + "'");

		JSONObject responseJSONObject = HttpUtil.invoke(
			null, "testrayroutines", null, parametersMap,
			HttpInvoker.HttpMethod.GET);

		JSONArray routinesJSONArray = responseJSONObject.getJSONArray("items");

		if (!routinesJSONArray.isEmpty()) {
			JSONObject routineJSONObject = routinesJSONArray.getJSONObject(0);

			return routineJSONObject.getLong("id");
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", routineName);
		bodyMap.put("testrayProjectId", String.valueOf(projectId));

		responseJSONObject = HttpUtil.invoke(
			new JSONObject(
				bodyMap
			).toString(),
			"testrayroutines", null, null, HttpInvoker.HttpMethod.POST);

	   	return responseJSONObject.getLong("id");
	}

	public Storage getStorage() throws Exception {
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

	public long fetchOrAddTestrayTeam(long projectId, String teamName) throws Exception {
		Map<String, String> parametersMap = new HashMap<>();

		parametersMap.put("filter", "name eq '" + teamName + "'");

		JSONObject responseJSONObject = HttpUtil.invoke(
			null, "testrayteams", null, parametersMap, HttpInvoker.HttpMethod.GET);

		JSONArray teamsJSONArray = responseJSONObject.getJSONArray("items");

		if (!teamsJSONArray.isEmpty()) {
			JSONObject teamJSONObject = teamsJSONArray.getJSONObject(0);

			return teamJSONObject.getLong("id");
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("name", teamName);
		bodyMap.put("testrayProjectId", String.valueOf(projectId));

		responseJSONObject = HttpUtil.invoke(
			new JSONObject(
				bodyMap
			).toString(),
			"testrayteams", null, null, HttpInvoker.HttpMethod.POST);

		return responseJSONObject.getLong("id");
	}

	public void readFiles(String folderName) throws Exception {
		Page<Blob> page;

		if (folderName == null) {
			page = _storage.list(
				PropsValues.TESTRAY_BUCKET_NAME,
				Storage.BlobListOption.currentDirectory());
		}
		else {
			page = _storage.list(
				PropsValues.TESTRAY_BUCKET_NAME,
				Storage.BlobListOption.prefix(folderName),
				Storage.BlobListOption.currentDirectory());
		}

		for (Blob blob : page.iterateAll()) {
			if (blob.getName(
				).endsWith(
					"results.tar.gz"
				)) {

				Blob lfrTestrayCompletedBlod = _storage.get(
					PropsValues.TESTRAY_BUCKET_NAME,
					blob.getName(
					).replace(
						"results.tar.gz", ".lfr-testray-completed"
					));

				if (lfrTestrayCompletedBlod != null) {
					_unTarGzip(blob.getContent());
				}

				continue;
			}

			if (blob.getName(
				).endsWith(
					"/"
				)) {

				folderName = blob.getName(
				).replace(
					folderName, ""
				);

				if (!folderName.equals("")) {
					readFiles(folderName);
				}
			}
		}
	}

	private void _unTarGzip(byte[] bytes) throws Exception {
		Path pathTempFile = Files.createTempFile(null, null);

		Files.write(pathTempFile, bytes);

		File tempFile = pathTempFile.toFile();

		Path pathTempDirectory = Files.createTempDirectory(null);

		File tempDirectory = pathTempDirectory.toFile();

		Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");

		archiver.extract(tempFile, tempDirectory);

		File[] files = tempDirectory.listFiles();

		for (File file : files) {
			Document document = _documentBuilder.parse(file);

			_processResults(document);
		}
	}

	private final DocumentBuilder _documentBuilder;
	private final DocumentBuilderFactory _documentBuilderFactory;
	private final Storage _storage;

}