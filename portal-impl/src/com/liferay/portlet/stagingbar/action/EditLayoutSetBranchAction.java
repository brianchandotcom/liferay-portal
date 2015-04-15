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

package com.liferay.portlet.stagingbar.action;

import com.liferay.portal.LayoutSetBranchNameException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.LayoutSetBranchConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutSetBranchServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.layoutsadmin.action.EditLayoutsAction;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class EditLayoutSetBranchAction extends EditLayoutsAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			checkPermissions(actionRequest);
		}
		catch (PrincipalException pe) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateLayoutSetBranch(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteLayoutSetBranch(actionRequest);
			}
			else if (cmd.equals("merge_layout_set_branch")) {
				mergeLayoutSetBranch(actionRequest);
			}

			if (SessionErrors.isEmpty(actionRequest)) {
				SessionMessages.add(
					actionRequest,
					PortalUtil.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
					PortletKeys.STAGING_BAR);

				Map<String, String> data = new HashMap<>();

				data.put("preventNotification", Boolean.TRUE.toString());

				SessionMessages.add(
					actionRequest,
					PortalUtil.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_REFRESH_PORTLET_DATA,
					data);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof LayoutSetBranchNameException) {
				SessionErrors.add(actionRequest, e.getClass(), e);

				sendRedirect(actionRequest, actionResponse);
			}
			else if (e instanceof PrincipalException ||
					 e instanceof SystemException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.staging_bar.error");
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

		try {
			checkPermissions(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return actionMapping.findForward("portlet.staging_bar.error");
		}

		try {
			getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.staging_bar.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.staging_bar.edit_layout_set_branch"));
	}

	protected void deleteLayoutSetBranch(ActionRequest actionRequest)
		throws Exception {

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		long currentLayoutBranchId = ParamUtil.getLong(
			actionRequest, "currentLayoutBranchId");

		if (layoutSetBranchId == currentLayoutBranchId) {
			SessionMessages.add(
				actionRequest,
				PortalUtil.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_PORTLET_NOT_AJAXABLE);
		}

		LayoutSetBranchServiceUtil.deleteLayoutSetBranch(layoutSetBranchId);

		SessionMessages.add(actionRequest, "sitePageVariationDeleted");
	}

	protected void mergeLayoutSetBranch(ActionRequest actionRequest)
		throws Exception {

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		long mergeLayoutSetBranchId = ParamUtil.getLong(
			actionRequest, "mergeLayoutSetBranchId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		LayoutSetBranchServiceUtil.mergeLayoutSetBranch(
			layoutSetBranchId, mergeLayoutSetBranchId, serviceContext);

		SessionMessages.add(actionRequest, "sitePageVariationMerged");
	}

	protected void updateLayoutSetBranch(ActionRequest actionRequest)
		throws Exception {

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		long copyLayoutSetBranchId = ParamUtil.getLong(
			actionRequest, "copyLayoutSetBranchId",
			LayoutSetBranchConstants.ALL_BRANCHES);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (layoutSetBranchId <= 0) {
			LayoutSetBranchServiceUtil.addLayoutSetBranch(
				groupId, name, description, false, copyLayoutSetBranchId,
				serviceContext);

			SessionMessages.add(actionRequest, "sitePageVariationAdded");
		}
		else {
			LayoutSetBranchServiceUtil.updateLayoutSetBranch(
				groupId, layoutSetBranchId, name, description, serviceContext);

			SessionMessages.add(actionRequest, "sitePageVariationUpdated");
		}
	}

}