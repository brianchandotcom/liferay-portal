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
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.user.client.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.serdes.v1_0.EmailAddressSerDes;
import com.liferay.headless.admin.user.resource.v1_0.EmailAddressResource;
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
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
public abstract class BaseEmailAddressResourceTestCase {

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
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		EmailAddress emailAddress1 = randomEmailAddress();

		String json = objectMapper.writeValueAsString(emailAddress1);

		EmailAddress emailAddress2 = EmailAddressSerDes.toDTO(json);

		Assert.assertTrue(equals(emailAddress1, emailAddress2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		EmailAddress emailAddress = randomEmailAddress();

		String json1 = objectMapper.writeValueAsString(emailAddress);
		String json2 = EmailAddressSerDes.toJSON(emailAddress);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testGetEmailAddress() throws Exception {
		EmailAddress postEmailAddress = testGetEmailAddress_addEmailAddress();

		EmailAddress getEmailAddress = invokeGetEmailAddress(
			postEmailAddress.getId());

		assertEquals(postEmailAddress, getEmailAddress);
		assertValid(getEmailAddress);
	}

	protected EmailAddress testGetEmailAddress_addEmailAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected EmailAddress invokeGetEmailAddress(Long emailAddressId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/email-addresses/{emailAddressId}", emailAddressId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return EmailAddressSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetEmailAddressResponse(Long emailAddressId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/email-addresses/{emailAddressId}", emailAddressId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetOrganizationEmailAddressesPage() throws Exception {
		Long organizationId =
			testGetOrganizationEmailAddressesPage_getOrganizationId();
		Long irrelevantOrganizationId =
			testGetOrganizationEmailAddressesPage_getIrrelevantOrganizationId();

		if ((irrelevantOrganizationId != null)) {
			EmailAddress irrelevantEmailAddress =
				testGetOrganizationEmailAddressesPage_addEmailAddress(
					irrelevantOrganizationId, randomIrrelevantEmailAddress());

			Page<EmailAddress> page = invokeGetOrganizationEmailAddressesPage(
				irrelevantOrganizationId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantEmailAddress),
				(List<EmailAddress>)page.getItems());
			assertValid(page);
		}

		EmailAddress emailAddress1 =
			testGetOrganizationEmailAddressesPage_addEmailAddress(
				organizationId, randomEmailAddress());

		EmailAddress emailAddress2 =
			testGetOrganizationEmailAddressesPage_addEmailAddress(
				organizationId, randomEmailAddress());

		Page<EmailAddress> page = invokeGetOrganizationEmailAddressesPage(
			organizationId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(emailAddress1, emailAddress2),
			(List<EmailAddress>)page.getItems());
		assertValid(page);
	}

	protected EmailAddress
			testGetOrganizationEmailAddressesPage_addEmailAddress(
				Long organizationId, EmailAddress emailAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrganizationEmailAddressesPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetOrganizationEmailAddressesPage_getIrrelevantOrganizationId()
		throws Exception {

		return null;
	}

	protected Page<EmailAddress> invokeGetOrganizationEmailAddressesPage(
			Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organizationId}/email-addresses",
					organizationId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, EmailAddressSerDes::toDTO);
	}

	protected Http.Response invokeGetOrganizationEmailAddressesPageResponse(
			Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organizationId}/email-addresses",
					organizationId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetUserAccountEmailAddressesPage() throws Exception {
		Long userAccountId =
			testGetUserAccountEmailAddressesPage_getUserAccountId();
		Long irrelevantUserAccountId =
			testGetUserAccountEmailAddressesPage_getIrrelevantUserAccountId();

		if ((irrelevantUserAccountId != null)) {
			EmailAddress irrelevantEmailAddress =
				testGetUserAccountEmailAddressesPage_addEmailAddress(
					irrelevantUserAccountId, randomIrrelevantEmailAddress());

			Page<EmailAddress> page = invokeGetUserAccountEmailAddressesPage(
				irrelevantUserAccountId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantEmailAddress),
				(List<EmailAddress>)page.getItems());
			assertValid(page);
		}

		EmailAddress emailAddress1 =
			testGetUserAccountEmailAddressesPage_addEmailAddress(
				userAccountId, randomEmailAddress());

		EmailAddress emailAddress2 =
			testGetUserAccountEmailAddressesPage_addEmailAddress(
				userAccountId, randomEmailAddress());

		Page<EmailAddress> page = invokeGetUserAccountEmailAddressesPage(
			userAccountId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(emailAddress1, emailAddress2),
			(List<EmailAddress>)page.getItems());
		assertValid(page);
	}

	protected EmailAddress testGetUserAccountEmailAddressesPage_addEmailAddress(
			Long userAccountId, EmailAddress emailAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserAccountEmailAddressesPage_getUserAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetUserAccountEmailAddressesPage_getIrrelevantUserAccountId()
		throws Exception {

		return null;
	}

	protected Page<EmailAddress> invokeGetUserAccountEmailAddressesPage(
			Long userAccountId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/user-accounts/{userAccountId}/email-addresses",
					userAccountId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, EmailAddressSerDes::toDTO);
	}

	protected Http.Response invokeGetUserAccountEmailAddressesPageResponse(
			Long userAccountId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/user-accounts/{userAccountId}/email-addresses",
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
		EmailAddress emailAddress1, EmailAddress emailAddress2) {

		Assert.assertTrue(
			emailAddress1 + " does not equal " + emailAddress2,
			equals(emailAddress1, emailAddress2));
	}

	protected void assertEquals(
		List<EmailAddress> emailAddresses1,
		List<EmailAddress> emailAddresses2) {

		Assert.assertEquals(emailAddresses1.size(), emailAddresses2.size());

		for (int i = 0; i < emailAddresses1.size(); i++) {
			EmailAddress emailAddress1 = emailAddresses1.get(i);
			EmailAddress emailAddress2 = emailAddresses2.get(i);

			assertEquals(emailAddress1, emailAddress2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<EmailAddress> emailAddresses1,
		List<EmailAddress> emailAddresses2) {

		Assert.assertEquals(emailAddresses1.size(), emailAddresses2.size());

		for (EmailAddress emailAddress1 : emailAddresses1) {
			boolean contains = false;

			for (EmailAddress emailAddress2 : emailAddresses2) {
				if (equals(emailAddress1, emailAddress2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				emailAddresses2 + " does not contain " + emailAddress1,
				contains);
		}
	}

	protected void assertValid(EmailAddress emailAddress) {
		boolean valid = true;

		if (emailAddress.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("emailAddress", additionalAssertFieldName)) {
				if (emailAddress.getEmailAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (emailAddress.getPrimary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (emailAddress.getType() == null) {
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

	protected void assertValid(Page<EmailAddress> page) {
		boolean valid = false;

		Collection<EmailAddress> emailAddresses = page.getItems();

		int size = emailAddresses.size();

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
		EmailAddress emailAddress1, EmailAddress emailAddress2) {

		if (emailAddress1 == emailAddress2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("emailAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						emailAddress1.getEmailAddress(),
						emailAddress2.getEmailAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						emailAddress1.getId(), emailAddress2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						emailAddress1.getPrimary(),
						emailAddress2.getPrimary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						emailAddress1.getType(), emailAddress2.getType())) {

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
		if (!(_emailAddressResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_emailAddressResource;

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
		EntityField entityField, String operator, EmailAddress emailAddress) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("emailAddress")) {
			sb.append("'");
			sb.append(String.valueOf(emailAddress.getEmailAddress()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("primary")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(emailAddress.getType()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected EmailAddress randomEmailAddress() {
		return new EmailAddress() {
			{
				emailAddress = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				primary = RandomTestUtil.randomBoolean();
				type = RandomTestUtil.randomString();
			}
		};
	}

	protected EmailAddress randomIrrelevantEmailAddress() {
		EmailAddress randomIrrelevantEmailAddress = randomEmailAddress();

		return randomIrrelevantEmailAddress;
	}

	protected EmailAddress randomPatchEmailAddress() {
		return randomEmailAddress();
	}

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

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

	private String _toJSON(Map<String, String> map) {
		if (map == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		Set<Map.Entry<String, String>> set = map.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			sb.append("\"" + entry.getKey() + "\": ");

			if (entry.getValue() == null) {
				sb.append("null");
			}
			else {
				sb.append("\"" + entry.getValue() + "\"");
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
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
		BaseEmailAddressResourceTestCase.class);

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
	private EmailAddressResource _emailAddressResource;

	private URL _resourceURL;

}