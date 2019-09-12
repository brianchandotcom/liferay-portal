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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.GroupInheritContentException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredLayoutException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.exception.RequiredSegmentsExperienceException;
import com.liferay.sites.kernel.util.SitesUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/delete_layout"
	},
	service = MVCActionCommand.class
)
public class DeleteLayoutMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteLayout(
			long selPlid, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(selPlid);

		if (layout == null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = layout.getGroup();

		if (!SitesUtil.isLayoutDeleteable(layout)) {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			SessionMessages.add(
				actionRequest,
				portletDisplay.getId() +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);

			throw new GroupInheritContentException();
		}

		if (group.isStagingGroup() &&
			!GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), group,
				ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), group,
				ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException.MustHavePermission(
				themeDisplay.getPermissionChecker(), Group.class.getName(),
				group.getGroupId(), ActionKeys.MANAGE_STAGING,
				ActionKeys.PUBLISH_STAGING);
		}

		if (LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout,
				ActionKeys.DELETE)) {

			LayoutType layoutType = layout.getLayoutType();

			EventsProcessorUtil.process(
				PropsKeys.LAYOUT_CONFIGURATION_ACTION_DELETE,
				layoutType.getConfigurationActionDelete(),
				_portal.getHttpServletRequest(actionRequest),
				_portal.getHttpServletResponse(actionResponse));
		}

		if (group.isGuest() && !layout.isPrivateLayout() &&
			layout.isRootLayout()) {

			int count = _layoutLocalService.getLayoutsCount(
				group, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (count == 1) {
				throw new RequiredLayoutException(
					RequiredLayoutException.AT_LEAST_ONE);
			}
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		serviceContext.setAttribute("layoutSetBranchId", layoutSetBranchId);

		try {
			_layoutService.deleteLayout(selPlid, serviceContext);
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof
					RequiredSegmentsExperienceException.
						MustNotDeleteSegmentsExperienceReferencedBySegmentsExperiments) {

				SessionErrors.add(actionRequest, cause.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long selPlid = ParamUtil.getLong(actionRequest, "selPlid");

		long[] selPlids = ParamUtil.getLongValues(actionRequest, "rowIds");

		if ((selPlid > 0) && ArrayUtil.isEmpty(selPlids)) {
			selPlids = new long[] {selPlid};
		}

		for (long curSelPlid : selPlids) {
			deleteLayout(curSelPlid, actionRequest, actionResponse);
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}