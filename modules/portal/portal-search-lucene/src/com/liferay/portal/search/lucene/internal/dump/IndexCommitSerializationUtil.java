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

package com.liferay.portal.search.lucene.internal.dump;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.lucene.internal.configuration.LuceneConfiguration;
import com.liferay.portal.search.lucene.internal.dump.IndexCommitMetaInfo.Segment;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.index.SegmentInfos;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.IndexOutput;

/**
 * @author Shuyang Zhou
 */
public class IndexCommitSerializationUtil {

	public static LuceneConfiguration luceneConfiguration;

	public static void deserializeIndex(
			InputStream inputStream, Directory directory)
		throws IOException {

		if (luceneConfiguration.indexDumpCompressionEnabled()) {
			inputStream = new GZIPInputStream(inputStream);
		}

		try (ObjectInputStream objectInputStream = new ObjectInputStream(
				inputStream)) {

			IndexCommitMetaInfo indexCommitMetaInfo = null;

			try {
				indexCommitMetaInfo =
					(IndexCommitMetaInfo)objectInputStream.readObject();
			}
			catch (ClassNotFoundException cnfe) {
				throw new IOException(cnfe);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Deserializing " + indexCommitMetaInfo);
			}

			if (indexCommitMetaInfo.isEmpty()) {
				return;
			}

			List<Segment> segments = indexCommitMetaInfo.getSegments();

			for (Segment segment : segments) {
				if (_log.isDebugEnabled()) {
					_log.debug("Deserializing segment " + segment);
				}

				_deserializeSegment(
					objectInputStream, segment.getFileSize(),
					directory.createOutput(segment.getFileName()));
			}

			_writeSegmentsGen(directory, indexCommitMetaInfo.getGeneration());
		}
	}

	public static void serializeIndex(
			IndexCommit indexCommit, OutputStream outputStream)
		throws IOException {

		if (luceneConfiguration.indexDumpCompressionEnabled()) {
			outputStream = new GZIPOutputStream(outputStream);
		}

		ObjectOutputStream objectOputStream = new ObjectOutputStream(
			outputStream);

		IndexCommitMetaInfo indexCommitMetaInfo = new IndexCommitMetaInfo(
			indexCommit);

		if (_log.isDebugEnabled()) {
			_log.debug("Serializing " + indexCommitMetaInfo);
		}

		objectOputStream.writeObject(indexCommitMetaInfo);

		List<Segment> segments = indexCommitMetaInfo.getSegments();

		Directory directory = indexCommit.getDirectory();

		for (Segment segment : segments) {
			if (_log.isDebugEnabled()) {
				_log.debug("Serializing segment " + segment);
			}

			_serializeSegment(
				directory.openInput(segment.getFileName()),
				segment.getFileSize(), objectOputStream);
		}

		objectOputStream.flush();

		if (luceneConfiguration.indexDumpCompressionEnabled()) {
			GZIPOutputStream gZipOutputStream = (GZIPOutputStream)outputStream;

			gZipOutputStream.finish();
		}
	}

	private static void _deserializeSegment(
			InputStream inputStream, long length, IndexOutput indexOutput)
		throws IOException {

		try {
			indexOutput.setLength(length);

			byte[] buffer = new byte[_BUFFER_SIZE];

			long received = 0;

			while (received < length) {
				int bufferSize = _BUFFER_SIZE;

				if ((received + _BUFFER_SIZE) > length) {
					bufferSize = (int)(length - received);
				}

				int actualSize = inputStream.read(buffer, 0, bufferSize);

				indexOutput.writeBytes(buffer, actualSize);

				received += actualSize;
			}
		}
		finally {
			indexOutput.close();
		}
	}

	private static void _serializeSegment(
			IndexInput indexInput, long length, OutputStream outputStream)
		throws IOException {

		byte[] buffer = new byte[_BUFFER_SIZE];

		int count = (int)(length / _BUFFER_SIZE);
		int tail = (int)(length - count * _BUFFER_SIZE);

		try {
			for (int i = 0; i < count; i++) {
				indexInput.readBytes(buffer, 0, _BUFFER_SIZE);
				outputStream.write(buffer);
			}

			indexInput.readBytes(buffer, 0, tail);
			outputStream.write(buffer, 0, tail);
		}
		finally {
			indexInput.close();
		}
	}

	private static void _writeSegmentsGen(Directory directory, long generation)
		throws IOException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Writing " + _SEGMENTS_GEN_FILE_NAME + " with generation " +
					generation);
		}

		try (IndexOutput indexOutput = directory.createOutput(
				_SEGMENTS_GEN_FILE_NAME)) {

			indexOutput.writeInt(SegmentInfos.FORMAT_LOCKLESS);
			indexOutput.writeLong(generation);
			indexOutput.writeLong(generation);
		}
	}

	private static final int _BUFFER_SIZE = 8192;

	private static final String _SEGMENTS_GEN_FILE_NAME = "segments.gen";

	private static final Log _log = LogFactoryUtil.getLog(
		IndexCommitSerializationUtil.class);

}