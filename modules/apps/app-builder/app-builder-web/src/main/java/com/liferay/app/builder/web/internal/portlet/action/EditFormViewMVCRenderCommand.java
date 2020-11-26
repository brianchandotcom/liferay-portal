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

package com.liferay.app.builder.web.internal.portlet.action;

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.web.internal.configuration.AppBuilderConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bruno Basto
 */
@Component(
	configurationPid = "com.liferay.app.builder.web.internal.configuration.AppBuilderConfiguration",
	property = {
		"javax.portlet.name=" + AppBuilderPortletKeys.OBJECTS,
		"mvc.command.name=/app_builder/edit_form_view"
	},
	service = MVCRenderCommand.class
)
public class EditFormViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return "/edit_form_view.jsp";
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_appBuilderConfiguration = ConfigurableUtil.createConfigurable(
			AppBuilderConfiguration.class, properties);
	}

	private volatile AppBuilderConfiguration _appBuilderConfiguration;

}