/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class DLFolderFinderImpl
	extends BasePersistenceImpl<DLFolder> implements DLFolderFinder {

	public static final String COUNT_F_BY_G_M_F =
		DLFolderFinder.class.getName() + ".countF_ByG_M_F";

	public static final String COUNT_FE_BY_G_F =
		DLFolderFinder.class.getName() + ".countFE_ByG_F";

	public static final String COUNT_FS_BY_G_F_A =
		DLFolderFinder.class.getName() + ".countFS_ByG_F_A";

	public static final String FIND_F_BY_NO_ASSETS =
		DLFolderFinder.class.getName() + ".findF_ByNoAssets";

	public static final String FIND_F_BY_G_M_F =
		DLFolderFinder.class.getName() + ".findF_ByG_M_F";

	public static final String FIND_FE_BY_G_F =
		DLFolderFinder.class.getName() + ".findFE_ByG_F";

	public static final String FIND_FS_BY_G_F_A =
		DLFolderFinder.class.getName() + ".findFS_ByG_F_A";

	public static final String JOIN_FE_BY_DL_FILE_VERSION =
		DLFolderFinder.class.getName() + ".joinFE_ByDLFileVersion";

	public static final String JOIN_FS_BY_DL_FILE_ENTRY =
		DLFolderFinder.class.getName() + ".joinFS_ByDLFileEntry";

	@Override
	public int countF_FE_FS_ByG_F_M_M(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition queryDefinition)
		throws SystemException {

		return doCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition,
			false);
	}

	@Override
	public int countFE_ByG_F(
			long groupId, long folderId, QueryDefinition queryDefinition)
		throws SystemException {

		return doCountFE_ByG_F(groupId, folderId, queryDefinition, false);
	}

	@Override
	public int countFE_FS_ByG_F(
			long groupId, long folderId, QueryDefinition queryDefinition)
		throws SystemException {

		return doCountFE_FS_ByG_F_M(
			groupId, folderId, null, queryDefinition, false);
	}

	@Override
	public int filterCountF_FE_FS_ByG_F_M_M(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition queryDefinition)
		throws SystemException {

		return doCountF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition,
			true);
	}

	@Override
	public int filterCountFE_ByG_F(
			long groupId, long folderId, QueryDefinition queryDefinition)
		throws SystemException {

		return doCountFE_ByG_F(groupId, folderId, queryDefinition, true);
	}

	@Override
	public int filterCountFE_FS_ByG_F(
			long groupId, long folderId, QueryDefinition queryDefinition)
		throws SystemException {

		return doCountFE_FS_ByG_F_M(
			groupId, folderId, null, queryDefinition, true);
	}

	@Override
	public int filterCountFE_FS_ByG_F_M(
			long groupId, long folderId, String[] mimeTypes,
			QueryDefinition queryDefinition)
		throws SystemException {

		return doCountFE_FS_ByG_F_M(
			groupId, folderId, mimeTypes, queryDefinition, true);
	}

	@Override
	public List<Object> filterFindF_FE_FS_ByG_F_M_M(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition queryDefinition)
		throws SystemException {

		return doFindF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition,
			true);
	}

	@Override
	public List<Object> filterFindFE_FS_ByG_F(
			long groupId, long folderId, QueryDefinition queryDefinition)
		throws SystemException {

		return doFindFE_FS_ByG_F(groupId, folderId, queryDefinition, true);
	}

	@Override
	public List<DLFolder> findF_ByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_F_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("DLFolder", DLFolderImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Object> findF_FE_FS_ByG_F_M_M(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition queryDefinition)
		throws SystemException {

		return doFindF_FE_FS_ByG_F_M_M(
			groupId, folderId, mimeTypes, includeMountFolders, queryDefinition,
			false);
	}

	@Override
	public List<Object> findFE_FS_ByG_F(
			long groupId, long folderId, QueryDefinition queryDefinition)
		throws SystemException {

		return doFindFE_FS_ByG_F(groupId, folderId, queryDefinition, false);
	}

	protected int doCountF_FE_FS_ByG_F_M_M(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition queryDefinition,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(7);

			sb.append(StringPool.OPEN_PARENTHESIS);

			String sql = CustomSQLUtil.get(
				COUNT_F_BY_G_M_F, queryDefinition, DLFolderImpl.TABLE_NAME);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFolder.class.getName(), "DLFolder.folderId",
					groupId);
			}

			sb.append(sql);
			sb.append(") UNION ALL (");
			sb.append(
				getFileVersionsSQL(
					COUNT_FE_BY_G_F, groupId, mimeTypes, queryDefinition,
					inlineSQLHelper));
			sb.append(") UNION ALL (");
			sb.append(
				getFileShortcutsSQL(
					COUNT_FS_BY_G_F_A, groupId, mimeTypes, queryDefinition,
					inlineSQLHelper));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();

			sql = updateSQL(sql, folderId, includeMountFolders);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (!includeMountFolders) {
				qPos.add(false);
			}

			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);
			qPos.add(groupId);
			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);

			if (mimeTypes != null) {
				qPos.add(mimeTypes);
			}

			qPos.add(groupId);
			qPos.add(true);
			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);

			if (mimeTypes != null) {
				qPos.add(mimeTypes);
			}

			int count = 0;

			Iterator<Long> itr = q.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountFE_ByG_F(
			long groupId, long folderId, QueryDefinition queryDefinition,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = getFileVersionsSQL(
				COUNT_FE_BY_G_F, groupId, null, queryDefinition,
				inlineSQLHelper);

			sql = updateSQL(sql, folderId, false);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountFE_FS_ByG_F_M(
			long groupId, long folderId, String[] mimeTypes,
			QueryDefinition queryDefinition, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(7);

			sb.append(StringPool.OPEN_PARENTHESIS);

			String sql = getFileVersionsSQL(
				COUNT_FE_BY_G_F, groupId, mimeTypes, queryDefinition,
				inlineSQLHelper);

			sb.append(sql);
			sb.append(") UNION ALL (");
			sb.append(
				getFileShortcutsSQL(
					COUNT_FS_BY_G_F_A, groupId, mimeTypes, queryDefinition,
					inlineSQLHelper));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();

			sql = updateSQL(sql, folderId, false);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);

			if (mimeTypes != null) {
				qPos.add(mimeTypes);
			}

			qPos.add(groupId);
			qPos.add(true);
			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);

			if (mimeTypes != null) {
				qPos.add(mimeTypes);
			}

			int count = 0;

			Iterator<Long> itr = q.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<Object> doFindF_FE_FS_ByG_F_M_M(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition queryDefinition,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(7);

			sb.append("SELECT * FROM (");

			String sql = CustomSQLUtil.get(
				FIND_F_BY_G_M_F, queryDefinition, DLFolderImpl.TABLE_NAME);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFolder.class.getName(), "DLFolder.folderId",
					groupId);
			}

			sb.append(sql);
			sb.append(" UNION ALL ");

			sql = getFileEntriesSQL(
				FIND_FE_BY_G_F, groupId, mimeTypes, queryDefinition,
				inlineSQLHelper);

			sb.append(sql);
			sb.append(" UNION ALL ");

			sql = getFileShortcutsSQL(
				FIND_FS_BY_G_F_A, groupId, mimeTypes, queryDefinition,
				inlineSQLHelper);

			sb.append(sql);
			sb.append(") TEMP_TABLE ORDER BY modelFolder DESC, title ASC");

			sql = sb.toString();

			sql = updateSQL(sql, folderId, includeMountFolders);
			sql = CustomSQLUtil.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("modelFolderId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("title", Type.STRING);
			q.addScalar("fileShortcutId", Type.LONG);
			q.addScalar("modelFolder", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (!includeMountFolders) {
				qPos.add(false);
			}

			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);
			qPos.add(groupId);
			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);

			if (mimeTypes != null) {
				qPos.add(mimeTypes);
			}

			qPos.add(groupId);
			qPos.add(true);
			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);

			if (mimeTypes != null) {
				qPos.add(mimeTypes);
			}

			List<Object> models = new ArrayList<Object>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long curFolderId = (Long)array[0];
				String name = (String)array[1];
				//String title = (String)array[2];
				long fileShortcutId = (Long)array[3];
				long modelFolder = (Long)array[4];

				Object obj = null;

				if (modelFolder == 1) {
					obj = DLFolderUtil.findByPrimaryKey(curFolderId);
				}
				else if (fileShortcutId > 0) {
					obj = DLFileShortcutUtil.findByPrimaryKey(fileShortcutId);
				}
				else {
					obj = DLFileEntryUtil.findByG_F_N(
						groupId, curFolderId, name);
				}

				models.add(obj);
			}

			return models;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<Object> doFindFE_FS_ByG_F(
			long groupId, long folderId, QueryDefinition queryDefinition,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append("SELECT * FROM (");

			String sql = getFileEntriesSQL(
				FIND_FE_BY_G_F, groupId, null, queryDefinition,
				inlineSQLHelper);

			sb.append(sql);
			sb.append(" UNION ALL ");

			sql = getFileShortcutsSQL(
				FIND_FS_BY_G_F_A, groupId, null, queryDefinition,
				inlineSQLHelper);

			sb.append(sql);
			sb.append(") TEMP_TABLE ORDER BY modelFolder DESC, title ASC");

			sql = sb.toString();

			sql = updateSQL(sql, folderId, false);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("modelFolderId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("title", Type.STRING);
			q.addScalar("fileShortcutId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);
			qPos.add(groupId);
			qPos.add(true);
			qPos.add(queryDefinition.getStatus());
			qPos.add(folderId);

			List<Object> models = new ArrayList<Object>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long folderId2 = (Long)array[0];
				String name = (String)array[1];
				//String title = (String)array[2];
				long fileShortcutId = (Long)array[3];

				Object obj = null;

				if (fileShortcutId > 0) {
					obj = DLFileShortcutUtil.findByPrimaryKey(fileShortcutId);
				}
				else {
					obj = DLFileEntryUtil.findByG_F_N(groupId, folderId2, name);
				}

				models.add(obj);
			}

			return models;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getFileEntriesSQL(
		String id, long groupId, String[] mimeTypes,
		QueryDefinition queryDefinition, boolean inlineSQLHelper) {

		String sql = CustomSQLUtil.get(
			id, queryDefinition, DLFileVersionImpl.TABLE_NAME);

		if (inlineSQLHelper) {
			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, DLFileEntry.class.getName(), "DLFileEntry.fileEntryId",
				groupId);
		}

		if (queryDefinition.getStatus() == WorkflowConstants.STATUS_ANY) {
			sql = StringUtil.replace(sql, "[$JOIN$]", StringPool.BLANK);
		}
		else {
			sql = StringUtil.replace(
				sql, "[$JOIN$]",
				CustomSQLUtil.get(
					DLFolderFinderImpl.JOIN_FE_BY_DL_FILE_VERSION));
		}

		if ((mimeTypes != null) && (mimeTypes.length > 0)) {
			StringBundler sb = new StringBundler(5);

			sb.append(sql);
			sb.append(WHERE_AND);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(getMimeTypes(mimeTypes, DLFileEntryImpl.TABLE_NAME));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();
		}

		return sql;
	}

	protected String getFileShortcutsSQL(
		String id, long groupId, String[] mimeTypes,
		QueryDefinition queryDefinition, boolean inlineSQLHelper) {

		String sql = CustomSQLUtil.get(
			id, queryDefinition, DLFileShortcutImpl.TABLE_NAME);

		if (inlineSQLHelper) {
			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, DLFileShortcut.class.getName(),
				"DLFileShortcut.fileShortcutId", groupId);
		}

		if ((mimeTypes != null) && (mimeTypes.length > 0)) {
			StringBundler sb = new StringBundler(5);

			sb.append(
				StringUtil.replace(
					sql, "[$JOIN$]",
					CustomSQLUtil.get(JOIN_FS_BY_DL_FILE_ENTRY)));

			sb.append(WHERE_AND);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(getMimeTypes(mimeTypes, DLFileEntryImpl.TABLE_NAME));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();
		}
		else {
			sql = StringUtil.replace(sql, "[$JOIN$]", StringPool.BLANK);
		}

		return sql;
	}

	protected String getFileVersionsSQL(
		String id, long groupId, String[] mimeTypes,
		QueryDefinition queryDefinition, boolean inlineSQLHelper) {

		String sql = CustomSQLUtil.get(
			id, queryDefinition, DLFileVersionImpl.TABLE_NAME);

		if (inlineSQLHelper) {
			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, DLFileEntry.class.getName(), "DLFileVersion.fileEntryId",
				groupId);
		}

		if ((mimeTypes != null) && (mimeTypes.length > 0)) {
			StringBundler sb = new StringBundler(5);

			sb.append(sql);
			sb.append(WHERE_AND);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(getMimeTypes(mimeTypes, DLFileVersionImpl.TABLE_NAME));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();
		}

		return sql;
	}

	protected String getFolderId(long folderId, String tableName) {
		StringBundler sb = new StringBundler(4);

		sb.append(tableName);
		sb.append(".");

		if (tableName.equals(DLFolderImpl.TABLE_NAME)) {
			sb.append("parentFolderId");
		}
		else {
			sb.append("folderId");
		}

		sb.append("= ? ");

		return sb.toString();
	}

	protected String getMimeTypes(String[] mimeTypes, String tableName) {
		if (mimeTypes.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(mimeTypes.length * 2 - 1);

		for (int i = 0; i < mimeTypes.length; i++) {
			sb.append(tableName);
			sb.append(".mimeType = ?");

			if ((i + 1) != mimeTypes.length) {
				sb.append(WHERE_OR);
			}
		}

		return sb.toString();
	}

	protected String updateSQL(
		String sql, long folderId, boolean includeMountFolders) {

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$FILE_ENTRY_FOLDER_ID$]", "[$FILE_SHORTCUT_FOLDER_ID$]",
				"[$FILE_VERSION_FOLDER_ID$]", "[$FOLDER_PARENT_FOLDER_ID$]"
			},
			new String[] {
				getFolderId(folderId, DLFileEntryImpl.TABLE_NAME),
				getFolderId(folderId, DLFileShortcutImpl.TABLE_NAME),
				getFolderId(folderId, DLFileVersionImpl.TABLE_NAME),
				getFolderId(folderId, DLFolderImpl.TABLE_NAME)
			});

		if (includeMountFolders) {
			sql = StringUtil.replace(
				sql, "(DLFolder.mountPoint = ?) AND", StringPool.BLANK);
		}

		return sql;
	}

}