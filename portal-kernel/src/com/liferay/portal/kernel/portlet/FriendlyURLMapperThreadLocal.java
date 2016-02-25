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

package com.liferay.portal.kernel.portlet;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class FriendlyURLMapperThreadLocal {

	public static Map<String, String> getPRPIdentifiers() {
		return _prpIdentifiers.get();
	}

	public static void setPRPIdentifiers(Map<String, String> prpIdentifiers) {
		_prpIdentifiers.set(prpIdentifiers);
	}

	private static final ThreadLocal<Map<String, String>> _prpIdentifiers =
		new ThreadLocal<>();

}