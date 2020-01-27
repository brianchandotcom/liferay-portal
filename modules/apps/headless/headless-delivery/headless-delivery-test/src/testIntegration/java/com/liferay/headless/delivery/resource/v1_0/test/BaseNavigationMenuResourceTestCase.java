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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.NavigationMenu;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.NavigationMenuResource;
import com.liferay.headless.delivery.client.serdes.v1_0.NavigationMenuSerDes;
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
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseNavigationMenuResourceTestCase {

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

		_navigationMenuResource.setContextCompany(testCompany);

		NavigationMenuResource.Builder builder =
			NavigationMenuResource.builder();

		navigationMenuResource = builder.locale(
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

		NavigationMenu navigationMenu1 = randomNavigationMenu();

		String json = objectMapper.writeValueAsString(navigationMenu1);

		NavigationMenu navigationMenu2 = NavigationMenuSerDes.toDTO(json);

		Assert.assertTrue(equals(navigationMenu1, navigationMenu2));
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

		NavigationMenu navigationMenu = randomNavigationMenu();

		String json1 = objectMapper.writeValueAsString(navigationMenu);
		String json2 = NavigationMenuSerDes.toJSON(navigationMenu);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		NavigationMenu navigationMenu = randomNavigationMenu();

		navigationMenu.setName(regex);

		String json = NavigationMenuSerDes.toJSON(navigationMenu);

		Assert.assertFalse(json.contains(regex));

		navigationMenu = NavigationMenuSerDes.toDTO(json);

		Assert.assertEquals(regex, navigationMenu.getName());
	}

	@Test
	public void testGetNavigationMenu() throws Exception {
		NavigationMenu postNavigationMenu =
			testGetNavigationMenu_addNavigationMenu();

		NavigationMenu getNavigationMenu =
			navigationMenuResource.getNavigationMenu(
				postNavigationMenu.getId());

		assertEquals(postNavigationMenu, getNavigationMenu);
		assertValid(getNavigationMenu);
	}

	protected NavigationMenu testGetNavigationMenu_addNavigationMenu()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetNavigationMenu() throws Exception {
		NavigationMenu navigationMenu =
			testGraphQLNavigationMenu_addNavigationMenu();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"navigationMenu",
				new HashMap<String, Object>() {
					{
						put("navigationMenuId", navigationMenu.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				navigationMenu,
				dataJSONObject.getJSONObject("navigationMenu")));
	}

	@Test
	public void testGetSiteNavigationMenusPage() throws Exception {
		Page<NavigationMenu> page =
			navigationMenuResource.getSiteNavigationMenusPage(
				testGetSiteNavigationMenusPage_getSiteId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteNavigationMenusPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteNavigationMenusPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			NavigationMenu irrelevantNavigationMenu =
				testGetSiteNavigationMenusPage_addNavigationMenu(
					irrelevantSiteId, randomIrrelevantNavigationMenu());

			page = navigationMenuResource.getSiteNavigationMenusPage(
				irrelevantSiteId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantNavigationMenu),
				(List<NavigationMenu>)page.getItems());
			assertValid(page);
		}

		NavigationMenu navigationMenu1 =
			testGetSiteNavigationMenusPage_addNavigationMenu(
				siteId, randomNavigationMenu());

		NavigationMenu navigationMenu2 =
			testGetSiteNavigationMenusPage_addNavigationMenu(
				siteId, randomNavigationMenu());

		page = navigationMenuResource.getSiteNavigationMenusPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(navigationMenu1, navigationMenu2),
			(List<NavigationMenu>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteNavigationMenusPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteNavigationMenusPage_getSiteId();

		NavigationMenu navigationMenu1 =
			testGetSiteNavigationMenusPage_addNavigationMenu(
				siteId, randomNavigationMenu());

		NavigationMenu navigationMenu2 =
			testGetSiteNavigationMenusPage_addNavigationMenu(
				siteId, randomNavigationMenu());

		NavigationMenu navigationMenu3 =
			testGetSiteNavigationMenusPage_addNavigationMenu(
				siteId, randomNavigationMenu());

		Page<NavigationMenu> page1 =
			navigationMenuResource.getSiteNavigationMenusPage(
				siteId, Pagination.of(1, 2));

		List<NavigationMenu> navigationMenus1 =
			(List<NavigationMenu>)page1.getItems();

		Assert.assertEquals(
			navigationMenus1.toString(), 2, navigationMenus1.size());

		Page<NavigationMenu> page2 =
			navigationMenuResource.getSiteNavigationMenusPage(
				siteId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<NavigationMenu> navigationMenus2 =
			(List<NavigationMenu>)page2.getItems();

		Assert.assertEquals(
			navigationMenus2.toString(), 1, navigationMenus2.size());

		Page<NavigationMenu> page3 =
			navigationMenuResource.getSiteNavigationMenusPage(
				siteId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(navigationMenu1, navigationMenu2, navigationMenu3),
			(List<NavigationMenu>)page3.getItems());
	}

	protected NavigationMenu testGetSiteNavigationMenusPage_addNavigationMenu(
			Long siteId, NavigationMenu navigationMenu)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteNavigationMenusPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteNavigationMenusPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteNavigationMenusPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"navigationMenus",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject navigationMenusJSONObject = dataJSONObject.getJSONObject(
			"navigationMenus");

		Assert.assertEquals(0, navigationMenusJSONObject.get("totalCount"));

		NavigationMenu navigationMenu1 =
			testGraphQLNavigationMenu_addNavigationMenu();
		NavigationMenu navigationMenu2 =
			testGraphQLNavigationMenu_addNavigationMenu();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		navigationMenusJSONObject = dataJSONObject.getJSONObject(
			"navigationMenus");

		Assert.assertEquals(2, navigationMenusJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(navigationMenu1, navigationMenu2),
			navigationMenusJSONObject.getJSONArray("items"));
	}

	protected NavigationMenu testGraphQLNavigationMenu_addNavigationMenu()
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
		NavigationMenu navigationMenu1, NavigationMenu navigationMenu2) {

		Assert.assertTrue(
			navigationMenu1 + " does not equal " + navigationMenu2,
			equals(navigationMenu1, navigationMenu2));
	}

	protected void assertEquals(
		List<NavigationMenu> navigationMenus1,
		List<NavigationMenu> navigationMenus2) {

		Assert.assertEquals(navigationMenus1.size(), navigationMenus2.size());

		for (int i = 0; i < navigationMenus1.size(); i++) {
			NavigationMenu navigationMenu1 = navigationMenus1.get(i);
			NavigationMenu navigationMenu2 = navigationMenus2.get(i);

			assertEquals(navigationMenu1, navigationMenu2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<NavigationMenu> navigationMenus1,
		List<NavigationMenu> navigationMenus2) {

		Assert.assertEquals(navigationMenus1.size(), navigationMenus2.size());

		for (NavigationMenu navigationMenu1 : navigationMenus1) {
			boolean contains = false;

			for (NavigationMenu navigationMenu2 : navigationMenus2) {
				if (equals(navigationMenu1, navigationMenu2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				navigationMenus2 + " does not contain " + navigationMenu1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<NavigationMenu> navigationMenus, JSONArray jsonArray) {

		for (NavigationMenu navigationMenu : navigationMenus) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(navigationMenu, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + navigationMenu, contains);
		}
	}

	protected void assertValid(NavigationMenu navigationMenu) {
		boolean valid = true;

		if (navigationMenu.getDateCreated() == null) {
			valid = false;
		}

		if (navigationMenu.getDateModified() == null) {
			valid = false;
		}

		if (navigationMenu.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				navigationMenu.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (navigationMenu.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (navigationMenu.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"navigationMenuItems", additionalAssertFieldName)) {

				if (navigationMenu.getNavigationMenuItems() == null) {
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

	protected void assertValid(Page<NavigationMenu> page) {
		boolean valid = false;

		java.util.Collection<NavigationMenu> navigationMenus = page.getItems();

		int size = navigationMenus.size();

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

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		NavigationMenu navigationMenu1, NavigationMenu navigationMenu2) {

		if (navigationMenu1 == navigationMenu2) {
			return true;
		}

		if (!Objects.equals(
				navigationMenu1.getSiteId(), navigationMenu2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						navigationMenu1.getCreator(),
						navigationMenu2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						navigationMenu1.getDateCreated(),
						navigationMenu2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						navigationMenu1.getDateModified(),
						navigationMenu2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						navigationMenu1.getId(), navigationMenu2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						navigationMenu1.getName(), navigationMenu2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"navigationMenuItems", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						navigationMenu1.getNavigationMenuItems(),
						navigationMenu2.getNavigationMenuItems())) {

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

	protected boolean equalsJSONObject(
		NavigationMenu navigationMenu, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						navigationMenu.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						navigationMenu.getName(),
						jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_navigationMenuResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_navigationMenuResource;

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
		EntityField entityField, String operator,
		NavigationMenu navigationMenu) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("creator")) {
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
							navigationMenu.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							navigationMenu.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(navigationMenu.getDateCreated()));
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
							navigationMenu.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							navigationMenu.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(navigationMenu.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(navigationMenu.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("navigationMenuItems")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
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

	protected NavigationMenu randomNavigationMenu() throws Exception {
		return new NavigationMenu() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected NavigationMenu randomIrrelevantNavigationMenu() throws Exception {
		NavigationMenu randomIrrelevantNavigationMenu = randomNavigationMenu();

		randomIrrelevantNavigationMenu.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantNavigationMenu;
	}

	protected NavigationMenu randomPatchNavigationMenu() throws Exception {
		return randomNavigationMenu();
	}

	protected NavigationMenuResource navigationMenuResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

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

			if (_graphQLFields.length > 0) {
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

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseNavigationMenuResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.NavigationMenuResource
		_navigationMenuResource;

}