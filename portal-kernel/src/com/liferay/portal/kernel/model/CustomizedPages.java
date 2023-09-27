/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-15626.
 * </p>
 *
 * @author Raymond Augé
 */
public class CustomizedPages {

	public static String namespaceColumnId(String columnId) {
		return columnId.concat(_CUSTOMIZABLE_SUFFIX);
	}

	public static String namespacePlid(long plid) {
		String className = CustomizedPages.class.getName();

		return className.concat(String.valueOf(plid));
	}

	private static final String _CUSTOMIZABLE_SUFFIX = "-customizable";

}