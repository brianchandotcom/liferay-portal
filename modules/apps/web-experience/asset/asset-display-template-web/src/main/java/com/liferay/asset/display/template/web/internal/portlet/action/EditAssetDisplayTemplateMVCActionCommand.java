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

package com.liferay.asset.display.template.web.internal.portlet.action;

import com.liferay.asset.display.template.constants.AssetDisplayTemplatePortletKeys;
import com.liferay.asset.display.template.service.AssetDisplayTemplateService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"javax.portlet.name=" + AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE,
		"mvc.command.name=/asset_display_template/edit_asset_display_template"
	},
	service = MVCActionCommand.class
)
public class EditAssetDisplayTemplateMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long assetDisplayTemplateId = ParamUtil.getLong(
			actionRequest, "assetDisplayTemplateId");

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");

		String name = ParamUtil.getString(actionRequest, "name");
		String language = ParamUtil.getString(actionRequest, "language");
		String scriptContent = ParamUtil.getString(
			actionRequest, "scriptContent");
		boolean main = ParamUtil.getBoolean(actionRequest, "main");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (assetDisplayTemplateId <= 0) {
			_assetDisplayTemplateService.addAssetDisplayTemplate(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(), name,
				classNameId, language, scriptContent, main, serviceContext);
		}
		else {
			_assetDisplayTemplateService.updateAssetDisplayTemplate(
				assetDisplayTemplateId, name, classNameId, language,
				scriptContent, main, serviceContext);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditAssetDisplayTemplateMVCActionCommand.class);

	@Reference
	private AssetDisplayTemplateService _assetDisplayTemplateService;

	@Reference
	private Portal _portal;

}