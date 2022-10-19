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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/edit_ct_collection"
	},
	service = MVCActionCommand.class
)
public class EditCTCollectionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ctCollectionId = ParamUtil.getLong(
			actionRequest, "ctCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		try {
			if (ctCollectionId > 0) {
				_ctCollectionService.updateCTCollection(
					themeDisplay.getUserId(), ctCollectionId, name,
					description);
			}
			else {
				CTCollection ctCollection =
					_ctCollectionService.addCTCollection(
						themeDisplay.getCompanyId(), themeDisplay.getUserId(),
						name, description);

				CTPreferences ctPreferences =
					_ctPreferencesLocalService.getCTPreferences(
						themeDisplay.getCompanyId(), themeDisplay.getUserId());

				ctPreferences.setCtCollectionId(
					ctCollection.getCtCollectionId());
				ctPreferences.setPreviousCtCollectionId(
					CTCollectionThreadLocal.getCTCollectionId());

				_ctPreferencesLocalService.updateCTPreferences(ctPreferences);
			}
		}
		catch (PortalException portalException) {
			SessionErrors.add(actionRequest, portalException.getClass());

			_portal.copyRequestParameters(actionRequest, actionResponse);

			actionResponse.setRenderParameter(
				"mvcPath", "/edit_ct_collection.jsp");
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private Portal _portal;

}