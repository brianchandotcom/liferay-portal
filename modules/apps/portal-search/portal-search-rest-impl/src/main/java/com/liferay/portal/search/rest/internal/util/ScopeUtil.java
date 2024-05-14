/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.rest.internal.resource.exception.IllegalScopeParameterException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class ScopeUtil {

	public static long[] toGroupIds(long companyId, String scope) {
		List<Long> groupIds = new ArrayList<>();

		String[] scopes = ValueUtil.toArray(scope);

		for (String s : scopes) {
			Group group =
				GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
					s, companyId);

			if (group != null) {
				groupIds.add(group.getGroupId());

				continue;
			}

			try {
				groupIds.add(Long.parseLong(s));
			}
			catch (NumberFormatException numberFormatException) {
				throw new IllegalScopeParameterException(
					StringBundler.concat(
						"Invalid group external reference code or ID ", s, ". ",
						numberFormatException));
			}
		}

		return ArrayUtil.toLongArray(groupIds);
	}

}