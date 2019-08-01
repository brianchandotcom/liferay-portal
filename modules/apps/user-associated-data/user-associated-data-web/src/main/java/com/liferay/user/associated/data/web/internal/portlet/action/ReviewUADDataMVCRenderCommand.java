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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.ScopeDisplay;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.display.UADEntity;
import com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay;
import com.liferay.user.associated.data.web.internal.display.UADInfoPanelDisplay;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.search.UADHierarchyResultRowSplitter;
import com.liferay.user.associated.data.web.internal.util.GroupUtil;
import com.liferay.user.associated.data.web.internal.util.SelectedUserHelper;
import com.liferay.user.associated.data.web.internal.util.UADApplicationSummaryHelper;
import com.liferay.user.associated.data.web.internal.util.UADSearchContainerBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/review_uad_data"
	},
	service = MVCRenderCommand.class
)
public class ReviewUADDataMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selectedUser = _selectedUserHelper.getSelectedUser(
				renderRequest);

			List<ScopeDisplay> scopeDisplays = _getScopeDisplays(selectedUser);

			ScopeDisplay activeScopeDisplay = _getActiveScopeDisplay(
				renderRequest, scopeDisplays);

			String applicationKey = _getApplicationKey(
				renderRequest, activeScopeDisplay);

			UADHierarchyDisplay uadHierarchyDisplay =
				_uadRegistry.getUADHierarchyDisplay(applicationKey);

			if (uadHierarchyDisplay != null) {
				renderRequest.setAttribute(
					UADWebKeys.UAD_HIERARCHY_DISPLAY, uadHierarchyDisplay);
			}
			else {
				renderRequest.setAttribute(
					UADWebKeys.APPLICATION_UAD_DISPLAYS,
					_uadRegistry.getApplicationUADDisplays(applicationKey));
			}

			String uadRegistryKey = _getUADRegistryKey(
				renderRequest, applicationKey);

			UADDisplay uadDisplay = _uadRegistry.getUADDisplay(uadRegistryKey);

			renderRequest.setAttribute(
				UADWebKeys.SCOPE_DISPLAYS, scopeDisplays);
			renderRequest.setAttribute(
				UADWebKeys.TOTAL_UAD_ENTITIES_COUNT,
				_getTotalUADEntitiesCount(selectedUser));
			renderRequest.setAttribute(
				UADWebKeys.UAD_APPLICATION_SUMMARY_DISPLAY_LIST,
				activeScopeDisplay.getUADApplicationSummaryDisplays());
			renderRequest.setAttribute(
				UADWebKeys.UAD_INFO_PANEL_DISPLAY,
				_getUADInfoPanelDisplay(uadHierarchyDisplay, uadDisplay));
			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY,
				_getViewUADEntitiesDisplay(
					renderRequest, renderResponse, applicationKey,
					activeScopeDisplay, selectedUser, uadHierarchyDisplay,
					uadDisplay, uadRegistryKey));
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return "/review_uad_data.jsp";
	}

	private ScopeDisplay _getActiveScopeDisplay(
		RenderRequest renderRequest, List<ScopeDisplay> scopeDisplays) {

		ScopeDisplay activeScopeDisplay = null;

		String scope = ParamUtil.getString(
			renderRequest, "scope", UADConstants.SCOPE_PERSONAL_SITE);

		for (ScopeDisplay scopeDisplay : scopeDisplays) {
			if (scope.equals(scopeDisplay.getScopeName())) {
				activeScopeDisplay = scopeDisplay;
			}
		}

		if (!activeScopeDisplay.hasItems()) {
			for (ScopeDisplay curScopeDisplay : scopeDisplays) {
				if (curScopeDisplay.hasItems()) {
					activeScopeDisplay = curScopeDisplay;
				}
			}
		}

		activeScopeDisplay.setActive(true);

		return activeScopeDisplay;
	}

	private String _getApplicationKey(
		RenderRequest renderRequest, ScopeDisplay scopeDisplay) {

		String applicationKey = ParamUtil.getString(
			renderRequest, "applicationKey");

		if (Validator.isNull(applicationKey)) {
			return scopeDisplay.getApplicationKey();
		}

		for (UADApplicationSummaryDisplay uadApplicationSummaryDisplay :
				scopeDisplay.getUADApplicationSummaryDisplays()) {

			if (applicationKey.equals(
					uadApplicationSummaryDisplay.getApplicationKey()) &&
				!uadApplicationSummaryDisplay.hasItems()) {

				return scopeDisplay.getApplicationKey();
			}
		}

		return scopeDisplay.getApplicationKey();
	}

	private List<ScopeDisplay> _getScopeDisplays(User selectedUser) {
		List<ScopeDisplay> scopeDisplays = new ArrayList<>();

		for (String curScope : UADConstants.SCOPES) {
			long[] curGroupIds = GroupUtil.getGroupIds(
				_groupLocalService, selectedUser, curScope);

			ScopeDisplay curScopeDisplay = new ScopeDisplay(
				curScope, curGroupIds,
				_uadApplicationSummaryHelper.getUADApplicationSummaryDisplays(
					selectedUser.getUserId(), curGroupIds));

			scopeDisplays.add(curScopeDisplay);
		}

		return scopeDisplays;
	}

	private SearchContainer<UADEntity> _getSearchContainer(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String applicationKey, ScopeDisplay scopeDisplay, User selectedUser,
			UADHierarchyDisplay uadHierarchyDisplay, UADDisplay uadDisplay)
		throws Exception {

		PortletURL currentURL = PortletURLUtil.getCurrent(
			renderRequest, renderResponse);

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(renderResponse);

		if (applicationKey.equals(UADConstants.ALL_APPLICATIONS)) {
			return _uadSearchContainerBuilder.getSearchContainer(
				renderRequest, liferayPortletResponse, currentURL,
				scopeDisplay.getUADApplicationSummaryDisplays());
		}

		if (uadHierarchyDisplay != null) {
			return _uadSearchContainerBuilder.getSearchContainer(
				renderRequest, liferayPortletResponse, applicationKey,
				currentURL, scopeDisplay.getGroupIds(),
				uadHierarchyDisplay.getFirstContainerTypeClass(), 0L,
				selectedUser, uadHierarchyDisplay);
		}

		return _uadSearchContainerBuilder.getSearchContainer(
			renderRequest, liferayPortletResponse, currentURL,
			scopeDisplay.getGroupIds(), selectedUser, uadDisplay);
	}

	private int _getTotalUADEntitiesCount(User selectedUser) {
		return _uadApplicationSummaryHelper.getTotalReviewableUADEntitiesCount(
			selectedUser.getUserId());
	}

	private UADInfoPanelDisplay _getUADInfoPanelDisplay(
		UADHierarchyDisplay uadHierarchyDisplay, UADDisplay uadDisplay) {

		UADInfoPanelDisplay uadInfoPanelDisplay = new UADInfoPanelDisplay();

		if (uadHierarchyDisplay != null) {
			UADDisplay<?>[] uadDisplays = uadHierarchyDisplay.getUADDisplays();

			uadInfoPanelDisplay.setUADDisplay(uadDisplays[0]);

			uadInfoPanelDisplay.setHierarchyView(true);
		}
		else {
			uadInfoPanelDisplay.setUADDisplay(uadDisplay);
		}

		return uadInfoPanelDisplay;
	}

	private String _getUADRegistryKey(
		RenderRequest renderRequest, String applicationKey) {

		String uadRegistryKey = ParamUtil.getString(
			renderRequest, "uadRegistryKey");

		if (Validator.isNull(uadRegistryKey)) {
			uadRegistryKey =
				_uadApplicationSummaryHelper.getDefaultUADRegistryKey(
					applicationKey);
		}

		return uadRegistryKey;
	}

	private ViewUADEntitiesDisplay _getViewUADEntitiesDisplay(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String applicationKey, ScopeDisplay activeScopeDisplay,
			User selectedUser, UADHierarchyDisplay uadHierarchyDisplay,
			UADDisplay uadDisplay, String uadRegistryKey)
		throws Exception {

		ViewUADEntitiesDisplay viewUADEntitiesDisplay =
			new ViewUADEntitiesDisplay();

		viewUADEntitiesDisplay.setApplicationKey(applicationKey);
		viewUADEntitiesDisplay.setGroupIds(activeScopeDisplay.getGroupIds());

		if (uadHierarchyDisplay != null) {
			viewUADEntitiesDisplay.setHierarchy(true);
			viewUADEntitiesDisplay.setResultRowSplitter(
				new UADHierarchyResultRowSplitter(
					LocaleThreadLocal.getThemeDisplayLocale(),
					uadHierarchyDisplay.getUADDisplays()));
			viewUADEntitiesDisplay.setTypeClasses(
				uadHierarchyDisplay.getTypeClasses());
		}
		else {
			viewUADEntitiesDisplay.setTypeName(
				uadDisplay.getTypeName(
					LocaleThreadLocal.getThemeDisplayLocale()));
			viewUADEntitiesDisplay.setTypeClasses(
				new Class<?>[] {uadDisplay.getTypeClass()});

			viewUADEntitiesDisplay.setUADRegistryKey(uadRegistryKey);
		}

		viewUADEntitiesDisplay.setScope(activeScopeDisplay.getScopeName());
		viewUADEntitiesDisplay.setSearchContainer(
			_getSearchContainer(
				renderRequest, renderResponse, applicationKey,
				activeScopeDisplay, selectedUser, uadHierarchyDisplay,
				uadDisplay));

		return viewUADEntitiesDisplay;
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