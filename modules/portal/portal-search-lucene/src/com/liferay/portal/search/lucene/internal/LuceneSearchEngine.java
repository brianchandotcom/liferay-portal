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

package com.liferay.portal.search.lucene.internal;

import com.liferay.portal.kernel.search.BaseSearchEngine;
import com.liferay.portal.kernel.search.BooleanQueryFactory;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.TermQueryFactory;
import com.liferay.portal.kernel.search.TermRangeQueryFactory;
import com.liferay.portal.kernel.search.generic.BooleanClauseFactoryImpl;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.search.lucene.internal.query.BooleanQueryFactoryImpl;
import com.liferay.portal.search.lucene.internal.query.TermQueryFactoryImpl;
import com.liferay.portal.search.lucene.internal.query.TermRangeQueryFactoryImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"search.engine.id=SYSTEM_ENGINE", "vendor=Lucene"},
	service = {LuceneSearchEngine.class, SearchEngine.class}
)
public class LuceneSearchEngine extends BaseSearchEngine {

	@Override
	public synchronized String backup(long companyId, String backupName) {
		FileOutputStream fileOutputStream = null;

		try {
			String fileName = getFileName(backupName);

			fileOutputStream = new FileOutputStream(fileName);

			_luceneHelper.dumpIndex(companyId, fileOutputStream);

			return fileName;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			StreamUtil.cleanUp(fileOutputStream);
		}
	}

	@Override
	public void initialize(long companyId) {
		super.initialize(companyId);

		_luceneHelper.startup(companyId);
	}

	@Override
	public synchronized void removeBackup(long companyId, String backupName) {
		String fileName = getFileName(backupName);

		FileUtil.delete(fileName);
	}

	@Override
	public void removeCompany(long companyId) {
		super.removeCompany(companyId);

		_luceneHelper.delete(companyId);

		_luceneHelper.shutdown(companyId);
	}

	@Override
	public synchronized void restore(long companyId, String backupName) {
		FileInputStream fileInputStream = null;

		try {
			String fileName = getFileName(backupName);

			fileInputStream = new FileInputStream(fileName);

			_luceneHelper.loadIndex(companyId, fileInputStream);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			StreamUtil.cleanUp(fileInputStream);
		}
	}

	@Override
	@Reference(service = BooleanQueryFactoryImpl.class)
	public void setBooleanQueryFactory(
		BooleanQueryFactory booleanQueryFactory) {

		super.setBooleanQueryFactory(booleanQueryFactory);
	}

	@Override
	@Reference(service = LuceneIndexSearcher.class)
	public void setIndexSearcher(IndexSearcher indexSearcher) {
		super.setIndexSearcher(indexSearcher);
	}

	@Override
	@Reference(service = LuceneIndexWriter.class)
	public void setIndexWriter(IndexWriter indexWriter) {
		super.setIndexWriter(indexWriter);
	}

	@Override
	@Reference(service = TermQueryFactoryImpl.class)
	public void setTermQueryFactory(TermQueryFactory termQueryFactory) {
		super.setTermQueryFactory(termQueryFactory);
	}

	@Override
	@Reference(service = TermRangeQueryFactoryImpl.class)
	public void setTermRangeQueryFactory(
		TermRangeQueryFactory termRangeQueryFactory) {

		super.setTermRangeQueryFactory(termRangeQueryFactory);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		setBooleanClauseFactory(new BooleanClauseFactoryImpl());

		setVendor(GetterUtil.getString(properties.get("vendor"), "Lucene"));
	}

	protected String getFileName(String backupName) {
		return SystemProperties.get(SystemProperties.TMP_DIR) +
			File.separator + backupName;
	}

	@Reference
	protected void setLuceneHelper(LuceneHelper luceneHelper) {
		_luceneHelper = luceneHelper;
	}

	private LuceneHelper _luceneHelper;

}