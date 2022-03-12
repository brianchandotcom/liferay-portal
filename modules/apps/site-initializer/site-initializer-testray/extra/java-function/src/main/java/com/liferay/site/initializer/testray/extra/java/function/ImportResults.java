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

	public void addTestrayBuild(long projectId, Document document)
		throws Exception  {
		String runName = null;
		
		Map<String, String> map = new HashMap<>();

		map.put("testrayProjectId", String.valueOf(projectId));

		NodeList propertiesNodeList = document.getElementsByTagName(
			"properties");

		for (int i = 0; i < propertiesNodeList.getLength(); i++) {
			Node propertiesNode = propertiesNodeList.item(i);

			Element element = (Element)propertiesNode;

			NodeList propertyNodeList = element.getElementsByTagName(
				"property");

			for (int j = 0; j < propertyNodeList.getLength(); j++) {
				Node propertyNode = propertyNodeList.item(j);

				if ((propertyNode.getNodeType() == Node.ELEMENT_NODE) &&
					!propertyNode.getNodeName(
					).equals(
						"#text"
					) &&
					(propertyNode.getAttributes(
					).getLength() > 0)) {

					String name = propertyNode.getAttributes(
					).getNamedItem(
						"name"
					).getTextContent();

					String value = null;

					if (name.equals("testray.build.name")) {
						value = propertyNode.getAttributes(
						).getNamedItem(
							"value"
						).getTextContent();

						map.put("name", value);

					}
					else if (name.equals("testray.build.time")) {
						value = propertyNode.getAttributes(
						).getNamedItem(
							"value"
						).getTextContent();

						map.put("dueDate", value);

					}
					else if (name.equals("testray.build.type")) {
						value = propertyNode.getAttributes(
						).getNamedItem(
							"value"
						).getTextContent();

						long routineId = fetchOrAddTestrayRoutine(projectId, value);

						map.put("testrayRoutineId", String.valueOf(routineId));

					}
					else if (name.equals("testray.run.id")) {
						runName = propertyNode.getAttributes(
						).getNamedItem(
							"value"
						).getTextContent();
					}
				}
			}
		}

		JSONObject responseJSONObject = HttpUtil.invoke(
				new JSONObject(
					map
				).toString(),
				"testraybuilds", null, null, HttpInvoker.HttpMethod.POST);

		long buildId = responseJSONObject.getLong("id");

		if (runName != null){
			long runId = fetchOrAddTestrayRun(buildId,runName);

			System.out.println(runId);
		}
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

	public long fetchOrAddTestrayRun(long buildId, String runName) throws Exception {

		Map<String, String> parametersMap = new HashMap<>();

		parametersMap.put("filter", "name eq '" + runName + "'");

		JSONObject responseJSONObject = HttpUtil.invoke(
			null, "testrayruns", null, parametersMap,
			HttpInvoker.HttpMethod.GET);

		JSONArray runsJSONArray = responseJSONObject.getJSONArray("items");

		if (!runsJSONArray.isEmpty()) {
			JSONObject runJSONObject = runsJSONArray.getJSONObject(0);

			return runJSONObject.getLong("id");
		}

		Map<String, String> bodyMap = new HashMap<>();

		bodyMap.put("externalReferencePK", runName);
		bodyMap.put("name", runName);
		bodyMap.put("testrayBuildId", String.valueOf(buildId));

		responseJSONObject = HttpUtil.invoke(
			new JSONObject(
				bodyMap
			).toString(),
			"testrayruns", null, null, HttpInvoker.HttpMethod.POST);

	   	return responseJSONObject.getLong("id");
	}


	public long addTestrayProject(Document document) throws Exception {
		Map<String, String> bodyMap = new HashMap<>();

		Element element = document.getDocumentElement();

		element.normalize();

		NodeList nodeList = document.getElementsByTagName("property");

		String projectName = null;

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

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

				if (name.equals("testray.project.name")) {
					String value = node.getAttributes(
					).getNamedItem(
						"value"
					).getTextContent();

					projectName = value;

					bodyMap.put("description", name);
					bodyMap.put("name", value);

					break;
				}
			}
		}

		Map<String, String> parametersMap = new HashMap<>();

		parametersMap.put("filter", "name eq '" + projectName + "'");

		JSONObject responseJSONObject = HttpUtil.invoke(
			null, "testrayprojects", null, parametersMap,
			HttpInvoker.HttpMethod.GET);

		JSONArray projectsJSONArray = responseJSONObject.getJSONArray("items");

		if (!projectsJSONArray.isEmpty()) {
			JSONObject projectJSONObject = projectsJSONArray.getJSONObject(0);

			return projectJSONObject.getLong("id");
		}

		responseJSONObject = HttpUtil.invoke(
			new JSONObject(
				bodyMap
			).toString(),
			"testrayprojects", null, null, HttpInvoker.HttpMethod.POST);

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

			long projectId = addTestrayProject(document);

			addTestrayBuild(projectId, document);
			addTestrayCase(projectId, document);
		}
	}

	private final DocumentBuilder _documentBuilder;
	private final DocumentBuilderFactory _documentBuilderFactory;
	private final Storage _storage;

}