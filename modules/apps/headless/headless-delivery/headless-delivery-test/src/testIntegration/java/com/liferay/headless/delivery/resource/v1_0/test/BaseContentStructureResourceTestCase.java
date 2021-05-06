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
import com.liferay.headless.delivery.client.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.ContentStructureResource;
import com.liferay.headless.delivery.client.serdes.v1_0.ContentStructureSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
public abstract class BaseContentStructureResourceTestCase {

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

		_contentStructureResource.setContextCompany(testCompany);

		ContentStructureResource.Builder builder =
			ContentStructureResource.builder();

		contentStructureResource = builder.authentication(
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

		ContentStructure contentStructure1 = randomContentStructure();

		String json = objectMapper.writeValueAsString(contentStructure1);

		ContentStructure contentStructure2 = ContentStructureSerDes.toDTO(json);

		Assert.assertTrue(equals(contentStructure1, contentStructure2));
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

		ContentStructure contentStructure = randomContentStructure();

		String json1 = objectMapper.writeValueAsString(contentStructure);
		String json2 = ContentStructureSerDes.toJSON(contentStructure);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ContentStructure contentStructure = randomContentStructure();

		contentStructure.setAssetLibraryKey(regex);
		contentStructure.setDescription(regex);
		contentStructure.setName(regex);

		String json = ContentStructureSerDes.toJSON(contentStructure);

		Assert.assertFalse(json.contains(regex));

		contentStructure = ContentStructureSerDes.toDTO(json);

		Assert.assertEquals(regex, contentStructure.getAssetLibraryKey());
		Assert.assertEquals(regex, contentStructure.getDescription());
		Assert.assertEquals(regex, contentStructure.getName());
	}

	@Test
	public void testGetAssetLibraryContentStructuresPage() throws Exception {
		Page<ContentStructure> page =
			contentStructureResource.getAssetLibraryContentStructuresPage(
				testGetAssetLibraryContentStructuresPage_getAssetLibraryId(),
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long assetLibraryId =
			testGetAssetLibraryContentStructuresPage_getAssetLibraryId();
		Long irrelevantAssetLibraryId =
			testGetAssetLibraryContentStructuresPage_getIrrelevantAssetLibraryId();

		if (irrelevantAssetLibraryId != null) {
			ContentStructure irrelevantContentStructure =
				testGetAssetLibraryContentStructuresPage_addContentStructure(
					irrelevantAssetLibraryId,
					randomIrrelevantContentStructure());

			page =
				contentStructureResource.getAssetLibraryContentStructuresPage(
					irrelevantAssetLibraryId, null, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentStructure),
				(List<ContentStructure>)page.getItems());
			assertValid(page);
		}

		ContentStructure contentStructure1 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, randomContentStructure());

		ContentStructure contentStructure2 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, randomContentStructure());

		page = contentStructureResource.getAssetLibraryContentStructuresPage(
			assetLibraryId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentStructure1, contentStructure2),
			(List<ContentStructure>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAssetLibraryContentStructuresPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryContentStructuresPage_getAssetLibraryId();

		ContentStructure contentStructure1 = randomContentStructure();

		contentStructure1 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, contentStructure1);

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> page =
				contentStructureResource.getAssetLibraryContentStructuresPage(
					assetLibraryId, null, null,
					getFilterString(entityField, "between", contentStructure1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentStructure1),
				(List<ContentStructure>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryContentStructuresPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryContentStructuresPage_getAssetLibraryId();

		ContentStructure contentStructure1 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, randomContentStructure());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentStructure contentStructure2 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, randomContentStructure());

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> page =
				contentStructureResource.getAssetLibraryContentStructuresPage(
					assetLibraryId, null, null,
					getFilterString(entityField, "eq", contentStructure1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentStructure1),
				(List<ContentStructure>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryContentStructuresPageWithPagination()
		throws Exception {

		Long assetLibraryId =
			testGetAssetLibraryContentStructuresPage_getAssetLibraryId();

		ContentStructure contentStructure1 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, randomContentStructure());

		ContentStructure contentStructure2 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, randomContentStructure());

		ContentStructure contentStructure3 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, randomContentStructure());

		Page<ContentStructure> page1 =
			contentStructureResource.getAssetLibraryContentStructuresPage(
				assetLibraryId, null, null, null, Pagination.of(1, 2), null);

		List<ContentStructure> contentStructures1 =
			(List<ContentStructure>)page1.getItems();

		Assert.assertEquals(
			contentStructures1.toString(), 2, contentStructures1.size());

		Page<ContentStructure> page2 =
			contentStructureResource.getAssetLibraryContentStructuresPage(
				assetLibraryId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentStructure> contentStructures2 =
			(List<ContentStructure>)page2.getItems();

		Assert.assertEquals(
			contentStructures2.toString(), 1, contentStructures2.size());

		Page<ContentStructure> page3 =
			contentStructureResource.getAssetLibraryContentStructuresPage(
				assetLibraryId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentStructure1, contentStructure2, contentStructure3),
			(List<ContentStructure>)page3.getItems());
	}

	@Test
	public void testGetAssetLibraryContentStructuresPageWithSortDateTime()
		throws Exception {

		testGetAssetLibraryContentStructuresPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, contentStructure1, contentStructure2) -> {
				BeanUtils.setProperty(
					contentStructure1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAssetLibraryContentStructuresPageWithSortInteger()
		throws Exception {

		testGetAssetLibraryContentStructuresPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, contentStructure1, contentStructure2) -> {
				BeanUtils.setProperty(
					contentStructure1, entityField.getName(), 0);
				BeanUtils.setProperty(
					contentStructure2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAssetLibraryContentStructuresPageWithSortString()
		throws Exception {

		testGetAssetLibraryContentStructuresPageWithSort(
			EntityField.Type.STRING,
			(entityField, contentStructure1, contentStructure2) -> {
				Class<?> clazz = contentStructure1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						contentStructure1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						contentStructure2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						contentStructure1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						contentStructure2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						contentStructure1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						contentStructure2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAssetLibraryContentStructuresPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, ContentStructure, ContentStructure, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryContentStructuresPage_getAssetLibraryId();

		ContentStructure contentStructure1 = randomContentStructure();
		ContentStructure contentStructure2 = randomContentStructure();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, contentStructure1, contentStructure2);
		}

		contentStructure1 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, contentStructure1);

		contentStructure2 =
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				assetLibraryId, contentStructure2);

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> ascPage =
				contentStructureResource.getAssetLibraryContentStructuresPage(
					assetLibraryId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(contentStructure1, contentStructure2),
				(List<ContentStructure>)ascPage.getItems());

			Page<ContentStructure> descPage =
				contentStructureResource.getAssetLibraryContentStructuresPage(
					assetLibraryId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(contentStructure2, contentStructure1),
				(List<ContentStructure>)descPage.getItems());
		}
	}

	protected ContentStructure
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				Long assetLibraryId, ContentStructure contentStructure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAssetLibraryContentStructuresPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	protected Long
			testGetAssetLibraryContentStructuresPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetAssetLibraryContentStructurePermissionsPage()
		throws Exception {

		Page<Permission> page =
			contentStructureResource.
				getAssetLibraryContentStructurePermissionsPage(
					testDepotEntry.getDepotEntryId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected ContentStructure
			testGetAssetLibraryContentStructurePermissionsPage_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutAssetLibraryContentStructurePermission()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentStructure contentStructure =
			testPutAssetLibraryContentStructurePermission_addContentStructure();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			contentStructureResource.
				putAssetLibraryContentStructurePermissionHttpResponse(
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
			contentStructureResource.
				putAssetLibraryContentStructurePermissionHttpResponse(
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

	protected ContentStructure
			testPutAssetLibraryContentStructurePermission_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetContentStructure() throws Exception {
		ContentStructure postContentStructure =
			testGetContentStructure_addContentStructure();

		ContentStructure getContentStructure =
			contentStructureResource.getContentStructure(
				postContentStructure.getId());

		assertEquals(postContentStructure, getContentStructure);
		assertValid(getContentStructure);
	}

	protected ContentStructure testGetContentStructure_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetContentStructure() throws Exception {
		ContentStructure contentStructure =
			testGraphQLContentStructure_addContentStructure();

		Assert.assertTrue(
			equals(
				contentStructure,
				ContentStructureSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"contentStructure",
								new HashMap<String, Object>() {
									{
										put(
											"contentStructureId",
											contentStructure.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/contentStructure"))));
	}

	@Test
	public void testGraphQLGetContentStructureNotFound() throws Exception {
		Long irrelevantContentStructureId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"contentStructure",
						new HashMap<String, Object>() {
							{
								put(
									"contentStructureId",
									irrelevantContentStructureId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGetContentStructurePermissionsPage() throws Exception {
		ContentStructure postContentStructure =
			testGetContentStructurePermissionsPage_addContentStructure();

		Page<Permission> page =
			contentStructureResource.getContentStructurePermissionsPage(
				postContentStructure.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected ContentStructure
			testGetContentStructurePermissionsPage_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutContentStructurePermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentStructure contentStructure =
			testPutContentStructurePermission_addContentStructure();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			contentStructureResource.putContentStructurePermissionHttpResponse(
				contentStructure.getId(),
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
			contentStructureResource.putContentStructurePermissionHttpResponse(
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

	protected ContentStructure
			testPutContentStructurePermission_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteContentStructuresPage() throws Exception {
		Page<ContentStructure> page =
			contentStructureResource.getSiteContentStructuresPage(
				testGetSiteContentStructuresPage_getSiteId(),
				RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteContentStructuresPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteContentStructuresPage_getIrrelevantSiteId();

		if (irrelevantSiteId != null) {
			ContentStructure irrelevantContentStructure =
				testGetSiteContentStructuresPage_addContentStructure(
					irrelevantSiteId, randomIrrelevantContentStructure());

			page = contentStructureResource.getSiteContentStructuresPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentStructure),
				(List<ContentStructure>)page.getItems());
			assertValid(page);
		}

		ContentStructure contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		ContentStructure contentStructure2 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		page = contentStructureResource.getSiteContentStructuresPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentStructure1, contentStructure2),
			(List<ContentStructure>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteContentStructuresPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentStructuresPage_getSiteId();

		ContentStructure contentStructure1 = randomContentStructure();

		contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, contentStructure1);

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> page =
				contentStructureResource.getSiteContentStructuresPage(
					siteId, null, null,
					getFilterString(entityField, "between", contentStructure1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentStructure1),
				(List<ContentStructure>)page.getItems());
		}
	}

	@Test
	public void testGetSiteContentStructuresPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentStructuresPage_getSiteId();

		ContentStructure contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentStructure contentStructure2 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> page =
				contentStructureResource.getSiteContentStructuresPage(
					siteId, null, null,
					getFilterString(entityField, "eq", contentStructure1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentStructure1),
				(List<ContentStructure>)page.getItems());
		}
	}

	@Test
	public void testGetSiteContentStructuresPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteContentStructuresPage_getSiteId();

		ContentStructure contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		ContentStructure contentStructure2 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		ContentStructure contentStructure3 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		Page<ContentStructure> page1 =
			contentStructureResource.getSiteContentStructuresPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<ContentStructure> contentStructures1 =
			(List<ContentStructure>)page1.getItems();

		Assert.assertEquals(
			contentStructures1.toString(), 2, contentStructures1.size());

		Page<ContentStructure> page2 =
			contentStructureResource.getSiteContentStructuresPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentStructure> contentStructures2 =
			(List<ContentStructure>)page2.getItems();

		Assert.assertEquals(
			contentStructures2.toString(), 1, contentStructures2.size());

		Page<ContentStructure> page3 =
			contentStructureResource.getSiteContentStructuresPage(
				siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentStructure1, contentStructure2, contentStructure3),
			(List<ContentStructure>)page3.getItems());
	}

	@Test
	public void testGetSiteContentStructuresPageWithSortDateTime()
		throws Exception {

		testGetSiteContentStructuresPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, contentStructure1, contentStructure2) -> {
				BeanUtils.setProperty(
					contentStructure1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteContentStructuresPageWithSortInteger()
		throws Exception {

		testGetSiteContentStructuresPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, contentStructure1, contentStructure2) -> {
				BeanUtils.setProperty(
					contentStructure1, entityField.getName(), 0);
				BeanUtils.setProperty(
					contentStructure2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteContentStructuresPageWithSortString()
		throws Exception {

		testGetSiteContentStructuresPageWithSort(
			EntityField.Type.STRING,
			(entityField, contentStructure1, contentStructure2) -> {
				Class<?> clazz = contentStructure1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						contentStructure1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						contentStructure2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						contentStructure1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						contentStructure2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						contentStructure1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						contentStructure2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteContentStructuresPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, ContentStructure, ContentStructure, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentStructuresPage_getSiteId();

		ContentStructure contentStructure1 = randomContentStructure();
		ContentStructure contentStructure2 = randomContentStructure();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, contentStructure1, contentStructure2);
		}

		contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, contentStructure1);

		contentStructure2 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, contentStructure2);

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> ascPage =
				contentStructureResource.getSiteContentStructuresPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(contentStructure1, contentStructure2),
				(List<ContentStructure>)ascPage.getItems());

			Page<ContentStructure> descPage =
				contentStructureResource.getSiteContentStructuresPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(contentStructure2, contentStructure1),
				(List<ContentStructure>)descPage.getItems());
		}
	}

	protected ContentStructure
			testGetSiteContentStructuresPage_addContentStructure(
				Long siteId, ContentStructure contentStructure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteContentStructuresPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteContentStructuresPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteContentStructuresPage() throws Exception {
		Long siteId = testGetSiteContentStructuresPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"contentStructures",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject contentStructuresJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/contentStructures");

		Assert.assertEquals(0, contentStructuresJSONObject.get("totalCount"));

		ContentStructure contentStructure1 =
			testGraphQLContentStructure_addContentStructure();
		ContentStructure contentStructure2 =
			testGraphQLContentStructure_addContentStructure();

		contentStructuresJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/contentStructures");

		Assert.assertEquals(2, contentStructuresJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(contentStructure1, contentStructure2),
			Arrays.asList(
				ContentStructureSerDes.toDTOs(
					contentStructuresJSONObject.getString("items"))));
	}

	@Test
	public void testGetSiteContentStructurePermissionsPage() throws Exception {
		Page<Permission> page =
			contentStructureResource.getSiteContentStructurePermissionsPage(
				testGroup.getGroupId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected ContentStructure
			testGetSiteContentStructurePermissionsPage_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutSiteContentStructurePermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentStructure contentStructure =
			testPutSiteContentStructurePermission_addContentStructure();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			contentStructureResource.
				putSiteContentStructurePermissionHttpResponse(
					contentStructure.getSiteId(),
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
			contentStructureResource.
				putSiteContentStructurePermissionHttpResponse(
					contentStructure.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected ContentStructure
			testPutSiteContentStructurePermission_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected ContentStructure testGraphQLContentStructure_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ContentStructure contentStructure1,
		ContentStructure contentStructure2) {

		Assert.assertTrue(
			contentStructure1 + " does not equal " + contentStructure2,
			equals(contentStructure1, contentStructure2));
	}

	protected void assertEquals(
		List<ContentStructure> contentStructures1,
		List<ContentStructure> contentStructures2) {

		Assert.assertEquals(
			contentStructures1.size(), contentStructures2.size());

		for (int i = 0; i < contentStructures1.size(); i++) {
			ContentStructure contentStructure1 = contentStructures1.get(i);
			ContentStructure contentStructure2 = contentStructures2.get(i);

			assertEquals(contentStructure1, contentStructure2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ContentStructure> contentStructures1,
		List<ContentStructure> contentStructures2) {

		Assert.assertEquals(
			contentStructures1.size(), contentStructures2.size());

		for (ContentStructure contentStructure1 : contentStructures1) {
			boolean contains = false;

			for (ContentStructure contentStructure2 : contentStructures2) {
				if (equals(contentStructure1, contentStructure2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				contentStructures2 + " does not contain " + contentStructure1,
				contains);
		}
	}

	protected void assertValid(ContentStructure contentStructure)
		throws Exception {

		boolean valid = true;

		if (contentStructure.getDateCreated() == null) {
			valid = false;
		}

		if (contentStructure.getDateModified() == null) {
			valid = false;
		}

		if (contentStructure.getId() == null) {
			valid = false;
		}

		Group group = testDepotEntry.getGroup();

		if (!Objects.equals(
				contentStructure.getAssetLibraryKey(), group.getGroupKey()) &&
			!Objects.equals(
				contentStructure.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assetLibraryKey", additionalAssertFieldName)) {
				if (contentStructure.getAssetLibraryKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (contentStructure.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureFields", additionalAssertFieldName)) {

				if (contentStructure.getContentStructureFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (contentStructure.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (contentStructure.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (contentStructure.getDescription_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (contentStructure.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (contentStructure.getName_i18n() == null) {
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

	protected void assertValid(Page<ContentStructure> page) {
		boolean valid = false;

		java.util.Collection<ContentStructure> contentStructures =
			page.getItems();

		int size = contentStructures.size();

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
					com.liferay.headless.delivery.dto.v1_0.ContentStructure.
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
		ContentStructure contentStructure1,
		ContentStructure contentStructure2) {

		if (contentStructure1 == contentStructure2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentStructure1.getAvailableLanguages(),
						contentStructure2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureFields", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentStructure1.getContentStructureFields(),
						contentStructure2.getContentStructureFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getCreator(),
						contentStructure2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getDateCreated(),
						contentStructure2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getDateModified(),
						contentStructure2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getDescription(),
						contentStructure2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)contentStructure1.getDescription_i18n(),
						(Map)contentStructure2.getDescription_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getId(), contentStructure2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getName(),
						contentStructure2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)contentStructure1.getName_i18n(),
						(Map)contentStructure2.getName_i18n())) {

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

		if (!(_contentStructureResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_contentStructureResource;

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
		ContentStructure contentStructure) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("assetLibraryKey")) {
			sb.append("'");
			sb.append(String.valueOf(contentStructure.getAssetLibraryKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentStructureFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
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
							contentStructure.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							contentStructure.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(contentStructure.getDateCreated()));
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
							contentStructure.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							contentStructure.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(contentStructure.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(contentStructure.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(contentStructure.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name_i18n")) {
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

	protected ContentStructure randomContentStructure() throws Exception {
		return new ContentStructure() {
			{
				assetLibraryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected ContentStructure randomIrrelevantContentStructure()
		throws Exception {

		ContentStructure randomIrrelevantContentStructure =
			randomContentStructure();

		randomIrrelevantContentStructure.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantContentStructure;
	}

	protected ContentStructure randomPatchContentStructure() throws Exception {
		return randomContentStructure();
	}

	protected ContentStructureResource contentStructureResource;
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
		BaseContentStructureResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.ContentStructureResource
		_contentStructureResource;

}