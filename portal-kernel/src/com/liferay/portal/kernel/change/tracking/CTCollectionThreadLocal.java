/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.change.tracking;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Preston Crary
 */
public class CTCollectionThreadLocal {

	public static final long CT_COLLECTION_ID_PRODUCTION = 0;

	public static long getCTCollectionId() {
		return _ctCollectionId.get();
	}

	public static boolean isProductionMode() {
		if (_ctCollectionId.get() == CT_COLLECTION_ID_PRODUCTION) {
			return true;
		}

		return false;
	}

	public static void removeCTCollectionId() {
		_ctCollectionId.remove();
	}

	public static SafeCloseable setCTCollectionIdWithSafeCloseable(
		long ctCollectionId) {

		return _ctCollectionId.setWithSafeCloseable(ctCollectionId);
	}

	public static SafeCloseable setProductionModeWithSafeCloseable() {
		return setCTCollectionIdWithSafeCloseable(CT_COLLECTION_ID_PRODUCTION);
	}

	private static long _getCTCollectionId() {
		CTCollectionIdSupplier ctCollectionIdSupplier = _ctCollectionIdSupplier;

		if (ctCollectionIdSupplier == null) {
			return CT_COLLECTION_ID_PRODUCTION;
		}

		return ctCollectionIdSupplier.getCTCollectionId();
	}

	private CTCollectionThreadLocal() {
	}

	private static final CentralizedThreadLocal<Long> _ctCollectionId =
		new CentralizedThreadLocal<>(
			CTCollectionThreadLocal.class + "._ctCollectionId",
			CTCollectionThreadLocal::_getCTCollectionId);
	private static volatile CTCollectionIdSupplier _ctCollectionIdSupplier =
		ServiceProxyFactory.newServiceTrackedInstance(
			CTCollectionIdSupplier.class, CTCollectionThreadLocal.class,
			"_ctCollectionIdSupplier", false, true);

}