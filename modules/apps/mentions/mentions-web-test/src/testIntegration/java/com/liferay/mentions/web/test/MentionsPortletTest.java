/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mentions.web.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class MentionsPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_companyLocalService.deleteCompany(_company);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Before
	public void setUp() throws Exception {
		User adminUser = UserTestUtil.getAdminUser(_company.getCompanyId());

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), adminUser.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_layout = _addLayout(_group.getGroupId(), adminUser.getUserId());
	}

	@Test
	public void testServletResponseWithoutQuery() throws Exception {
		_addUser("example");

		JSONArray jsonArray = _getServletResponseJSONArray(null);

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals("example", jsonObject.getString("screenName"));
	}

	@Test
	public void testServletResponseWithQueryWithFullScreenName()
		throws Exception {

		_addUser("example");

		JSONArray jsonArray = _getServletResponseJSONArray("example");

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals("example", jsonObject.getString("screenName"));
	}

	@Test
	public void testServletResponseWithQueryWithPartialScreenName()
		throws Exception {

		_addUser("example");

		JSONArray jsonArray = _getServletResponseJSONArray("exa");

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals("example", jsonObject.getString("screenName"));
	}

	@Test
	public void testServletResponseWithQueryWithWildard() throws Exception {
		_addUser("example");

		JSONArray jsonArray = _getServletResponseJSONArray("");

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals("example", jsonObject.getString("screenName"));
	}

	@Test
	public void testServletResponseWithQueryWithWildcardAndNoResults()
		throws Exception {

		JSONArray jsonArray = _getServletResponseJSONArray("");

		Assert.assertEquals(0, jsonArray.length());
	}

	private Layout _addLayout(long groupId, long userId) throws Exception {
		String name = RandomTestUtil.randomString();

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		return _layoutLocalService.addLayout(
			userId, groupId, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			name, null, RandomTestUtil.randomString(),
			LayoutConstants.TYPE_PORTLET, false, friendlyURL,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _addUser(String screenName) throws Exception {
		User adminUser = UserTestUtil.getAdminUser(_company.getCompanyId());

		_users.add(
			UserTestUtil.addUser(
				_company.getCompanyId(), adminUser.getUserId(), screenName,
				LocaleUtil.getDefault(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), new long[] {_group.getGroupId()},
				ServiceContextTestUtil.getServiceContext(
					_company.getGroupId())));
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
			String query)
		throws Exception {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
		mockLiferayResourceRequest.setParameter(
			"discussionPortletId", themeDisplay.getPpid());

		if (query != null) {
			mockLiferayResourceRequest.setParameter("query", query);
		}

		return mockLiferayResourceRequest;
	}

	private JSONArray _getServletResponseJSONArray(String query)
		throws Exception {

		MVCPortlet mvcPortlet = (MVCPortlet)_portlet;

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		mvcPortlet.serveResource(
			_getMockLiferayResourceRequest(query), mockLiferayResourceResponse);

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayResourceResponse.getHttpServletResponse();

		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON,
			mockHttpServletResponse.getContentType());

		return JSONFactoryUtil.createJSONArray(
			mockHttpServletResponse.getContentAsString());
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setPpid(MentionsPortletKeys.MENTIONS);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(
			UserTestUtil.getAdminUser(_company.getCompanyId()));

		return themeDisplay;
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static String _originalName;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "javax.portlet.name=" + MentionsPortletKeys.MENTIONS)
	private Portlet _portlet;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}