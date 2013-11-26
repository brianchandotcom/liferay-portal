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

package com.liferay.portlet.portalsettings.action;

import com.liferay.portal.ImageTypeException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class EditCompanyLogoAction extends EditLogoAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (cmd.equals(Constants.ADD_TEMP)) {
				addTempImageFile(actionRequest);
			}
			else {
				saveTempImageFile(actionRequest);

				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.portal_settings.error");
			}
			else if (e instanceof FileSizeException ||
					 e instanceof ImageTypeException) {

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.putException(e);

				writeJSON(actionRequest, actionResponse, jsonObject);
			}
			else if (e instanceof NoSuchFileException ||
					 e instanceof UploadException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.portal_settings.edit_company_logo"));
	}

	@Override
	protected String getTempImageFileName(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return String.valueOf(themeDisplay.getCompanyId());
	}

	@Override
	protected void saveTempImageFile(
			PortletRequest portletRequest, byte[] bytes)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = CompanyServiceUtil.updateLogo(
			themeDisplay.getCompanyId(), bytes);

		themeDisplay.setCompany(company);
	}

}