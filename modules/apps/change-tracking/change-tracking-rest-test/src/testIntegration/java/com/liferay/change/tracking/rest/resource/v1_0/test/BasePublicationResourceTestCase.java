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

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.change.tracking.rest.client.dto.v1_0.Publication;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.PublicationResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.PublicationSerDes;
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
public abstract class BasePublicationResourceTestCase {

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

		_publicationResource.setContextCompany(testCompany);

		PublicationResource.Builder builder = PublicationResource.builder();

		publicationResource = builder.authentication(
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

		Publication publication1 = randomPublication();

		String json = objectMapper.writeValueAsString(publication1);

		Publication publication2 = PublicationSerDes.toDTO(json);

		Assert.assertTrue(equals(publication1, publication2));
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

		Publication publication = randomPublication();

		String json1 = objectMapper.writeValueAsString(publication);
		String json2 = PublicationSerDes.toJSON(publication);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Publication publication = randomPublication();

		publication.setDescription(regex);
		publication.setName(regex);
		publication.setOwnerName(regex);

		String json = PublicationSerDes.toJSON(publication);

		Assert.assertFalse(json.contains(regex));

		publication = PublicationSerDes.toDTO(json);

		Assert.assertEquals(regex, publication.getDescription());
		Assert.assertEquals(regex, publication.getName());
		Assert.assertEquals(regex, publication.getOwnerName());
	}

	@Test
	public void testGetPublicationsPage() throws Exception {
		Page<Publication> page = publicationResource.getPublicationsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Publication publication1 = testGetPublicationsPage_addPublication(
			randomPublication());

		Publication publication2 = testGetPublicationsPage_addPublication(
			randomPublication());

		page = publicationResource.getPublicationsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(publication1, (List<Publication>)page.getItems());
		assertContains(publication2, (List<Publication>)page.getItems());
		assertValid(page, testGetPublicationsPage_getExpectedActions());

		publicationResource.deletePublication(publication1.getId());

		publicationResource.deletePublication(publication2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetPublicationsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetPublicationsPageWithPagination() throws Exception {
		Page<Publication> totalPage = publicationResource.getPublicationsPage(
			null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Publication publication1 = testGetPublicationsPage_addPublication(
			randomPublication());

		Publication publication2 = testGetPublicationsPage_addPublication(
			randomPublication());

		Publication publication3 = testGetPublicationsPage_addPublication(
			randomPublication());

		Page<Publication> page1 = publicationResource.getPublicationsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<Publication> publications1 = (List<Publication>)page1.getItems();

		Assert.assertEquals(
			publications1.toString(), totalCount + 2, publications1.size());

		Page<Publication> page2 = publicationResource.getPublicationsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Publication> publications2 = (List<Publication>)page2.getItems();

		Assert.assertEquals(publications2.toString(), 1, publications2.size());

		Page<Publication> page3 = publicationResource.getPublicationsPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(publication1, (List<Publication>)page3.getItems());
		assertContains(publication2, (List<Publication>)page3.getItems());
		assertContains(publication3, (List<Publication>)page3.getItems());
	}

	@Test
	public void testGetPublicationsPageWithSortDateTime() throws Exception {
		testGetPublicationsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, publication1, publication2) -> {
				BeanTestUtil.setProperty(
					publication1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetPublicationsPageWithSortDouble() throws Exception {
		testGetPublicationsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, publication1, publication2) -> {
				BeanTestUtil.setProperty(
					publication1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					publication2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetPublicationsPageWithSortInteger() throws Exception {
		testGetPublicationsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, publication1, publication2) -> {
				BeanTestUtil.setProperty(
					publication1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					publication2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetPublicationsPageWithSortString() throws Exception {
		testGetPublicationsPageWithSort(
			EntityField.Type.STRING,
			(entityField, publication1, publication2) -> {
				Class<?> clazz = publication1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						publication1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						publication2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						publication1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						publication2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						publication1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						publication2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetPublicationsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Publication, Publication, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Publication publication1 = randomPublication();
		Publication publication2 = randomPublication();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, publication1, publication2);
		}

		publication1 = testGetPublicationsPage_addPublication(publication1);

		publication2 = testGetPublicationsPage_addPublication(publication2);

		for (EntityField entityField : entityFields) {
			Page<Publication> ascPage = publicationResource.getPublicationsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(publication1, publication2),
				(List<Publication>)ascPage.getItems());

			Page<Publication> descPage =
				publicationResource.getPublicationsPage(
					null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(publication2, publication1),
				(List<Publication>)descPage.getItems());
		}
	}

	protected Publication testGetPublicationsPage_addPublication(
			Publication publication)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPublicationsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"publications",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject publicationsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/publications");

		long totalCount = publicationsJSONObject.getLong("totalCount");

		Publication publication1 =
			testGraphQLGetPublicationsPage_addPublication();
		Publication publication2 =
			testGraphQLGetPublicationsPage_addPublication();

		publicationsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/publications");

		Assert.assertEquals(
			totalCount + 2, publicationsJSONObject.getLong("totalCount"));

		assertContains(
			publication1,
			Arrays.asList(
				PublicationSerDes.toDTOs(
					publicationsJSONObject.getString("items"))));
		assertContains(
			publication2,
			Arrays.asList(
				PublicationSerDes.toDTOs(
					publicationsJSONObject.getString("items"))));
	}

	protected Publication testGraphQLGetPublicationsPage_addPublication()
		throws Exception {

		return testGraphQLPublication_addPublication();
	}

	@Test
	public void testPostPublication() throws Exception {
		Publication randomPublication = randomPublication();

		Publication postPublication = testPostPublication_addPublication(
			randomPublication);

		assertEquals(randomPublication, postPublication);
		assertValid(postPublication);
	}

	protected Publication testPostPublication_addPublication(
			Publication publication)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeletePublication() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Publication publication = testDeletePublication_addPublication();

		assertHttpResponseStatusCode(
			204,
			publicationResource.deletePublicationHttpResponse(
				publication.getId()));

		assertHttpResponseStatusCode(
			404,
			publicationResource.getPublicationHttpResponse(
				publication.getId()));

		assertHttpResponseStatusCode(
			404,
			publicationResource.getPublicationHttpResponse(
				publication.getId()));
	}

	protected Publication testDeletePublication_addPublication()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeletePublication() throws Exception {
		Publication publication = testGraphQLDeletePublication_addPublication();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deletePublication",
						new HashMap<String, Object>() {
							{
								put("id", publication.getId());
							}
						})),
				"JSONObject/data", "Object/deletePublication"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"publication",
					new HashMap<String, Object>() {
						{
							put("id", publication.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected Publication testGraphQLDeletePublication_addPublication()
		throws Exception {

		return testGraphQLPublication_addPublication();
	}

	@Test
	public void testGetPublication() throws Exception {
		Publication postPublication = testGetPublication_addPublication();

		Publication getPublication = publicationResource.getPublication(
			postPublication.getId());

		assertEquals(postPublication, getPublication);
		assertValid(getPublication);
	}

	protected Publication testGetPublication_addPublication() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPublication() throws Exception {
		Publication publication = testGraphQLGetPublication_addPublication();

		Assert.assertTrue(
			equals(
				publication,
				PublicationSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"publication",
								new HashMap<String, Object>() {
									{
										put("id", publication.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/publication"))));
	}

	@Test
	public void testGraphQLGetPublicationNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"publication",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Publication testGraphQLGetPublication_addPublication()
		throws Exception {

		return testGraphQLPublication_addPublication();
	}

	@Test
	public void testPatchPublication() throws Exception {
		Publication postPublication = testPatchPublication_addPublication();

		Publication randomPatchPublication = randomPatchPublication();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Publication patchPublication = publicationResource.patchPublication(
			postPublication.getId(), randomPatchPublication);

		Publication expectedPatchPublication = postPublication.clone();

		BeanTestUtil.copyProperties(
			randomPatchPublication, expectedPatchPublication);

		Publication getPublication = publicationResource.getPublication(
			patchPublication.getId());

		assertEquals(expectedPatchPublication, getPublication);
		assertValid(getPublication);
	}

	protected Publication testPatchPublication_addPublication()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutPublication() throws Exception {
		Publication postPublication = testPutPublication_addPublication();

		Publication randomPublication = randomPublication();

		Publication putPublication = publicationResource.putPublication(
			postPublication.getId(), randomPublication);

		assertEquals(randomPublication, putPublication);
		assertValid(putPublication);

		Publication getPublication = publicationResource.getPublication(
			putPublication.getId());

		assertEquals(randomPublication, getPublication);
		assertValid(getPublication);
	}

	protected Publication testPutPublication_addPublication() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostPublicationCheckout() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Publication publication = testPostPublicationCheckout_addPublication();

		assertHttpResponseStatusCode(
			204,
			publicationResource.postPublicationCheckoutHttpResponse(
				publication.getId()));

		assertHttpResponseStatusCode(
			404,
			publicationResource.postPublicationCheckoutHttpResponse(
				publication.getId()));
	}

	protected Publication testPostPublicationCheckout_addPublication()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostPublicationPublish() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Publication publication = testPostPublicationPublish_addPublication();

		assertHttpResponseStatusCode(
			204,
			publicationResource.postPublicationPublishHttpResponse(
				publication.getId()));

		assertHttpResponseStatusCode(
			404,
			publicationResource.postPublicationPublishHttpResponse(
				publication.getId()));
	}

	protected Publication testPostPublicationPublish_addPublication()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostPublicationSchedulePublish() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Publication publication =
			testPostPublicationSchedulePublish_addPublication();

		assertHttpResponseStatusCode(
			204,
			publicationResource.postPublicationSchedulePublishHttpResponse(
				publication.getId(), null));

		assertHttpResponseStatusCode(
			404,
			publicationResource.postPublicationSchedulePublishHttpResponse(
				publication.getId(), null));
	}

	protected Publication testPostPublicationSchedulePublish_addPublication()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Publication testGraphQLPublication_addPublication()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		Publication publication, List<Publication> publications) {

		boolean contains = false;

		for (Publication item : publications) {
			if (equals(publication, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			publications + " does not contain " + publication, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		Publication publication1, Publication publication2) {

		Assert.assertTrue(
			publication1 + " does not equal " + publication2,
			equals(publication1, publication2));
	}

	protected void assertEquals(
		List<Publication> publications1, List<Publication> publications2) {

		Assert.assertEquals(publications1.size(), publications2.size());

		for (int i = 0; i < publications1.size(); i++) {
			Publication publication1 = publications1.get(i);
			Publication publication2 = publications2.get(i);

			assertEquals(publication1, publication2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Publication> publications1, List<Publication> publications2) {

		Assert.assertEquals(publications1.size(), publications2.size());

		for (Publication publication1 : publications1) {
			boolean contains = false;

			for (Publication publication2 : publications2) {
				if (equals(publication1, publication2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				publications2 + " does not contain " + publication1, contains);
		}
	}

	protected void assertValid(Publication publication) throws Exception {
		boolean valid = true;

		if (publication.getDateCreated() == null) {
			valid = false;
		}

		if (publication.getDateModified() == null) {
			valid = false;
		}

		if (publication.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (publication.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateScheduled", additionalAssertFieldName)) {
				if (publication.getDateScheduled() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (publication.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (publication.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ownerName", additionalAssertFieldName)) {
				if (publication.getOwnerName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (publication.getStatus() == null) {
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

	protected void assertValid(Page<Publication> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Publication> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Publication> publications = page.getItems();

		int size = publications.size();

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
					com.liferay.change.tracking.rest.dto.v1_0.Publication.
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
		Publication publication1, Publication publication2) {

		if (publication1 == publication2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)publication1.getActions(),
						(Map)publication2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						publication1.getDateCreated(),
						publication2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						publication1.getDateModified(),
						publication2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateScheduled", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						publication1.getDateScheduled(),
						publication2.getDateScheduled())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						publication1.getDescription(),
						publication2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						publication1.getId(), publication2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						publication1.getName(), publication2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("ownerName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						publication1.getOwnerName(),
						publication2.getOwnerName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						publication1.getStatus(), publication2.getStatus())) {

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

		if (!(_publicationResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_publicationResource;

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
		EntityField entityField, String operator, Publication publication) {

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
							publication.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(publication.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(publication.getDateCreated()));
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
							publication.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							publication.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(publication.getDateModified()));
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
							publication.getDateScheduled(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							publication.getDateScheduled(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(publication.getDateScheduled()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(publication.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(publication.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("ownerName")) {
			sb.append("'");
			sb.append(String.valueOf(publication.getOwnerName()));
			sb.append("'");

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

	protected Publication randomPublication() throws Exception {
		return new Publication() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				dateScheduled = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				ownerName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected Publication randomIrrelevantPublication() throws Exception {
		Publication randomIrrelevantPublication = randomPublication();

		return randomIrrelevantPublication;
	}

	protected Publication randomPatchPublication() throws Exception {
		return randomPublication();
	}

	protected PublicationResource publicationResource;
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
		LogFactoryUtil.getLog(BasePublicationResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.change.tracking.rest.resource.v1_0.PublicationResource
		_publicationResource;

}