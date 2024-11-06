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
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

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
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_objectEntry1 = _addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_TEXT,
			RandomTestUtil.randomString(), TestPropsValues.getUser());

		_user = UserTestUtil.addUser();

		_objectEntry2 = _addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_TEXT,
			RandomTestUtil.randomString(), _user);

		_json = _executeExportTask(
			"com.liferay.object.rest.dto.v1_0.ObjectEntry#" +
				_objectDefinition1.getName());

		ObjectEntryLocalServiceUtil.deleteObjectEntry(_objectEntry1);
		ObjectEntryLocalServiceUtil.deleteObjectEntry(_objectEntry2);
	}

	@Test
	public void testImportWithInsertAndKeepCreator() throws Exception {
		_executeImportTask(
			"com.liferay.object.rest.dto.v1_0.ObjectEntry#" +
				_objectDefinition1.getName(),
			_json, "INSERT", "KEEP_CREATOR");

		_objectEntry1 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry1.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());
		_objectEntry2 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry2.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry1.getUserId());
		Assert.assertEquals(_user.getUserId(), _objectEntry2.getUserId());
	}

	@Test
	public void testImportWithInsertAndKeepCreatorUserDoesNotExist()
		throws Exception {

		UserLocalServiceUtil.deleteUser(_user);

		_executeImportTask(
			"com.liferay.object.rest.dto.v1_0.ObjectEntry#" +
				_objectDefinition1.getName(),
			_json, "INSERT", "KEEP_CREATOR");

		_objectEntry1 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry1.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());
		_objectEntry2 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry2.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry1.getUserId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry2.getUserId());
	}

	@Test
	public void testImportWithInsertAndKeepCreatorUserExistByERC()
		throws Exception {

		String userERC = _user.getExternalReferenceCode();

		UserLocalServiceUtil.deleteUser(_user);

		_user = UserTestUtil.addUser();

		_user.setExternalReferenceCode(userERC);

		_user = UserLocalServiceUtil.updateUser(_user);

		_executeImportTask(
			"com.liferay.object.rest.dto.v1_0.ObjectEntry#" +
				_objectDefinition1.getName(),
			_json, "INSERT", "KEEP_CREATOR");

		_objectEntry1 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry1.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());
		_objectEntry2 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry2.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry1.getUserId());
		Assert.assertEquals(_user.getUserId(), _objectEntry2.getUserId());
	}

	@Test
	public void testImportWithInsertAndOverwriteCreator() throws Exception {
		_executeImportTask(
			"com.liferay.object.rest.dto.v1_0.ObjectEntry#" +
				_objectDefinition1.getName(),
			_json, "INSERT", "OVERWRITE_CREATOR");

		_objectEntry1 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry1.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());
		_objectEntry2 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry2.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry1.getUserId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry2.getUserId());
	}

	@Test
	public void testImportWithUpsertAndKeepCreator() throws Exception {
		_objectEntry1 = _addObjectEntry(
			_objectEntry1.getExternalReferenceCode(), _objectDefinition1,
			_OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString(),
			TestPropsValues.getUser());

		_objectEntry2 = _addObjectEntry(
			_objectEntry2.getExternalReferenceCode(), _objectDefinition1,
			_OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString(),
			TestPropsValues.getUser());

		_executeImportTask(
			"com.liferay.object.rest.dto.v1_0.ObjectEntry#" +
				_objectDefinition1.getName(),
			_json, "UPSERT", "KEEP_CREATOR");

		_objectEntry1 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry1.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());
		_objectEntry2 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry2.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry1.getUserId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry2.getUserId());
	}

	@Test
	public void testImportWithUpsertAndOverwriteCreator() throws Exception {
		_objectEntry1 = _addObjectEntry(
			_objectEntry1.getExternalReferenceCode(), _objectDefinition1,
			_OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString(),
			TestPropsValues.getUser());

		_objectEntry2 = _addObjectEntry(
			_objectEntry2.getExternalReferenceCode(), _objectDefinition1,
			_OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString(),
			TestPropsValues.getUser());

		_executeImportTask(
			"com.liferay.object.rest.dto.v1_0.ObjectEntry#" +
				_objectDefinition1.getName(),
			_json, "UPSERT", "OVERWRITE_CREATOR");

		_objectEntry1 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry1.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());
		_objectEntry2 = ObjectEntryLocalServiceUtil.getObjectEntry(
			_objectEntry2.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());

		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry1.getUserId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), _objectEntry2.getUserId());
	}

	private ObjectEntry _addObjectEntry(
			ObjectDefinition objectDefinition, String objectFieldName,
			Serializable objectFieldValue, User user)
		throws Exception {

		return ObjectEntryLocalServiceUtil.addObjectEntry(
			user.getUserId(), 0L, objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectFieldName, objectFieldValue
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private ObjectEntry _addObjectEntry(
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String objectFieldName, Serializable objectFieldValue, User user)
		throws Exception {

		return ObjectEntryLocalServiceUtil.addObjectEntry(
			user.getUserId(), 0L, objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectFieldName, objectFieldValue
			).put(
				"externalReferenceCode", externalReferenceCode
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private String _executeExportTask(String className) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Class name: " + className);
		}

		Map<String, String> classNamePartsMap = _splitClassName(className);

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.header(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
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

	private void _executeImportTask(
			String className, String json, String createStrategy,
			String importCreatorStrategy)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Class name: " + className);
		}

		Map<String, String> classNamePartsMap = _splitClassName(className);

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(json, "application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		StringBundler sb = new StringBundler(
			classNamePartsMap.containsKey("taskItemDelegateName") ? 6 : 4);

		sb.append("http://localhost:8080/o/headless-batch-engine/v1.0");
		sb.append("/import-task/");
		sb.append(classNamePartsMap.get("className"));
		sb.append("?createStrategy=");
		sb.append(createStrategy);
		sb.append("&importCreatorStrategy=");
		sb.append(importCreatorStrategy);

		if (classNamePartsMap.containsKey("taskItemDelegateName")) {
			sb.append("&taskItemDelegateName=");
			sb.append(classNamePartsMap.get("taskItemDelegateName"));
		}

		httpInvoker.path(sb.toString());

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

		Date endDate = importTask.getEndTime();
		Date startDate = importTask.getStartTime();

		_log.info(
			"Import task duration: " +
				(endDate.getTime() - startDate.getTime()) + " ms");
	}

	private String _invoke(String url) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		Assert.assertEquals(200, httpResponse.getStatusCode());

		return httpResponse.getContent();
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

	private static final String _OBJECT_FIELD_NAME_TEXT = "testFieldName";

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportTaskResourceCreatorInfoTest.class);

	private String _json;
	private ObjectDefinition _objectDefinition1;
	private ObjectEntry _objectEntry1;
	private ObjectEntry _objectEntry2;
	private User _user;

}