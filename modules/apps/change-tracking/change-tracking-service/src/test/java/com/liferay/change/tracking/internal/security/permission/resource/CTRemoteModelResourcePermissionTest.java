/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.security.permission.resource;

import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.model.impl.CTRemoteImpl;
import com.liferay.change.tracking.service.CTRemoteLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author David Truong
 */
public class CTRemoteModelResourcePermissionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_ctRemoteModelResourcePermission =
			new CTRemoteModelResourcePermission();

		CTRemoteLocalService ctRemoteLocalService = Mockito.mock(
			CTRemoteLocalService.class);

		Mockito.when(
			ctRemoteLocalService.getCTRemote(_OTHER_COMPANY_CTREMOTE_ID)
		).thenReturn(
			_createCTRemote(_OTHER_COMPANY_ID, _OTHER_COMPANY_CTREMOTE_ID)
		);

		ReflectionTestUtil.setFieldValue(
			_ctRemoteModelResourcePermission, "_ctRemoteLocalService",
			ctRemoteLocalService);
	}

	@Test
	public void testContainsAllowsCTRemoteFromSameCompany() throws Exception {
		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.getCompanyId()
		).thenReturn(
			_CURRENT_COMPANY_ID
		);

		Mockito.when(
			permissionChecker.hasOwnerPermission(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			true
		);

		CTRemote ctRemote = _createCTRemote(_CURRENT_COMPANY_ID, 200L);

		Assert.assertTrue(
			"contains() must return true for a CTRemote owned by the same " +
				"company when the user has owner permission",
			_ctRemoteModelResourcePermission.contains(
				permissionChecker, ctRemote, ActionKeys.VIEW));
	}

	@Test
	public void testContainsRejectsCTRemoteFromOtherCompany() throws Exception {
		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.getCompanyId()
		).thenReturn(
			_CURRENT_COMPANY_ID
		);

		Mockito.when(
			permissionChecker.hasOwnerPermission(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			true
		);

		Mockito.when(
			permissionChecker.hasPermission(
				Mockito.nullable(Group.class), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			true
		);

		CTRemote ctRemote = _createCTRemote(
			_OTHER_COMPANY_ID, _OTHER_COMPANY_CTREMOTE_ID);

		Assert.assertFalse(
			"contains() must return false for a CTRemote owned by a " +
				"different company",
			_ctRemoteModelResourcePermission.contains(
				permissionChecker, ctRemote, ActionKeys.VIEW));

		Assert.assertFalse(
			"contains(long) must return false for a CTRemote owned by a " +
				"different company",
			_ctRemoteModelResourcePermission.contains(
				permissionChecker, _OTHER_COMPANY_CTREMOTE_ID,
				ActionKeys.VIEW));
	}

	private CTRemote _createCTRemote(long companyId, long ctRemoteId) {
		CTRemote ctRemote = new CTRemoteImpl();

		ctRemote.setCtRemoteId(ctRemoteId);
		ctRemote.setCompanyId(companyId);

		return ctRemote;
	}

	private static final long _CURRENT_COMPANY_ID = 1;

	private static final long _OTHER_COMPANY_CTREMOTE_ID = 100;

	private static final long _OTHER_COMPANY_ID = 2;

	private CTRemoteModelResourcePermission _ctRemoteModelResourcePermission;

}