/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.batch.engine.action.test.util;

import com.liferay.headless.admin.taxonomy.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.dto.v1_0.ExportTask;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ExportTaskSerDes;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ImportTaskSerDes;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.junit.Assert;

/**
 * @author Jürgen Kappler
 */
public class ExportImportTaskResourceTestUtil {

	public static String executeExportTask(String className, long groupId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.header(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path(
			StringBundler.concat(
				"http://localhost:8080/o/headless-batch-engine/v1.0",
				"/export-task/", className, "/JSON?siteId=", groupId));
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		ExportTask exportTask = ExportTaskSerDes.toDTO(
			httpResponse.getContent());

		String externalReferenceCode = exportTask.getExternalReferenceCode();

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

		httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.header(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_OCTET_STREAM);
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(
			StringBundler.concat(
				"http://localhost:8080/o/headless-batch-engine/v1.0",
				"/export-task/by-external-reference-code/",
				externalReferenceCode, "/content"));
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		httpResponse = httpInvoker.invoke();

		try (InputStream inputStream = new UnsyncByteArrayInputStream(
				httpResponse.getBinaryContent())) {

			ZipInputStream zipInputStream = new ZipInputStream(inputStream);

			zipInputStream.getNextEntry();

			return StringUtil.read(zipInputStream);
		}
	}

	public static void executeImportTask(
			String className, String createStrategy, long groupId,
			String importCreatorStrategy, String jsonString)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(jsonString, "application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			StringBundler.concat(
				"http://localhost:8080/o/headless-batch-engine/v1.0",
				"/import-task/", className, "?createStrategy=", createStrategy,
				"&importCreatorStrategy=", importCreatorStrategy, "&siteId=",
				groupId));

		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		ImportTask importTask = ImportTaskSerDes.toDTO(
			httpResponse.getContent());

		String externalReferenceCode = importTask.getExternalReferenceCode();

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
	}

	private static String _invoke(String url) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		Assert.assertEquals(200, httpResponse.getStatusCode());

		return httpResponse.getContent();
	}

}