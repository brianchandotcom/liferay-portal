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

package com.liferay.portlet.myplaces.action;

import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class ViewAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String privateLayoutParam = actionRequest.getParameter("privateLayout");

		List<Layout> layouts = getLayouts(groupId, privateLayoutParam);

		if (layouts.isEmpty()) {
			SessionErrors.add(
				actionRequest, NoSuchLayoutSetException.class.getName(),
				new NoSuchLayoutSetException(
					"{groupId=" + groupId + ",privateLayout=" +
						privateLayoutParam + "}"));
		}

		String redirect = getRedirect(
			themeDisplay, layouts, groupId, privateLayoutParam);

		if (Validator.isNull(redirect)) {
			redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return actionMapping.findForward("portlet.my_sites.view");
	}

	@Override
	public ActionForward strutsExecute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(request, "groupId");
		String privateLayoutParam = request.getParameter("privateLayout");

		List<Layout> layouts = getLayouts(groupId, privateLayoutParam);

		if (layouts.isEmpty()) {
			SessionErrors.add(
				request, NoSuchLayoutSetException.class.getName(),
				new NoSuchLayoutSetException(
					"{groupId=" + groupId + ",privateLayout=" +
						privateLayoutParam + "}"));
		}

		String redirect = getRedirect(
			themeDisplay, layouts, groupId, privateLayoutParam);

		if (Validator.isNull(redirect)) {
			redirect = ParamUtil.getString(request, "redirect");
		}

		response.sendRedirect(redirect);

		return null;
	}

	protected List<Layout> getLayouts(long groupId, boolean privateLayout)
		throws Exception {

		return LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	protected List<Layout> getLayouts(long groupId, String privateLayoutParam)
		throws Exception {

		List<Layout> layouts = null;

		boolean privateLayout = false;

		if (Validator.isNull(privateLayoutParam)) {
			layouts = getLayouts(groupId, false);

			if (layouts.isEmpty()) {
				layouts = getLayouts(groupId, true);
			}
		}
		else {
			privateLayout = GetterUtil.getBoolean(privateLayoutParam);

			layouts = getLayouts(groupId, privateLayout);
		}

		return layouts;
	}

	protected String getRedirect(
			ThemeDisplay themeDisplay, List<Layout> layouts, long groupId,
			String privateLayoutParam)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		for (Layout layout : layouts) {
			if (!layout.isHidden() &&
				LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.VIEW)) {

				return PortalUtil.getCanonicalURL(
					null, themeDisplay, layout, true);
			}
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return PortalUtil.getGroupFriendlyURL(
			group, GetterUtil.getBoolean(privateLayoutParam), themeDisplay);
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}