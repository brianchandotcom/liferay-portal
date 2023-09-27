/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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