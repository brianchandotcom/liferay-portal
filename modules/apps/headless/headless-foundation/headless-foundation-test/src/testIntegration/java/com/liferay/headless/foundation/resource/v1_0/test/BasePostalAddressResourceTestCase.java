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

import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.headless.foundation.resource.v1_0.PostalAddressResource;
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
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetGenericParentPostalAddressesPage() throws Exception {
		Object genericParentId =
			testGetGenericParentPostalAddressesPage_getGenericParentId();

		PostalAddress postalAddress1 =
			testGetGenericParentPostalAddressesPage_addPostalAddress(
				genericParentId, randomPostalAddress());
		PostalAddress postalAddress2 =
			testGetGenericParentPostalAddressesPage_addPostalAddress(
				genericParentId, randomPostalAddress());

		Page<PostalAddress> page = invokeGetGenericParentPostalAddressesPage(
			genericParentId, Pagination.of(2, 1));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(postalAddress1, postalAddress2),
			(List<PostalAddress>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetGenericParentPostalAddressesPageWithPagination()
		throws Exception {

		Object genericParentId =
			testGetGenericParentPostalAddressesPage_getGenericParentId();

		PostalAddress postalAddress1 =
			testGetGenericParentPostalAddressesPage_addPostalAddress(
				genericParentId, randomPostalAddress());
		PostalAddress postalAddress2 =
			testGetGenericParentPostalAddressesPage_addPostalAddress(
				genericParentId, randomPostalAddress());
		PostalAddress postalAddress3 =
			testGetGenericParentPostalAddressesPage_addPostalAddress(
				genericParentId, randomPostalAddress());

		Page<PostalAddress> page1 = invokeGetGenericParentPostalAddressesPage(
			genericParentId, Pagination.of(2, 1));

		List<PostalAddress> postalAddresses1 =
			(List<PostalAddress>)page1.getItems();

		Assert.assertEquals(
			postalAddresses1.toString(), 2, postalAddresses1.size());

		Page<PostalAddress> page2 = invokeGetGenericParentPostalAddressesPage(
			genericParentId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PostalAddress> postalAddresses2 =
			(List<PostalAddress>)page2.getItems();

		Assert.assertEquals(
			postalAddresses2.toString(), 1, postalAddresses2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(postalAddress1, postalAddress2, postalAddress3),
			new ArrayList<PostalAddress>() {
				{
					addAll(postalAddresses1);
					addAll(postalAddresses2);
				}
			});
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

	protected void assertEquals(
		PostalAddress postalAddress1, PostalAddress postalAddress2) {

		Assert.assertTrue(
			postalAddress1 + " does not equal " + postalAddress2,
			equals(postalAddress1, postalAddress2));
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

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(Page<PostalAddress> page) {
		boolean valid = false;

		Collection<PostalAddress> postalAddresses = page.getItems();

		int size = postalAddresses.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(PostalAddress postalAddress) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(
		PostalAddress postalAddress1, PostalAddress postalAddress2) {

		if (postalAddress1 == postalAddress2) {
			return true;
		}

		return false;
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

	protected Page<PostalAddress> invokeGetGenericParentPostalAddressesPage(
			Object genericParentId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/postal-addresses", genericParentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<PostalAddress>>() {
			});
	}

	protected Http.Response invokeGetGenericParentPostalAddressesPageResponse(
			Object genericParentId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/postal-addresses", genericParentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected PostalAddress invokeGetPostalAddress(Long postalAddressId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/postal-addresses/{postal-address-id}", postalAddressId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), PostalAddress.class);
	}

	protected Http.Response invokeGetPostalAddressResponse(Long postalAddressId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/postal-addresses/{postal-address-id}", postalAddressId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
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
				streetAddressLine1 = RandomTestUtil.randomString();
				streetAddressLine2 = RandomTestUtil.randomString();
				streetAddressLine3 = RandomTestUtil.randomString();
			}
		};
	}

	protected PostalAddress
			testGetGenericParentPostalAddressesPage_addPostalAddress(
				Object genericParentId, PostalAddress postalAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Object
			testGetGenericParentPostalAddressesPage_getGenericParentId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected PostalAddress testGetPostalAddress_addPostalAddress()
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
	private PostalAddressResource _postalAddressResource;

	private URL _resourceURL;

}