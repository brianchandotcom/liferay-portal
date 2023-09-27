/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Specification;
import com.liferay.headless.commerce.admin.catalog.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.SpecificationResource;
import com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0.SpecificationSerDes;
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
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseSpecificationResourceTestCase {

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

		_specificationResource.setContextCompany(testCompany);

		SpecificationResource.Builder builder = SpecificationResource.builder();

		specificationResource = builder.authentication(
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

		Specification specification1 = randomSpecification();

		String json = objectMapper.writeValueAsString(specification1);

		Specification specification2 = SpecificationSerDes.toDTO(json);

		Assert.assertTrue(equals(specification1, specification2));
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

		Specification specification = randomSpecification();

		String json1 = objectMapper.writeValueAsString(specification);
		String json2 = SpecificationSerDes.toJSON(specification);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Specification specification = randomSpecification();

		specification.setKey(regex);

		String json = SpecificationSerDes.toJSON(specification);

		Assert.assertFalse(json.contains(regex));

		specification = SpecificationSerDes.toDTO(json);

		Assert.assertEquals(regex, specification.getKey());
	}

	@Test
	public void testGetSpecificationsPage() throws Exception {
		Page<Specification> page = specificationResource.getSpecificationsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Specification specification1 =
			testGetSpecificationsPage_addSpecification(randomSpecification());

		Specification specification2 =
			testGetSpecificationsPage_addSpecification(randomSpecification());

		page = specificationResource.getSpecificationsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(specification1, (List<Specification>)page.getItems());
		assertContains(specification2, (List<Specification>)page.getItems());
		assertValid(page, testGetSpecificationsPage_getExpectedActions());

		specificationResource.deleteSpecification(specification1.getId());

		specificationResource.deleteSpecification(specification2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetSpecificationsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetSpecificationsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Specification specification1 = randomSpecification();

		specification1 = testGetSpecificationsPage_addSpecification(
			specification1);

		for (EntityField entityField : entityFields) {
			Page<Specification> page =
				specificationResource.getSpecificationsPage(
					null,
					getFilterString(entityField, "between", specification1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(specification1),
				(List<Specification>)page.getItems());
		}
	}

	@Test
	public void testGetSpecificationsPageWithFilterDoubleEquals()
		throws Exception {

		testGetSpecificationsPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetSpecificationsPageWithFilterStringContains()
		throws Exception {

		testGetSpecificationsPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetSpecificationsPageWithFilterStringEquals()
		throws Exception {

		testGetSpecificationsPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetSpecificationsPageWithFilterStringStartsWith()
		throws Exception {

		testGetSpecificationsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetSpecificationsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Specification specification1 =
			testGetSpecificationsPage_addSpecification(randomSpecification());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Specification specification2 =
			testGetSpecificationsPage_addSpecification(randomSpecification());

		for (EntityField entityField : entityFields) {
			Page<Specification> page =
				specificationResource.getSpecificationsPage(
					null,
					getFilterString(entityField, operator, specification1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(specification1),
				(List<Specification>)page.getItems());
		}
	}

	@Test
	public void testGetSpecificationsPageWithPagination() throws Exception {
		Page<Specification> totalPage =
			specificationResource.getSpecificationsPage(null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Specification specification1 =
			testGetSpecificationsPage_addSpecification(randomSpecification());

		Specification specification2 =
			testGetSpecificationsPage_addSpecification(randomSpecification());

		Specification specification3 =
			testGetSpecificationsPage_addSpecification(randomSpecification());

		Page<Specification> page1 = specificationResource.getSpecificationsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<Specification> specifications1 =
			(List<Specification>)page1.getItems();

		Assert.assertEquals(
			specifications1.toString(), totalCount + 2, specifications1.size());

		Page<Specification> page2 = specificationResource.getSpecificationsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Specification> specifications2 =
			(List<Specification>)page2.getItems();

		Assert.assertEquals(
			specifications2.toString(), 1, specifications2.size());

		Page<Specification> page3 = specificationResource.getSpecificationsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(specification1, (List<Specification>)page3.getItems());
		assertContains(specification2, (List<Specification>)page3.getItems());
		assertContains(specification3, (List<Specification>)page3.getItems());
	}

	@Test
	public void testGetSpecificationsPageWithSortDateTime() throws Exception {
		testGetSpecificationsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, specification1, specification2) -> {
				BeanTestUtil.setProperty(
					specification1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSpecificationsPageWithSortDouble() throws Exception {
		testGetSpecificationsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, specification1, specification2) -> {
				BeanTestUtil.setProperty(
					specification1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					specification2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetSpecificationsPageWithSortInteger() throws Exception {
		testGetSpecificationsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, specification1, specification2) -> {
				BeanTestUtil.setProperty(
					specification1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					specification2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSpecificationsPageWithSortString() throws Exception {
		testGetSpecificationsPageWithSort(
			EntityField.Type.STRING,
			(entityField, specification1, specification2) -> {
				Class<?> clazz = specification1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						specification1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						specification2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						specification1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						specification2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						specification1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						specification2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSpecificationsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, Specification, Specification, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Specification specification1 = randomSpecification();
		Specification specification2 = randomSpecification();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, specification1, specification2);
		}

		specification1 = testGetSpecificationsPage_addSpecification(
			specification1);

		specification2 = testGetSpecificationsPage_addSpecification(
			specification2);

		for (EntityField entityField : entityFields) {
			Page<Specification> ascPage =
				specificationResource.getSpecificationsPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(specification1, specification2),
				(List<Specification>)ascPage.getItems());

			Page<Specification> descPage =
				specificationResource.getSpecificationsPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(specification2, specification1),
				(List<Specification>)descPage.getItems());
		}
	}

	protected Specification testGetSpecificationsPage_addSpecification(
			Specification specification)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSpecificationsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"specifications",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject specificationsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/specifications");

		long totalCount = specificationsJSONObject.getLong("totalCount");

		Specification specification1 =
			testGraphQLGetSpecificationsPage_addSpecification();
		Specification specification2 =
			testGraphQLGetSpecificationsPage_addSpecification();

		specificationsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/specifications");

		Assert.assertEquals(
			totalCount + 2, specificationsJSONObject.getLong("totalCount"));

		assertContains(
			specification1,
			Arrays.asList(
				SpecificationSerDes.toDTOs(
					specificationsJSONObject.getString("items"))));
		assertContains(
			specification2,
			Arrays.asList(
				SpecificationSerDes.toDTOs(
					specificationsJSONObject.getString("items"))));
	}

	protected Specification testGraphQLGetSpecificationsPage_addSpecification()
		throws Exception {

		return testGraphQLSpecification_addSpecification();
	}

	@Test
	public void testPostSpecification() throws Exception {
		Specification randomSpecification = randomSpecification();

		Specification postSpecification =
			testPostSpecification_addSpecification(randomSpecification);

		assertEquals(randomSpecification, postSpecification);
		assertValid(postSpecification);
	}

	protected Specification testPostSpecification_addSpecification(
			Specification specification)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteSpecification() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Specification specification =
			testDeleteSpecification_addSpecification();

		assertHttpResponseStatusCode(
			204,
			specificationResource.deleteSpecificationHttpResponse(
				specification.getId()));

		assertHttpResponseStatusCode(
			404,
			specificationResource.getSpecificationHttpResponse(
				specification.getId()));

		assertHttpResponseStatusCode(
			404,
			specificationResource.getSpecificationHttpResponse(
				specification.getId()));
	}

	protected Specification testDeleteSpecification_addSpecification()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteSpecification() throws Exception {
		Specification specification =
			testGraphQLDeleteSpecification_addSpecification();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteSpecification",
						new HashMap<String, Object>() {
							{
								put("id", specification.getId());
							}
						})),
				"JSONObject/data", "Object/deleteSpecification"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"specification",
					new HashMap<String, Object>() {
						{
							put("id", specification.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected Specification testGraphQLDeleteSpecification_addSpecification()
		throws Exception {

		return testGraphQLSpecification_addSpecification();
	}

	@Test
	public void testGetSpecification() throws Exception {
		Specification postSpecification =
			testGetSpecification_addSpecification();

		Specification getSpecification = specificationResource.getSpecification(
			postSpecification.getId());

		assertEquals(postSpecification, getSpecification);
		assertValid(getSpecification);
	}

	protected Specification testGetSpecification_addSpecification()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSpecification() throws Exception {
		Specification specification =
			testGraphQLGetSpecification_addSpecification();

		Assert.assertTrue(
			equals(
				specification,
				SpecificationSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"specification",
								new HashMap<String, Object>() {
									{
										put("id", specification.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/specification"))));
	}

	@Test
	public void testGraphQLGetSpecificationNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"specification",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Specification testGraphQLGetSpecification_addSpecification()
		throws Exception {

		return testGraphQLSpecification_addSpecification();
	}

	@Test
	public void testPatchSpecification() throws Exception {
		Assert.assertTrue(false);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Specification testGraphQLSpecification_addSpecification()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		Specification specification, List<Specification> specifications) {

		boolean contains = false;

		for (Specification item : specifications) {
			if (equals(specification, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			specifications + " does not contain " + specification, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		Specification specification1, Specification specification2) {

		Assert.assertTrue(
			specification1 + " does not equal " + specification2,
			equals(specification1, specification2));
	}

	protected void assertEquals(
		List<Specification> specifications1,
		List<Specification> specifications2) {

		Assert.assertEquals(specifications1.size(), specifications2.size());

		for (int i = 0; i < specifications1.size(); i++) {
			Specification specification1 = specifications1.get(i);
			Specification specification2 = specifications2.get(i);

			assertEquals(specification1, specification2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Specification> specifications1,
		List<Specification> specifications2) {

		Assert.assertEquals(specifications1.size(), specifications2.size());

		for (Specification specification1 : specifications1) {
			boolean contains = false;

			for (Specification specification2 : specifications2) {
				if (equals(specification1, specification2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				specifications2 + " does not contain " + specification1,
				contains);
		}
	}

	protected void assertValid(Specification specification) throws Exception {
		boolean valid = true;

		if (specification.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (specification.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("facetable", additionalAssertFieldName)) {
				if (specification.getFacetable() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (specification.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("optionCategory", additionalAssertFieldName)) {
				if (specification.getOptionCategory() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (specification.getTitle() == null) {
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

	protected void assertValid(Page<Specification> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Specification> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Specification> specifications = page.getItems();

		int size = specifications.size();

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
					com.liferay.headless.commerce.admin.catalog.dto.v1_0.
						Specification.class)) {

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
		Specification specification1, Specification specification2) {

		if (specification1 == specification2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!equals(
						(Map)specification1.getDescription(),
						(Map)specification2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("facetable", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						specification1.getFacetable(),
						specification2.getFacetable())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						specification1.getId(), specification2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						specification1.getKey(), specification2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("optionCategory", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						specification1.getOptionCategory(),
						specification2.getOptionCategory())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!equals(
						(Map)specification1.getTitle(),
						(Map)specification2.getTitle())) {

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

		if (!(_specificationResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_specificationResource;

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
		EntityField entityField, String operator, Specification specification) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("description")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("facetable")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			Object object = specification.getKey();

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

		if (entityFieldName.equals("optionCategory")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
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

	protected Specification randomSpecification() throws Exception {
		return new Specification() {
			{
				facetable = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Specification randomIrrelevantSpecification() throws Exception {
		Specification randomIrrelevantSpecification = randomSpecification();

		return randomIrrelevantSpecification;
	}

	protected Specification randomPatchSpecification() throws Exception {
		return randomSpecification();
	}

	protected SpecificationResource specificationResource;
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
		LogFactoryUtil.getLog(BaseSpecificationResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.admin.catalog.resource.v1_0.
		SpecificationResource _specificationResource;

}