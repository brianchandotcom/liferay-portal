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

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductSubscriptionConfiguration;
import com.liferay.headless.commerce.admin.catalog.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.ProductSubscriptionConfigurationResource;
import com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0.ProductSubscriptionConfigurationSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseProductSubscriptionConfigurationResourceTestCase {

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

		_productSubscriptionConfigurationResource.setContextCompany(
			testCompany);

		ProductSubscriptionConfigurationResource.Builder builder =
			ProductSubscriptionConfigurationResource.builder();

		productSubscriptionConfigurationResource = builder.authentication(
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

		ProductSubscriptionConfiguration productSubscriptionConfiguration1 =
			randomProductSubscriptionConfiguration();

		String json = objectMapper.writeValueAsString(
			productSubscriptionConfiguration1);

		ProductSubscriptionConfiguration productSubscriptionConfiguration2 =
			ProductSubscriptionConfigurationSerDes.toDTO(json);

		Assert.assertTrue(
			equals(
				productSubscriptionConfiguration1,
				productSubscriptionConfiguration2));
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

		ProductSubscriptionConfiguration productSubscriptionConfiguration =
			randomProductSubscriptionConfiguration();

		String json1 = objectMapper.writeValueAsString(
			productSubscriptionConfiguration);
		String json2 = ProductSubscriptionConfigurationSerDes.toJSON(
			productSubscriptionConfiguration);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ProductSubscriptionConfiguration productSubscriptionConfiguration =
			randomProductSubscriptionConfiguration();

		String json = ProductSubscriptionConfigurationSerDes.toJSON(
			productSubscriptionConfiguration);

		Assert.assertFalse(json.contains(regex));

		productSubscriptionConfiguration =
			ProductSubscriptionConfigurationSerDes.toDTO(json);
	}

	@Test
	public void testGetProductByExternalReferenceCodeSubscriptionConfiguration()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeSubscriptionConfiguration()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeSubscriptionConfigurationNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testPatchProductByExternalReferenceCodeSubscriptionConfiguration()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetProductIdSubscriptionConfiguration() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetProductIdSubscriptionConfiguration()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetProductIdSubscriptionConfigurationNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testPatchProductIdSubscriptionConfiguration() throws Exception {
		Assert.assertTrue(false);
	}

	protected void assertContains(
		ProductSubscriptionConfiguration productSubscriptionConfiguration,
		List<ProductSubscriptionConfiguration>
			productSubscriptionConfigurations) {

		boolean contains = false;

		for (ProductSubscriptionConfiguration item :
				productSubscriptionConfigurations) {

			if (equals(productSubscriptionConfiguration, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			productSubscriptionConfigurations + " does not contain " +
				productSubscriptionConfiguration,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ProductSubscriptionConfiguration productSubscriptionConfiguration1,
		ProductSubscriptionConfiguration productSubscriptionConfiguration2) {

		Assert.assertTrue(
			productSubscriptionConfiguration1 + " does not equal " +
				productSubscriptionConfiguration2,
			equals(
				productSubscriptionConfiguration1,
				productSubscriptionConfiguration2));
	}

	protected void assertEquals(
		List<ProductSubscriptionConfiguration>
			productSubscriptionConfigurations1,
		List<ProductSubscriptionConfiguration>
			productSubscriptionConfigurations2) {

		Assert.assertEquals(
			productSubscriptionConfigurations1.size(),
			productSubscriptionConfigurations2.size());

		for (int i = 0; i < productSubscriptionConfigurations1.size(); i++) {
			ProductSubscriptionConfiguration productSubscriptionConfiguration1 =
				productSubscriptionConfigurations1.get(i);
			ProductSubscriptionConfiguration productSubscriptionConfiguration2 =
				productSubscriptionConfigurations2.get(i);

			assertEquals(
				productSubscriptionConfiguration1,
				productSubscriptionConfiguration2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ProductSubscriptionConfiguration>
			productSubscriptionConfigurations1,
		List<ProductSubscriptionConfiguration>
			productSubscriptionConfigurations2) {

		Assert.assertEquals(
			productSubscriptionConfigurations1.size(),
			productSubscriptionConfigurations2.size());

		for (ProductSubscriptionConfiguration
				productSubscriptionConfiguration1 :
					productSubscriptionConfigurations1) {

			boolean contains = false;

			for (ProductSubscriptionConfiguration
					productSubscriptionConfiguration2 :
						productSubscriptionConfigurations2) {

				if (equals(
						productSubscriptionConfiguration1,
						productSubscriptionConfiguration2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				productSubscriptionConfigurations2 + " does not contain " +
					productSubscriptionConfiguration1,
				contains);
		}
	}

	protected void assertValid(
			ProductSubscriptionConfiguration productSubscriptionConfiguration)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("enable", additionalAssertFieldName)) {
				if (productSubscriptionConfiguration.getEnable() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("length", additionalAssertFieldName)) {
				if (productSubscriptionConfiguration.getLength() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfLength", additionalAssertFieldName)) {
				if (productSubscriptionConfiguration.getNumberOfLength() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscriptionType", additionalAssertFieldName)) {
				if (productSubscriptionConfiguration.getSubscriptionType() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"subscriptionTypeSettings", additionalAssertFieldName)) {

				if (productSubscriptionConfiguration.
						getSubscriptionTypeSettings() == null) {

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

	protected void assertValid(Page<ProductSubscriptionConfiguration> page) {
		boolean valid = false;

		java.util.Collection<ProductSubscriptionConfiguration>
			productSubscriptionConfigurations = page.getItems();

		int size = productSubscriptionConfigurations.size();

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
					com.liferay.headless.commerce.admin.catalog.dto.v1_0.
						ProductSubscriptionConfiguration.class)) {

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

	protected boolean equals(
		ProductSubscriptionConfiguration productSubscriptionConfiguration1,
		ProductSubscriptionConfiguration productSubscriptionConfiguration2) {

		if (productSubscriptionConfiguration1 ==
				productSubscriptionConfiguration2) {

			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("enable", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						productSubscriptionConfiguration1.getEnable(),
						productSubscriptionConfiguration2.getEnable())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("length", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						productSubscriptionConfiguration1.getLength(),
						productSubscriptionConfiguration2.getLength())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfLength", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						productSubscriptionConfiguration1.getNumberOfLength(),
						productSubscriptionConfiguration2.
							getNumberOfLength())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscriptionType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						productSubscriptionConfiguration1.getSubscriptionType(),
						productSubscriptionConfiguration2.
							getSubscriptionType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"subscriptionTypeSettings", additionalAssertFieldName)) {

				if (!equals(
						(Map)
							productSubscriptionConfiguration1.
								getSubscriptionTypeSettings(),
						(Map)
							productSubscriptionConfiguration2.
								getSubscriptionTypeSettings())) {

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

		if (!(_productSubscriptionConfigurationResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_productSubscriptionConfigurationResource;

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
		ProductSubscriptionConfiguration productSubscriptionConfiguration) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("enable")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("length")) {
			sb.append(
				String.valueOf(productSubscriptionConfiguration.getLength()));

			return sb.toString();
		}

		if (entityFieldName.equals("numberOfLength")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subscriptionType")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subscriptionTypeSettings")) {
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

	protected ProductSubscriptionConfiguration
			randomProductSubscriptionConfiguration()
		throws Exception {

		return new ProductSubscriptionConfiguration() {
			{
				enable = RandomTestUtil.randomBoolean();
				length = RandomTestUtil.randomInt();
				numberOfLength = RandomTestUtil.randomLong();
			}
		};
	}

	protected ProductSubscriptionConfiguration
			randomIrrelevantProductSubscriptionConfiguration()
		throws Exception {

		ProductSubscriptionConfiguration
			randomIrrelevantProductSubscriptionConfiguration =
				randomProductSubscriptionConfiguration();

		return randomIrrelevantProductSubscriptionConfiguration;
	}

	protected ProductSubscriptionConfiguration
			randomPatchProductSubscriptionConfiguration()
		throws Exception {

		return randomProductSubscriptionConfiguration();
	}

	protected ProductSubscriptionConfigurationResource
		productSubscriptionConfigurationResource;
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
		LogFactoryUtil.getLog(
			BaseProductSubscriptionConfigurationResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.catalog.resource.v1_0.
		ProductSubscriptionConfigurationResource
			_productSubscriptionConfigurationResource;

}