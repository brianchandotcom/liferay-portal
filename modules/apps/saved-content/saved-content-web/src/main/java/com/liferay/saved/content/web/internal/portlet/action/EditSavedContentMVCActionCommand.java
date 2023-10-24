/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saved.content.constants.MySavedContentPortletKeys;
import com.liferay.saved.content.model.SavedContentEntry;
import com.liferay.saved.content.service.SavedContentEntryService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = {
		"javax.portlet.name=" + MySavedContentPortletKeys.MY_SAVED_CONTENT,
		"mvc.command.name=/saved_content/edit_saved_content"
	},
	service = MVCActionCommand.class
)
public class EditSavedContentMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			_updateSavedContentEntry(actionRequest));
	}

	private String _updateSavedContentEntry(ActionRequest actionRequest)
		throws Exception {

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if (Validator.isNull(className) || (classPK == 0)) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SavedContentEntry savedContentEntry =
			_savedContentEntryService.fetchSavedContentEntry(
				themeDisplay.getScopeGroupId(), className, classPK);

		if (savedContentEntry == null) {
			_savedContentEntryService.addSavedContentEntry(
				themeDisplay.getScopeGroupId(), className, classPK);

			return Boolean.TRUE.toString();
		}

		_savedContentEntryService.deleteSavedContentEntry(savedContentEntry);

		return Boolean.FALSE.toString();
	}

	@Reference
	private SavedContentEntryService _savedContentEntryService;

}