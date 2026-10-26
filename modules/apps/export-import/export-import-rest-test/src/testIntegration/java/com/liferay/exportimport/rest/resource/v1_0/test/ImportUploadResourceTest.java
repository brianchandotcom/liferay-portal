/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.rest.client.dto.v1_0.ImportPreview;
import com.liferay.exportimport.rest.client.dto.v1_0.ImportUpload;
import com.liferay.exportimport.rest.client.dto.v1_0.ImportUploadRequest;
import com.liferay.exportimport.rest.client.resource.v1_0.ImportPreviewResource;
import com.liferay.exportimport.rest.client.resource.v1_0.ImportUploadResource;
import com.liferay.exportimport.test.util.ExportImportTestUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProviderUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 * @author Jaime León
 */
@RunWith(Arquillian.class)
public class ImportUploadResourceTest extends BaseImportUploadResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_adminUser = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		String password = RandomTestUtil.randomString();

		_user = UserTestUtil.addUser(testCompany, password);

		_userImportUploadResource = ImportUploadResource.builder(
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
	public void testDeleteAssetLibraryImportUpload() throws Exception {
		super.testDeleteAssetLibraryImportUpload();
	}

	@Override
	@Test
	public void testDeleteImportUpload() throws Exception {
		super.testDeleteImportUpload();
	}

	@Override
	@Test
	public void testDeleteSiteImportUpload() throws Exception {
		super.testDeleteSiteImportUpload();
	}

	@Override
	@Test
	public void testGetAssetLibraryImportUpload() throws Exception {
		super.testGetAssetLibraryImportUpload();
	}

	@Override
	@Test
	public void testGetImportUpload() throws Exception {
		super.testGetImportUpload();
	}

	@Override
	@Test
	public void testGetSiteImportUpload() throws Exception {
		super.testGetSiteImportUpload();
	}

	@Override
	@Test
	public void testPostAssetLibraryImportUpload() throws Exception {
		super.testPostAssetLibraryImportUpload();
	}

	@Override
	@Test
	public void testPostImportUpload() throws Exception {
		super.testPostImportUpload();
	}

	@Override
	@Test
	public void testPostSiteImportUpload() throws Exception {
		super.testPostSiteImportUpload();

		_testPostSiteImportUploadWhenFileIsEmpty();
		_testPostSiteImportUploadWhenFileIsTooLarge();
		_testPostSiteImportUploadWhenUserLacksPermission();
	}

	@Override
	@Test
	public void testPutAssetLibraryImportUploadChunkChunkIndex()
		throws Exception {

		try (LogCapture logCapture1 = _configureBackgroundTaskLogCapture();
			LogCapture logCapture2 = _configureZipReaderLogCapture()) {

			super.testPutAssetLibraryImportUploadChunkChunkIndex();

			_assertFailed(
				() -> importUploadResource.getAssetLibraryImportUpload(
					testDepotEntryGroup.getExternalReferenceCode(),
					_importUploadId));
		}
	}

	@Override
	@Test
	public void testPutImportUploadChunkChunkIndex() throws Exception {
		try (LogCapture logCapture1 = _configureBackgroundTaskLogCapture();
			LogCapture logCapture2 = _configureZipReaderLogCapture()) {

			super.testPutImportUploadChunkChunkIndex();

			_assertFailed(
				() -> importUploadResource.getImportUpload(_importUploadId));
		}
	}

	@Override
	@Test
	public void testPutSiteImportUploadChunkChunkIndex() throws Exception {
		try (LogCapture logCapture1 = _configureBackgroundTaskLogCapture();
			LogCapture logCapture2 = _configureZipReaderLogCapture()) {

			super.testPutSiteImportUploadChunkChunkIndex();

			_assertFailed(
				() -> importUploadResource.getSiteImportUpload(
					testGroup.getExternalReferenceCode(), _importUploadId));
		}

		_testPutSiteImportUploadChunkChunkIndexWhenFileIsLAR();
	}

	@Override
	protected ImportUpload testDeleteAssetLibraryImportUpload_addImportUpload()
		throws Exception {

		return importUploadResource.postAssetLibraryImportUpload(
			testDepotEntryGroup.getExternalReferenceCode(), null,
			_createImportUploadRequest(RandomTestUtil.randomInt(1, 100)));
	}

	@Override
	protected ImportUpload testDeleteImportUpload_addImportUpload()
		throws Exception {

		return importUploadResource.postImportUpload(
			null, _createImportUploadRequest(RandomTestUtil.randomInt(1, 100)));
	}

	@Override
	protected ImportUpload testDeleteSiteImportUpload_addImportUpload()
		throws Exception {

		return importUploadResource.postSiteImportUpload(
			testGroup.getExternalReferenceCode(), null, null,
			_createImportUploadRequest(RandomTestUtil.randomInt(1, 100)));
	}

	@Override
	protected ImportUpload testGetAssetLibraryImportUpload_addImportUpload()
		throws Exception {

		return testDeleteAssetLibraryImportUpload_addImportUpload();
	}

	@Override
	protected ImportUpload testGetImportUpload_addImportUpload()
		throws Exception {

		return testDeleteImportUpload_addImportUpload();
	}

	@Override
	protected ImportUpload testGetSiteImportUpload_addImportUpload()
		throws Exception {

		return testDeleteSiteImportUpload_addImportUpload();
	}

	@Override
	protected ImportUpload testPostAssetLibraryImportUpload_addImportUpload(
			ImportUpload importUpload)
		throws Exception {

		return testDeleteAssetLibraryImportUpload_addImportUpload();
	}

	@Override
	protected ImportUpload testPostImportUpload_addImportUpload(
			ImportUpload importUpload)
		throws Exception {

		return testDeleteImportUpload_addImportUpload();
	}

	@Override
	protected ImportUpload testPostSiteImportUpload_addImportUpload(
			ImportUpload importUpload)
		throws Exception {

		return testDeleteSiteImportUpload_addImportUpload();
	}

	@Override
	protected ImportUpload
			testPutAssetLibraryImportUploadChunkChunkIndex_addImportUpload()
		throws Exception {

		return _addImportUpload(
			importUploadRequest ->
				importUploadResource.postAssetLibraryImportUpload(
					testDepotEntryGroup.getExternalReferenceCode(), null,
					importUploadRequest));
	}

	@Override
	protected Integer
			testPutAssetLibraryImportUploadChunkChunkIndex_getChunkIndex()
		throws Exception {

		return 0;
	}

	@Override
	protected ImportUpload
		testPutAssetLibraryImportUploadChunkChunkIndex_getImportUpload(
			String assetLibraryExternalReferenceCode, String importUploadId,
			Integer chunkIndex) {

		return _getImportUpload(
			() -> importUploadResource.getAssetLibraryImportUpload(
				assetLibraryExternalReferenceCode, importUploadId));
	}

	@Override
	protected ImportUpload testPutImportUploadChunkChunkIndex_addImportUpload()
		throws Exception {

		return _addImportUpload(
			importUploadRequest -> importUploadResource.postImportUpload(
				null, importUploadRequest));
	}

	@Override
	protected Integer testPutImportUploadChunkChunkIndex_getChunkIndex()
		throws Exception {

		return 0;
	}

	@Override
	protected ImportUpload testPutImportUploadChunkChunkIndex_getImportUpload(
		String importUploadId, Integer chunkIndex) {

		return _getImportUpload(
			() -> importUploadResource.getImportUpload(importUploadId));
	}

	@Override
	protected ImportUpload
			testPutSiteImportUploadChunkChunkIndex_addImportUpload()
		throws Exception {

		return _addImportUpload(
			importUploadRequest -> importUploadResource.postSiteImportUpload(
				testGroup.getExternalReferenceCode(), null, null,
				importUploadRequest));
	}

	@Override
	protected Integer testPutSiteImportUploadChunkChunkIndex_getChunkIndex()
		throws Exception {

		return 0;
	}

	@Override
	protected ImportUpload
		testPutSiteImportUploadChunkChunkIndex_getImportUpload(
			String siteExternalReferenceCode, String importUploadId,
			Integer chunkIndex) {

		return _getImportUpload(
			() -> importUploadResource.getSiteImportUpload(
				siteExternalReferenceCode, importUploadId));
	}

	private ImportUpload _addImportUpload(
			UnsafeFunction<ImportUploadRequest, ImportUpload, Exception>
				unsafeFunction)
		throws Exception {

		ImportUpload importUpload = unsafeFunction.apply(
			_createImportUploadRequest("[]".length()));

		_importUploadId = importUpload.getId();

		return importUpload;
	}

	private void _assertFailed(
			UnsafeSupplier<ImportUpload, Exception> unsafeSupplier)
		throws Exception {

		ExportImportTestUtil.retryAssert(
			1, TimeUnit.SECONDS, 60, TimeUnit.SECONDS,
			() -> {
				ImportUpload importUpload = unsafeSupplier.get();

				Assert.assertNotNull(importUpload.getErrorMessage());
				Assert.assertNull(importUpload.getImportPreview());
				Assert.assertEquals(
					ImportUpload.Status.FAILED, importUpload.getStatus());
			});
	}

	private LogCapture _configureBackgroundTaskLogCapture() {
		return LoggerTestUtil.configureLog4JLogger(
			"com.liferay.portal.background.task.internal.messaging." +
				"BackgroundTaskMessageListener",
			LoggerTestUtil.OFF);
	}

	private LogCapture _configureWebApplicationExceptionMapperLogCapture() {
		return LoggerTestUtil.configureLog4JLogger(
			"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
				"WebApplicationExceptionMapper",
			LoggerTestUtil.WARN);
	}

	private LogCapture _configureZipReaderLogCapture() {
		return LoggerTestUtil.configureLog4JLogger(
			"com.liferay.portal.zip.internal.reader.BaseZipReader",
			LoggerTestUtil.OFF);
	}

	private ImportUploadRequest _createImportUploadRequest(long fileSize) {
		return new ImportUploadRequest() {
			{
				setContentType(RandomTestUtil.randomString());
				setFileName(RandomTestUtil.randomString());
				setSize(fileSize);
			}
		};
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

	private ImportUpload _getImportUpload(
		UnsafeSupplier<ImportUpload, Exception> unsafeSupplier) {

		try {
			return unsafeSupplier.get();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private int _putChunk(byte[] bytes, int chunkIndex, String importUploadId)
		throws Exception {

		return ExportImportTestUtil.putImportUploadChunk(
			bytes, _adminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD,
			StringBundler.concat(
				"http://", testCompany.getVirtualHostname(), ":",
				PortalUtil.getPortalServerPort(false),
				"/o/export-import/v1.0/sites/",
				testGroup.getExternalReferenceCode(), "/import-uploads/",
				importUploadId, "/chunks/", chunkIndex));
	}

	private void _testPostSiteImportUploadWhenFileIsEmpty() throws Exception {
		try (LogCapture logCapture =
				_configureWebApplicationExceptionMapperLogCapture()) {

			assertHttpResponseStatusCode(
				400,
				importUploadResource.postSiteImportUploadHttpResponse(
					testGroup.getExternalReferenceCode(), null, null,
					_createImportUploadRequest(0)));
		}
	}

	private void _testPostSiteImportUploadWhenFileIsTooLarge()
		throws Exception {

		try (LogCapture logCapture =
				_configureWebApplicationExceptionMapperLogCapture()) {

			long maxSize =
				UploadServletRequestConfigurationProviderUtil.getMaxSize();

			assertHttpResponseStatusCode(
				400,
				importUploadResource.postSiteImportUploadHttpResponse(
					testGroup.getExternalReferenceCode(), null, null,
					_createImportUploadRequest(maxSize + 1)));
		}
	}

	private void _testPostSiteImportUploadWhenUserLacksPermission()
		throws Exception {

		assertHttpResponseStatusCode(
			403,
			_userImportUploadResource.postSiteImportUploadHttpResponse(
				testGroup.getExternalReferenceCode(), null, null,
				_createImportUploadRequest(RandomTestUtil.randomInt(1, 100))));
	}

	private void _testPutSiteImportUploadChunkChunkIndexWhenFileIsLAR()
		throws Exception {

		File file = _exportLayoutAsFile(testGroup.getGroupId());

		byte[] bytes = FileUtil.getBytes(file);

		String externalReferenceCode = testGroup.getExternalReferenceCode();

		ImportUpload importUpload = importUploadResource.postSiteImportUpload(
			externalReferenceCode, null, null,
			_createImportUploadRequest(bytes.length));

		Assert.assertEquals(200, _putChunk(bytes, 0, importUpload.getId()));
		Assert.assertEquals(200, _putChunk(bytes, 0, importUpload.getId()));

		try (LogCapture logCapture =
				_configureWebApplicationExceptionMapperLogCapture()) {

			Assert.assertEquals(
				400,
				_putChunk(
					Arrays.copyOf(bytes, bytes.length + 1), 0,
					importUpload.getId()));
			Assert.assertEquals(400, _putChunk(bytes, 1, importUpload.getId()));
		}

		ExportImportTestUtil.retryAssert(
			1, TimeUnit.SECONDS, 60, TimeUnit.SECONDS,
			() -> {
				ImportUpload validatingImportUpload =
					importUploadResource.getSiteImportUpload(
						externalReferenceCode, importUpload.getId());

				Assert.assertEquals(
					ImportUpload.Status.READY,
					validatingImportUpload.getStatus());
			});

		ImportUpload validatedImportUpload =
			importUploadResource.getSiteImportUpload(
				externalReferenceCode, importUpload.getId());

		Assert.assertEquals(
			Integer.valueOf(1), validatedImportUpload.getUploadedChunksCount());

		ImportPreview importPreview = validatedImportUpload.getImportPreview();

		Assert.assertEquals(
			validatedImportUpload.getFileName(), importPreview.getFileName());
		Assert.assertEquals(
			Long.valueOf(bytes.length), importPreview.getFileSize());

		ImportPreviewResource importPreviewResource =
			ImportPreviewResource.builder(
			).authentication(
				_adminUser.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
			).endpoint(
				testCompany.getVirtualHostname(),
				PortalUtil.getPortalServerPort(false), "http"
			).locale(
				LocaleUtil.getDefault()
			).build();

		ImportPreview expectedImportPreview =
			importPreviewResource.postSiteImportPreview(
				externalReferenceCode, null, null, null,
				HashMapBuilder.put(
					"file", file
				).build());

		Assert.assertEquals(
			expectedImportPreview.getAdditionCount(),
			importPreview.getAdditionCount());
		Assert.assertEquals(
			expectedImportPreview.getDeletionCount(),
			importPreview.getDeletionCount());
		Assert.assertEquals(
			expectedImportPreview.getExportDate(),
			importPreview.getExportDate());

		assertHttpResponseStatusCode(
			204,
			importUploadResource.deleteSiteImportUploadHttpResponse(
				externalReferenceCode, importUpload.getId()));
		assertHttpResponseStatusCode(
			404,
			importUploadResource.getSiteImportUploadHttpResponse(
				externalReferenceCode, importUpload.getId()));

		FileUtil.delete(file);
	}

	private User _adminUser;
	private String _importUploadId;
	private User _user;
	private ImportUploadResource _userImportUploadResource;

	@Inject
	private UserLocalService _userLocalService;

}