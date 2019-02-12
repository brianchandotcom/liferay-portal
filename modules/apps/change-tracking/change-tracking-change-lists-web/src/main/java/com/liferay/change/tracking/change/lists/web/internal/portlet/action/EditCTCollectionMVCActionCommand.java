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

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

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
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ctCollectionId = ParamUtil.getLong(
		actionRequest, "ctCollectionId");
		String description = ParamUtil.getString(actionRequest, "description");
		String name = ParamUtil.getString(actionRequest, "name");

		if (ctCollectionId > 0) {
			_updateCTCollection(ctCollectionId, name, description);
		}
		else {
			_addCTCollection(themeDisplay.getUserId(), name, description);
		}
	}

	private void _addCTCollection(
		long userId, String name, String description) {

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(userId, name, description);

		ctCollectionOptional.ifPresent(
			ctCollection ->
				_ctEngineManager.checkoutCTCollection(
					userId, ctCollection.getCtCollectionId()));
	}

	private void _updateCTCollection(
		long ctCollectionId, String name, String description) {

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(ctCollectionId);

		ctCollectionOptional.ifPresent(
			ctCollection -> {
				ctCollection.setName(name);
				ctCollection.setDescription(description);

				_ctCollectionLocalService.updateCTCollection(ctCollection);
			});
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

}