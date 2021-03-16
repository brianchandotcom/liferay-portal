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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
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
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
		_userSearchFixture = new UserSearchFixture();

		_userSearchFixture.setUp();

		_users = _userSearchFixture.getUsers();

		_addGroupAndUser();

		_layout = LayoutTestUtil.addLayout(_group.getGroupId());

		_createJournalArticles();
	}

	@Test
	public void testBoostAClause() throws Exception {
		_addCompanyBlueprint(readConfiguration());

		List<String> expectedValues = new ArrayList<>();

		expectedValues.add(_titles[2]);
		expectedValues.add(_titles[0]);
		expectedValues.add(_titles[1]);

		_assertSearch(
			"localized_title", "restaurant 1", String.valueOf(expectedValues));
	}

	@Test
	public void testMustBeClause() throws Exception {
		_addCompanyBlueprint(readConfiguration());

		List<String> expectedValues = ListUtil.fromArray(_titles[2]);

		_assertSearch(
			"localized_title", "restaurant 1", String.valueOf(expectedValues));
	}

	@Test
	public void testMustNotBeClause() throws Exception {
		_addCompanyBlueprint(readConfiguration());

		List<String> expectedValues = new ArrayList<>();

		expectedValues.add(_titles[0]);
		expectedValues.add(_titles[1]);

		_assertSearch(
			"localized_title", "restaurant 1", String.valueOf(expectedValues));
	}

	@Test
	public void testTextMatch() throws Exception {
		_addCompanyBlueprint(readConfiguration());

		List<String> expectedValues = ListUtil.fromArray(_titles[2]);

		_assertSearch(
			"localized_title", "cafe", String.valueOf(expectedValues));
	}

	@Rule
	public TestName testName = new TestName();

	protected String readConfiguration() {
		Class<?> clazz = getClass();

		StringBundler sb = new StringBundler(5);

		sb.append(clazz.getSimpleName());
		sb.append(CharPool.PERIOD);
		sb.append(testName.getMethodName());
		sb.append(CharPool.PERIOD);
		sb.append("json");

		return StringUtil.read(clazz, sb.toString());
	}

	private void _addCompanyBlueprint(String configurationString)
		throws Exception {

		Blueprint blueprint = _blueprintService.addCompanyBlueprint(
			Collections.singletonMap(LocaleUtil.US, "Los Angeles Boost"),
			Collections.singletonMap(LocaleUtil.US, "test Description"),
			configurationString, "", 1, _getServiceContext());

		_blueprint = blueprint;

		_updateBlueprintWithMVCActionCommand(blueprint, configurationString);
	}

	private void _addGroupAndUser() throws Exception {
		GroupSearchFixture groupSearchFixture = new GroupSearchFixture();

		_group = groupSearchFixture.addGroup(new GroupBlueprint());

		_groups = groupSearchFixture.getGroups();

		_user = TestPropsValues.getUser();

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(_user));
	}

	private void _assertSearch(
		String fieldName, String keywords, String expected) {

		SearchResponse searchResponse = _searcher.search(
			_searchRequestBuilderFactory.builder(
			).addComplexQueryPart(
				_complexQueryPartBuilderFactory.builder(
				).field(
					"screenName"
				).occur(
					"must_not"
				).type(
					"term"
				).value(
					"test"
				).build()
			).companyId(
				_group.getCompanyId()
			).groupIds(
				_group.getGroupId()
			).withSearchContext(
				searchContext -> {
					searchContext.setAttribute(
						SearchContextAttributeKeys.BLUEPRINT_ID,
						_blueprint.getBlueprintId());
					searchContext.setKeywords(keywords);
					searchContext.setLayout(_layout);
					searchContext.setTimeZone(_user.getTimeZone());
					searchContext.setUserId(_user.getUserId());
				}
			).build());

		DocumentsAssert.assertValues(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), fieldName, expected);
	}

	private void _createJournalArticles() throws Exception {
		String[] titles = {"restaurant 1", "restaurant 2", "la cafe"};

		_titles = titles;

		JournalTestUtil.addArticle(
			_group.getGroupId(), _titles[0], "restaurant content");

		JournalTestUtil.addArticle(
			_group.getGroupId(), _titles[1], "restaurant content");

		JournalTestUtil.addArticle(
			_group.getGroupId(), _titles[2], "los angeles restaurant");
	}

	private MockActionRequest _getMockActionrequest() throws Exception {
		MockLiferayPortletActionRequest mockActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLanguageId(
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()));
		themeDisplay.setPlid(_layout.getPlid());
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

		MockActionRequest mockActionRequest = _getMockActionrequest();

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

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "doProcessAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockActionRequest, mockActionResponse);
	}

	@DeleteAfterTestRun
	private Blueprint _blueprint;

	@Inject
	private BlueprintService _blueprintService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=" + BlueprintsAdminMVCCommandNames.EDIT_BLUEPRINT
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private String[] _titles;
	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

	private UserSearchFixture _userSearchFixture;

}