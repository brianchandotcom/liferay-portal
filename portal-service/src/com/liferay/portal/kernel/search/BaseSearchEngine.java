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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * @author Bruno Farache
 * @author Carlos Sierra Andrés
 * @author Marcellus Tavares
 */
@DoPrivileged
public class BaseSearchEngine implements SearchEngine {

	/**
	 * @throws SearchException
	 */
	@Override
	public String backup(long companyId, String backupName)
		throws SearchException {

		return null;
	}

	@Override
	public BooleanClauseFactory getBooleanClauseFactory() {
		if (_booleanClauseFactory == null) {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			String className =
				"com.liferay.portal.kernel.search.generic." +
					"BooleanClauseFactoryImpl";

			try {
				_booleanClauseFactory =
					(BooleanClauseFactory)InstanceFactory.newInstance(
						classLoader, className);
			}
			catch (Exception e) {
				_log.fatal(
					"Unable to locate appropriate BooleanClauseFactory", e);
			}
		}

		return _booleanClauseFactory;
	}

	@Override
	public BooleanQueryFactory getBooleanQueryFactory() {
		if (_booleanQueryFactory != null) {
			return _booleanQueryFactory;
		}

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		String className =
			"com.liferay.portal.search.lucene.BooleanQueryFactoryImpl";

		if (!isLuceneBased()) {
			className =
				"com.liferay.portal.kernel.search.generic." +
					"BooleanQueryFactoryImpl";
		}

		try {
			_booleanQueryFactory =
				(BooleanQueryFactory)InstanceFactory.newInstance(
					classLoader, className);
		}
		catch (Exception e) {
			_log.fatal("Unable to locate appropriate BooleanQueryFactory", e);
		}

		return _booleanQueryFactory;
	}

	@Override
	public Priority getClusteredWritePriority() {
		return _clusteredWritePriority;
	}

	@Override
	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	@Override
	public TermQueryFactory getTermQueryFactory() {
		if (_termQueryFactory != null) {
			return _termQueryFactory;
		}

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		String className =
			"com.liferay.portal.search.lucene.TermQueryFactoryImpl";

		if (!isLuceneBased()) {
			className =
				"com.liferay.portal.kernel.search.generic.TermQueryFactoryImpl";
		}

		try {
			_termQueryFactory = (TermQueryFactory)InstanceFactory.newInstance(
				classLoader, className);
		}
		catch (Exception e) {
			_log.fatal("Unable to locate appropriate BooleanQueryFactory", e);
		}

		return _termQueryFactory;
	}

	@Override
	public TermRangeQueryFactory getTermRangeQueryFactory() {
		if (_termRangeQueryFactory != null) {
			return _termRangeQueryFactory;
		}

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		String className =
			"com.liferay.portal.search.lucene.TermRangeQueryFactoryImpl";

		if (!isLuceneBased()) {
			className =
				"com.liferay.portal.kernel.search.generic." +
					"TermRangeQueryFactoryImpl";
		}

		try {
			_termRangeQueryFactory =
				(TermRangeQueryFactory)InstanceFactory.newInstance(
					classLoader, className);
		}
		catch (Exception e) {
			_log.fatal("Unable to locate appropriate BooleanQueryFactory", e);
		}

		return _termRangeQueryFactory;
	}

	@Override
	public String getVendor() {
		return _vendor;
	}

	@Override
	public void initialize(long companyId) {
	}

	@Override
	public boolean isClusteredWrite() {
		return _clusteredWrite;
	}

	@Override
	public boolean isLuceneBased() {
		return _luceneBased;
	}

	/**
	 * @throws SearchException
	 */
	@Override
	public void removeBackup(long companyId, String backupName)
		throws SearchException {
	}

	@Override
	public void removeCompany(long companyId) {
	}

	/**
	 * @throws SearchException
	 */
	@Override
	public void restore(long companyId, String backupName)
		throws SearchException {
	}

	public void setBooleanClauseFactory(
		BooleanClauseFactory booleanClauseFactory) {

		_booleanClauseFactory = booleanClauseFactory;
	}

	public void setBooleanQueryFactory(
		BooleanQueryFactory booleanQueryFactory) {

		_booleanQueryFactory = booleanQueryFactory;
	}

	public void setClusteredWrite(boolean clusteredWrite) {
		_clusteredWrite = clusteredWrite;
	}

	public void setClusteredWritePriority(Priority clusteredWritePriority) {
		_clusteredWritePriority = clusteredWritePriority;
	}

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		_indexSearcher = indexSearcher;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		_indexWriter = indexWriter;
	}

	public void setLuceneBased(boolean luceneBased) {
		_luceneBased = luceneBased;
	}

	public void setTermQueryFactory(TermQueryFactory termQueryFactory) {
		_termQueryFactory = termQueryFactory;
	}

	public void setTermRangeQueryFactory(
		TermRangeQueryFactory termRangeQueryFactory) {

		_termRangeQueryFactory = termRangeQueryFactory;
	}

	public void setVendor(String vendor) {
		_vendor = vendor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSearchEngine.class);

	private BooleanClauseFactory _booleanClauseFactory;
	private BooleanQueryFactory _booleanQueryFactory;
	private boolean _clusteredWrite;
	private Priority _clusteredWritePriority;
	private IndexSearcher _indexSearcher = new DummyIndexSearcher();
	private IndexWriter _indexWriter = new DummyIndexWriter();
	private boolean _luceneBased;
	private TermQueryFactory _termQueryFactory;
	private TermRangeQueryFactory _termRangeQueryFactory;
	private String _vendor;

}