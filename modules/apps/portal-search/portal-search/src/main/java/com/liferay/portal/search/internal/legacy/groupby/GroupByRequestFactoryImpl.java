/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
@Component(immediate = true, service = GroupByRequestFactory.class)
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