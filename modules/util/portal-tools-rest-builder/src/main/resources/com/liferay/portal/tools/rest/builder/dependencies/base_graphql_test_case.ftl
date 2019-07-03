package ${configYAML.apiPackagePath}.graphql.${escapedVersion}.test;

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName};
</#list>

import ${configYAML.apiPackagePath}.client.http.HttpInvoker;

import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.graphql.query.GraphQLField;
import com.liferay.portal.vulcan.util.GraphQLQueryUtil;

import java.io.IOException;

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

					Map<String, Object> parameterMap = new HashMap<String, Object>() {
						{
							put("${schemaVarName}Id", post${schemaName}.getId());
						}
					};

					List<GraphQLField> graphQLFields = new ArrayList<>();

					graphQLFields.add(GraphQLQueryUtil.field("id"));

					for (String additionalAssertFieldName : getAdditionalAssertFieldNames()) {
						graphQLFields.add(GraphQLQueryUtil.field(additionalAssertFieldName));
					}

					GraphQLField graphQLField = GraphQLQueryUtil.field("query",
						GraphQLQueryUtil.field("${javaMethodSignature.methodName}",
						parameterMap, graphQLFields.toArray(new GraphQLField[0])
					));

					JSONObject responseJSONObject = new JSONObjectImpl(
						_invoke(graphQLField.toString()));

					JSONObject dataJSONObject =
						responseJSONObject.getJSONObject("data");

					JSONObject jsonObject =
						dataJSONObject.getJSONObject("${javaMethodSignature.methodName}");

					Assert.assertTrue(equals(post${schemaName}, jsonObject));
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

	protected boolean equals(${schemaName} ${schemaVarName}, JSONObject jsonObject) {

		List<String> fields = new ArrayList(Arrays.asList(getAdditionalAssertFieldNames()));

		fields.add("id");

		for (String field : fields) {
			<#list properties?keys as propertyName>

				<#if randomDataTypes?seq_contains(properties[propertyName])>

					if (Objects.equals("${propertyName}", field)) {
						<#assign capitalizedPropertyName = propertyName?cap_first />

						if (!Objects.equals(${schemaVarName}.get${capitalizedPropertyName}(), (${properties[propertyName]})jsonObject.get${properties[propertyName]}("${propertyName}"))) {
							return false;
						}

						continue;
					}
				</#if>
			</#list>

			throw new IllegalArgumentException("Invalid additional assert field name " + field);
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

	private String _invoke(String query) throws IOException {
		JSONObject jsonObject = JSONUtil.put("query", query);

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();
		httpInvoker.body(jsonObject.toString(), "application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected Company testCompany;
	protected Group testGroup;
}