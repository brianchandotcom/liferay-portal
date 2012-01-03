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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
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

	public static String COUNT_F_BY_G_M_F =
		DLFolderFinder.class.getName() + ".countF_ByG_M_F";

	public static String COUNT_FE_BY_G_F =
		DLFolderFinder.class.getName() + ".countFE_ByG_F";

	public static String COUNT_FE_BY_G_F_S =
		DLFolderFinder.class.getName() + ".countFE_ByG_F_S";

	public static String COUNT_FS_BY_G_F_S =
		DLFolderFinder.class.getName() + ".countFS_ByG_F_S";

	public static String FIND_F_BY_G_M_F =
		DLFolderFinder.class.getName() + ".findF_ByG_M_F";

	public static String FIND_FE_BY_G_F =
		DLFolderFinder.class.getName() + ".findFE_ByG_F";

	public static String FIND_FE_BY_G_F_S =
		DLFolderFinder.class.getName() + ".findFE_ByG_F_S";

	public static String FIND_FS_BY_G_F_S =
		DLFolderFinder.class.getName() + ".findFS_ByG_F_S";

	public static String JOIN_FS_BY_DL_FILE_ENTRY =
		DLFolderFinder.class.getName() + ".joinFS_ByDLFileEntry";

	public static String JOIN_FV_BY_DL_FILE_ENTRY =
		DLFolderFinder.class.getName() + ".joinFV_ByDLFileEntry";

	public int countF_FE_FS_ByG_F_S(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		return doCountF_FE_FS_ByG_F_S(
			groupId, folderId, status, mimeTypes, includeMountFolders, false);
	}

	public int countFE_ByG_F_S(long groupId, long folderId, int status)
		throws SystemException {

		return doCountFE_ByG_F_S(groupId, folderId, status, false);
	}

	public int filterCountF_FE_FS_ByG_F_S(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		return doCountF_FE_FS_ByG_F_S(
			groupId, folderId, status, mimeTypes, includeMountFolders, true);
	}

	public int filterCountFE_ByG_F_S(long groupId, long folderId, int status)
		throws SystemException {

		return doCountFE_ByG_F_S(groupId, folderId, status, true);
	}

	public int filterCountFE_FS_ByG_F_S(
			long groupId, long folderId, int status, String[] mimeTypes)
		throws SystemException {

		return doCountFE_FS_ByG_F_S(groupId, folderId, status, mimeTypes, true);
	}

	public List<Object> filterFindF_FE_FS_ByG_F_S(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return doFindF_FE_FS_ByG_F_S(
			groupId, folderId, status, mimeTypes, includeMountFolders, start,
			end, obc, true);
	}

	public List<Object> filterFindFE_FS_ByG_F_S(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return doFindFE_FS_ByG_F_S(groupId, folderId, status, start, end, true);
	}

	public List<Object> findF_FE_FS_ByG_F_S(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return doFindF_FE_FS_ByG_F_S(
			groupId, folderId, status, mimeTypes, includeMountFolders, start,
			end, obc, false);
	}

	public List<Object> findFE_FS_ByG_F_S(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return doFindFE_FS_ByG_F_S(
			groupId, folderId, status, start, end, false);
	}

	protected int doCountF_FE_FS_ByG_F_S(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(7);

			sb.append(StringPool.OPEN_PARENTHESIS);

			String sql = CustomSQLUtil.get(COUNT_F_BY_G_M_F);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFolder.class.getName(), "DLFolder.folderId",
					groupId);
			}
			sb.append(sql);

			sb.append(") UNION ALL (");

			sb.append(getFileEntriesQuery(
				groupId, inlineSQLHelper, status, mimeTypes));

			sb.append(") UNION ALL (");

			sb.append(getFileShortcutsQuery(
				groupId, inlineSQLHelper, status, mimeTypes));

			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();

			sql = replaceFolderTemplates(
					sql, folderId, includeMountFolders, status);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (!includeMountFolders) {
				qPos.add(false);
			}

			qPos.add(folderId);
			qPos.add(groupId);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(folderId);
			qPos.add(groupId);
			qPos.add(folderId);

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

	protected int doCountFE_ByG_F_S(
			long groupId, long folderId, int status, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_FE_BY_G_F_S);

			if (inlineSQLHelper && InlineSQLHelperUtil.isEnabled(groupId)) {
				sql = StringUtil.replace(
					sql, "[$JOIN$]",
					CustomSQLUtil.get(JOIN_FV_BY_DL_FILE_ENTRY));

				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileEntry.class.getName(), "DLFileEntry.fileEntryId",
					groupId);
			}
			else {
				sql = StringUtil.replace(sql, "[$JOIN$]", "");
			}

			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderId(folderId, "DLFileVersion"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(status);
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

	protected int doCountFE_FS_ByG_F_S(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(7);

			sb.append(StringPool.OPEN_PARENTHESIS);

			String sql = getFileEntriesQuery(
					groupId, inlineSQLHelper, status, mimeTypes);

			sb.append(sql);

			sb.append(") UNION ALL (");

			sb.append(getFileShortcutsQuery(
					groupId, inlineSQLHelper, status, mimeTypes));

			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();

			sql = replaceFolderTemplates(sql, folderId, false, status);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(folderId);
			qPos.add(groupId);
			qPos.add(folderId);

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

	protected List<Object> doFindF_FE_FS_ByG_F_S(
			long groupId, long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(7);

			sb.append("SELECT * FROM (");

			String sql = CustomSQLUtil.get(FIND_F_BY_G_M_F);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFolder.class.getName(), "DLFolder.folderId",
					groupId);
			}

			sb.append(sql);
			sb.append(" UNION ALL ");

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.get(FIND_FE_BY_G_F);
			}
			else {
				sql = CustomSQLUtil.get(FIND_FE_BY_G_F_S);
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileEntry.class.getName(), "DLFileEntry.fileEntryId",
					groupId);
			}

			sb.append(sql);

			if ((mimeTypes != null) && (mimeTypes.length > 0)) {
				for (int i = 0; i < mimeTypes.length; i++) {
					if (i == 0) {
						sb.append(" AND (");
					}
					else {
						sb.append(" OR");
					}

					sb.append(" DLFileEntry.mimeType = '");
					sb.append(mimeTypes[i]);
					sb.append("'");
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sb.append(" UNION ALL ");

			sql = CustomSQLUtil.get(FIND_FS_BY_G_F_S);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileShortcut.class.getName(),
					"DLFileShortcut.fileShortcutId", groupId);
			}

			sb.append(sql);

			if ((mimeTypes != null) && (mimeTypes.length > 0)) {
				for (int i = 0; i < mimeTypes.length; i++) {
					if (i == 0) {
						sb.append(" AND (");
					}
					else {
						sb.append(" OR");
					}

					sb.append(" mimeType = '");
					sb.append(mimeTypes[i]);
					sb.append("'");
				}

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sb.append(") TEMP_TABLE ORDER BY modelFolder DESC, title ASC");

			sql = sb.toString();

			if (includeMountFolders) {
				sql = StringUtil.replace(
					sql, "(DLFolder.mountPoint = ?) AND", "");
			}

			sql = StringUtil.replace(
				sql, "[$FOLDER_PARENT_FOLDER_ID$]",
				getFolderId(folderId, "DLFolder"));
			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderId(folderId, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderId(folderId, "DLFileShortcut"));
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

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

			qPos.add(folderId);
			qPos.add(groupId);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(folderId);
			qPos.add(groupId);
			qPos.add(folderId);

			List<Object> models = new ArrayList<Object>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), start, end);

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

	protected List<Object> doFindFE_FS_ByG_F_S(
			long groupId, long folderId, int status, int start, int end,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append("SELECT * FROM (");

			String sql = null;

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.get(FIND_FE_BY_G_F);
			}
			else {
				sql = CustomSQLUtil.get(FIND_FE_BY_G_F_S);
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileEntry.class.getName(), "DLFileEntry.fileEntryId",
					groupId);
			}

			sb.append(sql);
			sb.append(" UNION ALL ");

			sql = CustomSQLUtil.get(FIND_FS_BY_G_F_S);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileShortcut.class.getName(),
					"DLFileShortcut.fileShortcutId", groupId);
			}

			sb.append(sql);
			sb.append(") TEMP_TABLE ORDER BY modelFolder DESC, title ASC");

			sql = sb.toString();

			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderId(folderId, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderId(folderId, "DLFileShortcut"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("modelFolderId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("title", Type.STRING);
			q.addScalar("fileShortcutId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(folderId);
			qPos.add(groupId);
			qPos.add(folderId);

			List<Object> models = new ArrayList<Object>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), start, end);

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

	protected String getFileEntriesQuery(long groupId,
			boolean inlineSQLHelper, int status, String[] mimeTypes) {
		StringBundler sb = new StringBundler(50);

		String sql;

		if (status == WorkflowConstants.STATUS_ANY) {
			sql = CustomSQLUtil.get(COUNT_FE_BY_G_F);
		}
		else {
			sql = CustomSQLUtil.get(COUNT_FE_BY_G_F_S);

			if ((inlineSQLHelper &&
				 InlineSQLHelperUtil.isEnabled(groupId)) ||
				((mimeTypes != null) && (mimeTypes.length > 0))) {

				sql = StringUtil.replace(
					sql, "[$JOIN$]",
					CustomSQLUtil.get(JOIN_FV_BY_DL_FILE_ENTRY));
			}
			else {
				sql = StringUtil.replace(sql, "[$JOIN$]", "");
			}
		}

		if (inlineSQLHelper) {
			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, DLFileEntry.class.getName(), "DLFileEntry.fileEntryId",
				groupId);
		}

		sb.append(sql);

		if ((mimeTypes != null) && (mimeTypes.length > 0)) {
			sb.append(WHERE_AND).append(StringPool.OPEN_PARENTHESIS);

			sb.append(getMimeTypes(mimeTypes, "DLFileEntry"));

			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		return sb.toString();
	}

	protected String getFileShortcutsQuery(long groupId,
		boolean inlineSQLHelper, int status, String[] mimeTypes) {
		String sql = CustomSQLUtil.get(COUNT_FS_BY_G_F_S);

		if ((inlineSQLHelper && InlineSQLHelperUtil.isEnabled(groupId)) ||
			((mimeTypes != null) && (mimeTypes.length > 0))) {

			sql = StringUtil.replace(
				sql, "[$JOIN$]",
				CustomSQLUtil.get(JOIN_FS_BY_DL_FILE_ENTRY));
		}
		else {
			sql = StringUtil.replace(sql, "[$JOIN$]", "");
		}

		if (inlineSQLHelper) {
			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, DLFileShortcut.class.getName(),
				"DLFileShortcut.fileShortcutId", groupId);
		}

		StringBundler sb = new StringBundler(sql);

		if ((mimeTypes != null) && (mimeTypes.length > 0)) {
			sb.append(WHERE_AND).append(StringPool.OPEN_PARENTHESIS);

			sb.append(getMimeTypes(mimeTypes, "DLFileEntry"));

			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		return sb.toString();
	}

	protected String getFolderId(long folderId, String table) {
		StringBundler sb = new StringBundler(4);

		sb.append(table);
		sb.append(".");

		if (table.equals("DLFolder")) {
			sb.append("parentFolderId");
		}
		else {
			sb.append("folderId");
		}

		sb.append("= ? ");

		return sb.toString();
	}

	protected String getMimeTypes(String[] mimeTypes, String table) {
		if (mimeTypes.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(mimeTypes.length * 2 - 1);

		for (int i = 0; i < mimeTypes.length; i++) {
			sb.append(table);

			sb.append(".mimeType = '");
			sb.append(mimeTypes[i]);
			sb.append("'");

			if ((i + 1) != mimeTypes.length) {
				sb.append(WHERE_OR);
			}
		}

		return sb.toString();
	}

	protected String replaceFolderTemplates(String query,
			long folderId, boolean includeMountFolders, int status) {
		String sql = query;

		if (includeMountFolders) {
			sql = StringUtil.replace(sql, "(DLFolder.mountPoint = ?) AND", "");
		}

		sql = StringUtil.replace(
			sql, "[$FOLDER_PARENT_FOLDER_ID$]",
			getFolderId(folderId, "DLFolder"));

		if (status == WorkflowConstants.STATUS_ANY) {
			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderId(folderId, "DLFileEntry"));
		}
		else {
			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderId(folderId, "DLFileVersion"));
		}

		return StringUtil.replace(
			sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
			getFolderId(folderId, "DLFileShortcut"));
	}

}