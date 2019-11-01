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

package com.liferay.adaptive.media.document.library.thumbnails.internal.processor;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.document.library.thumbnails.internal.configuration.AMSystemImagesConfiguration;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.adaptive.media.processor.AMAsyncProcessor;
import com.liferay.adaptive.media.processor.AMAsyncProcessorLocator;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.ImageProcessor;
import com.liferay.document.library.security.io.InputStreamSanitizer;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileEntryWrapper;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.FileVersionWrapper;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.documentlibrary.util.ImageProcessorImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.adaptive.media.document.library.thumbnails.internal.configuration.AMSystemImagesConfiguration",
	immediate = true, property = "service.ranking:Integer=100",
	service = {AMImageEntryProcessor.class, DLProcessor.class}
)
public class AMImageEntryProcessor implements DLProcessor, ImageProcessor {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		afterPropertiesSet();

		_amSystemImagesConfiguration = ConfigurableUtil.createConfigurable(
			AMSystemImagesConfiguration.class, properties);
	}

	@Override
	public void afterPropertiesSet() {
		_imageProcessor = new ImageProcessorImpl();
	}

	@Override
	public void cleanUp(FileEntry fileEntry) {
	}

	@Override
	public void cleanUp(FileVersion fileVersion) {
	}

	@Override
	public void copy(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {
	}

	@Override
	public void exportGeneratedFiles(
		PortletDataContext portletDataContext, FileEntry fileEntry,
		Element fileEntryElement) {
	}

	@Override
	public void generateImages(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {
	}

	@Override
	public Set<String> getImageMimeTypes() {
		return new HashSet<>(
			Arrays.asList(_amImageMimeTypeProvider.getSupportedMimeTypes()));
	}

	@Override
	public InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_getAdaptiveMediaStream(
				fileVersion,
				_amSystemImagesConfiguration.previewAMConfiguration(),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT));

		Optional<AdaptiveMedia<AMImageProcessor>> adaptiveMediaOptional =
			adaptiveMediaStream.findFirst();

		if (!adaptiveMediaOptional.isPresent()) {
			_processAMImage(fileVersion);

			return fileVersion.getContentStream(false);
		}

		AdaptiveMedia<AMImageProcessor> adaptiveMedia =
			adaptiveMediaOptional.get();

		return adaptiveMedia.getInputStream();
	}

	@Override
	public long getPreviewFileSize(FileVersion fileVersion) throws Exception {
		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_getAdaptiveMediaStream(
				fileVersion,
				_amSystemImagesConfiguration.previewAMConfiguration(),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT));

		Optional<AdaptiveMedia<AMImageProcessor>> adaptiveMediaOptional =
			adaptiveMediaStream.findFirst();

		if (!adaptiveMediaOptional.isPresent()) {
			_processAMImage(fileVersion);
		}

		return adaptiveMediaOptional.flatMap(
			mediaMedia -> mediaMedia.getValueOptional(
				AMAttribute.getContentLengthAMAttribute())
		).orElse(
			0L
		);
	}

	@Override
	public String getPreviewType(FileVersion fileVersion) {
		return _imageProcessor.getPreviewType(fileVersion);
	}

	@Override
	public InputStream getThumbnailAsStream(FileVersion fileVersion, int index)
		throws Exception {

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_getThumbnailAdaptiveMedia(fileVersion, index);

		Optional<AdaptiveMedia<AMImageProcessor>> adaptiveMediaOptional =
			adaptiveMediaStream.findFirst();

		if (!adaptiveMediaOptional.isPresent()) {
			_processAMImage(fileVersion);
		}

		return adaptiveMediaOptional.map(
			AdaptiveMedia::getInputStream
		).orElse(
			new ByteArrayInputStream(new byte[0])
		);
	}

	@Override
	public long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_getThumbnailAdaptiveMedia(fileVersion, index);

		Optional<AdaptiveMedia<AMImageProcessor>> adaptiveMediaOptional =
			adaptiveMediaStream.findFirst();

		if (!adaptiveMediaOptional.isPresent()) {
			_processAMImage(fileVersion);
		}

		return adaptiveMediaOptional.flatMap(
			mediaMedia -> mediaMedia.getValueOptional(
				AMAttribute.getContentLengthAMAttribute())
		).orElse(
			0L
		);
	}

	@Override
	public String getThumbnailType(FileVersion fileVersion) {
		return _imageProcessor.getThumbnailType(fileVersion);
	}

	@Override
	public String getType() {
		return DLProcessorConstants.IMAGE_PROCESSOR;
	}

	@Override
	public boolean hasImages(FileVersion fileVersion) {
		try {
			Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
				_getThumbnailAdaptiveMedia(fileVersion);

			Optional<AdaptiveMedia<AMImageProcessor>> adaptiveMediaOptional =
				adaptiveMediaStream.findFirst();

			if (adaptiveMediaOptional.isPresent()) {
				return true;
			}

			_processAMImage(fileVersion);

			return false;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return false;
		}
	}

	@Override
	public void importGeneratedFiles(
		PortletDataContext portletDataContext, FileEntry fileEntry,
		FileEntry importedFileEntry, Element fileEntryElement) {
	}

	@Override
	public boolean isImageSupported(FileVersion fileVersion) {
		return _amImageValidator.isValid(fileVersion);
	}

	@Override
	public boolean isImageSupported(String mimeType) {
		return _isMimeTypeSupported(mimeType);
	}

	@Override
	public boolean isSupported(FileVersion fileVersion) {
		return _amImageValidator.isValid(fileVersion);
	}

	@Override
	public boolean isSupported(String mimeType) {
		return _isMimeTypeSupported(mimeType);
	}

	@Override
	public void storeThumbnail(
		long companyId, long groupId, long fileEntryId, long fileVersionId,
		long custom1ImageId, long custom2ImageId, InputStream is, String type) {
	}

	@Override
	public void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {
	}

	private Stream<AdaptiveMedia<AMImageProcessor>> _getAdaptiveMediaStream(
			FileVersion fileVersion, String configurationUuid, int defaultWidth,
			int defaultHeight)
		throws PortalException {

		if (Validator.isNotNull(configurationUuid)) {
			return _amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
					fileVersion
				).forConfiguration(
					configurationUuid
				).done());
		}

		return _amImageFinder.getAdaptiveMediaStream(
			amImageQueryBuilder -> amImageQueryBuilder.forFileVersion(
				fileVersion
			).with(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH, defaultWidth
			).with(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT, defaultHeight
			).done());
	}

	private Stream<AdaptiveMedia<AMImageProcessor>> _getThumbnailAdaptiveMedia(
			FileVersion fileVersion)
		throws PortalException {

		return _getThumbnailAdaptiveMedia(fileVersion, 0);
	}

	private Stream<AdaptiveMedia<AMImageProcessor>> _getThumbnailAdaptiveMedia(
			FileVersion fileVersion, int index)
		throws PortalException {

		if (index == _THUMBNAIL_INDEX_CUSTOM_1) {
			return _getAdaptiveMediaStream(
				fileVersion,
				_amSystemImagesConfiguration.thumbnailCustom1AMConfiguration(),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT));
		}

		if (index == _THUMBNAIL_INDEX_CUSTOM_2) {
			return _getAdaptiveMediaStream(
				fileVersion,
				_amSystemImagesConfiguration.thumbnailCustom2AMConfiguration(),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH),
				PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT));
		}

		return _getAdaptiveMediaStream(
			fileVersion,
			_amSystemImagesConfiguration.thumbnailAMConfiguration(),
			PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH),
			PrefsPropsUtil.getInteger(
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT));
	}

	private boolean _isMimeTypeSupported(String mimeType) {
		return _amImageMimeTypeProvider.isMimeTypeSupported(mimeType);
	}

	private void _processAMImage(FileVersion fileVersion) {
		if (!_amImageValidator.isValid(fileVersion)) {
			return;
		}

		try {
			AMAsyncProcessor<FileVersion, ?> amAsyncProcessor =
				_amAsyncProcessorLocator.locateForClass(FileVersion.class);

			amAsyncProcessor.triggerProcess(
				new SafeFileVersion(fileVersion),
				String.valueOf(fileVersion.getFileVersionId()));
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to create lazy adaptive media for file version " +
					fileVersion.getFileVersionId(),
				pe);
		}
	}

	private static final int _THUMBNAIL_INDEX_CUSTOM_1 = 1;

	private static final int _THUMBNAIL_INDEX_CUSTOM_2 = 2;

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageEntryProcessor.class);

	@Reference
	private AMAsyncProcessorLocator _amAsyncProcessorLocator;

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AMImageValidator _amImageValidator;

	private AMSystemImagesConfiguration _amSystemImagesConfiguration;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private ImageProcessor _imageProcessor;

	@Reference
	private InputStreamSanitizer _inputStreamSanitizer;

	private class SafeFileEntry extends FileEntryWrapper {

		public SafeFileEntry(FileEntry fileEntry) {
			super(fileEntry);
		}

		@Override
		public InputStream getContentStream() throws PortalException {
			return _inputStreamSanitizer.sanitize(super.getContentStream());
		}

		@Override
		public InputStream getContentStream(String version)
			throws PortalException {

			return _inputStreamSanitizer.sanitize(
				super.getContentStream(version));
		}

		@Override
		public FileVersion getFileVersion() throws PortalException {
			return new SafeFileVersion(super.getFileVersion());
		}

		@Override
		public FileVersion getFileVersion(String version)
			throws PortalException {

			return new SafeFileVersion(super.getFileVersion(version));
		}

		@Override
		public FileVersion getLatestFileVersion() throws PortalException {
			return new SafeFileVersion(super.getLatestFileVersion());
		}

		@Override
		public FileVersion getLatestFileVersion(boolean trusted)
			throws PortalException {

			return new SafeFileVersion(super.getLatestFileVersion(trusted));
		}

	}

	private class SafeFileVersion extends FileVersionWrapper {

		public SafeFileVersion(FileVersion fileVersion) {
			super(fileVersion);
		}

		@Override
		public InputStream getContentStream(boolean incrementCounter)
			throws PortalException {

			return _inputStreamSanitizer.sanitize(
				super.getContentStream(incrementCounter));
		}

		@Override
		public FileEntry getFileEntry() throws PortalException {
			return new SafeFileEntry(super.getFileEntry());
		}

	}

}