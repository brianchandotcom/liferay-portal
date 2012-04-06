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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import javax.portlet.PortletPreferences;

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

	public void generateImages(FileVersion fileVersion) throws Exception {
		Initializer._initializedInstance._generateImages(fileVersion);
	}

	public String getGlobalSearchPath() throws Exception {
		PortletPreferences preferences = PrefsPropsUtil.getPreferences();

		String globalSearchPath = preferences.getValue(
			PropsKeys.IMAGEMAGICK_GLOBAL_SEARCH_PATH, null);

		if (Validator.isNotNull(globalSearchPath)) {
			return globalSearchPath;
		}

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

		return PropsUtil.get(
			PropsKeys.IMAGEMAGICK_GLOBAL_SEARCH_PATH, new Filter(filterName));
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

	public Properties getResourceLimitsProperties() throws Exception {
		Properties resourceLimitsProperties = PrefsPropsUtil.getProperties(
			PropsKeys.IMAGEMAGICK_RESOURCE_LIMIT, true);

		if (resourceLimitsProperties.isEmpty()) {
			resourceLimitsProperties = PropsUtil.getProperties(
				PropsKeys.IMAGEMAGICK_RESOURCE_LIMIT, true);
		}

		return resourceLimitsProperties;
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
				Initializer._initializedInstance._queueGeneration(fileVersion);
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

	public boolean isImageMagickEnabled() throws Exception {
		if (PrefsPropsUtil.getBoolean(PropsKeys.IMAGEMAGICK_ENABLED)) {
			return true;
		}

		if (!_warned) {
			StringBundler sb = new StringBundler(5);

			sb.append("Liferay is not configured to use ImageMagick for ");
			sb.append("generating Documents and Media previews and will ");
			sb.append("default to PDFBox. For better quality previews, ");
			sb.append("install ImageMagick and enable it in ");
			sb.append("portal-ext.properties.");

			_log.warn(sb.toString());

			_warned = true;
		}

		return false;
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

	public void reset() throws Exception {
		if (isImageMagickEnabled()) {
			_globalSearchPath = getGlobalSearchPath();

			_resourceLimitsProperties = getResourceLimitsProperties();
		}
	}

	public void trigger(FileVersion fileVersion) {
		Initializer._initializedInstance._queueGeneration(fileVersion);
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

			reset();
		}
		catch (Exception e) {
			_log.warn(e, e);
		}
	}

	private PDFProcessorImpl() {
	}

	private void _generateImages(FileVersion fileVersion) throws Exception {
		InputStream inputStream = null;

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

				inputStream = fileVersion.getContentStream(false);

				_generateImages(fileVersion, inputStream);
			}
			else if (DocumentConversionUtil.isEnabled()) {
				inputStream = fileVersion.getContentStream(false);

				String tempFileId = DLUtil.getTempFileId(
					fileVersion.getFileEntryId(), fileVersion.getVersion());

				File file = DocumentConversionUtil.convert(
					tempFileId, inputStream, extension, "pdf");

				_generateImages(fileVersion, file);
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			_fileVersionIds.remove(fileVersion.getFileVersionId());
		}
	}

	private void _generateImages(FileVersion fileVersion, File file)
		throws Exception {

		if (isImageMagickEnabled()) {
			_generateImagesIM(fileVersion, file);
		}
		else {
			_generateImagesPB(fileVersion, file);
		}
	}

	private void _generateImages(
			FileVersion fileVersion, InputStream inputStream)
		throws Exception {

		if (isImageMagickEnabled()) {
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

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_FORK_PROCESS_ENABLED) {
			ProcessCallable<String> processCallable =
				new ImageMagickProcessCallable(
					_globalSearchPath, _resourceLimitsProperties,
					imOperation.getCmdArgs());

			Future<String> future = ProcessExecutor.execute(
				ClassPathUtil.getPortalClassPath(), processCallable);

			future.get();
		}
		else {
			LiferayConvertCmd.run(
				_globalSearchPath, _resourceLimitsProperties,
				imOperation.getCmdArgs());
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
			if (!DLStoreUtil.hasFile(
					fileVersion.getCompanyId(), REPOSITORY_ID,
					getPreviewFilePath(fileVersion, 1))) {

				return false;
			}
		}

		return hasThumbnails(fileVersion);
	}

	private boolean _isGeneratePreview(FileVersion fileVersion)
		throws Exception {

		String previewFilePath = getPreviewFilePath(fileVersion, 1);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID, previewFilePath)) {

			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isGenerateThumbnail(FileVersion fileVersion)
		throws Exception {

		String thumbnailFilePath = getThumbnailFilePath(
			fileVersion, THUMBNAIL_INDEX_DEFAULT);

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID, thumbnailFilePath)) {

			return true;
		}
		else {
			return false;
		}
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (_fileVersionIds.contains(fileVersion.getFileVersionId())) {
			return;
		}

		boolean generateImages = false;

		String extension = fileVersion.getExtension();

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
			_fileVersionIds.add(fileVersion.getFileVersionId());

			if (PropsValues.DL_FILE_ENTRY_PROCESSORS_TRIGGER_SYNCHRONOUSLY) {
				try {
					MessageBusUtil.sendSynchronousMessage(
						DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR,
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
					DestinationNames.DOCUMENT_LIBRARY_PDF_PROCESSOR,
					fileVersion);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PDFProcessorImpl.class);

	private static PDFProcessorImpl _instance = new PDFProcessorImpl();

	static {
		InstancePool.put(PDFProcessorImpl.class.getName(), _instance);
	}

	private List<Long> _fileVersionIds = new Vector<Long>();
	private String _globalSearchPath;
	private Properties _resourceLimitsProperties;
	private boolean _warned;

	private static class ImageMagickProcessCallable
		implements ProcessCallable<String> {

		public ImageMagickProcessCallable(
			String globalSearchPath, Properties resourceLimits,
			LinkedList<String> commandArguments) {

			_globalSearchPath = globalSearchPath;
			_commandArguments = commandArguments;
			_resourceLimits = resourceLimits;
		}

		public String call() throws ProcessException {
			try {
				LiferayConvertCmd.run(
					_globalSearchPath, _resourceLimits, _commandArguments);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return StringPool.BLANK;
		}

		private LinkedList<String> _commandArguments;
		private String _globalSearchPath;
		private Properties _resourceLimits;

	}

	private static class Initializer {

		private static PDFProcessorImpl _initializedInstance;

		static {
			_instance.initialize();

			_initializedInstance = _instance;
		}

	}

}