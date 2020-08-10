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

package com.liferay.commerce.account.group.admin.web.internal.search;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import javax.portlet.PortletResponse;

/**
 * @author Alec Sloan
 */
public class CommerceAccountGroupChecker extends EmptyOnClickRowChecker {

	public CommerceAccountGroupChecker(PortletResponse portletResponse) {
		super(portletResponse);
	}

	@Override
	public boolean isDisabled(Object object) {
		CommerceAccountGroup commerceAccountGroup =
			(CommerceAccountGroup)object;

		return commerceAccountGroup.isSystem();
	}

}