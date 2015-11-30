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

package com.liferay.server.admin.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoPresetUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author Philip Jones
 */
@Component(
	property = {
		"javax.portlet.name=" + PortletKeys.SERVER_ADMIN,
		"mvc.command.name=/server_admin/edit_document_library_extra_settings"
	},
	service = MVCActionCommand.class
)
public class EditDocumentLibraryExtraSettingsMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals("convert")) {
			convert(actionRequest, actionResponse);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	protected int addCustomField(long companyId, String name, String preset)
		throws Exception {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, DLFileEntryConstants.getClassName(), 0);

		if (preset.startsWith("Preset")) {
			return ExpandoPresetUtil.addPresetExpando(
				expandoBridge, preset, name);
		}

		int type = GetterUtil.getInteger(preset);

		expandoBridge.addAttribute(name, type);

		return type;
	}

	protected void convert(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] keys = StringUtil.split(
			ParamUtil.getString(actionRequest, "keys"));

		String[] presets = new String[keys.length];

		int[] types = new int[keys.length];

		for (int i = 0; i < keys.length; i++) {
			presets[i] = ParamUtil.getString(actionRequest, "type_" + keys[i]);

			types[i] = addCustomField(
				themeDisplay.getCompanyId(), keys[i], presets[i]);
		}

		_dlFileEntryLocalService.convertExtraSettings(keys);
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	private volatile DLFileEntryLocalService _dlFileEntryLocalService;

}