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

import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.document.library.kernel.util.PDFProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.xml.Element;

import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 * @author Juan González
 * @author Sergio González
 * @author Ivica Cardic
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class PDFProcessorImpl
	extends DLPreviewableProcessor implements PDFProcessor {

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		_pdfProcessor.generateImages(sourceFileVersion, destinationFileVersion);
	}

	@Override
	public InputStream getPreviewAsStream(FileVersion fileVersion, int index)
		throws Exception {

		return _pdfProcessor.getPreviewAsStream(fileVersion, index);
	}

	@Override
	public int getPreviewFileCount(FileVersion fileVersion) {
		return _pdfProcessor.getPreviewFileCount(fileVersion);
	}

	@Override
	public long getPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return _pdfProcessor.getPreviewFileSize(fileVersion, index);
	}

	@Override
	public InputStream getThumbnailAsStream(FileVersion fileVersion, int index)
		throws Exception {

		return _pdfProcessor.getThumbnailAsStream(fileVersion, index);
	}

	@Override
	public long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return _pdfProcessor.getThumbnailFileSize(fileVersion, index);
	}

	@Override
	public String getType() {
		return DLProcessorConstants.PDF_PROCESSOR;
	}

	@Override
	public boolean hasImages(FileVersion fileVersion) {
		return _pdfProcessor.hasImages(fileVersion);
	}

	@Override
	public boolean isDocumentSupported(FileVersion fileVersion) {
		return _pdfProcessor.isDocumentSupported(fileVersion);
	}

	@Override
	public boolean isDocumentSupported(String mimeType) {
		return _pdfProcessor.isDocumentSupported(mimeType);
	}

	@Override
	public boolean isSupported(String mimeType) {
		return _pdfProcessor.isSupported(mimeType);
	}

	@Override
	public void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		_pdfProcessor.trigger(sourceFileVersion, destinationFileVersion);
	}

	@Override
	protected void copyPreviews(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		throw new UnsupportedOperationException();
	}

	@Override
	protected void doExportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected void doImportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected void exportPreviews(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected List<Long> getFileVersionIds() {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean hasPreview(FileVersion fileVersion, String type)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected void importPreviews(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("deprecation")
	private static volatile PDFProcessor _pdfProcessor =
		ServiceProxyFactory.newServiceTrackedInstance(
			PDFProcessor.class, PDFProcessorImpl.class, "_pdfProcessor", true);

}