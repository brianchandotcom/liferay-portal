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

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.change.tracking.rest.client.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.client.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.client.dto.v1_0.ProcessUser;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.ProcessUserResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.ProcessUserSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public abstract class BaseProcessUserResourceTestCase {

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

		_processUserResource.setContextCompany(testCompany);

		ProcessUserResource.Builder builder = ProcessUserResource.builder();

		processUserResource = builder.locale(
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

		ProcessUser processUser1 = randomProcessUser();

		String json = objectMapper.writeValueAsString(processUser1);

		ProcessUser processUser2 = ProcessUserSerDes.toDTO(json);

		Assert.assertTrue(equals(processUser1, processUser2));
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

		ProcessUser processUser = randomProcessUser();

		String json1 = objectMapper.writeValueAsString(processUser);
		String json2 = ProcessUserSerDes.toJSON(processUser);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ProcessUser processUser = randomProcessUser();

		processUser.setUserInitials(regex);
		processUser.setUserName(regex);
		processUser.setUserPortraitURL(regex);

		String json = ProcessUserSerDes.toJSON(processUser);

		Assert.assertFalse(json.contains(regex));

		processUser = ProcessUserSerDes.toDTO(json);

		Assert.assertEquals(regex, processUser.getUserInitials());
		Assert.assertEquals(regex, processUser.getUserName());
		Assert.assertEquals(regex, processUser.getUserPortraitURL());
	}

	@Test
	public void testGetProcessUsersPage() throws Exception {
		Page<ProcessUser> page = processUserResource.getProcessUsersPage(
			null, RandomTestUtil.randomString(), null, Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		ProcessUser processUser1 = testGetProcessUsersPage_addProcessUser(
			randomProcessUser());

		ProcessUser processUser2 = testGetProcessUsersPage_addProcessUser(
			randomProcessUser());

		page = processUserResource.getProcessUsersPage(
			null, null, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(processUser1, processUser2),
			(List<ProcessUser>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProcessUsersPageWithPagination() throws Exception {
		ProcessUser processUser1 = testGetProcessUsersPage_addProcessUser(
			randomProcessUser());

		ProcessUser processUser2 = testGetProcessUsersPage_addProcessUser(
			randomProcessUser());

		ProcessUser processUser3 = testGetProcessUsersPage_addProcessUser(
			randomProcessUser());

		Page<ProcessUser> page1 = processUserResource.getProcessUsersPage(
			null, null, null, Pagination.of(1, 2));

		List<ProcessUser> processUsers1 = (List<ProcessUser>)page1.getItems();

		Assert.assertEquals(processUsers1.toString(), 2, processUsers1.size());

		Page<ProcessUser> page2 = processUserResource.getProcessUsersPage(
			null, null, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ProcessUser> processUsers2 = (List<ProcessUser>)page2.getItems();

		Assert.assertEquals(processUsers2.toString(), 1, processUsers2.size());

		Page<ProcessUser> page3 = processUserResource.getProcessUsersPage(
			null, null, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(processUser1, processUser2, processUser3),
			(List<ProcessUser>)page3.getItems());
	}

	protected ProcessUser testGetProcessUsersPage_addProcessUser(
			ProcessUser processUser)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProcessUsersPage() throws Exception {
		Assert.assertTrue(false);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ProcessUser processUser1, ProcessUser processUser2) {

		Assert.assertTrue(
			processUser1 + " does not equal " + processUser2,
			equals(processUser1, processUser2));
	}

	protected void assertEquals(
		List<ProcessUser> processUsers1, List<ProcessUser> processUsers2) {

		Assert.assertEquals(processUsers1.size(), processUsers2.size());

		for (int i = 0; i < processUsers1.size(); i++) {
			ProcessUser processUser1 = processUsers1.get(i);
			ProcessUser processUser2 = processUsers2.get(i);

			assertEquals(processUser1, processUser2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ProcessUser> processUsers1, List<ProcessUser> processUsers2) {

		Assert.assertEquals(processUsers1.size(), processUsers2.size());

		for (ProcessUser processUser1 : processUsers1) {
			boolean contains = false;

			for (ProcessUser processUser2 : processUsers2) {
				if (equals(processUser1, processUser2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				processUsers2 + " does not contain " + processUser1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<ProcessUser> processUsers, JSONArray jsonArray) {

		for (ProcessUser processUser : processUsers) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(processUser, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + processUser, contains);
		}
	}

	protected void assertValid(ProcessUser processUser) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (processUser.getUserId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userInitials", additionalAssertFieldName)) {
				if (processUser.getUserInitials() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (processUser.getUserName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userPortraitURL", additionalAssertFieldName)) {
				if (processUser.getUserPortraitURL() == null) {
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

	protected void assertValid(Page<ProcessUser> page) {
		boolean valid = false;

		java.util.Collection<ProcessUser> processUsers = page.getItems();

		int size = processUsers.size();

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

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		ProcessUser processUser1, ProcessUser processUser2) {

		if (processUser1 == processUser2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						processUser1.getUserId(), processUser2.getUserId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userInitials", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						processUser1.getUserInitials(),
						processUser2.getUserInitials())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						processUser1.getUserName(),
						processUser2.getUserName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userPortraitURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						processUser1.getUserPortraitURL(),
						processUser2.getUserPortraitURL())) {

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

	protected boolean equalsJSONObject(
		ProcessUser processUser, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("userId", fieldName)) {
				if (!Objects.deepEquals(
						processUser.getUserId(),
						jsonObject.getLong("userId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userInitials", fieldName)) {
				if (!Objects.deepEquals(
						processUser.getUserInitials(),
						jsonObject.getString("userInitials"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userName", fieldName)) {
				if (!Objects.deepEquals(
						processUser.getUserName(),
						jsonObject.getString("userName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userPortraitURL", fieldName)) {
				if (!Objects.deepEquals(
						processUser.getUserPortraitURL(),
						jsonObject.getString("userPortraitURL"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_processUserResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_processUserResource;

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
		EntityField entityField, String operator, ProcessUser processUser) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("userId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("userInitials")) {
			sb.append("'");
			sb.append(String.valueOf(processUser.getUserInitials()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userName")) {
			sb.append("'");
			sb.append(String.valueOf(processUser.getUserName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userPortraitURL")) {
			sb.append("'");
			sb.append(String.valueOf(processUser.getUserPortraitURL()));
			sb.append("'");

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

	protected ProcessUser randomProcessUser() throws Exception {
		return new ProcessUser() {
			{
				userId = RandomTestUtil.randomLong();
				userInitials = RandomTestUtil.randomString();
				userName = RandomTestUtil.randomString();
				userPortraitURL = RandomTestUtil.randomString();
			}
		};
	}

	protected ProcessUser randomIrrelevantProcessUser() throws Exception {
		ProcessUser randomIrrelevantProcessUser = randomProcessUser();

		return randomIrrelevantProcessUser;
	}

	protected ProcessUser randomPatchProcessUser() throws Exception {
		return randomProcessUser();
	}

	protected ProcessUserResource processUserResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

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
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseProcessUserResourceTestCase.class);

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
	private com.liferay.change.tracking.rest.resource.v1_0.ProcessUserResource
		_processUserResource;

}