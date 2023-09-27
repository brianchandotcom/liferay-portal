/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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