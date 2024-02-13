/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.ExportTask;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.resource.v1_0.ExportTaskResource;
import com.liferay.headless.batch.engine.client.resource.v1_0.ImportTaskResource;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.zip.ZipInputStream;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class BatchExportImportPerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(Validator.isNull(System.getenv("JENKINS_HOME")));
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class<?> clazz = BatchExportImportPerformanceTest.class;

		Properties properties = PropertiesUtil.load(
			clazz.getResourceAsStream(
				"dependencies/batch-export-import-performance.properties"),
			"UTF-8");

		_recordsCount = GetterUtil.getInteger(
			properties.getProperty("records.count"));

		_logFilePath = Paths.get(properties.getProperty("log.file"));

		Files.deleteIfExists(_logFilePath);

		_writeToLogFile(
			"Properties:",
			StreamUtil.toString(
				clazz.getResourceAsStream(
					"dependencies/batch-export-import-performance.properties")),
			"\nResults:");

		_jsonTemplate = JSONUtil.put(
			"additionalName", ""
		).put(
			"alternateName", "[$ALTERNATE_NAME$]"
		).put(
			"birthDate", "1977-01-01T00:00:00Z"
		).put(
			"customFields", JSONFactoryUtil.createJSONArray()
		).put(
			"dashboardURL", ""
		).put(
			"dateCreated", "2021-05-19T16:04:46Z"
		).put(
			"dateModified", "2021-05-19T16:04:46Z"
		).put(
			"emailAddress", "[$EMAIL_ADDRESS$]"
		).put(
			"familyName", "[$FAMILY_NAME$]"
		).put(
			"givenName", "[$GIVEN_NAME$]"
		).put(
			"jobTitle", ""
		).put(
			"keywords", JSONFactoryUtil.createJSONArray()
		).put(
			"name", "[$GIVEN_NAME$] [$FAMILY_NAME$]"
		).put(
			"organizationBriefs", JSONFactoryUtil.createJSONArray()
		).put(
			"profileURL", ""
		).put(
			"roleBriefs",
			JSONUtil.put(
				JSONUtil.put(
					"id", 20113
				).put(
					"name", "User"
				))
		).put(
			"siteBriefs",
			JSONUtil.put(
				JSONUtil.merge(
					JSONUtil.put(
						"id", 20127
					).put(
						"name", "Global"
					),
					JSONUtil.put(
						"id", 20125
					).put(
						"name", "Guest"
					)))
		).put(
			"userAccountContactInformation",
			JSONUtil.put(
				"emailAddresses", JSONFactoryUtil.createJSONArray()
			).put(
				"facebook", ""
			).put(
				"postalAddresses", JSONFactoryUtil.createJSONArray()
			).put(
				"skype", ""
			).put(
				"sms", ""
			).put(
				"telephones", JSONFactoryUtil.createJSONArray()
			).put(
				"twitter", ""
			).put(
				"webUrls", JSONFactoryUtil.createJSONArray()
			)
		).toString();
	}

	@Test
	public void testImportAndExportTask() throws Exception {
		_testPostImportTask(
			"com.liferay.headless.admin.user.dto.v1_0.UserAccount");

		_testPostExportTask(
			"com.liferay.headless.admin.user.dto.v1_0.UserAccount");
	}

	private static void _writeToLogFile(String... contents) throws IOException {
		Files.write(
			_logFilePath, Arrays.asList(contents), StandardOpenOption.APPEND,
			StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	}

	private String _createBatchJSON(int usersCount) {
		StringBundler batchJsonSB = new StringBundler();

		batchJsonSB.append(StringPool.OPEN_BRACKET);

		for (int i = 0; i < usersCount; i++) {
			String alternateName = RandomTestUtil.randomString(
				8, UniqueStringRandomizerBumper.INSTANCE);

			String json = StringUtil.replace(
				_jsonTemplate, "[$ALTERNATE_NAME$]", alternateName);

			json = StringUtil.replace(
				json, "[$FAMILY_NAME$]",
				StringUtil.getTitleCase(
					RandomTestUtil.randomString(
						8, UniqueStringRandomizerBumper.INSTANCE),
					true, ""));

			json = StringUtil.replace(
				json, "[$GIVEN_NAME$]",
				StringUtil.getTitleCase(
					RandomTestUtil.randomString(
						8, UniqueStringRandomizerBumper.INSTANCE),
					true, ""));

			batchJsonSB.append(
				StringUtil.replace(
					json, "[$EMAIL_ADDRESS$]",
					StringBundler.concat(
						alternateName, "@", RandomTestUtil.randomString(),
						".com")));

			if (i < (usersCount - 1)) {
				batchJsonSB.append(StringPool.COMMA);
			}
		}

		batchJsonSB.append(StringPool.CLOSE_BRACKET);

		return batchJsonSB.toString();
	}

	private Map<String, String> _splitClassName(String className) {
		Map<String, String> classNamePartsMap = new HashMap<>();

		if (className.contains("#")) {
			String[] classNameParts = className.split("#");

			classNamePartsMap.put("className", classNameParts[0]);
			classNamePartsMap.put("taskItemDelegateName", classNameParts[1]);
		}
		else {
			classNamePartsMap.put("className", className);
		}

		return classNamePartsMap;
	}

	private Closeable _startTimer() {
		Thread thread = Thread.currentThread();

		StackTraceElement stackTraceElement = thread.getStackTrace()[2];

		String invokerName = StringBundler.concat(
			stackTraceElement.getClassName(), StringPool.POUND,
			stackTraceElement.getMethodName());

		long startTime = System.currentTimeMillis();

		return () -> {
			long totalTimeMillis = System.currentTimeMillis() - startTime;

			_writeToLogFile(
				StringBundler.concat(
					invokerName, " used ", totalTimeMillis, " ms, for ",
					_recordsCount, " records speed=",
					(double)(_recordsCount * 1000) / (double)totalTimeMillis,
					" records/s"));
		};
	}

	private void _testPostExportTask(String className) throws Exception {
		Map<String, String> classNamePartsMap = _splitClassName(className);

		try (Closeable closeable = _startTimer()) {
			ExportTaskResource.Builder builder = ExportTaskResource.builder();

			ExportTaskResource exportTaskResource = builder.authentication(
				"test@liferay.com", "test"
			).header(
				HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
			).build();

			ExportTask exportTask = exportTaskResource.postExportTask(
				classNamePartsMap.get("className"), "json", null, null, null,
				classNamePartsMap.get("taskItemDelegateName"));

			String externalReferenceCode =
				exportTask.getExternalReferenceCode();

			while (true) {
				exportTask =
					exportTaskResource.getExportTaskByExternalReferenceCode(
						externalReferenceCode);

				if (Objects.equals(
						exportTask.getExecuteStatusAsString(), "COMPLETED")) {

					break;
				}
				else if (Objects.equals(
							exportTask.getExecuteStatusAsString(), "FAILED")) {

					throw new AssertionError(exportTask.getErrorMessage());
				}
			}

			exportTaskResource = builder.authentication(
				"test@liferay.com", "test"
			).header(
				HttpHeaders.ACCEPT, ContentTypes.APPLICATION_OCTET_STREAM
			).build();

			HttpInvoker.HttpResponse httpResponse =
				exportTaskResource.
					getExportTaskByExternalReferenceCodeContentHttpResponse(
						externalReferenceCode);

			try (InputStream inputStream = new UnsyncByteArrayInputStream(
					httpResponse.getBinaryContent())) {

				ZipInputStream zipInputStream = new ZipInputStream(inputStream);

				zipInputStream.getNextEntry();

				StringUtil.read(zipInputStream);
			}
		}
	}

	private void _testPostImportTask(String className) throws Exception {
		Map<String, String> classNamePartsMap = _splitClassName(className);

		String json = _createBatchJSON(_recordsCount);

		JSONArray itemsJSONArray = _jsonFactory.createJSONArray(json);

		ImportTaskResource importTaskResource = ImportTaskResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).header(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON
		).build();

		try (Closeable closeable = _startTimer()) {
			ImportTask importTask = importTaskResource.postImportTask(
				classNamePartsMap.get("className"), null, "INSERT", null, null,
				null, classNamePartsMap.get("taskItemDelegateName"),
				itemsJSONArray);

			String externalReferenceCode =
				importTask.getExternalReferenceCode();

			while (true) {
				importTask =
					importTaskResource.getImportTaskByExternalReferenceCode(
						externalReferenceCode);

				if (Objects.equals(
						importTask.getExecuteStatusAsString(), "COMPLETED")) {

					break;
				}
				else if (Objects.equals(
							importTask.getExecuteStatusAsString(), "FAILED")) {

					throw new AssertionError(importTask.getErrorMessage());
				}
			}
		}
	}

	private static String _jsonTemplate;
	private static Path _logFilePath;
	private static int _recordsCount;

	@Inject
	private Http _http;

	@Inject
	private JSONFactory _jsonFactory;

	private final List<LogCapture> _logCaptures = new ArrayList<>();

}