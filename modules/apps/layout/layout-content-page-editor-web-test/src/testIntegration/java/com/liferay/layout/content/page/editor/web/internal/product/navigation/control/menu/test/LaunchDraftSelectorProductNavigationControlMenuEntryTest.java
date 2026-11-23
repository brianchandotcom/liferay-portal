/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.product.navigation.control.menu.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.test.util.LayoutPageTemplateTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
@FeatureFlag("LPD-72278")
@RunWith(Arquillian.class)
public class LaunchDraftSelectorProductNavigationControlMenuEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);
	}

	@Test
	@TestInfo("LPD-116938")
	public void testGetDraftLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout mainDraftLayout = _layoutLocalService.fetchDraftLayout(
			layout.getPlid());

		String launchSetName = "A-" + RandomTestUtil.randomString();

		ObjectEntry launchSetObjectEntry = _addLaunchSetObjectEntry(
			launchSetName);

		Layout launchDraftLayout = LayoutTestUtil.addLaunchDraftLayout(
			launchSetObjectEntry.getObjectEntryId(), layout);

		_testGetDraftLayouts(
			Arrays.asList(
				_buildExpectedDraftLayoutMap(
					mainDraftLayout, null, mainDraftLayout, false),
				_buildExpectedDraftLayoutMap(
					launchDraftLayout, null, mainDraftLayout, true)),
			mainDraftLayout, UserTestUtil.addUser());

		_testGetDraftLayouts(
			Arrays.asList(
				_buildExpectedDraftLayoutMap(
					mainDraftLayout, null, launchDraftLayout, false),
				_buildExpectedDraftLayoutMap(
					launchDraftLayout, launchSetName, launchDraftLayout,
					false)),
			launchDraftLayout, TestPropsValues.getUser());
	}

	@Test
	@TestInfo("LPD-116938")
	public void testIsShow() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_testIsShow(false, layout, Constants.EDIT);

		Layout draftLayout = _layoutLocalService.fetchDraftLayout(
			layout.getPlid());

		_testIsShow(false, draftLayout, Constants.PREVIEW);
		_testIsShow(true, draftLayout, Constants.EDIT);

		try (SafeCloseable safeCloseable =
				FeatureFlagTestUtil.setFeatureFlagsWithSafeCloseable(
					false, "LPD-72278")) {

			_testIsShow(false, draftLayout, Constants.EDIT);
		}

		Layout launchDraftLayout = LayoutTestUtil.addLaunchDraftLayout(
			RandomTestUtil.randomLong(), layout);

		_testIsShow(false, launchDraftLayout, Constants.PREVIEW);
		_testIsShow(true, launchDraftLayout, Constants.EDIT);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_group.getGroupId(), LayoutPageTemplateEntryTypeConstants.BASIC,
				WorkflowConstants.STATUS_DRAFT);

		_testIsShow(
			false,
			_layoutLocalService.fetchDraftLayout(
				layoutPageTemplateEntry.getPlid()),
			Constants.EDIT);

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				true, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		_testIsShow(
			false,
			_layoutLocalService.fetchDraftLayout(
				layoutUtilityPageEntry.getPlid()),
			Constants.EDIT);
	}

	private ObjectEntry _addLaunchSetObjectEntry(String name) throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				TestPropsValues.getCompanyId(), "LaunchSet");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
			HashMapBuilder.<String, Serializable>put(
				"description", RandomTestUtil.randomString()
			).put(
				"name", name
			).build(),
			serviceContext);
	}

	private Map<String, Object> _buildExpectedDraftLayoutMap(
			Layout draftLayout, String launchSetName, Layout layout,
			boolean restricted)
		throws Exception {

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			layout, Constants.EDIT, TestPropsValues.getUser());

		long launchSetId = draftLayout.getLaunchSetId();

		return HashMapBuilder.<String, Object>put(
			"active", draftLayout.getPlid() == layout.getPlid()
		).put(
			"disabled", restricted
		).put(
			"href",
			() -> {
				if (restricted || (draftLayout.getPlid() == layout.getPlid())) {
					return null;
				}

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				if (launchSetId > 0) {
					return HttpComponentsUtil.addParameters(
						_portal.getLayoutFullURL(draftLayout, themeDisplay),
						"editLaunchSetId", launchSetId, "p_l_mode",
						Constants.EDIT);
				}

				return HttpComponentsUtil.addParameters(
					_portal.getLayoutFullURL(draftLayout, themeDisplay),
					"p_l_mode", Constants.EDIT);
			}
		).put(
			"label",
			() -> {
				if (restricted) {
					return LanguageUtil.get(
						httpServletRequest, "restricted-launch");
				}

				if (launchSetId > 0) {
					return launchSetName;
				}

				return LanguageUtil.get(httpServletRequest, "main");
			}
		).put(
			"launchSetId", launchSetId
		).build();
	}

	private HttpServletRequest _getHttpServletRequest(
			Layout layout, String mode, User user)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(layout);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setParameter("p_l_mode", mode);

		return mockHttpServletRequest;
	}

	private void _testGetDraftLayouts(
			List<Map<String, Object>> expectedDraftLayouts, Layout layout,
			User user)
		throws Exception {

		List<Map<String, Object>> draftLayouts = ReflectionTestUtil.invoke(
			_productNavigationControlMenuEntry, "_getDraftLayouts",
			new Class<?>[] {HttpServletRequest.class},
			_getHttpServletRequest(layout, Constants.EDIT, user));

		Assert.assertEquals(expectedDraftLayouts, draftLayouts);
	}

	private void _testIsShow(boolean expected, Layout layout, String mode)
		throws Exception {

		Assert.assertEquals(
			expected,
			_productNavigationControlMenuEntry.isShow(
				_getHttpServletRequest(
					layout, mode, TestPropsValues.getUser())));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.layout.content.page.editor.web.internal.product.navigation.control.menu.LaunchDraftSelectorProductNavigationControlMenuEntry"
	)
	private ProductNavigationControlMenuEntry
		_productNavigationControlMenuEntry;

}