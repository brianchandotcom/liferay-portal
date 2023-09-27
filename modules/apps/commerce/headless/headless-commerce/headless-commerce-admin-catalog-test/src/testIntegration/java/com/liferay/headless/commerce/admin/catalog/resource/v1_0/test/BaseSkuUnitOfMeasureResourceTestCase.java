/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.admin.catalog.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.SkuUnitOfMeasureResource;
import com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0.SkuUnitOfMeasureSerDes;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
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
public abstract class BaseSkuUnitOfMeasureResourceTestCase {

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

		_skuUnitOfMeasureResource.setContextCompany(testCompany);

		SkuUnitOfMeasureResource.Builder builder =
			SkuUnitOfMeasureResource.builder();

		skuUnitOfMeasureResource = builder.authentication(
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

		SkuUnitOfMeasure skuUnitOfMeasure1 = randomSkuUnitOfMeasure();

		String json = objectMapper.writeValueAsString(skuUnitOfMeasure1);

		SkuUnitOfMeasure skuUnitOfMeasure2 = SkuUnitOfMeasureSerDes.toDTO(json);

		Assert.assertTrue(equals(skuUnitOfMeasure1, skuUnitOfMeasure2));
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

		SkuUnitOfMeasure skuUnitOfMeasure = randomSkuUnitOfMeasure();

		String json1 = objectMapper.writeValueAsString(skuUnitOfMeasure);
		String json2 = SkuUnitOfMeasureSerDes.toJSON(skuUnitOfMeasure);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		SkuUnitOfMeasure skuUnitOfMeasure = randomSkuUnitOfMeasure();

		skuUnitOfMeasure.setKey(regex);
		skuUnitOfMeasure.setSku(regex);

		String json = SkuUnitOfMeasureSerDes.toJSON(skuUnitOfMeasure);

		Assert.assertFalse(json.contains(regex));

		skuUnitOfMeasure = SkuUnitOfMeasureSerDes.toDTO(json);

		Assert.assertEquals(regex, skuUnitOfMeasure.getKey());
		Assert.assertEquals(regex, skuUnitOfMeasure.getSku());
	}

	@Test
	public void testDeleteSkuUnitOfMeasure() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		SkuUnitOfMeasure skuUnitOfMeasure =
			testDeleteSkuUnitOfMeasure_addSkuUnitOfMeasure();

		assertHttpResponseStatusCode(
			204,
			skuUnitOfMeasureResource.deleteSkuUnitOfMeasureHttpResponse(
				skuUnitOfMeasure.getId()));

		assertHttpResponseStatusCode(
			404,
			skuUnitOfMeasureResource.getSkuUnitOfMeasureHttpResponse(
				skuUnitOfMeasure.getId()));

		assertHttpResponseStatusCode(
			404,
			skuUnitOfMeasureResource.getSkuUnitOfMeasureHttpResponse(
				skuUnitOfMeasure.getId()));
	}

	protected SkuUnitOfMeasure testDeleteSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteSkuUnitOfMeasure() throws Exception {
		SkuUnitOfMeasure skuUnitOfMeasure =
			testGraphQLDeleteSkuUnitOfMeasure_addSkuUnitOfMeasure();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteSkuUnitOfMeasure",
						new HashMap<String, Object>() {
							{
								put("id", skuUnitOfMeasure.getId());
							}
						})),
				"JSONObject/data", "Object/deleteSkuUnitOfMeasure"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"skuUnitOfMeasure",
					new HashMap<String, Object>() {
						{
							put("id", skuUnitOfMeasure.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected SkuUnitOfMeasure
			testGraphQLDeleteSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		return testGraphQLSkuUnitOfMeasure_addSkuUnitOfMeasure();
	}

	@Test
	public void testGetSkuUnitOfMeasure() throws Exception {
		SkuUnitOfMeasure postSkuUnitOfMeasure =
			testGetSkuUnitOfMeasure_addSkuUnitOfMeasure();

		SkuUnitOfMeasure getSkuUnitOfMeasure =
			skuUnitOfMeasureResource.getSkuUnitOfMeasure(
				postSkuUnitOfMeasure.getId());

		assertEquals(postSkuUnitOfMeasure, getSkuUnitOfMeasure);
		assertValid(getSkuUnitOfMeasure);
	}

	protected SkuUnitOfMeasure testGetSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSkuUnitOfMeasure() throws Exception {
		SkuUnitOfMeasure skuUnitOfMeasure =
			testGraphQLGetSkuUnitOfMeasure_addSkuUnitOfMeasure();

		Assert.assertTrue(
			equals(
				skuUnitOfMeasure,
				SkuUnitOfMeasureSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"skuUnitOfMeasure",
								new HashMap<String, Object>() {
									{
										put("id", skuUnitOfMeasure.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/skuUnitOfMeasure"))));
	}

	@Test
	public void testGraphQLGetSkuUnitOfMeasureNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"skuUnitOfMeasure",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected SkuUnitOfMeasure
			testGraphQLGetSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		return testGraphQLSkuUnitOfMeasure_addSkuUnitOfMeasure();
	}

	@Test
	public void testPatchSkuUnitOfMeasure() throws Exception {
		SkuUnitOfMeasure postSkuUnitOfMeasure =
			testPatchSkuUnitOfMeasure_addSkuUnitOfMeasure();

		SkuUnitOfMeasure randomPatchSkuUnitOfMeasure =
			randomPatchSkuUnitOfMeasure();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		SkuUnitOfMeasure patchSkuUnitOfMeasure =
			skuUnitOfMeasureResource.patchSkuUnitOfMeasure(
				postSkuUnitOfMeasure.getId(), randomPatchSkuUnitOfMeasure);

		SkuUnitOfMeasure expectedPatchSkuUnitOfMeasure =
			postSkuUnitOfMeasure.clone();

		BeanTestUtil.copyProperties(
			randomPatchSkuUnitOfMeasure, expectedPatchSkuUnitOfMeasure);

		SkuUnitOfMeasure getSkuUnitOfMeasure =
			skuUnitOfMeasureResource.getSkuUnitOfMeasure(
				patchSkuUnitOfMeasure.getId());

		assertEquals(expectedPatchSkuUnitOfMeasure, getSkuUnitOfMeasure);
		assertValid(getSkuUnitOfMeasure);
	}

	protected SkuUnitOfMeasure testPatchSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage()
		throws Exception {

		String externalReferenceCode =
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_getIrrelevantExternalReferenceCode();

		Page<SkuUnitOfMeasure> page =
			skuUnitOfMeasureResource.
				getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			SkuUnitOfMeasure irrelevantSkuUnitOfMeasure =
				testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
					irrelevantExternalReferenceCode,
					randomIrrelevantSkuUnitOfMeasure());

			page =
				skuUnitOfMeasureResource.
					getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSkuUnitOfMeasure),
				(List<SkuUnitOfMeasure>)page.getItems());
			assertValid(
				page,
				testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_getExpectedActions(
					irrelevantExternalReferenceCode));
		}

		SkuUnitOfMeasure skuUnitOfMeasure1 =
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				externalReferenceCode, randomSkuUnitOfMeasure());

		SkuUnitOfMeasure skuUnitOfMeasure2 =
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				externalReferenceCode, randomSkuUnitOfMeasure());

		page =
			skuUnitOfMeasureResource.
				getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(skuUnitOfMeasure1, skuUnitOfMeasure2),
			(List<SkuUnitOfMeasure>)page.getItems());
		assertValid(
			page,
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_getExpectedActions(
				externalReferenceCode));

		skuUnitOfMeasureResource.deleteSkuUnitOfMeasure(
			skuUnitOfMeasure1.getId());

		skuUnitOfMeasureResource.deleteSkuUnitOfMeasure(
			skuUnitOfMeasure2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_getExpectedActions(
				String externalReferenceCode)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_getExternalReferenceCode();

		SkuUnitOfMeasure skuUnitOfMeasure1 =
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				externalReferenceCode, randomSkuUnitOfMeasure());

		SkuUnitOfMeasure skuUnitOfMeasure2 =
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				externalReferenceCode, randomSkuUnitOfMeasure());

		SkuUnitOfMeasure skuUnitOfMeasure3 =
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				externalReferenceCode, randomSkuUnitOfMeasure());

		Page<SkuUnitOfMeasure> page1 =
			skuUnitOfMeasureResource.
				getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<SkuUnitOfMeasure> skuUnitOfMeasures1 =
			(List<SkuUnitOfMeasure>)page1.getItems();

		Assert.assertEquals(
			skuUnitOfMeasures1.toString(), 2, skuUnitOfMeasures1.size());

		Page<SkuUnitOfMeasure> page2 =
			skuUnitOfMeasureResource.
				getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<SkuUnitOfMeasure> skuUnitOfMeasures2 =
			(List<SkuUnitOfMeasure>)page2.getItems();

		Assert.assertEquals(
			skuUnitOfMeasures2.toString(), 1, skuUnitOfMeasures2.size());

		Page<SkuUnitOfMeasure> page3 =
			skuUnitOfMeasureResource.
				getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				skuUnitOfMeasure1, skuUnitOfMeasure2, skuUnitOfMeasure3),
			(List<SkuUnitOfMeasure>)page3.getItems());
	}

	protected SkuUnitOfMeasure
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				String externalReferenceCode, SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSkuByExternalReferenceCodeSkuUnitOfMeasuresPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostSkuByExternalReferenceCodeSkuUnitOfMeasure()
		throws Exception {

		SkuUnitOfMeasure randomSkuUnitOfMeasure = randomSkuUnitOfMeasure();

		SkuUnitOfMeasure postSkuUnitOfMeasure =
			testPostSkuByExternalReferenceCodeSkuUnitOfMeasure_addSkuUnitOfMeasure(
				randomSkuUnitOfMeasure);

		assertEquals(randomSkuUnitOfMeasure, postSkuUnitOfMeasure);
		assertValid(postSkuUnitOfMeasure);
	}

	protected SkuUnitOfMeasure
			testPostSkuByExternalReferenceCodeSkuUnitOfMeasure_addSkuUnitOfMeasure(
				SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSkuIdSkuUnitOfMeasuresPage() throws Exception {
		Long id = testGetSkuIdSkuUnitOfMeasuresPage_getId();
		Long irrelevantId = testGetSkuIdSkuUnitOfMeasuresPage_getIrrelevantId();

		Page<SkuUnitOfMeasure> page =
			skuUnitOfMeasureResource.getSkuIdSkuUnitOfMeasuresPage(
				id, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantId != null) {
			SkuUnitOfMeasure irrelevantSkuUnitOfMeasure =
				testGetSkuIdSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
					irrelevantId, randomIrrelevantSkuUnitOfMeasure());

			page = skuUnitOfMeasureResource.getSkuIdSkuUnitOfMeasuresPage(
				irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSkuUnitOfMeasure),
				(List<SkuUnitOfMeasure>)page.getItems());
			assertValid(
				page,
				testGetSkuIdSkuUnitOfMeasuresPage_getExpectedActions(
					irrelevantId));
		}

		SkuUnitOfMeasure skuUnitOfMeasure1 =
			testGetSkuIdSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				id, randomSkuUnitOfMeasure());

		SkuUnitOfMeasure skuUnitOfMeasure2 =
			testGetSkuIdSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				id, randomSkuUnitOfMeasure());

		page = skuUnitOfMeasureResource.getSkuIdSkuUnitOfMeasuresPage(
			id, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(skuUnitOfMeasure1, skuUnitOfMeasure2),
			(List<SkuUnitOfMeasure>)page.getItems());
		assertValid(
			page, testGetSkuIdSkuUnitOfMeasuresPage_getExpectedActions(id));

		skuUnitOfMeasureResource.deleteSkuUnitOfMeasure(
			skuUnitOfMeasure1.getId());

		skuUnitOfMeasureResource.deleteSkuUnitOfMeasure(
			skuUnitOfMeasure2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetSkuIdSkuUnitOfMeasuresPage_getExpectedActions(Long id)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetSkuIdSkuUnitOfMeasuresPageWithPagination()
		throws Exception {

		Long id = testGetSkuIdSkuUnitOfMeasuresPage_getId();

		SkuUnitOfMeasure skuUnitOfMeasure1 =
			testGetSkuIdSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				id, randomSkuUnitOfMeasure());

		SkuUnitOfMeasure skuUnitOfMeasure2 =
			testGetSkuIdSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				id, randomSkuUnitOfMeasure());

		SkuUnitOfMeasure skuUnitOfMeasure3 =
			testGetSkuIdSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				id, randomSkuUnitOfMeasure());

		Page<SkuUnitOfMeasure> page1 =
			skuUnitOfMeasureResource.getSkuIdSkuUnitOfMeasuresPage(
				id, Pagination.of(1, 2));

		List<SkuUnitOfMeasure> skuUnitOfMeasures1 =
			(List<SkuUnitOfMeasure>)page1.getItems();

		Assert.assertEquals(
			skuUnitOfMeasures1.toString(), 2, skuUnitOfMeasures1.size());

		Page<SkuUnitOfMeasure> page2 =
			skuUnitOfMeasureResource.getSkuIdSkuUnitOfMeasuresPage(
				id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<SkuUnitOfMeasure> skuUnitOfMeasures2 =
			(List<SkuUnitOfMeasure>)page2.getItems();

		Assert.assertEquals(
			skuUnitOfMeasures2.toString(), 1, skuUnitOfMeasures2.size());

		Page<SkuUnitOfMeasure> page3 =
			skuUnitOfMeasureResource.getSkuIdSkuUnitOfMeasuresPage(
				id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				skuUnitOfMeasure1, skuUnitOfMeasure2, skuUnitOfMeasure3),
			(List<SkuUnitOfMeasure>)page3.getItems());
	}

	protected SkuUnitOfMeasure
			testGetSkuIdSkuUnitOfMeasuresPage_addSkuUnitOfMeasure(
				Long id, SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSkuIdSkuUnitOfMeasuresPage_getId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSkuIdSkuUnitOfMeasuresPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostSkuIdSkuUnitOfMeasure() throws Exception {
		SkuUnitOfMeasure randomSkuUnitOfMeasure = randomSkuUnitOfMeasure();

		SkuUnitOfMeasure postSkuUnitOfMeasure =
			testPostSkuIdSkuUnitOfMeasure_addSkuUnitOfMeasure(
				randomSkuUnitOfMeasure);

		assertEquals(randomSkuUnitOfMeasure, postSkuUnitOfMeasure);
		assertValid(postSkuUnitOfMeasure);
	}

	protected SkuUnitOfMeasure
			testPostSkuIdSkuUnitOfMeasure_addSkuUnitOfMeasure(
				SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected SkuUnitOfMeasure testGraphQLSkuUnitOfMeasure_addSkuUnitOfMeasure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		SkuUnitOfMeasure skuUnitOfMeasure,
		List<SkuUnitOfMeasure> skuUnitOfMeasures) {

		boolean contains = false;

		for (SkuUnitOfMeasure item : skuUnitOfMeasures) {
			if (equals(skuUnitOfMeasure, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			skuUnitOfMeasures + " does not contain " + skuUnitOfMeasure,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		SkuUnitOfMeasure skuUnitOfMeasure1,
		SkuUnitOfMeasure skuUnitOfMeasure2) {

		Assert.assertTrue(
			skuUnitOfMeasure1 + " does not equal " + skuUnitOfMeasure2,
			equals(skuUnitOfMeasure1, skuUnitOfMeasure2));
	}

	protected void assertEquals(
		List<SkuUnitOfMeasure> skuUnitOfMeasures1,
		List<SkuUnitOfMeasure> skuUnitOfMeasures2) {

		Assert.assertEquals(
			skuUnitOfMeasures1.size(), skuUnitOfMeasures2.size());

		for (int i = 0; i < skuUnitOfMeasures1.size(); i++) {
			SkuUnitOfMeasure skuUnitOfMeasure1 = skuUnitOfMeasures1.get(i);
			SkuUnitOfMeasure skuUnitOfMeasure2 = skuUnitOfMeasures2.get(i);

			assertEquals(skuUnitOfMeasure1, skuUnitOfMeasure2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<SkuUnitOfMeasure> skuUnitOfMeasures1,
		List<SkuUnitOfMeasure> skuUnitOfMeasures2) {

		Assert.assertEquals(
			skuUnitOfMeasures1.size(), skuUnitOfMeasures2.size());

		for (SkuUnitOfMeasure skuUnitOfMeasure1 : skuUnitOfMeasures1) {
			boolean contains = false;

			for (SkuUnitOfMeasure skuUnitOfMeasure2 : skuUnitOfMeasures2) {
				if (equals(skuUnitOfMeasure1, skuUnitOfMeasure2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				skuUnitOfMeasures2 + " does not contain " + skuUnitOfMeasure1,
				contains);
		}
	}

	protected void assertValid(SkuUnitOfMeasure skuUnitOfMeasure)
		throws Exception {

		boolean valid = true;

		if (skuUnitOfMeasure.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("basePrice", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getBasePrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"incrementalOrderQuantity", additionalAssertFieldName)) {

				if (skuUnitOfMeasure.getIncrementalOrderQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("precision", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getPrecision() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getPrimary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("promoPrice", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getPromoPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("rate", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getRate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (skuUnitOfMeasure.getSkuId() == null) {
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

	protected void assertValid(Page<SkuUnitOfMeasure> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<SkuUnitOfMeasure> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<SkuUnitOfMeasure> skuUnitOfMeasures =
			page.getItems();

		int size = skuUnitOfMeasures.size();

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
						SkuUnitOfMeasure.class)) {

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

	protected boolean equals(
		SkuUnitOfMeasure skuUnitOfMeasure1,
		SkuUnitOfMeasure skuUnitOfMeasure2) {

		if (skuUnitOfMeasure1 == skuUnitOfMeasure2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)skuUnitOfMeasure1.getActions(),
						(Map)skuUnitOfMeasure2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getActive(),
						skuUnitOfMeasure2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("basePrice", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getBasePrice(),
						skuUnitOfMeasure2.getBasePrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getId(), skuUnitOfMeasure2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"incrementalOrderQuantity", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getIncrementalOrderQuantity(),
						skuUnitOfMeasure2.getIncrementalOrderQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getKey(),
						skuUnitOfMeasure2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)skuUnitOfMeasure1.getName(),
						(Map)skuUnitOfMeasure2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("precision", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getPrecision(),
						skuUnitOfMeasure2.getPrecision())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getPrimary(),
						skuUnitOfMeasure2.getPrimary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getPriority(),
						skuUnitOfMeasure2.getPriority())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("promoPrice", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getPromoPrice(),
						skuUnitOfMeasure2.getPromoPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("rate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getRate(),
						skuUnitOfMeasure2.getRate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getSku(),
						skuUnitOfMeasure2.getSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						skuUnitOfMeasure1.getSkuId(),
						skuUnitOfMeasure2.getSkuId())) {

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

		if (!(_skuUnitOfMeasureResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_skuUnitOfMeasureResource;

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
		EntityField entityField, String operator,
		SkuUnitOfMeasure skuUnitOfMeasure) {

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

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("basePrice")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("incrementalOrderQuantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			Object object = skuUnitOfMeasure.getKey();

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

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("precision")) {
			sb.append(String.valueOf(skuUnitOfMeasure.getPrecision()));

			return sb.toString();
		}

		if (entityFieldName.equals("primary")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priority")) {
			sb.append(String.valueOf(skuUnitOfMeasure.getPriority()));

			return sb.toString();
		}

		if (entityFieldName.equals("promoPrice")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("rate")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sku")) {
			Object object = skuUnitOfMeasure.getSku();

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

		if (entityFieldName.equals("skuId")) {
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

	protected SkuUnitOfMeasure randomSkuUnitOfMeasure() throws Exception {
		return new SkuUnitOfMeasure() {
			{
				active = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				precision = RandomTestUtil.randomInt();
				primary = RandomTestUtil.randomBoolean();
				priority = RandomTestUtil.randomDouble();
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				skuId = RandomTestUtil.randomLong();
			}
		};
	}

	protected SkuUnitOfMeasure randomIrrelevantSkuUnitOfMeasure()
		throws Exception {

		SkuUnitOfMeasure randomIrrelevantSkuUnitOfMeasure =
			randomSkuUnitOfMeasure();

		return randomIrrelevantSkuUnitOfMeasure;
	}

	protected SkuUnitOfMeasure randomPatchSkuUnitOfMeasure() throws Exception {
		return randomSkuUnitOfMeasure();
	}

	protected SkuUnitOfMeasureResource skuUnitOfMeasureResource;
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
		LogFactoryUtil.getLog(BaseSkuUnitOfMeasureResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.admin.catalog.resource.v1_0.
		SkuUnitOfMeasureResource _skuUnitOfMeasureResource;

}