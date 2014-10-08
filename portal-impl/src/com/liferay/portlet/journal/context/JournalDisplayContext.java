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

package com.liferay.portlet.journal.context;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.util.JournalUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Eudaldo Alonso
 */
public class JournalDisplayContext {

	public JournalDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		PortletPreferences portletPreferences) {

		_liferayPortletRequest = liferayPortletRequest;
		_portletPreferences = portletPreferences;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = JournalUtil.getDisplayStyle(
				_liferayPortletRequest, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		if (_displayViews == null) {
			_displayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _liferayPortletRequest, "displayViews",
					StringUtil.merge(PropsValues.JOURNAL_DISPLAY_VIEWS)));
		}

		return _displayViews;
	}

	private String _displayStyle;
	private String[] _displayViews;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final PortletPreferences _portletPreferences;

}