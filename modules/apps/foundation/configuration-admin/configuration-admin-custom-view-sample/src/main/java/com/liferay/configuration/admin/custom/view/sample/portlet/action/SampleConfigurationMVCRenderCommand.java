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

package com.liferay.configuration.admin.custom.view.sample.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.custom.view.sample.configuration.SampleConfiguration;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	configurationPid = "com.liferay.configuration.admin.custom.view.sample.configuration.SampleConfiguration",
	immediate = true,
	property = {
		"configurationPid=com.liferay.configuration.admin.custom.view.sample.configuration.SampleConfiguration",
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/edit_configuration"
	},
	service = MVCRenderCommand.class
)
public class SampleConfigurationMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				"sampleCheckbox", _sampleConfiguration.sampleCheckbox());
			renderRequest.setAttribute(
				"sampleNumber", _sampleConfiguration.sampleNumber());
			renderRequest.setAttribute(
				"sampleText", _sampleConfiguration.sampleText());

			_jspRenderer.renderJSP(
				_servletContext, _portal.getHttpServletRequest(renderRequest),
				_portal.getHttpServletResponse(renderResponse),
				"/view_sample_configuration.jsp");
		}
		catch (Exception e) {
			throw new PortletException(
				"Unable to include view_sample_configuration.jsp", e);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_sampleConfiguration = ConfigurableUtil.createConfigurable(
			SampleConfiguration.class, properties);
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	private volatile SampleConfiguration _sampleConfiguration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.configuration.admin.custom.view.sample)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}