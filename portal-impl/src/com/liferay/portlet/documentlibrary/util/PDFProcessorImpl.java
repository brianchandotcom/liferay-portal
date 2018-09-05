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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.PDFProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.log4j.Log4JUtil;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.fabric.InputResource;
import com.liferay.portal.fabric.OutputResource;
import com.liferay.portal.kernel.image.GhostscriptUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.SystemEnv;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PortalClassPathUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.time.StopWatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

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
		FileUtil.mkdirs(DECRYPT_TMP_PATH);
		FileUtil.mkdirs(PREVIEW_TMP_PATH);
		FileUtil.mkdirs(THUMBNAIL_TMP_PATH);
	}

	@Override
	public void destroy() {
		FileUtil.deltree(TMP_PATH);
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
	public String getType() {
		return DLProcessorConstants.PDF_PROCESSOR;
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

	private void _addDimensions(List<String> arguments, File file)
		throws Exception {

		Map<String, Integer> scaledDimensions = _getScaledDimensions(file);

		if ((PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH != 0) &&
			(PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT != 0)) {

			arguments.add(
				"-dDEVICEWIDTH=" +
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH);

			arguments.add(
				"-dDEVICEHEIGHT=" +
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT);
		}
		else if ((PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH != 0) &&
				 (PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT == 0)) {

			arguments.add(
				"-dDEVICEWIDTH=" +
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH);

			arguments.add("-dDEVICEHEIGHT=" + scaledDimensions.get("height"));
		}
		else if ((PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH == 0) &&
				 (PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT != 0)) {

			arguments.add("-dDEVICEWIDTH=" + scaledDimensions.get("width"));

			arguments.add(
				"-dDEVICEHEIGHT=" +
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT);
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

				try (InputStream inputStream =
						destinationFileVersion.getContentStream(false)) {

					_generateImages(destinationFileVersion, inputStream);
				}
			}
			else if (DocumentConversionUtil.isEnabled()) {
				try (InputStream inputStream =
						destinationFileVersion.getContentStream(false)) {

					String tempFileId = DLUtil.getTempFileId(
						destinationFileVersion.getFileEntryId(),
						destinationFileVersion.getVersion());

					if (Objects.equals(
							"PWC", destinationFileVersion.getVersion()) ||
						destinationFileVersion.isPending()) {

						File file = new File(
							DocumentConversionUtil.getFilePath(
								tempFileId, "pdf"));

						FileUtil.delete(file);
					}

					File file = DocumentConversionUtil.convert(
						tempFileId, inputStream, extension, "pdf");

					_generateImages(destinationFileVersion, file);
				}
			}
		}
		catch (NoSuchFileEntryException nsfee) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfee, nsfee);
			}
		}
		finally {
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
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			_generateImagesGS(fileVersion, file, false);

			if (_log.isInfoEnabled()) {
				int previewFileCount = getPreviewFileCount(fileVersion);

				_log.info(
					StringBundler.concat(
						"Ghostscript generated ",
						String.valueOf(previewFileCount), " preview pages for ",
						fileVersion.getTitle(), " in ",
						String.valueOf(stopWatch.getTime()), " ms"));
			}
		}

		if (_isGenerateThumbnail(fileVersion)) {
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			_generateImagesGS(fileVersion, file, true);

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Ghostscript generated a thumbnail for ",
						fileVersion.getTitle(), " in ",
						String.valueOf(stopWatch.getTime()), " ms"));
			}
		}
	}

	private void _generateImagesGS(
			FileVersion fileVersion, File file, boolean thumbnail)
		throws Exception {

		// Generate images

		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		List<String> arguments = new ArrayList<>();

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

		_addDimensions(arguments, file);

		arguments.add(file.getPath());

		Future<?> future = GhostscriptUtil.execute(arguments);

		String processIdentity = String.valueOf(fileVersion.getFileVersionId());

		long ghostscriptTimeout =
			PropsValues.DL_FILE_ENTRY_PREVIEW_GENERATION_TIMEOUT_GHOSTSCRIPT;

		if (_log.isDebugEnabled()) {
			if (thumbnail) {
				_log.debug(
					StringBundler.concat(
						"Waiting for ", String.valueOf(ghostscriptTimeout),
						" seconds to generate thumbnail for ", file.getPath()));
			}
			else {
				_log.debug(
					StringBundler.concat(
						"Waiting for ", String.valueOf(ghostscriptTimeout),
						" seconds to generate preview for ", file.getPath()));
			}
		}

		try {
			future.get(ghostscriptTimeout, TimeUnit.SECONDS);

			futures.put(processIdentity, future);
		}
		catch (TimeoutException te) {
			String errorMessage =
				"Timeout when generating preview for " + file.getPath();

			if (thumbnail) {
				errorMessage =
					"Timeout when generating thumbanil for " + file.getPath();
			}

			if (future.cancel(true)) {
				errorMessage += " resulted in a canceled timeout for " + future;
			}

			_log.error(errorMessage);

			throw te;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}

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

		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File decryptedFile = getDecryptedTempFile(tempFileId);

		int previewFilesCount = _getPreviewFilesCount(file, decryptedFile);

		if (previewFilesCount == 0) {
			_log.error(
				"Unable to decrypt PDF document for file version " +
					fileVersion.getFileVersionId());

			return;
		}

		File thumbnailFile = getThumbnailTempFile(tempFileId);

		File[] previewFiles = new File[previewFilesCount];

		for (int i = 0; i < previewFilesCount; i++) {
			previewFiles[i] = getPreviewTempFile(tempFileId, i);
		}

		boolean generatePreview = _isGeneratePreview(fileVersion);
		boolean generateThumbnail = _isGenerateThumbnail(fileVersion);

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED) {
			ProcessCallable<String> processCallable =
				new LiferayPDFBoxProcessCallable(
					ServerDetector.getServerId(),
					PropsUtil.get(PropsKeys.LIFERAY_HOME),
					Log4JUtil.getCustomLogSettings(), decryptedFile,
					thumbnailFile, previewFiles, getThumbnailType(fileVersion),
					getPreviewType(fileVersion),
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH,
					generatePreview, generateThumbnail);

			ProcessChannel<String> processChannel = _processExecutor.execute(
				PortalClassPathUtil.getPortalProcessConfig(), processCallable);

			Future<String> future = processChannel.getProcessNoticeableFuture();

			String processIdentity = String.valueOf(
				fileVersion.getFileVersionId());

			long pdfBoxTimeout =
				PropsValues.DL_FILE_ENTRY_PREVIEW_GENERATION_TIMEOUT_PDFBOX;

			if (_log.isDebugEnabled()) {
				if (generateThumbnail && generatePreview) {
					_log.debug(
						StringBundler.concat(
							"Waiting for ", String.valueOf(pdfBoxTimeout),
							" seconds to generate thumbnail and preview for ",
							decryptedFile.getPath()));
				}
				else {
					if (generateThumbnail) {
						_log.debug(
							StringBundler.concat(
								"Waiting for ", String.valueOf(pdfBoxTimeout),
								" seconds to generate thumbnail for ",
								decryptedFile.getPath()));
					}

					if (generatePreview) {
						_log.debug(
							StringBundler.concat(
								"Waiting for ", String.valueOf(pdfBoxTimeout),
								" seconds to generate preview for ",
								decryptedFile.getPath()));
					}
				}
			}

			try {
				future.get(pdfBoxTimeout, TimeUnit.SECONDS);

				futures.put(processIdentity, future);
			}
			catch (TimeoutException te) {
				String errorMessage = null;

				if (generateThumbnail && generatePreview) {
					errorMessage =
						"Timeout when generating thumbnail and preview for " +
							decryptedFile.getPath();
				}
				else {
					if (generateThumbnail) {
						errorMessage =
							"Timeout when generating thumbnail for " +
								decryptedFile.getPath();
					}

					if (generatePreview) {
						errorMessage =
							"Timeout when generating preview for " +
								decryptedFile.getPath();
					}
				}

				if (future.cancel(true)) {
					errorMessage +=
						" resulted in a canceled timeout for " + future;
				}

				_log.error(errorMessage);

				throw te;
			}
			catch (Exception e) {
				_log.error(e, e);

				throw e;
			}
		}
		else {
			LiferayPDFBoxConverter liferayConverter =
				new LiferayPDFBoxConverter(
					decryptedFile, thumbnailFile, previewFiles,
					getPreviewType(fileVersion), getThumbnailType(fileVersion),
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_DPI,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT,
					PropsValues.DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH,
					generatePreview, generateThumbnail);

			liferayConverter.generateImagesPB();
		}

		FileUtil.delete(decryptedFile);

		if (generateThumbnail) {
			try {
				storeThumbnailImages(fileVersion, thumbnailFile);
			}
			finally {
				FileUtil.delete(thumbnailFile);
			}
		}

		if (generatePreview) {
			int index = 0;

			for (File previewFile : previewFiles) {
				try {
					addFileToStore(
						fileVersion.getCompanyId(), PREVIEW_PATH,
						getPreviewFilePath(fileVersion, index + 1),
						previewFile);
				}
				finally {
					FileUtil.delete(previewFile);
				}

				index++;
			}
		}

		if (_log.isInfoEnabled()) {
			long fileVersionId = fileVersion.getFileVersionId();
			int previewFileCount = getPreviewFileCount(fileVersion);
			long time = stopWatch.getTime();

			if (generateThumbnail && generatePreview) {
				_log.info(
					StringBundler.concat(
						"PDFBox generated a thumbnail and ",
						String.valueOf(previewFileCount), " preview pages for ",
						String.valueOf(fileVersionId), " in ",
						String.valueOf(time), " ms"));
			}
			else {
				if (generateThumbnail) {
					_log.info(
						StringBundler.concat(
							"PDFBox generated a thumbnail for ",
							String.valueOf(fileVersionId), " in ",
							String.valueOf(time), " ms"));
				}

				if (generatePreview) {
					_log.info(
						StringBundler.concat(
							"PDFBox generated ",
							String.valueOf(previewFileCount),
							" preview pages for ",
							String.valueOf(fileVersionId), " in ",
							String.valueOf(time), " ms"));
				}
			}
		}
	}

	private void _generateImagesPB(
			FileVersion fileVersion, InputStream inputStream)
		throws Exception {

		File file = null;

		try {
			file = FileUtil.createTempFile(inputStream);

			_generateImagesPB(fileVersion, file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private int _getPreviewFilesCount(File encryptedFile, File decryptedFile) {
		String[] decryptPasswords = ArrayUtil.append(
			PropsValues.
				DL_FILE_ENTRY_PREVIEW_GENERATION_DECRYPT_PASSWORDS_PDFBOX,
			StringPool.BLANK);

		for (String decryptPassword : decryptPasswords) {
			try (PDDocument pdDocument =
					PDDocument.load(encryptedFile, decryptPassword)) {

				pdDocument.setAllSecurityToBeRemoved(true);

				pdDocument.save(decryptedFile);

				return pdDocument.getNumberOfPages();
			}
			catch (IOException ioe) {
				if (!(ioe instanceof InvalidPasswordException)) {
					_log.error(ioe, ioe);
				}
			}
		}

		return 0;
	}

	private Map<String, Integer> _getScaledDimensions(File file)
		throws Exception {

		try (PDDocument pdDocument = PDDocument.load(file)) {
			Map<String, Integer> scaledDimensions = new HashMap<>();

			PDDocumentCatalog pdDocumentCatalog =
				pdDocument.getDocumentCatalog();

			PDPageTree pages = pdDocumentCatalog.getPages();

			PDPage pdPage = pages.get(0);

			PDRectangle pdRectangle = pdPage.getMediaBox();

			float width = pdRectangle.getWidth();

			double widthFactor =
				(double)PropsValues.
					DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_WIDTH / width;

			float height = pdRectangle.getHeight();

			int scaledHeight = (int)Math.round(widthFactor * height);

			scaledDimensions.put("height", scaledHeight);

			double heightFactor =
				(double)PropsValues.
					DL_FILE_ENTRY_PREVIEW_DOCUMENT_MAX_HEIGHT / height;

			int scaledWidth = (int)Math.round(heightFactor * width);

			scaledDimensions.put("width", scaledWidth);

			return scaledDimensions;
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

		return false;
	}

	private boolean _isGenerateThumbnail(FileVersion fileVersion)
		throws Exception {

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!hasThumbnail(fileVersion, THUMBNAIL_INDEX_DEFAULT)) {

			return true;
		}

		return false;
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
				sourceFileVersion, destinationFileVersion);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PDFProcessorImpl.class);

	private static volatile ProcessExecutor _processExecutor =
		ServiceProxyFactory.newServiceTrackedInstance(
			ProcessExecutor.class, PDFProcessorImpl.class, "_processExecutor",
			true);

	private final List<Long> _fileVersionIds = new Vector<>();
	private boolean _ghostscriptInitialized;

	private static class LiferayPDFBoxProcessCallable
		implements ProcessCallable<String> {

		public LiferayPDFBoxProcessCallable(
			String serverId, String liferayHome,
			Map<String, String> customLogSettings, File inputFile,
			File thumbnailFile, File[] previewFiles, String extension,
			String thumbnailExtension, int dpi, int height, int width,
			boolean generatePreview, boolean generateThumbnail) {

			_serverId = serverId;
			_liferayHome = liferayHome;
			_customLogSettings = customLogSettings;
			_inputFile = inputFile;
			_thumbnailFile = thumbnailFile;
			_previewFiles = previewFiles;
			_extension = extension;
			_thumbnailExtension = thumbnailExtension;
			_dpi = dpi;
			_height = height;
			_width = width;
			_generatePreview = generatePreview;
			_generateThumbnail = generateThumbnail;
		}

		@Override
		public String call() throws ProcessException {
			Properties systemProperties = System.getProperties();

			SystemEnv.setProperties(systemProperties);

			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			Log4JUtil.initLog4J(
				_serverId, _liferayHome, classLoader, new Log4jLogFactoryImpl(),
				_customLogSettings);

			try {
				LiferayPDFBoxConverter liferayConverter =
					new LiferayPDFBoxConverter(
						_inputFile, _thumbnailFile, _previewFiles, _extension,
						_thumbnailExtension, _dpi, _height, _width,
						_generatePreview, _generateThumbnail);

				liferayConverter.generateImagesPB();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return StringPool.BLANK;
		}

		private static final long serialVersionUID = 1L;

		private final Map<String, String> _customLogSettings;
		private final int _dpi;
		private final String _extension;
		private final boolean _generatePreview;
		private final boolean _generateThumbnail;
		private final int _height;

		@InputResource
		private final File _inputFile;

		private final String _liferayHome;

		@OutputResource
		private final File[] _previewFiles;

		private final String _serverId;
		private final String _thumbnailExtension;

		@OutputResource
		private final File _thumbnailFile;

		private final int _width;

	}

}