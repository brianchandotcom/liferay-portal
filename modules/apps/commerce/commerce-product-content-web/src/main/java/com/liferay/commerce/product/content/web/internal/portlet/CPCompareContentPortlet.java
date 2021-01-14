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

package com.liferay.commerce.product.content.web.internal.portlet;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.constants.CPContentWebKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.util.CPCompareContentHelper;
import com.liferay.commerce.product.content.web.internal.display.context.CPCompareContentDisplayContext;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPCompareHelper;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-product-compare-content",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Product Comparison Table",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/compare_products/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_COMPARE_CONTENT_WEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = {CPCompareContentPortlet.class, Portlet.class}
)
public class CPCompareContentPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			CPContentWebKeys.CP_COMPARE_CONTENT_HELPER,
			_cpCompareContentHelper);

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);

			CPCompareContentDisplayContext cpCompareContentDisplayContext =
				new CPCompareContentDisplayContext(
					_cpCompareHelper, _cpContentListEntryRendererRegistry,
					_cpContentListRendererRegistry, _cpDefinitionHelper,
					_cpTypeServicesTracker, httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpCompareContentDisplayContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		super.render(renderRequest, renderResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPCompareContentPortlet.class);

	@Reference
	private CPCompareContentHelper _cpCompareContentHelper;

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

	@Reference
	private Portal _portal;

}