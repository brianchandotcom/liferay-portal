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

package com.liferay.layout.seo.web.internal.servlet.taglib;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.redirect.configuration.CrawlerUserAgentsConfiguration;

import java.io.IOException;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = DynamicInclude.class)
public class LayoutSEODynamicRenderingTopHeadJSPDynamicInclude
	extends BaseJSPDynamicInclude {

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		String configurationPid =
			CrawlerUserAgentsConfiguration.class.getName();

		httpServletRequest.setAttribute(
			"edit-configuration.jsp-configurationURL",
			_language.format(
				httpServletRequest,
				"to-set-up-the-user-agents-go-to-crawler-user-agents",
				new String[] {
					"<a href=\"" +
						PortletURLBuilder.create(
							_portal.getControlPanelPortletURL(
								httpServletRequest,
								ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
								PortletRequest.RENDER_PHASE)
						).setMVCRenderCommandName(
							"/configuration_admin/edit_configuration"
						).setRedirect(
							_portal.getCurrentCompleteURL(httpServletRequest)
						).setParameter(
							"factoryPid", configurationPid
						).setParameter(
							"pid", configurationPid
						).buildString() + "\">",
					"</a>"
				}));

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			StringBundler.concat(
				"com.liferay.configuration.admin.web#/edit_configuration.jsp#",
				"com.liferay.layout.seo.web.internal.configuration.",
				"LayoutSEODynamicRenderingConfiguration#pre"));
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/com.liferay.configuration.admin.web" +
			"/edit_configuration.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEODynamicRenderingTopHeadJSPDynamicInclude.class);

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.layout.seo.web)")
	private ServletContext _servletContext;

}