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
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

		String[] entryClassNames = {
			JournalArticle.class.getName(), User.class.getName()
		};

		searchContext.setEntryClassNames(entryClassNames);

		searchContext.setLocale(_locale);

		Facet facet = createUserFacet(searchContext);

		searchContext.addFacet(facet);

		setUpPermissionChecker(group.getCompanyId());

		Hits hits = search(searchContext);

		Map<String, Integer> frequencies = Collections.singletonMap(
			StringUtil.toLowerCase(user.getFullName()), 2);

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, frequencies);
	}

	protected void addJournalArticle(User user, Group group, String title)
		throws Exception {

		ServiceContext serviceContext = createServiceContext(group, user);

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		JournalArticle article = _journalArticleLocalService.addArticle(
			user.getUserId(), group.getGroupId(), 0,
			Collections.singletonMap(_locale, title), null, content,
			"BASIC-WEB-CONTENT", "BASIC-WEB-CONTENT", serviceContext);

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
		Facet facet = new MultiValueFacet(searchContext);

		facet.setFieldName(Field.USER_NAME);

		return facet;
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
	private static JournalArticleLocalService _journalArticleLocalService;

	private static final Locale _locale = LocaleUtil.US;

	@DeleteAfterTestRun
	private final List<JournalArticle> _articles = new ArrayList<>();

}