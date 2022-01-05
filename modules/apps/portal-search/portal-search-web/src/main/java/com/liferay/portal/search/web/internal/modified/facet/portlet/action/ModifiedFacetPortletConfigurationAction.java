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
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.modified.facet.builder.DateRangeFactory;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetDisplayBuilder;

import java.text.ParseException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		ModifiedFacetDisplayBuilder modifiedFacetDisplayBuilder =
			_createModifiedFacetDisplayBuilder(renderRequest);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			modifiedFacetDisplayBuilder.build());

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		UnicodeProperties unicodeProperties = PropertiesParamUtil.getProperties(
			actionRequest, _PARAMETER_NAME_PREFIX);

		String ranges = unicodeProperties.getProperty("ranges");

		try {
			DateRangeFactory dateRangeFactory = new DateRangeFactory(
				DateFormatFactoryUtil.getDateFormatFactory());

			dateRangeFactory.validateRange(ranges);
		}
		catch (JSONException | ParseException exception) {
			SessionErrors.add(actionRequest, "unparsableDate");

			_log.error(exception, exception);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			super.processAction(portletConfig, actionRequest, actionResponse);
		}
	}

	private ModifiedFacetDisplayBuilder _createModifiedFacetDisplayBuilder(
		RenderRequest renderRequest) {

		try {
			return new ModifiedFacetDisplayBuilder(
				null, null, null, renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private static final String _PARAMETER_NAME_PREFIX = "preferences--";

	private static final Log _log = LogFactoryUtil.getLog(
		ModifiedFacetPortletConfigurationAction.class);

}