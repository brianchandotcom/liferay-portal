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

package com.liferay.document.library.web.internal.display.context.logic;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

/**
 * @author Iván Zaera
 */
public class DLVisualizationHelper {

	public DLVisualizationHelper(DLRequestHelper dlRequestHelper) {
		_dlRequestHelper = dlRequestHelper;
	}

	public String getDisplayStyle() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		String[] displayViews = dlPortletInstanceSettings.getDisplayViews();

		String displayStyle = ParamUtil.getString(
			_dlRequestHelper.getRequest(), "displayStyle");

		if (Validator.isNull(displayStyle)) {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					_dlRequestHelper.getLiferayPortletRequest());

			displayStyle = portalPreferences.getValue(
				DLPortletKeys.DOCUMENT_LIBRARY, "display-style",
				PropsValues.DL_DEFAULT_DISPLAY_VIEW);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	public boolean isAddFolderButtonVisible() {
		String portletName = _dlRequestHelper.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return false;
	}

	public boolean isMountFolderVisible() {
		return ParamUtil.getBoolean(
			_dlRequestHelper.getRequest(), "showMountFolder", true);
	}

	public boolean isShowMinimalActionsButton() {
		String portletName = _dlRequestHelper.getPortletName();

		if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) ||
			portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return true;
		}

		return ParamUtil.getBoolean(
			_dlRequestHelper.getRequest(), "showMinimalActionButtons");
	}

	private final DLRequestHelper _dlRequestHelper;

}