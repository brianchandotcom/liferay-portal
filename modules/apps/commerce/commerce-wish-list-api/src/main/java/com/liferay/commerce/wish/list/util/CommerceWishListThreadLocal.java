/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Crescenzo Rega
 */
public class CommerceWishListThreadLocal {

	public static boolean isDefaultWishListDeletable() {
		return _defaultWishListDeletable.get();
	}

	public static void setDefaultWishListDeletable(
		boolean defaultWishListDeletable) {

		_defaultWishListDeletable.set(defaultWishListDeletable);
	}

	private static final ThreadLocal<Boolean> _defaultWishListDeletable =
		new CentralizedThreadLocal<>(
			CommerceWishListThreadLocal.class + "._defaultWishListDeletable",
			() -> Boolean.FALSE);

}