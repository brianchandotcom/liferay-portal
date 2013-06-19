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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.setup.SetupWizardUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Manuel de la Peña
 * @author Brian Wing Shun Chan
 */
public class SetupWizardAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!PropsValues.SETUP_WIZARD_ENABLED) {
			response.sendRedirect(themeDisplay.getPathMain());
		}

		String cmd = ParamUtil.getString(request, Constants.CMD);

		try {
			if (Validator.isNull(cmd)) {
				return actionMapping.findForward("portal.setup_wizard");
			}
			else if (cmd.equals(Constants.TRANSLATE)) {
				SetupWizardUtil.updateLanguage(request, response);

				return actionMapping.findForward("portal.setup_wizard");
			}
			else if (cmd.equals(Constants.TEST)) {
				testDatabase(request, response);

				return null;
			}
			else if (cmd.equals(Constants.UPDATE)) {
				SetupWizardUtil.updateSetup(request, response);
			}

			response.sendRedirect(
				themeDisplay.getPathMain() + "/portal/setup_wizard");

			return null;
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(request, e.getClass());

				return actionMapping.findForward("portal.setup_wizard");
			}
			else {
				PortalUtil.sendError(e, request, response);

				return null;
			}
		}
	}

	protected void putMessage(
		HttpServletRequest request, JSONObject jsonObject, String key,
		Object... arguments) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String message = themeDisplay.translate(key, arguments);

		jsonObject.put("message", message);
	}

	protected void testDatabase(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			SetupWizardUtil.testDatabase(request);

			jsonObject.put("success", true);

			putMessage(
				request, jsonObject,
				"database-connection-was-established-sucessfully");
		}
		catch (ClassNotFoundException cnfe) {
			putMessage(
				request, jsonObject, "database-driver-x-is-not-present",
				cnfe.getLocalizedMessage());
		}
		catch (SQLException sqle) {
			putMessage(
				request, jsonObject,
				"database-connection-could-not-be-established");
		}

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setHeader(
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);

		ServletResponseUtil.write(response, jsonObject.toString());
	}

}