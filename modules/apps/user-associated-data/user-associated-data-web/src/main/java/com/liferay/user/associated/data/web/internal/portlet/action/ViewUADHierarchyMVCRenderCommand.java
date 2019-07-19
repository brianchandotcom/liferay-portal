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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay;
import com.liferay.user.associated.data.web.internal.display.UADInfoPanelDisplay;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.search.UADHierarchyResultRowSplitter;
import com.liferay.user.associated.data.web.internal.util.GroupUtil;
import com.liferay.user.associated.data.web.internal.util.SelectedUserHelper;
import com.liferay.user.associated.data.web.internal.util.UADApplicationSummaryHelper;
import com.liferay.user.associated.data.web.internal.util.UADSearchContainerBuilder;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/view_uad_hierarchy"
	},
	service = MVCRenderCommand.class
)
public class ViewUADHierarchyMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selectedUser = _selectedUserHelper.getSelectedUser(
				renderRequest);

			String applicationKey = ParamUtil.getString(
				renderRequest, "applicationKey");

			ViewUADEntitiesDisplay viewUADEntitiesDisplay =
				new ViewUADEntitiesDisplay();

			viewUADEntitiesDisplay.setApplicationKey(applicationKey);
			viewUADEntitiesDisplay.setHierarchy(true);

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(renderResponse);

			PortletURL currentURL = PortletURLUtil.getCurrent(
				renderRequest, renderResponse);

			UADHierarchyDisplay uadHierarchyDisplay =
				_uadRegistry.getUADHierarchyDisplay(applicationKey);

			viewUADEntitiesDisplay.setResultRowSplitter(
				new UADHierarchyResultRowSplitter(
					LocaleThreadLocal.getThemeDisplayLocale(),
					uadHierarchyDisplay.getUADDisplays()));
			viewUADEntitiesDisplay.setTypeClasses(
				uadHierarchyDisplay.getTypeClasses());

			String className = ParamUtil.getString(
				renderRequest, "parentContainerClass");

			UADDisplay uadDisplay = _uadRegistry.getUADDisplay(className);

			UADInfoPanelDisplay uadInfoPanelDisplay = new UADInfoPanelDisplay();

			uadInfoPanelDisplay.setHierarchyView(true);
			uadInfoPanelDisplay.setTopLevelView(false);
			uadInfoPanelDisplay.setUADDisplay(uadDisplay);

			Class<?> typeClass = uadDisplay.getTypeClass();

			String scope = ParamUtil.getString(
				renderRequest, "scope", UADConstants.SCOPE_PERSONAL_SITE);

			long parentContainerId = ParamUtil.getLong(
				renderRequest, "parentContainerId");

			viewUADEntitiesDisplay.setSearchContainer(
				_uadSearchContainerBuilder.getSearchContainer(
					renderRequest, liferayPortletResponse, applicationKey,
					currentURL,
					GroupUtil.getGroupIds(
						_groupLocalService, selectedUser, scope),
					typeClass, parentContainerId, selectedUser,
					uadHierarchyDisplay));

			renderRequest.setAttribute(
				UADWebKeys.UAD_HIERARCHY_DISPLAY, uadHierarchyDisplay);
			renderRequest.setAttribute(
				UADWebKeys.UAD_INFO_PANEL_DISPLAY, uadInfoPanelDisplay);
			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY, viewUADEntitiesDisplay);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return "/view_uad_hierarchy.jsp";
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SelectedUserHelper _selectedUserHelper;

	@Reference
	private UADApplicationSummaryHelper _uadApplicationSummaryHelper;

	@Reference
	private UADRegistry _uadRegistry;

	@Reference
	private UADSearchContainerBuilder _uadSearchContainerBuilder;

}