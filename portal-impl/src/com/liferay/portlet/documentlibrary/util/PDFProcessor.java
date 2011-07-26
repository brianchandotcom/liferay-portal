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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageProcessor;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.io.FileFilter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.util.SystemProperties;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
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
 */
public class PDFProcessor implements DLProcessor {

	public static final String PREVIEW_TYPE = ImageProcessor.TYPE_PNG;

	public static final String THUMBNAIL_TYPE = ImageProcessor.TYPE_PNG;

	public static void generateImages(FileVersion fileVersion) {
		_instance._generateImages(fileVersion);
	}

	public static File getPreviewFile(String id, int index) {
		return _instance._getPreviewFile(id, index);
	}

	public static int getPreviewFileCount(FileEntry fileEntry, String version) {
		try {
			FileVersion fileVersion = fileEntry.getFileVersion(version);

			return _instance._getPreviewFileCount(fileVersion);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	public static File getThumbnailFile(String id) {
		return _instance._getThumbnailFile(id);
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

	private void _generateImages(FileVersion fileVersion) {
		try {
			if (_hasImages(fileVersion)) {
				return;
			}

			String extension = fileVersion.getExtension();

			if (extension.equals("pdf")) {
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
		throws IOException, PortalException, SystemException {

		if (PropsValues.IMAGEMAGICK_ENABLED) {
			String id = DLUtil.getTempFileId(
				fileVersion.getFileEntryId(), fileVersion.getVersion());

			if (_isGeneratePreview(id)) {
				_generateImagesIM(
					file, id, PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DEPTH,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_HEIGHT,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_WIDTH, false);

				// ImageMagick converts single page PDFs without appending an
				// index. Rename file for consistency.

				File singlePagePreviewFile = _getPreviewFile(id, -1);

				if (singlePagePreviewFile.exists()) {
					singlePagePreviewFile.renameTo(_getPreviewFile(id, 1));
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						"ImageMagick generated " +
							_getPreviewFileCount(fileVersion) +
								" preview pages for " + id);
				}
			}

			if (_isGenerateThumbnail(id)) {
				_generateImagesIM(
					file, id, PropsValues.DL_FILE_ENTRY_THUMBNAIL_DEPTH,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_DPI,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT,
					PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH, true);

				if (_log.isInfoEnabled()) {
					_log.info("ImageMagick generated a thumbnail for " + id);
				}
			}
		}
		else {
			_generateImages(fileVersion, new FileInputStream(file));
		}
	}

	private void _generateImages(
			FileVersion fileVersion, InputStream inputStream)
		throws IOException, PortalException, SystemException {

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
					file.delete();
				}
			}
			else {
				File file = DocumentConversionUtil.convert(
					id, inputStream, fileVersion.getExtension(), "pdf");

				_generateImages(fileVersion, file);
			}
		}
		else {
			boolean generatePreview = _isGeneratePreview(id);
			boolean generateThumbnail = _isGenerateThumbnail(id);

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
							pdPage, id, PropsValues.DL_FILE_ENTRY_THUMBNAIL_DPI,
							PropsValues.DL_FILE_ENTRY_THUMBNAIL_HEIGHT,
							PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH, true, 0);

						if (_log.isInfoEnabled()) {
							_log.info("PDFBox generated a thumbnail for " + id);
						}
					}

					if (!generatePreview) {
						break;
					}

					_generateImagesPB(
						pdPage, id,
						PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI,
						PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_WIDTH, false,
						i + 1);
				}

				if (_log.isInfoEnabled() && generatePreview) {
					_log.info(
						"PDFBox generated " +
							_getPreviewFileCount(fileVersion) +
								" preview pages for " + id);
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
			File file, String id, int depth, int dpi, int height, int width,
			boolean thumbnail)
		throws IOException, SystemException {

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
			imOperation.addImage(_getThumbnailFilePath(id));
		}
		else {
			imOperation.addImage(file.getPath());
			imOperation.addImage(_getPreviewFilePath(id, -1));
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
	}

	private void _generateImagesPB(
			PDPage pdPage, String id, int dpi, int height, int width,
			boolean thumbnail, int index)
		throws IOException, FileNotFoundException {

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

		if (thumbnail) {
			File file = _getThumbnailFile(id);

			file.createNewFile();

			ImageIO.write(
				renderedImage, THUMBNAIL_TYPE, new FileOutputStream(file));
		}
		else {
			File file = _getPreviewFile(id, index);

			file.createNewFile();

			ImageIO.write(
				renderedImage, PREVIEW_TYPE, new FileOutputStream(file));
		}
	}

	private File _getPreviewFile(String id, int index) {
		String filePath = _getPreviewFilePath(id, index);

		return new File(filePath);
	}

	private String _getPreviewFilePath(String id, int index) {
		StringBundler sb;

		if (index <= 0 ) {
			sb = new StringBundler(4);
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_PREVIEW_PATH);
		sb.append(id);

		if (index > 0) {
			sb.append(StringPool.DASH);
			sb.append(index - 1);
		}

		sb.append(StringPool.PERIOD);
		sb.append(PREVIEW_TYPE);

		return sb.toString();
	}

	private int _getPreviewFileCount(FileVersion fileVersion) {
		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		StringBundler sb = new StringBundler(5);

		sb.append(id);
		sb.append(StringPool.DASH);
		sb.append("(.*)");
		sb.append(StringPool.PERIOD);
		sb.append(PREVIEW_TYPE);

		File dir = new File(_PREVIEW_PATH);

		File[] files = dir.listFiles(new FileFilter(sb.toString()));

		if (_log.isDebugEnabled()) {
			for (File file : files) {
				_log.debug("Preview page for " + id + " " + file);
			}
		}

		return files.length;
	}

	private File _getThumbnailFile(String id) {
		String filePath = _getThumbnailFilePath(id);

		return new File(filePath);
	}

	private String _getThumbnailFilePath(String id) {
		StringBundler sb = new StringBundler(4);

		sb.append(_THUMBNAIL_PATH);
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(THUMBNAIL_TYPE);

		return sb.toString();
	}

	private boolean _hasImages(FileVersion fileVersion) {
		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File previewFile = _getPreviewFile(id, 1);

		Date statusDate = fileVersion.getStatusDate();

		if (previewFile.lastModified() < statusDate.getTime()) {
			int previewFileCount = _getPreviewFileCount(fileVersion);

			for (int i = 0; i < previewFileCount; i++) {
				File file = _getPreviewFile(id, i + 1);

				file.delete();
			}
		}

		File thumbnailFile = _getThumbnailFile(id);

		if (thumbnailFile.lastModified() < statusDate.getTime()) {
			thumbnailFile.delete();
		}

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {

			if (previewFile.exists() && thumbnailFile.exists()) {
				return true;
			}
		}
		else  if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
				  previewFile.exists()) {

			return true;
		}
		else if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
				 thumbnailFile.exists()) {

			return true;
		}

		return false;
	}

	private boolean _isGeneratePreview(String id) {
		File previewFile = _getPreviewFile(id, 1);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!previewFile.exists()) {

			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isGenerateThumbnail(String id) {
		File thumbnailFile = _getThumbnailFile(id);

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!thumbnailFile.exists()) {

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

	private static final String _PREVIEW_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/document_preview/";

	private static final String _THUMBNAIL_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/document_thumbnail/";

	private static Log _log = LogFactoryUtil.getLog(PDFProcessor.class);

	private static PDFProcessor _instance = new PDFProcessor();

	private static ConvertCmd _convertCmd;
	private static List<Long> _fileEntries = new Vector<Long>();

	static {
		FileUtil.mkdirs(_PREVIEW_PATH);
		FileUtil.mkdirs(_THUMBNAIL_PATH);

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