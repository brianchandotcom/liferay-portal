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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Eudaldo Alonso
 */
public class EditLayoutRevisionAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("delete_layout_revision")) {
				deleteLayoutRevision(actionRequest);
			}
			else if (cmd.equals("update_layout_revision")) {
				updateLayoutRevision(actionRequest);
			}

			MultiSessionMessages.add(
				actionRequest,
				PortalUtil.getPortletId(actionRequest) + "requestProcessed");

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.export_import.error");
			}
			else if (e instanceof SystemException) {
				SessionErrors.add(actionRequest, e.getClass(), e);

				sendRedirect(actionRequest, actionResponse);
			}
			else {
				throw e;
			}
		}
	}

	protected void deleteLayoutRevision(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		long layoutRevisionId = ParamUtil.getLong(
			actionRequest, "layoutRevisionId");

		LayoutRevision layoutRevision =
			LayoutRevisionLocalServiceUtil.getLayoutRevision(layoutRevisionId);

		LayoutRevisionLocalServiceUtil.deleteLayoutRevision(layoutRevision);

		boolean updateRecentLayoutRevisionId = ParamUtil.getBoolean(
			actionRequest, "updateRecentLayoutRevisionId");

		if (updateRecentLayoutRevisionId) {
			StagingUtil.setRecentLayoutRevisionId(
				request, layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid(),
				layoutRevision.getParentLayoutRevisionId());
		}
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void updateLayoutRevision(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long layoutRevisionId = ParamUtil.getLong(
			actionRequest, "layoutRevisionId");

		LayoutRevision layoutRevision =
			LayoutRevisionLocalServiceUtil.getLayoutRevision(layoutRevisionId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		LayoutRevision enableLayoutRevision =
			LayoutRevisionLocalServiceUtil.updateLayoutRevision(
				serviceContext.getUserId(), layoutRevisionId,
				layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
				layoutRevision.getTitle(), layoutRevision.getDescription(),
				layoutRevision.getKeywords(), layoutRevision.getRobots(),
				layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
				layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
				layoutRevision.getColorSchemeId(),
				layoutRevision.getWapThemeId(),
				layoutRevision.getWapColorSchemeId(), layoutRevision.getCss(),
				serviceContext);

		if (layoutRevision.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) {
			StagingUtil.setRecentLayoutRevisionId(
				themeDisplay.getUser(), layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid(), layoutRevision.getLayoutRevisionId());

			return;
		}

		LayoutRevision lastLayoutRevision =
			LayoutRevisionLocalServiceUtil.fetchLastLayoutRevision(
				enableLayoutRevision.getPlid(), true);

		if (lastLayoutRevision != null) {
			LayoutRevision newLayoutRevision =
				LayoutRevisionLocalServiceUtil.addLayoutRevision(
					serviceContext.getUserId(),
					layoutRevision.getLayoutSetBranchId(),
					layoutRevision.getLayoutBranchId(),
					enableLayoutRevision.getLayoutRevisionId(), false,
					layoutRevision.getPlid(),
					lastLayoutRevision.getLayoutRevisionId(),
					lastLayoutRevision.isPrivateLayout(),
					lastLayoutRevision.getName(), lastLayoutRevision.getTitle(),
					lastLayoutRevision.getDescription(),
					lastLayoutRevision.getKeywords(),
					lastLayoutRevision.getRobots(),
					lastLayoutRevision.getTypeSettings(),
					lastLayoutRevision.isIconImage(),
					lastLayoutRevision.getIconImageId(),
					lastLayoutRevision.getThemeId(),
					lastLayoutRevision.getColorSchemeId(),
					lastLayoutRevision.getWapThemeId(),
					lastLayoutRevision.getWapColorSchemeId(),
					lastLayoutRevision.getCss(), serviceContext);

			StagingUtil.setRecentLayoutRevisionId(
				themeDisplay.getUser(),
				newLayoutRevision.getLayoutSetBranchId(),
				newLayoutRevision.getPlid(),
				newLayoutRevision.getLayoutRevisionId());
		}
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}