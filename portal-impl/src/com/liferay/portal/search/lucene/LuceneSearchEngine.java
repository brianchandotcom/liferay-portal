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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.BaseSearchEngine;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author Michael C. Han
 */
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

	public void setLuceneHelper(LuceneHelper luceneHelper) {
		_luceneHelper = luceneHelper;
	}

	protected String getFileName(String backupName) {
		return SystemProperties.get(SystemProperties.TMP_DIR) +
			File.separator + backupName;
	}

	private LuceneHelper _luceneHelper;

}