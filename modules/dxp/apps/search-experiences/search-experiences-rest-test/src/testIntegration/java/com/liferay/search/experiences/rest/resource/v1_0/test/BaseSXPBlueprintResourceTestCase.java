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
import com.liferay.search.experiences.rest.client.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.client.http.HttpInvoker;
import com.liferay.search.experiences.rest.client.pagination.Page;
import com.liferay.search.experiences.rest.client.pagination.Pagination;
import com.liferay.search.experiences.rest.client.resource.v1_0.SXPBlueprintResource;
import com.liferay.search.experiences.rest.client.serdes.v1_0.SXPBlueprintSerDes;

import java.lang.reflect.InvocationTargetException;

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
public abstract class BaseSXPBlueprintResourceTestCase {

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

		_sxpBlueprintResource.setContextCompany(testCompany);

		SXPBlueprintResource.Builder builder = SXPBlueprintResource.builder();

		sxpBlueprintResource = builder.authentication(
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

		SXPBlueprint sxpBlueprint1 = randomSXPBlueprint();

		String json = objectMapper.writeValueAsString(sxpBlueprint1);

		SXPBlueprint sxpBlueprint2 = SXPBlueprintSerDes.toDTO(json);

		Assert.assertTrue(equals(sxpBlueprint1, sxpBlueprint2));
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

		SXPBlueprint sxpBlueprint = randomSXPBlueprint();

		String json1 = objectMapper.writeValueAsString(sxpBlueprint);
		String json2 = SXPBlueprintSerDes.toJSON(sxpBlueprint);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		SXPBlueprint sxpBlueprint = randomSXPBlueprint();

		sxpBlueprint.setDescription(regex);
		sxpBlueprint.setSchemaVersion(regex);
		sxpBlueprint.setTitle(regex);
		sxpBlueprint.setUserName(regex);

		String json = SXPBlueprintSerDes.toJSON(sxpBlueprint);

		Assert.assertFalse(json.contains(regex));

		sxpBlueprint = SXPBlueprintSerDes.toDTO(json);

		Assert.assertEquals(regex, sxpBlueprint.getDescription());
		Assert.assertEquals(regex, sxpBlueprint.getSchemaVersion());
		Assert.assertEquals(regex, sxpBlueprint.getTitle());
		Assert.assertEquals(regex, sxpBlueprint.getUserName());
	}

	@Test
	public void testGetSXPBlueprintsPage() throws Exception {
		Page<SXPBlueprint> page = sxpBlueprintResource.getSXPBlueprintsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		SXPBlueprint sxpBlueprint1 = testGetSXPBlueprintsPage_addSXPBlueprint(
			randomSXPBlueprint());

		SXPBlueprint sxpBlueprint2 = testGetSXPBlueprintsPage_addSXPBlueprint(
			randomSXPBlueprint());

		page = sxpBlueprintResource.getSXPBlueprintsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(sxpBlueprint1, (List<SXPBlueprint>)page.getItems());
		assertContains(sxpBlueprint2, (List<SXPBlueprint>)page.getItems());
		assertValid(page);

		sxpBlueprintResource.deleteSXPBlueprint(sxpBlueprint1.getId());

		sxpBlueprintResource.deleteSXPBlueprint(sxpBlueprint2.getId());
	}

	@Test
	public void testGetSXPBlueprintsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		SXPBlueprint sxpBlueprint1 = randomSXPBlueprint();

		sxpBlueprint1 = testGetSXPBlueprintsPage_addSXPBlueprint(sxpBlueprint1);

		for (EntityField entityField : entityFields) {
			Page<SXPBlueprint> page = sxpBlueprintResource.getSXPBlueprintsPage(
				null, getFilterString(entityField, "between", sxpBlueprint1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(sxpBlueprint1),
				(List<SXPBlueprint>)page.getItems());
		}
	}

	@Test
	public void testGetSXPBlueprintsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		SXPBlueprint sxpBlueprint1 = testGetSXPBlueprintsPage_addSXPBlueprint(
			randomSXPBlueprint());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		SXPBlueprint sxpBlueprint2 = testGetSXPBlueprintsPage_addSXPBlueprint(
			randomSXPBlueprint());

		for (EntityField entityField : entityFields) {
			Page<SXPBlueprint> page = sxpBlueprintResource.getSXPBlueprintsPage(
				null, getFilterString(entityField, "eq", sxpBlueprint1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(sxpBlueprint1),
				(List<SXPBlueprint>)page.getItems());
		}
	}

	@Test
	public void testGetSXPBlueprintsPageWithPagination() throws Exception {
		Page<SXPBlueprint> totalPage =
			sxpBlueprintResource.getSXPBlueprintsPage(null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		SXPBlueprint sxpBlueprint1 = testGetSXPBlueprintsPage_addSXPBlueprint(
			randomSXPBlueprint());

		SXPBlueprint sxpBlueprint2 = testGetSXPBlueprintsPage_addSXPBlueprint(
			randomSXPBlueprint());

		SXPBlueprint sxpBlueprint3 = testGetSXPBlueprintsPage_addSXPBlueprint(
			randomSXPBlueprint());

		Page<SXPBlueprint> page1 = sxpBlueprintResource.getSXPBlueprintsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<SXPBlueprint> sxpBlueprints1 =
			(List<SXPBlueprint>)page1.getItems();

		Assert.assertEquals(
			sxpBlueprints1.toString(), totalCount + 2, sxpBlueprints1.size());

		Page<SXPBlueprint> page2 = sxpBlueprintResource.getSXPBlueprintsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<SXPBlueprint> sxpBlueprints2 =
			(List<SXPBlueprint>)page2.getItems();

		Assert.assertEquals(
			sxpBlueprints2.toString(), 1, sxpBlueprints2.size());

		Page<SXPBlueprint> page3 = sxpBlueprintResource.getSXPBlueprintsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(sxpBlueprint1, (List<SXPBlueprint>)page3.getItems());
		assertContains(sxpBlueprint2, (List<SXPBlueprint>)page3.getItems());
		assertContains(sxpBlueprint3, (List<SXPBlueprint>)page3.getItems());
	}

	@Test
	public void testGetSXPBlueprintsPageWithSortDateTime() throws Exception {
		testGetSXPBlueprintsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, sxpBlueprint1, sxpBlueprint2) -> {
				BeanUtils.setProperty(
					sxpBlueprint1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSXPBlueprintsPageWithSortInteger() throws Exception {
		testGetSXPBlueprintsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, sxpBlueprint1, sxpBlueprint2) -> {
				BeanUtils.setProperty(sxpBlueprint1, entityField.getName(), 0);
				BeanUtils.setProperty(sxpBlueprint2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSXPBlueprintsPageWithSortString() throws Exception {
		testGetSXPBlueprintsPageWithSort(
			EntityField.Type.STRING,
			(entityField, sxpBlueprint1, sxpBlueprint2) -> {
				Class<?> clazz = sxpBlueprint1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						sxpBlueprint1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						sxpBlueprint2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						sxpBlueprint1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						sxpBlueprint2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						sxpBlueprint1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						sxpBlueprint2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSXPBlueprintsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, SXPBlueprint, SXPBlueprint, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		SXPBlueprint sxpBlueprint1 = randomSXPBlueprint();
		SXPBlueprint sxpBlueprint2 = randomSXPBlueprint();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, sxpBlueprint1, sxpBlueprint2);
		}

		sxpBlueprint1 = testGetSXPBlueprintsPage_addSXPBlueprint(sxpBlueprint1);

		sxpBlueprint2 = testGetSXPBlueprintsPage_addSXPBlueprint(sxpBlueprint2);

		for (EntityField entityField : entityFields) {
			Page<SXPBlueprint> ascPage =
				sxpBlueprintResource.getSXPBlueprintsPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(sxpBlueprint1, sxpBlueprint2),
				(List<SXPBlueprint>)ascPage.getItems());

			Page<SXPBlueprint> descPage =
				sxpBlueprintResource.getSXPBlueprintsPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(sxpBlueprint2, sxpBlueprint1),
				(List<SXPBlueprint>)descPage.getItems());
		}
	}

	protected SXPBlueprint testGetSXPBlueprintsPage_addSXPBlueprint(
			SXPBlueprint sxpBlueprint)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostSXPBlueprint() throws Exception {
		SXPBlueprint randomSXPBlueprint = randomSXPBlueprint();

		SXPBlueprint postSXPBlueprint = testPostSXPBlueprint_addSXPBlueprint(
			randomSXPBlueprint);

		assertEquals(randomSXPBlueprint, postSXPBlueprint);
		assertValid(postSXPBlueprint);
	}

	protected SXPBlueprint testPostSXPBlueprint_addSXPBlueprint(
			SXPBlueprint sxpBlueprint)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostSXPBlueprintValidate() throws Exception {
		SXPBlueprint randomSXPBlueprint = randomSXPBlueprint();

		SXPBlueprint postSXPBlueprint =
			testPostSXPBlueprintValidate_addSXPBlueprint(randomSXPBlueprint);

		assertEquals(randomSXPBlueprint, postSXPBlueprint);
		assertValid(postSXPBlueprint);
	}

	protected SXPBlueprint testPostSXPBlueprintValidate_addSXPBlueprint(
			SXPBlueprint sxpBlueprint)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteSXPBlueprint() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		SXPBlueprint sxpBlueprint = testDeleteSXPBlueprint_addSXPBlueprint();

		assertHttpResponseStatusCode(
			204,
			sxpBlueprintResource.deleteSXPBlueprintHttpResponse(
				sxpBlueprint.getId()));

		assertHttpResponseStatusCode(
			404,
			sxpBlueprintResource.getSXPBlueprintHttpResponse(
				sxpBlueprint.getId()));

		assertHttpResponseStatusCode(
			404, sxpBlueprintResource.getSXPBlueprintHttpResponse(0L));
	}

	protected SXPBlueprint testDeleteSXPBlueprint_addSXPBlueprint()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteSXPBlueprint() throws Exception {
		SXPBlueprint sxpBlueprint = testGraphQLSXPBlueprint_addSXPBlueprint();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteSXPBlueprint",
						new HashMap<String, Object>() {
							{
								put("sxpBlueprintId", sxpBlueprint.getId());
							}
						})),
				"JSONObject/data", "Object/deleteSXPBlueprint"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"sXPBlueprint",
					new HashMap<String, Object>() {
						{
							put("sxpBlueprintId", sxpBlueprint.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetSXPBlueprint() throws Exception {
		SXPBlueprint postSXPBlueprint = testGetSXPBlueprint_addSXPBlueprint();

		SXPBlueprint getSXPBlueprint = sxpBlueprintResource.getSXPBlueprint(
			postSXPBlueprint.getId());

		assertEquals(postSXPBlueprint, getSXPBlueprint);
		assertValid(getSXPBlueprint);
	}

	protected SXPBlueprint testGetSXPBlueprint_addSXPBlueprint()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSXPBlueprint() throws Exception {
		SXPBlueprint sxpBlueprint = testGraphQLSXPBlueprint_addSXPBlueprint();

		Assert.assertTrue(
			equals(
				sxpBlueprint,
				SXPBlueprintSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"sXPBlueprint",
								new HashMap<String, Object>() {
									{
										put(
											"sxpBlueprintId",
											sxpBlueprint.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/sXPBlueprint"))));
	}

	@Test
	public void testGraphQLGetSXPBlueprintNotFound() throws Exception {
		Long irrelevantSxpBlueprintId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"sXPBlueprint",
						new HashMap<String, Object>() {
							{
								put("sxpBlueprintId", irrelevantSxpBlueprintId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchSXPBlueprint() throws Exception {
		SXPBlueprint postSXPBlueprint = testPatchSXPBlueprint_addSXPBlueprint();

		SXPBlueprint randomPatchSXPBlueprint = randomPatchSXPBlueprint();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		SXPBlueprint patchSXPBlueprint = sxpBlueprintResource.patchSXPBlueprint(
			postSXPBlueprint.getId(), randomPatchSXPBlueprint);

		SXPBlueprint expectedPatchSXPBlueprint = postSXPBlueprint.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchSXPBlueprint, randomPatchSXPBlueprint);

		SXPBlueprint getSXPBlueprint = sxpBlueprintResource.getSXPBlueprint(
			patchSXPBlueprint.getId());

		assertEquals(expectedPatchSXPBlueprint, getSXPBlueprint);
		assertValid(getSXPBlueprint);
	}

	protected SXPBlueprint testPatchSXPBlueprint_addSXPBlueprint()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostSXPBlueprintCopy() throws Exception {
		SXPBlueprint randomSXPBlueprint = randomSXPBlueprint();

		SXPBlueprint postSXPBlueprint =
			testPostSXPBlueprintCopy_addSXPBlueprint(randomSXPBlueprint);

		assertEquals(randomSXPBlueprint, postSXPBlueprint);
		assertValid(postSXPBlueprint);
	}

	protected SXPBlueprint testPostSXPBlueprintCopy_addSXPBlueprint(
			SXPBlueprint sxpBlueprint)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSXPBlueprintExport() throws Exception {
		Assert.assertTrue(false);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected SXPBlueprint testGraphQLSXPBlueprint_addSXPBlueprint()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		SXPBlueprint sxpBlueprint, List<SXPBlueprint> sxpBlueprints) {

		boolean contains = false;

		for (SXPBlueprint item : sxpBlueprints) {
			if (equals(sxpBlueprint, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			sxpBlueprints + " does not contain " + sxpBlueprint, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		SXPBlueprint sxpBlueprint1, SXPBlueprint sxpBlueprint2) {

		Assert.assertTrue(
			sxpBlueprint1 + " does not equal " + sxpBlueprint2,
			equals(sxpBlueprint1, sxpBlueprint2));
	}

	protected void assertEquals(
		List<SXPBlueprint> sxpBlueprints1, List<SXPBlueprint> sxpBlueprints2) {

		Assert.assertEquals(sxpBlueprints1.size(), sxpBlueprints2.size());

		for (int i = 0; i < sxpBlueprints1.size(); i++) {
			SXPBlueprint sxpBlueprint1 = sxpBlueprints1.get(i);
			SXPBlueprint sxpBlueprint2 = sxpBlueprints2.get(i);

			assertEquals(sxpBlueprint1, sxpBlueprint2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<SXPBlueprint> sxpBlueprints1, List<SXPBlueprint> sxpBlueprints2) {

		Assert.assertEquals(sxpBlueprints1.size(), sxpBlueprints2.size());

		for (SXPBlueprint sxpBlueprint1 : sxpBlueprints1) {
			boolean contains = false;

			for (SXPBlueprint sxpBlueprint2 : sxpBlueprints2) {
				if (equals(sxpBlueprint1, sxpBlueprint2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				sxpBlueprints2 + " does not contain " + sxpBlueprint1,
				contains);
		}
	}

	protected void assertValid(SXPBlueprint sxpBlueprint) throws Exception {
		boolean valid = true;

		if (sxpBlueprint.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("configuration", additionalAssertFieldName)) {
				if (sxpBlueprint.getConfiguration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (sxpBlueprint.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (sxpBlueprint.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (sxpBlueprint.getDescription_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("elementInstances", additionalAssertFieldName)) {
				if (sxpBlueprint.getElementInstances() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (sxpBlueprint.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("schemaVersion", additionalAssertFieldName)) {
				if (sxpBlueprint.getSchemaVersion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (sxpBlueprint.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (sxpBlueprint.getTitle_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (sxpBlueprint.getUserName() == null) {
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

	protected void assertValid(Page<SXPBlueprint> page) {
		boolean valid = false;

		java.util.Collection<SXPBlueprint> sxpBlueprints = page.getItems();

		int size = sxpBlueprints.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint.
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
		SXPBlueprint sxpBlueprint1, SXPBlueprint sxpBlueprint2) {

		if (sxpBlueprint1 == sxpBlueprint2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("configuration", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpBlueprint1.getConfiguration(),
						sxpBlueprint2.getConfiguration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpBlueprint1.getCreateDate(),
						sxpBlueprint2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpBlueprint1.getDescription(),
						sxpBlueprint2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)sxpBlueprint1.getDescription_i18n(),
						(Map)sxpBlueprint2.getDescription_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("elementInstances", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpBlueprint1.getElementInstances(),
						sxpBlueprint2.getElementInstances())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpBlueprint1.getId(), sxpBlueprint2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpBlueprint1.getModifiedDate(),
						sxpBlueprint2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("schemaVersion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpBlueprint1.getSchemaVersion(),
						sxpBlueprint2.getSchemaVersion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpBlueprint1.getTitle(), sxpBlueprint2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)sxpBlueprint1.getTitle_i18n(),
						(Map)sxpBlueprint2.getTitle_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sxpBlueprint1.getUserName(),
						sxpBlueprint2.getUserName())) {

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

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_sxpBlueprintResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_sxpBlueprintResource;

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
		EntityField entityField, String operator, SXPBlueprint sxpBlueprint) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("configuration")) {
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
						DateUtils.addSeconds(
							sxpBlueprint.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(sxpBlueprint.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(sxpBlueprint.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(sxpBlueprint.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("elementInstances")) {
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
							sxpBlueprint.getModifiedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							sxpBlueprint.getModifiedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(sxpBlueprint.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("schemaVersion")) {
			sb.append("'");
			sb.append(String.valueOf(sxpBlueprint.getSchemaVersion()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(sxpBlueprint.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("userName")) {
			sb.append("'");
			sb.append(String.valueOf(sxpBlueprint.getUserName()));
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

	protected SXPBlueprint randomSXPBlueprint() throws Exception {
		return new SXPBlueprint() {
			{
				createDate = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				schemaVersion = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				userName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected SXPBlueprint randomIrrelevantSXPBlueprint() throws Exception {
		SXPBlueprint randomIrrelevantSXPBlueprint = randomSXPBlueprint();

		return randomIrrelevantSXPBlueprint;
	}

	protected SXPBlueprint randomPatchSXPBlueprint() throws Exception {
		return randomSXPBlueprint();
	}

	protected SXPBlueprintResource sxpBlueprintResource;
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
		LogFactoryUtil.getLog(BaseSXPBlueprintResourceTestCase.class);

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
		com.liferay.search.experiences.rest.resource.v1_0.SXPBlueprintResource
			_sxpBlueprintResource;

}