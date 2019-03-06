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

import com.liferay.headless.foundation.dto.v1_0.Phone;
import com.liferay.headless.foundation.resource.v1_0.PhoneResource;
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
public abstract class BasePhoneResourceTestCase {

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
	public void testGetGenericParentPhonesPage() throws Exception {
		Object genericParentId =
			testGetGenericParentPhonesPage_getGenericParentId();

		Phone phone1 = testGetGenericParentPhonesPage_addPhone(
			genericParentId, randomPhone());
		Phone phone2 = testGetGenericParentPhonesPage_addPhone(
			genericParentId, randomPhone());

		Page<Phone> page = invokeGetGenericParentPhonesPage(
			genericParentId, Pagination.of(2, 1));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(phone1, phone2), (List<Phone>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetGenericParentPhonesPageWithPagination()
		throws Exception {

		Object genericParentId =
			testGetGenericParentPhonesPage_getGenericParentId();

		Phone phone1 = testGetGenericParentPhonesPage_addPhone(
			genericParentId, randomPhone());
		Phone phone2 = testGetGenericParentPhonesPage_addPhone(
			genericParentId, randomPhone());
		Phone phone3 = testGetGenericParentPhonesPage_addPhone(
			genericParentId, randomPhone());

		Page<Phone> page1 = invokeGetGenericParentPhonesPage(
			genericParentId, Pagination.of(2, 1));

		List<Phone> phones1 = (List<Phone>)page1.getItems();

		Assert.assertEquals(phones1.toString(), 2, phones1.size());

		Page<Phone> page2 = invokeGetGenericParentPhonesPage(
			genericParentId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Phone> phones2 = (List<Phone>)page2.getItems();

		Assert.assertEquals(phones2.toString(), 1, phones2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(phone1, phone2, phone3),
			new ArrayList<Phone>() {
				{
					addAll(phones1);
					addAll(phones2);
				}
			});
	}

	@Test
	public void testGetPhone() throws Exception {
		Phone postPhone = testGetPhone_addPhone();

		Phone getPhone = invokeGetPhone(postPhone.getId());

		assertEquals(postPhone, getPhone);
		assertValid(getPhone);
	}

	protected void assertEquals(List<Phone> phones1, List<Phone> phones2) {
		Assert.assertEquals(phones1.size(), phones2.size());

		for (int i = 0; i < phones1.size(); i++) {
			Phone phone1 = phones1.get(i);
			Phone phone2 = phones2.get(i);

			assertEquals(phone1, phone2);
		}
	}

	protected void assertEquals(Phone phone1, Phone phone2) {
		Assert.assertTrue(
			phone1 + " does not equal " + phone2, equals(phone1, phone2));
	}

	protected void assertEqualsIgnoringOrder(
		List<Phone> phones1, List<Phone> phones2) {

		Assert.assertEquals(phones1.size(), phones2.size());

		for (Phone phone1 : phones1) {
			boolean contains = false;

			for (Phone phone2 : phones2) {
				if (equals(phone1, phone2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				phones2 + " does not contain " + phone1, contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(Page<Phone> page) {
		boolean valid = false;

		Collection<Phone> phones = page.getItems();

		int size = phones.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Phone phone) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(Phone phone1, Phone phone2) {
		if (phone1 == phone2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_phoneResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_phoneResource;

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
		EntityField entityField, String operator, Phone phone) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("extension")) {
			sb.append("'");
			sb.append(String.valueOf(phone.getExtension()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("phoneNumber")) {
			sb.append("'");
			sb.append(String.valueOf(phone.getPhoneNumber()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("phoneType")) {
			sb.append("'");
			sb.append(String.valueOf(phone.getPhoneType()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Page<Phone> invokeGetGenericParentPhonesPage(
			Object genericParentId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(_resourceURL + _toPath("/phones", genericParentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Phone>>() {
			});
	}

	protected Http.Response invokeGetGenericParentPhonesPageResponse(
			Object genericParentId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(_resourceURL + _toPath("/phones", genericParentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Phone invokeGetPhone(Long phoneId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/phones/{phone-id}", phoneId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Phone.class);
	}

	protected Http.Response invokeGetPhoneResponse(Long phoneId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/phones/{phone-id}", phoneId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Phone randomPhone() {
		return new Phone() {
			{
				extension = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				phoneNumber = RandomTestUtil.randomString();
				phoneType = RandomTestUtil.randomString();
			}
		};
	}

	protected Phone testGetGenericParentPhonesPage_addPhone(
			Object genericParentId, Phone phone)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Object testGetGenericParentPhonesPage_getGenericParentId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Phone testGetPhone_addPhone() throws Exception {
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
	private PhoneResource _phoneResource;

	private URL _resourceURL;

}