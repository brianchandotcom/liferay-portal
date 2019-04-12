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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay;
import com.liferay.user.associated.data.web.internal.display.UADInfoPanelDisplay;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.search.UADHierarchyResultRowSplitter;
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

			String scope = ParamUtil.getString(
				renderRequest, "scope", UADConstants.SCOPE_PERSONAL_SITE);

			long[] groupIds = _getGroupIds(selectedUser, scope);

			List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
				_uadApplicationSummaryHelper.getUADApplicationSummaryDisplays(
					selectedUser.getUserId(), groupIds);

			UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
				uadApplicationSummaryDisplays.get(0);

			for (UADApplicationSummaryDisplay
					currentUADApplicationSummaryDisplay :
						uadApplicationSummaryDisplays) {

				if (currentUADApplicationSummaryDisplay.getCount() > 0) {
					uadApplicationSummaryDisplay =
						currentUADApplicationSummaryDisplay;

					break;
				}
			}

			String applicationKey = ParamUtil.getString(
				renderRequest, "applicationKey");

			if (Validator.isNull(applicationKey)) {
				applicationKey =
					uadApplicationSummaryDisplay.getApplicationKey();
			}

			ViewUADEntitiesDisplay viewUADEntitiesDisplay =
				new ViewUADEntitiesDisplay();

			viewUADEntitiesDisplay.setApplicationKey(applicationKey);

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(renderResponse);

			PortletURL currentURL = PortletURLUtil.getCurrent(
				renderRequest, renderResponse);

			UADInfoPanelDisplay uadInfoPanelDisplay = new UADInfoPanelDisplay();

			UADHierarchyDisplay uadHierarchyDisplay =
				_uadRegistry.getUADHierarchyDisplay(applicationKey);

			if (applicationKey.equals(UADConstants.ALL_APPLICATIONS)) {
				viewUADEntitiesDisplay.setSearchContainer(
					_uadSearchContainerBuilder.getSearchContainer(
						renderRequest, liferayPortletResponse, currentURL,
						uadApplicationSummaryDisplays));
			}
			else if (uadHierarchyDisplay != null) {
				UADDisplay<?>[] uadDisplays =
					uadHierarchyDisplay.getUADDisplays();

				uadInfoPanelDisplay.setUADDisplay(uadDisplays[0]);

				uadInfoPanelDisplay.setHierarchyView(true);

				viewUADEntitiesDisplay.setHierarchy(true);
				viewUADEntitiesDisplay.setResultRowSplitter(
					new UADHierarchyResultRowSplitter(
						LocaleThreadLocal.getThemeDisplayLocale(),
						uadHierarchyDisplay.getUADDisplays()));
				viewUADEntitiesDisplay.setTypeClasses(
					uadHierarchyDisplay.getTypeClasses());

				Class<?> parentContainerClass =
					uadHierarchyDisplay.getFirstContainerTypeClass();

				viewUADEntitiesDisplay.setSearchContainer(
					_uadSearchContainerBuilder.getSearchContainer(
						renderRequest, liferayPortletResponse, applicationKey,
						currentURL, groupIds, parentContainerClass, 0L,
						selectedUser, uadHierarchyDisplay));

				renderRequest.setAttribute(
					UADWebKeys.UAD_HIERARCHY_DISPLAY, uadHierarchyDisplay);
			}
			else {
				String uadRegistryKey = ParamUtil.getString(
					renderRequest, "uadRegistryKey");

				if (Validator.isNull(uadRegistryKey)) {
					uadRegistryKey =
						_uadApplicationSummaryHelper.getDefaultUADRegistryKey(
							applicationKey);
				}

				UADDisplay uadDisplay = _uadRegistry.getUADDisplay(
					uadRegistryKey);

				uadInfoPanelDisplay.setUADDisplay(uadDisplay);

				viewUADEntitiesDisplay.setSearchContainer(
					_uadSearchContainerBuilder.getSearchContainer(
						renderRequest, liferayPortletResponse, currentURL,
						groupIds, selectedUser, uadDisplay));
				viewUADEntitiesDisplay.setTypeName(
					uadDisplay.getTypeName(
						LocaleThreadLocal.getThemeDisplayLocale()));
				viewUADEntitiesDisplay.setTypeClasses(
					new Class<?>[] {uadDisplay.getTypeClass()});

				viewUADEntitiesDisplay.setUADRegistryKey(uadRegistryKey);

				renderRequest.setAttribute(
					UADWebKeys.APPLICATION_UAD_DISPLAYS,
					_uadRegistry.getApplicationUADDisplays(applicationKey));
			}

			renderRequest.setAttribute(UADWebKeys.GROUP_IDS, groupIds);
			renderRequest.setAttribute(
				UADWebKeys.TOTAL_UAD_ENTITIES_COUNT,
				_uadApplicationSummaryHelper.getTotalReviewableUADEntitiesCount(
					selectedUser.getUserId()));
			renderRequest.setAttribute(
				UADWebKeys.UAD_APPLICATION_SUMMARY_DISPLAY_LIST,
				uadApplicationSummaryDisplays);
			renderRequest.setAttribute(
				UADWebKeys.UAD_INFO_PANEL_DISPLAY, uadInfoPanelDisplay);
			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY, viewUADEntitiesDisplay);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return "/review_uad_data.jsp";
	}

	private long[] _getGroupIds(User user, String scope) {
		try {
			if (scope.equals(UADConstants.SCOPE_PERSONAL_SITE)) {
				Group userGroup = _groupLocalService.getUserGroup(
					user.getCompanyId(), user.getUserId());

				return new long[] {userGroup.getGroupId()};
			}

			if (scope.equals(UADConstants.SCOPE_REGULAR_SITES)) {
				List<Group> allGroups = new ArrayList<>();

				List<Group> liveGroups = _groupLocalService.getGroups(
					user.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true);

				allGroups.addAll(liveGroups);

				for (Group group : liveGroups) {
					Group stagingGroup = group.getStagingGroup();

					if (stagingGroup != null) {
						allGroups.add(stagingGroup);
					}
				}

				return ListUtil.toLongArray(allGroups, Group.GROUP_ID_ACCESSOR);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReviewUADDataMVCRenderCommand.class);

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