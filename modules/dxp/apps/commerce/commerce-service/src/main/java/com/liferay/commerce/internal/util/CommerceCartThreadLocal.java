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

package com.liferay.commerce.internal.util;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CommerceCartThreadLocal {

	public static String getCommerceCartUUID(int type) {
		Map<Integer, String> commerceCartUUIDMap = _commerceCartUUIDMap.get();

		if ((commerceCartUUIDMap != null) &&
			commerceCartUUIDMap.containsKey(type)) {

			return commerceCartUUIDMap.get(type);
		}

		return null;
	}

	public static void setCommerceCartUUID(int type, String commerceCartUUID) {
		Map<Integer, String> commerceCartUUIDMap = _commerceCartUUIDMap.get();

		if (commerceCartUUIDMap == null) {
			commerceCartUUIDMap = new HashMap<>();
		}

		commerceCartUUIDMap.put(type, commerceCartUUID);

		_commerceCartUUIDMap.set(commerceCartUUIDMap);
	}

	private static final ThreadLocal<Map<Integer, String>>
		_commerceCartUUIDMap = new AutoResetThreadLocal<>(
			CommerceCartThreadLocal.class + "._commerce_cart_uuid_map");

}