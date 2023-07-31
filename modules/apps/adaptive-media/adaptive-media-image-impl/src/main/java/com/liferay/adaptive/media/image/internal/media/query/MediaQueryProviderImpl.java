/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

		List<MediaQuery> mediaQueries = new ArrayList<>();

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			TransformUtil.transform(
				_amImageConfigurationHelper.getAMImageConfigurationEntries(
					fileEntry.getCompanyId()),
				amImageConfigurationEntry -> {
					AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia =
						_getAdaptiveMediaFromConfigurationEntry(
							fileEntry, amImageConfigurationEntry);

					if (_getWidth(adaptiveMedia) <= 0) {
						return null;
					}

					return adaptiveMedia;
				});

		adaptiveMedias.sort(_comparator);

		AdaptiveMedia<AMProcessor<FileVersion>> previousAdaptiveMedia = null;

		for (AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia :
				adaptiveMedias) {

			AdaptiveMedia<AMProcessor<FileVersion>> hdAdaptiveMedia =
				_getHDAdaptiveMedia(adaptiveMedia, adaptiveMedias);

			mediaQueries.add(
				_getMediaQuery(
					adaptiveMedia, previousAdaptiveMedia, hdAdaptiveMedia));

			previousAdaptiveMedia = adaptiveMedia;
		}

		return mediaQueries;
	}

	private AdaptiveMedia<AMProcessor<FileVersion>> _findAdaptiveMedia(
		FileEntry fileEntry,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		try {
			List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
				_amImageFinder.getAdaptiveMedias(
					amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
						fileEntry
					).forConfiguration(
						amImageConfigurationEntry.getUUID()
					).done());

			if (adaptiveMedias.isEmpty()) {
				return null;
			}

			return adaptiveMedias.get(0);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return null;
		}
	}

	private AdaptiveMedia<AMProcessor<FileVersion>>
		_getAdaptiveMediaFromConfigurationEntry(
			FileEntry fileEntry,
			AMImageConfigurationEntry amImageConfigurationEntry) {

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia =
			_findAdaptiveMedia(fileEntry, amImageConfigurationEntry);

		if (adaptiveMedia != null) {
			return adaptiveMedia;
		}

		Map<String, String> properties = HashMapBuilder.put(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(),
			String.valueOf(
				GetterUtil.getInteger(
					_getPropertiesValue(
						amImageConfigurationEntry, "max-width")))
		).put(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(),
			String.valueOf(
				GetterUtil.getInteger(
					_getPropertiesValue(
						amImageConfigurationEntry, "max-height")))
		).build();

		return new AMImage(
			() -> null, AMImageAttributeMapping.fromProperties(properties),
			_getFileEntryURL(fileEntry, amImageConfigurationEntry));
	}

	private List<Condition> _getConditions(
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia,
		AdaptiveMedia<AMProcessor<FileVersion>> previousAdaptiveMedia) {

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
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private AdaptiveMedia<AMProcessor<FileVersion>> _getHDAdaptiveMedia(
		AdaptiveMedia<AMProcessor<FileVersion>> originalAdaptiveMedia,
		Collection<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias) {

		int originalWidth = _getWidth(originalAdaptiveMedia) * 2;
		int originalHeight = _getHeight(originalAdaptiveMedia) * 2;

		for (AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia :
				adaptiveMedias) {

			if ((Math.abs(originalWidth - _getWidth(adaptiveMedia)) <= 1) &&
				(Math.abs(originalHeight - _getHeight(adaptiveMedia)) <= 1)) {

				return adaptiveMedia;
			}
		}

		return null;
	}

	private Integer _getHeight(
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia) {

		Integer height = adaptiveMedia.getValue(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);

		if (height == null) {
			return 0;
		}

		return height;
	}

	private MediaQuery _getMediaQuery(
			AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia,
			AdaptiveMedia<AMProcessor<FileVersion>> previousAdaptiveMedia,
			AdaptiveMedia<AMProcessor<FileVersion>> hdAdaptiveMedia)
		throws PortalException {

		StringBundler sb = new StringBundler(4);

		List<Condition> conditions = _getConditions(
			adaptiveMedia, previousAdaptiveMedia);

		sb.append(adaptiveMedia.getURI());

		if (hdAdaptiveMedia != null) {
			sb.append(", ");
			sb.append(hdAdaptiveMedia.getURI());
			sb.append(" 2x");
		}

		return new MediaQuery(conditions, sb.toString());
	}

	private Integer _getPropertiesValue(
		AMImageConfigurationEntry amImageConfigurationEntry, String name) {

		try {
			Map<String, String> properties =
				amImageConfigurationEntry.getProperties();

			return Integer.valueOf(properties.get(name));
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isDebugEnabled()) {
				_log.debug(numberFormatException);
			}

			return null;
		}
	}

	private Integer _getWidth(
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia) {

		Integer width = adaptiveMedia.getValue(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);

		if (width == null) {
			return 0;
		}

		return width;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MediaQueryProviderImpl.class);

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private AMImageURLFactory _amImageURLFactory;

	private final Comparator<AdaptiveMedia<AMProcessor<FileVersion>>>
		_comparator = Comparator.comparingInt(this::_getWidth);

}