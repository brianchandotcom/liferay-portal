/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.portlet.action;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.date.facet.constants.DateFacetPortletKeys;
import com.liferay.portal.search.web.internal.date.facet.display.context.builder.DateFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.date.range.DateRangeFactory;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	property = "javax.portlet.name=" + DateFacetPortletKeys.DATE_FACET,
	service = ConfigurationAction.class
)
public class DateFacetPortletConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/date/facet/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
			_createDateFacetDisplayContextBuilder(renderRequest);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			dateFacetDisplayContextBuilder.build());

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
				DateFormatFactoryUtil.getDateFormatFactory(), _jsonFactory);

			dateRangeFactory.validateRangeSyntax(ranges);
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, "unparsableSyntax");

			_log.error(exception);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			super.processAction(portletConfig, actionRequest, actionResponse);
		}
	}

	private DateFacetDisplayContextBuilder
		_createDateFacetDisplayContextBuilder(RenderRequest renderRequest) {

		try {
			DateFacetDisplayContextBuilder dateFacetDisplayContextBuilder =
				new DateFacetDisplayContextBuilder(null, renderRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			dateFacetDisplayContextBuilder.setLocale(themeDisplay.getLocale());
			dateFacetDisplayContextBuilder.setTimeZone(
				themeDisplay.getTimeZone());

			return dateFacetDisplayContextBuilder;
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private static final String _PARAMETER_NAME_PREFIX = "preferences--";

	private static final Log _log = LogFactoryUtil.getLog(
		DateFacetPortletConfigurationAction.class);

	@Reference
	private JSONFactory _jsonFactory;

}