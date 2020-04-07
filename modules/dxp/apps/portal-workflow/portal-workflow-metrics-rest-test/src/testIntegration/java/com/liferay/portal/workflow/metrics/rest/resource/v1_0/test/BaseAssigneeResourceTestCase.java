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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.resource.v1_0.AssigneeResource;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.AssigneeSerDes;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public abstract class BaseAssigneeResourceTestCase {

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

		_assigneeResource.setContextCompany(testCompany);

		AssigneeResource.Builder builder = AssigneeResource.builder();

		assigneeResource = builder.locale(
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

		Assignee assignee1 = randomAssignee();

		String json = objectMapper.writeValueAsString(assignee1);

		Assignee assignee2 = AssigneeSerDes.toDTO(json);

		Assert.assertTrue(equals(assignee1, assignee2));
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

		Assignee assignee = randomAssignee();

		String json1 = objectMapper.writeValueAsString(assignee);
		String json2 = AssigneeSerDes.toJSON(assignee);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Assignee assignee = randomAssignee();

		assignee.setImage(regex);
		assignee.setName(regex);

		String json = AssigneeSerDes.toJSON(assignee);

		Assert.assertFalse(json.contains(regex));

		assignee = AssigneeSerDes.toDTO(json);

		Assert.assertEquals(regex, assignee.getImage());
		Assert.assertEquals(regex, assignee.getName());
	}

	@Test
	public void testPostProcessAssigneesPage() throws Exception {
		Assert.assertTrue(false);
	}

	protected Assignee testGraphQLAssignee_addAssignee() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Assignee assignee1, Assignee assignee2) {
		Assert.assertTrue(
			assignee1 + " does not equal " + assignee2,
			equals(assignee1, assignee2));
	}

	protected void assertEquals(
		List<Assignee> assignees1, List<Assignee> assignees2) {

		Assert.assertEquals(assignees1.size(), assignees2.size());

		for (int i = 0; i < assignees1.size(); i++) {
			Assignee assignee1 = assignees1.get(i);
			Assignee assignee2 = assignees2.get(i);

			assertEquals(assignee1, assignee2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Assignee> assignees1, List<Assignee> assignees2) {

		Assert.assertEquals(assignees1.size(), assignees2.size());

		for (Assignee assignee1 : assignees1) {
			boolean contains = false;

			for (Assignee assignee2 : assignees2) {
				if (equals(assignee1, assignee2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				assignees2 + " does not contain " + assignee1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Assignee> assignees, JSONArray jsonArray) {

		for (Assignee assignee : assignees) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(assignee, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + assignee, contains);
		}
	}

	protected void assertValid(Assignee assignee) {
		boolean valid = true;

		if (assignee.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("durationTaskAvg", additionalAssertFieldName)) {
				if (assignee.getDurationTaskAvg() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (assignee.getImage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (assignee.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("onTimeTaskCount", additionalAssertFieldName)) {
				if (assignee.getOnTimeTaskCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("overdueTaskCount", additionalAssertFieldName)) {
				if (assignee.getOverdueTaskCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("taskCount", additionalAssertFieldName)) {
				if (assignee.getTaskCount() == null) {
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

	protected void assertValid(Page<Assignee> page) {
		boolean valid = false;

		java.util.Collection<Assignee> assignees = page.getItems();

		int size = assignees.size();

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

	protected boolean equals(Assignee assignee1, Assignee assignee2) {
		if (assignee1 == assignee2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("durationTaskAvg", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assignee1.getDurationTaskAvg(),
						assignee2.getDurationTaskAvg())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(assignee1.getId(), assignee2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assignee1.getImage(), assignee2.getImage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assignee1.getName(), assignee2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("onTimeTaskCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assignee1.getOnTimeTaskCount(),
						assignee2.getOnTimeTaskCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("overdueTaskCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assignee1.getOverdueTaskCount(),
						assignee2.getOverdueTaskCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taskCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						assignee1.getTaskCount(), assignee2.getTaskCount())) {

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
		Assignee assignee, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("durationTaskAvg", fieldName)) {
				if (!Objects.deepEquals(
						assignee.getDurationTaskAvg(),
						jsonObject.getLong("durationTaskAvg"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						assignee.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", fieldName)) {
				if (!Objects.deepEquals(
						assignee.getImage(), jsonObject.getString("image"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						assignee.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("onTimeTaskCount", fieldName)) {
				if (!Objects.deepEquals(
						assignee.getOnTimeTaskCount(),
						jsonObject.getLong("onTimeTaskCount"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("overdueTaskCount", fieldName)) {
				if (!Objects.deepEquals(
						assignee.getOverdueTaskCount(),
						jsonObject.getLong("overdueTaskCount"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taskCount", fieldName)) {
				if (!Objects.deepEquals(
						assignee.getTaskCount(),
						jsonObject.getLong("taskCount"))) {

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

		if (!(_assigneeResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_assigneeResource;

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
		EntityField entityField, String operator, Assignee assignee) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("durationTaskAvg")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("image")) {
			sb.append("'");
			sb.append(String.valueOf(assignee.getImage()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(assignee.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("onTimeTaskCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("overdueTaskCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taskCount")) {
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

	protected Assignee randomAssignee() throws Exception {
		return new Assignee() {
			{
				durationTaskAvg = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				image = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				onTimeTaskCount = RandomTestUtil.randomLong();
				overdueTaskCount = RandomTestUtil.randomLong();
				taskCount = RandomTestUtil.randomLong();
			}
		};
	}

	protected Assignee randomIrrelevantAssignee() throws Exception {
		Assignee randomIrrelevantAssignee = randomAssignee();

		return randomIrrelevantAssignee;
	}

	protected Assignee randomPatchAssignee() throws Exception {
		return randomAssignee();
	}

	protected AssigneeResource assigneeResource;
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
		BaseAssigneeResourceTestCase.class);

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
	private
		com.liferay.portal.workflow.metrics.rest.resource.v1_0.AssigneeResource
			_assigneeResource;

}