/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.rest.client.dto.v1_0.ImportPreview;
import com.liferay.exportimport.rest.client.dto.v1_0.ImportProcess;
import com.liferay.exportimport.rest.client.dto.v1_0.ImportProcessRequest;
import com.liferay.exportimport.rest.client.dto.v1_0.RequestPortletDataHandler;
import com.liferay.exportimport.rest.client.http.HttpInvoker;
import com.liferay.exportimport.rest.client.resource.v1_0.ImportPreviewResource;
import com.liferay.exportimport.rest.client.resource.v1_0.ImportProcessResource;
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
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.staging.StagingGroupHelper;

import java.io.File;
import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 * @author Daniel Raposo
 */
@RunWith(Arquillian.class)
public class ImportProcessResourceTest
	extends BaseImportProcessResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_adminUser = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		_importPreviewResource = ImportPreviewResource.builder(
		).authentication(
			_adminUser.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();

		String password = RandomTestUtil.randomString();

		_user = UserTestUtil.addUser(testCompany, password);

		_importProcessResource = ImportProcessResource.builder(
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

	@FeatureFlag("LPD-17564")
	@Override
	@Test
	public void testPostAssetLibraryImportProcess() throws Exception {
		assertHttpResponseStatusCode(
			403,
			_importProcessResource.postAssetLibraryImportProcessHttpResponse(
				testDepotEntryGroup.getExternalReferenceCode(),
				new ImportProcessRequest()));

		ObjectDefinition objectDefinition = _publishObjectDefinition(
			ObjectDefinitionConstants.SCOPE_DEPOT);

		try {
			_testPostImportProcessWithObjectDefinition(
				file -> _importPreviewResource.postAssetLibraryImportPreview(
					testDepotEntryGroup.getExternalReferenceCode(), null,
					HashMapBuilder.put(
						"file", file
					).build()),
				importProcessRequest ->
					importProcessResource.postAssetLibraryImportProcess(
						testDepotEntryGroup.getExternalReferenceCode(),
						importProcessRequest),
				testDepotEntryGroup.getGroupId(),
				testDepotEntryGroup.getGroupId(), objectDefinition);
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}

		_testPostImportProcessWithPreviewForOtherGroup(
			file -> _importPreviewResource.postSiteImportPreview(
				testGroup.getExternalReferenceCode(), null,
				HashMapBuilder.put(
					"file", file
				).build()),
			importProcessRequest ->
				importProcessResource.postAssetLibraryImportProcessHttpResponse(
					testDepotEntryGroup.getExternalReferenceCode(),
					importProcessRequest),
			testGroup.getGroupId());
		_testPostImportProcessWithSettings(
			file -> _importPreviewResource.postAssetLibraryImportPreview(
				testDepotEntryGroup.getExternalReferenceCode(), null,
				HashMapBuilder.put(
					"file", file
				).build()),
			importProcessRequest ->
				importProcessResource.postAssetLibraryImportProcess(
					testDepotEntryGroup.getExternalReferenceCode(),
					importProcessRequest),
			testDepotEntryGroup.getGroupId());
		_testPostImportProcessWithoutPreview(
			importProcessRequest ->
				importProcessResource.postAssetLibraryImportProcessHttpResponse(
					testDepotEntryGroup.getExternalReferenceCode(),
					importProcessRequest));
	}

	@Override
	@Test
	public void testPostImportProcess() throws Exception {
		assertHttpResponseStatusCode(
			403,
			_importProcessResource.postImportProcessHttpResponse(
				new ImportProcessRequest()));

		Group companyGroup = _stagingGroupHelper.fetchCompanyGroup(
			testCompany.getCompanyId());

		ObjectDefinition objectDefinition = _publishObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);

		try {
			_testPostImportProcessWithObjectDefinition(
				file -> _importPreviewResource.postImportPreview(
					null,
					HashMapBuilder.put(
						"file", file
					).build()),
				importProcessResource::postImportProcess,
				GroupConstants.DEFAULT_PARENT_GROUP_ID,
				companyGroup.getGroupId(), objectDefinition);
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}

		_testPostImportProcessWithPreviewForOtherGroup(
			file -> _importPreviewResource.postSiteImportPreview(
				testGroup.getExternalReferenceCode(), null,
				HashMapBuilder.put(
					"file", file
				).build()),
			importProcessResource::postImportProcessHttpResponse,
			testGroup.getGroupId());
		_testPostImportProcessWithSettings(
			file -> _importPreviewResource.postImportPreview(
				null,
				HashMapBuilder.put(
					"file", file
				).build()),
			importProcessResource::postImportProcess,
			companyGroup.getGroupId());
		_testPostImportProcessWithoutPreview(
			importProcessResource::postImportProcessHttpResponse);
	}

	@FeatureFlag("LPD-17564")
	@Override
	@Test
	public void testPostSiteImportProcess() throws Exception {
		assertHttpResponseStatusCode(
			403,
			_importProcessResource.postSiteImportProcessHttpResponse(
				testGroup.getExternalReferenceCode(),
				new ImportProcessRequest()));

		ObjectDefinition objectDefinition = _publishObjectDefinition(
			ObjectDefinitionConstants.SCOPE_SITE);

		_testPostImportProcessWithObjectDefinition(
			file -> _importPreviewResource.postSiteImportPreview(
				testGroup.getExternalReferenceCode(), null,
				HashMapBuilder.put(
					"file", file
				).build()),
			importProcessRequest -> importProcessResource.postSiteImportProcess(
				testGroup.getExternalReferenceCode(), importProcessRequest),
			testGroup.getGroupId(), testGroup.getGroupId(), objectDefinition);

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		_testPostImportProcessWithPreviewForOtherGroup(
			file -> _importPreviewResource.postAssetLibraryImportPreview(
				testDepotEntryGroup.getExternalReferenceCode(), null,
				HashMapBuilder.put(
					"file", file
				).build()),
			importProcessRequest ->
				importProcessResource.postSiteImportProcessHttpResponse(
					testGroup.getExternalReferenceCode(), importProcessRequest),
			testDepotEntryGroup.getGroupId());
		_testPostImportProcessWithSettings(
			file -> _importPreviewResource.postSiteImportPreview(
				testGroup.getExternalReferenceCode(), null,
				HashMapBuilder.put(
					"file", file
				).build()),
			importProcessRequest -> importProcessResource.postSiteImportProcess(
				testGroup.getExternalReferenceCode(), importProcessRequest),
			testGroup.getGroupId());
		_testPostImportProcessWithoutPreview(
			importProcessRequest ->
				importProcessResource.postSiteImportProcessHttpResponse(
					testGroup.getExternalReferenceCode(),
					importProcessRequest));
	}

	@Override
	protected ImportProcess
			testGetAssetLibraryImportProcessesPage_addImportProcess(
				String assetLibraryExternalReferenceCode,
				ImportProcess importProcess)
		throws Exception {

		return _addImportProcess(
			_getGroupId(assetLibraryExternalReferenceCode),
			randomImportProcess());
	}

	@Override
	protected ImportProcess testGetImportProcess_addImportProcess()
		throws Exception {

		return _addImportProcess(_getCompanyGroupId(), randomImportProcess());
	}

	@Override
	protected ImportProcess testGetImportProcessesPage_addImportProcess(
			ImportProcess importProcess)
		throws Exception {

		return _addImportProcess(_getCompanyGroupId(), randomImportProcess());
	}

	@Override
	protected ImportProcess testGetSiteImportProcessesPage_addImportProcess(
			String siteExternalReferenceCode, ImportProcess importProcess)
		throws Exception {

		return _addImportProcess(
			_getGroupId(siteExternalReferenceCode), randomImportProcess());
	}

	private ImportProcess _addImportProcess(
			long groupId, ImportProcess importProcess)
		throws Exception {

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.addBackgroundTask(
				TestPropsValues.getUserId(), groupId, importProcess.getName(),
				BackgroundTaskExecutorNames.
					LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
				HashMapBuilder.<String, Serializable>put(
					"exportImportConfigurationId", RandomTestUtil.randomLong()
				).build(),
				null);

		return new ImportProcess() {
			{
				setDateCreated(backgroundTask.getCreateDate());
				setDateModified(backgroundTask.getModifiedDate());
				setId(backgroundTask.getBackgroundTaskId());
				setName(backgroundTask.getName());
			}
		};
	}

	private void _deleteTempFileEntries(long groupId) throws Exception {
		String folderName = DigesterUtil.digestHex(
			DigesterUtil.SHA_256,
			"com.liferay.exportimport.rest.resource.v1_0." +
				"ImportPreviewResource");

		long userId = _adminUser.getUserId();

		for (String tempFileName :
				TempFileEntryUtil.getTempFileNames(
					groupId, userId, folderName)) {

			TempFileEntryUtil.deleteTempFileEntry(
				groupId, userId, folderName, tempFileName);
		}
	}

	private File _exportLayoutAsFile(long groupId) throws Exception {
		Map<String, Serializable> parameterMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUser(), groupId, false, null,
					HashMapBuilder.put(
						PortletDataHandlerKeys.PORTLET_DATA,
						new String[] {Boolean.TRUE.toString()}
					).put(
						PortletDataHandlerKeys.PORTLET_DATA_ALL,
						new String[] {Boolean.TRUE.toString()}
					).build());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					TestPropsValues.getUserId(), groupId,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					parameterMap, new ServiceContext());

		return ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	private long _getCompanyGroupId() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		return group.getGroupId();
	}

	private long _getGroupId(String externalReferenceCode) throws Exception {
		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			externalReferenceCode, TestPropsValues.getCompanyId());

		return group.getGroupId();
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

	private void _testPostImportProcessWithObjectDefinition(
			UnsafeFunction<File, ImportPreview, Exception>
				postImportPreviewUnsafeFunction,
			UnsafeFunction<ImportProcessRequest, ImportProcess, Exception>
				postImportProcessUnsafeFunction,
			long objectEntryGroupId, long exportImportGroupId,
			ObjectDefinition objectDefinition)
		throws Exception {

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectEntryGroupId, objectDefinition,
			HashMapBuilder.<String, Serializable>put(
				"textField", RandomTestUtil.randomString()
			).build());

		File file = _exportLayoutAsFile(exportImportGroupId);

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry.getObjectEntryId());

		postImportPreviewUnsafeFunction.apply(file);

		ImportProcessRequest importProcessRequest = new ImportProcessRequest();

		importProcessRequest.setRequestPortletDataHandlers(
			new RequestPortletDataHandler[] {
				new RequestPortletDataHandler() {
					{
						name =
							"PORTLET_DATA_" + objectDefinition.getPortletId();
					}
				}
			});

		ImportProcess importProcess = postImportProcessUnsafeFunction.apply(
			importProcessRequest);

		assertValid(importProcess);

		ExportImportTestUtil.retryAssert(
			1, TimeUnit.SECONDS, 30, TimeUnit.SECONDS,
			() -> {
				BackgroundTask backgroundTask =
					_backgroundTaskLocalService.getBackgroundTask(
						importProcess.getId());

				Assert.assertEquals(
					BackgroundTaskConstants.STATUS_SUCCESSFUL,
					backgroundTask.getStatus());
			});

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry.getExternalReferenceCode(), objectEntryGroupId,
				objectDefinition.getObjectDefinitionId()));
	}

	private void _testPostImportProcessWithoutPreview(
			UnsafeFunction
				<ImportProcessRequest, HttpInvoker.HttpResponse, Exception>
					unsafeFunction)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.WARN)) {

			assertHttpResponseStatusCode(
				404, unsafeFunction.apply(new ImportProcessRequest()));
		}
	}

	private void _testPostImportProcessWithPreviewForOtherGroup(
			UnsafeFunction<File, ImportPreview, Exception>
				postImportPreviewUnsafeFunction,
			UnsafeFunction
				<ImportProcessRequest, HttpInvoker.HttpResponse, Exception>
					postImportProcessUnsafeFunction,
			long exportImportGroupId)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.WARN)) {

			try {
				postImportPreviewUnsafeFunction.apply(
					_exportLayoutAsFile(exportImportGroupId));

				assertHttpResponseStatusCode(
					404,
					postImportProcessUnsafeFunction.apply(
						new ImportProcessRequest()));
			}
			finally {
				_deleteTempFileEntries(exportImportGroupId);
			}
		}
	}

	private void _testPostImportProcessWithSettings(
			UnsafeFunction<File, ImportPreview, Exception>
				postImportPreviewUnsafeFunction,
			UnsafeFunction<ImportProcessRequest, ImportProcess, Exception>
				postImportProcessUnsafeFunction,
			long exportImportGroupId)
		throws Exception {

		File file = _exportLayoutAsFile(exportImportGroupId);

		postImportPreviewUnsafeFunction.apply(file);

		ImportProcessRequest importProcessRequest = new ImportProcessRequest() {
			{
				dataStrategy = DataStrategy.COPY_AS_NEW;
				deletions = true;
				permissions = true;
				userIdStrategy = UserIdStrategy.ALWAYS_CURRENT_USER_ID;
			}
		};

		ImportProcess importProcess = postImportProcessUnsafeFunction.apply(
			importProcessRequest);

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(
				importProcess.getId());

		long exportImportConfigurationId = MapUtil.getLong(
			backgroundTask.getTaskContextMap(), "exportImportConfigurationId");

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				getExportImportConfiguration(exportImportConfigurationId);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		Assert.assertEquals(
			PortletDataHandlerKeys.DATA_STRATEGY_COPY_AS_NEW,
			MapUtil.getString(
				parameterMap, PortletDataHandlerKeys.DATA_STRATEGY));
		Assert.assertTrue(
			MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETIONS));
		Assert.assertTrue(
			MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.PERMISSIONS));
		Assert.assertEquals(
			UserIdStrategy.ALWAYS_CURRENT_USER_ID,
			MapUtil.getString(
				parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY));
	}

	private User _adminUser;

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	private ImportPreviewResource _importPreviewResource;
	private ImportProcessResource _importProcessResource;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}