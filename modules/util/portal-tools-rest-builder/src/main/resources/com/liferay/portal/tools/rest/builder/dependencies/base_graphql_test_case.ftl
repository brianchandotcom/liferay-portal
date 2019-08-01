package ${configYAML.apiPackagePath}.graphql.${escapedVersion}.test;

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName};
</#list>

import ${configYAML.apiPackagePath}.client.http.HttpInvoker;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

import org.apache.log4j.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public abstract class Base${schemaName}GraphQLTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(testGroup.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	<#assign
		javaMethodSignatures = freeMarkerTool.getResourceTestCaseJavaMethodSignatures(configYAML, openAPIYAML, schemaName)
		properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema)
		randomDataTypes = ["Boolean", "Double", "Long", "String"]
	/>

	<#list javaMethodSignatures as javaMethodSignature>
		<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete") && stringUtil.equals(freeMarkerTool.getGraphQLPropertyName(javaMethodSignature), "delete"+schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				${schemaName} ${schemaVarName} = test${schemaName}_add${schemaName}();

				GraphQLField graphQLField = new GraphQLField(
					"mutation",
					new GraphQLField(
						"delete${schemaName}",
						new HashMap<String, Object>() {
							{
								put("${schemaVarName}Id", ${schemaVarName}.getId());
							}
						}));

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					invoke(graphQLField.toString()));

				JSONObject dataJSONObject = jsonObject.getJSONObject("data");

				Assert.assertTrue(
					dataJSONObject.getBoolean("delete${schemaName}"));

				try (CaptureAppender captureAppender =
						 Log4JLoggerTestUtil.configureLog4JLogger(
							 "graphql.execution.SimpleDataFetcherExceptionHandler", Level.WARN)) {

					graphQLField = new GraphQLField(
						"query",
						new GraphQLField(
							"${schemaVarName}",
							new HashMap<String, Object>() {
								{
									put("${schemaVarName}Id", ${schemaVarName}.getId())
									;
								}
							},
							new GraphQLField("id")));

					jsonObject = JSONFactoryUtil.createJSONObject(
						invoke(graphQLField.toString()));

					JSONArray errors = jsonObject.getJSONArray("errors");

					Assert.assertTrue(errors.length() > 0);
				}
			}
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?contains("Page<") && stringUtil.equals(freeMarkerTool.getGraphQLPropertyName(javaMethodSignature), schemaVarNames)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				List<GraphQLField> graphQLFields = new ArrayList<>();

				List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

				graphQLFields.add(new GraphQLField("items", itemsGraphQLFields.toArray(new GraphQLField[0])));

				graphQLFields.add(new GraphQLField("page"));
				graphQLFields.add(new GraphQLField("totalCount"));

				GraphQLField graphQLField = new GraphQLField(
					"query",
					new GraphQLField(
						"${schemaVarNames}",
						new HashMap<String, Object>() {
							{
								put("page", 1);
								put("pageSize", 2);
								<#if javaMethodSignature.methodName?contains("Site")>
									put("siteId", testGroup.getGroupId());
								</#if>
							}
						},
						graphQLFields.toArray(new GraphQLField[0])));

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(invoke(graphQLField.toString()));

				JSONObject dataJSONObject = jsonObject.getJSONObject("data");

				JSONObject ${schemaVarNames}JSONObject = dataJSONObject.getJSONObject("${schemaVarNames}");

				Assert.assertEquals(0, ${schemaVarNames}JSONObject.get("totalCount"));

				${schemaName} ${schemaVarName}1 = test${schemaName}_add${schemaName}();
				${schemaName} ${schemaVarName}2 = test${schemaName}_add${schemaName}();

				jsonObject = JSONFactoryUtil.createJSONObject(invoke(graphQLField.toString()));

				dataJSONObject = jsonObject.getJSONObject("data");

				${schemaVarNames}JSONObject = dataJSONObject.getJSONObject("${schemaVarNames}");

				Assert.assertEquals(2, ${schemaVarNames}JSONObject.get("totalCount"));

				assertEqualsIgnoringOrder(Arrays.asList(${schemaVarName}1, ${schemaVarName}2), ${schemaVarNames}JSONObject.getJSONArray("items"));
			}

		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?ends_with(schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if properties?keys?seq_contains("id")>
					${schemaName} ${schemaVarName} = test${schemaName}_add${schemaName}();

					List<GraphQLField> graphQLFields = getGraphQLFields();

					GraphQLField graphQLField = new GraphQLField(
						"query",
						new GraphQLField(
							"${freeMarkerTool.getGraphQLPropertyName(javaMethodSignature)}",
							new HashMap<String, Object>() {
								{
									put("${schemaVarName}Id", ${schemaVarName}.getId());
								}
							},
							graphQLFields.toArray(new GraphQLField[0])));

					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(invoke(graphQLField.toString()));

					JSONObject dataJSONObject = jsonObject.getJSONObject("data");

					Assert.assertTrue(equals(${schemaVarName}, dataJSONObject.getJSONObject("${freeMarkerTool.getGraphQLPropertyName(javaMethodSignature)}")));
				<#else>
					Assert.assertTrue(true);
				</#if>
			}
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post") && stringUtil.equals(javaMethodSignature.methodName, "postSite"+schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				${schemaName} random${schemaName} = random${schemaName}();

				${schemaName} ${schemaVarName} = test${schemaName}_add${schemaName}(random${schemaName});

				Assert.assertTrue(
					equals(
						random${schemaName},
						JSONFactoryUtil.createJSONObject(JSONFactoryUtil.serialize(${schemaVarName}))));
			}
		</#if>
	</#list>

	protected void assertEqualsIgnoringOrder(List<${schemaName}> ${schemaVarNames}, JSONArray jsonArray) {
		for (${schemaName} ${schemaVarName} : ${schemaVarNames}) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equals(${schemaVarName}, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(jsonArray + " does not contain " + ${schemaVarName}, contains);
		}
	}

	protected boolean equals(${schemaName} ${schemaVarName}, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			<#list properties?keys as propertyName>
				<#if randomDataTypes?seq_contains(properties[propertyName])>
					if (Objects.equals("${propertyName}", fieldName)) {
						<#assign capitalizedPropertyName = propertyName?cap_first />

						if (!Objects.equals(${schemaVarName}.get${capitalizedPropertyName}(), (${properties[propertyName]})jsonObject.get${properties[propertyName]}("${propertyName}"))) {
							return false;
						}

						continue;
					}
				</#if>
			</#list>

			throw new IllegalArgumentException("Invalid field name " + fieldName);
		}

		return true;
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("id"));

		for (String additionalAssertFieldName : getAdditionalAssertFieldNames()) {
			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
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

	protected ${schemaName} random${schemaName}() throws Exception {
		return new ${schemaName}() {
			{
				<#list properties?keys as propertyName>
					<#if stringUtil.equals(propertyName, "siteId")>
						${propertyName} = testGroup.getGroupId();
					<#elseif randomDataTypes?seq_contains(properties[propertyName])>
						${propertyName} = RandomTestUtil.random${properties[propertyName]}();
					<#elseif stringUtil.equals(properties[propertyName], "Date")>
						${propertyName} = RandomTestUtil.nextDate();
					</#if>
				</#list>
			}
		};
	}

	<#if freeMarkerTool.hasJavaMethodSignature(javaMethodSignatures, "postSite"+schemaName)>
		protected ${schemaName} test${schemaName}_add${schemaName}() throws Exception {
			return test${schemaName}_add${schemaName}(random${schemaName}());
		}

		protected ${schemaName} test${schemaName}_add${schemaName}(${schemaName} ${schemaVarName}) throws Exception {
			StringBuilder sb = new StringBuilder("{");

			for (String field : getAdditionalAssertFieldNames()) {
			<#list properties?keys as propertyName>
				<#if randomDataTypes?seq_contains(properties[propertyName])>
					if (Objects.equals("${propertyName}", field)) {
						sb.append(field);
						sb.append(":");

						Object value = ${schemaVarName}.get${propertyName?cap_first}();

						if (value instanceof String) {
							sb.append("\"");
							sb.append(value);
							sb.append("\"");
						}
						else {
							sb.append(value);
						}
						sb.append(",");
					}
				</#if>
			</#list>
			}

			sb.append("}");

			List<GraphQLField> graphQLFields = getGraphQLFields();

			GraphQLField graphQLField = new GraphQLField(
				"mutation",
				new GraphQLField(
					"createSite${schemaName}",
					new HashMap<String, Object>() {
						{
							put("siteId", testGroup.getGroupId());
							put("${schemaVarName}", sb.toString());
						}
					},
					graphQLFields.toArray(new GraphQLField[0])
				)
			);

			JSONDeserializer<${schemaName}> jsonDeserializer =
				JSONFactoryUtil.createJSONDeserializer();

			String object = invoke(graphQLField.toString());

			JSONObject jsonObject =
				JSONFactoryUtil.createJSONObject(object);

			String data = jsonObject.getJSONObject("data" ).getJSONObject(
				"createSite${schemaName}" ).toString();

			return jsonDeserializer.deserialize(data, ${schemaName}.class);
		}
	<#else>
		protected ${schemaName} test${schemaName}_add${schemaName}() throws Exception {
			throw new UnsupportedOperationException("This method needs to be implemented");
		}
	</#if>

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

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

}