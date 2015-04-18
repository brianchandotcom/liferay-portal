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

package com.liferay.portal.search;

import com.liferay.portal.search.lucene.internal.configuration.LuceneConfiguration;

/**
 * @author Michael C. Han
 */
public class MockLuceneConfiguration implements LuceneConfiguration {

	@Override
	public int analyzerMaxTokens() {
		return 1000;
	}

	@Override
	public int bufferSize() {
		return 16;
	}

	@Override
	public int commitBatchSize() {
		return 0;
	}

	@Override
	public long commitTimeInterval() {
		return 0;
	}

	@Override
	public String dir() {
		return System.getProperty("java.io.tmpdir");
	}

	@Override
	public boolean indexDumpCompressionEnabled() {
		return true;
	}

	@Override
	public int mergeFactor() {
		return 10;
	}

	@Override
	public String mergePolicy() {
		return "org.apache.lucene.index.LogDocMergePolicy";
	}

	@Override
	public String mergeScheduler() {
		return "org.apache.lucene.index.ConcurrentMergeScheduler";
	}

	@Override
	public String storeType() {
		return "file";
	}

	@Override
	public boolean storeTypeFileForceMMap() {
		return false;
	}

}