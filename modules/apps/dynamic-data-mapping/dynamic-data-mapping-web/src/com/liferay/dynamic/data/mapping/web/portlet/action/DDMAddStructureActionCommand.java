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

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.dynamic.data.mapping.web.portlet.constants.DDMConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=ddmAddStructure",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = { ActionCommand.class }
)
public class DDMAddStructureActionCommand extends 
	DDMUpdateStructureActionCommand {

	@Override
	protected void doProcessCommand(PortletRequest portletRequest,
			PortletResponse portletResponse) throws Exception {
		DDMStructure structure = addStructure(portletRequest);
		
		String redirect = ParamUtil.getString(
			portletRequest, DDMConstants.REDIRECT);
		
		redirect = super.setRedirectAttribute(portletRequest, redirect);
		
		boolean saveAndContinue = ParamUtil.getBoolean(
			portletRequest, DDMConstants.SAVE_AND_CONTINUE);

		if (saveAndContinue) {
			redirect = getSaveAndContinueRedirect(
				portletRequest, structure, redirect);
			
			portletRequest.setAttribute(WebKeys.REDIRECT,redirect);
		}
	}

	protected DDMStructure addStructure(PortletRequest portletRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(portletRequest, DDMConstants.GROUP_ID);
		
		long scopeClassNameId = ParamUtil.getLong(
			portletRequest, DDMConstants.SCOPE_CLASS_NAME_ID);
		
		String structureKey = ParamUtil.getString(
			portletRequest, DDMConstants.STRUCTURE_KEY);
		
		long parentStructureId = ParamUtil.getLong(
			portletRequest, DDMConstants.PARENT_STRUCTURE_ID,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);
		
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			portletRequest, DDMConstants.NAME);
		
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				portletRequest, DDMConstants.DESCRIPTION);
		
		DDMForm ddmForm = DDMUtil.getDDMForm((ActionRequest)portletRequest);
		
		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);
		
		String storageType = ParamUtil.getString(
			portletRequest, DDMConstants.STORAGE_TYPE);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), portletRequest);

		return DDMStructureServiceUtil.addStructure(
			groupId, parentStructureId, scopeClassNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType,
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}
}
