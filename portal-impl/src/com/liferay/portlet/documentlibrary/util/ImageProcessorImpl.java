/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Sergio González
 * @author Alexander Chow
 */
public class ImageProcessorImpl
	extends DLPreviewableProcessor implements ImageProcessor {

	public static ImageProcessorImpl getInstance() {
		return _instance;
	}

	@Override
	public void cleanUp(FileEntry fileEntry) {
		deleteFiles(fileEntry, null);
	}

	@Override
	public void cleanUp(FileVersion fileVersion) {
		String type = _instance.getThumbnailType(fileVersion);

		deleteFiles(fileVersion, type);
	}

	public void generateImages(FileVersion fileVersion) {
		_instance._generateImages(fileVersion);
	}

	public Set<String> getImageMimeTypes() {
		return _instance._imageMimeTypes;
	}

	public InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		if (_previewGenerationRequired(fileVersion)) {
			String type = getPreviewType(fileVersion);

			return _instance.doGetPreviewAsStream(fileVersion, type);
		}

		return fileVersion.getContentStream(false);
	}

	public long getPreviewFileSize(FileVersion fileVersion) throws Exception {
		if (_previewGenerationRequired(fileVersion)) {
			String type = getPreviewType(fileVersion);

			return _instance.doGetPreviewFileSize(fileVersion, type);
		}

		return fileVersion.getSize();
	}

	@Override
	public String getPreviewType(FileVersion fileVersion) {
		return _getType(fileVersion);
	}

	public InputStream getThumbnailAsStream(FileVersion fileVersion, int index)
		throws Exception {

		return _instance.doGetThumbnailAsStream(fileVersion, index);
	}

	public long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return _instance.doGetThumbnailFileSize(fileVersion, index);
	}

	@Override
	public String getThumbnailType(FileVersion fileVersion) {
		return _getType(fileVersion);
	}

	public boolean hasImages(FileVersion fileVersion) {
		if (!PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {

			return false;
		}

		boolean hasImages = false;

		try {
			if (_instance._hasPreview(fileVersion) &&
				hasThumbnails(fileVersion)) {

				hasImages = true;
			}

			if (!hasImages && _instance.isSupported(fileVersion)) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasImages;
	}

	public boolean isImageSupported(FileVersion fileVersion) {
		return _instance.isSupported(fileVersion);
	}

	public boolean isImageSupported(String mimeType) {
		return _instance.isSupported(mimeType);
	}

	public boolean isSupported(String mimeType) {
		if (Validator.isNull(mimeType)) {
			return false;
		}

		return _imageMimeTypes.contains(mimeType);
	}

	public void storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId, InputStream is,
			String type)
		throws Exception {

		_instance._storeThumbnail(
			companyId, groupId, fileEntryId, fileVersionId, custom1ImageId,
			custom2ImageId, is, type);
	}

	public void trigger(FileVersion fileVersion) {
		_instance._queueGeneration(fileVersion);
	}

	@Override
	protected void doExportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		exportThumbnails(
			portletDataContext, fileEntry, fileEntryElement, "image");

		exportPreview(portletDataContext, fileEntry, fileEntryElement);
	}

	@Override
	protected void doImportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		importThumbnails(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement,
			"image");

		FileVersion importedFileVersion = importedFileEntry.getFileVersion();

		if (!_previewGenerationRequired(importedFileVersion)) {
			return;
		}

		importPreview(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement,
			"image", getPreviewType(importedFileVersion));
	}

	protected void exportPreview(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		FileVersion fileVersion = fileEntry.getFileVersion();

		if (!isSupported(fileVersion) ||
			!_previewGenerationRequired(fileVersion) ||
			!_hasPreview(fileVersion)) {

			return;
		}

		exportPreview(
			portletDataContext, fileEntry, fileEntryElement, "image",
			getPreviewType(fileVersion));
	}

	private ImageProcessorImpl() {
	}

	private void _generateImages(FileVersion fileVersion) {
		InputStream inputStream = null;

		try {
			if (!PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
				!PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED) {

				return;
			}

			inputStream = fileVersion.getContentStream(false);

			byte[] bytes = FileUtil.getBytes(inputStream);

			ImageBag imageBag = ImageToolUtil.read(bytes);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			if (renderedImage == null) {
				return;
			}

			if (!_hasPreview(fileVersion)) {
				_storePreviewImage(fileVersion, renderedImage);
			}

			if (!hasThumbnails(fileVersion)) {
				storeThumbnailImages(fileVersion, renderedImage);
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			_fileVersionIds.remove(fileVersion.getFileVersionId());
		}
	}

	private String _getType(FileVersion fileVersion) {
		String type = "png";

		if (fileVersion == null) {
			return type;
		}

		String extension = fileVersion.getExtension();

		if (extension.equals("jpeg")) {
			type = "jpg";
		}
		else if (!_previewGenerationRequired(fileVersion)) {
			type = extension;
		}

		return type;
	}

	private boolean _hasPreview(FileVersion fileVersion)
		throws PortalException, SystemException {

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			_previewGenerationRequired(fileVersion)) {

			String type = getPreviewType(fileVersion);

			String previewFilePath = getPreviewFilePath(fileVersion, type);

			if (!DLStoreUtil.hasFile(
					fileVersion.getCompanyId(), REPOSITORY_ID,
					previewFilePath)) {

				return false;
			}
		}

		return true;
	}

	private boolean _previewGenerationRequired(FileVersion fileVersion) {
		String type = fileVersion.getExtension();

		if (type.equals("tiff") || type.equals("tif")) {
			return true;
		}
		else {
			return false;
		}
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (!_fileVersionIds.contains(fileVersion.getFileVersionId()) &&
			isSupported(fileVersion) && !hasThumbnails(fileVersion)) {
			_fileVersionIds.add(fileVersion.getFileVersionId());

			if (PropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY) {
				try {
					MessageBusUtil.sendSynchronousMessage(
						DestinationNames.DOCUMENT_LIBRARY_IMAGE_PROCESSOR,
						fileVersion);
				}
				catch (MessageBusException mbe) {
					if (_log.isWarnEnabled()) {
						_log.warn(mbe, mbe);
					}
				}
			}
			else {
				MessageBusUtil.sendMessage(
					DestinationNames.DOCUMENT_LIBRARY_IMAGE_PROCESSOR,
					fileVersion);
			}
		}
	}

	private void _storePreviewImage(
			FileVersion fileVersion, RenderedImage renderedImage)
		throws Exception {

		String type = getPreviewType(fileVersion);

		File file = null;

		try {
			file = FileUtil.createTempFile(type);

			FileOutputStream fos = new FileOutputStream(file);

			try {
				ImageToolUtil.write(renderedImage, type, fos);
			}
			finally {
				fos.close();
			}

			addFileToStore(
				fileVersion.getCompanyId(), PREVIEW_PATH,
				getPreviewFilePath(fileVersion, type), file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private void _storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId, InputStream is,
			String type)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(getPathSegment(groupId, fileEntryId, fileVersionId, false));

		if (custom1ImageId != 0) {
			sb.append(StringPool.DASH);
			sb.append(1);
		}
		else if (custom2ImageId != 0) {
			sb.append(StringPool.DASH);
			sb.append(2);
		}

		sb.append(StringPool.PERIOD);
		sb.append(type);

		String filePath = sb.toString();

		File file = null;

		try {
			file = FileUtil.createTempFile(is);

			addFileToStore(companyId, THUMBNAIL_PATH, filePath, file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ImageProcessorImpl.class);

	private static ImageProcessorImpl _instance = new ImageProcessorImpl();

	static {
		InstancePool.put(ImageProcessorImpl.class.getName(), _instance);
	}

	private List<Long> _fileVersionIds = new Vector<Long>();
	private Set<String> _imageMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES);

}