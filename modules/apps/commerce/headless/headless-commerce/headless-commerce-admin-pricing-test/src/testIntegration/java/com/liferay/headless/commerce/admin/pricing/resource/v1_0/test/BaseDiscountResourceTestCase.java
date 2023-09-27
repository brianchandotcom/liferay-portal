/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.pricing.client.dto.v1_0.Discount;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v1_0.DiscountResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v1_0.DiscountSerDes;
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
public abstract class BaseDiscountResourceTestCase {

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

		_discountResource.setContextCompany(testCompany);

		DiscountResource.Builder builder = DiscountResource.builder();

		discountResource = builder.authentication(
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

		Discount discount1 = randomDiscount();

		String json = objectMapper.writeValueAsString(discount1);

		Discount discount2 = DiscountSerDes.toDTO(json);

		Assert.assertTrue(equals(discount1, discount2));
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

		Discount discount = randomDiscount();

		String json1 = objectMapper.writeValueAsString(discount);
		String json2 = DiscountSerDes.toJSON(discount);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Discount discount = randomDiscount();

		discount.setCouponCode(regex);
		discount.setExternalReferenceCode(regex);
		discount.setLimitationType(regex);
		discount.setTarget(regex);
		discount.setTitle(regex);

		String json = DiscountSerDes.toJSON(discount);

		Assert.assertFalse(json.contains(regex));

		discount = DiscountSerDes.toDTO(json);

		Assert.assertEquals(regex, discount.getCouponCode());
		Assert.assertEquals(regex, discount.getExternalReferenceCode());
		Assert.assertEquals(regex, discount.getLimitationType());
		Assert.assertEquals(regex, discount.getTarget());
		Assert.assertEquals(regex, discount.getTitle());
	}

	@Test
	public void testGetDiscountsPage() throws Exception {
		Page<Discount> page = discountResource.getDiscountsPage(
			Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		Discount discount1 = testGetDiscountsPage_addDiscount(randomDiscount());

		Discount discount2 = testGetDiscountsPage_addDiscount(randomDiscount());

		page = discountResource.getDiscountsPage(Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(discount1, (List<Discount>)page.getItems());
		assertContains(discount2, (List<Discount>)page.getItems());
		assertValid(page, testGetDiscountsPage_getExpectedActions());

		discountResource.deleteDiscount(discount1.getId());

		discountResource.deleteDiscount(discount2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetDiscountsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetDiscountsPageWithPagination() throws Exception {
		Page<Discount> totalPage = discountResource.getDiscountsPage(null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Discount discount1 = testGetDiscountsPage_addDiscount(randomDiscount());

		Discount discount2 = testGetDiscountsPage_addDiscount(randomDiscount());

		Discount discount3 = testGetDiscountsPage_addDiscount(randomDiscount());

		Page<Discount> page1 = discountResource.getDiscountsPage(
			Pagination.of(1, totalCount + 2));

		List<Discount> discounts1 = (List<Discount>)page1.getItems();

		Assert.assertEquals(
			discounts1.toString(), totalCount + 2, discounts1.size());

		Page<Discount> page2 = discountResource.getDiscountsPage(
			Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Discount> discounts2 = (List<Discount>)page2.getItems();

		Assert.assertEquals(discounts2.toString(), 1, discounts2.size());

		Page<Discount> page3 = discountResource.getDiscountsPage(
			Pagination.of(1, totalCount + 3));

		assertContains(discount1, (List<Discount>)page3.getItems());
		assertContains(discount2, (List<Discount>)page3.getItems());
		assertContains(discount3, (List<Discount>)page3.getItems());
	}

	protected Discount testGetDiscountsPage_addDiscount(Discount discount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDiscountsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"discounts",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject discountsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/discounts");

		long totalCount = discountsJSONObject.getLong("totalCount");

		Discount discount1 = testGraphQLGetDiscountsPage_addDiscount();
		Discount discount2 = testGraphQLGetDiscountsPage_addDiscount();

		discountsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/discounts");

		Assert.assertEquals(
			totalCount + 2, discountsJSONObject.getLong("totalCount"));

		assertContains(
			discount1,
			Arrays.asList(
				DiscountSerDes.toDTOs(discountsJSONObject.getString("items"))));
		assertContains(
			discount2,
			Arrays.asList(
				DiscountSerDes.toDTOs(discountsJSONObject.getString("items"))));
	}

	protected Discount testGraphQLGetDiscountsPage_addDiscount()
		throws Exception {

		return testGraphQLDiscount_addDiscount();
	}

	@Test
	public void testPostDiscount() throws Exception {
		Discount randomDiscount = randomDiscount();

		Discount postDiscount = testPostDiscount_addDiscount(randomDiscount);

		assertEquals(randomDiscount, postDiscount);
		assertValid(postDiscount);
	}

	protected Discount testPostDiscount_addDiscount(Discount discount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteDiscountByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Discount discount =
			testDeleteDiscountByExternalReferenceCode_addDiscount();

		assertHttpResponseStatusCode(
			204,
			discountResource.deleteDiscountByExternalReferenceCodeHttpResponse(
				discount.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			discountResource.getDiscountByExternalReferenceCodeHttpResponse(
				discount.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			discountResource.getDiscountByExternalReferenceCodeHttpResponse(
				discount.getExternalReferenceCode()));
	}

	protected Discount testDeleteDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDiscountByExternalReferenceCode() throws Exception {
		Discount postDiscount =
			testGetDiscountByExternalReferenceCode_addDiscount();

		Discount getDiscount =
			discountResource.getDiscountByExternalReferenceCode(
				postDiscount.getExternalReferenceCode());

		assertEquals(postDiscount, getDiscount);
		assertValid(getDiscount);
	}

	protected Discount testGetDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDiscountByExternalReferenceCode()
		throws Exception {

		Discount discount =
			testGraphQLGetDiscountByExternalReferenceCode_addDiscount();

		Assert.assertTrue(
			equals(
				discount,
				DiscountSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"discountByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												discount.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/discountByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetDiscountByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"discountByExternalReferenceCode",
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

	protected Discount
			testGraphQLGetDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		return testGraphQLDiscount_addDiscount();
	}

	@Test
	public void testPatchDiscountByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteDiscount() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Discount discount = testDeleteDiscount_addDiscount();

		assertHttpResponseStatusCode(
			204, discountResource.deleteDiscountHttpResponse(discount.getId()));

		assertHttpResponseStatusCode(
			404, discountResource.getDiscountHttpResponse(discount.getId()));

		assertHttpResponseStatusCode(
			404, discountResource.getDiscountHttpResponse(discount.getId()));
	}

	protected Discount testDeleteDiscount_addDiscount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDiscount() throws Exception {
		Discount discount = testGraphQLDeleteDiscount_addDiscount();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDiscount",
						new HashMap<String, Object>() {
							{
								put("id", discount.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDiscount"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"discount",
					new HashMap<String, Object>() {
						{
							put("id", discount.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected Discount testGraphQLDeleteDiscount_addDiscount()
		throws Exception {

		return testGraphQLDiscount_addDiscount();
	}

	@Test
	public void testGetDiscount() throws Exception {
		Discount postDiscount = testGetDiscount_addDiscount();

		Discount getDiscount = discountResource.getDiscount(
			postDiscount.getId());

		assertEquals(postDiscount, getDiscount);
		assertValid(getDiscount);
	}

	protected Discount testGetDiscount_addDiscount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDiscount() throws Exception {
		Discount discount = testGraphQLGetDiscount_addDiscount();

		Assert.assertTrue(
			equals(
				discount,
				DiscountSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"discount",
								new HashMap<String, Object>() {
									{
										put("id", discount.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/discount"))));
	}

	@Test
	public void testGraphQLGetDiscountNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"discount",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Discount testGraphQLGetDiscount_addDiscount() throws Exception {
		return testGraphQLDiscount_addDiscount();
	}

	@Test
	public void testPatchDiscount() throws Exception {
		Assert.assertTrue(false);
	}

	protected Discount testGraphQLDiscount_addDiscount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(Discount discount, List<Discount> discounts) {
		boolean contains = false;

		for (Discount item : discounts) {
			if (equals(discount, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			discounts + " does not contain " + discount, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Discount discount1, Discount discount2) {
		Assert.assertTrue(
			discount1 + " does not equal " + discount2,
			equals(discount1, discount2));
	}

	protected void assertEquals(
		List<Discount> discounts1, List<Discount> discounts2) {

		Assert.assertEquals(discounts1.size(), discounts2.size());

		for (int i = 0; i < discounts1.size(); i++) {
			Discount discount1 = discounts1.get(i);
			Discount discount2 = discounts2.get(i);

			assertEquals(discount1, discount2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Discount> discounts1, List<Discount> discounts2) {

		Assert.assertEquals(discounts1.size(), discounts2.size());

		for (Discount discount1 : discounts1) {
			boolean contains = false;

			for (Discount discount2 : discounts2) {
				if (equals(discount1, discount2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discounts2 + " does not contain " + discount1, contains);
		}
	}

	protected void assertValid(Discount discount) throws Exception {
		boolean valid = true;

		if (discount.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (discount.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (discount.getCouponCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (discount.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountAccountGroups", additionalAssertFieldName)) {

				if (discount.getDiscountAccountGroups() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountCategories", additionalAssertFieldName)) {

				if (discount.getDiscountCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountProducts", additionalAssertFieldName)) {
				if (discount.getDiscountProducts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountRules", additionalAssertFieldName)) {
				if (discount.getDiscountRules() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (discount.getDisplayDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (discount.getExpirationDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (discount.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("limitationTimes", additionalAssertFieldName)) {
				if (discount.getLimitationTimes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("limitationType", additionalAssertFieldName)) {
				if (discount.getLimitationType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"maximumDiscountAmount", additionalAssertFieldName)) {

				if (discount.getMaximumDiscountAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (discount.getNeverExpire() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfUse", additionalAssertFieldName)) {
				if (discount.getNumberOfUse() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel1", additionalAssertFieldName)) {
				if (discount.getPercentageLevel1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel2", additionalAssertFieldName)) {
				if (discount.getPercentageLevel2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel3", additionalAssertFieldName)) {
				if (discount.getPercentageLevel3() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel4", additionalAssertFieldName)) {
				if (discount.getPercentageLevel4() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("target", additionalAssertFieldName)) {
				if (discount.getTarget() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (discount.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("useCouponCode", additionalAssertFieldName)) {
				if (discount.getUseCouponCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("usePercentage", additionalAssertFieldName)) {
				if (discount.getUsePercentage() == null) {
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

	protected void assertValid(Page<Discount> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Discount> page, Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Discount> discounts = page.getItems();

		int size = discounts.size();

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
					com.liferay.headless.commerce.admin.pricing.dto.v1_0.
						Discount.class)) {

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

	protected boolean equals(Discount discount1, Discount discount2) {
		if (discount1 == discount2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getActive(), discount2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getCouponCode(), discount2.getCouponCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)discount1.getCustomFields(),
						(Map)discount2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountAccountGroups", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getDiscountAccountGroups(),
						discount2.getDiscountAccountGroups())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getDiscountCategories(),
						discount2.getDiscountCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountProducts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getDiscountProducts(),
						discount2.getDiscountProducts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountRules", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getDiscountRules(),
						discount2.getDiscountRules())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getDisplayDate(),
						discount2.getDisplayDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getExpirationDate(),
						discount2.getExpirationDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getExternalReferenceCode(),
						discount2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(discount1.getId(), discount2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("limitationTimes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getLimitationTimes(),
						discount2.getLimitationTimes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("limitationType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getLimitationType(),
						discount2.getLimitationType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"maximumDiscountAmount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getMaximumDiscountAmount(),
						discount2.getMaximumDiscountAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getNeverExpire(),
						discount2.getNeverExpire())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfUse", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getNumberOfUse(),
						discount2.getNumberOfUse())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel1", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getPercentageLevel1(),
						discount2.getPercentageLevel1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel2", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getPercentageLevel2(),
						discount2.getPercentageLevel2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel3", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getPercentageLevel3(),
						discount2.getPercentageLevel3())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel4", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getPercentageLevel4(),
						discount2.getPercentageLevel4())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("target", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getTarget(), discount2.getTarget())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getTitle(), discount2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("useCouponCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getUseCouponCode(),
						discount2.getUseCouponCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("usePercentage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getUsePercentage(),
						discount2.getUsePercentage())) {

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

		if (!(_discountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discountResource;

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
		EntityField entityField, String operator, Discount discount) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("couponCode")) {
			Object object = discount.getCouponCode();

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

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountAccountGroups")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountProducts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountRules")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("displayDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(discount.getDisplayDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(discount.getDisplayDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(discount.getDisplayDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("expirationDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							discount.getExpirationDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(discount.getExpirationDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(discount.getExpirationDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = discount.getExternalReferenceCode();

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

		if (entityFieldName.equals("limitationTimes")) {
			sb.append(String.valueOf(discount.getLimitationTimes()));

			return sb.toString();
		}

		if (entityFieldName.equals("limitationType")) {
			Object object = discount.getLimitationType();

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

		if (entityFieldName.equals("maximumDiscountAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("neverExpire")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfUse")) {
			sb.append(String.valueOf(discount.getNumberOfUse()));

			return sb.toString();
		}

		if (entityFieldName.equals("percentageLevel1")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("percentageLevel2")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("percentageLevel3")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("percentageLevel4")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("target")) {
			Object object = discount.getTarget();

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

		if (entityFieldName.equals("title")) {
			Object object = discount.getTitle();

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

		if (entityFieldName.equals("useCouponCode")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("usePercentage")) {
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

	protected Discount randomDiscount() throws Exception {
		return new Discount() {
			{
				active = RandomTestUtil.randomBoolean();
				couponCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				limitationTimes = RandomTestUtil.randomInt();
				limitationType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				neverExpire = RandomTestUtil.randomBoolean();
				numberOfUse = RandomTestUtil.randomInt();
				target = StringUtil.toLowerCase(RandomTestUtil.randomString());
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				useCouponCode = RandomTestUtil.randomBoolean();
				usePercentage = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected Discount randomIrrelevantDiscount() throws Exception {
		Discount randomIrrelevantDiscount = randomDiscount();

		return randomIrrelevantDiscount;
	}

	protected Discount randomPatchDiscount() throws Exception {
		return randomDiscount();
	}

	protected DiscountResource discountResource;
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
		LogFactoryUtil.getLog(BaseDiscountResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.headless.commerce.admin.pricing.resource.v1_0.
			DiscountResource _discountResource;

}