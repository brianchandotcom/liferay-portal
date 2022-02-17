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

import com.liferay.portal.kernel.util.HashMapBuilder;
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

/**
 * @author José Abelenda
 */
public class ImportResults {


	public static void addTestBuild(long groupId, int projectId) {
		Map<String, String> map = new HashMap<>();

		map.put("testrayBuildId", String.valueOf(projectId));

		try {
			File file = new File(_URL_KEY);
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

	public static void addTestCase(long groupId, int projectId) {
		Map<String, String> map = new HashMap<>();

		map.put("testrayProjectId", String.valueOf(projectId));

		try {
			File file = new File(_URL_KEY);
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

	public static int fetchOrAddProject(long groupId) {
		Map<String, String> map = null;

		int projectId = -1;

		try {
			File file = new File(_URL_KEY);

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

						map = HashMapBuilder.put(
							"description", name
						).put(
							"name", value
						).build();

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

		if ((projectId == -1) && (map != null)) {
			JSONObject response = HttpClient.post(
				_BASE_URL + "testrayprojects/scopes/" + groupId,
				new JSONObject(map));

			projectId = response.getInt("id");

			return projectId;
		}

		return -1;
	}

	public static void listBuckets(String projectId) throws Exception {
		GoogleCredentials credentials = GoogleCredentials.fromStream(
			new FileInputStream("/home/me/Downloads/key.json"));

		Storage storage = StorageOptions.newBuilder(
		).setProjectId(
			projectId
		).setCredentials(
			credentials
		).build(
		).getService();

		Page<Bucket> bucketsPage = storage.list();

		for (Bucket bucket : bucketsPage.iterateAll()) {
			System.out.println(bucket.getName());

			Page<Blob> blobsPage = storage.list(bucket.getName());

			for (Blob blob : blobsPage.iterateAll()) {
				System.out.println(blob.getName());

				blob.downloadTo(Paths.get(_URL_KEY));
			}
		}
	}

	public static void main(String[] args) {
		long groupId = 44059L;
		//listBuckets(_PROJECT_BUCKET_ID);
	 	int projectId = fetchOrAddProject(groupId);
	// 	addTestCase(groupId, projectId);
		addTestBuild(groupId, projectId);
	 }

	private static final String _BASE_URL = "http://localhost:8080/o/c/";

	private static final String _PROJECT_BUCKET_ID = "wise-aegis-340917";

	private static final String _URL_KEY =
		"/home/me/Downloads/key.xml";

}