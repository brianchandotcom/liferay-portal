/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.headless.batch.engine.client.dto.v1_0.ExportTask;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ExportTaskSerDes;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ImportTaskSerDes;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.performance.PerformanceTimer;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Path;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.zip.ZipInputStream;

import org.junit.Assert;
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
public class ExportImportTaskResourcePerformanceTest {

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
		_jsons = LinkedHashMapBuilder.put(
			"com.liferay.headless.admin.user.dto.v1_0.UserAccount",
			JSONUtil.put(
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
			).toString()
		).put(
			"com.liferay.headless.batch.engine.resource.v1_0.test.TestEntity",
			JSONUtil.put(
				"intValue", "[$INT_VALUE$]"
			).put(
				"textValue", "[$TEXT_VALUE$]"
			).toString()
		).build();

		Class<?> clazz = ExportImportTaskResourcePerformanceTest.class;

		_properties = PropertiesUtil.load(
			clazz.getResourceAsStream(
				"dependencies/export-import-task-resource-performance." +
					"properties"),
			"UTF-8");
	}

	@Test
	public void testPostExportTaskWithTestEntity() throws Exception {
		TestEntityBatchEngineTaskItemDelegate
			testEntityBatchEngineTaskItemDelegate =
				_getTestEntityBatchEngineTaskItemDelegate();

		int count = GetterUtil.getInteger(
			_properties.getProperty("test.entities.count"));

		testEntityBatchEngineTaskItemDelegate.generate(count);

		_testPostExportTask(
			"com.liferay.headless.batch.engine.resource.v1_0.test.TestEntity#" +
				"export-import-task-resource-performance-test-entities",
			count,
			GetterUtil.getLong(
				_properties.getProperty("test.entities.export.max.time")),
			GetterUtil.getLong(
				_properties.getProperty("test.entities.download.max.time")));
	}

	@Test
	public void testPostExportTaskWithUserAccount() throws Exception {
		int count = GetterUtil.getInteger(
			_properties.getProperty("user.accounts.count"));

		for (int i = 0; i < count; ++i) {
			UserTestUtil.addUser();
		}

		_testPostExportTask(
			"com.liferay.headless.admin.user.dto.v1_0.UserAccount", count,
			GetterUtil.getLong(
				_properties.getProperty("user.accounts.export.max.time")),
			GetterUtil.getLong(
				_properties.getProperty("user.accounts.download.max.time")));
	}

	@Test
	public void testPostImportTaskWithTestEntity() throws Exception {
		_testPostImportTask(
			"com.liferay.headless.batch.engine.resource.v1_0.test.TestEntity#" +
				"export-import-task-resource-performance-test-entities",
			GetterUtil.getInteger(
				_properties.getProperty("test.entities.count")),
			GetterUtil.getLong(
				_properties.getProperty("test.entities.import.max.time")));
	}

	@Test
	public void testPostImportTaskWithUserAccount() throws Exception {
		_testPostImportTask(
			"com.liferay.headless.admin.user.dto.v1_0.UserAccount",
			GetterUtil.getInteger(
				_properties.getProperty("user.accounts.count")),
			GetterUtil.getLong(
				_properties.getProperty("user.accounts.import.max.time")));
	}

	private String _createBatchJSON(String className, int count) {
		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);

		for (int i = 0; i < count; i++) {
			String alternateName = RandomTestUtil.randomString(
				8, UniqueStringRandomizerBumper.INSTANCE);

			String json = StringUtil.replace(
				_jsons.get(className), "[$ALTERNATE_NAME$]", alternateName);

			json = StringUtil.replace(
				json, "[$EMAIL_ADDRESS$]",
				StringBundler.concat(
					alternateName, "@", RandomTestUtil.randomString(), ".com"));
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
			json = StringUtil.replace(
				json, "\"[$INT_VALUE$]\"",
				String.valueOf(RandomTestUtil.nextInt()));
			json = StringUtil.replace(
				json, "[$TEXT_VALUE$]",
				StringUtil.getTitleCase(
					RandomTestUtil.randomString(
						8, UniqueStringRandomizerBumper.INSTANCE),
					true, ""));

			sb.append(json);

			if (i < (count - 1)) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

	private TestEntityBatchEngineTaskItemDelegate
		_getTestEntityBatchEngineTaskItemDelegate() {

		return (TestEntityBatchEngineTaskItemDelegate)
			_batchEngineTaskItemDelegateRegistry.getBatchEngineTaskItemDelegate(
				0,
				"com.liferay.headless.batch.engine.resource.v1_0.test." +
					"TestEntity",
				"export-import-task-resource-performance-test-entities");
	}

	private String _invoke(String url) throws IOException {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.userNameAndPassword("test@liferay.com:test");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);

		HttpInvoker.HttpResponse response = httpInvoker.invoke();

		Assert.assertEquals(200, response.getStatusCode());

		return response.getContent();
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

	private void _testPostExportTask(
			String className, int count, long maxExportTime,
			long maxDownloadTime)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Class name: " + className);
		}

		HttpInvoker httpInvoker = null;

		String externalReferenceCode = null;

		Map<String, String> classNamePartsMap = _splitClassName(className);

		try (TestEntityPerformanceTimer itemCountPerformanceTimer =
				new TestEntityPerformanceTimer(
					count, className + "#export", maxExportTime)) {

			httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.header(
				HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
			httpInvoker.userNameAndPassword("test@liferay.com:test");
			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			StringBundler sb = new StringBundler(
				classNamePartsMap.containsKey("taskItemDelegateName") ? 6 : 4);

			sb.append("http://localhost:8080/o/headless-batch-engine/v1.0");
			sb.append("/export-task/");
			sb.append(classNamePartsMap.get("className"));
			sb.append("/JSON");

			if (classNamePartsMap.containsKey("taskItemDelegateName")) {
				sb.append("?taskItemDelegateName=");
				sb.append(classNamePartsMap.get("taskItemDelegateName"));
			}

			httpInvoker.path(sb.toString());

			HttpInvoker.HttpResponse response = httpInvoker.invoke();

			ExportTask exportTask = ExportTaskSerDes.toDTO(
				response.getContent());

			externalReferenceCode = exportTask.getExternalReferenceCode();

			while (true) {
				exportTask = ExportTaskSerDes.toDTO(
					_invoke(
						"http://localhost:8080/o/headless-batch-engine/v1.0" +
							"/export-task/by-external-reference-code/" +
								externalReferenceCode));

				if (Objects.equals(
						exportTask.getExecuteStatusAsString(), "COMPLETED")) {

					break;
				}
				else if (Objects.equals(
							exportTask.getExecuteStatusAsString(), "FAILED")) {

					throw new AssertionError(exportTask.getErrorMessage());
				}
			}
		}

		try (TestEntityPerformanceTimer itemCountPerformanceTimer =
				new TestEntityPerformanceTimer(
					count, className + "#download", maxDownloadTime)) {

			httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.header(
				HttpHeaders.ACCEPT, ContentTypes.APPLICATION_OCTET_STREAM);
			httpInvoker.userNameAndPassword("test@liferay.com:test");
			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				StringBundler.concat(
					"http://localhost:8080/o/headless-batch-engine/v1.0",
					"/export-task/by-external-reference-code/",
					externalReferenceCode, "/content"));

			HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

			try (InputStream inputStream = new UnsyncByteArrayInputStream(
					httpResponse.getBinaryContent())) {

				ZipInputStream zipInputStream = new ZipInputStream(inputStream);

				zipInputStream.getNextEntry();

				StringUtil.read(zipInputStream);
			}
		}
	}

	private void _testPostImportTask(String className, int count, long maxTime)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("ClassName: " + className);
		}

		Map<String, String> classNamePartsMap = _splitClassName(className);

		String json = _createBatchJSON(
			classNamePartsMap.get("className"), count);

		try (Closeable closeable = new TestEntityPerformanceTimer(
				count, className, maxTime)) {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(json, "application/json");
			httpInvoker.userNameAndPassword("test@liferay.com:test");
			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			StringBundler sb = new StringBundler(
				classNamePartsMap.containsKey("taskItemDelegateName") ? 6 : 4);

			sb.append("http://localhost:8080/o/headless-batch-engine/v1.0");
			sb.append("/import-task/");
			sb.append(classNamePartsMap.get("className"));
			sb.append("?createStrategy=INSERT");

			if (classNamePartsMap.containsKey("taskItemDelegateName")) {
				sb.append("&taskItemDelegateName=");
				sb.append(classNamePartsMap.get("taskItemDelegateName"));
			}

			httpInvoker.path(sb.toString());

			HttpInvoker.HttpResponse response = httpInvoker.invoke();

			ImportTask importTask = ImportTaskSerDes.toDTO(
				response.getContent());

			String externalReferenceCode =
				importTask.getExternalReferenceCode();

			while (true) {
				importTask = ImportTaskSerDes.toDTO(
					_invoke(
						"http://localhost:8080/o/headless-batch-engine/v1.0" +
							"/import-task/by-external-reference-code/" +
								externalReferenceCode));

				if (Objects.equals(
						importTask.getExecuteStatusAsString(), "COMPLETED")) {

					break;
				}
				else if (Objects.equals(
							importTask.getExecuteStatusAsString(), "FAILED")) {

					throw new AssertionError(importTask.getErrorMessage());
				}
			}

			Date endTime = importTask.getEndTime();
			Date startTime = importTask.getStartTime();

			_log.info(
				"Import task duration: " +
					(endTime.getTime() - startTime.getTime()) + " ms");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportTaskResourcePerformanceTest.class);

	private static Map<String, String> _jsons;
	private static Properties _properties;

	@Inject
	private BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;

	@Inject
	private UserService _userService;

	private class TestEntityPerformanceTimer extends PerformanceTimer {

		public TestEntityPerformanceTimer(
			int count, String name, long maxTime) {

			this(
				count, getInvokerName(null, name), System.currentTimeMillis(),
				maxTime, null);
		}

		public TestEntityPerformanceTimer(
			int count, String name, long maxTime, Path logFilePath) {

			this(
				count, getInvokerName(null, name), System.currentTimeMillis(),
				maxTime, logFilePath);
		}

		@Override
		public void close() {
			long delta = System.currentTimeMillis() - startTime;

			double speed =
				(delta > 0) ? (double)(_count * 1000) / (double)delta :
					Double.NaN;

			log(
				StringBundler.concat(
					"Completed ", name, " in ", delta, " ms, speed: ",
					String.format("%.2f", speed), " items/s"));

			Assert.assertTrue(
				StringBundler.concat(
					"Completed in ", delta,
					"ms, but the expected completion time should be less than ",
					maxTime, "ms"),
				delta < maxTime);
		}

		protected TestEntityPerformanceTimer(
			int count, String name, long startTime, long maxTime,
			Path logFilePath) {

			super(name, startTime, maxTime, logFilePath);

			_count = count;
		}

		private int _count;

	}

}