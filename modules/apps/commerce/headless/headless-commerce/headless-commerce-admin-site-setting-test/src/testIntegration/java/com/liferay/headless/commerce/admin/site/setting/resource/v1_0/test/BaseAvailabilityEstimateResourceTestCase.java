/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.site.setting.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.site.setting.client.dto.v1_0.AvailabilityEstimate;
import com.liferay.headless.commerce.admin.site.setting.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.site.setting.client.pagination.Page;
import com.liferay.headless.commerce.admin.site.setting.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.site.setting.client.resource.v1_0.AvailabilityEstimateResource;
import com.liferay.headless.commerce.admin.site.setting.client.serdes.v1_0.AvailabilityEstimateSerDes;
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
public abstract class BaseAvailabilityEstimateResourceTestCase {

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

		_availabilityEstimateResource.setContextCompany(testCompany);

		AvailabilityEstimateResource.Builder builder =
			AvailabilityEstimateResource.builder();

		availabilityEstimateResource = builder.authentication(
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

		AvailabilityEstimate availabilityEstimate1 =
			randomAvailabilityEstimate();

		String json = objectMapper.writeValueAsString(availabilityEstimate1);

		AvailabilityEstimate availabilityEstimate2 =
			AvailabilityEstimateSerDes.toDTO(json);

		Assert.assertTrue(equals(availabilityEstimate1, availabilityEstimate2));
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

		AvailabilityEstimate availabilityEstimate =
			randomAvailabilityEstimate();

		String json1 = objectMapper.writeValueAsString(availabilityEstimate);
		String json2 = AvailabilityEstimateSerDes.toJSON(availabilityEstimate);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AvailabilityEstimate availabilityEstimate =
			randomAvailabilityEstimate();

		String json = AvailabilityEstimateSerDes.toJSON(availabilityEstimate);

		Assert.assertFalse(json.contains(regex));

		availabilityEstimate = AvailabilityEstimateSerDes.toDTO(json);
	}

	@Test
	public void testDeleteAvailabilityEstimate() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		AvailabilityEstimate availabilityEstimate =
			testDeleteAvailabilityEstimate_addAvailabilityEstimate();

		assertHttpResponseStatusCode(
			204,
			availabilityEstimateResource.deleteAvailabilityEstimateHttpResponse(
				availabilityEstimate.getId()));

		assertHttpResponseStatusCode(
			404,
			availabilityEstimateResource.getAvailabilityEstimateHttpResponse(
				availabilityEstimate.getId()));

		assertHttpResponseStatusCode(
			404,
			availabilityEstimateResource.getAvailabilityEstimateHttpResponse(
				availabilityEstimate.getId()));
	}

	protected AvailabilityEstimate
			testDeleteAvailabilityEstimate_addAvailabilityEstimate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteAvailabilityEstimate() throws Exception {
		AvailabilityEstimate availabilityEstimate =
			testGraphQLDeleteAvailabilityEstimate_addAvailabilityEstimate();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteAvailabilityEstimate",
						new HashMap<String, Object>() {
							{
								put("id", availabilityEstimate.getId());
							}
						})),
				"JSONObject/data", "Object/deleteAvailabilityEstimate"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"availabilityEstimate",
					new HashMap<String, Object>() {
						{
							put("id", availabilityEstimate.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected AvailabilityEstimate
			testGraphQLDeleteAvailabilityEstimate_addAvailabilityEstimate()
		throws Exception {

		return testGraphQLAvailabilityEstimate_addAvailabilityEstimate();
	}

	@Test
	public void testGetAvailabilityEstimate() throws Exception {
		AvailabilityEstimate postAvailabilityEstimate =
			testGetAvailabilityEstimate_addAvailabilityEstimate();

		AvailabilityEstimate getAvailabilityEstimate =
			availabilityEstimateResource.getAvailabilityEstimate(
				postAvailabilityEstimate.getId());

		assertEquals(postAvailabilityEstimate, getAvailabilityEstimate);
		assertValid(getAvailabilityEstimate);
	}

	protected AvailabilityEstimate
			testGetAvailabilityEstimate_addAvailabilityEstimate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAvailabilityEstimate() throws Exception {
		AvailabilityEstimate availabilityEstimate =
			testGraphQLGetAvailabilityEstimate_addAvailabilityEstimate();

		Assert.assertTrue(
			equals(
				availabilityEstimate,
				AvailabilityEstimateSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"availabilityEstimate",
								new HashMap<String, Object>() {
									{
										put("id", availabilityEstimate.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/availabilityEstimate"))));
	}

	@Test
	public void testGraphQLGetAvailabilityEstimateNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"availabilityEstimate",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected AvailabilityEstimate
			testGraphQLGetAvailabilityEstimate_addAvailabilityEstimate()
		throws Exception {

		return testGraphQLAvailabilityEstimate_addAvailabilityEstimate();
	}

	@Test
	public void testPutAvailabilityEstimate() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage()
		throws Exception {

		Long groupId =
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_getGroupId();
		Long irrelevantGroupId =
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_getIrrelevantGroupId();

		Page<AvailabilityEstimate> page =
			availabilityEstimateResource.
				getCommerceAdminSiteSettingGroupAvailabilityEstimatePage(
					groupId, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantGroupId != null) {
			AvailabilityEstimate irrelevantAvailabilityEstimate =
				testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_addAvailabilityEstimate(
					irrelevantGroupId, randomIrrelevantAvailabilityEstimate());

			page =
				availabilityEstimateResource.
					getCommerceAdminSiteSettingGroupAvailabilityEstimatePage(
						irrelevantGroupId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAvailabilityEstimate),
				(List<AvailabilityEstimate>)page.getItems());
			assertValid(
				page,
				testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_getExpectedActions(
					irrelevantGroupId));
		}

		AvailabilityEstimate availabilityEstimate1 =
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_addAvailabilityEstimate(
				groupId, randomAvailabilityEstimate());

		AvailabilityEstimate availabilityEstimate2 =
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_addAvailabilityEstimate(
				groupId, randomAvailabilityEstimate());

		page =
			availabilityEstimateResource.
				getCommerceAdminSiteSettingGroupAvailabilityEstimatePage(
					groupId, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(availabilityEstimate1, availabilityEstimate2),
			(List<AvailabilityEstimate>)page.getItems());
		assertValid(
			page,
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_getExpectedActions(
				groupId));

		availabilityEstimateResource.deleteAvailabilityEstimate(
			availabilityEstimate1.getId());

		availabilityEstimateResource.deleteAvailabilityEstimate(
			availabilityEstimate2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_getExpectedActions(
				Long groupId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePageWithPagination()
		throws Exception {

		Long groupId =
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_getGroupId();

		AvailabilityEstimate availabilityEstimate1 =
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_addAvailabilityEstimate(
				groupId, randomAvailabilityEstimate());

		AvailabilityEstimate availabilityEstimate2 =
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_addAvailabilityEstimate(
				groupId, randomAvailabilityEstimate());

		AvailabilityEstimate availabilityEstimate3 =
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_addAvailabilityEstimate(
				groupId, randomAvailabilityEstimate());

		Page<AvailabilityEstimate> page1 =
			availabilityEstimateResource.
				getCommerceAdminSiteSettingGroupAvailabilityEstimatePage(
					groupId, Pagination.of(1, 2));

		List<AvailabilityEstimate> availabilityEstimates1 =
			(List<AvailabilityEstimate>)page1.getItems();

		Assert.assertEquals(
			availabilityEstimates1.toString(), 2,
			availabilityEstimates1.size());

		Page<AvailabilityEstimate> page2 =
			availabilityEstimateResource.
				getCommerceAdminSiteSettingGroupAvailabilityEstimatePage(
					groupId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AvailabilityEstimate> availabilityEstimates2 =
			(List<AvailabilityEstimate>)page2.getItems();

		Assert.assertEquals(
			availabilityEstimates2.toString(), 1,
			availabilityEstimates2.size());

		Page<AvailabilityEstimate> page3 =
			availabilityEstimateResource.
				getCommerceAdminSiteSettingGroupAvailabilityEstimatePage(
					groupId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				availabilityEstimate1, availabilityEstimate2,
				availabilityEstimate3),
			(List<AvailabilityEstimate>)page3.getItems());
	}

	protected AvailabilityEstimate
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_addAvailabilityEstimate(
				Long groupId, AvailabilityEstimate availabilityEstimate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_getGroupId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetCommerceAdminSiteSettingGroupAvailabilityEstimatePage_getIrrelevantGroupId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostCommerceAdminSiteSettingGroupAvailabilityEstimate()
		throws Exception {

		AvailabilityEstimate randomAvailabilityEstimate =
			randomAvailabilityEstimate();

		AvailabilityEstimate postAvailabilityEstimate =
			testPostCommerceAdminSiteSettingGroupAvailabilityEstimate_addAvailabilityEstimate(
				randomAvailabilityEstimate);

		assertEquals(randomAvailabilityEstimate, postAvailabilityEstimate);
		assertValid(postAvailabilityEstimate);
	}

	protected AvailabilityEstimate
			testPostCommerceAdminSiteSettingGroupAvailabilityEstimate_addAvailabilityEstimate(
				AvailabilityEstimate availabilityEstimate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected AvailabilityEstimate
			testGraphQLAvailabilityEstimate_addAvailabilityEstimate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		AvailabilityEstimate availabilityEstimate,
		List<AvailabilityEstimate> availabilityEstimates) {

		boolean contains = false;

		for (AvailabilityEstimate item : availabilityEstimates) {
			if (equals(availabilityEstimate, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			availabilityEstimates + " does not contain " + availabilityEstimate,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		AvailabilityEstimate availabilityEstimate1,
		AvailabilityEstimate availabilityEstimate2) {

		Assert.assertTrue(
			availabilityEstimate1 + " does not equal " + availabilityEstimate2,
			equals(availabilityEstimate1, availabilityEstimate2));
	}

	protected void assertEquals(
		List<AvailabilityEstimate> availabilityEstimates1,
		List<AvailabilityEstimate> availabilityEstimates2) {

		Assert.assertEquals(
			availabilityEstimates1.size(), availabilityEstimates2.size());

		for (int i = 0; i < availabilityEstimates1.size(); i++) {
			AvailabilityEstimate availabilityEstimate1 =
				availabilityEstimates1.get(i);
			AvailabilityEstimate availabilityEstimate2 =
				availabilityEstimates2.get(i);

			assertEquals(availabilityEstimate1, availabilityEstimate2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AvailabilityEstimate> availabilityEstimates1,
		List<AvailabilityEstimate> availabilityEstimates2) {

		Assert.assertEquals(
			availabilityEstimates1.size(), availabilityEstimates2.size());

		for (AvailabilityEstimate availabilityEstimate1 :
				availabilityEstimates1) {

			boolean contains = false;

			for (AvailabilityEstimate availabilityEstimate2 :
					availabilityEstimates2) {

				if (equals(availabilityEstimate1, availabilityEstimate2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				availabilityEstimates2 + " does not contain " +
					availabilityEstimate1,
				contains);
		}
	}

	protected void assertValid(AvailabilityEstimate availabilityEstimate)
		throws Exception {

		boolean valid = true;

		if (availabilityEstimate.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("groupId", additionalAssertFieldName)) {
				if (availabilityEstimate.getGroupId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (availabilityEstimate.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (availabilityEstimate.getTitle() == null) {
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

	protected void assertValid(Page<AvailabilityEstimate> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<AvailabilityEstimate> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<AvailabilityEstimate> availabilityEstimates =
			page.getItems();

		int size = availabilityEstimates.size();

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
					com.liferay.headless.commerce.admin.site.setting.dto.v1_0.
						AvailabilityEstimate.class)) {

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
		AvailabilityEstimate availabilityEstimate1,
		AvailabilityEstimate availabilityEstimate2) {

		if (availabilityEstimate1 == availabilityEstimate2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("groupId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						availabilityEstimate1.getGroupId(),
						availabilityEstimate2.getGroupId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						availabilityEstimate1.getId(),
						availabilityEstimate2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						availabilityEstimate1.getPriority(),
						availabilityEstimate2.getPriority())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!equals(
						(Map)availabilityEstimate1.getTitle(),
						(Map)availabilityEstimate2.getTitle())) {

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

		if (!(_availabilityEstimateResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_availabilityEstimateResource;

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
		AvailabilityEstimate availabilityEstimate) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("groupId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priority")) {
			sb.append(String.valueOf(availabilityEstimate.getPriority()));

			return sb.toString();
		}

		if (entityFieldName.equals("title")) {
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

	protected AvailabilityEstimate randomAvailabilityEstimate()
		throws Exception {

		return new AvailabilityEstimate() {
			{
				groupId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				priority = RandomTestUtil.randomDouble();
			}
		};
	}

	protected AvailabilityEstimate randomIrrelevantAvailabilityEstimate()
		throws Exception {

		AvailabilityEstimate randomIrrelevantAvailabilityEstimate =
			randomAvailabilityEstimate();

		return randomIrrelevantAvailabilityEstimate;
	}

	protected AvailabilityEstimate randomPatchAvailabilityEstimate()
		throws Exception {

		return randomAvailabilityEstimate();
	}

	protected AvailabilityEstimateResource availabilityEstimateResource;
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
		LogFactoryUtil.getLog(BaseAvailabilityEstimateResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.admin.site.setting.resource.v1_0.
		AvailabilityEstimateResource _availabilityEstimateResource;

}