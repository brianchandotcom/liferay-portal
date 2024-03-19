/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ImportTaskSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Closeable;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class ImportTaskResourcePerformanceTest
	extends BaseTaskResourcePerformanceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseTaskResourcePerformanceTestCase.setUpClass();

		_jsonTemplates = LinkedHashMapBuilder.put(
			"com.liferay.headless.admin.user.dto.v1_0.UserAccount",
			_createUserAccountJSONTemplate()
		).put(
			"com.liferay.headless.batch.engine.resource.v1_0.test.DummyEntity",
			_createDummyEntityJSONTemplate()
		).build();
	}

	@Test
	public void testPostImportTaskWithDummyEntityDelegate() throws Exception {
		_testPostImportTask(
			"com.liferay.headless.batch.engine.resource.v1_0.test." +
				"DummyEntity#dummy-entity-performance-test");
	}

	@Test
	public void testPostImportTaskWithUserAccount() throws Exception {
		_testPostImportTask(
			"com.liferay.headless.admin.user.dto.v1_0.UserAccount");
	}

	private static String _createDummyEntityJSONTemplate() throws Exception {
		return JSONUtil.put(
			"intValue", "[$INT_VALUE$]"
		).put(
			"textValue", "[$TEXT_VALUE$]"
		).toString();
	}

	private static String _createUserAccountJSONTemplate() throws Exception {
		return JSONUtil.put(
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

	private String _createBatchJSON(String className, int recordsCount) {
		StringBundler batchJsonSB = new StringBundler();

		batchJsonSB.append(StringPool.OPEN_BRACKET);

		for (int i = 0; i < recordsCount; i++) {
			String alternateName = RandomTestUtil.randomString(
				8, UniqueStringRandomizerBumper.INSTANCE);

			String json = StringUtil.replace(
				_jsonTemplates.get(className), "[$ALTERNATE_NAME$]",
				alternateName);

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
				json, "[$EMAIL_ADDRESS$]",
				StringBundler.concat(
					alternateName, "@", RandomTestUtil.randomString(), ".com"));

			json = StringUtil.replace(
				json, "[$TEXT_VALUE$]",
				StringUtil.getTitleCase(
					RandomTestUtil.randomString(
						8, UniqueStringRandomizerBumper.INSTANCE),
					true, ""));

			json = StringUtil.replace(
				json, "\"[$INT_VALUE$]\"",
				String.valueOf(RandomTestUtil.nextInt()));

			batchJsonSB.append(json);

			if (i < (recordsCount - 1)) {
				batchJsonSB.append(StringPool.COMMA);
			}
		}

		batchJsonSB.append(StringPool.CLOSE_BRACKET);

		return batchJsonSB.toString();
	}

	private void _testPostImportTask(String className) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("ClassName: " + className);
		}

		Map<String, String> classNamePartsMap = splitClassName(className);

		String json = _createBatchJSON(
			classNamePartsMap.get("className"), recordsCount);

		try (Closeable closeable = startTimer(_log)) {
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
					getHttpResponseContent(
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
		ImportTaskResourcePerformanceTest.class);

	private static Map<String, String> _jsonTemplates;

}