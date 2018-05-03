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

package com.liferay.dynamic.data.mapping.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import java.io.File;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING,
		"javax.portlet.name=" + PortletKeys.PORTLET_DISPLAY_TEMPLATE,
		"mvc.command.name=updateTemplate"
	},
	service = MVCActionCommand.class
)
public class UpdateTemplateMVCActionCommand
	extends AddTemplateMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DDMTemplate template = updateTemplate(actionRequest);

		updatePortletPreferences(actionRequest, template);

		addSuccessMessage(actionRequest, actionResponse);

		setRedirectAttribute(actionRequest, template);
	}

	protected DDMTemplate updateTemplate(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			portal.getUploadPortletRequest(actionRequest);

		long templateId = ParamUtil.getLong(uploadPortletRequest, "templateId");

		long classPK = ParamUtil.getLong(uploadPortletRequest, "classPK");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			uploadPortletRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				uploadPortletRequest, "description");
		String type = ParamUtil.getString(uploadPortletRequest, "type");
		String mode = ParamUtil.getString(uploadPortletRequest, "mode");
		String language = ParamUtil.getString(
			uploadPortletRequest, "language", TemplateConstants.LANG_TYPE_VM);

		String script = getScript(uploadPortletRequest);

		boolean cacheable = ParamUtil.getBoolean(
			uploadPortletRequest, "cacheable");
		boolean smallImage = ParamUtil.getBoolean(
			uploadPortletRequest, "smallImage");
		String smallImageURL = ParamUtil.getString(
			uploadPortletRequest, "smallImageURL");
		File smallImageFile = uploadPortletRequest.getFile("smallImageFile");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMTemplate.class.getName(), uploadPortletRequest);

		return ddmTemplateService.updateTemplate(
			templateId, classPK, nameMap, descriptionMap, type, mode, language,
			script, cacheable, smallImage, smallImageURL, smallImageFile,
			serviceContext);
	}

}