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

import com.liferay.change.tracking.rest.client.dto.v1_0.CTProcess;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.CTProcessResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.CTProcessSerDes;
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
import com.liferay.portal.kernel.util.GetterUtil;
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
public abstract class BaseCTProcessResourceTestCase {

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

		_ctProcessResource.setContextCompany(testCompany);

		CTProcessResource.Builder builder = CTProcessResource.builder();

		ctProcessResource = builder.authentication(
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

		CTProcess ctProcess1 = randomCTProcess();

		String json = objectMapper.writeValueAsString(ctProcess1);

		CTProcess ctProcess2 = CTProcessSerDes.toDTO(json);

		Assert.assertTrue(equals(ctProcess1, ctProcess2));
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

		CTProcess ctProcess = randomCTProcess();

		String json1 = objectMapper.writeValueAsString(ctProcess);
		String json2 = CTProcessSerDes.toJSON(ctProcess);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		CTProcess ctProcess = randomCTProcess();

		ctProcess.setDescription(regex);
		ctProcess.setName(regex);
		ctProcess.setOwnerName(regex);

		String json = CTProcessSerDes.toJSON(ctProcess);

		Assert.assertFalse(json.contains(regex));

		ctProcess = CTProcessSerDes.toDTO(json);

		Assert.assertEquals(regex, ctProcess.getDescription());
		Assert.assertEquals(regex, ctProcess.getName());
		Assert.assertEquals(regex, ctProcess.getOwnerName());
	}

	@Test
	public void testGetCTProcessesPage() throws Exception {
		Page<CTProcess> page = ctProcessResource.getCTProcessesPage(
			null, null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		CTProcess ctProcess1 = testGetCTProcessesPage_addCTProcess(
			randomCTProcess());

		CTProcess ctProcess2 = testGetCTProcessesPage_addCTProcess(
			randomCTProcess());

		page = ctProcessResource.getCTProcessesPage(
			null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(ctProcess1, (List<CTProcess>)page.getItems());
		assertContains(ctProcess2, (List<CTProcess>)page.getItems());
		assertValid(page, testGetCTProcessesPage_getExpectedActions());
	}

	protected Map<String, Map<String, String>>
			testGetCTProcessesPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetCTProcessesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		CTProcess ctProcess1 = randomCTProcess();

		ctProcess1 = testGetCTProcessesPage_addCTProcess(ctProcess1);

		for (EntityField entityField : entityFields) {
			Page<CTProcess> page = ctProcessResource.getCTProcessesPage(
				null, null, getFilterString(entityField, "between", ctProcess1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(ctProcess1),
				(List<CTProcess>)page.getItems());
		}
	}

	@Test
	public void testGetCTProcessesPageWithFilterDoubleEquals()
		throws Exception {

		testGetCTProcessesPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetCTProcessesPageWithFilterStringContains()
		throws Exception {

		testGetCTProcessesPageWithFilter("contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetCTProcessesPageWithFilterStringEquals()
		throws Exception {

		testGetCTProcessesPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetCTProcessesPageWithFilterStringStartsWith()
		throws Exception {

		testGetCTProcessesPageWithFilter("startswith", EntityField.Type.STRING);
	}

	protected void testGetCTProcessesPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		CTProcess ctProcess1 = testGetCTProcessesPage_addCTProcess(
			randomCTProcess());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTProcess ctProcess2 = testGetCTProcessesPage_addCTProcess(
			randomCTProcess());

		for (EntityField entityField : entityFields) {
			Page<CTProcess> page = ctProcessResource.getCTProcessesPage(
				null, null, getFilterString(entityField, operator, ctProcess1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(ctProcess1),
				(List<CTProcess>)page.getItems());
		}
	}

	@Test
	public void testGetCTProcessesPageWithPagination() throws Exception {
		Page<CTProcess> totalPage = ctProcessResource.getCTProcessesPage(
			null, null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		CTProcess ctProcess1 = testGetCTProcessesPage_addCTProcess(
			randomCTProcess());

		CTProcess ctProcess2 = testGetCTProcessesPage_addCTProcess(
			randomCTProcess());

		CTProcess ctProcess3 = testGetCTProcessesPage_addCTProcess(
			randomCTProcess());

		Page<CTProcess> page1 = ctProcessResource.getCTProcessesPage(
			null, null, null, Pagination.of(1, totalCount + 2), null);

		List<CTProcess> ctProcesses1 = (List<CTProcess>)page1.getItems();

		Assert.assertEquals(
			ctProcesses1.toString(), totalCount + 2, ctProcesses1.size());

		Page<CTProcess> page2 = ctProcessResource.getCTProcessesPage(
			null, null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<CTProcess> ctProcesses2 = (List<CTProcess>)page2.getItems();

		Assert.assertEquals(ctProcesses2.toString(), 1, ctProcesses2.size());

		Page<CTProcess> page3 = ctProcessResource.getCTProcessesPage(
			null, null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(ctProcess1, (List<CTProcess>)page3.getItems());
		assertContains(ctProcess2, (List<CTProcess>)page3.getItems());
		assertContains(ctProcess3, (List<CTProcess>)page3.getItems());
	}

	@Test
	public void testGetCTProcessesPageWithSortDateTime() throws Exception {
		testGetCTProcessesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, ctProcess1, ctProcess2) -> {
				BeanTestUtil.setProperty(
					ctProcess1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetCTProcessesPageWithSortDouble() throws Exception {
		testGetCTProcessesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, ctProcess1, ctProcess2) -> {
				BeanTestUtil.setProperty(
					ctProcess1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					ctProcess2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetCTProcessesPageWithSortInteger() throws Exception {
		testGetCTProcessesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, ctProcess1, ctProcess2) -> {
				BeanTestUtil.setProperty(ctProcess1, entityField.getName(), 0);
				BeanTestUtil.setProperty(ctProcess2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetCTProcessesPageWithSortString() throws Exception {
		testGetCTProcessesPageWithSort(
			EntityField.Type.STRING,
			(entityField, ctProcess1, ctProcess2) -> {
				Class<?> clazz = ctProcess1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						ctProcess1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						ctProcess2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						ctProcess1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						ctProcess2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						ctProcess1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						ctProcess2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetCTProcessesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, CTProcess, CTProcess, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		CTProcess ctProcess1 = randomCTProcess();
		CTProcess ctProcess2 = randomCTProcess();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, ctProcess1, ctProcess2);
		}

		ctProcess1 = testGetCTProcessesPage_addCTProcess(ctProcess1);

		ctProcess2 = testGetCTProcessesPage_addCTProcess(ctProcess2);

		for (EntityField entityField : entityFields) {
			Page<CTProcess> ascPage = ctProcessResource.getCTProcessesPage(
				null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(ctProcess1, ctProcess2),
				(List<CTProcess>)ascPage.getItems());

			Page<CTProcess> descPage = ctProcessResource.getCTProcessesPage(
				null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(ctProcess2, ctProcess1),
				(List<CTProcess>)descPage.getItems());
		}
	}

	protected CTProcess testGetCTProcessesPage_addCTProcess(CTProcess ctProcess)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetCTProcess() throws Exception {
		CTProcess postCTProcess = testGetCTProcess_addCTProcess();

		CTProcess getCTProcess = ctProcessResource.getCTProcess(
			postCTProcess.getId());

		assertEquals(postCTProcess, getCTProcess);
		assertValid(getCTProcess);
	}

	protected CTProcess testGetCTProcess_addCTProcess() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCTProcess() throws Exception {
		CTProcess ctProcess = testGraphQLGetCTProcess_addCTProcess();

		Assert.assertTrue(
			equals(
				ctProcess,
				CTProcessSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"cTProcess",
								new HashMap<String, Object>() {
									{
										put("ctProcessId", ctProcess.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/cTProcess"))));
	}

	@Test
	public void testGraphQLGetCTProcessNotFound() throws Exception {
		Long irrelevantCtProcessId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"cTProcess",
						new HashMap<String, Object>() {
							{
								put("ctProcessId", irrelevantCtProcessId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected CTProcess testGraphQLGetCTProcess_addCTProcess()
		throws Exception {

		return testGraphQLCTProcess_addCTProcess();
	}

	@Test
	public void testPostCTProcessRevert() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		CTProcess ctProcess = testPostCTProcessRevert_addCTProcess();

		assertHttpResponseStatusCode(
			204,
			ctProcessResource.postCTProcessRevertHttpResponse(
				ctProcess.getId(), null, null));

		assertHttpResponseStatusCode(
			404,
			ctProcessResource.postCTProcessRevertHttpResponse(0L, null, null));
	}

	protected CTProcess testPostCTProcessRevert_addCTProcess()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected CTProcess testGraphQLCTProcess_addCTProcess() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		CTProcess ctProcess, List<CTProcess> ctProcesses) {

		boolean contains = false;

		for (CTProcess item : ctProcesses) {
			if (equals(ctProcess, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			ctProcesses + " does not contain " + ctProcess, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(CTProcess ctProcess1, CTProcess ctProcess2) {
		Assert.assertTrue(
			ctProcess1 + " does not equal " + ctProcess2,
			equals(ctProcess1, ctProcess2));
	}

	protected void assertEquals(
		List<CTProcess> ctProcesses1, List<CTProcess> ctProcesses2) {

		Assert.assertEquals(ctProcesses1.size(), ctProcesses2.size());

		for (int i = 0; i < ctProcesses1.size(); i++) {
			CTProcess ctProcess1 = ctProcesses1.get(i);
			CTProcess ctProcess2 = ctProcesses2.get(i);

			assertEquals(ctProcess1, ctProcess2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<CTProcess> ctProcesses1, List<CTProcess> ctProcesses2) {

		Assert.assertEquals(ctProcesses1.size(), ctProcesses2.size());

		for (CTProcess ctProcess1 : ctProcesses1) {
			boolean contains = false;

			for (CTProcess ctProcess2 : ctProcesses2) {
				if (equals(ctProcess1, ctProcess2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				ctProcesses2 + " does not contain " + ctProcess1, contains);
		}
	}

	protected void assertValid(CTProcess ctProcess) throws Exception {
		boolean valid = true;

		if (ctProcess.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (ctProcess.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ctCollectionId", additionalAssertFieldName)) {
				if (ctProcess.getCtCollectionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (ctProcess.getDatePublished() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (ctProcess.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (ctProcess.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ownerName", additionalAssertFieldName)) {
				if (ctProcess.getOwnerName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (ctProcess.getStatus() == null) {
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

	protected void assertValid(Page<CTProcess> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<CTProcess> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<CTProcess> ctProcesses = page.getItems();

		int size = ctProcesses.size();

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
					com.liferay.change.tracking.rest.dto.v1_0.CTProcess.
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

	protected boolean equals(CTProcess ctProcess1, CTProcess ctProcess2) {
		if (ctProcess1 == ctProcess2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)ctProcess1.getActions(),
						(Map)ctProcess2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("ctCollectionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctProcess1.getCtCollectionId(),
						ctProcess2.getCtCollectionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctProcess1.getDatePublished(),
						ctProcess2.getDatePublished())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctProcess1.getDescription(),
						ctProcess2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctProcess1.getId(), ctProcess2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctProcess1.getName(), ctProcess2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("ownerName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctProcess1.getOwnerName(), ctProcess2.getOwnerName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						ctProcess1.getStatus(), ctProcess2.getStatus())) {

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

		if (!(_ctProcessResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_ctProcessResource;

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
		EntityField entityField, String operator, CTProcess ctProcess) {

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

		if (entityFieldName.equals("ctCollectionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("datePublished")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							ctProcess.getDatePublished(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(ctProcess.getDatePublished(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(ctProcess.getDatePublished()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			Object object = ctProcess.getDescription();

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
			Object object = ctProcess.getName();

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
			Object object = ctProcess.getOwnerName();

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

	protected CTProcess randomCTProcess() throws Exception {
		return new CTProcess() {
			{
				ctCollectionId = RandomTestUtil.randomLong();
				datePublished = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				ownerName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected CTProcess randomIrrelevantCTProcess() throws Exception {
		CTProcess randomIrrelevantCTProcess = randomCTProcess();

		return randomIrrelevantCTProcess;
	}

	protected CTProcess randomPatchCTProcess() throws Exception {
		return randomCTProcess();
	}

	protected CTProcessResource ctProcessResource;
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
		LogFactoryUtil.getLog(BaseCTProcessResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.change.tracking.rest.resource.v1_0.CTProcessResource
		_ctProcessResource;

}