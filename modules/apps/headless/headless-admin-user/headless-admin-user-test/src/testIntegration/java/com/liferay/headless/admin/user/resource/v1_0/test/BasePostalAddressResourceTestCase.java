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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.resource.v1_0.PostalAddressResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-admin-user/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetOrganizationPostalAddressesPage() throws Exception {
		Long organizationId =
			testGetOrganizationPostalAddressesPage_getOrganizationId();
		Long irrelevantOrganizationId =
			testGetOrganizationPostalAddressesPage_getIrrelevantOrganizationId();

		if ((irrelevantOrganizationId != null)) {
			PostalAddress irrelevantPostalAddress =
				testGetOrganizationPostalAddressesPage_addPostalAddress(
					irrelevantOrganizationId, randomIrrelevantPostalAddress());

			Page<PostalAddress> page = invokeGetOrganizationPostalAddressesPage(
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

		Page<PostalAddress> page = invokeGetOrganizationPostalAddressesPage(
			organizationId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(postalAddress1, postalAddress2),
			(List<PostalAddress>)page.getItems());
		assertValid(page);
	}

	protected PostalAddress
			testGetOrganizationPostalAddressesPage_addPostalAddress(
				Long organizationId, PostalAddress postalAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrganizationPostalAddressesPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetOrganizationPostalAddressesPage_getIrrelevantOrganizationId()
		throws Exception {

		return null;
	}

	protected Page<PostalAddress> invokeGetOrganizationPostalAddressesPage(
			Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organizationId}/postal-addresses",
					organizationId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<PostalAddress>>() {
			});
	}

	protected Http.Response invokeGetOrganizationPostalAddressesPageResponse(
			Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organizationId}/postal-addresses",
					organizationId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetPostalAddress() throws Exception {
		PostalAddress postPostalAddress =
			testGetPostalAddress_addPostalAddress();

		PostalAddress getPostalAddress = invokeGetPostalAddress(
			postPostalAddress.getId());

		assertEquals(postPostalAddress, getPostalAddress);
		assertValid(getPostalAddress);
	}

	protected PostalAddress testGetPostalAddress_addPostalAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected PostalAddress invokeGetPostalAddress(Long postalAddressId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/postal-addresses/{postalAddressId}", postalAddressId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, PostalAddress.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetPostalAddressResponse(Long postalAddressId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/postal-addresses/{postalAddressId}", postalAddressId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetUserAccountPostalAddressesPage() throws Exception {
		Long userAccountId =
			testGetUserAccountPostalAddressesPage_getUserAccountId();
		Long irrelevantUserAccountId =
			testGetUserAccountPostalAddressesPage_getIrrelevantUserAccountId();

		if ((irrelevantUserAccountId != null)) {
			PostalAddress irrelevantPostalAddress =
				testGetUserAccountPostalAddressesPage_addPostalAddress(
					irrelevantUserAccountId, randomIrrelevantPostalAddress());

			Page<PostalAddress> page = invokeGetUserAccountPostalAddressesPage(
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

		Page<PostalAddress> page = invokeGetUserAccountPostalAddressesPage(
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

	protected Page<PostalAddress> invokeGetUserAccountPostalAddressesPage(
			Long userAccountId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/user-accounts/{userAccountId}/postal-addresses",
					userAccountId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<PostalAddress>>() {
			});
	}

	protected Http.Response invokeGetUserAccountPostalAddressesPageResponse(
			Long userAccountId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/user-accounts/{userAccountId}/postal-addresses",
					userAccountId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
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

		Collection<PostalAddress> postalAddresses = page.getItems();

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

	protected Collection<EntityField> getEntityFields() throws Exception {
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

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
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

	protected PostalAddress randomPostalAddress() {
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

	protected PostalAddress randomIrrelevantPostalAddress() {
		PostalAddress randomIrrelevantPostalAddress = randomPostalAddress();

		return randomIrrelevantPostalAddress;
	}

	protected PostalAddress randomPatchPostalAddress() {
		return randomPostalAddress();
	}

	protected static final ObjectMapper inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	protected static final ObjectMapper outputObjectMapper =
		new ObjectMapper() {
			{
				addMixIn(PostalAddress.class, PostalAddressMixin.class);
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
			}
		};

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	protected static class PostalAddressMixin {

		@JsonProperty
		String addressCountry;

		@JsonProperty
		String addressLocality;

		@JsonProperty
		String addressRegion;

		@JsonProperty
		String addressType;

		@JsonProperty
		Long id;

		@JsonProperty
		String postalCode;

		@JsonProperty
		Boolean primary;

		@JsonProperty
		String streetAddressLine1;

		@JsonProperty
		String streetAddressLine2;

		@JsonProperty
		String streetAddressLine3;

	}

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

		return options;
	}

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*?\\}", String.valueOf(values[i]));
		}

		return template;
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
	private PostalAddressResource _postalAddressResource;

	private URL _resourceURL;

}