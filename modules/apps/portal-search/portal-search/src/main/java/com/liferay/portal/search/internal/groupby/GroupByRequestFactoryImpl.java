/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.groupby;

import com.liferay.portal.search.groupby.GroupByRequest;
import com.liferay.portal.search.groupby.GroupByRequestFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = GroupByRequestFactory.class)
public class GroupByRequestFactoryImpl implements GroupByRequestFactory {

	@Override
	public GroupByRequest getGroupByRequest(String field) {
		return new GroupByRequestImpl(field);
	}

}