package ${configYAML.apiPackagePath}.resource.${versionDirName}.test;

<#compress>
	<#list allSchemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URL;

import java.util.Date;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public abstract class Base${schemaName}ResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o${configYAML.application.baseURI}/${openAPIYAML.info.version}");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	<#list freeMarkerTool.getJavaMethodSignatures(openAPIYAML, schemaName) as javaMethodSignature>
		@Test
		public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
			Assert.assertTrue(true);
		}
	</#list>

	<#list freeMarkerTool.getJavaMethodSignatures(openAPIYAML, schemaName) as javaMethodSignature>
		<@compress single_line=true>
			protected Response invoke${javaMethodSignature.methodName?cap_first}(
				<#list javaMethodSignature.javaParameters as javaParameter>
					${javaParameter.parameterType} ${javaParameter.parameterName}

					<#if javaParameter_has_next>
						,
					</#if>
				</#list>
			) throws Exception {
		</@compress>

		RequestSpecification requestSpecification = _createRequestSpecification();

		<#assign parametersContent>
			<@compress single_line=true>
				<#list javaMethodSignature.javaParameters as javaParameter>
					${javaParameter.parameterName}

					<#if javaParameter_has_next>
						,
					</#if>
				</#list>
			</@compress>
		</#assign>

		<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post", "put") && parametersContent?ends_with(", ${schemaName?uncap_first}")>
			return requestSpecification.body(
				${schemaName?uncap_first}
			).when(
			).${freeMarkerTool.getHTTPMethod(javaMethodSignature.operation)}(
				_resourceURL + "${javaMethodSignature.path}",
				${stringUtil.replaceLast(parametersContent, ", ${schemaName?uncap_first}", "")}
			);
		<#else>
			return requestSpecification.when(
			).${freeMarkerTool.getHTTPMethod(javaMethodSignature.operation)}(
				_resourceURL + "${javaMethodSignature.path}",
				${stringUtil.replaceLast(parametersContent, ", pagination", "")}
			);
		</#if>

		}
	</#list>

	protected ${schemaName} random${schemaName}() {
		return new ${schemaName}Impl() {
			{
				<#compress>
					<#list freeMarkerTool.getJavaParameters(schema) as javaParameter>
						<#assign randomDataTypes = ["Boolean", "Double", "Long", "String"] />

						<#if randomDataTypes?seq_contains(javaParameter.parameterType)>
							${javaParameter.parameterName} = RandomTestUtil.random${javaParameter.parameterType}();
						<#elseif stringUtil.equals(javaParameter.parameterType, "Date")>
							${javaParameter.parameterName} = RandomTestUtil.nextDate();
						</#if>
					</#list>
				</#compress>
			}
		};
	}

	protected Group testGroup;

	protected class ${schemaName}Impl implements ${schemaName} {

		<#list javaTool.getJavaParameters(schema) as javaParameter>
			public ${javaParameter.parameterType} get${javaParameter.parameterName?cap_first}() {
				return ${javaParameter.parameterName};
			}

			public void set${javaParameter.parameterName?cap_first}(${javaParameter.parameterType} ${javaParameter.parameterName}) {
				this.${javaParameter.parameterName} = ${javaParameter.parameterName};
			}

			@JsonIgnore
			public void set${javaParameter.parameterName?cap_first}(UnsafeSupplier<${javaParameter.parameterType}, Throwable> ${javaParameter.parameterName}UnsafeSupplier) {
				try {
					${javaParameter.parameterName} = ${javaParameter.parameterName}UnsafeSupplier.get();
				}
				catch (Throwable t) {
					throw new RuntimeException(t);
				}
			}

			@JsonProperty
			protected ${javaParameter.parameterType} ${javaParameter.parameterName};
		</#list>

	}

	private RequestSpecification _createRequestSpecification() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		);
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}