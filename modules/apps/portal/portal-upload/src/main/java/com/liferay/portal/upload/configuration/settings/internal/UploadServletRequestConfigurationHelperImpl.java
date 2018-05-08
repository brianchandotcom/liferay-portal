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

package com.liferay.portal.upload.configuration.settings.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelper;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upload.internal.configuration.UploadServletRequestConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Pei-Jung Lan
 */
@Component(
	configurationPid = "com.liferay.portal.upload.configuration.UploadServletRequestConfiguration",
	immediate = true, service = UploadServletRequestConfigurationHelper.class
)
public class UploadServletRequestConfigurationHelperImpl
	implements UploadServletRequestConfigurationHelper {

	@Override
	public long getMaxSize() {
		return _uploadServletRequestConfiguration.maxSize();
	}

	@Override
	public String getTempDir() {
		String tempDir = _uploadServletRequestConfiguration.tempDir();

		if (Validator.isNull(tempDir)) {
			tempDir = SystemProperties.get(SystemProperties.TMP_DIR);
		}

		return tempDir;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_uploadServletRequestConfiguration =
			ConfigurableUtil.createConfigurable(
				UploadServletRequestConfiguration.class, properties);
	}

	private UploadServletRequestConfiguration
		_uploadServletRequestConfiguration;

}