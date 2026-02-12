/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.legacy.groupby;

import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.search.groupby.GroupByRequest;

/**
 * @author Bryan Engler
 */
public class GroupByRequestFactory {

	/**
	 * Provides a GroupByRequest object based off a legacy GroupBy object.
	 *
	 * @param  groupBy the legacy GroupBy object to be converted
	 * @return the converted GroupByRequest object
	 * @review
	 */
	public static GroupByRequest getGroupByRequest(GroupBy groupBy) {
		GroupByRequest groupByRequest = new GroupByRequest(groupBy.getField());

		groupByRequest.setDocsSize(groupBy.getSize());
		groupByRequest.setDocsSorts(groupBy.getSorts());
		groupByRequest.setDocsStart(groupBy.getStart());

		return groupByRequest;
	}

}