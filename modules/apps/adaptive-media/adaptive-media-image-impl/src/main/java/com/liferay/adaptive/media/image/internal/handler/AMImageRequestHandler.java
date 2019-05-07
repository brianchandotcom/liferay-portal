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

package com.liferay.adaptive.media.image.internal.handler;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.handler.AMRequestHandler;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.internal.configuration.AMImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AMImage;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.adaptive.media.processor.AMAsyncProcessor;
import com.liferay.adaptive.media.processor.AMAsyncProcessorLocator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = "adaptive.media.handler.pattern=image",
	service = AMRequestHandler.class
)
public class AMImageRequestHandler
	implements AMRequestHandler<AMImageProcessor> {

	@Override
	public Optional<AdaptiveMedia<AMImageProcessor>> handleRequest(
			HttpServletRequest httpServletRequest)
		throws IOException, ServletException {

		Optional<Tuple<FileVersion, AMImageAttributeMapping>>
			interpretedPathOptional = _interpretPath(
				httpServletRequest.getPathInfo());

		return interpretedPathOptional.flatMap(
			tuple -> {
				Optional<AdaptiveMedia<AMImageProcessor>>
					adaptiveMediaOptional = _findAdaptiveMedia(
						tuple.first, tuple.second);

				adaptiveMediaOptional.ifPresent(
					adaptiveMedia -> _processAMImage(
						adaptiveMedia, tuple.first, tuple.second));

				return adaptiveMediaOptional;
			});
	}

	private AdaptiveMedia<AMImageProcessor> _createRawAdaptiveMedia(
			FileVersion fileVersion)
		throws PortalException {

		Map<String, String> properties = new HashMap<>();

		AMAttribute<Object, String> fileNameAMAttribute =
			AMAttribute.getFileNameAMAttribute();

		properties.put(
			fileNameAMAttribute.getName(), fileVersion.getFileName());

		AMAttribute<Object, String> contentTypeAMAttribute =
			AMAttribute.getContentTypeAMAttribute();

		properties.put(
			contentTypeAMAttribute.getName(), fileVersion.getMimeType());

		AMAttribute<Object, Long> contentLengthAMAttribute =
			AMAttribute.getContentLengthAMAttribute();

		properties.put(
			contentLengthAMAttribute.getName(),
			String.valueOf(fileVersion.getSize()));

		return new AMImage(
			() -> {
				try {
					return fileVersion.getContentStream(false);
				}
				catch (PortalException pe) {
					throw new AMRuntimeException(pe);
				}
			},
			AMImageAttributeMapping.fromProperties(properties), null);
	}

	private Optional<AdaptiveMedia<AMImageProcessor>> _findAdaptiveMedia(
		FileVersion fileVersion,
		AMImageAttributeMapping amImageAttributeMapping) {

		try {
			Optional<String> valueOptional =
				amImageAttributeMapping.getValueOptional(
					AMAttribute.getConfigurationUuidAMAttribute());

			Optional<AMImageConfigurationEntry>
				amImageConfigurationEntryOptional = valueOptional.flatMap(
					configurationUuid ->
						_amImageConfigurationHelper.
							getAMImageConfigurationEntry(
								fileVersion.getCompanyId(), configurationUuid));

			if (!amImageConfigurationEntryOptional.isPresent()) {
				return Optional.empty();
			}

			AMImageConfigurationEntry amImageConfigurationEntry =
				amImageConfigurationEntryOptional.get();

			Optional<AdaptiveMedia<AMImageProcessor>> adaptiveMediaOptional =
				_findExactAdaptiveMedia(fileVersion, amImageConfigurationEntry);

			if (adaptiveMediaOptional.isPresent()) {
				return adaptiveMediaOptional;
			}

			adaptiveMediaOptional = _findClosestAdaptiveMedia(
				fileVersion, amImageConfigurationEntry);

			if (adaptiveMediaOptional.isPresent()) {
				return adaptiveMediaOptional;
			}

			return Optional.of(_createRawAdaptiveMedia(fileVersion));
		}
		catch (PortalException pe) {
			throw new AMRuntimeException(pe);
		}
	}

	private Optional<AdaptiveMedia<AMImageProcessor>> _findClosestAdaptiveMedia(
		FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		final Integer configurationWidth = GetterUtil.getInteger(
			properties.get("max-width"));

		final Integer configurationHeight = GetterUtil.getInteger(
			properties.get("max-height"));

		try {
			Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
				_amImageFinder.getAdaptiveMediaStream(
					amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
						fileVersion
					).with(
						AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH,
						configurationWidth
					).with(
						AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT,
						configurationHeight
					).done());

			return adaptiveMediaStream.sorted(
				_getComparator(configurationWidth)
			).findFirst();
		}
		catch (PortalException pe) {
			throw new AMRuntimeException(pe);
		}
	}

	private Optional<AdaptiveMedia<AMImageProcessor>> _findExactAdaptiveMedia(
			FileVersion fileVersion,
			AMImageConfigurationEntry amImageConfigurationEntry)
		throws PortalException {

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					fileVersion
				).forConfiguration(
					amImageConfigurationEntry.getUUID()
				).done());

		return adaptiveMediaStream.findFirst();
	}

	private Comparator<AdaptiveMedia<AMImageProcessor>> _getComparator(
		Integer configurationWidth) {

		return Comparator.comparingInt(
			adaptiveMedia -> _getDistance(configurationWidth, adaptiveMedia));
	}

	private Integer _getDistance(
		int width, AdaptiveMedia<AMImageProcessor> adaptiveMedia) {

		Optional<Integer> imageWidthOptional = adaptiveMedia.getValueOptional(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);

		Optional<Integer> distanceOptional = imageWidthOptional.map(
			imageWidth -> Math.abs(imageWidth - width));

		return distanceOptional.orElse(Integer.MAX_VALUE);
	}

	private Optional<Tuple<FileVersion, AMImageAttributeMapping>>
		_interpretPath(String pathInfo) {

		try {
			Optional<Tuple<FileVersion, Map<String, String>>>
				fileVersionPropertiesTupleOptional =
					_pathInterpreter.interpretPath(pathInfo);

			if (!fileVersionPropertiesTupleOptional.isPresent()) {
				return Optional.empty();
			}

			Tuple<FileVersion, Map<String, String>> fileVersionMapTuple =
				fileVersionPropertiesTupleOptional.get();

			FileVersion fileVersion = fileVersionMapTuple.first;

			if (fileVersion.getStatus() == WorkflowConstants.STATUS_IN_TRASH) {
				return Optional.empty();
			}

			Map<String, String> properties = fileVersionMapTuple.second;

			AMAttribute<Object, Long> contentLengthAMAttribute =
				AMAttribute.getContentLengthAMAttribute();

			properties.put(
				contentLengthAMAttribute.getName(),
				String.valueOf(fileVersion.getSize()));

			AMAttribute<Object, String> contentTypeAMAttribute =
				AMAttribute.getContentTypeAMAttribute();

			properties.put(
				contentTypeAMAttribute.getName(), fileVersion.getMimeType());

			AMAttribute<Object, String> fileNameAMAttribute =
				AMAttribute.getFileNameAMAttribute();

			properties.put(
				fileNameAMAttribute.getName(), fileVersion.getFileName());

			AMImageAttributeMapping amImageAttributeMapping =
				AMImageAttributeMapping.fromProperties(properties);

			return Optional.of(Tuple.of(fileVersion, amImageAttributeMapping));
		}
		catch (AMRuntimeException | NumberFormatException e) {
			_log.error(e, e);

			return Optional.empty();
		}
	}

	private void _processAMImage(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia, FileVersion fileVersion,
		AMImageAttributeMapping amImageAttributeMapping) {

		Optional<String> adaptiveMediaConfigurationUuidOptional =
			adaptiveMedia.getValueOptional(
				AMAttribute.getConfigurationUuidAMAttribute());

		Optional<String> attributeMappingConfigurationUuidOptional =
			amImageAttributeMapping.getValueOptional(
				AMAttribute.getConfigurationUuidAMAttribute());

		if (adaptiveMediaConfigurationUuidOptional.equals(
				attributeMappingConfigurationUuidOptional)) {

			return;
		}

		try {
			AMAsyncProcessor<FileVersion, ?> amAsyncProcessor =
				_amAsyncProcessorLocator.locateForClass(FileVersion.class);

			amAsyncProcessor.triggerProcess(
				fileVersion, String.valueOf(fileVersion.getFileVersionId()));
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to create lazy adaptive media for file version " +
					fileVersion.getFileVersionId(),
				pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageRequestHandler.class);

	@Reference
	private AMAsyncProcessorLocator _amAsyncProcessorLocator;

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private PathInterpreter _pathInterpreter;

}