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
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import javax.portlet.ReadOnlyException;

/**
 * @author Brian Wing Shun Chan
 * @author László Csontos
 */
public class TemplatePortletPreferences {

	public void reset() {
		PortletPreferencesImpl portletPreferencesImpl =
			_portletPreferencesImplThreadLocal.get();

		portletPreferencesImpl.reset();
	}

	public void setValue(String key, String value) throws ReadOnlyException {
		PortletPreferencesImpl portletPreferencesImpl =
			_portletPreferencesImplThreadLocal.get();

		portletPreferencesImpl.setValue(key, value);
	}

	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		PortletPreferencesImpl portletPreferencesImpl =
			_portletPreferencesImplThreadLocal.get();

		portletPreferencesImpl.setValues(key, values);
	}

	@Override
	public String toString() {
		PortletPreferencesImpl portletPreferencesImpl =
			_portletPreferencesImplThreadLocal.get();

		try {
			return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
		}
		catch (Exception e) {
			_log.error(e, e);

			return PortletConstants.DEFAULT_PREFERENCES;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplatePortletPreferences.class);

	private final ThreadLocal<PortletPreferencesImpl>
		_portletPreferencesImplThreadLocal =
			new AutoResetThreadLocal<PortletPreferencesImpl>(
				TemplatePortletPreferences.class.getName()) {

				@Override
				protected PortletPreferencesImpl initialValue() {
					return new PortletPreferencesImpl();
				}

			};

}