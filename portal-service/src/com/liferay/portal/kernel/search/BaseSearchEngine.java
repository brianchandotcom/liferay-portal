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

import com.liferay.portal.kernel.search.dummy.DummyIndexSearcher;
import com.liferay.portal.kernel.search.dummy.DummyIndexWriter;
import com.liferay.portal.kernel.search.generic.BooleanClauseFactoryImpl;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

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

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public BooleanClauseFactory getBooleanClauseFactory() {
		if (_booleanClauseFactory == null) {
			_booleanClauseFactory = new BooleanClauseFactoryImpl();
		}

		return _booleanClauseFactory;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public BooleanQueryFactory getBooleanQueryFactory() {
		if (_booleanQueryFactory == null) {
			_booleanQueryFactory =
				new com.liferay.portal.kernel.search.generic.
					BooleanQueryFactoryImpl();
		}

		return _booleanQueryFactory;
	}

	@Override
	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public TermQueryFactory getTermQueryFactory() {
		if (_termQueryFactory == null) {
			_termQueryFactory =
				new com.liferay.portal.kernel.search.generic.
					TermQueryFactoryImpl();
		}

		return _termQueryFactory;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public TermRangeQueryFactory getTermRangeQueryFactory() {
		if (_termRangeQueryFactory == null) {
			_termRangeQueryFactory =
				new com.liferay.portal.kernel.search.generic.
					TermRangeQueryFactoryImpl();
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

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public void setBooleanClauseFactory(
		BooleanClauseFactory booleanClauseFactory) {

		_booleanClauseFactory = booleanClauseFactory;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public void setBooleanQueryFactory(
		BooleanQueryFactory booleanQueryFactory) {

		_booleanQueryFactory = booleanQueryFactory;
	}

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		_indexSearcher = indexSearcher;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		_indexWriter = indexWriter;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public void setTermQueryFactory(TermQueryFactory termQueryFactory) {
		_termQueryFactory = termQueryFactory;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public void setTermRangeQueryFactory(
		TermRangeQueryFactory termRangeQueryFactory) {

		_termRangeQueryFactory = termRangeQueryFactory;
	}

	public void setVendor(String vendor) {
		_vendor = vendor;
	}

	private BooleanClauseFactory _booleanClauseFactory;

	@SuppressWarnings("deprecation")
	private BooleanQueryFactory _booleanQueryFactory;

	private IndexSearcher _indexSearcher = new DummyIndexSearcher();
	private IndexWriter _indexWriter = new DummyIndexWriter();

	@SuppressWarnings("deprecation")
	private TermQueryFactory _termQueryFactory;

	@SuppressWarnings("deprecation")
	private TermRangeQueryFactory _termRangeQueryFactory;

	private String _vendor;

}