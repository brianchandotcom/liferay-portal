/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dante Wang
 */
public class RankingUtil {

	public static Collection<String> getQueryStrings(
		String queryString, List<String> aliases) {

		Set<String> queryStrings = new LinkedHashSet<>();

		if (!Validator.isBlank(queryString)) {
			queryStrings.add(queryString);
		}

		for (String alias : aliases) {
			if (!Validator.isBlank(alias)) {
				queryStrings.add(alias);
			}
		}

		return ListUtil.sort(new ArrayList<>(queryStrings));
	}

}