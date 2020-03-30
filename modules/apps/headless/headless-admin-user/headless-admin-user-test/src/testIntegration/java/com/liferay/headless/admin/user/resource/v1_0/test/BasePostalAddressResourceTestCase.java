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

import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.resource.v1_0.PostalAddressResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.PostalAddressSerDes;
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
public abstract class BasePostalAddressResourceTestCase {

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

		_postalAddressResource.setContextCompany(testCompany);

		PostalAddressResource.Builder builder = PostalAddressResource.builder();

		postalAddressResource = builder.locale(
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

		PostalAddress postalAddress1 = randomPostalAddress();

		String json = objectMapper.writeValueAsString(postalAddress1);

		PostalAddress postalAddress2 = PostalAddressSerDes.toDTO(json);

		Assert.assertTrue(equals(postalAddress1, postalAddress2));
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

		PostalAddress postalAddress = randomPostalAddress();

		String json1 = objectMapper.writeValueAsString(postalAddress);
		String json2 = PostalAddressSerDes.toJSON(postalAddress);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PostalAddress postalAddress = randomPostalAddress();

		postalAddress.setAddressCountry(regex);
		postalAddress.setAddressLocality(regex);
		postalAddress.setAddressRegion(regex);
		postalAddress.setAddressType(regex);
		postalAddress.setPostalCode(regex);
		postalAddress.setStreetAddressLine1(regex);
		postalAddress.setStreetAddressLine2(regex);
		postalAddress.setStreetAddressLine3(regex);

		String json = PostalAddressSerDes.toJSON(postalAddress);

		Assert.assertFalse(json.contains(regex));

		postalAddress = PostalAddressSerDes.toDTO(json);

		Assert.assertEquals(regex, postalAddress.getAddressCountry());
		Assert.assertEquals(regex, postalAddress.getAddressLocality());
		Assert.assertEquals(regex, postalAddress.getAddressRegion());
		Assert.assertEquals(regex, postalAddress.getAddressType());
		Assert.assertEquals(regex, postalAddress.getPostalCode());
		Assert.assertEquals(regex, postalAddress.getStreetAddressLine1());
		Assert.assertEquals(regex, postalAddress.getStreetAddressLine2());
		Assert.assertEquals(regex, postalAddress.getStreetAddressLine3());
	}

	@Test
	public void testGetOrganizationPostalAddressesPage() throws Exception {
		Page<PostalAddress> page =
			postalAddressResource.getOrganizationPostalAddressesPage(
				testGetOrganizationPostalAddressesPage_getOrganizationId());

		Assert.assertEquals(0, page.getTotalCount());

		String organizationId =
			testGetOrganizationPostalAddressesPage_getOrganizationId();
		String irrelevantOrganizationId =
			testGetOrganizationPostalAddressesPage_getIrrelevantOrganizationId();

		if ((irrelevantOrganizationId != null)) {
			PostalAddress irrelevantPostalAddress =
				testGetOrganizationPostalAddressesPage_addPostalAddress(
					irrelevantOrganizationId, randomIrrelevantPostalAddress());

			page = postalAddressResource.getOrganizationPostalAddressesPage(
				irrelevantOrganizationId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPostalAddress),
				(List<PostalAddress>)page.getItems());
			assertValid(page);
		}

		PostalAddress postalAddress1 =
			testGetOrganizationPostalAddressesPage_addPostalAddress(
				organizationId, randomPostalAddress());

		PostalAddress postalAddress2 =
			testGetOrganizationPostalAddressesPage_addPostalAddress(
				organizationId, randomPostalAddress());

		page = postalAddressResource.getOrganizationPostalAddressesPage(
			organizationId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(postalAddress1, postalAddress2),
			(List<PostalAddress>)page.getItems());
		assertValid(page);
	}

	protected PostalAddress
			testGetOrganizationPostalAddressesPage_addPostalAddress(
				String organizationId, PostalAddress postalAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetOrganizationPostalAddressesPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrganizationPostalAddressesPage_getIrrelevantOrganizationId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetPostalAddress() throws Exception {
		PostalAddress postPostalAddress =
			testGetPostalAddress_addPostalAddress();

		PostalAddress getPostalAddress = postalAddressResource.getPostalAddress(
			postPostalAddress.getId());

		assertEquals(postPostalAddress, getPostalAddress);
		assertValid(getPostalAddress);
	}

	protected PostalAddress testGetPostalAddress_addPostalAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPostalAddress() throws Exception {
		PostalAddress postalAddress =
			testGraphQLPostalAddress_addPostalAddress();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"postalAddress",
				new HashMap<String, Object>() {
					{
						put("postalAddressId", postalAddress.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				postalAddress, dataJSONObject.getJSONObject("postalAddress")));
	}

	@Test
	public void testGetUserAccountPostalAddressesPage() throws Exception {
		Page<PostalAddress> page =
			postalAddressResource.getUserAccountPostalAddressesPage(
				testGetUserAccountPostalAddressesPage_getUserAccountId());

		Assert.assertEquals(0, page.getTotalCount());

		Long userAccountId =
			testGetUserAccountPostalAddressesPage_getUserAccountId();
		Long irrelevantUserAccountId =
			testGetUserAccountPostalAddressesPage_getIrrelevantUserAccountId();

		if ((irrelevantUserAccountId != null)) {
			PostalAddress irrelevantPostalAddress =
				testGetUserAccountPostalAddressesPage_addPostalAddress(
					irrelevantUserAccountId, randomIrrelevantPostalAddress());

			page = postalAddressResource.getUserAccountPostalAddressesPage(
				irrelevantUserAccountId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPostalAddress),
				(List<PostalAddress>)page.getItems());
			assertValid(page);
		}

		PostalAddress postalAddress1 =
			testGetUserAccountPostalAddressesPage_addPostalAddress(
				userAccountId, randomPostalAddress());

		PostalAddress postalAddress2 =
			testGetUserAccountPostalAddressesPage_addPostalAddress(
				userAccountId, randomPostalAddress());

		page = postalAddressResource.getUserAccountPostalAddressesPage(
			userAccountId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(postalAddress1, postalAddress2),
			(List<PostalAddress>)page.getItems());
		assertValid(page);
	}

	protected PostalAddress
			testGetUserAccountPostalAddressesPage_addPostalAddress(
				Long userAccountId, PostalAddress postalAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserAccountPostalAddressesPage_getUserAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetUserAccountPostalAddressesPage_getIrrelevantUserAccountId()
		throws Exception {

		return null;
	}

	protected PostalAddress testGraphQLPostalAddress_addPostalAddress()
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
		PostalAddress postalAddress1, PostalAddress postalAddress2) {

		Assert.assertTrue(
			postalAddress1 + " does not equal " + postalAddress2,
			equals(postalAddress1, postalAddress2));
	}

	protected void assertEquals(
		List<PostalAddress> postalAddresses1,
		List<PostalAddress> postalAddresses2) {

		Assert.assertEquals(postalAddresses1.size(), postalAddresses2.size());

		for (int i = 0; i < postalAddresses1.size(); i++) {
			PostalAddress postalAddress1 = postalAddresses1.get(i);
			PostalAddress postalAddress2 = postalAddresses2.get(i);

			assertEquals(postalAddress1, postalAddress2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PostalAddress> postalAddresses1,
		List<PostalAddress> postalAddresses2) {

		Assert.assertEquals(postalAddresses1.size(), postalAddresses2.size());

		for (PostalAddress postalAddress1 : postalAddresses1) {
			boolean contains = false;

			for (PostalAddress postalAddress2 : postalAddresses2) {
				if (equals(postalAddress1, postalAddress2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				postalAddresses2 + " does not contain " + postalAddress1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<PostalAddress> postalAddresses, JSONArray jsonArray) {

		for (PostalAddress postalAddress : postalAddresses) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(postalAddress, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + postalAddress, contains);
		}
	}

	protected void assertValid(PostalAddress postalAddress) {
		boolean valid = true;

		if (postalAddress.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("addressCountry", additionalAssertFieldName)) {
				if (postalAddress.getAddressCountry() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"addressCountry_i18n", additionalAssertFieldName)) {

				if (postalAddress.getAddressCountry_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("addressLocality", additionalAssertFieldName)) {
				if (postalAddress.getAddressLocality() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("addressRegion", additionalAssertFieldName)) {
				if (postalAddress.getAddressRegion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("addressType", additionalAssertFieldName)) {
				if (postalAddress.getAddressType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("postalCode", additionalAssertFieldName)) {
				if (postalAddress.getPostalCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (postalAddress.getPrimary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine1", additionalAssertFieldName)) {

				if (postalAddress.getStreetAddressLine1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine2", additionalAssertFieldName)) {

				if (postalAddress.getStreetAddressLine2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine3", additionalAssertFieldName)) {

				if (postalAddress.getStreetAddressLine3() == null) {
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

	protected void assertValid(Page<PostalAddress> page) {
		boolean valid = false;

		java.util.Collection<PostalAddress> postalAddresses = page.getItems();

		int size = postalAddresses.size();

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
		PostalAddress postalAddress1, PostalAddress postalAddress2) {

		if (postalAddress1 == postalAddress2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("addressCountry", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getAddressCountry(),
						postalAddress2.getAddressCountry())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"addressCountry_i18n", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						postalAddress1.getAddressCountry_i18n(),
						postalAddress2.getAddressCountry_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressLocality", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getAddressLocality(),
						postalAddress2.getAddressLocality())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressRegion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getAddressRegion(),
						postalAddress2.getAddressRegion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getAddressType(),
						postalAddress2.getAddressType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getId(), postalAddress2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("postalCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getPostalCode(),
						postalAddress2.getPostalCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getPrimary(),
						postalAddress2.getPrimary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine1", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						postalAddress1.getStreetAddressLine1(),
						postalAddress2.getStreetAddressLine1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine2", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						postalAddress1.getStreetAddressLine2(),
						postalAddress2.getStreetAddressLine2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine3", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						postalAddress1.getStreetAddressLine3(),
						postalAddress2.getStreetAddressLine3())) {

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
		PostalAddress postalAddress, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("addressCountry", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getAddressCountry(),
						jsonObject.getString("addressCountry"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressLocality", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getAddressLocality(),
						jsonObject.getString("addressLocality"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressRegion", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getAddressRegion(),
						jsonObject.getString("addressRegion"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressType", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getAddressType(),
						jsonObject.getString("addressType"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("postalCode", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getPostalCode(),
						jsonObject.getString("postalCode"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primary", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getPrimary(),
						jsonObject.getBoolean("primary"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("streetAddressLine1", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getStreetAddressLine1(),
						jsonObject.getString("streetAddressLine1"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("streetAddressLine2", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getStreetAddressLine2(),
						jsonObject.getString("streetAddressLine2"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("streetAddressLine3", fieldName)) {
				if (!Objects.deepEquals(
						postalAddress.getStreetAddressLine3(),
						jsonObject.getString("streetAddressLine3"))) {

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

		if (!(_postalAddressResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_postalAddressResource;

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
		EntityField entityField, String operator, PostalAddress postalAddress) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("addressCountry")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getAddressCountry()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("addressCountry_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("addressLocality")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getAddressLocality()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("addressRegion")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getAddressRegion()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("addressType")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getAddressType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("postalCode")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getPostalCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("primary")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("streetAddressLine1")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getStreetAddressLine1()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("streetAddressLine2")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getStreetAddressLine2()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("streetAddressLine3")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getStreetAddressLine3()));
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

	protected PostalAddress randomPostalAddress() throws Exception {
		return new PostalAddress() {
			{
				addressCountry = RandomTestUtil.randomString();
				addressLocality = RandomTestUtil.randomString();
				addressRegion = RandomTestUtil.randomString();
				addressType = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				postalCode = RandomTestUtil.randomString();
				primary = RandomTestUtil.randomBoolean();
				streetAddressLine1 = RandomTestUtil.randomString();
				streetAddressLine2 = RandomTestUtil.randomString();
				streetAddressLine3 = RandomTestUtil.randomString();
			}
		};
	}

	protected PostalAddress randomIrrelevantPostalAddress() throws Exception {
		PostalAddress randomIrrelevantPostalAddress = randomPostalAddress();

		return randomIrrelevantPostalAddress;
	}

	protected PostalAddress randomPatchPostalAddress() throws Exception {
		return randomPostalAddress();
	}

	protected PostalAddressResource postalAddressResource;
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
		BasePostalAddressResourceTestCase.class);

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
	private com.liferay.headless.admin.user.resource.v1_0.PostalAddressResource
		_postalAddressResource;

}