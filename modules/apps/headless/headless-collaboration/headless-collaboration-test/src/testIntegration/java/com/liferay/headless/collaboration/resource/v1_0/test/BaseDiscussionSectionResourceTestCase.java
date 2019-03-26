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

package com.liferay.headless.collaboration.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.collaboration.dto.v1_0.DiscussionSection;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionSectionResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
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

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

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
public abstract class BaseDiscussionSectionResourceTestCase {

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

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-collaboration/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceDiscussionSectionsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceDiscussionSectionsPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceDiscussionSectionsPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			DiscussionSection irrelevantDiscussionSection =
				testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
					irrelevantContentSpaceId,
					randomIrrelevantDiscussionSection());

			Page<DiscussionSection> page =
				invokeGetContentSpaceDiscussionSectionsPage(
					irrelevantContentSpaceId, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscussionSection),
				(List<DiscussionSection>)page.getItems());
			assertValid(page);
		}

		DiscussionSection discussionSection1 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, randomDiscussionSection());

		DiscussionSection discussionSection2 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, randomDiscussionSection());

		Page<DiscussionSection> page =
			invokeGetContentSpaceDiscussionSectionsPage(
				contentSpaceId, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discussionSection1, discussionSection2),
			(List<DiscussionSection>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceDiscussionSectionsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDiscussionSectionsPage_getContentSpaceId();

		DiscussionSection discussionSection1 = randomDiscussionSection();
		DiscussionSection discussionSection2 = randomDiscussionSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionSection1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionSection1 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, discussionSection1);

		Thread.sleep(1000);

		discussionSection2 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, discussionSection2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionSection> page =
				invokeGetContentSpaceDiscussionSectionsPage(
					contentSpaceId,
					getFilterString(entityField, "eq", discussionSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionSection1),
				(List<DiscussionSection>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDiscussionSectionsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDiscussionSectionsPage_getContentSpaceId();

		DiscussionSection discussionSection1 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, randomDiscussionSection());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscussionSection discussionSection2 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, randomDiscussionSection());

		for (EntityField entityField : entityFields) {
			Page<DiscussionSection> page =
				invokeGetContentSpaceDiscussionSectionsPage(
					contentSpaceId,
					getFilterString(entityField, "eq", discussionSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionSection1),
				(List<DiscussionSection>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDiscussionSectionsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceDiscussionSectionsPage_getContentSpaceId();

		DiscussionSection discussionSection1 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, randomDiscussionSection());

		DiscussionSection discussionSection2 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, randomDiscussionSection());

		DiscussionSection discussionSection3 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, randomDiscussionSection());

		Page<DiscussionSection> page1 =
			invokeGetContentSpaceDiscussionSectionsPage(
				contentSpaceId, null, Pagination.of(1, 2), null);

		List<DiscussionSection> discussionSections1 =
			(List<DiscussionSection>)page1.getItems();

		Assert.assertEquals(
			discussionSections1.toString(), 2, discussionSections1.size());

		Page<DiscussionSection> page2 =
			invokeGetContentSpaceDiscussionSectionsPage(
				contentSpaceId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscussionSection> discussionSections2 =
			(List<DiscussionSection>)page2.getItems();

		Assert.assertEquals(
			discussionSections2.toString(), 1, discussionSections2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discussionSection1, discussionSection2, discussionSection3),
			new ArrayList<DiscussionSection>() {
				{
					addAll(discussionSections1);
					addAll(discussionSections2);
				}
			});
	}

	@Test
	public void testGetContentSpaceDiscussionSectionsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDiscussionSectionsPage_getContentSpaceId();

		DiscussionSection discussionSection1 = randomDiscussionSection();
		DiscussionSection discussionSection2 = randomDiscussionSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionSection1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionSection1 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, discussionSection1);

		Thread.sleep(1000);

		discussionSection2 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, discussionSection2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionSection> ascPage =
				invokeGetContentSpaceDiscussionSectionsPage(
					contentSpaceId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionSection1, discussionSection2),
				(List<DiscussionSection>)ascPage.getItems());

			Page<DiscussionSection> descPage =
				invokeGetContentSpaceDiscussionSectionsPage(
					contentSpaceId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionSection2, discussionSection1),
				(List<DiscussionSection>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDiscussionSectionsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDiscussionSectionsPage_getContentSpaceId();

		DiscussionSection discussionSection1 = randomDiscussionSection();
		DiscussionSection discussionSection2 = randomDiscussionSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionSection1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				discussionSection2, entityField.getName(), "Bbb");
		}

		discussionSection1 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, discussionSection1);

		discussionSection2 =
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				contentSpaceId, discussionSection2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionSection> ascPage =
				invokeGetContentSpaceDiscussionSectionsPage(
					contentSpaceId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionSection1, discussionSection2),
				(List<DiscussionSection>)ascPage.getItems());

			Page<DiscussionSection> descPage =
				invokeGetContentSpaceDiscussionSectionsPage(
					contentSpaceId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionSection2, discussionSection1),
				(List<DiscussionSection>)descPage.getItems());
		}
	}

	protected DiscussionSection
			testGetContentSpaceDiscussionSectionsPage_addDiscussionSection(
				Long contentSpaceId, DiscussionSection discussionSection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceDiscussionSectionsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceDiscussionSectionsPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<DiscussionSection>
			invokeGetContentSpaceDiscussionSectionsPage(
				Long contentSpaceId, String filterString, Pagination pagination,
				String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/discussion-sections",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<DiscussionSection>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceDiscussionSectionsPageResponse(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/discussion-sections",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostContentSpaceDiscussionSection() throws Exception {
		DiscussionSection randomDiscussionSection = randomDiscussionSection();

		DiscussionSection postDiscussionSection =
			testPostContentSpaceDiscussionSection_addDiscussionSection(
				randomDiscussionSection);

		assertEquals(randomDiscussionSection, postDiscussionSection);
		assertValid(postDiscussionSection);
	}

	protected DiscussionSection
			testPostContentSpaceDiscussionSection_addDiscussionSection(
				DiscussionSection discussionSection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionSection invokePostContentSpaceDiscussionSection(
			Long contentSpaceId, DiscussionSection discussionSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/discussion-sections",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceDiscussionSectionResponse(
			Long contentSpaceId, DiscussionSection discussionSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/discussion-sections",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteDiscussionSection() throws Exception {
		DiscussionSection discussionSection =
			testDeleteDiscussionSection_addDiscussionSection();

		assertResponseCode(
			204,
			invokeDeleteDiscussionSectionResponse(discussionSection.getId()));

		assertResponseCode(
			404, invokeGetDiscussionSectionResponse(discussionSection.getId()));
	}

	protected DiscussionSection
			testDeleteDiscussionSection_addDiscussionSection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteDiscussionSection(Long discussionSectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}",
					discussionSectionId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteDiscussionSectionResponse(
			Long discussionSectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}",
					discussionSectionId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDiscussionSection() throws Exception {
		DiscussionSection postDiscussionSection =
			testGetDiscussionSection_addDiscussionSection();

		DiscussionSection getDiscussionSection = invokeGetDiscussionSection(
			postDiscussionSection.getId());

		assertEquals(postDiscussionSection, getDiscussionSection);
		assertValid(getDiscussionSection);
	}

	protected DiscussionSection testGetDiscussionSection_addDiscussionSection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionSection invokeGetDiscussionSection(
			Long discussionSectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}",
					discussionSectionId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetDiscussionSectionResponse(
			Long discussionSectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}",
					discussionSectionId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchDiscussionSection() throws Exception {
		DiscussionSection postDiscussionSection =
			testPatchDiscussionSection_addDiscussionSection();

		DiscussionSection randomPatchDiscussionSection =
			randomPatchDiscussionSection();

		DiscussionSection patchDiscussionSection = invokePatchDiscussionSection(
			postDiscussionSection.getId(), randomPatchDiscussionSection);

		DiscussionSection expectedPatchDiscussionSection =
			(DiscussionSection)BeanUtils.cloneBean(postDiscussionSection);

		_beanUtilsBean.copyProperties(
			expectedPatchDiscussionSection, randomPatchDiscussionSection);

		DiscussionSection getDiscussionSection = invokeGetDiscussionSection(
			patchDiscussionSection.getId());

		assertEquals(expectedPatchDiscussionSection, getDiscussionSection);
		assertValid(getDiscussionSection);
	}

	protected DiscussionSection
			testPatchDiscussionSection_addDiscussionSection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionSection invokePatchDiscussionSection(
			Long discussionSectionId, DiscussionSection discussionSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}",
					discussionSectionId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchDiscussionSectionResponse(
			Long discussionSectionId, DiscussionSection discussionSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}",
					discussionSectionId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutDiscussionSection() throws Exception {
		DiscussionSection postDiscussionSection =
			testPutDiscussionSection_addDiscussionSection();

		DiscussionSection randomDiscussionSection = randomDiscussionSection();

		DiscussionSection putDiscussionSection = invokePutDiscussionSection(
			postDiscussionSection.getId(), randomDiscussionSection);

		assertEquals(randomDiscussionSection, putDiscussionSection);
		assertValid(putDiscussionSection);

		DiscussionSection getDiscussionSection = invokeGetDiscussionSection(
			putDiscussionSection.getId());

		assertEquals(randomDiscussionSection, getDiscussionSection);
		assertValid(getDiscussionSection);
	}

	protected DiscussionSection testPutDiscussionSection_addDiscussionSection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionSection invokePutDiscussionSection(
			Long discussionSectionId, DiscussionSection discussionSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}",
					discussionSectionId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutDiscussionSectionResponse(
			Long discussionSectionId, DiscussionSection discussionSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}",
					discussionSectionId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDiscussionSectionDiscussionSectionsPage()
		throws Exception {

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionSectionsPage_getDiscussionSectionId();
		Long irrelevantDiscussionSectionId =
			testGetDiscussionSectionDiscussionSectionsPage_getIrrelevantDiscussionSectionId();

		if ((irrelevantDiscussionSectionId != null)) {
			DiscussionSection irrelevantDiscussionSection =
				testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
					irrelevantDiscussionSectionId,
					randomIrrelevantDiscussionSection());

			Page<DiscussionSection> page =
				invokeGetDiscussionSectionDiscussionSectionsPage(
					irrelevantDiscussionSectionId, null, Pagination.of(1, 2),
					null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscussionSection),
				(List<DiscussionSection>)page.getItems());
			assertValid(page);
		}

		DiscussionSection discussionSection1 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, randomDiscussionSection());

		DiscussionSection discussionSection2 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, randomDiscussionSection());

		Page<DiscussionSection> page =
			invokeGetDiscussionSectionDiscussionSectionsPage(
				discussionSectionId, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discussionSection1, discussionSection2),
			(List<DiscussionSection>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDiscussionSectionDiscussionSectionsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionSectionsPage_getDiscussionSectionId();

		DiscussionSection discussionSection1 = randomDiscussionSection();
		DiscussionSection discussionSection2 = randomDiscussionSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionSection1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionSection1 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, discussionSection1);

		Thread.sleep(1000);

		discussionSection2 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, discussionSection2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionSection> page =
				invokeGetDiscussionSectionDiscussionSectionsPage(
					discussionSectionId,
					getFilterString(entityField, "eq", discussionSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionSection1),
				(List<DiscussionSection>)page.getItems());
		}
	}

	@Test
	public void testGetDiscussionSectionDiscussionSectionsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionSectionsPage_getDiscussionSectionId();

		DiscussionSection discussionSection1 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, randomDiscussionSection());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscussionSection discussionSection2 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, randomDiscussionSection());

		for (EntityField entityField : entityFields) {
			Page<DiscussionSection> page =
				invokeGetDiscussionSectionDiscussionSectionsPage(
					discussionSectionId,
					getFilterString(entityField, "eq", discussionSection1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discussionSection1),
				(List<DiscussionSection>)page.getItems());
		}
	}

	@Test
	public void testGetDiscussionSectionDiscussionSectionsPageWithPagination()
		throws Exception {

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionSectionsPage_getDiscussionSectionId();

		DiscussionSection discussionSection1 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, randomDiscussionSection());

		DiscussionSection discussionSection2 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, randomDiscussionSection());

		DiscussionSection discussionSection3 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, randomDiscussionSection());

		Page<DiscussionSection> page1 =
			invokeGetDiscussionSectionDiscussionSectionsPage(
				discussionSectionId, null, Pagination.of(1, 2), null);

		List<DiscussionSection> discussionSections1 =
			(List<DiscussionSection>)page1.getItems();

		Assert.assertEquals(
			discussionSections1.toString(), 2, discussionSections1.size());

		Page<DiscussionSection> page2 =
			invokeGetDiscussionSectionDiscussionSectionsPage(
				discussionSectionId, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscussionSection> discussionSections2 =
			(List<DiscussionSection>)page2.getItems();

		Assert.assertEquals(
			discussionSections2.toString(), 1, discussionSections2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discussionSection1, discussionSection2, discussionSection3),
			new ArrayList<DiscussionSection>() {
				{
					addAll(discussionSections1);
					addAll(discussionSections2);
				}
			});
	}

	@Test
	public void testGetDiscussionSectionDiscussionSectionsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionSectionsPage_getDiscussionSectionId();

		DiscussionSection discussionSection1 = randomDiscussionSection();
		DiscussionSection discussionSection2 = randomDiscussionSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionSection1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		discussionSection1 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, discussionSection1);

		Thread.sleep(1000);

		discussionSection2 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, discussionSection2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionSection> ascPage =
				invokeGetDiscussionSectionDiscussionSectionsPage(
					discussionSectionId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionSection1, discussionSection2),
				(List<DiscussionSection>)ascPage.getItems());

			Page<DiscussionSection> descPage =
				invokeGetDiscussionSectionDiscussionSectionsPage(
					discussionSectionId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionSection2, discussionSection1),
				(List<DiscussionSection>)descPage.getItems());
		}
	}

	@Test
	public void testGetDiscussionSectionDiscussionSectionsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long discussionSectionId =
			testGetDiscussionSectionDiscussionSectionsPage_getDiscussionSectionId();

		DiscussionSection discussionSection1 = randomDiscussionSection();
		DiscussionSection discussionSection2 = randomDiscussionSection();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				discussionSection1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				discussionSection2, entityField.getName(), "Bbb");
		}

		discussionSection1 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, discussionSection1);

		discussionSection2 =
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				discussionSectionId, discussionSection2);

		for (EntityField entityField : entityFields) {
			Page<DiscussionSection> ascPage =
				invokeGetDiscussionSectionDiscussionSectionsPage(
					discussionSectionId, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discussionSection1, discussionSection2),
				(List<DiscussionSection>)ascPage.getItems());

			Page<DiscussionSection> descPage =
				invokeGetDiscussionSectionDiscussionSectionsPage(
					discussionSectionId, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discussionSection2, discussionSection1),
				(List<DiscussionSection>)descPage.getItems());
		}
	}

	protected DiscussionSection
			testGetDiscussionSectionDiscussionSectionsPage_addDiscussionSection(
				Long discussionSectionId, DiscussionSection discussionSection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDiscussionSectionDiscussionSectionsPage_getDiscussionSectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDiscussionSectionDiscussionSectionsPage_getIrrelevantDiscussionSectionId()
		throws Exception {

		return null;
	}

	protected Page<DiscussionSection>
			invokeGetDiscussionSectionDiscussionSectionsPage(
				Long discussionSectionId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}/discussion-sections",
					discussionSectionId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<DiscussionSection>>() {
			});
	}

	protected Http.Response
			invokeGetDiscussionSectionDiscussionSectionsPageResponse(
				Long discussionSectionId, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}/discussion-sections",
					discussionSectionId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostDiscussionSectionDiscussionSection() throws Exception {
		DiscussionSection randomDiscussionSection = randomDiscussionSection();

		DiscussionSection postDiscussionSection =
			testPostDiscussionSectionDiscussionSection_addDiscussionSection(
				randomDiscussionSection);

		assertEquals(randomDiscussionSection, postDiscussionSection);
		assertValid(postDiscussionSection);
	}

	protected DiscussionSection
			testPostDiscussionSectionDiscussionSection_addDiscussionSection(
				DiscussionSection discussionSection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DiscussionSection invokePostDiscussionSectionDiscussionSection(
			Long discussionSectionId, DiscussionSection discussionSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}/discussion-sections",
					discussionSectionId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(
				string, DiscussionSection.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokePostDiscussionSectionDiscussionSectionResponse(
				Long discussionSectionId, DiscussionSection discussionSection)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(discussionSection),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/discussion-sections/{discussion-section-id}/discussion-sections",
					discussionSectionId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		DiscussionSection discussionSection1,
		DiscussionSection discussionSection2) {

		Assert.assertTrue(
			discussionSection1 + " does not equal " + discussionSection2,
			equals(discussionSection1, discussionSection2));
	}

	protected void assertEquals(
		List<DiscussionSection> discussionSections1,
		List<DiscussionSection> discussionSections2) {

		Assert.assertEquals(
			discussionSections1.size(), discussionSections2.size());

		for (int i = 0; i < discussionSections1.size(); i++) {
			DiscussionSection discussionSection1 = discussionSections1.get(i);
			DiscussionSection discussionSection2 = discussionSections2.get(i);

			assertEquals(discussionSection1, discussionSection2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscussionSection> discussionSections1,
		List<DiscussionSection> discussionSections2) {

		Assert.assertEquals(
			discussionSections1.size(), discussionSections2.size());

		for (DiscussionSection discussionSection1 : discussionSections1) {
			boolean contains = false;

			for (DiscussionSection discussionSection2 : discussionSections2) {
				if (equals(discussionSection1, discussionSection2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discussionSections2 + " does not contain " + discussionSection1,
				contains);
		}
	}

	protected void assertValid(DiscussionSection discussionSection) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<DiscussionSection> page) {
		boolean valid = false;

		Collection<DiscussionSection> discussionSections = page.getItems();

		int size = discussionSections.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		DiscussionSection discussionSection1,
		DiscussionSection discussionSection2) {

		if (discussionSection1 == discussionSection2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_discussionSectionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discussionSectionResource;

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
		DiscussionSection discussionSection) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentSpaceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(discussionSection.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(discussionSection.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(discussionSection.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfDiscussionSections")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfDiscussionThreads")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(discussionSection.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected DiscussionSection randomDiscussionSection() {
		return new DiscussionSection() {
			{
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected DiscussionSection randomIrrelevantDiscussionSection() {
		return randomDiscussionSection();
	}

	protected DiscussionSection randomPatchDiscussionSection() {
		return randomDiscussionSection();
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
		BaseDiscussionSectionResourceTestCase.class);

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
	private DiscussionSectionResource _discussionSectionResource;

	private URL _resourceURL;

}