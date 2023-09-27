/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.util;

import com.liferay.analytics.settings.rest.internal.util.comparator.IgnoreCaseOrderByComparator;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Riccardo Ferrari
 */
public class SortUtil {

	public static OrderByComparator<Group> getIgnoreCaseOrderByComparator(
		Locale locale, Sort[] sorts) {

		if (sorts == null) {
			return null;
		}

		List<Object> columns = new ArrayList<>();

		for (Sort sort : sorts) {
			columns.add(sort.getFieldName());
			columns.add(!sort.isReverse());
		}

		return new IgnoreCaseOrderByComparator<>(columns.toArray(), locale);
	}

}