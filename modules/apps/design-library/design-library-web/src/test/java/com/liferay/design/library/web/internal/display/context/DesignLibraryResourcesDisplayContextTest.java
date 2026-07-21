/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Gabriel Prates
 */
public class DesignLibraryResourcesDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		_group = Mockito.mock(Group.class);

		Mockito.when(
			_group.getClassPK()
		).thenReturn(
			_DEPOT_ENTRY_ID
		);

		Mockito.when(
			_group.getName(Mockito.any(Locale.class))
		).thenReturn(
			"design-library-name"
		);

		DepotEntry depotEntry = Mockito.mock(DepotEntry.class);

		Mockito.when(
			depotEntry.getGroup()
		).thenReturn(
			_group
		);

		_depotEntryLocalServiceUtilMockedStatic.when(
			() -> DepotEntryLocalServiceUtil.getDepotEntry(Mockito.anyLong())
		).thenReturn(
			depotEntry
		);

		_languageUtilMockedStatic.when(
			() -> LanguageUtil.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(1)
		);

		PortletURLBuilder.PortletURLStep portletURLStep = Mockito.mock(
			PortletURLBuilder.PortletURLStep.class, Mockito.RETURNS_SELF);

		_portletURLBuilderMockedStatic.when(
			() -> PortletURLBuilder.createActionURL(
				Mockito.any(LiferayPortletResponse.class))
		).thenReturn(
			portletURLStep
		);

		_portletURLBuilderMockedStatic.when(
			() -> PortletURLBuilder.create(Mockito.any())
		).thenReturn(
			portletURLStep
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_depotEntryLocalServiceUtilMockedStatic.close();
		_groupPermissionUtilMockedStatic.close();
		_languageUtilMockedStatic.close();
		_portalUtilMockedStatic.close();
		_portletURLBuilderMockedStatic.close();
	}

	@Test
	public void testGetBreadcrumbPropsActionItemsWithAssignMembersPermission()
		throws Exception {

		List<String> labels = _getBreadcrumbPropsActionItemsLabels(
			false, false, true);

		Assert.assertFalse(labels.toString(), labels.contains("view-members"));
		Assert.assertTrue(labels.toString(), labels.contains("manage-members"));
	}

	@Test
	public void testGetBreadcrumbPropsActionItemsWithDeletePermission()
		throws Exception {

		List<String> labels = _getBreadcrumbPropsActionItemsLabels(
			false, true, false);

		Assert.assertFalse(labels.toString(), labels.contains("export"));
		Assert.assertFalse(labels.toString(), labels.contains("import"));
		Assert.assertFalse(labels.toString(), labels.contains("settings"));
		Assert.assertTrue(labels.toString(), labels.contains("delete"));
	}

	@Test
	public void testGetBreadcrumbPropsActionItemsWithNoPermissions()
		throws Exception {

		List<String> labels = _getBreadcrumbPropsActionItemsLabels(
			false, false, false);

		Assert.assertFalse(labels.toString(), labels.contains("delete"));
		Assert.assertFalse(labels.toString(), labels.contains("export"));
		Assert.assertFalse(labels.toString(), labels.contains("import"));
		Assert.assertFalse(
			labels.toString(), labels.contains("manage-members"));
		Assert.assertFalse(labels.toString(), labels.contains("settings"));
		Assert.assertTrue(
			labels.toString(), labels.contains("connected-sites"));
		Assert.assertTrue(labels.toString(), labels.contains("view-members"));
	}

	@Test
	public void testGetBreadcrumbPropsActionItemsWithUpdateAndDeletePermission()
		throws Exception {

		List<String> labels = _getBreadcrumbPropsActionItemsLabels(
			true, true, true);

		Assert.assertTrue(
			labels.toString(), labels.contains("connected-sites"));
		Assert.assertTrue(labels.toString(), labels.contains("delete"));
		Assert.assertTrue(labels.toString(), labels.contains("export"));
		Assert.assertTrue(labels.toString(), labels.contains("import"));
		Assert.assertTrue(labels.toString(), labels.contains("manage-members"));
		Assert.assertTrue(labels.toString(), labels.contains("settings"));
	}

	@Test
	public void testGetBreadcrumbPropsActionItemsWithUpdatePermission()
		throws Exception {

		List<String> labels = _getBreadcrumbPropsActionItemsLabels(
			true, false, false);

		Assert.assertFalse(labels.toString(), labels.contains("delete"));
		Assert.assertTrue(labels.toString(), labels.contains("export"));
		Assert.assertTrue(labels.toString(), labels.contains("import"));
		Assert.assertTrue(labels.toString(), labels.contains("settings"));
	}

	private List<String> _getBreadcrumbPropsActionItemsLabels(
			boolean hasUpdatePermission, boolean hasDeletePermission,
			boolean hasAssignMembersPermission)
		throws Exception {

		_groupPermissionUtilMockedStatic.when(
			() -> GroupPermissionUtil.contains(
				Mockito.any(PermissionChecker.class), Mockito.anyLong(),
				Mockito.eq(ActionKeys.ASSIGN_MEMBERS))
		).thenReturn(
			hasAssignMembersPermission
		);

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.hasPermission(
				Mockito.any(Group.class),
				Mockito.eq(DepotEntry.class.getName()), Mockito.anyLong(),
				Mockito.eq(ActionKeys.UPDATE))
		).thenReturn(
			hasUpdatePermission
		);

		Mockito.when(
			permissionChecker.hasPermission(
				Mockito.any(Group.class),
				Mockito.eq(DepotEntry.class.getName()), Mockito.anyLong(),
				Mockito.eq(ActionKeys.DELETE))
		).thenReturn(
			hasDeletePermission
		);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPermissionChecker(permissionChecker);

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			httpServletRequest.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		DesignLibraryResourcesDisplayContext
			designLibraryResourcesDisplayContext =
				new DesignLibraryResourcesDisplayContext(
					httpServletRequest,
					Mockito.mock(LiferayPortletResponse.class));

		List<String> labels = new ArrayList<>();

		Map<String, Object> breadcrumbProps =
			designLibraryResourcesDisplayContext.getBreadcrumbProps(
				_group.getClassPK());

		JSONArray jsonArray = (JSONArray)breadcrumbProps.get("actionItems");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			labels.add(jsonObject.getString("label"));
		}

		return labels;
	}

	private static final long _DEPOT_ENTRY_ID = 12345;

	private static final MockedStatic<DepotEntryLocalServiceUtil>
		_depotEntryLocalServiceUtilMockedStatic = Mockito.mockStatic(
			DepotEntryLocalServiceUtil.class);
	private static Group _group;
	private static final MockedStatic<GroupPermissionUtil>
		_groupPermissionUtilMockedStatic = Mockito.mockStatic(
			GroupPermissionUtil.class);
	private static final MockedStatic<LanguageUtil> _languageUtilMockedStatic =
		Mockito.mockStatic(LanguageUtil.class);
	private static final MockedStatic<PortalUtil> _portalUtilMockedStatic =
		Mockito.mockStatic(PortalUtil.class);
	private static final MockedStatic<PortletURLBuilder>
		_portletURLBuilderMockedStatic = Mockito.mockStatic(
			PortletURLBuilder.class);

}