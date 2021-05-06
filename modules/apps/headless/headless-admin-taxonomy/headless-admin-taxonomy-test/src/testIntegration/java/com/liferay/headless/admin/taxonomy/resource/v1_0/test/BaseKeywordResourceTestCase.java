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

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.client.http.HttpInvoker;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.headless.admin.taxonomy.client.permission.Permission;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.KeywordResource;
import com.liferay.headless.admin.taxonomy.client.serdes.v1_0.KeywordSerDes;
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
public abstract class BaseKeywordResourceTestCase {

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

		_keywordResource.setContextCompany(testCompany);

		KeywordResource.Builder builder = KeywordResource.builder();

		keywordResource = builder.authentication(
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

		Keyword keyword1 = randomKeyword();

		String json = objectMapper.writeValueAsString(keyword1);

		Keyword keyword2 = KeywordSerDes.toDTO(json);

		Assert.assertTrue(equals(keyword1, keyword2));
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

		Keyword keyword = randomKeyword();

		String json1 = objectMapper.writeValueAsString(keyword);
		String json2 = KeywordSerDes.toJSON(keyword);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Keyword keyword = randomKeyword();

		keyword.setAssetLibraryKey(regex);
		keyword.setName(regex);

		String json = KeywordSerDes.toJSON(keyword);

		Assert.assertFalse(json.contains(regex));

		keyword = KeywordSerDes.toDTO(json);

		Assert.assertEquals(regex, keyword.getAssetLibraryKey());
		Assert.assertEquals(regex, keyword.getName());
	}

	@Test
	public void testGetAssetLibraryKeywordsPage() throws Exception {
		Page<Keyword> page = keywordResource.getAssetLibraryKeywordsPage(
			testGetAssetLibraryKeywordsPage_getAssetLibraryId(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long assetLibraryId =
			testGetAssetLibraryKeywordsPage_getAssetLibraryId();
		Long irrelevantAssetLibraryId =
			testGetAssetLibraryKeywordsPage_getIrrelevantAssetLibraryId();

		if (irrelevantAssetLibraryId != null) {
			Keyword irrelevantKeyword =
				testGetAssetLibraryKeywordsPage_addKeyword(
					irrelevantAssetLibraryId, randomIrrelevantKeyword());

			page = keywordResource.getAssetLibraryKeywordsPage(
				irrelevantAssetLibraryId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKeyword),
				(List<Keyword>)page.getItems());
			assertValid(page);
		}

		Keyword keyword1 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, randomKeyword());

		Keyword keyword2 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, randomKeyword());

		page = keywordResource.getAssetLibraryKeywordsPage(
			assetLibraryId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2), (List<Keyword>)page.getItems());
		assertValid(page);

		keywordResource.deleteKeyword(keyword1.getId());

		keywordResource.deleteKeyword(keyword2.getId());
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryKeywordsPage_getAssetLibraryId();

		Keyword keyword1 = randomKeyword();

		keyword1 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, keyword1);

		for (EntityField entityField : entityFields) {
			Page<Keyword> page = keywordResource.getAssetLibraryKeywordsPage(
				assetLibraryId, null,
				getFilterString(entityField, "between", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryKeywordsPage_getAssetLibraryId();

		Keyword keyword1 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, randomKeyword());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword2 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, randomKeyword());

		for (EntityField entityField : entityFields) {
			Page<Keyword> page = keywordResource.getAssetLibraryKeywordsPage(
				assetLibraryId, null,
				getFilterString(entityField, "eq", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithPagination()
		throws Exception {

		Long assetLibraryId =
			testGetAssetLibraryKeywordsPage_getAssetLibraryId();

		Keyword keyword1 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, randomKeyword());

		Keyword keyword2 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, randomKeyword());

		Keyword keyword3 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, randomKeyword());

		Page<Keyword> page1 = keywordResource.getAssetLibraryKeywordsPage(
			assetLibraryId, null, null, Pagination.of(1, 2), null);

		List<Keyword> keywords1 = (List<Keyword>)page1.getItems();

		Assert.assertEquals(keywords1.toString(), 2, keywords1.size());

		Page<Keyword> page2 = keywordResource.getAssetLibraryKeywordsPage(
			assetLibraryId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Keyword> keywords2 = (List<Keyword>)page2.getItems();

		Assert.assertEquals(keywords2.toString(), 1, keywords2.size());

		Page<Keyword> page3 = keywordResource.getAssetLibraryKeywordsPage(
			assetLibraryId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2, keyword3),
			(List<Keyword>)page3.getItems());
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithSortDateTime()
		throws Exception {

		testGetAssetLibraryKeywordsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, keyword1, keyword2) -> {
				BeanUtils.setProperty(
					keyword1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithSortInteger()
		throws Exception {

		testGetAssetLibraryKeywordsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, keyword1, keyword2) -> {
				BeanUtils.setProperty(keyword1, entityField.getName(), 0);
				BeanUtils.setProperty(keyword2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithSortString()
		throws Exception {

		testGetAssetLibraryKeywordsPageWithSort(
			EntityField.Type.STRING,
			(entityField, keyword1, keyword2) -> {
				Class<?> clazz = keyword1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						keyword1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						keyword2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						keyword1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						keyword2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						keyword1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						keyword2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAssetLibraryKeywordsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Keyword, Keyword, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryKeywordsPage_getAssetLibraryId();

		Keyword keyword1 = randomKeyword();
		Keyword keyword2 = randomKeyword();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, keyword1, keyword2);
		}

		keyword1 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, keyword1);

		keyword2 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, keyword2);

		for (EntityField entityField : entityFields) {
			Page<Keyword> ascPage = keywordResource.getAssetLibraryKeywordsPage(
				assetLibraryId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(keyword1, keyword2),
				(List<Keyword>)ascPage.getItems());

			Page<Keyword> descPage =
				keywordResource.getAssetLibraryKeywordsPage(
					assetLibraryId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(keyword2, keyword1),
				(List<Keyword>)descPage.getItems());
		}
	}

	protected Keyword testGetAssetLibraryKeywordsPage_addKeyword(
			Long assetLibraryId, Keyword keyword)
		throws Exception {

		return keywordResource.postAssetLibraryKeyword(assetLibraryId, keyword);
	}

	protected Long testGetAssetLibraryKeywordsPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	protected Long testGetAssetLibraryKeywordsPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAssetLibraryKeyword() throws Exception {
		Keyword randomKeyword = randomKeyword();

		Keyword postKeyword = testPostAssetLibraryKeyword_addKeyword(
			randomKeyword);

		assertEquals(randomKeyword, postKeyword);
		assertValid(postKeyword);
	}

	protected Keyword testPostAssetLibraryKeyword_addKeyword(Keyword keyword)
		throws Exception {

		return keywordResource.postAssetLibraryKeyword(
			testGetAssetLibraryKeywordsPage_getAssetLibraryId(), keyword);
	}

	@Test
	public void testGetAssetLibraryKeywordPermissionsPage() throws Exception {
		Page<Permission> page =
			keywordResource.getAssetLibraryKeywordPermissionsPage(
				testDepotEntry.getDepotEntryId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected Keyword testGetAssetLibraryKeywordPermissionsPage_addKeyword()
		throws Exception {

		return testPostAssetLibraryKeyword_addKeyword(randomKeyword());
	}

	@Test
	public void testPutAssetLibraryKeywordPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword = testPutAssetLibraryKeywordPermission_addKeyword();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			keywordResource.putAssetLibraryKeywordPermissionHttpResponse(
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
			keywordResource.putAssetLibraryKeywordPermissionHttpResponse(
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

	protected Keyword testPutAssetLibraryKeywordPermission_addKeyword()
		throws Exception {

		return keywordResource.postAssetLibraryKeyword(
			testDepotEntry.getDepotEntryId(), randomKeyword());
	}

	@Test
	public void testGetKeywordsRankedPage() throws Exception {
		Page<Keyword> page = keywordResource.getKeywordsRankedPage(
			null, RandomTestUtil.randomString(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Keyword keyword1 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		Keyword keyword2 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		page = keywordResource.getKeywordsRankedPage(
			null, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2), (List<Keyword>)page.getItems());
		assertValid(page);

		keywordResource.deleteKeyword(keyword1.getId());

		keywordResource.deleteKeyword(keyword2.getId());
	}

	@Test
	public void testGetKeywordsRankedPageWithPagination() throws Exception {
		Keyword keyword1 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		Keyword keyword2 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		Keyword keyword3 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		Page<Keyword> page1 = keywordResource.getKeywordsRankedPage(
			null, null, Pagination.of(1, 2));

		List<Keyword> keywords1 = (List<Keyword>)page1.getItems();

		Assert.assertEquals(keywords1.toString(), 2, keywords1.size());

		Page<Keyword> page2 = keywordResource.getKeywordsRankedPage(
			null, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Keyword> keywords2 = (List<Keyword>)page2.getItems();

		Assert.assertEquals(keywords2.toString(), 1, keywords2.size());

		Page<Keyword> page3 = keywordResource.getKeywordsRankedPage(
			null, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2, keyword3),
			(List<Keyword>)page3.getItems());
	}

	protected Keyword testGetKeywordsRankedPage_addKeyword(Keyword keyword)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteKeyword() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword = testDeleteKeyword_addKeyword();

		assertHttpResponseStatusCode(
			204, keywordResource.deleteKeywordHttpResponse(keyword.getId()));

		assertHttpResponseStatusCode(
			404, keywordResource.getKeywordHttpResponse(keyword.getId()));

		assertHttpResponseStatusCode(
			404, keywordResource.getKeywordHttpResponse(0L));
	}

	protected Keyword testDeleteKeyword_addKeyword() throws Exception {
		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Test
	public void testGraphQLDeleteKeyword() throws Exception {
		Keyword keyword = testGraphQLKeyword_addKeyword();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteKeyword",
						new HashMap<String, Object>() {
							{
								put("keywordId", keyword.getId());
							}
						})),
				"JSONObject/data", "Object/deleteKeyword"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"keyword",
					new HashMap<String, Object>() {
						{
							put("keywordId", keyword.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetKeyword() throws Exception {
		Keyword postKeyword = testGetKeyword_addKeyword();

		Keyword getKeyword = keywordResource.getKeyword(postKeyword.getId());

		assertEquals(postKeyword, getKeyword);
		assertValid(getKeyword);
	}

	protected Keyword testGetKeyword_addKeyword() throws Exception {
		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Test
	public void testGraphQLGetKeyword() throws Exception {
		Keyword keyword = testGraphQLKeyword_addKeyword();

		Assert.assertTrue(
			equals(
				keyword,
				KeywordSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"keyword",
								new HashMap<String, Object>() {
									{
										put("keywordId", keyword.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/keyword"))));
	}

	@Test
	public void testGraphQLGetKeywordNotFound() throws Exception {
		Long irrelevantKeywordId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"keyword",
						new HashMap<String, Object>() {
							{
								put("keywordId", irrelevantKeywordId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPutKeyword() throws Exception {
		Keyword postKeyword = testPutKeyword_addKeyword();

		Keyword randomKeyword = randomKeyword();

		Keyword putKeyword = keywordResource.putKeyword(
			postKeyword.getId(), randomKeyword);

		assertEquals(randomKeyword, putKeyword);
		assertValid(putKeyword);

		Keyword getKeyword = keywordResource.getKeyword(putKeyword.getId());

		assertEquals(randomKeyword, getKeyword);
		assertValid(getKeyword);
	}

	protected Keyword testPutKeyword_addKeyword() throws Exception {
		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Test
	public void testGetKeywordPermissionsPage() throws Exception {
		Keyword postKeyword = testGetKeywordPermissionsPage_addKeyword();

		Page<Permission> page = keywordResource.getKeywordPermissionsPage(
			postKeyword.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected Keyword testGetKeywordPermissionsPage_addKeyword()
		throws Exception {

		return testPostSiteKeyword_addKeyword(randomKeyword());
	}

	@Test
	public void testPutKeywordPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword = testPutKeywordPermission_addKeyword();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			keywordResource.putKeywordPermissionHttpResponse(
				keyword.getId(),
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
			keywordResource.putKeywordPermissionHttpResponse(
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

	protected Keyword testPutKeywordPermission_addKeyword() throws Exception {
		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Test
	public void testGetSiteKeywordsPage() throws Exception {
		Page<Keyword> page = keywordResource.getSiteKeywordsPage(
			testGetSiteKeywordsPage_getSiteId(), RandomTestUtil.randomString(),
			null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteKeywordsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteKeywordsPage_getIrrelevantSiteId();

		if (irrelevantSiteId != null) {
			Keyword irrelevantKeyword = testGetSiteKeywordsPage_addKeyword(
				irrelevantSiteId, randomIrrelevantKeyword());

			page = keywordResource.getSiteKeywordsPage(
				irrelevantSiteId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKeyword),
				(List<Keyword>)page.getItems());
			assertValid(page);
		}

		Keyword keyword1 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		Keyword keyword2 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		page = keywordResource.getSiteKeywordsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2), (List<Keyword>)page.getItems());
		assertValid(page);

		keywordResource.deleteKeyword(keyword1.getId());

		keywordResource.deleteKeyword(keyword2.getId());
	}

	@Test
	public void testGetSiteKeywordsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKeywordsPage_getSiteId();

		Keyword keyword1 = randomKeyword();

		keyword1 = testGetSiteKeywordsPage_addKeyword(siteId, keyword1);

		for (EntityField entityField : entityFields) {
			Page<Keyword> page = keywordResource.getSiteKeywordsPage(
				siteId, null, getFilterString(entityField, "between", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetSiteKeywordsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKeywordsPage_getSiteId();

		Keyword keyword1 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword2 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		for (EntityField entityField : entityFields) {
			Page<Keyword> page = keywordResource.getSiteKeywordsPage(
				siteId, null, getFilterString(entityField, "eq", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetSiteKeywordsPageWithPagination() throws Exception {
		Long siteId = testGetSiteKeywordsPage_getSiteId();

		Keyword keyword1 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		Keyword keyword2 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		Keyword keyword3 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		Page<Keyword> page1 = keywordResource.getSiteKeywordsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		List<Keyword> keywords1 = (List<Keyword>)page1.getItems();

		Assert.assertEquals(keywords1.toString(), 2, keywords1.size());

		Page<Keyword> page2 = keywordResource.getSiteKeywordsPage(
			siteId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Keyword> keywords2 = (List<Keyword>)page2.getItems();

		Assert.assertEquals(keywords2.toString(), 1, keywords2.size());

		Page<Keyword> page3 = keywordResource.getSiteKeywordsPage(
			siteId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2, keyword3),
			(List<Keyword>)page3.getItems());
	}

	@Test
	public void testGetSiteKeywordsPageWithSortDateTime() throws Exception {
		testGetSiteKeywordsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, keyword1, keyword2) -> {
				BeanUtils.setProperty(
					keyword1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteKeywordsPageWithSortInteger() throws Exception {
		testGetSiteKeywordsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, keyword1, keyword2) -> {
				BeanUtils.setProperty(keyword1, entityField.getName(), 0);
				BeanUtils.setProperty(keyword2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteKeywordsPageWithSortString() throws Exception {
		testGetSiteKeywordsPageWithSort(
			EntityField.Type.STRING,
			(entityField, keyword1, keyword2) -> {
				Class<?> clazz = keyword1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						keyword1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						keyword2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						keyword1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						keyword2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						keyword1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						keyword2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteKeywordsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Keyword, Keyword, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKeywordsPage_getSiteId();

		Keyword keyword1 = randomKeyword();
		Keyword keyword2 = randomKeyword();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, keyword1, keyword2);
		}

		keyword1 = testGetSiteKeywordsPage_addKeyword(siteId, keyword1);

		keyword2 = testGetSiteKeywordsPage_addKeyword(siteId, keyword2);

		for (EntityField entityField : entityFields) {
			Page<Keyword> ascPage = keywordResource.getSiteKeywordsPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(keyword1, keyword2),
				(List<Keyword>)ascPage.getItems());

			Page<Keyword> descPage = keywordResource.getSiteKeywordsPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(keyword2, keyword1),
				(List<Keyword>)descPage.getItems());
		}
	}

	protected Keyword testGetSiteKeywordsPage_addKeyword(
			Long siteId, Keyword keyword)
		throws Exception {

		return keywordResource.postSiteKeyword(siteId, keyword);
	}

	protected Long testGetSiteKeywordsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteKeywordsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteKeywordsPage() throws Exception {
		Long siteId = testGetSiteKeywordsPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"keywords",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject keywordsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/keywords");

		Assert.assertEquals(0, keywordsJSONObject.get("totalCount"));

		Keyword keyword1 = testGraphQLKeyword_addKeyword();
		Keyword keyword2 = testGraphQLKeyword_addKeyword();

		keywordsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/keywords");

		Assert.assertEquals(2, keywordsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2),
			Arrays.asList(
				KeywordSerDes.toDTOs(keywordsJSONObject.getString("items"))));
	}

	@Test
	public void testPostSiteKeyword() throws Exception {
		Keyword randomKeyword = randomKeyword();

		Keyword postKeyword = testPostSiteKeyword_addKeyword(randomKeyword);

		assertEquals(randomKeyword, postKeyword);
		assertValid(postKeyword);
	}

	protected Keyword testPostSiteKeyword_addKeyword(Keyword keyword)
		throws Exception {

		return keywordResource.postSiteKeyword(
			testGetSiteKeywordsPage_getSiteId(), keyword);
	}

	@Test
	public void testGraphQLPostSiteKeyword() throws Exception {
		Keyword randomKeyword = randomKeyword();

		Keyword keyword = testGraphQLKeyword_addKeyword(randomKeyword);

		Assert.assertTrue(equals(randomKeyword, keyword));
	}

	@Test
	public void testGetSiteKeywordPermissionsPage() throws Exception {
		Page<Permission> page = keywordResource.getSiteKeywordPermissionsPage(
			testGroup.getGroupId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected Keyword testGetSiteKeywordPermissionsPage_addKeyword()
		throws Exception {

		return testPostSiteKeyword_addKeyword(randomKeyword());
	}

	@Test
	public void testPutSiteKeywordPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword = testPutSiteKeywordPermission_addKeyword();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			keywordResource.putSiteKeywordPermissionHttpResponse(
				keyword.getSiteId(),
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
			keywordResource.putSiteKeywordPermissionHttpResponse(
				keyword.getSiteId(),
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"-"});
							setRoleName("-");
						}
					}
				}));
	}

	protected Keyword testPutSiteKeywordPermission_addKeyword()
		throws Exception {

		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
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

	protected Keyword testGraphQLKeyword_addKeyword() throws Exception {
		return testGraphQLKeyword_addKeyword(randomKeyword());
	}

	protected Keyword testGraphQLKeyword_addKeyword(Keyword keyword)
		throws Exception {

		JSONDeserializer<Keyword> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (Field field : getDeclaredFields(Keyword.class)) {
			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(keyword));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteKeyword",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("keyword", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data", "JSONObject/createSiteKeyword"),
			Keyword.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Keyword keyword1, Keyword keyword2) {
		Assert.assertTrue(
			keyword1 + " does not equal " + keyword2,
			equals(keyword1, keyword2));
	}

	protected void assertEquals(
		List<Keyword> keywords1, List<Keyword> keywords2) {

		Assert.assertEquals(keywords1.size(), keywords2.size());

		for (int i = 0; i < keywords1.size(); i++) {
			Keyword keyword1 = keywords1.get(i);
			Keyword keyword2 = keywords2.get(i);

			assertEquals(keyword1, keyword2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Keyword> keywords1, List<Keyword> keywords2) {

		Assert.assertEquals(keywords1.size(), keywords2.size());

		for (Keyword keyword1 : keywords1) {
			boolean contains = false;

			for (Keyword keyword2 : keywords2) {
				if (equals(keyword1, keyword2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				keywords2 + " does not contain " + keyword1, contains);
		}
	}

	protected void assertValid(Keyword keyword) throws Exception {
		boolean valid = true;

		if (keyword.getDateCreated() == null) {
			valid = false;
		}

		if (keyword.getDateModified() == null) {
			valid = false;
		}

		if (keyword.getId() == null) {
			valid = false;
		}

		Group group = testDepotEntry.getGroup();

		if (!Objects.equals(
				keyword.getAssetLibraryKey(), group.getGroupKey()) &&
			!Objects.equals(keyword.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (keyword.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetLibraryKey", additionalAssertFieldName)) {
				if (keyword.getAssetLibraryKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (keyword.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"keywordUsageCount", additionalAssertFieldName)) {

				if (keyword.getKeywordUsageCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (keyword.getName() == null) {
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

	protected void assertValid(Page<Keyword> page) {
		boolean valid = false;

		java.util.Collection<Keyword> keywords = page.getItems();

		int size = keywords.size();

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
					com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword.
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

	protected boolean equals(Keyword keyword1, Keyword keyword2) {
		if (keyword1 == keyword2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)keyword1.getActions(),
						(Map)keyword2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getCreator(), keyword2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getDateCreated(), keyword2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getDateModified(),
						keyword2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(keyword1.getId(), keyword2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals(
					"keywordUsageCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						keyword1.getKeywordUsageCount(),
						keyword2.getKeywordUsageCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getName(), keyword2.getName())) {

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

		if (!(_keywordResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_keywordResource;

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
		EntityField entityField, String operator, Keyword keyword) {

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
			sb.append(String.valueOf(keyword.getAssetLibraryKey()));
			sb.append("'");

			return sb.toString();
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
						DateUtils.addSeconds(keyword.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(keyword.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(keyword.getDateCreated()));
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
						DateUtils.addSeconds(keyword.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(keyword.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(keyword.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywordUsageCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(keyword.getName()));
			sb.append("'");

			return sb.toString();
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

	protected Keyword randomKeyword() throws Exception {
		return new Keyword() {
			{
				assetLibraryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				keywordUsageCount = RandomTestUtil.randomInt();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected Keyword randomIrrelevantKeyword() throws Exception {
		Keyword randomIrrelevantKeyword = randomKeyword();

		randomIrrelevantKeyword.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantKeyword;
	}

	protected Keyword randomPatchKeyword() throws Exception {
		return randomKeyword();
	}

	protected KeywordResource keywordResource;
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
		BaseKeywordResourceTestCase.class);

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
	private com.liferay.headless.admin.taxonomy.resource.v1_0.KeywordResource
		_keywordResource;

}