/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.admin.util.OmniadminUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Amos Fong
 */
public class UpdateLicenseAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		if (_isValidRequest(request)) {
			return mapping.findForward("portal.license");
		}
		else {
			response.sendRedirect(
				PortalUtil.getPathContext() + "/c/portal/layout");

			return null;
		}
	}

	private boolean _isOmniAdmin(HttpServletRequest request) {
		User user = null;

		try {
			user = PortalUtil.getUser(request);
		}
		catch (Exception e) {
		}

		if ((user != null) && OmniadminUtil.isOmniadmin(user.getUserId())) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isValidRequest(HttpServletRequest request) {
		if (_isOmniAdmin(request)) {
			return true;
		}
		else {
			return false;
		}
	}

}