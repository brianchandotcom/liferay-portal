package ${configYAML.apiPackagePath}.resource.${escapedVersion}.test;

<#list allExternalSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName};
	import ${configYAML.apiPackagePath}.client.resource.${escapedVersion}.${schemaName}Resource;
	import ${configYAML.apiPackagePath}.client.serdes.${escapedVersion}.${schemaName}SerDes;
</#list>

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName};
	import ${configYAML.apiPackagePath}.client.resource.${escapedVersion}.${schemaName}Resource;
	import ${configYAML.apiPackagePath}.client.serdes.${escapedVersion}.${schemaName}SerDes;
</#list>

import ${configYAML.apiPackagePath}.client.http.HttpInvoker;
import ${configYAML.apiPackagePath}.client.pagination.Page;
import ${configYAML.apiPackagePath}.client.pagination.Pagination;
import ${configYAML.apiPackagePath}.client.permission.Permission;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

<#assign
	javaMethodSignatures = freeMarkerTool.getResourceTestCaseJavaMethodSignatures(configYAML, openAPIYAML, schemaName)

	generateDepotEntry = freeMarkerTool.containsJavaMethodSignature(javaMethodSignatures, "AssetLibrary")
/>

<#if generateDepotEntry>
	import com.liferay.depot.model.DepotEntry;
	import com.liferay.depot.service.DepotEntryLocalServiceUtil;
</#if>

import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.io.File;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public abstract class Base${schemaName}ResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule = new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(testGroup.getCompanyId());

		<#if generateDepotEntry>
			testDepotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
				Collections.singletonMap(LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				null, new ServiceContext() {
					{
						setCompanyId(testGroup.getCompanyId());
						setUserId(TestPropsValues.getUserId());
					}
				});
		</#if>

		_${schemaVarName}Resource.setContextCompany(testCompany);

		${schemaName}Resource.Builder builder = ${schemaName}Resource.builder();

		${schemaVarName}Resource = builder.authentication(
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
				configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		${schemaName} ${schemaVarName}1 = random${schemaName}();

		String json = objectMapper.writeValueAsString(${schemaVarName}1);

		${schemaName} ${schemaVarName}2 = ${schemaName}SerDes.toDTO(json);

		Assert.assertTrue(equals(${schemaVarName}1, ${schemaVarName}2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		${schemaName} ${schemaVarName} = random${schemaName}();

		String json1 = objectMapper.writeValueAsString(${schemaVarName});
		String json2 = ${schemaName}SerDes.toJSON(${schemaVarName});

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		${schemaName} ${schemaVarName} = random${schemaName}();

		<#list properties?keys as propertyName>
			<#if stringUtil.equals(properties[propertyName], "String")>
				${schemaVarName}.set${propertyName?cap_first}(regex);
			</#if>
		</#list>

		String json = ${schemaName}SerDes.toJSON(${schemaVarName});

		Assert.assertFalse(json.contains(regex));

		${schemaVarName} = ${schemaName}SerDes.toDTO(json);

		<#list properties?keys as propertyName>
			<#if stringUtil.equals(properties[propertyName], "String")>
				Assert.assertEquals(regex, ${schemaVarName}.get${propertyName?cap_first}());
			</#if>
		</#list>
	}

	<#assign
		enumSchemas = freeMarkerTool.getDTOEnumSchemas(openAPIYAML, schema)
		generateGetMultipartFilesMethod = false
		generateSearchTestRule = false
		properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema)
		randomDataTypes = ["Boolean", "Double", "Integer", "Long", "String"]
	/>

	<#list javaMethodSignatures as javaMethodSignature>
		<#assign
			arguments = freeMarkerTool.getResourceTestCaseArguments(javaMethodSignature.javaMethodParameters)
			parameters = freeMarkerTool.getResourceTestCaseParameters(javaMethodSignature.javaMethodParameters, openAPIYAML, javaMethodSignature.operation, false)
		/>

		<#if stringUtil.endsWith(javaMethodSignature.methodName, schemaName + "Batch")>
			<#continue>
		</#if>

		<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if properties?keys?seq_contains("id")>
					@SuppressWarnings("PMD.UnusedLocalVariable")
					${schemaName} ${schemaVarName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

					assertHttpResponseStatusCode(204, ${schemaVarName}Resource.${javaMethodSignature.methodName}HttpResponse(

					<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if !javaMethodParameter?is_first>
							,
						</#if>

						<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
							<#if stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
								${schemaVarName}.getId()
							<#elseif properties?keys?seq_contains(javaMethodParameter.parameterName)>
								${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}()
							<#else>
								null
							</#if>
						<#else>
							null
						</#if>
					</#list>

					));

					<#if freeMarkerTool.hasJavaMethodSignature(javaMethodSignatures, "get" + javaMethodSignature.methodName?remove_beginning("delete"))>
						assertHttpResponseStatusCode(404, ${schemaVarName}Resource.get${javaMethodSignature.methodName?remove_beginning("delete")}HttpResponse(

						<#assign
							getJavaMethodSignature = freeMarkerTool.getJavaMethodSignature(javaMethodSignatures, "get" + javaMethodSignature.methodName?remove_beginning("delete"))
						/>

						<#list getJavaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
								${schemaVarName}.getId()
							<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && properties?keys?seq_contains(javaMethodParameter.parameterName)>
								${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}()
							<#else>
								null
							</#if>
							<#sep>, </#sep>
						</#list>

						));

						assertHttpResponseStatusCode(404, ${schemaVarName}Resource.get${javaMethodSignature.methodName?remove_beginning("delete")}HttpResponse(

						<#list getJavaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
								<#if stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Double")>
									0D
								<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Integer")>
									0
								<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Long")>
									0L
								<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
									"-"
								<#else>
									null
								</#if>
							<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && properties?keys?seq_contains(javaMethodParameter.parameterName)>
								${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}()
							<#else>
								null
							</#if>
							<#sep>, </#sep>
						</#list>

						));
					</#if>
				<#else>
					Assert.assertTrue(false);
				</#if>
			}

			<#if properties?keys?seq_contains("id")>
				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
					<#if freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, "assetLibraryId", schemaName) || freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName)>
						<#assign postSchemaJavaMethodSignature = freeMarkerTool.getPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName) />

						return ${schemaVarName}Resource.postSite${schemaName}(testGroup.getGroupId(), random${schemaName}()

						<#if freeMarkerTool.hasRequestBodyMediaType(postSchemaJavaMethodSignature, "multipart/form-data")>
							<#assign generateGetMultipartFilesMethod = true />

							, getMultipartFiles()
						</#if>

						);
					<#else>
						throw new UnsupportedOperationException("This method needs to be implemented");
					</#if>
				}
			</#if>
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?contains("Page<")>
			<#if (javaMethodSignature.javaMethodParameters?size == 0) || stringUtil.equals(javaMethodSignature.javaMethodParameters[0].parameterName, "filter") || stringUtil.equals(javaMethodSignature.javaMethodParameters[0].parameterName, "pagination") || stringUtil.equals(javaMethodSignature.javaMethodParameters[0].parameterName, "sorts")>
				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					Assert.assertTrue(false);
				}
			<#elseif javaMethodSignature.methodName?contains("Permission")>
				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					<#if freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, javaMethodSignature.pathJavaMethodParameters[0].parameterName, schemaName)>
						@SuppressWarnings("PMD.UnusedLocalVariable")
						${schemaName} post${schemaName} = test${freeMarkerTool.getPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName).methodName?cap_first}_add${schemaName}(random${schemaName}());

						Page<Permission> page = ${schemaVarName}Resource.${javaMethodSignature.methodName}(testGroup.getGroupId(), RoleConstants.GUEST);

						Assert.assertNotNull(page);
					<#else>
						Assert.assertTrue(false);
					</#if>
				}
			<#elseif !javaMethodSignature.methodName?contains("Permission")>
				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					Page<${schemaName}> page = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

					<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if !javaMethodParameter?is_first>
							,
						</#if>

						<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
							Pagination.of(1, 2)
						<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
							test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}()
						<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
							RandomTestUtil.randomString()
						<#elseif stringUtil.equals(javaMethodParameter.parameterType, "boolean")>
							RandomTestUtil.randomBoolean()
						<#elseif stringUtil.equals(javaMethodParameter.parameterType, "double")>
							RandomTestUtil.randomDouble()
						<#elseif stringUtil.equals(javaMethodParameter.parameterType, "long")>
							RandomTestUtil.randomLong()
						<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.util.Date")>
							RandomTestUtil.nextDate()
						<#else>
							null
						</#if>
					</#list>

					);

					Assert.assertEquals(0, page.getTotalCount());

					<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
						${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
						${javaMethodParameter.parameterType} irrelevant${javaMethodParameter.parameterName?cap_first} = test${javaMethodSignature.methodName?cap_first}_getIrrelevant${javaMethodParameter.parameterName?cap_first}();
					</#list>

					<#if freeMarkerTool.hasPathParameter(javaMethodSignature)>
						if (<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
								<#if !javaMethodParameter?is_first>
									&&
								</#if>

								(irrelevant${javaMethodParameter.parameterName?cap_first} != null)
							</#list>) {

							${schemaName} irrelevant${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

							<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
								irrelevant${javaMethodParameter.parameterName?cap_first},
							</#list>

							randomIrrelevant${schemaName}());

							page = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

							<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
								<#if !javaMethodParameter?is_first>
									,
								</#if>

								<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
									Pagination.of(1, 2)
								<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
									irrelevant${javaMethodParameter.parameterName?cap_first}
								<#else>
									null
								</#if>
							</#list>

							);

							Assert.assertEquals(1, page.getTotalCount());

							assertEquals(Arrays.asList(irrelevant${schemaName}), (List<${schemaName}>)page.getItems());
							assertValid(page);
						}
					</#if>

					${schemaName} ${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

					<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
						${javaMethodParameter.parameterName},
					</#list>

					random${schemaName}());

					${schemaName} ${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

					<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
						${javaMethodParameter.parameterName},
					</#list>

					random${schemaName}());

					page = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

					<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if !javaMethodParameter?is_first>
							,
						</#if>

						<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
							Pagination.of(1, 2)
						<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
							${javaMethodParameter.parameterName}
						<#else>
							null
						</#if>
					</#list>

					);

					Assert.assertEquals(2, page.getTotalCount());

					assertEqualsIgnoringOrder(Arrays.asList(${schemaVarName}1, ${schemaVarName}2), (List<${schemaName}>)page.getItems());
					assertValid(page);

					<#if freeMarkerTool.hasJavaMethodSignature(javaMethodSignatures, "delete" + schemaName)>
						<#assign deleteJavaMethodSignature = freeMarkerTool.getJavaMethodSignature(javaMethodSignatures, "delete" + schemaName) />

						<#if properties?keys?seq_contains("id")>
							${schemaVarName}Resource.delete${schemaName}(
								<#list deleteJavaMethodSignature.javaMethodParameters as javaMethodParameter>
									<#if freeMarkerTool.isPathParameter(javaMethodParameter, deleteJavaMethodSignature.operation) && stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
										${schemaVarName}1.getId()
									<#else>
										null
									</#if>
									<#sep>, </#sep>
								</#list>);

							${schemaVarName}Resource.delete${schemaName}(
								<#list deleteJavaMethodSignature.javaMethodParameters as javaMethodParameter>
									<#if freeMarkerTool.isPathParameter(javaMethodParameter, deleteJavaMethodSignature.operation) && stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
										${schemaVarName}2.getId()
									<#else>
										null
									</#if>
									<#sep>, </#sep>
								</#list>);
						</#if>
					</#if>
				}

				<#if parameters?contains("Filter filter")>
					<#assign generateSearchTestRule = true />

					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithFilterDateTimeEquals() throws Exception {
						List<EntityField> entityFields = getEntityFields(EntityField.Type.DATE_TIME);

						if (entityFields.isEmpty()) {
							return;
						}

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
						</#list>

						${schemaName} ${schemaVarName}1 = random${schemaName}();

						${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterName},
						</#list>

						${schemaVarName}1);

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> page = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

							<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
								<#if !javaMethodParameter?is_first>
									,
								</#if>

								<#if stringUtil.equals(javaMethodParameter.parameterName, "filter")>
									getFilterString(entityField, "between", ${schemaVarName}1)
								<#elseif stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
									Pagination.of(1, 2)
								<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
									${javaMethodParameter.parameterName}
								<#else>
									null
								</#if>
							</#list>

							);

							assertEquals(Collections.singletonList(${schemaVarName}1), (List<${schemaName}>)page.getItems());
						}
					}

					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithFilterStringEquals() throws Exception {
						List<EntityField> entityFields = getEntityFields(EntityField.Type.STRING);

						if (entityFields.isEmpty()) {
							return;
						}

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
						</#list>

						${schemaName} ${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterName},
						</#list>

						random${schemaName}());

						@SuppressWarnings("PMD.UnusedLocalVariable")
						${schemaName} ${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterName},
						</#list>

						random${schemaName}());

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> page = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

							<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
								<#if !javaMethodParameter?is_first>
									,
								</#if>

								<#if stringUtil.equals(javaMethodParameter.parameterName, "filter")>
									getFilterString(entityField, "eq", ${schemaVarName}1)
								<#elseif stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
									Pagination.of(1, 2)
								<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
									${javaMethodParameter.parameterName}
								<#else>
									null
								</#if>
							</#list>

							);

							assertEquals(Collections.singletonList(${schemaVarName}1), (List<${schemaName}>)page.getItems());
						}
					}
				</#if>

				<#if parameters?contains("Pagination pagination")>
					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithPagination() throws Exception {
						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
						</#list>

						${schemaName} ${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterName},
						</#list>

						random${schemaName}());

						${schemaName} ${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterName},
						</#list>

						random${schemaName}());

						${schemaName} ${schemaVarName}3 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterName},
						</#list>

						random${schemaName}());

						Page<${schemaName}> page1 = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if !javaMethodParameter?is_first>
								,
							</#if>

							<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
								Pagination.of(1, 2)
							<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName}
							<#else>
								null
							</#if>
						</#list>

						);

						List<${schemaName}> ${schemaVarNames}1 = (List<${schemaName}>)page1.getItems();

						Assert.assertEquals(${schemaVarNames}1.toString(), 2, ${schemaVarNames}1.size());

						Page<${schemaName}> page2 = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if !javaMethodParameter?is_first>
								,
							</#if>

							<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
								Pagination.of(2, 2)
							<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName}
							<#else>
								null
							</#if>
						</#list>

						);

						Assert.assertEquals(3, page2.getTotalCount());

						List<${schemaName}> ${schemaVarNames}2 = (List<${schemaName}>)page2.getItems();

						Assert.assertEquals(${schemaVarNames}2.toString(), 1, ${schemaVarNames}2.size());

						Page<${schemaName}> page3 = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if !javaMethodParameter?is_first>
								,
							</#if>

							<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
								Pagination.of(1, 3)
							<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName}
							<#else>
								null
							</#if>
						</#list>

						);

						assertEqualsIgnoringOrder(Arrays.asList(${schemaVarName}1, ${schemaVarName}2, ${schemaVarName}3), (List<${schemaName}>)page3.getItems());
					}
				</#if>

				<#if parameters?contains("Sort[] sorts")>
					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithSortDateTime() throws Exception {
						test${javaMethodSignature.methodName?cap_first}WithSort(
							EntityField.Type.DATE_TIME,
							(entityField, ${schemaVarName}1, ${schemaVarName}2) -> {
								BeanUtils.setProperty(${schemaVarName}1, entityField.getName(), DateUtils.addMinutes(new Date(), -2));
							});
					}

					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithSortInteger() throws Exception {
						test${javaMethodSignature.methodName?cap_first}WithSort(
							EntityField.Type.INTEGER,
							(entityField, ${schemaVarName}1, ${schemaVarName}2) -> {
								BeanUtils.setProperty(${schemaVarName}1, entityField.getName(), 0);
								BeanUtils.setProperty(${schemaVarName}2, entityField.getName(), 1);
							});
					}

					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithSortString() throws Exception {
						test${javaMethodSignature.methodName?cap_first}WithSort(
							EntityField.Type.STRING,
							(entityField, ${schemaVarName}1, ${schemaVarName}2) -> {

								Class<?> clazz = ${schemaVarName}1.getClass();

								String entityFieldName = entityField.getName();

								Method method = clazz.getMethod( "get" + StringUtil.upperCaseFirstLetter(entityFieldName));

								Class<?> returnType = method.getReturnType();

								if (returnType.isAssignableFrom(Map.class)) {
									BeanUtils.setProperty(${schemaVarName}1, entityFieldName, Collections.singletonMap("Aaa", "Aaa"));
									BeanUtils.setProperty(${schemaVarName}2, entityFieldName, Collections.singletonMap("Bbb", "Bbb"));
								}
								else if (entityFieldName.contains("email")) {
									BeanUtils.setProperty(${schemaVarName}1, entityFieldName, "aaa" + StringUtil.toLowerCase(RandomTestUtil.randomString()) + "@liferay.com");
									BeanUtils.setProperty(${schemaVarName}2, entityFieldName, "bbb" + StringUtil.toLowerCase(RandomTestUtil.randomString()) + "@liferay.com");
								}
								else {
									BeanUtils.setProperty(${schemaVarName}1, entityFieldName, "aaa" + StringUtil.toLowerCase(RandomTestUtil.randomString()));
									BeanUtils.setProperty(${schemaVarName}2, entityFieldName, "bbb" + StringUtil.toLowerCase(RandomTestUtil.randomString()));
								}
							});
					}

					protected void test${javaMethodSignature.methodName?cap_first}WithSort(EntityField.Type type, UnsafeTriConsumer<EntityField, ${schemaName}, ${schemaName}, Exception> unsafeTriConsumer) throws Exception {
						List<EntityField> entityFields = getEntityFields(type);

						if (entityFields.isEmpty()) {
							return;
						}

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
						</#list>

						${schemaName} ${schemaVarName}1 = random${schemaName}();
						${schemaName} ${schemaVarName}2 = random${schemaName}();

						for (EntityField entityField : entityFields) {
							unsafeTriConsumer.accept(entityField, ${schemaVarName}1, ${schemaVarName}2);
						}

						${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterName},
						</#list>

						${schemaVarName}1);

						${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterName},
						</#list>

						${schemaVarName}2);

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> ascPage = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

							<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
								<#if !javaMethodParameter?is_first>
									,
								</#if>

								<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
									Pagination.of(1, 2)
								<#elseif stringUtil.equals(javaMethodParameter.parameterName, "sorts")>
									entityField.getName() + ":asc"
								<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
									${javaMethodParameter.parameterName}
								<#else>
									null
								</#if>
							</#list>

							);

							assertEquals(Arrays.asList(${schemaVarName}1, ${schemaVarName}2), (List<${schemaName}>)ascPage.getItems());

							Page<${schemaName}> descPage = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

							<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
								<#if !javaMethodParameter?is_first>
									,
								</#if>

								<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
									Pagination.of(1, 2)
								<#elseif stringUtil.equals(javaMethodParameter.parameterName, "sorts")>
									entityField.getName() + ":desc"
								<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
									${javaMethodParameter.parameterName}
								<#else>
									null
								</#if>
							</#list>

							);

							assertEquals(Arrays.asList(${schemaVarName}2, ${schemaVarName}1), (List<${schemaName}>)descPage.getItems());
						}
					}
				</#if>

				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}(
						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName},
						</#list>

						${schemaName} ${schemaVarName}
					) throws Exception {

					<#if (javaMethodSignature.pathJavaMethodParameters?size == 1)>
						<#assign firstPathJavaMethodParameter = javaMethodSignature.pathJavaMethodParameters[0] />

						<#if freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, firstPathJavaMethodParameter.parameterName, schemaName)>
							<#assign postSchemaJavaMethodSignature = freeMarkerTool.getPostSchemaJavaMethodSignature(javaMethodSignatures, firstPathJavaMethodParameter.parameterName, schemaName) />

							return ${schemaVarName}Resource.${postSchemaJavaMethodSignature.methodName}(${firstPathJavaMethodParameter.parameterName}, ${schemaVarName}

							<#if freeMarkerTool.hasRequestBodyMediaType(postSchemaJavaMethodSignature, "multipart/form-data")>
								<#assign generateGetMultipartFilesMethod = true />

								, getMultipartFiles()
							</#if>

							);
						<#else>
							throw new UnsupportedOperationException("This method needs to be implemented");
						</#if>
					<#else>
						throw new UnsupportedOperationException("This method needs to be implemented");
					</#if>
				}

				<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
					protected ${javaMethodParameter.parameterType} test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}() throws Exception {
						<#if stringUtil.equals(javaMethodParameter.parameterName, "assetLibraryId")>
							return testDepotEntry.getDepotEntryId();
						<#elseif stringUtil.equals(javaMethodParameter.parameterName, "siteId")>
							return testGroup.getGroupId();
						<#else>
							throw new UnsupportedOperationException("This method needs to be implemented");
						</#if>
					}

					protected ${javaMethodParameter.parameterType} test${javaMethodSignature.methodName?cap_first}_getIrrelevant${javaMethodParameter.parameterName?cap_first}() throws Exception {
						<#if stringUtil.equals(javaMethodParameter.parameterName, "siteId")>
							return irrelevantGroup.getGroupId();
						<#else>
							return null;
						</#if>
					}
				</#list>
			</#if>
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?ends_with(schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if properties?keys?seq_contains("id")>
					${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

					${schemaName} get${schemaName} = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

					<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if !javaMethodParameter?is_first>
							,
						</#if>

						<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
							Pagination.of(1, 2)
						<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
							<#if stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
								post${schemaName}.getId()
							<#elseif properties?keys?seq_contains(javaMethodParameter.parameterName)>
								post${schemaName}.get${javaMethodParameter.parameterName?cap_first}()
							<#else>
								null
							</#if>
						<#else>
							null
						</#if>
					</#list>

					);

					assertEquals(post${schemaName}, get${schemaName});
					assertValid(get${schemaName});
				<#else>
					Assert.assertTrue(false);
				</#if>
			}

			<#if properties?keys?seq_contains("id")>
				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
					<#if freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, "assetLibraryId", schemaName) || freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName)>
						<#assign postSchemaJavaMethodSignature = freeMarkerTool.getPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName) />

						return ${schemaVarName}Resource.postSite${schemaName}(testGroup.getGroupId(), random${schemaName}()

						<#if freeMarkerTool.hasRequestBodyMediaType(postSchemaJavaMethodSignature, "multipart/form-data")>
							<#assign generateGetMultipartFilesMethod = true />

							, getMultipartFiles()
						</#if>

						);
					<#else>
						throw new UnsupportedOperationException("This method needs to be implemented");
					</#if>
				}
			</#if>
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch") && freeMarkerTool.hasJavaMethodSignature(javaMethodSignatures, "get" + javaMethodSignature.methodName?remove_beginning("patch")) && javaMethodSignature.returnType?ends_with(schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if !properties?keys?seq_contains("id")>
					Assert.assertTrue(false);
				<#else>
					${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

					${schemaName} randomPatch${schemaName} = randomPatch${schemaName}();

					<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
						<#assign generateGetMultipartFilesMethod = true />

						Map<String, File> multipartFiles = getMultipartFiles();
					</#if>

					${schemaName} patch${schemaName} = ${schemaVarName}Resource.${javaMethodSignature.methodName}(
						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								<#if stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
									post${schemaName}.getId()
								<#elseif properties?keys?seq_contains(javaMethodParameter.parameterName)>
									post${schemaName}.get${javaMethodParameter.parameterName?cap_first}()
								<#else>
									null
								</#if>
							</#if>
						</#list>, randomPatch${schemaName}
						<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
							, multipartFiles
						</#if>
					);

					${schemaName} expectedPatch${schemaName} = post${schemaName}.clone();

					_beanUtilsBean.copyProperties(expectedPatch${schemaName}, randomPatch${schemaName});

					${schemaName} get${schemaName} = ${schemaVarName}Resource.get${schemaName}(patch${schemaName}.getId());

					assertEquals(expectedPatch${schemaName}, get${schemaName});
					assertValid(get${schemaName});

					<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
						assertValid(get${schemaName}, multipartFiles);
					</#if>
				</#if>
			}

			<#if properties?keys?seq_contains("id")>
				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
					<#if freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName)>
						<#assign postSchemaJavaMethodSignature = freeMarkerTool.getPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName) />

						return ${schemaVarName}Resource.postSite${schemaName}(testGroup.getGroupId(), random${schemaName}()

						<#if freeMarkerTool.hasRequestBodyMediaType(postSchemaJavaMethodSignature, "multipart/form-data")>
							, getMultipartFiles()
						</#if>

						);
					<#else>
						throw new UnsupportedOperationException("This method needs to be implemented");
					</#if>
				}
			</#if>
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post") && javaMethodSignature.returnType?ends_with(schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				${schemaName} random${schemaName} = random${schemaName}();

				<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
					<#assign generateGetMultipartFilesMethod = true />

					Map<String, File> multipartFiles = getMultipartFiles();
				</#if>

				${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(random${schemaName}

				<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
					, multipartFiles
				</#if>

				);

				assertEquals(random${schemaName}, post${schemaName});
				assertValid(post${schemaName});

				<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
					assertValid(post${schemaName}, multipartFiles);
				</#if>
			}

			protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${schemaName} ${schemaVarName}

			<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
				, Map<String, File> multipartFiles
			</#if>

			) throws Exception {
				<#if (javaMethodSignature.pathJavaMethodParameters?size == 1)>
					<#assign
						firstPathJavaMethodParameter = javaMethodSignature.pathJavaMethodParameters[0]
						modifiedPathJavaMethodParameterName = firstPathJavaMethodParameter.parameterName?remove_beginning("parent")?remove_ending("Id")?cap_first
					/>

					<#if freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, firstPathJavaMethodParameter.parameterName, schemaName) && stringUtil.equals(javaMethodSignature.methodName, "post" + modifiedPathJavaMethodParameterName + schemaName)>
						return ${schemaVarName}Resource.post${modifiedPathJavaMethodParameterName}${schemaName}(testGet${modifiedPathJavaMethodParameterName}${schemaNames}Page_get<#if stringUtil.startsWith(firstPathJavaMethodParameter.parameterName, "parent")>Parent</#if>${modifiedPathJavaMethodParameterName}Id(), ${schemaVarName}

						<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
							, multipartFiles
						</#if>

						);
					<#else>
						throw new UnsupportedOperationException("This method needs to be implemented");
					</#if>
				<#else>
					throw new UnsupportedOperationException("This method needs to be implemented");
				</#if>
			}
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put") && javaMethodSignature.returnType?ends_with(schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if !properties?keys?seq_contains("id")>
					Assert.assertTrue(false);
				<#else>
					${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

					${schemaName} random${schemaName} = random${schemaName}();

					<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
						<#assign generateGetMultipartFilesMethod = true />

						Map<String, File> multipartFiles = getMultipartFiles();
					</#if>

					${schemaName} put${schemaName} = ${schemaVarName}Resource.${javaMethodSignature.methodName}(

					<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
							post${schemaName}.getId()
						<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && properties?keys?seq_contains(javaMethodParameter.parameterName)>
							post${schemaName}.get${javaMethodParameter.parameterName?cap_first}()
						<#elseif stringUtil.equals(javaMethodParameter.parameterName, "multipartBody") || stringUtil.equals(javaMethodParameter.parameterName, schemaVarName)>
							random${schemaName}
						<#else>
							null
						</#if>
						<#sep>, </#sep>
					</#list>

					<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
						, multipartFiles
					</#if>

					);

					assertEquals(random${schemaName}, put${schemaName});
					assertValid(put${schemaName});

					${schemaName} get${schemaName} = ${schemaVarName}Resource.${javaMethodSignature.methodName?replace("put", "get")}(
						<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
								put${schemaName}.getId()
							<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && properties?keys?seq_contains(javaMethodParameter.parameterName)>
								put${schemaName}.get${javaMethodParameter.parameterName?cap_first}()
							</#if>

							<#sep>, </#sep>
						</#list>
					);

					assertEquals(random${schemaName}, get${schemaName});
					assertValid(get${schemaName});

					<#if freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
						assertValid(get${schemaName}, multipartFiles);
					</#if>
				</#if>
			}

			<#if properties?keys?seq_contains("id")>
				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
					<#if freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, "assetLibraryId", schemaName) || freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName)>
						<#assign postSchemaJavaMethodSignature = freeMarkerTool.getPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName) />

						return ${schemaVarName}Resource.postSite${schemaName}(testGroup.getGroupId(), random${schemaName}()

						<#if freeMarkerTool.hasRequestBodyMediaType(postSchemaJavaMethodSignature, "multipart/form-data")>
							, getMultipartFiles()
						</#if>

						);
					<#else>
						throw new UnsupportedOperationException("This method needs to be implemented");
					</#if>
				}
			</#if>
		<#elseif properties?keys?seq_contains("id") && stringUtil.equals(javaMethodSignature.returnType, "void")>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				@SuppressWarnings("PMD.UnusedLocalVariable")
				${schemaName} ${schemaVarName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

				assertHttpResponseStatusCode(
					204,
					${schemaVarName}Resource.${javaMethodSignature.methodName}HttpResponse(
						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
								${schemaVarName}.getId()
							<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && properties?keys?seq_contains(javaMethodParameter.parameterName)>
								${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}()
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, "siteId")>
								testGroup.getGroupId()
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, schemaVarName)>
								${schemaVarName}
							<#elseif stringUtil.equals(javaMethodParameter.parameterType, "[Lcom.liferay.portal.vulcan.permission.Permission;")>
								new Permission[] {
									new Permission() {
										{
											setActionIds(new String[] {"VIEW"});
											setRoleName("Guest");
										}
									}
								}
							<#else>
								null
							</#if>
							<#sep>, </#sep>
						</#list>
					));

				assertHttpResponseStatusCode(
					404,
					${schemaVarName}Resource.${javaMethodSignature.methodName}HttpResponse(
						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
								<#if stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Double")>
									0D
								<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Integer")>
									0
								<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Long")>
									0L
								<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
									""
								<#else>
									null
								</#if>
							<#elseif freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && properties?keys?seq_contains(javaMethodParameter.parameterName)>
								${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}()
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, "siteId")>
								testGroup.getGroupId()
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, schemaVarName)>
								${schemaVarName}
						 	<#elseif stringUtil.startsWith(javaMethodParameter.parameterType, "[Lcom.liferay.portal.vulcan.permission.Permission;")>
								new Permission[]{
									new Permission() {
										{
											setActionIds(new String[] {"-"});
											setRoleName("-");
										}
									}
								}
							<#else>
								null
							</#if>
							<#sep>, </#sep>
						</#list>
					));
			}

			protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
				<#if freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, "assetLibraryId", schemaName) || freeMarkerTool.hasPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName)>
					<#assign postSchemaJavaMethodSignature = freeMarkerTool.getPostSchemaJavaMethodSignature(javaMethodSignatures, "siteId", schemaName) />

					return ${schemaVarName}Resource.postSite${schemaName}(testGroup.getGroupId(), random${schemaName}()

					<#if freeMarkerTool.hasRequestBodyMediaType(postSchemaJavaMethodSignature, "multipart/form-data")>
						, getMultipartFiles()
					</#if>

					);
				<#else>
					throw new UnsupportedOperationException("This method needs to be implemented");
				</#if>
			}
		<#elseif !freeMarkerTool.isReturnTypeRelatedSchema(javaMethodSignature, relatedSchemaNames)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				Assert.assertTrue(false);
			}
		</#if>

		<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete") && stringUtil.equals(freeMarkerTool.getGraphQLPropertyName(javaMethodSignature, javaMethodSignatures), "delete" + schemaName)>
			@Test
			public void testGraphQL${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if !properties?keys?seq_contains("id")>
					Assert.assertTrue(false);
				<#else>
					${schemaName} ${schemaVarName} = testGraphQL${schemaName}_add${schemaName}();

					Assert.assertTrue(
						JSONUtil.getValueAsBoolean(
							invokeGraphQLMutation(
								new GraphQLField(
									"delete${schemaName}",
									new HashMap<String, Object>() {
										{
											put(
												"${schemaVarName}Id",
												<#if stringUtil.equals(properties.id, "String")>
													"\"" + ${schemaVarName}.getId() + "\""
												<#else>
													${schemaVarName}.getId()
												</#if>
											);
										}
									})),
							"JSONObject/data",
							"Object/delete${schemaName}"));

					try (CaptureAppender captureAppender = Log4JLoggerTestUtil.configureLog4JLogger("graphql.execution.SimpleDataFetcherExceptionHandler", Level.WARN)) {
						JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
							invokeGraphQLQuery(
								new GraphQLField(
									"${schemaName?uncap_first}",
									new HashMap<String, Object>() {
										{
											put(
												"${schemaVarName}Id",
												<#if stringUtil.equals(properties.id, "String")>
													"\"" + ${schemaVarName}.getId() + "\""
												<#else>
													${schemaVarName}.getId()
												</#if>
											);
										}
									},
									new GraphQLField("id"))),
							"JSONArray/errors");

						Assert.assertTrue(errorsJSONArray.length() > 0);
					}
				</#if>
			}
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?contains("Page<") && stringUtil.equals(freeMarkerTool.getGraphQLPropertyName(javaMethodSignature, javaMethodSignatures), schemaVarNames)>
			@Test
			public void testGraphQL${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if !properties?keys?seq_contains("id")>
					Assert.assertTrue(false);
				<#else>
					<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
						${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
					</#list>

					GraphQLField graphQLField = new GraphQLField(
						"${schemaVarNames}",
						new HashMap<String, Object>() {
							{
								<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
									<#if stringUtil.equals(javaMethodParameter.parameterName, "pagination")>
										put("page", 1);
										put("pageSize", 2);
									</#if>
								</#list>

								<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
									<#if stringUtil.equals(javaMethodParameter.parameterName, "siteId")>
										put("siteKey", "\"" + ${javaMethodParameter.parameterName} + "\"");
									<#else>
										put("${javaMethodParameter.parameterName}",
											<#if stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
												"\"" + ${javaMethodParameter.parameterName} + "\""
											<#else>
												${javaMethodParameter.parameterName}
											</#if>
										);
									</#if>
								</#list>
							}
						},
						new GraphQLField("items", getGraphQLFields()),
						new GraphQLField("page"),
						new GraphQLField("totalCount"));

					JSONObject ${schemaVarNames}JSONObject = JSONUtil.getValueAsJSONObject(
						invokeGraphQLQuery(graphQLField),
						"JSONObject/data",
						"JSONObject/${schemaVarNames}");

					Assert.assertEquals(0, ${schemaVarNames}JSONObject.get("totalCount"));

					${schemaName} ${schemaVarName}1 = testGraphQL${schemaName}_add${schemaName}();
					${schemaName} ${schemaVarName}2 = testGraphQL${schemaName}_add${schemaName}();

					${schemaVarNames}JSONObject = JSONUtil.getValueAsJSONObject(
						invokeGraphQLQuery(graphQLField),
						"JSONObject/data",
						"JSONObject/${schemaVarNames}");

					Assert.assertEquals(2, ${schemaVarNames}JSONObject.get("totalCount"));

					assertEqualsIgnoringOrder(Arrays.asList(${schemaVarName}1, ${schemaVarName}2), Arrays.asList((${schemaName}SerDes.toDTOs(${schemaVarNames}JSONObject.getString("items")))));
				</#if>
			}
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?ends_with(schemaName)>
			@Test
			public void testGraphQL${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if properties?keys?seq_contains("id")>
					${schemaName} ${schemaVarName} = testGraphQL${schemaName}_add${schemaName}();

					Assert.assertTrue(
						equals(${schemaVarName},
						${schemaName}SerDes.toDTO(
							JSONUtil.getValueAsString(
								invokeGraphQLQuery(
									new GraphQLField(
										"${freeMarkerTool.getGraphQLPropertyName(javaMethodSignature, javaMethodSignatures)}",
										new HashMap<String, Object>() {
											{
												<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
													<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
														<#if stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
															put("${javaMethodParameter.parameterName}",
																<#if stringUtil.equals(properties.id, "String")>
																	"\"" + ${schemaVarName}.getId() + "\""
																<#else>
																	${schemaVarName}.getId()
																</#if>
															);
														<#elseif stringUtil.equals(javaMethodParameter.parameterName, "siteId")>
															put("siteKey", "\"" + ${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}() + "\"");
														<#elseif properties?keys?seq_contains(javaMethodParameter.parameterName)>
															put("${javaMethodParameter.parameterName}",
																<#if stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
																	"\"" + ${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}() + "\""
																<#else>
																	${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}()
																</#if>
															);
														<#else>
															put("${javaMethodParameter.parameterName}", null);
														</#if>
													</#if>
												</#list>
											}
										},
										getGraphQLFields())),
								"JSONObject/data",
								"Object/${freeMarkerTool.getGraphQLPropertyName(javaMethodSignature, javaMethodSignatures)}"))));
				<#else>
					Assert.assertTrue(true);
				</#if>
			}

			@Test
			public void testGraphQL${javaMethodSignature.methodName?cap_first}NotFound() throws Exception {
				<#if javaMethodSignature.javaMethodParameters?size != 0 && properties?keys?seq_contains("id")>
					<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if !stringUtil.equals(javaMethodParameter.parameterName, "siteId") && freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
							${javaMethodParameter.parameterType} irrelevant${javaMethodParameter.parameterName?cap_first} =
							<#if stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Boolean")>
								RandomTestUtil.randomBoolean();
							<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Integer")>
								RandomTestUtil.randomInt();
							<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Long")>
								RandomTestUtil.randomLong();
							<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.Double")>
								RandomTestUtil.randomDouble();
							<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
								"\"" + RandomTestUtil.randomString() + "\"";
							<#else>
								null;
							</#if>
						</#if>
					</#list>

					Assert.assertEquals(
						"Not Found",
						JSONUtil.getValueAsString(
							invokeGraphQLQuery(
								new GraphQLField(
									"${freeMarkerTool.getGraphQLPropertyName(javaMethodSignature, javaMethodSignatures)}",
									new HashMap<String, Object>() {
										{
											<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
												<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
													<#if stringUtil.equals(javaMethodParameter.parameterName, "siteId")>
														put("siteKey", "\"" + irrelevantGroup.getGroupId() + "\"");
													<#else>
														put("${javaMethodParameter.parameterName}", irrelevant${javaMethodParameter.parameterName?cap_first});
													</#if>
												</#if>
											</#list>
										}
									},
									getGraphQLFields())),
							"JSONArray/errors", "Object/0", "JSONObject/extensions", "Object/code"));
				<#else>
					Assert.assertTrue(true);
				</#if>
				}
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post") && stringUtil.equals(javaMethodSignature.methodName, "postSite" + schemaName) && !freeMarkerTool.hasRequestBodyMediaType(javaMethodSignature, "multipart/form-data")>
			@Test
			public void testGraphQL${javaMethodSignature.methodName?cap_first}() throws Exception {
				${schemaName} random${schemaName} = random${schemaName}();

				${schemaName} ${schemaVarName} = testGraphQL${schemaName}_add${schemaName}(random${schemaName});

				Assert.assertTrue(equals(random${schemaName}, ${schemaVarName}));
			}
		</#if>
	</#list>

	<#if generateSearchTestRule>
		@Rule
		public SearchTestRule searchTestRule = new SearchTestRule();
	</#if>

	<#list relatedSchemaNames as relatedSchemaName>
		<#assign
			relatedSchemaProperties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, relatedSchemaName)
			relatedSchemaVarName = freeMarkerTool.getSchemaVarName(relatedSchemaName)
		/>

		<#list javaMethodSignatures as javaMethodSignature>
			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?ends_with("." + relatedSchemaName)>
				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					${schemaName} post${schemaName} = testGet${schemaName}_add${schemaName}();

					${relatedSchemaName} post${relatedSchemaName} = test${javaMethodSignature.methodName?cap_first}_add${relatedSchemaName}(post${schemaName}.getId(), random${relatedSchemaName}());

					${relatedSchemaName} get${relatedSchemaName} = ${schemaVarName}Resource.${javaMethodSignature.methodName}(post${schemaName}.getId());

					assertEquals(post${relatedSchemaName}, get${relatedSchemaName});
					assertValid(get${relatedSchemaName});
				}

				protected ${relatedSchemaName} test${javaMethodSignature.methodName?cap_first}_add${relatedSchemaName}(long ${schemaVarName}Id, ${relatedSchemaName} ${relatedSchemaVarName}) throws Exception {
					return ${schemaVarName}Resource.${javaMethodSignature.methodName?replace("get", "post")}(${schemaVarName}Id, ${relatedSchemaVarName});
				}
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post") && javaMethodSignature.returnType?ends_with("." + relatedSchemaName)>
				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					Assert.assertTrue(true);
				}
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put") && javaMethodSignature.returnType?ends_with("." + relatedSchemaName)>
				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					${schemaName} post${schemaName} = testPut${schemaName}_add${schemaName}();

					test${javaMethodSignature.methodName?cap_first}_add${relatedSchemaName}(post${schemaName}.getId(), random${relatedSchemaName}());

					${relatedSchemaName} random${relatedSchemaName} = random${relatedSchemaName}();

					${relatedSchemaName} put${relatedSchemaName} = ${schemaVarName}Resource.${javaMethodSignature.methodName}(
						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation) && stringUtil.equals(javaMethodParameter.parameterName, schemaVarName + "Id")>
								post${schemaName}.getId()
							<#elseif stringUtil.equals(javaMethodParameter.parameterName, relatedSchemaVarName)>
								random${relatedSchemaName}
							<#else>
								null
							</#if>
							<#sep>, </#sep>
						</#list>
					);

					assertEquals(random${relatedSchemaName}, put${relatedSchemaName});
					assertValid(put${relatedSchemaName});
				}

				protected ${relatedSchemaName} test${javaMethodSignature.methodName?cap_first}_add${relatedSchemaName}(long ${schemaVarName}Id, ${relatedSchemaName} ${relatedSchemaVarName}) throws Exception {
					<#if freeMarkerTool.hasJavaMethodSignature(javaMethodSignatures, javaMethodSignature.methodName?replace("put", "post"))>
						return ${schemaVarName}Resource.${javaMethodSignature.methodName?replace("put", "post")}(${schemaVarName}Id, ${relatedSchemaVarName});
					<#else>
						throw new UnsupportedOperationException("This method needs to be implemented");
					</#if>
				}
			</#if>
		</#list>
	</#list>

	<#if properties?keys?seq_contains("id")>
		<#if freeMarkerTool.hasJavaMethodSignature(javaMethodSignatures, "postSite" + schemaName)>
			protected void appendGraphQLFieldValue(StringBuilder sb, Object value) throws Exception {
				if (value instanceof Object[]) {
					StringBuilder arraySB = new StringBuilder("[");

					for (Object object : (Object[])value) {
						if (arraySB.length() > 1) {
							arraySB.append(",");
						}

						arraySB.append("{");

						Class<?> clazz = object.getClass();

						for (Field field : ReflectionUtil.getDeclaredFields(clazz.getSuperclass())) {
							arraySB.append(field.getName());
							arraySB.append(": ");

							appendGraphQLFieldValue(arraySB, field.get(object));

							arraySB.append(",");
						}

						arraySB.setLength(arraySB.length() - 1);

						arraySB.append("}");
					}

					arraySB.append("]");

					sb.append(arraySB.toString());
				}
				else if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}
			}

			protected ${schemaName} testGraphQL${schemaName}_add${schemaName}() throws Exception {
				return testGraphQL${schemaName}_add${schemaName}(random${schemaName}());
			}

			protected ${schemaName} testGraphQL${schemaName}_add${schemaName}(${schemaName} ${schemaVarName}) throws Exception {
				JSONDeserializer<${schemaName}> jsonDeserializer = JSONFactoryUtil.createJSONDeserializer();

				StringBuilder sb = new StringBuilder("{");

				for (Field field : ReflectionUtil.getDeclaredFields(${schemaName}.class)) {
					if (!ArrayUtil.contains(getAdditionalAssertFieldNames(), field.getName())) {
						continue;
					}

					if (sb.length() > 1) {
						sb.append(", ");
					}

					sb.append(field.getName());
					sb.append(": ");

					appendGraphQLFieldValue(sb, field.get(${schemaVarName}));
				}

				sb.append("}");

				List<GraphQLField> graphQLFields = getGraphQLFields();

				<#if properties?keys?seq_contains("id")>
					graphQLFields.add(new GraphQLField("id"));
				</#if>

				return jsonDeserializer.deserialize(
					JSONUtil.getValueAsString(
						invokeGraphQLMutation(
							new GraphQLField(
								"createSite${schemaName}",
								new HashMap<String, Object>() {
									{
										put("siteKey", "\"" + testGroup.getGroupId() + "\"");
										put("${schemaVarName}", sb.toString());
									}
								},
								graphQLFields)),
						"JSONObject/data",
						"JSONObject/createSite${schemaName}"),
					${schemaName}.class);
			}
		<#else>
			protected ${schemaName} testGraphQL${schemaName}_add${schemaName}() throws Exception {
				throw new UnsupportedOperationException("This method needs to be implemented");
			}
		</#if>
	</#if>

	protected void assertHttpResponseStatusCode(int expectedHttpResponseStatusCode, HttpInvoker.HttpResponse actualHttpResponse) {
		Assert.assertEquals(expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

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

	<#list relatedSchemaNames as relatedSchemaName>
		<#assign
			relatedSchemaProperties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, relatedSchemaName)
			relatedSchemaVarName = freeMarkerTool.getSchemaVarName(relatedSchemaName)
		/>

		protected void assertEquals(${relatedSchemaName} ${relatedSchemaVarName}1, ${relatedSchemaName} ${relatedSchemaVarName}2) {
			Assert.assertTrue(${relatedSchemaVarName}1 + " does not equal " + ${relatedSchemaVarName}2, equals(${relatedSchemaVarName}1, ${relatedSchemaVarName}2));
		}
	</#list>

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

	protected void assertValid(${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName} ${schemaVarName}) throws Exception {
		boolean valid = true;

		<#if properties?keys?seq_contains("dateCreated")>
			if (${schemaVarName}.getDateCreated() == null) {
				valid = false;
			}
		</#if>

		<#if properties?keys?seq_contains("dateModified")>
			if (${schemaVarName}.getDateModified() == null) {
				valid = false;
			}
		</#if>

		<#if properties?keys?seq_contains("id")>
			if (${schemaVarName}.getId() == null) {
				valid = false;
			}
		</#if>

		<#if properties?keys?seq_contains("siteId")>
			<#if generateDepotEntry>
				Group group = testDepotEntry.getGroup();

				if (!Objects.equals(${schemaVarName}.getSiteId(), testGroup.getGroupId()) && !Objects.equals(${schemaVarName}.getAssetLibraryKey(), group.getGroupKey())) {
					valid = false;
				}
			<#else>
				if (!Objects.equals(${schemaVarName}.getSiteId(), testGroup.getGroupId())) {
					valid = false;
				}
			</#if>
		</#if>

		for (String additionalAssertFieldName : getAdditionalAssertFieldNames()) {
			<#list properties?keys as propertyName>
				<#if stringUtil.equals(propertyName, "dateCreated") ||
					 stringUtil.equals(propertyName, "dateModified") ||
					 stringUtil.equals(propertyName, "id") ||
					 stringUtil.equals(propertyName, "siteId")>

					 <#continue>
				</#if>

				if (Objects.equals("${propertyName}", additionalAssertFieldName)) {
					<#assign capitalizedPropertyName = propertyName?cap_first />

					<#if enumSchemas?keys?seq_contains(properties[propertyName])>
						<#assign capitalizedPropertyName = properties[propertyName] />
					</#if>

					if (${schemaVarName}.get${capitalizedPropertyName}() == null) {
						valid = false;
					}

					continue;
				}
			</#list>

			throw new IllegalArgumentException("Invalid additional assert field name " + additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	<#if generateGetMultipartFilesMethod>
		protected void assertValid(${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName} ${schemaVarName}, Map<String, File> multipartFiles) throws Exception {
			throw new UnsupportedOperationException("This method needs to be implemented");
		}
	</#if>

	protected void assertValid(Page<${schemaName}> page) {
		boolean valid = false;

		java.util.Collection<${schemaName}> ${schemaVarNames} = page.getItems();

		int size = ${schemaVarNames}.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) && (page.getPageSize() > 0) && (page.getTotalCount() > 0) && (size > 0)) {
			valid = true;
		}

		Assert.assertTrue(valid);
	}

	<#list relatedSchemaNames as relatedSchemaName>
		<#assign
			relatedSchemaProperties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, relatedSchemaName)
			relatedSchemaVarName = freeMarkerTool.getSchemaVarName(relatedSchemaName)
		/>

		protected void assertValid(${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${relatedSchemaName} ${relatedSchemaVarName}) {
			boolean valid = true;

			<#if relatedSchemaProperties?keys?seq_contains("dateCreated")>
				if (${relatedSchemaVarName}.getDateCreated() == null) {
					valid = false;
				}
			</#if>

			<#if relatedSchemaProperties?keys?seq_contains("dateModified")>
				if (${relatedSchemaVarName}.getDateModified() == null) {
					valid = false;
				}
			</#if>

			<#if relatedSchemaProperties?keys?seq_contains("id")>
				if (${relatedSchemaVarName}.getId() == null) {
					valid = false;
				}
			</#if>

			<#if relatedSchemaProperties?keys?seq_contains("siteId")>
				if (!Objects.equals(${relatedSchemaVarName}.getSiteId(), testGroup.getGroupId())) {
					valid = false;
				}
			</#if>

			for (String additionalAssertFieldName : getAdditional${relatedSchemaName}AssertFieldNames()) {
				<#list relatedSchemaProperties?keys as propertyName>
					<#if stringUtil.equals(propertyName, "dateCreated") ||
						 stringUtil.equals(propertyName, "dateModified") ||
						 stringUtil.equals(propertyName, "id") ||
						 stringUtil.equals(propertyName, "siteId")>

						 <#continue>
					</#if>

					if (Objects.equals("${propertyName}", additionalAssertFieldName)) {
						<#assign capitalizedPropertyName = propertyName?cap_first />

						<#if enumSchemas?keys?seq_contains(relatedSchemaProperties[propertyName])>
							<#assign capitalizedPropertyName = relatedSchemaProperties[propertyName] />
						</#if>

						if (${relatedSchemaVarName}.get${capitalizedPropertyName}() == null) {
							valid = false;
						}

						continue;
					}
				</#list>

				throw new IllegalArgumentException("Invalid additional assert field name " + additionalAssertFieldName);
			}

			Assert.assertTrue(valid);
		}
	</#list>

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	<#list relatedSchemaNames as relatedSchemaName>
		protected String[] getAdditional${relatedSchemaName}AssertFieldNames() {
			return new String[0];
		}
	</#list>

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		<#if properties?keys?seq_contains("siteId")>
			graphQLFields.add(new GraphQLField("siteId"));
		</#if>

		<#if freeMarkerTool.getJavaDataType(configYAML, openAPIYAML, schemaName)??>
			for (Field field : ReflectionUtil.getDeclaredFields(${freeMarkerTool.getJavaDataType(configYAML, openAPIYAML, schemaName)}.class)) {
				if (!ArrayUtil.contains(getAdditionalAssertFieldNames(), field.getName())){
					continue;
				}

				graphQLFields.addAll(getGraphQLFields(field));
			}
		</#if>

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields) throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField vulcanGraphQLField = field.getAnnotation(com.liferay.portal.vulcan.graphql.annotation.GraphQLField.class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(${schemaName} ${schemaVarName}1, ${schemaName} ${schemaVarName}2) {
		if (${schemaVarName}1 == ${schemaVarName}2) {
			return true;
		}

		<#if !properties?keys?seq_contains("assetLibraryKey") && properties?keys?seq_contains("siteId")>
			if (!Objects.equals(${schemaVarName}1.getSiteId(), ${schemaVarName}2.getSiteId())) {
				return false;
			}
		</#if>

		for (String additionalAssertFieldName : getAdditionalAssertFieldNames()) {
			<#list properties?keys as propertyName>
				<#if stringUtil.equals(propertyName, "assetLibraryKey") || stringUtil.equals(propertyName, "siteId")>
					 <#continue>
				</#if>

				if (Objects.equals("${propertyName}", additionalAssertFieldName)) {
					<#assign capitalizedPropertyName = propertyName?cap_first />

					<#if enumSchemas?keys?seq_contains(properties[propertyName])>
						<#assign capitalizedPropertyName = properties[propertyName] />
					</#if>

					<#if stringUtil.startsWith(properties[propertyName], "Map<")>
						if (!equals((Map)${schemaVarName}1.get${capitalizedPropertyName}(), (Map)${schemaVarName}2.get${capitalizedPropertyName}())) {
							return false;
						}
					<#else>
						if (!Objects.deepEquals(${schemaVarName}1.get${capitalizedPropertyName}(), ${schemaVarName}2.get${capitalizedPropertyName}())) {
							return false;
						}
					</#if>

					continue;
				}
			</#list>

			throw new IllegalArgumentException("Invalid additional assert field name " + additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(Map<String, Object> map1, Map<String, Object> map2) {
		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals((Map)entry.getValue(), (Map)map2.get(entry.getKey()))) {
						return false;
					}
				}
				else if (!Objects.deepEquals(entry.getValue(), map2.get(entry.getKey()))){
					return false;
				}
			}
		}

		return true;
	}

	<#list relatedSchemaNames as relatedSchemaName>
		<#assign
			relatedSchemaProperties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, relatedSchemaName)
			relatedSchemaVarName = freeMarkerTool.getSchemaVarName(relatedSchemaName)
		/>

		protected boolean equals(${relatedSchemaName} ${relatedSchemaVarName}1, ${relatedSchemaName} ${relatedSchemaVarName}2) {
			if (${relatedSchemaVarName}1 == ${relatedSchemaVarName}2) {
				return true;
			}

			for (String additionalAssertFieldName : getAdditional${relatedSchemaName}AssertFieldNames()) {
				<#list relatedSchemaProperties?keys as propertyName>
					if (Objects.equals("${propertyName}", additionalAssertFieldName)) {
						<#assign capitalizedPropertyName = propertyName?cap_first />

						<#if enumSchemas?keys?seq_contains(relatedSchemaProperties[propertyName])>
							<#assign capitalizedPropertyName = relatedSchemaProperties[propertyName] />
						</#if>

						if (!Objects.deepEquals(${relatedSchemaVarName}1.get${capitalizedPropertyName}(), ${relatedSchemaVarName}2.get${capitalizedPropertyName}())) {
							return false;
						}

						continue;
					}
				</#list>

				throw new IllegalArgumentException("Invalid additional assert field name " + additionalAssertFieldName);
			}

			return true;
		}
	</#list>

	protected java.util.Collection<EntityField> getEntityFields() throws Exception {
		if (!(_${schemaVarName}Resource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException("Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource = (EntityModelResource)_${schemaVarName}Resource;

		EntityModel entityModel = entityModelResource.getEntityModel(new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap = entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type) throws Exception {
		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type) && !ArrayUtil.contains(getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(EntityField entityField, String operator, ${schemaName} ${schemaVarName}) {
		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		<#list properties?keys as propertyName>
			if (entityFieldName.equals("${propertyName}")) {
				<#if stringUtil.equals(properties[propertyName], "Date")>
					if (operator.equals("between")) {
						sb = new StringBundler();

						sb.append("(");
						sb.append(entityFieldName);
						sb.append(" gt ");
						sb.append(_dateFormat.format(DateUtils.addSeconds(${schemaVarName}.get${propertyName?cap_first}(), -2)));
						sb.append(" and ");
						sb.append(entityFieldName);
						sb.append(" lt ");
						sb.append(_dateFormat.format(DateUtils.addSeconds(${schemaVarName}.get${propertyName?cap_first}(), 2)));
						sb.append(")");
					}
					else {
						sb.append(entityFieldName);

						sb.append(" ");
						sb.append(operator);
						sb.append(" ");

						sb.append(_dateFormat.format(${schemaVarName}.get${propertyName?cap_first}()));
					}

					return sb.toString();
				<#elseif stringUtil.equals(properties[propertyName], "String")>
					sb.append("'");
					sb.append(String.valueOf(${schemaVarName}.get${propertyName?cap_first}()));
					sb.append("'");

					return sb.toString();
				<#else>
					throw new IllegalArgumentException("Invalid entity field " + entityFieldName);
				</#if>
			}
		</#list>

		throw new IllegalArgumentException("Invalid entity field " + entityFieldName);
	}

	<#if generateGetMultipartFilesMethod>
		protected Map<String, File> getMultipartFiles() throws Exception {
			throw new UnsupportedOperationException("This method needs to be implemented");
		}
	</#if>

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

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField) throws Exception {
		GraphQLField mutationGraphQLField = new GraphQLField("mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField) throws Exception {
		GraphQLField queryGraphQLField = new GraphQLField("query", graphQLField);

		return JSONFactoryUtil.createJSONObject(invoke(queryGraphQLField.toString()));
	}

	protected ${schemaName} random${schemaName}() throws Exception {
		return new ${schemaName}() {
			{
				<#list properties?keys as propertyName>
					<#if stringUtil.equals(propertyName, "siteId")>
						${propertyName} = testGroup.getGroupId();
					<#elseif stringUtil.equals(properties[propertyName], "Integer")>
						${propertyName} = RandomTestUtil.randomInt();
					<#elseif propertyName?contains("email") && stringUtil.equals(properties[propertyName], "String")>
						${propertyName} = StringUtil.toLowerCase(RandomTestUtil.randomString()) + "@liferay.com";
					<#elseif stringUtil.equals(properties[propertyName], "String")>
						${propertyName} = StringUtil.toLowerCase(RandomTestUtil.randomString());
					<#elseif randomDataTypes?seq_contains(properties[propertyName])>
						${propertyName} = RandomTestUtil.random${properties[propertyName]}();
					<#elseif stringUtil.equals(properties[propertyName], "Date")>
						${propertyName} = RandomTestUtil.nextDate();
					</#if>
				</#list>
			}
		};
	}

	protected ${schemaName} randomIrrelevant${schemaName}() throws Exception {
		${schemaName} randomIrrelevant${schemaName} = random${schemaName}();

		<#if properties?keys?seq_contains("siteId")>
			randomIrrelevant${schemaName}.setSiteId(irrelevantGroup.getGroupId());
		</#if>

		return randomIrrelevant${schemaName};
	}

	protected ${schemaName} randomPatch${schemaName}() throws Exception {
		return random${schemaName}();
	}

	<#list relatedSchemaNames as relatedSchemaName>
		protected ${relatedSchemaName} random${relatedSchemaName}() throws Exception {
			return new ${relatedSchemaName}() {
				{
					<#assign relatedSchemaProperties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, relatedSchemaName) />

					<#list relatedSchemaProperties?keys as propertyName>
						<#if randomDataTypes?seq_contains(relatedSchemaProperties[propertyName])>
							${propertyName} = RandomTestUtil.random${relatedSchemaProperties[propertyName]}();
						<#elseif stringUtil.equals(relatedSchemaProperties[propertyName], "Date")>
							${propertyName} = RandomTestUtil.nextDate();
						</#if>
					</#list>
				}
			};
		}
	</#list>

	protected ${schemaName}Resource ${schemaVarName}Resource;
	protected Group irrelevantGroup;
	protected Company testCompany;

	<#if generateDepotEntry>
		protected DepotEntry testDepotEntry;
	</#if>

	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, Map<String, Object> parameterMap, GraphQLField... graphQLFields) {
			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(String key, Map<String, Object> parameterMap, List<GraphQLField> graphQLFields) {
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

	private static final Log _log = LogFactoryUtil.getLog(Base${schemaName}ResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource _${schemaVarName}Resource;

}