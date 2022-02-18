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
import java.io.*;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

/**
 * @author José Abelenda
 */
public class ImportResults {

	public static File[] unzipFiles(String URL_KEY){

		File archive = new File(_URL_KEY);
		File destination = new File("/home/me/Downloads/2022-02-test-1-9-test-portal-testsuite-upstream(master)-650-results/");

		try{
			Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");
			archiver.extract(archive, destination);
		}
		catch(Exception exception){
			exception.printStackTrace();
		}

		return destination.listFiles();

	}


	public static void addTestBuild(long groupId, int projectId, File file) {
		Map<String, String> map = new HashMap<>();

		map.put("testrayBuildId", String.valueOf(projectId));

		try {
			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(file);

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
							_BASE_URL + "testraybuilds/scopes/" + groupId, new JSONObject(map));
						}
					}
				}

			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void addTestCase(long groupId, int projectId, File file) {
		Map<String, String> map = new HashMap<>();

		map.put("testrayProjectId", String.valueOf(projectId));

		try {
			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(file);

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
					_BASE_URL + "testraycases/scopes/" + groupId, new JSONObject(map));


			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static int fetchOrAddProject(long groupId, File file) {
		Map<String, String> map = new HashMap<>();

		int projectId = -1;

		try {

			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(file);

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
				_BASE_URL + "testrayprojects/scopes/" + groupId);

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
				_BASE_URL + "testrayprojects/scopes/" + groupId,
				new JSONObject(map));

			projectId = response.getInt("id");

			return projectId;
		}

		return -1;
	}

	public static void getResults(String projectId) throws Exception {
		GoogleCredentials credentials = GoogleCredentials.fromStream(
			new FileInputStream("/home/me/Downloads/key.json"));

		Storage storage = StorageOptions.newBuilder(
		).setProjectId(
			projectId
		).setCredentials(
			credentials
		).build(
		).getService();

    	Page<Blob> blobsPage1 =
        storage.list(
            _BUCKET_NAME,
            Storage.BlobListOption.prefix(_BUCKET_FOLDER_NAME),
            Storage.BlobListOption.currentDirectory());

		for (Blob blob1 : blobsPage1.iterateAll()) {

			if (blob1.getName().endsWith("/")) {
				_BUCKET_FOLDER_NAME = "" + blob1.getName();

				getResults(projectId);
			} 
			if (blob1.getName().endsWith("results.tar.gz")) {
					blob1.downloadTo(Paths.get("/home/me/Downloads/key.xml"));
			}
			
		}}
	

	public static void main(String[] args) {
		long groupId = 42657L;
		getResults(_PROJECT_BUCKET_ID);
		File[] files = unzipFiles(_URL_KEY);
	 	for(int index = 0; index<files.length; index++){
			File file = files[index];
			int projectId = fetchOrAddProject(groupId, file);
			addTestCase(groupId, projectId, file);
			addTestBuild(groupId, projectId, file);
		}
	 }

	private static final String _BASE_URL = "http://localhost:8080/o/c/";

	private static final String _PROJECT_BUCKET_ID = "wise-aegis-340917";

	private static final String _BUCKET_NAME = "testeray";

	private static  String _BUCKET_FOLDER_NAME = "test1/";

	private static final String _URL_KEY =
		"/home/me/Downloads/2022-02-test-1-9-test-portal-testsuite-upstream(master)-650-results.tar.gz";

}