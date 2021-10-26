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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.search.experiences.rest.client.dto.v1_0.SearchableAssetNamesDisplay;
import com.liferay.search.experiences.rest.client.http.HttpInvoker;
import com.liferay.search.experiences.rest.client.pagination.Page;
import com.liferay.search.experiences.rest.client.resource.v1_0.SearchableAssetNamesDisplayResource;
import com.liferay.search.experiences.rest.client.serdes.v1_0.SearchableAssetNamesDisplaySerDes;

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
public abstract class BaseSearchableAssetNamesDisplayResourceTestCase {

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

		_searchableAssetNamesDisplayResource.setContextCompany(testCompany);

		SearchableAssetNamesDisplayResource.Builder builder =
			SearchableAssetNamesDisplayResource.builder();

		searchableAssetNamesDisplayResource = builder.authentication(
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

		SearchableAssetNamesDisplay searchableAssetNamesDisplay1 =
			randomSearchableAssetNamesDisplay();

		String json = objectMapper.writeValueAsString(
			searchableAssetNamesDisplay1);

		SearchableAssetNamesDisplay searchableAssetNamesDisplay2 =
			SearchableAssetNamesDisplaySerDes.toDTO(json);

		Assert.assertTrue(
			equals(searchableAssetNamesDisplay1, searchableAssetNamesDisplay2));
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

		SearchableAssetNamesDisplay searchableAssetNamesDisplay =
			randomSearchableAssetNamesDisplay();

		String json1 = objectMapper.writeValueAsString(
			searchableAssetNamesDisplay);
		String json2 = SearchableAssetNamesDisplaySerDes.toJSON(
			searchableAssetNamesDisplay);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		SearchableAssetNamesDisplay searchableAssetNamesDisplay =
			randomSearchableAssetNamesDisplay();

		String json = SearchableAssetNamesDisplaySerDes.toJSON(
			searchableAssetNamesDisplay);

		Assert.assertFalse(json.contains(regex));

		searchableAssetNamesDisplay = SearchableAssetNamesDisplaySerDes.toDTO(
			json);
	}

	@Test
	public void testGetSearchableAssetNameLanguage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetSearchableAssetNameLanguage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetSearchableAssetNameLanguageNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	protected void assertContains(
		SearchableAssetNamesDisplay searchableAssetNamesDisplay,
		List<SearchableAssetNamesDisplay> searchableAssetNamesDisplays) {

		boolean contains = false;

		for (SearchableAssetNamesDisplay item : searchableAssetNamesDisplays) {
			if (equals(searchableAssetNamesDisplay, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			searchableAssetNamesDisplays + " does not contain " +
				searchableAssetNamesDisplay,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		SearchableAssetNamesDisplay searchableAssetNamesDisplay1,
		SearchableAssetNamesDisplay searchableAssetNamesDisplay2) {

		Assert.assertTrue(
			searchableAssetNamesDisplay1 + " does not equal " +
				searchableAssetNamesDisplay2,
			equals(searchableAssetNamesDisplay1, searchableAssetNamesDisplay2));
	}

	protected void assertEquals(
		List<SearchableAssetNamesDisplay> searchableAssetNamesDisplays1,
		List<SearchableAssetNamesDisplay> searchableAssetNamesDisplays2) {

		Assert.assertEquals(
			searchableAssetNamesDisplays1.size(),
			searchableAssetNamesDisplays2.size());

		for (int i = 0; i < searchableAssetNamesDisplays1.size(); i++) {
			SearchableAssetNamesDisplay searchableAssetNamesDisplay1 =
				searchableAssetNamesDisplays1.get(i);
			SearchableAssetNamesDisplay searchableAssetNamesDisplay2 =
				searchableAssetNamesDisplays2.get(i);

			assertEquals(
				searchableAssetNamesDisplay1, searchableAssetNamesDisplay2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<SearchableAssetNamesDisplay> searchableAssetNamesDisplays1,
		List<SearchableAssetNamesDisplay> searchableAssetNamesDisplays2) {

		Assert.assertEquals(
			searchableAssetNamesDisplays1.size(),
			searchableAssetNamesDisplays2.size());

		for (SearchableAssetNamesDisplay searchableAssetNamesDisplay1 :
				searchableAssetNamesDisplays1) {

			boolean contains = false;

			for (SearchableAssetNamesDisplay searchableAssetNamesDisplay2 :
					searchableAssetNamesDisplays2) {

				if (equals(
						searchableAssetNamesDisplay1,
						searchableAssetNamesDisplay2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				searchableAssetNamesDisplays2 + " does not contain " +
					searchableAssetNamesDisplay1,
				contains);
		}
	}

	protected void assertValid(
			SearchableAssetNamesDisplay searchableAssetNamesDisplay)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"searchableAssetNames", additionalAssertFieldName)) {

				if (searchableAssetNamesDisplay.getSearchableAssetNames() ==
						null) {

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

	protected void assertValid(Page<SearchableAssetNamesDisplay> page) {
		boolean valid = false;

		java.util.Collection<SearchableAssetNamesDisplay>
			searchableAssetNamesDisplays = page.getItems();

		int size = searchableAssetNamesDisplays.size();

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
					com.liferay.search.experiences.rest.dto.v1_0.
						SearchableAssetNamesDisplay.class)) {

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
		SearchableAssetNamesDisplay searchableAssetNamesDisplay1,
		SearchableAssetNamesDisplay searchableAssetNamesDisplay2) {

		if (searchableAssetNamesDisplay1 == searchableAssetNamesDisplay2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"searchableAssetNames", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						searchableAssetNamesDisplay1.getSearchableAssetNames(),
						searchableAssetNamesDisplay2.
							getSearchableAssetNames())) {

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

		if (!(_searchableAssetNamesDisplayResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_searchableAssetNamesDisplayResource;

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
		SearchableAssetNamesDisplay searchableAssetNamesDisplay) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("searchableAssetNames")) {
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

	protected SearchableAssetNamesDisplay randomSearchableAssetNamesDisplay()
		throws Exception {

		return new SearchableAssetNamesDisplay() {
			{
			}
		};
	}

	protected SearchableAssetNamesDisplay
			randomIrrelevantSearchableAssetNamesDisplay()
		throws Exception {

		SearchableAssetNamesDisplay
			randomIrrelevantSearchableAssetNamesDisplay =
				randomSearchableAssetNamesDisplay();

		return randomIrrelevantSearchableAssetNamesDisplay;
	}

	protected SearchableAssetNamesDisplay
			randomPatchSearchableAssetNamesDisplay()
		throws Exception {

		return randomSearchableAssetNamesDisplay();
	}

	protected SearchableAssetNamesDisplayResource
		searchableAssetNamesDisplayResource;
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
			BaseSearchableAssetNamesDisplayResourceTestCase.class);

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
	private com.liferay.search.experiences.rest.resource.v1_0.
		SearchableAssetNamesDisplayResource
			_searchableAssetNamesDisplayResource;

}