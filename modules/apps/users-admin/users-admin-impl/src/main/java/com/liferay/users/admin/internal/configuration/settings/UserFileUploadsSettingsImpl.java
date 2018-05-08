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

package com.liferay.users.admin.internal.configuration.settings;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Drew Brokke
 */
@Component(
	configurationPid = "com.liferay.users.admin.configuration.UserFileUploadsConfiguration",
	immediate = true, service = UserFileUploadsSettings.class
)
public class UserFileUploadsSettingsImpl implements UserFileUploadsSettings {

	@Override
	public int getImageMaxHeight() {
		return _userFileUploadsConfiguration.imageMaxHeight();
	}

	@Override
	public long getImageMaxSize() {
		return _userFileUploadsConfiguration.imageMaxSize();
	}

	@Override
	public int getImageMaxWidth() {
		return _userFileUploadsConfiguration.imageMaxWidth();
	}

	@Override
	public boolean isImageCheckToken() {
		return _userFileUploadsConfiguration.imageCheckToken();
	}

	@Override
	public boolean isImageDefaultUseInitials() {
		return _userFileUploadsConfiguration.imageDefaultUseInitials();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	private UserFileUploadsConfiguration _userFileUploadsConfiguration;

}