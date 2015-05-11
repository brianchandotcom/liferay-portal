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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=ddmCopyStructure",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = { ActionCommand.class }
)
public class DDMCopyStructureActionCommand extends DDMBaseActionCommand {

	@Override
	protected void doProcessCommand(PortletRequest portletRequest,
			PortletResponse portletResponse) throws Exception {
		
		DDMStructure structure = copyStructure(portletRequest);
		
		String redirect = getSaveAndContinueRedirect(
			portletRequest, structure);
		
		super.setRedirectAttribute(portletRequest, redirect);
		
	}

	protected DDMStructure copyStructure(PortletRequest portletRequest)
		throws Exception {

		long classPK = ParamUtil.getLong(portletRequest, DDMConstants.CLASS_PK);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			portletRequest, DDMConstants.NAME);
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				portletRequest, DDMConstants.DESCRIPTION);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), portletRequest);

		DDMStructure structure = DDMStructureServiceUtil.copyStructure(
			classPK, nameMap, descriptionMap, serviceContext);

		copyTemplates(portletRequest, classPK, structure.getStructureId());

		return structure;
	}

	protected void copyTemplates(
			PortletRequest portletRequest, long oldClassPK, long newClassPK)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMTemplate.class.getName(), portletRequest);

		long resourceClassNameId = ParamUtil.getLong(
			portletRequest, DDMConstants.RESOURCE_CLASS_NAME_ID);
		boolean copyDisplayTemplates = ParamUtil.getBoolean(
			portletRequest, DDMConstants.COPY_DISPLAY_TEMPLATES);

		if (copyDisplayTemplates) {
			DDMTemplateServiceUtil.copyTemplates(
				classNameId, oldClassPK, resourceClassNameId, newClassPK,
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, serviceContext);
		}

		boolean copyFormTemplates = ParamUtil.getBoolean(
			portletRequest, DDMConstants.COPY_FORM_TEMPLATES);

		if (copyFormTemplates) {
			DDMTemplateServiceUtil.copyTemplates(
				classNameId, oldClassPK, resourceClassNameId, newClassPK,
				DDMTemplateConstants.TEMPLATE_TYPE_FORM, serviceContext);
		}
	}

	protected String getSaveAndContinueRedirect(
			PortletRequest portletRequest, DDMStructure structure)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURLImpl portletURL = new PortletURLImpl(
			portletRequest, PortletKeys.DYNAMIC_DATA_MAPPING,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(DDMConstants.MVC_PATH, "/copy_structure.jsp");

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		portletURL.setParameter(
			DDMConstants.CLASS_NAME_ID, String.valueOf(classNameId), false);

		portletURL.setParameter(
			DDMConstants.CLASS_PK, String.valueOf(structure.getStructureId()), 
			false);
		portletURL.setParameter(
			DDMConstants.COPY_FORM_TEMPLATES,
			ParamUtil.getString(
				portletRequest, DDMConstants.COPY_FORM_TEMPLATES), false);
		portletURL.setParameter(
			DDMConstants.COPY_DISPLAY_TEMPLATES,
			ParamUtil.getString(
				portletRequest, DDMConstants.COPY_DISPLAY_TEMPLATES), false);
		portletURL.setWindowState(portletRequest.getWindowState());

		return portletURL.toString();
	}
}
