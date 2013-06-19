/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.convert.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.expando.action.EditExpandoAction;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 */
public class EditDocumentLibraryExtraSettingsAction extends EditExpandoAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals("convert")) {
			convert(actionRequest, actionResponse);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.admin.edit_document_library_extra_settings"));
	}

	protected int addCustomField(long companyId, String name, String preset)
		throws Exception {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, DLFileEntryConstants.getClassName(), 0);

		if (preset.startsWith("Preset")) {
			return addPresetExpando(expandoBridge, preset, name);
		}
		else {
			int type = GetterUtil.getInteger(preset);

			expandoBridge.addAttribute(name, type);

			return type;
		}
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

		DLFileEntryLocalServiceUtil.convertExtraSettings(keys);
	}

}