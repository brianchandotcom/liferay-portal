/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

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
import com.liferay.search.experiences.rest.client.dto.v1_0.Field;
import com.liferay.search.experiences.rest.client.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.client.http.HttpInvoker;
import com.liferay.search.experiences.rest.client.pagination.Page;
import com.liferay.search.experiences.rest.client.pagination.Pagination;
import com.liferay.search.experiences.rest.client.resource.v1_0.SXPElementResource;
import com.liferay.search.experiences.rest.client.serdes.v1_0.SXPElementSerDes;

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
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public abstract class BaseSXPElementResourceTestCase {

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

		_sxpElementResource.setContextCompany(testCompany);

		SXPElementResource.Builder builder = SXPElementResource.builder();

		sxpElementResource = builder.authentication(
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

		SXPElement sxpElement1 = randomSXPElement();

		String json = objectMapper.writeValueAsString(sxpElement1);

		SXPElement sxpElement2 = SXPElementSerDes.toDTO(json);

		Assert.assertTrue(equals(sxpElement1, sxpElement2));
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

		SXPElement sxpElement = randomSXPElement();

		String json1 = objectMapper.writeValueAsString(sxpElement);
		String json2 = SXPElementSerDes.toJSON(sxpElement);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		SXPElement sxpElement = randomSXPElement();

		sxpElement.setDescription(regex);
		sxpElement.setExternalReferenceCode(regex);
		sxpElement.setSchemaVersion(regex);
		sxpElement.setTitle(regex);
		sxpElement.setUserName(regex);
		sxpElement.setVersion(regex);

		String json = SXPElementSerDes.toJSON(sxpElement);

		Assert.assertFalse(json.contains(regex));

		sxpElement = SXPElementSerDes.toDTO(json);

		Assert.assertEquals(regex, sxpElement.getDescription());
		Assert.assertEquals(regex, sxpElement.getExternalReferenceCode());
		Assert.assertEquals(regex, sxpElement.getSchemaVersion());
		Assert.assertEquals(regex, sxpElement.getTitle());
		Assert.assertEquals(regex, sxpElement.getUserName());
		Assert.assertEquals(regex, sxpElement.getVersion());
	}

	@Test
	public void testGetSXPElementsPage() throws Exception {
		Page<SXPElement> page = sxpElementResource.getSXPElementsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		SXPElement sxpElement1 = testGetSXPElementsPage_addSXPElement(
			randomSXPElement());

		SXPElement sxpElement2 = testGetSXPElementsPage_addSXPElement(
			randomSXPElement());

		page = sxpElementResource.getSXPElementsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(sxpElement1, (List<SXPElement>)page.getItems());
		assertContains(sxpElement2, (List<SXPElement>)page.getItems());
		assertValid(page, testGetSXPElementsPage_getExpectedActions());

		sxpElementResource.deleteSXPElement(sxpElement1.getId());

		sxpElementResource.deleteSXPElement(sxpElement2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetSXPElementsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetSXPElementsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		SXPElement sxpElement1 = randomSXPElement();

		sxpElement1 = testGetSXPElementsPage_addSXPElement(sxpElement1);

		for (EntityField entityField : entityFields) {
			Page<SXPElement> page = sxpElementResource.getSXPElementsPage(
				null, getFilterString(entityField, "between", sxpElement1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(sxpElement1),
				(List<SXPElement>)page.getItems());
		}
	}

	@Test
	public void testGetSXPElementsPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		SXPElement sxpElement1 = testGetSXPElementsPage_addSXPElement(
			randomSXPElement());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		SXPElement sxpElement2 = testGetSXPElementsPage_addSXPElement(
			randomSXPElement());

		for (EntityField entityField : entityFields) {
			Page<SXPElement> page = sxpElementResource.getSXPElementsPage(
				null, getFilterString(entityField, "eq", sxpElement1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(sxpElement1),
				(List<SXPElement>)page.getItems());
		}
	}

	@Test
	public void testGetSXPElementsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		SXPElement sxpElement1 = testGetSXPElementsPage_addSXPElement(
			randomSXPElement());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		SXPElement sxpElement2 = testGetSXPElementsPage_addSXPElement(
			randomSXPElement());

		for (EntityField entityField : entityFields) {
			Page<SXPElement> page = sxpElementResource.getSXPElementsPage(
				null, getFilterString(entityField, "eq", sxpElement1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(sxpElement1),
				(List<SXPElement>)page.getItems());
		}
	}

	@Test
	public void testGetSXPElementsPageWithPagination() throws Exception {
		Page<SXPElement> totalPage = sxpElementResource.getSXPElementsPage(
			null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		SXPElement sxpElement1 = testGetSXPElementsPage_addSXPElement(
			randomSXPElement());

		SXPElement sxpElement2 = testGetSXPElementsPage_addSXPElement(
			randomSXPElement());

		SXPElement sxpElement3 = testGetSXPElementsPage_addSXPElement(
			randomSXPElement());

		Page<SXPElement> page1 = sxpElementResource.getSXPElementsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<SXPElement> sxpElements1 = (List<SXPElement>)page1.getItems();

		Assert.assertEquals(
			sxpElements1.toString(), totalCount + 2, sxpElements1.size());

		Page<SXPElement> page2 = sxpElementResource.getSXPElementsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<SXPElement> sxpElements2 = (List<SXPElement>)page2.getItems();

		Assert.assertEquals(sxpElements2.toString(), 1, sxpElements2.size());

		Page<SXPElement> page3 = sxpElementResource.getSXPElementsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(sxpElement1, (List<SXPElement>)page3.getItems());
		assertContains(sxpElement2, (List<SXPElement>)page3.getItems());
		assertContains(sxpElement3, (List<SXPElement>)page3.getItems());
	}

	@Test
	public void testGetSXPElementsPageWithSortDateTime() throws Exception {
		testGetSXPElementsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, sxpElement1, sxpElement2) -> {
				BeanTestUtil.setProperty(
					sxpElement1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSXPElementsPageWithSortDouble() throws Exception {
		testGetSXPElementsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, sxpElement1, sxpElement2) -> {
				BeanTestUtil.setProperty(
					sxpElement1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					sxpElement2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetSXPElementsPageWithSortInteger() throws Exception {
		testGetSXPElementsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, sxpElement1, sxpElement2) -> {
				BeanTestUtil.setProperty(sxpElement1, entityField.getName(), 0);
				BeanTestUtil.setProperty(sxpElement2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSXPElementsPageWithSortString() throws Exception {
		testGetSXPElementsPageWithSort(
			EntityField.Type.STRING,
			(entityField, sxpElement1, sxpElement2) -> {
				Class<?> clazz = sxpElement1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						sxpElement1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						sxpElement2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						sxpElement1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						sxpElement2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						sxpElement1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						sxpElement2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSXPElementsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, SXPElement, SXPElement, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		SXPElement sxpElement1 = randomSXPElement();
		SXPElement sxpElement2 = randomSXPElement();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, sxpElement1, sxpElement2);
		}

		sxpElement1 = testGetSXPElementsPage_addSXPElement(sxpElement1);

		sxpElement2 = testGetSXPElementsPage_addSXPElement(sxpElement2);

		for (EntityField entityField : entityFields) {
			Page<SXPElement> ascPage = sxpElementResource.getSXPElementsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(sxpElement1, sxpElement2),
				(List<SXPElement>)ascPage.getItems());

			Page<SXPElement> descPage = sxpElementResource.getSXPElementsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(sxpElement2, sxpElement1),
				(List<SXPElement>)descPage.getItems());
		}
	}

	protected SXPElement testGetSXPElementsPage_addSXPElement(
			SXPElement sxpElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostSXPElement() throws Exception {
		SXPElement randomSXPElement = randomSXPElement();

		SXPElement postSXPElement = testPostSXPElement_addSXPElement(
			randomSXPElement);

		assertEquals(randomSXPElement, postSXPElement);
		assertValid(postSXPElement);
	}

	protected SXPElement testPostSXPElement_addSXPElement(SXPElement sxpElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostSXPElementPreview() throws Exception {
		SXPElement randomSXPElement = randomSXPElement();

		SXPElement postSXPElement = testPostSXPElementPreview_addSXPElement(
			randomSXPElement);

		assertEquals(randomSXPElement, postSXPElement);
		assertValid(postSXPElement);
	}

	protected SXPElement testPostSXPElementPreview_addSXPElement(
			SXPElement sxpElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostSXPElementValidate() throws Exception {
		SXPElement randomSXPElement = randomSXPElement();

		SXPElement postSXPElement = testPostSXPElementValidate_addSXPElement(
			randomSXPElement);

		assertEquals(randomSXPElement, postSXPElement);
		assertValid(postSXPElement);
	}

	protected SXPElement testPostSXPElementValidate_addSXPElement(
			SXPElement sxpElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteSXPElement() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		SXPElement sxpElement = testDeleteSXPElement_addSXPElement();

		assertHttpResponseStatusCode(
			204,
			sxpElementResource.deleteSXPElementHttpResponse(
				sxpElement.getId()));

		assertHttpResponseStatusCode(
			404,
			sxpElementResource.getSXPElementHttpResponse(sxpElement.getId()));

		assertHttpResponseStatusCode(
			404, sxpElementResource.getSXPElementHttpResponse(0L));
	}

	protected SXPElement testDeleteSXPElement_addSXPElement() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteSXPElement() throws Exception {
		SXPElement sxpElement = testGraphQLDeleteSXPElement_addSXPElement();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteSXPElement",
						new HashMap<String, Object>() {
							{
								put("sxpElementId", sxpElement.getId());
							}
						})),
				"JSONObject/data", "Object/deleteSXPElement"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"sXPElement",
					new HashMap<String, Object>() {
						{
							put("sxpElementId", sxpElement.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected SXPElement testGraphQLDeleteSXPElement_addSXPElement()
		throws Exception {

		return testGraphQLSXPElement_addSXPElement();
	}

	@Test
	public void testGetSXPElement() throws Exception {
		SXPElement postSXPElement = testGetSXPElement_addSXPElement();

		SXPElement getSXPElement = sxpElementResource.getSXPElement(
			postSXPElement.getId());

		assertEquals(postSXPElement, getSXPElement);
		assertValid(getSXPElement);
	}

	protected SXPElement testGetSXPElement_addSXPElement() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSXPElement() throws Exception {
		SXPElement sxpElement = testGraphQLGetSXPElement_addSXPElement();

		Assert.assertTrue(
			equals(
				sxpElement,
				SXPElementSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"sXPElement",
								new HashMap<String, Object>() {
									{
										put("sxpElementId", sxpElement.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/sXPElement"))));
	}

	@Test
	public void testGraphQLGetSXPElementNotFound() throws Exception {
		Long irrelevantSxpElementId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"sXPElement",
						new HashMap<String, Object>() {
							{
								put("sxpElementId", irrelevantSxpElementId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected SXPElement testGraphQLGetSXPElement_addSXPElement()
		throws Exception {

		return testGraphQLSXPElement_addSXPElement();
	}

	@Test
	public void testPatchSXPElement() throws Exception {
		SXPElement postSXPElement = testPatchSXPElement_addSXPElement();

		SXPElement randomPatchSXPElement = randomPatchSXPElement();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		SXPElement patchSXPElement = sxpElementResource.patchSXPElement(
			postSXPElement.getId(), randomPatchSXPElement);

		SXPElement expectedPatchSXPElement = postSXPElement.clone();

		BeanTestUtil.copyProperties(
			randomPatchSXPElement, expectedPatchSXPElement);

		SXPElement getSXPElement = sxpElementResource.getSXPElement(
			patchSXPElement.getId());

		assertEquals(expectedPatchSXPElement, getSXPElement);
		assertValid(getSXPElement);
	}

	protected SXPElement testPatchSXPElement_addSXPElement() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostSXPElementCopy() throws Exception {
		SXPElement randomSXPElement = randomSXPElement();

		SXPElement postSXPElement = testPostSXPElementCopy_addSXPElement(
			randomSXPElement);

		assertEquals(randomSXPElement, postSXPElement);
		assertValid(postSXPElement);
	}

	protected SXPElement testPostSXPElementCopy_addSXPElement(
			SXPElement sxpElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSXPElementExport() throws Exception {
		Assert.assertTrue(false);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected SXPElement testGraphQLSXPElement_addSXPElement()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		SXPElement sxpElement, List<SXPElement> sxpElements) {

		boolean contains = false;

		for (SXPElement item : sxpElements) {
			if (equals(sxpElement, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			sxpElements + " does not contain " + sxpElement, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		SXPElement sxpElement1, SXPElement sxpElement2) {

		Assert.assertTrue(
			sxpElement1 + " does not equal " + sxpElement2,
			equals(sxpElement1, sxpElement2));
	}

	protected void assertEquals(
		List<SXPElement> sxpElements1, List<SXPElement> sxpElements2) {

		Assert.assertEquals(sxpElements1.size(), sxpElements2.size());

		for (int i = 0; i < sxpElements1.size(); i++) {
			SXPElement sxpElement1 = sxpElements1.get(i);
			SXPElement sxpElement2 = sxpElements2.get(i);

			assertEquals(sxpElement1, sxpElement2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<SXPElement> sxpElements1, List<SXPElement> sxpElements2) {

		Assert.assertEquals(sxpElements1.size(), sxpElements2.size());

		for (SXPElement sxpElement1 : sxpElements1) {
			boolean contains = false;

			for (SXPElement sxpElement2 : sxpElements2) {
				if (equals(sxpElement1, sxpElement2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				sxpElements2 + " does not contain " + sxpElement1, contains);
		}
	}

	protected void assertValid(SXPElement sxpElement) throws Exception {
		boolean valid = true;

		if (sxpElement.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (sxpElement.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (sxpElement.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (sxpElement.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (sxpElement.getDescription_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"elementDefinition", additionalAssertFieldName)) {

				if (sxpElement.getElementDefinition() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (sxpElement.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("hidden", additionalAssertFieldName)) {
				if (sxpElement.getHidden() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (sxpElement.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("readOnly", additionalAssertFieldName)) {
				if (sxpElement.getReadOnly() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("schemaVersion", additionalAssertFieldName)) {
				if (sxpElement.getSchemaVersion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (sxpElement.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (sxpElement.getTitle_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (sxpElement.getType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (sxpElement.getUserName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("version", additionalAssertFieldName)) {
				if (sxpElement.getVersion() == null) {
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

	protected void assertValid(Page<SXPElement> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<SXPElement> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<SXPElement> sxpElements = page.getItems();

		int size = sxpElements.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		Map<String, Map<String, String>> actions = page.getActions();

		for (String key : expectedActions.keySet()) {
			Map action = actions.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map expectedAction = expectedActions.get(key);

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
					com.liferay.search.experiences.rest.dto.v1_0.SXPElement.
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

	protected boolean equals(SXPElement sxpElement1, SXPElement sxpElement2) {
		if (sxpElement1 == sxpElement2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)sxpElement1.getActions(),
						(Map)sxpElement2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getCreateDate(),
						sxpElement2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getDescription(),
						sxpElement2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)sxpElement1.getDescription_i18n(),
						(Map)sxpElement2.getDescription_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"elementDefinition", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						sxpElement1.getElementDefinition(),
						sxpElement2.getElementDefinition())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						sxpElement1.getExternalReferenceCode(),
						sxpElement2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("hidden", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getHidden(), sxpElement2.getHidden())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getId(), sxpElement2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getModifiedDate(),
						sxpElement2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("readOnly", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getReadOnly(), sxpElement2.getReadOnly())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("schemaVersion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getSchemaVersion(),
						sxpElement2.getSchemaVersion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getTitle(), sxpElement2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)sxpElement1.getTitle_i18n(),
						(Map)sxpElement2.getTitle_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getType(), sxpElement2.getType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getUserName(), sxpElement2.getUserName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("version", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpElement1.getVersion(), sxpElement2.getVersion())) {

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

		if (!(_sxpElementResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_sxpElementResource;

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
		EntityField entityField, String operator, SXPElement sxpElement) {

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

		if (entityFieldName.equals("createDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(sxpElement.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(sxpElement.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(sxpElement.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(sxpElement.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("elementDefinition")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(sxpElement.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("hidden")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("modifiedDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							sxpElement.getModifiedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(sxpElement.getModifiedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(sxpElement.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("readOnly")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("schemaVersion")) {
			sb.append("'");
			sb.append(String.valueOf(sxpElement.getSchemaVersion()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(sxpElement.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append(String.valueOf(sxpElement.getType()));

			return sb.toString();
		}

		if (entityFieldName.equals("userName")) {
			sb.append("'");
			sb.append(String.valueOf(sxpElement.getUserName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("version")) {
			sb.append("'");
			sb.append(String.valueOf(sxpElement.getVersion()));
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

	protected SXPElement randomSXPElement() throws Exception {
		return new SXPElement() {
			{
				createDate = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				hidden = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				readOnly = RandomTestUtil.randomBoolean();
				schemaVersion = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				type = RandomTestUtil.randomInt();
				userName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				version = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected SXPElement randomIrrelevantSXPElement() throws Exception {
		SXPElement randomIrrelevantSXPElement = randomSXPElement();

		return randomIrrelevantSXPElement;
	}

	protected SXPElement randomPatchSXPElement() throws Exception {
		return randomSXPElement();
	}

	protected SXPElementResource sxpElementResource;
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
		LogFactoryUtil.getLog(BaseSXPElementResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.search.experiences.rest.resource.v1_0.SXPElementResource
		_sxpElementResource;

}