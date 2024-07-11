/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Eudaldo Alonso
 */
public class LayoutActionsHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setGroup();
		_setUpLayout();
		_setUpLayoutPermissionUtil();
		_setUpLayoutLocalServiceUtil();
	}

	@After
	public void tearDown() {
		_layoutLocalServiceUtilMockedStatic.close();
		_layoutPermissionUtilMockedStatic.close();
	}

	@Test
	public void testIsShowDeleteActionForLastPublicPageOnDefaultSite()
		throws PortalException {

		LayoutActionsHelper layoutActionsHelper = new LayoutActionsHelper(
			null, _themeDisplay, null);

		Assert.assertFalse(layoutActionsHelper.isShowDeleteAction(_layout));
	}

	private void _setGroup() {
		Mockito.when(
			_group.isGuest()
		).thenReturn(
			true
		);
	}

	private void _setUpLayout() {
		Mockito.when(
			_layout.getGroup()
		).thenReturn(
			_group
		);

		Mockito.when(
			_layout.isPrivateLayout()
		).thenReturn(
			false
		);

		Mockito.when(
			_layout.isRootLayout()
		).thenReturn(
			true
		);
	}

	private void _setUpLayoutLocalServiceUtil() {
		_layoutLocalServiceUtilMockedStatic.when(
			() -> LayoutLocalServiceUtil.getLayoutsCount(
				Mockito.any(), Mockito.anyBoolean(), Mockito.anyLong())
		).thenReturn(
			1
		);
	}

	private void _setUpLayoutPermissionUtil() {
		_layoutPermissionUtilMockedStatic.when(
			() -> LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), _layout,
				ActionKeys.DELETE)
		).thenReturn(
			true
		);
	}

	private final Group _group = Mockito.mock(Group.class);
	private final Layout _layout = Mockito.mock(Layout.class);
	private final MockedStatic<LayoutLocalServiceUtil>
		_layoutLocalServiceUtilMockedStatic = Mockito.mockStatic(
			LayoutLocalServiceUtil.class);
	private final MockedStatic<LayoutPermissionUtil>
		_layoutPermissionUtilMockedStatic = Mockito.mockStatic(
			LayoutPermissionUtil.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}