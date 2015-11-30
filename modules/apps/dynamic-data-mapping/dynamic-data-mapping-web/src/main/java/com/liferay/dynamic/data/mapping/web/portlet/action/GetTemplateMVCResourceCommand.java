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

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING,
		"mvc.command.name=getTemplate"
	},
	service = MVCResourceCommand.class
)
public class GetTemplateMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long templateId = ParamUtil.getLong(resourceRequest, "templateId");

		DDMTemplate template = _ddmTemplateService.getTemplate(templateId);

		String script = template.getScript();

		String contentType = null;

		String type = template.getType();

		String language = GetterUtil.getString(
			template.getLanguage(), TemplateConstants.LANG_TYPE_VM);

		if (type.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM)) {
			contentType = ContentTypes.APPLICATION_JSON;
		}
		else if (language.equals(TemplateConstants.LANG_TYPE_XSL)) {
			contentType = ContentTypes.TEXT_XML_UTF8;
		}
		else {
			contentType = ContentTypes.TEXT_PLAIN_UTF8;
		}

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(resourceRequest);

		HttpServletResponse httpServletResponse =
			PortalUtil.getHttpServletResponse(resourceResponse);

		ServletResponseUtil.sendFile(
			httpServletRequest, httpServletResponse, null, script.getBytes(),
			contentType);
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateService(
		DDMTemplateService ddmTemplateService) {

		_ddmTemplateService = ddmTemplateService;
	}

	private volatile DDMTemplateService _ddmTemplateService;

}