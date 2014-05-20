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

package com.liferay.portlet.layoutprototypes;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.io.IOException;

import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class LayoutConfigurator {

	public LayoutConfigurator(Layout layout) {
		_layout = layout;
	}

	public String addPortlet(
			Map<String, String> preferences, String portletId, String columnId)
		throws PortalException, SystemException {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)_layout.getLayoutType();

		portletId = layoutTypePortlet.addPortletId(
			0, portletId, columnId, -1, false);

		updateLayout(_layout);

		addResourcePermissions(_layout, portletId);

		if (preferences != null) {
			updatePortletSetup(_layout, portletId, preferences);
		}

		return portletId;
	}

	public String addPortlet(String portletId, String columnId)
		throws PortalException, SystemException {

		return addPortlet(null, portletId, columnId);
	}

	public Layout getLayout() {
		return _layout;
	}

	public void setTypeSettingsProperty(String key, String value)
		throws PortalException, SystemException {

		UnicodeProperties typeSettingsProperties =
			_layout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(key, value);

		_layout = LayoutLocalServiceUtil.updateLayout(
			_layout.getGroupId(), _layout.isPrivateLayout(),
			_layout.getLayoutId(), _layout.getTypeSettings());
	}

	protected void addResourcePermissions(Layout layout, String portletId)
		throws PortalException, SystemException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), portletId);

		PortalUtil.addPortletDefaultResource(
			layout.getCompanyId(), layout, portlet);
	}

	protected void updateLayout(Layout layout)
		throws PortalException, SystemException {

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	protected PortletPreferences updatePortletSetup(
			Layout layout, String portletId, Map<String, String> preferences)
		throws SystemException {

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

		for (Map.Entry<String, String> entry : preferences.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			try {
				portletSetup.setValue(key, value);
			}
			catch (ReadOnlyException roe) {
				throw new SystemException(roe);
			}
		}

		try {
			portletSetup.store();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ValidatorException ve) {
			throw new SystemException(ve);
		}

		return portletSetup;
	}

	private Layout _layout;

}