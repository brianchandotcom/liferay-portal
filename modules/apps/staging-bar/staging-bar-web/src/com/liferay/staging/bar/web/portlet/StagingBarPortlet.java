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

package com.liferay.staging.bar.web.portlet;

import com.liferay.portal.LayoutBranchNameException;
import com.liferay.portal.LayoutSetBranchNameException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutSetBranchConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutBranchService;
import com.liferay.portal.service.LayoutRevisionLocalService;
import com.liferay.portal.service.LayoutSetBranchLocalService;
import com.liferay.portal.service.LayoutSetBranchService;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.staging.StagingUtil;
import com.liferay.staging.bar.web.portlet.constants.StagingBarPortletKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Levente Hudák
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-staging-bar",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/js/staging.js",
		"com.liferay.portlet.header-portlet-javascript=/js/staging_branch.js",
		"com.liferay.portlet.header-portlet-javascript=/js/staging_version.js",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.show-portlet-access-denied=false",
		"com.liferay.portlet.show-portlet-inactive=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=Staging Bar",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + StagingBarPortletKeys.STAGING_BAR,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class StagingBarPortlet extends MVCPortlet {

	public void deleteLayoutBranch(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutBranchId = ParamUtil.getLong(
			actionRequest, "layoutBranchId");

		long currentLayoutBranchId = ParamUtil.getLong(
			actionRequest, "currentLayoutBranchId");

		_layoutBranchService.deleteLayoutBranch(layoutBranchId);

		SessionMessages.add(actionRequest, "pageVariationDeleted");

		if (layoutBranchId == currentLayoutBranchId) {
			SessionMessages.add(
				actionRequest,
				PortalUtil.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_PORTLET_NOT_AJAXABLE);
		}

		addLayoutBranchSessionMessages(actionRequest, actionResponse);
	}

	public void deleteLayoutRevision(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		long layoutRevisionId = ParamUtil.getLong(
			actionRequest, "layoutRevisionId");

		LayoutRevision layoutRevision =
			_layoutRevisionLocalService.getLayoutRevision(layoutRevisionId);

		_layoutRevisionLocalService.deleteLayoutRevision(layoutRevision);

		boolean updateRecentLayoutRevisionId = ParamUtil.getBoolean(
			actionRequest, "updateRecentLayoutRevisionId");

		if (updateRecentLayoutRevisionId) {
			StagingUtil.setRecentLayoutRevisionId(
				request, layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid(),
				layoutRevision.getParentLayoutRevisionId());
		}

		addLayoutRevisionSessionMessages(actionRequest, actionResponse);
	}

	public void deleteLayoutSetBranch(
			ActionRequest actionRequest, ActionResponse actionResponse)
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

		_layoutSetBranchService.deleteLayoutSetBranch(layoutSetBranchId);

		SessionMessages.add(actionRequest, "sitePageVariationDeleted");

		addLayoutBranchSessionMessages(actionRequest, actionResponse);
	}

	public void mergeLayoutSetBranch(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		long mergeLayoutSetBranchId = ParamUtil.getLong(
			actionRequest, "mergeLayoutSetBranchId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_layoutSetBranchService.mergeLayoutSetBranch(
			layoutSetBranchId, mergeLayoutSetBranchId, serviceContext);

		SessionMessages.add(actionRequest, "sitePageVariationMerged");

		addLayoutBranchSessionMessages(actionRequest, actionResponse);
	}

	public void selectLayoutBranch(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		long layoutBranchId = ParamUtil.getLong(
			actionRequest, "layoutBranchId");

		StagingUtil.setRecentLayoutBranchId(
			request, layoutSetBranchId, themeDisplay.getPlid(), layoutBranchId);

		addLayoutBranchSessionMessages(actionRequest, actionResponse);
	}

	public void selectLayoutSetBranch(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		// Ensure layout set branch exists

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.getLayoutSetBranch(layoutSetBranchId);

		StagingUtil.setRecentLayoutSetBranchId(
			request, layoutSet.getLayoutSetId(),
			layoutSetBranch.getLayoutSetBranchId());

		addLayoutBranchSessionMessages(actionRequest, actionResponse);
	}

	public void updateLayoutBranch(
			ActionRequest actionRequest, ActionResponse actionResponse)
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
			_layoutBranchService.addLayoutBranch(
				layoutRevisionId, name, description, false, serviceContext);

			SessionMessages.add(actionRequest, "pageVariationAdded");
		}
		else {
			_layoutBranchService.updateLayoutBranch(
				layoutBranchId, name, description, serviceContext);

			SessionMessages.add(actionRequest, "pageVariationUpdated");
		}

		addLayoutBranchSessionMessages(actionRequest, actionResponse);
	}

	public void updateLayoutRevision(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long layoutRevisionId = ParamUtil.getLong(
			actionRequest, "layoutRevisionId");

		LayoutRevision layoutRevision =
			_layoutRevisionLocalService.getLayoutRevision(layoutRevisionId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		LayoutRevision enableLayoutRevision =
			_layoutRevisionLocalService.updateLayoutRevision(
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

			addLayoutRevisionSessionMessages(actionRequest, actionResponse);

			return;
		}

		LayoutRevision lastLayoutRevision =
			_layoutRevisionLocalService.fetchLastLayoutRevision(
				enableLayoutRevision.getPlid(), true);

		if (lastLayoutRevision != null) {
			LayoutRevision newLayoutRevision =
				_layoutRevisionLocalService.addLayoutRevision(
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

		addLayoutRevisionSessionMessages(actionRequest, actionResponse);
	}

	public void updateLayoutSetBranch(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		long copyLayoutSetBranchId = ParamUtil.getLong(
			actionRequest, "copyLayoutSetBranchId",
			LayoutSetBranchConstants.ALL_BRANCHES);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (layoutSetBranchId <= 0) {
			_layoutSetBranchService.addLayoutSetBranch(
				groupId, privateLayout, name, description, false,
				copyLayoutSetBranchId, serviceContext);

			SessionMessages.add(actionRequest, "sitePageVariationAdded");
		}
		else {
			_layoutSetBranchLocalService.updateLayoutSetBranch(
				layoutSetBranchId, name, description, serviceContext);

			SessionMessages.add(actionRequest, "sitePageVariationUpdated");
		}

		addLayoutBranchSessionMessages(actionRequest, actionResponse);
	}

	protected void addLayoutBranchSessionMessages(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		if (SessionErrors.isEmpty(actionRequest)) {
			SessionMessages.add(
				actionRequest,
				PortalUtil.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
				StagingBarPortletKeys.STAGING_BAR);

			Map<String, String> data = new HashMap<>();

			data.put("preventNotification", Boolean.TRUE.toString());

			SessionMessages.add(
				actionRequest,
				PortalUtil.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_REFRESH_PORTLET_DATA,
				data);
		}

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		WindowState windowState = actionRequest.getWindowState();

		if (windowState.equals(LiferayWindowState.POP_UP)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	protected void addLayoutRevisionSessionMessages(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		MultiSessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) + "requestProcessed");

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses()) ||
			SessionErrors.contains(
				renderRequest, SystemException.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof LayoutBranchNameException ||
			cause instanceof LayoutSetBranchNameException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}

	@Reference
	protected void setLayoutBranchService(
		LayoutBranchService layoutBranchService) {

		_layoutBranchService = layoutBranchService;
	}

	@Reference
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	@Reference
	protected void setLayoutSetBranchLocalService(
		LayoutSetBranchLocalService layoutSetBranchLocalService) {

		_layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	@Reference
	protected void setLayoutSetBranchService(
		LayoutSetBranchService layoutSetBranchService) {

		_layoutSetBranchService = layoutSetBranchService;
	}

	@Reference
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	protected void unsetLayoutBranchService(
		LayoutBranchService layoutBranchService) {

		_layoutBranchService = null;
	}

	protected void unsetLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		_layoutRevisionLocalService = null;
	}

	protected void unsetLayoutSetBranchLocalService(
		LayoutSetBranchLocalService layoutSetBranchLocalService) {

		_layoutSetBranchLocalService = null;
	}

	protected void unsetLayoutSetBranchService(
		LayoutSetBranchService layoutSetBranchService) {

		_layoutSetBranchService = null;
	}

	protected void unsetLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = null;
	}

	private LayoutBranchService _layoutBranchService;
	private LayoutRevisionLocalService _layoutRevisionLocalService;
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;
	private LayoutSetBranchService _layoutSetBranchService;
	private LayoutSetLocalService _layoutSetLocalService;

}