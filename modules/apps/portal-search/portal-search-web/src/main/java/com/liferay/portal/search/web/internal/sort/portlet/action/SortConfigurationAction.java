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

package com.liferay.portal.search.web.internal.sort.portlet.action;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.sort.constants.SortPortletKeys;
import com.liferay.portal.search.web.internal.sort.display.context.SortDisplayBuilder;
import com.liferay.portal.search.web.internal.sort.portlet.SortPortletPreferences;
import com.liferay.portal.search.web.internal.sort.portlet.SortPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true, property = "javax.portlet.name=" + SortPortletKeys.SORT,
	service = ConfigurationAction.class
)
public class SortConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/sort/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(renderRequest);

		SortPortletPreferences sortPortletPreferences =
			new SortPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		SortDisplayBuilder sortDisplayBuilder = _createSortDisplayBuilder(
			language, portal, renderRequest, sortPortletPreferences);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, sortDisplayBuilder.build());

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Reference
	protected Language language;

	@Reference
	protected Portal portal;

	private SortDisplayBuilder _createSortDisplayBuilder(
		Language language, Portal portal, RenderRequest renderRequest,
		SortPortletPreferences sortPortletPreferences) {

		try {
			return new SortDisplayBuilder(
				language, portal, renderRequest, sortPortletPreferences);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	@Reference
	private PortletSharedSearchRequest _portletSharedSearchRequest;

}