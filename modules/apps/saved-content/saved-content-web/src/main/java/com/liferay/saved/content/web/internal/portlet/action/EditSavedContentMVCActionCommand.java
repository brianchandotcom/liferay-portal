/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
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

	private JSONObject _updateSavedContentEntry(ActionRequest actionRequest) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if (Validator.isBlank(className) || (classPK == 0)) {
			if (_log.isDebugEnabled()) {
				_log.debug("className or classPK are not correct");
			}

			jsonObject.put(
				"errorMessage",
				_language.get(
					_portal.getHttpServletRequest(actionRequest),
					"an-unexpected-error-occurred"));

			return jsonObject;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();

		try {
			SavedContentEntry savedContentEntry =
				_savedContentEntryService.fetchSavedContentEntry(
					themeDisplay.getScopeGroupId(), userId, className, classPK);

			if (savedContentEntry == null) {
				_savedContentEntryService.addSavedContentEntry(
					themeDisplay.getScopeGroupId(), userId, className, classPK,
					ServiceContextFactory.getInstance(
						SavedContentEntry.class.getName(), actionRequest));
				jsonObject.put("saved", Boolean.TRUE);

				return jsonObject;
			}

			_savedContentEntryService.deleteSavedContentEntry(
				savedContentEntry);

			jsonObject.put("saved", Boolean.FALSE);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			jsonObject.put(
				"errorMessage",
				_language.get(
					_portal.getHttpServletRequest(actionRequest),
					"an-unexpected-error-occurred"));
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditSavedContentMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private SavedContentEntryService _savedContentEntryService;

}