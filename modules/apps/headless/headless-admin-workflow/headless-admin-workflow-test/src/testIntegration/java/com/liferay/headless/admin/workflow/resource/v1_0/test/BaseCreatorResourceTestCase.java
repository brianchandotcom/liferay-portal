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

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.workflow.client.dto.v1_0.Creator;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.resource.v1_0.CreatorResource;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.CreatorSerDes;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseCreatorResourceTestCase {

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

		_creatorResource.setContextCompany(testCompany);

		CreatorResource.Builder builder = CreatorResource.builder();

		creatorResource = builder.locale(
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

		Creator creator1 = randomCreator();

		String json = objectMapper.writeValueAsString(creator1);

		Creator creator2 = CreatorSerDes.toDTO(json);

		Assert.assertTrue(equals(creator1, creator2));
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

		Creator creator = randomCreator();

		String json1 = objectMapper.writeValueAsString(creator);
		String json2 = CreatorSerDes.toJSON(creator);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Creator creator = randomCreator();

		creator.setAdditionalName(regex);
		creator.setContentType(regex);
		creator.setFamilyName(regex);
		creator.setGivenName(regex);
		creator.setImage(regex);
		creator.setName(regex);
		creator.setProfileURL(regex);

		String json = CreatorSerDes.toJSON(creator);

		Assert.assertFalse(json.contains(regex));

		creator = CreatorSerDes.toDTO(json);

		Assert.assertEquals(regex, creator.getAdditionalName());
		Assert.assertEquals(regex, creator.getContentType());
		Assert.assertEquals(regex, creator.getFamilyName());
		Assert.assertEquals(regex, creator.getGivenName());
		Assert.assertEquals(regex, creator.getImage());
		Assert.assertEquals(regex, creator.getName());
		Assert.assertEquals(regex, creator.getProfileURL());
	}

	@Test
	public void testGetWorkflowTaskAssignableUsersPage() throws Exception {
		Page<Creator> page = creatorResource.getWorkflowTaskAssignableUsersPage(
			testGetWorkflowTaskAssignableUsersPage_getWorkflowTaskId(),
			Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long workflowTaskId =
			testGetWorkflowTaskAssignableUsersPage_getWorkflowTaskId();
		Long irrelevantWorkflowTaskId =
			testGetWorkflowTaskAssignableUsersPage_getIrrelevantWorkflowTaskId();

		if ((irrelevantWorkflowTaskId != null)) {
			Creator irrelevantCreator =
				testGetWorkflowTaskAssignableUsersPage_addCreator(
					irrelevantWorkflowTaskId, randomIrrelevantCreator());

			page = creatorResource.getWorkflowTaskAssignableUsersPage(
				irrelevantWorkflowTaskId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantCreator),
				(List<Creator>)page.getItems());
			assertValid(page);
		}

		Creator creator1 = testGetWorkflowTaskAssignableUsersPage_addCreator(
			workflowTaskId, randomCreator());

		Creator creator2 = testGetWorkflowTaskAssignableUsersPage_addCreator(
			workflowTaskId, randomCreator());

		page = creatorResource.getWorkflowTaskAssignableUsersPage(
			workflowTaskId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(creator1, creator2), (List<Creator>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTaskAssignableUsersPageWithPagination()
		throws Exception {

		Long workflowTaskId =
			testGetWorkflowTaskAssignableUsersPage_getWorkflowTaskId();

		Creator creator1 = testGetWorkflowTaskAssignableUsersPage_addCreator(
			workflowTaskId, randomCreator());

		Creator creator2 = testGetWorkflowTaskAssignableUsersPage_addCreator(
			workflowTaskId, randomCreator());

		Creator creator3 = testGetWorkflowTaskAssignableUsersPage_addCreator(
			workflowTaskId, randomCreator());

		Page<Creator> page1 =
			creatorResource.getWorkflowTaskAssignableUsersPage(
				workflowTaskId, Pagination.of(1, 2));

		List<Creator> creators1 = (List<Creator>)page1.getItems();

		Assert.assertEquals(creators1.toString(), 2, creators1.size());

		Page<Creator> page2 =
			creatorResource.getWorkflowTaskAssignableUsersPage(
				workflowTaskId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Creator> creators2 = (List<Creator>)page2.getItems();

		Assert.assertEquals(creators2.toString(), 1, creators2.size());

		Page<Creator> page3 =
			creatorResource.getWorkflowTaskAssignableUsersPage(
				workflowTaskId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(creator1, creator2, creator3),
			(List<Creator>)page3.getItems());
	}

	protected Creator testGetWorkflowTaskAssignableUsersPage_addCreator(
			Long workflowTaskId, Creator creator)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWorkflowTaskAssignableUsersPage_getWorkflowTaskId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetWorkflowTaskAssignableUsersPage_getIrrelevantWorkflowTaskId()
		throws Exception {

		return null;
	}

	protected Creator testGraphQLCreator_addCreator() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Creator creator1, Creator creator2) {
		Assert.assertTrue(
			creator1 + " does not equal " + creator2,
			equals(creator1, creator2));
	}

	protected void assertEquals(
		List<Creator> creators1, List<Creator> creators2) {

		Assert.assertEquals(creators1.size(), creators2.size());

		for (int i = 0; i < creators1.size(); i++) {
			Creator creator1 = creators1.get(i);
			Creator creator2 = creators2.get(i);

			assertEquals(creator1, creator2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Creator> creators1, List<Creator> creators2) {

		Assert.assertEquals(creators1.size(), creators2.size());

		for (Creator creator1 : creators1) {
			boolean contains = false;

			for (Creator creator2 : creators2) {
				if (equals(creator1, creator2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				creators2 + " does not contain " + creator1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Creator> creators, JSONArray jsonArray) {

		for (Creator creator : creators) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(creator, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + creator, contains);
		}
	}

	protected void assertValid(Creator creator) {
		boolean valid = true;

		if (creator.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("additionalName", additionalAssertFieldName)) {
				if (creator.getAdditionalName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (creator.getContentType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("familyName", additionalAssertFieldName)) {
				if (creator.getFamilyName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("givenName", additionalAssertFieldName)) {
				if (creator.getGivenName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (creator.getImage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (creator.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("profileURL", additionalAssertFieldName)) {
				if (creator.getProfileURL() == null) {
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

	protected void assertValid(Page<Creator> page) {
		boolean valid = false;

		java.util.Collection<Creator> creators = page.getItems();

		int size = creators.size();

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

	protected boolean equals(Creator creator1, Creator creator2) {
		if (creator1 == creator2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("additionalName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						creator1.getAdditionalName(),
						creator2.getAdditionalName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						creator1.getContentType(), creator2.getContentType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("familyName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						creator1.getFamilyName(), creator2.getFamilyName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("givenName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						creator1.getGivenName(), creator2.getGivenName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(creator1.getId(), creator2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						creator1.getImage(), creator2.getImage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						creator1.getName(), creator2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("profileURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						creator1.getProfileURL(), creator2.getProfileURL())) {

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

	protected boolean equalsJSONObject(Creator creator, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("additionalName", fieldName)) {
				if (!Objects.deepEquals(
						creator.getAdditionalName(),
						jsonObject.getString("additionalName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentType", fieldName)) {
				if (!Objects.deepEquals(
						creator.getContentType(),
						jsonObject.getString("contentType"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("familyName", fieldName)) {
				if (!Objects.deepEquals(
						creator.getFamilyName(),
						jsonObject.getString("familyName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("givenName", fieldName)) {
				if (!Objects.deepEquals(
						creator.getGivenName(),
						jsonObject.getString("givenName"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						creator.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", fieldName)) {
				if (!Objects.deepEquals(
						creator.getImage(), jsonObject.getString("image"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						creator.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("profileURL", fieldName)) {
				if (!Objects.deepEquals(
						creator.getProfileURL(),
						jsonObject.getString("profileURL"))) {

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

		if (!(_creatorResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_creatorResource;

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
		EntityField entityField, String operator, Creator creator) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("additionalName")) {
			sb.append("'");
			sb.append(String.valueOf(creator.getAdditionalName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("contentType")) {
			sb.append("'");
			sb.append(String.valueOf(creator.getContentType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("familyName")) {
			sb.append("'");
			sb.append(String.valueOf(creator.getFamilyName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("givenName")) {
			sb.append("'");
			sb.append(String.valueOf(creator.getGivenName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("image")) {
			sb.append("'");
			sb.append(String.valueOf(creator.getImage()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(creator.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("profileURL")) {
			sb.append("'");
			sb.append(String.valueOf(creator.getProfileURL()));
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

	protected Creator randomCreator() throws Exception {
		return new Creator() {
			{
				additionalName = RandomTestUtil.randomString();
				contentType = RandomTestUtil.randomString();
				familyName = RandomTestUtil.randomString();
				givenName = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				image = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				profileURL = RandomTestUtil.randomString();
			}
		};
	}

	protected Creator randomIrrelevantCreator() throws Exception {
		Creator randomIrrelevantCreator = randomCreator();

		return randomIrrelevantCreator;
	}

	protected Creator randomPatchCreator() throws Exception {
		return randomCreator();
	}

	protected CreatorResource creatorResource;
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
		BaseCreatorResourceTestCase.class);

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
	private com.liferay.headless.admin.workflow.resource.v1_0.CreatorResource
		_creatorResource;

}