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

package com.liferay.push.notifications.web.portlet;

import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.push.notifications.constants.PushNotificationsPortletKeys;
import com.liferay.push.notifications.service.PushNotificationsDeviceService;
import com.liferay.push.notifications.web.constants.PushNotificationsWebKeys;
import com.liferay.push.notifications.web.util.ResourceBundleLoaderProvider;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=push-notifications",
		"com.liferay.portlet.display-category=category.hidden",
		"javax.portlet.display-name=Push Notifications",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Push Notifications",
		"javax.portlet.info.short-title=Push Notifications",
		"javax.portlet.info.title=Push Notifications",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PushNotificationsPortletKeys.PUSH_NOTIFICATIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class PushNotificationsPortlet extends MVCPortlet {

	public void deletePushNotificationsDevice(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long pushNotificationsDeviceId = ParamUtil.getLong(
			actionRequest, "pushNotificationsDeviceId");

		_pushNotificationsDeviceService.deletePushNotificationsDevice(
			pushNotificationsDeviceId);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			PushNotificationsWebKeys.RESOURCE_BUNDLE_LOADER_PROVIDER,
			_resourceBundleLoaderProvider);

		super.render(renderRequest, renderResponse);
	}

	@Reference(unbind = "-")
	protected void setPushNotificationsDeviceService(
		PushNotificationsDeviceService pushNotificationsDeviceService) {

		_pushNotificationsDeviceService = pushNotificationsDeviceService;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.push.notifications.web)(release.schema.version=1.0.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	@Reference(unbind = "-")
	protected void setResourceBundleLoaderProvider(
		ResourceBundleLoaderProvider resourceBundleLoaderProvider) {

		_resourceBundleLoaderProvider = resourceBundleLoaderProvider;
	}

	private PushNotificationsDeviceService _pushNotificationsDeviceService;
	private ResourceBundleLoaderProvider _resourceBundleLoaderProvider;

}