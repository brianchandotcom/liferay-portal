/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectValidationRule;
import com.liferay.object.admin.rest.client.http.HttpInvoker;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectValidationRuleResource;
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectValidationRuleSerDes;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseObjectValidationRuleResourceTestCase {

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

		_objectValidationRuleResource.setContextCompany(testCompany);

		ObjectValidationRuleResource.Builder builder =
			ObjectValidationRuleResource.builder();

		objectValidationRuleResource = builder.authentication(
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

		ObjectValidationRule objectValidationRule1 =
			randomObjectValidationRule();

		String json = objectMapper.writeValueAsString(objectValidationRule1);

		ObjectValidationRule objectValidationRule2 =
			ObjectValidationRuleSerDes.toDTO(json);

		Assert.assertTrue(equals(objectValidationRule1, objectValidationRule2));
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

		ObjectValidationRule objectValidationRule =
			randomObjectValidationRule();

		String json1 = objectMapper.writeValueAsString(objectValidationRule);
		String json2 = ObjectValidationRuleSerDes.toJSON(objectValidationRule);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ObjectValidationRule objectValidationRule =
			randomObjectValidationRule();

		objectValidationRule.setEngine(regex);
		objectValidationRule.setEngineLabel(regex);
		objectValidationRule.setObjectDefinitionExternalReferenceCode(regex);
		objectValidationRule.setScript(regex);

		String json = ObjectValidationRuleSerDes.toJSON(objectValidationRule);

		Assert.assertFalse(json.contains(regex));

		objectValidationRule = ObjectValidationRuleSerDes.toDTO(json);

		Assert.assertEquals(regex, objectValidationRule.getEngine());
		Assert.assertEquals(regex, objectValidationRule.getEngineLabel());
		Assert.assertEquals(
			regex,
			objectValidationRule.getObjectDefinitionExternalReferenceCode());
		Assert.assertEquals(regex, objectValidationRule.getScript());
	}

	@Test
	public void testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage()
		throws Exception {

		String externalReferenceCode =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_getIrrelevantExternalReferenceCode();

		Page<ObjectValidationRule> page =
			objectValidationRuleResource.
				getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
					externalReferenceCode, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			ObjectValidationRule irrelevantObjectValidationRule =
				testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_addObjectValidationRule(
					irrelevantExternalReferenceCode,
					randomIrrelevantObjectValidationRule());

			page =
				objectValidationRuleResource.
					getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
						irrelevantExternalReferenceCode, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantObjectValidationRule),
				(List<ObjectValidationRule>)page.getItems());
			assertValid(
				page,
				testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_getExpectedActions(
					irrelevantExternalReferenceCode));
		}

		ObjectValidationRule objectValidationRule1 =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_addObjectValidationRule(
				externalReferenceCode, randomObjectValidationRule());

		ObjectValidationRule objectValidationRule2 =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_addObjectValidationRule(
				externalReferenceCode, randomObjectValidationRule());

		page =
			objectValidationRuleResource.
				getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
					externalReferenceCode, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(objectValidationRule1, objectValidationRule2),
			(List<ObjectValidationRule>)page.getItems());
		assertValid(
			page,
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_getExpectedActions(
				externalReferenceCode));

		objectValidationRuleResource.deleteObjectValidationRule(
			objectValidationRule1.getId());

		objectValidationRuleResource.deleteObjectValidationRule(
			objectValidationRule2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_getExpectedActions(
				String externalReferenceCode)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_getExternalReferenceCode();

		ObjectValidationRule objectValidationRule1 =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_addObjectValidationRule(
				externalReferenceCode, randomObjectValidationRule());

		ObjectValidationRule objectValidationRule2 =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_addObjectValidationRule(
				externalReferenceCode, randomObjectValidationRule());

		ObjectValidationRule objectValidationRule3 =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_addObjectValidationRule(
				externalReferenceCode, randomObjectValidationRule());

		Page<ObjectValidationRule> page1 =
			objectValidationRuleResource.
				getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
					externalReferenceCode, null, Pagination.of(1, 2), null);

		List<ObjectValidationRule> objectValidationRules1 =
			(List<ObjectValidationRule>)page1.getItems();

		Assert.assertEquals(
			objectValidationRules1.toString(), 2,
			objectValidationRules1.size());

		Page<ObjectValidationRule> page2 =
			objectValidationRuleResource.
				getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
					externalReferenceCode, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<ObjectValidationRule> objectValidationRules2 =
			(List<ObjectValidationRule>)page2.getItems();

		Assert.assertEquals(
			objectValidationRules2.toString(), 1,
			objectValidationRules2.size());

		Page<ObjectValidationRule> page3 =
			objectValidationRuleResource.
				getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
					externalReferenceCode, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				objectValidationRule1, objectValidationRule2,
				objectValidationRule3),
			(List<ObjectValidationRule>)page3.getItems());
	}

	@Test
	public void testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithSortDateTime()
		throws Exception {

		testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, objectValidationRule1, objectValidationRule2) -> {
				BeanTestUtil.setProperty(
					objectValidationRule1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithSortDouble()
		throws Exception {

		testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, objectValidationRule1, objectValidationRule2) -> {
				BeanTestUtil.setProperty(
					objectValidationRule1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					objectValidationRule2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithSortInteger()
		throws Exception {

		testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, objectValidationRule1, objectValidationRule2) -> {
				BeanTestUtil.setProperty(
					objectValidationRule1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					objectValidationRule2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithSortString()
		throws Exception {

		testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithSort(
			EntityField.Type.STRING,
			(entityField, objectValidationRule1, objectValidationRule2) -> {
				Class<?> clazz = objectValidationRule1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						objectValidationRule1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						objectValidationRule2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						objectValidationRule1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						objectValidationRule2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						objectValidationRule1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						objectValidationRule2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, ObjectValidationRule, ObjectValidationRule,
					 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_getExternalReferenceCode();

		ObjectValidationRule objectValidationRule1 =
			randomObjectValidationRule();
		ObjectValidationRule objectValidationRule2 =
			randomObjectValidationRule();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, objectValidationRule1, objectValidationRule2);
		}

		objectValidationRule1 =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_addObjectValidationRule(
				externalReferenceCode, objectValidationRule1);

		objectValidationRule2 =
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_addObjectValidationRule(
				externalReferenceCode, objectValidationRule2);

		for (EntityField entityField : entityFields) {
			Page<ObjectValidationRule> ascPage =
				objectValidationRuleResource.
					getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
						externalReferenceCode, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(objectValidationRule1, objectValidationRule2),
				(List<ObjectValidationRule>)ascPage.getItems());

			Page<ObjectValidationRule> descPage =
				objectValidationRuleResource.
					getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
						externalReferenceCode, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(objectValidationRule2, objectValidationRule1),
				(List<ObjectValidationRule>)descPage.getItems());
		}
	}

	protected ObjectValidationRule
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_addObjectValidationRule(
				String externalReferenceCode,
				ObjectValidationRule objectValidationRule)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostObjectDefinitionByExternalReferenceCodeObjectValidationRule()
		throws Exception {

		ObjectValidationRule randomObjectValidationRule =
			randomObjectValidationRule();

		ObjectValidationRule postObjectValidationRule =
			testPostObjectDefinitionByExternalReferenceCodeObjectValidationRule_addObjectValidationRule(
				randomObjectValidationRule);

		assertEquals(randomObjectValidationRule, postObjectValidationRule);
		assertValid(postObjectValidationRule);
	}

	protected ObjectValidationRule
			testPostObjectDefinitionByExternalReferenceCodeObjectValidationRule_addObjectValidationRule(
				ObjectValidationRule objectValidationRule)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetObjectDefinitionObjectValidationRulesPage()
		throws Exception {

		Long objectDefinitionId =
			testGetObjectDefinitionObjectValidationRulesPage_getObjectDefinitionId();
		Long irrelevantObjectDefinitionId =
			testGetObjectDefinitionObjectValidationRulesPage_getIrrelevantObjectDefinitionId();

		Page<ObjectValidationRule> page =
			objectValidationRuleResource.
				getObjectDefinitionObjectValidationRulesPage(
					objectDefinitionId, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantObjectDefinitionId != null) {
			ObjectValidationRule irrelevantObjectValidationRule =
				testGetObjectDefinitionObjectValidationRulesPage_addObjectValidationRule(
					irrelevantObjectDefinitionId,
					randomIrrelevantObjectValidationRule());

			page =
				objectValidationRuleResource.
					getObjectDefinitionObjectValidationRulesPage(
						irrelevantObjectDefinitionId, null, Pagination.of(1, 2),
						null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantObjectValidationRule),
				(List<ObjectValidationRule>)page.getItems());
			assertValid(
				page,
				testGetObjectDefinitionObjectValidationRulesPage_getExpectedActions(
					irrelevantObjectDefinitionId));
		}

		ObjectValidationRule objectValidationRule1 =
			testGetObjectDefinitionObjectValidationRulesPage_addObjectValidationRule(
				objectDefinitionId, randomObjectValidationRule());

		ObjectValidationRule objectValidationRule2 =
			testGetObjectDefinitionObjectValidationRulesPage_addObjectValidationRule(
				objectDefinitionId, randomObjectValidationRule());

		page =
			objectValidationRuleResource.
				getObjectDefinitionObjectValidationRulesPage(
					objectDefinitionId, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(objectValidationRule1, objectValidationRule2),
			(List<ObjectValidationRule>)page.getItems());
		assertValid(
			page,
			testGetObjectDefinitionObjectValidationRulesPage_getExpectedActions(
				objectDefinitionId));

		objectValidationRuleResource.deleteObjectValidationRule(
			objectValidationRule1.getId());

		objectValidationRuleResource.deleteObjectValidationRule(
			objectValidationRule2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetObjectDefinitionObjectValidationRulesPage_getExpectedActions(
				Long objectDefinitionId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-validation-rules/batch".
				replace(
					"{objectDefinitionId}",
					String.valueOf(objectDefinitionId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
	}

	@Test
	public void testGetObjectDefinitionObjectValidationRulesPageWithPagination()
		throws Exception {

		Long objectDefinitionId =
			testGetObjectDefinitionObjectValidationRulesPage_getObjectDefinitionId();

		ObjectValidationRule objectValidationRule1 =
			testGetObjectDefinitionObjectValidationRulesPage_addObjectValidationRule(
				objectDefinitionId, randomObjectValidationRule());

		ObjectValidationRule objectValidationRule2 =
			testGetObjectDefinitionObjectValidationRulesPage_addObjectValidationRule(
				objectDefinitionId, randomObjectValidationRule());

		ObjectValidationRule objectValidationRule3 =
			testGetObjectDefinitionObjectValidationRulesPage_addObjectValidationRule(
				objectDefinitionId, randomObjectValidationRule());

		Page<ObjectValidationRule> page1 =
			objectValidationRuleResource.
				getObjectDefinitionObjectValidationRulesPage(
					objectDefinitionId, null, Pagination.of(1, 2), null);

		List<ObjectValidationRule> objectValidationRules1 =
			(List<ObjectValidationRule>)page1.getItems();

		Assert.assertEquals(
			objectValidationRules1.toString(), 2,
			objectValidationRules1.size());

		Page<ObjectValidationRule> page2 =
			objectValidationRuleResource.
				getObjectDefinitionObjectValidationRulesPage(
					objectDefinitionId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<ObjectValidationRule> objectValidationRules2 =
			(List<ObjectValidationRule>)page2.getItems();

		Assert.assertEquals(
			objectValidationRules2.toString(), 1,
			objectValidationRules2.size());

		Page<ObjectValidationRule> page3 =
			objectValidationRuleResource.
				getObjectDefinitionObjectValidationRulesPage(
					objectDefinitionId, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				objectValidationRule1, objectValidationRule2,
				objectValidationRule3),
			(List<ObjectValidationRule>)page3.getItems());
	}

	@Test
	public void testGetObjectDefinitionObjectValidationRulesPageWithSortDateTime()
		throws Exception {

		testGetObjectDefinitionObjectValidationRulesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, objectValidationRule1, objectValidationRule2) -> {
				BeanTestUtil.setProperty(
					objectValidationRule1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetObjectDefinitionObjectValidationRulesPageWithSortDouble()
		throws Exception {

		testGetObjectDefinitionObjectValidationRulesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, objectValidationRule1, objectValidationRule2) -> {
				BeanTestUtil.setProperty(
					objectValidationRule1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					objectValidationRule2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetObjectDefinitionObjectValidationRulesPageWithSortInteger()
		throws Exception {

		testGetObjectDefinitionObjectValidationRulesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, objectValidationRule1, objectValidationRule2) -> {
				BeanTestUtil.setProperty(
					objectValidationRule1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					objectValidationRule2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetObjectDefinitionObjectValidationRulesPageWithSortString()
		throws Exception {

		testGetObjectDefinitionObjectValidationRulesPageWithSort(
			EntityField.Type.STRING,
			(entityField, objectValidationRule1, objectValidationRule2) -> {
				Class<?> clazz = objectValidationRule1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						objectValidationRule1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						objectValidationRule2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						objectValidationRule1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						objectValidationRule2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						objectValidationRule1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						objectValidationRule2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetObjectDefinitionObjectValidationRulesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, ObjectValidationRule, ObjectValidationRule,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long objectDefinitionId =
			testGetObjectDefinitionObjectValidationRulesPage_getObjectDefinitionId();

		ObjectValidationRule objectValidationRule1 =
			randomObjectValidationRule();
		ObjectValidationRule objectValidationRule2 =
			randomObjectValidationRule();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, objectValidationRule1, objectValidationRule2);
		}

		objectValidationRule1 =
			testGetObjectDefinitionObjectValidationRulesPage_addObjectValidationRule(
				objectDefinitionId, objectValidationRule1);

		objectValidationRule2 =
			testGetObjectDefinitionObjectValidationRulesPage_addObjectValidationRule(
				objectDefinitionId, objectValidationRule2);

		for (EntityField entityField : entityFields) {
			Page<ObjectValidationRule> ascPage =
				objectValidationRuleResource.
					getObjectDefinitionObjectValidationRulesPage(
						objectDefinitionId, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(objectValidationRule1, objectValidationRule2),
				(List<ObjectValidationRule>)ascPage.getItems());

			Page<ObjectValidationRule> descPage =
				objectValidationRuleResource.
					getObjectDefinitionObjectValidationRulesPage(
						objectDefinitionId, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(objectValidationRule2, objectValidationRule1),
				(List<ObjectValidationRule>)descPage.getItems());
		}
	}

	protected ObjectValidationRule
			testGetObjectDefinitionObjectValidationRulesPage_addObjectValidationRule(
				Long objectDefinitionId,
				ObjectValidationRule objectValidationRule)
		throws Exception {

		return objectValidationRuleResource.
			postObjectDefinitionObjectValidationRule(
				objectDefinitionId, objectValidationRule);
	}

	protected Long
			testGetObjectDefinitionObjectValidationRulesPage_getObjectDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetObjectDefinitionObjectValidationRulesPage_getIrrelevantObjectDefinitionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostObjectDefinitionObjectValidationRule()
		throws Exception {

		ObjectValidationRule randomObjectValidationRule =
			randomObjectValidationRule();

		ObjectValidationRule postObjectValidationRule =
			testPostObjectDefinitionObjectValidationRule_addObjectValidationRule(
				randomObjectValidationRule);

		assertEquals(randomObjectValidationRule, postObjectValidationRule);
		assertValid(postObjectValidationRule);
	}

	protected ObjectValidationRule
			testPostObjectDefinitionObjectValidationRule_addObjectValidationRule(
				ObjectValidationRule objectValidationRule)
		throws Exception {

		return objectValidationRuleResource.
			postObjectDefinitionObjectValidationRule(
				testGetObjectDefinitionObjectValidationRulesPage_getObjectDefinitionId(),
				objectValidationRule);
	}

	@Test
	public void testDeleteObjectValidationRule() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ObjectValidationRule objectValidationRule =
			testDeleteObjectValidationRule_addObjectValidationRule();

		assertHttpResponseStatusCode(
			204,
			objectValidationRuleResource.deleteObjectValidationRuleHttpResponse(
				objectValidationRule.getId()));

		assertHttpResponseStatusCode(
			404,
			objectValidationRuleResource.getObjectValidationRuleHttpResponse(
				objectValidationRule.getId()));

		assertHttpResponseStatusCode(
			404,
			objectValidationRuleResource.getObjectValidationRuleHttpResponse(
				0L));
	}

	protected ObjectValidationRule
			testDeleteObjectValidationRule_addObjectValidationRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteObjectValidationRule() throws Exception {
		ObjectValidationRule objectValidationRule =
			testGraphQLDeleteObjectValidationRule_addObjectValidationRule();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteObjectValidationRule",
						new HashMap<String, Object>() {
							{
								put(
									"objectValidationRuleId",
									objectValidationRule.getId());
							}
						})),
				"JSONObject/data", "Object/deleteObjectValidationRule"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"objectValidationRule",
					new HashMap<String, Object>() {
						{
							put(
								"objectValidationRuleId",
								objectValidationRule.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected ObjectValidationRule
			testGraphQLDeleteObjectValidationRule_addObjectValidationRule()
		throws Exception {

		return testGraphQLObjectValidationRule_addObjectValidationRule();
	}

	@Test
	public void testGetObjectValidationRule() throws Exception {
		ObjectValidationRule postObjectValidationRule =
			testGetObjectValidationRule_addObjectValidationRule();

		ObjectValidationRule getObjectValidationRule =
			objectValidationRuleResource.getObjectValidationRule(
				postObjectValidationRule.getId());

		assertEquals(postObjectValidationRule, getObjectValidationRule);
		assertValid(getObjectValidationRule);
	}

	protected ObjectValidationRule
			testGetObjectValidationRule_addObjectValidationRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetObjectValidationRule() throws Exception {
		ObjectValidationRule objectValidationRule =
			testGraphQLGetObjectValidationRule_addObjectValidationRule();

		Assert.assertTrue(
			equals(
				objectValidationRule,
				ObjectValidationRuleSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"objectValidationRule",
								new HashMap<String, Object>() {
									{
										put(
											"objectValidationRuleId",
											objectValidationRule.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/objectValidationRule"))));
	}

	@Test
	public void testGraphQLGetObjectValidationRuleNotFound() throws Exception {
		Long irrelevantObjectValidationRuleId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"objectValidationRule",
						new HashMap<String, Object>() {
							{
								put(
									"objectValidationRuleId",
									irrelevantObjectValidationRuleId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected ObjectValidationRule
			testGraphQLGetObjectValidationRule_addObjectValidationRule()
		throws Exception {

		return testGraphQLObjectValidationRule_addObjectValidationRule();
	}

	@Test
	public void testPatchObjectValidationRule() throws Exception {
		ObjectValidationRule postObjectValidationRule =
			testPatchObjectValidationRule_addObjectValidationRule();

		ObjectValidationRule randomPatchObjectValidationRule =
			randomPatchObjectValidationRule();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ObjectValidationRule patchObjectValidationRule =
			objectValidationRuleResource.patchObjectValidationRule(
				postObjectValidationRule.getId(),
				randomPatchObjectValidationRule);

		ObjectValidationRule expectedPatchObjectValidationRule =
			postObjectValidationRule.clone();

		BeanTestUtil.copyProperties(
			randomPatchObjectValidationRule, expectedPatchObjectValidationRule);

		ObjectValidationRule getObjectValidationRule =
			objectValidationRuleResource.getObjectValidationRule(
				patchObjectValidationRule.getId());

		assertEquals(
			expectedPatchObjectValidationRule, getObjectValidationRule);
		assertValid(getObjectValidationRule);
	}

	protected ObjectValidationRule
			testPatchObjectValidationRule_addObjectValidationRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutObjectValidationRule() throws Exception {
		ObjectValidationRule postObjectValidationRule =
			testPutObjectValidationRule_addObjectValidationRule();

		ObjectValidationRule randomObjectValidationRule =
			randomObjectValidationRule();

		ObjectValidationRule putObjectValidationRule =
			objectValidationRuleResource.putObjectValidationRule(
				postObjectValidationRule.getId(), randomObjectValidationRule);

		assertEquals(randomObjectValidationRule, putObjectValidationRule);
		assertValid(putObjectValidationRule);

		ObjectValidationRule getObjectValidationRule =
			objectValidationRuleResource.getObjectValidationRule(
				putObjectValidationRule.getId());

		assertEquals(randomObjectValidationRule, getObjectValidationRule);
		assertValid(getObjectValidationRule);
	}

	protected ObjectValidationRule
			testPutObjectValidationRule_addObjectValidationRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ObjectValidationRule
			testGraphQLObjectValidationRule_addObjectValidationRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		ObjectValidationRule objectValidationRule,
		List<ObjectValidationRule> objectValidationRules) {

		boolean contains = false;

		for (ObjectValidationRule item : objectValidationRules) {
			if (equals(objectValidationRule, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			objectValidationRules + " does not contain " + objectValidationRule,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ObjectValidationRule objectValidationRule1,
		ObjectValidationRule objectValidationRule2) {

		Assert.assertTrue(
			objectValidationRule1 + " does not equal " + objectValidationRule2,
			equals(objectValidationRule1, objectValidationRule2));
	}

	protected void assertEquals(
		List<ObjectValidationRule> objectValidationRules1,
		List<ObjectValidationRule> objectValidationRules2) {

		Assert.assertEquals(
			objectValidationRules1.size(), objectValidationRules2.size());

		for (int i = 0; i < objectValidationRules1.size(); i++) {
			ObjectValidationRule objectValidationRule1 =
				objectValidationRules1.get(i);
			ObjectValidationRule objectValidationRule2 =
				objectValidationRules2.get(i);

			assertEquals(objectValidationRule1, objectValidationRule2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ObjectValidationRule> objectValidationRules1,
		List<ObjectValidationRule> objectValidationRules2) {

		Assert.assertEquals(
			objectValidationRules1.size(), objectValidationRules2.size());

		for (ObjectValidationRule objectValidationRule1 :
				objectValidationRules1) {

			boolean contains = false;

			for (ObjectValidationRule objectValidationRule2 :
					objectValidationRules2) {

				if (equals(objectValidationRule1, objectValidationRule2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				objectValidationRules2 + " does not contain " +
					objectValidationRule1,
				contains);
		}
	}

	protected void assertValid(ObjectValidationRule objectValidationRule)
		throws Exception {

		boolean valid = true;

		if (objectValidationRule.getDateCreated() == null) {
			valid = false;
		}

		if (objectValidationRule.getDateModified() == null) {
			valid = false;
		}

		if (objectValidationRule.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (objectValidationRule.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (objectValidationRule.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("engine", additionalAssertFieldName)) {
				if (objectValidationRule.getEngine() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("engineLabel", additionalAssertFieldName)) {
				if (objectValidationRule.getEngineLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("errorLabel", additionalAssertFieldName)) {
				if (objectValidationRule.getErrorLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (objectValidationRule.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"objectDefinitionExternalReferenceCode",
					additionalAssertFieldName)) {

				if (objectValidationRule.
						getObjectDefinitionExternalReferenceCode() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"objectDefinitionId", additionalAssertFieldName)) {

				if (objectValidationRule.getObjectDefinitionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"objectValidationRuleSettings",
					additionalAssertFieldName)) {

				if (objectValidationRule.getObjectValidationRuleSettings() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("outputType", additionalAssertFieldName)) {
				if (objectValidationRule.getOutputType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("script", additionalAssertFieldName)) {
				if (objectValidationRule.getScript() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("system", additionalAssertFieldName)) {
				if (objectValidationRule.getSystem() == null) {
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

	protected void assertValid(Page<ObjectValidationRule> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<ObjectValidationRule> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<ObjectValidationRule> objectValidationRules =
			page.getItems();

		int size = objectValidationRules.size();

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
					com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRule.
						class)) {

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
		ObjectValidationRule objectValidationRule1,
		ObjectValidationRule objectValidationRule2) {

		if (objectValidationRule1 == objectValidationRule2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)objectValidationRule1.getActions(),
						(Map)objectValidationRule2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectValidationRule1.getActive(),
						objectValidationRule2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectValidationRule1.getDateCreated(),
						objectValidationRule2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectValidationRule1.getDateModified(),
						objectValidationRule2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("engine", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectValidationRule1.getEngine(),
						objectValidationRule2.getEngine())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("engineLabel", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectValidationRule1.getEngineLabel(),
						objectValidationRule2.getEngineLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("errorLabel", additionalAssertFieldName)) {
				if (!equals(
						(Map)objectValidationRule1.getErrorLabel(),
						(Map)objectValidationRule2.getErrorLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectValidationRule1.getId(),
						objectValidationRule2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)objectValidationRule1.getName(),
						(Map)objectValidationRule2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"objectDefinitionExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						objectValidationRule1.
							getObjectDefinitionExternalReferenceCode(),
						objectValidationRule2.
							getObjectDefinitionExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"objectDefinitionId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						objectValidationRule1.getObjectDefinitionId(),
						objectValidationRule2.getObjectDefinitionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"objectValidationRuleSettings",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						objectValidationRule1.getObjectValidationRuleSettings(),
						objectValidationRule2.
							getObjectValidationRuleSettings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("outputType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectValidationRule1.getOutputType(),
						objectValidationRule2.getOutputType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("script", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectValidationRule1.getScript(),
						objectValidationRule2.getScript())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("system", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectValidationRule1.getSystem(),
						objectValidationRule2.getSystem())) {

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

		if (!(_objectValidationRuleResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_objectValidationRuleResource;

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
		ObjectValidationRule objectValidationRule) {

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

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							objectValidationRule.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							objectValidationRule.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(objectValidationRule.getDateCreated()));
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
							objectValidationRule.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							objectValidationRule.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(objectValidationRule.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("engine")) {
			Object object = objectValidationRule.getEngine();

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

		if (entityFieldName.equals("engineLabel")) {
			Object object = objectValidationRule.getEngineLabel();

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

		if (entityFieldName.equals("errorLabel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("objectDefinitionExternalReferenceCode")) {
			Object object =
				objectValidationRule.getObjectDefinitionExternalReferenceCode();

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

		if (entityFieldName.equals("objectDefinitionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("objectValidationRuleSettings")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("outputType")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("script")) {
			Object object = objectValidationRule.getScript();

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

	protected ObjectValidationRule randomObjectValidationRule()
		throws Exception {

		return new ObjectValidationRule() {
			{
				active = RandomTestUtil.randomBoolean();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				engine = StringUtil.toLowerCase(RandomTestUtil.randomString());
				engineLabel = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				objectDefinitionExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				objectDefinitionId = RandomTestUtil.randomLong();
				script = StringUtil.toLowerCase(RandomTestUtil.randomString());
				system = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected ObjectValidationRule randomIrrelevantObjectValidationRule()
		throws Exception {

		ObjectValidationRule randomIrrelevantObjectValidationRule =
			randomObjectValidationRule();

		return randomIrrelevantObjectValidationRule;
	}

	protected ObjectValidationRule randomPatchObjectValidationRule()
		throws Exception {

		return randomObjectValidationRule();
	}

	protected ObjectValidationRuleResource objectValidationRuleResource;
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
		LogFactoryUtil.getLog(BaseObjectValidationRuleResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.object.admin.rest.resource.v1_0.ObjectValidationRuleResource
			_objectValidationRuleResource;

}