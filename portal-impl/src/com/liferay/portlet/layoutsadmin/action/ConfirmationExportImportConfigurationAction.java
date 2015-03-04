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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.sites.action.ActionUtil;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Levente Hudák
 */
public class ConfirmationExportImportConfigurationAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			long exportImportConfigurationId = ParamUtil.getLong(
				renderRequest, "exportImportConfigurationId");

			if (exportImportConfigurationId <= 0) {
				createExportImportConfiguration(renderRequest);
			}

			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.confirmation"));
	}

	protected void createExportImportConfiguration(RenderRequest renderRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ExportImportConfiguration exportImportConfiguration = null;

		long sourceGroupId = ParamUtil.getLong(renderRequest, "sourceGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			renderRequest, "privateLayout");

		boolean localPublishing = ParamUtil.getBoolean(
			renderRequest, "localPublishing");

		if (localPublishing) {
			long targetGroupId = ParamUtil.getLong(
				renderRequest, "targetGroupId");

			exportImportConfiguration =
				ExportImportConfigurationFactory.
					buildDefaultLocalPublishingExportImportConfiguration(
						themeDisplay.getUser(), sourceGroupId, targetGroupId,
						privateLayout);
		}
		else {
			String remoteAddress = ParamUtil.getString(
				renderRequest, "remoteAddress");
			int remotePort = ParamUtil.getInteger(renderRequest, "remotePort");
			String remotePathContext = ParamUtil.getString(
				renderRequest, "remotePathContext");
			boolean secureConnection = ParamUtil.getBoolean(
				renderRequest, "secureConnection");
			long remoteGroupId = ParamUtil.getLong(
				renderRequest, "remoteGroupId");

			exportImportConfiguration =
				ExportImportConfigurationFactory.
					buildDefaultRemotePublishingExportImportConfiguration(
						themeDisplay.getUser(), sourceGroupId, privateLayout,
						remoteAddress, remotePort, remotePathContext,
						secureConnection, remoteGroupId);
		}

		renderRequest.setAttribute(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());
	}

}