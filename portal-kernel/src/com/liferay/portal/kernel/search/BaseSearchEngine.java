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
import com.liferay.portal.kernel.search.generic.BooleanQueryFactoryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryFactoryImpl;

/**
 * @author Bruno Farache
 * @author Carlos Sierra Andrés
 * @author Marcellus Tavares
 */
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
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public BooleanQueryFactory getBooleanQueryFactory() {
		if (_booleanQueryFactory == null) {
			_booleanQueryFactory = new BooleanQueryFactoryImpl();
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
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public TermQueryFactory getTermQueryFactory() {
		if (_termQueryFactory == null) {
			_termQueryFactory = new TermQueryFactoryImpl();
		}

		return _termQueryFactory;
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

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		_indexSearcher = indexSearcher;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		_indexWriter = indexWriter;
	}

	public void setVendor(String vendor) {
		_vendor = vendor;
	}

	@SuppressWarnings("deprecation")
	private BooleanQueryFactory _booleanQueryFactory;

	private IndexSearcher _indexSearcher = new DummyIndexSearcher();
	private IndexWriter _indexWriter = new DummyIndexWriter();

	@SuppressWarnings("deprecation")
	private TermQueryFactory _termQueryFactory;

	private String _vendor;

}