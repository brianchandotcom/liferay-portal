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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.store.Directory;

/**
 * @author Shuyang Zhou
 */
public class IndexCommitMetaInfo implements Serializable {

	public IndexCommitMetaInfo(IndexCommit indexCommit) throws IOException {
		if (indexCommit == null) {
			_empty = true;
			_generation = 0;
			_segments = Collections.emptyList();

			return;
		}

		_empty = false;
		_generation = indexCommit.getGeneration();

		List<String> fileNames = new ArrayList<>(indexCommit.getFileNames());

		_segments = new ArrayList<>(fileNames.size());

		Directory directory = indexCommit.getDirectory();

		for (String fileName : fileNames) {
			Segment segment = new Segment(
				fileName, directory.fileLength(fileName));

			_segments.add(segment);
		}
	}

	public long getGeneration() {
		return _generation;
	}

	public List<Segment> getSegments() {
		return _segments;
	}

	public boolean isEmpty() {
		return _empty;
	}

	@Override
	public String toString() {
		if (_empty) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(_segments.size() * 5 + 3);

		sb.append("{fileInfos[");

		for (int i = 0; i < _segments.size(); i++) {
			Segment fileInfo = _segments.get(i);

			sb.append("fileName=");
			sb.append(fileInfo._fileName);
			sb.append(", fileSize=");
			sb.append(fileInfo._fileSize);

			if ((i + 1) < _segments.size()) {
				sb.append(", ");
			}
		}

		sb.append("], generation=");
		sb.append(_generation);
		sb.append("}");

		return sb.toString();
	}

	public class Segment implements Serializable {

		public Segment(String fileName, long fileSize) {
			_fileName = fileName;
			_fileSize = fileSize;
		}

		public String getFileName() {
			return _fileName;
		}

		public long getFileSize() {
			return _fileSize;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(5);

			sb.append("{_fileName=");
			sb.append(_fileName);
			sb.append(", _fileSize=");
			sb.append(_fileSize);
			sb.append("}");

			return sb.toString();
		}

		private final String _fileName;
		private final long _fileSize;

	}

	private final boolean _empty;
	private final long _generation;
	private final List<Segment> _segments;

}