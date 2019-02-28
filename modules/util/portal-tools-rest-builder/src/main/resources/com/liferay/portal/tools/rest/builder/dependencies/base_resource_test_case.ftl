package ${configYAML.apiPackagePath}.resource.${escapedVersion}.test;

<#compress>
	<#list allSchemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
		import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;
	</#list>
</#compress>

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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

import org.apache.commons.beanutils.BeanUtils;
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
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o${configYAML.application.baseURI}/${openAPIYAML.info.version}");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	<#list freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName, false) as javaMethodSignature>
		<#assign
			arguments = freeMarkerTool.getResourceArguments(javaMethodSignature.javaMethodParameters)
			firstJavaMethodParameter = javaMethodSignature.javaMethodParameters[0]
			parameters = freeMarkerTool.getResourceParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, false)
		/>

		<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				${schemaName} ${schemaVarName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

				assertResponseCode(200, invoke${javaMethodSignature.methodName?cap_first}Response(${schemaVarName}.getId()));

				assertResponseCode(404, invokeGet${javaMethodSignature.methodName?remove_beginning("delete")}Response(${schemaVarName}.getId()));
			}

			protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
				throw new UnsupportedOperationException("This method needs to be implemented");
			}
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?contains("Page<")>
			<#if stringUtil.equals(firstJavaMethodParameter.parameterName, "filter") || stringUtil.equals(firstJavaMethodParameter.parameterName, "pagination") || stringUtil.equals(firstJavaMethodParameter.parameterName, "sorts")>
				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					Assert.assertTrue(true);
				}
			<#else>
				@Test
				public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
					${firstJavaMethodParameter.parameterType} ${firstJavaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${firstJavaMethodParameter.parameterName?cap_first}();

					${schemaName} ${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, random${schemaName}());
					${schemaName} ${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, random${schemaName}());

					Page<${schemaName}> page = invoke${javaMethodSignature.methodName?cap_first}(${firstJavaMethodParameter.parameterName}

					<#if parameters?contains("Filter filter")>
						, (String)null
					</#if>

					<#if parameters?contains("Pagination pagination")>
						, Pagination.of(2, 1)
					</#if>

					<#if parameters?contains("Sort[] sorts")>
						, (String)null
					</#if>

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

						${firstJavaMethodParameter.parameterType} ${firstJavaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${firstJavaMethodParameter.parameterName?cap_first}();

						${schemaName} ${schemaVarName}1 = random${schemaName}();
						${schemaName} ${schemaVarName}2 = random${schemaName}();

						for (EntityField entityField : entityFields) {
							BeanUtils.setProperty(${schemaVarName}1, entityField.getName(), DateUtils.addMinutes(new Date(), -2));
						}

						${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, ${schemaVarName}1);

						Thread.sleep(1000);

						${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, ${schemaVarName}2);

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> page = invoke${javaMethodSignature.methodName?cap_first}(${firstJavaMethodParameter.parameterName}

							, getFilterString(entityField, "eq", ${schemaVarName}1)

							<#if parameters?contains("Pagination pagination")>
								, Pagination.of(2, 1)
							</#if>

							<#if parameters?contains("Sort[] sorts")>
								, (String)null
							</#if>

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

						${firstJavaMethodParameter.parameterType} ${firstJavaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${firstJavaMethodParameter.parameterName?cap_first}();

						${schemaName} ${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, random${schemaName}());
						${schemaName} ${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, random${schemaName}());

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> page = invoke${javaMethodSignature.methodName?cap_first}(${firstJavaMethodParameter.parameterName}

							, getFilterString(entityField, "eq", ${schemaVarName}1)

							<#if parameters?contains("Pagination pagination")>
								, Pagination.of(2, 1)
							</#if>

							<#if parameters?contains("Sort[] sorts")>
								, (String)null
							</#if>

							);

							assertEquals(Collections.singletonList(${schemaVarName}1), (List<${schemaName}>)page.getItems());
						}
					}
				</#if>

				<#if parameters?contains("Pagination pagination")>
					@Test
					public void test${javaMethodSignature.methodName?cap_first}WithPagination() throws Exception {
						${firstJavaMethodParameter.parameterType} ${firstJavaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${firstJavaMethodParameter.parameterName?cap_first}();

						${schemaName} ${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, random${schemaName}());
						${schemaName} ${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, random${schemaName}());
						${schemaName} ${schemaVarName}3 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, random${schemaName}());

						Page<${schemaName}> page1 = invoke${javaMethodSignature.methodName?cap_first}(${firstJavaMethodParameter.parameterName}

						<#if parameters?contains("Filter filter")>
							, (String)null
						</#if>

						, Pagination.of(2, 1)

						<#if parameters?contains("Sort[] sorts")>
							, (String)null
						</#if>

						);

						List<${schemaName}> ${schemaVarNames}1 = (List<${schemaName}>)page1.getItems();

						Assert.assertEquals(${schemaVarNames}1.toString(), 2, ${schemaVarNames}1.size());

						Page<${schemaName}> page2 = invoke${javaMethodSignature.methodName?cap_first}(${firstJavaMethodParameter.parameterName}

						<#if parameters?contains("Filter filter")>
							, (String)null
						</#if>

						, Pagination.of(2, 2)

						<#if parameters?contains("Sort[] sorts")>
							, (String)null
						</#if>

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

						${firstJavaMethodParameter.parameterType} ${firstJavaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${firstJavaMethodParameter.parameterName?cap_first}();

						${schemaName} ${schemaVarName}1 = random${schemaName}();
						${schemaName} ${schemaVarName}2 = random${schemaName}();

						for (EntityField entityField : entityFields) {
							BeanUtils.setProperty(${schemaVarName}1, entityField.getName(), DateUtils.addMinutes(new Date(), -2));
						}

						${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, ${schemaVarName}1);

						Thread.sleep(1000);

						${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, ${schemaVarName}2);

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> ascPage = invoke${javaMethodSignature.methodName?cap_first}(${firstJavaMethodParameter.parameterName}

							<#if parameters?contains("Filter filter")>
								, (String)null
							</#if>

							<#if parameters?contains("Pagination pagination")>
								, Pagination.of(2, 1)
							</#if>

							, entityField.getName() + ":asc"

							);

							assertEquals(Arrays.asList(${schemaVarName}1, ${schemaVarName}2), (List<${schemaName}>)ascPage.getItems());

							Page<${schemaName}> descPage = invoke${javaMethodSignature.methodName?cap_first}(${firstJavaMethodParameter.parameterName}

							<#if parameters?contains("Filter filter")>
								, (String)null
							</#if>

							<#if parameters?contains("Pagination pagination")>
								, Pagination.of(2, 1)
							</#if>

							, entityField.getName() + ":desc"

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

						${firstJavaMethodParameter.parameterType} ${firstJavaMethodParameter.parameterName} = test${javaMethodSignature.methodName?cap_first}_get${firstJavaMethodParameter.parameterName?cap_first}();

						${schemaName} ${schemaVarName}1 = random${schemaName}();
						${schemaName} ${schemaVarName}2 = random${schemaName}();

						for (EntityField entityField : entityFields) {
							BeanUtils.setProperty(${schemaVarName}1, entityField.getName(), "Aaa");
							BeanUtils.setProperty(${schemaVarName}2, entityField.getName(), "Bbb");
						}

						${schemaVarName}1 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, ${schemaVarName}1);
						${schemaVarName}2 = test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterName}, ${schemaVarName}2);

						for (EntityField entityField : entityFields) {
							Page<${schemaName}> ascPage = invoke${javaMethodSignature.methodName?cap_first}(${firstJavaMethodParameter.parameterName}

							<#if parameters?contains("Filter filter")>
								, (String)null
							</#if>

							<#if parameters?contains("Pagination pagination")>
								, Pagination.of(2, 1)
							</#if>

							, entityField.getName() + ":asc"

							);

							assertEquals(Arrays.asList(${schemaVarName}1, ${schemaVarName}2), (List<${schemaName}>)ascPage.getItems());

							Page<${schemaName}> descPage = invoke${javaMethodSignature.methodName?cap_first}(${firstJavaMethodParameter.parameterName}

							<#if parameters?contains("Filter filter")>
								, (String)null
							</#if>

							<#if parameters?contains("Pagination pagination")>
								, Pagination.of(2, 1)
							</#if>

							, entityField.getName() + ":desc"

							);

							assertEquals(Arrays.asList(${schemaVarName}2, ${schemaVarName}1), (List<${schemaName}>)descPage.getItems());
						}
					}
				</#if>

				protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}(${firstJavaMethodParameter.parameterType} ${firstJavaMethodParameter.parameterName}, ${schemaName} ${schemaVarName}) throws Exception {
					throw new UnsupportedOperationException("This method needs to be implemented");
				}

				protected ${firstJavaMethodParameter.parameterType} test${javaMethodSignature.methodName?cap_first}_get${firstJavaMethodParameter.parameterName?cap_first}() throws Exception {
					<#if stringUtil.equals(firstJavaMethodParameter.parameterName, "contentSpaceId")>
						return testGroup.getGroupId();
					<#else>
						throw new UnsupportedOperationException("This method needs to be implemented");
					</#if>
				}
			</#if>
		<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "get") && javaMethodSignature.returnType?matches(schemaName)>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				${schemaName} post${schemaName} = test${javaMethodSignature.methodName?cap_first}_add${schemaName}();

				${schemaName} get${schemaName} = invoke${javaMethodSignature.methodName?cap_first}(post${schemaName}.getId());

				assertEquals(post${schemaName}, get${schemaName});
				assertValid(get${schemaName});
			}

			protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
				throw new UnsupportedOperationException("This method needs to be implemented");
			}
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
				<#if arguments?ends_with(",multipartBody")>
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

			protected ${schemaName} test${javaMethodSignature.methodName?cap_first}_add${schemaName}() throws Exception {
				throw new UnsupportedOperationException("This method needs to be implemented");
			}
		<#else>
			@Test
			public void test${javaMethodSignature.methodName?cap_first}() throws Exception {
				Assert.assertTrue(true);
			}
		</#if>

		<#assign
			hasFilterAndSorts = false
			invokeArguments = arguments
			invokeParameters = parameters
		/>

		<#if invokeParameters?contains("Filter filter") && invokeParameters?contains("Sort[] sorts")>
			<#assign
				hasFilterAndSorts = true
				invokeArguments = invokeArguments?replace("filter", "filterString")?replace("sorts", "sortString")
				invokeParameters = invokeParameters?replace("Filter filter", "String filterString")?replace("Sort[] sorts", "String sortString")
			/>
		</#if>

		protected ${javaMethodSignature.returnType} invoke${javaMethodSignature.methodName?cap_first}(${invokeParameters}) throws Exception {
			Http.Options options = _createHttpOptions();

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post", "put") && invokeArguments?ends_with(",${schemaVarName}")>
				options.setBody(_inputObjectMapper.writeValueAsString(${schemaVarName}), ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
				options.setDelete(true);
			</#if>

			<#if hasFilterAndSorts>
				options.setLocation(_${javaMethodSignature.methodName?remove_ending("Page")}Location(${invokeArguments}));
			<#else>
				options.setLocation(_resourceURL + _toPath("${javaMethodSignature.path}", ${firstJavaMethodParameter.parameterName}));
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

		protected Http.Response invoke${javaMethodSignature.methodName?cap_first}Response(${invokeParameters}) throws Exception {
			Http.Options options = _createHttpOptions();

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post", "put") && invokeArguments?ends_with(",${schemaVarName}")>
				options.setBody(_inputObjectMapper.writeValueAsString(${schemaVarName}), ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "delete")>
				options.setDelete(true);
			</#if>

			<#if hasFilterAndSorts>
				options.setLocation(_${javaMethodSignature.methodName?remove_ending("Page")}Location(${invokeArguments}));
			<#else>
				options.setLocation(_resourceURL + _toPath("${javaMethodSignature.path}", ${firstJavaMethodParameter.parameterName}));
			</#if>

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "post")>
				options.setPost(true);
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "put")>
				options.setPut(true);
			</#if>

			HttpUtil.URLtoString(options);

			return options.getResponse();
		}

		<#if hasFilterAndSorts>
			private String _${javaMethodSignature.methodName?remove_ending("Page")}Location(${invokeParameters}) {
				String url = _resourceURL + _toPath("${javaMethodSignature.path}", ${firstJavaMethodParameter.parameterName});

				url += "?filter=" + URLCodec.encodeURL(filterString);
				url += "&page=" + pagination.getPageNumber();
				url += "&pageSize=" + pagination.getItemsPerPage();
				url += "&sort=" + URLCodec.encodeURL(sortString);

				return url;
			}
		</#if>
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

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

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

		<#list freeMarkerTool.getDTOJavaMethodParameters(configYAML, openAPIYAML, schema, false) as javaMethodParameter>
			if (entityFieldName.equals("${javaMethodParameter.parameterName}")) {
				<#if stringUtil.equals(javaMethodParameter.parameterType, "java.util.Date")>
					sb.append(_dateFormat.format(${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}()));

					return sb.toString();
				<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.lang.String")>
					sb.append("'");
					sb.append(String.valueOf(${schemaVarName}.get${javaMethodParameter.parameterName?cap_first}()));
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

				<#list freeMarkerTool.getDTOJavaMethodParameters(configYAML, openAPIYAML, schema, false) as javaMethodParameter>
					<#if randomDataTypes?seq_contains(javaMethodParameter.parameterType)>
						${javaMethodParameter.parameterName} = RandomTestUtil.random${javaMethodParameter.parameterType}();
					<#elseif stringUtil.equals(javaMethodParameter.parameterType, "java.util.Date")>
						${javaMethodParameter.parameterName} = RandomTestUtil.nextDate();
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

		public long getItemsPerPage() {
			return itemsPerPage;
		}

		public long getLastPageNumber() {
			return lastPageNumber;
		}

		public long getPageNumber() {
			return pageNumber;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected long itemsPerPage;

		@JsonProperty
		protected long lastPageNumber;

		@JsonProperty("page")
		protected long pageNumber;

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

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static DateFormat _dateFormat;
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	@Inject
	private ${schemaName}Resource _${schemaVarName}Resource;

	private URL _resourceURL;

}