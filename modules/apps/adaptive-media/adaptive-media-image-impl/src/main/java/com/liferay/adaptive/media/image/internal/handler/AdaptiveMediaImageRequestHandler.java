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

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.exception.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.handler.AdaptiveMediaRequestHandler;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AdaptiveMediaImageFinder;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveMediaImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessorLocator;
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
	service = AdaptiveMediaRequestHandler.class
)
public class AdaptiveMediaImageRequestHandler
	implements AdaptiveMediaRequestHandler<AdaptiveMediaImageProcessor> {

	@Override
	public Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>> handleRequest(
			HttpServletRequest request)
		throws IOException, ServletException {

		Optional<Tuple<FileVersion, AdaptiveMediaImageAttributeMapping>>
			interpretedPathOptional = _interpretPath(request.getPathInfo());

		return interpretedPathOptional.flatMap(
			tuple -> {
				Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
					adaptiveMediaOptional = _findAdaptiveMedia(
						tuple.first, tuple.second);

				adaptiveMediaOptional.ifPresent(
					adaptiveMedia -> _processAdaptiveMediaImage(
						adaptiveMedia, tuple.first, tuple.second));

				return adaptiveMediaOptional;
			});
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaImageConfigurationHelper(
		AdaptiveMediaImageConfigurationHelper configurationHelper) {

		_configurationHelper = configurationHelper;
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaImageFinder(AdaptiveMediaImageFinder finder) {
		_finder = finder;
	}

	@Reference(unbind = "-")
	public void setAsyncProcessorLocator(
		AdaptiveMediaAsyncProcessorLocator asyncProcessorLocator) {

		_asyncProcessorLocator = asyncProcessorLocator;
	}

	@Reference(unbind = "-")
	public void setPathInterpreter(PathInterpreter pathInterpreter) {
		_pathInterpreter = pathInterpreter;
	}

	private AdaptiveMedia<AdaptiveMediaImageProcessor>
			_createRawAdaptiveMedia(FileVersion fileVersion)
		throws PortalException {

		Map<String, String> properties = new HashMap<>();

		AdaptiveMediaAttribute<Object, String> fileName =
			AdaptiveMediaAttribute.getFileName();

		properties.put(fileName.getName(), fileVersion.getFileName());

		AdaptiveMediaAttribute<Object, String> contentType =
			AdaptiveMediaAttribute.getContentType();

		properties.put(contentType.getName(), fileVersion.getMimeType());

		AdaptiveMediaAttribute<Object, Integer> contentLength =
			AdaptiveMediaAttribute.getContentLength();

		properties.put(
			contentLength.getName(), String.valueOf(fileVersion.getSize()));

		return new AdaptiveMediaImage(
			() -> {
				try {
					return fileVersion.getContentStream(false);
				}
				catch (PortalException pe) {
					throw new AdaptiveMediaRuntimeException(pe);
				}
			},
			AdaptiveMediaImageAttributeMapping.fromProperties(properties),
			null);
	}

	private Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
		_findAdaptiveMedia(
			FileVersion fileVersion,
			AdaptiveMediaImageAttributeMapping attributeMapping) {

		try {
			Optional<AdaptiveMediaImageConfigurationEntry>
				configurationEntryOptional = attributeMapping.getValueOptional(
					AdaptiveMediaAttribute.getConfigurationUuid()
				).flatMap(
					configurationUuid ->
						_configurationHelper.
							getAdaptiveMediaImageConfigurationEntry(
								fileVersion.getCompanyId(), configurationUuid)
				);

			if (!configurationEntryOptional.isPresent()) {
				return Optional.empty();
			}

			AdaptiveMediaImageConfigurationEntry configurationEntry =
				configurationEntryOptional.get();

			Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
				adaptiveMediaOptional = _findExactAdaptiveMedia(
					fileVersion, configurationEntry);

			if (adaptiveMediaOptional.isPresent()) {
				return adaptiveMediaOptional;
			}

			adaptiveMediaOptional = _findClosestAdaptiveMedia(
				fileVersion, configurationEntry);

			if (adaptiveMediaOptional.isPresent()) {
				return adaptiveMediaOptional;
			}

			return Optional.of(_createRawAdaptiveMedia(fileVersion));
		}
		catch (PortalException pe) {
			throw new AdaptiveMediaRuntimeException(pe);
		}
	}

	private Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
		_findClosestAdaptiveMedia(
			FileVersion fileVersion,
			AdaptiveMediaImageConfigurationEntry configurationEntry) {

		Map<String, String> properties = configurationEntry.getProperties();

		final Integer configurationWidth = GetterUtil.getInteger(
			properties.get("max-width"));

		final Integer configurationHeight = GetterUtil.getInteger(
			properties.get("max-height"));

		try {
			Stream<AdaptiveMedia<AdaptiveMediaImageProcessor>>
				adaptiveMediaStream = _finder.getAdaptiveMediaStream(
					queryBuilder -> queryBuilder.forVersion(
						fileVersion
					).with(
						AdaptiveMediaImageAttribute.IMAGE_WIDTH,
						configurationWidth
					).with(
						AdaptiveMediaImageAttribute.IMAGE_HEIGHT,
						configurationHeight
					).done());

			return adaptiveMediaStream.sorted(
				_getComparator(configurationWidth)
			).findFirst();
		}
		catch (PortalException pe) {
			throw new AdaptiveMediaRuntimeException(pe);
		}
	}

	private Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
			_findExactAdaptiveMedia(
				FileVersion fileVersion,
				AdaptiveMediaImageConfigurationEntry configurationEntry)
		throws PortalException {

		return _finder.getAdaptiveMediaStream(queryBuilder ->
			queryBuilder.forVersion(
				fileVersion
			).forConfiguration(
				configurationEntry.getUUID()
			).done()
		).findFirst();
	}

	private Comparator<AdaptiveMedia<AdaptiveMediaImageProcessor>>
		_getComparator(Integer configurationWidth) {

		return Comparator.comparingInt(
			adaptiveMedia -> _getDistance(configurationWidth, adaptiveMedia));
	}

	private Integer _getDistance(
		int width, AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia) {

		Optional<Integer> imageWidthOptional = adaptiveMedia.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH);

		Optional<Integer> distanceOptional = imageWidthOptional.map(
			imageWidth -> Math.abs(imageWidth - width));

		return distanceOptional.orElse(Integer.MAX_VALUE);
	}

	private Optional<Tuple<FileVersion, AdaptiveMediaImageAttributeMapping>>
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

			AdaptiveMediaAttribute<Object, Integer> contentLengthAttribute =
				AdaptiveMediaAttribute.getContentLength();

			properties.put(
				contentLengthAttribute.getName(),
				String.valueOf(fileVersion.getSize()));

			AdaptiveMediaAttribute<Object, String> contentTypeAttribute =
				AdaptiveMediaAttribute.getContentType();

			properties.put(
				contentTypeAttribute.getName(), fileVersion.getMimeType());

			AdaptiveMediaAttribute<Object, String> fileNameAttribute =
				AdaptiveMediaAttribute.getFileName();

			properties.put(
				fileNameAttribute.getName(), fileVersion.getFileName());

			AdaptiveMediaImageAttributeMapping attributeMapping =
				AdaptiveMediaImageAttributeMapping.fromProperties(properties);

			return Optional.of(Tuple.of(fileVersion, attributeMapping));
		}
		catch (AdaptiveMediaRuntimeException | NumberFormatException e) {
			_log.error(e);

			return Optional.empty();
		}
	}

	private void _processAdaptiveMediaImage(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia,
		FileVersion fileVersion,
		AdaptiveMediaImageAttributeMapping attributeMapping) {

		Optional<String> adaptiveMediaConfigurationUuidOptional =
			adaptiveMedia.getValueOptional(
				AdaptiveMediaAttribute.getConfigurationUuid());

		Optional<String> attributeMappingConfigurationUuidOptional =
			attributeMapping.getValueOptional(
				AdaptiveMediaAttribute.getConfigurationUuid());

		if (adaptiveMediaConfigurationUuidOptional.equals(
				attributeMappingConfigurationUuidOptional)) {

			return;
		}

		try {
			AdaptiveMediaAsyncProcessor<FileVersion, ?> asyncProcessor =
				_asyncProcessorLocator.locateForClass(FileVersion.class);

			asyncProcessor.triggerProcess(
				fileVersion, String.valueOf(fileVersion.getFileVersionId()));
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to create lazy adaptive media for file version id " +
					fileVersion.getFileVersionId(),
				pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdaptiveMediaImageRequestHandler.class);

	private AdaptiveMediaAsyncProcessorLocator _asyncProcessorLocator;
	private AdaptiveMediaImageConfigurationHelper _configurationHelper;
	private AdaptiveMediaImageFinder _finder;
	private PathInterpreter _pathInterpreter;

}