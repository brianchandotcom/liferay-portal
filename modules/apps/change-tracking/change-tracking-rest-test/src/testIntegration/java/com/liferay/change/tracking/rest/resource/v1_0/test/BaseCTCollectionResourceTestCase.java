/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.change.tracking.rest.client.dto.v1_0.CTCollection;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.CTCollectionResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.CTCollectionSerDes;
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
 * @author David Truong
 * @generated
 */
@Generated("")
public abstract class BaseCTCollectionResourceTestCase {

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

		_ctCollectionResource.setContextCompany(testCompany);

		CTCollectionResource.Builder builder = CTCollectionResource.builder();

		ctCollectionResource = builder.authentication(
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

		CTCollection ctCollection1 = randomCTCollection();

		String json = objectMapper.writeValueAsString(ctCollection1);

		CTCollection ctCollection2 = CTCollectionSerDes.toDTO(json);

		Assert.assertTrue(equals(ctCollection1, ctCollection2));
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

		CTCollection ctCollection = randomCTCollection();

		String json1 = objectMapper.writeValueAsString(ctCollection);
		String json2 = CTCollectionSerDes.toJSON(ctCollection);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		CTCollection ctCollection = randomCTCollection();

		ctCollection.setDescription(regex);
		ctCollection.setExternalReferenceCode(regex);
		ctCollection.setName(regex);
		ctCollection.setOwnerName(regex);

		String json = CTCollectionSerDes.toJSON(ctCollection);

		Assert.assertFalse(json.contains(regex));

		ctCollection = CTCollectionSerDes.toDTO(json);

		Assert.assertEquals(regex, ctCollection.getDescription());
		Assert.assertEquals(regex, ctCollection.getExternalReferenceCode());
		Assert.assertEquals(regex, ctCollection.getName());
		Assert.assertEquals(regex, ctCollection.getOwnerName());
	}

	@Test
	public void testGetCTCollectionsPage() throws Exception {
		Page<CTCollection> page = ctCollectionResource.getCTCollectionsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		CTCollection ctCollection1 = testGetCTCollectionsPage_addCTCollection(
			randomCTCollection());

		CTCollection ctCollection2 = testGetCTCollectionsPage_addCTCollection(
			randomCTCollection());

		page = ctCollectionResource.getCTCollectionsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(ctCollection1, (List<CTCollection>)page.getItems());
		assertContains(ctCollection2, (List<CTCollection>)page.getItems());
		assertValid(page, testGetCTCollectionsPage_getExpectedActions());

		ctCollectionResource.deleteCTCollection(ctCollection1.getId());

		ctCollectionResource.deleteCTCollection(ctCollection2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetCTCollectionsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetCTCollectionsPageWithPagination() throws Exception {
		Page<CTCollection> totalPage =
			ctCollectionResource.getCTCollectionsPage(null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		CTCollection ctCollection1 = testGetCTCollectionsPage_addCTCollection(
			randomCTCollection());

		CTCollection ctCollection2 = testGetCTCollectionsPage_addCTCollection(
			randomCTCollection());

		CTCollection ctCollection3 = testGetCTCollectionsPage_addCTCollection(
			randomCTCollection());

		Page<CTCollection> page1 = ctCollectionResource.getCTCollectionsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<CTCollection> ctCollections1 =
			(List<CTCollection>)page1.getItems();

		Assert.assertEquals(
			ctCollections1.toString(), totalCount + 2, ctCollections1.size());

		Page<CTCollection> page2 = ctCollectionResource.getCTCollectionsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<CTCollection> ctCollections2 =
			(List<CTCollection>)page2.getItems();

		Assert.assertEquals(
			ctCollections2.toString(), 1, ctCollections2.size());

		Page<CTCollection> page3 = ctCollectionResource.getCTCollectionsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(ctCollection1, (List<CTCollection>)page3.getItems());
		assertContains(ctCollection2, (List<CTCollection>)page3.getItems());
		assertContains(ctCollection3, (List<CTCollection>)page3.getItems());
	}

	@Test
	public void testGetCTCollectionsPageWithSortDateTime() throws Exception {
		testGetCTCollectionsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, ctCollection1, ctCollection2) -> {
				BeanTestUtil.setProperty(
					ctCollection1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetCTCollectionsPageWithSortDouble() throws Exception {
		testGetCTCollectionsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, ctCollection1, ctCollection2) -> {
				BeanTestUtil.setProperty(
					ctCollection1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					ctCollection2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetCTCollectionsPageWithSortInteger() throws Exception {
		testGetCTCollectionsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, ctCollection1, ctCollection2) -> {
				BeanTestUtil.setProperty(
					ctCollection1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					ctCollection2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetCTCollectionsPageWithSortString() throws Exception {
		testGetCTCollectionsPageWithSort(
			EntityField.Type.STRING,
			(entityField, ctCollection1, ctCollection2) -> {
				Class<?> clazz = ctCollection1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						ctCollection1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						ctCollection2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						ctCollection1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						ctCollection2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						ctCollection1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						ctCollection2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetCTCollectionsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, CTCollection, CTCollection, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		CTCollection ctCollection1 = randomCTCollection();
		CTCollection ctCollection2 = randomCTCollection();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, ctCollection1, ctCollection2);
		}

		ctCollection1 = testGetCTCollectionsPage_addCTCollection(ctCollection1);

		ctCollection2 = testGetCTCollectionsPage_addCTCollection(ctCollection2);

		for (EntityField entityField : entityFields) {
			Page<CTCollection> ascPage =
				ctCollectionResource.getCTCollectionsPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(ctCollection1, ctCollection2),
				(List<CTCollection>)ascPage.getItems());

			Page<CTCollection> descPage =
				ctCollectionResource.getCTCollectionsPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(ctCollection2, ctCollection1),
				(List<CTCollection>)descPage.getItems());
		}
	}

	protected CTCollection testGetCTCollectionsPage_addCTCollection(
			CTCollection ctCollection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostCTCollection() throws Exception {
		CTCollection randomCTCollection = randomCTCollection();

		CTCollection postCTCollection = testPostCTCollection_addCTCollection(
			randomCTCollection);

		assertEquals(randomCTCollection, postCTCollection);
		assertValid(postCTCollection);
	}

	protected CTCollection testPostCTCollection_addCTCollection(
			CTCollection ctCollection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteCTCollectionByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTCollection ctCollection =
			testDeleteCTCollectionByExternalReferenceCode_addCTCollection();

		assertHttpResponseStatusCode(
			204,
			ctCollectionResource.
				deleteCTCollectionByExternalReferenceCodeHttpResponse(
					ctCollection.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			ctCollectionResource.
				getCTCollectionByExternalReferenceCodeHttpResponse(
					ctCollection.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			ctCollectionResource.
				getCTCollectionByExternalReferenceCodeHttpResponse(
					ctCollection.getExternalReferenceCode()));
	}

	protected CTCollection
			testDeleteCTCollectionByExternalReferenceCode_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetCTCollectionByExternalReferenceCode() throws Exception {
		CTCollection postCTCollection =
			testGetCTCollectionByExternalReferenceCode_addCTCollection();

		CTCollection getCTCollection =
			ctCollectionResource.getCTCollectionByExternalReferenceCode(
				postCTCollection.getExternalReferenceCode());

		assertEquals(postCTCollection, getCTCollection);
		assertValid(getCTCollection);
	}

	protected CTCollection
			testGetCTCollectionByExternalReferenceCode_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCTCollectionByExternalReferenceCode()
		throws Exception {

		CTCollection ctCollection =
			testGraphQLGetCTCollectionByExternalReferenceCode_addCTCollection();

		Assert.assertTrue(
			equals(
				ctCollection,
				CTCollectionSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"cTCollectionByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												ctCollection.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/cTCollectionByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetCTCollectionByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"cTCollectionByExternalReferenceCode",
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

	protected CTCollection
			testGraphQLGetCTCollectionByExternalReferenceCode_addCTCollection()
		throws Exception {

		return testGraphQLCTCollection_addCTCollection();
	}

	@Test
	public void testPatchCTCollectionByExternalReferenceCode()
		throws Exception {

		CTCollection postCTCollection =
			testPatchCTCollectionByExternalReferenceCode_addCTCollection();

		CTCollection randomPatchCTCollection = randomPatchCTCollection();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTCollection patchCTCollection =
			ctCollectionResource.patchCTCollectionByExternalReferenceCode(
				postCTCollection.getExternalReferenceCode(),
				randomPatchCTCollection);

		CTCollection expectedPatchCTCollection = postCTCollection.clone();

		BeanTestUtil.copyProperties(
			randomPatchCTCollection, expectedPatchCTCollection);

		CTCollection getCTCollection =
			ctCollectionResource.getCTCollectionByExternalReferenceCode(
				patchCTCollection.getExternalReferenceCode());

		assertEquals(expectedPatchCTCollection, getCTCollection);
		assertValid(getCTCollection);
	}

	protected CTCollection
			testPatchCTCollectionByExternalReferenceCode_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteCTCollection() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTCollection ctCollection = testDeleteCTCollection_addCTCollection();

		assertHttpResponseStatusCode(
			204,
			ctCollectionResource.deleteCTCollectionHttpResponse(
				ctCollection.getId()));

		assertHttpResponseStatusCode(
			404,
			ctCollectionResource.getCTCollectionHttpResponse(
				ctCollection.getId()));

		assertHttpResponseStatusCode(
			404, ctCollectionResource.getCTCollectionHttpResponse(0L));
	}

	protected CTCollection testDeleteCTCollection_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteCTCollection() throws Exception {
		CTCollection ctCollection =
			testGraphQLDeleteCTCollection_addCTCollection();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteCTCollection",
						new HashMap<String, Object>() {
							{
								put("ctCollectionId", ctCollection.getId());
							}
						})),
				"JSONObject/data", "Object/deleteCTCollection"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"cTCollection",
					new HashMap<String, Object>() {
						{
							put("ctCollectionId", ctCollection.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected CTCollection testGraphQLDeleteCTCollection_addCTCollection()
		throws Exception {

		return testGraphQLCTCollection_addCTCollection();
	}

	@Test
	public void testGetCTCollection() throws Exception {
		CTCollection postCTCollection = testGetCTCollection_addCTCollection();

		CTCollection getCTCollection = ctCollectionResource.getCTCollection(
			postCTCollection.getId());

		assertEquals(postCTCollection, getCTCollection);
		assertValid(getCTCollection);
	}

	protected CTCollection testGetCTCollection_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCTCollection() throws Exception {
		CTCollection ctCollection =
			testGraphQLGetCTCollection_addCTCollection();

		Assert.assertTrue(
			equals(
				ctCollection,
				CTCollectionSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"cTCollection",
								new HashMap<String, Object>() {
									{
										put(
											"ctCollectionId",
											ctCollection.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/cTCollection"))));
	}

	@Test
	public void testGraphQLGetCTCollectionNotFound() throws Exception {
		Long irrelevantCtCollectionId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"cTCollection",
						new HashMap<String, Object>() {
							{
								put("ctCollectionId", irrelevantCtCollectionId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected CTCollection testGraphQLGetCTCollection_addCTCollection()
		throws Exception {

		return testGraphQLCTCollection_addCTCollection();
	}

	@Test
	public void testPatchCTCollection() throws Exception {
		CTCollection postCTCollection = testPatchCTCollection_addCTCollection();

		CTCollection randomPatchCTCollection = randomPatchCTCollection();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTCollection patchCTCollection = ctCollectionResource.patchCTCollection(
			postCTCollection.getId(), randomPatchCTCollection);

		CTCollection expectedPatchCTCollection = postCTCollection.clone();

		BeanTestUtil.copyProperties(
			randomPatchCTCollection, expectedPatchCTCollection);

		CTCollection getCTCollection = ctCollectionResource.getCTCollection(
			patchCTCollection.getId());

		assertEquals(expectedPatchCTCollection, getCTCollection);
		assertValid(getCTCollection);
	}

	protected CTCollection testPatchCTCollection_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutCTCollection() throws Exception {
		CTCollection postCTCollection = testPutCTCollection_addCTCollection();

		CTCollection randomCTCollection = randomCTCollection();

		CTCollection putCTCollection = ctCollectionResource.putCTCollection(
			postCTCollection.getId(), randomCTCollection);

		assertEquals(randomCTCollection, putCTCollection);
		assertValid(putCTCollection);

		CTCollection getCTCollection = ctCollectionResource.getCTCollection(
			putCTCollection.getId());

		assertEquals(randomCTCollection, getCTCollection);
		assertValid(getCTCollection);
	}

	protected CTCollection testPutCTCollection_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostCTCollectionCheckout() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTCollection ctCollection =
			testPostCTCollectionCheckout_addCTCollection();

		assertHttpResponseStatusCode(
			204,
			ctCollectionResource.postCTCollectionCheckoutHttpResponse(
				ctCollection.getId()));

		assertHttpResponseStatusCode(
			404, ctCollectionResource.postCTCollectionCheckoutHttpResponse(0L));
	}

	protected CTCollection testPostCTCollectionCheckout_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostCTCollectionPublish() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTCollection ctCollection =
			testPostCTCollectionPublish_addCTCollection();

		assertHttpResponseStatusCode(
			204,
			ctCollectionResource.postCTCollectionPublishHttpResponse(
				ctCollection.getId()));

		assertHttpResponseStatusCode(
			404, ctCollectionResource.postCTCollectionPublishHttpResponse(0L));
	}

	protected CTCollection testPostCTCollectionPublish_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostCTCollectionSchedulePublish() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTCollection ctCollection =
			testPostCTCollectionSchedulePublish_addCTCollection();

		assertHttpResponseStatusCode(
			204,
			ctCollectionResource.postCTCollectionSchedulePublishHttpResponse(
				ctCollection.getId(), null));

		assertHttpResponseStatusCode(
			404,
			ctCollectionResource.postCTCollectionSchedulePublishHttpResponse(
				0L, null));
	}

	protected CTCollection testPostCTCollectionSchedulePublish_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected CTCollection testGraphQLCTCollection_addCTCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		CTCollection ctCollection, List<CTCollection> ctCollections) {

		boolean contains = false;

		for (CTCollection item : ctCollections) {
			if (equals(ctCollection, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			ctCollections + " does not contain " + ctCollection, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		CTCollection ctCollection1, CTCollection ctCollection2) {

		Assert.assertTrue(
			ctCollection1 + " does not equal " + ctCollection2,
			equals(ctCollection1, ctCollection2));
	}

	protected void assertEquals(
		List<CTCollection> ctCollections1, List<CTCollection> ctCollections2) {

		Assert.assertEquals(ctCollections1.size(), ctCollections2.size());

		for (int i = 0; i < ctCollections1.size(); i++) {
			CTCollection ctCollection1 = ctCollections1.get(i);
			CTCollection ctCollection2 = ctCollections2.get(i);

			assertEquals(ctCollection1, ctCollection2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<CTCollection> ctCollections1, List<CTCollection> ctCollections2) {

		Assert.assertEquals(ctCollections1.size(), ctCollections2.size());

		for (CTCollection ctCollection1 : ctCollections1) {
			boolean contains = false;

			for (CTCollection ctCollection2 : ctCollections2) {
				if (equals(ctCollection1, ctCollection2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				ctCollections2 + " does not contain " + ctCollection1,
				contains);
		}
	}

	protected void assertValid(CTCollection ctCollection) throws Exception {
		boolean valid = true;

		if (ctCollection.getDateCreated() == null) {
			valid = false;
		}

		if (ctCollection.getDateModified() == null) {
			valid = false;
		}

		if (ctCollection.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (ctCollection.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateScheduled", additionalAssertFieldName)) {
				if (ctCollection.getDateScheduled() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (ctCollection.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (ctCollection.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (ctCollection.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ownerName", additionalAssertFieldName)) {
				if (ctCollection.getOwnerName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (ctCollection.getStatus() == null) {
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

	protected void assertValid(Page<CTCollection> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<CTCollection> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<CTCollection> ctCollections = page.getItems();

		int size = ctCollections.size();

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
					com.liferay.change.tracking.rest.dto.v1_0.CTCollection.
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
		CTCollection ctCollection1, CTCollection ctCollection2) {

		if (ctCollection1 == ctCollection2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)ctCollection1.getActions(),
						(Map)ctCollection2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctCollection1.getDateCreated(),
						ctCollection2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctCollection1.getDateModified(),
						ctCollection2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateScheduled", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctCollection1.getDateScheduled(),
						ctCollection2.getDateScheduled())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctCollection1.getDescription(),
						ctCollection2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						ctCollection1.getExternalReferenceCode(),
						ctCollection2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctCollection1.getId(), ctCollection2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctCollection1.getName(), ctCollection2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("ownerName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctCollection1.getOwnerName(),
						ctCollection2.getOwnerName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctCollection1.getStatus(), ctCollection2.getStatus())) {

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

		if (!(_ctCollectionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_ctCollectionResource;

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
		EntityField entityField, String operator, CTCollection ctCollection) {

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

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							ctCollection.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							ctCollection.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(ctCollection.getDateCreated()));
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
							ctCollection.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							ctCollection.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(ctCollection.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateScheduled")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							ctCollection.getDateScheduled(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							ctCollection.getDateScheduled(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(ctCollection.getDateScheduled()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			Object object = ctCollection.getDescription();

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

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = ctCollection.getExternalReferenceCode();

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

		if (entityFieldName.equals("name")) {
			Object object = ctCollection.getName();

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

		if (entityFieldName.equals("ownerName")) {
			Object object = ctCollection.getOwnerName();

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

		if (entityFieldName.equals("status")) {
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

	protected CTCollection randomCTCollection() throws Exception {
		return new CTCollection() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				dateScheduled = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				ownerName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected CTCollection randomIrrelevantCTCollection() throws Exception {
		CTCollection randomIrrelevantCTCollection = randomCTCollection();

		return randomIrrelevantCTCollection;
	}

	protected CTCollection randomPatchCTCollection() throws Exception {
		return randomCTCollection();
	}

	protected CTCollectionResource ctCollectionResource;
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
		LogFactoryUtil.getLog(BaseCTCollectionResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.change.tracking.rest.resource.v1_0.CTCollectionResource
		_ctCollectionResource;

}