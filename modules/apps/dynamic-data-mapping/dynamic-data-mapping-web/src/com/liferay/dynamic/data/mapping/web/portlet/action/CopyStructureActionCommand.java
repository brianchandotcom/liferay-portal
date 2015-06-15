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
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureService;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"command.name=copyStructure",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING
	},
	service = ActionCommand.class
)
public class CopyStructureActionCommand extends DDMBaseActionCommand {

	protected DDMStructure copyStructure(ActionRequest actionRequest)
		throws Exception {

		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), actionRequest);

		DDMStructure structure = _ddmStructureService.copyStructure(
			classPK, nameMap, descriptionMap, serviceContext);

		copyTemplates(actionRequest, classPK, structure.getStructureId());

		return structure;
	}

	protected void copyTemplates(
			ActionRequest actionRequest, long oldClassPK, long newClassPK)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMTemplate.class.getName(), actionRequest);

		long resourceClassNameId = ParamUtil.getLong(
			actionRequest, "resourceClassNameId");
		boolean copyDisplayTemplates = ParamUtil.getBoolean(
			actionRequest, "copyDisplayTemplates");

		if (copyDisplayTemplates) {
			_ddmTemplateService.copyTemplates(
				classNameId, oldClassPK, resourceClassNameId, newClassPK,
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, serviceContext);
		}

		boolean copyFormTemplates = ParamUtil.getBoolean(
			actionRequest, "copyFormTemplates");

		if (copyFormTemplates) {
			_ddmTemplateService.copyTemplates(
				classNameId, oldClassPK, resourceClassNameId, newClassPK,
				DDMTemplateConstants.TEMPLATE_TYPE_FORM, serviceContext);
		}
	}

	@Override
	protected void doProcessCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DDMStructure structure = copyStructure(actionRequest);

		setRedirectAttribute(actionRequest, structure);
	}

	@Override
	protected String getSaveAndContinueRedirect(
			PortletRequest portletRequest, DDMStructure structure,
			String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURLImpl portletURL = new PortletURLImpl(
			portletRequest, themeDisplay.getPpid(), themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/copy_structure");

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		portletURL.setParameter(
			"classNameId", String.valueOf(classNameId), false);

		portletURL.setParameter(
			"classPK", String.valueOf(structure.getStructureId()), false);
		portletURL.setParameter(
			"copyFormTemplates",
			ParamUtil.getString(portletRequest, "copyFormTemplates"), false);
		portletURL.setParameter(
			"copyDisplayTemplates",
			ParamUtil.getString(portletRequest, "copyDisplayTemplates"), false);
		portletURL.setWindowState(portletRequest.getWindowState());

		return portletURL.toString();
	}

	@Reference
	protected void setDDMStructureService(
		DDMStructureService ddmStructureService) {

		_ddmStructureService = ddmStructureService;
	}

	@Reference
	protected void setDDMTemplateService(
		DDMTemplateService ddmTemplateService) {

		_ddmTemplateService = ddmTemplateService;
	}

	private DDMStructureService _ddmStructureService;
	private DDMTemplateService _ddmTemplateService;

}