/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoBridgeFactoryUtil {

	public static ExpandoBridge getExpandoBridge(
		long companyId, String className) {

		ExpandoBridgeFactory expandoBridgeFactory =
			_expandoBridgeFactorySnapshot.get();

		return expandoBridgeFactory.getExpandoBridge(companyId, className);
	}

	public static ExpandoBridge getExpandoBridge(
		long companyId, String className, long classPK) {

		ExpandoBridgeFactory expandoBridgeFactory =
			_expandoBridgeFactorySnapshot.get();

		return expandoBridgeFactory.getExpandoBridge(
			companyId, className, classPK);
	}

	private static final Snapshot<ExpandoBridgeFactory>
		_expandoBridgeFactorySnapshot = new Snapshot<>(
			ExpandoBridgeFactoryUtil.class, ExpandoBridgeFactory.class);

}