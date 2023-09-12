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
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.facet.user.UserFacetFactory;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.test.util.search.DummyPermissionChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
public class UserFacetedSearcherTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Test
	public void testUserFacet() throws Exception {
		Group group = userSearchFixture.addGroup();

		String keyword = RandomTestUtil.randomString();

		User user = addUser(group, keyword);

		addJournalArticle(user, group, keyword);

		SearchContext searchContext = getSearchContext(keyword);

		searchContext.setEntryClassNames(
			new String[] {
				JournalArticle.class.getName(), User.class.getName()
			});
		searchContext.setLocale(_locale);

		Facet facet = createUserFacet(searchContext);

		searchContext.addFacet(facet);

		setUpPermissionChecker(group.getCompanyId());

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = Collections.singletonMap(
			String.valueOf(user.getUserId()), 2);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);
	}

	protected void addJournalArticle(User user, Group group, String title)
		throws Exception {

		ServiceContext serviceContext = createServiceContext(group, user);

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			portal.getSiteGroupId(group.getGroupId()),
			portal.getClassNameId(JournalArticle.class.getName()),
			"BASIC-WEB-CONTENT", true);

		JournalArticle article = _journalArticleLocalService.addArticle(
			null, user.getUserId(), group.getGroupId(), 0,
			Collections.singletonMap(_locale, title), null, content,
			ddmStructure.getStructureId(), "BASIC-WEB-CONTENT", serviceContext);

		_articles.add(article);
	}

	protected ServiceContext createServiceContext(Group group, User user)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	protected Facet createUserFacet(SearchContext searchContext) {
		return _userFacetFactory.newInstance(searchContext);
	}

	protected void setUpPermissionChecker(long companyId) {
		PermissionThreadLocal.setPermissionChecker(
			new DummyPermissionChecker() {

				@Override
				public long getCompanyId() {
					return companyId;
				}

				@Override
				public boolean isCompanyAdmin(long companyId) {
					return true;
				}

			});
	}

	@Inject
	protected static DDMStructureLocalService ddmStructureLocalService;

	@Inject
	protected static Portal portal;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	private static final Locale _locale = LocaleUtil.US;

	@Inject
	private static UserFacetFactory _userFacetFactory;

	@DeleteAfterTestRun
	private final List<JournalArticle> _articles = new ArrayList<>();

}