/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.BaseControlPanelEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.PortletCategoryKeys;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Feliphe Marinho
 */
public class ObjectDefinitionsControlPanelEntry extends BaseControlPanelEntry {

	public ObjectDefinitionsControlPanelEntry(
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	@Override
	protected boolean hasPermissionImplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		if (_objectDefinition.getRootObjectDefinitionId() == 0) {
			return false;
		}

		long groupId = 0L;

		if (StringUtil.startsWith(
				portlet.getControlPanelEntryCategory(),
				PortletCategoryKeys.SITE_ADMINISTRATION)) {

			groupId = group.getGroupId();
		}

		ObjectDefinition rootObjectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				_objectDefinition.getRootObjectDefinitionId());

		List<String> resourceActions = ResourceActionsUtil.getResourceActions(
			rootObjectDefinition.getPortletId());

		if (resourceActions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
			PortletPermissionUtil.contains(
				permissionChecker, groupId, 0,
				rootObjectDefinition.getPortletId(),
				ActionKeys.ACCESS_IN_CONTROL_PANEL, true)) {

			return true;
		}

		return false;
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;

}