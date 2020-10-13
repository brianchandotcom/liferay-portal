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

package com.liferay.depot.web.internal.servlet.taglib;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.constants.StagingProcessesPortletKeys;
import com.liferay.taglib.util.HtmlTopTag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DynamicInclude.class)
public class StagingIndicatorDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Group scopeGroup = themeDisplay.getScopeGroup();

			if (scopeGroup.isDepot() && scopeGroup.isStaged() &&
				_portletPermission.contains(
					themeDisplay.getPermissionChecker(),
					StagingProcessesPortletKeys.STAGING_PROCESSES,
					ActionKeys.VIEW)) {

				_includeStagingIndicator(
					scopeGroup, httpServletRequest, httpServletResponse);
			}
		}
		catch (JspException jspException) {
			ReflectionUtil.throwException(jspException);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.product.navigation.taglib#/page.jsp#pre");
	}

	private void _includeStagingIndicator(
			Group group, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, JspException {

		Writer writer = httpServletResponse.getWriter();

		HtmlTopTag htmlTopTag = new HtmlTopTag();

		htmlTopTag.setOutputKey("staging_indicator_css");
		htmlTopTag.setPosition("auto");

		htmlTopTag.doBodyTag(
			httpServletRequest, httpServletResponse,
			pageContext -> {
				try {
					writer.write("<link href=\"");
					writer.write(
						_portal.getStaticResourceURL(
							httpServletRequest,
							_servletContext.getContextPath() +
								"/dynamic_include/StagingIndicator.css"));
					writer.write("\" rel=\"stylesheet\" type=\"text/css\" />");
				}
				catch (IOException ioException) {
					ReflectionUtil.throwException(ioException);
				}
			});

		writer.write(
			"<div class=\"staging-indicator\"><div>" +
				"<button class=\"staging-indicator-button\">" +
					"<span className=\"staging-indicator-title\">");

		if (group.isStagingGroup()) {
			writer.write(_language.get(httpServletRequest, "staging"));
		}
		else {
			writer.write(_language.get(httpServletRequest, "live"));
		}

		writer.write("</span></button></div>");

		writer.write("</div>");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingIndicatorDynamicInclude.class);

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.depot.web)")
	private ServletContext _servletContext;

}