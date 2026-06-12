/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Georgel Pop
 */
public class FragmentEntryVersionCleanUpUtil {

	public static String getCleanUpSQL() {
		return _getCleanUpSQL(StringPool.BLANK);
	}

	public static String getCleanUpSQL(long companyId) {
		return _getCleanUpSQL(" where companyId = " + companyId);
	}

	private static String _getCleanUpSQL(String whereClause) {
		return StringBundler.concat(
			"delete from FragmentEntryVersion where fragmentEntryVersionId in ",
			"(select fragmentEntryVersionId from (select ",
			"fragmentEntryVersionId, row_number() over (partition by ",
			"ctCollectionId, fragmentEntryId order by version desc) ",
			"versionRowNumber from FragmentEntryVersion", whereClause,
			") tempFragmentEntryVersion where ",
			"tempFragmentEntryVersion.versionRowNumber > ",
			_FRAGMENT_ENTRY_VERSIONS_COUNT_MAX, ")");
	}

	private static final int _FRAGMENT_ENTRY_VERSIONS_COUNT_MAX = 10;

}