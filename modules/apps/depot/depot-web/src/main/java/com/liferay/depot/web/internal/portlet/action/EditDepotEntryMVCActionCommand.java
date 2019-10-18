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
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.depot.web.internal.util.DepotEntryURLUtil;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
		"mvc.command.name=/depot_entry/edit"
	},
	service = MVCActionCommand.class
)
public class EditDepotEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long depotEntryId = ParamUtil.getLong(actionRequest, "depotEntryId");

		DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
			depotEntryId);

		Group group = _groupService.getGroup(depotEntry.getGroupId());

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name", group.getNameMap());
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "description", group.getDescriptionMap());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DepotEntry.class.getName(), actionRequest);

		UnicodeProperties formTypeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		try {
			_depotEntryLocalService.updateDepotEntry(
				depotEntryId, formTypeSettingsProperties, nameMap,
				descriptionMap, serviceContext);
		}
		catch (LocaleException le) {
			SessionErrors.add(actionRequest, le.getClass(), le);

			RenderURL editDepotEntryRenderURL =
				DepotEntryURLUtil.getEditDepotEntryRenderURL(
					depotEntryId,
					ParamUtil.getString(actionRequest, "redirect"),
					_portal.getLiferayPortletResponse(actionResponse));

			sendRedirect(
				actionRequest, actionResponse,
				editDepotEntryRenderURL.toString());
		}
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private Portal _portal;

}