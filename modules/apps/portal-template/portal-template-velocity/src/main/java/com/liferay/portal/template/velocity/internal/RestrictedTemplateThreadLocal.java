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

package com.liferay.portal.template.velocity.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Tina Tian
 */
public class RestrictedTemplateThreadLocal {

	public static boolean isRestricted() {
		return _restricted.get();
	}

	public static void setRestricted(boolean restricted) {
		_restricted.set(restricted);
	}

	private static final ThreadLocal<Boolean> _restricted =
		new CentralizedThreadLocal<>(
			RestrictedTemplateThreadLocal.class + "._restricted",
			() -> Boolean.FALSE);

}