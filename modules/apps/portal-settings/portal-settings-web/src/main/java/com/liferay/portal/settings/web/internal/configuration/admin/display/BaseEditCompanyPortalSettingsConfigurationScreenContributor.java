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

package com.liferay.portal.settings.web.internal.configuration.admin.display;

import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
public abstract class
	BaseEditCompanyPortalSettingsConfigurationScreenContributor
		implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getSaveMVCActionCommandName() {
		return "/portal_settings/edit_company";
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.settings.web)",
		unbind = "-"
	)
	protected ServletContext servletContext;

}