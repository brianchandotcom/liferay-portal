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

package com.liferay.account.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.account.rest.client.dto.v1_0.AccountRole;
import com.liferay.account.rest.client.http.HttpInvoker;
import com.liferay.account.rest.client.pagination.Page;
import com.liferay.account.rest.client.pagination.Pagination;
import com.liferay.account.rest.client.resource.v1_0.AccountRoleResource;
import com.liferay.account.rest.client.serdes.v1_0.AccountRoleSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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
 * @author Drew Brokke
 * @generated
 */
@Generated("")
public abstract class BaseAccountRoleResourceTestCase {

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

		_accountRoleResource.setContextCompany(testCompany);

		AccountRoleResource.Builder builder = AccountRoleResource.builder();

		accountRoleResource = builder.locale(
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

		AccountRole accountRole1 = randomAccountRole();

		String json = objectMapper.writeValueAsString(accountRole1);

		AccountRole accountRole2 = AccountRoleSerDes.toDTO(json);

		Assert.assertTrue(equals(accountRole1, accountRole2));
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

		AccountRole accountRole = randomAccountRole();

		String json1 = objectMapper.writeValueAsString(accountRole);
		String json2 = AccountRoleSerDes.toJSON(accountRole);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AccountRole accountRole = randomAccountRole();

		accountRole.setDescription(regex);
		accountRole.setDisplayName(regex);
		accountRole.setName(regex);

		String json = AccountRoleSerDes.toJSON(accountRole);

		Assert.assertFalse(json.contains(regex));

		accountRole = AccountRoleSerDes.toDTO(json);

		Assert.assertEquals(regex, accountRole.getDescription());
		Assert.assertEquals(regex, accountRole.getDisplayName());
		Assert.assertEquals(regex, accountRole.getName());
	}

	@Test
	public void testGetAccountRolesPage() throws Exception {
		Page<AccountRole> page = accountRoleResource.getAccountRolesPage(
			testGetAccountRolesPage_getAccountId(),
			RandomTestUtil.randomString(), Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long accountId = testGetAccountRolesPage_getAccountId();
		Long irrelevantAccountId =
			testGetAccountRolesPage_getIrrelevantAccountId();

		if ((irrelevantAccountId != null)) {
			AccountRole irrelevantAccountRole =
				testGetAccountRolesPage_addAccountRole(
					irrelevantAccountId, randomIrrelevantAccountRole());

			page = accountRoleResource.getAccountRolesPage(
				irrelevantAccountId, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountRole),
				(List<AccountRole>)page.getItems());
			assertValid(page);
		}

		AccountRole accountRole1 = testGetAccountRolesPage_addAccountRole(
			accountId, randomAccountRole());

		AccountRole accountRole2 = testGetAccountRolesPage_addAccountRole(
			accountId, randomAccountRole());

		page = accountRoleResource.getAccountRolesPage(
			accountId, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountRole1, accountRole2),
			(List<AccountRole>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountRolesPageWithPagination() throws Exception {
		Long accountId = testGetAccountRolesPage_getAccountId();

		AccountRole accountRole1 = testGetAccountRolesPage_addAccountRole(
			accountId, randomAccountRole());

		AccountRole accountRole2 = testGetAccountRolesPage_addAccountRole(
			accountId, randomAccountRole());

		AccountRole accountRole3 = testGetAccountRolesPage_addAccountRole(
			accountId, randomAccountRole());

		Page<AccountRole> page1 = accountRoleResource.getAccountRolesPage(
			accountId, null, Pagination.of(1, 2), null);

		List<AccountRole> accountRoles1 = (List<AccountRole>)page1.getItems();

		Assert.assertEquals(accountRoles1.toString(), 2, accountRoles1.size());

		Page<AccountRole> page2 = accountRoleResource.getAccountRolesPage(
			accountId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountRole> accountRoles2 = (List<AccountRole>)page2.getItems();

		Assert.assertEquals(accountRoles2.toString(), 1, accountRoles2.size());

		Page<AccountRole> page3 = accountRoleResource.getAccountRolesPage(
			accountId, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(accountRole1, accountRole2, accountRole3),
			(List<AccountRole>)page3.getItems());
	}

	@Test
	public void testGetAccountRolesPageWithSortDateTime() throws Exception {
		testGetAccountRolesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, accountRole1, accountRole2) -> {
				BeanUtils.setProperty(
					accountRole1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountRolesPageWithSortInteger() throws Exception {
		testGetAccountRolesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, accountRole1, accountRole2) -> {
				BeanUtils.setProperty(accountRole1, entityField.getName(), 0);
				BeanUtils.setProperty(accountRole2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountRolesPageWithSortString() throws Exception {
		testGetAccountRolesPageWithSort(
			EntityField.Type.STRING,
			(entityField, accountRole1, accountRole2) -> {
				Class<?> clazz = accountRole1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						accountRole1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						accountRole2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						accountRole1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						accountRole2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetAccountRolesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, AccountRole, AccountRole, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountRolesPage_getAccountId();

		AccountRole accountRole1 = randomAccountRole();
		AccountRole accountRole2 = randomAccountRole();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, accountRole1, accountRole2);
		}

		accountRole1 = testGetAccountRolesPage_addAccountRole(
			accountId, accountRole1);

		accountRole2 = testGetAccountRolesPage_addAccountRole(
			accountId, accountRole2);

		for (EntityField entityField : entityFields) {
			Page<AccountRole> ascPage = accountRoleResource.getAccountRolesPage(
				accountId, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(accountRole1, accountRole2),
				(List<AccountRole>)ascPage.getItems());

			Page<AccountRole> descPage =
				accountRoleResource.getAccountRolesPage(
					accountId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(accountRole2, accountRole1),
				(List<AccountRole>)descPage.getItems());
		}
	}

	protected AccountRole testGetAccountRolesPage_addAccountRole(
			Long accountId, AccountRole accountRole)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountRolesPage_getAccountId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountRolesPage_getIrrelevantAccountId()
		throws Exception {

		return null;
	}

	@Test
	public void testGraphQLGetAccountRolesPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"accountRoles",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject accountRolesJSONObject = dataJSONObject.getJSONObject(
			"accountRoles");

		Assert.assertEquals(0, accountRolesJSONObject.get("totalCount"));

		AccountRole accountRole1 = testGraphQLAccountRole_addAccountRole();
		AccountRole accountRole2 = testGraphQLAccountRole_addAccountRole();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		accountRolesJSONObject = dataJSONObject.getJSONObject("accountRoles");

		Assert.assertEquals(2, accountRolesJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(accountRole1, accountRole2),
			accountRolesJSONObject.getJSONArray("items"));
	}

	@Test
	public void testPostAccountRole() throws Exception {
		AccountRole randomAccountRole = randomAccountRole();

		AccountRole postAccountRole = testPostAccountRole_addAccountRole(
			randomAccountRole);

		assertEquals(randomAccountRole, postAccountRole);
		assertValid(postAccountRole);
	}

	protected AccountRole testPostAccountRole_addAccountRole(
			AccountRole accountRole)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountRoleUserAssociation() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole =
			testDeleteAccountRoleUserAssociation_addAccountRole();

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.deleteAccountRoleUserAssociationHttpResponse(
				null, accountRole.getId(), null));
	}

	protected AccountRole testDeleteAccountRoleUserAssociation_addAccountRole()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostAccountRoleUserAssociation() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole =
			testPostAccountRoleUserAssociation_addAccountRole();

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.postAccountRoleUserAssociationHttpResponse(
				null, accountRole.getId(), null));

		assertHttpResponseStatusCode(
			404,
			accountRoleResource.postAccountRoleUserAssociationHttpResponse(
				null, 0L, null));
	}

	protected AccountRole testPostAccountRoleUserAssociation_addAccountRole()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected AccountRole testGraphQLAccountRole_addAccountRole()
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
		AccountRole accountRole1, AccountRole accountRole2) {

		Assert.assertTrue(
			accountRole1 + " does not equal " + accountRole2,
			equals(accountRole1, accountRole2));
	}

	protected void assertEquals(
		List<AccountRole> accountRoles1, List<AccountRole> accountRoles2) {

		Assert.assertEquals(accountRoles1.size(), accountRoles2.size());

		for (int i = 0; i < accountRoles1.size(); i++) {
			AccountRole accountRole1 = accountRoles1.get(i);
			AccountRole accountRole2 = accountRoles2.get(i);

			assertEquals(accountRole1, accountRole2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AccountRole> accountRoles1, List<AccountRole> accountRoles2) {

		Assert.assertEquals(accountRoles1.size(), accountRoles2.size());

		for (AccountRole accountRole1 : accountRoles1) {
			boolean contains = false;

			for (AccountRole accountRole2 : accountRoles2) {
				if (equals(accountRole1, accountRole2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accountRoles2 + " does not contain " + accountRole1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<AccountRole> accountRoles, JSONArray jsonArray) {

		for (AccountRole accountRole : accountRoles) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(accountRole, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + accountRole, contains);
		}
	}

	protected void assertValid(AccountRole accountRole) {
		boolean valid = true;

		if (accountRole.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (accountRole.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (accountRole.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("displayName", additionalAssertFieldName)) {
				if (accountRole.getDisplayName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (accountRole.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("roleId", additionalAssertFieldName)) {
				if (accountRole.getRoleId() == null) {
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

	protected void assertValid(Page<AccountRole> page) {
		boolean valid = false;

		java.util.Collection<AccountRole> accountRoles = page.getItems();

		int size = accountRoles.size();

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
		AccountRole accountRole1, AccountRole accountRole2) {

		if (accountRole1 == accountRole2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getAccountId(),
						accountRole2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getDescription(),
						accountRole2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getDisplayName(),
						accountRole2.getDisplayName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getId(), accountRole2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getName(), accountRole2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("roleId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getRoleId(), accountRole2.getRoleId())) {

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
		AccountRole accountRole, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("accountId", fieldName)) {
				if (!Objects.deepEquals(
						accountRole.getAccountId(),
						jsonObject.getLong("accountId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						accountRole.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayName", fieldName)) {
				if (!Objects.deepEquals(
						accountRole.getDisplayName(),
						jsonObject.getString("displayName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						accountRole.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						accountRole.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("roleId", fieldName)) {
				if (!Objects.deepEquals(
						accountRole.getRoleId(),
						jsonObject.getLong("roleId"))) {

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

		if (!(_accountRoleResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountRoleResource;

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
		EntityField entityField, String operator, AccountRole accountRole) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(accountRole.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("displayName")) {
			sb.append("'");
			sb.append(String.valueOf(accountRole.getDisplayName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(accountRole.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("roleId")) {
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

	protected AccountRole randomAccountRole() throws Exception {
		return new AccountRole() {
			{
				accountId = RandomTestUtil.randomLong();
				description = RandomTestUtil.randomString();
				displayName = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				roleId = RandomTestUtil.randomLong();
			}
		};
	}

	protected AccountRole randomIrrelevantAccountRole() throws Exception {
		AccountRole randomIrrelevantAccountRole = randomAccountRole();

		return randomIrrelevantAccountRole;
	}

	protected AccountRole randomPatchAccountRole() throws Exception {
		return randomAccountRole();
	}

	protected AccountRoleResource accountRoleResource;
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
		BaseAccountRoleResourceTestCase.class);

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
	private com.liferay.account.rest.resource.v1_0.AccountRoleResource
		_accountRoleResource;

}