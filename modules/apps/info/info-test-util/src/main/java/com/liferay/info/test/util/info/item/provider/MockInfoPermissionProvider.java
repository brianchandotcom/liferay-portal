/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.test.util.info.item.provider;

import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Eudaldo Alonso
 */
public class MockInfoPermissionProvider
	implements InfoPermissionProvider<MockObject> {

	public MockInfoPermissionProvider(MockObject mockObject) {
		_mockObject = mockObject;
	}

	@Override
	public boolean hasAddPermission(
		long groupId, PermissionChecker permissionChecker) {

		return _mockObject.hasAddPermission();
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return _mockObject.hasViewPermission();
	}

	private final MockObject _mockObject;

}