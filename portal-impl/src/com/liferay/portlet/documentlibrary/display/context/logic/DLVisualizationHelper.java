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

package com.liferay.portlet.documentlibrary.display.context.logic;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.display.context.util.DLRequestHelper;

/**
 * @author Ivan Zaera
 */
public class DLVisualizationHelper {

	public DLVisualizationHelper(DLRequestHelper dlRequestHelper) {
		_dlRequestHelper = dlRequestHelper;
	}

	public boolean isAddFolderButtonVisible() {
		String portletName = _dlRequestHelper.getPortletName();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	public boolean isShowMinimalActionsButton() {
		String portletName = _dlRequestHelper.getPortletName();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return ParamUtil.getBoolean(
			_dlRequestHelper.getRequest(), "showMinimalActionButtons");
	}

	public boolean isShowWhenSingleIconActionButton() {
		String portletName = _dlRequestHelper.getPortletName();

		if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(PortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	private final DLRequestHelper _dlRequestHelper;

}