/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentFolderResource;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentFolderSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseStructuredContentFolderResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		testDepotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(testGroup.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});

		_structuredContentFolderResource.setContextCompany(testCompany);

		StructuredContentFolderResource.Builder builder =
			StructuredContentFolderResource.builder();

		structuredContentFolderResource = builder.authentication(
			"test@liferay.com", "test"
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
		ObjectMapper objectMapper = new ObjectMapper() {
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

		StructuredContentFolder structuredContentFolder1 =
			randomStructuredContentFolder();

		String json = objectMapper.writeValueAsString(structuredContentFolder1);

		StructuredContentFolder structuredContentFolder2 =
			StructuredContentFolderSerDes.toDTO(json);

		Assert.assertTrue(
			equals(structuredContentFolder1, structuredContentFolder2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		StructuredContentFolder structuredContentFolder =
			randomStructuredContentFolder();

		String json1 = objectMapper.writeValueAsString(structuredContentFolder);
		String json2 = StructuredContentFolderSerDes.toJSON(
			structuredContentFolder);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		StructuredContentFolder structuredContentFolder =
			randomStructuredContentFolder();

		structuredContentFolder.setAssetLibraryKey(regex);
		structuredContentFolder.setDescription(regex);
		structuredContentFolder.setName(regex);

		String json = StructuredContentFolderSerDes.toJSON(
			structuredContentFolder);

		Assert.assertFalse(json.contains(regex));

		structuredContentFolder = StructuredContentFolderSerDes.toDTO(json);

		Assert.assertEquals(
			regex, structuredContentFolder.getAssetLibraryKey());
		Assert.assertEquals(regex, structuredContentFolder.getDescription());
		Assert.assertEquals(regex, structuredContentFolder.getName());
	}

	@Test
	public void testGetAssetLibraryStructuredContentFoldersPage()
		throws Exception {

		Page<StructuredContentFolder> page =
			structuredContentFolderResource.
				getAssetLibraryStructuredContentFoldersPage(
					testGetAssetLibraryStructuredContentFoldersPage_getAssetLibraryId(),
					null, RandomTestUtil.randomString(), null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentFoldersPage_getAssetLibraryId();
		Long irrelevantAssetLibraryId =
			testGetAssetLibraryStructuredContentFoldersPage_getIrrelevantAssetLibraryId();

		if (irrelevantAssetLibraryId != null) {
			StructuredContentFolder irrelevantStructuredContentFolder =
				testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
					irrelevantAssetLibraryId,
					randomIrrelevantStructuredContentFolder());

			page =
				structuredContentFolderResource.
					getAssetLibraryStructuredContentFoldersPage(
						irrelevantAssetLibraryId, null, null, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContentFolder),
				(List<StructuredContentFolder>)page.getItems());
			assertValid(page);
		}

		StructuredContentFolder structuredContentFolder1 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, randomStructuredContentFolder());

		StructuredContentFolder structuredContentFolder2 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, randomStructuredContentFolder());

		page =
			structuredContentFolderResource.
				getAssetLibraryStructuredContentFoldersPage(
					assetLibraryId, null, null, null, null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContentFolder1, structuredContentFolder2),
			(List<StructuredContentFolder>)page.getItems());
		assertValid(page);

		structuredContentFolderResource.deleteStructuredContentFolder(
			structuredContentFolder1.getId());

		structuredContentFolderResource.deleteStructuredContentFolder(
			structuredContentFolder2.getId());
	}

	@Test
	public void testGetAssetLibraryStructuredContentFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentFoldersPage_getAssetLibraryId();

		StructuredContentFolder structuredContentFolder1 =
			randomStructuredContentFolder();

		structuredContentFolder1 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, structuredContentFolder1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContentFolder> page =
				structuredContentFolderResource.
					getAssetLibraryStructuredContentFoldersPage(
						assetLibraryId, null, null, null,
						getFilterString(
							entityField, "between", structuredContentFolder1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContentFolder1),
				(List<StructuredContentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryStructuredContentFoldersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentFoldersPage_getAssetLibraryId();

		StructuredContentFolder structuredContentFolder1 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, randomStructuredContentFolder());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder structuredContentFolder2 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, randomStructuredContentFolder());

		for (EntityField entityField : entityFields) {
			Page<StructuredContentFolder> page =
				structuredContentFolderResource.
					getAssetLibraryStructuredContentFoldersPage(
						assetLibraryId, null, null, null,
						getFilterString(
							entityField, "eq", structuredContentFolder1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContentFolder1),
				(List<StructuredContentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryStructuredContentFoldersPageWithPagination()
		throws Exception {

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentFoldersPage_getAssetLibraryId();

		StructuredContentFolder structuredContentFolder1 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, randomStructuredContentFolder());

		StructuredContentFolder structuredContentFolder2 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, randomStructuredContentFolder());

		StructuredContentFolder structuredContentFolder3 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, randomStructuredContentFolder());

		Page<StructuredContentFolder> page1 =
			structuredContentFolderResource.
				getAssetLibraryStructuredContentFoldersPage(
					assetLibraryId, null, null, null, null, Pagination.of(1, 2),
					null);

		List<StructuredContentFolder> structuredContentFolders1 =
			(List<StructuredContentFolder>)page1.getItems();

		Assert.assertEquals(
			structuredContentFolders1.toString(), 2,
			structuredContentFolders1.size());

		Page<StructuredContentFolder> page2 =
			structuredContentFolderResource.
				getAssetLibraryStructuredContentFoldersPage(
					assetLibraryId, null, null, null, null, Pagination.of(2, 2),
					null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContentFolder> structuredContentFolders2 =
			(List<StructuredContentFolder>)page2.getItems();

		Assert.assertEquals(
			structuredContentFolders2.toString(), 1,
			structuredContentFolders2.size());

		Page<StructuredContentFolder> page3 =
			structuredContentFolderResource.
				getAssetLibraryStructuredContentFoldersPage(
					assetLibraryId, null, null, null, null, Pagination.of(1, 3),
					null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContentFolder1, structuredContentFolder2,
				structuredContentFolder3),
			(List<StructuredContentFolder>)page3.getItems());
	}

	@Test
	public void testGetAssetLibraryStructuredContentFoldersPageWithSortDateTime()
		throws Exception {

		testGetAssetLibraryStructuredContentFoldersPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContentFolder1, structuredContentFolder2) ->{
				BeanUtils.setProperty(
					structuredContentFolder1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAssetLibraryStructuredContentFoldersPageWithSortInteger()
		throws Exception {

		testGetAssetLibraryStructuredContentFoldersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContentFolder1, structuredContentFolder2) ->{
				BeanUtils.setProperty(
					structuredContentFolder1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContentFolder2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAssetLibraryStructuredContentFoldersPageWithSortString()
		throws Exception {

		testGetAssetLibraryStructuredContentFoldersPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContentFolder1, structuredContentFolder2) ->{
				Class<?> clazz = structuredContentFolder1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContentFolder1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContentFolder2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						structuredContentFolder1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						structuredContentFolder2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						structuredContentFolder1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						structuredContentFolder2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAssetLibraryStructuredContentFoldersPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContentFolder, StructuredContentFolder,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentFoldersPage_getAssetLibraryId();

		StructuredContentFolder structuredContentFolder1 =
			randomStructuredContentFolder();
		StructuredContentFolder structuredContentFolder2 =
			randomStructuredContentFolder();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContentFolder1,
				structuredContentFolder2);
		}

		structuredContentFolder1 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, structuredContentFolder1);

		structuredContentFolder2 =
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				assetLibraryId, structuredContentFolder2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContentFolder> ascPage =
				structuredContentFolderResource.
					getAssetLibraryStructuredContentFoldersPage(
						assetLibraryId, null, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(
					structuredContentFolder1, structuredContentFolder2),
				(List<StructuredContentFolder>)ascPage.getItems());

			Page<StructuredContentFolder> descPage =
				structuredContentFolderResource.
					getAssetLibraryStructuredContentFoldersPage(
						assetLibraryId, null, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(
					structuredContentFolder2, structuredContentFolder1),
				(List<StructuredContentFolder>)descPage.getItems());
		}
	}

	protected StructuredContentFolder
			testGetAssetLibraryStructuredContentFoldersPage_addStructuredContentFolder(
				Long assetLibraryId,
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return structuredContentFolderResource.
			postAssetLibraryStructuredContentFolder(
				assetLibraryId, structuredContentFolder);
	}

	protected Long
			testGetAssetLibraryStructuredContentFoldersPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	protected Long
			testGetAssetLibraryStructuredContentFoldersPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAssetLibraryStructuredContentFolder() throws Exception {
		StructuredContentFolder randomStructuredContentFolder =
			randomStructuredContentFolder();

		StructuredContentFolder postStructuredContentFolder =
			testPostAssetLibraryStructuredContentFolder_addStructuredContentFolder(
				randomStructuredContentFolder);

		assertEquals(
			randomStructuredContentFolder, postStructuredContentFolder);
		assertValid(postStructuredContentFolder);
	}

	protected StructuredContentFolder
			testPostAssetLibraryStructuredContentFolder_addStructuredContentFolder(
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return structuredContentFolderResource.
			postAssetLibraryStructuredContentFolder(
				testGetAssetLibraryStructuredContentFoldersPage_getAssetLibraryId(),
				structuredContentFolder);
	}

	@Test
	public void testGetAssetLibraryStructuredContentFolderPermissionsPage()
		throws Exception {

		Page<Permission> page =
			structuredContentFolderResource.
				getAssetLibraryStructuredContentFolderPermissionsPage(
					testDepotEntry.getDepotEntryId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected StructuredContentFolder
			testGetAssetLibraryStructuredContentFolderPermissionsPage_addStructuredContentFolder()
		throws Exception {

		return testPostAssetLibraryStructuredContentFolder_addStructuredContentFolder(
			randomStructuredContentFolder());
	}

	@Test
	public void testPutAssetLibraryStructuredContentFolderPermission()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder structuredContentFolder =
			testPutAssetLibraryStructuredContentFolderPermission_addStructuredContentFolder();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			structuredContentFolderResource.
				putAssetLibraryStructuredContentFolderPermissionHttpResponse(
					testDepotEntry.getDepotEntryId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"PERMISSIONS"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			structuredContentFolderResource.
				putAssetLibraryStructuredContentFolderPermissionHttpResponse(
					testDepotEntry.getDepotEntryId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected StructuredContentFolder
			testPutAssetLibraryStructuredContentFolderPermission_addStructuredContentFolder()
		throws Exception {

		return structuredContentFolderResource.
			postAssetLibraryStructuredContentFolder(
				testDepotEntry.getDepotEntryId(),
				randomStructuredContentFolder());
	}

	@Test
	public void testGetSiteStructuredContentFoldersPage() throws Exception {
		Page<StructuredContentFolder> page =
			structuredContentFolderResource.getSiteStructuredContentFoldersPage(
				testGetSiteStructuredContentFoldersPage_getSiteId(), null,
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteStructuredContentFoldersPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteStructuredContentFoldersPage_getIrrelevantSiteId();

		if (irrelevantSiteId != null) {
			StructuredContentFolder irrelevantStructuredContentFolder =
				testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
					irrelevantSiteId,
					randomIrrelevantStructuredContentFolder());

			page =
				structuredContentFolderResource.
					getSiteStructuredContentFoldersPage(
						irrelevantSiteId, null, null, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContentFolder),
				(List<StructuredContentFolder>)page.getItems());
			assertValid(page);
		}

		StructuredContentFolder structuredContentFolder1 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, randomStructuredContentFolder());

		StructuredContentFolder structuredContentFolder2 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, randomStructuredContentFolder());

		page =
			structuredContentFolderResource.getSiteStructuredContentFoldersPage(
				siteId, null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContentFolder1, structuredContentFolder2),
			(List<StructuredContentFolder>)page.getItems());
		assertValid(page);

		structuredContentFolderResource.deleteStructuredContentFolder(
			structuredContentFolder1.getId());

		structuredContentFolderResource.deleteStructuredContentFolder(
			structuredContentFolder2.getId());
	}

	@Test
	public void testGetSiteStructuredContentFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentFoldersPage_getSiteId();

		StructuredContentFolder structuredContentFolder1 =
			randomStructuredContentFolder();

		structuredContentFolder1 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, structuredContentFolder1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContentFolder> page =
				structuredContentFolderResource.
					getSiteStructuredContentFoldersPage(
						siteId, null, null, null,
						getFilterString(
							entityField, "between", structuredContentFolder1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContentFolder1),
				(List<StructuredContentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetSiteStructuredContentFoldersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentFoldersPage_getSiteId();

		StructuredContentFolder structuredContentFolder1 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, randomStructuredContentFolder());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder structuredContentFolder2 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, randomStructuredContentFolder());

		for (EntityField entityField : entityFields) {
			Page<StructuredContentFolder> page =
				structuredContentFolderResource.
					getSiteStructuredContentFoldersPage(
						siteId, null, null, null,
						getFilterString(
							entityField, "eq", structuredContentFolder1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContentFolder1),
				(List<StructuredContentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetSiteStructuredContentFoldersPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteStructuredContentFoldersPage_getSiteId();

		StructuredContentFolder structuredContentFolder1 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, randomStructuredContentFolder());

		StructuredContentFolder structuredContentFolder2 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, randomStructuredContentFolder());

		StructuredContentFolder structuredContentFolder3 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, randomStructuredContentFolder());

		Page<StructuredContentFolder> page1 =
			structuredContentFolderResource.getSiteStructuredContentFoldersPage(
				siteId, null, null, null, null, Pagination.of(1, 2), null);

		List<StructuredContentFolder> structuredContentFolders1 =
			(List<StructuredContentFolder>)page1.getItems();

		Assert.assertEquals(
			structuredContentFolders1.toString(), 2,
			structuredContentFolders1.size());

		Page<StructuredContentFolder> page2 =
			structuredContentFolderResource.getSiteStructuredContentFoldersPage(
				siteId, null, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContentFolder> structuredContentFolders2 =
			(List<StructuredContentFolder>)page2.getItems();

		Assert.assertEquals(
			structuredContentFolders2.toString(), 1,
			structuredContentFolders2.size());

		Page<StructuredContentFolder> page3 =
			structuredContentFolderResource.getSiteStructuredContentFoldersPage(
				siteId, null, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContentFolder1, structuredContentFolder2,
				structuredContentFolder3),
			(List<StructuredContentFolder>)page3.getItems());
	}

	@Test
	public void testGetSiteStructuredContentFoldersPageWithSortDateTime()
		throws Exception {

		testGetSiteStructuredContentFoldersPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContentFolder1, structuredContentFolder2) ->{
				BeanUtils.setProperty(
					structuredContentFolder1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteStructuredContentFoldersPageWithSortInteger()
		throws Exception {

		testGetSiteStructuredContentFoldersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContentFolder1, structuredContentFolder2) ->{
				BeanUtils.setProperty(
					structuredContentFolder1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContentFolder2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteStructuredContentFoldersPageWithSortString()
		throws Exception {

		testGetSiteStructuredContentFoldersPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContentFolder1, structuredContentFolder2) ->{
				Class<?> clazz = structuredContentFolder1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContentFolder1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContentFolder2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						structuredContentFolder1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						structuredContentFolder2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						structuredContentFolder1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						structuredContentFolder2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteStructuredContentFoldersPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContentFolder, StructuredContentFolder,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentFoldersPage_getSiteId();

		StructuredContentFolder structuredContentFolder1 =
			randomStructuredContentFolder();
		StructuredContentFolder structuredContentFolder2 =
			randomStructuredContentFolder();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContentFolder1,
				structuredContentFolder2);
		}

		structuredContentFolder1 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, structuredContentFolder1);

		structuredContentFolder2 =
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				siteId, structuredContentFolder2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContentFolder> ascPage =
				structuredContentFolderResource.
					getSiteStructuredContentFoldersPage(
						siteId, null, null, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(
					structuredContentFolder1, structuredContentFolder2),
				(List<StructuredContentFolder>)ascPage.getItems());

			Page<StructuredContentFolder> descPage =
				structuredContentFolderResource.
					getSiteStructuredContentFoldersPage(
						siteId, null, null, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(
					structuredContentFolder2, structuredContentFolder1),
				(List<StructuredContentFolder>)descPage.getItems());
		}
	}

	protected StructuredContentFolder
			testGetSiteStructuredContentFoldersPage_addStructuredContentFolder(
				Long siteId, StructuredContentFolder structuredContentFolder)
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			siteId, structuredContentFolder);
	}

	protected Long testGetSiteStructuredContentFoldersPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteStructuredContentFoldersPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteStructuredContentFoldersPage()
		throws Exception {

		Long siteId = testGetSiteStructuredContentFoldersPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"structuredContentFolders",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject structuredContentFoldersJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/structuredContentFolders");

		Assert.assertEquals(
			0, structuredContentFoldersJSONObject.get("totalCount"));

		StructuredContentFolder structuredContentFolder1 =
			testGraphQLStructuredContentFolder_addStructuredContentFolder();
		StructuredContentFolder structuredContentFolder2 =
			testGraphQLStructuredContentFolder_addStructuredContentFolder();

		structuredContentFoldersJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/structuredContentFolders");

		Assert.assertEquals(
			2, structuredContentFoldersJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContentFolder1, structuredContentFolder2),
			Arrays.asList(
				StructuredContentFolderSerDes.toDTOs(
					structuredContentFoldersJSONObject.getString("items"))));
	}

	@Test
	public void testPostSiteStructuredContentFolder() throws Exception {
		StructuredContentFolder randomStructuredContentFolder =
			randomStructuredContentFolder();

		StructuredContentFolder postStructuredContentFolder =
			testPostSiteStructuredContentFolder_addStructuredContentFolder(
				randomStructuredContentFolder);

		assertEquals(
			randomStructuredContentFolder, postStructuredContentFolder);
		assertValid(postStructuredContentFolder);
	}

	protected StructuredContentFolder
			testPostSiteStructuredContentFolder_addStructuredContentFolder(
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			testGetSiteStructuredContentFoldersPage_getSiteId(),
			structuredContentFolder);
	}

	@Test
	public void testGraphQLPostSiteStructuredContentFolder() throws Exception {
		StructuredContentFolder randomStructuredContentFolder =
			randomStructuredContentFolder();

		StructuredContentFolder structuredContentFolder =
			testGraphQLStructuredContentFolder_addStructuredContentFolder(
				randomStructuredContentFolder);

		Assert.assertTrue(
			equals(randomStructuredContentFolder, structuredContentFolder));
	}

	@Test
	public void testGetSiteStructuredContentFolderPermissionsPage()
		throws Exception {

		Page<Permission> page =
			structuredContentFolderResource.
				getSiteStructuredContentFolderPermissionsPage(
					testGroup.getGroupId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected StructuredContentFolder
			testGetSiteStructuredContentFolderPermissionsPage_addStructuredContentFolder()
		throws Exception {

		return testPostSiteStructuredContentFolder_addStructuredContentFolder(
			randomStructuredContentFolder());
	}

	@Test
	public void testPutSiteStructuredContentFolderPermission()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder structuredContentFolder =
			testPutSiteStructuredContentFolderPermission_addStructuredContentFolder();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			structuredContentFolderResource.
				putSiteStructuredContentFolderPermissionHttpResponse(
					structuredContentFolder.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"PERMISSIONS"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			structuredContentFolderResource.
				putSiteStructuredContentFolderPermissionHttpResponse(
					structuredContentFolder.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected StructuredContentFolder
			testPutSiteStructuredContentFolderPermission_addStructuredContentFolder()
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Test
	public void testGetStructuredContentFolderPermissionsPage()
		throws Exception {

		StructuredContentFolder postStructuredContentFolder =
			testGetStructuredContentFolderPermissionsPage_addStructuredContentFolder();

		Page<Permission> page =
			structuredContentFolderResource.
				getStructuredContentFolderPermissionsPage(
					postStructuredContentFolder.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected StructuredContentFolder
			testGetStructuredContentFolderPermissionsPage_addStructuredContentFolder()
		throws Exception {

		return testPostStructuredContentFolderStructuredContentFolder_addStructuredContentFolder(
			randomStructuredContentFolder());
	}

	@Test
	public void testPutStructuredContentFolderPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder structuredContentFolder =
			testPutStructuredContentFolderPermission_addStructuredContentFolder();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			structuredContentFolderResource.
				putStructuredContentFolderPermissionHttpResponse(
					structuredContentFolder.getId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"VIEW"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			structuredContentFolderResource.
				putStructuredContentFolderPermissionHttpResponse(
					0L,
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected StructuredContentFolder
			testPutStructuredContentFolderPermission_addStructuredContentFolder()
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentFoldersPage()
		throws Exception {

		Page<StructuredContentFolder> page =
			structuredContentFolderResource.
				getStructuredContentFolderStructuredContentFoldersPage(
					testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId(),
					RandomTestUtil.randomString(), null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long parentStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId();
		Long irrelevantParentStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentFoldersPage_getIrrelevantParentStructuredContentFolderId();

		if (irrelevantParentStructuredContentFolderId != null) {
			StructuredContentFolder irrelevantStructuredContentFolder =
				testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
					irrelevantParentStructuredContentFolderId,
					randomIrrelevantStructuredContentFolder());

			page =
				structuredContentFolderResource.
					getStructuredContentFolderStructuredContentFoldersPage(
						irrelevantParentStructuredContentFolderId, null, null,
						null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContentFolder),
				(List<StructuredContentFolder>)page.getItems());
			assertValid(page);
		}

		StructuredContentFolder structuredContentFolder1 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId,
				randomStructuredContentFolder());

		StructuredContentFolder structuredContentFolder2 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId,
				randomStructuredContentFolder());

		page =
			structuredContentFolderResource.
				getStructuredContentFolderStructuredContentFoldersPage(
					parentStructuredContentFolderId, null, null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContentFolder1, structuredContentFolder2),
			(List<StructuredContentFolder>)page.getItems());
		assertValid(page);

		structuredContentFolderResource.deleteStructuredContentFolder(
			structuredContentFolder1.getId());

		structuredContentFolderResource.deleteStructuredContentFolder(
			structuredContentFolder2.getId());
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId();

		StructuredContentFolder structuredContentFolder1 =
			randomStructuredContentFolder();

		structuredContentFolder1 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId, structuredContentFolder1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContentFolder> page =
				structuredContentFolderResource.
					getStructuredContentFolderStructuredContentFoldersPage(
						parentStructuredContentFolderId, null, null,
						getFilterString(
							entityField, "between", structuredContentFolder1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContentFolder1),
				(List<StructuredContentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentFoldersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId();

		StructuredContentFolder structuredContentFolder1 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId,
				randomStructuredContentFolder());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder structuredContentFolder2 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId,
				randomStructuredContentFolder());

		for (EntityField entityField : entityFields) {
			Page<StructuredContentFolder> page =
				structuredContentFolderResource.
					getStructuredContentFolderStructuredContentFoldersPage(
						parentStructuredContentFolderId, null, null,
						getFilterString(
							entityField, "eq", structuredContentFolder1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContentFolder1),
				(List<StructuredContentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentFoldersPageWithPagination()
		throws Exception {

		Long parentStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId();

		StructuredContentFolder structuredContentFolder1 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId,
				randomStructuredContentFolder());

		StructuredContentFolder structuredContentFolder2 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId,
				randomStructuredContentFolder());

		StructuredContentFolder structuredContentFolder3 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId,
				randomStructuredContentFolder());

		Page<StructuredContentFolder> page1 =
			structuredContentFolderResource.
				getStructuredContentFolderStructuredContentFoldersPage(
					parentStructuredContentFolderId, null, null, null,
					Pagination.of(1, 2), null);

		List<StructuredContentFolder> structuredContentFolders1 =
			(List<StructuredContentFolder>)page1.getItems();

		Assert.assertEquals(
			structuredContentFolders1.toString(), 2,
			structuredContentFolders1.size());

		Page<StructuredContentFolder> page2 =
			structuredContentFolderResource.
				getStructuredContentFolderStructuredContentFoldersPage(
					parentStructuredContentFolderId, null, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContentFolder> structuredContentFolders2 =
			(List<StructuredContentFolder>)page2.getItems();

		Assert.assertEquals(
			structuredContentFolders2.toString(), 1,
			structuredContentFolders2.size());

		Page<StructuredContentFolder> page3 =
			structuredContentFolderResource.
				getStructuredContentFolderStructuredContentFoldersPage(
					parentStructuredContentFolderId, null, null, null,
					Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContentFolder1, structuredContentFolder2,
				structuredContentFolder3),
			(List<StructuredContentFolder>)page3.getItems());
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentFoldersPageWithSortDateTime()
		throws Exception {

		testGetStructuredContentFolderStructuredContentFoldersPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContentFolder1, structuredContentFolder2) ->{
				BeanUtils.setProperty(
					structuredContentFolder1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentFoldersPageWithSortInteger()
		throws Exception {

		testGetStructuredContentFolderStructuredContentFoldersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContentFolder1, structuredContentFolder2) ->{
				BeanUtils.setProperty(
					structuredContentFolder1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContentFolder2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentFoldersPageWithSortString()
		throws Exception {

		testGetStructuredContentFolderStructuredContentFoldersPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContentFolder1, structuredContentFolder2) ->{
				Class<?> clazz = structuredContentFolder1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContentFolder1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContentFolder2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						structuredContentFolder1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						structuredContentFolder2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						structuredContentFolder1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						structuredContentFolder2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void
			testGetStructuredContentFolderStructuredContentFoldersPageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, StructuredContentFolder,
					 StructuredContentFolder, Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId();

		StructuredContentFolder structuredContentFolder1 =
			randomStructuredContentFolder();
		StructuredContentFolder structuredContentFolder2 =
			randomStructuredContentFolder();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContentFolder1,
				structuredContentFolder2);
		}

		structuredContentFolder1 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId, structuredContentFolder1);

		structuredContentFolder2 =
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				parentStructuredContentFolderId, structuredContentFolder2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContentFolder> ascPage =
				structuredContentFolderResource.
					getStructuredContentFolderStructuredContentFoldersPage(
						parentStructuredContentFolderId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(
					structuredContentFolder1, structuredContentFolder2),
				(List<StructuredContentFolder>)ascPage.getItems());

			Page<StructuredContentFolder> descPage =
				structuredContentFolderResource.
					getStructuredContentFolderStructuredContentFoldersPage(
						parentStructuredContentFolderId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(
					structuredContentFolder2, structuredContentFolder1),
				(List<StructuredContentFolder>)descPage.getItems());
		}
	}

	protected StructuredContentFolder
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				Long parentStructuredContentFolderId,
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return structuredContentFolderResource.
			postStructuredContentFolderStructuredContentFolder(
				parentStructuredContentFolderId, structuredContentFolder);
	}

	protected Long
			testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetStructuredContentFolderStructuredContentFoldersPage_getIrrelevantParentStructuredContentFolderId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostStructuredContentFolderStructuredContentFolder()
		throws Exception {

		StructuredContentFolder randomStructuredContentFolder =
			randomStructuredContentFolder();

		StructuredContentFolder postStructuredContentFolder =
			testPostStructuredContentFolderStructuredContentFolder_addStructuredContentFolder(
				randomStructuredContentFolder);

		assertEquals(
			randomStructuredContentFolder, postStructuredContentFolder);
		assertValid(postStructuredContentFolder);
	}

	protected StructuredContentFolder
			testPostStructuredContentFolderStructuredContentFolder_addStructuredContentFolder(
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return structuredContentFolderResource.
			postStructuredContentFolderStructuredContentFolder(
				testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId(),
				structuredContentFolder);
	}

	@Test
	public void testDeleteStructuredContentFolder() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder structuredContentFolder =
			testDeleteStructuredContentFolder_addStructuredContentFolder();

		assertHttpResponseStatusCode(
			204,
			structuredContentFolderResource.
				deleteStructuredContentFolderHttpResponse(
					structuredContentFolder.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentFolderResource.
				getStructuredContentFolderHttpResponse(
					structuredContentFolder.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentFolderResource.
				getStructuredContentFolderHttpResponse(0L));
	}

	protected StructuredContentFolder
			testDeleteStructuredContentFolder_addStructuredContentFolder()
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Test
	public void testGraphQLDeleteStructuredContentFolder() throws Exception {
		StructuredContentFolder structuredContentFolder =
			testGraphQLStructuredContentFolder_addStructuredContentFolder();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteStructuredContentFolder",
						new HashMap<String, Object>() {
							{
								put(
									"structuredContentFolderId",
									structuredContentFolder.getId());
							}
						})),
				"JSONObject/data", "Object/deleteStructuredContentFolder"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"structuredContentFolder",
					new HashMap<String, Object>() {
						{
							put(
								"structuredContentFolderId",
								structuredContentFolder.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetStructuredContentFolder() throws Exception {
		StructuredContentFolder postStructuredContentFolder =
			testGetStructuredContentFolder_addStructuredContentFolder();

		StructuredContentFolder getStructuredContentFolder =
			structuredContentFolderResource.getStructuredContentFolder(
				postStructuredContentFolder.getId());

		assertEquals(postStructuredContentFolder, getStructuredContentFolder);
		assertValid(getStructuredContentFolder);
	}

	protected StructuredContentFolder
			testGetStructuredContentFolder_addStructuredContentFolder()
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Test
	public void testGraphQLGetStructuredContentFolder() throws Exception {
		StructuredContentFolder structuredContentFolder =
			testGraphQLStructuredContentFolder_addStructuredContentFolder();

		Assert.assertTrue(
			equals(
				structuredContentFolder,
				StructuredContentFolderSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"structuredContentFolder",
								new HashMap<String, Object>() {
									{
										put(
											"structuredContentFolderId",
											structuredContentFolder.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/structuredContentFolder"))));
	}

	@Test
	public void testGraphQLGetStructuredContentFolderNotFound()
		throws Exception {

		Long irrelevantStructuredContentFolderId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"structuredContentFolder",
						new HashMap<String, Object>() {
							{
								put(
									"structuredContentFolderId",
									irrelevantStructuredContentFolderId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchStructuredContentFolder() throws Exception {
		StructuredContentFolder postStructuredContentFolder =
			testPatchStructuredContentFolder_addStructuredContentFolder();

		StructuredContentFolder randomPatchStructuredContentFolder =
			randomPatchStructuredContentFolder();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder patchStructuredContentFolder =
			structuredContentFolderResource.patchStructuredContentFolder(
				postStructuredContentFolder.getId(),
				randomPatchStructuredContentFolder);

		StructuredContentFolder expectedPatchStructuredContentFolder =
			postStructuredContentFolder.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchStructuredContentFolder,
			randomPatchStructuredContentFolder);

		StructuredContentFolder getStructuredContentFolder =
			structuredContentFolderResource.getStructuredContentFolder(
				patchStructuredContentFolder.getId());

		assertEquals(
			expectedPatchStructuredContentFolder, getStructuredContentFolder);
		assertValid(getStructuredContentFolder);
	}

	protected StructuredContentFolder
			testPatchStructuredContentFolder_addStructuredContentFolder()
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Test
	public void testPutStructuredContentFolder() throws Exception {
		StructuredContentFolder postStructuredContentFolder =
			testPutStructuredContentFolder_addStructuredContentFolder();

		StructuredContentFolder randomStructuredContentFolder =
			randomStructuredContentFolder();

		StructuredContentFolder putStructuredContentFolder =
			structuredContentFolderResource.putStructuredContentFolder(
				postStructuredContentFolder.getId(),
				randomStructuredContentFolder);

		assertEquals(randomStructuredContentFolder, putStructuredContentFolder);
		assertValid(putStructuredContentFolder);

		StructuredContentFolder getStructuredContentFolder =
			structuredContentFolderResource.getStructuredContentFolder(
				putStructuredContentFolder.getId());

		assertEquals(randomStructuredContentFolder, getStructuredContentFolder);
		assertValid(getStructuredContentFolder);
	}

	protected StructuredContentFolder
			testPutStructuredContentFolder_addStructuredContentFolder()
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Test
	public void testPutStructuredContentFolderSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder structuredContentFolder =
			testPutStructuredContentFolderSubscribe_addStructuredContentFolder();

		assertHttpResponseStatusCode(
			204,
			structuredContentFolderResource.
				putStructuredContentFolderSubscribeHttpResponse(
					structuredContentFolder.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentFolderResource.
				putStructuredContentFolderSubscribeHttpResponse(0L));
	}

	protected StructuredContentFolder
			testPutStructuredContentFolderSubscribe_addStructuredContentFolder()
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Test
	public void testPutStructuredContentFolderUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContentFolder structuredContentFolder =
			testPutStructuredContentFolderUnsubscribe_addStructuredContentFolder();

		assertHttpResponseStatusCode(
			204,
			structuredContentFolderResource.
				putStructuredContentFolderUnsubscribeHttpResponse(
					structuredContentFolder.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentFolderResource.
				putStructuredContentFolderUnsubscribeHttpResponse(0L));
	}

	protected StructuredContentFolder
			testPutStructuredContentFolderUnsubscribe_addStructuredContentFolder()
		throws Exception {

		return structuredContentFolderResource.postSiteStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void appendGraphQLFieldValue(StringBuilder sb, Object value)
		throws Exception {

		if (value instanceof Object[]) {
			StringBuilder arraySB = new StringBuilder("[");

			for (Object object : (Object[])value) {
				if (arraySB.length() > 1) {
					arraySB.append(", ");
				}

				arraySB.append("{");

				Class<?> clazz = object.getClass();

				for (Field field : getDeclaredFields(clazz.getSuperclass())) {
					arraySB.append(field.getName());
					arraySB.append(": ");

					appendGraphQLFieldValue(arraySB, field.get(object));

					arraySB.append(", ");
				}

				arraySB.setLength(arraySB.length() - 2);

				arraySB.append("}");
			}

			arraySB.append("]");

			sb.append(arraySB.toString());
		}
		else if (value instanceof String) {
			sb.append("\"");
			sb.append(value);
			sb.append("\"");
		}
		else {
			sb.append(value);
		}
	}

	protected StructuredContentFolder
			testGraphQLStructuredContentFolder_addStructuredContentFolder()
		throws Exception {

		return testGraphQLStructuredContentFolder_addStructuredContentFolder(
			randomStructuredContentFolder());
	}

	protected StructuredContentFolder
			testGraphQLStructuredContentFolder_addStructuredContentFolder(
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		JSONDeserializer<StructuredContentFolder> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (Field field : getDeclaredFields(StructuredContentFolder.class)) {
			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(structuredContentFolder));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteStructuredContentFolder",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("structuredContentFolder", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data",
				"JSONObject/createSiteStructuredContentFolder"),
			StructuredContentFolder.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		StructuredContentFolder structuredContentFolder1,
		StructuredContentFolder structuredContentFolder2) {

		Assert.assertTrue(
			structuredContentFolder1 + " does not equal " +
				structuredContentFolder2,
			equals(structuredContentFolder1, structuredContentFolder2));
	}

	protected void assertEquals(
		List<StructuredContentFolder> structuredContentFolders1,
		List<StructuredContentFolder> structuredContentFolders2) {

		Assert.assertEquals(
			structuredContentFolders1.size(), structuredContentFolders2.size());

		for (int i = 0; i < structuredContentFolders1.size(); i++) {
			StructuredContentFolder structuredContentFolder1 =
				structuredContentFolders1.get(i);
			StructuredContentFolder structuredContentFolder2 =
				structuredContentFolders2.get(i);

			assertEquals(structuredContentFolder1, structuredContentFolder2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<StructuredContentFolder> structuredContentFolders1,
		List<StructuredContentFolder> structuredContentFolders2) {

		Assert.assertEquals(
			structuredContentFolders1.size(), structuredContentFolders2.size());

		for (StructuredContentFolder structuredContentFolder1 :
				structuredContentFolders1) {

			boolean contains = false;

			for (StructuredContentFolder structuredContentFolder2 :
					structuredContentFolders2) {

				if (equals(
						structuredContentFolder1, structuredContentFolder2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				structuredContentFolders2 + " does not contain " +
					structuredContentFolder1,
				contains);
		}
	}

	protected void assertValid(StructuredContentFolder structuredContentFolder)
		throws Exception {

		boolean valid = true;

		if (structuredContentFolder.getDateCreated() == null) {
			valid = false;
		}

		if (structuredContentFolder.getDateModified() == null) {
			valid = false;
		}

		if (structuredContentFolder.getId() == null) {
			valid = false;
		}

		Group group = testDepotEntry.getGroup();

		if (!Objects.equals(
				structuredContentFolder.getAssetLibraryKey(),
				group.getGroupKey()) &&
			!Objects.equals(
				structuredContentFolder.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (structuredContentFolder.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetLibraryKey", additionalAssertFieldName)) {
				if (structuredContentFolder.getAssetLibraryKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (structuredContentFolder.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (structuredContentFolder.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (structuredContentFolder.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (structuredContentFolder.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfStructuredContentFolders",
					additionalAssertFieldName)) {

				if (structuredContentFolder.
						getNumberOfStructuredContentFolders() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfStructuredContents", additionalAssertFieldName)) {

				if (structuredContentFolder.getNumberOfStructuredContents() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentStructuredContentFolderId",
					additionalAssertFieldName)) {

				if (structuredContentFolder.
						getParentStructuredContentFolderId() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (structuredContentFolder.getSubscribed() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (structuredContentFolder.getViewableBy() == null) {
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

	protected void assertValid(Page<StructuredContentFolder> page) {
		boolean valid = false;

		java.util.Collection<StructuredContentFolder> structuredContentFolders =
			page.getItems();

		int size = structuredContentFolders.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.
						StructuredContentFolder.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
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
		StructuredContentFolder structuredContentFolder1,
		StructuredContentFolder structuredContentFolder2) {

		if (structuredContentFolder1 == structuredContentFolder2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)structuredContentFolder1.getActions(),
						(Map)structuredContentFolder2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContentFolder1.getCreator(),
						structuredContentFolder2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContentFolder1.getCustomFields(),
						structuredContentFolder2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContentFolder1.getDateCreated(),
						structuredContentFolder2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContentFolder1.getDateModified(),
						structuredContentFolder2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContentFolder1.getDescription(),
						structuredContentFolder2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContentFolder1.getId(),
						structuredContentFolder2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContentFolder1.getName(),
						structuredContentFolder2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfStructuredContentFolders",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContentFolder1.
							getNumberOfStructuredContentFolders(),
						structuredContentFolder2.
							getNumberOfStructuredContentFolders())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfStructuredContents", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContentFolder1.
							getNumberOfStructuredContents(),
						structuredContentFolder2.
							getNumberOfStructuredContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentStructuredContentFolderId",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContentFolder1.
							getParentStructuredContentFolderId(),
						structuredContentFolder2.
							getParentStructuredContentFolderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContentFolder1.getSubscribed(),
						structuredContentFolder2.getSubscribed())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContentFolder1.getViewableBy(),
						structuredContentFolder2.getViewableBy())) {

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

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_structuredContentFolderResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_structuredContentFolderResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		StructuredContentFolder structuredContentFolder) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("assetLibraryKey")) {
			sb.append("'");
			sb.append(
				String.valueOf(structuredContentFolder.getAssetLibraryKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContentFolder.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContentFolder.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(
						structuredContentFolder.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContentFolder.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContentFolder.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(
						structuredContentFolder.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContentFolder.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContentFolder.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("numberOfStructuredContentFolders")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfStructuredContents")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentStructuredContentFolderId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subscribed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("viewableBy")) {
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
		httpInvoker.userNameAndPassword("test@liferay.com:test");

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

	protected StructuredContentFolder randomStructuredContentFolder()
		throws Exception {

		return new StructuredContentFolder() {
			{
				assetLibraryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				numberOfStructuredContentFolders = RandomTestUtil.randomInt();
				numberOfStructuredContents = RandomTestUtil.randomInt();
				parentStructuredContentFolderId = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected StructuredContentFolder randomIrrelevantStructuredContentFolder()
		throws Exception {

		StructuredContentFolder randomIrrelevantStructuredContentFolder =
			randomStructuredContentFolder();

		randomIrrelevantStructuredContentFolder.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantStructuredContentFolder;
	}

	protected StructuredContentFolder randomPatchStructuredContentFolder()
		throws Exception {

		return randomStructuredContentFolder();
	}

	protected StructuredContentFolderResource structuredContentFolderResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected DepotEntry testDepotEntry;
	protected Group testGroup;

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

	private static final Log _log = LogFactoryUtil.getLog(
		BaseStructuredContentFolderResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.headless.delivery.resource.v1_0.
			StructuredContentFolderResource _structuredContentFolderResource;

}