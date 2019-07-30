package ${configYAML.apiPackagePath}.graphql.${escapedVersion}.test;

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName};
</#list>

import ${configYAML.apiPackagePath}.client.http.HttpInvoker;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

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
		<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?ends_with(schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if properties?keys?seq_contains("id")>
					${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

					List<GraphQLField> graphQLFields = new ArrayList<>();

					graphQLFields.add(new GraphQLField("id"));

					for (String additionalAssertFieldName : getAdditionalAssertFieldNames()) {
						graphQLFields.add(new GraphQLField(additionalAssertFieldName));
					}

					GraphQLField graphQLField = new GraphQLField(
						"query",
						new GraphQLField(
							"${freeMarkerTool.getGraphQLPropertyName(javaMethodSignature)}",
							new HashMap<String, Object>() {
								{
									put("${schemaVarName}Id", post${schemaName}.getId());
								}
							},
							graphQLFields.toArray(new GraphQLField[0])));

					JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(invoke(graphQLField.toString()));

					JSONObject dataJSONObject = responseJSONObject.getJSONObject("data");

					Assert.assertTrue(equals(post${schemaName}, dataJSONObject.getJSONObject("${freeMarkerTool.getGraphQLPropertyName(javaMethodSignature)}")));
				<#else>
					Assert.assertTrue(true);
				</#if>
			}

			<#if properties?keys?seq_contains("id")>
				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
					throw new UnsupportedOperationException("This method needs to be implemented");
				}
			</#if>
		</#if>
	</#list>

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		JSONObject jsonObject = JSONUtil.put("query", query);

		httpInvoker.body(jsonObject.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected boolean equals(${schemaName} ${schemaVarName}, JSONObject jsonObject) {
		List<String> fieldNames = new ArrayList(Arrays.asList(getAdditionalAssertFieldNames()));

		fieldNames.add("id");

		for (String fieldName : fieldNames) {
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

	protected Company testCompany;
	protected Group testGroup;

}