/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.rest.client.dto.v1_0.ExportProcess;
import com.liferay.exportimport.rest.client.dto.v1_0.ExportRequest;
import com.liferay.exportimport.rest.client.dto.v1_0.RequestPortletDataHandler;
import com.liferay.exportimport.rest.client.http.HttpInvoker;
import com.liferay.exportimport.rest.client.resource.v1_0.ExportProcessResource;
import com.liferay.exportimport.test.util.ExportImportTestUtil;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.staging.StagingGroupHelper;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Daniel Raposo
 */
@RunWith(Arquillian.class)
public class ExportProcessResourceTest
	extends BaseExportProcessResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		String password = RandomTestUtil.randomString();

		_user = UserTestUtil.addUser(testCompany, password);

		_exportProcessResource = ExportProcessResource.builder(
		).authentication(
			_user.getEmailAddress(), password
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_userLocalService.deleteUser(_user);
	}

	@Override
	@Test
	public void testPostAssetLibraryExportProcess() throws Exception {
		assertHttpResponseStatusCode(
			403,
			_exportProcessResource.postAssetLibraryExportProcessHttpResponse(
				testDepotEntryGroup.getExternalReferenceCode(),
				new ExportRequest() {
					{
						fileName = RandomTestUtil.randomString() + ".lar";
					}
				}));

		_testPostExportProcessWithInvalidDateRange(
			exportRequest ->
				exportProcessResource.postAssetLibraryExportProcessHttpResponse(
					testDepotEntryGroup.getExternalReferenceCode(),
					exportRequest));

		ObjectDefinition objectDefinition = _publishObjectDefinition(
			ObjectDefinitionConstants.SCOPE_DEPOT);

		try {
			_testPostExportProcessWithObjectDefinition(
				exportRequest ->
					exportProcessResource.postAssetLibraryExportProcess(
						testDepotEntryGroup.getExternalReferenceCode(),
						exportRequest),
				testDepotEntryGroup.getGroupId(), objectDefinition,
				_addObjectEntries(
					objectDefinition, testDepotEntryGroup.getGroupId()));
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Override
	@Test
	public void testPostExportProcess() throws Exception {
		assertHttpResponseStatusCode(
			403,
			_exportProcessResource.postExportProcessHttpResponse(
				new ExportRequest() {
					{
						fileName = RandomTestUtil.randomString() + ".lar";
					}
				}));

		Group companyGroup = _stagingGroupHelper.fetchCompanyGroup(
			testCompany.getCompanyId());

		_testPostExportProcessWithInvalidDateRange(
			exportProcessResource::postExportProcessHttpResponse);

		ObjectDefinition objectDefinition = _publishObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);

		try {
			_testPostExportProcessWithObjectDefinition(
				exportProcessResource::postExportProcess,
				companyGroup.getGroupId(), objectDefinition,
				_addObjectEntries(
					objectDefinition, GroupConstants.DEFAULT_PARENT_GROUP_ID));
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Override
	@Test
	public void testPostSiteExportProcess() throws Exception {
		assertHttpResponseStatusCode(
			403,
			_exportProcessResource.postSiteExportProcessHttpResponse(
				testGroup.getExternalReferenceCode(),
				new ExportRequest() {
					{
						fileName = RandomTestUtil.randomString() + ".lar";
					}
				}));

		_testPostExportProcessWithInvalidDateRange(
			exportRequest ->
				exportProcessResource.postSiteExportProcessHttpResponse(
					testGroup.getExternalReferenceCode(), exportRequest));

		ObjectDefinition objectDefinition = _publishObjectDefinition(
			ObjectDefinitionConstants.SCOPE_SITE);

		try {
			_testPostExportProcessWithObjectDefinition(
				exportRequest -> exportProcessResource.postSiteExportProcess(
					testGroup.getExternalReferenceCode(), exportRequest),
				testGroup.getGroupId(), objectDefinition,
				_addObjectEntries(objectDefinition, testGroup.getGroupId()));
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"creator", "name", "status"};
	}

	private ObjectEntry[] _addObjectEntries(
			ObjectDefinition objectDefinition, long groupId)
		throws Exception {

		return new ObjectEntry[] {
			_addObjectEntry(objectDefinition, groupId),
			_addObjectEntry(objectDefinition, groupId)
		};
	}

	private ObjectEntry _addObjectEntry(
			ObjectDefinition objectDefinition, long groupId)
		throws Exception {

		return ObjectEntryTestUtil.addObjectEntry(
			groupId, objectDefinition,
			HashMapBuilder.<String, Serializable>put(
				"textField", RandomTestUtil.randomString()
			).build());
	}

	private ObjectDefinition _publishObjectDefinition(String scope)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(), "textField", false)),
				scope);

		if (Objects.equals(scope, ObjectDefinitionConstants.SCOPE_DEPOT)) {
			_objectDefinitionSettingLocalService.addObjectDefinitionSetting(
				objectDefinition.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS,
				StringPool.TRUE);
		}

		return objectDefinition;
	}

	private void _testPostExportProcessWithInvalidDateRange(
			UnsafeFunction<ExportRequest, HttpInvoker.HttpResponse, Exception>
				unsafeFunction)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.WARN)) {

			ExportRequest dateRangeExportRequest = new ExportRequest();

			dateRangeExportRequest.setFileName(
				RandomTestUtil.randomString() + ".lar");
			dateRangeExportRequest.setRange(ExportRequest.Range.DATE_RANGE);

			assertHttpResponseStatusCode(
				400, unsafeFunction.apply(dateRangeExportRequest));

			ExportRequest lastExportRequest = new ExportRequest();

			lastExportRequest.setFileName(
				RandomTestUtil.randomString() + ".lar");
			lastExportRequest.setRange(ExportRequest.Range.LAST);

			assertHttpResponseStatusCode(
				400, unsafeFunction.apply(lastExportRequest));
		}
	}

	private void _testPostExportProcessWithObjectDefinition(
			UnsafeFunction<ExportRequest, ExportProcess, Exception>
				unsafeFunction,
			long groupId, ObjectDefinition objectDefinition,
			ObjectEntry[] objectEntries)
		throws Exception {

		ExportRequest exportRequest = new ExportRequest();

		exportRequest.setFileName(RandomTestUtil.randomString() + ".lar");
		exportRequest.setRequestPortletDataHandlers(
			new RequestPortletDataHandler[] {
				new RequestPortletDataHandler() {
					{
						name =
							"PORTLET_DATA_" + objectDefinition.getPortletId();
					}
				}
			});

		ExportProcess exportProcess = null;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineExportTaskExecutorImpl",
				LoggerTestUtil.WARN)) {

			exportProcess = unsafeFunction.apply(exportRequest);

			assertValid(exportProcess);

			ExportProcess finalExportProcess = exportProcess;

			ExportImportTestUtil.retryAssert(
				1, TimeUnit.SECONDS, 30, TimeUnit.SECONDS,
				() -> {
					BackgroundTask backgroundTask =
						_backgroundTaskLocalService.getBackgroundTask(
							finalExportProcess.getId());

					Assert.assertEquals(
						BackgroundTaskConstants.STATUS_SUCCESSFUL,
						backgroundTask.getStatus());
				});
		}

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(
				exportProcess.getId());

		List<FileEntry> fileEntries =
			backgroundTask.getAttachmentsFileEntries();

		Assert.assertEquals(fileEntries.toString(), 1, fileEntries.size());

		FileEntry larFileEntry = fileEntries.get(0);

		JSONAssert.assertEquals(
			JSONUtil.toJSONArray(
				objectEntries,
				objectEntry -> JSONUtil.put(
					"externalReferenceCode",
					objectEntry.getExternalReferenceCode())
			).toString(),
			String.valueOf(
				ExportImportTestUtil.getExportedObjectEntriesJSONArray(
					objectDefinition.getExternalReferenceCode(),
					larFileEntry.getContentStream(), groupId)),
			JSONCompareMode.LENIENT);
	}

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	private ExportProcessResource _exportProcessResource;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}