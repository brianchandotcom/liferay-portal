/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.search.JournalArticleSearchFixture;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public abstract class BaseFacetedSearcherTestCase {

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		setUpJournalArticleSearchFixture();
		setUpUserSearchFixture();
	}

	@After
	public void tearDown() throws Exception {
		journalArticleSearchFixture.tearDown();
		userSearchFixture.tearDown();
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Rule
	public TestName testName = new TestName();

	protected User addUser(Group group, String... assetTagNames)
		throws Exception {

		String screenName = testName.getMethodName();

		int size = _users.size();

		if (size > 0) {
			screenName = screenName.concat(String.valueOf(size));
		}

		return userSearchFixture.addUser(screenName, group, assetTagNames);
	}

	protected void assertAllHitsAreUsers(
		String keywords, Hits hits, SearchContext searchContext) {

		List<Document> documents = ListUtil.filter(
			Arrays.asList(hits.getDocs()), this::isMissingScreenName);

		Assert.assertTrue(
			(String)searchContext.getAttribute("queryString") + "->" +
				documents,
			documents.isEmpty());
	}

	protected void assertTags(
		String keywords, Hits hits, Map<String, String> expected,
		SearchContext searchContext) {

		assertAllHitsAreUsers(keywords, hits, searchContext);

		AssertUtils.assertEquals(
			(String)searchContext.getAttribute("queryString"), expected,
			userSearchFixture.toMap(hits.toList()));
	}

	protected FacetedSearcher createFacetedSearcher() {
		return _facetedSearcherManager.createFacetedSearcher();
	}

	protected SearchContext getSearchContext(String keywords) throws Exception {
		SearchContext searchContext = userSearchFixture.getSearchContext(
			keywords);

		searchContext.setGroupIds(
			TransformUtil.transformToLongArray(_groups, Group::getGroupId));

		return searchContext;
	}

	protected SearchContext getSearchContextWithGroupIdsUnset(String keywords)
		throws Exception {

		return userSearchFixture.getSearchContext(keywords);
	}

	protected boolean isMissingScreenName(Document document) {
		return Validator.isNull(document.get("screenName"));
	}

	protected Hits search(SearchContext searchContext) throws Exception {
		FacetedSearcher facetedSearcher = createFacetedSearcher();

		return facetedSearcher.search(searchContext);
	}

	protected void setUpJournalArticleSearchFixture() throws Exception {
		journalArticleSearchFixture = new JournalArticleSearchFixture(
			_ddmStructureLocalService, _journalArticleLocalService, _portal);

		journalArticleSearchFixture.setUp();

		_journalArticles = journalArticleSearchFixture.getJournalArticles();
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture.setUp();

		_assetTags = userSearchFixture.getAssetTags();
		_groups = userSearchFixture.getGroups();
		_users = userSearchFixture.getUsers();
	}

	protected Map<String, String> toMap(User user, String... tags) {
		return userSearchFixture.toMap(user, tags);
	}

	protected JournalArticleSearchFixture journalArticleSearchFixture;
	protected final UserSearchFixture userSearchFixture =
		new UserSearchFixture();

	@Inject
	private static DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private static FacetedSearcherManager _facetedSearcherManager;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private static Portal _portal;

	@DeleteAfterTestRun
	private List<AssetTag> _assetTags;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<JournalArticle> _journalArticles;

	@DeleteAfterTestRun
	private List<User> _users;

}