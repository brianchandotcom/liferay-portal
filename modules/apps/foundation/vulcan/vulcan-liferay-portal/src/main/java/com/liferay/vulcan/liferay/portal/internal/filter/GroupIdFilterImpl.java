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

package com.liferay.vulcan.liferay.portal.internal.filter;

import com.liferay.vulcan.liferay.portal.filter.GroupIdFilter;

/**
 * @author Alejandro Hernández
 */
public class GroupIdFilterImpl implements GroupIdFilter {

	public GroupIdFilterImpl(Long groupId) {
		_groupId = groupId;
	}

	@Override
	public Long getId() {
		return _groupId;
	}

	private final Long _groupId;

}