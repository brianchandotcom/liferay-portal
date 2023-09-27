/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.permission.Permission;
import com.liferay.data.engine.rest.client.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataDefinitionSerDes;
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
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
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
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public abstract class BaseDataDefinitionResourceTestCase {

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

		_dataDefinitionResource.setContextCompany(testCompany);

		DataDefinitionResource.Builder builder =
			DataDefinitionResource.builder();

		dataDefinitionResource = builder.authentication(
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

		DataDefinition dataDefinition1 = randomDataDefinition();

		String json = objectMapper.writeValueAsString(dataDefinition1);

		DataDefinition dataDefinition2 = DataDefinitionSerDes.toDTO(json);

		Assert.assertTrue(equals(dataDefinition1, dataDefinition2));
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

		DataDefinition dataDefinition = randomDataDefinition();

		String json1 = objectMapper.writeValueAsString(dataDefinition);
		String json2 = DataDefinitionSerDes.toJSON(dataDefinition);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DataDefinition dataDefinition = randomDataDefinition();

		dataDefinition.setContentType(regex);
		dataDefinition.setDataDefinitionKey(regex);
		dataDefinition.setDefaultLanguageId(regex);
		dataDefinition.setStorageType(regex);

		String json = DataDefinitionSerDes.toJSON(dataDefinition);

		Assert.assertFalse(json.contains(regex));

		dataDefinition = DataDefinitionSerDes.toDTO(json);

		Assert.assertEquals(regex, dataDefinition.getContentType());
		Assert.assertEquals(regex, dataDefinition.getDataDefinitionKey());
		Assert.assertEquals(regex, dataDefinition.getDefaultLanguageId());
		Assert.assertEquals(regex, dataDefinition.getStorageType());
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePage()
		throws Exception {

		String contentType =
			testGetDataDefinitionByContentTypeContentTypePage_getContentType();
		String irrelevantContentType =
			testGetDataDefinitionByContentTypeContentTypePage_getIrrelevantContentType();

		Page<DataDefinition> page =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					contentType, RandomTestUtil.randomString(),
					Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantContentType != null) {
			DataDefinition irrelevantDataDefinition =
				testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
					irrelevantContentType, randomIrrelevantDataDefinition());

			page =
				dataDefinitionResource.
					getDataDefinitionByContentTypeContentTypePage(
						irrelevantContentType, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataDefinition),
				(List<DataDefinition>)page.getItems());
			assertValid(
				page,
				testGetDataDefinitionByContentTypeContentTypePage_getExpectedActions(
					irrelevantContentType));
		}

		DataDefinition dataDefinition1 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		page =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					contentType, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2),
			(List<DataDefinition>)page.getItems());
		assertValid(
			page,
			testGetDataDefinitionByContentTypeContentTypePage_getExpectedActions(
				contentType));

		dataDefinitionResource.deleteDataDefinition(dataDefinition1.getId());

		dataDefinitionResource.deleteDataDefinition(dataDefinition2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetDataDefinitionByContentTypeContentTypePage_getExpectedActions(
				String contentType)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePageWithPagination()
		throws Exception {

		String contentType =
			testGetDataDefinitionByContentTypeContentTypePage_getContentType();

		DataDefinition dataDefinition1 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		DataDefinition dataDefinition3 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		Page<DataDefinition> page1 =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					contentType, null, Pagination.of(1, 2), null);

		List<DataDefinition> dataDefinitions1 =
			(List<DataDefinition>)page1.getItems();

		Assert.assertEquals(
			dataDefinitions1.toString(), 2, dataDefinitions1.size());

		Page<DataDefinition> page2 =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					contentType, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataDefinition> dataDefinitions2 =
			(List<DataDefinition>)page2.getItems();

		Assert.assertEquals(
			dataDefinitions2.toString(), 1, dataDefinitions2.size());

		Page<DataDefinition> page3 =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					contentType, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2, dataDefinition3),
			(List<DataDefinition>)page3.getItems());
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePageWithSortDateTime()
		throws Exception {

		testGetDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanTestUtil.setProperty(
					dataDefinition1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePageWithSortDouble()
		throws Exception {

		testGetDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanTestUtil.setProperty(
					dataDefinition1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					dataDefinition2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePageWithSortInteger()
		throws Exception {

		testGetDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanTestUtil.setProperty(
					dataDefinition1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					dataDefinition2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePageWithSortString()
		throws Exception {

		testGetDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.STRING,
			(entityField, dataDefinition1, dataDefinition2) -> {
				Class<?> clazz = dataDefinition1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						dataDefinition1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						dataDefinition2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						dataDefinition1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						dataDefinition2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						dataDefinition1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						dataDefinition2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DataDefinition, DataDefinition, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String contentType =
			testGetDataDefinitionByContentTypeContentTypePage_getContentType();

		DataDefinition dataDefinition1 = randomDataDefinition();
		DataDefinition dataDefinition2 = randomDataDefinition();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, dataDefinition1, dataDefinition2);
		}

		dataDefinition1 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, dataDefinition1);

		dataDefinition2 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, dataDefinition2);

		for (EntityField entityField : entityFields) {
			Page<DataDefinition> ascPage =
				dataDefinitionResource.
					getDataDefinitionByContentTypeContentTypePage(
						contentType, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(dataDefinition1, dataDefinition2),
				(List<DataDefinition>)ascPage.getItems());

			Page<DataDefinition> descPage =
				dataDefinitionResource.
					getDataDefinitionByContentTypeContentTypePage(
						contentType, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(dataDefinition2, dataDefinition1),
				(List<DataDefinition>)descPage.getItems());
		}
	}

	protected DataDefinition
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				String contentType, DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDataDefinitionByContentTypeContentTypePage_getContentType()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDataDefinitionByContentTypeContentTypePage_getIrrelevantContentType()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDataDefinitionByContentType() throws Exception {
		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition postDataDefinition =
			testPostDataDefinitionByContentType_addDataDefinition(
				randomDataDefinition);

		assertEquals(randomDataDefinition, postDataDefinition);
		assertValid(postDataDefinition);
	}

	protected DataDefinition
			testPostDataDefinitionByContentType_addDataDefinition(
				DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDataDefinitionDataDefinitionFieldFieldTypes()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteDataDefinition() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DataDefinition dataDefinition =
			testDeleteDataDefinition_addDataDefinition();

		assertHttpResponseStatusCode(
			204,
			dataDefinitionResource.deleteDataDefinitionHttpResponse(
				dataDefinition.getId()));

		assertHttpResponseStatusCode(
			404,
			dataDefinitionResource.getDataDefinitionHttpResponse(
				dataDefinition.getId()));

		assertHttpResponseStatusCode(
			404, dataDefinitionResource.getDataDefinitionHttpResponse(0L));
	}

	protected DataDefinition testDeleteDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDataDefinition() throws Exception {
		DataDefinition dataDefinition =
			testGraphQLDeleteDataDefinition_addDataDefinition();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDataDefinition",
						new HashMap<String, Object>() {
							{
								put("dataDefinitionId", dataDefinition.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDataDefinition"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"dataDefinition",
					new HashMap<String, Object>() {
						{
							put("dataDefinitionId", dataDefinition.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected DataDefinition testGraphQLDeleteDataDefinition_addDataDefinition()
		throws Exception {

		return testGraphQLDataDefinition_addDataDefinition();
	}

	@Test
	public void testGetDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testGetDataDefinition_addDataDefinition();

		DataDefinition getDataDefinition =
			dataDefinitionResource.getDataDefinition(
				postDataDefinition.getId());

		assertEquals(postDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected DataDefinition testGetDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDataDefinition() throws Exception {
		DataDefinition dataDefinition =
			testGraphQLGetDataDefinition_addDataDefinition();

		Assert.assertTrue(
			equals(
				dataDefinition,
				DataDefinitionSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"dataDefinition",
								new HashMap<String, Object>() {
									{
										put(
											"dataDefinitionId",
											dataDefinition.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/dataDefinition"))));
	}

	@Test
	public void testGraphQLGetDataDefinitionNotFound() throws Exception {
		Long irrelevantDataDefinitionId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"dataDefinition",
						new HashMap<String, Object>() {
							{
								put(
									"dataDefinitionId",
									irrelevantDataDefinitionId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected DataDefinition testGraphQLGetDataDefinition_addDataDefinition()
		throws Exception {

		return testGraphQLDataDefinition_addDataDefinition();
	}

	@Test
	public void testPatchDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testPatchDataDefinition_addDataDefinition();

		DataDefinition randomPatchDataDefinition = randomPatchDataDefinition();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DataDefinition patchDataDefinition =
			dataDefinitionResource.patchDataDefinition(
				postDataDefinition.getId(), randomPatchDataDefinition);

		DataDefinition expectedPatchDataDefinition = postDataDefinition.clone();

		BeanTestUtil.copyProperties(
			randomPatchDataDefinition, expectedPatchDataDefinition);

		DataDefinition getDataDefinition =
			dataDefinitionResource.getDataDefinition(
				patchDataDefinition.getId());

		assertEquals(expectedPatchDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected DataDefinition testPatchDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testPutDataDefinition_addDataDefinition();

		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition putDataDefinition =
			dataDefinitionResource.putDataDefinition(
				postDataDefinition.getId(), randomDataDefinition);

		assertEquals(randomDataDefinition, putDataDefinition);
		assertValid(putDataDefinition);

		DataDefinition getDataDefinition =
			dataDefinitionResource.getDataDefinition(putDataDefinition.getId());

		assertEquals(randomDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected DataDefinition testPutDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostDataDefinitionCopy() throws Exception {
		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition postDataDefinition =
			testPostDataDefinitionCopy_addDataDefinition(randomDataDefinition);

		assertEquals(randomDataDefinition, postDataDefinition);
		assertValid(postDataDefinition);
	}

	protected DataDefinition testPostDataDefinitionCopy_addDataDefinition(
			DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDataDefinitionPermissionsPage() throws Exception {
		DataDefinition postDataDefinition =
			testGetDataDefinitionPermissionsPage_addDataDefinition();

		Page<Permission> page =
			dataDefinitionResource.getDataDefinitionPermissionsPage(
				postDataDefinition.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected DataDefinition
			testGetDataDefinitionPermissionsPage_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutDataDefinitionPermissionsPage() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DataDefinition dataDefinition =
			testPutDataDefinitionPermissionsPage_addDataDefinition();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			dataDefinitionResource.putDataDefinitionPermissionsPageHttpResponse(
				dataDefinition.getId(),
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"VIEW"});
							setRoleName(role.getName());
						}
					}
				}));

		assertHttpResponseStatusCode(
			404,
			dataDefinitionResource.putDataDefinitionPermissionsPageHttpResponse(
				0L,
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"-"});
							setRoleName("-");
						}
					}
				}));
	}

	protected DataDefinition
			testPutDataDefinitionPermissionsPage_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePage()
		throws Exception {

		Long siteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getIrrelevantSiteId();
		String contentType =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType();
		String irrelevantContentType =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getIrrelevantContentType();

		Page<DataDefinition> page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId, contentType, RandomTestUtil.randomString(),
					Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if ((irrelevantSiteId != null) && (irrelevantContentType != null)) {
			DataDefinition irrelevantDataDefinition =
				testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
					irrelevantSiteId, irrelevantContentType,
					randomIrrelevantDataDefinition());

			page =
				dataDefinitionResource.
					getSiteDataDefinitionByContentTypeContentTypePage(
						irrelevantSiteId, irrelevantContentType, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataDefinition),
				(List<DataDefinition>)page.getItems());
			assertValid(
				page,
				testGetSiteDataDefinitionByContentTypeContentTypePage_getExpectedActions(
					irrelevantSiteId, irrelevantContentType));
		}

		DataDefinition dataDefinition1 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId, contentType, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2),
			(List<DataDefinition>)page.getItems());
		assertValid(
			page,
			testGetSiteDataDefinitionByContentTypeContentTypePage_getExpectedActions(
				siteId, contentType));

		dataDefinitionResource.deleteDataDefinition(dataDefinition1.getId());

		dataDefinitionResource.deleteDataDefinition(dataDefinition2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetSiteDataDefinitionByContentTypeContentTypePage_getExpectedActions(
				Long siteId, String contentType)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePageWithPagination()
		throws Exception {

		Long siteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId();
		String contentType =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType();

		DataDefinition dataDefinition1 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		DataDefinition dataDefinition3 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		Page<DataDefinition> page1 =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId, contentType, null, Pagination.of(1, 2), null);

		List<DataDefinition> dataDefinitions1 =
			(List<DataDefinition>)page1.getItems();

		Assert.assertEquals(
			dataDefinitions1.toString(), 2, dataDefinitions1.size());

		Page<DataDefinition> page2 =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId, contentType, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataDefinition> dataDefinitions2 =
			(List<DataDefinition>)page2.getItems();

		Assert.assertEquals(
			dataDefinitions2.toString(), 1, dataDefinitions2.size());

		Page<DataDefinition> page3 =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId, contentType, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2, dataDefinition3),
			(List<DataDefinition>)page3.getItems());
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePageWithSortDateTime()
		throws Exception {

		testGetSiteDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanTestUtil.setProperty(
					dataDefinition1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePageWithSortDouble()
		throws Exception {

		testGetSiteDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanTestUtil.setProperty(
					dataDefinition1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					dataDefinition2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePageWithSortInteger()
		throws Exception {

		testGetSiteDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanTestUtil.setProperty(
					dataDefinition1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					dataDefinition2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePageWithSortString()
		throws Exception {

		testGetSiteDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.STRING,
			(entityField, dataDefinition1, dataDefinition2) -> {
				Class<?> clazz = dataDefinition1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						dataDefinition1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						dataDefinition2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						dataDefinition1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						dataDefinition2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						dataDefinition1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						dataDefinition2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void
			testGetSiteDataDefinitionByContentTypeContentTypePageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, DataDefinition, DataDefinition, Exception>
						unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId();
		String contentType =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType();

		DataDefinition dataDefinition1 = randomDataDefinition();
		DataDefinition dataDefinition2 = randomDataDefinition();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, dataDefinition1, dataDefinition2);
		}

		dataDefinition1 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, dataDefinition1);

		dataDefinition2 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, dataDefinition2);

		for (EntityField entityField : entityFields) {
			Page<DataDefinition> ascPage =
				dataDefinitionResource.
					getSiteDataDefinitionByContentTypeContentTypePage(
						siteId, contentType, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(dataDefinition1, dataDefinition2),
				(List<DataDefinition>)ascPage.getItems());

			Page<DataDefinition> descPage =
				dataDefinitionResource.
					getSiteDataDefinitionByContentTypeContentTypePage(
						siteId, contentType, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(dataDefinition2, dataDefinition1),
				(List<DataDefinition>)descPage.getItems());
		}
	}

	protected DataDefinition
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				Long siteId, String contentType, DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetSiteDataDefinitionByContentTypeContentTypePage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected String
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSiteDataDefinitionByContentTypeContentTypePage_getIrrelevantContentType()
		throws Exception {

		return null;
	}

	@Test
	public void testPostSiteDataDefinitionByContentType() throws Exception {
		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition postDataDefinition =
			testPostSiteDataDefinitionByContentType_addDataDefinition(
				randomDataDefinition);

		assertEquals(randomDataDefinition, postDataDefinition);
		assertValid(postDataDefinition);
	}

	protected DataDefinition
			testPostSiteDataDefinitionByContentType_addDataDefinition(
				DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeByDataDefinitionKey()
		throws Exception {

		DataDefinition postDataDefinition =
			testGetSiteDataDefinitionByContentTypeByDataDefinitionKey_addDataDefinition();

		DataDefinition getDataDefinition =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeByDataDefinitionKey(
					testGetSiteDataDefinitionByContentTypeByDataDefinitionKey_getSiteId(
						postDataDefinition),
					postDataDefinition.getContentType(),
					postDataDefinition.getDataDefinitionKey());

		assertEquals(postDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected Long
			testGetSiteDataDefinitionByContentTypeByDataDefinitionKey_getSiteId(
				DataDefinition dataDefinition)
		throws Exception {

		return dataDefinition.getSiteId();
	}

	protected DataDefinition
			testGetSiteDataDefinitionByContentTypeByDataDefinitionKey_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKey()
		throws Exception {

		DataDefinition dataDefinition =
			testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKey_addDataDefinition();

		Assert.assertTrue(
			equals(
				dataDefinition,
				DataDefinitionSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"dataDefinitionByContentTypeByDataDefinitionKey",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" +
												testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKey_getSiteId(
													dataDefinition) + "\"");

										put(
											"contentType",
											"\"" +
												dataDefinition.
													getContentType() + "\"");

										put(
											"dataDefinitionKey",
											"\"" +
												dataDefinition.
													getDataDefinitionKey() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/dataDefinitionByContentTypeByDataDefinitionKey"))));
	}

	protected Long
			testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKey_getSiteId(
				DataDefinition dataDefinition)
		throws Exception {

		return dataDefinition.getSiteId();
	}

	@Test
	public void testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKeyNotFound()
		throws Exception {

		String irrelevantContentType =
			"\"" + RandomTestUtil.randomString() + "\"";
		String irrelevantDataDefinitionKey =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"dataDefinitionByContentTypeByDataDefinitionKey",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put("contentType", irrelevantContentType);
								put(
									"dataDefinitionKey",
									irrelevantDataDefinitionKey);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected DataDefinition
			testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKey_addDataDefinition()
		throws Exception {

		return testGraphQLDataDefinition_addDataDefinition();
	}

	protected DataDefinition testGraphQLDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		DataDefinition dataDefinition, List<DataDefinition> dataDefinitions) {

		boolean contains = false;

		for (DataDefinition item : dataDefinitions) {
			if (equals(dataDefinition, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			dataDefinitions + " does not contain " + dataDefinition, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DataDefinition dataDefinition1, DataDefinition dataDefinition2) {

		Assert.assertTrue(
			dataDefinition1 + " does not equal " + dataDefinition2,
			equals(dataDefinition1, dataDefinition2));
	}

	protected void assertEquals(
		List<DataDefinition> dataDefinitions1,
		List<DataDefinition> dataDefinitions2) {

		Assert.assertEquals(dataDefinitions1.size(), dataDefinitions2.size());

		for (int i = 0; i < dataDefinitions1.size(); i++) {
			DataDefinition dataDefinition1 = dataDefinitions1.get(i);
			DataDefinition dataDefinition2 = dataDefinitions2.get(i);

			assertEquals(dataDefinition1, dataDefinition2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataDefinition> dataDefinitions1,
		List<DataDefinition> dataDefinitions2) {

		Assert.assertEquals(dataDefinitions1.size(), dataDefinitions2.size());

		for (DataDefinition dataDefinition1 : dataDefinitions1) {
			boolean contains = false;

			for (DataDefinition dataDefinition2 : dataDefinitions2) {
				if (equals(dataDefinition1, dataDefinition2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataDefinitions2 + " does not contain " + dataDefinition1,
				contains);
		}
	}

	protected void assertValid(DataDefinition dataDefinition) throws Exception {
		boolean valid = true;

		if (dataDefinition.getDateCreated() == null) {
			valid = false;
		}

		if (dataDefinition.getDateModified() == null) {
			valid = false;
		}

		if (dataDefinition.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				dataDefinition.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguageIds", additionalAssertFieldName)) {

				if (dataDefinition.getAvailableLanguageIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (dataDefinition.getContentType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionFields", additionalAssertFieldName)) {

				if (dataDefinition.getDataDefinitionFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionKey", additionalAssertFieldName)) {

				if (dataDefinition.getDataDefinitionKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataRules", additionalAssertFieldName)) {
				if (dataDefinition.getDataRules() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultDataLayout", additionalAssertFieldName)) {

				if (dataDefinition.getDefaultDataLayout() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultLanguageId", additionalAssertFieldName)) {

				if (dataDefinition.getDefaultLanguageId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (dataDefinition.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (dataDefinition.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("storageType", additionalAssertFieldName)) {
				if (dataDefinition.getStorageType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (dataDefinition.getUserId() == null) {
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

	protected void assertValid(Page<DataDefinition> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<DataDefinition> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<DataDefinition> dataDefinitions = page.getItems();

		int size = dataDefinitions.size();

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

		graphQLFields.add(new GraphQLField("siteId"));

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.data.engine.rest.dto.v2_0.DataDefinition.
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
		DataDefinition dataDefinition1, DataDefinition dataDefinition2) {

		if (dataDefinition1 == dataDefinition2) {
			return true;
		}

		if (!Objects.equals(
				dataDefinition1.getSiteId(), dataDefinition2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguageIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getAvailableLanguageIds(),
						dataDefinition2.getAvailableLanguageIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getContentType(),
						dataDefinition2.getContentType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionFields", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getDataDefinitionFields(),
						dataDefinition2.getDataDefinitionFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionKey", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getDataDefinitionKey(),
						dataDefinition2.getDataDefinitionKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataRules", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getDataRules(),
						dataDefinition2.getDataRules())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getDateCreated(),
						dataDefinition2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getDateModified(),
						dataDefinition2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultDataLayout", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getDefaultDataLayout(),
						dataDefinition2.getDefaultDataLayout())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultLanguageId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getDefaultLanguageId(),
						dataDefinition2.getDefaultLanguageId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!equals(
						(Map)dataDefinition1.getDescription(),
						(Map)dataDefinition2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getId(), dataDefinition2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)dataDefinition1.getName(),
						(Map)dataDefinition2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("storageType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getStorageType(),
						dataDefinition2.getStorageType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getUserId(),
						dataDefinition2.getUserId())) {

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

		if (!(_dataDefinitionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataDefinitionResource;

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
		DataDefinition dataDefinition) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("availableLanguageIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentType")) {
			Object object = dataDefinition.getContentType();

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

		if (entityFieldName.equals("dataDefinitionFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataDefinitionKey")) {
			Object object = dataDefinition.getDataDefinitionKey();

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

		if (entityFieldName.equals("dataRules")) {
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
							dataDefinition.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							dataDefinition.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataDefinition.getDateCreated()));
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
							dataDefinition.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							dataDefinition.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataDefinition.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultDataLayout")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("defaultLanguageId")) {
			Object object = dataDefinition.getDefaultLanguageId();

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

		if (entityFieldName.equals("description")) {
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

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("storageType")) {
			Object object = dataDefinition.getStorageType();

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

		if (entityFieldName.equals("userId")) {
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

	protected DataDefinition randomDataDefinition() throws Exception {
		return new DataDefinition() {
			{
				contentType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dataDefinitionKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				defaultLanguageId = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				storageType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataDefinition randomIrrelevantDataDefinition() throws Exception {
		DataDefinition randomIrrelevantDataDefinition = randomDataDefinition();

		randomIrrelevantDataDefinition.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantDataDefinition;
	}

	protected DataDefinition randomPatchDataDefinition() throws Exception {
		return randomDataDefinition();
	}

	protected DataDefinitionResource dataDefinitionResource;
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
		LogFactoryUtil.getLog(BaseDataDefinitionResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource
		_dataDefinitionResource;

}