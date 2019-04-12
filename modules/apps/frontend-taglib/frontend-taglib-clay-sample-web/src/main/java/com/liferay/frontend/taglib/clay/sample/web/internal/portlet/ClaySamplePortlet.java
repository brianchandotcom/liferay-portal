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

package com.liferay.frontend.taglib.clay.sample.web.internal.portlet;

import com.liferay.frontend.taglib.clay.sample.web.constants.ClaySamplePortletKeys;
import com.liferay.frontend.taglib.clay.sample.web.internal.display.context.CardsDisplayContext;
import com.liferay.frontend.taglib.clay.sample.web.internal.display.context.DropdownsDisplayContext;
import com.liferay.frontend.taglib.clay.sample.web.internal.display.context.ManagementToolbarsDisplayContext;
import com.liferay.frontend.taglib.clay.sample.web.internal.display.context.NavigationBarsDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.ClayTagDataSourceAjaxInvoker;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-clay-sample",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Clay Sample",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ClaySamplePortletKeys.CLAY_SAMPLE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ClaySamplePortlet extends MVCPortlet {

	@Override
	public void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CardsDisplayContext cardsDisplayContext = new CardsDisplayContext();

		renderRequest.setAttribute(
			ClaySamplePortletKeys.CARDS_DISPLAY_CONTEXT, cardsDisplayContext);

		DropdownsDisplayContext dropdownsDisplayContext =
			new DropdownsDisplayContext();

		renderRequest.setAttribute(
			ClaySamplePortletKeys.DROPDOWNS_DISPLAY_CONTEXT,
			dropdownsDisplayContext);

		ManagementToolbarsDisplayContext managementToolbarsDisplayContext =
			new ManagementToolbarsDisplayContext();

		renderRequest.setAttribute(
			ClaySamplePortletKeys.MANAGEMENT_TOOLBARS_DISPLAY_CONTEXT,
			managementToolbarsDisplayContext);

		NavigationBarsDisplayContext navigationBarsDisplayContext =
			new NavigationBarsDisplayContext();

		renderRequest.setAttribute(
			ClaySamplePortletKeys.NAVIGATION_BARS_DISPLAY_CONTEXT,
			navigationBarsDisplayContext);

		super.doDispatch(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("tableItems")) {
			resourceResponse.setContentType(ContentTypes.APPLICATION_JSON);

			PrintWriter writer = resourceResponse.getWriter();

			String dataSourceKey = resourceRequest.getParameter(
				"dataSourceKey");
			int page = Integer.valueOf(resourceRequest.getParameter("page"));
			int pageSize = Integer.valueOf(
				resourceRequest.getParameter("pageSize"));

			writer.write(
				_clayTagDataSourceAjaxInvoker.getItems(
					_portal.getHttpServletRequest(resourceRequest),
					dataSourceKey, page, pageSize));
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	@Reference
	private ClayTagDataSourceAjaxInvoker _clayTagDataSourceAjaxInvoker;

	@Reference
	private Portal _portal;

}