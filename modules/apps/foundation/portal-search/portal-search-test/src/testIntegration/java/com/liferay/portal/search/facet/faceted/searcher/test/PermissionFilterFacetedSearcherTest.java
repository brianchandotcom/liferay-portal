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

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacetFactory;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
@Sync
public class PermissionFilterFacetedSearcherTest
	extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Registry registry = RegistryUtil.getRegistry();

		assetEntriesFacetFactory = registry.getService(
			AssetEntriesFacetFactory.class);

		configurationAdmin = registry.getService(ConfigurationAdmin.class);

		journalFolderLocalService = registry.getService(
			JournalFolderLocalService.class);

		journalArticleLocalService = registry.getService(
			JournalArticleLocalService.class);

		permissionCheckerFactory = registry.getService(
			PermissionCheckerFactory.class);

		setUpJournalServiceConfiguration();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		tearDownJournalServiceConfiguration();

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testDecrementFrequencyCount() throws Exception {
		Group group = userSearchFixture.addGroup();

		User user1 = addUser(group);

		ServiceContext serviceContext = createServiceContext(group, user1);

		String title = RandomTestUtil.randomString();

		addArticle(title, user1, group, 0, serviceContext);

		JournalFolder folder = addFolder(user1, group, serviceContext);

		addArticle(title, user1, group, folder.getFolderId(), serviceContext);

		User user2 = addUser(group);

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user2));

		SearchContext searchContext = getSearchContext(title);

		Facet facet = assetEntriesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		search(searchContext);

		Map<String, Integer> expected = Collections.singletonMap(
			JournalArticle.class.getName(), 1);

		assertFrequencies(facet.getFieldName(), searchContext, expected);
	}

	protected void addArticle(
			String title, User user, Group group, long folderId,
			ServiceContext serviceContext)
		throws Exception {

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		JournalArticle article = journalArticleLocalService.addArticle(
			user.getUserId(), group.getGroupId(), folderId,
			Collections.singletonMap(LocaleUtil.US, title), null, content,
			"BASIC-WEB-CONTENT", "BASIC-WEB-CONTENT", serviceContext);

		_articles.add(article);
	}

	protected JournalFolder addFolder(
			User user, Group group, ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = journalFolderLocalService.addFolder(
			user.getUserId(), group.getGroupId(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		_folders.add(folder);

		return folder;
	}

	protected ServiceContext createServiceContext(Group group, User user)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ModelPermissions modelPermissions = new ModelPermissions();

		modelPermissions.addRolePermissions(
			RoleConstants.OWNER, ActionKeys.VIEW);

		serviceContext.setModelPermissions(modelPermissions);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(false);

		return serviceContext;
	}

	protected void setUpJournalServiceConfiguration() throws Exception {
		_configuration = configurationAdmin.getConfiguration(
			JournalServiceConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("articleViewPermissionsCheckEnabled", true);

		_configuration.update(properties);
	}

	protected void tearDownJournalServiceConfiguration() throws Exception {
		_configuration.delete();
	}

	protected AssetEntriesFacetFactory assetEntriesFacetFactory;
	protected ConfigurationAdmin configurationAdmin;
	protected JournalArticleLocalService journalArticleLocalService;
	protected JournalFolderLocalService journalFolderLocalService;
	protected PermissionCheckerFactory permissionCheckerFactory;

	@DeleteAfterTestRun
	private final List<JournalArticle> _articles = new ArrayList<>();

	private Configuration _configuration;

	@DeleteAfterTestRun
	private final List<JournalFolder> _folders = new ArrayList<>();

	private PermissionChecker _originalPermissionChecker;

}