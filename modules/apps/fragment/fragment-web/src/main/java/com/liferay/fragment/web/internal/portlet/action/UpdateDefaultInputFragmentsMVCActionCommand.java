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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.helper.DefaultInputFragmentEntryConfigurationProvider;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/update_default_input_fragments"
	},
	service = MVCActionCommand.class
)
public class UpdateDefaultInputFragmentsMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject defaultInputFragmentEntryKeysJSONObject =
			_defaultInputFragmentEntryConfigurationProvider.
				getDefaultInputFragmentEntryKeysJSONObject(
					themeDisplay.getScopeGroupId());

		JSONObject valuesJSONObject = _jsonFactory.createJSONObject(
			ParamUtil.getString(actionRequest, "values"));

		for (String key : valuesJSONObject.keySet()) {
			JSONObject jsonObject = valuesJSONObject.getJSONObject(key);

			defaultInputFragmentEntryKeysJSONObject.put(
				key,
				JSONUtil.put(
					"groupKey", jsonObject.getString("groupKey")
				).put(
					"key", jsonObject.getString("key")
				));
		}

		_defaultInputFragmentEntryConfigurationProvider.
			updateDefaultInputFragmentEntryKeysJSONObject(
				defaultInputFragmentEntryKeysJSONObject,
				themeDisplay.getScopeGroupId());
	}

	@Reference
	private DefaultInputFragmentEntryConfigurationProvider
		_defaultInputFragmentEntryConfigurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

}