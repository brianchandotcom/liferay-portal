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

package com.liferay.portal.kernel.security.permission.propagator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;

/**
 * @author Hugo Huijser
 */
public abstract class BasePermissionPropagator implements PermissionPropagator {

	protected Set<String> getActionIds(String className) {
		List<String> actionIds = ResourceActionsUtil.getModelResourceActions(
			className);

		return SetUtil.fromCollection(actionIds);
	}

	protected Set<String> getAvailableActionIds(
			long companyId, String className, long primKey, long roleId,
			Set<String> actionIds)
		throws PortalException {

		List<String> availableActionIds =
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(primKey), roleId, actionIds);

		return SetUtil.fromCollection(availableActionIds);
	}

	protected void propagateRolePermissions(
			ActionRequest actionRequest, long roleId, String parentClassName,
			long parentPrimKey, String childClassName, long childPrimKey)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Set<String> parentActionIds = getActionIds(parentClassName);
		Set<String> childActionIds = getActionIds(childClassName);

		Set<String> parentAndChildCommonActionIds = new HashSet<>();

		for (String actionId : childActionIds) {
			if (parentActionIds.contains(actionId)) {
				parentAndChildCommonActionIds.add(actionId);
			}
		}

		Set<String> parentAvailableActionIds = getAvailableActionIds(
			themeDisplay.getCompanyId(), parentClassName, parentPrimKey, roleId,
			parentActionIds);
		Set<String> childAvailableActionIds = getAvailableActionIds(
			themeDisplay.getCompanyId(), childClassName, childPrimKey, roleId,
			childActionIds);

		List<String> actionIds = new ArrayList<>();

		for (String actionId : parentAndChildCommonActionIds) {
			if (parentAvailableActionIds.contains(actionId)) {
				actionIds.add(actionId);
			}
		}

		for (String actionId : childAvailableActionIds) {
			if (!parentAndChildCommonActionIds.contains(actionId)) {
				actionIds.add(actionId);
			}
		}

		ResourcePermissionServiceUtil.setIndividualResourcePermissions(
			themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(),
			childClassName, String.valueOf(childPrimKey), roleId,
			actionIds.toArray(new String[0]));
	}

}