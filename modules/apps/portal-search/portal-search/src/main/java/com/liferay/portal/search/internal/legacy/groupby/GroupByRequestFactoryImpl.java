/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.legacy.groupby;

import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.search.groupby.GroupByRequest;
import com.liferay.portal.search.internal.groupby.GroupByRequestImpl;
import com.liferay.portal.search.legacy.groupby.GroupByRequestFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = GroupByRequestFactory.class)
public class GroupByRequestFactoryImpl implements GroupByRequestFactory {

	@Override
	public GroupByRequest getGroupByRequest(GroupBy groupBy) {
		GroupByRequest groupByRequest = new GroupByRequestImpl(
			groupBy.getField());

		groupByRequest.setDocsSize(groupBy.getSize());
		groupByRequest.setDocsSorts(groupBy.getSorts());
		groupByRequest.setDocsStart(groupBy.getStart());

		return groupByRequest;
	}

}