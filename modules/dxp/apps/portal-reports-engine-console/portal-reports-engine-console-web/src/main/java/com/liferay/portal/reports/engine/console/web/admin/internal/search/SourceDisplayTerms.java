/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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