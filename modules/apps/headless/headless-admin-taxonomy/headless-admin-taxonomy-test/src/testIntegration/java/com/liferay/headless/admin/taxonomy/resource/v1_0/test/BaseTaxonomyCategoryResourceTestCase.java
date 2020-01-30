/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.http.HttpInvoker;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.client.serdes.v1_0.TaxonomyCategorySerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseTaxonomyCategoryResourceTestCase {

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

		_taxonomyCategoryResource.setContextCompany(testCompany);

		TaxonomyCategoryResource.Builder builder =
			TaxonomyCategoryResource.builder();

		taxonomyCategoryResource = builder.locale(
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

		TaxonomyCategory taxonomyCategory1 = randomTaxonomyCategory();

		String json = objectMapper.writeValueAsString(taxonomyCategory1);

		TaxonomyCategory taxonomyCategory2 = TaxonomyCategorySerDes.toDTO(json);

		Assert.assertTrue(equals(taxonomyCategory1, taxonomyCategory2));
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

		TaxonomyCategory taxonomyCategory = randomTaxonomyCategory();

		String json1 = objectMapper.writeValueAsString(taxonomyCategory);
		String json2 = TaxonomyCategorySerDes.toJSON(taxonomyCategory);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		TaxonomyCategory taxonomyCategory = randomTaxonomyCategory();

		taxonomyCategory.setDescription(regex);
		taxonomyCategory.setExternalReferenceCode(regex);
		taxonomyCategory.setId(regex);
		taxonomyCategory.setName(regex);

		String json = TaxonomyCategorySerDes.toJSON(taxonomyCategory);

		Assert.assertFalse(json.contains(regex));

		taxonomyCategory = TaxonomyCategorySerDes.toDTO(json);

		Assert.assertEquals(regex, taxonomyCategory.getDescription());
		Assert.assertEquals(regex, taxonomyCategory.getExternalReferenceCode());
		Assert.assertEquals(regex, taxonomyCategory.getId());
		Assert.assertEquals(regex, taxonomyCategory.getName());
	}

	@Test
	public void testGetTaxonomyCategoryTaxonomyCategoriesPage()
		throws Exception {

		Page<TaxonomyCategory> page =
			taxonomyCategoryResource.getTaxonomyCategoryTaxonomyCategoriesPage(
				testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		String parentTaxonomyCategoryId =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId();
		String irrelevantParentTaxonomyCategoryId =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getIrrelevantParentTaxonomyCategoryId();

		if ((irrelevantParentTaxonomyCategoryId != null)) {
			TaxonomyCategory irrelevantTaxonomyCategory =
				testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
					irrelevantParentTaxonomyCategoryId,
					randomIrrelevantTaxonomyCategory());

			page =
				taxonomyCategoryResource.
					getTaxonomyCategoryTaxonomyCategoriesPage(
						irrelevantParentTaxonomyCategoryId, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTaxonomyCategory),
				(List<TaxonomyCategory>)page.getItems());
			assertValid(page);
		}

		TaxonomyCategory taxonomyCategory1 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, randomTaxonomyCategory());

		TaxonomyCategory taxonomyCategory2 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, randomTaxonomyCategory());

		page =
			taxonomyCategoryResource.getTaxonomyCategoryTaxonomyCategoriesPage(
				parentTaxonomyCategoryId, null, null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyCategory1, taxonomyCategory2),
			(List<TaxonomyCategory>)page.getItems());
		assertValid(page);

		taxonomyCategoryResource.deleteTaxonomyCategory(
			taxonomyCategory1.getId());

		taxonomyCategoryResource.deleteTaxonomyCategory(
			taxonomyCategory2.getId());
	}

	@Test
	public void testGetTaxonomyCategoryTaxonomyCategoriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		String parentTaxonomyCategoryId =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId();

		TaxonomyCategory taxonomyCategory1 = randomTaxonomyCategory();

		taxonomyCategory1 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, taxonomyCategory1);

		for (EntityField entityField : entityFields) {
			Page<TaxonomyCategory> page =
				taxonomyCategoryResource.
					getTaxonomyCategoryTaxonomyCategoriesPage(
						parentTaxonomyCategoryId, null,
						getFilterString(
							entityField, "between", taxonomyCategory1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(taxonomyCategory1),
				(List<TaxonomyCategory>)page.getItems());
		}
	}

	@Test
	public void testGetTaxonomyCategoryTaxonomyCategoriesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		String parentTaxonomyCategoryId =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId();

		TaxonomyCategory taxonomyCategory1 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, randomTaxonomyCategory());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyCategory taxonomyCategory2 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, randomTaxonomyCategory());

		for (EntityField entityField : entityFields) {
			Page<TaxonomyCategory> page =
				taxonomyCategoryResource.
					getTaxonomyCategoryTaxonomyCategoriesPage(
						parentTaxonomyCategoryId, null,
						getFilterString(entityField, "eq", taxonomyCategory1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(taxonomyCategory1),
				(List<TaxonomyCategory>)page.getItems());
		}
	}

	@Test
	public void testGetTaxonomyCategoryTaxonomyCategoriesPageWithPagination()
		throws Exception {

		String parentTaxonomyCategoryId =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId();

		TaxonomyCategory taxonomyCategory1 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, randomTaxonomyCategory());

		TaxonomyCategory taxonomyCategory2 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, randomTaxonomyCategory());

		TaxonomyCategory taxonomyCategory3 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, randomTaxonomyCategory());

		Page<TaxonomyCategory> page1 =
			taxonomyCategoryResource.getTaxonomyCategoryTaxonomyCategoriesPage(
				parentTaxonomyCategoryId, null, null, Pagination.of(1, 2),
				null);

		List<TaxonomyCategory> taxonomyCategories1 =
			(List<TaxonomyCategory>)page1.getItems();

		Assert.assertEquals(
			taxonomyCategories1.toString(), 2, taxonomyCategories1.size());

		Page<TaxonomyCategory> page2 =
			taxonomyCategoryResource.getTaxonomyCategoryTaxonomyCategoriesPage(
				parentTaxonomyCategoryId, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<TaxonomyCategory> taxonomyCategories2 =
			(List<TaxonomyCategory>)page2.getItems();

		Assert.assertEquals(
			taxonomyCategories2.toString(), 1, taxonomyCategories2.size());

		Page<TaxonomyCategory> page3 =
			taxonomyCategoryResource.getTaxonomyCategoryTaxonomyCategoriesPage(
				parentTaxonomyCategoryId, null, null, Pagination.of(1, 3),
				null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				taxonomyCategory1, taxonomyCategory2, taxonomyCategory3),
			(List<TaxonomyCategory>)page3.getItems());
	}

	@Test
	public void testGetTaxonomyCategoryTaxonomyCategoriesPageWithSortDateTime()
		throws Exception {

		testGetTaxonomyCategoryTaxonomyCategoriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, taxonomyCategory1, taxonomyCategory2) -> {
				BeanUtils.setProperty(
					taxonomyCategory1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetTaxonomyCategoryTaxonomyCategoriesPageWithSortInteger()
		throws Exception {

		testGetTaxonomyCategoryTaxonomyCategoriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, taxonomyCategory1, taxonomyCategory2) -> {
				BeanUtils.setProperty(
					taxonomyCategory1, entityField.getName(), 0);
				BeanUtils.setProperty(
					taxonomyCategory2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetTaxonomyCategoryTaxonomyCategoriesPageWithSortString()
		throws Exception {

		testGetTaxonomyCategoryTaxonomyCategoriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, taxonomyCategory1, taxonomyCategory2) -> {
				Class<?> clazz = taxonomyCategory1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						taxonomyCategory1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						taxonomyCategory2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						taxonomyCategory1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						taxonomyCategory2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetTaxonomyCategoryTaxonomyCategoriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, TaxonomyCategory, TaxonomyCategory, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String parentTaxonomyCategoryId =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId();

		TaxonomyCategory taxonomyCategory1 = randomTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = randomTaxonomyCategory();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, taxonomyCategory1, taxonomyCategory2);
		}

		taxonomyCategory1 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, taxonomyCategory1);

		taxonomyCategory2 =
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				parentTaxonomyCategoryId, taxonomyCategory2);

		for (EntityField entityField : entityFields) {
			Page<TaxonomyCategory> ascPage =
				taxonomyCategoryResource.
					getTaxonomyCategoryTaxonomyCategoriesPage(
						parentTaxonomyCategoryId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(taxonomyCategory1, taxonomyCategory2),
				(List<TaxonomyCategory>)ascPage.getItems());

			Page<TaxonomyCategory> descPage =
				taxonomyCategoryResource.
					getTaxonomyCategoryTaxonomyCategoriesPage(
						parentTaxonomyCategoryId, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(taxonomyCategory2, taxonomyCategory1),
				(List<TaxonomyCategory>)descPage.getItems());
		}
	}

	protected TaxonomyCategory
			testGetTaxonomyCategoryTaxonomyCategoriesPage_addTaxonomyCategory(
				String parentTaxonomyCategoryId,
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		return taxonomyCategoryResource.postTaxonomyCategoryTaxonomyCategory(
			parentTaxonomyCategoryId, taxonomyCategory);
	}

	protected String
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getIrrelevantParentTaxonomyCategoryId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostTaxonomyCategoryTaxonomyCategory() throws Exception {
		TaxonomyCategory randomTaxonomyCategory = randomTaxonomyCategory();

		TaxonomyCategory postTaxonomyCategory =
			testPostTaxonomyCategoryTaxonomyCategory_addTaxonomyCategory(
				randomTaxonomyCategory);

		assertEquals(randomTaxonomyCategory, postTaxonomyCategory);
		assertValid(postTaxonomyCategory);
	}

	protected TaxonomyCategory
			testPostTaxonomyCategoryTaxonomyCategory_addTaxonomyCategory(
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		return taxonomyCategoryResource.postTaxonomyCategoryTaxonomyCategory(
			testGetTaxonomyCategoryTaxonomyCategoriesPage_getParentTaxonomyCategoryId(),
			taxonomyCategory);
	}

	@Test
	public void testDeleteTaxonomyCategory() throws Exception {
		TaxonomyCategory taxonomyCategory =
			testDeleteTaxonomyCategory_addTaxonomyCategory();

		assertHttpResponseStatusCode(
			204,
			taxonomyCategoryResource.deleteTaxonomyCategoryHttpResponse(
				taxonomyCategory.getId()));

		assertHttpResponseStatusCode(
			404,
			taxonomyCategoryResource.getTaxonomyCategoryHttpResponse(
				taxonomyCategory.getId()));

		assertHttpResponseStatusCode(
			404, taxonomyCategoryResource.getTaxonomyCategoryHttpResponse("-"));
	}

	protected TaxonomyCategory testDeleteTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteTaxonomyCategory() throws Exception {
		TaxonomyCategory taxonomyCategory =
			testGraphQLTaxonomyCategory_addTaxonomyCategory();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteTaxonomyCategory",
				new HashMap<String, Object>() {
					{
						put(
							"taxonomyCategoryId",
							"\"" + taxonomyCategory.getId() + "\"");
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteTaxonomyCategory"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"taxonomyCategory",
					new HashMap<String, Object>() {
						{
							put(
								"taxonomyCategoryId",
								"\"" + taxonomyCategory.getId() + "\"");
						}
					},
					new GraphQLField("id")));

			jsonObject = JSONFactoryUtil.createJSONObject(
				invoke(graphQLField.toString()));

			JSONArray errorsJSONArray = jsonObject.getJSONArray("errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetTaxonomyCategory() throws Exception {
		TaxonomyCategory postTaxonomyCategory =
			testGetTaxonomyCategory_addTaxonomyCategory();

		TaxonomyCategory getTaxonomyCategory =
			taxonomyCategoryResource.getTaxonomyCategory(
				postTaxonomyCategory.getId());

		assertEquals(postTaxonomyCategory, getTaxonomyCategory);
		assertValid(getTaxonomyCategory);
	}

	protected TaxonomyCategory testGetTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetTaxonomyCategory() throws Exception {
		TaxonomyCategory taxonomyCategory =
			testGraphQLTaxonomyCategory_addTaxonomyCategory();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"taxonomyCategory",
				new HashMap<String, Object>() {
					{
						put(
							"taxonomyCategoryId",
							"\"" + taxonomyCategory.getId() + "\"");
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				taxonomyCategory,
				dataJSONObject.getJSONObject("taxonomyCategory")));
	}

	@Test
	public void testPatchTaxonomyCategory() throws Exception {
		TaxonomyCategory postTaxonomyCategory =
			testPatchTaxonomyCategory_addTaxonomyCategory();

		TaxonomyCategory randomPatchTaxonomyCategory =
			randomPatchTaxonomyCategory();

		TaxonomyCategory patchTaxonomyCategory =
			taxonomyCategoryResource.patchTaxonomyCategory(
				postTaxonomyCategory.getId(), randomPatchTaxonomyCategory);

		TaxonomyCategory expectedPatchTaxonomyCategory =
			(TaxonomyCategory)BeanUtils.cloneBean(postTaxonomyCategory);

		_beanUtilsBean.copyProperties(
			expectedPatchTaxonomyCategory, randomPatchTaxonomyCategory);

		TaxonomyCategory getTaxonomyCategory =
			taxonomyCategoryResource.getTaxonomyCategory(
				patchTaxonomyCategory.getId());

		assertEquals(expectedPatchTaxonomyCategory, getTaxonomyCategory);
		assertValid(getTaxonomyCategory);
	}

	protected TaxonomyCategory testPatchTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutTaxonomyCategory() throws Exception {
		TaxonomyCategory postTaxonomyCategory =
			testPutTaxonomyCategory_addTaxonomyCategory();

		TaxonomyCategory randomTaxonomyCategory = randomTaxonomyCategory();

		TaxonomyCategory putTaxonomyCategory =
			taxonomyCategoryResource.putTaxonomyCategory(
				postTaxonomyCategory.getId(), randomTaxonomyCategory);

		assertEquals(randomTaxonomyCategory, putTaxonomyCategory);
		assertValid(putTaxonomyCategory);

		TaxonomyCategory getTaxonomyCategory =
			taxonomyCategoryResource.getTaxonomyCategory(
				putTaxonomyCategory.getId());

		assertEquals(randomTaxonomyCategory, getTaxonomyCategory);
		assertValid(getTaxonomyCategory);
	}

	protected TaxonomyCategory testPutTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoriesPage()
		throws Exception {

		Page<TaxonomyCategory> page =
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoriesPage(
					testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId(),
					RandomTestUtil.randomString(), null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(0, page.getTotalCount());

		Long taxonomyVocabularyId =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId();
		Long irrelevantTaxonomyVocabularyId =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getIrrelevantTaxonomyVocabularyId();

		if ((irrelevantTaxonomyVocabularyId != null)) {
			TaxonomyCategory irrelevantTaxonomyCategory =
				testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
					irrelevantTaxonomyVocabularyId,
					randomIrrelevantTaxonomyCategory());

			page =
				taxonomyCategoryResource.
					getTaxonomyVocabularyTaxonomyCategoriesPage(
						irrelevantTaxonomyVocabularyId, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTaxonomyCategory),
				(List<TaxonomyCategory>)page.getItems());
			assertValid(page);
		}

		TaxonomyCategory taxonomyCategory1 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, randomTaxonomyCategory());

		TaxonomyCategory taxonomyCategory2 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, randomTaxonomyCategory());

		page =
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoriesPage(
					taxonomyVocabularyId, null, null, Pagination.of(1, 2),
					null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(taxonomyCategory1, taxonomyCategory2),
			(List<TaxonomyCategory>)page.getItems());
		assertValid(page);

		taxonomyCategoryResource.deleteTaxonomyCategory(
			taxonomyCategory1.getId());

		taxonomyCategoryResource.deleteTaxonomyCategory(
			taxonomyCategory2.getId());
	}

	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long taxonomyVocabularyId =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId();

		TaxonomyCategory taxonomyCategory1 = randomTaxonomyCategory();

		taxonomyCategory1 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, taxonomyCategory1);

		for (EntityField entityField : entityFields) {
			Page<TaxonomyCategory> page =
				taxonomyCategoryResource.
					getTaxonomyVocabularyTaxonomyCategoriesPage(
						taxonomyVocabularyId, null,
						getFilterString(
							entityField, "between", taxonomyCategory1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(taxonomyCategory1),
				(List<TaxonomyCategory>)page.getItems());
		}
	}

	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoriesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long taxonomyVocabularyId =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId();

		TaxonomyCategory taxonomyCategory1 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, randomTaxonomyCategory());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		TaxonomyCategory taxonomyCategory2 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, randomTaxonomyCategory());

		for (EntityField entityField : entityFields) {
			Page<TaxonomyCategory> page =
				taxonomyCategoryResource.
					getTaxonomyVocabularyTaxonomyCategoriesPage(
						taxonomyVocabularyId, null,
						getFilterString(entityField, "eq", taxonomyCategory1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(taxonomyCategory1),
				(List<TaxonomyCategory>)page.getItems());
		}
	}

	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoriesPageWithPagination()
		throws Exception {

		Long taxonomyVocabularyId =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId();

		TaxonomyCategory taxonomyCategory1 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, randomTaxonomyCategory());

		TaxonomyCategory taxonomyCategory2 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, randomTaxonomyCategory());

		TaxonomyCategory taxonomyCategory3 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, randomTaxonomyCategory());

		Page<TaxonomyCategory> page1 =
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoriesPage(
					taxonomyVocabularyId, null, null, Pagination.of(1, 2),
					null);

		List<TaxonomyCategory> taxonomyCategories1 =
			(List<TaxonomyCategory>)page1.getItems();

		Assert.assertEquals(
			taxonomyCategories1.toString(), 2, taxonomyCategories1.size());

		Page<TaxonomyCategory> page2 =
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoriesPage(
					taxonomyVocabularyId, null, null, Pagination.of(2, 2),
					null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<TaxonomyCategory> taxonomyCategories2 =
			(List<TaxonomyCategory>)page2.getItems();

		Assert.assertEquals(
			taxonomyCategories2.toString(), 1, taxonomyCategories2.size());

		Page<TaxonomyCategory> page3 =
			taxonomyCategoryResource.
				getTaxonomyVocabularyTaxonomyCategoriesPage(
					taxonomyVocabularyId, null, null, Pagination.of(1, 3),
					null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				taxonomyCategory1, taxonomyCategory2, taxonomyCategory3),
			(List<TaxonomyCategory>)page3.getItems());
	}

	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoriesPageWithSortDateTime()
		throws Exception {

		testGetTaxonomyVocabularyTaxonomyCategoriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, taxonomyCategory1, taxonomyCategory2) -> {
				BeanUtils.setProperty(
					taxonomyCategory1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoriesPageWithSortInteger()
		throws Exception {

		testGetTaxonomyVocabularyTaxonomyCategoriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, taxonomyCategory1, taxonomyCategory2) -> {
				BeanUtils.setProperty(
					taxonomyCategory1, entityField.getName(), 0);
				BeanUtils.setProperty(
					taxonomyCategory2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetTaxonomyVocabularyTaxonomyCategoriesPageWithSortString()
		throws Exception {

		testGetTaxonomyVocabularyTaxonomyCategoriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, taxonomyCategory1, taxonomyCategory2) -> {
				Class<?> clazz = taxonomyCategory1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						taxonomyCategory1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						taxonomyCategory2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						taxonomyCategory1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						taxonomyCategory2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetTaxonomyVocabularyTaxonomyCategoriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, TaxonomyCategory, TaxonomyCategory, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long taxonomyVocabularyId =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId();

		TaxonomyCategory taxonomyCategory1 = randomTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = randomTaxonomyCategory();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, taxonomyCategory1, taxonomyCategory2);
		}

		taxonomyCategory1 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, taxonomyCategory1);

		taxonomyCategory2 =
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				taxonomyVocabularyId, taxonomyCategory2);

		for (EntityField entityField : entityFields) {
			Page<TaxonomyCategory> ascPage =
				taxonomyCategoryResource.
					getTaxonomyVocabularyTaxonomyCategoriesPage(
						taxonomyVocabularyId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(taxonomyCategory1, taxonomyCategory2),
				(List<TaxonomyCategory>)ascPage.getItems());

			Page<TaxonomyCategory> descPage =
				taxonomyCategoryResource.
					getTaxonomyVocabularyTaxonomyCategoriesPage(
						taxonomyVocabularyId, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(taxonomyCategory2, taxonomyCategory1),
				(List<TaxonomyCategory>)descPage.getItems());
		}
	}

	protected TaxonomyCategory
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_addTaxonomyCategory(
				Long taxonomyVocabularyId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		return taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
			taxonomyVocabularyId, taxonomyCategory);
	}

	protected Long
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getIrrelevantTaxonomyVocabularyId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostTaxonomyVocabularyTaxonomyCategory() throws Exception {
		TaxonomyCategory randomTaxonomyCategory = randomTaxonomyCategory();

		TaxonomyCategory postTaxonomyCategory =
			testPostTaxonomyVocabularyTaxonomyCategory_addTaxonomyCategory(
				randomTaxonomyCategory);

		assertEquals(randomTaxonomyCategory, postTaxonomyCategory);
		assertValid(postTaxonomyCategory);
	}

	protected TaxonomyCategory
			testPostTaxonomyVocabularyTaxonomyCategory_addTaxonomyCategory(
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		return taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
			testGetTaxonomyVocabularyTaxonomyCategoriesPage_getTaxonomyVocabularyId(),
			taxonomyCategory);
	}

	protected TaxonomyCategory testGraphQLTaxonomyCategory_addTaxonomyCategory()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		TaxonomyCategory taxonomyCategory1,
		TaxonomyCategory taxonomyCategory2) {

		Assert.assertTrue(
			taxonomyCategory1 + " does not equal " + taxonomyCategory2,
			equals(taxonomyCategory1, taxonomyCategory2));
	}

	protected void assertEquals(
		List<TaxonomyCategory> taxonomyCategories1,
		List<TaxonomyCategory> taxonomyCategories2) {

		Assert.assertEquals(
			taxonomyCategories1.size(), taxonomyCategories2.size());

		for (int i = 0; i < taxonomyCategories1.size(); i++) {
			TaxonomyCategory taxonomyCategory1 = taxonomyCategories1.get(i);
			TaxonomyCategory taxonomyCategory2 = taxonomyCategories2.get(i);

			assertEquals(taxonomyCategory1, taxonomyCategory2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<TaxonomyCategory> taxonomyCategories1,
		List<TaxonomyCategory> taxonomyCategories2) {

		Assert.assertEquals(
			taxonomyCategories1.size(), taxonomyCategories2.size());

		for (TaxonomyCategory taxonomyCategory1 : taxonomyCategories1) {
			boolean contains = false;

			for (TaxonomyCategory taxonomyCategory2 : taxonomyCategories2) {
				if (equals(taxonomyCategory1, taxonomyCategory2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				taxonomyCategories2 + " does not contain " + taxonomyCategory1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<TaxonomyCategory> taxonomyCategories, JSONArray jsonArray) {

		for (TaxonomyCategory taxonomyCategory : taxonomyCategories) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(taxonomyCategory, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + taxonomyCategory, contains);
		}
	}

	protected void assertValid(TaxonomyCategory taxonomyCategory) {
		boolean valid = true;

		if (taxonomyCategory.getDateCreated() == null) {
			valid = false;
		}

		if (taxonomyCategory.getDateModified() == null) {
			valid = false;
		}

		if (taxonomyCategory.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (taxonomyCategory.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (taxonomyCategory.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (taxonomyCategory.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (taxonomyCategory.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (taxonomyCategory.getDescription_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (taxonomyCategory.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (taxonomyCategory.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (taxonomyCategory.getName_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfTaxonomyCategories", additionalAssertFieldName)) {

				if (taxonomyCategory.getNumberOfTaxonomyCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentTaxonomyCategory", additionalAssertFieldName)) {

				if (taxonomyCategory.getParentTaxonomyCategory() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentTaxonomyVocabulary", additionalAssertFieldName)) {

				if (taxonomyCategory.getParentTaxonomyVocabulary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (taxonomyCategory.getViewableBy() == null) {
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

	protected void assertValid(Page<TaxonomyCategory> page) {
		boolean valid = false;

		java.util.Collection<TaxonomyCategory> taxonomyCategories =
			page.getItems();

		int size = taxonomyCategories.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		TaxonomyCategory taxonomyCategory1,
		TaxonomyCategory taxonomyCategory2) {

		if (taxonomyCategory1 == taxonomyCategory2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getActions(),
						taxonomyCategory2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						taxonomyCategory1.getAvailableLanguages(),
						taxonomyCategory2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getCreator(),
						taxonomyCategory2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getDateCreated(),
						taxonomyCategory2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getDateModified(),
						taxonomyCategory2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getDescription(),
						taxonomyCategory2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getDescription_i18n(),
						taxonomyCategory2.getDescription_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						taxonomyCategory1.getExternalReferenceCode(),
						taxonomyCategory2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getId(), taxonomyCategory2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getName(),
						taxonomyCategory2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getName_i18n(),
						taxonomyCategory2.getName_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfTaxonomyCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						taxonomyCategory1.getNumberOfTaxonomyCategories(),
						taxonomyCategory2.getNumberOfTaxonomyCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentTaxonomyCategory", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						taxonomyCategory1.getParentTaxonomyCategory(),
						taxonomyCategory2.getParentTaxonomyCategory())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentTaxonomyVocabulary", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						taxonomyCategory1.getParentTaxonomyVocabulary(),
						taxonomyCategory2.getParentTaxonomyVocabulary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory1.getViewableBy(),
						taxonomyCategory2.getViewableBy())) {

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

	protected boolean equalsJSONObject(
		TaxonomyCategory taxonomyCategory, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("externalReferenceCode", fieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory.getExternalReferenceCode(),
						jsonObject.getString("externalReferenceCode"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory.getId(), jsonObject.getString("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory.getName(),
						jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfTaxonomyCategories", fieldName)) {
				if (!Objects.deepEquals(
						taxonomyCategory.getNumberOfTaxonomyCategories(),
						jsonObject.getInt("numberOfTaxonomyCategories"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_taxonomyCategoryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_taxonomyCategoryResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		TaxonomyCategory taxonomyCategory) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							taxonomyCategory.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							taxonomyCategory.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(taxonomyCategory.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							taxonomyCategory.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							taxonomyCategory.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(taxonomyCategory.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(taxonomyCategory.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(taxonomyCategory.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			sb.append("'");
			sb.append(String.valueOf(taxonomyCategory.getId()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(taxonomyCategory.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfTaxonomyCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentTaxonomyCategory")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentTaxonomyVocabulary")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("viewableBy")) {
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

	protected TaxonomyCategory randomTaxonomyCategory() throws Exception {
		return new TaxonomyCategory() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				externalReferenceCode = RandomTestUtil.randomString();
				id = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				numberOfTaxonomyCategories = RandomTestUtil.randomInt();
			}
		};
	}

	protected TaxonomyCategory randomIrrelevantTaxonomyCategory()
		throws Exception {

		TaxonomyCategory randomIrrelevantTaxonomyCategory =
			randomTaxonomyCategory();

		return randomIrrelevantTaxonomyCategory;
	}

	protected TaxonomyCategory randomPatchTaxonomyCategory() throws Exception {
		return randomTaxonomyCategory();
	}

	protected TaxonomyCategoryResource taxonomyCategoryResource;
	protected Group irrelevantGroup;
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

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
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

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseTaxonomyCategoryResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.headless.admin.taxonomy.resource.v1_0.
			TaxonomyCategoryResource _taxonomyCategoryResource;

}