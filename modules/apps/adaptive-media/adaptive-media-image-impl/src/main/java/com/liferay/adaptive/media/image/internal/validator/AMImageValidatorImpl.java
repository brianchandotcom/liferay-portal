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

package com.liferay.adaptive.media.image.internal.validator;

import com.liferay.adaptive.media.image.internal.configuration.AMImageConfiguration;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.adaptive.media.image.internal.configuration.AMImageConfiguration",
	service = AMImageValidator.class
)
public class AMImageValidatorImpl implements AMImageValidator {

	@Override
	public boolean isValid(FileVersion fileVersion) {
		if (!DLProcessorRegistryUtil.isPreviewableSize(fileVersion)) {
			return false;
		}

		long imageMaxSize = _amImageConfiguration.imageMaxSize();

		if ((imageMaxSize != -1) &&
			((imageMaxSize == 0) || (fileVersion.getSize() == 0) ||
			 (fileVersion.getSize() >= imageMaxSize))) {

			return false;
		}

		if (!_amImageMimeTypeProvider.isMimeTypeSupported(
				fileVersion.getMimeType())) {

			return false;
		}

		return true;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_amImageConfiguration = ConfigurableUtil.createConfigurable(
			AMImageConfiguration.class, properties);
	}

	private volatile AMImageConfiguration _amImageConfiguration;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

}