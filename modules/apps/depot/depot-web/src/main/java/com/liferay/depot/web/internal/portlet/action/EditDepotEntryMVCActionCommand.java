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

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_SETTINGS,
		"mvc.command.name=/depot_entry/edit"
	},
	service = MVCActionCommand.class
)
public class EditDepotEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long depotEntryId = ParamUtil.getLong(
				actionRequest, "depotEntryId");

			DepotEntry depotEntry = _depotEntryService.getDepotEntry(
				depotEntryId);

			Group group = depotEntry.getGroup();

			UnicodeProperties depotAppCustomizationProperties =
				PropertiesParamUtil.getProperties(
					actionRequest, "DepotAppCustomization--");

			_depotEntryService.updateDepotEntry(
				depotEntryId,
				LocalizationUtil.getLocalizationMap(
					actionRequest, "name", group.getNameMap()),
				LocalizationUtil.getLocalizationMap(
					actionRequest, "description", group.getDescriptionMap()),
				_toStringBooleanMap(depotAppCustomizationProperties),
				PropertiesParamUtil.getProperties(
					actionRequest, "TypeSettingsProperties--"),
				ServiceContextFactory.getInstance(
					DepotEntry.class.getName(), actionRequest));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			SessionErrors.add(
				actionRequest, portalException.getClass(), portalException);

			actionResponse.sendRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	private Map<String, Boolean> _toStringBooleanMap(
		UnicodeProperties unicodeProperties) {

		Map<String, Boolean> map = new HashMap<>();

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			map.put(entry.getKey(), GetterUtil.getBoolean(entry.getValue()));
		}

		return map;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditDepotEntryMVCActionCommand.class);

	@Reference
	private DepotEntryService _depotEntryService;

}