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

package com.liferay.dispatch.web.internal.portlet;

import com.liferay.dispatch.constants.DispatchPortletKeys;
import com.liferay.dispatch.constants.DispatchWebKeys;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.web.internal.display.context.DispatchTriggerDisplayContext;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-dispatch",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Dispatch",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DispatchPortletKeys.DISPATCH,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class DispatchPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		long dispatchTriggerId = ParamUtil.getLong(
			renderRequest, "dispatchTriggerId");

		if (dispatchTriggerId > 0) {
			DispatchTrigger dispatchTrigger =
				_dispatchTriggerLocalService.fetchDispatchTrigger(
					dispatchTriggerId);

			if (dispatchTrigger != null) {
				renderRequest.setAttribute(
					DispatchWebKeys.DISPATCH_TRIGGER, dispatchTrigger);
			}
		}

		DispatchTriggerDisplayContext dispatchTriggerDisplayContext =
			new DispatchTriggerDisplayContext(
				_dispatchTriggerLocalService, renderRequest,
				_dispatchTaskExecutorServiceTrackerMap.keySet());

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, dispatchTriggerDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dispatchTaskExecutorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DispatchTaskExecutor.class,
				"dispatch.task.executor.type");
	}

	@Deactivate
	protected void deactivate() {
		_dispatchTaskExecutorServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, DispatchTaskExecutor>
		_dispatchTaskExecutorServiceTrackerMap;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private Portal _portal;

}