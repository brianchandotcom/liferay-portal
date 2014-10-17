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

package com.liferay.portal.metadata;

import com.liferay.portal.fabric.InputResource;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutorUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.concurrent.Future;

import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.XMPDM;
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

	protected static Metadata extractMetadata(
			File file, Metadata metadata, Parser parser)
		throws IOException {

		if (metadata == null) {
			metadata = new Metadata();
		}

		ParseContext parserContext = new ParseContext();

		parserContext.set(Parser.class, parser);

		ContentHandler contentHandler = new WriteOutContentHandler(
			new DummyWriter());

		try (InputStream inputStream = new FileInputStream(file)) {
			parser.parse(inputStream, contentHandler, metadata, parserContext);
		}
		catch (Exception e) {
			Throwable throwable = ExceptionUtils.getRootCause(e);

			if ((throwable instanceof CryptographyException) ||
				(throwable instanceof EncryptedDocumentException) ||
				(throwable instanceof UnsupportedZipFeatureException)) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to extract metadata from an encrypted file");
				}
			}
			else if (e instanceof TikaException) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to extract metadata");
				}
			}
			else {
				_log.error(e, e);
			}

			throw new IOException(e);
		}

		// Remove potential security risks

		metadata.remove(XMPDM.ABS_PEAK_AUDIO_FILE_PATH.getName());
		metadata.remove(XMPDM.RELATIVE_PEAK_AUDIO_FILE_PATH.getName());

		return metadata;
	}

	@Override
	protected Metadata extractMetadata(
		String extension, String mimeType, File file) {

		Metadata metadata = super.extractMetadata(extension, mimeType, file);

		boolean forkProcess = false;

		if (PropsValues.TEXT_EXTRACTION_FORK_PROCESS_ENABLED) {
			if (ArrayUtil.contains(
					PropsValues.TEXT_EXTRACTION_FORK_PROCESS_MIME_TYPES,
					mimeType)) {

				forkProcess = true;
			}
		}

		if (forkProcess) {
			ExtractMetadataProcessCallable extractMetadataProcessCallable =
				new ExtractMetadataProcessCallable(file, metadata, _parser);

			try {
				ProcessChannel<Metadata> processChannel =
					ProcessExecutorUtil.execute(
						ClassPathUtil.getPortalProcessConfig(),
						extractMetadataProcessCallable);

				Future<Metadata> future =
					processChannel.getProcessNoticeableFuture();

				return future.get();
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		try {
			return extractMetadata(file, metadata, _parser);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	protected Metadata extractMetadata(
		String extension, String mimeType, InputStream inputStream) {

		File file = FileUtil.createTempFile();

		try {
			FileUtil.write(file, inputStream);

			return extractMetadata(extension, mimeType, file);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			file.delete();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TikaRawMetadataProcessor.class);

	private Parser _parser;

	private static class ExtractMetadataProcessCallable
		implements ProcessCallable<Metadata> {

		public ExtractMetadataProcessCallable(
			File file, Metadata metadata, Parser parser) {

			_file = file;
			_metadata = metadata;
			_parser = parser;
		}

		@Override
		public Metadata call() throws ProcessException {
			try {
				return extractMetadata(_file, _metadata, _parser);
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}
		}

		private static final long serialVersionUID = 1L;

		@InputResource
		private final File _file;

		private final Metadata _metadata;
		private final Parser _parser;

	}

}