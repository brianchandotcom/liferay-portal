/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.admin.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.nio.charset.Charset;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Stian Sigvartsen
 */
@RunWith(Arquillian.class)
public class BatchEngineImportTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testBatchEngineImport() throws Exception {
		try (TestEnvironment testEnvironment = new TestEnvironment(
				_companyLocalService, _configurationAdmin)) {

			testEnvironment.temporarilyClearSamlProviderConfiguration();
			testEnvironment.mountKeyStore();

			doTestBatchEngineImport();
		}
	}

	protected void doTestBatchEngineImport() throws Exception {
		JSONObject jsonObject = JSONUtil.put(
			"enabled", false
		).put(
			"entityId", "testSAMLEntity"
		);

		BatchEngineImportTask batchEngineImportTask = _getBatchEngineImportTask(
			jsonObject);

		Assert.assertEquals(
			BatchEngineTaskExecuteStatus.COMPLETED.name(),
			batchEngineImportTask.getExecuteStatus());

		jsonObject.put("keyStoreCredentialPassword", "WRONG");

		batchEngineImportTask = _getBatchEngineImportTask(jsonObject);

		Assert.assertEquals(
			BatchEngineTaskExecuteStatus.FAILED.name(),
			batchEngineImportTask.getExecuteStatus());

		AssertUtil.assertSigningCredentialExceptionMessage(
			batchEngineImportTask.getErrorMessage());

		jsonObject.put(
			"enabled", true
		).put(
			"keyStoreCredentialPassword", "testSAMLEntitySigning"
		);

		batchEngineImportTask = _getBatchEngineImportTask(jsonObject);

		Assert.assertEquals(
			BatchEngineTaskExecuteStatus.COMPLETED.name(),
			batchEngineImportTask.getExecuteStatus());

		Assert.assertEquals("", batchEngineImportTask.getErrorMessage());

		JSONObject spJSONObject = JSONUtil.put(
			"keyStoreEncryptionCredentialPassword", "WRONG");

		jsonObject.put("sp", spJSONObject);

		batchEngineImportTask = _getBatchEngineImportTask(jsonObject);

		Assert.assertEquals(
			BatchEngineTaskExecuteStatus.FAILED.name(),
			batchEngineImportTask.getExecuteStatus());

		AssertUtil.assertEncryptionCredentialExceptionMessage(
			batchEngineImportTask.getErrorMessage());

		spJSONObject.put(
			"keyStoreEncryptionCredentialPassword", "testSAMLEntityEncryption");

		batchEngineImportTask = _getBatchEngineImportTask(jsonObject);

		Assert.assertEquals("", batchEngineImportTask.getErrorMessage());

		Assert.assertEquals(
			BatchEngineTaskExecuteStatus.COMPLETED.name(),
			batchEngineImportTask.getExecuteStatus());
	}

	private BatchEngineImportTask _getBatchEngineImportTask(
			JSONObject jsonObject)
		throws Exception {

		UnsyncByteArrayOutputStream compressedUnsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				compressedUnsyncByteArrayOutputStream)) {

			zipOutputStream.putNextEntry(new ZipEntry("testdata.json"));

			String json = JSONUtil.put(
				jsonObject
			).toString();

			zipOutputStream.write(json.getBytes(Charset.defaultCharset()));
		}

		byte[] content = compressedUnsyncByteArrayOutputStream.toByteArray();
		String contentType = "json";

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				null, TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), 100, null,
				"com.liferay.saml.admin.rest.dto.v1_0.SamlProvider", content,
				StringUtil.toUpperCase(contentType),
				BatchEngineTaskExecuteStatus.INITIAL.name(), null,
				BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
				BatchEngineTaskOperation.CREATE.name(), null, null);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineImportTaskExecutorImpl",
				LoggerTestUtil.FATAL)) {

			_batchEngineImportTaskExecutor.execute(batchEngineImportTask);
		}

		return batchEngineImportTask;
	}

	@Inject
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Inject
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

}