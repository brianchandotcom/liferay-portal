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

package com.liferay.organizations.item.selector.web.internal.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.ArrayUtil;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class OrganizationItemSelectorChecker extends EmptyOnClickRowChecker {

	public OrganizationItemSelectorChecker(
		RenderResponse renderResponse, long[] checkedOrganizationIds) {

		super(renderResponse);

		_checkedOrganizationIds = checkedOrganizationIds;
	}

	@Override
	public boolean isChecked(Object obj) {
		Organization organization = (Organization)obj;

		return ArrayUtil.contains(
			_checkedOrganizationIds, organization.getOrganizationId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		return isChecked(obj);
	}

	private final long[] _checkedOrganizationIds;

}