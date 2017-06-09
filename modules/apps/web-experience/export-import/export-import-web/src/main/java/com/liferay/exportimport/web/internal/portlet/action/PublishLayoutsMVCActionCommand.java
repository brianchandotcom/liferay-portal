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

package com.liferay.exportimport.web.internal.portlet.action;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.exception.RemoteExportException;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.exception.LayoutPrototypeException;
import com.liferay.portal.kernel.exception.RemoteOptionsException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.RemoteAuthException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.ui.util.SessionTreeJSClicks;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ExportImportPortletKeys.EXPORT_IMPORT,
		"mvc.command.name=publishLayouts"
	},
	service = MVCActionCommand.class
)
public class PublishLayoutsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNull(cmd)) {
			SessionMessages.add(
				actionRequest,
				_portal.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_FORCE_SEND_REDIRECT);

			hideDefaultSuccessMessage(actionRequest);

			return;
		}

		try {
			if (cmd.equals("copy_from_live")) {
				setLayoutIdMap(actionRequest);

				_staging.copyFromLive(actionRequest);
			}
			else if (cmd.equals(Constants.PUBLISH_TO_LIVE)) {
				hideDefaultSuccessMessage(actionRequest);

				setLayoutIdMap(actionRequest);

				setRedirect(
					actionRequest, actionResponse,
					_staging.publishToLive(actionRequest));
			}
			else if (cmd.equals(Constants.PUBLISH_TO_REMOTE)) {
				hideDefaultSuccessMessage(actionRequest);

				setLayoutIdMap(actionRequest);

				setRedirect(
					actionRequest, actionResponse,
					_staging.publishToRemote(actionRequest));
			}
			else if (cmd.equals("schedule_copy_from_live")) {
				setLayoutIdMap(actionRequest);

				_staging.scheduleCopyFromLive(actionRequest);
			}
			else if (cmd.equals("schedule_publish_to_live")) {
				setLayoutIdMap(actionRequest);

				_staging.schedulePublishToLive(actionRequest);
			}
			else if (cmd.equals("schedule_publish_to_remote")) {
				setLayoutIdMap(actionRequest);

				_staging.schedulePublishToRemote(actionRequest);
			}
			else if (cmd.equals("unschedule_copy_from_live")) {
				_staging.unscheduleCopyFromLive(actionRequest);
			}
			else if (cmd.equals("unschedule_publish_to_live")) {
				_staging.unschedulePublishToLive(actionRequest);
			}
			else if (cmd.equals("unschedule_publish_to_remote")) {
				_staging.unschedulePublishToRemote(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof AuthException ||
					 e instanceof DuplicateLockException ||
					 e instanceof LayoutPrototypeException ||
					 e instanceof RemoteAuthException ||
					 e instanceof RemoteExportException ||
					 e instanceof RemoteOptionsException ||
					 e instanceof SystemException) {

				if (e instanceof RemoteAuthException) {
					SessionErrors.add(actionRequest, AuthException.class, e);
				}
				else {
					SessionErrors.add(actionRequest, e.getClass(), e);
				}

				sendRedirect(actionRequest, actionResponse);
			}
			else {
				throw e;
			}
		}
	}

	protected void setLayoutIdMap(ActionRequest actionRequest) {
		HttpServletRequest portletRequest = _portal.getHttpServletRequest(
			actionRequest);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");

		String treeId = ParamUtil.getString(actionRequest, "treeId");

		String openNodes = SessionTreeJSClicks.getOpenNodes(
			portletRequest, treeId + "SelectedNode");

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				groupId, privateLayout, openNodes);

		actionRequest.setAttribute("layoutIdMap", selectedLayoutsJSON);
	}

	protected void setRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		long backgroundTaskId) {

		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)actionResponse;

		PortletURL renderURL = liferayPortletResponse.createRenderURL();

		renderURL.setParameter("mvcPath", "/view_export_import.jsp");
		renderURL.setParameter(
			"backgroundTaskId", String.valueOf(backgroundTaskId));

		actionRequest.setAttribute(WebKeys.REDIRECT, renderURL.toString());
	}

	@Reference
	private Portal _portal;

	@Reference
	private Staging _staging;

}