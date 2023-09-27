/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.finder;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AMDistanceComparator;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.finder.AMQuery;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.AMImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AMImage;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;

import java.net.URI;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileVersion",
	service = AMImageFinder.class
)
public class AMImageFinderImpl implements AMImageFinder {

	@Override
	public List<AdaptiveMedia<AMProcessor<FileVersion>>> getAdaptiveMedias(
			Function
				<AMImageQueryBuilder,
				 AMQuery<FileVersion, AMProcessor<FileVersion>>>
					amImageQueryBuilderFunction)
		throws PortalException {

		if (amImageQueryBuilderFunction == null) {
			throw new IllegalArgumentException(
				"Adaptive media image query builder is null");
		}

		AMImageQueryBuilderImpl amImageQueryBuilderImpl =
			new AMImageQueryBuilderImpl();

		AMQuery<FileVersion, AMProcessor<FileVersion>> amQuery =
			amImageQueryBuilderFunction.apply(amImageQueryBuilderImpl);

		if (amQuery != AMImageQueryBuilderImpl.AM_QUERY) {
			throw new IllegalArgumentException(
				"Only queries built by the provided query builder are valid");
		}

		FileVersion fileVersion = amImageQueryBuilderImpl.getFileVersion();

		if (!_amImageMimeTypeProvider.isMimeTypeSupported(
				fileVersion.getMimeType())) {

			return Collections.emptyList();
		}

		String mimeType = fileVersion.getMimeType();

		if (mimeType.equals(ContentTypes.IMAGE_SVG_XML)) {
			return Collections.singletonList(
				_createRawAdaptiveMedia(fileVersion));
		}

		BiFunction<FileVersion, AMImageConfigurationEntry, URI> uriFactory =
			_getURIFactory(amImageQueryBuilderImpl);

		AMImageQueryBuilder.ConfigurationStatus configurationStatus =
			amImageQueryBuilderImpl.getConfigurationStatus();

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				fileVersion.getCompanyId(), configurationStatus.getPredicate());

		Predicate<AMImageConfigurationEntry> filter =
			amImageQueryBuilderImpl.getConfigurationEntryFilter();

		List<AdaptiveMedia<AMProcessor<FileVersion>>> adaptiveMedias =
			TransformUtil.transform(
				amImageConfigurationEntries,
				amImageConfigurationEntry -> {
					if (filter.test(amImageConfigurationEntry) &&
						_hasAdaptiveMedia(
							fileVersion, amImageConfigurationEntry)) {

						return _createMedia(
							fileVersion, uriFactory, amImageConfigurationEntry);
					}

					return null;
				});

		AMDistanceComparator<AdaptiveMedia<AMProcessor<FileVersion>>>
			amDistanceComparator =
				amImageQueryBuilderImpl.getAMDistanceComparator();

		adaptiveMedias.sort(amDistanceComparator.toComparator());

		return adaptiveMedias;
	}

	private AdaptiveMedia<AMProcessor<FileVersion>> _createMedia(
		FileVersion fileVersion,
		BiFunction<FileVersion, AMImageConfigurationEntry, URI> uriFactory,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

		AMAttribute<Object, String> configurationUuidAMAttribute =
			AMAttribute.getConfigurationUuidAMAttribute();

		properties.put(
			configurationUuidAMAttribute.getName(),
			amImageConfigurationEntry.getUUID());

		AMAttribute<Object, String> fileNameAMAttribute =
			AMAttribute.getFileNameAMAttribute();

		properties.put(
			fileNameAMAttribute.getName(), fileVersion.getFileName());

		AMImageEntry amImageEntry = _amImageEntryLocalService.fetchAMImageEntry(
			amImageConfigurationEntry.getUUID(),
			fileVersion.getFileVersionId());

		if (amImageEntry != null) {
			AMAttribute<AMProcessor<FileVersion>, Integer>
				imageHeightAMAttribute =
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT;

			properties.put(
				imageHeightAMAttribute.getName(),
				String.valueOf(amImageEntry.getHeight()));

			AMAttribute<AMProcessor<FileVersion>, Integer>
				imageWidthAMAttribute =
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH;

			properties.put(
				imageWidthAMAttribute.getName(),
				String.valueOf(amImageEntry.getWidth()));

			AMAttribute<Object, String> contentTypeAMAttribute =
				AMAttribute.getContentTypeAMAttribute();

			properties.put(
				contentTypeAMAttribute.getName(), amImageEntry.getMimeType());

			AMAttribute<Object, Long> contentLengthAMAttribute =
				AMAttribute.getContentLengthAMAttribute();

			properties.put(
				contentLengthAMAttribute.getName(),
				String.valueOf(amImageEntry.getSize()));
		}

		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(properties);

		return new AMImage(
			() -> _amImageEntryLocalService.getAMImageEntryContentStream(
				amImageConfigurationEntry, fileVersion),
			amImageAttributeMapping,
			uriFactory.apply(fileVersion, amImageConfigurationEntry));
	}

	private AdaptiveMedia<AMProcessor<FileVersion>> _createRawAdaptiveMedia(
		FileVersion fileVersion) {

		return new AMImage(
			() -> {
				try {
					return fileVersion.getContentStream(false);
				}
				catch (PortalException portalException) {
					throw new AMRuntimeException(portalException);
				}
			},
			AMImageAttributeMapping.fromFileVersion(fileVersion), null);
	}

	private BiFunction<FileVersion, AMImageConfigurationEntry, URI>
		_getURIFactory(AMImageQueryBuilderImpl amImageQueryBuilderImpl) {

		if (amImageQueryBuilderImpl.hasFileVersion()) {
			return _amImageURLFactory::createFileVersionURL;
		}

		return _amImageURLFactory::createFileEntryURL;
	}

	private boolean _hasAdaptiveMedia(
		FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		AMImageEntry amImageEntry = _amImageEntryLocalService.fetchAMImageEntry(
			amImageConfigurationEntry.getUUID(),
			fileVersion.getFileVersionId());

		if (amImageEntry == null) {
			return false;
		}

		return true;
	}

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AMImageURLFactory _amImageURLFactory;

}