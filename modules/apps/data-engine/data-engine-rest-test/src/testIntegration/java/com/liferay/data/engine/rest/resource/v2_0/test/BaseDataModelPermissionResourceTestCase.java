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

package com.liferay.data.engine.rest.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.data.engine.rest.client.dto.v2_0.DataModelPermission;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.resource.v2_0.DataModelPermissionResource;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataModelPermissionSerDes;
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
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public abstract class BaseDataModelPermissionResourceTestCase {

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

		_dataModelPermissionResource.setContextCompany(testCompany);

		DataModelPermissionResource.Builder builder =
			DataModelPermissionResource.builder();

		dataModelPermissionResource = builder.locale(
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

		DataModelPermission dataModelPermission1 = randomDataModelPermission();

		String json = objectMapper.writeValueAsString(dataModelPermission1);

		DataModelPermission dataModelPermission2 =
			DataModelPermissionSerDes.toDTO(json);

		Assert.assertTrue(equals(dataModelPermission1, dataModelPermission2));
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

		DataModelPermission dataModelPermission = randomDataModelPermission();

		String json1 = objectMapper.writeValueAsString(dataModelPermission);
		String json2 = DataModelPermissionSerDes.toJSON(dataModelPermission);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DataModelPermission dataModelPermission = randomDataModelPermission();

		dataModelPermission.setRoleName(regex);

		String json = DataModelPermissionSerDes.toJSON(dataModelPermission);

		Assert.assertFalse(json.contains(regex));

		dataModelPermission = DataModelPermissionSerDes.toDTO(json);

		Assert.assertEquals(regex, dataModelPermission.getRoleName());
	}

	@Test
	public void testPutDataDefinitionDataModelPermission() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPutDataRecordCollectionDataModelPermission()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetDataRecordCollectionDataModelPermissionByCurrentUser()
		throws Exception {

		Assert.assertTrue(false);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DataModelPermission dataModelPermission1,
		DataModelPermission dataModelPermission2) {

		Assert.assertTrue(
			dataModelPermission1 + " does not equal " + dataModelPermission2,
			equals(dataModelPermission1, dataModelPermission2));
	}

	protected void assertEquals(
		List<DataModelPermission> dataModelPermissions1,
		List<DataModelPermission> dataModelPermissions2) {

		Assert.assertEquals(
			dataModelPermissions1.size(), dataModelPermissions2.size());

		for (int i = 0; i < dataModelPermissions1.size(); i++) {
			DataModelPermission dataModelPermission1 =
				dataModelPermissions1.get(i);
			DataModelPermission dataModelPermission2 =
				dataModelPermissions2.get(i);

			assertEquals(dataModelPermission1, dataModelPermission2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataModelPermission> dataModelPermissions1,
		List<DataModelPermission> dataModelPermissions2) {

		Assert.assertEquals(
			dataModelPermissions1.size(), dataModelPermissions2.size());

		for (DataModelPermission dataModelPermission1 : dataModelPermissions1) {
			boolean contains = false;

			for (DataModelPermission dataModelPermission2 :
					dataModelPermissions2) {

				if (equals(dataModelPermission1, dataModelPermission2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataModelPermissions2 + " does not contain " +
					dataModelPermission1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<DataModelPermission> dataModelPermissions, JSONArray jsonArray) {

		for (DataModelPermission dataModelPermission : dataModelPermissions) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(dataModelPermission, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + dataModelPermission,
				contains);
		}
	}

	protected void assertValid(DataModelPermission dataModelPermission) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actionIds", additionalAssertFieldName)) {
				if (dataModelPermission.getActionIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("roleName", additionalAssertFieldName)) {
				if (dataModelPermission.getRoleName() == null) {
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

	protected void assertValid(Page<DataModelPermission> page) {
		boolean valid = false;

		java.util.Collection<DataModelPermission> dataModelPermissions =
			page.getItems();

		int size = dataModelPermissions.size();

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
		DataModelPermission dataModelPermission1,
		DataModelPermission dataModelPermission2) {

		if (dataModelPermission1 == dataModelPermission2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actionIds", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataModelPermission1.getActionIds(),
						dataModelPermission2.getActionIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("roleName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataModelPermission1.getRoleName(),
						dataModelPermission2.getRoleName())) {

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
		DataModelPermission dataModelPermission, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("roleName", fieldName)) {
				if (!Objects.deepEquals(
						dataModelPermission.getRoleName(),
						jsonObject.getString("roleName"))) {

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

		if (!(_dataModelPermissionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataModelPermissionResource;

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
		DataModelPermission dataModelPermission) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actionIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("roleName")) {
			sb.append("'");
			sb.append(String.valueOf(dataModelPermission.getRoleName()));
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

	protected DataModelPermission randomDataModelPermission() throws Exception {
		return new DataModelPermission() {
			{
				roleName = RandomTestUtil.randomString();
			}
		};
	}

	protected DataModelPermission randomIrrelevantDataModelPermission()
		throws Exception {

		DataModelPermission randomIrrelevantDataModelPermission =
			randomDataModelPermission();

		return randomIrrelevantDataModelPermission;
	}

	protected DataModelPermission randomPatchDataModelPermission()
		throws Exception {

		return randomDataModelPermission();
	}

	protected DataModelPermissionResource dataModelPermissionResource;
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
		BaseDataModelPermissionResourceTestCase.class);

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
		com.liferay.data.engine.rest.resource.v2_0.DataModelPermissionResource
			_dataModelPermissionResource;

}