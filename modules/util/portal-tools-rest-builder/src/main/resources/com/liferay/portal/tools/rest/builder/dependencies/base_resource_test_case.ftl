package ${configYAML.apiPackagePath}.resource.${escapedVersion}.test;

<#list allSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
	import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;
</#list>

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

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

		_resourceURL = new URL("http://localhost:8080/o${configYAML.application.baseURI}/${openAPIYAML.info.version}");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

	<#list freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName) as javaMethodSignature>
		<#assign
			arguments = freeMarkerTool.getResourceArguments(javaMethodSignature.javaMethodParameters)
			parameters = freeMarkerTool.getResourceParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, false)
		/>

		<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if properties?keys?seq_contains("id")>
					${schemaName} ${schemaVarName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

					assertResponseCode(200, invoke${javaMethodSignature.methodName?cap_first}Response(${schemaVarName}.getId()));

					assertResponseCode(404, invokeGet${javaMethodSignature.methodName?remove_beginning("delete")}Response(${schemaVarName}.getId()));
				<#else>
					Assert.assertTrue(true);
				</#if>
			}

			<#if properties?keys?seq_contains("id")>
				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
					throw new UnsupportedOperationException("This method needs to be implemented");
				}
			</#if>
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?contains("Page<")>
			<#if (javaMethodSignature.javaMethodParameters?size == 0) || stringUtil.equals(javaMethodSignature.javaMethodParameters[0].parameterName, "filter") || stringUtil.equals(javaMethodSignature.javaMethodParameters[0].parameterName, "pagination") || stringUtil.equals(javaMethodSignature.javaMethodParameters[0].parameterName, "sorts")>
				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					Assert.assertTrue(true);
				}
			<#else>
				<#assign firstJavaMethodParameter = javaMethodSignature.javaMethodParameters[0] />

				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
							${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
							${javaMethodParameter.parameterType} irrelevant${javaMethodParameter.parameterName?cap_first} = test${javaMethodSignature.methodName?cap_first}_getIrrelevant${javaMethodParameter.parameterName?cap_first}();
						</#if>
					</#list>

					if (<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								<#if !javaMethodParameter?is_first>
									&&
								</#if>

								(irrelevant${javaMethodParameter.parameterName?cap_first} != null)
							</#if>
						</#list>) {

						${schemaName} irrelevant${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								irrelevant${javaMethodParameter.parameterName?cap_first},
							</#if>
						</#list>

						randomIrrelevant${schemaName}());

						Page<${schemaName}> page = invoke${javaMethodSignature.methodName?cap_first}(

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

					${schemaName} ${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

					<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
							${javaMethodParameter.parameterName},
						</#if>
					</#list>

					random${schemaName}());

					${schemaName} ${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

					<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
						<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
							${javaMethodParameter.parameterName},
						</#if>
					</#list>

					random${schemaName}());

					Page<${schemaName}> page = invoke${javaMethodSignature.methodName?cap_first}(

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
				}

				<#if parameters?contains("Filter filter")>
					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithFilterDateTimeEquals() throws Exception {
						List<EntityField> entityFields = getEntityFields(EntityField.Type.DATE_TIME);

						if (entityFields.isEmpty()) {
							return;
						}

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
							</#if>
						</#list>

						${schemaName} ${schemaVarName}1 = random${schemaName}();
						${schemaName} ${schemaVarName}2 = random${schemaName}();

						for (EntityField entityField : entityFields) {
							BeanUtils.setProperty(${schemaVarName}1, entityField.getName(), DateUtils.addMinutes(new Date(), -2));
						}

						${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						${schemaVarName}1);

						Thread.sleep(1000);

						${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						${schemaVarName}2);

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> page = invoke${javaMethodSignature.methodName?cap_first}(

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

					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithFilterStringEquals() throws Exception {
						List<EntityField> entityFields = getEntityFields(EntityField.Type.STRING);

						if (entityFields.isEmpty()) {
							return;
						}

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
							</#if>
						</#list>

						${schemaName} ${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						random${schemaName}());

						@SuppressWarnings("PMD.UnusedLocalVariable")
						${schemaName} ${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						random${schemaName}());

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> page = invoke${javaMethodSignature.methodName?cap_first}(

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
						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
							</#if>
						</#list>

						${schemaName} ${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						random${schemaName}());

						${schemaName} ${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						random${schemaName}());

						${schemaName} ${schemaVarName}3 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						random${schemaName}());

						Page<${schemaName}> page1 = invoke${javaMethodSignature.methodName?cap_first}(

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

						Page<${schemaName}> page2 = invoke${javaMethodSignature.methodName?cap_first}(

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

						assertEqualsIgnoringOrder(
							Arrays.asList(${schemaVarName}1, ${schemaVarName}2, ${schemaVarName}3),
							new ArrayList<${schemaName}>() {
								{
									addAll(${schemaVarNames}1);
									addAll(${schemaVarNames}2);
								}
							});
					}
				</#if>

				<#if parameters?contains("Sort[] sorts")>
					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithSortDateTime() throws Exception {
						List<EntityField> entityFields = getEntityFields(EntityField.Type.DATE_TIME);

						if (entityFields.isEmpty()) {
							return;
						}

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
							</#if>
						</#list>

						${schemaName} ${schemaVarName}1 = random${schemaName}();
						${schemaName} ${schemaVarName}2 = random${schemaName}();

						for (EntityField entityField : entityFields) {
							BeanUtils.setProperty(${schemaVarName}1, entityField.getName(), DateUtils.addMinutes(new Date(), -2));
						}

						${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						${schemaVarName}1);

						Thread.sleep(1000);

						${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						${schemaVarName}2);

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> ascPage = invoke${javaMethodSignature.methodName?cap_first}(

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

							Page<${schemaName}> descPage = invoke${javaMethodSignature.methodName?cap_first}(

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

					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithSortString() throws Exception {
						List<EntityField> entityFields = getEntityFields(EntityField.Type.STRING);

						if (entityFields.isEmpty()) {
							return;
						}

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}();
							</#if>
						</#list>

						${schemaName} ${schemaVarName}1 = random${schemaName}();
						${schemaName} ${schemaVarName}2 = random${schemaName}();

						for (EntityField entityField : entityFields) {
							BeanUtils.setProperty(${schemaVarName}1, entityField.getName(), "Aaa");
							BeanUtils.setProperty(${schemaVarName}2, entityField.getName(), "Bbb");
						}

						${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						${schemaVarName}1);

						${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(

						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterName},
							</#if>
						</#list>

						${schemaVarName}2);

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> ascPage = invoke${javaMethodSignature.methodName?cap_first}(

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

							Page<${schemaName}> descPage = invoke${javaMethodSignature.methodName?cap_first}(

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
						<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
							<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
								${javaMethodParameter.parameterType} ${javaMethodParameter.parameterName},
							</#if>
						</#list>

						${schemaName} ${schemaVarName}
					) throws Exception {

					throw new UnsupportedOperationException("This method needs to be implemented");
				}

				<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
					<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
						protected ${javaMethodParameter.parameterType} test${javaMethodSignature.methodName?cap_first}_get${javaMethodParameter.parameterName?cap_first}() throws Exception {
							<#if stringUtil.equals(javaMethodParameter.parameterName, "contentSpaceId")>
								return testGroup.getGroupId();
							<#else>
								throw new UnsupportedOperationException("This method needs to be implemented");
							</#if>
						}

						protected ${javaMethodParameter.parameterType} test${javaMethodSignature.methodName?cap_first}_getIrrelevant${javaMethodParameter.parameterName?cap_first}() throws Exception {
							<#if stringUtil.equals(javaMethodParameter.parameterName, "contentSpaceId")>
								return irrelevantGroup.getGroupId();
							<#else>
								return null;
							</#if>
						}
					</#if>
				</#list>
			</#if>
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?ends_with(schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if properties?keys?seq_contains("id")>
					${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

					${schemaName} get${schemaName} = invoke${javaMethodSignature.methodName?cap_first}(post${schemaName}.getId());

					assertEquals(post${schemaName}, get${schemaName});
					assertValid(get${schemaName});
				<#else>
					Assert.assertTrue(true);
				</#if>
			}

			<#if properties?keys?seq_contains("id")>
				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
					throw new UnsupportedOperationException("This method needs to be implemented");
				}
			</#if>
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch")>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if !properties?keys?seq_contains("id") || arguments?ends_with(",multipartBody")>
					Assert.assertTrue(true);
				<#else>
					${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(random${schemaName}());

					${schemaName} randomPatch${schemaName} = random${schemaName}();

					${schemaName} patch${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(randomPatch${schemaName});

					${schemaName} expectedPatch${schemaName} = (${schemaName})BeanUtils.cloneBean(post${schemaName});

					_beanUtilsBean.copyProperties(expectedPatch${schemaName}, randomPatch${schemaName});

					${schemaName} get${schemaName} = invokeGet${schemaName}(patch${schemaName}.getId());

					assertEquals(expectedPatch${schemaName}, get${schemaName});
					assertValid(get${schemaName});
				</#if>
			}

			<#if properties?keys?seq_contains("id")>
				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${schemaName} ${schemaVarName}) throws Exception {
					throw new UnsupportedOperationException("This method needs to be implemented");
				}
			</#if>
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post")>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if arguments?ends_with(",multipartBody")>
					Assert.assertTrue(true);
				<#else>
					${schemaName} random${schemaName} = random${schemaName}();

					${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(random${schemaName});

					assertEquals(random${schemaName}, post${schemaName});
					assertValid(post${schemaName});
				</#if>
			}

			protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${schemaName} ${schemaVarName}) throws Exception {
				throw new UnsupportedOperationException("This method needs to be implemented");
			}
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put")>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				<#if !properties?keys?seq_contains("id") || arguments?ends_with(",multipartBody")>
					Assert.assertTrue(true);
				<#else>
					${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

					${schemaName} random${schemaName} = random${schemaName}();

					${schemaName} put${schemaName} = invokePut${schemaName}(post${schemaName}.getId(), random${schemaName});

					assertEquals(random${schemaName}, put${schemaName});
					assertValid(put${schemaName});

					${schemaName} get${schemaName} = invokeGet${schemaName}(put${schemaName}.getId());

					assertEquals(random${schemaName}, get${schemaName});
					assertValid(get${schemaName});
				</#if>
			}

			<#if properties?keys?seq_contains("id")>
				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
					throw new UnsupportedOperationException("This method needs to be implemented");
				}
			</#if>
		<#else>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				Assert.assertTrue(true);
			}
		</#if>

		<#assign
			invokeArguments = arguments?replace("filter", "filterString")?replace("sorts", "sortString")
			invokeParameters = parameters?replace("com.liferay.portal.kernel.search.filter.Filter filter", "String filterString")?replace("com.liferay.portal.kernel.search.Sort[] sorts", "String sortString")
		/>

		protected ${javaMethodSignature.returnType} invoke${javaMethodSignature.methodName?cap_first}(${invokeParameters}) throws Exception {
			Http.Options options = _createHttpOptions();

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch", "post", "put") && invokeArguments?ends_with(",${schemaVarName}")>
				options.setBody(_inputObjectMapper.writeValueAsString(${schemaVarName}), ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
				options.setDelete(true);
			</#if>

			<#if (javaMethodSignature.javaMethodParameters?size > 0)>
				String location = _resourceURL + _toPath("${javaMethodSignature.path}"

				<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
					<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
						, ${javaMethodParameter.parameterName}
					</#if>
				</#list>

				);

				<#if parameters?contains("Filter filter")>
					location = HttpUtil.addParameter(location, "filter", filterString);
				</#if>

				<#if parameters?contains("Pagination pagination")>
					location = HttpUtil.addParameter(location, "page", pagination.getPage());
					location = HttpUtil.addParameter(location, "pageSize", pagination.getPageSize());
				</#if>

				<#if parameters?contains("Sort[] sorts")>
					location = HttpUtil.addParameter(location, "sort", sortString);
				</#if>
			<#else>
				String location = _resourceURL + "${javaMethodSignature.path}";
			</#if>

			options.setLocation(location);

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch")>
				options.setPatch(true);
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post")>
				options.setPost(true);
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put")>
				options.setPut(true);
			</#if>

			String string = HttpUtil.URLtoString(options);

			<#if stringUtil.equals(javaMethodSignature.returnType, "boolean")>
				try {
					return _outputObjectMapper.readValue(string, Boolean.class);
				}
				catch (Exception e) {
					Assert.fail("HTTP response: " + string);

					throw e;
				}
			<#elseif javaMethodSignature.returnType?contains("Page<")>
				return _outputObjectMapper.readValue(string, new TypeReference<Page<${schemaName}>>() {});
			<#elseif javaMethodSignature.returnType?ends_with("String")>
				return string;
			<#else>
				try {
					return _outputObjectMapper.readValue(string, ${javaMethodSignature.returnType}.class);
				}
				catch (Exception e) {
					Assert.fail("HTTP response: " + string);

					throw e;
				}
			</#if>
		}

		protected Http.Response invoke${javaMethodSignature.methodName?cap_first}Response(${invokeParameters}) throws Exception {
			Http.Options options = _createHttpOptions();

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch", "post", "put") && invokeArguments?ends_with(",${schemaVarName}")>
				options.setBody(_inputObjectMapper.writeValueAsString(${schemaVarName}), ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
				options.setDelete(true);
			</#if>

			<#if (javaMethodSignature.javaMethodParameters?size > 0)>
				String location = _resourceURL + _toPath("${javaMethodSignature.path}"

				<#list javaMethodSignature.javaMethodParameters as javaMethodParameter>
					<#if freeMarkerTool.isPathParameter(javaMethodParameter, javaMethodSignature.operation)>
						, ${javaMethodParameter.parameterName}
					</#if>
				</#list>

				);

				<#if parameters?contains("Filter filter")>
					location = HttpUtil.addParameter(location, "filter", filterString);
				</#if>

				<#if parameters?contains("Pagination pagination")>
					location = HttpUtil.addParameter(location, "page", pagination.getPage());
					location = HttpUtil.addParameter(location, "pageSize", pagination.getPageSize());
				</#if>

				<#if parameters?contains("Sort[] sorts")>
					location = HttpUtil.addParameter(location, "sort", sortString);
				</#if>
			<#else>
				String location = _resourceURL + "${javaMethodSignature.path}";
			</#if>

			options.setLocation(location);

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch")>
				options.setPatch(true);
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post")>
				options.setPost(true);
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put")>
				options.setPut(true);
			</#if>

			HttpUtil.URLtoString(options);

			return options.getResponse();
		}
	</#list>

	protected void assertResponseCode(int expectedResponseCode, Http.Response actualResponse) {
		Assert.assertEquals(expectedResponseCode, actualResponse.getResponseCode());
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

	protected void assertValid(${schemaName} ${schemaVarName}) {
		throw new UnsupportedOperationException("This method needs to be implemented");
	}

	protected void assertValid(Page<${schemaName}> page) {
		boolean valid = false;

		Collection<${schemaName}> ${schemaVarNames} = page.getItems();

		int size = ${schemaVarNames}.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) && (page.getPageSize() > 0) && (page.getTotalCount() > 0) && (size > 0)) {
			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(${schemaName} ${schemaVarName}1, ${schemaName} ${schemaVarName}2) {
		if (${schemaVarName}1 == ${schemaVarName}2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_${schemaVarName}Resource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException("Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource = (EntityModelResource)_${schemaVarName}Resource;

		EntityModel entityModel = entityModelResource.getEntityModel(new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap = entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type) throws Exception {
		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
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
				<#if stringUtil.equals(properties[propertyName], "java.util.Date")>
					sb.append(_dateFormat.format(${schemaVarName}.get${propertyName?cap_first}()));

					return sb.toString();
				<#elseif stringUtil.equals(properties[propertyName], "java.lang.String")>
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

	protected ${schemaName} random${schemaName}() {
		return new ${schemaName}() {
			{
				<#assign randomDataTypes = ["java.lang.Boolean", "java.lang.Double", "java.lang.Long", "java.lang.String"] />

				<#list properties?keys as propertyName>
					<#if randomDataTypes?seq_contains(properties[propertyName])>
						${propertyName} = RandomTestUtil.random${properties[propertyName]}();
					<#elseif stringUtil.equals(properties[propertyName], "java.util.Date")>
						${propertyName} = RandomTestUtil.nextDate();
					</#if>
				</#list>
			}
		};
	}

	protected ${schemaName} randomIrrelevant${schemaName}() {
		return random${schemaName}();
	}

	protected ${schemaName} randomPatch${schemaName}() {
		return random${schemaName}();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

		@JsonProperty
		protected long totalCount;

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

	private String _toPath(String template, Object... values) {
		if  (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst("\\{.*\\}", String.valueOf(values[i]));
		}

		return template;
	}

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter("Liferay.Vulcan", SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter("Liferay.Vulcan", SimpleBeanPropertyFilter.serializeAll());
					}
				});
		}
	};

	@Inject
	private ${schemaName}Resource _${schemaVarName}Resource;

	private URL _resourceURL;

}