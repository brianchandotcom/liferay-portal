/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageProcessor;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 * @author Juan González
 */
public class PDFProcessor extends DLPreviewableProcessor {

	public static final String PREVIEW_TYPE = ImageProcessor.TYPE_PNG;

	public static final String THUMBNAIL_TYPE = ImageProcessor.TYPE_PNG;

	public static void generateImages(FileVersion fileVersion) {
		_instance._generateImages(fileVersion);
	}

	public static InputStream getPreviewAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		return _instance.doGetPreviewAsStream(fileVersion, index);
	}

	public static long getPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return _instance.doGetPreviewFileSize(fileVersion, index);
	}

	public static int getPreviewFileCount(FileEntry fileEntry, String version) {
		try {
			FileVersion fileVersion = fileEntry.getFileVersion(version);

			return _instance.getPreviewFileCount(fileVersion);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	public static InputStream getThumbnailAsStream(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetThumbnailAsStream(fileVersion);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetThumbnailFileSize(fileVersion);
	}

	public static boolean hasImages(FileEntry fileEntry, String version) {
		boolean hasImages = false;

		try {
			FileVersion fileVersion = fileEntry.getFileVersion(version);

			hasImages = _instance._hasImages(fileVersion);

			if (!hasImages) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasImages;
	}

	public void trigger(FileEntry fileEntry) {
		try {
			FileVersion fileVersion = fileEntry.getLatestFileVersion();

			trigger(fileVersion);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void trigger(FileVersion fileVersion) {
		_instance._queueGeneration(fileVersion);
	}

	protected String getPreviewType() {
		return PREVIEW_TYPE;
	}

	protected String getThumbnailType() {
		return THUMBNAIL_TYPE;
	}

	private void _generateImages(FileVersion fileVersion) {
		try {
			if (_hasImages(fileVersion)) {
				return;
			}

			String extension = fileVersion.getExtension();

			if (extension.equals("pdf")) {
				if (fileVersion instanceof LiferayFileVersion) {
					try {
						LiferayFileVersion liferayFileVersion =
							(LiferayFileVersion)fileVersion;

						File file = liferayFileVersion.getFile(false);

						_generateImages(fileVersion, file);

						return;
					}
					catch (UnsupportedOperationException uoe) {
					}
				}

				InputStream inputStream = fileVersion.getContentStream(false);

				_generateImages(fileVersion, inputStream);
			}
			else if (DocumentConversionUtil.isEnabled()) {
				InputStream inputStream = fileVersion.getContentStream(false);

				String id = DLUtil.getTempFileId(
					fileVersion.getFileEntryId(), fileVersion.getVersion());

				File file = DocumentConversionUtil.convert(
					id, inputStream, fileVersion.getExtension(), "pdf");

				_generateImages(fileVersion, file);
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			_fileEntries.remove(fileVersion.getFileVersionId());
		}
	}

	private void _generateImages(FileVersion fileVersion, File file)
		throws Exception {

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			long fileVersionId = fileVersion.getFileVersionId();

			if (_isGeneratePreview(fileVersion)) {
				_generateImagesIM(
					fileVersion, file,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DEPTH,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_HEIGHT,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_WIDTH, false);

				if (_log.isInfoEnabled()) {
					_log.info(
						"ImageMagick generated " +
							getPreviewFileCount(fileVersion) +
								" preview pages for " + fileVersionId);
				}
			}

			if (_isGenerateThumbnail(fileVersion)) {
				_generateImagesIM(
					fileVersion, file,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_DEPTH,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_DPI,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH, true);

				if (_log.isInfoEnabled()) {
					_log.info(
						"ImageMagick generated a thumbnail for " +
							fileVersionId);
				}
			}
		}
		else {
			_generateImages(fileVersion, new FileInputStream(file));
		}
	}

	private void _generateImages(
			FileVersion fileVersion, InputStream inputStream)
		throws Exception {

		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			if (fileVersion.getExtension().equals("pdf")) {
				String filePath = DocumentConversionUtil.getFilePath(id, "pdf");

				File file = new File(filePath);

				try {
					FileUtil.write(file, inputStream);

					_generateImages(fileVersion, file);
				}
				finally {
					FileUtil.delete(file);
				}
			}
			else {
				File file = DocumentConversionUtil.convert(
					id, inputStream, fileVersion.getExtension(), "pdf");

				_generateImages(fileVersion, file);
			}
		}
		else {
			long fileVersionId = fileVersion.getFileVersionId();

			boolean generatePreview = _isGeneratePreview(fileVersion);
			boolean generateThumbnail = _isGenerateThumbnail(fileVersion);

			PDDocument pdDocument = null;

			try {
				pdDocument = PDDocument.load(inputStream);

				PDDocumentCatalog pdDocumentCatalog =
					pdDocument.getDocumentCatalog();

				List<PDPage> pdPages = pdDocumentCatalog.getAllPages();

				for (int i = 0; i < pdPages.size(); i++) {
					PDPage pdPage = pdPages.get(i);

					if (generateThumbnail && (i == 0)) {
						_generateImagesPB(
							fileVersion, pdPage,
							PropsValues.DL_FILE_ENTRY_THUMBNAIL_DPI,
							PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT,
							PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH, true, 0);

						if (_log.isInfoEnabled()) {
							_log.info(
								"PDFBox generated a thumbnail for " +
									fileVersionId);
						}
					}

					if (!generatePreview) {
						break;
					}

					_generateImagesPB(
						fileVersion, pdPage,
						PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI,
						PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_WIDTH, false,
						i + 1);
				}

				if (_log.isInfoEnabled() && generatePreview) {
					_log.info(
						"PDFBox generated " +
							getPreviewFileCount(fileVersion) +
								" preview pages for " + fileVersionId);
				}
			}
			finally {
				if (pdDocument != null) {
					pdDocument.close();
				}
			}
		}
	}

	private void _generateImagesIM(
			FileVersion fileVersion, File file, int depth, int dpi, int height,
			int width, boolean thumbnail)
		throws Exception {

		// Generate images

		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		IMOperation imOperation = new IMOperation();

		imOperation.density(dpi, dpi);

		if (height != 0) {
			imOperation.adaptiveResize(width, height);
		}
		else {
			imOperation.adaptiveResize(width);
		}

		imOperation.depth(depth);

		if (thumbnail) {
			imOperation.addImage(file.getPath() + "[0]");
			imOperation.addImage(getThumbnailTempFilePath(id));
		}
		else {
			imOperation.addImage(file.getPath());
			imOperation.addImage(getPreviewTempFilePath(id, -1));
		}

		try {
			_convertCmd.run(imOperation);
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		// Store images

		if (thumbnail) {
			File tempFile = getThumbnailTempFile(id);

			try {
				addFileToStore(
					fileVersion.getCompanyId(), THUMBNAIL_PATH,
					getThumbnailFilePath(fileVersion), tempFile);
			}
			finally {
				FileUtil.delete(tempFile);
			}
		}
		else {

			// ImageMagick converts single page PDFs without appending an
			// index. Rename file for consistency.

			File singlePagePreviewFile = getPreviewTempFile(id, -1);

			if (singlePagePreviewFile.exists()) {
				singlePagePreviewFile.renameTo(getPreviewTempFile(id, 1));
			}

			int total = getPreviewTempFileCount(fileVersion);

			for (int i = 0; i < total; i++) {
				File tempFile = getPreviewTempFile(id, i + 1);

				try {
					addFileToStore(
						fileVersion.getCompanyId(), PREVIEW_PATH,
						getPreviewFilePath(fileVersion, i + 1), tempFile);
				}
				finally {
					FileUtil.delete(tempFile);
				}
			}
		}
	}

	private void _generateImagesPB(
			FileVersion fileVersion, PDPage pdPage, int dpi, int height,
			int width, boolean thumbnail, int index)
		throws Exception {

		// Generate images

		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		RenderedImage renderedImage = pdPage.convertToImage(
			BufferedImage.TYPE_INT_RGB,
			PropsValues.DL_FILE_ENTRY_THUMBNAIL_DPI);

		if (height != 0) {
			renderedImage = ImageProcessorUtil.scale(
				renderedImage, width, height);
		}
		else {
			renderedImage = ImageProcessorUtil.scale(renderedImage, width);
		}

		// Store images

		File tempFile = null;

		try {
			if (thumbnail) {
				tempFile = getThumbnailTempFile(id);

				tempFile.createNewFile();

				ImageIO.write(
					renderedImage, THUMBNAIL_TYPE,
					new FileOutputStream(tempFile));

				addFileToStore(
					fileVersion.getCompanyId(), THUMBNAIL_PATH,
					getThumbnailFilePath(fileVersion), tempFile);
			}
			else {
				tempFile = getPreviewTempFile(id, index);

				tempFile.createNewFile();

				ImageIO.write(
					renderedImage, PREVIEW_TYPE,
					new FileOutputStream(tempFile));

				addFileToStore(
					fileVersion.getCompanyId(), PREVIEW_PATH,
					getPreviewFilePath(fileVersion, index), tempFile);
			}
		}
		finally {
			FileUtil.delete(tempFile);
		}
	}

	private boolean _hasImages(FileVersion fileVersion) throws Exception {
		long companyId = fileVersion.getCompanyId();

		boolean previewExists = DLStoreUtil.hasFile(
			companyId, REPOSITORY_ID, getPreviewFilePath(fileVersion, 1));
		boolean thumbnailExists = DLStoreUtil.hasFile(
			companyId, REPOSITORY_ID, getThumbnailFilePath(fileVersion));

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {

			if (previewExists && thumbnailExists) {
				return true;
			}
		}
		else  if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED && previewExists) {
			return true;
		}
		else if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
				 thumbnailExists) {

			return true;
		}

		return false;
	}

	private boolean _isGeneratePreview(FileVersion fileVersion)
		throws Exception {

		long companyId = fileVersion.getCompanyId();

		String previewFilePath = getPreviewFilePath(fileVersion, 1);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!DLStoreUtil.hasFile(companyId, REPOSITORY_ID, previewFilePath)) {

			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isGenerateThumbnail(FileVersion fileVersion)
		throws Exception {

		long companyId = fileVersion.getCompanyId();

		String thumbnailFilePath = getThumbnailFilePath(fileVersion);

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!DLStoreUtil.hasFile(companyId, REPOSITORY_ID, thumbnailFilePath)) {

			return true;
		}
		else {
			return false;
		}
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (!_fileEntries.contains(fileVersion.getFileVersionId())) {
			boolean generateImages = false;

			String extension = fileVersion.getExtension();

			if (extension.equals("pdf")) {
				generateImages = true;
			}
			else if (DocumentConversionUtil.isEnabled()) {
				String[] conversions = DocumentConversionUtil.getConversions(
					fileVersion.getExtension());

				for (String conversion : conversions) {
					if (conversion.equals("pdf")) {
						generateImages = true;

						break;
					}
				}
			}

			if (generateImages) {
				_fileEntries.add(fileVersion.getFileVersionId());

				MessageBusUtil.sendMessage(
					DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR,
					fileVersion);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PDFProcessor.class);

	private static PDFProcessor _instance = new PDFProcessor();

	private static ConvertCmd _convertCmd;
	private static List<Long> _fileEntries = new Vector<Long>();

	static {
		FileUtil.mkdirs(PREVIEW_TMP_PATH);
		FileUtil.mkdirs(THUMBNAIL_TMP_PATH);

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			String filterName = null;

			if (OSDetector.isApple()) {
				filterName = "apple";
			}
			else if (OSDetector.isWindows()) {
				filterName = "windows";
			}
			else {
				filterName = "unix";
			}

			String globalSearchPath = PropsUtil.get(
				PropsKeys.IMAGEMAGICK_GLOBAL_SEARCH_PATH,
				new Filter(filterName));

			ProcessStarter.setGlobalSearchPath(globalSearchPath);

			_convertCmd = new ConvertCmd();
		}
	}

}