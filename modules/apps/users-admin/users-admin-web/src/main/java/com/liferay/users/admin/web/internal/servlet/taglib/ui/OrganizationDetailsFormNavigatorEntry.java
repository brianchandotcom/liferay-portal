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

package com.liferay.users.admin.web.internal.servlet.taglib.ui;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.io.IOException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Pei-Jung Lan
 */
@Component(
	configurationPid = "com.liferay.users.admin.configuration.UserFileUploadsConfiguration",
	property = "form.navigator.entry.order:Integer=30",
	service = FormNavigatorEntry.class
)
public class OrganizationDetailsFormNavigatorEntry
	extends BaseOrganizationFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.
			CATEGORY_KEY_ORGANIZATION_ORGANIZATION_INFORMATION;
	}

	@Override
	public String getKey() {
		return "details";
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(
			UserFileUploadsConfiguration.class.getName(),
			_userFileUploadsConfiguration);

		super.include(request, response);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	@Override
	protected String getJspPath() {
		return "/organization/details.jsp";
	}

	private volatile UserFileUploadsConfiguration _userFileUploadsConfiguration;

}