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

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.portlet.ReadOnlyException;

/**
 * @author Brian Wing Shun Chan
 * @author László Csontos
 */
public class TemplatePortletPreferences {

	public String getPreferences(Map<String, Object> preferences)
		throws ReadOnlyException {

		StringBundler sb = new StringBundler();

		sb.append("<portlet-preferences>");

		for (Map.Entry<String, Object> entry : preferences.entrySet()) {
			sb.append("<preference><name>");
			sb.append(entry.getKey());
			sb.append("</name>");

			Object valueObject = entry.getValue();

			if (valueObject instanceof Collection) {
				for (Object value : (Collection)valueObject) {
					if (value instanceof String) {
						sb.append("<value>");
						sb.append(XMLUtil.toCompactSafe((String)value));
						sb.append("</value>");
					}
				}
			}
			else if (valueObject instanceof String) {
				sb.append("<value>");
				sb.append(XMLUtil.toCompactSafe((String)valueObject));
				sb.append("</value>");
			}
			else if (valueObject instanceof String[]) {
				for (String value : (String[])valueObject) {
					sb.append("<value>");
					sb.append(XMLUtil.toCompactSafe(value));
					sb.append("</value>");
				}
			}
			else {
				sb.setIndex(sb.index() - 3);

				continue;
			}

			sb.append("</preference>");
		}

		sb.append("</portlet-preferences>");

		return sb.toString();
	}

	public String getPreferences(String key, String value)
		throws ReadOnlyException {

		return getPreferences(Collections.singletonMap(key, value));
	}

	@Override
	public String toString() {
		PortletPreferencesImpl portletPreferencesImpl =
			_portletPreferencesImplThreadLocal.get();

		try {
			return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
		}
		catch (Exception exception) {
			_log.error(exception);

			return PortletConstants.DEFAULT_PREFERENCES;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplatePortletPreferences.class);

	private final ThreadLocal<PortletPreferencesImpl>
		_portletPreferencesImplThreadLocal = new CentralizedThreadLocal<>(
			TemplatePortletPreferences.class.getName(),
			PortletPreferencesImpl::new);

}