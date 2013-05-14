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

package com.liferay.portal.search.lucene.dump;

import java.io.IOException;
import java.io.OutputStream;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;

/**
 * @author Shuyang Zhou
 */
public class DumpIndexDeletionPolicy implements IndexDeletionPolicy {

	public void dump(
			OutputStream outputStream, IndexWriter indexWriter, Lock commitLock)
		throws IOException {

		IndexCommit indexCommit = null;

		String segmentsFileName = null;

		commitLock.lock();

		try {
			indexWriter.commit();

			indexCommit = _lastIndexCommit;

			segmentsFileName = indexCommit.getSegmentsFileName();

			_segmentsFileNames.add(segmentsFileName);
		}
		finally {
			commitLock.unlock();
		}

		try {
			IndexCommitSerializationUtil.serializeIndex(
				indexCommit, outputStream);
		}
		finally {
			_segmentsFileNames.remove(segmentsFileName);
		}
	}

	public long getLastGeneration() {
		return _lastIndexCommit.getGeneration();
	}

	@Override
	public void onCommit(List<? extends IndexCommit> indexCommits) {
		_lastIndexCommit = indexCommits.get(indexCommits.size() - 1);

		for (int i = 0; i < indexCommits.size() - 1; i++) {
			IndexCommit indexCommit = indexCommits.get(i);

			if (!_segmentsFileNames.contains(
					indexCommit.getSegmentsFileName())) {

				indexCommit.delete();
			}
		}
	}

	@Override
	public void onInit(List<? extends IndexCommit> indexCommits) {
		onCommit(indexCommits);
	}

	private volatile IndexCommit _lastIndexCommit;
	private List<String> _segmentsFileNames =
		new CopyOnWriteArrayList<String>();

}