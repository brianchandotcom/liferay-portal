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
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.liferay.util.HttpClient;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * @author José Abelenda
 */
public class ImportResults {

	public ImportResults(long groupId) throws Exception {
		_storage = getStorage();

		_documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		_documentBuilder =
			_documentBuilderFactory.newDocumentBuilder();

		_groupId = groupId;
	}

	public void addTestBuild(int projectId, Document document) {
		Map<String, String> map = new HashMap<>();

		map.put("testrayBuildId", String.valueOf(projectId));

		try {
			NodeList testcases = document.getElementsByTagName("properties");

			for (int i = 0; i < testcases.getLength(); i++) {
				Node testCase = testcases.item(i);

				Element eElement = (Element) testCase;

				NodeList properties = eElement.getElementsByTagName("property");

				for(int property = 0; property<properties.getLength();property++){

					Node node = properties.item(property);

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

						if (name.equals("testray.build.name")) {
							map.put("name", value);

						HttpClient.post(
							_BASE_URL + "testraybuilds/scopes/" + _groupId, new JSONObject(map));
						}
					}
				}

			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void addTestCase(int projectId, Document document) {
		Map<String, String> map = new HashMap<>();

		map.put("testrayProjectId", String.valueOf(projectId));

		try {
			NodeList testcases = document.getElementsByTagName("testcase");

			for (int i = 0; i < testcases.getLength(); i++) {
				Node testCase = testcases.item(i);

				Element eElement = (Element) testCase;

				NodeList properties = eElement.getElementsByTagName("property");

				for(int property = 0; property<properties.getLength();property++){

					Node node = properties.item(property);

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

				HttpClient.post(
					_BASE_URL + "testraycases/scopes/" + _groupId, new JSONObject(map));


			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public int fetchOrAddProject(Document document) {
		Map<String, String> map = new HashMap<>();

		int projectId = -1;

		try {
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

					String value = node.getAttributes(
					).getNamedItem(
						"value"
					).getTextContent();

					if (name.equals("testray.project.name")) {
						projectName = value;

						map.put("description", name);
						map.put("name", value);

						break;
					}
				}
			}

			JSONObject response = HttpClient.get(
				_BASE_URL + "testrayprojects/scopes/" + _groupId);

			JSONArray projects = response.getJSONArray("items");

			for (int i = 0; i < projects.length(); i++) {
				JSONObject project = projects.getJSONObject(i);

				if (project.getString(
						"name"
					).equals(
						projectName
					)) {

					projectId = project.getInt("id");

					return projectId;
				}
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}

		if ((projectId == -1) && (!map.isEmpty())) {
			JSONObject response = HttpClient.post(
				_BASE_URL + "testrayprojects/scopes/" + _groupId,
				new JSONObject(map));

			projectId = response.getInt("id");

			return projectId;
		}

		return -1;
	}

	public Storage getStorage() throws Exception {
		GoogleCredentials credentials = GoogleCredentials.fromStream(
			new FileInputStream(_URL_API_KEY));

		return StorageOptions.newBuilder(
		).setProjectId(
			_BUCKET_NAME
		).setCredentials(
			credentials
		).build(
		).getService();
	}

	public void readFiles(String folderName) throws Exception {
		Page<Blob> page;

		if(folderName == null) {
			page = _storage.list(_BUCKET_NAME,  Storage.BlobListOption.currentDirectory());
		}
		else {
    		page = _storage.list(_BUCKET_NAME, Storage.BlobListOption.prefix(folderName), Storage.BlobListOption.currentDirectory());
		}

		for (Blob blob : page.iterateAll()) {
			if (blob.getName().endsWith("results.tar.gz")) {
				if(_storage.get(_BUCKET_NAME, blob.getName().replace("results.tar.gz", ".lfr-testray-completed")) != null) {
					_unTarGzip(blob.getName(), blob.getContent());
				}

				continue;
			}

			if (blob.getName().endsWith("/")) {
				folderName = blob.getName().replace(folderName,"");

				if(!folderName.equals("")){
					readFiles(folderName);
				}
			}
		}
	}

	private void _unTarGzip(String fileName, byte[] bytes) throws Exception {
		System.out.println("Processing " + fileName);

		Path pathTempFile = Files.createTempFile(null, null);
		
		Files.write(pathTempFile, bytes);
		
		File tempFile = pathTempFile.toFile();
		
		Path pathTempDirectory = Files.createTempDirectory(null);

		File tempDirectory = pathTempDirectory.toFile();

		Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");

		archiver.extract(tempFile, tempDirectory);

		File[] files = tempDirectory.listFiles();
			
		for(File file : files) {
			System.out.println("\t" + file);

			Document document = _documentBuilder.parse(file);

			int projectId = fetchOrAddProject(document);

			addTestBuild(projectId, document);
			addTestCase(projectId, document);
		}
	}
	
	public static void main(String[] args) {
		try {
			long groupId = 44357L;

			ImportResults importResults = new ImportResults(44357L);

			importResults.readFiles("");
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	private final DocumentBuilderFactory _documentBuilderFactory;
	
	private final DocumentBuilder _documentBuilder;
	
	private final Long _groupId;

	private final Storage _storage;
 
	private final String _BASE_URL = "http://localhost:8080/o/c/";
	 
	private final String _PROJECT_BUCKET_ID = "wise-aegis-340917";
	
	private final String _BUCKET_NAME = "testray-test";
	
	private String _BUCKET_FOLDER_NAME = "/";

	private final String _URL_API_KEY = "/Users/joseabelenda/temp/ictusweb.json";

	private final String _URL_KEY =
		"/home/me/Downloads/2022-02-test-1-9-test-portal-testsuite-upstream(master)-650-results.tar.gz";
}