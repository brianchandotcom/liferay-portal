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

package com.liferay.portlet.stagingbar.action;

import com.liferay.portal.LayoutBranchNameException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutBranchServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
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
public class EditLayoutBranchAction extends EditLayoutsAction {

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
				updateLayoutBranch(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteLayoutBranch(actionRequest, portletConfig);
			}

			if (SessionErrors.isEmpty(actionRequest)) {
				LiferayPortletConfig liferayPortletConfig =
					(LiferayPortletConfig)portletConfig;

				SessionMessages.add(
					actionRequest,
					liferayPortletConfig.getPortletId() +
						SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
					PortletKeys.STAGING_BAR);

				Map<String, String> data = new HashMap<String, String>();

				data.put("preventNotification", Boolean.TRUE.toString());

				SessionMessages.add(
					actionRequest,
					liferayPortletConfig.getPortletId() +
						SessionMessages.KEY_SUFFIX_REFRESH_PORTLET_DATA,
					data);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof LayoutBranchNameException) {
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
				renderRequest, "portlet.staging_bar.edit_layout_branch"));
	}

	protected void deleteLayoutBranch(
			ActionRequest actionRequest, PortletConfig portletConfig)
		throws Exception {

		long layoutBranchId = ParamUtil.getLong(
			actionRequest, "layoutBranchId");

		long currentLayoutBranchId = ParamUtil.getLong(
			actionRequest, "currentLayoutBranchId");

		LayoutBranchServiceUtil.deleteLayoutBranch(layoutBranchId);

		SessionMessages.add(actionRequest, "pageVariationDeleted");

		if (layoutBranchId == currentLayoutBranchId) {
			LiferayPortletConfig liferayPortletConfig =
				(LiferayPortletConfig)portletConfig;

			SessionMessages.add(
				actionRequest,
				liferayPortletConfig.getPortletId() +
					SessionMessages.KEY_SUFFIX_PORTLET_NOT_AJAXABLE);
		}
	}

	protected void updateLayoutBranch(ActionRequest actionRequest)
		throws Exception {

		long layoutBranchId = ParamUtil.getLong(
			actionRequest, "layoutBranchId");

		long layoutRevisionId = ParamUtil.getLong(
			actionRequest, "copyLayoutRevisionId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (layoutBranchId <= 0) {
			LayoutBranchServiceUtil.addLayoutBranch(
				layoutRevisionId, name, description, false, serviceContext);

			SessionMessages.add(actionRequest, "pageVariationAdded");
		}
		else {
			LayoutBranchServiceUtil.updateLayoutBranch(
				layoutBranchId, name, description, serviceContext);

			SessionMessages.add(actionRequest, "pageVariationUpdated");
		}
	}

}