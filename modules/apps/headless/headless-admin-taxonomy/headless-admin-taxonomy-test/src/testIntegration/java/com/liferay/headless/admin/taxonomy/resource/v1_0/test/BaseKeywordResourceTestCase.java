/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import java.text.DateFormat;

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

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
		Long assetLibraryId =
			testGetAssetLibraryKeywordsPage_getAssetLibraryId();
		Long irrelevantAssetLibraryId =
			testGetAssetLibraryKeywordsPage_getIrrelevantAssetLibraryId();

		Page<Keyword> page = keywordResource.getAssetLibraryKeywordsPage(
			assetLibraryId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantAssetLibraryId != null) {
			Keyword irrelevantKeyword =
				testGetAssetLibraryKeywordsPage_addKeyword(
					irrelevantAssetLibraryId, randomIrrelevantKeyword());

			page = keywordResource.getAssetLibraryKeywordsPage(
				irrelevantAssetLibraryId, null, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKeyword),
				(List<Keyword>)page.getItems());
			assertValid(
				page,
				testGetAssetLibraryKeywordsPage_getExpectedActions(
					irrelevantAssetLibraryId));
		}

		Keyword keyword1 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, randomKeyword());

		Keyword keyword2 = testGetAssetLibraryKeywordsPage_addKeyword(
			assetLibraryId, randomKeyword());

		page = keywordResource.getAssetLibraryKeywordsPage(
			assetLibraryId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2), (List<Keyword>)page.getItems());
		assertValid(
			page,
			testGetAssetLibraryKeywordsPage_getExpectedActions(assetLibraryId));

		keywordResource.deleteKeyword(keyword1.getId());

		keywordResource.deleteKeyword(keyword2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetAssetLibraryKeywordsPage_getExpectedActions(
				Long assetLibraryId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/keywords/batch".
				replace("{assetLibraryId}", String.valueOf(assetLibraryId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
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
				assetLibraryId, null, null,
				getFilterString(entityField, "between", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithFilterDoubleEquals()
		throws Exception {

		testGetAssetLibraryKeywordsPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithFilterStringContains()
		throws Exception {

		testGetAssetLibraryKeywordsPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithFilterStringEquals()
		throws Exception {

		testGetAssetLibraryKeywordsPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithFilterStringStartsWith()
		throws Exception {

		testGetAssetLibraryKeywordsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetAssetLibraryKeywordsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

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
				assetLibraryId, null, null,
				getFilterString(entityField, operator, keyword1),
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
			assetLibraryId, null, null, null, Pagination.of(1, 2), null);

		List<Keyword> keywords1 = (List<Keyword>)page1.getItems();

		Assert.assertEquals(keywords1.toString(), 2, keywords1.size());

		Page<Keyword> page2 = keywordResource.getAssetLibraryKeywordsPage(
			assetLibraryId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Keyword> keywords2 = (List<Keyword>)page2.getItems();

		Assert.assertEquals(keywords2.toString(), 1, keywords2.size());

		Page<Keyword> page3 = keywordResource.getAssetLibraryKeywordsPage(
			assetLibraryId, null, null, null, Pagination.of(1, 3), null);

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
				BeanTestUtil.setProperty(
					keyword1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithSortDouble()
		throws Exception {

		testGetAssetLibraryKeywordsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, keyword1, keyword2) -> {
				BeanTestUtil.setProperty(keyword1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(keyword2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAssetLibraryKeywordsPageWithSortInteger()
		throws Exception {

		testGetAssetLibraryKeywordsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, keyword1, keyword2) -> {
				BeanTestUtil.setProperty(keyword1, entityField.getName(), 0);
				BeanTestUtil.setProperty(keyword2, entityField.getName(), 1);
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
					BeanTestUtil.setProperty(
						keyword1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						keyword2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						keyword1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						keyword2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						keyword1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
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
				assetLibraryId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(keyword1, keyword2),
				(List<Keyword>)ascPage.getItems());

			Page<Keyword> descPage =
				keywordResource.getAssetLibraryKeywordsPage(
					assetLibraryId, null, null, null, Pagination.of(1, 2),
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
	public void testPutAssetLibraryKeywordPermissionsPage() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword =
			testPutAssetLibraryKeywordPermissionsPage_addKeyword();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			keywordResource.putAssetLibraryKeywordPermissionsPageHttpResponse(
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
			keywordResource.putAssetLibraryKeywordPermissionsPageHttpResponse(
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

	protected Keyword testPutAssetLibraryKeywordPermissionsPage_addKeyword()
		throws Exception {

		return keywordResource.postAssetLibraryKeyword(
			testDepotEntry.getDepotEntryId(), randomKeyword());
	}

	@Test
	public void testGetKeywordsRankedPage() throws Exception {
		Page<Keyword> page = keywordResource.getKeywordsRankedPage(
			null, null, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		Keyword keyword1 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		Keyword keyword2 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		page = keywordResource.getKeywordsRankedPage(
			null, null, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(keyword1, (List<Keyword>)page.getItems());
		assertContains(keyword2, (List<Keyword>)page.getItems());
		assertValid(page, testGetKeywordsRankedPage_getExpectedActions());

		keywordResource.deleteKeyword(keyword1.getId());

		keywordResource.deleteKeyword(keyword2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetKeywordsRankedPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetKeywordsRankedPageWithPagination() throws Exception {
		Page<Keyword> totalPage = keywordResource.getKeywordsRankedPage(
			null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Keyword keyword1 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		Keyword keyword2 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		Keyword keyword3 = testGetKeywordsRankedPage_addKeyword(
			randomKeyword());

		Page<Keyword> page1 = keywordResource.getKeywordsRankedPage(
			null, null, Pagination.of(1, totalCount + 2));

		List<Keyword> keywords1 = (List<Keyword>)page1.getItems();

		Assert.assertEquals(
			keywords1.toString(), totalCount + 2, keywords1.size());

		Page<Keyword> page2 = keywordResource.getKeywordsRankedPage(
			null, null, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Keyword> keywords2 = (List<Keyword>)page2.getItems();

		Assert.assertEquals(keywords2.toString(), 1, keywords2.size());

		Page<Keyword> page3 = keywordResource.getKeywordsRankedPage(
			null, null, Pagination.of(1, totalCount + 3));

		assertContains(keyword1, (List<Keyword>)page3.getItems());
		assertContains(keyword2, (List<Keyword>)page3.getItems());
		assertContains(keyword3, (List<Keyword>)page3.getItems());
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
		Keyword keyword = testGraphQLDeleteKeyword_addKeyword();

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

	protected Keyword testGraphQLDeleteKeyword_addKeyword() throws Exception {
		return testGraphQLKeyword_addKeyword();
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
		Keyword keyword = testGraphQLGetKeyword_addKeyword();

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

	protected Keyword testGraphQLGetKeyword_addKeyword() throws Exception {
		return testGraphQLKeyword_addKeyword();
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
	public void testPutKeywordSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword = testPutKeywordSubscribe_addKeyword();

		assertHttpResponseStatusCode(
			204,
			keywordResource.putKeywordSubscribeHttpResponse(keyword.getId()));

		assertHttpResponseStatusCode(
			404, keywordResource.putKeywordSubscribeHttpResponse(0L));
	}

	protected Keyword testPutKeywordSubscribe_addKeyword() throws Exception {
		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Test
	public void testPutKeywordUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword = testPutKeywordUnsubscribe_addKeyword();

		assertHttpResponseStatusCode(
			204,
			keywordResource.putKeywordUnsubscribeHttpResponse(keyword.getId()));

		assertHttpResponseStatusCode(
			404, keywordResource.putKeywordUnsubscribeHttpResponse(0L));
	}

	protected Keyword testPutKeywordUnsubscribe_addKeyword() throws Exception {
		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Test
	public void testGetSiteKeywordsPage() throws Exception {
		Long siteId = testGetSiteKeywordsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteKeywordsPage_getIrrelevantSiteId();

		Page<Keyword> page = keywordResource.getSiteKeywordsPage(
			siteId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantSiteId != null) {
			Keyword irrelevantKeyword = testGetSiteKeywordsPage_addKeyword(
				irrelevantSiteId, randomIrrelevantKeyword());

			page = keywordResource.getSiteKeywordsPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKeyword),
				(List<Keyword>)page.getItems());
			assertValid(
				page,
				testGetSiteKeywordsPage_getExpectedActions(irrelevantSiteId));
		}

		Keyword keyword1 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		Keyword keyword2 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		page = keywordResource.getSiteKeywordsPage(
			siteId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2), (List<Keyword>)page.getItems());
		assertValid(page, testGetSiteKeywordsPage_getExpectedActions(siteId));

		keywordResource.deleteKeyword(keyword1.getId());

		keywordResource.deleteKeyword(keyword2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetSiteKeywordsPage_getExpectedActions(Long siteId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords/batch".
				replace("{siteId}", String.valueOf(siteId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
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
				siteId, null, null,
				getFilterString(entityField, "between", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetSiteKeywordsPageWithFilterDoubleEquals()
		throws Exception {

		testGetSiteKeywordsPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetSiteKeywordsPageWithFilterStringContains()
		throws Exception {

		testGetSiteKeywordsPageWithFilter("contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetSiteKeywordsPageWithFilterStringEquals()
		throws Exception {

		testGetSiteKeywordsPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetSiteKeywordsPageWithFilterStringStartsWith()
		throws Exception {

		testGetSiteKeywordsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetSiteKeywordsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

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
				siteId, null, null,
				getFilterString(entityField, operator, keyword1),
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
			siteId, null, null, null, Pagination.of(1, 2), null);

		List<Keyword> keywords1 = (List<Keyword>)page1.getItems();

		Assert.assertEquals(keywords1.toString(), 2, keywords1.size());

		Page<Keyword> page2 = keywordResource.getSiteKeywordsPage(
			siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Keyword> keywords2 = (List<Keyword>)page2.getItems();

		Assert.assertEquals(keywords2.toString(), 1, keywords2.size());

		Page<Keyword> page3 = keywordResource.getSiteKeywordsPage(
			siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2, keyword3),
			(List<Keyword>)page3.getItems());
	}

	@Test
	public void testGetSiteKeywordsPageWithSortDateTime() throws Exception {
		testGetSiteKeywordsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, keyword1, keyword2) -> {
				BeanTestUtil.setProperty(
					keyword1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteKeywordsPageWithSortDouble() throws Exception {
		testGetSiteKeywordsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, keyword1, keyword2) -> {
				BeanTestUtil.setProperty(keyword1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(keyword2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetSiteKeywordsPageWithSortInteger() throws Exception {
		testGetSiteKeywordsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, keyword1, keyword2) -> {
				BeanTestUtil.setProperty(keyword1, entityField.getName(), 0);
				BeanTestUtil.setProperty(keyword2, entityField.getName(), 1);
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
					BeanTestUtil.setProperty(
						keyword1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						keyword2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						keyword1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						keyword2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						keyword1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
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
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(keyword1, keyword2),
				(List<Keyword>)ascPage.getItems());

			Page<Keyword> descPage = keywordResource.getSiteKeywordsPage(
				siteId, null, null, null, Pagination.of(1, 2),
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
					put("pageSize", 10);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject keywordsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/keywords");

		Assert.assertEquals(0, keywordsJSONObject.get("totalCount"));

		Keyword keyword1 = testGraphQLGetSiteKeywordsPage_addKeyword();
		Keyword keyword2 = testGraphQLGetSiteKeywordsPage_addKeyword();

		keywordsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/keywords");

		Assert.assertEquals(2, keywordsJSONObject.getLong("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2),
			Arrays.asList(
				KeywordSerDes.toDTOs(keywordsJSONObject.getString("items"))));
	}

	protected Keyword testGraphQLGetSiteKeywordsPage_addKeyword()
		throws Exception {

		return testGraphQLKeyword_addKeyword();
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
	public void testPutSiteKeywordPermissionsPage() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword = testPutSiteKeywordPermissionsPage_addKeyword();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			keywordResource.putSiteKeywordPermissionsPageHttpResponse(
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
			keywordResource.putSiteKeywordPermissionsPageHttpResponse(
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

	protected Keyword testPutSiteKeywordPermissionsPage_addKeyword()
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

				for (java.lang.reflect.Field field :
						getDeclaredFields(clazz.getSuperclass())) {

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

		for (java.lang.reflect.Field field : getDeclaredFields(Keyword.class)) {
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

	protected void assertContains(Keyword keyword, List<Keyword> keywords) {
		boolean contains = false;

		for (Keyword item : keywords) {
			if (equals(keyword, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(keywords + " does not contain " + keyword, contains);
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

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (keyword.getSubscribed() == null) {
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
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Keyword> page, Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Keyword> keywords = page.getItems();

		int size = keywords.size();

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

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getSubscribed(), keyword2.getSubscribed())) {

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

		if (!(_keywordResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_keywordResource;

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
			Object object = keyword.getAssetLibraryKey();

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
			sb.append(String.valueOf(keyword.getKeywordUsageCount()));

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			Object object = keyword.getName();

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

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subscribed")) {
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
				subscribed = RandomTestUtil.randomBoolean();
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

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
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

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
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
		LogFactoryUtil.getLog(BaseKeywordResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.admin.taxonomy.resource.v1_0.KeywordResource
		_keywordResource;

}