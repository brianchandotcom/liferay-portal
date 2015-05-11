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

package com.liferay.dynamic.data.mapping.web.portlet.action;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.dynamic.data.mapping.web.portlet.constants.DDMConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=ddmCopyTemplate",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = { ActionCommand.class }
)
public class DDMCopyTemplateActionCommand extends DDMBaseActionCommand {

	@Override
	protected void doProcessCommand(PortletRequest portletRequest,
			PortletResponse portletResponse) throws Exception {
		
		DDMTemplate template = copyTemplate(portletRequest);

		String redirect = getSaveAndContinueRedirect(
			portletRequest, template);
		
		super.setRedirectAttribute(portletRequest, redirect);
	}

	protected DDMTemplate copyTemplate(PortletRequest portletRequest)
		throws Exception {

		long templateId = ParamUtil.getLong(
			portletRequest, DDMConstants.TEMPLATE_ID);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			portletRequest, DDMConstants.NAME);
		
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				portletRequest, DDMConstants.DESCRIPTION);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMTemplate.class.getName(), portletRequest);

		return DDMTemplateServiceUtil.copyTemplate(
			templateId, nameMap, descriptionMap, serviceContext);
	}

	protected String getSaveAndContinueRedirect(
			PortletRequest portletRequest, DDMTemplate template)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURLImpl portletURL = new PortletURLImpl(
			portletRequest, PortletKeys.DYNAMIC_DATA_MAPPING,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			DDMConstants.MVC_PATH, "/copy_template.jsp");
		
		portletURL.setParameter(
			DDMConstants.TEMPLATE_ID, String.valueOf(template.getTemplateId()), 
			false);
		
		portletURL.setWindowState(portletRequest.getWindowState());

		return portletURL.toString();
	}
}
