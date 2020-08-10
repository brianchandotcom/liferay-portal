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

import com.liferay.account.rest.client.dto.v1_0.AccountUser;
import com.liferay.account.rest.client.http.HttpInvoker;
import com.liferay.account.rest.client.pagination.Page;
import com.liferay.account.rest.client.pagination.Pagination;
import com.liferay.account.rest.client.resource.v1_0.AccountUserResource;
import com.liferay.account.rest.client.serdes.v1_0.AccountUserSerDes;
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
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
 * @author Drew Brokke
 * @generated
 */
@Generated("")
public abstract class BaseAccountUserResourceTestCase {

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

		_accountUserResource.setContextCompany(testCompany);

		AccountUserResource.Builder builder = AccountUserResource.builder();

		accountUserResource = builder.authentication(
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

		AccountUser accountUser1 = randomAccountUser();

		String json = objectMapper.writeValueAsString(accountUser1);

		AccountUser accountUser2 = AccountUserSerDes.toDTO(json);

		Assert.assertTrue(equals(accountUser1, accountUser2));
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

		AccountUser accountUser = randomAccountUser();

		String json1 = objectMapper.writeValueAsString(accountUser);
		String json2 = AccountUserSerDes.toJSON(accountUser);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AccountUser accountUser = randomAccountUser();

		accountUser.setEmailAddress(regex);
		accountUser.setFirstName(regex);
		accountUser.setLastName(regex);
		accountUser.setMiddleName(regex);
		accountUser.setPrefix(regex);
		accountUser.setScreenName(regex);
		accountUser.setSuffix(regex);

		String json = AccountUserSerDes.toJSON(accountUser);

		Assert.assertFalse(json.contains(regex));

		accountUser = AccountUserSerDes.toDTO(json);

		Assert.assertEquals(regex, accountUser.getEmailAddress());
		Assert.assertEquals(regex, accountUser.getFirstName());
		Assert.assertEquals(regex, accountUser.getLastName());
		Assert.assertEquals(regex, accountUser.getMiddleName());
		Assert.assertEquals(regex, accountUser.getPrefix());
		Assert.assertEquals(regex, accountUser.getScreenName());
		Assert.assertEquals(regex, accountUser.getSuffix());
	}

	@Test
	public void testGetAccountUsersPage() throws Exception {
		Page<AccountUser> page = accountUserResource.getAccountUsersPage(
			testGetAccountUsersPage_getAccountId(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long accountId = testGetAccountUsersPage_getAccountId();
		Long irrelevantAccountId =
			testGetAccountUsersPage_getIrrelevantAccountId();

		if ((irrelevantAccountId != null)) {
			AccountUser irrelevantAccountUser =
				testGetAccountUsersPage_addAccountUser(
					irrelevantAccountId, randomIrrelevantAccountUser());

			page = accountUserResource.getAccountUsersPage(
				irrelevantAccountId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountUser),
				(List<AccountUser>)page.getItems());
			assertValid(page);
		}

		AccountUser accountUser1 = testGetAccountUsersPage_addAccountUser(
			accountId, randomAccountUser());

		AccountUser accountUser2 = testGetAccountUsersPage_addAccountUser(
			accountId, randomAccountUser());

		page = accountUserResource.getAccountUsersPage(
			accountId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountUser1, accountUser2),
			(List<AccountUser>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountUsersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountUsersPage_getAccountId();

		AccountUser accountUser1 = randomAccountUser();

		accountUser1 = testGetAccountUsersPage_addAccountUser(
			accountId, accountUser1);

		for (EntityField entityField : entityFields) {
			Page<AccountUser> page = accountUserResource.getAccountUsersPage(
				accountId, null,
				getFilterString(entityField, "between", accountUser1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountUser1),
				(List<AccountUser>)page.getItems());
		}
	}

	@Test
	public void testGetAccountUsersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountUsersPage_getAccountId();

		AccountUser accountUser1 = testGetAccountUsersPage_addAccountUser(
			accountId, randomAccountUser());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountUser accountUser2 = testGetAccountUsersPage_addAccountUser(
			accountId, randomAccountUser());

		for (EntityField entityField : entityFields) {
			Page<AccountUser> page = accountUserResource.getAccountUsersPage(
				accountId, null,
				getFilterString(entityField, "eq", accountUser1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountUser1),
				(List<AccountUser>)page.getItems());
		}
	}

	@Test
	public void testGetAccountUsersPageWithPagination() throws Exception {
		Long accountId = testGetAccountUsersPage_getAccountId();

		AccountUser accountUser1 = testGetAccountUsersPage_addAccountUser(
			accountId, randomAccountUser());

		AccountUser accountUser2 = testGetAccountUsersPage_addAccountUser(
			accountId, randomAccountUser());

		AccountUser accountUser3 = testGetAccountUsersPage_addAccountUser(
			accountId, randomAccountUser());

		Page<AccountUser> page1 = accountUserResource.getAccountUsersPage(
			accountId, null, null, Pagination.of(1, 2), null);

		List<AccountUser> accountUsers1 = (List<AccountUser>)page1.getItems();

		Assert.assertEquals(accountUsers1.toString(), 2, accountUsers1.size());

		Page<AccountUser> page2 = accountUserResource.getAccountUsersPage(
			accountId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountUser> accountUsers2 = (List<AccountUser>)page2.getItems();

		Assert.assertEquals(accountUsers2.toString(), 1, accountUsers2.size());

		Page<AccountUser> page3 = accountUserResource.getAccountUsersPage(
			accountId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(accountUser1, accountUser2, accountUser3),
			(List<AccountUser>)page3.getItems());
	}

	@Test
	public void testGetAccountUsersPageWithSortDateTime() throws Exception {
		testGetAccountUsersPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, accountUser1, accountUser2) -> {
				BeanUtils.setProperty(
					accountUser1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountUsersPageWithSortInteger() throws Exception {
		testGetAccountUsersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, accountUser1, accountUser2) -> {
				BeanUtils.setProperty(accountUser1, entityField.getName(), 0);
				BeanUtils.setProperty(accountUser2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountUsersPageWithSortString() throws Exception {
		testGetAccountUsersPageWithSort(
			EntityField.Type.STRING,
			(entityField, accountUser1, accountUser2) -> {
				Class<?> clazz = accountUser1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						accountUser1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						accountUser2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						accountUser1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						accountUser2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						accountUser1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						accountUser2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAccountUsersPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, AccountUser, AccountUser, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountUsersPage_getAccountId();

		AccountUser accountUser1 = randomAccountUser();
		AccountUser accountUser2 = randomAccountUser();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, accountUser1, accountUser2);
		}

		accountUser1 = testGetAccountUsersPage_addAccountUser(
			accountId, accountUser1);

		accountUser2 = testGetAccountUsersPage_addAccountUser(
			accountId, accountUser2);

		for (EntityField entityField : entityFields) {
			Page<AccountUser> ascPage = accountUserResource.getAccountUsersPage(
				accountId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(accountUser1, accountUser2),
				(List<AccountUser>)ascPage.getItems());

			Page<AccountUser> descPage =
				accountUserResource.getAccountUsersPage(
					accountId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(accountUser2, accountUser1),
				(List<AccountUser>)descPage.getItems());
		}
	}

	protected AccountUser testGetAccountUsersPage_addAccountUser(
			Long accountId, AccountUser accountUser)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountUsersPage_getAccountId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountUsersPage_getIrrelevantAccountId()
		throws Exception {

		return null;
	}

	@Test
	public void testGraphQLGetAccountUsersPage() throws Exception {
		Long accountId = testGetAccountUsersPage_getAccountId();

		GraphQLField graphQLField = new GraphQLField(
			"accountUsers",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("accountId", accountId);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject accountUsersJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/accountUsers");

		Assert.assertEquals(0, accountUsersJSONObject.get("totalCount"));

		AccountUser accountUser1 = testGraphQLAccountUser_addAccountUser();
		AccountUser accountUser2 = testGraphQLAccountUser_addAccountUser();

		accountUsersJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/accountUsers");

		Assert.assertEquals(2, accountUsersJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(accountUser1, accountUser2),
			Arrays.asList(
				AccountUserSerDes.toDTOs(
					accountUsersJSONObject.getString("items"))));
	}

	@Test
	public void testPostAccountUser() throws Exception {
		AccountUser randomAccountUser = randomAccountUser();

		AccountUser postAccountUser = testPostAccountUser_addAccountUser(
			randomAccountUser);

		assertEquals(randomAccountUser, postAccountUser);
		assertValid(postAccountUser);
	}

	protected AccountUser testPostAccountUser_addAccountUser(
			AccountUser accountUser)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected AccountUser testGraphQLAccountUser_addAccountUser()
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
		AccountUser accountUser1, AccountUser accountUser2) {

		Assert.assertTrue(
			accountUser1 + " does not equal " + accountUser2,
			equals(accountUser1, accountUser2));
	}

	protected void assertEquals(
		List<AccountUser> accountUsers1, List<AccountUser> accountUsers2) {

		Assert.assertEquals(accountUsers1.size(), accountUsers2.size());

		for (int i = 0; i < accountUsers1.size(); i++) {
			AccountUser accountUser1 = accountUsers1.get(i);
			AccountUser accountUser2 = accountUsers2.get(i);

			assertEquals(accountUser1, accountUser2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AccountUser> accountUsers1, List<AccountUser> accountUsers2) {

		Assert.assertEquals(accountUsers1.size(), accountUsers2.size());

		for (AccountUser accountUser1 : accountUsers1) {
			boolean contains = false;

			for (AccountUser accountUser2 : accountUsers2) {
				if (equals(accountUser1, accountUser2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accountUsers2 + " does not contain " + accountUser1, contains);
		}
	}

	protected void assertValid(AccountUser accountUser) throws Exception {
		boolean valid = true;

		if (accountUser.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("emailAddress", additionalAssertFieldName)) {
				if (accountUser.getEmailAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("firstName", additionalAssertFieldName)) {
				if (accountUser.getFirstName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("lastName", additionalAssertFieldName)) {
				if (accountUser.getLastName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("middleName", additionalAssertFieldName)) {
				if (accountUser.getMiddleName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("prefix", additionalAssertFieldName)) {
				if (accountUser.getPrefix() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("screenName", additionalAssertFieldName)) {
				if (accountUser.getScreenName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("suffix", additionalAssertFieldName)) {
				if (accountUser.getSuffix() == null) {
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

	protected void assertValid(Page<AccountUser> page) {
		boolean valid = false;

		java.util.Collection<AccountUser> accountUsers = page.getItems();

		int size = accountUsers.size();

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

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.account.rest.dto.v1_0.AccountUser.class)) {

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
					ReflectionUtil.getDeclaredFields(clazz));

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
		AccountUser accountUser1, AccountUser accountUser2) {

		if (accountUser1 == accountUser2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("emailAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountUser1.getEmailAddress(),
						accountUser2.getEmailAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("firstName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountUser1.getFirstName(),
						accountUser2.getFirstName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountUser1.getId(), accountUser2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("lastName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountUser1.getLastName(),
						accountUser2.getLastName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("middleName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountUser1.getMiddleName(),
						accountUser2.getMiddleName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("prefix", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountUser1.getPrefix(), accountUser2.getPrefix())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("screenName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountUser1.getScreenName(),
						accountUser2.getScreenName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("suffix", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountUser1.getSuffix(), accountUser2.getSuffix())) {

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
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_accountUserResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountUserResource;

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
		EntityField entityField, String operator, AccountUser accountUser) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("emailAddress")) {
			sb.append("'");
			sb.append(String.valueOf(accountUser.getEmailAddress()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("firstName")) {
			sb.append("'");
			sb.append(String.valueOf(accountUser.getFirstName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("lastName")) {
			sb.append("'");
			sb.append(String.valueOf(accountUser.getLastName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("middleName")) {
			sb.append("'");
			sb.append(String.valueOf(accountUser.getMiddleName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("prefix")) {
			sb.append("'");
			sb.append(String.valueOf(accountUser.getPrefix()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("screenName")) {
			sb.append("'");
			sb.append(String.valueOf(accountUser.getScreenName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("suffix")) {
			sb.append("'");
			sb.append(String.valueOf(accountUser.getSuffix()));
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

	protected AccountUser randomAccountUser() throws Exception {
		return new AccountUser() {
			{
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				firstName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				lastName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				middleName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				prefix = StringUtil.toLowerCase(RandomTestUtil.randomString());
				screenName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				suffix = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected AccountUser randomIrrelevantAccountUser() throws Exception {
		AccountUser randomIrrelevantAccountUser = randomAccountUser();

		return randomIrrelevantAccountUser;
	}

	protected AccountUser randomPatchAccountUser() throws Exception {
		return randomAccountUser();
	}

	protected AccountUserResource accountUserResource;
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
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
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

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAccountUserResourceTestCase.class);

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
	private com.liferay.account.rest.resource.v1_0.AccountUserResource
		_accountUserResource;

}