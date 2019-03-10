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

package com.liferay.bulk.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.bulk.rest.dto.v1_0.BulkStatus;
import com.liferay.bulk.rest.resource.v1_0.BulkStatusResource;
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
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
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
public abstract class BaseBulkStatusResourceTestCase {

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

		_resourceURL = new URL("http://localhost:8080/o/bulk/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetStatu() throws Exception {
		BulkStatus postBulkStatus = testGetStatu_addBulkStatus();

		BulkStatus getBulkStatus = invokeGetStatu(postBulkStatus.getId());

		assertEquals(postBulkStatus, getBulkStatus);
		assertValid(getBulkStatus);
	}

	protected BulkStatus testGetStatu_addBulkStatus() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BulkStatus invokeGetStatu(Long param) throws Exception {
		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/status", param);

		options.setLocation(location);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BulkStatus.class);
	}

	protected Http.Response invokeGetStatuResponse(Long param)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/status", param);

		options.setLocation(location);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		BulkStatus bulkStatus1, BulkStatus bulkStatus2) {

		Assert.assertTrue(
			bulkStatus1 + " does not equal " + bulkStatus2,
			equals(bulkStatus1, bulkStatus2));
	}

	protected void assertEquals(
		List<BulkStatus> bulkStatuses1, List<BulkStatus> bulkStatuses2) {

		Assert.assertEquals(bulkStatuses1.size(), bulkStatuses2.size());

		for (int i = 0; i < bulkStatuses1.size(); i++) {
			BulkStatus bulkStatus1 = bulkStatuses1.get(i);
			BulkStatus bulkStatus2 = bulkStatuses2.get(i);

			assertEquals(bulkStatus1, bulkStatus2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<BulkStatus> bulkStatuses1, List<BulkStatus> bulkStatuses2) {

		Assert.assertEquals(bulkStatuses1.size(), bulkStatuses2.size());

		for (BulkStatus bulkStatus1 : bulkStatuses1) {
			boolean contains = false;

			for (BulkStatus bulkStatus2 : bulkStatuses2) {
				if (equals(bulkStatus1, bulkStatus2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				bulkStatuses2 + " does not contain " + bulkStatus1, contains);
		}
	}

	protected void assertValid(BulkStatus bulkStatus) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<BulkStatus> page) {
		boolean valid = false;

		Collection<BulkStatus> bulkStatuses = page.getItems();

		int size = bulkStatuses.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(BulkStatus bulkStatus1, BulkStatus bulkStatus2) {
		if (bulkStatus1 == bulkStatus2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_bulkStatusResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_bulkStatusResource;

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
		EntityField entityField, String operator, BulkStatus bulkStatus) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("busy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected BulkStatus randomBulkStatus() {
		return new BulkStatus() {
			{
				busy = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected Group testGroup;

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
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
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
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
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

	@Inject
	private BulkStatusResource _bulkStatusResource;

	private URL _resourceURL;

}