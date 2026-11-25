/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.product.navigation.control.menu.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.test.util.LayoutPageTemplateTestUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
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
public class DraftLayoutSelectorProductNavigationControlMenuEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);
	}

	@Test
	@TestInfo("LPD-116938")
	public void testGetDraftLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout mainDraftLayout = _layoutLocalService.fetchDraftLayout(
			layout.getPlid());

		String launchSetName = RandomTestUtil.randomString();

		ObjectEntry launchSetObjectEntry = _addLaunchSetObjectEntry(
			launchSetName);

		Layout launchDraftLayout = LayoutTestUtil.addLaunchDraftLayout(
			launchSetObjectEntry.getObjectEntryId(), layout);

		String mainDraftLayoutEditURL = _getEditURL(mainDraftLayout);

		String mainDraftLayoutLabel = LanguageUtil.get(
			LocaleUtil.getDefault(), "main");

		_testGetDraftLayouts(
			Arrays.asList(
				_getExpectedDraftLayoutMap(
					false, false, mainDraftLayoutEditURL, mainDraftLayoutLabel,
					0),
				_getExpectedDraftLayoutMap(
					true, false, null, launchSetName,
					launchDraftLayout.getLaunchSetId())),
			_getMockHttpServletRequest(
				launchDraftLayout, Constants.EDIT, TestPropsValues.getUser()));

		String launchDraftLayoutEditURL = _getEditURL(launchDraftLayout);

		_testGetDraftLayouts(
			Arrays.asList(
				_getExpectedDraftLayoutMap(
					true, false, null, mainDraftLayoutLabel, 0),
				_getExpectedDraftLayoutMap(
					false, false, launchDraftLayoutEditURL, launchSetName,
					launchDraftLayout.getLaunchSetId())),
			_getMockHttpServletRequest(
				mainDraftLayout, Constants.EDIT, TestPropsValues.getUser()));

		_testGetDraftLayouts(
			Arrays.asList(
				_getExpectedDraftLayoutMap(
					true, false, null, mainDraftLayoutLabel, 0),
				_getExpectedDraftLayoutMap(
					false, true, null,
					LanguageUtil.get(
						LocaleUtil.getDefault(), "restricted-launch"),
					launchDraftLayout.getLaunchSetId())),
			_getMockHttpServletRequest(
				mainDraftLayout, Constants.EDIT, UserTestUtil.addUser()));
	}

	@Test
	@TestInfo("LPD-116938")
	public void testIsRelevant() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(layout, TestPropsValues.getUser());

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isRelevant(
				mockHttpServletRequest));

		_testIsRelevant(false, mockHttpServletRequest, Constants.PREVIEW);
		_testIsRelevant(false, mockHttpServletRequest, Constants.VIEW);
		_testIsRelevant(true, mockHttpServletRequest, Constants.EDIT);
	}

	@Test
	@TestInfo("LPD-116938")
	public void testIsShow() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_testIsShow(false, layout, Constants.EDIT);

		Layout draftLayout = _layoutLocalService.fetchDraftLayout(
			layout.getPlid());

		_testIsShow(false, draftLayout, Constants.EDIT);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_group.getGroupId(), LayoutPageTemplateEntryTypeConstants.BASIC,
				WorkflowConstants.STATUS_DRAFT);

		_testIsShow(
			false,
			_layoutLocalService.fetchDraftLayout(
				layoutPageTemplateEntry.getPlid()),
			Constants.EDIT);

		layoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_group.getGroupId(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
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

		Layout launchDraftLayout = LayoutTestUtil.addLaunchDraftLayout(
			RandomTestUtil.randomLong(), layout);

		_testIsShow(false, draftLayout, Constants.PREVIEW);
		_testIsShow(false, launchDraftLayout, Constants.PREVIEW);

		try (SafeCloseable safeCloseable =
				FeatureFlagTestUtil.setFeatureFlagsWithSafeCloseable(
					false, "LPD-72278")) {

			_testIsShow(false, draftLayout, Constants.EDIT);
			_testIsShow(false, launchDraftLayout, Constants.EDIT);
		}

		_testIsShow(true, draftLayout, Constants.EDIT);
		_testIsShow(true, launchDraftLayout, Constants.EDIT);
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

	private String _getEditURL(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			_company, _group, layout);

		if (layout.getLaunchSetId() > 0) {
			return HttpComponentsUtil.addParameters(
				_portal.getLayoutFullURL(layout, themeDisplay),
				"editLaunchSetId", layout.getLaunchSetId(), "p_l_mode",
				Constants.EDIT);
		}

		return HttpComponentsUtil.addParameters(
			_portal.getLayoutFullURL(layout, themeDisplay), "p_l_mode",
			Constants.EDIT);
	}

	private Map<String, Object> _getExpectedDraftLayoutMap(
		boolean active, boolean disabled, String href, String label,
		long launchSetId) {

		return HashMapBuilder.<String, Object>put(
			"active", active
		).put(
			"disabled", disabled
		).put(
			"href", () -> href
		).put(
			"label", label
		).put(
			"launchSetId", launchSetId
		).build();
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			Layout layout, String mode, User user)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(layout, user);

		mockHttpServletRequest.setParameter("p_l_mode", mode);

		return mockHttpServletRequest;
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			Layout layout, User user)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_company, _group, layout);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setUser(user);

		return mockHttpServletRequest;
	}

	private void _testGetDraftLayouts(
		List<Map<String, Object>> expectedDraftLayouts,
		HttpServletRequest httpServletRequest) {

		List<Map<String, Object>> draftLayouts = ReflectionTestUtil.invoke(
			_productNavigationControlMenuEntry, "_getDraftLayouts",
			new Class<?>[] {HttpServletRequest.class}, httpServletRequest);

		Assert.assertEquals(expectedDraftLayouts, draftLayouts);
	}

	private void _testIsRelevant(
		boolean expected, MockHttpServletRequest mockHttpServletRequest,
		String mode) {

		mockHttpServletRequest.setParameter("p_l_mode", mode);

		Assert.assertEquals(
			expected,
			_productNavigationControlMenuEntry.isRelevant(
				mockHttpServletRequest));
	}

	private void _testIsShow(boolean expected, Layout layout, String mode)
		throws Exception {

		Assert.assertEquals(
			expected,
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(
					layout, mode, TestPropsValues.getUser())));
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

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
		filter = "component.name=com.liferay.layout.content.page.editor.web.internal.product.navigation.control.menu.DraftLayoutSelectorProductNavigationControlMenuEntry"
	)
	private ProductNavigationControlMenuEntry
		_productNavigationControlMenuEntry;

}