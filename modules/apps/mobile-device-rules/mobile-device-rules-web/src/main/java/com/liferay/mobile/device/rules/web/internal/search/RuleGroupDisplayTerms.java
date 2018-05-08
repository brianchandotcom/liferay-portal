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

package com.liferay.mobile.device.rules.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Edward Han
 */
public class RuleGroupDisplayTerms extends DisplayTerms {

	public static final String GROUP_ID = "searchGroupId";

	public static final String NAME = "searchName";

	public RuleGroupDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		_groupId = ParamUtil.getLong(portletRequest, GROUP_ID);
		_name = ParamUtil.getString(portletRequest, NAME);
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getName() {
		return _name;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _groupId;
	private String _name;

}