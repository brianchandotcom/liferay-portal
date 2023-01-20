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

package com.liferay.search.experiences.internal.blueprint.parameter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class SXPParameterDataCreatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		WorkflowThreadLocal.setEnabled(false);
	}

	@AfterClass
	public static void tearDownClass() {
		WorkflowThreadLocal.setEnabled(true);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, _user.getUserId());

		_sxpBlueprint = _sxpBlueprintLocalService.addSXPBlueprint(
			null, _user.getUserId(), _defaultConfigurationJSONObject.toString(),
			Collections.singletonMap(LocaleUtil.US, StringPool.BLANK),
			StringPool.BLANK, StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			_serviceContext);
	}

	@Test
	public void testBreakingCharacters() throws Exception {
		_addJournalArticle(
			_group.getGroupId(), 0,
			Collections.singletonMap(LocaleUtil.US, "break"), StringPool.BLANK,
			false, true);

		_assertSearch("[break]", "/\\$[] break");
	}

	@Test
	public void testPhraseSearch() throws Exception {
		_addJournalArticle(
			_group.getGroupId(), 0,
			Collections.singletonMap(LocaleUtil.US, "phrase"), StringPool.BLANK,
			false, true);

		_addJournalArticle(
			_group.getGroupId(), 0,
			Collections.singletonMap(LocaleUtil.US, "phrase search"),
			StringPool.BLANK, false, true);

		_assertSearch("[phrase search]", "\"phrase search\"");
	}

	// https://issues.liferay.com/browse/LPS-173239

	@Test
	public void testPropertyExpanderLoopResolves() throws Exception {
		_addJournalArticle(
			_group.getGroupId(), 0,
			Collections.singletonMap(LocaleUtil.US, "substitution"),
			StringPool.BLANK, false, true);

		_assertSearch("[substitution]", "${keywords} substitution");
	}

	private JournalArticle _addJournalArticle(
			long groupId, long folderId, Map<Locale, String> titleMap,
			String content, boolean workflowEnabled, boolean approved)
		throws Exception {

		return JournalTestUtil.addArticle(
			groupId, folderId, PortalUtil.getClassNameId(JournalArticle.class),
			titleMap, null,
			HashMapBuilder.put(
				LocaleUtil.US, content
			).build(),
			LocaleUtil.getSiteDefault(), workflowEnabled, approved,
			_serviceContext);
	}

	private void _assertSearch(String expected, String keywords)
		throws Exception {

		SearchResponse searchResponse = _searcher.search(
			_searchRequestBuilderFactory.builder(
			).companyId(
				TestPropsValues.getCompanyId()
			).queryString(
				keywords
			).withSearchContext(
				_searchContext -> {
					_searchContext.setAttribute(
						"search.experiences.blueprint.id",
						String.valueOf(_sxpBlueprint.getSXPBlueprintId()));
					_searchContext.setAttribute(
						"search.experiences.scope.group.id",
						_group.getGroupId());
					_searchContext.setUserId(_serviceContext.getUserId());
				}
			).build());

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(), searchResponse.getDocuments(),
			"title_en_US", expected);
	}

	private final JSONObject _defaultConfigurationJSONObject = JSONUtil.put(
		"generalConfiguration",
		JSONUtil.put(
			"searchableAssetTypes",
			JSONUtil.put("com.liferay.journal.model.JournalArticle"))
	).put(
		"queryConfiguration", JSONUtil.put("applyIndexerClauses", true)
	);

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private SXPBlueprint _sxpBlueprint;

	@Inject
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	private User _user;

}