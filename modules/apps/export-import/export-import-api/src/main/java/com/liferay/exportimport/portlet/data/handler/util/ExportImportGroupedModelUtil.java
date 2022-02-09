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

package com.liferay.exportimport.portlet.data.handler.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;

/**
 * @author Mariano Álvaro Sáiz
 */
public class ExportImportGroupedModelUtil {

	public static boolean isReferenceInLayoutGroupWithinExportScope(
		PortletDataContext portletDataContext, GroupedModel groupedModel) {

		if (portletDataContext.getGroupId() == groupedModel.getGroupId()) {
			return true;
		}

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getGroup(groupedModel.getGroupId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return false;
		}

		String className = group.getClassName();

		if (className.equals(Layout.class.getName())) {
			Layout scopeLayout = null;

			try {
				scopeLayout = LayoutLocalServiceUtil.getLayout(
					group.getClassPK());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				return false;
			}

			if (scopeLayout.getGroupId() == portletDataContext.getGroupId()) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportGroupedModelUtil.class);

}