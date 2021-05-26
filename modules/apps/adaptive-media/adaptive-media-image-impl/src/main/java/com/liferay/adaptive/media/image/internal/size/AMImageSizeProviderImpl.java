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

package com.liferay.adaptive.media.image.internal.size;

import com.liferay.adaptive.media.image.internal.configuration.AMImageConfiguration;
import com.liferay.adaptive.media.image.size.AMImageSizeProvider;
import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Roberto Díaz
 */
@Component(
	configurationPid = {
		"com.liferay.adaptive.media.image.internal.configuration.AMImageConfiguration",
		"com.liferay.document.library.configuration.DLFileEntryConfiguration"
	},
	service = AMImageSizeProvider.class
)
public class AMImageSizeProviderImpl implements AMImageSizeProvider {

	@Override
	public long getImageMaxSize() {
		if (_dlFileEntryConfiguration.previewableProcessorMaxSize() >
				_amImageConfiguration.imageMaxSize()) {

			return _amImageConfiguration.imageMaxSize();
		}

		return _dlFileEntryConfiguration.previewableProcessorMaxSize();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_amImageConfiguration = ConfigurableUtil.createConfigurable(
			AMImageConfiguration.class, properties);
		_dlFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);
	}

	private volatile AMImageConfiguration _amImageConfiguration;
	private volatile DLFileEntryConfiguration _dlFileEntryConfiguration;

}