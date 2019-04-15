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

import com.liferay.headless.admin.user.dto.v1_0.Email;
import com.liferay.headless.admin.user.resource.v1_0.EmailResource;
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
public abstract class BaseEmailResourceTestCase {

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
	public void testGetEmail() throws Exception {
		Email postEmail = testGetEmail_addEmail();

		Email getEmail = invokeGetEmail(postEmail.getId());

		assertEquals(postEmail, getEmail);
		assertValid(getEmail);
	}

	protected Email testGetEmail_addEmail() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Email invokeGetEmail(Long emailId) throws Exception {
		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/emails/{emailId}", emailId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Email.class);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetEmailResponse(Long emailId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/emails/{emailId}", emailId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetOrganizationEmailsPage() throws Exception {
		Long organizationId = testGetOrganizationEmailsPage_getOrganizationId();
		Long irrelevantOrganizationId =
			testGetOrganizationEmailsPage_getIrrelevantOrganizationId();

		if ((irrelevantOrganizationId != null)) {
			Email irrelevantEmail = testGetOrganizationEmailsPage_addEmail(
				irrelevantOrganizationId, randomIrrelevantEmail());

			Page<Email> page = invokeGetOrganizationEmailsPage(
				irrelevantOrganizationId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantEmail), (List<Email>)page.getItems());
			assertValid(page);
		}

		Email email1 = testGetOrganizationEmailsPage_addEmail(
			organizationId, randomEmail());

		Email email2 = testGetOrganizationEmailsPage_addEmail(
			organizationId, randomEmail());

		Page<Email> page = invokeGetOrganizationEmailsPage(organizationId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(email1, email2), (List<Email>)page.getItems());
		assertValid(page);
	}

	protected Email testGetOrganizationEmailsPage_addEmail(
			Long organizationId, Email email)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrganizationEmailsPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrganizationEmailsPage_getIrrelevantOrganizationId()
		throws Exception {

		return null;
	}

	protected Page<Email> invokeGetOrganizationEmailsPage(Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organizationId}/emails", organizationId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Email>>() {
			});
	}

	protected Http.Response invokeGetOrganizationEmailsPageResponse(
			Long organizationId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organizationId}/emails", organizationId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetUserAccountEmailsPage() throws Exception {
		Long userAccountId = testGetUserAccountEmailsPage_getUserAccountId();
		Long irrelevantUserAccountId =
			testGetUserAccountEmailsPage_getIrrelevantUserAccountId();

		if ((irrelevantUserAccountId != null)) {
			Email irrelevantEmail = testGetUserAccountEmailsPage_addEmail(
				irrelevantUserAccountId, randomIrrelevantEmail());

			Page<Email> page = invokeGetUserAccountEmailsPage(
				irrelevantUserAccountId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantEmail), (List<Email>)page.getItems());
			assertValid(page);
		}

		Email email1 = testGetUserAccountEmailsPage_addEmail(
			userAccountId, randomEmail());

		Email email2 = testGetUserAccountEmailsPage_addEmail(
			userAccountId, randomEmail());

		Page<Email> page = invokeGetUserAccountEmailsPage(userAccountId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(email1, email2), (List<Email>)page.getItems());
		assertValid(page);
	}

	protected Email testGetUserAccountEmailsPage_addEmail(
			Long userAccountId, Email email)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserAccountEmailsPage_getUserAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetUserAccountEmailsPage_getIrrelevantUserAccountId()
		throws Exception {

		return null;
	}

	protected Page<Email> invokeGetUserAccountEmailsPage(Long userAccountId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/user-accounts/{userAccountId}/emails", userAccountId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Email>>() {
			});
	}

	protected Http.Response invokeGetUserAccountEmailsPageResponse(
			Long userAccountId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/user-accounts/{userAccountId}/emails", userAccountId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(Email email1, Email email2) {
		Assert.assertTrue(
			email1 + " does not equal " + email2, equals(email1, email2));
	}

	protected void assertEquals(List<Email> emails1, List<Email> emails2) {
		Assert.assertEquals(emails1.size(), emails2.size());

		for (int i = 0; i < emails1.size(); i++) {
			Email email1 = emails1.get(i);
			Email email2 = emails2.get(i);

			assertEquals(email1, email2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Email> emails1, List<Email> emails2) {

		Assert.assertEquals(emails1.size(), emails2.size());

		for (Email email1 : emails1) {
			boolean contains = false;

			for (Email email2 : emails2) {
				if (equals(email1, email2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				emails2 + " does not contain " + email1, contains);
		}
	}

	protected void assertValid(Email email) {
		boolean valid = true;

		if (email.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("email", additionalAssertFieldName)) {
				if (email.getEmail() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (email.getPrimary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (email.getType() == null) {
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

	protected void assertValid(Page<Email> page) {
		boolean valid = false;

		Collection<Email> emails = page.getItems();

		int size = emails.size();

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

	protected boolean equals(Email email1, Email email2) {
		if (email1 == email2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("email", additionalAssertFieldName)) {
				if (!Objects.deepEquals(email1.getEmail(), email2.getEmail())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(email1.getId(), email2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						email1.getPrimary(), email2.getPrimary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(email1.getType(), email2.getType())) {
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
		if (!(_emailResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_emailResource;

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
		EntityField entityField, String operator, Email email) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("email")) {
			sb.append("'");
			sb.append(String.valueOf(email.getEmail()));
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
			sb.append(String.valueOf(email.getType()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Email randomEmail() {
		return new Email() {
			{
				email = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				primary = RandomTestUtil.randomBoolean();
				type = RandomTestUtil.randomString();
			}
		};
	}

	protected Email randomIrrelevantEmail() {
		Email randomIrrelevantEmail = randomEmail();

		return randomIrrelevantEmail;
	}

	protected Email randomPatchEmail() {
		return randomEmail();
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
				addMixIn(Email.class, EmailMixin.class);
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

	protected static class EmailMixin {

		@JsonProperty
		String email;
		@JsonProperty
		Long id;
		@JsonProperty
		Boolean primary;
		@JsonProperty
		String type;

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
		BaseEmailResourceTestCase.class);

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
	private EmailResource _emailResource;

	private URL _resourceURL;

}