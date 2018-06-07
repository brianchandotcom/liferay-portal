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

package com.liferay.adaptive.media.document.library.thumbnails.internal.osgi.commands;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;

import java.awt.image.RenderedImage;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
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
		"osgi.command.function=check", "osgi.command.function=cleanUp",
		"osgi.command.function=migrate", "osgi.command.scope=thumbnails"
	},
	service = AMThumbnailsOSGiCommands.class
)
public class AMThumbnailsOSGiCommands {

	public void check(String... companyIds) {
		System.out.println("Company ID\t# of thumbnails pending migration");
		System.out.println("-------------------------------------------------");

		int total = 0;

		for (long companyId : _getCompanyIds(companyIds)) {
			try {
				String[] fileNames = DLStoreUtil.getFileNames(
					companyId, DLPreviewableProcessor.REPOSITORY_ID,
					DLPreviewableProcessor.THUMBNAIL_PATH);

				int companyTotal = 0;

				for (String fileName : fileNames) {

					// See LPS-70788

					String actualFileName = StringUtil.replace(
						fileName, StringPool.DOUBLE_SLASH, StringPool.SLASH);

					for (ThumbnailConfiguration thumbnailConfiguration :
							_getThumbnailConfigurations()) {

						FileVersion fileVersion = _getFileVersion(
							thumbnailConfiguration.getFileVersionId(
								actualFileName));

						if (fileVersion != null) {
							companyTotal += 1;
						}
					}
				}

				System.out.printf("%d\t\t%d%n", companyId, companyTotal);

				total += companyTotal;
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		System.out.printf("%nTOTAL: %d%n", total);
	}

	public void cleanUp(String... companyIds) {
		for (long companyId : _getCompanyIds(companyIds)) {
			try {
				String[] fileNames = DLStoreUtil.getFileNames(
					companyId, DLPreviewableProcessor.REPOSITORY_ID,
					DLPreviewableProcessor.THUMBNAIL_PATH);

				for (String fileName : fileNames) {

					// See LPS-70788

					String actualFileName = StringUtil.replace(
						fileName, "//", StringPool.SLASH);

					for (ThumbnailConfiguration thumbnailConfiguration :
							_getThumbnailConfigurations()) {

						FileVersion fileVersion = _getFileVersion(
							thumbnailConfiguration.getFileVersionId(
								actualFileName));

						if (fileVersion != null) {
							DLStoreUtil.deleteFile(
								companyId, DLPreviewableProcessor.REPOSITORY_ID,
								actualFileName);
						}
					}
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void migrate(String... companyIds) throws PortalException {
		for (long companyId : _getCompanyIds(companyIds)) {
			Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
				_amImageConfigurationHelper.getAMImageConfigurationEntries(
					companyId);

			if (!_isValidConfigurationEntries(amImageConfigurationEntries)) {
				throw new PortalException(
					"No valid Adaptive Media configuration found. Please " +
						"refer to the upgrade documentation for the details.");
			}

			try {
				String[] fileNames = DLStoreUtil.getFileNames(
					companyId, DLPreviewableProcessor.REPOSITORY_ID,
					DLPreviewableProcessor.THUMBNAIL_PATH);

				for (String fileName : fileNames) {

					// See LPS-70788

					String actualFileName = StringUtil.replace(
						fileName, "//", StringPool.SLASH);

					for (ThumbnailConfiguration thumbnailConfiguration :
							_getThumbnailConfigurations()) {

						Optional<AMImageConfigurationEntry>
							amImageConfigurationEntryOptional =
								thumbnailConfiguration.
									selectMatchingConfigurationEntry(
										amImageConfigurationEntries);

						amImageConfigurationEntryOptional.ifPresent(
							amImageConfigurationEntry -> _migrate(
								actualFileName, amImageConfigurationEntry,
								thumbnailConfiguration));
					}
				}
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}
	}

	private Iterable<Long> _getCompanyIds(String... companyIds) {
		if (companyIds.length == 0) {
			List<Company> companies = _companyLocalService.getCompanies();

			Stream<Company> companyStream = companies.stream();

			return companyStream.map(
				Company::getCompanyId
			).collect(
				Collectors.toList()
			);
		}

		Stream<String> companyIdStream = Arrays.stream(companyIds);

		return companyIdStream.map(
			Long::parseLong
		).collect(
			Collectors.toList()
		);
	}

	private FileVersion _getFileVersion(long fileVersionId) {
		try {
			if (fileVersionId == 0) {
				return null;
			}

			FileVersion fileVersion = _dlAppLocalService.getFileVersion(
				fileVersionId);

			if (!_isMimeTypeSupported(fileVersion)) {
				return null;
			}

			return fileVersion;
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return null;
		}
	}

	private ThumbnailConfiguration[] _getThumbnailConfigurations() {
		return new ThumbnailConfiguration[] {
			new ThumbnailConfiguration(
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT),
				Pattern.compile(
					DLPreviewableProcessor.THUMBNAIL_PATH +
						"\\d+/\\d+(?:/\\d+)+/(\\d+)(?:\\..+)?$")),
			new ThumbnailConfiguration(
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT),
				Pattern.compile(
					DLPreviewableProcessor.THUMBNAIL_PATH +
						"\\d+/\\d+(?:/\\d+)+/(\\d+)-1(?:\\..+)?$")),
			new ThumbnailConfiguration(
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT),
				Pattern.compile(
					DLPreviewableProcessor.THUMBNAIL_PATH +
						"\\d+/\\d+(?:/\\d+)+/(\\d+)-2(?:\\..+)?$"))
		};
	}

	private boolean _isMimeTypeSupported(FileVersion fileVersion) {
		return ArrayUtil.contains(
			_amImageMimeTypeProvider.getSupportedMimeTypes(),
			fileVersion.getMimeType());
	}

	private boolean _isValidConfigurationEntries(
		Collection<AMImageConfigurationEntry> amImageConfigurationEntries) {

		Stream<ThumbnailConfiguration> thumbnailConfigurationStream =
			Arrays.stream(_getThumbnailConfigurations());

		return thumbnailConfigurationStream.anyMatch(
			thumbnailConfiguration -> {
				Stream<AMImageConfigurationEntry>
					amImageConfigurationEntryStream =
						amImageConfigurationEntries.stream();

				return amImageConfigurationEntryStream.anyMatch(
					thumbnailConfiguration::matches);
			});
	}

	private void _migrate(
		String fileName, AMImageConfigurationEntry amImageConfigurationEntry,
		ThumbnailConfiguration thumbnailConfiguration) {

		try {
			FileVersion fileVersion = _getFileVersion(
				thumbnailConfiguration.getFileVersionId(fileName));

			if (fileVersion == null) {
				return;
			}

			AMImageEntry amImageEntry =
				_amImageEntryLocalService.fetchAMImageEntry(
					amImageConfigurationEntry.getUUID(),
					fileVersion.getFileVersionId());

			if (amImageEntry != null) {
				return;
			}

			byte[] bytes = DLStoreUtil.getFileAsBytes(
				fileVersion.getCompanyId(),
				DLPreviewableProcessor.REPOSITORY_ID, fileName);

			ImageBag imageBag = ImageToolUtil.read(bytes);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			_amImageEntryLocalService.addAMImageEntry(
				amImageConfigurationEntry, fileVersion,
				renderedImage.getHeight(), renderedImage.getWidth(),
				new UnsyncByteArrayInputStream(bytes), bytes.length);
		}
		catch (IOException | PortalException e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMThumbnailsOSGiCommands.class);

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}