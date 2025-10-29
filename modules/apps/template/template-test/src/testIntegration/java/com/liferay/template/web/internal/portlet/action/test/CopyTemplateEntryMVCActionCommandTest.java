/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;
import com.liferay.template.test.util.TemplateTestUtil;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
@Sync
public class CopyTemplateEntryMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = _groupLocalService.fetchGroup(TestPropsValues.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_templateEntry = TemplateTestUtil.addAnyTemplateEntry(
			_infoItemServiceRegistry, serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testCopyTemplateEntry() throws Exception {
		ActionRequestSetupTest setup = _setUpActionRequest();

		_invokeActionRequest(setup.mockLiferayPortletActionRequest, false);

		List<TemplateEntry> templateEntries =
			_templateEntryLocalService.getTemplateEntries(
				_group.getGroupId(), _templateEntry.getInfoItemClassName(),
				_templateEntry.getInfoItemFormVariationKey(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertTrue(templateEntries.size() > 1);

		TemplateEntry templateEntry = null;

		DDMTemplate originalDDMTemplate =
			_ddmTemplateLocalService.getDDMTemplate(
				_templateEntry.getDDMTemplateId());

		for (TemplateEntry curTemplateEntry : templateEntries) {
			DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchDDMTemplate(
				curTemplateEntry.getDDMTemplateId());

			if ((ddmTemplate != null) &&
				Objects.equals(
					setup.name, ddmTemplate.getName(setup.languageId)) &&
				Objects.equals(
					setup.description,
					ddmTemplate.getDescription(setup.languageId)) &&
				Objects.equals(
					originalDDMTemplate.getScript(), ddmTemplate.getScript())) {

				templateEntry = curTemplateEntry;

				break;
			}
		}

		Assert.assertNotNull(templateEntry);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	@TestInfo("LPD-69505")
	public void testCopyTemplateEntryWithNoPermissions() throws Exception {
		ActionRequestSetupTest setup = _setUpActionRequest();

		_invokeActionRequest(setup.mockLiferayPortletActionRequest, true);
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private void _invokeActionRequest(
			ActionRequest actionRequest, boolean noPermissions)
		throws Exception {

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if (noPermissions) {
				User user = UserTestUtil.addUser();

				PermissionThreadLocal.setPermissionChecker(
					_permissionCheckerFactory.create(user));
			}

			ReflectionTestUtil.invoke(
				_mvcActionCommand, "doTransactionalCommand",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				actionRequest, new MockLiferayPortletActionResponse());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	private ActionRequestSetupTest _setUpActionRequest() throws Exception {
		ActionRequestSetupTest setup = new ActionRequestSetupTest();

		setup.mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		setup.mockLiferayPortletActionRequest.addParameter(
			"templateEntryId",
			String.valueOf(_templateEntry.getTemplateEntryId()));

		setup.languageId = LocaleUtil.toLanguageId(
			_portal.getSiteDefaultLocale(_group.getGroupId()));
		setup.name = RandomTestUtil.randomString();

		setup.mockLiferayPortletActionRequest.addParameter(
			"name_" + setup.languageId, setup.name);

		setup.description = RandomTestUtil.randomString();

		setup.mockLiferayPortletActionRequest.addParameter(
			"description_" + setup.languageId, setup.description);

		return setup;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject(filter = "mvc.command.name=/template/copy_template_entry")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private Portal _portal;

	@DeleteAfterTestRun
	private TemplateEntry _templateEntry;

	@Inject
	private TemplateEntryLocalService _templateEntryLocalService;

	private static class ActionRequestSetupTest {

		public String description;
		public String languageId;
		public MockLiferayPortletActionRequest mockLiferayPortletActionRequest;
		public String name;

	}

}