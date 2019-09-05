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

import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.resource.v1_0.PhoneResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.PhoneSerDes;
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
public abstract class BasePhoneResourceTestCase {

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

		_phoneResource.setContextCompany(testCompany);

		PhoneResource.Builder builder = PhoneResource.builder();

		phoneResource = builder.locale(
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

		Phone phone1 = randomPhone();

		String json = objectMapper.writeValueAsString(phone1);

		Phone phone2 = PhoneSerDes.toDTO(json);

		Assert.assertTrue(equals(phone1, phone2));
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

		Phone phone = randomPhone();

		String json1 = objectMapper.writeValueAsString(phone);
		String json2 = PhoneSerDes.toJSON(phone);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Phone phone = randomPhone();

		phone.setExtension(regex);
		phone.setPhoneNumber(regex);
		phone.setPhoneType(regex);

		String json = PhoneSerDes.toJSON(phone);

		Assert.assertFalse(json.contains(regex));

		phone = PhoneSerDes.toDTO(json);

		Assert.assertEquals(regex, phone.getExtension());
		Assert.assertEquals(regex, phone.getPhoneNumber());
		Assert.assertEquals(regex, phone.getPhoneType());
	}

	@Test
	public void testGetOrganizationPhonesPage() throws Exception {
		Page<Phone> page = phoneResource.getOrganizationPhonesPage(
			testGetOrganizationPhonesPage_getOrganizationId());

		Assert.assertEquals(0, page.getTotalCount());

		Long organizationId = testGetOrganizationPhonesPage_getOrganizationId();
		Long irrelevantOrganizationId =
			testGetOrganizationPhonesPage_getIrrelevantOrganizationId();

		if ((irrelevantOrganizationId != null)) {
			Phone irrelevantPhone = testGetOrganizationPhonesPage_addPhone(
				irrelevantOrganizationId, randomIrrelevantPhone());

			page = phoneResource.getOrganizationPhonesPage(
				irrelevantOrganizationId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPhone), (List<Phone>)page.getItems());
			assertValid(page);
		}

		Phone phone1 = testGetOrganizationPhonesPage_addPhone(
			organizationId, randomPhone());

		Phone phone2 = testGetOrganizationPhonesPage_addPhone(
			organizationId, randomPhone());

		page = phoneResource.getOrganizationPhonesPage(organizationId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(phone1, phone2), (List<Phone>)page.getItems());
		assertValid(page);
	}

	protected Phone testGetOrganizationPhonesPage_addPhone(
			Long organizationId, Phone phone)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrganizationPhonesPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrganizationPhonesPage_getIrrelevantOrganizationId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetPhone() throws Exception {
		Phone postPhone = testGetPhone_addPhone();

		Phone getPhone = phoneResource.getPhone(postPhone.getId());

		assertEquals(postPhone, getPhone);
		assertValid(getPhone);
	}

	protected Phone testGetPhone_addPhone() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPhone() throws Exception {
		Phone phone = testGraphQLPhone_addPhone();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"phone",
				new HashMap<String, Object>() {
					{
						put("phoneId", phone.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(phone, dataJSONObject.getJSONObject("phone")));
	}

	@Test
	public void testGetUserAccountPhonesPage() throws Exception {
		Page<Phone> page = phoneResource.getUserAccountPhonesPage(
			testGetUserAccountPhonesPage_getUserAccountId());

		Assert.assertEquals(0, page.getTotalCount());

		Long userAccountId = testGetUserAccountPhonesPage_getUserAccountId();
		Long irrelevantUserAccountId =
			testGetUserAccountPhonesPage_getIrrelevantUserAccountId();

		if ((irrelevantUserAccountId != null)) {
			Phone irrelevantPhone = testGetUserAccountPhonesPage_addPhone(
				irrelevantUserAccountId, randomIrrelevantPhone());

			page = phoneResource.getUserAccountPhonesPage(
				irrelevantUserAccountId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPhone), (List<Phone>)page.getItems());
			assertValid(page);
		}

		Phone phone1 = testGetUserAccountPhonesPage_addPhone(
			userAccountId, randomPhone());

		Phone phone2 = testGetUserAccountPhonesPage_addPhone(
			userAccountId, randomPhone());

		page = phoneResource.getUserAccountPhonesPage(userAccountId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(phone1, phone2), (List<Phone>)page.getItems());
		assertValid(page);
	}

	protected Phone testGetUserAccountPhonesPage_addPhone(
			Long userAccountId, Phone phone)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserAccountPhonesPage_getUserAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserAccountPhonesPage_getIrrelevantUserAccountId()
		throws Exception {

		return null;
	}

	protected Phone testGraphQLPhone_addPhone() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Phone phone1, Phone phone2) {
		Assert.assertTrue(
			phone1 + " does not equal " + phone2, equals(phone1, phone2));
	}

	protected void assertEquals(List<Phone> phones1, List<Phone> phones2) {
		Assert.assertEquals(phones1.size(), phones2.size());

		for (int i = 0; i < phones1.size(); i++) {
			Phone phone1 = phones1.get(i);
			Phone phone2 = phones2.get(i);

			assertEquals(phone1, phone2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Phone> phones1, List<Phone> phones2) {

		Assert.assertEquals(phones1.size(), phones2.size());

		for (Phone phone1 : phones1) {
			boolean contains = false;

			for (Phone phone2 : phones2) {
				if (equals(phone1, phone2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				phones2 + " does not contain " + phone1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Phone> phones, JSONArray jsonArray) {

		for (Phone phone : phones) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(phone, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + phone, contains);
		}
	}

	protected void assertValid(Phone phone) {
		boolean valid = true;

		if (phone.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("extension", additionalAssertFieldName)) {
				if (phone.getExtension() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (phone.getPhoneNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("phoneType", additionalAssertFieldName)) {
				if (phone.getPhoneType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (phone.getPrimary() == null) {
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

	protected void assertValid(Page<Phone> page) {
		boolean valid = false;

		java.util.Collection<Phone> phones = page.getItems();

		int size = phones.size();

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

	protected boolean equals(Phone phone1, Phone phone2) {
		if (phone1 == phone2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("extension", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						phone1.getExtension(), phone2.getExtension())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(phone1.getId(), phone2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						phone1.getPhoneNumber(), phone2.getPhoneNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("phoneType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						phone1.getPhoneType(), phone2.getPhoneType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						phone1.getPrimary(), phone2.getPrimary())) {

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

	protected boolean equalsJSONObject(Phone phone, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("extension", fieldName)) {
				if (!Objects.deepEquals(
						phone.getExtension(),
						jsonObject.getString("extension"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						phone.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", fieldName)) {
				if (!Objects.deepEquals(
						phone.getPhoneNumber(),
						jsonObject.getString("phoneNumber"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("phoneType", fieldName)) {
				if (!Objects.deepEquals(
						phone.getPhoneType(),
						jsonObject.getString("phoneType"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primary", fieldName)) {
				if (!Objects.deepEquals(
						phone.getPrimary(), jsonObject.getBoolean("primary"))) {

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

		if (!(_phoneResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_phoneResource;

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
		EntityField entityField, String operator, Phone phone) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("extension")) {
			sb.append("'");
			sb.append(String.valueOf(phone.getExtension()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("phoneNumber")) {
			sb.append("'");
			sb.append(String.valueOf(phone.getPhoneNumber()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("phoneType")) {
			sb.append("'");
			sb.append(String.valueOf(phone.getPhoneType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("primary")) {
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

	protected Phone randomPhone() throws Exception {
		return new Phone() {
			{
				extension = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				phoneNumber = RandomTestUtil.randomString();
				phoneType = RandomTestUtil.randomString();
				primary = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected Phone randomIrrelevantPhone() throws Exception {
		Phone randomIrrelevantPhone = randomPhone();

		return randomIrrelevantPhone;
	}

	protected Phone randomPatchPhone() throws Exception {
		return randomPhone();
	}

	protected PhoneResource phoneResource;
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
		BasePhoneResourceTestCase.class);

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
	private com.liferay.headless.admin.user.resource.v1_0.PhoneResource
		_phoneResource;

}