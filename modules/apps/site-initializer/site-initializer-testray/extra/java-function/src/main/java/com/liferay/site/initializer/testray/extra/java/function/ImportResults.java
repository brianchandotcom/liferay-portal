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

	public void addTestrayBuild(int projectId, Document document) {
		Map<String, String> map = new HashMap<>();

		map.put("testrayBuildId", String.valueOf(projectId));

		try {
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

						if (name.equals("testray.build.name")) {
							String value = propertyNode.getAttributes(
							).getNamedItem(
								"value"
							).getTextContent();

							map.put("name", value);

							HttpUtil.invoke(
								new JSONObject(
									map
								).toString(),
								"testraybuilds", null, null,
								HttpInvoker.HttpMethod.POST);
						}
					}
				}
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void addTestrayCase(int projectId, Document document) {
		Map<String, String> map = new HashMap<>();

		map.put("testrayProjectId", String.valueOf(projectId));

		try {
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

						String value = node.getAttributes(
						).getNamedItem(
							"value"
						).getTextContent();

						if (name.equals("testray.testcase.priority")) {
							map.put("priority", value);
						}
						else if (name.equals("testray.testcase.name")) {
							map.put("name", value);
							map.put("stepsType", name);
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
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public int addTestrayProject(Document document) throws Exception {
		Map<String, String> map = new HashMap<>();

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

					map.put("description", name);
					map.put("name", value);

					break;
				}
			}
		}

		Map<String, String> parameters = new HashMap<>();

		parameters.put("filter", "name eq '" + projectName + "'");

		JSONObject responseJSONObject = HttpUtil.invoke(
			null, "testrayprojects", null, parameters,
			HttpInvoker.HttpMethod.GET);

		JSONArray projectsJSONArray = responseJSONObject.getJSONArray("items");

		if (!projectsJSONArray.isEmpty()) {
			JSONObject projectJSONObject = projectsJSONArray.getJSONObject(0);

			return projectJSONObject.getInt("id");
		}

		responseJSONObject = HttpUtil.invoke(
			new JSONObject(
				map
			).toString(),
			"testrayprojects", null, null, HttpInvoker.HttpMethod.POST);

		return responseJSONObject.getInt("id");
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

			int projectId = addTestrayProject(document);

			addTestrayBuild(projectId, document);
			addTestrayCase(projectId, document);
		}
	}

	private final DocumentBuilder _documentBuilder;
	private final DocumentBuilderFactory _documentBuilderFactory;
	private final Storage _storage;

}