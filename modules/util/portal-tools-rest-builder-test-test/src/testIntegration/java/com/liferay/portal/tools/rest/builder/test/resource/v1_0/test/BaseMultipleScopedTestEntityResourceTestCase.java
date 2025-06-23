/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.MultipleScopedTestEntity;
import com.liferay.portal.tools.rest.builder.test.client.http.HttpInvoker;
import com.liferay.portal.tools.rest.builder.test.client.pagination.Page;
import com.liferay.portal.tools.rest.builder.test.client.resource.v1_0.MultipleScopedTestEntityResource;
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0.MultipleScopedTestEntitySerDes;
import com.liferay.portal.util.PropsValues;
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
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public abstract class BaseMultipleScopedTestEntityResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
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
			null,
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
			null,
			new ServiceContext() {
				{
					setCompanyId(testCompany.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});
		testDepotEntryGroup = testDepotEntry.getGroup();

		_multipleScopedTestEntityResource.setContextCompany(testCompany);

		_testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		multipleScopedTestEntityResource =
			MultipleScopedTestEntityResource.builder(
			).authentication(
				_testCompanyAdminUser.getEmailAddress(),
				PropsValues.DEFAULT_ADMIN_PASSWORD
			).endpoint(
				testCompany.getVirtualHostname(), 8080, "http"
			).locale(
				LocaleUtil.getDefault()
			).build();

		importTaskResource = ImportTaskResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		MultipleScopedTestEntity multipleScopedTestEntity1 =
			randomMultipleScopedTestEntity();

		String json = objectMapper.writeValueAsString(
			multipleScopedTestEntity1);

		MultipleScopedTestEntity multipleScopedTestEntity2 =
			MultipleScopedTestEntitySerDes.toDTO(json);

		Assert.assertTrue(
			equals(multipleScopedTestEntity1, multipleScopedTestEntity2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		MultipleScopedTestEntity multipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		String json1 = objectMapper.writeValueAsString(
			multipleScopedTestEntity);
		String json2 = MultipleScopedTestEntitySerDes.toJSON(
			multipleScopedTestEntity);

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

		MultipleScopedTestEntity multipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		multipleScopedTestEntity.setAssetLibraryKey(regex);
		multipleScopedTestEntity.setDescription(regex);
		multipleScopedTestEntity.setExternalReferenceCode(regex);

		String json = MultipleScopedTestEntitySerDes.toJSON(
			multipleScopedTestEntity);

		Assert.assertFalse(json.contains(regex));

		multipleScopedTestEntity = MultipleScopedTestEntitySerDes.toDTO(json);

		Assert.assertEquals(
			regex, multipleScopedTestEntity.getAssetLibraryKey());
		Assert.assertEquals(regex, multipleScopedTestEntity.getDescription());
		Assert.assertEquals(
			regex, multipleScopedTestEntity.getExternalReferenceCode());
	}

	@Test
	public void testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MultipleScopedTestEntity multipleScopedTestEntity =
			testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		assertHttpResponseStatusCode(
			204,
			multipleScopedTestEntityResource.
				deleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCodeHttpResponse(
					testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId(),
					multipleScopedTestEntity.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			multipleScopedTestEntityResource.
				getAssetLibraryMultipleScopedTestEntityByExternalReferenceCodeHttpResponse(
					testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId(),
					multipleScopedTestEntity.getExternalReferenceCode()));
		assertHttpResponseStatusCode(
			404,
			multipleScopedTestEntityResource.
				getAssetLibraryMultipleScopedTestEntityByExternalReferenceCodeHttpResponse(
					testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId(),
					"-"));
	}

	protected MultipleScopedTestEntity
			testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MultipleScopedTestEntity multipleScopedTestEntity =
			testDeleteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		assertHttpResponseStatusCode(
			204,
			multipleScopedTestEntityResource.
				deleteMultipleScopedTestEntityByExternalReferenceCodeHttpResponse(
					multipleScopedTestEntity.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			multipleScopedTestEntityResource.
				getMultipleScopedTestEntityByExternalReferenceCodeHttpResponse(
					multipleScopedTestEntity.getExternalReferenceCode()));
		assertHttpResponseStatusCode(
			404,
			multipleScopedTestEntityResource.
				getMultipleScopedTestEntityByExternalReferenceCodeHttpResponse(
					"-"));
	}

	protected MultipleScopedTestEntity
			testDeleteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteSiteMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MultipleScopedTestEntity multipleScopedTestEntity =
			testDeleteSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		assertHttpResponseStatusCode(
			204,
			multipleScopedTestEntityResource.
				deleteSiteMultipleScopedTestEntityByExternalReferenceCodeHttpResponse(
					multipleScopedTestEntity.getSiteId(),
					multipleScopedTestEntity.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			multipleScopedTestEntityResource.
				getSiteMultipleScopedTestEntityByExternalReferenceCodeHttpResponse(
					multipleScopedTestEntity.getSiteId(),
					multipleScopedTestEntity.getExternalReferenceCode()));
		assertHttpResponseStatusCode(
			404,
			multipleScopedTestEntityResource.
				getSiteMultipleScopedTestEntityByExternalReferenceCodeHttpResponse(
					multipleScopedTestEntity.getSiteId(), "-"));
	}

	protected MultipleScopedTestEntity
			testDeleteSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAssetLibraryMultipleScopedTestEntitiesPage()
		throws Exception {

		Long assetLibraryId =
			testGetAssetLibraryMultipleScopedTestEntitiesPage_getAssetLibraryId();
		Long irrelevantAssetLibraryId =
			testGetAssetLibraryMultipleScopedTestEntitiesPage_getIrrelevantAssetLibraryId();

		Page<MultipleScopedTestEntity> page =
			multipleScopedTestEntityResource.
				getAssetLibraryMultipleScopedTestEntitiesPage(assetLibraryId);

		long totalCount = page.getTotalCount();

		if (irrelevantAssetLibraryId != null) {
			MultipleScopedTestEntity irrelevantMultipleScopedTestEntity =
				testGetAssetLibraryMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
					irrelevantAssetLibraryId,
					randomIrrelevantMultipleScopedTestEntity());

			page =
				multipleScopedTestEntityResource.
					getAssetLibraryMultipleScopedTestEntitiesPage(
						irrelevantAssetLibraryId);

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantMultipleScopedTestEntity,
				(List<MultipleScopedTestEntity>)page.getItems());
			assertValid(
				page,
				testGetAssetLibraryMultipleScopedTestEntitiesPage_getExpectedActions(
					irrelevantAssetLibraryId));
		}

		MultipleScopedTestEntity multipleScopedTestEntity1 =
			testGetAssetLibraryMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				assetLibraryId, randomMultipleScopedTestEntity());

		MultipleScopedTestEntity multipleScopedTestEntity2 =
			testGetAssetLibraryMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				assetLibraryId, randomMultipleScopedTestEntity());

		page =
			multipleScopedTestEntityResource.
				getAssetLibraryMultipleScopedTestEntitiesPage(assetLibraryId);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			multipleScopedTestEntity1,
			(List<MultipleScopedTestEntity>)page.getItems());
		assertContains(
			multipleScopedTestEntity2,
			(List<MultipleScopedTestEntity>)page.getItems());
		assertValid(
			page,
			testGetAssetLibraryMultipleScopedTestEntitiesPage_getExpectedActions(
				assetLibraryId));
	}

	protected Map<String, Map<String, String>>
			testGetAssetLibraryMultipleScopedTestEntitiesPage_getExpectedActions(
				Long assetLibraryId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	protected MultipleScopedTestEntity
			testGetAssetLibraryMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				Long assetLibraryId,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetAssetLibraryMultipleScopedTestEntitiesPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	protected Long
			testGetAssetLibraryMultipleScopedTestEntitiesPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return irrelevantDepotEntry.getDepotEntryId();
	}

	@Test
	public void testGetAssetLibraryMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testGetAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		MultipleScopedTestEntity getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
					testGetAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId(),
					postMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(postMultipleScopedTestEntity, getMultipleScopedTestEntity);
		assertValid(getMultipleScopedTestEntity);
	}

	protected MultipleScopedTestEntity
			testGetAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetMultipleScopedTestEntitiesPage() throws Exception {
		Page<MultipleScopedTestEntity> page =
			multipleScopedTestEntityResource.
				getMultipleScopedTestEntitiesPage();

		long totalCount = page.getTotalCount();

		MultipleScopedTestEntity multipleScopedTestEntity1 =
			testGetMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				randomMultipleScopedTestEntity());

		MultipleScopedTestEntity multipleScopedTestEntity2 =
			testGetMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				randomMultipleScopedTestEntity());

		page =
			multipleScopedTestEntityResource.
				getMultipleScopedTestEntitiesPage();

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			multipleScopedTestEntity1,
			(List<MultipleScopedTestEntity>)page.getItems());
		assertContains(
			multipleScopedTestEntity2,
			(List<MultipleScopedTestEntity>)page.getItems());
		assertValid(
			page, testGetMultipleScopedTestEntitiesPage_getExpectedActions());
	}

	protected Map<String, Map<String, String>>
			testGetMultipleScopedTestEntitiesPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	protected MultipleScopedTestEntity
			testGetMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testGetMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		MultipleScopedTestEntity getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getMultipleScopedTestEntityByExternalReferenceCode(
					postMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(postMultipleScopedTestEntity, getMultipleScopedTestEntity);
		assertValid(getMultipleScopedTestEntity);
	}

	protected MultipleScopedTestEntity
			testGetMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteMultipleScopedTestEntitiesPage() throws Exception {
		Long siteId = testGetSiteMultipleScopedTestEntitiesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteMultipleScopedTestEntitiesPage_getIrrelevantSiteId();

		Page<MultipleScopedTestEntity> page =
			multipleScopedTestEntityResource.
				getSiteMultipleScopedTestEntitiesPage(siteId);

		long totalCount = page.getTotalCount();

		if (irrelevantSiteId != null) {
			MultipleScopedTestEntity irrelevantMultipleScopedTestEntity =
				testGetSiteMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
					irrelevantSiteId,
					randomIrrelevantMultipleScopedTestEntity());

			page =
				multipleScopedTestEntityResource.
					getSiteMultipleScopedTestEntitiesPage(irrelevantSiteId);

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantMultipleScopedTestEntity,
				(List<MultipleScopedTestEntity>)page.getItems());
			assertValid(
				page,
				testGetSiteMultipleScopedTestEntitiesPage_getExpectedActions(
					irrelevantSiteId));
		}

		MultipleScopedTestEntity multipleScopedTestEntity1 =
			testGetSiteMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				siteId, randomMultipleScopedTestEntity());

		MultipleScopedTestEntity multipleScopedTestEntity2 =
			testGetSiteMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				siteId, randomMultipleScopedTestEntity());

		page =
			multipleScopedTestEntityResource.
				getSiteMultipleScopedTestEntitiesPage(siteId);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			multipleScopedTestEntity1,
			(List<MultipleScopedTestEntity>)page.getItems());
		assertContains(
			multipleScopedTestEntity2,
			(List<MultipleScopedTestEntity>)page.getItems());
		assertValid(
			page,
			testGetSiteMultipleScopedTestEntitiesPage_getExpectedActions(
				siteId));
	}

	protected Map<String, Map<String, String>>
			testGetSiteMultipleScopedTestEntitiesPage_getExpectedActions(
				Long siteId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	protected MultipleScopedTestEntity
			testGetSiteMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				Long siteId, MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteMultipleScopedTestEntitiesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetSiteMultipleScopedTestEntitiesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGetSiteMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testGetSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		MultipleScopedTestEntity getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getSiteMultipleScopedTestEntityByExternalReferenceCode(
					postMultipleScopedTestEntity.getSiteId(),
					postMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(postMultipleScopedTestEntity, getMultipleScopedTestEntity);
		assertValid(getMultipleScopedTestEntity);
	}

	protected MultipleScopedTestEntity
			testGetSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPatchAssetLibraryMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testPatchAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		MultipleScopedTestEntity randomPatchMultipleScopedTestEntity =
			randomPatchMultipleScopedTestEntity();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MultipleScopedTestEntity patchMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				patchAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
					null,
					postMultipleScopedTestEntity.getExternalReferenceCode(),
					randomPatchMultipleScopedTestEntity);

		MultipleScopedTestEntity expectedPatchMultipleScopedTestEntity =
			postMultipleScopedTestEntity.clone();

		BeanTestUtil.copyProperties(
			randomPatchMultipleScopedTestEntity,
			expectedPatchMultipleScopedTestEntity);

		MultipleScopedTestEntity getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
					null,
					patchMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(
			expectedPatchMultipleScopedTestEntity, getMultipleScopedTestEntity);
		assertValid(getMultipleScopedTestEntity);
	}

	protected MultipleScopedTestEntity
			testPatchAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPatchMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testPatchMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		MultipleScopedTestEntity randomPatchMultipleScopedTestEntity =
			randomPatchMultipleScopedTestEntity();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MultipleScopedTestEntity patchMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				patchMultipleScopedTestEntityByExternalReferenceCode(
					postMultipleScopedTestEntity.getExternalReferenceCode(),
					randomPatchMultipleScopedTestEntity);

		MultipleScopedTestEntity expectedPatchMultipleScopedTestEntity =
			postMultipleScopedTestEntity.clone();

		BeanTestUtil.copyProperties(
			randomPatchMultipleScopedTestEntity,
			expectedPatchMultipleScopedTestEntity);

		MultipleScopedTestEntity getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getMultipleScopedTestEntityByExternalReferenceCode(
					patchMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(
			expectedPatchMultipleScopedTestEntity, getMultipleScopedTestEntity);
		assertValid(getMultipleScopedTestEntity);
	}

	protected MultipleScopedTestEntity
			testPatchMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPatchSiteMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testPatchSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		MultipleScopedTestEntity randomPatchMultipleScopedTestEntity =
			randomPatchMultipleScopedTestEntity();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		MultipleScopedTestEntity patchMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				patchSiteMultipleScopedTestEntityByExternalReferenceCode(
					postMultipleScopedTestEntity.getSiteId(),
					postMultipleScopedTestEntity.getExternalReferenceCode(),
					randomPatchMultipleScopedTestEntity);

		MultipleScopedTestEntity expectedPatchMultipleScopedTestEntity =
			postMultipleScopedTestEntity.clone();

		BeanTestUtil.copyProperties(
			randomPatchMultipleScopedTestEntity,
			expectedPatchMultipleScopedTestEntity);

		MultipleScopedTestEntity getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getSiteMultipleScopedTestEntityByExternalReferenceCode(
					patchMultipleScopedTestEntity.getSiteId(),
					patchMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(
			expectedPatchMultipleScopedTestEntity, getMultipleScopedTestEntity);
		assertValid(getMultipleScopedTestEntity);
	}

	protected MultipleScopedTestEntity
			testPatchSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostAssetLibraryMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity randomMultipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testPostAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity(
				randomMultipleScopedTestEntity);

		assertEquals(
			randomMultipleScopedTestEntity, postMultipleScopedTestEntity);
		assertValid(postMultipleScopedTestEntity);
	}

	protected MultipleScopedTestEntity
			testPostAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity(
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity randomMultipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testPostMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity(
				randomMultipleScopedTestEntity);

		assertEquals(
			randomMultipleScopedTestEntity, postMultipleScopedTestEntity);
		assertValid(postMultipleScopedTestEntity);
	}

	protected MultipleScopedTestEntity
			testPostMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity(
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostSiteMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity randomMultipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testPostSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity(
				randomMultipleScopedTestEntity);

		assertEquals(
			randomMultipleScopedTestEntity, postMultipleScopedTestEntity);
		assertValid(postMultipleScopedTestEntity);
	}

	protected MultipleScopedTestEntity
			testPostSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity(
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		MultipleScopedTestEntity randomMultipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		MultipleScopedTestEntity putMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				putAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
					testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId(),
					postMultipleScopedTestEntity.getExternalReferenceCode(),
					randomMultipleScopedTestEntity);

		assertEquals(
			randomMultipleScopedTestEntity, putMultipleScopedTestEntity);
		assertValid(putMultipleScopedTestEntity);

		MultipleScopedTestEntity getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
					testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId(),
					putMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(
			randomMultipleScopedTestEntity, getMultipleScopedTestEntity);
		assertValid(getMultipleScopedTestEntity);

		MultipleScopedTestEntity newMultipleScopedTestEntity =
			testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_createMultipleScopedTestEntity();

		putMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				putAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
					testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId(),
					newMultipleScopedTestEntity.getExternalReferenceCode(),
					newMultipleScopedTestEntity);

		assertEquals(newMultipleScopedTestEntity, putMultipleScopedTestEntity);
		assertValid(putMultipleScopedTestEntity);

		getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
					testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId(),
					putMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(newMultipleScopedTestEntity, getMultipleScopedTestEntity);

		Assert.assertEquals(
			newMultipleScopedTestEntity.getExternalReferenceCode(),
			putMultipleScopedTestEntity.getExternalReferenceCode());
	}

	protected MultipleScopedTestEntity
			testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MultipleScopedTestEntity
			testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_createMultipleScopedTestEntity()
		throws Exception {

		return randomMultipleScopedTestEntity();
	}

	@Test
	public void testPutMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testPutMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		MultipleScopedTestEntity randomMultipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		MultipleScopedTestEntity putMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				putMultipleScopedTestEntityByExternalReferenceCode(
					postMultipleScopedTestEntity.getExternalReferenceCode(),
					randomMultipleScopedTestEntity);

		assertEquals(
			randomMultipleScopedTestEntity, putMultipleScopedTestEntity);
		assertValid(putMultipleScopedTestEntity);

		MultipleScopedTestEntity getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getMultipleScopedTestEntityByExternalReferenceCode(
					putMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(
			randomMultipleScopedTestEntity, getMultipleScopedTestEntity);
		assertValid(getMultipleScopedTestEntity);

		MultipleScopedTestEntity newMultipleScopedTestEntity =
			testPutMultipleScopedTestEntityByExternalReferenceCode_createMultipleScopedTestEntity();

		putMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				putMultipleScopedTestEntityByExternalReferenceCode(
					newMultipleScopedTestEntity.getExternalReferenceCode(),
					newMultipleScopedTestEntity);

		assertEquals(newMultipleScopedTestEntity, putMultipleScopedTestEntity);
		assertValid(putMultipleScopedTestEntity);

		getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getMultipleScopedTestEntityByExternalReferenceCode(
					putMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(newMultipleScopedTestEntity, getMultipleScopedTestEntity);

		Assert.assertEquals(
			newMultipleScopedTestEntity.getExternalReferenceCode(),
			putMultipleScopedTestEntity.getExternalReferenceCode());
	}

	protected MultipleScopedTestEntity
			testPutMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MultipleScopedTestEntity
			testPutMultipleScopedTestEntityByExternalReferenceCode_createMultipleScopedTestEntity()
		throws Exception {

		return randomMultipleScopedTestEntity();
	}

	@Test
	public void testPutSiteMultipleScopedTestEntityByExternalReferenceCode()
		throws Exception {

		MultipleScopedTestEntity postMultipleScopedTestEntity =
			testPutSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();

		MultipleScopedTestEntity randomMultipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		MultipleScopedTestEntity putMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				putSiteMultipleScopedTestEntityByExternalReferenceCode(
					postMultipleScopedTestEntity.getSiteId(),
					postMultipleScopedTestEntity.getExternalReferenceCode(),
					randomMultipleScopedTestEntity);

		assertEquals(
			randomMultipleScopedTestEntity, putMultipleScopedTestEntity);
		assertValid(putMultipleScopedTestEntity);

		MultipleScopedTestEntity getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getSiteMultipleScopedTestEntityByExternalReferenceCode(
					putMultipleScopedTestEntity.getSiteId(),
					putMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(
			randomMultipleScopedTestEntity, getMultipleScopedTestEntity);
		assertValid(getMultipleScopedTestEntity);

		MultipleScopedTestEntity newMultipleScopedTestEntity =
			testPutSiteMultipleScopedTestEntityByExternalReferenceCode_createMultipleScopedTestEntity();

		putMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				putSiteMultipleScopedTestEntityByExternalReferenceCode(
					newMultipleScopedTestEntity.getSiteId(),
					newMultipleScopedTestEntity.getExternalReferenceCode(),
					newMultipleScopedTestEntity);

		assertEquals(newMultipleScopedTestEntity, putMultipleScopedTestEntity);
		assertValid(putMultipleScopedTestEntity);

		getMultipleScopedTestEntity =
			multipleScopedTestEntityResource.
				getSiteMultipleScopedTestEntityByExternalReferenceCode(
					putMultipleScopedTestEntity.getSiteId(),
					putMultipleScopedTestEntity.getExternalReferenceCode());

		assertEquals(newMultipleScopedTestEntity, getMultipleScopedTestEntity);

		Assert.assertEquals(
			newMultipleScopedTestEntity.getExternalReferenceCode(),
			putMultipleScopedTestEntity.getExternalReferenceCode());
	}

	protected MultipleScopedTestEntity
			testPutSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected MultipleScopedTestEntity
			testPutSiteMultipleScopedTestEntityByExternalReferenceCode_createMultipleScopedTestEntity()
		throws Exception {

		return randomMultipleScopedTestEntity();
	}

	@Test
	public void testBatchEngineDeleteImportTask() throws Exception {
		MultipleScopedTestEntity multipleScopedTestEntity1 =
			testBatchEngineDeleteImportTask_addMultipleScopedTestEntity();

		testBatchEngineDeleteImportTask_deleteMultipleScopedTestEntity(
			200, multipleScopedTestEntity1.getExternalReferenceCode());
	}

	protected MultipleScopedTestEntity
			testBatchEngineDeleteImportTask_addMultipleScopedTestEntity()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void
			testBatchEngineDeleteImportTask_deleteMultipleScopedTestEntity(
				int expectedStatusCode, String externalReferenceCode,
				String... parameters)
		throws Exception {

		ImportTaskResource importTaskResource = ImportTaskResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).parameters(
			parameters
		).build();

		HttpResponse httpResponse =
			importTaskResource.deleteImportTaskHttpResponse(
				"com.liferay.portal.tools.rest.builder.test.dto.v1_0.MultipleScopedTestEntity",
				null, null, null, null,
				JSONUtil.putAll(
					JSONUtil.put(
						"externalReferenceCode", () -> externalReferenceCode)));

		Assert.assertEquals(expectedStatusCode, httpResponse.getStatusCode());

		if (expectedStatusCode == 200) {
			waitForFinish(
				"COMPLETED",
				JSONFactoryUtil.createJSONObject(httpResponse.getContent()));
		}
	}

	protected void assertContains(
		MultipleScopedTestEntity multipleScopedTestEntity,
		List<MultipleScopedTestEntity> multipleScopedTestEntities) {

		boolean contains = false;

		for (MultipleScopedTestEntity item : multipleScopedTestEntities) {
			if (equals(multipleScopedTestEntity, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			multipleScopedTestEntities + " does not contain " +
				multipleScopedTestEntity,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		MultipleScopedTestEntity multipleScopedTestEntity1,
		MultipleScopedTestEntity multipleScopedTestEntity2) {

		Assert.assertTrue(
			multipleScopedTestEntity1 + " does not equal " +
				multipleScopedTestEntity2,
			equals(multipleScopedTestEntity1, multipleScopedTestEntity2));
	}

	protected void assertEquals(
		List<MultipleScopedTestEntity> multipleScopedTestEntities1,
		List<MultipleScopedTestEntity> multipleScopedTestEntities2) {

		Assert.assertEquals(
			multipleScopedTestEntities1.size(),
			multipleScopedTestEntities2.size());

		for (int i = 0; i < multipleScopedTestEntities1.size(); i++) {
			MultipleScopedTestEntity multipleScopedTestEntity1 =
				multipleScopedTestEntities1.get(i);
			MultipleScopedTestEntity multipleScopedTestEntity2 =
				multipleScopedTestEntities2.get(i);

			assertEquals(multipleScopedTestEntity1, multipleScopedTestEntity2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<MultipleScopedTestEntity> multipleScopedTestEntities1,
		List<MultipleScopedTestEntity> multipleScopedTestEntities2) {

		Assert.assertEquals(
			multipleScopedTestEntities1.size(),
			multipleScopedTestEntities2.size());

		for (MultipleScopedTestEntity multipleScopedTestEntity1 :
				multipleScopedTestEntities1) {

			boolean contains = false;

			for (MultipleScopedTestEntity multipleScopedTestEntity2 :
					multipleScopedTestEntities2) {

				if (equals(
						multipleScopedTestEntity1, multipleScopedTestEntity2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				multipleScopedTestEntities2 + " does not contain " +
					multipleScopedTestEntity1,
				contains);
		}
	}

	protected void assertValid(
			MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		boolean valid = true;

		if (multipleScopedTestEntity.getDateCreated() == null) {
			valid = false;
		}

		if (multipleScopedTestEntity.getDateModified() == null) {
			valid = false;
		}

		if (!Objects.equals(
				multipleScopedTestEntity.getAssetLibraryKey(),
				testDepotEntryGroup.getGroupKey()) &&
			!Objects.equals(
				multipleScopedTestEntity.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assetLibraryKey", additionalAssertFieldName)) {
				if (multipleScopedTestEntity.getAssetLibraryKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (multipleScopedTestEntity.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (multipleScopedTestEntity.getExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("permissions", additionalAssertFieldName)) {
				if (multipleScopedTestEntity.getPermissions() == null) {
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

	protected void assertValid(Page<MultipleScopedTestEntity> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<MultipleScopedTestEntity> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<MultipleScopedTestEntity>
			multipleScopedTestEntities = page.getItems();

		int size = multipleScopedTestEntities.size();

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

		graphQLFields.add(new GraphQLField("siteId"));

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.portal.tools.rest.builder.test.dto.v1_0.
						MultipleScopedTestEntity.class)) {

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
		MultipleScopedTestEntity multipleScopedTestEntity1,
		MultipleScopedTestEntity multipleScopedTestEntity2) {

		if (multipleScopedTestEntity1 == multipleScopedTestEntity2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						multipleScopedTestEntity1.getDateCreated(),
						multipleScopedTestEntity2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						multipleScopedTestEntity1.getDateModified(),
						multipleScopedTestEntity2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						multipleScopedTestEntity1.getDescription(),
						multipleScopedTestEntity2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						multipleScopedTestEntity1.getExternalReferenceCode(),
						multipleScopedTestEntity2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("permissions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						multipleScopedTestEntity1.getPermissions(),
						multipleScopedTestEntity2.getPermissions())) {

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

		if (!(_multipleScopedTestEntityResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_multipleScopedTestEntityResource;

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
		EntityField entityField, String operator,
		MultipleScopedTestEntity multipleScopedTestEntity) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("assetLibraryKey")) {
			Object object = multipleScopedTestEntity.getAssetLibraryKey();

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

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				Date date = multipleScopedTestEntity.getDateCreated();

				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(_format.format(date.getTime() - (2 * Time.SECOND)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(_format.format(date.getTime() + (2 * Time.SECOND)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_format.format(multipleScopedTestEntity.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				Date date = multipleScopedTestEntity.getDateModified();

				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(_format.format(date.getTime() - (2 * Time.SECOND)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(_format.format(date.getTime() + (2 * Time.SECOND)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_format.format(multipleScopedTestEntity.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			Object object = multipleScopedTestEntity.getDescription();

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

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = multipleScopedTestEntity.getExternalReferenceCode();

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

		if (entityFieldName.equals("permissions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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
		httpInvoker.path("http://localhost:8080/o/graphql");
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

	protected MultipleScopedTestEntity randomMultipleScopedTestEntity()
		throws Exception {

		return new MultipleScopedTestEntity() {
			{
				assetLibraryKey = String.valueOf(
					testDepotEntry.getDepotEntryId());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected MultipleScopedTestEntity
			randomIrrelevantMultipleScopedTestEntity()
		throws Exception {

		MultipleScopedTestEntity randomIrrelevantMultipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		randomIrrelevantMultipleScopedTestEntity.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantMultipleScopedTestEntity;
	}

	protected MultipleScopedTestEntity randomPatchMultipleScopedTestEntity()
		throws Exception {

		return randomMultipleScopedTestEntity();
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

	protected MultipleScopedTestEntityResource multipleScopedTestEntityResource;
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
		LogFactoryUtil.getLog(
			BaseMultipleScopedTestEntityResourceTestCase.class);

	private static Format _format;

	private com.liferay.portal.kernel.model.User _testCompanyAdminUser;

	@Inject
	private com.liferay.portal.tools.rest.builder.test.resource.v1_0.
		MultipleScopedTestEntityResource _multipleScopedTestEntityResource;

}