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

package com.liferay.portal.search.web.internal.modified.facet.portlet.action;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.search.web.internal.modified.facet.builder.DateRangeFactory;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;

import java.text.ParseException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lino Alves
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ModifiedFacetPortletKeys.MODIFIED_FACET,
	service = ConfigurationAction.class
)
public class ModifiedFacetPortletConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/modified/facet/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, _PARAMETER_NAME_PREFIX);

		String ranges = properties.getProperty("ranges");

		try {
			DateRangeFactory dateRangeFactory = new DateRangeFactory(
				DateFormatFactoryUtil.getDateFormatFactory());

			dateRangeFactory.validateRange(ranges);
		}
		catch (JSONException | ParseException e) {
			SessionErrors.add(actionRequest, "unparsableDate");

			_log.error(e, e);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			super.processAction(portletConfig, actionRequest, actionResponse);
		}
	}

	private static final String _PARAMETER_NAME_PREFIX = "preferences--";

	private static final Log _log = LogFactoryUtil.getLog(
		ModifiedFacetPortletConfigurationAction.class);

}