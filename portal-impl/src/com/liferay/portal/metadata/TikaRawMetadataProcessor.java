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

package com.liferay.portal.metadata;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.WriteOutContentHandler;

import org.xml.sax.ContentHandler;

/**
 * @author Miguel Pastor
 * @author Alexander Chow
 * @author Shuyang Zhou
 */
public class TikaRawMetadataProcessor extends XugglerRawMetadataProcessor {

	public void setParser(Parser parser) {
		_parser = parser;
	}

	protected Metadata extractMetadata(
			InputStream inputStream, Metadata metadata)
		throws IOException {

		if (metadata == null) {
			metadata = new Metadata();
		}

		ParseContext parserContext = new ParseContext();

		parserContext.set(Parser.class, _parser);

		ContentHandler contentHandler = new WriteOutContentHandler(
			new DummyWriter());

		try {
			_parser.parse(inputStream, contentHandler, metadata, parserContext);
		}
		catch (Exception e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);

			if ((rootCause instanceof CryptographyException) ||
				(rootCause instanceof EncryptedDocumentException)) {

				_log.error("Cannot parse encrypted document.");
			}
			else {
				_log.error(e, e);
			}

			throw new IOException(e.getMessage());
		}

		// Remove potential security risks

		metadata.remove(XMPDM.ABS_PEAK_AUDIO_FILE_PATH.getName());
		metadata.remove(XMPDM.RELATIVE_PEAK_AUDIO_FILE_PATH.getName());

		return metadata;
	}

	@Override
	protected Metadata extractMetadata(
			String extension, String mimeType, File file)
		throws SystemException {

		Metadata metadata = super.extractMetadata(extension, mimeType, file);

		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);

			return extractMetadata(inputStream, metadata);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	@Override
	protected Metadata extractMetadata(
			String extension, String mimeType, InputStream inputStream)
		throws SystemException {

		Metadata metadata = super.extractMetadata(
			extension, mimeType, inputStream);

		try {
			return extractMetadata(inputStream, metadata);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		TikaRawMetadataProcessor.class);

	private Parser _parser;

}