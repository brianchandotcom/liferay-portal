package ${configYAML.apiPackagePath}.resource.${versionDirName}.test;

<#compress>
	<#list allSchemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

		_resourceURL = new URL("http://localhost:8080/o${configYAML.application.baseURI}/${openAPIYAML.info.version}");
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
		<#assign
			parameters = freeMarkerTool.getResourceParameters(javaMethodSignature.javaParameters, false)
			privateLocationMethod = false
		/>

		<#if parameters?contains("Filter filter") && parameters?contains("Sort[] sorts")>
			<#assign
				parameters = parameters?replace("Filter filter", "String filter")?replace("Sort[] sorts", "String sorts")
				privateLocationMethod = true
			/>
		</#if>

		protected ${javaMethodSignature.returnType} invoke${javaMethodSignature.methodName?cap_first}(${parameters})
			throws Exception {

			Http.Options options = _createHttpOptions();

			<#assign arguments = freeMarkerTool.getResourceArguments(javaMethodSignature.javaParameters) />

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post", "put") && arguments?ends_with(",${schemaVarName}")>
				options.setBody(_inputObjectMapper.writeValueAsString(${schemaVarName}), ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
				options.setDelete(true);
			</#if>

			<#if privateLocationMethod>
				options.setLocation(_${javaMethodSignature.methodName?remove_ending("Page")}Location(${arguments}));
			<#else>
				options.setLocation(_resourceURL + _toPath("${javaMethodSignature.path}", ${javaMethodSignature.javaParameters[0].parameterName}));
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post")>
				options.setPost(true);
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put")>
				options.setPut(true);
			</#if>

			<#if stringUtil.equals(javaMethodSignature.returnType, "boolean")>
				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), Boolean.class);
			<#elseif javaMethodSignature.returnType?contains("Page<")>
				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), new TypeReference<Page<${schemaName}>>() {});
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "String")>
				return HttpUtil.URLtoString(options);
			<#else>
				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), ${javaMethodSignature.returnType}.class);
			</#if>
		}

		protected Http.Response invoke${javaMethodSignature.methodName?cap_first}Response(${parameters})
			throws Exception {

			Http.Options options = _createHttpOptions();

			<#assign arguments = freeMarkerTool.getResourceArguments(javaMethodSignature.javaParameters) />

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post", "put") && arguments?ends_with(",${schemaVarName}")>
				options.setBody(_inputObjectMapper.writeValueAsString(${schemaVarName}), ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
				options.setDelete(true);
			</#if>

			<#if privateLocationMethod>
				options.setLocation(_${javaMethodSignature.methodName?remove_ending("Page")}Location(${arguments}));
			<#else>
				options.setLocation(_resourceURL + _toPath("${javaMethodSignature.path}", ${javaMethodSignature.javaParameters[0].parameterName}));
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post")>
				options.setPost(true);
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put")>
				options.setPut(true);
			</#if>

			HttpUtil.URLtoString(options);

			return options.getResponse();
		}

		<#if privateLocationMethod>
			private String _${javaMethodSignature.methodName?remove_ending("Page")}Location(${parameters}) {
				Http http = new HttpImpl();

				String url = _resourceURL + _toPath(
					"${javaMethodSignature.path}", ${javaMethodSignature.javaParameters[0].parameterName});

				url = http.addParameter(url, "filter", filter);
				url = http.addParameter(url, "page", pagination.getPageNumber());
				url = http.addParameter(url, "pageSize", pagination.getItemsPerPage());
				url = http.addParameter(url, "sort", sort);

				return url;
			}
		</#if>
	</#list>

	protected void assertEquals(${schemaName} ${schemaVarName}1, ${schemaName} ${schemaVarName}2) {
		Assert.assertTrue(${schemaVarName}1 + " does not equal " + ${schemaVarName}2, equals(${schemaVarName}1, ${schemaVarName}2));
	}

	protected void assertEquals(List<${schemaName}> ${schemaVarNames}1, List<${schemaName}> ${schemaVarNames}2) {
		Assert.assertEquals(${schemaVarNames}1.size(), ${schemaVarNames}2.size());

		for (int i = 0; i < ${schemaVarNames}1.size(); i++) {
			${schemaName} ${schemaVarName}1 = ${schemaVarNames}1.get(i);
			${schemaName} ${schemaVarName}2 = ${schemaVarNames}2.get(i);

			assertEquals(${schemaVarName}1, ${schemaVarName}2);
		}
	}

	protected void assertEqualsIgnoringOrder(List<${schemaName}> ${schemaVarNames}1, List<${schemaName}> ${schemaVarNames}2) {
		Assert.assertEquals(${schemaVarNames}1.size(), ${schemaVarNames}2.size());

		for (${schemaName} ${schemaVarName}1 : ${schemaVarNames}1) {
			boolean contains = false;

			for (${schemaName} ${schemaVarName}2 : ${schemaVarNames}2) {
				if (equals(${schemaVarName}1, ${schemaVarName}2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(${schemaVarNames}2 + " does not contain " + ${schemaVarName}1, contains);
		}
	}

	protected boolean equals(${schemaName} ${schemaVarName}1, ${schemaName} ${schemaVarName}2) {
		if (${schemaVarName}1 == ${schemaVarName}2) {
			return true;
		}

		return false;
	}

	protected ${schemaName} random${schemaName}() {
		return new ${schemaName}() {
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

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public int getItemsPerPage() {
			return itemsPerPage;
		}

		public int getLastPageNumber() {
			return lastPageNumber;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public int getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected int itemsPerPage;

		@JsonProperty
		protected int lastPageNumber;

		@JsonProperty("page")
		protected int pageNumber;

		@JsonProperty
		protected int totalCount;

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