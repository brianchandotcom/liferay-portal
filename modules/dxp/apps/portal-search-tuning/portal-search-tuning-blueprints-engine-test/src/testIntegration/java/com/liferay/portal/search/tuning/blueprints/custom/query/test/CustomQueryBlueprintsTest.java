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

package com.liferay.portal.search.tuning.blueprints.custom.query.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.engine.constants.SearchContextAttributeKeys;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class CustomQueryBlueprintsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		GroupSearchFixture groupSearchFixture = new GroupSearchFixture();

		_group = groupSearchFixture.addGroup(new GroupBlueprint());

		_groups = groupSearchFixture.getGroups();

		_user = TestPropsValues.getUser();
	}

	@Test
	public void testBoostAClause() throws Exception {
		addArticle("cafe 1", RandomTestUtil.randomString());
		addArticle("cafe 2", RandomTestUtil.randomString());
		addArticle("cafe 3", "los angeles");

		addBlueprint();

		_assertSearch("cafe 2", "[cafe 3, cafe 2, cafe 1]");
	}

	@Test
	public void testMustBeClause() throws Exception {
		addArticle("cafe 1", RandomTestUtil.randomString());
		addArticle("cafe 2", RandomTestUtil.randomString());
		addArticle("cafe 3", "los angeles");

		addBlueprint();

		_assertSearch("cafe 2", "[cafe 3]");
	}

	@Test
	public void testMustNotBeClause() throws Exception {
		addArticle("cafe 1", RandomTestUtil.randomString());
		addArticle("cafe 2", RandomTestUtil.randomString());
		addArticle("cafe 3", "los angeles");

		addBlueprint();

		_assertSearch("cafe 2", "[cafe 2, cafe 1]");
	}

	@Test
	public void testTextMatch() throws Exception {
		addArticle("restaurant 1", RandomTestUtil.randomString());
		addArticle("restaurant 2", RandomTestUtil.randomString());
		addArticle("cafe 1", RandomTestUtil.randomString());

		addBlueprint();

		_assertSearch("cafe", "[cafe 1]");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Rule
	public TestName testName = new TestName();

	protected void addArticle(String title, String content) throws Exception {
		JournalTestUtil.addArticle(_group.getGroupId(), title, content);
	}

	protected void addBlueprint() throws Exception {
		String configurationString = readConfiguration();

		Blueprint blueprint = blueprintService.addCompanyBlueprint(
			Collections.singletonMap(LocaleUtil.US, "Los Angeles Boost"),
			Collections.singletonMap(LocaleUtil.US, "test Description"),
			configurationString, "", 1, _getServiceContext());

		_blueprint = blueprint;

		_updateBlueprintWithMVCActionCommand(blueprint, configurationString);
	}

	protected String readConfiguration() {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz,
			StringBundler.concat(
				clazz.getSimpleName(), StringPool.PERIOD,
				testName.getMethodName(), ".json"));
	}

	@Inject
	protected BlueprintService blueprintService;

	@Inject
	protected CompanyLocalService companyLocalService;

	@Inject
	protected ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory;

	@Inject(
		filter = "mvc.command.name=" + BlueprintsAdminMVCCommandNames.EDIT_BLUEPRINT
	)
	protected MVCActionCommand mvcActionCommand;

	@Inject
	protected Searcher searcher;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private void _assertSearch(String keywords, String expected) {
		SearchResponse searchResponse = searcher.search(
			searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).groupIds(
				_group.getGroupId()
			).queryString(
				keywords
			).withSearchContext(
				searchContext -> searchContext.setAttribute(
					SearchContextAttributeKeys.BLUEPRINT_ID,
					_blueprint.getBlueprintId())
			).build());

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), "localized_title", expected);
	}

	private MockActionRequest _getMockActionRequest() throws Exception {
		MockLiferayPortletActionRequest mockActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLanguageId(
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()));
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setUser(_user);

		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return mockActionRequest;
	}

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());
	}

	private void _updateBlueprintWithMVCActionCommand(
			Blueprint blueprint, String configurationString)
		throws Exception {

		MockActionRequest mockActionRequest = _getMockActionRequest();

		mockActionRequest.setParameter(
			BlueprintsAdminWebKeys.BLUEPRINT_ID,
			String.valueOf(blueprint.getBlueprintId()));

		mockActionRequest.setParameter(
			BlueprintsAdminWebKeys.BLUEPRINT_TYPE, "1");

		mockActionRequest.setParameter(
			"title_" + LocaleUtil.toLanguageId(LocaleUtil.US),
			"Los Angeles Boost");

		mockActionRequest.setParameter("configuration", configurationString);
		mockActionRequest.setParameter(Constants.CMD, "edit");

		MockLiferayPortletActionResponse mockActionResponse =
			new MockLiferayPortletActionResponse();

		mvcActionCommand.processAction(mockActionRequest, mockActionResponse);
	}

	@DeleteAfterTestRun
	private Blueprint _blueprint;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private User _user;

}