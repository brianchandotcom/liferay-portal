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
import com.liferay.headless.admin.user.client.resource.v1_0.UserGroupResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.UserGroupSerDes;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;

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
		userGroup.setName(regex);

		String json = UserGroupSerDes.toJSON(userGroup);

		Assert.assertFalse(json.contains(regex));

		userGroup = UserGroupSerDes.toDTO(json);

		Assert.assertEquals(regex, userGroup.getDescription());
		Assert.assertEquals(regex, userGroup.getName());
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
		UserGroup userGroup = testGraphQLUserGroup_addUserGroup();

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
		UserGroup userGroup = testGraphQLUserGroup_addUserGroup();

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

	@Test
	public void testPatchUserGroup() throws Exception {
		UserGroup postUserGroup = testPatchUserGroup_addUserGroup();

		UserGroup randomPatchUserGroup = randomPatchUserGroup();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserGroup patchUserGroup = userGroupResource.patchUserGroup(
			postUserGroup.getId(), randomPatchUserGroup);

		UserGroup expectedPatchUserGroup = postUserGroup.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchUserGroup, randomPatchUserGroup);

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
		boolean valid = false;

		java.util.Collection<UserGroup> userGroups = page.getItems();

		int size = userGroups.size();

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

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
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
			sb.append("'");
			sb.append(String.valueOf(userGroup.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(userGroup.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("usersCount")) {
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

	protected UserGroup randomUserGroup() throws Exception {
		return new UserGroup() {
			{
				description = StringUtil.toLowerCase(
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
	private com.liferay.headless.admin.user.resource.v1_0.UserGroupResource
		_userGroupResource;

}