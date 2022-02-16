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

package com.liferay.portal.tika.internal.extract;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncBufferedInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.TextExtractor;
import com.liferay.portal.tika.internal.util.ProcessConfigUtil;
import com.liferay.portal.tika.internal.util.TikaConfigUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.extractor.EmbeddedDocumentExtractor;
import org.apache.tika.extractor.ParsingEmbeddedDocumentExtractor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.txt.UniversalEncodingDetector;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * @author Shuyang Zhou
 */
@Component(service = TextExtractor.class)
public class TextExtractorImpl implements TextExtractor {

	@Override
	public String extractText(InputStream inputStream, int maxStringLength) {
		if (maxStringLength == 0) {
			return StringPool.BLANK;
		}

		String text = null;

		try {
			Tika tika = new Tika(TikaConfigUtil.getTikaConfig());

			tika.setMaxStringLength(maxStringLength);

			boolean forkProcess = false;

			if (!inputStream.markSupported()) {
				inputStream = new UnsyncBufferedInputStream(inputStream);
			}

			if (PropsValues.TEXT_EXTRACTION_FORK_PROCESS_ENABLED) {
				String mimeType = tika.detect(inputStream);

				if (ArrayUtil.contains(
						PropsValues.TEXT_EXTRACTION_FORK_PROCESS_MIME_TYPES,
						mimeType)) {

					forkProcess = true;
				}
			}

			if (forkProcess) {
				InputStream finalInputStream = inputStream;

				ProcessChannel<String> processChannel =
					_processExecutor.execute(
						ProcessConfigUtil.getProcessConfig(),
						new ExtractTextProcessCallable(
							StreamUtil.toByteArray(finalInputStream)));

				Future<String> future =
					processChannel.getProcessNoticeableFuture();

				text = future.get();
			}
			else {
				text = _parseToString(tika, inputStream);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return text;
	}

	private static String _parseToString(Tika tika, InputStream inputStream)
		throws IOException, TikaException {

		inputStream.mark(1);

		try {
			if (inputStream.read() == -1) {
				return StringPool.BLANK;
			}
		}
		finally {
			inputStream.reset();
		}

		UniversalEncodingDetector universalEncodingDetector =
			new UniversalEncodingDetector();

		Metadata metadata = new Metadata();

		Charset charset = universalEncodingDetector.detect(
			inputStream, metadata);

		String contentEncoding = StringPool.BLANK;

		if (charset != null) {
			contentEncoding = charset.name();
		}

		if (!contentEncoding.equals(StringPool.BLANK)) {
			metadata.set("Content-Encoding", contentEncoding);
			metadata.set(
				"Content-Type", "text/plain; charset=" + contentEncoding);
		}

		WriteOutContentHandler writeOutContentHandler =
			new WriteOutContentHandler(tika.getMaxStringLength());

		try {
			Parser parser = tika.getParser();

			ParseContext parseContext = new ParseContext();

			parseContext.set(
				EmbeddedDocumentExtractor.class,
				new ParsingEmbeddedDocumentExtractor(parseContext) {

					@Override
					public void parseEmbedded(
							InputStream inputStream,
							ContentHandler contentHandler, Metadata metadata,
							boolean outputHtml)
						throws IOException, SAXException {

						String mimeType = tika.detect(inputStream);

						if (mimeType.equals(ContentTypes.IMAGE_PNG)) {
							return;
						}

						super.parseEmbedded(
							inputStream, contentHandler, metadata, outputHtml);
					}

				});
			parseContext.set(Parser.class, parser);

			parser.parse(
				inputStream, new BodyContentHandler(writeOutContentHandler),
				metadata, parseContext);
		}
		catch (SAXException saxException) {
			if (!writeOutContentHandler.isWriteLimitReached(saxException)) {
				throw new TikaException(
					saxException.getMessage(), saxException);
			}
		}
		finally {
			inputStream.close();
		}

		return writeOutContentHandler.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TextExtractorImpl.class);

	@Reference
	private ProcessExecutor _processExecutor;

	private static class ExtractTextProcessCallable
		implements ProcessCallable<String> {

		@Override
		public String call() throws ProcessException {
			if (ArrayUtil.isEmpty(_data)) {
				return StringPool.BLANK;
			}

			Logger logger = Logger.getLogger(
				"org.apache.tika.parser.SQLite3Parser");

			logger.setLevel(Level.SEVERE);

			logger = Logger.getLogger("org.apache.tika.parsers.PDFParser");

			logger.setLevel(Level.SEVERE);

			Tika tika = new Tika(TikaConfigUtil.getTikaConfig());

			try {
				return _parseToString(
					tika, new UnsyncByteArrayInputStream(_data));
			}
			catch (Exception exception) {
				throw new ProcessException(exception);
			}
		}

		private ExtractTextProcessCallable(byte[] data) {
			_data = data;
		}

		private static final long serialVersionUID = 1L;

		private final byte[] _data;

	}

}