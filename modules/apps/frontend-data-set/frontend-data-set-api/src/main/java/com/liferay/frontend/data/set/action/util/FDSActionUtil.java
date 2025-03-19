/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.action.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Daniel Sanz
 */
public class FDSActionUtil {

	public static String createFDSCreationActionERC(String fdsName, String id) {
		return StringBundler.concat(fdsName, _CREATION_ACTION_SEPARATOR, id);
	}

	public static String createFDSItemActionERC(String fdsName, String id) {
		return StringBundler.concat(fdsName, _ITEM_ACTION_SEPARATOR, id);
	}

	public static String getFDSCreationActionId(String erc) {
		return StringUtil.extractLast(erc, _CREATION_ACTION_SEPARATOR);
	}

	public static String getFDSItemActionId(String erc) {
		return StringUtil.extractLast(erc, _ITEM_ACTION_SEPARATOR);
	}

	private static final String _CREATION_ACTION_SEPARATOR =
		"_CREATION_ACTION_";

	private static final String _ITEM_ACTION_SEPARATOR = "_ITEM_ACTION_";

}