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

package com.liferay.application.list.adapter;

import com.liferay.application.list.BasePanelApp;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;

/**
 * @author Adolfo Pérez
 */
public class PortletPanelAppAdapter extends BasePanelApp {

	public PortletPanelAppAdapter(String portletId) {
		_portletId = portletId;
	}

	@Override
	public String getKey() {
		return "portlet-adapter-" + getPortletId();
	}

	@Override
	public String getLabel(Locale locale) {
		PortletConfig portletConfig = PortletConfigFactoryUtil.get(
			getPortletId());

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		Portlet portlet = getPortlet();

		String key =
			JavaConstants.JAVAX_PORTLET_TITLE + StringPool.PERIOD +
				portlet.getPortletName();

		String value = LanguageUtil.get(resourceBundle, key);

		if (!key.equals(value)) {
			return value;
		}

		value = LanguageUtil.get(locale, key);

		if (!key.equals(value)) {
			return value;
		}

		String displayName = portlet.getDisplayName();

		if (!displayName.equals(portlet.getPortletName())) {
			return displayName;
		}

		return key;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	private final String _portletId;

}