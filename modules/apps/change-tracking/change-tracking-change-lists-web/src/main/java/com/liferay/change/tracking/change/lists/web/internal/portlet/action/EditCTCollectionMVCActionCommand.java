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

package com.liferay.change.tracking.change.lists.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS,
		"mvc.command.name=/change_lists/add_ct_collection",
		"mvc.command.name=/change_lists/edit_ct_collection"
	},
	service = MVCActionCommand.class
)
public class EditCTCollectionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");

		long ctCollectionId = ParamUtil.getLong(
			actionRequest, "ctCollectionId");

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			themeDisplay.getCompanyId(), name);

		if ((ctCollection != null) &&
			(ctCollection.getCtCollectionId() != ctCollectionId)) {

			SessionErrors.add(actionRequest, "ctCollectionDuplicate");

			_portal.copyRequestParameters(actionRequest, actionResponse);

			actionResponse.setRenderParameter(
				"mvcPath", "/edit_ct_collection.jsp");

			return;
		}

		try {
			String description = ParamUtil.getString(
				actionRequest, "description");

			if (ctCollectionId > 0) {
				_ctCollectionLocalService.updateCTCollection(
					themeDisplay.getUserId(), ctCollectionId, name,
					description);
			}
			else {
				ctCollection = _ctCollectionLocalService.addCTCollection(
					themeDisplay.getUserId(), name, description);

				CTPreferences ctPreferences =
					_ctPreferencesLocalService.getCTPreferences(
						themeDisplay.getCompanyId(), themeDisplay.getUserId());

				ctPreferences.setCtCollectionId(
					ctCollection.getCtCollectionId());

				_ctPreferencesLocalService.updateCTPreferences(ctPreferences);
			}
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());

			_portal.copyRequestParameters(actionRequest, actionResponse);

			actionResponse.setRenderParameter(
				"mvcPath", "/edit_ct_collection.jsp");
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private Portal _portal;

}