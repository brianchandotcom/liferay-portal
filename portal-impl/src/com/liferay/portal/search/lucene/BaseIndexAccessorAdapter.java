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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.search.lucene.dump.DumpIndexDeletionPolicy;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LimitTokenCountAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;

/**
 * @author Michael C. Han
 */
public abstract class BaseIndexAccessorAdapter {

	protected abstract String doGetPath();

	protected FSDirectory getDirectory(String path) throws IOException {
		if (PropsValues.LUCENE_STORE_TYPE_FILE_FORCE_MMAP) {
			return new MMapDirectory(new File(path));
		}
		else {
			return FSDirectory.open(new File(path));
		}
	}

	protected Directory getIndexDirectory() {
		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(LUCENE_STORE_TYPE_FILE)) {
			return getLuceneDirFile();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(LUCENE_STORE_TYPE_JDBC)) {

			throw new IllegalArgumentException(
				"Store type JDBC is no longer supported in favor of SOLR");
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(LUCENE_STORE_TYPE_RAM)) {
			return getLuceneDirRam();
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	protected IndexWriterConfig getIndexWriterConfig() throws IOException {
		Analyzer analyzer = new LimitTokenCountAnalyzer(
			LuceneHelperUtil.getAnalyzer(),
			PropsValues.LUCENE_ANALYZER_MAX_TOKENS);

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
			LuceneHelperUtil.getVersion(), analyzer);

		indexWriterConfig.setIndexDeletionPolicy(dumpIndexDeletionPolicy);

		try {
			indexWriterConfig.setMergePolicy(getMergePolicy());
		}
		catch (Exception e) {
			throw new IOException("Unable to initialize merge policy", e);
		}

		indexWriterConfig.setRAMBufferSizeMB(PropsValues.LUCENE_BUFFER_SIZE);

		return indexWriterConfig;
	}

	protected Directory getLuceneDirFile() {
		Directory directory = null;

		String path = doGetPath();

		try {
			directory = getDirectory(path);
		}
		catch (IOException ioe) {
			if (directory != null) {
				try {
					directory.close();
				}
				catch (Exception e) {
				}
			}
		}

		return directory;
	}

	protected Directory getLuceneDirRam() {
		String path = doGetPath();

		Directory directory = _ramDirectories.get(path);

		if (directory == null) {
			directory = new RAMDirectory();

			_ramDirectories.put(path, directory);
		}

		return directory;
	}

	protected MergePolicy getMergePolicy() throws Exception {
		ClassLoader classLoader = PACLClassLoaderUtil.getPortalClassLoader();

		MergePolicy mergePolicy = (MergePolicy)InstanceFactory.newInstance(
			classLoader, PropsValues.LUCENE_MERGE_POLICY);

		if (mergePolicy instanceof LogMergePolicy) {
			LogMergePolicy logMergePolicy = (LogMergePolicy)mergePolicy;

			logMergePolicy.setMergeFactor(PropsValues.LUCENE_MERGE_FACTOR);
		}

		return mergePolicy;
	}

	protected static final String LUCENE_STORE_TYPE_FILE = "file";

	protected static final String LUCENE_STORE_TYPE_JDBC = "jdbc";

	protected static final String LUCENE_STORE_TYPE_RAM = "ram";

	protected DumpIndexDeletionPolicy dumpIndexDeletionPolicy =
		new DumpIndexDeletionPolicy();

	private static Log _log = LogFactoryUtil.getLog(
		BaseIndexAccessorAdapter.class);

	private Map<String, Directory> _ramDirectories =
		new ConcurrentHashMap<String, Directory>();

}