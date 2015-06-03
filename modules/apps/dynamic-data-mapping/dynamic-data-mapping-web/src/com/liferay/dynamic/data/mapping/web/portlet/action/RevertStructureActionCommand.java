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

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureService;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureVersionService;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=revertStructure",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = ActionCommand.class
)
public class RevertStructureActionCommand extends BaseActionCommand {

	@Override
	protected void doProcessCommand(
		PortletRequest portletRequest, PortletResponse portletResponse) 
		throws Exception {

		long structureId = ParamUtil.getLong(portletRequest, "structureId");
		
		long structureVersionId = ParamUtil.getLong(
			portletRequest, "structureVersionId");
		
		DDMStructureVersion structureVersion =
			_ddmStructureVersionService.getStructureVersion(structureVersionId);
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DDMStructure.class.getName(), portletRequest);
		
		serviceContext.setAttribute("status", structureVersion.getStatus());

		_ddmStructureService.revertStructure(
			structureId, structureVersionId, serviceContext);
	}

	@Reference
	protected void setDDMStructureService(
		DDMStructureService ddmStructureService) {

		_ddmStructureService = ddmStructureService;
	}
	
	@Reference
	protected void setDDMStructureVersionService(
		DDMStructureVersionService ddmStructureVersionService) {

		_ddmStructureVersionService = ddmStructureVersionService;
	}

	private DDMStructureService _ddmStructureService;
	private DDMStructureVersionService _ddmStructureVersionService;

}
