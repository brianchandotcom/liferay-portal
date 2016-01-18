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

package com.liferay.exportimport.web.portlet.action;

import com.liferay.exportimport.web.constants.ExportImportPortletKeys;
import com.liferay.portal.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationFactory;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
		"mvc.command.name=confirmation"
	},
	service = MVCRenderCommand.class
)
public class ConfirmationExportImportConfigurationMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

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

				return "/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/confirmation.jsp";
	}

	protected void createExportImportConfiguration(RenderRequest renderRequest)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration = null;

		boolean localPublishing = ParamUtil.getBoolean(
			renderRequest, "localPublishing");

		if (localPublishing) {
			exportImportConfiguration =
				ExportImportConfigurationFactory.
					buildDefaultLocalPublishingExportImportConfiguration(
						renderRequest);
		}
		else {
			exportImportConfiguration =
				ExportImportConfigurationFactory.
					buildDefaultRemotePublishingExportImportConfiguration(
						renderRequest);
		}

		renderRequest.setAttribute(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());
	}

}