/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Catalog;
import com.liferay.headless.commerce.admin.catalog.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.CatalogResource;
import com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0.CatalogSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseCatalogResourceTestCase {

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

		_catalogResource.setContextCompany(testCompany);

		CatalogResource.Builder builder = CatalogResource.builder();

		catalogResource = builder.authentication(
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

		Catalog catalog1 = randomCatalog();

		String json = objectMapper.writeValueAsString(catalog1);

		Catalog catalog2 = CatalogSerDes.toDTO(json);

		Assert.assertTrue(equals(catalog1, catalog2));
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

		Catalog catalog = randomCatalog();

		String json1 = objectMapper.writeValueAsString(catalog);
		String json2 = CatalogSerDes.toJSON(catalog);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Catalog catalog = randomCatalog();

		catalog.setCurrencyCode(regex);
		catalog.setDefaultLanguageId(regex);
		catalog.setExternalReferenceCode(regex);
		catalog.setName(regex);

		String json = CatalogSerDes.toJSON(catalog);

		Assert.assertFalse(json.contains(regex));

		catalog = CatalogSerDes.toDTO(json);

		Assert.assertEquals(regex, catalog.getCurrencyCode());
		Assert.assertEquals(regex, catalog.getDefaultLanguageId());
		Assert.assertEquals(regex, catalog.getExternalReferenceCode());
		Assert.assertEquals(regex, catalog.getName());
	}

	@Test
	public void testDeleteCatalogByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Catalog catalog = testDeleteCatalogByExternalReferenceCode_addCatalog();

		assertHttpResponseStatusCode(
			204,
			catalogResource.deleteCatalogByExternalReferenceCodeHttpResponse(
				catalog.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			catalogResource.getCatalogByExternalReferenceCodeHttpResponse(
				catalog.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			catalogResource.getCatalogByExternalReferenceCodeHttpResponse(
				catalog.getExternalReferenceCode()));
	}

	protected Catalog testDeleteCatalogByExternalReferenceCode_addCatalog()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetCatalogByExternalReferenceCode() throws Exception {
		Catalog postCatalog =
			testGetCatalogByExternalReferenceCode_addCatalog();

		Catalog getCatalog = catalogResource.getCatalogByExternalReferenceCode(
			postCatalog.getExternalReferenceCode());

		assertEquals(postCatalog, getCatalog);
		assertValid(getCatalog);
	}

	protected Catalog testGetCatalogByExternalReferenceCode_addCatalog()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCatalogByExternalReferenceCode()
		throws Exception {

		Catalog catalog =
			testGraphQLGetCatalogByExternalReferenceCode_addCatalog();

		Assert.assertTrue(
			equals(
				catalog,
				CatalogSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"catalogByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												catalog.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/catalogByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetCatalogByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"catalogByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Catalog testGraphQLGetCatalogByExternalReferenceCode_addCatalog()
		throws Exception {

		return testGraphQLCatalog_addCatalog();
	}

	@Test
	public void testPatchCatalogByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteCatalog() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Catalog catalog = testDeleteCatalog_addCatalog();

		assertHttpResponseStatusCode(
			204, catalogResource.deleteCatalogHttpResponse(catalog.getId()));

		assertHttpResponseStatusCode(
			404, catalogResource.getCatalogHttpResponse(catalog.getId()));

		assertHttpResponseStatusCode(
			404, catalogResource.getCatalogHttpResponse(catalog.getId()));
	}

	protected Catalog testDeleteCatalog_addCatalog() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteCatalog() throws Exception {
		Catalog catalog = testGraphQLDeleteCatalog_addCatalog();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteCatalog",
						new HashMap<String, Object>() {
							{
								put("id", catalog.getId());
							}
						})),
				"JSONObject/data", "Object/deleteCatalog"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"catalog",
					new HashMap<String, Object>() {
						{
							put("id", catalog.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected Catalog testGraphQLDeleteCatalog_addCatalog() throws Exception {
		return testGraphQLCatalog_addCatalog();
	}

	@Test
	public void testGetCatalog() throws Exception {
		Catalog postCatalog = testGetCatalog_addCatalog();

		Catalog getCatalog = catalogResource.getCatalog(postCatalog.getId());

		assertEquals(postCatalog, getCatalog);
		assertValid(getCatalog);
	}

	protected Catalog testGetCatalog_addCatalog() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCatalog() throws Exception {
		Catalog catalog = testGraphQLGetCatalog_addCatalog();

		Assert.assertTrue(
			equals(
				catalog,
				CatalogSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"catalog",
								new HashMap<String, Object>() {
									{
										put("id", catalog.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/catalog"))));
	}

	@Test
	public void testGraphQLGetCatalogNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"catalog",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Catalog testGraphQLGetCatalog_addCatalog() throws Exception {
		return testGraphQLCatalog_addCatalog();
	}

	@Test
	public void testPatchCatalog() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetCatalogsPage() throws Exception {
		Page<Catalog> page = catalogResource.getCatalogsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Catalog catalog1 = testGetCatalogsPage_addCatalog(randomCatalog());

		Catalog catalog2 = testGetCatalogsPage_addCatalog(randomCatalog());

		page = catalogResource.getCatalogsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(catalog1, (List<Catalog>)page.getItems());
		assertContains(catalog2, (List<Catalog>)page.getItems());
		assertValid(page, testGetCatalogsPage_getExpectedActions());

		catalogResource.deleteCatalog(catalog1.getId());

		catalogResource.deleteCatalog(catalog2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetCatalogsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetCatalogsPageWithFilterDateTimeEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Catalog catalog1 = randomCatalog();

		catalog1 = testGetCatalogsPage_addCatalog(catalog1);

		for (EntityField entityField : entityFields) {
			Page<Catalog> page = catalogResource.getCatalogsPage(
				null, getFilterString(entityField, "between", catalog1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(catalog1),
				(List<Catalog>)page.getItems());
		}
	}

	@Test
	public void testGetCatalogsPageWithFilterDoubleEquals() throws Exception {
		testGetCatalogsPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetCatalogsPageWithFilterStringContains() throws Exception {
		testGetCatalogsPageWithFilter("contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetCatalogsPageWithFilterStringEquals() throws Exception {
		testGetCatalogsPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetCatalogsPageWithFilterStringStartsWith()
		throws Exception {

		testGetCatalogsPageWithFilter("startswith", EntityField.Type.STRING);
	}

	protected void testGetCatalogsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Catalog catalog1 = testGetCatalogsPage_addCatalog(randomCatalog());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Catalog catalog2 = testGetCatalogsPage_addCatalog(randomCatalog());

		for (EntityField entityField : entityFields) {
			Page<Catalog> page = catalogResource.getCatalogsPage(
				null, getFilterString(entityField, operator, catalog1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(catalog1),
				(List<Catalog>)page.getItems());
		}
	}

	@Test
	public void testGetCatalogsPageWithPagination() throws Exception {
		Page<Catalog> totalPage = catalogResource.getCatalogsPage(
			null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Catalog catalog1 = testGetCatalogsPage_addCatalog(randomCatalog());

		Catalog catalog2 = testGetCatalogsPage_addCatalog(randomCatalog());

		Catalog catalog3 = testGetCatalogsPage_addCatalog(randomCatalog());

		Page<Catalog> page1 = catalogResource.getCatalogsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<Catalog> catalogs1 = (List<Catalog>)page1.getItems();

		Assert.assertEquals(
			catalogs1.toString(), totalCount + 2, catalogs1.size());

		Page<Catalog> page2 = catalogResource.getCatalogsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Catalog> catalogs2 = (List<Catalog>)page2.getItems();

		Assert.assertEquals(catalogs2.toString(), 1, catalogs2.size());

		Page<Catalog> page3 = catalogResource.getCatalogsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(catalog1, (List<Catalog>)page3.getItems());
		assertContains(catalog2, (List<Catalog>)page3.getItems());
		assertContains(catalog3, (List<Catalog>)page3.getItems());
	}

	@Test
	public void testGetCatalogsPageWithSortDateTime() throws Exception {
		testGetCatalogsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, catalog1, catalog2) -> {
				BeanTestUtil.setProperty(
					catalog1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetCatalogsPageWithSortDouble() throws Exception {
		testGetCatalogsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, catalog1, catalog2) -> {
				BeanTestUtil.setProperty(catalog1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(catalog2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetCatalogsPageWithSortInteger() throws Exception {
		testGetCatalogsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, catalog1, catalog2) -> {
				BeanTestUtil.setProperty(catalog1, entityField.getName(), 0);
				BeanTestUtil.setProperty(catalog2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetCatalogsPageWithSortString() throws Exception {
		testGetCatalogsPageWithSort(
			EntityField.Type.STRING,
			(entityField, catalog1, catalog2) -> {
				Class<?> clazz = catalog1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						catalog1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						catalog2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						catalog1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						catalog2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						catalog1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						catalog2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetCatalogsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Catalog, Catalog, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Catalog catalog1 = randomCatalog();
		Catalog catalog2 = randomCatalog();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, catalog1, catalog2);
		}

		catalog1 = testGetCatalogsPage_addCatalog(catalog1);

		catalog2 = testGetCatalogsPage_addCatalog(catalog2);

		for (EntityField entityField : entityFields) {
			Page<Catalog> ascPage = catalogResource.getCatalogsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(catalog1, catalog2),
				(List<Catalog>)ascPage.getItems());

			Page<Catalog> descPage = catalogResource.getCatalogsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(catalog2, catalog1),
				(List<Catalog>)descPage.getItems());
		}
	}

	protected Catalog testGetCatalogsPage_addCatalog(Catalog catalog)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCatalogsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"catalogs",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject catalogsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/catalogs");

		long totalCount = catalogsJSONObject.getLong("totalCount");

		Catalog catalog1 = testGraphQLGetCatalogsPage_addCatalog();
		Catalog catalog2 = testGraphQLGetCatalogsPage_addCatalog();

		catalogsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/catalogs");

		Assert.assertEquals(
			totalCount + 2, catalogsJSONObject.getLong("totalCount"));

		assertContains(
			catalog1,
			Arrays.asList(
				CatalogSerDes.toDTOs(catalogsJSONObject.getString("items"))));
		assertContains(
			catalog2,
			Arrays.asList(
				CatalogSerDes.toDTOs(catalogsJSONObject.getString("items"))));
	}

	protected Catalog testGraphQLGetCatalogsPage_addCatalog() throws Exception {
		return testGraphQLCatalog_addCatalog();
	}

	@Test
	public void testPostCatalog() throws Exception {
		Catalog randomCatalog = randomCatalog();

		Catalog postCatalog = testPostCatalog_addCatalog(randomCatalog);

		assertEquals(randomCatalog, postCatalog);
		assertValid(postCatalog);
	}

	protected Catalog testPostCatalog_addCatalog(Catalog catalog)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetProductByExternalReferenceCodeCatalog()
		throws Exception {

		Catalog postCatalog =
			testGetProductByExternalReferenceCodeCatalog_addCatalog();

		Catalog getCatalog =
			catalogResource.getProductByExternalReferenceCodeCatalog(
				testGetProductByExternalReferenceCodeCatalog_getExternalReferenceCode(
					postCatalog),
				Pagination.of(1, 2));

		assertEquals(postCatalog, getCatalog);
		assertValid(getCatalog);
	}

	protected String
			testGetProductByExternalReferenceCodeCatalog_getExternalReferenceCode(
				Catalog catalog)
		throws Exception {

		return catalog.getExternalReferenceCode();
	}

	protected Catalog testGetProductByExternalReferenceCodeCatalog_addCatalog()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeCatalog()
		throws Exception {

		Catalog catalog =
			testGraphQLGetProductByExternalReferenceCodeCatalog_addCatalog();

		Assert.assertTrue(
			equals(
				catalog,
				CatalogSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productByExternalReferenceCodeCatalog",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												testGraphQLGetProductByExternalReferenceCodeCatalog_getExternalReferenceCode(
													catalog) + "\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/productByExternalReferenceCodeCatalog"))));
	}

	protected String
			testGraphQLGetProductByExternalReferenceCodeCatalog_getExternalReferenceCode(
				Catalog catalog)
		throws Exception {

		return catalog.getExternalReferenceCode();
	}

	@Test
	public void testGraphQLGetProductByExternalReferenceCodeCatalogNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productByExternalReferenceCodeCatalog",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Catalog
			testGraphQLGetProductByExternalReferenceCodeCatalog_addCatalog()
		throws Exception {

		return testGraphQLCatalog_addCatalog();
	}

	@Test
	public void testGetProductIdCatalog() throws Exception {
		Catalog postCatalog = testGetProductIdCatalog_addCatalog();

		Catalog getCatalog = catalogResource.getProductIdCatalog(
			testGetProductIdCatalog_getId(postCatalog), Pagination.of(1, 2));

		assertEquals(postCatalog, getCatalog);
		assertValid(getCatalog);
	}

	protected Long testGetProductIdCatalog_getId(Catalog catalog)
		throws Exception {

		return catalog.getId();
	}

	protected Catalog testGetProductIdCatalog_addCatalog() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetProductIdCatalog() throws Exception {
		Catalog catalog = testGraphQLGetProductIdCatalog_addCatalog();

		Assert.assertTrue(
			equals(
				catalog,
				CatalogSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"productIdCatalog",
								new HashMap<String, Object>() {
									{
										put(
											"id",
											testGraphQLGetProductIdCatalog_getId(
												catalog));
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/productIdCatalog"))));
	}

	protected Long testGraphQLGetProductIdCatalog_getId(Catalog catalog)
		throws Exception {

		return catalog.getId();
	}

	@Test
	public void testGraphQLGetProductIdCatalogNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"productIdCatalog",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Catalog testGraphQLGetProductIdCatalog_addCatalog()
		throws Exception {

		return testGraphQLCatalog_addCatalog();
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Catalog testGraphQLCatalog_addCatalog() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(Catalog catalog, List<Catalog> catalogs) {
		boolean contains = false;

		for (Catalog item : catalogs) {
			if (equals(catalog, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(catalogs + " does not contain " + catalog, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Catalog catalog1, Catalog catalog2) {
		Assert.assertTrue(
			catalog1 + " does not equal " + catalog2,
			equals(catalog1, catalog2));
	}

	protected void assertEquals(
		List<Catalog> catalogs1, List<Catalog> catalogs2) {

		Assert.assertEquals(catalogs1.size(), catalogs2.size());

		for (int i = 0; i < catalogs1.size(); i++) {
			Catalog catalog1 = catalogs1.get(i);
			Catalog catalog2 = catalogs2.get(i);

			assertEquals(catalog1, catalog2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Catalog> catalogs1, List<Catalog> catalogs2) {

		Assert.assertEquals(catalogs1.size(), catalogs2.size());

		for (Catalog catalog1 : catalogs1) {
			boolean contains = false;

			for (Catalog catalog2 : catalogs2) {
				if (equals(catalog1, catalog2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				catalogs2 + " does not contain " + catalog1, contains);
		}
	}

	protected void assertValid(Catalog catalog) throws Exception {
		boolean valid = true;

		if (catalog.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (catalog.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (catalog.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (catalog.getCurrencyCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultLanguageId", additionalAssertFieldName)) {

				if (catalog.getDefaultLanguageId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (catalog.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (catalog.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("system", additionalAssertFieldName)) {
				if (catalog.getSystem() == null) {
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

	protected void assertValid(Page<Catalog> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Catalog> page, Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Catalog> catalogs = page.getItems();

		int size = catalogs.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.commerce.admin.catalog.dto.v1_0.
						Catalog.class)) {

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

	protected boolean equals(Catalog catalog1, Catalog catalog2) {
		if (catalog1 == catalog2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						catalog1.getAccountId(), catalog2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)catalog1.getActions(),
						(Map)catalog2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						catalog1.getCurrencyCode(),
						catalog2.getCurrencyCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultLanguageId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						catalog1.getDefaultLanguageId(),
						catalog2.getDefaultLanguageId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						catalog1.getExternalReferenceCode(),
						catalog2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(catalog1.getId(), catalog2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						catalog1.getName(), catalog2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("system", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						catalog1.getSystem(), catalog2.getSystem())) {

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

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_catalogResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_catalogResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator, Catalog catalog) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("currencyCode")) {
			Object object = catalog.getCurrencyCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultLanguageId")) {
			Object object = catalog.getDefaultLanguageId();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = catalog.getExternalReferenceCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			Object object = catalog.getName();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("system")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

	protected Catalog randomCatalog() throws Exception {
		return new Catalog() {
			{
				accountId = RandomTestUtil.randomLong();
				currencyCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				defaultLanguageId = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				system = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected Catalog randomIrrelevantCatalog() throws Exception {
		Catalog randomIrrelevantCatalog = randomCatalog();

		return randomIrrelevantCatalog;
	}

	protected Catalog randomPatchCatalog() throws Exception {
		return randomCatalog();
	}

	protected CatalogResource catalogResource;
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
		LogFactoryUtil.getLog(BaseCatalogResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.headless.commerce.admin.catalog.resource.v1_0.
			CatalogResource _catalogResource;

}