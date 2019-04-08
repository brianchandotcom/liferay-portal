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

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
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
public abstract class BaseKnowledgeBaseFolderResourceTestCase {

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
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceKnowledgeBaseFoldersPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceKnowledgeBaseFoldersPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceKnowledgeBaseFoldersPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			KnowledgeBaseFolder irrelevantKnowledgeBaseFolder =
				testGetContentSpaceKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
					irrelevantContentSpaceId,
					randomIrrelevantKnowledgeBaseFolder());

			Page<KnowledgeBaseFolder> page =
				invokeGetContentSpaceKnowledgeBaseFoldersPage(
					irrelevantContentSpaceId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseFolder),
				(List<KnowledgeBaseFolder>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseFolder knowledgeBaseFolder1 =
			testGetContentSpaceKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				contentSpaceId, randomKnowledgeBaseFolder());

		KnowledgeBaseFolder knowledgeBaseFolder2 =
			testGetContentSpaceKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				contentSpaceId, randomKnowledgeBaseFolder());

		Page<KnowledgeBaseFolder> page =
			invokeGetContentSpaceKnowledgeBaseFoldersPage(
				contentSpaceId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseFolder1, knowledgeBaseFolder2),
			(List<KnowledgeBaseFolder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceKnowledgeBaseFoldersPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceKnowledgeBaseFoldersPage_getContentSpaceId();

		KnowledgeBaseFolder knowledgeBaseFolder1 =
			testGetContentSpaceKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				contentSpaceId, randomKnowledgeBaseFolder());

		KnowledgeBaseFolder knowledgeBaseFolder2 =
			testGetContentSpaceKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				contentSpaceId, randomKnowledgeBaseFolder());

		KnowledgeBaseFolder knowledgeBaseFolder3 =
			testGetContentSpaceKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				contentSpaceId, randomKnowledgeBaseFolder());

		Page<KnowledgeBaseFolder> page1 =
			invokeGetContentSpaceKnowledgeBaseFoldersPage(
				contentSpaceId, Pagination.of(1, 2));

		List<KnowledgeBaseFolder> knowledgeBaseFolders1 =
			(List<KnowledgeBaseFolder>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseFolders1.toString(), 2, knowledgeBaseFolders1.size());

		Page<KnowledgeBaseFolder> page2 =
			invokeGetContentSpaceKnowledgeBaseFoldersPage(
				contentSpaceId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseFolder> knowledgeBaseFolders2 =
			(List<KnowledgeBaseFolder>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseFolders2.toString(), 1, knowledgeBaseFolders2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseFolder1, knowledgeBaseFolder2,
				knowledgeBaseFolder3),
			new ArrayList<KnowledgeBaseFolder>() {
				{
					addAll(knowledgeBaseFolders1);
					addAll(knowledgeBaseFolders2);
				}
			});
	}

	protected KnowledgeBaseFolder
			testGetContentSpaceKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				Long contentSpaceId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentSpaceKnowledgeBaseFoldersPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceKnowledgeBaseFoldersPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<KnowledgeBaseFolder>
			invokeGetContentSpaceKnowledgeBaseFoldersPage(
				Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/knowledge-base-folders",
					contentSpaceId);

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
			new TypeReference<Page<KnowledgeBaseFolder>>() {
			});
	}

	protected Http.Response
			invokeGetContentSpaceKnowledgeBaseFoldersPageResponse(
				Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/knowledge-base-folders",
					contentSpaceId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostContentSpaceKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder randomKnowledgeBaseFolder =
			randomKnowledgeBaseFolder();

		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testPostContentSpaceKnowledgeBaseFolder_addKnowledgeBaseFolder(
				randomKnowledgeBaseFolder);

		assertEquals(randomKnowledgeBaseFolder, postKnowledgeBaseFolder);
		assertValid(postKnowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			testPostContentSpaceKnowledgeBaseFolder_addKnowledgeBaseFolder(
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseFolder invokePostContentSpaceKnowledgeBaseFolder(
			Long contentSpaceId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/knowledge-base-folders",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceKnowledgeBaseFolderResponse(
			Long contentSpaceId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/knowledge-base-folders",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder knowledgeBaseFolder =
			testDeleteKnowledgeBaseFolder_addKnowledgeBaseFolder();

		assertResponseCode(
			204,
			invokeDeleteKnowledgeBaseFolderResponse(
				knowledgeBaseFolder.getId()));

		assertResponseCode(
			404,
			invokeGetKnowledgeBaseFolderResponse(knowledgeBaseFolder.getId()));
	}

	protected KnowledgeBaseFolder
			testDeleteKnowledgeBaseFolder_addKnowledgeBaseFolder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteKnowledgeBaseFolder(Long knowledgeBaseFolderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}",
					knowledgeBaseFolderId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteKnowledgeBaseFolderResponse(
			Long knowledgeBaseFolderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}",
					knowledgeBaseFolderId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testGetKnowledgeBaseFolder_addKnowledgeBaseFolder();

		KnowledgeBaseFolder getKnowledgeBaseFolder =
			invokeGetKnowledgeBaseFolder(postKnowledgeBaseFolder.getId());

		assertEquals(postKnowledgeBaseFolder, getKnowledgeBaseFolder);
		assertValid(getKnowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			testGetKnowledgeBaseFolder_addKnowledgeBaseFolder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseFolder invokeGetKnowledgeBaseFolder(
			Long knowledgeBaseFolderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}",
					knowledgeBaseFolderId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetKnowledgeBaseFolderResponse(
			Long knowledgeBaseFolderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}",
					knowledgeBaseFolderId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testPatchKnowledgeBaseFolder_addKnowledgeBaseFolder();

		KnowledgeBaseFolder randomPatchKnowledgeBaseFolder =
			randomPatchKnowledgeBaseFolder();

		KnowledgeBaseFolder patchKnowledgeBaseFolder =
			invokePatchKnowledgeBaseFolder(
				postKnowledgeBaseFolder.getId(),
				randomPatchKnowledgeBaseFolder);

		KnowledgeBaseFolder expectedPatchKnowledgeBaseFolder =
			(KnowledgeBaseFolder)BeanUtils.cloneBean(postKnowledgeBaseFolder);

		_beanUtilsBean.copyProperties(
			expectedPatchKnowledgeBaseFolder, randomPatchKnowledgeBaseFolder);

		KnowledgeBaseFolder getKnowledgeBaseFolder =
			invokeGetKnowledgeBaseFolder(patchKnowledgeBaseFolder.getId());

		assertEquals(expectedPatchKnowledgeBaseFolder, getKnowledgeBaseFolder);
		assertValid(getKnowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			testPatchKnowledgeBaseFolder_addKnowledgeBaseFolder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseFolder invokePatchKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}",
					knowledgeBaseFolderId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchKnowledgeBaseFolderResponse(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}",
					knowledgeBaseFolderId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testPutKnowledgeBaseFolder_addKnowledgeBaseFolder();

		KnowledgeBaseFolder randomKnowledgeBaseFolder =
			randomKnowledgeBaseFolder();

		KnowledgeBaseFolder putKnowledgeBaseFolder =
			invokePutKnowledgeBaseFolder(
				postKnowledgeBaseFolder.getId(), randomKnowledgeBaseFolder);

		assertEquals(randomKnowledgeBaseFolder, putKnowledgeBaseFolder);
		assertValid(putKnowledgeBaseFolder);

		KnowledgeBaseFolder getKnowledgeBaseFolder =
			invokeGetKnowledgeBaseFolder(putKnowledgeBaseFolder.getId());

		assertEquals(randomKnowledgeBaseFolder, getKnowledgeBaseFolder);
		assertValid(getKnowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			testPutKnowledgeBaseFolder_addKnowledgeBaseFolder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseFolder invokePutKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}",
					knowledgeBaseFolderId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutKnowledgeBaseFolderResponse(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{knowledgeBaseFolderId}",
					knowledgeBaseFolderId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage()
		throws Exception {

		Long parentKnowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getParentKnowledgeBaseFolderId();
		Long irrelevantParentKnowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getIrrelevantParentKnowledgeBaseFolderId();

		if ((irrelevantParentKnowledgeBaseFolderId != null)) {
			KnowledgeBaseFolder irrelevantKnowledgeBaseFolder =
				testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
					irrelevantParentKnowledgeBaseFolderId,
					randomIrrelevantKnowledgeBaseFolder());

			Page<KnowledgeBaseFolder> page =
				invokeGetKnowledgeBaseFolderKnowledgeBaseFoldersPage(
					irrelevantParentKnowledgeBaseFolderId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseFolder),
				(List<KnowledgeBaseFolder>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseFolder knowledgeBaseFolder1 =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				parentKnowledgeBaseFolderId, randomKnowledgeBaseFolder());

		KnowledgeBaseFolder knowledgeBaseFolder2 =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				parentKnowledgeBaseFolderId, randomKnowledgeBaseFolder());

		Page<KnowledgeBaseFolder> page =
			invokeGetKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				parentKnowledgeBaseFolderId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseFolder1, knowledgeBaseFolder2),
			(List<KnowledgeBaseFolder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseFoldersPageWithPagination()
		throws Exception {

		Long parentKnowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getParentKnowledgeBaseFolderId();

		KnowledgeBaseFolder knowledgeBaseFolder1 =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				parentKnowledgeBaseFolderId, randomKnowledgeBaseFolder());

		KnowledgeBaseFolder knowledgeBaseFolder2 =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				parentKnowledgeBaseFolderId, randomKnowledgeBaseFolder());

		KnowledgeBaseFolder knowledgeBaseFolder3 =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				parentKnowledgeBaseFolderId, randomKnowledgeBaseFolder());

		Page<KnowledgeBaseFolder> page1 =
			invokeGetKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				parentKnowledgeBaseFolderId, Pagination.of(1, 2));

		List<KnowledgeBaseFolder> knowledgeBaseFolders1 =
			(List<KnowledgeBaseFolder>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseFolders1.toString(), 2, knowledgeBaseFolders1.size());

		Page<KnowledgeBaseFolder> page2 =
			invokeGetKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				parentKnowledgeBaseFolderId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseFolder> knowledgeBaseFolders2 =
			(List<KnowledgeBaseFolder>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseFolders2.toString(), 1, knowledgeBaseFolders2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseFolder1, knowledgeBaseFolder2,
				knowledgeBaseFolder3),
			new ArrayList<KnowledgeBaseFolder>() {
				{
					addAll(knowledgeBaseFolders1);
					addAll(knowledgeBaseFolders2);
				}
			});
	}

	protected KnowledgeBaseFolder
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				Long parentKnowledgeBaseFolderId,
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getParentKnowledgeBaseFolderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getIrrelevantParentKnowledgeBaseFolderId()
		throws Exception {

		return null;
	}

	protected Page<KnowledgeBaseFolder>
			invokeGetKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				Long parentKnowledgeBaseFolderId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders",
					parentKnowledgeBaseFolderId);

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
			new TypeReference<Page<KnowledgeBaseFolder>>() {
			});
	}

	protected Http.Response
			invokeGetKnowledgeBaseFolderKnowledgeBaseFoldersPageResponse(
				Long parentKnowledgeBaseFolderId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders",
					parentKnowledgeBaseFolderId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostKnowledgeBaseFolderKnowledgeBaseFolder()
		throws Exception {

		KnowledgeBaseFolder randomKnowledgeBaseFolder =
			randomKnowledgeBaseFolder();

		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testPostKnowledgeBaseFolderKnowledgeBaseFolder_addKnowledgeBaseFolder(
				randomKnowledgeBaseFolder);

		assertEquals(randomKnowledgeBaseFolder, postKnowledgeBaseFolder);
		assertValid(postKnowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			testPostKnowledgeBaseFolderKnowledgeBaseFolder_addKnowledgeBaseFolder(
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseFolder
			invokePostKnowledgeBaseFolderKnowledgeBaseFolder(
				Long parentKnowledgeBaseFolderId,
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders",
					parentKnowledgeBaseFolderId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(
				string, KnowledgeBaseFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response
			invokePostKnowledgeBaseFolderKnowledgeBaseFolderResponse(
				Long parentKnowledgeBaseFolderId,
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders",
					parentKnowledgeBaseFolderId);

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
		KnowledgeBaseFolder knowledgeBaseFolder1,
		KnowledgeBaseFolder knowledgeBaseFolder2) {

		Assert.assertTrue(
			knowledgeBaseFolder1 + " does not equal " + knowledgeBaseFolder2,
			equals(knowledgeBaseFolder1, knowledgeBaseFolder2));
	}

	protected void assertEquals(
		List<KnowledgeBaseFolder> knowledgeBaseFolders1,
		List<KnowledgeBaseFolder> knowledgeBaseFolders2) {

		Assert.assertEquals(
			knowledgeBaseFolders1.size(), knowledgeBaseFolders2.size());

		for (int i = 0; i < knowledgeBaseFolders1.size(); i++) {
			KnowledgeBaseFolder knowledgeBaseFolder1 =
				knowledgeBaseFolders1.get(i);
			KnowledgeBaseFolder knowledgeBaseFolder2 =
				knowledgeBaseFolders2.get(i);

			assertEquals(knowledgeBaseFolder1, knowledgeBaseFolder2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<KnowledgeBaseFolder> knowledgeBaseFolders1,
		List<KnowledgeBaseFolder> knowledgeBaseFolders2) {

		Assert.assertEquals(
			knowledgeBaseFolders1.size(), knowledgeBaseFolders2.size());

		for (KnowledgeBaseFolder knowledgeBaseFolder1 : knowledgeBaseFolders1) {
			boolean contains = false;

			for (KnowledgeBaseFolder knowledgeBaseFolder2 :
					knowledgeBaseFolders2) {

				if (equals(knowledgeBaseFolder1, knowledgeBaseFolder2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				knowledgeBaseFolders2 + " does not contain " +
					knowledgeBaseFolder1,
				contains);
		}
	}

	protected void assertValid(KnowledgeBaseFolder knowledgeBaseFolder) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<KnowledgeBaseFolder> page) {
		boolean valid = false;

		Collection<KnowledgeBaseFolder> knowledgeBaseFolders = page.getItems();

		int size = knowledgeBaseFolders.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		KnowledgeBaseFolder knowledgeBaseFolder1,
		KnowledgeBaseFolder knowledgeBaseFolder2) {

		if (knowledgeBaseFolder1 == knowledgeBaseFolder2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_knowledgeBaseFolderResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_knowledgeBaseFolderResource;

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
		KnowledgeBaseFolder knowledgeBaseFolder) {

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
			sb.append(_dateFormat.format(knowledgeBaseFolder.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(
				_dateFormat.format(knowledgeBaseFolder.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseFolder.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseFolder.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("numberOfKnowledgeBaseArticles")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfKnowledgeBaseFolders")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentKnowledgeBaseFolder")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentKnowledgeBaseFolderId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected KnowledgeBaseFolder randomKnowledgeBaseFolder() {
		return new KnowledgeBaseFolder() {
			{
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				parentKnowledgeBaseFolderId = RandomTestUtil.randomLong();
			}
		};
	}

	protected KnowledgeBaseFolder randomIrrelevantKnowledgeBaseFolder() {
		return randomKnowledgeBaseFolder();
	}

	protected KnowledgeBaseFolder randomPatchKnowledgeBaseFolder() {
		return randomKnowledgeBaseFolder();
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

	protected String contentType = "application/json";
	protected Group irrelevantGroup;
	protected Group testGroup;
	protected String userNameAndPassword = "test@liferay.com:test";

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

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", contentType);

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
		BaseKnowledgeBaseFolderResourceTestCase.class);

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
	private KnowledgeBaseFolderResource _knowledgeBaseFolderResource;

	private URL _resourceURL;

}