/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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