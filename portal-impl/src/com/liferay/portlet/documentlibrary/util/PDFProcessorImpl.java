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

import com.liferay.portal.kernel.image.ImageMagickUtil;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.commons.lang.time.StopWatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;

import org.im4java.core.IMOperation;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 * @author Juan González
 * @author Sergio González
 */
public class PDFProcessorImpl
	extends DLPreviewableProcessor implements PDFProcessor {

	public static PDFProcessorImpl getInstance() {
		return _instance;
	}

	public void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		Initializer._initializedInstance._generateImages(
			sourceFileVersion, destinationFileVersion);
	}

	public InputStream getPreviewAsStream(FileVersion fileVersion, int index)
		throws Exception {

		return Initializer._initializedInstance.doGetPreviewAsStream(
			fileVersion, index, PREVIEW_TYPE);
	}

	public int getPreviewFileCount(FileVersion fileVersion) {
		try {
			return Initializer._initializedInstance.doGetPreviewFileCount(
				fileVersion);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	public long getPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return Initializer._initializedInstance.doGetPreviewFileSize(
			fileVersion, index);
	}

	public InputStream getThumbnailAsStream(FileVersion fileVersion, int index)
		throws Exception {

		return doGetThumbnailAsStream(fileVersion, index);
	}

	public long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return doGetThumbnailFileSize(fileVersion, index);
	}

	public boolean hasImages(FileVersion fileVersion) {
		boolean hasImages = false;

		try {
			hasImages = _hasImages(fileVersion);

			if (!hasImages && isSupported(fileVersion)) {
				Initializer._initializedInstance._queueGeneration(
					null, fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasImages;
	}

	public boolean isDocumentSupported(FileVersion fileVersion) {
		return Initializer._initializedInstance.isSupported(fileVersion);
	}

	public boolean isDocumentSupported(String mimeType) {
		return Initializer._initializedInstance.isSupported(mimeType);
	}

	public boolean isSupported(String mimeType) {
		if (Validator.isNull(mimeType)) {
			return false;
		}

		if (mimeType.equals(ContentTypes.APPLICATION_PDF) ||
			mimeType.equals(ContentTypes.APPLICATION_X_PDF)) {

			return true;
		}

		if (DocumentConversionUtil.isEnabled()) {
			Set<String> extensions = MimeTypesUtil.getExtensions(mimeType);

			for (String extension : extensions) {
				extension = extension.substring(1);

				String[] targetExtensions =
					DocumentConversionUtil.getConversions(extension);

				if (Arrays.binarySearch(targetExtensions, "pdf") >= 0) {
					return true;
				}
			}
		}

		return false;
	}

	public void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		Initializer._initializedInstance._queueGeneration(
			sourceFileVersion, destinationFileVersion);
	}

	@Override
	protected void copyPreviews(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		if (!PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED) {
			return;
		}

		try {
			if (hasPreview(sourceFileVersion) &&
				!hasPreview(destinationFileVersion)) {

				int count = getPreviewFileCount(sourceFileVersion);

				for (int i = 0; i < count; i++) {
					String previewFilePath = getPreviewFilePath(
						destinationFileVersion, i + 1);

					InputStream is = doGetPreviewAsStream(
						sourceFileVersion, i + 1, PREVIEW_TYPE);

					addFileToStore(
						destinationFileVersion.getCompanyId(), PREVIEW_PATH,
						previewFilePath, is);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	protected void doExportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		exportThumbnails(
			portletDataContext, fileEntry, fileEntryElement, "pdf");

		exportPreviews(portletDataContext, fileEntry, fileEntryElement);
	}

	@Override
	protected void doImportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		importThumbnails(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement,
			"pdf");

		importPreviews(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement);
	}

	protected void exportPreviews(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		FileVersion fileVersion = fileEntry.getFileVersion();

		if (!isSupported(fileVersion) || !_hasImages(fileVersion)) {
			return;
		}

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			int previewFileCount = PDFProcessorUtil.getPreviewFileCount(
				fileVersion);

			fileEntryElement.addAttribute(
				"bin-path-pdf-preview-count", String.valueOf(previewFileCount));

			for (int i = 0; i < previewFileCount; i++) {
				exportPreview(
					portletDataContext, fileEntry, fileEntryElement, "pdf",
					PREVIEW_TYPE, i);
			}
		}
	}

	@Override
	protected String getPreviewType(FileVersion fileVersion) {
		return PREVIEW_TYPE;
	}

	@Override
	protected String getThumbnailType(FileVersion fileVersion) {
		return THUMBNAIL_TYPE;
	}

	protected boolean hasPreview(FileVersion fileVersion) throws Exception {
		return hasPreview(fileVersion, null);
	}

	@Override
	protected boolean hasPreview(FileVersion fileVersion, String type)
		throws Exception {

		String previewFilePath = getPreviewFilePath(fileVersion, 1);

		return DLStoreUtil.hasFile(
			fileVersion.getCompanyId(), REPOSITORY_ID, previewFilePath);
	}

	protected void importPreviews(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		int previewFileCount = GetterUtil.getInteger(
			fileEntryElement.attributeValue("bin-path-pdf-preview-count"));

		for (int i = 0; i < previewFileCount; i++) {
			importPreview(
				portletDataContext, fileEntry, importedFileEntry,
				fileEntryElement, "pdf", PREVIEW_TYPE, i);
		}
	}

	protected void initialize() {
		try {
			FileUtil.mkdirs(PREVIEW_TMP_PATH);
			FileUtil.mkdirs(THUMBNAIL_TMP_PATH);

			ImageMagickUtil.reset();
		}
		catch (Exception e) {
			_log.warn(e, e);
		}
	}

	private PDFProcessorImpl() {
	}

	private void _generateImages(FileVersion fileVersion, File file)
		throws Exception {

		if (ImageMagickUtil.isEnabled()) {
			_generateImagesIM(fileVersion, file);
		}
		else {
			_generateImagesPB(fileVersion, file);
		}
	}

	private void _generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		InputStream inputStream = null;

		try {
			if (sourceFileVersion != null) {
				copy(sourceFileVersion, destinationFileVersion);

				return;
			}

			if (_hasImages(destinationFileVersion)) {
				return;
			}

			String extension = destinationFileVersion.getExtension();

			if (extension.equals("pdf")) {
				if (destinationFileVersion instanceof LiferayFileVersion) {
					try {
						LiferayFileVersion liferayFileVersion =
							(LiferayFileVersion)destinationFileVersion;

						File file = liferayFileVersion.getFile(false);

						_generateImages(destinationFileVersion, file);

						return;
					}
					catch (UnsupportedOperationException uoe) {
					}
				}

				inputStream = destinationFileVersion.getContentStream(false);

				_generateImages(destinationFileVersion, inputStream);
			}
			else if (DocumentConversionUtil.isEnabled()) {
				inputStream = destinationFileVersion.getContentStream(false);

				String tempFileId = DLUtil.getTempFileId(
					destinationFileVersion.getFileEntryId(),
					destinationFileVersion.getVersion());

				File file = DocumentConversionUtil.convert(
					tempFileId, inputStream, extension, "pdf");

				_generateImages(destinationFileVersion, file);
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			_fileVersionIds.remove(destinationFileVersion.getFileVersionId());
		}
	}

	private void _generateImages(
			FileVersion fileVersion, InputStream inputStream)
		throws Exception {

		if (ImageMagickUtil.isEnabled()) {
			_generateImagesIM(fileVersion, inputStream);
		}
		else {
			_generateImagesPB(fileVersion, inputStream);
		}
	}

	private void _generateImagesIM(FileVersion fileVersion, File file)
		throws Exception {

		if (_isGeneratePreview(fileVersion)) {
			StopWatch stopWatch = null;

			if (_log.isInfoEnabled()) {
				stopWatch = new StopWatch();

				stopWatch.start();
			}

			_generateImagesIM(fileVersion, file, false);

			if (_log.isInfoEnabled()) {
				int previewFileCount = getPreviewFileCount(fileVersion);

				_log.info(
					"ImageMagick generated " + previewFileCount +
						" preview pages for " + fileVersion.getTitle() +
							" in " + stopWatch);
			}
		}

		if (_isGenerateThumbnail(fileVersion)) {
			StopWatch stopWatch = null;

			if (_log.isInfoEnabled()) {
				stopWatch = new StopWatch();

				stopWatch.start();
			}

			_generateImagesIM(fileVersion, file, true);

			if (_log.isInfoEnabled()) {
				_log.info(
					"ImageMagick generated a thumbnail for " +
						fileVersion.getTitle() + " in " + stopWatch);
			}
		}
	}

	private void _generateImagesIM(
			FileVersion fileVersion, File file, boolean thumbnail)
		throws Exception {

		// Generate images

		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		IMOperation imOperation = new IMOperation();

		imOperation.alpha("off");

		imOperation.density(
			PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI,
			PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT != 0) {
			imOperation.adaptiveResize(
				PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH,
				PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT);
		}
		else {
			imOperation.adaptiveResize(
				PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH);
		}

		imOperation.depth(PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DEPTH);

		if (thumbnail) {
			imOperation.addImage(file.getPath() + "[0]");
			imOperation.addImage(getThumbnailTempFilePath(tempFileId));
		}
		else {
			imOperation.addImage(file.getPath());
			imOperation.addImage(getPreviewTempFilePath(tempFileId, -1));
		}

		ImageMagickUtil.convert(
			imOperation.getCmdArgs(),
			PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED);

		// Store images

		if (thumbnail) {
			File thumbnailTempFile = getThumbnailTempFile(tempFileId);

			try {
				storeThumbnailImages(fileVersion, thumbnailTempFile);
			}
			finally {
				FileUtil.delete(thumbnailTempFile);
			}
		}
		else {

			// ImageMagick converts single page PDFs without appending an
			// index. Rename file for consistency.

			File singlePagePreviewFile = getPreviewTempFile(tempFileId, -1);

			if (singlePagePreviewFile.exists()) {
				singlePagePreviewFile.renameTo(
					getPreviewTempFile(tempFileId, 1));
			}

			int total = getPreviewTempFileCount(fileVersion);

			for (int i = 0; i < total; i++) {
				File previewTempFile = getPreviewTempFile(tempFileId, i + 1);

				try {
					addFileToStore(
						fileVersion.getCompanyId(), PREVIEW_PATH,
						getPreviewFilePath(fileVersion, i + 1),
						previewTempFile);
				}
				finally {
					FileUtil.delete(previewTempFile);
				}
			}
		}
	}

	private void _generateImagesIM(
			FileVersion fileVersion, InputStream inputStream)
		throws Exception {

		File file = null;

		try {
			file = FileUtil.createTempFile(inputStream);

			_generateImagesIM(fileVersion, file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private void _generateImagesPB(FileVersion fileVersion, File file)
		throws Exception {

		_generateImagesPB(fileVersion, new FileInputStream(file));
	}

	private void _generateImagesPB(
			FileVersion fileVersion, InputStream inputStream)
		throws Exception {

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
					_generateImagesPB(fileVersion, pdPage, i);

					if (_log.isInfoEnabled()) {
						_log.info(
							"PDFBox generated a thumbnail for " +
								fileVersion.getFileVersionId());
					}
				}

				if (!generatePreview) {
					break;
				}

				_generateImagesPB(fileVersion, pdPage, i + 1);
			}

			if (_log.isInfoEnabled() && generatePreview) {
				_log.info(
					"PDFBox generated " +
						getPreviewFileCount(fileVersion) +
							" preview pages for " +
								fileVersion.getFileVersionId());
			}
		}
		finally {
			if (pdDocument != null) {
				pdDocument.close();
			}
		}
	}

	private void _generateImagesPB(
			FileVersion fileVersion, PDPage pdPage, int index)
		throws Exception {

		// Generate images

		RenderedImage renderedImage = pdPage.convertToImage(
			BufferedImage.TYPE_INT_RGB,
			PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT != 0) {
			renderedImage = ImageToolUtil.scale(
				renderedImage,
				PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH,
				PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT);
		}
		else {
			renderedImage = ImageToolUtil.scale(
				renderedImage,
				PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH);
		}

		// Store images

		if (index == 0) {
			storeThumbnailImages(fileVersion, renderedImage);
		}
		else {
			File tempFile = null;

			try {
				String tempFileId = DLUtil.getTempFileId(
					fileVersion.getFileEntryId(), fileVersion.getVersion());

				tempFile = getPreviewTempFile(tempFileId, index);

				tempFile.createNewFile();

				ImageIO.write(
					renderedImage, PREVIEW_TYPE,
					new FileOutputStream(tempFile));

				addFileToStore(
					fileVersion.getCompanyId(), PREVIEW_PATH,
					getPreviewFilePath(fileVersion, index), tempFile);
			}
			finally {
				FileUtil.delete(tempFile);
			}
		}
	}

	private boolean _hasImages(FileVersion fileVersion) throws Exception {
		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED) {
			if (!hasPreview(fileVersion)) {
				return false;
			}
		}

		return hasThumbnails(fileVersion);
	}

	private boolean _isGeneratePreview(FileVersion fileVersion)
		throws Exception {

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!hasPreview(fileVersion)) {

			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isGenerateThumbnail(FileVersion fileVersion)
		throws Exception {

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!hasThumbnail(fileVersion, THUMBNAIL_INDEX_DEFAULT)) {

			return true;
		}
		else {
			return false;
		}
	}

	private void _queueGeneration(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		if (_fileVersionIds.contains(
				destinationFileVersion.getFileVersionId())) {

			return;
		}

		boolean generateImages = false;

		String extension = destinationFileVersion.getExtension();

		if (extension.equals("pdf")) {
			generateImages = true;
		}
		else if (DocumentConversionUtil.isEnabled()) {
			String[] conversions = DocumentConversionUtil.getConversions(
				extension);

			for (String conversion : conversions) {
				if (conversion.equals("pdf")) {
					generateImages = true;

					break;
				}
			}
		}

		if (generateImages) {
			_fileVersionIds.add(destinationFileVersion.getFileVersionId());

			sendGenerationMessage(
				DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR,
				PropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY,
				sourceFileVersion, destinationFileVersion);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PDFProcessorImpl.class);

	private static PDFProcessorImpl _instance = new PDFProcessorImpl();

	static {
		InstancePool.put(PDFProcessorImpl.class.getName(), _instance);
	}

	private List<Long> _fileVersionIds = new Vector<Long>();

	private static class Initializer {

		private static PDFProcessorImpl _initializedInstance;

		static {
			_instance.initialize();

			_initializedInstance = _instance;
		}

	}

}