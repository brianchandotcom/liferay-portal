/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Roberto Díaz
 */
public class FileShortcutDisplayContextHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsCopyActionAvailableWhenGuestUser()
		throws PortalException {

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.isSignedIn()
		).thenReturn(
			false
		);

		FileShortcut fileShortcut = Mockito.mock(FileShortcut.class);

		FileShortcutDisplayContextHelper fileShortcutDisplayContextHelper =
			new FileShortcutDisplayContextHelper(
				permissionChecker, fileShortcut);

		Assert.assertFalse(
			fileShortcutDisplayContextHelper.isCopyActionAvailable());
	}

}