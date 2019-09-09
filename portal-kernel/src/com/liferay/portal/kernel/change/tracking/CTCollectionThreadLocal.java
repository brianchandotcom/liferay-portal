/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.change.tracking;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Preston Crary
 */
public class CTCollectionThreadLocal {

	public static long getCTCollectionId() {
		return _ctCollectionId.get();
	}

	public static void removeCTCollectionId() {
		_ctCollectionId.remove();
	}

	public static SafeClosable setCTCollectionId(long ctCollectionId) {
		return _ctCollectionId.setWithSafeClosable(ctCollectionId);
	}

	private static long _getCTCollectionId() {
		CTCollectionIdSupplier ctCollectionIdSupplier = _ctCollectionIdSupplier;

		if (ctCollectionIdSupplier == null) {
			return 0;
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