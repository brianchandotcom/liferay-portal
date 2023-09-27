/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.constants;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 */
public class AssetLinkConstants {

	public static final int TYPE_CHILD = 1;

	public static final int TYPE_RELATED = 0;

	public static boolean isTypeBi(int type) {
		return !isTypeUni(type);
	}

	public static boolean isTypeUni(int type) {
		if (type == TYPE_CHILD) {
			return true;
		}

		return false;
	}

}