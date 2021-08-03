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

package com.liferay.template.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.configuration.DDMGroupServiceConfiguration;
import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.web.internal.display.context.EditDDMTemplateDisplayContext;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TemplatePortletKeys.TEMPLATE,
		"mvc.command.name=/template/edit_ddm_template"
	},
	service = MVCRenderCommand.class
)
public class EditDDMTemplateMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		renderRequest.setAttribute(
			DDMGroupServiceConfiguration.class.getName(),
			_getDDMGroupServiceConfiguration(themeDisplay.getScopeGroupId()));

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new EditDDMTemplateDisplayContext(
				_portal.getLiferayPortletRequest(renderRequest),
				_portal.getLiferayPortletResponse(renderResponse)));

		return "/edit_ddm_template.jsp";
	}

	private DDMGroupServiceConfiguration _getDDMGroupServiceConfiguration(
		long groupId) {

		try {
			return _configurationProvider.getConfiguration(
				DDMGroupServiceConfiguration.class,
				new GroupServiceSettingsLocator(
					groupId, DDMConstants.SERVICE_NAME));
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	private Portal _portal;

}