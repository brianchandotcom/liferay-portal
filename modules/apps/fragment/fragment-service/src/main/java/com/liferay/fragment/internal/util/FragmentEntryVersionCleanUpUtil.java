/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.util;

import com.liferay.fragment.constants.FragmentEntryVersionConstants;
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
		return _getCleanUpSQL(
			"FragmentEntryVersion1.companyId = " + companyId + " and ");
	}

	private static String _getCleanUpSQL(String whereClause) {
		return StringBundler.concat(
			"delete from FragmentEntryVersion where fragmentEntryVersionId in ",
			"(select fragmentEntryVersionId from (select ",
			"fragmentEntryVersionId from FragmentEntryVersion ",
			"FragmentEntryVersion1 where ", whereClause,
			"(select count(*) from FragmentEntryVersion FragmentEntryVersion2 ",
			"where FragmentEntryVersion2.ctCollectionId = ",
			"FragmentEntryVersion1.ctCollectionId and ",
			"FragmentEntryVersion2.fragmentEntryId = ",
			"FragmentEntryVersion1.fragmentEntryId and ",
			"FragmentEntryVersion2.version >= FragmentEntryVersion1.version) ",
			"> ",
			FragmentEntryVersionConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX,
			") tempFragmentEntryVersion)");
	}

}