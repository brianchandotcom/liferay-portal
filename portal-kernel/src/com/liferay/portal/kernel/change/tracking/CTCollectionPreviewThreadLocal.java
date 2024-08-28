/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.change.tracking;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author David Truong
 */
public class CTCollectionPreviewThreadLocal {

	public static long getCTCollectionId() {
		return _ctCollectionId.get();
	}

	public static boolean isIndicatorEnabled() {
		return _indicatorEnabled.get();
	}

	public static void setCTCollectionId(long collectionId) {
		_ctCollectionId.set(collectionId);

		CTCollectionThreadLocal.removeCTCollectionId();
	}

	public static void setIndicatorEnabled(boolean indicatorEnabled) {
		_indicatorEnabled.set(indicatorEnabled);
	}

	private CTCollectionPreviewThreadLocal() {
	}

	private static final CentralizedThreadLocal<Long> _ctCollectionId =
		new CentralizedThreadLocal<>(
			CTCollectionPreviewThreadLocal.class + "._ctCollectionId",
			() -> -1L);
	private static final CentralizedThreadLocal<Boolean> _indicatorEnabled =
		new CentralizedThreadLocal<>(
			CTCollectionPreviewThreadLocal.class + "._indicatorEnabled",
			() -> false);

}