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

package com.liferay.commerce.product.content.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.web.internal.display.context.CPCompareContentMiniDisplayContext;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPCompareHelper;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "javax.portlet.name=" + CPPortletKeys.CP_COMPARE_CONTENT_MINI_WEB,
	service = ConfigurationAction.class
)
public class CPCompareContentMiniConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		try {
			CPCompareContentMiniDisplayContext
				cpCompareContentMiniDisplayContext =
					new CPCompareContentMiniDisplayContext(
						_cpCompareHelper, _cpContentListEntryRendererRegistry,
						_cpContentListRendererRegistry, _cpDefinitionHelper,
						_cpTypeServicesTracker, httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpCompareContentMiniDisplayContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return "/compare_products_mini/configuration.jsp";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.content.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPCompareContentMiniConfigurationAction.class);

	@Reference
	private CPCompareHelper _cpCompareHelper;

	@Reference
	private CPContentListEntryRendererRegistry
		_cpContentListEntryRendererRegistry;

	@Reference
	private CPContentListRendererRegistry _cpContentListRendererRegistry;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

}