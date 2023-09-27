/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
public class PermissionFilterFacetedSearcherTest
	extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testDecrementFrequencyCount() throws Exception {
		Group group = userSearchFixture.addGroup();

		User user1 = addUser(group);

		String title = RandomTestUtil.randomString();

		addJournalArticle(user1, group, title, 0);

		JournalFolder folder = addJournalFolder(user1, group);

		addJournalArticle(user1, group, title, folder.getFolderId());

		User user2 = addUser(group);

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user2));

		SearchContext searchContext = getSearchContext(title);

		Facet facet = assetEntriesFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Map<String, Integer> expected = Collections.singletonMap(
			JournalArticle.class.getName(), 1);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, expected);
	}

	protected void addJournalArticle(
			User user, Group group, String title, long folderId)
		throws Exception {

		ServiceContext serviceContext = createServiceContext(group, user);

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			portal.getSiteGroupId(group.getGroupId()),
			portal.getClassNameId(JournalArticle.class.getName()),
			"BASIC-WEB-CONTENT", true);

		JournalArticle article = journalArticleLocalService.addArticle(
			null, user.getUserId(), group.getGroupId(), folderId,
			Collections.singletonMap(LocaleUtil.US, title), null, content,
			ddmStructure.getStructureId(), "BASIC-WEB-CONTENT", serviceContext);

		_articles.add(article);
	}

	protected JournalFolder addJournalFolder(User user, Group group)
		throws Exception {

		ServiceContext serviceContext = createServiceContext(group, user);

		JournalFolder folder = journalFolderLocalService.addFolder(
			null, user.getUserId(), group.getGroupId(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		_folders.add(folder);

		return folder;
	}

	protected ServiceContext createServiceContext(Group group, User user)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getCompanyId(), group.getGroupId(), user.getUserId());

		serviceContext.setAddGuestPermissions(false);

		ModelPermissions modelPermissions =
			ModelPermissionsFactory.createForAllResources();

		modelPermissions.addRolePermissions(
			RoleConstants.OWNER, ActionKeys.VIEW);

		serviceContext.setModelPermissions(modelPermissions);

		return serviceContext;
	}

	@Inject
	protected static AssetEntriesFacetFactory assetEntriesFacetFactory;

	@Inject
	protected static ConfigurationAdmin configurationAdmin;

	@Inject
	protected static DDMStructureLocalService ddmStructureLocalService;

	@Inject
	protected static JournalArticleLocalService journalArticleLocalService;

	@Inject
	protected static JournalFolderLocalService journalFolderLocalService;

	@Inject
	protected static PermissionCheckerFactory permissionCheckerFactory;

	@Inject
	protected static Portal portal;

	@DeleteAfterTestRun
	private final List<JournalArticle> _articles = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<JournalFolder> _folders = new ArrayList<>();

	private PermissionChecker _originalPermissionChecker;

}