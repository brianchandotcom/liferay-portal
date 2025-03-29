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

	public static String getFDSCreationActionExternalReferenceCode(
		String fdsName, String id) {

		return StringBundler.concat(fdsName, _SEPARATOR_CREATION_ACTION, id);
	}

	public static String getFDSCreationActionId(String externalReferenceCode) {
		return StringUtil.extractLast(
			externalReferenceCode, _SEPARATOR_CREATION_ACTION);
	}

	public static String getFDSItemActionExternalReferenceCode(
		String fdsName, String id) {

		return StringBundler.concat(fdsName, _SEPARATOR_ITEM_ACTION, id);
	}

	public static String getFDSItemActionId(String externalReferenceCode) {
		return StringUtil.extractLast(
			externalReferenceCode, _SEPARATOR_ITEM_ACTION);
	}

	private static final String _SEPARATOR_CREATION_ACTION =
		"_CREATION_ACTION_";

	private static final String _SEPARATOR_ITEM_ACTION = "_ITEM_ACTION_";

}