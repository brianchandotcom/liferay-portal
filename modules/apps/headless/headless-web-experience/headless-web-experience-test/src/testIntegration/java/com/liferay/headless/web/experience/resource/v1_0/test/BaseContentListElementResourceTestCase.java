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

package com.liferay.headless.web.experience.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.web.experience.dto.v1_0.ContentListElement;
import com.liferay.headless.web.experience.resource.v1_0.ContentListElementResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

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
public abstract class BaseContentListElementResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-web-experience/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentListContentsPage() throws Exception {
		Long contentListId = testGetContentListContentsPage_getContentListId();
		Long irrelevantContentListId =
			testGetContentListContentsPage_getIrrelevantContentListId();

		if ((irrelevantContentListId != null)) {
			ContentListElement irrelevantContentListElement =
				testGetContentListContentsPage_addContentListElement(
					irrelevantContentListId,
					randomIrrelevantContentListElement());

			Page<ContentListElement> page = invokeGetContentListContentsPage(
				irrelevantContentListId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentListElement),
				(List<ContentListElement>)page.getItems());
			assertValid(page);
		}

		ContentListElement contentListElement1 =
			testGetContentListContentsPage_addContentListElement(
				contentListId, randomContentListElement());

		ContentListElement contentListElement2 =
			testGetContentListContentsPage_addContentListElement(
				contentListId, randomContentListElement());

		Page<ContentListElement> page = invokeGetContentListContentsPage(
			contentListId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentListElement1, contentListElement2),
			(List<ContentListElement>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentListContentsPageWithPagination()
		throws Exception {

		Long contentListId = testGetContentListContentsPage_getContentListId();

		ContentListElement contentListElement1 =
			testGetContentListContentsPage_addContentListElement(
				contentListId, randomContentListElement());

		ContentListElement contentListElement2 =
			testGetContentListContentsPage_addContentListElement(
				contentListId, randomContentListElement());

		ContentListElement contentListElement3 =
			testGetContentListContentsPage_addContentListElement(
				contentListId, randomContentListElement());

		Page<ContentListElement> page1 = invokeGetContentListContentsPage(
			contentListId, Pagination.of(1, 2));

		List<ContentListElement> contentListElements1 =
			(List<ContentListElement>)page1.getItems();

		Assert.assertEquals(
			contentListElements1.toString(), 2, contentListElements1.size());

		Page<ContentListElement> page2 = invokeGetContentListContentsPage(
			contentListId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentListElement> contentListElements2 =
			(List<ContentListElement>)page2.getItems();

		Assert.assertEquals(
			contentListElements2.toString(), 1, contentListElements2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentListElement1, contentListElement2, contentListElement3),
			new ArrayList<ContentListElement>() {
				{
					addAll(contentListElements1);
					addAll(contentListElements2);
				}
			});
	}

	protected ContentListElement
			testGetContentListContentsPage_addContentListElement(
				Long contentListId, ContentListElement contentListElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentListContentsPage_getContentListId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentListContentsPage_getIrrelevantContentListId()
		throws Exception {

		return null;
	}

	protected Page<ContentListElement> invokeGetContentListContentsPage(
			Long contentListId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-lists/{content-list-id}/contents", contentListId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<ContentListElement>>() {
			});
	}

	protected Http.Response invokeGetContentListContentsPageResponse(
			Long contentListId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-lists/{content-list-id}/contents", contentListId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

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
		ContentListElement contentListElement1,
		ContentListElement contentListElement2) {

		Assert.assertTrue(
			contentListElement1 + " does not equal " + contentListElement2,
			equals(contentListElement1, contentListElement2));
	}

	protected void assertEquals(
		List<ContentListElement> contentListElements1,
		List<ContentListElement> contentListElements2) {

		Assert.assertEquals(
			contentListElements1.size(), contentListElements2.size());

		for (int i = 0; i < contentListElements1.size(); i++) {
			ContentListElement contentListElement1 = contentListElements1.get(
				i);
			ContentListElement contentListElement2 = contentListElements2.get(
				i);

			assertEquals(contentListElement1, contentListElement2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ContentListElement> contentListElements1,
		List<ContentListElement> contentListElements2) {

		Assert.assertEquals(
			contentListElements1.size(), contentListElements2.size());

		for (ContentListElement contentListElement1 : contentListElements1) {
			boolean contains = false;

			for (ContentListElement contentListElement2 :
					contentListElements2) {

				if (equals(contentListElement1, contentListElement2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				contentListElements2 + " does not contain " +
					contentListElement1,
				contains);
		}
	}

	protected void assertValid(ContentListElement contentListElement) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<ContentListElement> page) {
		boolean valid = false;

		Collection<ContentListElement> contentListElements = page.getItems();

		int size = contentListElements.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		ContentListElement contentListElement1,
		ContentListElement contentListElement2) {

		if (contentListElement1 == contentListElement2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_contentListElementResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_contentListElementResource;

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
		EntityField entityField, String operator,
		ContentListElement contentListElement) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentType")) {
			sb.append("'");
			sb.append(String.valueOf(contentListElement.getContentType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("order")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected ContentListElement randomContentListElement() {
		return new ContentListElement() {
			{
				contentType = RandomTestUtil.randomString();
			}
		};
	}

	protected ContentListElement randomIrrelevantContentListElement() {
		return randomContentListElement();
	}

	protected ContentListElement randomPatchContentListElement() {
		return randomContentListElement();
	}

	protected Group irrelevantGroup;
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

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*\\}", String.valueOf(values[i]));
		}

		return template;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseContentListElementResourceTestCase.class);

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
	private ContentListElementResource _contentListElementResource;

	private URL _resourceURL;

}