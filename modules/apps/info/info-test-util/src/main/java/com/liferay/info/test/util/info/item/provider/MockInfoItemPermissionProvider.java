/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.test.util.info.item.provider;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Lourdes Fernández Besada
 */
public class MockInfoItemPermissionProvider
	implements InfoItemPermissionProvider<MockObject> {

	public MockInfoItemPermissionProvider(MockObject mockObject) {
		_mockObject = mockObject;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		return _mockObject.hasPermission(actionId);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, MockObject mockObject,
			String actionId)
		throws InfoItemPermissionException {

		return _mockObject.hasPermission(actionId);
	}

	private final MockObject _mockObject;

}