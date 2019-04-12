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

import com.liferay.headless.admin.user.dto.v1_0.SegmentUser;
import com.liferay.headless.admin.user.resource.v1_0.SegmentUserResource;
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
import com.liferay.portal.vulcan.pagination.Pagination;
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
public abstract class BaseSegmentUserResourceTestCase {

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
	public void testGetSegmentUserAccountsPage() throws Exception {
		Long segmentId = testGetSegmentUserAccountsPage_getSegmentId();
		Long irrelevantSegmentId =
			testGetSegmentUserAccountsPage_getIrrelevantSegmentId();

		if ((irrelevantSegmentId != null)) {
			SegmentUser irrelevantSegmentUser =
				testGetSegmentUserAccountsPage_addSegmentUser(
					irrelevantSegmentId, randomIrrelevantSegmentUser());

			Page<SegmentUser> page = invokeGetSegmentUserAccountsPage(
				irrelevantSegmentId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSegmentUser),
				(List<SegmentUser>)page.getItems());
			assertValid(page);
		}

		SegmentUser segmentUser1 =
			testGetSegmentUserAccountsPage_addSegmentUser(
				segmentId, randomSegmentUser());

		SegmentUser segmentUser2 =
			testGetSegmentUserAccountsPage_addSegmentUser(
				segmentId, randomSegmentUser());

		Page<SegmentUser> page = invokeGetSegmentUserAccountsPage(
			segmentId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(segmentUser1, segmentUser2),
			(List<SegmentUser>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSegmentUserAccountsPageWithPagination()
		throws Exception {

		Long segmentId = testGetSegmentUserAccountsPage_getSegmentId();

		SegmentUser segmentUser1 =
			testGetSegmentUserAccountsPage_addSegmentUser(
				segmentId, randomSegmentUser());

		SegmentUser segmentUser2 =
			testGetSegmentUserAccountsPage_addSegmentUser(
				segmentId, randomSegmentUser());

		SegmentUser segmentUser3 =
			testGetSegmentUserAccountsPage_addSegmentUser(
				segmentId, randomSegmentUser());

		Page<SegmentUser> page1 = invokeGetSegmentUserAccountsPage(
			segmentId, Pagination.of(1, 2));

		List<SegmentUser> segmentUsers1 = (List<SegmentUser>)page1.getItems();

		Assert.assertEquals(segmentUsers1.toString(), 2, segmentUsers1.size());

		Page<SegmentUser> page2 = invokeGetSegmentUserAccountsPage(
			segmentId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<SegmentUser> segmentUsers2 = (List<SegmentUser>)page2.getItems();

		Assert.assertEquals(segmentUsers2.toString(), 1, segmentUsers2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(segmentUser1, segmentUser2, segmentUser3),
			new ArrayList<SegmentUser>() {
				{
					addAll(segmentUsers1);
					addAll(segmentUsers2);
				}
			});
	}

	protected SegmentUser testGetSegmentUserAccountsPage_addSegmentUser(
			Long segmentId, SegmentUser segmentUser)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSegmentUserAccountsPage_getSegmentId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSegmentUserAccountsPage_getIrrelevantSegmentId()
		throws Exception {

		return null;
	}

	protected Page<SegmentUser> invokeGetSegmentUserAccountsPage(
			Long segmentId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/segments/{segmentId}/user-accounts", segmentId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<SegmentUser>>() {
			});
	}

	protected Http.Response invokeGetSegmentUserAccountsPageResponse(
			Long segmentId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/segments/{segmentId}/user-accounts", segmentId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

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
		SegmentUser segmentUser1, SegmentUser segmentUser2) {

		Assert.assertTrue(
			segmentUser1 + " does not equal " + segmentUser2,
			equals(segmentUser1, segmentUser2));
	}

	protected void assertEquals(
		List<SegmentUser> segmentUsers1, List<SegmentUser> segmentUsers2) {

		Assert.assertEquals(segmentUsers1.size(), segmentUsers2.size());

		for (int i = 0; i < segmentUsers1.size(); i++) {
			SegmentUser segmentUser1 = segmentUsers1.get(i);
			SegmentUser segmentUser2 = segmentUsers2.get(i);

			assertEquals(segmentUser1, segmentUser2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<SegmentUser> segmentUsers1, List<SegmentUser> segmentUsers2) {

		Assert.assertEquals(segmentUsers1.size(), segmentUsers2.size());

		for (SegmentUser segmentUser1 : segmentUsers1) {
			boolean contains = false;

			for (SegmentUser segmentUser2 : segmentUsers2) {
				if (equals(segmentUser1, segmentUser2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				segmentUsers2 + " does not contain " + segmentUser1, contains);
		}
	}

	protected void assertValid(SegmentUser segmentUser) {
		boolean valid = true;

		if (segmentUser.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("email", additionalAssertFieldName)) {
				if (segmentUser.getEmail() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (segmentUser.getName() == null) {
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

	protected void assertValid(Page<SegmentUser> page) {
		boolean valid = false;

		Collection<SegmentUser> segmentUsers = page.getItems();

		int size = segmentUsers.size();

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
		SegmentUser segmentUser1, SegmentUser segmentUser2) {

		if (segmentUser1 == segmentUser2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("email", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						segmentUser1.getEmail(), segmentUser2.getEmail())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						segmentUser1.getId(), segmentUser2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						segmentUser1.getName(), segmentUser2.getName())) {

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
		if (!(_segmentUserResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_segmentUserResource;

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
		EntityField entityField, String operator, SegmentUser segmentUser) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("email")) {
			sb.append("'");
			sb.append(String.valueOf(segmentUser.getEmail()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(segmentUser.getName()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected SegmentUser randomSegmentUser() {
		return new SegmentUser() {
			{
				email = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected SegmentUser randomIrrelevantSegmentUser() {
		SegmentUser randomIrrelevantSegmentUser = randomSegmentUser();

		return randomIrrelevantSegmentUser;
	}

	protected SegmentUser randomPatchSegmentUser() {
		return randomSegmentUser();
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
				addMixIn(SegmentUser.class, SegmentUserMixin.class);
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

	protected static class SegmentUserMixin {

		@JsonProperty
		String email;

		@JsonProperty
		Long id;

		@JsonProperty
		String name;

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
		BaseSegmentUserResourceTestCase.class);

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
	private SegmentUserResource _segmentUserResource;

	private URL _resourceURL;

}