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

import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.size.AMImageSizeProvider;
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = AMImageValidator.class)
public class AMImageValidatorImpl implements AMImageValidator {

	@Override
	public boolean isProcessingSupported(FileVersion fileVersion) {
		if (!isValid(fileVersion)) {
			return false;
		}

		if (Objects.equals(
				fileVersion.getMimeType(), ContentTypes.IMAGE_SVG_XML)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isValid(FileVersion fileVersion) {
		long imageMaxSize = _amImageSizeProvider.getImageMaxSize();

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

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AMImageSizeProvider _amImageSizeProvider;

}