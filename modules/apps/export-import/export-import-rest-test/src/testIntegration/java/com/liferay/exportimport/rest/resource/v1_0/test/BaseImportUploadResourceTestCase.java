/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.exportimport.rest.client.dto.v1_0.ImportUpload;
import com.liferay.exportimport.rest.client.dto.v1_0.Type;
import com.liferay.exportimport.rest.client.http.HttpInvoker;
import com.liferay.exportimport.rest.client.pagination.Page;
import com.liferay.exportimport.rest.client.resource.v1_0.ImportUploadResource;
import com.liferay.exportimport.rest.client.serdes.v1_0.ImportUploadSerDes;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker.HttpResponse;
import com.liferay.headless.batch.engine.client.resource.v1_0.ImportTaskResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.JAXRSWhiteboardTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import jakarta.annotation.Generated;

import jakarta.ws.rs.core.MultivaluedHashMap;

import java.lang.reflect.Method;

import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public abstract class BaseImportUploadResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		JAXRSWhiteboardTestUtil.ensureReady();
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		irrelevantDepotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null, DepotConstants.TYPE_ASSET_LIBRARY,
			new ServiceContext() {
				{
					setCompanyId(testCompany.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});
		irrelevantDepotEntryGroup = irrelevantDepotEntry.getGroup();
		testDepotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null, DepotConstants.TYPE_ASSET_LIBRARY,
			new ServiceContext() {
				{
					setCompanyId(testCompany.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});
		testDepotEntryGroup = testDepotEntry.getGroup();

		_importUploadResource.setContextCompany(testCompany);

		_testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		importUploadResource = ImportUploadResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();

		importTaskResource = ImportTaskResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		DepotEntryLocalServiceUtil.deleteDepotEntry(irrelevantDepotEntry);
		DepotEntryLocalServiceUtil.deleteDepotEntry(testDepotEntry);

		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		ImportUpload importUpload1 = randomImportUpload();

		String json = objectMapper.writeValueAsString(importUpload1);

		ImportUpload importUpload2 = ImportUploadSerDes.toDTO(json);

		Assert.assertTrue(equals(importUpload1, importUpload2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		ImportUpload importUpload = randomImportUpload();

		String json1 = objectMapper.writeValueAsString(importUpload);
		String json2 = ImportUploadSerDes.toJSON(importUpload);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	protected ObjectMapper getClientSerDesObjectMapper() {
		return new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ImportUpload importUpload = randomImportUpload();

		importUpload.setErrorMessage(regex);
		importUpload.setFileName(regex);
		importUpload.setId(regex);

		String json = ImportUploadSerDes.toJSON(importUpload);

		Assert.assertFalse(json.contains(regex));

		importUpload = ImportUploadSerDes.toDTO(json);

		Assert.assertEquals(regex, importUpload.getErrorMessage());
		Assert.assertEquals(regex, importUpload.getFileName());
		Assert.assertEquals(regex, importUpload.getId());
	}

	@Test
	public void testDeleteAssetLibraryImportUpload() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ImportUpload importUpload =
			testDeleteAssetLibraryImportUpload_addImportUpload();

		assertHttpResponseStatusCode(
			204,
			importUploadResource.deleteAssetLibraryImportUploadHttpResponse(
				testDeleteAssetLibraryImportUpload_getAssetLibraryExternalReferenceCode(),
				importUpload.getId()));

		assertHttpResponseStatusCode(
			404,
			importUploadResource.getAssetLibraryImportUploadHttpResponse(
				testDeleteAssetLibraryImportUpload_getAssetLibraryExternalReferenceCode(),
				importUpload.getId()));
		assertHttpResponseStatusCode(
			404,
			importUploadResource.getAssetLibraryImportUploadHttpResponse(
				testDeleteAssetLibraryImportUpload_getAssetLibraryExternalReferenceCode(),
				"-"));
	}

	protected ImportUpload testDeleteAssetLibraryImportUpload_addImportUpload()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testDeleteAssetLibraryImportUpload_getAssetLibraryExternalReferenceCode()
		throws Exception {

		return testDepotEntryGroup.getExternalReferenceCode();
	}

	@Test
	public void testDeleteImportUpload() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ImportUpload importUpload = testDeleteImportUpload_addImportUpload();

		assertHttpResponseStatusCode(
			204,
			importUploadResource.deleteImportUploadHttpResponse(
				importUpload.getId()));

		assertHttpResponseStatusCode(
			404,
			importUploadResource.getImportUploadHttpResponse(
				importUpload.getId()));
		assertHttpResponseStatusCode(
			404, importUploadResource.getImportUploadHttpResponse("-"));
	}

	protected ImportUpload testDeleteImportUpload_addImportUpload()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteImportUploadBatch() throws Exception {
		ImportUpload importUpload1 =
			testDeleteImportUploadBatch_addImportUpload();

		testDeleteImportUploadBatch_deleteImportUpload(
			202, null, importUpload1.getId());

		assertHttpResponseStatusCode(
			404,
			importUploadResource.getImportUploadHttpResponse(
				importUpload1.getId()));
	}

	protected ImportUpload testDeleteImportUploadBatch_addImportUpload()
		throws Exception {

		return testDeleteImportUpload_addImportUpload();
	}

	protected void testDeleteImportUploadBatch_deleteImportUpload(
			int expectedStatusCode, String externalReferenceCode, String id)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			importUploadResource.deleteImportUploadBatchHttpResponse(
				null,
				JSONUtil.putAll(
					JSONUtil.put(
						"externalReferenceCode", () -> externalReferenceCode
					).put(
						"id", () -> id
					)));

		Assert.assertEquals(expectedStatusCode, httpResponse.getStatusCode());

		waitForFinish(
			"COMPLETED",
			JSONFactoryUtil.createJSONObject(httpResponse.getContent()));
	}

	@Test
	public void testDeleteSiteImportUpload() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ImportUpload importUpload =
			testDeleteSiteImportUpload_addImportUpload();

		assertHttpResponseStatusCode(
			204,
			importUploadResource.deleteSiteImportUploadHttpResponse(
				testDeleteSiteImportUpload_getSiteExternalReferenceCode(),
				importUpload.getId()));

		assertHttpResponseStatusCode(
			404,
			importUploadResource.getSiteImportUploadHttpResponse(
				testDeleteSiteImportUpload_getSiteExternalReferenceCode(),
				importUpload.getId()));
		assertHttpResponseStatusCode(
			404,
			importUploadResource.getSiteImportUploadHttpResponse(
				testDeleteSiteImportUpload_getSiteExternalReferenceCode(),
				"-"));
	}

	protected ImportUpload testDeleteSiteImportUpload_addImportUpload()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testDeleteSiteImportUpload_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Test
	public void testGetAssetLibraryImportUpload() throws Exception {
		ImportUpload postImportUpload =
			testGetAssetLibraryImportUpload_addImportUpload();

		ImportUpload getImportUpload =
			importUploadResource.getAssetLibraryImportUpload(
				testGetAssetLibraryImportUpload_getAssetLibraryExternalReferenceCode(),
				postImportUpload.getId());

		assertEquals(postImportUpload, getImportUpload);
		assertValid(getImportUpload);
	}

	protected ImportUpload testGetAssetLibraryImportUpload_addImportUpload()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAssetLibraryImportUpload_getAssetLibraryExternalReferenceCode()
		throws Exception {

		return testDepotEntryGroup.getExternalReferenceCode();
	}

	@Test
	public void testGetImportUpload() throws Exception {
		ImportUpload postImportUpload = testGetImportUpload_addImportUpload();

		ImportUpload getImportUpload = importUploadResource.getImportUpload(
			postImportUpload.getId());

		assertEquals(postImportUpload, getImportUpload);
		assertValid(getImportUpload);
	}

	protected ImportUpload testGetImportUpload_addImportUpload()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteImportUpload() throws Exception {
		ImportUpload postImportUpload =
			testGetSiteImportUpload_addImportUpload();

		ImportUpload getImportUpload = importUploadResource.getSiteImportUpload(
			testGetSiteImportUpload_getSiteExternalReferenceCode(),
			postImportUpload.getId());

		assertEquals(postImportUpload, getImportUpload);
		assertValid(getImportUpload);
	}

	protected ImportUpload testGetSiteImportUpload_addImportUpload()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetSiteImportUpload_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Test
	public void testPostAssetLibraryImportUpload() throws Exception {
		ImportUpload randomImportUpload = randomImportUpload();

		ImportUpload postImportUpload =
			testPostAssetLibraryImportUpload_addImportUpload(
				randomImportUpload);

		assertEquals(randomImportUpload, postImportUpload);
		assertValid(postImportUpload);
	}

	protected ImportUpload testPostAssetLibraryImportUpload_addImportUpload(
			ImportUpload importUpload)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostImportUpload() throws Exception {
		ImportUpload randomImportUpload = randomImportUpload();

		ImportUpload postImportUpload = testPostImportUpload_addImportUpload(
			randomImportUpload);

		assertEquals(randomImportUpload, postImportUpload);
		assertValid(postImportUpload);
	}

	protected ImportUpload testPostImportUpload_addImportUpload(
			ImportUpload importUpload)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostSiteImportUpload() throws Exception {
		ImportUpload randomImportUpload = randomImportUpload();

		ImportUpload postImportUpload =
			testPostSiteImportUpload_addImportUpload(randomImportUpload);

		assertEquals(randomImportUpload, postImportUpload);
		assertValid(postImportUpload);
	}

	protected ImportUpload testPostSiteImportUpload_addImportUpload(
			ImportUpload importUpload)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutAssetLibraryImportUploadChunkChunkIndex()
		throws Exception {

		ImportUpload postImportUpload =
			testPutAssetLibraryImportUploadChunkChunkIndex_addImportUpload();

		ImportUpload randomImportUpload = randomImportUpload();

		ImportUpload putImportUpload =
			importUploadResource.putAssetLibraryImportUploadChunkChunkIndex(
				testPutAssetLibraryImportUploadChunkChunkIndex_getAssetLibraryExternalReferenceCode(),
				postImportUpload.getId(),
				testPutAssetLibraryImportUploadChunkChunkIndex_getChunkIndex());

		assertEquals(randomImportUpload, putImportUpload);
		assertValid(putImportUpload);

		ImportUpload getImportUpload =
			testPutAssetLibraryImportUploadChunkChunkIndex_getImportUpload(
				testPutAssetLibraryImportUploadChunkChunkIndex_getAssetLibraryExternalReferenceCode(),
				putImportUpload.getId(),
				testPutAssetLibraryImportUploadChunkChunkIndex_getChunkIndex());

		assertEquals(randomImportUpload, getImportUpload);
		assertValid(getImportUpload);
	}

	protected ImportUpload
		testPutAssetLibraryImportUploadChunkChunkIndex_getImportUpload(
			String assetLibraryExternalReferenceCode, String importUploadId,
			Integer chunkIndex) {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ImportUpload
			testPutAssetLibraryImportUploadChunkChunkIndex_addImportUpload()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testPutAssetLibraryImportUploadChunkChunkIndex_getAssetLibraryExternalReferenceCode()
		throws Exception {

		return testDepotEntryGroup.getExternalReferenceCode();
	}

	protected Integer
			testPutAssetLibraryImportUploadChunkChunkIndex_getChunkIndex()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutImportUploadChunkChunkIndex() throws Exception {
		ImportUpload postImportUpload =
			testPutImportUploadChunkChunkIndex_addImportUpload();

		ImportUpload randomImportUpload = randomImportUpload();

		ImportUpload putImportUpload =
			importUploadResource.putImportUploadChunkChunkIndex(
				postImportUpload.getId(),
				testPutImportUploadChunkChunkIndex_getChunkIndex());

		assertEquals(randomImportUpload, putImportUpload);
		assertValid(putImportUpload);

		ImportUpload getImportUpload =
			testPutImportUploadChunkChunkIndex_getImportUpload(
				putImportUpload.getId(),
				testPutImportUploadChunkChunkIndex_getChunkIndex());

		assertEquals(randomImportUpload, getImportUpload);
		assertValid(getImportUpload);
	}

	protected ImportUpload testPutImportUploadChunkChunkIndex_getImportUpload(
		String importUploadId, Integer chunkIndex) {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ImportUpload testPutImportUploadChunkChunkIndex_addImportUpload()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Integer testPutImportUploadChunkChunkIndex_getChunkIndex()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutSiteImportUploadChunkChunkIndex() throws Exception {
		ImportUpload postImportUpload =
			testPutSiteImportUploadChunkChunkIndex_addImportUpload();

		ImportUpload randomImportUpload = randomImportUpload();

		ImportUpload putImportUpload =
			importUploadResource.putSiteImportUploadChunkChunkIndex(
				testPutSiteImportUploadChunkChunkIndex_getSiteExternalReferenceCode(),
				postImportUpload.getId(),
				testPutSiteImportUploadChunkChunkIndex_getChunkIndex());

		assertEquals(randomImportUpload, putImportUpload);
		assertValid(putImportUpload);

		ImportUpload getImportUpload =
			testPutSiteImportUploadChunkChunkIndex_getImportUpload(
				testPutSiteImportUploadChunkChunkIndex_getSiteExternalReferenceCode(),
				putImportUpload.getId(),
				testPutSiteImportUploadChunkChunkIndex_getChunkIndex());

		assertEquals(randomImportUpload, getImportUpload);
		assertValid(getImportUpload);
	}

	protected ImportUpload
		testPutSiteImportUploadChunkChunkIndex_getImportUpload(
			String siteExternalReferenceCode, String importUploadId,
			Integer chunkIndex) {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ImportUpload
			testPutSiteImportUploadChunkChunkIndex_addImportUpload()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testPutSiteImportUploadChunkChunkIndex_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	protected Integer testPutSiteImportUploadChunkChunkIndex_getChunkIndex()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testBatchEngineDeleteImportTask() throws Exception {
		ImportUpload importUpload1 =
			testBatchEngineDeleteImportTask_addImportUpload();

		testBatchEngineDeleteImportTask_deleteImportUpload(
			200, null, importUpload1.getId());

		assertHttpResponseStatusCode(
			404,
			importUploadResource.getImportUploadHttpResponse(
				importUpload1.getId()));
	}

	protected ImportUpload testBatchEngineDeleteImportTask_addImportUpload()
		throws Exception {

		return testDeleteImportUpload_addImportUpload();
	}

	protected void testBatchEngineDeleteImportTask_deleteImportUpload(
			int expectedStatusCode, String externalReferenceCode, String id,
			String... parameters)
		throws Exception {

		ImportTaskResource importTaskResource = ImportTaskResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).parameters(
			parameters
		).build();

		HttpResponse httpResponse =
			importTaskResource.deleteImportTaskHttpResponse(
				"com.liferay.exportimport.rest.dto.v1_0.ImportUpload", null,
				null, null, null,
				JSONUtil.putAll(
					JSONUtil.put(
						"externalReferenceCode", () -> externalReferenceCode
					).put(
						"id", () -> id
					)));

		Assert.assertEquals(expectedStatusCode, httpResponse.getStatusCode());

		if (expectedStatusCode == 200) {
			waitForFinish(
				"COMPLETED",
				JSONFactoryUtil.createJSONObject(httpResponse.getContent()));
		}
	}

	protected void assertContains(
		ImportUpload importUpload, List<ImportUpload> importUploads) {

		boolean contains = false;

		for (ImportUpload item : importUploads) {
			if (equals(importUpload, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			importUploads + " does not contain " + importUpload, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ImportUpload importUpload1, ImportUpload importUpload2) {

		Assert.assertTrue(
			importUpload1 + " does not equal " + importUpload2,
			equals(importUpload1, importUpload2));
	}

	protected void assertEquals(
		List<ImportUpload> importUploads1, List<ImportUpload> importUploads2) {

		Assert.assertEquals(importUploads1.size(), importUploads2.size());

		for (int i = 0; i < importUploads1.size(); i++) {
			ImportUpload importUpload1 = importUploads1.get(i);
			ImportUpload importUpload2 = importUploads2.get(i);

			assertEquals(importUpload1, importUpload2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ImportUpload> importUploads1, List<ImportUpload> importUploads2) {

		Assert.assertEquals(importUploads1.size(), importUploads2.size());

		for (ImportUpload importUpload1 : importUploads1) {
			boolean contains = false;

			for (ImportUpload importUpload2 : importUploads2) {
				if (equals(importUpload1, importUpload2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				importUploads2 + " does not contain " + importUpload1,
				contains);
		}
	}

	protected void assertValid(ImportUpload importUpload) throws Exception {
		boolean valid = true;

		if (importUpload.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("chunkSize", additionalAssertFieldName)) {
				if (importUpload.getChunkSize() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("errorMessage", additionalAssertFieldName)) {
				if (importUpload.getErrorMessage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fileName", additionalAssertFieldName)) {
				if (importUpload.getFileName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("importPreview", additionalAssertFieldName)) {
				if (importUpload.getImportPreview() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentage", additionalAssertFieldName)) {
				if (importUpload.getPercentage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("size", additionalAssertFieldName)) {
				if (importUpload.getSize() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (importUpload.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("totalChunksCount", additionalAssertFieldName)) {
				if (importUpload.getTotalChunksCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"uploadedChunksCount", additionalAssertFieldName)) {

				if (importUpload.getUploadedChunksCount() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<ImportUpload> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<ImportUpload> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<ImportUpload> importUploads = page.getItems();

		int size = importUploads.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("id"));

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.exportimport.rest.dto.v1_0.ImportUpload.
						class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		ImportUpload importUpload1, ImportUpload importUpload2) {

		if (importUpload1 == importUpload2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("chunkSize", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						importUpload1.getChunkSize(),
						importUpload2.getChunkSize())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("errorMessage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						importUpload1.getErrorMessage(),
						importUpload2.getErrorMessage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fileName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						importUpload1.getFileName(),
						importUpload2.getFileName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						importUpload1.getId(), importUpload2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("importPreview", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						importUpload1.getImportPreview(),
						importUpload2.getImportPreview())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						importUpload1.getPercentage(),
						importUpload2.getPercentage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("size", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						importUpload1.getSize(), importUpload2.getSize())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						importUpload1.getStatus(), importUpload2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("totalChunksCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						importUpload1.getTotalChunksCount(),
						importUpload2.getTotalChunksCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"uploadedChunksCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						importUpload1.getUploadedChunksCount(),
						importUpload2.getUploadedChunksCount())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		if (clazz.getClassLoader() == null) {
			return new java.lang.reflect.Field[0];
		}

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_importUploadResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_importUploadResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator, ImportUpload importUpload) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("chunkSize")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("errorMessage")) {
			Object object = importUpload.getErrorMessage();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("fileName")) {
			Object object = importUpload.getFileName();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			Object object = importUpload.getId();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("importPreview")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("percentage")) {
			sb.append(String.valueOf(importUpload.getPercentage()));

			return sb.toString();
		}

		if (entityFieldName.equals("size")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("totalChunksCount")) {
			sb.append(String.valueOf(importUpload.getTotalChunksCount()));

			return sb.toString();
		}

		if (entityFieldName.equals("uploadedChunksCount")) {
			sb.append(String.valueOf(importUpload.getUploadedChunksCount()));

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path(
			"http://localhost:" + PortalUtil.getPortalServerPort(false) +
				"/o/graphql");
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected ImportUpload randomImportUpload() throws Exception {
		return new ImportUpload() {
			{
				chunkSize = RandomTestUtil.randomLong();
				errorMessage = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				fileName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = StringUtil.toLowerCase(RandomTestUtil.randomString());
				percentage = RandomTestUtil.randomInt();
				size = RandomTestUtil.randomLong();
				totalChunksCount = RandomTestUtil.randomInt();
				uploadedChunksCount = RandomTestUtil.randomInt();
			}
		};
	}

	protected ImportUpload randomIrrelevantImportUpload() throws Exception {
		ImportUpload randomIrrelevantImportUpload = randomImportUpload();

		return randomIrrelevantImportUpload;
	}

	protected ImportUpload randomPatchImportUpload() throws Exception {
		return randomImportUpload();
	}

	protected final JSONObject waitForFinish(
			String expectedExecuteStatus, JSONObject jsonObject)
		throws Exception {

		while (true) {
			ImportTask importTask = importTaskResource.getImportTask(
				jsonObject.getLong("id"));

			ImportTask.ExecuteStatus executeStatus =
				importTask.getExecuteStatus();

			if (StringUtil.equals(executeStatus.getValue(), "COMPLETED") ||
				StringUtil.equals(executeStatus.getValue(), "FAILED")) {

				Assert.assertEquals(
					expectedExecuteStatus, executeStatus.getValue());

				return jsonObject;
			}
		}
	}

	protected ImportUploadResource importUploadResource;
	protected ImportTaskResource importTaskResource;
	protected com.liferay.portal.kernel.model.Group irrelevantGroup;
	protected com.liferay.portal.kernel.model.Company testCompany;
	protected DepotEntry irrelevantDepotEntry;
	protected com.liferay.portal.kernel.model.Group irrelevantDepotEntryGroup;
	protected DepotEntry testDepotEntry;
	protected com.liferay.portal.kernel.model.Group testDepotEntryGroup;
	protected com.liferay.portal.kernel.model.Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = source.getClass();

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					_getAllDeclaredFields(sourceClass)) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				try {
					Method setMethod = _getMethod(
						targetClass, field.getName(), "set",
						getMethod.getReturnType());

					setMethod.invoke(target, getMethod.invoke(source));
				}
				catch (Exception e) {
					continue;
				}
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static List<java.lang.reflect.Field> _getAllDeclaredFields(
			Class<?> clazz) {

			List<java.lang.reflect.Field> fields = new ArrayList<>();

			while ((clazz != null) && (clazz != Object.class)) {
				for (java.lang.reflect.Field field :
						clazz.getDeclaredFields()) {

					fields.add(field);
				}

				clazz = clazz.getSuperclass();
			}

			return fields;
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseImportUploadResourceTestCase.class);

	private static Format _format;

	private com.liferay.portal.kernel.model.User _testCompanyAdminUser;

	@Inject
	private com.liferay.exportimport.rest.resource.v1_0.ImportUploadResource
		_importUploadResource;

}
// LIFERAY-REST-BUILDER-HASH:1203787203