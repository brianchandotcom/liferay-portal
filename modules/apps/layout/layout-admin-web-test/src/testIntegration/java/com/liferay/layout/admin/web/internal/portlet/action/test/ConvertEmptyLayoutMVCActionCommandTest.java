/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.admin.web.internal.portlet.constants.LayoutAdminWebPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.PortletException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class ConvertEmptyLayoutMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = _groupLocalService.getGroup(TestPropsValues.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(),
			TestPropsValues.getUserId());

		_serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testCannotConvertEmptyLayoutToEmbeddedLayout()
		throws Exception {

		Layout emptyLayout = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			LayoutConstants.TYPE_EMPTY, true, StringPool.BLANK,
			_serviceContext);

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(
				emptyLayout, LayoutConstants.TYPE_EMBEDDED,
				TestPropsValues.getUser()),
			new MockLiferayPortletActionResponse());

		Layout layout = _layoutLocalService.getLayout(emptyLayout.getPlid());

		Assert.assertTrue(layout.isTypeEmpty());
	}

	@Test
	public void testCannotConvertEmptyLayoutToTypeLinkToLayoutLayout()
		throws Exception {

		Layout emptyLayout = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			LayoutConstants.TYPE_EMPTY, true, StringPool.BLANK,
			_serviceContext);

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(
				emptyLayout, LayoutConstants.TYPE_LINK_TO_LAYOUT,
				TestPropsValues.getUser()),
			new MockLiferayPortletActionResponse());

		Layout layout = _layoutLocalService.getLayout(emptyLayout.getPlid());

		Assert.assertTrue(layout.isTypeEmpty());
	}

	@Test
	public void testConvertEmptyLayoutToPortletLayoutWithoutPermissions()
		throws Exception {

		Layout emptyLayout = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			LayoutConstants.TYPE_EMPTY, true, StringPool.BLANK,
			_serviceContext);

		User user = _userLocalService.getDefaultUser(_group.getCompanyId());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, PermissionCheckerFactoryUtil.create(user))) {

			Exception exception = Assert.assertThrows(
				PortletException.class,
				() -> _mvcActionCommand.processAction(
					_getMockLiferayPortletActionRequest(
						emptyLayout, LayoutConstants.TYPE_PORTLET, user),
					new MockLiferayPortletActionResponse()));

			Assert.assertTrue(
				exception.getCause() instanceof PrincipalException);
		}
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Layout layout, String type, User user)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"selPlid", String.valueOf(layout.getPlid()));
		mockLiferayPortletActionRequest.addParameter("type", type);
		mockLiferayPortletActionRequest.addParameter(
			"name", layout.getExternalReferenceCode());
		mockLiferayPortletActionRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_CONFIG,
			PortletConfigFactoryUtil.create(
				PortletLocalServiceUtil.getPortletById(
					LayoutAdminWebPortletKeys.LAYOUT_ADMIN_WEB_TEST_PORTLET),
				null));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(_group.getCompanyId()));
		themeDisplay.setLanguageId(_group.getDefaultLanguageId());
		themeDisplay.setLayout(layout);
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setRequest(
			mockLiferayPortletActionRequest.getHttpServletRequest());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockLiferayPortletActionRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/layout_admin/convert_empty_layout")
	private MVCActionCommand _mvcActionCommand;

	private ServiceContext _serviceContext;

	@Inject
	private UserLocalService _userLocalService;

}