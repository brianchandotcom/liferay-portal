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

package com.liferay.headless.form.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.form.client.dto.v1_0.FormStructure;
import com.liferay.headless.form.client.http.HttpInvoker;
import com.liferay.headless.form.client.pagination.Page;
import com.liferay.headless.form.client.pagination.Pagination;
import com.liferay.headless.form.client.resource.v1_0.FormStructureResource;
import com.liferay.headless.form.client.serdes.v1_0.FormStructureSerDes;
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
import org.apache.commons.lang.time.DateUtils;

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
public abstract class BaseFormStructureResourceTestCase {

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

		_formStructureResource.setContextCompany(testCompany);

		FormStructureResource.Builder builder = FormStructureResource.builder();

		formStructureResource = builder.authentication(
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

		FormStructure formStructure1 = randomFormStructure();

		String json = objectMapper.writeValueAsString(formStructure1);

		FormStructure formStructure2 = FormStructureSerDes.toDTO(json);

		Assert.assertTrue(equals(formStructure1, formStructure2));
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

		FormStructure formStructure = randomFormStructure();

		String json1 = objectMapper.writeValueAsString(formStructure);
		String json2 = FormStructureSerDes.toJSON(formStructure);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		FormStructure formStructure = randomFormStructure();

		formStructure.setDescription(regex);
		formStructure.setName(regex);

		String json = FormStructureSerDes.toJSON(formStructure);

		Assert.assertFalse(json.contains(regex));

		formStructure = FormStructureSerDes.toDTO(json);

		Assert.assertEquals(regex, formStructure.getDescription());
		Assert.assertEquals(regex, formStructure.getName());
	}

	@Test
	public void testGetFormStructure() throws Exception {
		FormStructure postFormStructure =
			testGetFormStructure_addFormStructure();

		FormStructure getFormStructure = formStructureResource.getFormStructure(
			postFormStructure.getId());

		assertEquals(postFormStructure, getFormStructure);
		assertValid(getFormStructure);
	}

	protected FormStructure testGetFormStructure_addFormStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetFormStructure() throws Exception {
		FormStructure formStructure =
			testGraphQLFormStructure_addFormStructure();

		Assert.assertTrue(
			equals(
				formStructure,
				FormStructureSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"formStructure",
								new HashMap<String, Object>() {
									{
										put(
											"formStructureId",
											formStructure.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/formStructure"))));
	}

	@Test
	public void testGraphQLGetFormStructureNotFound() throws Exception {
		Long irrelevantFormStructureId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"formStructure",
						new HashMap<String, Object>() {
							{
								put(
									"formStructureId",
									irrelevantFormStructureId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGetSiteFormStructuresPage() throws Exception {
		Page<FormStructure> page =
			formStructureResource.getSiteFormStructuresPage(
				testGetSiteFormStructuresPage_getSiteId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteFormStructuresPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteFormStructuresPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			FormStructure irrelevantFormStructure =
				testGetSiteFormStructuresPage_addFormStructure(
					irrelevantSiteId, randomIrrelevantFormStructure());

			page = formStructureResource.getSiteFormStructuresPage(
				irrelevantSiteId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantFormStructure),
				(List<FormStructure>)page.getItems());
			assertValid(page);
		}

		FormStructure formStructure1 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		FormStructure formStructure2 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		page = formStructureResource.getSiteFormStructuresPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(formStructure1, formStructure2),
			(List<FormStructure>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteFormStructuresPageWithPagination() throws Exception {
		Long siteId = testGetSiteFormStructuresPage_getSiteId();

		FormStructure formStructure1 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		FormStructure formStructure2 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		FormStructure formStructure3 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		Page<FormStructure> page1 =
			formStructureResource.getSiteFormStructuresPage(
				siteId, Pagination.of(1, 2));

		List<FormStructure> formStructures1 =
			(List<FormStructure>)page1.getItems();

		Assert.assertEquals(
			formStructures1.toString(), 2, formStructures1.size());

		Page<FormStructure> page2 =
			formStructureResource.getSiteFormStructuresPage(
				siteId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<FormStructure> formStructures2 =
			(List<FormStructure>)page2.getItems();

		Assert.assertEquals(
			formStructures2.toString(), 1, formStructures2.size());

		Page<FormStructure> page3 =
			formStructureResource.getSiteFormStructuresPage(
				siteId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(formStructure1, formStructure2, formStructure3),
			(List<FormStructure>)page3.getItems());
	}

	protected FormStructure testGetSiteFormStructuresPage_addFormStructure(
			Long siteId, FormStructure formStructure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteFormStructuresPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteFormStructuresPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteFormStructuresPage() throws Exception {
		Long siteId = testGetSiteFormStructuresPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"formStructures",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject formStructuresJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/formStructures");

		Assert.assertEquals(0, formStructuresJSONObject.get("totalCount"));

		FormStructure formStructure1 =
			testGraphQLFormStructure_addFormStructure();
		FormStructure formStructure2 =
			testGraphQLFormStructure_addFormStructure();

		formStructuresJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/formStructures");

		Assert.assertEquals(2, formStructuresJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(formStructure1, formStructure2),
			Arrays.asList(
				FormStructureSerDes.toDTOs(
					formStructuresJSONObject.getString("items"))));
	}

	protected FormStructure testGraphQLFormStructure_addFormStructure()
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
		FormStructure formStructure1, FormStructure formStructure2) {

		Assert.assertTrue(
			formStructure1 + " does not equal " + formStructure2,
			equals(formStructure1, formStructure2));
	}

	protected void assertEquals(
		List<FormStructure> formStructures1,
		List<FormStructure> formStructures2) {

		Assert.assertEquals(formStructures1.size(), formStructures2.size());

		for (int i = 0; i < formStructures1.size(); i++) {
			FormStructure formStructure1 = formStructures1.get(i);
			FormStructure formStructure2 = formStructures2.get(i);

			assertEquals(formStructure1, formStructure2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<FormStructure> formStructures1,
		List<FormStructure> formStructures2) {

		Assert.assertEquals(formStructures1.size(), formStructures2.size());

		for (FormStructure formStructure1 : formStructures1) {
			boolean contains = false;

			for (FormStructure formStructure2 : formStructures2) {
				if (equals(formStructure1, formStructure2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				formStructures2 + " does not contain " + formStructure1,
				contains);
		}
	}

	protected void assertValid(FormStructure formStructure) throws Exception {
		boolean valid = true;

		if (formStructure.getDateCreated() == null) {
			valid = false;
		}

		if (formStructure.getDateModified() == null) {
			valid = false;
		}

		if (formStructure.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				formStructure.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (formStructure.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (formStructure.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (formStructure.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (formStructure.getDescription_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("formPages", additionalAssertFieldName)) {
				if (formStructure.getFormPages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("formSuccessPage", additionalAssertFieldName)) {
				if (formStructure.getFormSuccessPage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (formStructure.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (formStructure.getName_i18n() == null) {
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

	protected void assertValid(Page<FormStructure> page) {
		boolean valid = false;

		java.util.Collection<FormStructure> formStructures = page.getItems();

		int size = formStructures.size();

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

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.form.dto.v1_0.FormStructure.class)) {

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
		FormStructure formStructure1, FormStructure formStructure2) {

		if (formStructure1 == formStructure2) {
			return true;
		}

		if (!Objects.equals(
				formStructure1.getSiteId(), formStructure2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						formStructure1.getAvailableLanguages(),
						formStructure2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getCreator(),
						formStructure2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getDateCreated(),
						formStructure2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getDateModified(),
						formStructure2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getDescription(),
						formStructure2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)formStructure1.getDescription_i18n(),
						(Map)formStructure2.getDescription_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("formPages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getFormPages(),
						formStructure2.getFormPages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("formSuccessPage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getFormSuccessPage(),
						formStructure2.getFormSuccessPage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getId(), formStructure2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getName(), formStructure2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)formStructure1.getName_i18n(),
						(Map)formStructure2.getName_i18n())) {

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

		if (!(_formStructureResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_formStructureResource;

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
		EntityField entityField, String operator, FormStructure formStructure) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							formStructure.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							formStructure.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(formStructure.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							formStructure.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							formStructure.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(formStructure.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(formStructure.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("formPages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("formSuccessPage")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(formStructure.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
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

	protected FormStructure randomFormStructure() throws Exception {
		return new FormStructure() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected FormStructure randomIrrelevantFormStructure() throws Exception {
		FormStructure randomIrrelevantFormStructure = randomFormStructure();

		randomIrrelevantFormStructure.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantFormStructure;
	}

	protected FormStructure randomPatchFormStructure() throws Exception {
		return randomFormStructure();
	}

	protected FormStructureResource formStructureResource;
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
		BaseFormStructureResourceTestCase.class);

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
	private com.liferay.headless.form.resource.v1_0.FormStructureResource
		_formStructureResource;

}