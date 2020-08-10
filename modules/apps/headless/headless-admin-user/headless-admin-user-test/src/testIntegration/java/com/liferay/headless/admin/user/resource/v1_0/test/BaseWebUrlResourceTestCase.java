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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.resource.v1_0.WebUrlResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.WebUrlSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
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
public abstract class BaseWebUrlResourceTestCase {

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

		_webUrlResource.setContextCompany(testCompany);

		WebUrlResource.Builder builder = WebUrlResource.builder();

		webUrlResource = builder.authentication(
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

		WebUrl webUrl1 = randomWebUrl();

		String json = objectMapper.writeValueAsString(webUrl1);

		WebUrl webUrl2 = WebUrlSerDes.toDTO(json);

		Assert.assertTrue(equals(webUrl1, webUrl2));
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

		WebUrl webUrl = randomWebUrl();

		String json1 = objectMapper.writeValueAsString(webUrl);
		String json2 = WebUrlSerDes.toJSON(webUrl);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WebUrl webUrl = randomWebUrl();

		webUrl.setUrl(regex);
		webUrl.setUrlType(regex);

		String json = WebUrlSerDes.toJSON(webUrl);

		Assert.assertFalse(json.contains(regex));

		webUrl = WebUrlSerDes.toDTO(json);

		Assert.assertEquals(regex, webUrl.getUrl());
		Assert.assertEquals(regex, webUrl.getUrlType());
	}

	@Test
	public void testGetOrganizationWebUrlsPage() throws Exception {
		Page<WebUrl> page = webUrlResource.getOrganizationWebUrlsPage(
			testGetOrganizationWebUrlsPage_getOrganizationId());

		Assert.assertEquals(0, page.getTotalCount());

		String organizationId =
			testGetOrganizationWebUrlsPage_getOrganizationId();
		String irrelevantOrganizationId =
			testGetOrganizationWebUrlsPage_getIrrelevantOrganizationId();

		if ((irrelevantOrganizationId != null)) {
			WebUrl irrelevantWebUrl = testGetOrganizationWebUrlsPage_addWebUrl(
				irrelevantOrganizationId, randomIrrelevantWebUrl());

			page = webUrlResource.getOrganizationWebUrlsPage(
				irrelevantOrganizationId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWebUrl), (List<WebUrl>)page.getItems());
			assertValid(page);
		}

		WebUrl webUrl1 = testGetOrganizationWebUrlsPage_addWebUrl(
			organizationId, randomWebUrl());

		WebUrl webUrl2 = testGetOrganizationWebUrlsPage_addWebUrl(
			organizationId, randomWebUrl());

		page = webUrlResource.getOrganizationWebUrlsPage(organizationId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(webUrl1, webUrl2), (List<WebUrl>)page.getItems());
		assertValid(page);
	}

	protected WebUrl testGetOrganizationWebUrlsPage_addWebUrl(
			String organizationId, WebUrl webUrl)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetOrganizationWebUrlsPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrganizationWebUrlsPage_getIrrelevantOrganizationId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetUserAccountWebUrlsPage() throws Exception {
		Page<WebUrl> page = webUrlResource.getUserAccountWebUrlsPage(
			testGetUserAccountWebUrlsPage_getUserAccountId());

		Assert.assertEquals(0, page.getTotalCount());

		Long userAccountId = testGetUserAccountWebUrlsPage_getUserAccountId();
		Long irrelevantUserAccountId =
			testGetUserAccountWebUrlsPage_getIrrelevantUserAccountId();

		if ((irrelevantUserAccountId != null)) {
			WebUrl irrelevantWebUrl = testGetUserAccountWebUrlsPage_addWebUrl(
				irrelevantUserAccountId, randomIrrelevantWebUrl());

			page = webUrlResource.getUserAccountWebUrlsPage(
				irrelevantUserAccountId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWebUrl), (List<WebUrl>)page.getItems());
			assertValid(page);
		}

		WebUrl webUrl1 = testGetUserAccountWebUrlsPage_addWebUrl(
			userAccountId, randomWebUrl());

		WebUrl webUrl2 = testGetUserAccountWebUrlsPage_addWebUrl(
			userAccountId, randomWebUrl());

		page = webUrlResource.getUserAccountWebUrlsPage(userAccountId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(webUrl1, webUrl2), (List<WebUrl>)page.getItems());
		assertValid(page);
	}

	protected WebUrl testGetUserAccountWebUrlsPage_addWebUrl(
			Long userAccountId, WebUrl webUrl)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserAccountWebUrlsPage_getUserAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserAccountWebUrlsPage_getIrrelevantUserAccountId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetWebUrl() throws Exception {
		WebUrl postWebUrl = testGetWebUrl_addWebUrl();

		WebUrl getWebUrl = webUrlResource.getWebUrl(postWebUrl.getId());

		assertEquals(postWebUrl, getWebUrl);
		assertValid(getWebUrl);
	}

	protected WebUrl testGetWebUrl_addWebUrl() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWebUrl() throws Exception {
		WebUrl webUrl = testGraphQLWebUrl_addWebUrl();

		Assert.assertTrue(
			equals(
				webUrl,
				WebUrlSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"webUrl",
								new HashMap<String, Object>() {
									{
										put("webUrlId", webUrl.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/webUrl"))));
	}

	@Test
	public void testGraphQLGetWebUrlNotFound() throws Exception {
		Long irrelevantWebUrlId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"webUrl",
						new HashMap<String, Object>() {
							{
								put("webUrlId", irrelevantWebUrlId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected WebUrl testGraphQLWebUrl_addWebUrl() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(WebUrl webUrl1, WebUrl webUrl2) {
		Assert.assertTrue(
			webUrl1 + " does not equal " + webUrl2, equals(webUrl1, webUrl2));
	}

	protected void assertEquals(List<WebUrl> webUrls1, List<WebUrl> webUrls2) {
		Assert.assertEquals(webUrls1.size(), webUrls2.size());

		for (int i = 0; i < webUrls1.size(); i++) {
			WebUrl webUrl1 = webUrls1.get(i);
			WebUrl webUrl2 = webUrls2.get(i);

			assertEquals(webUrl1, webUrl2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WebUrl> webUrls1, List<WebUrl> webUrls2) {

		Assert.assertEquals(webUrls1.size(), webUrls2.size());

		for (WebUrl webUrl1 : webUrls1) {
			boolean contains = false;

			for (WebUrl webUrl2 : webUrls2) {
				if (equals(webUrl1, webUrl2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				webUrls2 + " does not contain " + webUrl1, contains);
		}
	}

	protected void assertValid(WebUrl webUrl) throws Exception {
		boolean valid = true;

		if (webUrl.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (webUrl.getPrimary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("url", additionalAssertFieldName)) {
				if (webUrl.getUrl() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("urlType", additionalAssertFieldName)) {
				if (webUrl.getUrlType() == null) {
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

	protected void assertValid(Page<WebUrl> page) {
		boolean valid = false;

		java.util.Collection<WebUrl> webUrls = page.getItems();

		int size = webUrls.size();

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

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.admin.user.dto.v1_0.WebUrl.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
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
					ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(WebUrl webUrl1, WebUrl webUrl2) {
		if (webUrl1 == webUrl2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(webUrl1.getId(), webUrl2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						webUrl1.getPrimary(), webUrl2.getPrimary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("url", additionalAssertFieldName)) {
				if (!Objects.deepEquals(webUrl1.getUrl(), webUrl2.getUrl())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("urlType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						webUrl1.getUrlType(), webUrl2.getUrlType())) {

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
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_webUrlResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_webUrlResource;

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
		EntityField entityField, String operator, WebUrl webUrl) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("primary")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("url")) {
			sb.append("'");
			sb.append(String.valueOf(webUrl.getUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("urlType")) {
			sb.append("'");
			sb.append(String.valueOf(webUrl.getUrlType()));
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

	protected WebUrl randomWebUrl() throws Exception {
		return new WebUrl() {
			{
				id = RandomTestUtil.randomLong();
				primary = RandomTestUtil.randomBoolean();
				url = StringUtil.toLowerCase(RandomTestUtil.randomString());
				urlType = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected WebUrl randomIrrelevantWebUrl() throws Exception {
		WebUrl randomIrrelevantWebUrl = randomWebUrl();

		return randomIrrelevantWebUrl;
	}

	protected WebUrl randomPatchWebUrl() throws Exception {
		return randomWebUrl();
	}

	protected WebUrlResource webUrlResource;
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
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
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

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWebUrlResourceTestCase.class);

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
	private com.liferay.headless.admin.user.resource.v1_0.WebUrlResource
		_webUrlResource;

}