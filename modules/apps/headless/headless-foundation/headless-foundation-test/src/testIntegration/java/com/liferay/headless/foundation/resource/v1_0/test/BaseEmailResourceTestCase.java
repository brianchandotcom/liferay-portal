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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.foundation.dto.v1_0.Email;
import com.liferay.headless.foundation.resource.v1_0.EmailResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetEmail() throws Exception {
		Email postEmail = testGetEmail_addEmail();

		Email getEmail = invokeGetEmail(postEmail.getId());

		assertEquals(postEmail, getEmail);
		assertValid(getEmail);
	}

	@Test
	public void testGetGenericParentEmailsPage() throws Exception {
		Object genericParentId =
			testGetGenericParentEmailsPage_getGenericParentId();

		Email email1 = testGetGenericParentEmailsPage_addEmail(
			genericParentId, randomEmail());
		Email email2 = testGetGenericParentEmailsPage_addEmail(
			genericParentId, randomEmail());

		Page<Email> page = invokeGetGenericParentEmailsPage(
			genericParentId, Pagination.of(2, 1));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(email1, email2), (List<Email>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetGenericParentEmailsPageWithPagination()
		throws Exception {

		Object genericParentId =
			testGetGenericParentEmailsPage_getGenericParentId();

		Email email1 = testGetGenericParentEmailsPage_addEmail(
			genericParentId, randomEmail());
		Email email2 = testGetGenericParentEmailsPage_addEmail(
			genericParentId, randomEmail());
		Email email3 = testGetGenericParentEmailsPage_addEmail(
			genericParentId, randomEmail());

		Page<Email> page1 = invokeGetGenericParentEmailsPage(
			genericParentId, Pagination.of(2, 1));

		List<Email> emails1 = (List<Email>)page1.getItems();

		Assert.assertEquals(emails1.toString(), 2, emails1.size());

		Page<Email> page2 = invokeGetGenericParentEmailsPage(
			genericParentId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Email> emails2 = (List<Email>)page2.getItems();

		Assert.assertEquals(emails2.toString(), 1, emails2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(email1, email2, email3),
			new ArrayList<Email>() {
				{
					addAll(emails1);
					addAll(emails2);
				}
			});
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

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(Email email) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Email> page) {
		boolean valid = false;

		Collection<Email> emails = page.getItems();

		int size = emails.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(Email email1, Email email2) {
		if (email1 == email2) {
			return true;
		}

		return false;
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

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(email.getType()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Email invokeGetEmail(Long emailId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/emails/{email-id}", emailId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Email.class);
	}

	protected Http.Response invokeGetEmailResponse(Long emailId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/emails/{email-id}", emailId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Email> invokeGetGenericParentEmailsPage(
			Object genericParentId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(_resourceURL + _toPath("/emails", genericParentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Email>>() {
			});
	}

	protected Http.Response invokeGetGenericParentEmailsPageResponse(
			Object genericParentId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(_resourceURL + _toPath("/emails", genericParentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Email randomEmail() {
		return new Email() {
			{
				email = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				type = RandomTestUtil.randomString();
			}
		};
	}

	protected Email testGetEmail_addEmail() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Email testGetGenericParentEmailsPage_addEmail(
			Object genericParentId, Email email)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Object testGetGenericParentEmailsPage_getGenericParentId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getItemsPerPage() {
			return itemsPerPage;
		}

		public long getLastPageNumber() {
			return lastPageNumber;
		}

		public long getPageNumber() {
			return pageNumber;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected long itemsPerPage;

		@JsonProperty
		protected long lastPageNumber;

		@JsonProperty("page")
		protected long pageNumber;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static DateFormat _dateFormat;
	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	@Inject
	private EmailResource _emailResource;

	private URL _resourceURL;

}