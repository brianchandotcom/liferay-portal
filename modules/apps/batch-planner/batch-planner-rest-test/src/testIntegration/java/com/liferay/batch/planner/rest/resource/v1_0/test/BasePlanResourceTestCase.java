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

package com.liferay.batch.planner.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.batch.planner.rest.client.dto.v1_0.Log;
import com.liferay.batch.planner.rest.client.dto.v1_0.Plan;
import com.liferay.batch.planner.rest.client.http.HttpInvoker;
import com.liferay.batch.planner.rest.client.pagination.Page;
import com.liferay.batch.planner.rest.client.pagination.Pagination;
import com.liferay.batch.planner.rest.client.resource.v1_0.PlanResource;
import com.liferay.batch.planner.rest.client.serdes.v1_0.PlanSerDes;
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
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public abstract class BasePlanResourceTestCase {

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

		_planResource.setContextCompany(testCompany);

		PlanResource.Builder builder = PlanResource.builder();

		planResource = builder.authentication(
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

		Plan plan1 = randomPlan();

		String json = objectMapper.writeValueAsString(plan1);

		Plan plan2 = PlanSerDes.toDTO(json);

		Assert.assertTrue(equals(plan1, plan2));
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

		Plan plan = randomPlan();

		String json1 = objectMapper.writeValueAsString(plan);
		String json2 = PlanSerDes.toJSON(plan);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Plan plan = randomPlan();

		plan.setExternalType(regex);
		plan.setExternalURL(regex);
		plan.setInternalClassName(regex);
		plan.setName(regex);

		String json = PlanSerDes.toJSON(plan);

		Assert.assertFalse(json.contains(regex));

		plan = PlanSerDes.toDTO(json);

		Assert.assertEquals(regex, plan.getExternalType());
		Assert.assertEquals(regex, plan.getExternalURL());
		Assert.assertEquals(regex, plan.getInternalClassName());
		Assert.assertEquals(regex, plan.getName());
	}

	@Test
	public void testGetPlansPage() throws Exception {
		Page<Plan> page = planResource.getPlansPage(Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		Plan plan1 = testGetPlansPage_addPlan(randomPlan());

		Plan plan2 = testGetPlansPage_addPlan(randomPlan());

		page = planResource.getPlansPage(Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(plan1, (List<Plan>)page.getItems());
		assertContains(plan2, (List<Plan>)page.getItems());
		assertValid(page);

		planResource.deletePlan(plan1.getId());

		planResource.deletePlan(plan2.getId());
	}

	@Test
	public void testGetPlansPageWithPagination() throws Exception {
		Page<Plan> totalPage = planResource.getPlansPage(null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Plan plan1 = testGetPlansPage_addPlan(randomPlan());

		Plan plan2 = testGetPlansPage_addPlan(randomPlan());

		Plan plan3 = testGetPlansPage_addPlan(randomPlan());

		Page<Plan> page1 = planResource.getPlansPage(
			Pagination.of(1, totalCount + 2));

		List<Plan> plans1 = (List<Plan>)page1.getItems();

		Assert.assertEquals(plans1.toString(), totalCount + 2, plans1.size());

		Page<Plan> page2 = planResource.getPlansPage(
			Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Plan> plans2 = (List<Plan>)page2.getItems();

		Assert.assertEquals(plans2.toString(), 1, plans2.size());

		Page<Plan> page3 = planResource.getPlansPage(
			Pagination.of(1, totalCount + 3));

		assertContains(plan1, (List<Plan>)page3.getItems());
		assertContains(plan2, (List<Plan>)page3.getItems());
		assertContains(plan3, (List<Plan>)page3.getItems());
	}

	protected Plan testGetPlansPage_addPlan(Plan plan) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostPlan() throws Exception {
		Plan randomPlan = randomPlan();

		Plan postPlan = testPostPlan_addPlan(randomPlan);

		assertEquals(randomPlan, postPlan);
		assertValid(postPlan);
	}

	protected Plan testPostPlan_addPlan(Plan plan) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeletePlan() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Plan plan = testDeletePlan_addPlan();

		assertHttpResponseStatusCode(
			204, planResource.deletePlanHttpResponse(plan.getId()));

		assertHttpResponseStatusCode(
			404, planResource.getPlanHttpResponse(plan.getId()));

		assertHttpResponseStatusCode(404, planResource.getPlanHttpResponse(0L));
	}

	protected Plan testDeletePlan_addPlan() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPlan() throws Exception {
		Plan postPlan = testGetPlan_addPlan();

		Plan getPlan = planResource.getPlan(postPlan.getId());

		assertEquals(postPlan, getPlan);
		assertValid(getPlan);
	}

	protected Plan testGetPlan_addPlan() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPatchPlan() throws Exception {
		Plan postPlan = testPatchPlan_addPlan();

		Plan randomPatchPlan = randomPatchPlan();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Plan patchPlan = planResource.patchPlan(
			postPlan.getId(), randomPatchPlan);

		Plan expectedPatchPlan = postPlan.clone();

		_beanUtilsBean.copyProperties(expectedPatchPlan, randomPatchPlan);

		Plan getPlan = planResource.getPlan(patchPlan.getId());

		assertEquals(expectedPatchPlan, getPlan);
		assertValid(getPlan);
	}

	protected Plan testPatchPlan_addPlan() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Plan testGraphQLPlan_addPlan() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(Plan plan, List<Plan> plans) {
		boolean contains = false;

		for (Plan item : plans) {
			if (equals(plan, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(plans + " does not contain " + plan, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Plan plan1, Plan plan2) {
		Assert.assertTrue(
			plan1 + " does not equal " + plan2, equals(plan1, plan2));
	}

	protected void assertEquals(List<Plan> plans1, List<Plan> plans2) {
		Assert.assertEquals(plans1.size(), plans2.size());

		for (int i = 0; i < plans1.size(); i++) {
			Plan plan1 = plans1.get(i);
			Plan plan2 = plans2.get(i);

			assertEquals(plan1, plan2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Plan> plans1, List<Plan> plans2) {

		Assert.assertEquals(plans1.size(), plans2.size());

		for (Plan plan1 : plans1) {
			boolean contains = false;

			for (Plan plan2 : plans2) {
				if (equals(plan1, plan2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(plans2 + " does not contain " + plan1, contains);
		}
	}

	protected void assertValid(Plan plan) throws Exception {
		boolean valid = true;

		if (plan.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (plan.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("export", additionalAssertFieldName)) {
				if (plan.getExport() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("externalType", additionalAssertFieldName)) {
				if (plan.getExternalType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("externalURL", additionalAssertFieldName)) {
				if (plan.getExternalURL() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"internalClassName", additionalAssertFieldName)) {

				if (plan.getInternalClassName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("mappings", additionalAssertFieldName)) {
				if (plan.getMappings() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (plan.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("policies", additionalAssertFieldName)) {
				if (plan.getPolicies() == null) {
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

	protected void assertValid(Page<Plan> page) {
		boolean valid = false;

		java.util.Collection<Plan> plans = page.getItems();

		int size = plans.size();

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
				getDeclaredFields(
					com.liferay.batch.planner.rest.dto.v1_0.Plan.class)) {

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

	protected boolean equals(Plan plan1, Plan plan2) {
		if (plan1 == plan2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(plan1.getActive(), plan2.getActive())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("export", additionalAssertFieldName)) {
				if (!Objects.deepEquals(plan1.getExport(), plan2.getExport())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("externalType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						plan1.getExternalType(), plan2.getExternalType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("externalURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						plan1.getExternalURL(), plan2.getExternalURL())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(plan1.getId(), plan2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals(
					"internalClassName", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						plan1.getInternalClassName(),
						plan2.getInternalClassName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("mappings", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						plan1.getMappings(), plan2.getMappings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(plan1.getName(), plan2.getName())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("policies", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						plan1.getPolicies(), plan2.getPolicies())) {

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

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_planResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_planResource;

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
		EntityField entityField, String operator, Plan plan) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("export")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalType")) {
			sb.append("'");
			sb.append(String.valueOf(plan.getExternalType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("externalURL")) {
			sb.append("'");
			sb.append(String.valueOf(plan.getExternalURL()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("internalClassName")) {
			sb.append("'");
			sb.append(String.valueOf(plan.getInternalClassName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("mappings")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(plan.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("policies")) {
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

	protected Plan randomPlan() throws Exception {
		return new Plan() {
			{
				active = RandomTestUtil.randomBoolean();
				export = RandomTestUtil.randomBoolean();
				externalType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				internalClassName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Plan randomIrrelevantPlan() throws Exception {
		Plan randomIrrelevantPlan = randomPlan();

		return randomIrrelevantPlan;
	}

	protected Plan randomPatchPlan() throws Exception {
		return randomPlan();
	}

	protected PlanResource planResource;
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
		LogFactoryUtil.getLog(BasePlanResourceTestCase.class);

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
	private com.liferay.batch.planner.rest.resource.v1_0.PlanResource
		_planResource;

}