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

package com.liferay.portal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.EnvPropertiesUtil;
import com.liferay.portal.kernel.util.SystemProperties;

/**
 * @author Shuyang Zhou
 */
public class ClassLoaderAggregatePropertiesUtil {

	public static ClassLoaderAggregateProperties create(
		ClassLoader classLoader, String companyId, String componentName) {

		SystemProperties.set("base.path", ".");

		ClassLoaderAggregateProperties classLoaderAggregateProperties =
			new ClassLoaderAggregateProperties(
				classLoader, companyId, componentName);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Properties for ", componentName, " loaded from ",
					classLoaderAggregateProperties.loadedSources()));
		}

		EnvPropertiesUtil.loadEnvOverrides(
			_ENV_OVERRIDE_PREFIX, classLoaderAggregateProperties::setProperty);

		return classLoaderAggregateProperties;
	}

	private static final String _ENV_OVERRIDE_PREFIX = "LIFERAY_";

	private static final Log _log = LogFactoryUtil.getLog(
		ClassLoaderAggregatePropertiesUtil.class);

}