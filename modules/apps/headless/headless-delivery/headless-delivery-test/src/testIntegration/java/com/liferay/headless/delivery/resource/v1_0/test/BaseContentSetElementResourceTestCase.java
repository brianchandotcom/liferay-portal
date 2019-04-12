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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.delivery.dto.v1_0.ContentSetElement;
import com.liferay.headless.delivery.resource.v1_0.ContentSetElementResource;
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
public abstract class BaseContentSetElementResourceTestCase {

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
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSetContentSetElementsPage() throws Exception {
		Long contentSetId =
			testGetContentSetContentSetElementsPage_getContentSetId();
		Long irrelevantContentSetId =
			testGetContentSetContentSetElementsPage_getIrrelevantContentSetId();

		if ((irrelevantContentSetId != null)) {
			ContentSetElement irrelevantContentSetElement =
				testGetContentSetContentSetElementsPage_addContentSetElement(
					irrelevantContentSetId,
					randomIrrelevantContentSetElement());

			Page<ContentSetElement> page =
				invokeGetContentSetContentSetElementsPage(
					irrelevantContentSetId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentSetElement),
				(List<ContentSetElement>)page.getItems());
			assertValid(page);
		}

		ContentSetElement contentSetElement1 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		Page<ContentSetElement> page =
			invokeGetContentSetContentSetElementsPage(
				contentSetId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentSetElement1, contentSetElement2),
			(List<ContentSetElement>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSetContentSetElementsPageWithPagination()
		throws Exception {

		Long contentSetId =
			testGetContentSetContentSetElementsPage_getContentSetId();

		ContentSetElement contentSetElement1 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		ContentSetElement contentSetElement3 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		Page<ContentSetElement> page1 =
			invokeGetContentSetContentSetElementsPage(
				contentSetId, Pagination.of(1, 2));

		List<ContentSetElement> contentSetElements1 =
			(List<ContentSetElement>)page1.getItems();

		Assert.assertEquals(
			contentSetElements1.toString(), 2, contentSetElements1.size());

		Page<ContentSetElement> page2 =
			invokeGetContentSetContentSetElementsPage(
				contentSetId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentSetElement> contentSetElements2 =
			(List<ContentSetElement>)page2.getItems();

		Assert.assertEquals(
			contentSetElements2.toString(), 1, contentSetElements2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentSetElement1, contentSetElement2, contentSetElement3),
			new ArrayList<ContentSetElement>() {
				{
					addAll(contentSetElements1);
					addAll(contentSetElements2);
				}
			});
	}

	protected ContentSetElement
			testGetContentSetContentSetElementsPage_addContentSetElement(
				Long contentSetId, ContentSetElement contentSetElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSetContentSetElementsPage_getContentSetId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentSetContentSetElementsPage_getIrrelevantContentSetId()
		throws Exception {

		return null;
	}

	protected Page<ContentSetElement> invokeGetContentSetContentSetElementsPage(
			Long contentSetId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-sets/{contentSetId}/content-set-elements",
					contentSetId);

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
			new TypeReference<Page<ContentSetElement>>() {
			});
	}

	protected Http.Response invokeGetContentSetContentSetElementsPageResponse(
			Long contentSetId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-sets/{contentSetId}/content-set-elements",
					contentSetId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetSiteContentSetByKeyContentSetElementsPage()
		throws Exception {

		Long siteId =
			testGetSiteContentSetByKeyContentSetElementsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteContentSetByKeyContentSetElementsPage_getIrrelevantSiteId();
		String key = testGetSiteContentSetByKeyContentSetElementsPage_getKey();
		String irrelevantKey =
			testGetSiteContentSetByKeyContentSetElementsPage_getIrrelevantKey();

		if ((irrelevantSiteId != null) && (irrelevantKey != null)) {
			ContentSetElement irrelevantContentSetElement =
				testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
					irrelevantSiteId, irrelevantKey,
					randomIrrelevantContentSetElement());

			Page<ContentSetElement> page =
				invokeGetSiteContentSetByKeyContentSetElementsPage(
					irrelevantSiteId, irrelevantKey, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentSetElement),
				(List<ContentSetElement>)page.getItems());
			assertValid(page);
		}

		ContentSetElement contentSetElement1 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		Page<ContentSetElement> page =
			invokeGetSiteContentSetByKeyContentSetElementsPage(
				siteId, key, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentSetElement1, contentSetElement2),
			(List<ContentSetElement>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteContentSetByKeyContentSetElementsPageWithPagination()
		throws Exception {

		Long siteId =
			testGetSiteContentSetByKeyContentSetElementsPage_getSiteId();
		String key = testGetSiteContentSetByKeyContentSetElementsPage_getKey();

		ContentSetElement contentSetElement1 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		ContentSetElement contentSetElement3 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		Page<ContentSetElement> page1 =
			invokeGetSiteContentSetByKeyContentSetElementsPage(
				siteId, key, Pagination.of(1, 2));

		List<ContentSetElement> contentSetElements1 =
			(List<ContentSetElement>)page1.getItems();

		Assert.assertEquals(
			contentSetElements1.toString(), 2, contentSetElements1.size());

		Page<ContentSetElement> page2 =
			invokeGetSiteContentSetByKeyContentSetElementsPage(
				siteId, key, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentSetElement> contentSetElements2 =
			(List<ContentSetElement>)page2.getItems();

		Assert.assertEquals(
			contentSetElements2.toString(), 1, contentSetElements2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentSetElement1, contentSetElement2, contentSetElement3),
			new ArrayList<ContentSetElement>() {
				{
					addAll(contentSetElements1);
					addAll(contentSetElements2);
				}
			});
	}

	protected ContentSetElement
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				Long siteId, String key, ContentSetElement contentSetElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteContentSetByKeyContentSetElementsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetSiteContentSetByKeyContentSetElementsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected String testGetSiteContentSetByKeyContentSetElementsPage_getKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSiteContentSetByKeyContentSetElementsPage_getIrrelevantKey()
		throws Exception {

		return null;
	}

	protected Page<ContentSetElement>
			invokeGetSiteContentSetByKeyContentSetElementsPage(
				Long siteId, String key, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/sites/{siteId}/content-sets/by-key/{key}/content-set-elements",
					siteId, key);

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
			new TypeReference<Page<ContentSetElement>>() {
			});
	}

	protected Http.Response
			invokeGetSiteContentSetByKeyContentSetElementsPageResponse(
				Long siteId, String key, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/sites/{siteId}/content-sets/by-key/{key}/content-set-elements",
					siteId, key);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetSiteContentSetByUuidContentSetElementsPage()
		throws Exception {

		Long siteId =
			testGetSiteContentSetByUuidContentSetElementsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteContentSetByUuidContentSetElementsPage_getIrrelevantSiteId();
		String uuid =
			testGetSiteContentSetByUuidContentSetElementsPage_getUuid();
		String irrelevantUuid =
			testGetSiteContentSetByUuidContentSetElementsPage_getIrrelevantUuid();

		if ((irrelevantSiteId != null) && (irrelevantUuid != null)) {
			ContentSetElement irrelevantContentSetElement =
				testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
					irrelevantSiteId, irrelevantUuid,
					randomIrrelevantContentSetElement());

			Page<ContentSetElement> page =
				invokeGetSiteContentSetByUuidContentSetElementsPage(
					irrelevantSiteId, irrelevantUuid, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentSetElement),
				(List<ContentSetElement>)page.getItems());
			assertValid(page);
		}

		ContentSetElement contentSetElement1 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		Page<ContentSetElement> page =
			invokeGetSiteContentSetByUuidContentSetElementsPage(
				siteId, uuid, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentSetElement1, contentSetElement2),
			(List<ContentSetElement>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteContentSetByUuidContentSetElementsPageWithPagination()
		throws Exception {

		Long siteId =
			testGetSiteContentSetByUuidContentSetElementsPage_getSiteId();
		String uuid =
			testGetSiteContentSetByUuidContentSetElementsPage_getUuid();

		ContentSetElement contentSetElement1 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		ContentSetElement contentSetElement3 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		Page<ContentSetElement> page1 =
			invokeGetSiteContentSetByUuidContentSetElementsPage(
				siteId, uuid, Pagination.of(1, 2));

		List<ContentSetElement> contentSetElements1 =
			(List<ContentSetElement>)page1.getItems();

		Assert.assertEquals(
			contentSetElements1.toString(), 2, contentSetElements1.size());

		Page<ContentSetElement> page2 =
			invokeGetSiteContentSetByUuidContentSetElementsPage(
				siteId, uuid, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentSetElement> contentSetElements2 =
			(List<ContentSetElement>)page2.getItems();

		Assert.assertEquals(
			contentSetElements2.toString(), 1, contentSetElements2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentSetElement1, contentSetElement2, contentSetElement3),
			new ArrayList<ContentSetElement>() {
				{
					addAll(contentSetElements1);
					addAll(contentSetElements2);
				}
			});
	}

	protected ContentSetElement
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				Long siteId, String uuid, ContentSetElement contentSetElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteContentSetByUuidContentSetElementsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetSiteContentSetByUuidContentSetElementsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected String testGetSiteContentSetByUuidContentSetElementsPage_getUuid()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSiteContentSetByUuidContentSetElementsPage_getIrrelevantUuid()
		throws Exception {

		return null;
	}

	protected Page<ContentSetElement>
			invokeGetSiteContentSetByUuidContentSetElementsPage(
				Long siteId, String uuid, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/sites/{siteId}/content-sets/by-uuid/{uuid}/content-set-elements",
					siteId, uuid);

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
			new TypeReference<Page<ContentSetElement>>() {
			});
	}

	protected Http.Response
			invokeGetSiteContentSetByUuidContentSetElementsPageResponse(
				Long siteId, String uuid, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/sites/{siteId}/content-sets/by-uuid/{uuid}/content-set-elements",
					siteId, uuid);

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
		ContentSetElement contentSetElement1,
		ContentSetElement contentSetElement2) {

		Assert.assertTrue(
			contentSetElement1 + " does not equal " + contentSetElement2,
			equals(contentSetElement1, contentSetElement2));
	}

	protected void assertEquals(
		List<ContentSetElement> contentSetElements1,
		List<ContentSetElement> contentSetElements2) {

		Assert.assertEquals(
			contentSetElements1.size(), contentSetElements2.size());

		for (int i = 0; i < contentSetElements1.size(); i++) {
			ContentSetElement contentSetElement1 = contentSetElements1.get(i);
			ContentSetElement contentSetElement2 = contentSetElements2.get(i);

			assertEquals(contentSetElement1, contentSetElement2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ContentSetElement> contentSetElements1,
		List<ContentSetElement> contentSetElements2) {

		Assert.assertEquals(
			contentSetElements1.size(), contentSetElements2.size());

		for (ContentSetElement contentSetElement1 : contentSetElements1) {
			boolean contains = false;

			for (ContentSetElement contentSetElement2 : contentSetElements2) {
				if (equals(contentSetElement1, contentSetElement2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				contentSetElements2 + " does not contain " + contentSetElement1,
				contains);
		}
	}

	protected void assertValid(ContentSetElement contentSetElement) {
		boolean valid = true;

		if (contentSetElement.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (contentSetElement.getContent() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (contentSetElement.getContentType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (contentSetElement.getTitle() == null) {
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

	protected void assertValid(Page<ContentSetElement> page) {
		boolean valid = false;

		Collection<ContentSetElement> contentSetElements = page.getItems();

		int size = contentSetElements.size();

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
		ContentSetElement contentSetElement1,
		ContentSetElement contentSetElement2) {

		if (contentSetElement1 == contentSetElement2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentSetElement1.getContent(),
						contentSetElement2.getContent())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentSetElement1.getContentType(),
						contentSetElement2.getContentType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentSetElement1.getId(),
						contentSetElement2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentSetElement1.getTitle(),
						contentSetElement2.getTitle())) {

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
		if (!(_contentSetElementResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_contentSetElementResource;

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
		ContentSetElement contentSetElement) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("content")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentType")) {
			sb.append("'");
			sb.append(String.valueOf(contentSetElement.getContentType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(contentSetElement.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected ContentSetElement randomContentSetElement() {
		return new ContentSetElement() {
			{
				contentType = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected ContentSetElement randomIrrelevantContentSetElement() {
		ContentSetElement randomIrrelevantContentSetElement =
			randomContentSetElement();

		return randomIrrelevantContentSetElement;
	}

	protected ContentSetElement randomPatchContentSetElement() {
		return randomContentSetElement();
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
				addMixIn(ContentSetElement.class, ContentSetElementMixin.class);
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

	protected static class ContentSetElementMixin {

		@JsonProperty
		Object content;

		@JsonProperty
		String contentType;

		@JsonProperty
		Long id;

		@JsonProperty
		String title;

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
		BaseContentSetElementResourceTestCase.class);

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
	private ContentSetElementResource _contentSetElementResource;

	private URL _resourceURL;

}