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

package com.liferay.change.tracking.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author David Truong
 */
public class CTCollectionPreviewThreadLocal {

	public static long getCTCollectionId() {
		return _ctCollectionId.get();
	}

	public static void setCTCollectionId(long collectionId) {
		_ctCollectionId.set(collectionId);
	}

	private CTCollectionPreviewThreadLocal() {
	}

	private static final CentralizedThreadLocal<Long> _ctCollectionId =
		new CentralizedThreadLocal<>(
			CTCollectionPreviewThreadLocal.class + "._ctCollectionId",
			() -> -1L);

}