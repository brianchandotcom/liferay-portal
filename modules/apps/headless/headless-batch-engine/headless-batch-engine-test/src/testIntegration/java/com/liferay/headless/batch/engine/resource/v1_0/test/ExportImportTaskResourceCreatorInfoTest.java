/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.ExportTask;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ExportTaskSerDes;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ImportTaskSerDes;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalInstances;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class ExportImportTaskResourceCreatorInfoTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectDefinitionTestUtil.getRandomName(),
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
					false)),
			ObjectDefinitionConstants.SCOPE_COMPANY,
			TestPropsValues.getUserId());

		_user = UserTestUtil.addUser();

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectDefinitionTestUtil.getRandomName(),
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
					false)),
			ObjectDefinitionConstants.SCOPE_COMPANY, _user.getUserId());

		_executeExportTask();

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition1);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition2);
	}

	@Test
	public void testImportWithInsertAndKeepCreator() throws Exception {
		_executeImportTask("INSERT", "KEEP_CREATOR");

		_objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition1.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());
		_objectDefinition2 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition2.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition1.getUserId());
		Assert.assertEquals(_user.getUserId(), _objectDefinition2.getUserId());
	}

	@Test
	public void testImportWithInsertAndKeepCreatorInDifferentCompany()
		throws Exception {

		Company company = CompanyLocalServiceUtil.addCompany(
			null, _VIRTUAL_HOST_NAME, _VIRTUAL_HOST_NAME, _VIRTUAL_HOST_NAME, 0,
			true, true, null, null, null, null, null, null);

		PortalInstances.initCompany(company);

		_executeImportTask("INSERT", _VIRTUAL_HOST_NAME, "KEEP_CREATOR");

		_objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition1.getExternalReferenceCode(),
					company.getCompanyId());
		_objectDefinition2 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition2.getExternalReferenceCode(),
					company.getCompanyId());

		User user = UserTestUtil.getAdminUser(company.getCompanyId());

		Assert.assertEquals(user.getUserId(), _objectDefinition1.getUserId());
		Assert.assertEquals(user.getUserId(), _objectDefinition2.getUserId());
	}

	@Test
	public void testImportWithInsertAndKeepCreatorUserDoesNotExist()
		throws Exception {

		_userLocalService.deleteUser(_user);

		_executeImportTask("INSERT", "KEEP_CREATOR");

		_objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition1.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());
		_objectDefinition2 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition2.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition1.getUserId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition2.getUserId());
	}

	@Test
	public void testImportWithInsertAndKeepCreatorUserExistByExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = _user.getExternalReferenceCode();

		_userLocalService.deleteUser(_user);

		_user = UserTestUtil.addUser();

		_user.setExternalReferenceCode(externalReferenceCode);

		_user = _userLocalService.updateUser(_user);

		_executeImportTask("INSERT", "KEEP_CREATOR");

		_objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition1.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());
		_objectDefinition2 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition2.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition1.getUserId());
		Assert.assertEquals(_user.getUserId(), _objectDefinition2.getUserId());
	}

	@Test
	public void testImportWithInsertAndOverwriteCreator() throws Exception {
		_executeImportTask("INSERT", "OVERWRITE_CREATOR");

		_objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition1.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());
		_objectDefinition2 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition2.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition1.getUserId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition2.getUserId());
	}

	@Test
	public void testImportWithUpsertAndKeepCreator() throws Exception {
		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				TestPropsValues.getUserId());

		_objectDefinitionLocalService.updateExternalReferenceCode(
			objectDefinition1.getObjectDefinitionId(),
			_objectDefinition1.getExternalReferenceCode());

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				TestPropsValues.getUserId());

		_objectDefinitionLocalService.updateExternalReferenceCode(
			objectDefinition2.getObjectDefinitionId(),
			_objectDefinition2.getExternalReferenceCode());

		_executeImportTask("UPSERT", "KEEP_CREATOR");

		_objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition1.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());
		_objectDefinition2 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition2.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition1.getUserId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition2.getUserId());
	}

	@Test
	public void testImportWithUpsertAndKeepCreatorAndObjectEntriesDoNotExist()
		throws Exception {

		_executeImportTask("UPSERT", "KEEP_CREATOR");

		_objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition1.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());
		_objectDefinition2 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition2.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition1.getUserId());
		Assert.assertEquals(_user.getUserId(), _objectDefinition2.getUserId());
	}

	@Test
	public void testImportWithUpsertAndOverwriteCreator() throws Exception {
		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				TestPropsValues.getUserId());

		_objectDefinitionLocalService.updateExternalReferenceCode(
			objectDefinition1.getObjectDefinitionId(),
			_objectDefinition1.getExternalReferenceCode());

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(),
						"x" + RandomTestUtil.randomString(), false)),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				TestPropsValues.getUserId());

		_objectDefinitionLocalService.updateExternalReferenceCode(
			objectDefinition2.getObjectDefinitionId(),
			_objectDefinition2.getExternalReferenceCode());

		_executeImportTask("UPSERT", "OVERWRITE_CREATOR");

		_objectDefinition1 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition1.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());
		_objectDefinition2 =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					_objectDefinition2.getExternalReferenceCode(),
					TestPropsValues.getCompanyId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition1.getUserId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectDefinition2.getUserId());
	}

	private void _executeExportTask() throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.header(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path(
			StringBundler.concat(
				"http://localhost:8080/o/headless-batch-engine/v1.0",
				"/export-task/com.liferay.object.admin.rest.dto.v1_0.",
				"ObjectDefinition/JSON?filter=",
				URLCodec.encodeURL(
					StringBundler.concat(
						"name in ('", _objectDefinition1.getShortName(), "','",
						_objectDefinition2.getShortName(), "')"))));
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		ExportTask exportTask = ExportTaskSerDes.toDTO(
			httpResponse.getContent());

		String externalReferenceCode = exportTask.getExternalReferenceCode();

		while (true) {
			exportTask = ExportTaskSerDes.toDTO(
				_invoke(
					"localhost",
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

			_json = StringUtil.read(zipInputStream);
		}
	}

	private void _executeImportTask(
			String createStrategy, String importCreatorStrategy)
		throws Exception {

		_executeImportTask(createStrategy, "localhost", importCreatorStrategy);
	}

	private void _executeImportTask(
			String createStrategy, String host, String importCreatorStrategy)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(_json, "application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			StringBundler.concat(
				"http://", host, ":8080/o/headless-batch-engine/v1.0",
				"/import-task/com.liferay.object.admin.rest.dto.v1_0.",
				"ObjectDefinition?createStrategy=", createStrategy,
				"&importCreatorStrategy=", importCreatorStrategy));

		if (StringUtil.equals(host, "localhost")) {
			httpInvoker.userNameAndPassword(
				"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);
		}
		else {
			httpInvoker.userNameAndPassword(
				StringBundler.concat(
					"test@", host, ":", PropsValues.DEFAULT_ADMIN_PASSWORD));
		}

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		ImportTask importTask = ImportTaskSerDes.toDTO(
			httpResponse.getContent());

		String externalReferenceCode = importTask.getExternalReferenceCode();

		while (true) {
			importTask = ImportTaskSerDes.toDTO(
				_invoke(
					host,
					StringBundler.concat(
						"http://", host, ":8080/o/headless-batch-engine/v1.0",
						"/import-task/by-external-reference-code/",
						externalReferenceCode)));

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

	private String _invoke(String host, String url) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);

		if (StringUtil.equals(host, "localhost")) {
			httpInvoker.userNameAndPassword(
				"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);
		}
		else {
			httpInvoker.userNameAndPassword(
				StringBundler.concat(
					"test@", host, ":", PropsValues.DEFAULT_ADMIN_PASSWORD));
		}

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		Assert.assertEquals(200, httpResponse.getStatusCode());

		return httpResponse.getContent();
	}

	private static final String _OBJECT_FIELD_NAME_TEXT = "testFieldName";

	private static final String _VIRTUAL_HOST_NAME = "www.able.com";

	private String _json;
	private ObjectDefinition _objectDefinition1;
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}