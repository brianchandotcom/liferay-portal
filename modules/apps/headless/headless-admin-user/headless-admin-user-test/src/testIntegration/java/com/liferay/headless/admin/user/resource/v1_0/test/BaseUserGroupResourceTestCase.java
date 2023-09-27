/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.user.client.dto.v1_0.UserGroup;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.UserGroupResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.UserGroupSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
public abstract class BaseUserGroupResourceTestCase {

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

		_userGroupResource.setContextCompany(testCompany);

		UserGroupResource.Builder builder = UserGroupResource.builder();

		userGroupResource = builder.authentication(
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

		UserGroup userGroup1 = randomUserGroup();

		String json = objectMapper.writeValueAsString(userGroup1);

		UserGroup userGroup2 = UserGroupSerDes.toDTO(json);

		Assert.assertTrue(equals(userGroup1, userGroup2));
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

		UserGroup userGroup = randomUserGroup();

		String json1 = objectMapper.writeValueAsString(userGroup);
		String json2 = UserGroupSerDes.toJSON(userGroup);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		UserGroup userGroup = randomUserGroup();

		userGroup.setDescription(regex);
		userGroup.setExternalReferenceCode(regex);
		userGroup.setName(regex);

		String json = UserGroupSerDes.toJSON(userGroup);

		Assert.assertFalse(json.contains(regex));

		userGroup = UserGroupSerDes.toDTO(json);

		Assert.assertEquals(regex, userGroup.getDescription());
		Assert.assertEquals(regex, userGroup.getExternalReferenceCode());
		Assert.assertEquals(regex, userGroup.getName());
	}

	@Test
	public void testGetUserUserGroups() throws Exception {
		Long userAccountId = testGetUserUserGroups_getUserAccountId();
		Long irrelevantUserAccountId =
			testGetUserUserGroups_getIrrelevantUserAccountId();

		Page<UserGroup> page = userGroupResource.getUserUserGroups(
			userAccountId);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantUserAccountId != null) {
			UserGroup irrelevantUserGroup = testGetUserUserGroups_addUserGroup(
				irrelevantUserAccountId, randomIrrelevantUserGroup());

			page = userGroupResource.getUserUserGroups(irrelevantUserAccountId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantUserGroup),
				(List<UserGroup>)page.getItems());
			assertValid(
				page,
				testGetUserUserGroups_getExpectedActions(
					irrelevantUserAccountId));
		}

		UserGroup userGroup1 = testGetUserUserGroups_addUserGroup(
			userAccountId, randomUserGroup());

		UserGroup userGroup2 = testGetUserUserGroups_addUserGroup(
			userAccountId, randomUserGroup());

		page = userGroupResource.getUserUserGroups(userAccountId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userGroup1, userGroup2),
			(List<UserGroup>)page.getItems());
		assertValid(
			page, testGetUserUserGroups_getExpectedActions(userAccountId));

		userGroupResource.deleteUserGroup(userGroup1.getId());

		userGroupResource.deleteUserGroup(userGroup2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetUserUserGroups_getExpectedActions(Long userAccountId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	protected UserGroup testGetUserUserGroups_addUserGroup(
			Long userAccountId, UserGroup userGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserUserGroups_getUserAccountId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserUserGroups_getIrrelevantUserAccountId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetUserGroupsPage() throws Exception {
		Page<UserGroup> page = userGroupResource.getUserGroupsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		UserGroup userGroup1 = testGetUserGroupsPage_addUserGroup(
			randomUserGroup());

		UserGroup userGroup2 = testGetUserGroupsPage_addUserGroup(
			randomUserGroup());

		page = userGroupResource.getUserGroupsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(userGroup1, (List<UserGroup>)page.getItems());
		assertContains(userGroup2, (List<UserGroup>)page.getItems());
		assertValid(page, testGetUserGroupsPage_getExpectedActions());

		userGroupResource.deleteUserGroup(userGroup1.getId());

		userGroupResource.deleteUserGroup(userGroup2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetUserGroupsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetUserGroupsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		UserGroup userGroup1 = randomUserGroup();

		userGroup1 = testGetUserGroupsPage_addUserGroup(userGroup1);

		for (EntityField entityField : entityFields) {
			Page<UserGroup> page = userGroupResource.getUserGroupsPage(
				null, getFilterString(entityField, "between", userGroup1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userGroup1),
				(List<UserGroup>)page.getItems());
		}
	}

	@Test
	public void testGetUserGroupsPageWithFilterDoubleEquals() throws Exception {
		testGetUserGroupsPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetUserGroupsPageWithFilterStringContains()
		throws Exception {

		testGetUserGroupsPageWithFilter("contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetUserGroupsPageWithFilterStringEquals() throws Exception {
		testGetUserGroupsPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetUserGroupsPageWithFilterStringStartsWith()
		throws Exception {

		testGetUserGroupsPageWithFilter("startswith", EntityField.Type.STRING);
	}

	protected void testGetUserGroupsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		UserGroup userGroup1 = testGetUserGroupsPage_addUserGroup(
			randomUserGroup());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserGroup userGroup2 = testGetUserGroupsPage_addUserGroup(
			randomUserGroup());

		for (EntityField entityField : entityFields) {
			Page<UserGroup> page = userGroupResource.getUserGroupsPage(
				null, getFilterString(entityField, operator, userGroup1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userGroup1),
				(List<UserGroup>)page.getItems());
		}
	}

	@Test
	public void testGetUserGroupsPageWithPagination() throws Exception {
		Page<UserGroup> totalPage = userGroupResource.getUserGroupsPage(
			null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		UserGroup userGroup1 = testGetUserGroupsPage_addUserGroup(
			randomUserGroup());

		UserGroup userGroup2 = testGetUserGroupsPage_addUserGroup(
			randomUserGroup());

		UserGroup userGroup3 = testGetUserGroupsPage_addUserGroup(
			randomUserGroup());

		Page<UserGroup> page1 = userGroupResource.getUserGroupsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<UserGroup> userGroups1 = (List<UserGroup>)page1.getItems();

		Assert.assertEquals(
			userGroups1.toString(), totalCount + 2, userGroups1.size());

		Page<UserGroup> page2 = userGroupResource.getUserGroupsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<UserGroup> userGroups2 = (List<UserGroup>)page2.getItems();

		Assert.assertEquals(userGroups2.toString(), 1, userGroups2.size());

		Page<UserGroup> page3 = userGroupResource.getUserGroupsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(userGroup1, (List<UserGroup>)page3.getItems());
		assertContains(userGroup2, (List<UserGroup>)page3.getItems());
		assertContains(userGroup3, (List<UserGroup>)page3.getItems());
	}

	@Test
	public void testGetUserGroupsPageWithSortDateTime() throws Exception {
		testGetUserGroupsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, userGroup1, userGroup2) -> {
				BeanTestUtil.setProperty(
					userGroup1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetUserGroupsPageWithSortDouble() throws Exception {
		testGetUserGroupsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, userGroup1, userGroup2) -> {
				BeanTestUtil.setProperty(
					userGroup1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					userGroup2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetUserGroupsPageWithSortInteger() throws Exception {
		testGetUserGroupsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, userGroup1, userGroup2) -> {
				BeanTestUtil.setProperty(userGroup1, entityField.getName(), 0);
				BeanTestUtil.setProperty(userGroup2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetUserGroupsPageWithSortString() throws Exception {
		testGetUserGroupsPageWithSort(
			EntityField.Type.STRING,
			(entityField, userGroup1, userGroup2) -> {
				Class<?> clazz = userGroup1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						userGroup1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						userGroup2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						userGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						userGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						userGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						userGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetUserGroupsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, UserGroup, UserGroup, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		UserGroup userGroup1 = randomUserGroup();
		UserGroup userGroup2 = randomUserGroup();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, userGroup1, userGroup2);
		}

		userGroup1 = testGetUserGroupsPage_addUserGroup(userGroup1);

		userGroup2 = testGetUserGroupsPage_addUserGroup(userGroup2);

		for (EntityField entityField : entityFields) {
			Page<UserGroup> ascPage = userGroupResource.getUserGroupsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(userGroup1, userGroup2),
				(List<UserGroup>)ascPage.getItems());

			Page<UserGroup> descPage = userGroupResource.getUserGroupsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userGroup2, userGroup1),
				(List<UserGroup>)descPage.getItems());
		}
	}

	protected UserGroup testGetUserGroupsPage_addUserGroup(UserGroup userGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetUserGroupsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"userGroups",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject userGroupsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/userGroups");

		long totalCount = userGroupsJSONObject.getLong("totalCount");

		UserGroup userGroup1 = testGraphQLGetUserGroupsPage_addUserGroup();
		UserGroup userGroup2 = testGraphQLGetUserGroupsPage_addUserGroup();

		userGroupsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/userGroups");

		Assert.assertEquals(
			totalCount + 2, userGroupsJSONObject.getLong("totalCount"));

		assertContains(
			userGroup1,
			Arrays.asList(
				UserGroupSerDes.toDTOs(
					userGroupsJSONObject.getString("items"))));
		assertContains(
			userGroup2,
			Arrays.asList(
				UserGroupSerDes.toDTOs(
					userGroupsJSONObject.getString("items"))));
	}

	protected UserGroup testGraphQLGetUserGroupsPage_addUserGroup()
		throws Exception {

		return testGraphQLUserGroup_addUserGroup();
	}

	@Test
	public void testPostUserGroup() throws Exception {
		UserGroup randomUserGroup = randomUserGroup();

		UserGroup postUserGroup = testPostUserGroup_addUserGroup(
			randomUserGroup);

		assertEquals(randomUserGroup, postUserGroup);
		assertValid(postUserGroup);
	}

	protected UserGroup testPostUserGroup_addUserGroup(UserGroup userGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteUserGroupByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserGroup userGroup =
			testDeleteUserGroupByExternalReferenceCode_addUserGroup();

		assertHttpResponseStatusCode(
			204,
			userGroupResource.
				deleteUserGroupByExternalReferenceCodeHttpResponse(
					userGroup.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			userGroupResource.getUserGroupByExternalReferenceCodeHttpResponse(
				userGroup.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			userGroupResource.getUserGroupByExternalReferenceCodeHttpResponse(
				userGroup.getExternalReferenceCode()));
	}

	protected UserGroup
			testDeleteUserGroupByExternalReferenceCode_addUserGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetUserGroupByExternalReferenceCode() throws Exception {
		UserGroup postUserGroup =
			testGetUserGroupByExternalReferenceCode_addUserGroup();

		UserGroup getUserGroup =
			userGroupResource.getUserGroupByExternalReferenceCode(
				postUserGroup.getExternalReferenceCode());

		assertEquals(postUserGroup, getUserGroup);
		assertValid(getUserGroup);
	}

	protected UserGroup testGetUserGroupByExternalReferenceCode_addUserGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetUserGroupByExternalReferenceCode()
		throws Exception {

		UserGroup userGroup =
			testGraphQLGetUserGroupByExternalReferenceCode_addUserGroup();

		Assert.assertTrue(
			equals(
				userGroup,
				UserGroupSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"userGroupByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												userGroup.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/userGroupByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetUserGroupByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"userGroupByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected UserGroup
			testGraphQLGetUserGroupByExternalReferenceCode_addUserGroup()
		throws Exception {

		return testGraphQLUserGroup_addUserGroup();
	}

	@Test
	public void testPatchUserGroupByExternalReferenceCode() throws Exception {
		UserGroup postUserGroup =
			testPatchUserGroupByExternalReferenceCode_addUserGroup();

		UserGroup randomPatchUserGroup = randomPatchUserGroup();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserGroup patchUserGroup =
			userGroupResource.patchUserGroupByExternalReferenceCode(
				postUserGroup.getExternalReferenceCode(), randomPatchUserGroup);

		UserGroup expectedPatchUserGroup = postUserGroup.clone();

		BeanTestUtil.copyProperties(
			randomPatchUserGroup, expectedPatchUserGroup);

		UserGroup getUserGroup =
			userGroupResource.getUserGroupByExternalReferenceCode(
				patchUserGroup.getExternalReferenceCode());

		assertEquals(expectedPatchUserGroup, getUserGroup);
		assertValid(getUserGroup);
	}

	protected UserGroup testPatchUserGroupByExternalReferenceCode_addUserGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutUserGroupByExternalReferenceCode() throws Exception {
		UserGroup postUserGroup =
			testPutUserGroupByExternalReferenceCode_addUserGroup();

		UserGroup randomUserGroup = randomUserGroup();

		UserGroup putUserGroup =
			userGroupResource.putUserGroupByExternalReferenceCode(
				postUserGroup.getExternalReferenceCode(), randomUserGroup);

		assertEquals(randomUserGroup, putUserGroup);
		assertValid(putUserGroup);

		UserGroup getUserGroup =
			userGroupResource.getUserGroupByExternalReferenceCode(
				putUserGroup.getExternalReferenceCode());

		assertEquals(randomUserGroup, getUserGroup);
		assertValid(getUserGroup);

		UserGroup newUserGroup =
			testPutUserGroupByExternalReferenceCode_createUserGroup();

		putUserGroup = userGroupResource.putUserGroupByExternalReferenceCode(
			newUserGroup.getExternalReferenceCode(), newUserGroup);

		assertEquals(newUserGroup, putUserGroup);
		assertValid(putUserGroup);

		getUserGroup = userGroupResource.getUserGroupByExternalReferenceCode(
			putUserGroup.getExternalReferenceCode());

		assertEquals(newUserGroup, getUserGroup);

		Assert.assertEquals(
			newUserGroup.getExternalReferenceCode(),
			putUserGroup.getExternalReferenceCode());
	}

	protected UserGroup
			testPutUserGroupByExternalReferenceCode_createUserGroup()
		throws Exception {

		return randomUserGroup();
	}

	protected UserGroup testPutUserGroupByExternalReferenceCode_addUserGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteUserGroup() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserGroup userGroup = testDeleteUserGroup_addUserGroup();

		assertHttpResponseStatusCode(
			204,
			userGroupResource.deleteUserGroupHttpResponse(userGroup.getId()));

		assertHttpResponseStatusCode(
			404, userGroupResource.getUserGroupHttpResponse(userGroup.getId()));

		assertHttpResponseStatusCode(
			404, userGroupResource.getUserGroupHttpResponse(0L));
	}

	protected UserGroup testDeleteUserGroup_addUserGroup() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteUserGroup() throws Exception {
		UserGroup userGroup = testGraphQLDeleteUserGroup_addUserGroup();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteUserGroup",
						new HashMap<String, Object>() {
							{
								put("userGroupId", userGroup.getId());
							}
						})),
				"JSONObject/data", "Object/deleteUserGroup"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"userGroup",
					new HashMap<String, Object>() {
						{
							put("userGroupId", userGroup.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected UserGroup testGraphQLDeleteUserGroup_addUserGroup()
		throws Exception {

		return testGraphQLUserGroup_addUserGroup();
	}

	@Test
	public void testGetUserGroup() throws Exception {
		UserGroup postUserGroup = testGetUserGroup_addUserGroup();

		UserGroup getUserGroup = userGroupResource.getUserGroup(
			postUserGroup.getId());

		assertEquals(postUserGroup, getUserGroup);
		assertValid(getUserGroup);
	}

	protected UserGroup testGetUserGroup_addUserGroup() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetUserGroup() throws Exception {
		UserGroup userGroup = testGraphQLGetUserGroup_addUserGroup();

		Assert.assertTrue(
			equals(
				userGroup,
				UserGroupSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"userGroup",
								new HashMap<String, Object>() {
									{
										put("userGroupId", userGroup.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/userGroup"))));
	}

	@Test
	public void testGraphQLGetUserGroupNotFound() throws Exception {
		Long irrelevantUserGroupId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"userGroup",
						new HashMap<String, Object>() {
							{
								put("userGroupId", irrelevantUserGroupId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected UserGroup testGraphQLGetUserGroup_addUserGroup()
		throws Exception {

		return testGraphQLUserGroup_addUserGroup();
	}

	@Test
	public void testPatchUserGroup() throws Exception {
		UserGroup postUserGroup = testPatchUserGroup_addUserGroup();

		UserGroup randomPatchUserGroup = randomPatchUserGroup();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserGroup patchUserGroup = userGroupResource.patchUserGroup(
			postUserGroup.getId(), randomPatchUserGroup);

		UserGroup expectedPatchUserGroup = postUserGroup.clone();

		BeanTestUtil.copyProperties(
			randomPatchUserGroup, expectedPatchUserGroup);

		UserGroup getUserGroup = userGroupResource.getUserGroup(
			patchUserGroup.getId());

		assertEquals(expectedPatchUserGroup, getUserGroup);
		assertValid(getUserGroup);
	}

	protected UserGroup testPatchUserGroup_addUserGroup() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutUserGroup() throws Exception {
		UserGroup postUserGroup = testPutUserGroup_addUserGroup();

		UserGroup randomUserGroup = randomUserGroup();

		UserGroup putUserGroup = userGroupResource.putUserGroup(
			postUserGroup.getId(), randomUserGroup);

		assertEquals(randomUserGroup, putUserGroup);
		assertValid(putUserGroup);

		UserGroup getUserGroup = userGroupResource.getUserGroup(
			putUserGroup.getId());

		assertEquals(randomUserGroup, getUserGroup);
		assertValid(getUserGroup);
	}

	protected UserGroup testPutUserGroup_addUserGroup() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteUserGroupUsers() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserGroup userGroup = testDeleteUserGroupUsers_addUserGroup();

		assertHttpResponseStatusCode(
			204,
			userGroupResource.deleteUserGroupUsersHttpResponse(
				userGroup.getId(), null));
	}

	protected UserGroup testDeleteUserGroupUsers_addUserGroup()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostUserGroupUsers() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserGroup userGroup = testPostUserGroupUsers_addUserGroup();

		assertHttpResponseStatusCode(
			204,
			userGroupResource.postUserGroupUsersHttpResponse(
				userGroup.getId(), null));

		assertHttpResponseStatusCode(
			404, userGroupResource.postUserGroupUsersHttpResponse(0L, null));
	}

	protected UserGroup testPostUserGroupUsers_addUserGroup() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected UserGroup testGraphQLUserGroup_addUserGroup() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		UserGroup userGroup, List<UserGroup> userGroups) {

		boolean contains = false;

		for (UserGroup item : userGroups) {
			if (equals(userGroup, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			userGroups + " does not contain " + userGroup, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(UserGroup userGroup1, UserGroup userGroup2) {
		Assert.assertTrue(
			userGroup1 + " does not equal " + userGroup2,
			equals(userGroup1, userGroup2));
	}

	protected void assertEquals(
		List<UserGroup> userGroups1, List<UserGroup> userGroups2) {

		Assert.assertEquals(userGroups1.size(), userGroups2.size());

		for (int i = 0; i < userGroups1.size(); i++) {
			UserGroup userGroup1 = userGroups1.get(i);
			UserGroup userGroup2 = userGroups2.get(i);

			assertEquals(userGroup1, userGroup2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<UserGroup> userGroups1, List<UserGroup> userGroups2) {

		Assert.assertEquals(userGroups1.size(), userGroups2.size());

		for (UserGroup userGroup1 : userGroups1) {
			boolean contains = false;

			for (UserGroup userGroup2 : userGroups2) {
				if (equals(userGroup1, userGroup2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				userGroups2 + " does not contain " + userGroup1, contains);
		}
	}

	protected void assertValid(UserGroup userGroup) throws Exception {
		boolean valid = true;

		if (userGroup.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (userGroup.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (userGroup.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (userGroup.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (userGroup.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("usersCount", additionalAssertFieldName)) {
				if (userGroup.getUsersCount() == null) {
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

	protected void assertValid(Page<UserGroup> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<UserGroup> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<UserGroup> userGroups = page.getItems();

		int size = userGroups.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.admin.user.dto.v1_0.UserGroup.class)) {

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

	protected boolean equals(UserGroup userGroup1, UserGroup userGroup2) {
		if (userGroup1 == userGroup2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)userGroup1.getActions(),
						(Map)userGroup2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userGroup1.getDescription(),
						userGroup2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						userGroup1.getExternalReferenceCode(),
						userGroup2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userGroup1.getId(), userGroup2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userGroup1.getName(), userGroup2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("usersCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userGroup1.getUsersCount(),
						userGroup2.getUsersCount())) {

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

		if (!(_userGroupResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_userGroupResource;

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
		EntityField entityField, String operator, UserGroup userGroup) {

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

		if (entityFieldName.equals("description")) {
			Object object = userGroup.getDescription();

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
			Object object = userGroup.getExternalReferenceCode();

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
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			Object object = userGroup.getName();

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

		if (entityFieldName.equals("usersCount")) {
			sb.append(String.valueOf(userGroup.getUsersCount()));

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

	protected UserGroup randomUserGroup() throws Exception {
		return new UserGroup() {
			{
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				usersCount = RandomTestUtil.randomInt();
			}
		};
	}

	protected UserGroup randomIrrelevantUserGroup() throws Exception {
		UserGroup randomIrrelevantUserGroup = randomUserGroup();

		return randomIrrelevantUserGroup;
	}

	protected UserGroup randomPatchUserGroup() throws Exception {
		return randomUserGroup();
	}

	protected UserGroupResource userGroupResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
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
		LogFactoryUtil.getLog(BaseUserGroupResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.admin.user.resource.v1_0.UserGroupResource
		_userGroupResource;

}