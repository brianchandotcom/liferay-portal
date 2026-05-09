/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.util;

import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

/**
 * @author Leslie Wong
 */
public class FaroPaginationUtil {

	public static int getCur(Pagination pagination) {
		if (pagination == null) {
			return _DEFAULT_CUR;
		}

		return pagination.getPage();
	}

	public static int getDelta(Pagination pagination) {
		if (pagination == null) {
			return _DEFAULT_DELTA;
		}

		return pagination.getPageSize();
	}

	public static Map<String, Object> toGraphQLSort(Sort[] sorts) {
		String column = "visitorsMetric";
		String type = "DESC";

		if (ArrayUtil.isNotEmpty(sorts)) {
			Sort sort = sorts[0];

			if (sort.getFieldName() != null) {
				column = sort.getFieldName();
				type = sort.isReverse() ? "DESC" : "ASC";
			}
		}

		return HashMapBuilder.<String, Object>put(
			"column", column
		).put(
			"type", type
		).build();
	}

	private static final int _DEFAULT_CUR = 1;

	private static final int _DEFAULT_DELTA = 20;

}