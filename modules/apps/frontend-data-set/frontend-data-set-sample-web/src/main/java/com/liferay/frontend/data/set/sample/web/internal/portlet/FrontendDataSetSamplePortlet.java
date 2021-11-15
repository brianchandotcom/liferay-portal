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

package com.liferay.frontend.data.set.sample.web.internal.portlet;

import com.liferay.frontend.data.set.sample.web.internal.FrontendDataSetSampleGenerator;
import com.liferay.frontend.data.set.sample.web.internal.constants.FrontendDataSetSamplePortletKeys;
import com.liferay.frontend.data.set.sample.web.internal.constants.FrontendDataSetSampleWebKeys;
import com.liferay.frontend.data.set.sample.web.internal.display.context.FrontendDataSetSampleDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.concurrent.CompletableFuture;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Frontend Data Set Sample",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + FrontendDataSetSamplePortletKeys.FRONTEND_DATA_SET_SAMPLE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class FrontendDataSetSamplePortlet extends MVCPortlet {

	@Override
	public void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CompletableFuture.runAsync(
			() -> _frontendDataSetSampleGenerator.generateFrontendDataSetSample(
				_portal.getCompanyId(renderRequest)));

		renderRequest.setAttribute(
			FrontendDataSetSampleWebKeys.
				FRONTEND_DATA_SET_SAMPLE_DISPLAY_CONTEXT,
			new FrontendDataSetSampleDisplayContext(
				_portal.getHttpServletRequest(renderRequest)));

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference
	private FrontendDataSetSampleGenerator _frontendDataSetSampleGenerator;

	@Reference
	private Portal _portal;

}