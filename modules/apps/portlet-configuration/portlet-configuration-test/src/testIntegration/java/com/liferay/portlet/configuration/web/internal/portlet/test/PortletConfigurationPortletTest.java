/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.configuration.web.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MutableActionParameters;
import javax.portlet.Portlet;
import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class PortletConfigurationPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_testPortlet = _portletLocalService.getPortletById(
			_company.getCompanyId(), PortletKeys.BLOGS);
	}

	@Test
	public void testEditScopeForLayoutPortlet() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		_portletPreferencesLocalService.addPortletPreferences(
			_company.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			_testPortlet.getPortletId(), _testPortlet,
			_getDefaultPreferences(
				HashMapBuilder.put(
					"lfrScopeLayoutUuid", StringPool.BLANK
				).put(
					"lfrScopeType", StringPool.BLANK
				).put(
					"portletSetupTitle_en_US", "blogs"
				).put(
					"portletSetupUseCustomTitle", Boolean.TRUE.toString()
				).build()));

		ReflectionTestUtil.invoke(
			_portlet, "_updateScope", new Class<?>[] {ActionRequest.class},
			_getMockActionRequestForScopeTest(layout));

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.getPreferences(
				_company.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				_testPortlet.getPortletId());

		String scope = portletPreferences.getValue(
			"lfrScopeType", StringPool.BLANK);

		Assert.assertEquals("company", scope);
	}

	@Test
	public void testEditScopeForSharedPortlet() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		String defaultPreferences = _getDefaultPreferences(
			HashMapBuilder.put(
				"lfrScopeLayoutUuid", StringPool.BLANK
			).put(
				"lfrScopeType", StringPool.BLANK
			).put(
				"portletSetupTitle_en_US", "blogs"
			).put(
				"portletSetupUseCustomTitle", Boolean.TRUE.toString()
			).build());

		_portletPreferencesLocalService.addPortletPreferences(
			_company.getCompanyId(), _group.getGroupId(),
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, PortletKeys.PREFS_PLID_SHARED,
			_testPortlet.getPortletId(), _testPortlet, defaultPreferences);

		_portletPreferencesLocalService.addPortletPreferences(
			_company.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			_testPortlet.getPortletId(), _testPortlet, defaultPreferences);

		ReflectionTestUtil.invoke(
			_portlet, "_updateScope", new Class<?>[] {ActionRequest.class},
			_getMockActionRequestForScopeTest(layout));

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.getPreferences(
				_company.getCompanyId(), _group.getGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				PortletKeys.PREFS_PLID_SHARED, _testPortlet.getPortletId());

		String scope = portletPreferences.getValue(
			"lfrScopeType", StringPool.BLANK);

		Assert.assertEquals("company", scope);
	}

	@Test
	public void testUpdateRolePermissions() throws Exception {
		List<String> plids = _addLayouts(1);

		List<Long> roleIds = _addRoles();

		ReflectionTestUtil.invoke(
			_portlet, "updateRolePermissions",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			_getMockActionRequest(plids, roleIds), new MockActionResponse());

		_assertResourcePermissions(plids, roleIds);
	}

	@Test
	public void testUpdateRolePermissionsInBulk() throws Exception {
		List<String> plids = _addLayouts(100);
		List<Long> roleIds = _addRoles();

		ReflectionTestUtil.invoke(
			_portlet, "updateRolePermissions",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			_getMockActionRequest(plids, roleIds), new MockActionResponse());

		_assertResourcePermissions(plids, roleIds);
	}

	private List<String> _addLayouts(int numberOfItems) throws Exception {
		List<String> plids = new ArrayList<>();

		for (int i = 0; i < numberOfItems; i++) {
			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			plids.add(String.valueOf(layout.getPlid()));
		}

		return plids;
	}

	private List<Long> _addRoles() throws Exception {
		List<Long> roleIds = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

			roleIds.add(role.getRoleId());
		}

		return roleIds;
	}

	private void _assertResourcePermissions(
		List<String> plids, List<Long> roleIds) {

		for (String plid : plids) {
			for (long roleId : roleIds) {
				Assert.assertNotNull(
					_resourcePermissionLocalService.fetchResourcePermission(
						_company.getCompanyId(), Layout.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL, plid, roleId));
			}
		}
	}

	private String _getDefaultPreferences(HashMap<String, String> preferences) {
		StringBundler sb = new StringBundler();

		sb.append("<portlet-preferences>");

		for (Map.Entry<String, String> entry : preferences.entrySet()) {
			sb.append("<preference><name>");
			sb.append(entry.getKey());
			sb.append("</name><value>");
			sb.append(entry.getValue());
			sb.append("</value></preference>");
		}

		sb.append("</portlet-preferences>");

		return sb.toString();
	}

	private MockActionRequest _getMockActionRequest(
			List<String> plids, List<Long> roleIds)
		throws Exception {

		MockActionRequest mockActionRequest = new MockActionRequest();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"resourcePrimKey", StringUtil.merge(plids, StringPool.COMMA));

		mockActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, mockHttpServletRequest);

		mockActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockActionRequest.setParameter("modelResource", Layout.class.getName());
		mockActionRequest.setParameter(
			"resourceGroupId", String.valueOf(_group.getGroupId()));

		String roleSearchContainerPrimaryKeys = StringPool.BLANK;

		for (long roleId : roleIds) {
			mockActionRequest.setParameter(roleId + "_ACTION_DELETE", "");
			mockActionRequest.setParameter(roleId + "_ACTION_UPDATE", "");
			mockActionRequest.setParameter(roleId + "_ACTION_VIEW", "");

			roleSearchContainerPrimaryKeys = StringBundler.concat(
				roleSearchContainerPrimaryKeys, roleId, StringPool.COMMA);
		}

		mockActionRequest.setParameter(
			"rolesSearchContainerPrimaryKeys", roleSearchContainerPrimaryKeys);

		return mockActionRequest;
	}

	private MockActionRequest _getMockActionRequestForScopeTest(Layout layout)
		throws Exception {

		MockActionRequest mockActionRequest = new MockActionRequest();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockActionRequest.setParameter("scope", "company");

		mockHttpServletRequest.setParameter("scope", "company");

		mockActionRequest.setParameter(
			"portletResource", _testPortlet.getPortletId());

		mockHttpServletRequest.setParameter(
			"portletResource", _testPortlet.getPortletId());

		mockActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, mockHttpServletRequest);

		mockActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		return mockActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());

		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private ThemeDisplay _getThemeDisplay(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());

		themeDisplay.setLanguageId(LocaleUtil.toLanguageId(LocaleUtil.US));
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.portlet.configuration.web.internal.portlet.PortletConfigurationPortlet"
	)
	private Portlet _portlet;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private com.liferay.portal.kernel.model.Portlet _testPortlet;

	private static class MockActionRequest
		extends MockLiferayPortletActionRequest {

		@Override
		public ActionParameters getActionParameters() {
			return new MockActionParameters();
		}

		private class MockActionParameters implements ActionParameters {

			@Override
			public MutableActionParameters clone() {
				return null;
			}

			@Override
			public Set<String> getNames() {
				return getParameterMap().keySet();
			}

			@Override
			public String getValue(String name) {
				return getParameter(name);
			}

			@Override
			public String[] getValues(String name) {
				return getParameterValues(name);
			}

			@Override
			public boolean isEmpty() {
				return getParameterMap().isEmpty();
			}

			@Override
			public int size() {
				return getParameterMap().size();
			}

		}

	}

}