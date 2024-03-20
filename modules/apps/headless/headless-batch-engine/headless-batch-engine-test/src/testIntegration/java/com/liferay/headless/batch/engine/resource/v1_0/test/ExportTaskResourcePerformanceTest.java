/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.headless.batch.engine.client.dto.v1_0.ExportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ExportTaskSerDes;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.Closeable;
import java.io.InputStream;

import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class ExportTaskResourcePerformanceTest
	extends BaseTaskResourcePerformanceTestCase {

	@Test
	public void testPostExportTaskWithDummyEntityDelegate() throws Exception {
		DummyEntityPerformanceTestBatchEngineTaskItemDelegate
			userAccountPerformanceTestBatchEngineTaskItemDelegate =
				(DummyEntityPerformanceTestBatchEngineTaskItemDelegate)
					_batchEngineTaskItemDelegateRegistry.
						getBatchEngineTaskItemDelegate(
							0,
							"com.liferay.headless.batch.engine.resource.v1_0." +
								"test.DummyEntity",
							"dummy-entity-performance-test");

		userAccountPerformanceTestBatchEngineTaskItemDelegate.generateTestData(
			recordsCount);

		_testPostExportTask(
			"com.liferay.headless.batch.engine.resource.v1_0.test." +
				"DummyEntity#dummy-entity-performance-test");
	}

	@Test
	public void testPostExportTaskWithUserAccount() throws Exception {
		_generateTestUserAccounts(recordsCount);

		_testPostExportTask(
			"com.liferay.headless.admin.user.dto.v1_0.UserAccount");
	}

	private void _generateTestUserAccounts(int count) throws Exception {
		for (int i = 0; i < count; ++i) {
			UserTestUtil.addUser();
		}
	}

	private void _testPostExportTask(String className) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("ClassName: " + className);
		}

		HttpInvoker httpInvoker = null;

		String externalReferenceCode = null;

		Map<String, String> classNamePartsMap = splitClassName(className);

		try (Closeable closeable = startTimer(_log, "export_items")) {
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
					getHttpResponseContent(
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

		try (Closeable closeable = startTimer(_log, "download")) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		ExportTaskResourcePerformanceTest.class);

	@Inject
	private BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;

	@Inject
	private UserService _userService;

}