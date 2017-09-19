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

package com.liferay.adaptive.media.document.library.internal.document.library.exportimport.data.handler;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntrySerializer;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.image.util.AMImageSerializer;
import com.liferay.document.library.exportimport.data.handler.DLPluggableContentDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portal.kernel.repository.model.FileEntry"
	},
	service = DLPluggableContentDataHandler.class
)
public class AMImageDLPluggableContentDataHandler
	implements DLPluggableContentDataHandler<FileEntry> {

	@Override
	public void exportContent(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry)
		throws Exception {

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				portletDataContext.getCompanyId());

		amImageConfigurationEntries.forEach(
			amImageConfigurationEntry -> _exportConfigurationEntry(
				portletDataContext, amImageConfigurationEntry));

		_exportMedia(portletDataContext, fileEntry);
	}

	@Override
	public void importContent(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry, FileEntry importedFileEntry)
		throws Exception {

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				portletDataContext.getCompanyId());

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			_importGeneratedMedia(
				portletDataContext, importedFileEntry,
				amImageConfigurationEntry);
		}
	}

	private void _exportConfigurationEntry(
		PortletDataContext portletDataContext,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		portletDataContext.addZipEntry(
			_getConfigurationEntryBinPath(amImageConfigurationEntry),
			_amImageConfigurationEntrySerializer.serialize(
				amImageConfigurationEntry));
	}

	private void _exportMedia(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws IOException, PortalException {

		List<FileVersion> fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_APPROVED);

		for (FileVersion fileVersion : fileVersions) {
			Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
				_amImageFinder.getAdaptiveMediaStream(
					amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
						fileVersion
					).done());

			List<AdaptiveMedia<AMImageProcessor>> adaptiveMediaList =
				adaptiveMediaStream.collect(Collectors.toList());

			for (AdaptiveMedia<AMImageProcessor> adaptiveMedia :
					adaptiveMediaList) {

				_exportMedia(portletDataContext, fileVersion, adaptiveMedia);
			}
		}
	}

	private void _exportMedia(
			PortletDataContext portletDataContext, FileVersion fileVersion,
			AdaptiveMedia<AMImageProcessor> adaptiveMedia)
		throws IOException {

		Optional<String> configurationUuidOptional =
			adaptiveMedia.getValueOptional(
				AMAttribute.getConfigurationUuidAMAttribute());

		if (!configurationUuidOptional.isPresent()) {
			return;
		}

		String basePath = _getAMBasePath(
			fileVersion, configurationUuidOptional.get());

		try (InputStream inputStream = adaptiveMedia.getInputStream()) {
			portletDataContext.addZipEntry(basePath + ".bin", inputStream);
		}

		portletDataContext.addZipEntry(
			basePath + ".json", _amImageSerializer.serialize(adaptiveMedia));
	}

	private String _getAMBasePath(FileVersion fileVersion, String uuid) {
		return String.format(
			"adaptive-media/%s/%s/%s", FileVersion.class.getSimpleName(),
			fileVersion.getUuid(), uuid);
	}

	private String _getConfigurationEntryBinPath(
		AMImageConfigurationEntry amImageConfigurationEntry) {

		return String.format(
			"adaptive-media/%s.cf", amImageConfigurationEntry.getUUID());
	}

	private AdaptiveMedia<AMImageProcessor> _getExportedMedia(
		PortletDataContext portletDataContext, FileVersion fileVersion,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		String basePath = _getAMBasePath(
			fileVersion, amImageConfigurationEntry.getUUID());

		String serializedAdaptiveMedia = portletDataContext.getZipEntryAsString(
			basePath + ".json");

		if (serializedAdaptiveMedia == null) {
			return null;
		}

		return _amImageSerializer.deserialize(
			serializedAdaptiveMedia,
			() -> portletDataContext.getZipEntryAsInputStream(
				basePath + ".bin"));
	}

	private void _importGeneratedMedia(
			PortletDataContext portletDataContext, FileEntry importedFileEntry,
			AMImageConfigurationEntry amImageConfigurationEntry)
		throws IOException, PortalException {

		String configuration = portletDataContext.getZipEntryAsString(
			_getConfigurationEntryBinPath(amImageConfigurationEntry));

		if (configuration == null) {
			return;
		}

		AMImageConfigurationEntry importedAMImageConfigurationEntry =
			_amImageConfigurationEntrySerializer.deserialize(configuration);

		if (!importedAMImageConfigurationEntry.equals(
				amImageConfigurationEntry)) {

			return;
		}

		List<FileVersion> fileVersions = importedFileEntry.getFileVersions(
			WorkflowConstants.STATUS_APPROVED);

		for (FileVersion fileVersion : fileVersions) {
			AdaptiveMedia<AMImageProcessor> adaptiveMedia = _getExportedMedia(
				portletDataContext, fileVersion, amImageConfigurationEntry);

			if (adaptiveMedia == null) {
				continue;
			}

			Optional<Long> contentLengthOptional =
				adaptiveMedia.getValueOptional(
					AMAttribute.getContentLengthAMAttribute());

			Optional<Integer> widthOptional = adaptiveMedia.getValueOptional(
				AMImageAttribute.IMAGE_WIDTH);

			Optional<Integer> heightOptional = adaptiveMedia.getValueOptional(
				AMImageAttribute.IMAGE_HEIGHT);

			if (!contentLengthOptional.isPresent() ||
				!widthOptional.isPresent() || !heightOptional.isPresent()) {

				continue;
			}

			AMImageEntry amImageEntry =
				_amImageEntryLocalService.fetchAMImageEntry(
					amImageConfigurationEntry.getUUID(),
					fileVersion.getFileVersionId());

			if (amImageEntry != null) {
				_amImageEntryLocalService.deleteAMImageEntryFileVersion(
					amImageConfigurationEntry.getUUID(),
					fileVersion.getFileVersionId());
			}

			try (InputStream inputStream = adaptiveMedia.getInputStream()) {
				_amImageEntryLocalService.addAMImageEntry(
					amImageConfigurationEntry, fileVersion, widthOptional.get(),
					heightOptional.get(), inputStream,
					contentLengthOptional.get());
			}
		}
	}

	@Reference
	private AMImageConfigurationEntrySerializer
		_amImageConfigurationEntrySerializer;

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private AMImageSerializer _amImageSerializer;

}