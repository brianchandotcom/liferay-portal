/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.security.permission.resource;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jhosseph Gonzalez
 */
public class KaleoInstanceModelResourcePermissionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContains() throws Exception {

		// Kaleo instance from a different company

		KaleoInstance kaleoInstance = Mockito.mock(KaleoInstance.class);

		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			kaleoInstance.getCompanyId()
		).thenReturn(
			companyId
		);

		Assert.assertFalse(
			_kaleoInstanceModelResourcePermission.contains(
				_mockPermissionChecker(companyId + 1), kaleoInstance,
				RandomTestUtil.randomString()));

		// Kaleo instance from the same company

		Assert.assertTrue(
			_kaleoInstanceModelResourcePermission.contains(
				_mockPermissionChecker(companyId), kaleoInstance,
				RandomTestUtil.randomString()));
	}

	private PermissionChecker _mockPermissionChecker(long companyId) {
		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			permissionChecker.isContentReviewer(
				Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			true
		);

		return permissionChecker;
	}

	private final KaleoInstanceModelResourcePermission
		_kaleoInstanceModelResourcePermission =
			new KaleoInstanceModelResourcePermission();

}