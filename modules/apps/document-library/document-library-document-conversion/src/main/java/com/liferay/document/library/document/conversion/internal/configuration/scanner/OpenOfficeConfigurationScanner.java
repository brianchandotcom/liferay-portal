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

package com.liferay.document.library.document.conversion.internal.configuration.scanner;

import com.liferay.document.library.document.conversion.internal.configuration.OpenOfficeConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Matthew Tambara
 */
@Component(
	configurationPid = "com.liferay.document.library.document.conversion.internal.configuration.OpenOfficeConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class OpenOfficeConfigurationScanner {

	@Activate
	protected void activate(Map<String, Object> properties) {
		OpenOfficeConfiguration openOfficeConfiguration =
			ConfigurableUtil.createConfigurable(
				OpenOfficeConfiguration.class, properties);

		String host = openOfficeConfiguration.serverHost();

		if (_log.isWarnEnabled() && Validator.isNotNull(host) &&
			!host.equals(_LOCALHOST_IP) && !host.startsWith(_LOCALHOST)) {

			StringBundler sb = new StringBundler(3);

			sb.append("Conversion using a remote OpenOffice instance is not ");
			sb.append("fully supported. Please use a local instance to ");
			sb.append("prevent any limitations and problems.");

			_log.warn(sb.toString());
		}
	}

	private static final String _LOCALHOST = "localhost";

	private static final String _LOCALHOST_IP = "127.0.0.1";

	private static final Log _log = LogFactoryUtil.getLog(
		OpenOfficeConfigurationScanner.class);

}