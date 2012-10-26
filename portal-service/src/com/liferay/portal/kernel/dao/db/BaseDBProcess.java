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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;

/**
 * @author Hugo Huijser
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public abstract class BaseDBProcess implements DBProcess {

	public BaseDBProcess() {
	}

	public void addPermanentIndex(
			boolean unique, String tableName, String... columnNames)
		throws IOException, SQLException {

		IndexMetadata indexMetadata =
			IndexMetadataFactoryUtil.createIndexMetadata(
				unique, tableName, columnNames);

		Set<String> existingIndexNames = new HashSet<String>();

		for (Index index : getIndexes()) {
			String indexName = index.getIndexName();

			existingIndexNames.add(indexName.toUpperCase());
		}

		if (existingIndexNames.contains(indexMetadata.getIndexName())) {
			return;
		}

		runSQL(indexMetadata.getCreateSQL());
	}

	public void addTemporaryIndexes() throws IOException, SQLException {
		if (_requestedTemporaryIndexMetadatas.isEmpty()) {
			return;
		}

		Set<String> existingIndexNames = new HashSet<String>();

		for (Index index : getIndexes()) {
			String indexName = index.getIndexName();

			existingIndexNames.add(indexName.toUpperCase());
		}

		for (IndexMetadata indexMetadata : _requestedTemporaryIndexMetadatas) {
			if (existingIndexNames.contains(indexMetadata.getIndexName())) {
				continue;
			}

			runSQL(indexMetadata.getCreateSQL());

			_utilizedTemporaryIndexMetadatas.add(indexMetadata);
		}
	}

	public void dropTemporaryIndexes() throws IOException, SQLException {
		for (IndexMetadata indexMetadata : _utilizedTemporaryIndexMetadatas) {
			runSQL(indexMetadata.getDropSQL());
		}

		_utilizedTemporaryIndexMetadatas.clear();
	}

	public void requestTemporaryIndex(
		boolean unique, String tableName, String... columnNames) {

		if (!_INDEX_ON_UPGRADE) {
			return;
		}

		IndexMetadata indexMetadata =
			IndexMetadataFactoryUtil.createIndexMetadata(
				unique, tableName, columnNames);

		_requestedTemporaryIndexMetadatas.add(indexMetadata);
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

	protected List<Index> getIndexes() throws SQLException {
		DB db = DBFactoryUtil.getDB();

		Connection con = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			return db.getIndexes(con);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	private static final boolean _INDEX_ON_UPGRADE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_ON_UPGRADE));

	private List<IndexMetadata> _requestedTemporaryIndexMetadatas =
		new ArrayList<IndexMetadata>();
	private List<IndexMetadata> _utilizedTemporaryIndexMetadatas =
		new ArrayList<IndexMetadata>();

}