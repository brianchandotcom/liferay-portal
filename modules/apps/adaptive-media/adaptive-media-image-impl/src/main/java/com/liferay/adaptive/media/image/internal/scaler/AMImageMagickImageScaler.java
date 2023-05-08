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

package com.liferay.adaptive.media.image.internal.scaler;

import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.scaler.AMImageScaledImage;
import com.liferay.adaptive.media.image.scaler.AMImageScaler;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageMagick;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Yan
 */
@Component(
	configurationPid = "com.liferay.adaptive.media.image.internal.configuration.AMImageMagickConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = AMImageScaler.class
)
public class AMImageMagickImageScaler implements AMImageScaler {

	@Override
	public boolean isEnabled() {
		if (_imageMagick.isEnabled()) {
			return true;
		}

		return false;
	}

	@Override
	public AMImageScaledImage scaleImage(
		FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		File imageFile = null;
		File scaledImageFile = null;

		try {
			imageFile = _getFile(fileVersion);

			scaledImageFile = _scaleAndConvertToPNG(
				amImageConfigurationEntry, imageFile);

			if (!scaledImageFile.exists()) {
				throw new AMRuntimeException.IOException(
					"Unable to scale image using ImageMagick");
			}

			ImageBag imageBag = _imageTool.read(scaledImageFile);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			return new AMImageScaledImageImpl(
				_imageTool.getBytes(renderedImage, imageBag.getType()),
				renderedImage.getHeight(), ContentTypes.IMAGE_PNG,
				renderedImage.getWidth());
		}
		catch (Exception exception) {
			throw new AMRuntimeException.IOException(
				StringBundler.concat(
					"Unable to scale file entry ", fileVersion.getFileEntryId(),
					" to match adaptive media configuration ",
					amImageConfigurationEntry.getUUID()),
				exception);
		}
		finally {
			if (imageFile != null) {
				imageFile.delete();
			}

			if (scaledImageFile != null) {
				scaledImageFile.delete();
			}
		}
	}

	private File _getFile(FileVersion fileVersion)
		throws IOException, PortalException {

		try (InputStream inputStream = fileVersion.getContentStream(false)) {
			return _file.createTempFile(inputStream);
		}
	}

	private String _getResizeArg(
		AMImageConfigurationEntry amImageConfigurationEntry) {

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		int maxHeight = GetterUtil.getInteger(properties.get("max-height"));
		int maxWidth = GetterUtil.getInteger(properties.get("max-width"));

		if ((maxHeight > 0) && (maxWidth > 0)) {
			return StringBundler.concat(maxWidth, "x", maxHeight, ">");
		}

		return null;
	}

	private File _scaleAndConvertToPNG(
			AMImageConfigurationEntry amImageConfigurationEntry, File imageFile)
		throws Exception {

		File scaledImageFile = _file.createTempFile(ImageTool.TYPE_PNG);

		List<String> arguments = new ArrayList<>();

		arguments.add(imageFile.getAbsolutePath());

		String resizeArg = _getResizeArg(amImageConfigurationEntry);

		if (resizeArg != null) {
			arguments.add("-resize");
			arguments.add(resizeArg);
		}

		arguments.add(scaledImageFile.getAbsolutePath());

		Future<?> future = _imageMagick.convert(arguments);

		future.get();

		return scaledImageFile;
	}

	@Reference
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private ImageMagick _imageMagick;

	@Reference
	private ImageTool _imageTool;

}