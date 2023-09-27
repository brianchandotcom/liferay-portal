/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tika.internal.configuration.helper;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.tika.internal.configuration.TikaConfiguration;

import java.io.InputStream;

import java.util.Map;

import org.apache.tika.config.TikaConfig;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.tika.internal.configuration.TikaConfiguration",
	service = TikaConfigurationHelper.class
)
public class TikaConfigurationHelper {

	public TikaConfig getTikaConfig() {
		return _tikaConfig;
	}

	public boolean useForkProcess(String mimeType) {
		if (_tikaConfiguration.textExtractionForkProcessEnabled() &&
			ArrayUtil.contains(
				_tikaConfiguration.textExtractionForkProcessMimeTypes(),
				mimeType)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Fork process is enabled for " + mimeType);
			}

			return true;
		}

		return false;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		TikaConfiguration tikaConfiguration =
			ConfigurableUtil.createConfigurable(
				TikaConfiguration.class, properties);

		String tikaConfigXml = tikaConfiguration.tikaConfigXml();

		Class<?> clazz = TikaConfigurationHelper.class;

		InputStream inputStream = clazz.getResourceAsStream(tikaConfigXml);

		if (inputStream == null) {
			ClassLoader classLoader = clazz.getClassLoader();

			inputStream = classLoader.getResourceAsStream(tikaConfigXml);

			if (inputStream == null) {
				classLoader = PortalClassLoaderUtil.getClassLoader();

				inputStream = classLoader.getResourceAsStream(tikaConfigXml);

				if (inputStream == null) {
					throw new IllegalArgumentException(
						"Unable to read tika configuration " + tikaConfigXml);
				}
			}
		}

		try {
			_tikaConfig = new TikaConfig(inputStream);
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Unable to create tika configuration", exception);
		}

		_tikaConfiguration = tikaConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TikaConfigurationHelper.class);

	private volatile TikaConfig _tikaConfig;
	private volatile TikaConfiguration _tikaConfiguration;

}