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

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.UADEntity;
import com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay;
import com.liferay.user.associated.data.web.internal.display.UADInfoPanelDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/info_panel"
	},
	service = MVCResourceCommand.class
)
public class InfoPanelMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		UADInfoPanelDisplay uadInfoPanelDisplay = new UADInfoPanelDisplay();

		List<String> entityTypes = new ArrayList<>();

		Map<String, String[]> parameterMap = resourceRequest.getParameterMap();

		for (String key : parameterMap.keySet()) {
			if (key.startsWith("uadRegistryKey__")) {
				entityTypes.add(
					StringUtil.removeSubstring(key, "uadRegistryKey__"));
			}
		}

		for (String entityType : entityTypes) {
			List<UADEntity> uadEntities = new ArrayList<>();

			String uadRegistryKey = ParamUtil.getString(
				resourceRequest, "uadRegistryKey__" + entityType);

			UADDisplay uadDisplay = _uadRegistry.getUADDisplay(uadRegistryKey);

			String[] rowIds = ParamUtil.getStringValues(
				resourceRequest, "rowIds" + entityType);

			for (String rowId : rowIds) {
				Object entity = uadDisplay.get(rowId);

				UADEntity uadEntity = new UADEntity(
					entity, uadDisplay.getPrimaryKey(entity), null, false,
					uadDisplay.getTypeClass(), true, null);

				uadEntities.add(uadEntity);
			}

			if (!uadEntities.isEmpty()) {
				uadInfoPanelDisplay.addUADEntities(uadEntities);
				uadInfoPanelDisplay.setUADDisplay(uadDisplay);
			}
		}

		if (uadInfoPanelDisplay.getUADEntitiesCount() != 1) {
			String uadRegistryKey = ParamUtil.getString(
				resourceRequest, "uadRegistryKey");

			if (Validator.isNull(uadRegistryKey)) {
				uadRegistryKey = ParamUtil.getString(
					resourceRequest, "parentContainerClass");
			}

			if (Validator.isNull(uadRegistryKey)) {
				String applicationKey = ParamUtil.getString(
					resourceRequest, "applicationKey");

				UADHierarchyDisplay uadHierarchyDisplay =
					_uadRegistry.getUADHierarchyDisplay(applicationKey);

				if (uadHierarchyDisplay != null) {
					Class<?> typeClass =
						uadHierarchyDisplay.getFirstContainerTypeClass();

					uadRegistryKey = typeClass.getName();
				}
				else {
					uadRegistryKey = ParamUtil.getString(
						resourceRequest,
						"uadRegistryKey__" + entityTypes.get(0));
				}
			}

			uadInfoPanelDisplay.setUADDisplay(
				_uadRegistry.getUADDisplay(uadRegistryKey));
		}

		boolean hierarchyView = ParamUtil.getBoolean(
			resourceRequest, "hierarchyView");

		uadInfoPanelDisplay.setHierarchyView(hierarchyView);

		boolean topLevelView = ParamUtil.getBoolean(
			resourceRequest, "topLevelView");

		uadInfoPanelDisplay.setTopLevelView(topLevelView);

		resourceRequest.setAttribute(
			UADWebKeys.UAD_INFO_PANEL_DISPLAY, uadInfoPanelDisplay);

		include(resourceRequest, resourceResponse, "/info_panel.jsp");
	}

	@Reference
	private UADRegistry _uadRegistry;

}