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

package com.liferay.adaptive.media.image.internal.mime.type;

import com.liferay.adaptive.media.image.internal.configuration.AMImageConfiguration;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.adaptive.media.image.internal.configuration.AMImageConfiguration",
	service = AMImageMimeTypeProvider.class
)
public class AMImageMimeTypeProviderImpl implements AMImageMimeTypeProvider {

	@Override
	public String[] getSupportedMimeTypes() {
		return _amImageConfiguration.supportedMimeTypes();
	}

	@Override
	public boolean isMimeTypeSupported(String mimeType) {
		return _supportedMimeTypes.contains(mimeType);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_amImageConfiguration = ConfigurableUtil.createConfigurable(
			AMImageConfiguration.class, properties);

		_supportedMimeTypes = new HashSet<>(
			Arrays.asList(_amImageConfiguration.supportedMimeTypes()));
	}

	private AMImageConfiguration _amImageConfiguration;
	private Set<String> _supportedMimeTypes;

}