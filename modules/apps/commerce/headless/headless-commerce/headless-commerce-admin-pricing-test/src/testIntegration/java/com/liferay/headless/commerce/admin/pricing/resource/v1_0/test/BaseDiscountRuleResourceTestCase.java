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

import com.liferay.headless.commerce.admin.pricing.client.dto.v1_0.DiscountRule;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v1_0.DiscountRuleResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v1_0.DiscountRuleSerDes;
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
public abstract class BaseDiscountRuleResourceTestCase {

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

		_discountRuleResource.setContextCompany(testCompany);

		DiscountRuleResource.Builder builder = DiscountRuleResource.builder();

		discountRuleResource = builder.authentication(
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

		DiscountRule discountRule1 = randomDiscountRule();

		String json = objectMapper.writeValueAsString(discountRule1);

		DiscountRule discountRule2 = DiscountRuleSerDes.toDTO(json);

		Assert.assertTrue(equals(discountRule1, discountRule2));
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

		DiscountRule discountRule = randomDiscountRule();

		String json1 = objectMapper.writeValueAsString(discountRule);
		String json2 = DiscountRuleSerDes.toJSON(discountRule);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DiscountRule discountRule = randomDiscountRule();

		discountRule.setType(regex);
		discountRule.setTypeSettings(regex);

		String json = DiscountRuleSerDes.toJSON(discountRule);

		Assert.assertFalse(json.contains(regex));

		discountRule = DiscountRuleSerDes.toDTO(json);

		Assert.assertEquals(regex, discountRule.getType());
		Assert.assertEquals(regex, discountRule.getTypeSettings());
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountRulesPage()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_getIrrelevantExternalReferenceCode();

		Page<DiscountRule> page =
			discountRuleResource.
				getDiscountByExternalReferenceCodeDiscountRulesPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			DiscountRule irrelevantDiscountRule =
				testGetDiscountByExternalReferenceCodeDiscountRulesPage_addDiscountRule(
					irrelevantExternalReferenceCode,
					randomIrrelevantDiscountRule());

			page =
				discountRuleResource.
					getDiscountByExternalReferenceCodeDiscountRulesPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountRule),
				(List<DiscountRule>)page.getItems());
			assertValid(
				page,
				testGetDiscountByExternalReferenceCodeDiscountRulesPage_getExpectedActions(
					irrelevantExternalReferenceCode));
		}

		DiscountRule discountRule1 =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_addDiscountRule(
				externalReferenceCode, randomDiscountRule());

		DiscountRule discountRule2 =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_addDiscountRule(
				externalReferenceCode, randomDiscountRule());

		page =
			discountRuleResource.
				getDiscountByExternalReferenceCodeDiscountRulesPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountRule1, discountRule2),
			(List<DiscountRule>)page.getItems());
		assertValid(
			page,
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_getExpectedActions(
				externalReferenceCode));

		discountRuleResource.deleteDiscountRule(discountRule1.getId());

		discountRuleResource.deleteDiscountRule(discountRule2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_getExpectedActions(
				String externalReferenceCode)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountRulesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_getExternalReferenceCode();

		DiscountRule discountRule1 =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_addDiscountRule(
				externalReferenceCode, randomDiscountRule());

		DiscountRule discountRule2 =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_addDiscountRule(
				externalReferenceCode, randomDiscountRule());

		DiscountRule discountRule3 =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_addDiscountRule(
				externalReferenceCode, randomDiscountRule());

		Page<DiscountRule> page1 =
			discountRuleResource.
				getDiscountByExternalReferenceCodeDiscountRulesPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<DiscountRule> discountRules1 =
			(List<DiscountRule>)page1.getItems();

		Assert.assertEquals(
			discountRules1.toString(), 2, discountRules1.size());

		Page<DiscountRule> page2 =
			discountRuleResource.
				getDiscountByExternalReferenceCodeDiscountRulesPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountRule> discountRules2 =
			(List<DiscountRule>)page2.getItems();

		Assert.assertEquals(
			discountRules2.toString(), 1, discountRules2.size());

		Page<DiscountRule> page3 =
			discountRuleResource.
				getDiscountByExternalReferenceCodeDiscountRulesPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(discountRule1, discountRule2, discountRule3),
			(List<DiscountRule>)page3.getItems());
	}

	protected DiscountRule
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_addDiscountRule(
				String externalReferenceCode, DiscountRule discountRule)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountByExternalReferenceCodeDiscountRule()
		throws Exception {

		DiscountRule randomDiscountRule = randomDiscountRule();

		DiscountRule postDiscountRule =
			testPostDiscountByExternalReferenceCodeDiscountRule_addDiscountRule(
				randomDiscountRule);

		assertEquals(randomDiscountRule, postDiscountRule);
		assertValid(postDiscountRule);
	}

	protected DiscountRule
			testPostDiscountByExternalReferenceCodeDiscountRule_addDiscountRule(
				DiscountRule discountRule)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteDiscountRule() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountRule discountRule = testDeleteDiscountRule_addDiscountRule();

		assertHttpResponseStatusCode(
			204,
			discountRuleResource.deleteDiscountRuleHttpResponse(
				discountRule.getId()));

		assertHttpResponseStatusCode(
			404,
			discountRuleResource.getDiscountRuleHttpResponse(
				discountRule.getId()));

		assertHttpResponseStatusCode(
			404,
			discountRuleResource.getDiscountRuleHttpResponse(
				discountRule.getId()));
	}

	protected DiscountRule testDeleteDiscountRule_addDiscountRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDiscountRule() throws Exception {
		DiscountRule discountRule =
			testGraphQLDeleteDiscountRule_addDiscountRule();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDiscountRule",
						new HashMap<String, Object>() {
							{
								put("id", discountRule.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDiscountRule"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"discountRule",
					new HashMap<String, Object>() {
						{
							put("id", discountRule.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected DiscountRule testGraphQLDeleteDiscountRule_addDiscountRule()
		throws Exception {

		return testGraphQLDiscountRule_addDiscountRule();
	}

	@Test
	public void testGetDiscountRule() throws Exception {
		DiscountRule postDiscountRule = testGetDiscountRule_addDiscountRule();

		DiscountRule getDiscountRule = discountRuleResource.getDiscountRule(
			postDiscountRule.getId());

		assertEquals(postDiscountRule, getDiscountRule);
		assertValid(getDiscountRule);
	}

	protected DiscountRule testGetDiscountRule_addDiscountRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDiscountRule() throws Exception {
		DiscountRule discountRule =
			testGraphQLGetDiscountRule_addDiscountRule();

		Assert.assertTrue(
			equals(
				discountRule,
				DiscountRuleSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"discountRule",
								new HashMap<String, Object>() {
									{
										put("id", discountRule.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/discountRule"))));
	}

	@Test
	public void testGraphQLGetDiscountRuleNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"discountRule",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected DiscountRule testGraphQLGetDiscountRule_addDiscountRule()
		throws Exception {

		return testGraphQLDiscountRule_addDiscountRule();
	}

	@Test
	public void testPatchDiscountRule() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetDiscountIdDiscountRulesPage() throws Exception {
		Long id = testGetDiscountIdDiscountRulesPage_getId();
		Long irrelevantId =
			testGetDiscountIdDiscountRulesPage_getIrrelevantId();

		Page<DiscountRule> page =
			discountRuleResource.getDiscountIdDiscountRulesPage(
				id, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantId != null) {
			DiscountRule irrelevantDiscountRule =
				testGetDiscountIdDiscountRulesPage_addDiscountRule(
					irrelevantId, randomIrrelevantDiscountRule());

			page = discountRuleResource.getDiscountIdDiscountRulesPage(
				irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountRule),
				(List<DiscountRule>)page.getItems());
			assertValid(
				page,
				testGetDiscountIdDiscountRulesPage_getExpectedActions(
					irrelevantId));
		}

		DiscountRule discountRule1 =
			testGetDiscountIdDiscountRulesPage_addDiscountRule(
				id, randomDiscountRule());

		DiscountRule discountRule2 =
			testGetDiscountIdDiscountRulesPage_addDiscountRule(
				id, randomDiscountRule());

		page = discountRuleResource.getDiscountIdDiscountRulesPage(
			id, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountRule1, discountRule2),
			(List<DiscountRule>)page.getItems());
		assertValid(
			page, testGetDiscountIdDiscountRulesPage_getExpectedActions(id));

		discountRuleResource.deleteDiscountRule(discountRule1.getId());

		discountRuleResource.deleteDiscountRule(discountRule2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetDiscountIdDiscountRulesPage_getExpectedActions(Long id)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetDiscountIdDiscountRulesPageWithPagination()
		throws Exception {

		Long id = testGetDiscountIdDiscountRulesPage_getId();

		DiscountRule discountRule1 =
			testGetDiscountIdDiscountRulesPage_addDiscountRule(
				id, randomDiscountRule());

		DiscountRule discountRule2 =
			testGetDiscountIdDiscountRulesPage_addDiscountRule(
				id, randomDiscountRule());

		DiscountRule discountRule3 =
			testGetDiscountIdDiscountRulesPage_addDiscountRule(
				id, randomDiscountRule());

		Page<DiscountRule> page1 =
			discountRuleResource.getDiscountIdDiscountRulesPage(
				id, Pagination.of(1, 2));

		List<DiscountRule> discountRules1 =
			(List<DiscountRule>)page1.getItems();

		Assert.assertEquals(
			discountRules1.toString(), 2, discountRules1.size());

		Page<DiscountRule> page2 =
			discountRuleResource.getDiscountIdDiscountRulesPage(
				id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountRule> discountRules2 =
			(List<DiscountRule>)page2.getItems();

		Assert.assertEquals(
			discountRules2.toString(), 1, discountRules2.size());

		Page<DiscountRule> page3 =
			discountRuleResource.getDiscountIdDiscountRulesPage(
				id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(discountRule1, discountRule2, discountRule3),
			(List<DiscountRule>)page3.getItems());
	}

	protected DiscountRule testGetDiscountIdDiscountRulesPage_addDiscountRule(
			Long id, DiscountRule discountRule)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountRulesPage_getId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountRulesPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountIdDiscountRule() throws Exception {
		DiscountRule randomDiscountRule = randomDiscountRule();

		DiscountRule postDiscountRule =
			testPostDiscountIdDiscountRule_addDiscountRule(randomDiscountRule);

		assertEquals(randomDiscountRule, postDiscountRule);
		assertValid(postDiscountRule);
	}

	protected DiscountRule testPostDiscountIdDiscountRule_addDiscountRule(
			DiscountRule discountRule)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscountRule testGraphQLDiscountRule_addDiscountRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		DiscountRule discountRule, List<DiscountRule> discountRules) {

		boolean contains = false;

		for (DiscountRule item : discountRules) {
			if (equals(discountRule, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			discountRules + " does not contain " + discountRule, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DiscountRule discountRule1, DiscountRule discountRule2) {

		Assert.assertTrue(
			discountRule1 + " does not equal " + discountRule2,
			equals(discountRule1, discountRule2));
	}

	protected void assertEquals(
		List<DiscountRule> discountRules1, List<DiscountRule> discountRules2) {

		Assert.assertEquals(discountRules1.size(), discountRules2.size());

		for (int i = 0; i < discountRules1.size(); i++) {
			DiscountRule discountRule1 = discountRules1.get(i);
			DiscountRule discountRule2 = discountRules2.get(i);

			assertEquals(discountRule1, discountRule2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscountRule> discountRules1, List<DiscountRule> discountRules2) {

		Assert.assertEquals(discountRules1.size(), discountRules2.size());

		for (DiscountRule discountRule1 : discountRules1) {
			boolean contains = false;

			for (DiscountRule discountRule2 : discountRules2) {
				if (equals(discountRule1, discountRule2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discountRules2 + " does not contain " + discountRule1,
				contains);
		}
	}

	protected void assertValid(DiscountRule discountRule) throws Exception {
		boolean valid = true;

		if (discountRule.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (discountRule.getDiscountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (discountRule.getType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("typeSettings", additionalAssertFieldName)) {
				if (discountRule.getTypeSettings() == null) {
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

	protected void assertValid(Page<DiscountRule> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<DiscountRule> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<DiscountRule> discountRules = page.getItems();

		int size = discountRules.size();

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
						DiscountRule.class)) {

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
		DiscountRule discountRule1, DiscountRule discountRule2) {

		if (discountRule1 == discountRule2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountRule1.getDiscountId(),
						discountRule2.getDiscountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountRule1.getId(), discountRule2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountRule1.getType(), discountRule2.getType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("typeSettings", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountRule1.getTypeSettings(),
						discountRule2.getTypeSettings())) {

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

		if (!(_discountRuleResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discountRuleResource;

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
		EntityField entityField, String operator, DiscountRule discountRule) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("discountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			Object object = discountRule.getType();

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

		if (entityFieldName.equals("typeSettings")) {
			Object object = discountRule.getTypeSettings();

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

	protected DiscountRule randomDiscountRule() throws Exception {
		return new DiscountRule() {
			{
				discountId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
				typeSettings = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected DiscountRule randomIrrelevantDiscountRule() throws Exception {
		DiscountRule randomIrrelevantDiscountRule = randomDiscountRule();

		return randomIrrelevantDiscountRule;
	}

	protected DiscountRule randomPatchDiscountRule() throws Exception {
		return randomDiscountRule();
	}

	protected DiscountRuleResource discountRuleResource;
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
		LogFactoryUtil.getLog(BaseDiscountRuleResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.admin.pricing.resource.v1_0.
		DiscountRuleResource _discountRuleResource;

}