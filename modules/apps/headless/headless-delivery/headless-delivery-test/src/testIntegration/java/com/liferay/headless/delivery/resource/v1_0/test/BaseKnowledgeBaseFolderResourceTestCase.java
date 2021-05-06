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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseFolderSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_knowledgeBaseFolderResource.setContextCompany(testCompany);

		KnowledgeBaseFolderResource.Builder builder =
			KnowledgeBaseFolderResource.builder();

		knowledgeBaseFolderResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		KnowledgeBaseFolder knowledgeBaseFolder1 = randomKnowledgeBaseFolder();

		String json = objectMapper.writeValueAsString(knowledgeBaseFolder1);

		KnowledgeBaseFolder knowledgeBaseFolder2 =
			KnowledgeBaseFolderSerDes.toDTO(json);

		Assert.assertTrue(equals(knowledgeBaseFolder1, knowledgeBaseFolder2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		KnowledgeBaseFolder knowledgeBaseFolder = randomKnowledgeBaseFolder();

		String json1 = objectMapper.writeValueAsString(knowledgeBaseFolder);
		String json2 = KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		KnowledgeBaseFolder knowledgeBaseFolder = randomKnowledgeBaseFolder();

		knowledgeBaseFolder.setDescription(regex);
		knowledgeBaseFolder.setName(regex);

		String json = KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder);

		Assert.assertFalse(json.contains(regex));

		knowledgeBaseFolder = KnowledgeBaseFolderSerDes.toDTO(json);

		Assert.assertEquals(regex, knowledgeBaseFolder.getDescription());
		Assert.assertEquals(regex, knowledgeBaseFolder.getName());
	}

	@Test
	public void testDeleteKnowledgeBaseFolder() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseFolder knowledgeBaseFolder =
			testDeleteKnowledgeBaseFolder_addKnowledgeBaseFolder();

		assertHttpResponseStatusCode(
			204,
			knowledgeBaseFolderResource.deleteKnowledgeBaseFolderHttpResponse(
				knowledgeBaseFolder.getId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseFolderResource.getKnowledgeBaseFolderHttpResponse(
				knowledgeBaseFolder.getId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseFolderResource.getKnowledgeBaseFolderHttpResponse(0L));
	}

	protected KnowledgeBaseFolder
			testDeleteKnowledgeBaseFolder_addKnowledgeBaseFolder()
		throws Exception {

		return knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
	}

	@Test
	public void testGraphQLDeleteKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder knowledgeBaseFolder =
			testGraphQLKnowledgeBaseFolder_addKnowledgeBaseFolder();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteKnowledgeBaseFolder",
						new HashMap<String, Object>() {
							{
								put(
									"knowledgeBaseFolderId",
									knowledgeBaseFolder.getId());
							}
						})),
				"JSONObject/data", "Object/deleteKnowledgeBaseFolder"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"knowledgeBaseFolder",
					new HashMap<String, Object>() {
						{
							put(
								"knowledgeBaseFolderId",
								knowledgeBaseFolder.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testGetKnowledgeBaseFolder_addKnowledgeBaseFolder();

		KnowledgeBaseFolder getKnowledgeBaseFolder =
			knowledgeBaseFolderResource.getKnowledgeBaseFolder(
				postKnowledgeBaseFolder.getId());

		assertEquals(postKnowledgeBaseFolder, getKnowledgeBaseFolder);
		assertValid(getKnowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			testGetKnowledgeBaseFolder_addKnowledgeBaseFolder()
		throws Exception {

		return knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
	}

	@Test
	public void testGraphQLGetKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder knowledgeBaseFolder =
			testGraphQLKnowledgeBaseFolder_addKnowledgeBaseFolder();

		Assert.assertTrue(
			equals(
				knowledgeBaseFolder,
				KnowledgeBaseFolderSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"knowledgeBaseFolder",
								new HashMap<String, Object>() {
									{
										put(
											"knowledgeBaseFolderId",
											knowledgeBaseFolder.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/knowledgeBaseFolder"))));
	}

	@Test
	public void testGraphQLGetKnowledgeBaseFolderNotFound() throws Exception {
		Long irrelevantKnowledgeBaseFolderId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"knowledgeBaseFolder",
						new HashMap<String, Object>() {
							{
								put(
									"knowledgeBaseFolderId",
									irrelevantKnowledgeBaseFolderId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testPatchKnowledgeBaseFolder_addKnowledgeBaseFolder();

		KnowledgeBaseFolder randomPatchKnowledgeBaseFolder =
			randomPatchKnowledgeBaseFolder();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseFolder patchKnowledgeBaseFolder =
			knowledgeBaseFolderResource.patchKnowledgeBaseFolder(
				postKnowledgeBaseFolder.getId(),
				randomPatchKnowledgeBaseFolder);

		KnowledgeBaseFolder expectedPatchKnowledgeBaseFolder =
			postKnowledgeBaseFolder.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchKnowledgeBaseFolder, randomPatchKnowledgeBaseFolder);

		KnowledgeBaseFolder getKnowledgeBaseFolder =
			knowledgeBaseFolderResource.getKnowledgeBaseFolder(
				patchKnowledgeBaseFolder.getId());

		assertEquals(expectedPatchKnowledgeBaseFolder, getKnowledgeBaseFolder);
		assertValid(getKnowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			testPatchKnowledgeBaseFolder_addKnowledgeBaseFolder()
		throws Exception {

		return knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
	}

	@Test
	public void testPutKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testPutKnowledgeBaseFolder_addKnowledgeBaseFolder();

		KnowledgeBaseFolder randomKnowledgeBaseFolder =
			randomKnowledgeBaseFolder();

		KnowledgeBaseFolder putKnowledgeBaseFolder =
			knowledgeBaseFolderResource.putKnowledgeBaseFolder(
				postKnowledgeBaseFolder.getId(), randomKnowledgeBaseFolder);

		assertEquals(randomKnowledgeBaseFolder, putKnowledgeBaseFolder);
		assertValid(putKnowledgeBaseFolder);

		KnowledgeBaseFolder getKnowledgeBaseFolder =
			knowledgeBaseFolderResource.getKnowledgeBaseFolder(
				putKnowledgeBaseFolder.getId());

		assertEquals(randomKnowledgeBaseFolder, getKnowledgeBaseFolder);
		assertValid(getKnowledgeBaseFolder);
	}

	protected KnowledgeBaseFolder
			testPutKnowledgeBaseFolder_addKnowledgeBaseFolder()
		throws Exception {

		return knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
	}

	@Test
	public void testGetKnowledgeBaseFolderPermissionsPage() throws Exception {
		KnowledgeBaseFolder postKnowledgeBaseFolder =
			testGetKnowledgeBaseFolderPermissionsPage_addKnowledgeBaseFolder();

		Page<Permission> page =
			knowledgeBaseFolderResource.getKnowledgeBaseFolderPermissionsPage(
				postKnowledgeBaseFolder.getId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected KnowledgeBaseFolder
			testGetKnowledgeBaseFolderPermissionsPage_addKnowledgeBaseFolder()
		throws Exception {

		return testPostKnowledgeBaseFolderKnowledgeBaseFolder_addKnowledgeBaseFolder(
			randomKnowledgeBaseFolder());
	}

	@Test
	public void testPutKnowledgeBaseFolderPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseFolder knowledgeBaseFolder =
			testPutKnowledgeBaseFolderPermission_addKnowledgeBaseFolder();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			knowledgeBaseFolderResource.
				putKnowledgeBaseFolderPermissionHttpResponse(
					knowledgeBaseFolder.getId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"VIEW"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseFolderResource.
				putKnowledgeBaseFolderPermissionHttpResponse(
					0L,
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected KnowledgeBaseFolder
			testPutKnowledgeBaseFolderPermission_addKnowledgeBaseFolder()
		throws Exception {

		return knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage()
		throws Exception {

		Page<KnowledgeBaseFolder> page =
			knowledgeBaseFolderResource.
				getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
					testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getParentKnowledgeBaseFolderId(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long parentKnowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getParentKnowledgeBaseFolderId();
		Long irrelevantParentKnowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getIrrelevantParentKnowledgeBaseFolderId();

		if (irrelevantParentKnowledgeBaseFolderId != null) {
			KnowledgeBaseFolder irrelevantKnowledgeBaseFolder =
				testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
					irrelevantParentKnowledgeBaseFolderId,
					randomIrrelevantKnowledgeBaseFolder());

			page =
				knowledgeBaseFolderResource.
					getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
						irrelevantParentKnowledgeBaseFolderId,
						Pagination.of(1, 2));

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

		page =
			knowledgeBaseFolderResource.
				getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
					parentKnowledgeBaseFolderId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseFolder1, knowledgeBaseFolder2),
			(List<KnowledgeBaseFolder>)page.getItems());
		assertValid(page);

		knowledgeBaseFolderResource.deleteKnowledgeBaseFolder(
			knowledgeBaseFolder1.getId());

		knowledgeBaseFolderResource.deleteKnowledgeBaseFolder(
			knowledgeBaseFolder2.getId());
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
			knowledgeBaseFolderResource.
				getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
					parentKnowledgeBaseFolderId, Pagination.of(1, 2));

		List<KnowledgeBaseFolder> knowledgeBaseFolders1 =
			(List<KnowledgeBaseFolder>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseFolders1.toString(), 2, knowledgeBaseFolders1.size());

		Page<KnowledgeBaseFolder> page2 =
			knowledgeBaseFolderResource.
				getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
					parentKnowledgeBaseFolderId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseFolder> knowledgeBaseFolders2 =
			(List<KnowledgeBaseFolder>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseFolders2.toString(), 1, knowledgeBaseFolders2.size());

		Page<KnowledgeBaseFolder> page3 =
			knowledgeBaseFolderResource.
				getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
					parentKnowledgeBaseFolderId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseFolder1, knowledgeBaseFolder2,
				knowledgeBaseFolder3),
			(List<KnowledgeBaseFolder>)page3.getItems());
	}

	protected KnowledgeBaseFolder
			testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				Long parentKnowledgeBaseFolderId,
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return knowledgeBaseFolderResource.
			postKnowledgeBaseFolderKnowledgeBaseFolder(
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

		return knowledgeBaseFolderResource.
			postKnowledgeBaseFolderKnowledgeBaseFolder(
				testGetKnowledgeBaseFolderKnowledgeBaseFoldersPage_getParentKnowledgeBaseFolderId(),
				knowledgeBaseFolder);
	}

	@Test
	public void testGetSiteKnowledgeBaseFoldersPage() throws Exception {
		Page<KnowledgeBaseFolder> page =
			knowledgeBaseFolderResource.getSiteKnowledgeBaseFoldersPage(
				testGetSiteKnowledgeBaseFoldersPage_getSiteId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteKnowledgeBaseFoldersPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteKnowledgeBaseFoldersPage_getIrrelevantSiteId();

		if (irrelevantSiteId != null) {
			KnowledgeBaseFolder irrelevantKnowledgeBaseFolder =
				testGetSiteKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
					irrelevantSiteId, randomIrrelevantKnowledgeBaseFolder());

			page = knowledgeBaseFolderResource.getSiteKnowledgeBaseFoldersPage(
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

		page = knowledgeBaseFolderResource.getSiteKnowledgeBaseFoldersPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseFolder1, knowledgeBaseFolder2),
			(List<KnowledgeBaseFolder>)page.getItems());
		assertValid(page);

		knowledgeBaseFolderResource.deleteKnowledgeBaseFolder(
			knowledgeBaseFolder1.getId());

		knowledgeBaseFolderResource.deleteKnowledgeBaseFolder(
			knowledgeBaseFolder2.getId());
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

		Page<KnowledgeBaseFolder> page1 =
			knowledgeBaseFolderResource.getSiteKnowledgeBaseFoldersPage(
				siteId, Pagination.of(1, 2));

		List<KnowledgeBaseFolder> knowledgeBaseFolders1 =
			(List<KnowledgeBaseFolder>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseFolders1.toString(), 2, knowledgeBaseFolders1.size());

		Page<KnowledgeBaseFolder> page2 =
			knowledgeBaseFolderResource.getSiteKnowledgeBaseFoldersPage(
				siteId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseFolder> knowledgeBaseFolders2 =
			(List<KnowledgeBaseFolder>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseFolders2.toString(), 1, knowledgeBaseFolders2.size());

		Page<KnowledgeBaseFolder> page3 =
			knowledgeBaseFolderResource.getSiteKnowledgeBaseFoldersPage(
				siteId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseFolder1, knowledgeBaseFolder2,
				knowledgeBaseFolder3),
			(List<KnowledgeBaseFolder>)page3.getItems());
	}

	protected KnowledgeBaseFolder
			testGetSiteKnowledgeBaseFoldersPage_addKnowledgeBaseFolder(
				Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
			siteId, knowledgeBaseFolder);
	}

	protected Long testGetSiteKnowledgeBaseFoldersPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteKnowledgeBaseFoldersPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteKnowledgeBaseFoldersPage() throws Exception {
		Long siteId = testGetSiteKnowledgeBaseFoldersPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"knowledgeBaseFolders",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject knowledgeBaseFoldersJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/knowledgeBaseFolders");

		Assert.assertEquals(
			0, knowledgeBaseFoldersJSONObject.get("totalCount"));

		KnowledgeBaseFolder knowledgeBaseFolder1 =
			testGraphQLKnowledgeBaseFolder_addKnowledgeBaseFolder();
		KnowledgeBaseFolder knowledgeBaseFolder2 =
			testGraphQLKnowledgeBaseFolder_addKnowledgeBaseFolder();

		knowledgeBaseFoldersJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/knowledgeBaseFolders");

		Assert.assertEquals(
			2, knowledgeBaseFoldersJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseFolder1, knowledgeBaseFolder2),
			Arrays.asList(
				KnowledgeBaseFolderSerDes.toDTOs(
					knowledgeBaseFoldersJSONObject.getString("items"))));
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

		return knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
			testGetSiteKnowledgeBaseFoldersPage_getSiteId(),
			knowledgeBaseFolder);
	}

	@Test
	public void testGraphQLPostSiteKnowledgeBaseFolder() throws Exception {
		KnowledgeBaseFolder randomKnowledgeBaseFolder =
			randomKnowledgeBaseFolder();

		KnowledgeBaseFolder knowledgeBaseFolder =
			testGraphQLKnowledgeBaseFolder_addKnowledgeBaseFolder(
				randomKnowledgeBaseFolder);

		Assert.assertTrue(
			equals(randomKnowledgeBaseFolder, knowledgeBaseFolder));
	}

	@Test
	public void testGetSiteKnowledgeBaseFolderPermissionsPage()
		throws Exception {

		Page<Permission> page =
			knowledgeBaseFolderResource.
				getSiteKnowledgeBaseFolderPermissionsPage(
					testGroup.getGroupId(), RoleConstants.GUEST);

		Assert.assertNotNull(page);
	}

	protected KnowledgeBaseFolder
			testGetSiteKnowledgeBaseFolderPermissionsPage_addKnowledgeBaseFolder()
		throws Exception {

		return testPostSiteKnowledgeBaseFolder_addKnowledgeBaseFolder(
			randomKnowledgeBaseFolder());
	}

	@Test
	public void testPutSiteKnowledgeBaseFolderPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseFolder knowledgeBaseFolder =
			testPutSiteKnowledgeBaseFolderPermission_addKnowledgeBaseFolder();

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			knowledgeBaseFolderResource.
				putSiteKnowledgeBaseFolderPermissionHttpResponse(
					knowledgeBaseFolder.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"PERMISSIONS"});
								setRoleName(role.getName());
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseFolderResource.
				putSiteKnowledgeBaseFolderPermissionHttpResponse(
					knowledgeBaseFolder.getSiteId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected KnowledgeBaseFolder
			testPutSiteKnowledgeBaseFolderPermission_addKnowledgeBaseFolder()
		throws Exception {

		return knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
			testGroup.getGroupId(), randomKnowledgeBaseFolder());
	}

	protected void appendGraphQLFieldValue(StringBuilder sb, Object value)
		throws Exception {

		if (value instanceof Object[]) {
			StringBuilder arraySB = new StringBuilder("[");

			for (Object object : (Object[])value) {
				if (arraySB.length() > 1) {
					arraySB.append(", ");
				}

				arraySB.append("{");

				Class<?> clazz = object.getClass();

				for (Field field : getDeclaredFields(clazz.getSuperclass())) {
					arraySB.append(field.getName());
					arraySB.append(": ");

					appendGraphQLFieldValue(arraySB, field.get(object));

					arraySB.append(", ");
				}

				arraySB.setLength(arraySB.length() - 2);

				arraySB.append("}");
			}

			arraySB.append("]");

			sb.append(arraySB.toString());
		}
		else if (value instanceof String) {
			sb.append("\"");
			sb.append(value);
			sb.append("\"");
		}
		else {
			sb.append(value);
		}
	}

	protected KnowledgeBaseFolder
			testGraphQLKnowledgeBaseFolder_addKnowledgeBaseFolder()
		throws Exception {

		return testGraphQLKnowledgeBaseFolder_addKnowledgeBaseFolder(
			randomKnowledgeBaseFolder());
	}

	protected KnowledgeBaseFolder
			testGraphQLKnowledgeBaseFolder_addKnowledgeBaseFolder(
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		JSONDeserializer<KnowledgeBaseFolder> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (Field field : getDeclaredFields(KnowledgeBaseFolder.class)) {
			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(knowledgeBaseFolder));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteKnowledgeBaseFolder",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("knowledgeBaseFolder", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data", "JSONObject/createSiteKnowledgeBaseFolder"),
			KnowledgeBaseFolder.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
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

	protected void assertValid(KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (knowledgeBaseFolder.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (knowledgeBaseFolder.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (knowledgeBaseFolder.getCustomFields() == null) {
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

		java.util.Collection<KnowledgeBaseFolder> knowledgeBaseFolders =
			page.getItems();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder.
						class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
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

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)knowledgeBaseFolder1.getActions(),
						(Map)knowledgeBaseFolder2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getCreator(),
						knowledgeBaseFolder2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseFolder1.getCustomFields(),
						knowledgeBaseFolder2.getCustomFields())) {

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

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

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

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
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

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
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

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected KnowledgeBaseFolder randomKnowledgeBaseFolder() throws Exception {
		return new KnowledgeBaseFolder() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				numberOfKnowledgeBaseArticles = RandomTestUtil.randomInt();
				numberOfKnowledgeBaseFolders = RandomTestUtil.randomInt();
				parentKnowledgeBaseFolderId = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected KnowledgeBaseFolder randomIrrelevantKnowledgeBaseFolder()
		throws Exception {

		KnowledgeBaseFolder randomIrrelevantKnowledgeBaseFolder =
			randomKnowledgeBaseFolder();

		randomIrrelevantKnowledgeBaseFolder.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantKnowledgeBaseFolder;
	}

	protected KnowledgeBaseFolder randomPatchKnowledgeBaseFolder()
		throws Exception {

		return randomKnowledgeBaseFolder();
	}

	protected KnowledgeBaseFolderResource knowledgeBaseFolderResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

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
	private
		com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource
			_knowledgeBaseFolderResource;

}