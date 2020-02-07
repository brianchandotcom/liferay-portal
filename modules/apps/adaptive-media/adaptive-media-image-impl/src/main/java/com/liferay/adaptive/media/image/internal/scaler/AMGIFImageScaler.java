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
import com.liferay.adaptive.media.image.internal.configuration.AMImageConfiguration;
import com.liferay.adaptive.media.image.internal.util.RenderedImageUtil;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.adaptive.media.image.scaler.AMImageScaledImage;
import com.liferay.adaptive.media.image.scaler.AMImageScaler;
import com.liferay.petra.process.CollectorOutputProcessor;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.ImageResolutionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.adaptive.media.image.internal.configuration.AMImageConfiguration",
	immediate = true, property = "mime.type=image/gif",
	service = AMImageScaler.class
)
public class AMGIFImageScaler implements AMImageScaler {

	@Override
	public boolean isEnabled() {
		return _amImageConfiguration.gifsicleEnabled();
	}

	@Override
	public AMImageScaledImage scaleImage(
		FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		try {
			File file = _getFile(fileVersion);

			Future<Map.Entry<byte[], byte[]>> collectorFuture =
				ProcessUtil.execute(
					CollectorOutputProcessor.INSTANCE, "gifsicle",
					"--resize-fit",
					getResizeFitValues(amImageConfigurationEntry), "--output",
					"-", file.getAbsolutePath());

			Map.Entry<byte[], byte[]> objectValuePair = collectorFuture.get();

			file.delete();

			byte[] bytes = objectValuePair.getKey();

			Tuple<Integer, Integer> dimension = getDimension(bytes);

			return new AMImageScaledImageImpl(
				bytes, dimension.second, dimension.first);
		}
		catch (ExecutionException | InterruptedException | IOException |
			   PortalException | ProcessException exception) {

			throw new AMRuntimeException.IOException(exception);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_amImageConfiguration = ConfigurableUtil.createConfigurable(
			AMImageConfiguration.class, properties);
	}

	protected Tuple<Integer, Integer> getDimension(byte[] bytes)
		throws ImageResolutionException, IOException {

		try (InputStream inputStream = new UnsyncByteArrayInputStream(bytes)) {
			RenderedImage renderedImage = RenderedImageUtil.readImage(
				inputStream);

			return Tuple.of(
				renderedImage.getWidth(), renderedImage.getHeight());
		}
	}

	protected String getResizeFitValues(
		AMImageConfigurationEntry amImageConfigurationEntry) {

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		int maxHeight = GetterUtil.getInteger(properties.get("max-height"));

		String maxHeightString = StringPool.UNDERLINE;

		if (maxHeight != 0) {
			maxHeightString = String.valueOf(maxHeight);
		}

		int maxWidth = GetterUtil.getInteger(properties.get("max-width"));

		String maxWidthString = StringPool.UNDERLINE;

		if (maxWidth != 0) {
			maxWidthString = String.valueOf(maxWidth);
		}

		return maxWidthString.concat(
			"x"
		).concat(
			maxHeightString
		);
	}

	private File _getFile(FileVersion fileVersion)
		throws IOException, PortalException {

		try (InputStream inputStream = fileVersion.getContentStream(false)) {
			return FileUtil.createTempFile(inputStream);
		}
	}

	private volatile AMImageConfiguration _amImageConfiguration;

}