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

package com.liferay.sync.engine.service.persistence;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import com.liferay.sync.engine.model.SyncFile;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;

/**
 * @author Shinn Lok
 */
public class SyncFilePersistence extends BasePersistenceImpl<SyncFile, Long> {

	public SyncFilePersistence() throws SQLException {
		super(SyncFile.class);
	}

	public long countByUIEvent(int uiEvent) throws SQLException {
		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("uiEvent", uiEvent);

		return where.countOf();
	}

	public long countByS_T_U(long syncAccountId, String type, int uiEvent)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("syncAccountId", syncAccountId);
		where.eq("type", type);
		where.eq("uiEvent", uiEvent);

		where.and(3);

		return where.countOf();
	}

	public SyncFile fetchByFilePathName(String filePathName)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		queryBuilder.limit(1L);

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("filePathName", new SelectArg(filePathName));

		return where.queryForFirst();
	}

	public SyncFile fetchByC_S(String checksum, int state) throws SQLException {
		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		queryBuilder.limit(1L);

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("checksum", checksum);
		where.eq("state", state);

		where.and(2);

		return where.queryForFirst();
	}

	public SyncFile fetchByR_S_S_First(
			long repositoryId, int state, long syncAccountId)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		queryBuilder.limit(1L);

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("repositoryId", repositoryId);
		where.eq("state", state);
		where.eq("syncAccountId", syncAccountId);
		where.ne("uiEvent", SyncFile.UI_EVENT_DELETED_LOCAL);
		where.ne("uiEvent", SyncFile.UI_EVENT_DELETED_REMOTE);
		where.ne("uiEvent", SyncFile.UI_EVENT_TRASHED_LOCAL);
		where.ne("uiEvent", SyncFile.UI_EVENT_TRASHED_REMOTE);

		where.and(7);

		return where.queryForFirst();
	}

	public SyncFile fetchByPF_S_First(String parentFilePathName, int state)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		queryBuilder.limit(1L);

		Where<SyncFile, Long> where = queryBuilder.where();

		FileSystem fileSystem = FileSystems.getDefault();

		parentFilePathName = StringUtils.replace(
			parentFilePathName + fileSystem.getSeparator(), "\\", "\\\\");

		where.like("filePathName", new SelectArg(parentFilePathName + "%"));
		where.eq("state", state);
		where.ne("uiEvent", SyncFile.UI_EVENT_DELETED_LOCAL);
		where.ne("uiEvent", SyncFile.UI_EVENT_DELETED_REMOTE);
		where.ne("uiEvent", SyncFile.UI_EVENT_TRASHED_LOCAL);
		where.ne("uiEvent", SyncFile.UI_EVENT_TRASHED_REMOTE);

		where.and(6);

		return where.queryForFirst();
	}

	public SyncFile fetchByS_U_First(long syncAccountId, int uiEvent)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		queryBuilder.limit(1L);

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("syncAccountId", syncAccountId);
		where.eq("uiEvent", uiEvent);

		where.and(2);

		return where.queryForFirst();
	}

	public SyncFile fetchByR_S_T(
			long repositoryId, long syncAccountId, long typePK)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		queryBuilder.limit(1L);

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("repositoryId", repositoryId);
		where.eq("syncAccountId", syncAccountId);
		where.eq("typePK", typePK);

		where.and(3);

		return where.queryForFirst();
	}

	public List<SyncFile> findByParentFilePathName(String parentFilePathName)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		Where<SyncFile, Long> where = queryBuilder.where();

		FileSystem fileSystem = FileSystems.getDefault();

		parentFilePathName = StringUtils.replace(
			parentFilePathName + fileSystem.getSeparator(), "\\", "\\\\");

		where.like("filePathName", new SelectArg(parentFilePathName + "%"));

		return where.query();
	}

	public List<SyncFile> findBySyncAccountId(long syncAccountId)
		throws SQLException {

		return queryForEq("syncAccountId", syncAccountId);
	}

	public List<SyncFile> findByPF_L(
			String parentFilePathName, long localSyncTime)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		Where<SyncFile, Long> where = queryBuilder.where();

		FileSystem fileSystem = FileSystems.getDefault();

		parentFilePathName = StringUtils.replace(
			parentFilePathName + fileSystem.getSeparator(), "\\", "\\\\");

		where.like("filePathName", new SelectArg(parentFilePathName + "%"));
		where.lt("localSyncTime", localSyncTime);
		where.or(
			where.eq("state", SyncFile.STATE_SYNCED),
			where.eq("uiEvent", SyncFile.UI_EVENT_UPLOADING));
		where.ne("type", SyncFile.TYPE_SYSTEM);

		where.and(4);

		return where.query();
	}

	public List<SyncFile> findByP_S(long parentFolderId, long syncAccountId)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("parentFolderId", parentFolderId);
		fieldValues.put("syncAccountId", syncAccountId);

		return queryForFieldValues(fieldValues);
	}

	public List<SyncFile> findByS_S(int state, long syncAccountId)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("state", state);
		fieldValues.put("syncAccountId", syncAccountId);

		return queryForFieldValues(fieldValues);
	}

	public List<SyncFile> findByS_U(
			long syncAccountId, int uiEvent, String orderByColumn,
			boolean ascending)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		queryBuilder.orderBy(orderByColumn, ascending);

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("syncAccountId", syncAccountId);
		where.eq("uiEvent", uiEvent);

		where.and(2);

		return where.query();
	}

	public List<SyncFile> findByR_S_S(
			long repositoryId, int state, long syncAccountId)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("repositoryId", repositoryId);
		fieldValues.put("state", state);
		fieldValues.put("syncAccountId", syncAccountId);

		return queryForFieldValues(fieldValues);
	}

	public void renameByParentFilePathName(
			final String sourceParentFilePathName,
			final String targetParentFilePathName)
		throws SQLException {

		Callable<Object> callable = new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				FileSystem fileSystem = FileSystems.getDefault();

				List<SyncFile> syncFiles = findByParentFilePathName(
					sourceParentFilePathName);

				for (SyncFile syncFile : syncFiles) {
					String filePathName = syncFile.getFilePathName();

					filePathName = StringUtils.replaceOnce(
						filePathName,
						sourceParentFilePathName + fileSystem.getSeparator(),
						targetParentFilePathName + fileSystem.getSeparator());

					syncFile.setFilePathName(filePathName);

					update(syncFile);
				}

				return null;
			}

		};

		callBatchTasks(callable);
	}

	public List<SyncFile> findByR_S_T(
			long repositoryId, long syncAccountId, String type)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("repositoryId", repositoryId);
		fieldValues.put("syncAccountId", syncAccountId);
		fieldValues.put("type", type);

		return queryForFieldValues(fieldValues);
	}

	public void updateByParentFilePathName(
			String parentFilePathName, int state, int uiEvent)
		throws SQLException {

		UpdateBuilder<SyncFile, Long> updateBuilder = updateBuilder();

		updateBuilder.updateColumnValue("state", state);
		updateBuilder.updateColumnValue("uiEvent", uiEvent);

		Where<SyncFile, Long> where = updateBuilder.where();

		FileSystem fileSystem = FileSystems.getDefault();

		parentFilePathName = StringUtils.replace(
			parentFilePathName + fileSystem.getSeparator(), "\\", "\\\\");

		where.like("filePathName", new SelectArg(parentFilePathName + "%"));

		updateBuilder.update();
	}

}