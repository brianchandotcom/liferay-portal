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

package com.liferay.portlet.osgiadmin.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.osgiadmin.OSGiException;
import com.liferay.portlet.osgiadmin.service.OSGiServiceUtil;

import java.io.File;
import java.io.FileInputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Raymond Augé
 */
public class EditBundleAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			UploadPortletRequest uploadRequest =
				PortalUtil.getUploadPortletRequest(actionRequest);

			String cmd = ParamUtil.getString(uploadRequest, Constants.CMD);
			File file = uploadRequest.getFile("importBundle");
			String location = ParamUtil.getString(uploadRequest, "location");

			if (cmd.equals("install-from-upload")) {
				if (Validator.isNull(location)) {
					location = uploadRequest.getFullFileName("importBundle");
				}

				if (file == null || !file.exists()) {
					throw new OSGiException("file-does-not-exist");
				}

				OSGiServiceUtil.addBundle(location, new FileInputStream(file));
			}
			else if (cmd.equals("install-from-remote-location")) {
				OSGiServiceUtil.addBundle(location);
			}
			else if (cmd.equals("update-from-upload")) {
				long bundleId = ParamUtil.getLong(uploadRequest, "bundleId");

				if (file == null || !file.exists()) {
					throw new OSGiException("file-does-not-exist");
				}

				OSGiServiceUtil.updateBundle(
					bundleId, new FileInputStream(file));
			}
			else if (cmd.equals("update-from-remote-location")) {
				long bundleId = ParamUtil.getLong(uploadRequest, "bundleId");

				OSGiServiceUtil.updateBundle(bundleId);
			}
			else if (cmd.equals("uninstall")) {
				long bundleId = ParamUtil.getLong(uploadRequest, "bundleId");

				OSGiServiceUtil.uninstallBundle(bundleId);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if ((e instanceof OSGiException) ||
				(e instanceof PrincipalException)) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return mapping.findForward(
			getForward(renderRequest, "portlet.osgiadmin.edit_bundle"));
	}

}