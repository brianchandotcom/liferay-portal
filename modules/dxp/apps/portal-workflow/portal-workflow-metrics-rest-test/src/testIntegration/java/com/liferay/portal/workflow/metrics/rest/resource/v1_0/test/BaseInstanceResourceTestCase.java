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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.client.resource.v1_0.InstanceResource;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.InstanceSerDes;

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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public abstract class BaseInstanceResourceTestCase {

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

		_instanceResource.setContextCompany(testCompany);

		InstanceResource.Builder builder = InstanceResource.builder();

		instanceResource = builder.locale(
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

		Instance instance1 = randomInstance();

		String json = objectMapper.writeValueAsString(instance1);

		Instance instance2 = InstanceSerDes.toDTO(json);

		Assert.assertTrue(equals(instance1, instance2));
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

		Instance instance = randomInstance();

		String json1 = objectMapper.writeValueAsString(instance);
		String json2 = InstanceSerDes.toJSON(instance);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Instance instance = randomInstance();

		instance.setAssetTitle(regex);
		instance.setAssetType(regex);

		String json = InstanceSerDes.toJSON(instance);

		Assert.assertFalse(json.contains(regex));

		instance = InstanceSerDes.toDTO(json);

		Assert.assertEquals(regex, instance.getAssetTitle());
		Assert.assertEquals(regex, instance.getAssetType());
	}

	@Test
	public void testGetProcessInstancesPage() throws Exception {
		Page<Instance> page = instanceResource.getProcessInstancesPage(
			testGetProcessInstancesPage_getProcessId(), null,
			RandomTestUtil.nextDate(), RandomTestUtil.nextDate(), null, null,
			null, Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long processId = testGetProcessInstancesPage_getProcessId();
		Long irrelevantProcessId =
			testGetProcessInstancesPage_getIrrelevantProcessId();

		if ((irrelevantProcessId != null)) {
			Instance irrelevantInstance =
				testGetProcessInstancesPage_addInstance(
					irrelevantProcessId, randomIrrelevantInstance());

			page = instanceResource.getProcessInstancesPage(
				irrelevantProcessId, null, null, null, null, null, null,
				Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantInstance),
				(List<Instance>)page.getItems());
			assertValid(page);
		}

		Instance instance1 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		Instance instance2 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		page = instanceResource.getProcessInstancesPage(
			processId, null, null, null, null, null, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(instance1, instance2),
			(List<Instance>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProcessInstancesPageWithPagination() throws Exception {
		Long processId = testGetProcessInstancesPage_getProcessId();

		Instance instance1 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		Instance instance2 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		Instance instance3 = testGetProcessInstancesPage_addInstance(
			processId, randomInstance());

		Page<Instance> page1 = instanceResource.getProcessInstancesPage(
			processId, null, null, null, null, null, null, Pagination.of(1, 2));

		List<Instance> instances1 = (List<Instance>)page1.getItems();

		Assert.assertEquals(instances1.toString(), 2, instances1.size());

		Page<Instance> page2 = instanceResource.getProcessInstancesPage(
			processId, null, null, null, null, null, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Instance> instances2 = (List<Instance>)page2.getItems();

		Assert.assertEquals(instances2.toString(), 1, instances2.size());

		Page<Instance> page3 = instanceResource.getProcessInstancesPage(
			processId, null, null, null, null, null, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(instance1, instance2, instance3),
			(List<Instance>)page3.getItems());
	}

	protected Instance testGetProcessInstancesPage_addInstance(
			Long processId, Instance instance)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessInstancesPage_getProcessId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessInstancesPage_getIrrelevantProcessId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetProcessInstance() throws Exception {
		Instance postInstance = testGetProcessInstance_addInstance();

		Instance getInstance = instanceResource.getProcessInstance(
			postInstance.getProcessId(), postInstance.getId());

		assertEquals(postInstance, getInstance);
		assertValid(getInstance);
	}

	protected Instance testGetProcessInstance_addInstance() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProcessInstance() throws Exception {
		Instance instance = testGraphQLInstance_addInstance();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"processInstance",
				new HashMap<String, Object>() {
					{
						put("processId", instance.getProcessId());
						put("instanceId", instance.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				instance, dataJSONObject.getJSONObject("processInstance")));
	}

	protected Instance testGraphQLInstance_addInstance() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Instance instance1, Instance instance2) {
		Assert.assertTrue(
			instance1 + " does not equal " + instance2,
			equals(instance1, instance2));
	}

	protected void assertEquals(
		List<Instance> instances1, List<Instance> instances2) {

		Assert.assertEquals(instances1.size(), instances2.size());

		for (int i = 0; i < instances1.size(); i++) {
			Instance instance1 = instances1.get(i);
			Instance instance2 = instances2.get(i);

			assertEquals(instance1, instance2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Instance> instances1, List<Instance> instances2) {

		Assert.assertEquals(instances1.size(), instances2.size());

		for (Instance instance1 : instances1) {
			boolean contains = false;

			for (Instance instance2 : instances2) {
				if (equals(instance1, instance2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				instances2 + " does not contain " + instance1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Instance> instances, JSONArray jsonArray) {

		for (Instance instance : instances) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(instance, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + instance, contains);
		}
	}

	protected void assertValid(Instance instance) {
		boolean valid = true;

		if (instance.getDateCreated() == null) {
			valid = false;
		}

		if (instance.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assetTitle", additionalAssertFieldName)) {
				if (instance.getAssetTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assetType", additionalAssertFieldName)) {
				if (instance.getAssetType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("assigneeUsers", additionalAssertFieldName)) {
				if (instance.getAssigneeUsers() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creatorUser", additionalAssertFieldName)) {
				if (instance.getCreatorUser() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateCompletion", additionalAssertFieldName)) {
				if (instance.getDateCompletion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (instance.getProcessId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("slaResults", additionalAssertFieldName)) {
				if (instance.getSlaResults() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("slaStatus", additionalAssertFieldName)) {
				if (instance.getSLAStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (instance.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("taskNames", additionalAssertFieldName)) {
				if (instance.getTaskNames() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("transitions", additionalAssertFieldName)) {
				if (instance.getTransitions() == null) {
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

	protected void assertValid(Page<Instance> page) {
		boolean valid = false;

		java.util.Collection<Instance> instances = page.getItems();

		int size = instances.size();

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

	protected boolean equals(Instance instance1, Instance instance2) {
		if (instance1 == instance2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assetTitle", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getAssetTitle(), instance2.getAssetTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("assetType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getAssetType(), instance2.getAssetType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("assigneeUsers", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getAssigneeUsers(),
						instance2.getAssigneeUsers())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creatorUser", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getCreatorUser(),
						instance2.getCreatorUser())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCompletion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getDateCompletion(),
						instance2.getDateCompletion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getDateCreated(),
						instance2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(instance1.getId(), instance2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getProcessId(), instance2.getProcessId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("slaResults", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getSlaResults(), instance2.getSlaResults())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("slaStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getSLAStatus(), instance2.getSLAStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getStatus(), instance2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taskNames", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getTaskNames(), instance2.getTaskNames())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("transitions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						instance1.getTransitions(),
						instance2.getTransitions())) {

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
		Instance instance, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("assetTitle", fieldName)) {
				if (!Objects.deepEquals(
						instance.getAssetTitle(),
						jsonObject.getString("assetTitle"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("assetType", fieldName)) {
				if (!Objects.deepEquals(
						instance.getAssetType(),
						jsonObject.getString("assetType"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						instance.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("processId", fieldName)) {
				if (!Objects.deepEquals(
						instance.getProcessId(),
						jsonObject.getLong("processId"))) {

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

		if (!(_instanceResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_instanceResource;

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
		EntityField entityField, String operator, Instance instance) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("assetTitle")) {
			sb.append("'");
			sb.append(String.valueOf(instance.getAssetTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("assetType")) {
			sb.append("'");
			sb.append(String.valueOf(instance.getAssetType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("assigneeUsers")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creatorUser")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCompletion")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							instance.getDateCompletion(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(instance.getDateCompletion(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(instance.getDateCompletion()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(instance.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(instance.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(instance.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("processId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("slaResults")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("slaStatus")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taskNames")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("transitions")) {
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

	protected Instance randomInstance() throws Exception {
		return new Instance() {
			{
				assetTitle = RandomTestUtil.randomString();
				assetType = RandomTestUtil.randomString();
				dateCompletion = RandomTestUtil.nextDate();
				dateCreated = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				processId = RandomTestUtil.randomLong();
			}
		};
	}

	protected Instance randomIrrelevantInstance() throws Exception {
		Instance randomIrrelevantInstance = randomInstance();

		return randomIrrelevantInstance;
	}

	protected Instance randomPatchInstance() throws Exception {
		return randomInstance();
	}

	protected InstanceResource instanceResource;
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
		BaseInstanceResourceTestCase.class);

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
		com.liferay.portal.workflow.metrics.rest.resource.v1_0.InstanceResource
			_instanceResource;

}