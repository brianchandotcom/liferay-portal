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

import com.liferay.change.tracking.exception.CTLocalizedException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/undo_ct_collection"
	},
	service = MVCActionCommand.class
)
public class UndoCTCollectionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ctCollectionId = ParamUtil.getLong(
			actionRequest, "ctCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		if (Validator.isNull(name)) {
			CTCollection ctCollection =
				_ctCollectionLocalService.getCTCollection(ctCollectionId);

			name = StringBundler.concat(
				_language.get(themeDisplay.getLocale(), "revert"), " \"",
				ctCollection.getName(), "\"");
		}

		try {
			CTCollection ctCollection = _ctCollectionService.undoCTCollection(
				ctCollectionId, themeDisplay.getUserId(), name, description);

			PortletURL redirectURL = PortletURLFactoryUtil.create(
				actionRequest, CTPortletKeys.PUBLICATIONS,
				PortletRequest.RENDER_PHASE);

			String publishTime = ParamUtil.get(
				actionRequest, "publishTime", "now");

			if (publishTime.equals("now")) {
				redirectURL.setParameter(
					"mvcRenderCommandName", "/change_tracking/view_conflicts");
			}
			else {
				redirectURL.setParameter(
					"mvcRenderCommandName", "/change_tracking/view_changes");
			}

			redirectURL.setParameter(
				"ctCollectionId",
				String.valueOf(ctCollection.getCtCollectionId()));

			sendRedirect(actionRequest, actionResponse, redirectURL.toString());
		}
		catch (CTLocalizedException ctLocalizedException) {
			_log.error(ctLocalizedException, ctLocalizedException);

			SessionErrors.add(
				actionRequest, CTLocalizedException.class.getName(),
				ctLocalizedException);

			hideDefaultErrorMessage(actionRequest);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UndoCTCollectionMVCActionCommand.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private Language _language;

}