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
import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentSerDes;
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
public abstract class BaseStructuredContentResourceTestCase {

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

		_structuredContentResource.setContextCompany(testCompany);

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		structuredContentResource = builder.authentication(
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

		StructuredContent structuredContent1 = randomStructuredContent();

		String json = objectMapper.writeValueAsString(structuredContent1);

		StructuredContent structuredContent2 = StructuredContentSerDes.toDTO(
			json);

		Assert.assertTrue(equals(structuredContent1, structuredContent2));
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

		StructuredContent structuredContent = randomStructuredContent();

		String json1 = objectMapper.writeValueAsString(structuredContent);
		String json2 = StructuredContentSerDes.toJSON(structuredContent);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		StructuredContent structuredContent = randomStructuredContent();

		structuredContent.setAssetLibraryKey(regex);
		structuredContent.setDescription(regex);
		structuredContent.setFriendlyUrlPath(regex);
		structuredContent.setKey(regex);
		structuredContent.setTitle(regex);
		structuredContent.setUuid(regex);

		String json = StructuredContentSerDes.toJSON(structuredContent);

		Assert.assertFalse(json.contains(regex));

		structuredContent = StructuredContentSerDes.toDTO(json);

		Assert.assertEquals(regex, structuredContent.getAssetLibraryKey());
		Assert.assertEquals(regex, structuredContent.getDescription());
		Assert.assertEquals(regex, structuredContent.getFriendlyUrlPath());
		Assert.assertEquals(regex, structuredContent.getKey());
		Assert.assertEquals(regex, structuredContent.getTitle());
		Assert.assertEquals(regex, structuredContent.getUuid());
	}

	@Test
	public void testGetAssetLibraryStructuredContentsPage() throws Exception {
		Page<StructuredContent> page =
			structuredContentResource.getAssetLibraryStructuredContentsPage(
				testGetAssetLibraryStructuredContentsPage_getAssetLibraryId(),
				null, RandomTestUtil.randomString(), null, null,
				Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentsPage_getAssetLibraryId();
		Long irrelevantAssetLibraryId =
			testGetAssetLibraryStructuredContentsPage_getIrrelevantAssetLibraryId();

		if (irrelevantAssetLibraryId != null) {
			StructuredContent irrelevantStructuredContent =
				testGetAssetLibraryStructuredContentsPage_addStructuredContent(
					irrelevantAssetLibraryId,
					randomIrrelevantStructuredContent());

			page =
				structuredContentResource.getAssetLibraryStructuredContentsPage(
					irrelevantAssetLibraryId, null, null, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, randomStructuredContent());

		page = structuredContentResource.getAssetLibraryStructuredContentsPage(
			assetLibraryId, null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			structuredContent1.getId());

		structuredContentResource.deleteStructuredContent(
			structuredContent2.getId());
	}

	@Test
	public void testGetAssetLibraryStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentsPage_getAssetLibraryId();

		StructuredContent structuredContent1 = randomStructuredContent();

		structuredContent1 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, structuredContent1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.getAssetLibraryStructuredContentsPage(
					assetLibraryId, null, null, null,
					getFilterString(entityField, "between", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentsPage_getAssetLibraryId();

		StructuredContent structuredContent1 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.getAssetLibraryStructuredContentsPage(
					assetLibraryId, null, null, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryStructuredContentsPageWithPagination()
		throws Exception {

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentsPage_getAssetLibraryId();

		StructuredContent structuredContent1 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, randomStructuredContent());

		Page<StructuredContent> page1 =
			structuredContentResource.getAssetLibraryStructuredContentsPage(
				assetLibraryId, null, null, null, null, Pagination.of(1, 2),
				null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			structuredContentResource.getAssetLibraryStructuredContentsPage(
				assetLibraryId, null, null, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		Page<StructuredContent> page3 =
			structuredContentResource.getAssetLibraryStructuredContentsPage(
				assetLibraryId, null, null, null, null, Pagination.of(1, 3),
				null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			(List<StructuredContent>)page3.getItems());
	}

	@Test
	public void testGetAssetLibraryStructuredContentsPageWithSortDateTime()
		throws Exception {

		testGetAssetLibraryStructuredContentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAssetLibraryStructuredContentsPageWithSortInteger()
		throws Exception {

		testGetAssetLibraryStructuredContentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContent2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAssetLibraryStructuredContentsPageWithSortString()
		throws Exception {

		testGetAssetLibraryStructuredContentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContent1, structuredContent2) -> {
				Class<?> clazz = structuredContent1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAssetLibraryStructuredContentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContent, StructuredContent, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryStructuredContentsPage_getAssetLibraryId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContent1, structuredContent2);
		}

		structuredContent1 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, structuredContent1);

		structuredContent2 =
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				assetLibraryId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				structuredContentResource.getAssetLibraryStructuredContentsPage(
					assetLibraryId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				structuredContentResource.getAssetLibraryStructuredContentsPage(
					assetLibraryId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetAssetLibraryStructuredContentsPage_addStructuredContent(
				Long assetLibraryId, StructuredContent structuredContent)
		throws Exception {

		return structuredContentResource.postAssetLibraryStructuredContent(
			assetLibraryId, structuredContent);
	}

	protected Long testGetAssetLibraryStructuredContentsPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	protected Long
			testGetAssetLibraryStructuredContentsPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAssetLibraryStructuredContent() throws Exception {
		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			testPostAssetLibraryStructuredContent_addStructuredContent(
				randomStructuredContent);

		assertEquals(randomStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	protected StructuredContent
			testPostAssetLibraryStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		return structuredContentResource.postAssetLibraryStructuredContent(
			testGetAssetLibraryStructuredContentsPage_getAssetLibraryId(),
			structuredContent);
	}

	@Test
	public void testGetAssetLibraryStructuredContentPermissionsPage()
		throws Exception {

		Page<Permission> page =
			structuredContentResource.
				getAssetLibraryStructuredContentPermissionsPage(
					testDepotEntry.getDepotEntryId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected StructuredContent
			testGetAssetLibraryStructuredContentPermissionsPage_addStructuredContent()
		throws Exception {

		return testPostAssetLibraryStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	@Test
	public void testPutAssetLibraryStructuredContentPermission()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testPutAssetLibraryStructuredContentPermission_addStructuredContent();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			structuredContentResource.
				putAssetLibraryStructuredContentPermissionHttpResponse(
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
			structuredContentResource.
				putAssetLibraryStructuredContentPermissionHttpResponse(
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

	protected StructuredContent
			testPutAssetLibraryStructuredContentPermission_addStructuredContent()
		throws Exception {

		return structuredContentResource.postAssetLibraryStructuredContent(
			testDepotEntry.getDepotEntryId(), randomStructuredContent());
	}

	@Test
	public void testGetContentStructureStructuredContentsPage()
		throws Exception {

		Page<StructuredContent> page =
			structuredContentResource.getContentStructureStructuredContentsPage(
				testGetContentStructureStructuredContentsPage_getContentStructureId(),
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();
		Long irrelevantContentStructureId =
			testGetContentStructureStructuredContentsPage_getIrrelevantContentStructureId();

		if (irrelevantContentStructureId != null) {
			StructuredContent irrelevantStructuredContent =
				testGetContentStructureStructuredContentsPage_addStructuredContent(
					irrelevantContentStructureId,
					randomIrrelevantStructuredContent());

			page =
				structuredContentResource.
					getContentStructureStructuredContentsPage(
						irrelevantContentStructureId, null, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		page =
			structuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			structuredContent1.getId());

		structuredContentResource.deleteStructuredContent(
			structuredContent2.getId());
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 = randomStructuredContent();

		structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null, null,
						getFilterString(
							entityField, "between", structuredContent1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null, null,
						getFilterString(entityField, "eq", structuredContent1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithPagination()
		throws Exception {

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, randomStructuredContent());

		Page<StructuredContent> page1 =
			structuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, null, Pagination.of(1, 2),
				null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			structuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		Page<StructuredContent> page3 =
			structuredContentResource.getContentStructureStructuredContentsPage(
				contentStructureId, null, null, null, Pagination.of(1, 3),
				null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			(List<StructuredContent>)page3.getItems());
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortDateTime()
		throws Exception {

		testGetContentStructureStructuredContentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortInteger()
		throws Exception {

		testGetContentStructureStructuredContentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContent2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetContentStructureStructuredContentsPageWithSortString()
		throws Exception {

		testGetContentStructureStructuredContentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContent1, structuredContent2) -> {
				Class<?> clazz = structuredContent1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetContentStructureStructuredContentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContent, StructuredContent, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentStructureId =
			testGetContentStructureStructuredContentsPage_getContentStructureId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContent1, structuredContent2);
		}

		structuredContent1 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent1);

		structuredContent2 =
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				contentStructureId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				structuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				structuredContentResource.
					getContentStructureStructuredContentsPage(
						contentStructureId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetContentStructureStructuredContentsPage_addStructuredContent(
				Long contentStructureId, StructuredContent structuredContent)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentStructureStructuredContentsPage_getContentStructureId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentStructureStructuredContentsPage_getIrrelevantContentStructureId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetSiteStructuredContentsPage() throws Exception {
		Page<StructuredContent> page =
			structuredContentResource.getSiteStructuredContentsPage(
				testGetSiteStructuredContentsPage_getSiteId(), null,
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteStructuredContentsPage_getIrrelevantSiteId();

		if (irrelevantSiteId != null) {
			StructuredContent irrelevantStructuredContent =
				testGetSiteStructuredContentsPage_addStructuredContent(
					irrelevantSiteId, randomIrrelevantStructuredContent());

			page = structuredContentResource.getSiteStructuredContentsPage(
				irrelevantSiteId, null, null, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		page = structuredContentResource.getSiteStructuredContentsPage(
			siteId, null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			structuredContent1.getId());

		structuredContentResource.deleteStructuredContent(
			structuredContent2.getId());
	}

	@Test
	public void testGetSiteStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 = randomStructuredContent();

		structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null,
					getFilterString(entityField, "between", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetSiteStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null,
					getFilterString(entityField, "eq", structuredContent1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetSiteStructuredContentsPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, randomStructuredContent());

		Page<StructuredContent> page1 =
			structuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, null, Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			structuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		Page<StructuredContent> page3 =
			structuredContentResource.getSiteStructuredContentsPage(
				siteId, null, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			(List<StructuredContent>)page3.getItems());
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortDateTime()
		throws Exception {

		testGetSiteStructuredContentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortInteger()
		throws Exception {

		testGetSiteStructuredContentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContent2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSortString()
		throws Exception {

		testGetSiteStructuredContentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContent1, structuredContent2) -> {
				Class<?> clazz = structuredContent1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteStructuredContentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContent, StructuredContent, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContent1, structuredContent2);
		}

		structuredContent1 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent1);

		structuredContent2 =
			testGetSiteStructuredContentsPage_addStructuredContent(
				siteId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				structuredContentResource.getSiteStructuredContentsPage(
					siteId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetSiteStructuredContentsPage_addStructuredContent(
				Long siteId, StructuredContent structuredContent)
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			siteId, structuredContent);
	}

	protected Long testGetSiteStructuredContentsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteStructuredContentsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteStructuredContentsPage() throws Exception {
		Long siteId = testGetSiteStructuredContentsPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"structuredContents",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject structuredContentsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/structuredContents");

		Assert.assertEquals(0, structuredContentsJSONObject.get("totalCount"));

		StructuredContent structuredContent1 =
			testGraphQLStructuredContent_addStructuredContent();
		StructuredContent structuredContent2 =
			testGraphQLStructuredContent_addStructuredContent();

		structuredContentsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/structuredContents");

		Assert.assertEquals(2, structuredContentsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			Arrays.asList(
				StructuredContentSerDes.toDTOs(
					structuredContentsJSONObject.getString("items"))));
	}

	@Test
	public void testPostSiteStructuredContent() throws Exception {
		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			testPostSiteStructuredContent_addStructuredContent(
				randomStructuredContent);

		assertEquals(randomStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	protected StructuredContent
			testPostSiteStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGetSiteStructuredContentsPage_getSiteId(), structuredContent);
	}

	@Test
	public void testGraphQLPostSiteStructuredContent() throws Exception {
		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent(
				randomStructuredContent);

		Assert.assertTrue(equals(randomStructuredContent, structuredContent));
	}

	@Test
	public void testGetSiteStructuredContentByKey() throws Exception {
		StructuredContent postStructuredContent =
			testGetSiteStructuredContentByKey_addStructuredContent();

		StructuredContent getStructuredContent =
			structuredContentResource.getSiteStructuredContentByKey(
				postStructuredContent.getSiteId(),
				postStructuredContent.getKey());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testGetSiteStructuredContentByKey_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGraphQLGetSiteStructuredContentByKey() throws Exception {
		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent();

		Assert.assertTrue(
			equals(
				structuredContent,
				StructuredContentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"structuredContentByKey",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" +
												structuredContent.getSiteId() +
													"\"");
										put(
											"key",
											"\"" + structuredContent.getKey() +
												"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/structuredContentByKey"))));
	}

	@Test
	public void testGraphQLGetSiteStructuredContentByKeyNotFound()
		throws Exception {

		String irrelevantKey = "\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"structuredContentByKey",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put("key", irrelevantKey);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGetSiteStructuredContentByUuid() throws Exception {
		StructuredContent postStructuredContent =
			testGetSiteStructuredContentByUuid_addStructuredContent();

		StructuredContent getStructuredContent =
			structuredContentResource.getSiteStructuredContentByUuid(
				postStructuredContent.getSiteId(),
				postStructuredContent.getUuid());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testGetSiteStructuredContentByUuid_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGraphQLGetSiteStructuredContentByUuid() throws Exception {
		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent();

		Assert.assertTrue(
			equals(
				structuredContent,
				StructuredContentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"structuredContentByUuid",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" +
												structuredContent.getSiteId() +
													"\"");
										put(
											"uuid",
											"\"" + structuredContent.getUuid() +
												"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/structuredContentByUuid"))));
	}

	@Test
	public void testGraphQLGetSiteStructuredContentByUuidNotFound()
		throws Exception {

		String irrelevantUuid = "\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"structuredContentByUuid",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put("uuid", irrelevantUuid);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGetSiteStructuredContentPermissionsPage() throws Exception {
		Page<Permission> page =
			structuredContentResource.getSiteStructuredContentPermissionsPage(
				testGroup.getGroupId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected StructuredContent
			testGetSiteStructuredContentPermissionsPage_addStructuredContent()
		throws Exception {

		return testPostSiteStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	@Test
	public void testPutSiteStructuredContentPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testPutSiteStructuredContentPermission_addStructuredContent();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			structuredContentResource.
				putSiteStructuredContentPermissionHttpResponse(
					structuredContent.getSiteId(),
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
			structuredContentResource.
				putSiteStructuredContentPermissionHttpResponse(
					structuredContent.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected StructuredContent
			testPutSiteStructuredContentPermission_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPage()
		throws Exception {

		Page<StructuredContent> page =
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId(),
					null, RandomTestUtil.randomString(), null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();
		Long irrelevantStructuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getIrrelevantStructuredContentFolderId();

		if (irrelevantStructuredContentFolderId != null) {
			StructuredContent irrelevantStructuredContent =
				testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
					irrelevantStructuredContentFolderId,
					randomIrrelevantStructuredContent());

			page =
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						irrelevantStructuredContentFolderId, null, null, null,
						null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantStructuredContent),
				(List<StructuredContent>)page.getItems());
			assertValid(page);
		}

		StructuredContent structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		page =
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(structuredContent1, structuredContent2),
			(List<StructuredContent>)page.getItems());
		assertValid(page);

		structuredContentResource.deleteStructuredContent(
			structuredContent1.getId());

		structuredContentResource.deleteStructuredContent(
			structuredContent2.getId());
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 = randomStructuredContent();

		structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent1);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null, null,
						getFilterString(
							entityField, "between", structuredContent1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> page =
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null, null,
						getFilterString(entityField, "eq", structuredContent1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(structuredContent1),
				(List<StructuredContent>)page.getItems());
		}
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithPagination()
		throws Exception {

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		StructuredContent structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		StructuredContent structuredContent3 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, randomStructuredContent());

		Page<StructuredContent> page1 =
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, null, null,
					Pagination.of(1, 2), null);

		List<StructuredContent> structuredContents1 =
			(List<StructuredContent>)page1.getItems();

		Assert.assertEquals(
			structuredContents1.toString(), 2, structuredContents1.size());

		Page<StructuredContent> page2 =
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<StructuredContent> structuredContents2 =
			(List<StructuredContent>)page2.getItems();

		Assert.assertEquals(
			structuredContents2.toString(), 1, structuredContents2.size());

		Page<StructuredContent> page3 =
			structuredContentResource.
				getStructuredContentFolderStructuredContentsPage(
					structuredContentFolderId, null, null, null, null,
					Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				structuredContent1, structuredContent2, structuredContent3),
			(List<StructuredContent>)page3.getItems());
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithSortDateTime()
		throws Exception {

		testGetStructuredContentFolderStructuredContentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithSortInteger()
		throws Exception {

		testGetStructuredContentFolderStructuredContentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, structuredContent1, structuredContent2) -> {
				BeanUtils.setProperty(
					structuredContent1, entityField.getName(), 0);
				BeanUtils.setProperty(
					structuredContent2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetStructuredContentFolderStructuredContentsPageWithSortString()
		throws Exception {

		testGetStructuredContentFolderStructuredContentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, structuredContent1, structuredContent2) -> {
				Class<?> clazz = structuredContent1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						structuredContent1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						structuredContent2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetStructuredContentFolderStructuredContentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, StructuredContent, StructuredContent, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long structuredContentFolderId =
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId();

		StructuredContent structuredContent1 = randomStructuredContent();
		StructuredContent structuredContent2 = randomStructuredContent();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, structuredContent1, structuredContent2);
		}

		structuredContent1 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent1);

		structuredContent2 =
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				structuredContentFolderId, structuredContent2);

		for (EntityField entityField : entityFields) {
			Page<StructuredContent> ascPage =
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(structuredContent1, structuredContent2),
				(List<StructuredContent>)ascPage.getItems());

			Page<StructuredContent> descPage =
				structuredContentResource.
					getStructuredContentFolderStructuredContentsPage(
						structuredContentFolderId, null, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(structuredContent2, structuredContent1),
				(List<StructuredContent>)descPage.getItems());
		}
	}

	protected StructuredContent
			testGetStructuredContentFolderStructuredContentsPage_addStructuredContent(
				Long structuredContentFolderId,
				StructuredContent structuredContent)
		throws Exception {

		return structuredContentResource.
			postStructuredContentFolderStructuredContent(
				structuredContentFolderId, structuredContent);
	}

	protected Long
			testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetStructuredContentFolderStructuredContentsPage_getIrrelevantStructuredContentFolderId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostStructuredContentFolderStructuredContent()
		throws Exception {

		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			testPostStructuredContentFolderStructuredContent_addStructuredContent(
				randomStructuredContent);

		assertEquals(randomStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	protected StructuredContent
			testPostStructuredContentFolderStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		return structuredContentResource.
			postStructuredContentFolderStructuredContent(
				testGetStructuredContentFolderStructuredContentsPage_getStructuredContentFolderId(),
				structuredContent);
	}

	@Test
	public void testDeleteStructuredContent() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testDeleteStructuredContent_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.deleteStructuredContentHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.getStructuredContentHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.getStructuredContentHttpResponse(0L));
	}

	protected StructuredContent
			testDeleteStructuredContent_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGraphQLDeleteStructuredContent() throws Exception {
		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteStructuredContent",
						new HashMap<String, Object>() {
							{
								put(
									"structuredContentId",
									structuredContent.getId());
							}
						})),
				"JSONObject/data", "Object/deleteStructuredContent"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"structuredContent",
					new HashMap<String, Object>() {
						{
							put(
								"structuredContentId",
								structuredContent.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContent(
				postStructuredContent.getId());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent testGetStructuredContent_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGraphQLGetStructuredContent() throws Exception {
		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent();

		Assert.assertTrue(
			equals(
				structuredContent,
				StructuredContentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"structuredContent",
								new HashMap<String, Object>() {
									{
										put(
											"structuredContentId",
											structuredContent.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/structuredContent"))));
	}

	@Test
	public void testGraphQLGetStructuredContentNotFound() throws Exception {
		Long irrelevantStructuredContentId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"structuredContent",
						new HashMap<String, Object>() {
							{
								put(
									"structuredContentId",
									irrelevantStructuredContentId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testPatchStructuredContent_addStructuredContent();

		StructuredContent randomPatchStructuredContent =
			randomPatchStructuredContent();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent patchStructuredContent =
			structuredContentResource.patchStructuredContent(
				postStructuredContent.getId(), randomPatchStructuredContent);

		StructuredContent expectedPatchStructuredContent =
			postStructuredContent.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchStructuredContent, randomPatchStructuredContent);

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContent(
				patchStructuredContent.getId());

		assertEquals(expectedPatchStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent
			testPatchStructuredContent_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testPutStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			testPutStructuredContent_addStructuredContent();

		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent putStructuredContent =
			structuredContentResource.putStructuredContent(
				postStructuredContent.getId(), randomStructuredContent);

		assertEquals(randomStructuredContent, putStructuredContent);
		assertValid(putStructuredContent);

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContent(
				putStructuredContent.getId());

		assertEquals(randomStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	protected StructuredContent testPutStructuredContent_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testDeleteStructuredContentMyRating() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testDeleteStructuredContentMyRating_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.
				deleteStructuredContentMyRatingHttpResponse(
					structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.getStructuredContentMyRatingHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.getStructuredContentMyRatingHttpResponse(
				0L));
	}

	protected StructuredContent
			testDeleteStructuredContentMyRating_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetStructuredContentPermissionsPage() throws Exception {
		StructuredContent postStructuredContent =
			testGetStructuredContentPermissionsPage_addStructuredContent();

		Page<Permission> page =
			structuredContentResource.getStructuredContentPermissionsPage(
				postStructuredContent.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected StructuredContent
			testGetStructuredContentPermissionsPage_addStructuredContent()
		throws Exception {

		return testPostSiteStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	@Test
	public void testPutStructuredContentPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testPutStructuredContentPermission_addStructuredContent();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			structuredContentResource.
				putStructuredContentPermissionHttpResponse(
					structuredContent.getId(),
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
			structuredContentResource.
				putStructuredContentPermissionHttpResponse(
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

	protected StructuredContent
			testPutStructuredContentPermission_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testGetStructuredContentRenderedContentByDisplayPageDisplayPageKey()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetStructuredContentRenderedContentTemplate()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testPutStructuredContentSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testPutStructuredContentSubscribe_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.putStructuredContentSubscribeHttpResponse(
				structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.putStructuredContentSubscribeHttpResponse(
				0L));
	}

	protected StructuredContent
			testPutStructuredContentSubscribe_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Test
	public void testPutStructuredContentUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StructuredContent structuredContent =
			testPutStructuredContentUnsubscribe_addStructuredContent();

		assertHttpResponseStatusCode(
			204,
			structuredContentResource.
				putStructuredContentUnsubscribeHttpResponse(
					structuredContent.getId()));

		assertHttpResponseStatusCode(
			404,
			structuredContentResource.
				putStructuredContentUnsubscribeHttpResponse(0L));
	}

	protected StructuredContent
			testPutStructuredContentUnsubscribe_addStructuredContent()
		throws Exception {

		return structuredContentResource.postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Test
	public void testGetStructuredContentMyRating() throws Exception {
		StructuredContent postStructuredContent =
			testGetStructuredContent_addStructuredContent();

		Rating postRating = testGetStructuredContentMyRating_addRating(
			postStructuredContent.getId(), randomRating());

		Rating getRating =
			structuredContentResource.getStructuredContentMyRating(
				postStructuredContent.getId());

		assertEquals(postRating, getRating);
		assertValid(getRating);
	}

	protected Rating testGetStructuredContentMyRating_addRating(
			long structuredContentId, Rating rating)
		throws Exception {

		return structuredContentResource.postStructuredContentMyRating(
			structuredContentId, rating);
	}

	@Test
	public void testPostStructuredContentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutStructuredContentMyRating() throws Exception {
		StructuredContent postStructuredContent =
			testPutStructuredContent_addStructuredContent();

		testPutStructuredContentMyRating_addRating(
			postStructuredContent.getId(), randomRating());

		Rating randomRating = randomRating();

		Rating putRating =
			structuredContentResource.putStructuredContentMyRating(
				postStructuredContent.getId(), randomRating);

		assertEquals(randomRating, putRating);
		assertValid(putRating);
	}

	protected Rating testPutStructuredContentMyRating_addRating(
			long structuredContentId, Rating rating)
		throws Exception {

		return structuredContentResource.postStructuredContentMyRating(
			structuredContentId, rating);
	}

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

	protected StructuredContent
			testGraphQLStructuredContent_addStructuredContent()
		throws Exception {

		return testGraphQLStructuredContent_addStructuredContent(
			randomStructuredContent());
	}

	protected StructuredContent
			testGraphQLStructuredContent_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		JSONDeserializer<StructuredContent> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (Field field : getDeclaredFields(StructuredContent.class)) {
			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(structuredContent));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteStructuredContent",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("structuredContent", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data", "JSONObject/createSiteStructuredContent"),
			StructuredContent.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		Assert.assertTrue(
			structuredContent1 + " does not equal " + structuredContent2,
			equals(structuredContent1, structuredContent2));
	}

	protected void assertEquals(
		List<StructuredContent> structuredContents1,
		List<StructuredContent> structuredContents2) {

		Assert.assertEquals(
			structuredContents1.size(), structuredContents2.size());

		for (int i = 0; i < structuredContents1.size(); i++) {
			StructuredContent structuredContent1 = structuredContents1.get(i);
			StructuredContent structuredContent2 = structuredContents2.get(i);

			assertEquals(structuredContent1, structuredContent2);
		}
	}

	protected void assertEquals(Rating rating1, Rating rating2) {
		Assert.assertTrue(
			rating1 + " does not equal " + rating2, equals(rating1, rating2));
	}

	protected void assertEqualsIgnoringOrder(
		List<StructuredContent> structuredContents1,
		List<StructuredContent> structuredContents2) {

		Assert.assertEquals(
			structuredContents1.size(), structuredContents2.size());

		for (StructuredContent structuredContent1 : structuredContents1) {
			boolean contains = false;

			for (StructuredContent structuredContent2 : structuredContents2) {
				if (equals(structuredContent1, structuredContent2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				structuredContents2 + " does not contain " + structuredContent1,
				contains);
		}
	}

	protected void assertValid(StructuredContent structuredContent)
		throws Exception {

		boolean valid = true;

		if (structuredContent.getDateCreated() == null) {
			valid = false;
		}

		if (structuredContent.getDateModified() == null) {
			valid = false;
		}

		if (structuredContent.getId() == null) {
			valid = false;
		}

		Group group = testDepotEntry.getGroup();

		if (!Objects.equals(
				structuredContent.getAssetLibraryKey(), group.getGroupKey()) &&
			!Objects.equals(
				structuredContent.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (structuredContent.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (structuredContent.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetLibraryKey", additionalAssertFieldName)) {
				if (structuredContent.getAssetLibraryKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (structuredContent.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentFields", additionalAssertFieldName)) {
				if (structuredContent.getContentFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureId", additionalAssertFieldName)) {

				if (structuredContent.getContentStructureId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (structuredContent.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (structuredContent.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (structuredContent.getDatePublished() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (structuredContent.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (structuredContent.getDescription_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (structuredContent.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"friendlyUrlPath_i18n", additionalAssertFieldName)) {

				if (structuredContent.getFriendlyUrlPath_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (structuredContent.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (structuredContent.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (structuredContent.getNumberOfComments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (structuredContent.getRelatedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("renderedContents", additionalAssertFieldName)) {
				if (structuredContent.getRenderedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (structuredContent.getSubscribed() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (structuredContent.getTaxonomyCategoryBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (structuredContent.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (structuredContent.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (structuredContent.getTitle_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (structuredContent.getUuid() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (structuredContent.getViewableBy() == null) {
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

	protected void assertValid(Page<StructuredContent> page) {
		boolean valid = false;

		java.util.Collection<StructuredContent> structuredContents =
			page.getItems();

		int size = structuredContents.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Rating rating) {
		boolean valid = true;

		if (rating.getDateCreated() == null) {
			valid = false;
		}

		if (rating.getDateModified() == null) {
			valid = false;
		}

		if (rating.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (rating.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (rating.getBestRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (rating.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (rating.getRatingValue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (rating.getWorstRating() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected String[] getAdditionalRatingAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.StructuredContent.
						class)) {

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
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		if (structuredContent1 == structuredContent2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)structuredContent1.getActions(),
						(Map)structuredContent2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getAggregateRating(),
						structuredContent2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContent1.getAvailableLanguages(),
						structuredContent2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getContentFields(),
						structuredContent2.getContentFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContent1.getContentStructureId(),
						structuredContent2.getContentStructureId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getCreator(),
						structuredContent2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getCustomFields(),
						structuredContent2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getDateCreated(),
						structuredContent2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getDateModified(),
						structuredContent2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getDatePublished(),
						structuredContent2.getDatePublished())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getDescription(),
						structuredContent2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)structuredContent1.getDescription_i18n(),
						(Map)structuredContent2.getDescription_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getFriendlyUrlPath(),
						structuredContent2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"friendlyUrlPath_i18n", additionalAssertFieldName)) {

				if (!equals(
						(Map)structuredContent1.getFriendlyUrlPath_i18n(),
						(Map)structuredContent2.getFriendlyUrlPath_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getId(),
						structuredContent2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getKey(),
						structuredContent2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getKeywords(),
						structuredContent2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getNumberOfComments(),
						structuredContent2.getNumberOfComments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getRelatedContents(),
						structuredContent2.getRelatedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("renderedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getRenderedContents(),
						structuredContent2.getRenderedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getSubscribed(),
						structuredContent2.getSubscribed())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContent1.getTaxonomyCategoryBriefs(),
						structuredContent2.getTaxonomyCategoryBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						structuredContent1.getTaxonomyCategoryIds(),
						structuredContent2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getTitle(),
						structuredContent2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)structuredContent1.getTitle_i18n(),
						(Map)structuredContent2.getTitle_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getUuid(),
						structuredContent2.getUuid())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						structuredContent1.getViewableBy(),
						structuredContent2.getViewableBy())) {

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

	protected boolean equals(Rating rating1, Rating rating2) {
		if (rating1 == rating2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getActions(), rating2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getBestRating(), rating2.getBestRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getCreator(), rating2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateCreated(), rating2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateModified(), rating2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(rating1.getId(), rating2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getRatingValue(), rating2.getRatingValue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getWorstRating(), rating2.getWorstRating())) {

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

		if (!(_structuredContentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_structuredContentResource;

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
		StructuredContent structuredContent) {

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

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("assetLibraryKey")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getAssetLibraryKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentStructureId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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
							structuredContent.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContent.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(structuredContent.getDateCreated()));
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
							structuredContent.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContent.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(structuredContent.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("datePublished")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContent.getDatePublished(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							structuredContent.getDatePublished(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(structuredContent.getDatePublished()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfComments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("relatedContents")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("renderedContents")) {
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

		if (entityFieldName.equals("taxonomyCategoryBriefs")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("uuid")) {
			sb.append("'");
			sb.append(String.valueOf(structuredContent.getUuid()));
			sb.append("'");

			return sb.toString();
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

	protected StructuredContent randomStructuredContent() throws Exception {
		return new StructuredContent() {
			{
				assetLibraryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				contentStructureId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				friendlyUrlPath = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				numberOfComments = RandomTestUtil.randomInt();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected StructuredContent randomIrrelevantStructuredContent()
		throws Exception {

		StructuredContent randomIrrelevantStructuredContent =
			randomStructuredContent();

		randomIrrelevantStructuredContent.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantStructuredContent;
	}

	protected StructuredContent randomPatchStructuredContent()
		throws Exception {

		return randomStructuredContent();
	}

	protected Rating randomRating() throws Exception {
		return new Rating() {
			{
				bestRating = RandomTestUtil.randomDouble();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				ratingValue = RandomTestUtil.randomDouble();
				worstRating = RandomTestUtil.randomDouble();
			}
		};
	}

	protected StructuredContentResource structuredContentResource;
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
		BaseStructuredContentResourceTestCase.class);

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
		com.liferay.headless.delivery.resource.v1_0.StructuredContentResource
			_structuredContentResource;

}