/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;

/**
 * @author David Truong
 */
public class CTCollectionPreviewThreadLocal {

	public static long getCTCollectionId() {
		return _ctCollectionId.get();
	}

	public static void setCTCollectionId(long collectionId) {
		_ctCollectionId.set(collectionId);

		CTCollectionThreadLocal.removeCTCollectionId();
	}

	private CTCollectionPreviewThreadLocal() {
	}

	private static final CentralizedThreadLocal<Long> _ctCollectionId =
		new CentralizedThreadLocal<>(
			CTCollectionPreviewThreadLocal.class + "._ctCollectionId",
			() -> -1L);

}