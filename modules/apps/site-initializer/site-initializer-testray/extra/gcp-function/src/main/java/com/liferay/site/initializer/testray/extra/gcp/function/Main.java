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

import java.util.Collections;
import java.util.HashMap;
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

	private void _addTestrayFactor(
			long testrayRunId, long testrayCategoryId,
			String testrayFactorCategoryName, String testrayFactorOptionName,
			long testrayOptionId)
		throws Exception {

		_postObjectEntry(
			HashMapBuilder.put(
				"classNameId", String.valueOf(testrayRunId)
			).put(
				"classPK", String.valueOf(testrayRunId)
			).put(
				"r_factorCategoryToFactors_c_factorCategoryId",
				String.valueOf(testrayCategoryId)
			).put(
				"r_factorOptionToFactors_c_factorOptionId",
				String.valueOf(testrayOptionId)
			).put(
				"testrayFactorCategoryName", testrayFactorCategoryName
			).put(
				"testrayFactorOptionName",
				String.valueOf(testrayFactorOptionName)
			).build(),
			null, "factors");
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
			String testrayFactorOptionName, long testrayFactorCategoryId)
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
				testrayFactorOptionName, testrayFactorCategoryId);

			_addTestrayFactor(
				testrayRunId, testrayFactorCategoryId,
				testrayFactorCategoryName, testrayFactorOptionName,
				testrayFactorOptionId);

			sb.append(testrayFactorCategoryId);

			sb.append(testrayFactorOptionId);
		}

		String testrayFactorsString = sb.toString();

		return String.valueOf(testrayFactorsString.hashCode());
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

			Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");

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

		_getTestrayBuildId(propertiesMap, testrayBuildName, testrayProjectId);
	}

	private final String _liferayLogin;
	private final String _liferayPassword;
	private final String _liferayURL;
	private final Map<String, JSONObject> _objectEntryJSONObjects =
		new HashMap<>();
	private final String _s3APIKeyPath;
	private final String _s3BucketName;

}