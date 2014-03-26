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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.NoSuchBackgroundTaskException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Daniel Kocsis
 */
public class DeleteExportImportBackgroundTaskAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			deleteBackgroundTask(actionRequest);

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchBackgroundTaskException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.background_task.error");
			}
			else {
				throw e;
			}
		}
	}

	protected void deleteBackgroundTask(ActionRequest actionRequest)
		throws PortalException, SystemException {

		long backgroundTaskId = ParamUtil.getLong(
			actionRequest, "backgroundTaskId");

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.getBackgroundTask(backgroundTaskId);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long exportImportConfigurationId = MapUtil.getLong(
			taskContextMap, "exportImportConfigurationId", 0L);

		if (exportImportConfigurationId > 0) {
			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					getExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration.getStatus() ==
					WorkflowConstants.STATUS_DRAFT) {

				ExportImportConfigurationLocalServiceUtil.
					deleteExportImportConfiguration(exportImportConfiguration);
			}
		}

		BackgroundTaskLocalServiceUtil.deleteBackgroundTask(backgroundTaskId);
	}

}