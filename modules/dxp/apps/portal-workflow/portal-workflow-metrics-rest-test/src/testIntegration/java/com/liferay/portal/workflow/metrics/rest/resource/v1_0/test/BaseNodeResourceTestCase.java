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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.resource.v1_0.NodeResource;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.NodeSerDes;

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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public abstract class BaseNodeResourceTestCase {

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

		_nodeResource.setContextCompany(testCompany);

		NodeResource.Builder builder = NodeResource.builder();

		nodeResource = builder.authentication(
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

		Node node1 = randomNode();

		String json = objectMapper.writeValueAsString(node1);

		Node node2 = NodeSerDes.toDTO(json);

		Assert.assertTrue(equals(node1, node2));
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

		Node node = randomNode();

		String json1 = objectMapper.writeValueAsString(node);
		String json2 = NodeSerDes.toJSON(node);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Node node = randomNode();

		node.setLabel(regex);
		node.setName(regex);
		node.setProcessVersion(regex);
		node.setType(regex);

		String json = NodeSerDes.toJSON(node);

		Assert.assertFalse(json.contains(regex));

		node = NodeSerDes.toDTO(json);

		Assert.assertEquals(regex, node.getLabel());
		Assert.assertEquals(regex, node.getName());
		Assert.assertEquals(regex, node.getProcessVersion());
		Assert.assertEquals(regex, node.getType());
	}

	@Test
	public void testGetProcessNodesPage() throws Exception {
		Page<Node> page = nodeResource.getProcessNodesPage(
			testGetProcessNodesPage_getProcessId());

		Assert.assertEquals(0, page.getTotalCount());

		Long processId = testGetProcessNodesPage_getProcessId();
		Long irrelevantProcessId =
			testGetProcessNodesPage_getIrrelevantProcessId();

		if ((irrelevantProcessId != null)) {
			Node irrelevantNode = testGetProcessNodesPage_addNode(
				irrelevantProcessId, randomIrrelevantNode());

			page = nodeResource.getProcessNodesPage(irrelevantProcessId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantNode), (List<Node>)page.getItems());
			assertValid(page);
		}

		Node node1 = testGetProcessNodesPage_addNode(processId, randomNode());

		Node node2 = testGetProcessNodesPage_addNode(processId, randomNode());

		page = nodeResource.getProcessNodesPage(processId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(node1, node2), (List<Node>)page.getItems());
		assertValid(page);
	}

	protected Node testGetProcessNodesPage_addNode(Long processId, Node node)
		throws Exception {

		return nodeResource.postProcessNode(processId, node);
	}

	protected Long testGetProcessNodesPage_getProcessId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessNodesPage_getIrrelevantProcessId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostProcessNode() throws Exception {
		Node randomNode = randomNode();

		Node postNode = testPostProcessNode_addNode(randomNode);

		assertEquals(randomNode, postNode);
		assertValid(postNode);
	}

	protected Node testPostProcessNode_addNode(Node node) throws Exception {
		return nodeResource.postProcessNode(
			testGetProcessNodesPage_getProcessId(), node);
	}

	@Test
	public void testDeleteProcessNode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Node node = testDeleteProcessNode_addNode();

		assertHttpResponseStatusCode(
			204,
			nodeResource.deleteProcessNodeHttpResponse(
				node.getProcessId(), node.getId()));
	}

	protected Node testDeleteProcessNode_addNode() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Node testGraphQLNode_addNode() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Node node1, Node node2) {
		Assert.assertTrue(
			node1 + " does not equal " + node2, equals(node1, node2));
	}

	protected void assertEquals(List<Node> nodes1, List<Node> nodes2) {
		Assert.assertEquals(nodes1.size(), nodes2.size());

		for (int i = 0; i < nodes1.size(); i++) {
			Node node1 = nodes1.get(i);
			Node node2 = nodes2.get(i);

			assertEquals(node1, node2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Node> nodes1, List<Node> nodes2) {

		Assert.assertEquals(nodes1.size(), nodes2.size());

		for (Node node1 : nodes1) {
			boolean contains = false;

			for (Node node2 : nodes2) {
				if (equals(node1, node2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(nodes2 + " does not contain " + node1, contains);
		}
	}

	protected void assertValid(Node node) throws Exception {
		boolean valid = true;

		if (node.getDateCreated() == null) {
			valid = false;
		}

		if (node.getDateModified() == null) {
			valid = false;
		}

		if (node.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("initial", additionalAssertFieldName)) {
				if (node.getInitial() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("label", additionalAssertFieldName)) {
				if (node.getLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (node.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (node.getProcessId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("processVersion", additionalAssertFieldName)) {
				if (node.getProcessVersion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("terminal", additionalAssertFieldName)) {
				if (node.getTerminal() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (node.getType() == null) {
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

	protected void assertValid(Page<Node> page) {
		boolean valid = false;

		java.util.Collection<Node> nodes = page.getItems();

		int size = nodes.size();

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
					com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node.
						class)) {

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

	protected boolean equals(Node node1, Node node2) {
		if (node1 == node2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						node1.getDateCreated(), node2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						node1.getDateModified(), node2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(node1.getId(), node2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("initial", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						node1.getInitial(), node2.getInitial())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("label", additionalAssertFieldName)) {
				if (!Objects.deepEquals(node1.getLabel(), node2.getLabel())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(node1.getName(), node2.getName())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						node1.getProcessId(), node2.getProcessId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("processVersion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						node1.getProcessVersion(), node2.getProcessVersion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("terminal", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						node1.getTerminal(), node2.getTerminal())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(node1.getType(), node2.getType())) {
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

		if (!(_nodeResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_nodeResource;

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
		EntityField entityField, String operator, Node node) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(node.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(node.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(node.getDateCreated()));
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
						DateUtils.addSeconds(node.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(node.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(node.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("initial")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("label")) {
			sb.append("'");
			sb.append(String.valueOf(node.getLabel()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(node.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("processId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("processVersion")) {
			sb.append("'");
			sb.append(String.valueOf(node.getProcessVersion()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("terminal")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(node.getType()));
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

	protected Node randomNode() throws Exception {
		return new Node() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				initial = RandomTestUtil.randomBoolean();
				label = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				processId = RandomTestUtil.randomLong();
				processVersion = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				terminal = RandomTestUtil.randomBoolean();
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Node randomIrrelevantNode() throws Exception {
		Node randomIrrelevantNode = randomNode();

		return randomIrrelevantNode;
	}

	protected Node randomPatchNode() throws Exception {
		return randomNode();
	}

	protected NodeResource nodeResource;
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
		BaseNodeResourceTestCase.class);

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
	private com.liferay.portal.workflow.metrics.rest.resource.v1_0.NodeResource
		_nodeResource;

}