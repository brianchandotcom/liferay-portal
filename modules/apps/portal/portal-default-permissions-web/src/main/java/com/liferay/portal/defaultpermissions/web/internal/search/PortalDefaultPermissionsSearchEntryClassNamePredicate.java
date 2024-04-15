/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.function.Predicate;

/**
 * @author Stefano Motta
 */
public class PortalDefaultPermissionsSearchEntryClassNamePredicate
	implements Predicate<PortalDefaultPermissionsSearchEntry> {

	public PortalDefaultPermissionsSearchEntryClassNamePredicate(
		String keywords) {

		_keywords = keywords;
	}

	@Override
	public boolean test(
		PortalDefaultPermissionsSearchEntry
			portalDefaultPermissionsSearchEntry) {

		if (Validator.isNull(_keywords)) {
			return true;
		}

		String delimiter = StringPool.SPACE;

		if (!StringUtil.contains(_keywords, StringPool.SPACE)) {
			delimiter = StringPool.BLANK;
		}

		return StringUtil.containsIgnoreCase(
			portalDefaultPermissionsSearchEntry.getClassName(), _keywords,
			delimiter);
	}

	private final String _keywords;

}