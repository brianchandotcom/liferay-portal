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

package com.liferay.layout.seo.web.internal.configuration.util;

import com.liferay.layout.seo.web.internal.configuration.FFMappingInputConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	configurationPid = "com.liferay.layout.seo.web.internal.configuration.FFMappingInputConfiguration",
	immediate = true, service = {}
)
public class FFMappingInputConfigurationUtil {

	public static boolean enabled() {
		return _ffMappingInputConfiguration.enabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffMappingInputConfiguration = ConfigurableUtil.createConfigurable(
			FFMappingInputConfiguration.class, properties);
	}

	private static volatile FFMappingInputConfiguration
		_ffMappingInputConfiguration;

}