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
import com.liferay.search.experiences.rest.client.dto.v1_0.TextEmbeddingProviderValidationResult;
import com.liferay.search.experiences.rest.client.http.HttpInvoker;
import com.liferay.search.experiences.rest.client.pagination.Page;
import com.liferay.search.experiences.rest.client.resource.v1_0.TextEmbeddingProviderValidationResultResource;
import com.liferay.search.experiences.rest.client.serdes.v1_0.TextEmbeddingProviderValidationResultSerDes;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
public abstract class
	BaseTextEmbeddingProviderValidationResultResourceTestCase {

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

		_textEmbeddingProviderValidationResultResource.setContextCompany(
			testCompany);

		TextEmbeddingProviderValidationResultResource.Builder builder =
			TextEmbeddingProviderValidationResultResource.builder();

		textEmbeddingProviderValidationResultResource = builder.authentication(
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

		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult1 =
				randomTextEmbeddingProviderValidationResult();

		String json = objectMapper.writeValueAsString(
			textEmbeddingProviderValidationResult1);

		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult2 =
				TextEmbeddingProviderValidationResultSerDes.toDTO(json);

		Assert.assertTrue(
			equals(
				textEmbeddingProviderValidationResult1,
				textEmbeddingProviderValidationResult2));
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

		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult =
				randomTextEmbeddingProviderValidationResult();

		String json1 = objectMapper.writeValueAsString(
			textEmbeddingProviderValidationResult);
		String json2 = TextEmbeddingProviderValidationResultSerDes.toJSON(
			textEmbeddingProviderValidationResult);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult =
				randomTextEmbeddingProviderValidationResult();

		textEmbeddingProviderValidationResult.setErrorMessage(regex);

		String json = TextEmbeddingProviderValidationResultSerDes.toJSON(
			textEmbeddingProviderValidationResult);

		Assert.assertFalse(json.contains(regex));

		textEmbeddingProviderValidationResult =
			TextEmbeddingProviderValidationResultSerDes.toDTO(json);

		Assert.assertEquals(
			regex, textEmbeddingProviderValidationResult.getErrorMessage());
	}

	@Test
	public void testPostTextEmbeddingValidateConfiguration() throws Exception {
		TextEmbeddingProviderValidationResult
			randomTextEmbeddingProviderValidationResult =
				randomTextEmbeddingProviderValidationResult();

		TextEmbeddingProviderValidationResult
			postTextEmbeddingProviderValidationResult =
				testPostTextEmbeddingValidateConfiguration_addTextEmbeddingProviderValidationResult(
					randomTextEmbeddingProviderValidationResult);

		assertEquals(
			randomTextEmbeddingProviderValidationResult,
			postTextEmbeddingProviderValidationResult);
		assertValid(postTextEmbeddingProviderValidationResult);
	}

	protected TextEmbeddingProviderValidationResult
			testPostTextEmbeddingValidateConfiguration_addTextEmbeddingProviderValidationResult(
				TextEmbeddingProviderValidationResult
					textEmbeddingProviderValidationResult)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult,
		List<TextEmbeddingProviderValidationResult>
			textEmbeddingProviderValidationResults) {

		boolean contains = false;

		for (TextEmbeddingProviderValidationResult item :
				textEmbeddingProviderValidationResults) {

			if (equals(textEmbeddingProviderValidationResult, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			textEmbeddingProviderValidationResults + " does not contain " +
				textEmbeddingProviderValidationResult,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult1,
		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult2) {

		Assert.assertTrue(
			textEmbeddingProviderValidationResult1 + " does not equal " +
				textEmbeddingProviderValidationResult2,
			equals(
				textEmbeddingProviderValidationResult1,
				textEmbeddingProviderValidationResult2));
	}

	protected void assertEquals(
		List<TextEmbeddingProviderValidationResult>
			textEmbeddingProviderValidationResults1,
		List<TextEmbeddingProviderValidationResult>
			textEmbeddingProviderValidationResults2) {

		Assert.assertEquals(
			textEmbeddingProviderValidationResults1.size(),
			textEmbeddingProviderValidationResults2.size());

		for (int i = 0; i < textEmbeddingProviderValidationResults1.size();
			 i++) {

			TextEmbeddingProviderValidationResult
				textEmbeddingProviderValidationResult1 =
					textEmbeddingProviderValidationResults1.get(i);
			TextEmbeddingProviderValidationResult
				textEmbeddingProviderValidationResult2 =
					textEmbeddingProviderValidationResults2.get(i);

			assertEquals(
				textEmbeddingProviderValidationResult1,
				textEmbeddingProviderValidationResult2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<TextEmbeddingProviderValidationResult>
			textEmbeddingProviderValidationResults1,
		List<TextEmbeddingProviderValidationResult>
			textEmbeddingProviderValidationResults2) {

		Assert.assertEquals(
			textEmbeddingProviderValidationResults1.size(),
			textEmbeddingProviderValidationResults2.size());

		for (TextEmbeddingProviderValidationResult
				textEmbeddingProviderValidationResult1 :
					textEmbeddingProviderValidationResults1) {

			boolean contains = false;

			for (TextEmbeddingProviderValidationResult
					textEmbeddingProviderValidationResult2 :
						textEmbeddingProviderValidationResults2) {

				if (equals(
						textEmbeddingProviderValidationResult1,
						textEmbeddingProviderValidationResult2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				textEmbeddingProviderValidationResults2 + " does not contain " +
					textEmbeddingProviderValidationResult1,
				contains);
		}
	}

	protected void assertValid(
			TextEmbeddingProviderValidationResult
				textEmbeddingProviderValidationResult)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("errorMessage", additionalAssertFieldName)) {
				if (textEmbeddingProviderValidationResult.getErrorMessage() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"expectedDimensions", additionalAssertFieldName)) {

				if (textEmbeddingProviderValidationResult.
						getExpectedDimensions() == null) {

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

	protected void assertValid(
		Page<TextEmbeddingProviderValidationResult> page) {

		boolean valid = false;

		java.util.Collection<TextEmbeddingProviderValidationResult>
			textEmbeddingProviderValidationResults = page.getItems();

		int size = textEmbeddingProviderValidationResults.size();

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
						TextEmbeddingProviderValidationResult.class)) {

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
		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult1,
		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult2) {

		if (textEmbeddingProviderValidationResult1 ==
				textEmbeddingProviderValidationResult2) {

			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("errorMessage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						textEmbeddingProviderValidationResult1.
							getErrorMessage(),
						textEmbeddingProviderValidationResult2.
							getErrorMessage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"expectedDimensions", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						textEmbeddingProviderValidationResult1.
							getExpectedDimensions(),
						textEmbeddingProviderValidationResult2.
							getExpectedDimensions())) {

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

		if (!(_textEmbeddingProviderValidationResultResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_textEmbeddingProviderValidationResultResource;

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
		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("errorMessage")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					textEmbeddingProviderValidationResult.getErrorMessage()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("expectedDimensions")) {
			sb.append(
				String.valueOf(
					textEmbeddingProviderValidationResult.
						getExpectedDimensions()));

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

	protected TextEmbeddingProviderValidationResult
			randomTextEmbeddingProviderValidationResult()
		throws Exception {

		return new TextEmbeddingProviderValidationResult() {
			{
				errorMessage = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				expectedDimensions = RandomTestUtil.randomInt();
			}
		};
	}

	protected TextEmbeddingProviderValidationResult
			randomIrrelevantTextEmbeddingProviderValidationResult()
		throws Exception {

		TextEmbeddingProviderValidationResult
			randomIrrelevantTextEmbeddingProviderValidationResult =
				randomTextEmbeddingProviderValidationResult();

		return randomIrrelevantTextEmbeddingProviderValidationResult;
	}

	protected TextEmbeddingProviderValidationResult
			randomPatchTextEmbeddingProviderValidationResult()
		throws Exception {

		return randomTextEmbeddingProviderValidationResult();
	}

	protected TextEmbeddingProviderValidationResultResource
		textEmbeddingProviderValidationResultResource;
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
		LogFactoryUtil.getLog(
			BaseTextEmbeddingProviderValidationResultResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.search.experiences.rest.resource.v1_0.
		TextEmbeddingProviderValidationResultResource
			_textEmbeddingProviderValidationResultResource;

}