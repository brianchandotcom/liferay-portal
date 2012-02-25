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
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class DLFileEntryFinderImpl
	extends BasePersistenceImpl<DLFileEntry> implements DLFileEntryFinder {

	public static final String COUNT_BY_EXTRA_SETTINGS =
		DLFileEntryFinder.class.getName() + ".countByExtraSettings";

	public static final String COUNT_BY_G_F =
		DLFileEntryFinder.class.getName() + ".countByG_F";

	public static final String COUNT_BY_G_U_F =
		DLFileEntryFinder.class.getName() + ".countByG_U_F";

	public static final String COUNT_BY_G_F_S =
		DLFileEntryFinder.class.getName() + ".countByG_F_S";

	public static final String COUNT_BY_G_U_F_S =
		DLFileEntryFinder.class.getName() + ".countByG_U_F_S";

	public static final String FIND_BY_ANY_IMAGE_ID =
		DLFileEntryFinder.class.getName() + ".findByAnyImageId";

	public static final String FIND_BY_EXTRA_SETTINGS =
		DLFileEntryFinder.class.getName() + ".findByExtraSettings";

	public static final String FIND_BY_NO_ASSETS =
		DLFileEntryFinder.class.getName() + ".findByNoAssets";

	public static final String FIND_BY_ORPHANED_FILE_ENTRIES =
		DLFileEntryFinder.class.getName() + ".findByOrphanedFileEntries";

	public static final String FIND_BY_G_F =
		DLFileEntryFinder.class.getName() + ".findByG_F";

	public static final String FIND_BY_G_U_F =
		DLFileEntryFinder.class.getName() + ".findByG_U_F";

	public static final String FIND_BY_G_F_S =
		DLFileEntryFinder.class.getName() + ".findByG_F_S";

	public static final String FIND_BY_G_U_F_S =
		DLFileEntryFinder.class.getName() + ".findByG_U_F_S";

	public int countByExtraSettings() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_EXTRA_SETTINGS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

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

	public int countByG_F_S(long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return doCountByG_F_S(groupId, folderIds, status, false);
	}

	public int countByG_U_F_M_S(
			long groupId, long userId, List<Long> folderIds, String[] mimeTypes,
			int status)
		throws SystemException {

		Session session = null;

		String table = DLFileVersionImpl.TABLE_NAME;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_U_F_S);

			if (userId <= 0) {
				if (status == WorkflowConstants.STATUS_ANY) {
					table = DLFileEntryImpl.TABLE_NAME;

					sql = CustomSQLUtil.get(COUNT_BY_G_F);
				}
				else {
					sql = CustomSQLUtil.get(COUNT_BY_G_F_S);

					sql = StringUtil.replace(sql, "[$JOIN$]", "");
				}
			}
			else {
				if (status == WorkflowConstants.STATUS_ANY) {
					table = DLFileEntryImpl.TABLE_NAME;

					sql = CustomSQLUtil.get(COUNT_BY_G_U_F);
				}
			}

			StringBundler sb = new StringBundler();

			if (folderIds.size() > 0) {
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(getFolderIds(folderIds, table));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if ((mimeTypes != null) && (mimeTypes.length > 0)) {
				sb.append(WHERE_AND);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(getMimeTypes(mimeTypes, table));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sql = StringUtil.replace(sql, "[$FOLDER_ID$]", sb.toString());

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (userId > 0) {
				qPos.add(userId);
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

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

	public DLFileEntry fetchByAnyImageId(long imageId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_ANY_IMAGE_ID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("DLFileEntry", DLFileEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(imageId);
			qPos.add(imageId);
			qPos.add(imageId);
			qPos.add(imageId);

			List<DLFileEntry> dlFileEntries = q.list();

			if (!dlFileEntries.isEmpty()) {
				return dlFileEntries.get(0);
			}

			return null;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int filterCountByG_F_S(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return doCountByG_F_S(groupId, folderIds, status, true);
	}

	public DLFileEntry findByAnyImageId(long imageId)
		throws NoSuchFileEntryException, SystemException {

		DLFileEntry dlFileEntry = fetchByAnyImageId(imageId);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		throw new NoSuchFileEntryException(
			"No DLFileEntry exists with the imageId " + imageId);
	}

	public List<DLFileEntry> findByExtraSettings(int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_EXTRA_SETTINGS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("DLFileEntry", DLFileEntryImpl.class);

			return (List<DLFileEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileEntry> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("DLFileEntry", DLFileEntryImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileEntry> findByOrphanedFileEntries()
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_ORPHANED_FILE_ENTRIES);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("DLFileEntry", DLFileEntryImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileEntry> findByG_U_F_M_S(
			long groupId, long userId, List<Long> folderIds, String[] mimeTypes,
			int status, int start, int end, OrderByComparator obc)
		throws SystemException {

		Session session = null;

		String table = DLFileVersionImpl.TABLE_NAME;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_U_F_S);

			if (userId <= 0) {
				if (status == WorkflowConstants.STATUS_ANY) {
					table = DLFileEntryImpl.TABLE_NAME;

					sql = CustomSQLUtil.get(FIND_BY_G_F);
				}
				else {
					sql = CustomSQLUtil.get(FIND_BY_G_F_S);
				}
			}
			else {
				if (status == WorkflowConstants.STATUS_ANY) {
					table = DLFileEntryImpl.TABLE_NAME;

					sql = CustomSQLUtil.get(FIND_BY_G_U_F);
				}
			}

			StringBundler sb = new StringBundler();

			if (folderIds.size() > 0) {
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(getFolderIds(folderIds, table));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			if ((mimeTypes != null) && (mimeTypes.length > 0)) {
				sb.append(WHERE_AND);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(getMimeTypes(mimeTypes, table));
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sql = StringUtil.replace(sql, "[$FOLDER_ID$]", sb.toString());
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("DLFileEntry", DLFileEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (userId > 0) {
				qPos.add(userId);
			}

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			return (List<DLFileEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByG_F_S(
			long groupId, List<Long> folderIds, int status,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = null;

			String table = "DLFileEntry";

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.get(COUNT_BY_G_F);
			}
			else {
				sql = CustomSQLUtil.get(COUNT_BY_G_F_S);

				if (inlineSQLHelper && InlineSQLHelperUtil.isEnabled()) {

					sql = StringUtil.replace(
						sql, "[$JOIN$]",
						CustomSQLUtil.get(
							DLFolderFinderImpl.JOIN_FV_BY_DL_FILE_ENTRY));
				}
				else {
					table = "DLFileVersion";

					sql = StringUtil.replace(sql, "[$JOIN$]", "");
				}
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DLFileEntry.class.getName(), "DLFileEntry.fileEntryId",
					groupId);
			}

			sql = StringUtil.replace(
				sql, "[$FOLDER_ID$]", getFolderIds(folderIds, table));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			for (int i = 0; i < folderIds.size(); i++) {
				Long folderId = folderIds.get(i);

				qPos.add(folderId);
			}

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

	protected String getFolderIds(List<Long> folderIds, String table) {
		if (folderIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(folderIds.size() * 2 - 1);

		for (int i = 0; i < folderIds.size(); i++) {
			sb.append(table);
			sb.append(".folderId = ? ");

			if ((i + 1) != folderIds.size()) {
				sb.append(WHERE_OR);
			}
		}

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

}