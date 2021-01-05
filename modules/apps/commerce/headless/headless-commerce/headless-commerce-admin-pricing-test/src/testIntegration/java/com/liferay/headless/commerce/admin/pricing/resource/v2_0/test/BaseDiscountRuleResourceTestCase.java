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

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountRule;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.DiscountRuleResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.DiscountRuleSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
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
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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

		discountRule.setName(regex);
		discountRule.setType(regex);
		discountRule.setTypeSettings(regex);

		String json = DiscountRuleSerDes.toJSON(discountRule);

		Assert.assertFalse(json.contains(regex));

		discountRule = DiscountRuleSerDes.toDTO(json);

		Assert.assertEquals(regex, discountRule.getName());
		Assert.assertEquals(regex, discountRule.getType());
		Assert.assertEquals(regex, discountRule.getTypeSettings());
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
		DiscountRule discountRule = testGraphQLDiscountRule_addDiscountRule();

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

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

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
		DiscountRule discountRule = testGraphQLDiscountRule_addDiscountRule();

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

	@Test
	public void testPatchDiscountRule() throws Exception {
		DiscountRule postDiscountRule = testPatchDiscountRule_addDiscountRule();

		DiscountRule randomPatchDiscountRule = randomPatchDiscountRule();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountRule patchDiscountRule = discountRuleResource.patchDiscountRule(
			postDiscountRule.getId(), randomPatchDiscountRule);

		DiscountRule expectedPatchDiscountRule = postDiscountRule.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchDiscountRule, randomPatchDiscountRule);

		DiscountRule getDiscountRule = discountRuleResource.getDiscountRule(
			patchDiscountRule.getId());

		assertEquals(expectedPatchDiscountRule, getDiscountRule);
		assertValid(getDiscountRule);
	}

	protected DiscountRule testPatchDiscountRule_addDiscountRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountRulesPage()
		throws Exception {

		Page<DiscountRule> page =
			discountRuleResource.
				getDiscountByExternalReferenceCodeDiscountRulesPage(
					testGetDiscountByExternalReferenceCodeDiscountRulesPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountRulesPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
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
			assertValid(page);
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
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountRule1, discountRule2),
			(List<DiscountRule>)page.getItems());
		assertValid(page);

		discountRuleResource.deleteDiscountRule(discountRule1.getId());

		discountRuleResource.deleteDiscountRule(discountRule2.getId());
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
	public void testGetDiscountIdDiscountRulesPage() throws Exception {
		Page<DiscountRule> page =
			discountRuleResource.getDiscountIdDiscountRulesPage(
				testGetDiscountIdDiscountRulesPage_getId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetDiscountIdDiscountRulesPage_getId();
		Long irrelevantId =
			testGetDiscountIdDiscountRulesPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			DiscountRule irrelevantDiscountRule =
				testGetDiscountIdDiscountRulesPage_addDiscountRule(
					irrelevantId, randomIrrelevantDiscountRule());

			page = discountRuleResource.getDiscountIdDiscountRulesPage(
				irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountRule),
				(List<DiscountRule>)page.getItems());
			assertValid(page);
		}

		DiscountRule discountRule1 =
			testGetDiscountIdDiscountRulesPage_addDiscountRule(
				id, randomDiscountRule());

		DiscountRule discountRule2 =
			testGetDiscountIdDiscountRulesPage_addDiscountRule(
				id, randomDiscountRule());

		page = discountRuleResource.getDiscountIdDiscountRulesPage(
			id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountRule1, discountRule2),
			(List<DiscountRule>)page.getItems());
		assertValid(page);

		discountRuleResource.deleteDiscountRule(discountRule1.getId());

		discountRuleResource.deleteDiscountRule(discountRule2.getId());
	}

	@Test
	public void testGetDiscountIdDiscountRulesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountRulesPage_getId();

		DiscountRule discountRule1 = randomDiscountRule();

		discountRule1 = testGetDiscountIdDiscountRulesPage_addDiscountRule(
			id, discountRule1);

		for (EntityField entityField : entityFields) {
			Page<DiscountRule> page =
				discountRuleResource.getDiscountIdDiscountRulesPage(
					id, null,
					getFilterString(entityField, "between", discountRule1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountRule1),
				(List<DiscountRule>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountRulesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountRulesPage_getId();

		DiscountRule discountRule1 =
			testGetDiscountIdDiscountRulesPage_addDiscountRule(
				id, randomDiscountRule());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountRule discountRule2 =
			testGetDiscountIdDiscountRulesPage_addDiscountRule(
				id, randomDiscountRule());

		for (EntityField entityField : entityFields) {
			Page<DiscountRule> page =
				discountRuleResource.getDiscountIdDiscountRulesPage(
					id, null, getFilterString(entityField, "eq", discountRule1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountRule1),
				(List<DiscountRule>)page.getItems());
		}
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
				id, null, null, Pagination.of(1, 2), null);

		List<DiscountRule> discountRules1 =
			(List<DiscountRule>)page1.getItems();

		Assert.assertEquals(
			discountRules1.toString(), 2, discountRules1.size());

		Page<DiscountRule> page2 =
			discountRuleResource.getDiscountIdDiscountRulesPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountRule> discountRules2 =
			(List<DiscountRule>)page2.getItems();

		Assert.assertEquals(
			discountRules2.toString(), 1, discountRules2.size());

		Page<DiscountRule> page3 =
			discountRuleResource.getDiscountIdDiscountRulesPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(discountRule1, discountRule2, discountRule3),
			(List<DiscountRule>)page3.getItems());
	}

	@Test
	public void testGetDiscountIdDiscountRulesPageWithSortDateTime()
		throws Exception {

		testGetDiscountIdDiscountRulesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, discountRule1, discountRule2) -> {
				BeanUtils.setProperty(
					discountRule1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDiscountIdDiscountRulesPageWithSortInteger()
		throws Exception {

		testGetDiscountIdDiscountRulesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, discountRule1, discountRule2) -> {
				BeanUtils.setProperty(discountRule1, entityField.getName(), 0);
				BeanUtils.setProperty(discountRule2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDiscountIdDiscountRulesPageWithSortString()
		throws Exception {

		testGetDiscountIdDiscountRulesPageWithSort(
			EntityField.Type.STRING,
			(entityField, discountRule1, discountRule2) -> {
				Class<?> clazz = discountRule1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						discountRule1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						discountRule2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						discountRule1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						discountRule2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						discountRule1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						discountRule2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDiscountIdDiscountRulesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DiscountRule, DiscountRule, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountRulesPage_getId();

		DiscountRule discountRule1 = randomDiscountRule();
		DiscountRule discountRule2 = randomDiscountRule();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, discountRule1, discountRule2);
		}

		discountRule1 = testGetDiscountIdDiscountRulesPage_addDiscountRule(
			id, discountRule1);

		discountRule2 = testGetDiscountIdDiscountRulesPage_addDiscountRule(
			id, discountRule2);

		for (EntityField entityField : entityFields) {
			Page<DiscountRule> ascPage =
				discountRuleResource.getDiscountIdDiscountRulesPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discountRule1, discountRule2),
				(List<DiscountRule>)ascPage.getItems());

			Page<DiscountRule> descPage =
				discountRuleResource.getDiscountIdDiscountRulesPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discountRule2, discountRule1),
				(List<DiscountRule>)descPage.getItems());
		}
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

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected DiscountRule testGraphQLDiscountRule_addDiscountRule()
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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (discountRule.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (discountRule.getDiscountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (discountRule.getName() == null) {
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
		boolean valid = false;

		java.util.Collection<DiscountRule> discountRules = page.getItems();

		int size = discountRules.size();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.commerce.admin.pricing.dto.v2_0.
						DiscountRule.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
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
					ReflectionUtil.getDeclaredFields(clazz));

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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)discountRule1.getActions(),
						(Map)discountRule2.getActions())) {

					return false;
				}

				continue;
			}

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

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountRule1.getName(), discountRule2.getName())) {

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
		EntityField entityField, String operator, DiscountRule discountRule) {

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

		if (entityFieldName.equals("discountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(discountRule.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(discountRule.getType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("typeSettings")) {
			sb.append("'");
			sb.append(String.valueOf(discountRule.getTypeSettings()));
			sb.append("'");

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
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
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

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDiscountRuleResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.pricing.resource.v2_0.
		DiscountRuleResource _discountRuleResource;

}