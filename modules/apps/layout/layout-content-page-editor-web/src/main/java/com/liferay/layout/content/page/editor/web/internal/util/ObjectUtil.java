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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class ObjectUtil {

	public static Map<String, List<Map<String, Object>>>
		getLayoutElementMapsListMap(long companyId) {

		Map<String, List<Map<String, Object>>> layoutElementMapsListMap =
			new HashMap<>(ContentPageEditorConstants.layoutElementMapsListMap);

		if (hideInputFragments(companyId)) {
			layoutElementMapsListMap.remove("INPUTS");
		}

		return layoutElementMapsListMap;
	}

	public static Boolean hideInputFragments(long companyId) {
		List<ObjectDefinition> objectDefinitions =
			ObjectDefinitionLocalServiceUtil.getObjectDefinitions(
				companyId, true, false, WorkflowConstants.STATUS_APPROVED);

		if (objectDefinitions.isEmpty()) {
			return true;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			if (_hasPermissions(objectDefinition)) {
				return false;
			}
		}

		return true;
	}

	private static boolean _hasPermissions(ObjectDefinition objectDefinition) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			objectDefinition.getCompanyId(), objectDefinition.getPortletId());

		if (!portlet.isActive()) {
			return false;
		}

		try {
			return PortletPermissionUtil.contains(
				PermissionThreadLocal.getPermissionChecker(),
				portlet.getRootPortletId(), ActionKeys.VIEW);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(ObjectUtil.class);

}