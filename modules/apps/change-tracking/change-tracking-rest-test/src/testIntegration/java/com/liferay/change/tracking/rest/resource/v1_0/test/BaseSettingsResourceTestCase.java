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

import com.liferay.change.tracking.rest.client.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.client.dto.v1_0.Settings;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.resource.v1_0.SettingsResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.SettingsSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public abstract class BaseSettingsResourceTestCase {

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

		_settingsResource.setContextCompany(testCompany);

		SettingsResource.Builder builder = SettingsResource.builder();

		settingsResource = builder.locale(
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

		Settings settings1 = randomSettings();

		String json = objectMapper.writeValueAsString(settings1);

		Settings settings2 = SettingsSerDes.toDTO(json);

		Assert.assertTrue(equals(settings1, settings2));
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

		Settings settings = randomSettings();

		String json1 = objectMapper.writeValueAsString(settings);
		String json2 = SettingsSerDes.toJSON(settings);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Settings settings = randomSettings();

		String json = SettingsSerDes.toJSON(settings);

		Assert.assertFalse(json.contains(regex));

		settings = SettingsSerDes.toDTO(json);
	}

	@Test
	public void testGetSettingsPage() throws Exception {
		Page<Settings> page = settingsResource.getSettingsPage(null, null);

		Assert.assertEquals(0, page.getTotalCount());

		Settings settings1 = testGetSettingsPage_addSettings(randomSettings());

		Settings settings2 = testGetSettingsPage_addSettings(randomSettings());

		page = settingsResource.getSettingsPage(null, null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(settings1, settings2),
			(List<Settings>)page.getItems());
		assertValid(page);
	}

	protected Settings testGetSettingsPage_addSettings(Settings settings)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutSettings() throws Exception {
		Assert.assertTrue(false);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Settings settings1, Settings settings2) {
		Assert.assertTrue(
			settings1 + " does not equal " + settings2,
			equals(settings1, settings2));
	}

	protected void assertEquals(
		List<Settings> settingses1, List<Settings> settingses2) {

		Assert.assertEquals(settingses1.size(), settingses2.size());

		for (int i = 0; i < settingses1.size(); i++) {
			Settings settings1 = settingses1.get(i);
			Settings settings2 = settingses2.get(i);

			assertEquals(settings1, settings2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Settings> settingses1, List<Settings> settingses2) {

		Assert.assertEquals(settingses1.size(), settingses2.size());

		for (Settings settings1 : settingses1) {
			boolean contains = false;

			for (Settings settings2 : settingses2) {
				if (equals(settings1, settings2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				settingses2 + " does not contain " + settings1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Settings> settingses, JSONArray jsonArray) {

		for (Settings settings : settingses) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(settings, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + settings, contains);
		}
	}

	protected void assertValid(Settings settings) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"changeTrackingAllowed", additionalAssertFieldName)) {

				if (settings.getChangeTrackingAllowed() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"changeTrackingEnabled", additionalAssertFieldName)) {

				if (settings.getChangeTrackingEnabled() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"checkoutCTCollectionConfirmationEnabled",
					additionalAssertFieldName)) {

				if (settings.getCheckoutCTCollectionConfirmationEnabled() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (settings.getCompanyId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"supportedContentTypeLanguageKeys",
					additionalAssertFieldName)) {

				if (settings.getSupportedContentTypeLanguageKeys() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"supportedContentTypes", additionalAssertFieldName)) {

				if (settings.getSupportedContentTypes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (settings.getUserId() == null) {
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

	protected void assertValid(Page<Settings> page) {
		boolean valid = false;

		java.util.Collection<Settings> settingses = page.getItems();

		int size = settingses.size();

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

	protected boolean equals(Settings settings1, Settings settings2) {
		if (settings1 == settings2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"changeTrackingAllowed", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						settings1.getChangeTrackingAllowed(),
						settings2.getChangeTrackingAllowed())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"changeTrackingEnabled", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						settings1.getChangeTrackingEnabled(),
						settings2.getChangeTrackingEnabled())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"checkoutCTCollectionConfirmationEnabled",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						settings1.getCheckoutCTCollectionConfirmationEnabled(),
						settings2.
							getCheckoutCTCollectionConfirmationEnabled())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						settings1.getCompanyId(), settings2.getCompanyId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"supportedContentTypeLanguageKeys",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						settings1.getSupportedContentTypeLanguageKeys(),
						settings2.getSupportedContentTypeLanguageKeys())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"supportedContentTypes", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						settings1.getSupportedContentTypes(),
						settings2.getSupportedContentTypes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						settings1.getUserId(), settings2.getUserId())) {

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
		Settings settings, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("changeTrackingAllowed", fieldName)) {
				if (!Objects.deepEquals(
						settings.getChangeTrackingAllowed(),
						jsonObject.getBoolean("changeTrackingAllowed"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("changeTrackingEnabled", fieldName)) {
				if (!Objects.deepEquals(
						settings.getChangeTrackingEnabled(),
						jsonObject.getBoolean("changeTrackingEnabled"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"checkoutCTCollectionConfirmationEnabled", fieldName)) {

				if (!Objects.deepEquals(
						settings.getCheckoutCTCollectionConfirmationEnabled(),
						jsonObject.getBoolean(
							"checkoutCTCollectionConfirmationEnabled"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("companyId", fieldName)) {
				if (!Objects.deepEquals(
						settings.getCompanyId(),
						jsonObject.getLong("companyId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", fieldName)) {
				if (!Objects.deepEquals(
						settings.getUserId(), jsonObject.getLong("userId"))) {

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

		if (!(_settingsResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_settingsResource;

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
		EntityField entityField, String operator, Settings settings) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("changeTrackingAllowed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("changeTrackingEnabled")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("checkoutCTCollectionConfirmationEnabled")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("companyId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("supportedContentTypeLanguageKeys")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("supportedContentTypes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

	protected Settings randomSettings() throws Exception {
		return new Settings() {
			{
				changeTrackingAllowed = RandomTestUtil.randomBoolean();
				changeTrackingEnabled = RandomTestUtil.randomBoolean();
				checkoutCTCollectionConfirmationEnabled =
					RandomTestUtil.randomBoolean();
				companyId = RandomTestUtil.randomLong();
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected Settings randomIrrelevantSettings() throws Exception {
		Settings randomIrrelevantSettings = randomSettings();

		return randomIrrelevantSettings;
	}

	protected Settings randomPatchSettings() throws Exception {
		return randomSettings();
	}

	protected SettingsResource settingsResource;
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
		BaseSettingsResourceTestCase.class);

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
	private com.liferay.change.tracking.rest.resource.v1_0.SettingsResource
		_settingsResource;

}