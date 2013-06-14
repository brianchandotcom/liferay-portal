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

import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.LayoutPrototypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.RemoteExportException;
import com.liferay.portal.RemoteOptionsException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.sites.action.ActionUtil;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 * @author Levente Hudák
 */
public class PublishLayoutsAction extends EditLayoutsAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			checkPermissions(actionRequest);
		}
		catch (PrincipalException pe) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");
		String closeRedirect = ParamUtil.getString(
			actionRequest, "closeRedirect");

		try {
			if (Validator.isNotNull(cmd)) {
				if (cmd.equals("copy_from_live")) {
					StagingUtil.copyFromLive(actionRequest);
				}
				else if (cmd.equals("publish_to_live")) {
					StagingUtil.publishToLive(actionRequest);
				}
				else if (cmd.equals("publish_to_remote")) {
					StagingUtil.publishToRemote(actionRequest);
				}
				else if (cmd.equals("schedule_copy_from_live")) {
					StagingUtil.scheduleCopyFromLive(actionRequest);
				}
				else if (cmd.equals("schedule_publish_to_live")) {
					StagingUtil.schedulePublishToLive(actionRequest);
				}
				else if (cmd.equals("schedule_publish_to_remote")) {
					StagingUtil.schedulePublishToRemote(actionRequest);
				}
				else if (cmd.equals("unschedule_copy_from_live")) {
					StagingUtil.unscheduleCopyFromLive(actionRequest);
				}
				else if (cmd.equals("unschedule_publish_to_live")) {
					StagingUtil.unschedulePublishToLive(actionRequest);
				}
				else if (cmd.equals("unschedule_publish_to_remote")) {
					StagingUtil.unschedulePublishToRemote(actionRequest);
				}

				sendRedirect(
					portletConfig, actionRequest, actionResponse, redirect,
					closeRedirect);
			}
			else {
				long groupId = ParamUtil.getLong(actionRequest, "groupId");
				boolean privateLayout = ParamUtil.getBoolean(
					actionRequest, "privateLayout");

				DateRange dateRange = ExportImportHelperUtil.getDateRange(
					actionRequest, groupId, privateLayout, 0, null);

				Date startDate = dateRange.getStartDate();

				if (startDate != null) {
					actionResponse.setRenderParameter(
						"startDate", String.valueOf(startDate.getTime()));
				}

				Date endDate = dateRange.getEndDate();

				if (endDate != null) {
					actionResponse.setRenderParameter(
						"endDate", String.valueOf(endDate.getTime()));
				}
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.layouts_admin.error");
			}
			else if (e instanceof DuplicateLockException ||
					 e instanceof LayoutPrototypeException ||
					 e instanceof RemoteExportException ||
					 e instanceof RemoteOptionsException ||
					 e instanceof SystemException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				redirect = ParamUtil.getString(actionRequest, "pagesRedirect");

				sendRedirect(
					portletConfig, actionRequest, actionResponse, redirect,
					closeRedirect);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.publish_layouts"));
	}

}