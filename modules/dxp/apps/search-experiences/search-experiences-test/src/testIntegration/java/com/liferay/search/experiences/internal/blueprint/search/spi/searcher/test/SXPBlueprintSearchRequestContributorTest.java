/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.search.spi.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class SXPBlueprintSearchRequestContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		WorkflowThreadLocal.setEnabled(false);

		_sxpBlueprint = _addSXPBlueprint();
	}

	@Test
	public void testQueryConfiguration() throws Exception {
		_addJournalArticles("alpha", "beta", "charlie");

		_assertSearch("[beta]", "localized_title_en_US", "beta");
	}

	@Test
	public void testSortConfiguration() throws Exception {
		_addJournalArticles("alpha delta", "beta delta", "charlie delta");

		_assertSearch(
			"[charlie delta, beta delta, alpha delta]", "localized_title_en_US",
			"delta");
	}

	@Rule
	public TestName testName = new TestName();

	private void _addJournalArticles(String... titles) throws Exception {
		for (String title : titles) {
			_journalArticles.add(
				JournalTestUtil.addArticle(
					TestPropsValues.getGroupId(), 0,
					PortalUtil.getClassNameId(JournalArticle.class),
					HashMapBuilder.put(
						LocaleUtil.US, title
					).build(),
					null,
					HashMapBuilder.put(
						LocaleUtil.US, ""
					).build(),
					LocaleUtil.getSiteDefault(), false, true,
					ServiceContextTestUtil.getServiceContext()));
		}
	}

	private SXPBlueprint _addSXPBlueprint() throws Exception {
		Class<?> clazz = getClass();

		return _sxpBlueprintLocalService.addSXPBlueprint(
			TestPropsValues.getUserId(),
			StringUtil.read(
				clazz,
				StringBundler.concat(
					"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
					testName.getMethodName(), ".json")),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			"", "",
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertSearch(
			String expected, String fieldName, String keywords)
		throws Exception {

		SearchResponse searchResponse = _searcher.search(
			_searchRequestBuilderFactory.builder(
			).companyId(
				TestPropsValues.getCompanyId()
			).queryString(
				keywords
			).withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.experiences.blueprint.id",
					_sxpBlueprint.getSXPBlueprintId())
			).build());

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), fieldName, expected);
	}

	@DeleteAfterTestRun
	private List<JournalArticle> _journalArticles = new ArrayList<>();

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}