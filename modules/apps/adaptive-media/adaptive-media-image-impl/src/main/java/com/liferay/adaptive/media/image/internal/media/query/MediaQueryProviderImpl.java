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

package com.liferay.adaptive.media.image.internal.media.query;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.internal.configuration.AMImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AMImage;
import com.liferay.adaptive.media.image.media.query.Condition;
import com.liferay.adaptive.media.image.media.query.MediaQuery;
import com.liferay.adaptive.media.image.media.query.MediaQueryProvider;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = MediaQueryProvider.class)
public class MediaQueryProviderImpl implements MediaQueryProvider {

	@Override
	public List<MediaQuery> getMediaQueries(FileEntry fileEntry)
		throws PortalException {

		Collection<AdaptiveMedia<AMImageProcessor>> adaptiveMedias =
			_getAdaptiveMedias(fileEntry);

		List<MediaQuery> mediaQueries = new ArrayList<>();
		AdaptiveMedia<AMImageProcessor> previousAdaptiveMedia = null;

		for (AdaptiveMedia<AMImageProcessor> adaptiveMedia : adaptiveMedias) {
			Optional<AdaptiveMedia<AMImageProcessor>> hdAdaptiveMediaOptional =
				_getHDAdaptiveMedia(adaptiveMedia, adaptiveMedias);

			mediaQueries.add(
				_getMediaQuery(
					adaptiveMedia, previousAdaptiveMedia,
					hdAdaptiveMediaOptional));

			previousAdaptiveMedia = adaptiveMedia;
		}

		return mediaQueries;
	}

	private Optional<AdaptiveMedia<AMImageProcessor>> _findAdaptiveMedia(
		FileEntry fileEntry,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		try {
			Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
				_amImageFinder.getAdaptiveMediaStream(
					amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
						fileEntry
					).forConfiguration(
						amImageConfigurationEntry.getUUID()
					).done());

			return adaptiveMediaStream.findFirst();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return Optional.empty();
		}
	}

	private AdaptiveMedia<AMImageProcessor>
		_getAdaptiveMediaFromConfigurationEntry(
			FileEntry fileEntry,
			AMImageConfigurationEntry amImageConfigurationEntry) {

		Optional<AdaptiveMedia<AMImageProcessor>> adaptiveMediaOptional =
			_findAdaptiveMedia(fileEntry, amImageConfigurationEntry);

		if (adaptiveMediaOptional.isPresent()) {
			return adaptiveMediaOptional.get();
		}

		Optional<Integer> widthOptional = _getWidth(amImageConfigurationEntry);
		Optional<Integer> heightOptional = _getHeight(
			amImageConfigurationEntry);

		Map<String, String> properties = new HashMap<>();

		properties.put(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(),
			String.valueOf(widthOptional.orElse(0)));

		properties.put(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(),
			String.valueOf(heightOptional.orElse(0)));

		return new AMImage(
			() -> null, AMImageAttributeMapping.fromProperties(properties),
			_getFileEntryURL(fileEntry, amImageConfigurationEntry));
	}

	private Collection<AdaptiveMedia<AMImageProcessor>> _getAdaptiveMedias(
			FileEntry fileEntry)
		throws PortalException {

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				fileEntry.getCompanyId());

		List<AdaptiveMedia<AMImageProcessor>> adaptiveMedias =
			new ArrayList<>();

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			AdaptiveMedia<AMImageProcessor> adaptiveMedia =
				_getAdaptiveMediaFromConfigurationEntry(
					fileEntry, amImageConfigurationEntry);

			if (_getWidth(adaptiveMedia) > 0) {
				adaptiveMedias.add(adaptiveMedia);
			}
		}

		Collections.sort(adaptiveMedias, _comparator);

		return adaptiveMedias;
	}

	private List<Condition> _getConditions(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia,
		AdaptiveMedia<AMImageProcessor> previousAdaptiveMedia) {

		List<Condition> conditions = new ArrayList<>();

		conditions.add(
			new Condition("max-width", _getWidth(adaptiveMedia) + "px"));

		if (previousAdaptiveMedia != null) {
			conditions.add(
				new Condition(
					"min-width", _getWidth(previousAdaptiveMedia) + "px"));
		}

		return conditions;
	}

	private URI _getFileEntryURL(
		FileEntry fileEntry,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		try {
			return _amImageURLFactory.createFileEntryURL(
				fileEntry.getFileVersion(), amImageConfigurationEntry);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private Optional<AdaptiveMedia<AMImageProcessor>> _getHDAdaptiveMedia(
		AdaptiveMedia<AMImageProcessor> originalAdaptiveMedia,
		Collection<AdaptiveMedia<AMImageProcessor>> adaptiveMedias) {

		for (AdaptiveMedia<AMImageProcessor> adaptiveMedia : adaptiveMedias) {
			int originalWidth = _getWidth(originalAdaptiveMedia) * 2;
			int originalHeight = _getHeight(originalAdaptiveMedia) * 2;

			IntStream widthIntStream = IntStream.range(
				originalWidth - 1, originalWidth + 2);

			boolean widthMatch = widthIntStream.anyMatch(
				value -> value == _getWidth(adaptiveMedia));

			IntStream heightIntStream = IntStream.range(
				originalHeight - 1, originalHeight + 2);

			boolean heightMatch = heightIntStream.anyMatch(
				value -> value == _getHeight(adaptiveMedia));

			if (widthMatch && heightMatch) {
				return Optional.of(adaptiveMedia);
			}
		}

		return Optional.empty();
	}

	private Integer _getHeight(AdaptiveMedia<AMImageProcessor> adaptiveMedia) {
		Optional<Integer> optional = adaptiveMedia.getValueOptional(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);

		return optional.orElse(0);
	}

	private Optional<Integer> _getHeight(
		AMImageConfigurationEntry originalAMImageConfigurationEntry) {

		return _getPropertiesValue(
			originalAMImageConfigurationEntry, "max-height");
	}

	private MediaQuery _getMediaQuery(
			AdaptiveMedia<AMImageProcessor> adaptiveMedia,
			AdaptiveMedia<AMImageProcessor> previousAdaptiveMedia,
			Optional<AdaptiveMedia<AMImageProcessor>> hdAdaptiveMediaOptional)
		throws PortalException {

		StringBundler sb = new StringBundler(4);

		List<Condition> conditions = _getConditions(
			adaptiveMedia, previousAdaptiveMedia);

		sb.append(adaptiveMedia.getURI());

		hdAdaptiveMediaOptional.ifPresent(
			hdAdaptiveMedia -> {
				sb.append(", ");
				sb.append(hdAdaptiveMedia.getURI());
				sb.append(" 2x");
			});

		return new MediaQuery(conditions, sb.toString());
	}

	private Optional<Integer> _getPropertiesValue(
		AMImageConfigurationEntry amImageConfigurationEntry, String name) {

		try {
			Map<String, String> properties =
				amImageConfigurationEntry.getProperties();

			Integer height = Integer.valueOf(properties.get(name));

			return Optional.of(height);
		}
		catch (NumberFormatException nfe) {
			return Optional.empty();
		}
	}

	private Integer _getWidth(AdaptiveMedia<AMImageProcessor> adaptiveMedia) {
		Optional<Integer> attributeValue = adaptiveMedia.getValueOptional(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);

		return attributeValue.orElse(0);
	}

	private Optional<Integer> _getWidth(
		AMImageConfigurationEntry amImageConfigurationEntry) {

		return _getPropertiesValue(amImageConfigurationEntry, "max-width");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MediaQueryProviderImpl.class);

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private AMImageURLFactory _amImageURLFactory;

	private final Comparator<AdaptiveMedia<AMImageProcessor>> _comparator =
		Comparator.comparingInt(this::_getWidth);

}