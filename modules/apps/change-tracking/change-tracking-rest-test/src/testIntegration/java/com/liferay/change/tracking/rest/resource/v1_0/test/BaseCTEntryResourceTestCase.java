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

import com.liferay.change.tracking.rest.client.dto.v1_0.CTEntry;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.CTEntryResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.CTEntrySerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.search.test.util.SearchTestRule;
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
public abstract class BaseCTEntryResourceTestCase {

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

		_ctEntryResource.setContextCompany(testCompany);

		CTEntryResource.Builder builder = CTEntryResource.builder();

		ctEntryResource = builder.authentication(
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

		CTEntry ctEntry1 = randomCTEntry();

		String json = objectMapper.writeValueAsString(ctEntry1);

		CTEntry ctEntry2 = CTEntrySerDes.toDTO(json);

		Assert.assertTrue(equals(ctEntry1, ctEntry2));
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

		CTEntry ctEntry = randomCTEntry();

		String json1 = objectMapper.writeValueAsString(ctEntry);
		String json2 = CTEntrySerDes.toJSON(ctEntry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		CTEntry ctEntry = randomCTEntry();

		ctEntry.setOwnerName(regex);
		ctEntry.setSiteName(regex);
		ctEntry.setTitle(regex);
		ctEntry.setTypeName(regex);

		String json = CTEntrySerDes.toJSON(ctEntry);

		Assert.assertFalse(json.contains(regex));

		ctEntry = CTEntrySerDes.toDTO(json);

		Assert.assertEquals(regex, ctEntry.getOwnerName());
		Assert.assertEquals(regex, ctEntry.getSiteName());
		Assert.assertEquals(regex, ctEntry.getTitle());
		Assert.assertEquals(regex, ctEntry.getTypeName());
	}

	@Test
	public void testGetCtCollectionCTEntriesPage() throws Exception {
		Long ctCollectionId =
			testGetCtCollectionCTEntriesPage_getCtCollectionId();
		Long irrelevantCtCollectionId =
			testGetCtCollectionCTEntriesPage_getIrrelevantCtCollectionId();

		Page<CTEntry> page = ctEntryResource.getCtCollectionCTEntriesPage(
			ctCollectionId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantCtCollectionId != null) {
			CTEntry irrelevantCTEntry =
				testGetCtCollectionCTEntriesPage_addCTEntry(
					irrelevantCtCollectionId, randomIrrelevantCTEntry());

			page = ctEntryResource.getCtCollectionCTEntriesPage(
				irrelevantCtCollectionId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantCTEntry),
				(List<CTEntry>)page.getItems());
			assertValid(
				page,
				testGetCtCollectionCTEntriesPage_getExpectedActions(
					irrelevantCtCollectionId));
		}

		CTEntry ctEntry1 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, randomCTEntry());

		CTEntry ctEntry2 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, randomCTEntry());

		page = ctEntryResource.getCtCollectionCTEntriesPage(
			ctCollectionId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(ctEntry1, ctEntry2), (List<CTEntry>)page.getItems());
		assertValid(
			page,
			testGetCtCollectionCTEntriesPage_getExpectedActions(
				ctCollectionId));
	}

	protected Map<String, Map<String, String>>
			testGetCtCollectionCTEntriesPage_getExpectedActions(
				Long ctCollectionId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long ctCollectionId =
			testGetCtCollectionCTEntriesPage_getCtCollectionId();

		CTEntry ctEntry1 = randomCTEntry();

		ctEntry1 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, ctEntry1);

		for (EntityField entityField : entityFields) {
			Page<CTEntry> page = ctEntryResource.getCtCollectionCTEntriesPage(
				ctCollectionId, null,
				getFilterString(entityField, "between", ctEntry1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(ctEntry1),
				(List<CTEntry>)page.getItems());
		}
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithFilterDoubleEquals()
		throws Exception {

		testGetCtCollectionCTEntriesPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithFilterStringContains()
		throws Exception {

		testGetCtCollectionCTEntriesPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithFilterStringEquals()
		throws Exception {

		testGetCtCollectionCTEntriesPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithFilterStringStartsWith()
		throws Exception {

		testGetCtCollectionCTEntriesPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetCtCollectionCTEntriesPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long ctCollectionId =
			testGetCtCollectionCTEntriesPage_getCtCollectionId();

		CTEntry ctEntry1 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, randomCTEntry());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTEntry ctEntry2 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, randomCTEntry());

		for (EntityField entityField : entityFields) {
			Page<CTEntry> page = ctEntryResource.getCtCollectionCTEntriesPage(
				ctCollectionId, null,
				getFilterString(entityField, operator, ctEntry1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(ctEntry1),
				(List<CTEntry>)page.getItems());
		}
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithPagination()
		throws Exception {

		Long ctCollectionId =
			testGetCtCollectionCTEntriesPage_getCtCollectionId();

		CTEntry ctEntry1 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, randomCTEntry());

		CTEntry ctEntry2 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, randomCTEntry());

		CTEntry ctEntry3 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, randomCTEntry());

		Page<CTEntry> page1 = ctEntryResource.getCtCollectionCTEntriesPage(
			ctCollectionId, null, null, Pagination.of(1, 2), null);

		List<CTEntry> ctEntries1 = (List<CTEntry>)page1.getItems();

		Assert.assertEquals(ctEntries1.toString(), 2, ctEntries1.size());

		Page<CTEntry> page2 = ctEntryResource.getCtCollectionCTEntriesPage(
			ctCollectionId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<CTEntry> ctEntries2 = (List<CTEntry>)page2.getItems();

		Assert.assertEquals(ctEntries2.toString(), 1, ctEntries2.size());

		Page<CTEntry> page3 = ctEntryResource.getCtCollectionCTEntriesPage(
			ctCollectionId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(ctEntry1, ctEntry2, ctEntry3),
			(List<CTEntry>)page3.getItems());
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithSortDateTime()
		throws Exception {

		testGetCtCollectionCTEntriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, ctEntry1, ctEntry2) -> {
				BeanTestUtil.setProperty(
					ctEntry1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithSortDouble()
		throws Exception {

		testGetCtCollectionCTEntriesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, ctEntry1, ctEntry2) -> {
				BeanTestUtil.setProperty(ctEntry1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(ctEntry2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithSortInteger()
		throws Exception {

		testGetCtCollectionCTEntriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, ctEntry1, ctEntry2) -> {
				BeanTestUtil.setProperty(ctEntry1, entityField.getName(), 0);
				BeanTestUtil.setProperty(ctEntry2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetCtCollectionCTEntriesPageWithSortString()
		throws Exception {

		testGetCtCollectionCTEntriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, ctEntry1, ctEntry2) -> {
				Class<?> clazz = ctEntry1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						ctEntry1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						ctEntry2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						ctEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						ctEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						ctEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						ctEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetCtCollectionCTEntriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, CTEntry, CTEntry, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long ctCollectionId =
			testGetCtCollectionCTEntriesPage_getCtCollectionId();

		CTEntry ctEntry1 = randomCTEntry();
		CTEntry ctEntry2 = randomCTEntry();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, ctEntry1, ctEntry2);
		}

		ctEntry1 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, ctEntry1);

		ctEntry2 = testGetCtCollectionCTEntriesPage_addCTEntry(
			ctCollectionId, ctEntry2);

		for (EntityField entityField : entityFields) {
			Page<CTEntry> ascPage =
				ctEntryResource.getCtCollectionCTEntriesPage(
					ctCollectionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(ctEntry1, ctEntry2),
				(List<CTEntry>)ascPage.getItems());

			Page<CTEntry> descPage =
				ctEntryResource.getCtCollectionCTEntriesPage(
					ctCollectionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(ctEntry2, ctEntry1),
				(List<CTEntry>)descPage.getItems());
		}
	}

	protected CTEntry testGetCtCollectionCTEntriesPage_addCTEntry(
			Long ctCollectionId, CTEntry ctEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCtCollectionCTEntriesPage_getCtCollectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetCtCollectionCTEntriesPage_getIrrelevantCtCollectionId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetCTEntry() throws Exception {
		CTEntry postCTEntry = testGetCTEntry_addCTEntry();

		CTEntry getCTEntry = ctEntryResource.getCTEntry(postCTEntry.getId());

		assertEquals(postCTEntry, getCTEntry);
		assertValid(getCTEntry);
	}

	protected CTEntry testGetCTEntry_addCTEntry() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCTEntry() throws Exception {
		CTEntry ctEntry = testGraphQLGetCTEntry_addCTEntry();

		Assert.assertTrue(
			equals(
				ctEntry,
				CTEntrySerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"cTEntry",
								new HashMap<String, Object>() {
									{
										put("ctEntryId", ctEntry.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/cTEntry"))));
	}

	@Test
	public void testGraphQLGetCTEntryNotFound() throws Exception {
		Long irrelevantCtEntryId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"cTEntry",
						new HashMap<String, Object>() {
							{
								put("ctEntryId", irrelevantCtEntryId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected CTEntry testGraphQLGetCTEntry_addCTEntry() throws Exception {
		return testGraphQLCTEntry_addCTEntry();
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected CTEntry testGraphQLCTEntry_addCTEntry() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(CTEntry ctEntry, List<CTEntry> ctEntries) {
		boolean contains = false;

		for (CTEntry item : ctEntries) {
			if (equals(ctEntry, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(ctEntries + " does not contain " + ctEntry, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(CTEntry ctEntry1, CTEntry ctEntry2) {
		Assert.assertTrue(
			ctEntry1 + " does not equal " + ctEntry2,
			equals(ctEntry1, ctEntry2));
	}

	protected void assertEquals(
		List<CTEntry> ctEntries1, List<CTEntry> ctEntries2) {

		Assert.assertEquals(ctEntries1.size(), ctEntries2.size());

		for (int i = 0; i < ctEntries1.size(); i++) {
			CTEntry ctEntry1 = ctEntries1.get(i);
			CTEntry ctEntry2 = ctEntries2.get(i);

			assertEquals(ctEntry1, ctEntry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<CTEntry> ctEntries1, List<CTEntry> ctEntries2) {

		Assert.assertEquals(ctEntries1.size(), ctEntries2.size());

		for (CTEntry ctEntry1 : ctEntries1) {
			boolean contains = false;

			for (CTEntry ctEntry2 : ctEntries2) {
				if (equals(ctEntry1, ctEntry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				ctEntries2 + " does not contain " + ctEntry1, contains);
		}
	}

	protected void assertValid(CTEntry ctEntry) throws Exception {
		boolean valid = true;

		if (ctEntry.getDateCreated() == null) {
			valid = false;
		}

		if (ctEntry.getDateModified() == null) {
			valid = false;
		}

		if (ctEntry.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (ctEntry.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("changeType", additionalAssertFieldName)) {
				if (ctEntry.getChangeType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ctCollectionId", additionalAssertFieldName)) {
				if (ctEntry.getCtCollectionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("hideable", additionalAssertFieldName)) {
				if (ctEntry.getHideable() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modelClassNameId", additionalAssertFieldName)) {
				if (ctEntry.getModelClassNameId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modelClassPK", additionalAssertFieldName)) {
				if (ctEntry.getModelClassPK() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ownerName", additionalAssertFieldName)) {
				if (ctEntry.getOwnerName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("siteName", additionalAssertFieldName)) {
				if (ctEntry.getSiteName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (ctEntry.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (ctEntry.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("typeName", additionalAssertFieldName)) {
				if (ctEntry.getTypeName() == null) {
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

	protected void assertValid(Page<CTEntry> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<CTEntry> page, Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<CTEntry> ctEntries = page.getItems();

		int size = ctEntries.size();

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
					com.liferay.change.tracking.rest.dto.v1_0.CTEntry.class)) {

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

	protected boolean equals(CTEntry ctEntry1, CTEntry ctEntry2) {
		if (ctEntry1 == ctEntry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)ctEntry1.getActions(),
						(Map)ctEntry2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("changeType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getChangeType(), ctEntry2.getChangeType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("ctCollectionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getCtCollectionId(),
						ctEntry2.getCtCollectionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getDateCreated(), ctEntry2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getDateModified(),
						ctEntry2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("hideable", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getHideable(), ctEntry2.getHideable())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(ctEntry1.getId(), ctEntry2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("modelClassNameId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getModelClassNameId(),
						ctEntry2.getModelClassNameId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modelClassPK", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getModelClassPK(),
						ctEntry2.getModelClassPK())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("ownerName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getOwnerName(), ctEntry2.getOwnerName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("siteName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getSiteName(), ctEntry2.getSiteName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getStatus(), ctEntry2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getTitle(), ctEntry2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("typeName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctEntry1.getTypeName(), ctEntry2.getTypeName())) {

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

		if (!(_ctEntryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_ctEntryResource;

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
		EntityField entityField, String operator, CTEntry ctEntry) {

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

		if (entityFieldName.equals("changeType")) {
			sb.append(String.valueOf(ctEntry.getChangeType()));

			return sb.toString();
		}

		if (entityFieldName.equals("ctCollectionId")) {
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
						DateUtils.addSeconds(ctEntry.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(ctEntry.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(ctEntry.getDateCreated()));
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
						DateUtils.addSeconds(ctEntry.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(ctEntry.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(ctEntry.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("hideable")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("modelClassNameId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("modelClassPK")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("ownerName")) {
			Object object = ctEntry.getOwnerName();

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

		if (entityFieldName.equals("siteName")) {
			Object object = ctEntry.getSiteName();

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

		if (entityFieldName.equals("title")) {
			Object object = ctEntry.getTitle();

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

		if (entityFieldName.equals("typeName")) {
			Object object = ctEntry.getTypeName();

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

	protected CTEntry randomCTEntry() throws Exception {
		return new CTEntry() {
			{
				changeType = RandomTestUtil.randomInt();
				ctCollectionId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				hideable = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				modelClassNameId = RandomTestUtil.randomLong();
				modelClassPK = RandomTestUtil.randomLong();
				ownerName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				siteName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				typeName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected CTEntry randomIrrelevantCTEntry() throws Exception {
		CTEntry randomIrrelevantCTEntry = randomCTEntry();

		return randomIrrelevantCTEntry;
	}

	protected CTEntry randomPatchCTEntry() throws Exception {
		return randomCTEntry();
	}

	protected CTEntryResource ctEntryResource;
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
		LogFactoryUtil.getLog(BaseCTEntryResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.change.tracking.rest.resource.v1_0.CTEntryResource
		_ctEntryResource;

}