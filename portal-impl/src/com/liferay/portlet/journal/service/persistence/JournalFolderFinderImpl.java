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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="JournalFolderFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Juan Fernández
 */
public class JournalFolderFinderImpl extends BasePersistenceImpl<JournalFolder>
	implements JournalFolderFinder {

	public static final String COUNT_F_BY_G_F =
		JournalFolderFinder.class.getName() + ".countF_ByG_F";

	public static final String COUNT_JA_BY_G_F =
		JournalFolderFinder.class.getName() + ".countJA_ByG_F";

	public static final String FIND_F_BY_G_F =
		JournalFolderFinder.class.getName() + ".findF_ByG_F";

	public static final String FIND_JA_BY_G_F =
		JournalFolderFinder.class.getName() + ".findJA_ByG_F";

	public int countF_JA_ByG_F(long groupId, long folderId)
		throws SystemException {

		return doCountF_JA_ByG_F(groupId, folderId, false);
	}

	public int filterCountF_JA_ByG_F(long groupId, long folderId)
		throws SystemException {

		return doCountF_JA_ByG_F(groupId, folderId, true);
	}

	public List<Object> filterFindF_JAByG_F(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return doFindF_JAByG_F(groupId, folderId, start, end, obc, true);
	}

	public List<Object> findF_JAByG_F(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return doFindF_JAByG_F(groupId, folderId, start, end, obc, false);
	}

	protected int doCountF_JA_ByG_F(
			long groupId, long folderId, boolean inlineSQLHelper)
		throws SystemException {

	   Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);

			String sql = CustomSQLUtil.get(COUNT_F_BY_G_F);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalFolder.class.getName(),
					"JournalFolder.folderId", groupId);
			}

			sb.append(sql);
			sb.append(") UNION ALL (");
			sb.append(getCountJournalArticlesSQL(groupId, inlineSQLHelper));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();

			sql = updateSQL(sql);

			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(folderId);

			qPos.add(groupId);
			qPos.add(folderId);

			int count = 0;

			Iterator<BigInteger> itr = q.iterate();

			while (itr.hasNext()) {
				BigInteger bi = itr.next();

				if (bi != null) {
					count += bi.intValue();
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

	protected List<Object> doFindF_JAByG_F(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);

			String sql = CustomSQLUtil.get(FIND_F_BY_G_F);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalFolder.class.getName(),
					"JournalFolder.folderId", groupId);
			}

			sb.append(sql);
			sb.append(") UNION ALL (");
			sb.append(getJournalArticlesSQL(groupId, inlineSQLHelper));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = updateSQL(sb.toString());

			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(folderId);

			qPos.add(groupId);
			qPos.add(folderId);

			List<Object> models = new ArrayList<Object>();

			Iterator<Object[]> itr = (Iterator<Object[]>) QueryUtil.iterate(
				q, getDialect(), start, end);

			while (itr.hasNext()) {
				Object[] array = itr.next();

				BigInteger curFolderId = (BigInteger) array[0];
				BigInteger modelFolder = (BigInteger) array[4];

				Object obj = null;

				if (modelFolder.intValue() == 1) {
					obj = JournalFolderUtil.findByPrimaryKey(
						curFolderId.intValue());
				}
				else {
					String articleId = (String)array[5];

					obj = JournalArticleUtil.findByG_A_Last(
						groupId, articleId, null);
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

	protected String getCountJournalArticlesSQL(
		long groupId, boolean inlineSQLHelper) {

		String sql = CustomSQLUtil.get(COUNT_JA_BY_G_F);

		if (inlineSQLHelper) {
			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, JournalArticle.class.getName(),
				"JournalArticle.resourcePrimKey", groupId);
		}

		return sql;
	}

	protected String getFolderId(String table) {
		StringBundler sb = new StringBundler(4);

		sb.append(table);
		sb.append(".");

		if (table.equals("JournalFolder")) {
			sb.append("parentFolderId");
		}
		else {
			sb.append("folderId");
		}

		sb.append("= ? ");

		return sb.toString();
	}

	protected String getJournalArticlesSQL(
		long groupId, boolean inlineSQLHelper) {

		String sql = CustomSQLUtil.get(FIND_JA_BY_G_F);

		if (inlineSQLHelper) {
			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, JournalArticle.class.getName(),
				"JournalArticle.resourcePrimKey", groupId);
		}

		return sql;
	}

	protected String updateSQL(String sql) {
		sql = StringUtil.replace(
			sql, "[$FOLDER_PARENT_FOLDER_ID$]", getFolderId("JournalFolder"));

		sql = StringUtil.replace(
			sql, "[$JOURNAL_ARTICLE_FOLDER_ID$]",
			getFolderId("JournalArticle"));

		return sql;
	}

}