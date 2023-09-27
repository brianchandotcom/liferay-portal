/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.groupby;

import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.groupby.GroupByResponseFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = GroupByResponseFactory.class)
public class GroupByResponseFactoryImpl implements GroupByResponseFactory {

	@Override
	public GroupByResponse getGroupByResponse(String field) {
		return new GroupByResponseImpl(field);
	}

}