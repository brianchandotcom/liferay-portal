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

package com.liferay.portal.reports.engine.console.web.admin.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Rafael Praxedes
 */
public class SourceDisplayTerms extends DisplayTerms {

	public static final String DRIVER_URL = "driverUrl";

	public static final String NAME = "name";

	public SourceDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		name = ParamUtil.getString(portletRequest, NAME);
		driverUrl = ParamUtil.getString(portletRequest, DRIVER_URL);
	}

	public String getDriverUrl() {
		return driverUrl;
	}

	public String getName() {
		return name;
	}

	protected String driverUrl;
	protected String name;

}