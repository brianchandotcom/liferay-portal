/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.properties.swapper.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Shuyang Zhou
 */
public class PropsHelperUtil {

	public static boolean isCustomized(String key) {
		for (Map.Entry<String, Properties> entry : _propertiesMap.entrySet()) {
			Properties properties = entry.getValue();

			if (properties.containsKey(key)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Found customized value for key " + key + " in " +
							entry.getKey());
				}

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PropsHelperUtil.class);

	private static final Map<String, Properties> _propertiesMap =
		new LinkedHashMap<>();

	static {
		String[] propertiesLocations = PropsUtil.getArray(
			"include-and-override");

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		for (int i = propertiesLocations.length - 1; i >= 0; i--) {
			String propertiesLocation = propertiesLocations[i];

			try {
				if (Files.exists(Paths.get(propertiesLocation))) {
					try (InputStream inputStream = new FileInputStream(
							propertiesLocation)) {

						Properties properties = new Properties();

						properties.load(inputStream);

						_propertiesMap.put(propertiesLocation, properties);
					}
					catch (IOException ioe) {
						_log.error("Unable to read " + propertiesLocation, ioe);
					}

					continue;
				}
			}
			catch (InvalidPathException ipe) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to parse " + propertiesLocation, ipe);
				}
			}

			try (InputStream inputStream =
					classLoader.getResourceAsStream(propertiesLocation)) {

				if (inputStream != null) {
					Properties properties = new Properties();

					properties.load(inputStream);

					_propertiesMap.put(propertiesLocation, properties);
				}
			}
			catch (IOException ioe) {
				_log.error("Unable to read " + propertiesLocation, ioe);
			}
		}
	}

}