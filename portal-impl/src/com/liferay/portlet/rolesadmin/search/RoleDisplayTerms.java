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

package com.liferay.portlet.rolesadmin.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class RoleDisplayTerms extends DisplayTerms {

	public static final String DESCRIPTION = "description";

	public static final String NAME = "name";

	public static final String TYPE = "type";

	public RoleDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		name = ParamUtil.getString(portletRequest, NAME);
		type = ParamUtil.getInteger(portletRequest, TYPE);
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		if ((type == RoleConstants.TYPE_ORGANIZATION) ||
			(type == RoleConstants.TYPE_REGULAR) ||
			(type == RoleConstants.TYPE_SITE)) {

			return type;
		}

		return 0;
	}

	public String getTypeString() {
		if ((type == RoleConstants.TYPE_ORGANIZATION) ||
			(type == RoleConstants.TYPE_REGULAR) ||
			(type == RoleConstants.TYPE_SITE)) {

			return String.valueOf(type);
		}

		return StringPool.BLANK;
	}

	public void setType(int type) {
		this.type = type;
	}

	protected String description;
	protected String name;
	protected int type;

}