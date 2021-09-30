/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.machine.learning.client.dto.v1_0.AccountCategoryForecast;
import com.liferay.headless.commerce.machine.learning.client.http.HttpInvoker;
import com.liferay.headless.commerce.machine.learning.client.pagination.Page;
import com.liferay.headless.commerce.machine.learning.client.pagination.Pagination;
import com.liferay.headless.commerce.machine.learning.client.resource.v1_0.AccountCategoryForecastResource;
import com.liferay.headless.commerce.machine.learning.client.serdes.v1_0.AccountCategoryForecastSerDes;
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
import com.liferay.portal.kernel.util.GetterUtil;
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
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public abstract class BaseAccountCategoryForecastResourceTestCase {

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

		_accountCategoryForecastResource.setContextCompany(testCompany);

		AccountCategoryForecastResource.Builder builder =
			AccountCategoryForecastResource.builder();

		accountCategoryForecastResource = builder.authentication(
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

		AccountCategoryForecast accountCategoryForecast1 =
			randomAccountCategoryForecast();

		String json = objectMapper.writeValueAsString(accountCategoryForecast1);

		AccountCategoryForecast accountCategoryForecast2 =
			AccountCategoryForecastSerDes.toDTO(json);

		Assert.assertTrue(
			equals(accountCategoryForecast1, accountCategoryForecast2));
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

		AccountCategoryForecast accountCategoryForecast =
			randomAccountCategoryForecast();

		String json1 = objectMapper.writeValueAsString(accountCategoryForecast);
		String json2 = AccountCategoryForecastSerDes.toJSON(
			accountCategoryForecast);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AccountCategoryForecast accountCategoryForecast =
			randomAccountCategoryForecast();

		accountCategoryForecast.setCategoryTitle(regex);
		accountCategoryForecast.setUnit(regex);

		String json = AccountCategoryForecastSerDes.toJSON(
			accountCategoryForecast);

		Assert.assertFalse(json.contains(regex));

		accountCategoryForecast = AccountCategoryForecastSerDes.toDTO(json);

		Assert.assertEquals(regex, accountCategoryForecast.getCategoryTitle());
		Assert.assertEquals(regex, accountCategoryForecast.getUnit());
	}

	@Test
	public void testGetAccountCategoryForecastsByMonthlyRevenuePage()
		throws Exception {

		Page<AccountCategoryForecast> page =
			accountCategoryForecastResource.
				getAccountCategoryForecastsByMonthlyRevenuePage(
					null, null, null, RandomTestUtil.nextDate(), null,
					Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		AccountCategoryForecast accountCategoryForecast1 =
			testGetAccountCategoryForecastsByMonthlyRevenuePage_addAccountCategoryForecast(
				randomAccountCategoryForecast());

		AccountCategoryForecast accountCategoryForecast2 =
			testGetAccountCategoryForecastsByMonthlyRevenuePage_addAccountCategoryForecast(
				randomAccountCategoryForecast());

		page =
			accountCategoryForecastResource.
				getAccountCategoryForecastsByMonthlyRevenuePage(
					null, null, null, null, null, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			accountCategoryForecast1,
			(List<AccountCategoryForecast>)page.getItems());
		assertContains(
			accountCategoryForecast2,
			(List<AccountCategoryForecast>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountCategoryForecastsByMonthlyRevenuePageWithPagination()
		throws Exception {

		Page<AccountCategoryForecast> totalPage =
			accountCategoryForecastResource.
				getAccountCategoryForecastsByMonthlyRevenuePage(
					null, null, null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		AccountCategoryForecast accountCategoryForecast1 =
			testGetAccountCategoryForecastsByMonthlyRevenuePage_addAccountCategoryForecast(
				randomAccountCategoryForecast());

		AccountCategoryForecast accountCategoryForecast2 =
			testGetAccountCategoryForecastsByMonthlyRevenuePage_addAccountCategoryForecast(
				randomAccountCategoryForecast());

		AccountCategoryForecast accountCategoryForecast3 =
			testGetAccountCategoryForecastsByMonthlyRevenuePage_addAccountCategoryForecast(
				randomAccountCategoryForecast());

		Page<AccountCategoryForecast> page1 =
			accountCategoryForecastResource.
				getAccountCategoryForecastsByMonthlyRevenuePage(
					null, null, null, null, null,
					Pagination.of(1, totalCount + 2));

		List<AccountCategoryForecast> accountCategoryForecasts1 =
			(List<AccountCategoryForecast>)page1.getItems();

		Assert.assertEquals(
			accountCategoryForecasts1.toString(), totalCount + 2,
			accountCategoryForecasts1.size());

		Page<AccountCategoryForecast> page2 =
			accountCategoryForecastResource.
				getAccountCategoryForecastsByMonthlyRevenuePage(
					null, null, null, null, null,
					Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<AccountCategoryForecast> accountCategoryForecasts2 =
			(List<AccountCategoryForecast>)page2.getItems();

		Assert.assertEquals(
			accountCategoryForecasts2.toString(), 1,
			accountCategoryForecasts2.size());

		Page<AccountCategoryForecast> page3 =
			accountCategoryForecastResource.
				getAccountCategoryForecastsByMonthlyRevenuePage(
					null, null, null, null, null,
					Pagination.of(1, totalCount + 3));

		assertContains(
			accountCategoryForecast1,
			(List<AccountCategoryForecast>)page3.getItems());
		assertContains(
			accountCategoryForecast2,
			(List<AccountCategoryForecast>)page3.getItems());
		assertContains(
			accountCategoryForecast3,
			(List<AccountCategoryForecast>)page3.getItems());
	}

	protected AccountCategoryForecast
			testGetAccountCategoryForecastsByMonthlyRevenuePage_addAccountCategoryForecast(
				AccountCategoryForecast accountCategoryForecast)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		AccountCategoryForecast accountCategoryForecast,
		List<AccountCategoryForecast> accountCategoryForecasts) {

		boolean contains = false;

		for (AccountCategoryForecast item : accountCategoryForecasts) {
			if (equals(accountCategoryForecast, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			accountCategoryForecasts + " does not contain " +
				accountCategoryForecast,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		AccountCategoryForecast accountCategoryForecast1,
		AccountCategoryForecast accountCategoryForecast2) {

		Assert.assertTrue(
			accountCategoryForecast1 + " does not equal " +
				accountCategoryForecast2,
			equals(accountCategoryForecast1, accountCategoryForecast2));
	}

	protected void assertEquals(
		List<AccountCategoryForecast> accountCategoryForecasts1,
		List<AccountCategoryForecast> accountCategoryForecasts2) {

		Assert.assertEquals(
			accountCategoryForecasts1.size(), accountCategoryForecasts2.size());

		for (int i = 0; i < accountCategoryForecasts1.size(); i++) {
			AccountCategoryForecast accountCategoryForecast1 =
				accountCategoryForecasts1.get(i);
			AccountCategoryForecast accountCategoryForecast2 =
				accountCategoryForecasts2.get(i);

			assertEquals(accountCategoryForecast1, accountCategoryForecast2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AccountCategoryForecast> accountCategoryForecasts1,
		List<AccountCategoryForecast> accountCategoryForecasts2) {

		Assert.assertEquals(
			accountCategoryForecasts1.size(), accountCategoryForecasts2.size());

		for (AccountCategoryForecast accountCategoryForecast1 :
				accountCategoryForecasts1) {

			boolean contains = false;

			for (AccountCategoryForecast accountCategoryForecast2 :
					accountCategoryForecasts2) {

				if (equals(
						accountCategoryForecast1, accountCategoryForecast2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accountCategoryForecasts2 + " does not contain " +
					accountCategoryForecast1,
				contains);
		}
	}

	protected void assertValid(AccountCategoryForecast accountCategoryForecast)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (accountCategoryForecast.getAccount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actual", additionalAssertFieldName)) {
				if (accountCategoryForecast.getActual() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("category", additionalAssertFieldName)) {
				if (accountCategoryForecast.getCategory() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("categoryTitle", additionalAssertFieldName)) {
				if (accountCategoryForecast.getCategoryTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("forecast", additionalAssertFieldName)) {
				if (accountCategoryForecast.getForecast() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"forecastLowerBound", additionalAssertFieldName)) {

				if (accountCategoryForecast.getForecastLowerBound() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"forecastUpperBound", additionalAssertFieldName)) {

				if (accountCategoryForecast.getForecastUpperBound() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("timestamp", additionalAssertFieldName)) {
				if (accountCategoryForecast.getTimestamp() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("unit", additionalAssertFieldName)) {
				if (accountCategoryForecast.getUnit() == null) {
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

	protected void assertValid(Page<AccountCategoryForecast> page) {
		boolean valid = false;

		java.util.Collection<AccountCategoryForecast> accountCategoryForecasts =
			page.getItems();

		int size = accountCategoryForecasts.size();

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
					com.liferay.headless.commerce.machine.learning.dto.v1_0.
						AccountCategoryForecast.class)) {

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
		AccountCategoryForecast accountCategoryForecast1,
		AccountCategoryForecast accountCategoryForecast2) {

		if (accountCategoryForecast1 == accountCategoryForecast2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountCategoryForecast1.getAccount(),
						accountCategoryForecast2.getAccount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actual", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountCategoryForecast1.getActual(),
						accountCategoryForecast2.getActual())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("category", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountCategoryForecast1.getCategory(),
						accountCategoryForecast2.getCategory())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("categoryTitle", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountCategoryForecast1.getCategoryTitle(),
						accountCategoryForecast2.getCategoryTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("forecast", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountCategoryForecast1.getForecast(),
						accountCategoryForecast2.getForecast())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"forecastLowerBound", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						accountCategoryForecast1.getForecastLowerBound(),
						accountCategoryForecast2.getForecastLowerBound())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"forecastUpperBound", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						accountCategoryForecast1.getForecastUpperBound(),
						accountCategoryForecast2.getForecastUpperBound())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("timestamp", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountCategoryForecast1.getTimestamp(),
						accountCategoryForecast2.getTimestamp())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("unit", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountCategoryForecast1.getUnit(),
						accountCategoryForecast2.getUnit())) {

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

		if (!(_accountCategoryForecastResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountCategoryForecastResource;

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
		AccountCategoryForecast accountCategoryForecast) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("account")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("actual")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("category")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("categoryTitle")) {
			sb.append("'");
			sb.append(
				String.valueOf(accountCategoryForecast.getCategoryTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("forecast")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("forecastLowerBound")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("forecastUpperBound")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("timestamp")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							accountCategoryForecast.getTimestamp(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							accountCategoryForecast.getTimestamp(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(accountCategoryForecast.getTimestamp()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("unit")) {
			sb.append("'");
			sb.append(String.valueOf(accountCategoryForecast.getUnit()));
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

	protected AccountCategoryForecast randomAccountCategoryForecast()
		throws Exception {

		return new AccountCategoryForecast() {
			{
				account = RandomTestUtil.randomLong();
				category = RandomTestUtil.randomLong();
				categoryTitle = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				timestamp = RandomTestUtil.nextDate();
				unit = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected AccountCategoryForecast randomIrrelevantAccountCategoryForecast()
		throws Exception {

		AccountCategoryForecast randomIrrelevantAccountCategoryForecast =
			randomAccountCategoryForecast();

		return randomIrrelevantAccountCategoryForecast;
	}

	protected AccountCategoryForecast randomPatchAccountCategoryForecast()
		throws Exception {

		return randomAccountCategoryForecast();
	}

	protected AccountCategoryForecastResource accountCategoryForecastResource;
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
			BaseAccountCategoryForecastResourceTestCase.class);

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
	private com.liferay.headless.commerce.machine.learning.resource.v1_0.
		AccountCategoryForecastResource _accountCategoryForecastResource;

}