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

package com.liferay.search.experiences.rest.resource.v1_0.test;

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
import com.liferay.search.experiences.rest.client.dto.v1_0.Field;
import com.liferay.search.experiences.rest.client.dto.v1_0.SearchResponse;
import com.liferay.search.experiences.rest.client.http.HttpInvoker;
import com.liferay.search.experiences.rest.client.pagination.Page;
import com.liferay.search.experiences.rest.client.resource.v1_0.SearchResponseResource;
import com.liferay.search.experiences.rest.client.serdes.v1_0.SearchResponseSerDes;

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
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public abstract class BaseSearchResponseResourceTestCase {

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

		_searchResponseResource.setContextCompany(testCompany);

		SearchResponseResource.Builder builder =
			SearchResponseResource.builder();

		searchResponseResource = builder.authentication(
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

		SearchResponse searchResponse1 = randomSearchResponse();

		String json = objectMapper.writeValueAsString(searchResponse1);

		SearchResponse searchResponse2 = SearchResponseSerDes.toDTO(json);

		Assert.assertTrue(equals(searchResponse1, searchResponse2));
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

		SearchResponse searchResponse = randomSearchResponse();

		String json1 = objectMapper.writeValueAsString(searchResponse);
		String json2 = SearchResponseSerDes.toJSON(searchResponse);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		SearchResponse searchResponse = randomSearchResponse();

		searchResponse.setRequestString(regex);
		searchResponse.setResponseString(regex);

		String json = SearchResponseSerDes.toJSON(searchResponse);

		Assert.assertFalse(json.contains(regex));

		searchResponse = SearchResponseSerDes.toDTO(json);

		Assert.assertEquals(regex, searchResponse.getRequestString());
		Assert.assertEquals(regex, searchResponse.getResponseString());
	}

	@Test
	public void testPostSearch() throws Exception {
		SearchResponse randomSearchResponse = randomSearchResponse();

		SearchResponse postSearchResponse = testPostSearch_addSearchResponse(
			randomSearchResponse);

		assertEquals(randomSearchResponse, postSearchResponse);
		assertValid(postSearchResponse);
	}

	protected SearchResponse testPostSearch_addSearchResponse(
			SearchResponse searchResponse)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		SearchResponse searchResponse, List<SearchResponse> searchResponses) {

		boolean contains = false;

		for (SearchResponse item : searchResponses) {
			if (equals(searchResponse, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			searchResponses + " does not contain " + searchResponse, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		SearchResponse searchResponse1, SearchResponse searchResponse2) {

		Assert.assertTrue(
			searchResponse1 + " does not equal " + searchResponse2,
			equals(searchResponse1, searchResponse2));
	}

	protected void assertEquals(
		List<SearchResponse> searchResponses1,
		List<SearchResponse> searchResponses2) {

		Assert.assertEquals(searchResponses1.size(), searchResponses2.size());

		for (int i = 0; i < searchResponses1.size(); i++) {
			SearchResponse searchResponse1 = searchResponses1.get(i);
			SearchResponse searchResponse2 = searchResponses2.get(i);

			assertEquals(searchResponse1, searchResponse2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<SearchResponse> searchResponses1,
		List<SearchResponse> searchResponses2) {

		Assert.assertEquals(searchResponses1.size(), searchResponses2.size());

		for (SearchResponse searchResponse1 : searchResponses1) {
			boolean contains = false;

			for (SearchResponse searchResponse2 : searchResponses2) {
				if (equals(searchResponse1, searchResponse2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				searchResponses2 + " does not contain " + searchResponse1,
				contains);
		}
	}

	protected void assertValid(SearchResponse searchResponse) throws Exception {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("page", additionalAssertFieldName)) {
				if (searchResponse.getPage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pageSize", additionalAssertFieldName)) {
				if (searchResponse.getPageSize() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("request", additionalAssertFieldName)) {
				if (searchResponse.getRequest() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("requestString", additionalAssertFieldName)) {
				if (searchResponse.getRequestString() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("response", additionalAssertFieldName)) {
				if (searchResponse.getResponse() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("responseString", additionalAssertFieldName)) {
				if (searchResponse.getResponseString() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("searchHits", additionalAssertFieldName)) {
				if (searchResponse.getSearchHits() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("searchRequest", additionalAssertFieldName)) {
				if (searchResponse.getSearchRequest() == null) {
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

	protected void assertValid(Page<SearchResponse> page) {
		boolean valid = false;

		java.util.Collection<SearchResponse> searchResponses = page.getItems();

		int size = searchResponses.size();

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
					com.liferay.search.experiences.rest.dto.v1_0.SearchResponse.
						class)) {

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
		SearchResponse searchResponse1, SearchResponse searchResponse2) {

		if (searchResponse1 == searchResponse2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("page", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						searchResponse1.getPage(), searchResponse2.getPage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("pageSize", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						searchResponse1.getPageSize(),
						searchResponse2.getPageSize())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("request", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						searchResponse1.getRequest(),
						searchResponse2.getRequest())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("requestString", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						searchResponse1.getRequestString(),
						searchResponse2.getRequestString())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("response", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						searchResponse1.getResponse(),
						searchResponse2.getResponse())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("responseString", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						searchResponse1.getResponseString(),
						searchResponse2.getResponseString())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("searchHits", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						searchResponse1.getSearchHits(),
						searchResponse2.getSearchHits())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("searchRequest", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						searchResponse1.getSearchRequest(),
						searchResponse2.getSearchRequest())) {

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

		if (!(_searchResponseResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_searchResponseResource;

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
		SearchResponse searchResponse) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("page")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("pageSize")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("request")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("requestString")) {
			sb.append("'");
			sb.append(String.valueOf(searchResponse.getRequestString()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("response")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("responseString")) {
			sb.append("'");
			sb.append(String.valueOf(searchResponse.getResponseString()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("searchHits")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("searchRequest")) {
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

	protected SearchResponse randomSearchResponse() throws Exception {
		return new SearchResponse() {
			{
				page = RandomTestUtil.randomInt();
				pageSize = RandomTestUtil.randomInt();
				requestString = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				responseString = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected SearchResponse randomIrrelevantSearchResponse() throws Exception {
		SearchResponse randomIrrelevantSearchResponse = randomSearchResponse();

		return randomIrrelevantSearchResponse;
	}

	protected SearchResponse randomPatchSearchResponse() throws Exception {
		return randomSearchResponse();
	}

	protected SearchResponseResource searchResponseResource;
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
		LogFactoryUtil.getLog(BaseSearchResponseResourceTestCase.class);

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
		com.liferay.search.experiences.rest.resource.v1_0.SearchResponseResource
			_searchResponseResource;

}