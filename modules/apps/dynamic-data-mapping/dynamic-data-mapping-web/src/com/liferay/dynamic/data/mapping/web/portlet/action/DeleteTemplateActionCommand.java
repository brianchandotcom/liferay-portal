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

import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateService;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=deleteTemplate",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING,
		"javax.portlet.name=" + PortletKeys.PORTLET_DISPLAY_TEMPLATES
	},
	service = ActionCommand.class
)
public class DeleteTemplateActionCommand extends DDMBaseActionCommand {

	@Override
	protected void doProcessCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long[] deleteTemplateIds = null;

		long templateId = ParamUtil.getLong(portletRequest, "templateId");

		if (templateId > 0) {
			deleteTemplateIds = new long[] {templateId};
		}
		else {
			deleteTemplateIds = StringUtil.split(
				ParamUtil.getString(portletRequest, "deleteTemplateIds"), 0L);
		}

		for (long deleteTemplateId : deleteTemplateIds) {
			_ddmTemplateService.deleteTemplate(deleteTemplateId);
		}

		setRedirectAttribute(portletRequest);
	}

	@Reference
	protected void setDDMTemplateService(
		DDMTemplateService ddmTemplateService) {

		_ddmTemplateService = ddmTemplateService;
	}

	private DDMTemplateService _ddmTemplateService;

}