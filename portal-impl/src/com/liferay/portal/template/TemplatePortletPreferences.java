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

package com.liferay.portal.template;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import javax.portlet.ReadOnlyException;

/**
 * @author Brian Wing Shun Chan
 */
public class TemplatePortletPreferences {

	public TemplatePortletPreferences() {
		_portletPreferencesImpl = new PortletPreferencesImpl();
	}

	public void reset() {
		_portletPreferencesImpl.reset();
	}

	public void setValue(String key, String value) throws ReadOnlyException {
		_portletPreferencesImpl.setValue(key, value);
	}

	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		_portletPreferencesImpl.setValues(key, values);
	}

	@Override
	public String toString() {
		try {
			return PortletPreferencesFactoryUtil.toXML(_portletPreferencesImpl);
		}
		catch (Exception e) {
			_log.error(e, e);

			return PortletConstants.DEFAULT_PREFERENCES;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplatePortletPreferences.class);

	private final PortletPreferencesImpl _portletPreferencesImpl;

}