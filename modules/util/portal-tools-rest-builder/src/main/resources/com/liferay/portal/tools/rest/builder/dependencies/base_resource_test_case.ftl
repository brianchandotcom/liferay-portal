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
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import java.util.Date;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public abstract class Base${schemaName}ResourceTestCase {

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o${configYAML.application.baseURI}/${openAPIYAML.info.version}");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	<#list freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName, false) as javaMethodSignature>
		@Test
		public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
			Assert.assertTrue(true);
		}
	</#list>

	protected void assertResponseCode(int expectedResponseCode, Http.Response actualResponse) {
		Assert.assertEquals(expectedResponseCode, actualResponse.getResponseCode());
	}

	<#list freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName, false) as javaMethodSignature>
		protected ${javaMethodSignature.returnType} invoke${javaMethodSignature.methodName?cap_first}(
				${freeMarkerTool.getResourceParameters(javaMethodSignature.javaParameters, false)})
			throws Exception {

			Http.Options options = _createHttpOptions();

			<#assign arguments = freeMarkerTool.getResourceArguments(javaMethodSignature.javaParameters) />

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post", "put") && arguments?ends_with(",${schemaName?uncap_first}")>
				options.setBody(_inputObjectMapper.writeValueAsString(${schemaName?uncap_first}), ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
				options.setDelete(true);
			</#if>

			options.setLocation(_resourceURL + _toPath("${javaMethodSignature.path}", ${javaMethodSignature.javaParameters[0].parameterName}));

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post")>
				options.setPost(true);
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put")>
				options.setPut(true);
			</#if>

			<#if stringUtil.equals(javaMethodSignature.returnType, "boolean")>
				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), Boolean.class);
			<#elseif javaMethodSignature.returnType?contains("Page<")>
				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), Page.class);
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "String")>
				return HttpUtil.URLtoString(options);
			<#else>
				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), ${javaMethodSignature.returnType}Impl.class);
			</#if>
		}

		protected Http.Response invoke${javaMethodSignature.methodName?cap_first}Response(
				${freeMarkerTool.getResourceParameters(javaMethodSignature.javaParameters, false)})
			throws Exception {

			Http.Options options = _createHttpOptions();

			<#assign arguments = freeMarkerTool.getResourceArguments(javaMethodSignature.javaParameters) />

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post", "put") && arguments?ends_with(",${schemaName?uncap_first}")>
				options.setBody(_inputObjectMapper.writeValueAsString(${schemaName?uncap_first}), ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
				options.setDelete(true);
			</#if>

			options.setLocation(_resourceURL + _toPath("${javaMethodSignature.path}", ${javaMethodSignature.javaParameters[0].parameterName}));

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post")>
				options.setPost(true);
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put")>
				options.setPut(true);
			</#if>

			HttpUtil.URLtoString(options);

			return options.getResponse();
		}
	</#list>

	protected ${schemaName} random${schemaName}() {
		return new ${schemaName}Impl() {
			{
				<#assign randomDataTypes = ["Boolean", "Double", "Long", "String"] />

				<#list freeMarkerTool.getDTOJavaParameters(configYAML, openAPIYAML, schema, false) as javaParameter>
					<#if randomDataTypes?seq_contains(javaParameter.parameterType)>
						${javaParameter.parameterName} = RandomTestUtil.random${javaParameter.parameterType}();
					<#elseif stringUtil.equals(javaParameter.parameterType, "Date")>
						${javaParameter.parameterName} = RandomTestUtil.nextDate();
					</#if>
				</#list>
			}
		};
	}

	protected Group testGroup;

	protected static class ${schemaName}Impl implements ${schemaName} {

		<#list freeMarkerTool.getDTOJavaParameters(configYAML, openAPIYAML, schema, false) as javaParameter>
			public ${javaParameter.parameterType} get${javaParameter.parameterName?cap_first}() {
				return ${javaParameter.parameterName};
			}

			public void set${javaParameter.parameterName?cap_first}(${javaParameter.parameterType} ${javaParameter.parameterName}) {
				this.${javaParameter.parameterName} = ${javaParameter.parameterName};
			}

			@JsonIgnore
			public void set${javaParameter.parameterName?cap_first}(
				UnsafeSupplier<${javaParameter.parameterType}, Throwable> ${javaParameter.parameterName}UnsafeSupplier) {

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

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(userNameAndPassword.getBytes());

		options.addHeader("Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}