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

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;

/**
 * @author Hugo Huijser
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public abstract class BaseDBProcess implements DBProcess {

	public BaseDBProcess() {
	}

	public IndexMetadata addIndex(IndexMetadata indexMetadata) {
		List<IndexMetadata> indexMetadatas = new ArrayList<IndexMetadata>();

		indexMetadatas.add(indexMetadata);

		List<IndexMetadata> selIndexMetadatas = addIndexes(indexMetadatas);

		if (selIndexMetadatas.isEmpty()) {
			return null;
		}

		return selIndexMetadatas.get(0);
	}

	public List<IndexMetadata> addIndexes(List<IndexMetadata> indexMetadatas) {
		if (indexMetadatas.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> indexNames = new ArrayList<String>();

		for (Index index : getIndexes()) {
			String indexName = index.getIndexName();

			indexNames.add(indexName.toUpperCase());
		}

		List<IndexMetadata> selIndexMetadatas = new ArrayList<IndexMetadata>();

		for (IndexMetadata indexMetadata : indexMetadatas) {
			if (indexNames.contains(indexMetadata.getIndexName())) {
				continue;
			}

			if (_log.isInfoEnabled()) {
				_log.info(indexMetadata.getIndexSQLCreate());
			}

			try {
				runSQL(indexMetadata.getIndexSQLCreate());
			}
			catch (Exception e) {
				_log.error(e, e);

				continue;
			}

			selIndexMetadatas.add(indexMetadata);
		}

		return selIndexMetadatas;
	}

	public void dropIndex(IndexMetadata indexMetadata) {
		List<IndexMetadata> indexMetadatas = new ArrayList<IndexMetadata>();

		indexMetadatas.add(indexMetadata);

		dropIndexes(indexMetadatas);
	}

	public void dropIndexes(List<IndexMetadata> indexMetadatas) {
		for (IndexMetadata indexMetadata : indexMetadatas) {
			if (_log.isInfoEnabled()) {
				_log.info(indexMetadata.getIndexSQLDrop());
			}

			try {
				runSQL(indexMetadata.getIndexSQLDrop());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void runSQL(String template) throws IOException, SQLException {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(template);
	}

	public void runSQL(String[] templates) throws IOException, SQLException {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(templates);
	}

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		DB db = DBFactoryUtil.getDB();

		db.runSQLTemplate(path);
	}

	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException {

		DB db = DBFactoryUtil.getDB();

		db.runSQLTemplate(path, failOnError);
	}

	protected List<Index> getIndexes() {
		DB db = DBFactoryUtil.getDB();

		Connection con = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			return db.getIndexes(con);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			DataAccess.cleanUp(con);
		}

		return Collections.emptyList();
	}

	private static Log _log = LogFactoryUtil.getLog(BaseDBProcess.class);

}