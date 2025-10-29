/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.exception.TemplateNameException;
import com.liferay.dynamic.data.mapping.exception.TemplateScriptException;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
@Sync
public class AddTemplateEntryMVCActionCommandTest {

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
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testAddTemplateEntry() throws Exception {
		ActionRequestSetupTest setup = _setUpActionRequest();

		_invokeActionRequest(setup, false);

		List<TemplateEntry> templateEntries =
			_templateEntryLocalService.getTemplateEntries(
				_group.getGroupId(), setup.infoItemClassDetails.getClassName(),
				setup.infoItemFormVariationKey, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			templateEntries.toString(), setup.initialTemplateEntries.size() + 1,
			templateEntries.size());

		TemplateEntry newTemplateEntry = null;

		for (TemplateEntry entry : templateEntries) {
			boolean foundInInitial = false;

			for (TemplateEntry initialEntry : setup.initialTemplateEntries) {
				if (entry.getTemplateEntryId() ==
						initialEntry.getTemplateEntryId()) {

					foundInInitial = true;

					break;
				}
			}

			if (!foundInInitial) {
				newTemplateEntry = entry;

				break;
			}
		}

		Assert.assertNotNull(newTemplateEntry);

		try {
			DDMTemplate ddmTemplate = _ddmTemplateLocalService.getTemplate(
				newTemplateEntry.getDDMTemplateId());

			Assert.assertNotNull(ddmTemplate);
		}
		finally {
			if (newTemplateEntry != null) {
				_templateEntryLocalService.deleteTemplateEntry(
					newTemplateEntry.getTemplateEntryId());
				_ddmTemplateLocalService.deleteTemplate(
					newTemplateEntry.getDDMTemplateId());
			}
		}
	}

	@Test
	@TestInfo("LPD-69505")
	public void testAddTemplateEntryWithNoPermissions() throws Exception {
		ActionRequestSetupTest setup = _setUpActionRequest();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			_invokeActionRequest(setup, true);

		List<TemplateEntry> templateEntries =
			_templateEntryLocalService.getTemplateEntries(
				_group.getGroupId(), setup.infoItemClassDetails.getClassName(),
				setup.infoItemFormVariationKey, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			templateEntries.toString(), setup.initialTemplateEntries.size(),
			templateEntries.size());

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayPortletActionResponse.getHttpServletResponse();

		Assert.assertEquals(
			"application/json", mockHttpServletResponse.getContentType());

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		Assert.assertEquals(
			"you-do-not-have-the-required-permissions",
			responseJSONObject.getJSONObject(
				"error"
			).getString(
				"other"
			));
	}

	@Test
	public void testHandleTemplateNameException() throws Exception {
		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_getErrorJSONObject",
			new Class<?>[] {PortalException.class, ThemeDisplay.class},
			new TemplateNameException(), _getThemeDisplay());

		Assert.assertTrue(jsonObject.has("name"));
	}

	@Test
	public void testHandleTemplateScriptException() throws Exception {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_getErrorJSONObject",
			new Class<?>[] {PortalException.class, ThemeDisplay.class},
			new TemplateScriptException(), themeDisplay);

		Assert.assertTrue(jsonObject.has("other"));

		Assert.assertEquals(
			LanguageUtil.get(
				themeDisplay.getLocale(), "please-enter-a-valid-script"),
			jsonObject.get("other"));
	}

	@Test
	public void testHandleUnexpectedException() throws Exception {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_getErrorJSONObject",
			new Class<?>[] {PortalException.class, ThemeDisplay.class},
			new PortalException(), themeDisplay);

		Assert.assertTrue(jsonObject.has("other"));

		Assert.assertEquals(
			LanguageUtil.get(
				themeDisplay.getLocale(), "an-unexpected-error-occurred"),
			jsonObject.get("other"));
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

	private MockLiferayPortletActionResponse _invokeActionRequest(
			ActionRequestSetupTest setup, boolean noPermissions)
		throws Exception {

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		try {
			if (noPermissions) {
				_user = UserTestUtil.addUser();

				PermissionThreadLocal.setPermissionChecker(
					_permissionCheckerFactory.create(_user));
			}

			ReflectionTestUtil.invoke(
				_mvcActionCommand, "doTransactionalCommand",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				setup.mockLiferayPortletActionRequest,
				mockLiferayPortletActionResponse);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}

		return mockLiferayPortletActionResponse;
	}

	private ActionRequestSetupTest _setUpActionRequest() throws Exception {
		ActionRequestSetupTest setup = new ActionRequestSetupTest();

		setup.mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		setup.infoItemClassDetails =
			TemplateTestUtil.getFirstTemplateInfoItemClassDetails(
				_infoItemServiceRegistry, _group.getGroupId());

		setup.infoItemFormVariationKey = StringPool.BLANK;

		InfoItemFormVariation infoItemFormVariation =
			TemplateTestUtil.getFirstInfoItemFormVariation(
				setup.infoItemClassDetails, _infoItemServiceRegistry,
				_group.getGroupId());

		if (infoItemFormVariation != null) {
			setup.infoItemFormVariationKey = infoItemFormVariation.getKey();
		}

		setup.name = RandomTestUtil.randomString();

		setup.mockLiferayPortletActionRequest.addParameter(
			"infoItemClassName", setup.infoItemClassDetails.getClassName());
		setup.mockLiferayPortletActionRequest.addParameter(
			"infoItemFormVariationKey", setup.infoItemFormVariationKey);
		setup.mockLiferayPortletActionRequest.addParameter("name", setup.name);

		setup.initialTemplateEntries =
			_templateEntryLocalService.getTemplateEntries(
				_group.getGroupId(), setup.infoItemClassDetails.getClassName(),
				setup.infoItemFormVariationKey, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

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

	@Inject(filter = "mvc.command.name=/template/add_template_entry")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private TemplateEntryLocalService _templateEntryLocalService;

	@DeleteAfterTestRun
	private User _user;

	private static class ActionRequestSetupTest {

		public InfoItemClassDetails infoItemClassDetails;
		public String infoItemFormVariationKey;
		public List<TemplateEntry> initialTemplateEntries;
		public MockLiferayPortletActionRequest mockLiferayPortletActionRequest;
		public String name;

	}

}