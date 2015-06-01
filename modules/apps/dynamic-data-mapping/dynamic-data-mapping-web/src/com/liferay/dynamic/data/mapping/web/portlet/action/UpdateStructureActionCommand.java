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
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureService;
import com.liferay.portlet.dynamicdatamapping.util.DDM;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=updateStructure",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = ActionCommand.class
)
public class UpdateStructureActionCommand extends DDMBaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		DDMStructure structure = updateStructure(portletRequest);

		setRedirectAttribute(portletRequest, structure);
	}

	@Reference
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference
	protected void setDDMStructureService(
		DDMStructureService ddmStructureService) {

		_ddmStructureService = ddmStructureService;
	}

	protected DDMStructure updateStructure(PortletRequest portletRequest)
		throws Exception {

		long classPK = ParamUtil.getLong(portletRequest, "classPK");

		long parentStructureId = ParamUtil.getLong(
			portletRequest, "parentStructureId",
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			portletRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(portletRequest, "description");
		DDMForm ddmForm = _ddm.getDDMForm((ActionRequest)portletRequest);
		DDMFormLayout ddmFormLayout = _ddm.getDefaultDDMFormLayout(ddmForm);
		
		int status = ParamUtil.getInteger(portletRequest, "status");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), portletRequest);
		
		serviceContext.setAttribute("status", status);

		return _ddmStructureService.updateStructure(
			classPK, parentStructureId, nameMap, descriptionMap, ddmForm,
			ddmFormLayout, serviceContext);
	}

	private DDM _ddm;
	private DDMStructureService _ddmStructureService;

}