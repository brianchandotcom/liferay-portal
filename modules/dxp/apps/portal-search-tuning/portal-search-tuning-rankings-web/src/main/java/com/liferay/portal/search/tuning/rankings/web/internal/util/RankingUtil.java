/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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