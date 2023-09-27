/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	service = UserFileUploadsSettings.class
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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	private volatile UserFileUploadsConfiguration _userFileUploadsConfiguration;

}