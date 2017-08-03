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

package com.liferay.vulcan.liferay.portal.filter.provider;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.vulcan.filter.FilterProvider;
import com.liferay.vulcan.filter.IdFilterProviderHelper;
import com.liferay.vulcan.liferay.portal.filter.GroupIdFilter;
import com.liferay.vulcan.liferay.portal.internal.filter.GroupIdFilterImpl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Allows resources to provide {@link GroupIdFilter} in {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder}'s
 * <code>filteredCollectionPage</code> methods.
 *
 * As well as some utility methods for getting a filter's query param map, a
 * filter's name or creating a new filter based on a <code>groupId</code>.
 *
 * @author Alejandro Hernández
 */
@Component(
	immediate = true,
	service = {FilterProvider.class, GroupIdFilterProvider.class}
)
public class GroupIdFilterProvider implements FilterProvider<GroupIdFilter> {

	/**
	 * Creates a new {@link GroupIdFilter} from a given <code>groupId</code>.
	 *
	 * @param  groupId the groupId that will be used to filter.
	 * @return an instance of a {@link GroupIdFilter}.
	 */
	public GroupIdFilter create(Long groupId) {
		return new GroupIdFilterImpl(groupId);
	}

	@Override
	public String getFilterName() {
		return "groupId";
	}

	@Override
	public Map<String, String> getQueryParamMap(
		GroupIdFilter queryParamFilterType) {

		return _idFilterProviderHelper.getQueryParamMap(queryParamFilterType);
	}

	@Override
	public GroupIdFilter provide(HttpServletRequest httpServletRequest) {
		String id = _idFilterProviderHelper.getId(httpServletRequest);

		Long groupId = GetterUtil.getLong(id);

		if (groupId == GetterUtil.DEFAULT_LONG) {
			throw new BadRequestException();
		}

		return new GroupIdFilterImpl(groupId);
	}

	@Reference
	private IdFilterProviderHelper _idFilterProviderHelper;

}