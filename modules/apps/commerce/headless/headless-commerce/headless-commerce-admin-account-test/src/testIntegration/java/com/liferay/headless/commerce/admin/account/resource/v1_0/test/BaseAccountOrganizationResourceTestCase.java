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

package com.liferay.headless.commerce.admin.account.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.account.client.dto.v1_0.AccountOrganization;
import com.liferay.headless.commerce.admin.account.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.account.client.pagination.Page;
import com.liferay.headless.commerce.admin.account.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.account.client.resource.v1_0.AccountOrganizationResource;
import com.liferay.headless.commerce.admin.account.client.serdes.v1_0.AccountOrganizationSerDes;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
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
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public abstract class BaseAccountOrganizationResourceTestCase {

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

		_accountOrganizationResource.setContextCompany(testCompany);

		AccountOrganizationResource.Builder builder =
			AccountOrganizationResource.builder();

		accountOrganizationResource = builder.authentication(
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

		AccountOrganization accountOrganization1 = randomAccountOrganization();

		String json = objectMapper.writeValueAsString(accountOrganization1);

		AccountOrganization accountOrganization2 =
			AccountOrganizationSerDes.toDTO(json);

		Assert.assertTrue(equals(accountOrganization1, accountOrganization2));
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

		AccountOrganization accountOrganization = randomAccountOrganization();

		String json1 = objectMapper.writeValueAsString(accountOrganization);
		String json2 = AccountOrganizationSerDes.toJSON(accountOrganization);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AccountOrganization accountOrganization = randomAccountOrganization();

		accountOrganization.setName(regex);
		accountOrganization.setOrganizationExternalReferenceCode(regex);
		accountOrganization.setTreePath(regex);

		String json = AccountOrganizationSerDes.toJSON(accountOrganization);

		Assert.assertFalse(json.contains(regex));

		accountOrganization = AccountOrganizationSerDes.toDTO(json);

		Assert.assertEquals(regex, accountOrganization.getName());
		Assert.assertEquals(
			regex, accountOrganization.getOrganizationExternalReferenceCode());
		Assert.assertEquals(regex, accountOrganization.getTreePath());
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountOrganizationsPage()
		throws Exception {

		Page<AccountOrganization> page =
			accountOrganizationResource.
				getAccountByExternalReferenceCodeAccountOrganizationsPage(
					testGetAccountByExternalReferenceCodeAccountOrganizationsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			AccountOrganization irrelevantAccountOrganization =
				testGetAccountByExternalReferenceCodeAccountOrganizationsPage_addAccountOrganization(
					irrelevantExternalReferenceCode,
					randomIrrelevantAccountOrganization());

			page =
				accountOrganizationResource.
					getAccountByExternalReferenceCodeAccountOrganizationsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountOrganization),
				(List<AccountOrganization>)page.getItems());
			assertValid(page);
		}

		AccountOrganization accountOrganization1 =
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_addAccountOrganization(
				externalReferenceCode, randomAccountOrganization());

		AccountOrganization accountOrganization2 =
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_addAccountOrganization(
				externalReferenceCode, randomAccountOrganization());

		page =
			accountOrganizationResource.
				getAccountByExternalReferenceCodeAccountOrganizationsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountOrganization1, accountOrganization2),
			(List<AccountOrganization>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountOrganizationsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_getExternalReferenceCode();

		AccountOrganization accountOrganization1 =
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_addAccountOrganization(
				externalReferenceCode, randomAccountOrganization());

		AccountOrganization accountOrganization2 =
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_addAccountOrganization(
				externalReferenceCode, randomAccountOrganization());

		AccountOrganization accountOrganization3 =
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_addAccountOrganization(
				externalReferenceCode, randomAccountOrganization());

		Page<AccountOrganization> page1 =
			accountOrganizationResource.
				getAccountByExternalReferenceCodeAccountOrganizationsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<AccountOrganization> accountOrganizations1 =
			(List<AccountOrganization>)page1.getItems();

		Assert.assertEquals(
			accountOrganizations1.toString(), 2, accountOrganizations1.size());

		Page<AccountOrganization> page2 =
			accountOrganizationResource.
				getAccountByExternalReferenceCodeAccountOrganizationsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountOrganization> accountOrganizations2 =
			(List<AccountOrganization>)page2.getItems();

		Assert.assertEquals(
			accountOrganizations2.toString(), 1, accountOrganizations2.size());

		Page<AccountOrganization> page3 =
			accountOrganizationResource.
				getAccountByExternalReferenceCodeAccountOrganizationsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				accountOrganization1, accountOrganization2,
				accountOrganization3),
			(List<AccountOrganization>)page3.getItems());
	}

	protected AccountOrganization
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_addAccountOrganization(
				String externalReferenceCode,
				AccountOrganization accountOrganization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeAccountOrganizationsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountByExternalReferenceCodeAccountOrganization()
		throws Exception {

		AccountOrganization randomAccountOrganization =
			randomAccountOrganization();

		AccountOrganization postAccountOrganization =
			testPostAccountByExternalReferenceCodeAccountOrganization_addAccountOrganization(
				randomAccountOrganization);

		assertEquals(randomAccountOrganization, postAccountOrganization);
		assertValid(postAccountOrganization);
	}

	protected AccountOrganization
			testPostAccountByExternalReferenceCodeAccountOrganization_addAccountOrganization(
				AccountOrganization accountOrganization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountByExternalReferenceCodeAccountOrganization()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountOrganization()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeAccountOrganization()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeAccountOrganizationNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testGetAccountIdAccountOrganizationsPage() throws Exception {
		Page<AccountOrganization> page =
			accountOrganizationResource.getAccountIdAccountOrganizationsPage(
				testGetAccountIdAccountOrganizationsPage_getId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetAccountIdAccountOrganizationsPage_getId();
		Long irrelevantId =
			testGetAccountIdAccountOrganizationsPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			AccountOrganization irrelevantAccountOrganization =
				testGetAccountIdAccountOrganizationsPage_addAccountOrganization(
					irrelevantId, randomIrrelevantAccountOrganization());

			page =
				accountOrganizationResource.
					getAccountIdAccountOrganizationsPage(
						irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountOrganization),
				(List<AccountOrganization>)page.getItems());
			assertValid(page);
		}

		AccountOrganization accountOrganization1 =
			testGetAccountIdAccountOrganizationsPage_addAccountOrganization(
				id, randomAccountOrganization());

		AccountOrganization accountOrganization2 =
			testGetAccountIdAccountOrganizationsPage_addAccountOrganization(
				id, randomAccountOrganization());

		page = accountOrganizationResource.getAccountIdAccountOrganizationsPage(
			id, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountOrganization1, accountOrganization2),
			(List<AccountOrganization>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountIdAccountOrganizationsPageWithPagination()
		throws Exception {

		Long id = testGetAccountIdAccountOrganizationsPage_getId();

		AccountOrganization accountOrganization1 =
			testGetAccountIdAccountOrganizationsPage_addAccountOrganization(
				id, randomAccountOrganization());

		AccountOrganization accountOrganization2 =
			testGetAccountIdAccountOrganizationsPage_addAccountOrganization(
				id, randomAccountOrganization());

		AccountOrganization accountOrganization3 =
			testGetAccountIdAccountOrganizationsPage_addAccountOrganization(
				id, randomAccountOrganization());

		Page<AccountOrganization> page1 =
			accountOrganizationResource.getAccountIdAccountOrganizationsPage(
				id, Pagination.of(1, 2));

		List<AccountOrganization> accountOrganizations1 =
			(List<AccountOrganization>)page1.getItems();

		Assert.assertEquals(
			accountOrganizations1.toString(), 2, accountOrganizations1.size());

		Page<AccountOrganization> page2 =
			accountOrganizationResource.getAccountIdAccountOrganizationsPage(
				id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountOrganization> accountOrganizations2 =
			(List<AccountOrganization>)page2.getItems();

		Assert.assertEquals(
			accountOrganizations2.toString(), 1, accountOrganizations2.size());

		Page<AccountOrganization> page3 =
			accountOrganizationResource.getAccountIdAccountOrganizationsPage(
				id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				accountOrganization1, accountOrganization2,
				accountOrganization3),
			(List<AccountOrganization>)page3.getItems());
	}

	protected AccountOrganization
			testGetAccountIdAccountOrganizationsPage_addAccountOrganization(
				Long id, AccountOrganization accountOrganization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountIdAccountOrganizationsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountIdAccountOrganizationsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountIdAccountOrganization() throws Exception {
		AccountOrganization randomAccountOrganization =
			randomAccountOrganization();

		AccountOrganization postAccountOrganization =
			testPostAccountIdAccountOrganization_addAccountOrganization(
				randomAccountOrganization);

		assertEquals(randomAccountOrganization, postAccountOrganization);
		assertValid(postAccountOrganization);
	}

	protected AccountOrganization
			testPostAccountIdAccountOrganization_addAccountOrganization(
				AccountOrganization accountOrganization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountIdAccountOrganization() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetAccountIdAccountOrganization() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetAccountIdAccountOrganization() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetAccountIdAccountOrganizationNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		AccountOrganization accountOrganization1,
		AccountOrganization accountOrganization2) {

		Assert.assertTrue(
			accountOrganization1 + " does not equal " + accountOrganization2,
			equals(accountOrganization1, accountOrganization2));
	}

	protected void assertEquals(
		List<AccountOrganization> accountOrganizations1,
		List<AccountOrganization> accountOrganizations2) {

		Assert.assertEquals(
			accountOrganizations1.size(), accountOrganizations2.size());

		for (int i = 0; i < accountOrganizations1.size(); i++) {
			AccountOrganization accountOrganization1 =
				accountOrganizations1.get(i);
			AccountOrganization accountOrganization2 =
				accountOrganizations2.get(i);

			assertEquals(accountOrganization1, accountOrganization2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AccountOrganization> accountOrganizations1,
		List<AccountOrganization> accountOrganizations2) {

		Assert.assertEquals(
			accountOrganizations1.size(), accountOrganizations2.size());

		for (AccountOrganization accountOrganization1 : accountOrganizations1) {
			boolean contains = false;

			for (AccountOrganization accountOrganization2 :
					accountOrganizations2) {

				if (equals(accountOrganization1, accountOrganization2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accountOrganizations2 + " does not contain " +
					accountOrganization1,
				contains);
		}
	}

	protected void assertValid(AccountOrganization accountOrganization)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (accountOrganization.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (accountOrganization.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationExternalReferenceCode",
					additionalAssertFieldName)) {

				if (accountOrganization.
						getOrganizationExternalReferenceCode() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("organizationId", additionalAssertFieldName)) {
				if (accountOrganization.getOrganizationId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("treePath", additionalAssertFieldName)) {
				if (accountOrganization.getTreePath() == null) {
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

	protected void assertValid(Page<AccountOrganization> page) {
		boolean valid = false;

		java.util.Collection<AccountOrganization> accountOrganizations =
			page.getItems();

		int size = accountOrganizations.size();

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
					com.liferay.headless.commerce.admin.account.dto.v1_0.
						AccountOrganization.class)) {

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
		AccountOrganization accountOrganization1,
		AccountOrganization accountOrganization2) {

		if (accountOrganization1 == accountOrganization2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountOrganization1.getAccountId(),
						accountOrganization2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountOrganization1.getName(),
						accountOrganization2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						accountOrganization1.
							getOrganizationExternalReferenceCode(),
						accountOrganization2.
							getOrganizationExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("organizationId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountOrganization1.getOrganizationId(),
						accountOrganization2.getOrganizationId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("treePath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountOrganization1.getTreePath(),
						accountOrganization2.getTreePath())) {

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

		if (!(_accountOrganizationResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountOrganizationResource;

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
		AccountOrganization accountOrganization) {

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

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(accountOrganization.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("organizationExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					accountOrganization.
						getOrganizationExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("organizationId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("treePath")) {
			sb.append("'");
			sb.append(String.valueOf(accountOrganization.getTreePath()));
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

	protected AccountOrganization randomAccountOrganization() throws Exception {
		return new AccountOrganization() {
			{
				accountId = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				organizationExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				organizationId = RandomTestUtil.randomLong();
				treePath = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected AccountOrganization randomIrrelevantAccountOrganization()
		throws Exception {

		AccountOrganization randomIrrelevantAccountOrganization =
			randomAccountOrganization();

		return randomIrrelevantAccountOrganization;
	}

	protected AccountOrganization randomPatchAccountOrganization()
		throws Exception {

		return randomAccountOrganization();
	}

	protected AccountOrganizationResource accountOrganizationResource;
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
		BaseAccountOrganizationResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.account.resource.v1_0.
		AccountOrganizationResource _accountOrganizationResource;

}