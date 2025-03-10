/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletSession;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Gustavo Lima
 */
@RunWith(Arquillian.class)
public class EditMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_companyAdminUser = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup();
		_omniadminUser = UserTestUtil.addOmniadminUser();
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		_user = UserTestUtil.addUser(_company);

		_setUpLayout();
	}

	@AfterClass
	public static void tearDownClass() throws PortalException {
		_companyLocalService.deleteCompany(_company);

		PrincipalThreadLocal.setName(_originalName);

		_layout.setType(_layoutType);
	}

	@Test
	public void testPermissionToReindexAsCompanyAdmin() throws Exception {
		UserTestUtil.setUser(_companyAdminUser);

		Assert.assertTrue(
			_editMVCActionCommand.processAction(
				_getMockLiferayPortletActionRequest(
					HashMapBuilder.put(
						Constants.CMD,
						new String[] {RandomTestUtil.randomString()}
					).put(
						"companyIds",
						new String[] {String.valueOf(_company.getCompanyId())}
					).build()),
				new MockLiferayPortletActionResponse()));
	}

	@Test
	public void testPermissionToReindexAsOmniadmin() throws Exception {
		UserTestUtil.setUser(_omniadminUser);

		Assert.assertTrue(
			_editMVCActionCommand.processAction(
				_getMockLiferayPortletActionRequest(
					HashMapBuilder.put(
						Constants.CMD,
						new String[] {RandomTestUtil.randomString()}
					).put(
						"companyIds",
						new String[] {String.valueOf(_company.getCompanyId())}
					).build()),
				new MockLiferayPortletActionResponse()));
	}

	@Test
	public void testPermissionToReindexAsUser() throws Exception {
		UserTestUtil.setUser(_user);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				HashMapBuilder.put(
					Constants.CMD, new String[] {RandomTestUtil.randomString()}
				).put(
					"companyIds",
					new String[] {String.valueOf(_company.getCompanyId())}
				).build());

		Assert.assertFalse(
			_editMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				PrincipalException.MustHavePermission.class.getName()));
	}

	@Test
	public void testPermissionToReindexMultipleCompaniesAsCompanyAdmin()
		throws Exception {

		UserTestUtil.setUser(_companyAdminUser);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				HashMapBuilder.put(
					Constants.CMD, new String[] {RandomTestUtil.randomString()}
				).put(
					"companyIds",
					new String[] {
						String.valueOf(_company.getCompanyId()),
						String.valueOf(RandomTestUtil.randomLong())
					}
				).build());

		Assert.assertFalse(
			_editMVCActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				PrincipalException.MustHavePermission.class.getName()));
	}

	@Test
	public void testPermissionToReindexMultipleCompaniesAsOmniadmin()
		throws Exception {

		UserTestUtil.setUser(_omniadminUser);

		Assert.assertTrue(
			_editMVCActionCommand.processAction(
				_getMockLiferayPortletActionRequest(
					HashMapBuilder.put(
						Constants.CMD,
						new String[] {RandomTestUtil.randomString()}
					).put(
						"companyIds",
						new String[] {
							String.valueOf(_company.getCompanyId()),
							String.valueOf(RandomTestUtil.randomLong())
						}
					).build()),
				new MockLiferayPortletActionResponse()));
	}

	private static void _setUpLayout() throws Exception {
		_layout = _layoutLocalService.fetchLayout(TestPropsValues.getPlid());

		_layoutType = _layout.getType();

		_layout.setType(LayoutConstants.TYPE_CONTROL_PANEL);
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Map<String, String[]> parameters)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			mockLiferayPortletActionRequest.setParameter(
				entry.getKey(), entry.getValue());
		}

		mockLiferayPortletActionRequest.setPortletSession(
			new MockPortletSession());

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRequest(new MockHttpServletRequest());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private static Company _company;
	private static User _companyAdminUser;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Group _group;
	private static Layout _layout;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	private static String _layoutType;
	private static User _omniadminUser;
	private static String _originalName;
	private static User _user;

	@Inject(filter = "mvc.command.name=/portal_search_admin/edit")
	private MVCActionCommand _editMVCActionCommand;

}