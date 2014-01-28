/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Jorge Ferrer
 */
public class CompanyPortletSettings extends BasePortletSettings {

	public CompanyPortletSettings(PortletPreferences portletPreferences) {
		super._companyPortletPreferences = portletPreferences;
	}

	public PortletSettings setPortalDefaults(Properties portalProperties) {
		super._portalProperties = portalProperties;

		return this;
	}

	protected CompanyPortletSettings() {
	}

	protected PortletPreferences getWriteablePortletPreferences() {
		return super._companyPortletPreferences;
	}

}