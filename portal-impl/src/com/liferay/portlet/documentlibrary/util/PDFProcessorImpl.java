/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.image.GhostscriptUtil;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import org.apache.commons.lang.time.StopWatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 * @author Juan González
 * @author Sergio González
 * @author Ivica Cardic
 */
public class PDFProcessorImpl
	extends DLPreviewableProcessor implements PDFProcessor {

	@Override
	public void afterPropertiesSet() throws Exception {
		FileUtil.mkdirs(PREVIEW_TMP_PATH);
		FileUtil.mkdirs(THUMBNAIL_TMP_PATH);
	}

	@Override
	public void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		_generateImages(sourceFileVersion, destinationFileVersion);
	}

	@Override
	public InputStream getPreviewAsStream(FileVersion fileVersion, int index)
		throws Exception {

		return doGetPreviewAsStream(fileVersion, index, PREVIEW_TYPE);
	}

	@Override
	public int getPreviewFileCount(FileVersion fileVersion) {
		try {
			return doGetPreviewFileCount(fileVersion);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	@Override
	public long getPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return doGetPreviewFileSize(fileVersion, index);
	}

	@Override
	public InputStream getThumbnailAsStream(FileVersion fileVersion, int index)
		throws Exception {

		return doGetThumbnailAsStream(fileVersion, index);
	}

	@Override
	public long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return doGetThumbnailFileSize(fileVersion, index);
	}

	@Override
	public boolean hasImages(FileVersion fileVersion) {
		boolean hasImages = false;

		try {
			hasImages = _hasImages(fileVersion);

			if (!hasImages && isSupported(fileVersion)) {
				_queueGeneration(null, fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasImages;
	}

	@Override
	public boolean isDocumentSupported(FileVersion fileVersion) {
		return isSupported(fileVersion);
	}

	@Override
	public boolean isDocumentSupported(String mimeType) {
		return isSupported(mimeType);
	}

	@Override
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

	@Override
	public void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		super.trigger(sourceFileVersion, destinationFileVersion);

		_queueGeneration(sourceFileVersion, destinationFileVersion);
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
			int previewFileCount = getPreviewFileCount(fileVersion);

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
	protected List<Long> getFileVersionIds() {
		return _fileVersionIds;
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

	private void _generateImages(FileVersion fileVersion, File file)
		throws Exception {

		if (GhostscriptUtil.isEnabled()) {
			if (!_ghostscriptInitialized) {
				GhostscriptUtil.reset();

				_ghostscriptInitialized = true;
			}

			_generateImagesGS(fileVersion, file);
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

		if (GhostscriptUtil.isEnabled()) {
			_generateImagesGS(fileVersion, inputStream);
		}
		else {
			_generateImagesPB(fileVersion, inputStream);
		}
	}

	private void _generateImagesGS(FileVersion fileVersion, File file)
		throws Exception {

		if (_isGeneratePreview(fileVersion)) {
			StopWatch stopWatch = null;

			if (_log.isInfoEnabled()) {
				stopWatch = new StopWatch();

				stopWatch.start();
			}

			_generateImagesGS(fileVersion, file, false);

			if (_log.isInfoEnabled()) {
				int previewFileCount = getPreviewFileCount(fileVersion);

				_log.info(
					"Ghostscript generated " + previewFileCount +
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

			_generateImagesGS(fileVersion, file, true);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Ghostscript generated a thumbnail for " +
						fileVersion.getTitle() + " in " + stopWatch);
			}
		}
	}

	private void _generateImagesGS(
			FileVersion fileVersion, File file, boolean thumbnail)
		throws Exception {

		// Generate images

		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		List<String> arguments = new ArrayList<String>();

		arguments.add("-sDEVICE=png16m");

		if (thumbnail) {
			arguments.add(
				"-sOutputFile=" + getThumbnailTempFilePath(tempFileId));
			arguments.add("-dFirstPage=1");
			arguments.add("-dLastPage=1");
		}
		else {
			arguments.add(
				"-sOutputFile=" + getPreviewTempFilePath(tempFileId, -1));
		}

		arguments.add("-dPDFFitPage");
		arguments.add("-dTextAlphaBits=4");
		arguments.add("-dGraphicsAlphaBits=4");
		arguments.add("-r" + PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH != 0) {
			arguments.add(
				"-dDEVICEWIDTH" +
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH);
		}

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT != 0) {
			arguments.add(
				"-dDEVICEHEIGHT" +
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT);
		}

		arguments.add(file.getPath());

		Future<?> future = GhostscriptUtil.execute(arguments);

		String processIdentity = String.valueOf(fileVersion.getFileVersionId());

		futures.put(processIdentity, future);

		future.get();

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
			int total = getPreviewTempFileCount(fileVersion);

			for (int i = 0; i < total; i++) {
				File previewTempFile = getPreviewTempFile(tempFileId, i + 2);

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

	private void _generateImagesGS(
			FileVersion fileVersion, InputStream inputStream)
		throws Exception {

		File file = null;

		try {
			file = FileUtil.createTempFile(inputStream);

			_generateImagesGS(fileVersion, file);
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

	private List<Long> _fileVersionIds = new Vector<Long>();
	private boolean _ghostscriptInitialized = false;

}