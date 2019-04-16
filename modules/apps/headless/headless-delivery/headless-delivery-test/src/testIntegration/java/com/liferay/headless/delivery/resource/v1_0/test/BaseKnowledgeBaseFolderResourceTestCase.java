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
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseFolderSerDes;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
	public void testDeserializeKnowledgeBaseFolder() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		KnowledgeBaseFolder knowledgeBaseFolder1 = randomKnowledgeBaseFolder();

		String json = objectMapper.writeValueAsString(knowledgeBaseFolder1);

		KnowledgeBaseFolder knowledgeBaseFolder2 =
			KnowledgeBaseFolderSerDes.toDTO(json);

		Assert.assertTrue(equals(knowledgeBaseFolder1, knowledgeBaseFolder2));
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

		return invokePostSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
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

		return invokePostSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
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
			return KnowledgeBaseFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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

		return invokePostSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
	}

	protected KnowledgeBaseFolder invokePatchKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
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
			return KnowledgeBaseFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePatchKnowledgeBaseFolderResponse(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
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

		return invokePostSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
	}

	protected KnowledgeBaseFolder invokePutKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
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
			return KnowledgeBaseFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutKnowledgeBaseFolderResponse(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
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

		return invokePostKnowledgeBaseFolderKnowledgeBaseFolder(
			parentKnowledgeBaseFolderId, knowledgeBaseFolder);
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

		return Page.of(string, KnowledgeBaseFolderSerDes::toDTO);
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

		return invokePostKnowledgeBaseFolderKnowledgeBaseFolder(
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getParentKnowledgeBaseFolderId(),
			knowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			invokePostKnowledgeBaseFolderKnowledgeBaseFolder(
				Long parentKnowledgeBaseFolderId,
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
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
			return KnowledgeBaseFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
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

	@Test
	public void testGetSiteKnowledgeBaseFoldersPage() throws Exception {
		Long siteId = testGetSiteKnowledgeBaseFoldersPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteKnowledgeBaseFoldersPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			KnowledgeBaseFolder irrelevantKnowledgeBaseFolder =
				testGetSiteKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
					irrelevantSiteId, randomIrrelevantKnowledgeBaseFolder());

			Page<KnowledgeBaseFolder> page =
				invokeGetSiteKnowledgeBaseFoldersPage(
					irrelevantSiteId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseFolder),
				(List<KnowledgeBaseFolder>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseFolder knowledgeBaseFolder1 =
			testGetSiteKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				siteId, randomKnowledgeBaseFolder());

		KnowledgeBaseFolder knowledgeBaseFolder2 =
			testGetSiteKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				siteId, randomKnowledgeBaseFolder());

		Page<KnowledgeBaseFolder> page = invokeGetSiteKnowledgeBaseFoldersPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseFolder1, knowledgeBaseFolder2),
			(List<KnowledgeBaseFolder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteKnowledgeBaseFoldersPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteKnowledgeBaseFoldersPage_getSiteId();

		KnowledgeBaseFolder knowledgeBaseFolder1 =
			testGetSiteKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				siteId, randomKnowledgeBaseFolder());

		KnowledgeBaseFolder knowledgeBaseFolder2 =
			testGetSiteKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				siteId, randomKnowledgeBaseFolder());

		KnowledgeBaseFolder knowledgeBaseFolder3 =
			testGetSiteKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				siteId, randomKnowledgeBaseFolder());

		Page<KnowledgeBaseFolder> page1 = invokeGetSiteKnowledgeBaseFoldersPage(
			siteId, Pagination.of(1, 2));

		List<KnowledgeBaseFolder> knowledgeBaseFolders1 =
			(List<KnowledgeBaseFolder>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseFolders1.toString(), 2, knowledgeBaseFolders1.size());

		Page<KnowledgeBaseFolder> page2 = invokeGetSiteKnowledgeBaseFoldersPage(
			siteId, Pagination.of(2, 2));

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
			testGetSiteKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return invokePostSiteKnowledgeBaseFolder(siteId, knowledgeBaseFolder);
	}

	protected Long testGetSiteKnowledgeBaseFoldersPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteKnowledgeBaseFoldersPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<KnowledgeBaseFolder> invokeGetSiteKnowledgeBaseFoldersPage(
			Long siteId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/knowledge-base-folders", siteId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, KnowledgeBaseFolderSerDes::toDTO);
	}

	protected Http.Response invokeGetSiteKnowledgeBaseFoldersPageResponse(
			Long siteId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/knowledge-base-folders", siteId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostSiteKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder randomKnowledgeBaseFolder =
			randomKnowledgeBaseFolder();

		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testPostSiteKnowledgeBaseFolder_addKnowledgeBaseFolder(
				randomKnowledgeBaseFolder);

		assertEquals(randomKnowledgeBaseFolder, postKnowledgeBaseFolder);
		assertValid(postKnowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			testPostSiteKnowledgeBaseFolder_addKnowledgeBaseFolder(
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return invokePostSiteKnowledgeBaseFolder(
			testGetSiteKnowledgeBaseFoldersPage_getSiteId(),
			knowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder invokePostSiteKnowledgeBaseFolder(
			Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/knowledge-base-folders", siteId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return KnowledgeBaseFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostSiteKnowledgeBaseFolderResponse(
			Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/sites/{siteId}/knowledge-base-folders", siteId);

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
		boolean valid = true;

		if (knowledgeBaseFolder.getDateCreated() == null) {
			valid = false;
		}

		if (knowledgeBaseFolder.getDateModified() == null) {
			valid = false;
		}

		if (knowledgeBaseFolder.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				knowledgeBaseFolder.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (knowledgeBaseFolder.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (knowledgeBaseFolder.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (knowledgeBaseFolder.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfKnowledgeBaseArticles",
					additionalAssertFieldName)) {

				if (knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfKnowledgeBaseFolders",
					additionalAssertFieldName)) {

				if (knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolder", additionalAssertFieldName)) {

				if (knowledgeBaseFolder.getParentKnowledgeBaseFolder() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolderId", additionalAssertFieldName)) {

				if (knowledgeBaseFolder.getParentKnowledgeBaseFolderId() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (knowledgeBaseFolder.getViewableBy() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		KnowledgeBaseFolder knowledgeBaseFolder1,
		KnowledgeBaseFolder knowledgeBaseFolder2) {

		if (knowledgeBaseFolder1 == knowledgeBaseFolder2) {
			return true;
		}

		if (!Objects.equals(
				knowledgeBaseFolder1.getSiteId(),
				knowledgeBaseFolder2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getCreator(),
						knowledgeBaseFolder2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getDateCreated(),
						knowledgeBaseFolder2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getDateModified(),
						knowledgeBaseFolder2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getDescription(),
						knowledgeBaseFolder2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getId(),
						knowledgeBaseFolder2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getName(),
						knowledgeBaseFolder2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfKnowledgeBaseArticles",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getNumberOfKnowledgeBaseArticles(),
						knowledgeBaseFolder2.
							getNumberOfKnowledgeBaseArticles())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfKnowledgeBaseFolders",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getNumberOfKnowledgeBaseFolders(),
						knowledgeBaseFolder2.
							getNumberOfKnowledgeBaseFolders())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolder", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getParentKnowledgeBaseFolder(),
						knowledgeBaseFolder2.getParentKnowledgeBaseFolder())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolderId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getParentKnowledgeBaseFolderId(),
						knowledgeBaseFolder2.
							getParentKnowledgeBaseFolderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getViewableBy(),
						knowledgeBaseFolder2.getViewableBy())) {

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

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseFolder.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseFolder.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(knowledgeBaseFolder.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseFolder.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseFolder.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(knowledgeBaseFolder.getDateModified()));
			}

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

		if (entityFieldName.equals("siteId")) {
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
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				parentKnowledgeBaseFolderId = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected KnowledgeBaseFolder randomIrrelevantKnowledgeBaseFolder() {
		KnowledgeBaseFolder randomIrrelevantKnowledgeBaseFolder =
			randomKnowledgeBaseFolder();

		randomIrrelevantKnowledgeBaseFolder.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantKnowledgeBaseFolder;
	}

	protected KnowledgeBaseFolder randomPatchKnowledgeBaseFolder() {
		return randomKnowledgeBaseFolder();
	}

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

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

	private String _toJSON(Map<String, String> map) {
		if (map == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		Set<Map.Entry<String, String>> set = map.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			sb.append("\"" + entry.getKey() + "\": ");

			if (entry.getValue() == null) {
				sb.append("null");
			}
			else {
				sb.append("\"" + entry.getValue() + "\"");
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
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