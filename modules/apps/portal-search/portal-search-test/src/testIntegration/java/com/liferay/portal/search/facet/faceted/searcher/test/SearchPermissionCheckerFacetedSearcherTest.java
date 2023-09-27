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
import com.liferay.journal.service.JournalArticleLocalService;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.facet.user.UserFacetFactory;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class SearchPermissionCheckerFacetedSearcherTest
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
	public void testFacetCountAfterPermissionsFiltered() throws Exception {
		Group group = userSearchFixture.addGroup();

		User user1 = addUser(group);

		String title = RandomTestUtil.randomString();

		addJournalArticle(user1, group, title);

		User user2 = addUser(group);

		addJournalArticle(user2, group, title);

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user2));

		SearchContext searchContext = getSearchContext(title);

		searchContext.setEntryClassNames(
			new String[] {JournalArticle.class.getName()});
		searchContext.setUserId(user2.getUserId());

		Facet facet = createUserFacet(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits,
			Collections.singletonMap(String.valueOf(user2.getUserId()), 1));
	}

	protected void addJournalArticle(User user, Group group, String title)
		throws Exception {

		ServiceContext serviceContext = createServiceContext(group, user);

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			portal.getSiteGroupId(group.getGroupId()),
			portal.getClassNameId(JournalArticle.class.getName()),
			"BASIC-WEB-CONTENT", true);

		JournalArticle article = journalArticleLocalService.addArticle(
			null, user.getUserId(), group.getGroupId(), 0,
			Collections.singletonMap(LocaleUtil.US, title), null, content,
			ddmStructure.getStructureId(), "BASIC-WEB-CONTENT", serviceContext);

		_articles.add(article);
	}

	protected ModelPermissions createModelPermissions() {
		ModelPermissions modelPermissions =
			ModelPermissionsFactory.createForAllResources();

		modelPermissions.addRolePermissions(
			RoleConstants.OWNER, ActionKeys.VIEW);

		return modelPermissions;
	}

	protected ServiceContext createServiceContext(Group group, User user)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setModelPermissions(createModelPermissions());
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	protected Facet createUserFacet(SearchContext searchContext) {
		return userFacetFactory.newInstance(searchContext);
	}

	@Inject
	protected static DDMStructureLocalService ddmStructureLocalService;

	@Inject
	protected static JournalArticleLocalService journalArticleLocalService;

	@Inject
	protected static PermissionCheckerFactory permissionCheckerFactory;

	@Inject
	protected static Portal portal;

	@Inject
	protected static UserFacetFactory userFacetFactory;

	@DeleteAfterTestRun
	private final List<JournalArticle> _articles = new ArrayList<>();

	private PermissionChecker _originalPermissionChecker;

}