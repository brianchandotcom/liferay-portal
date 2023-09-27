/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upload.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProvider;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Pei-Jung Lan
 */
@Component(
	configurationPid = "com.liferay.portal.upload.internal.configuration.UploadServletRequestConfiguration",
	service = UploadServletRequestConfigurationProvider.class
)
public class UploadServletRequestConfigurationProviderImpl
	implements UploadServletRequestConfigurationProvider {

	@Override
	public long getMaxSize() {
		return _uploadServletRequestConfiguration.maxSize();
	}

	@Override
	public long getMaxTries() {
		return _uploadServletRequestConfiguration.maxTries();
	}

	@Override
	public String getTempDir() {
		String tempDir = _uploadServletRequestConfiguration.tempDir();

		if (Validator.isNotNull(tempDir)) {
			return tempDir;
		}

		return SystemProperties.get(SystemProperties.TMP_DIR);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_uploadServletRequestConfiguration =
			ConfigurableUtil.createConfigurable(
				UploadServletRequestConfiguration.class, properties);
	}

	private volatile UploadServletRequestConfiguration
		_uploadServletRequestConfiguration;

}