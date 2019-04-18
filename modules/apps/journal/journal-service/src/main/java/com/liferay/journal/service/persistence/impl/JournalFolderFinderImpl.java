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

package com.liferay.journal.service.persistence.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.impl.JournalArticleImpl;
import com.liferay.journal.model.impl.JournalFolderImpl;
import com.liferay.journal.service.persistence.JournalArticleUtil;
import com.liferay.journal.service.persistence.JournalFolderFinder;
import com.liferay.journal.service.persistence.JournalFolderUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 * @author Zsolt Berentey
 */
@Component(service = JournalFolderFinder.class)
public class JournalFolderFinderImpl
	extends JournalFolderFinderBaseImpl implements JournalFolderFinder {

	public static final String COUNT_A_BY_G_U_F =
		JournalFolderFinder.class.getName() + ".countA_ByG_U_F";

	public static final String COUNT_F_BY_G_F =
		JournalFolderFinder.class.getName() + ".countF_ByG_F";

	public static final String FIND_A_BY_G_U_F =
		JournalFolderFinder.class.getName() + ".findA_ByG_U_F";

	public static final String FIND_A_BY_G_U_F_L =
		JournalFolderFinder.class.getName() + ".findA_ByG_U_F_L";

	public static final String FIND_F_BY_NO_ASSETS =
		JournalFolderFinder.class.getName() + ".findF_ByNoAssets";

	public static final String FIND_F_BY_G_F =
		JournalFolderFinder.class.getName() + ".findF_ByG_F";

	public static final String FIND_F_BY_G_F_L =
		JournalFolderFinder.class.getName() + ".findF_ByG_F_L";

	@Override
	public int countF_A_ByG_F(
		long groupId, long folderId, QueryDefinition<?> queryDefinition) {

		return doCountF_A_ByG_F(groupId, folderId, queryDefinition, false);
	}

	@Override
	public int filterCountF_A_ByG_F(
		long groupId, long folderId, QueryDefinition<?> queryDefinition) {

		return doCountF_A_ByG_F(groupId, folderId, queryDefinition, true);
	}

	@Override
	public List<Object> filterFindF_A_ByG_F(
		long groupId, long folderId, QueryDefinition<?> queryDefinition) {

		return doFindF_A_ByG_F(groupId, folderId, queryDefinition, true);
	}

	@Override
	public List<Object> filterFindF_A_ByG_F_L(
		long groupId, long folderId, Locale locale,
		QueryDefinition<?> queryDefinition) {

		return doFindF_A_ByG_F_L(
			groupId, folderId, locale, queryDefinition, true);
	}

	@Override
	public List<Object> findF_A_ByG_F(
		long groupId, long folderId, QueryDefinition<?> queryDefinition) {

		return doFindF_A_ByG_F(groupId, folderId, queryDefinition, false);
	}

	@Override
	public List<JournalFolder> findF_ByNoAssets() {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_F_BY_NO_ASSETS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(JournalFolderImpl.TABLE_NAME, JournalFolderImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(_portal.getClassNameId(JournalFolder.class));

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountF_A_ByG_F(
		long groupId, long folderId, QueryDefinition<?> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				getFoldersSQL(
					COUNT_F_BY_G_F, groupId, queryDefinition, inlineSQLHelper));
			sb.append(") UNION ALL (");
			sb.append(
				getArticlesSQL(
					COUNT_A_BY_G_U_F, groupId, queryDefinition,
					inlineSQLHelper));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String sql = updateSQL(sb.toString(), folderId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(queryDefinition.getStatus());

			if (folderId >= 0) {
				qPos.add(folderId);
			}

			qPos.add(groupId);

			if (queryDefinition.getOwnerUserId() > 0) {
				qPos.add(queryDefinition.getOwnerUserId());
				qPos.add(WorkflowConstants.STATUS_IN_TRASH);
			}

			qPos.add(queryDefinition.getStatus());

			if (folderId >= 0) {
				qPos.add(folderId);
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

	protected List<Object> doFindF_A_ByG_F(
		long groupId, long folderId, QueryDefinition<?> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				getFoldersSQL(
					FIND_F_BY_G_F, groupId, queryDefinition, inlineSQLHelper));
			sb.append(") UNION ALL (");
			sb.append(
				getArticlesSQL(
					FIND_A_BY_G_U_F, groupId, queryDefinition,
					inlineSQLHelper));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String sql = updateSQL(sb.toString(), folderId);

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("modelFolderId", Type.LONG);
			q.addScalar("modelFolder", Type.LONG);
			q.addScalar("articleId", Type.STRING);
			q.addScalar("version", Type.DOUBLE);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(queryDefinition.getStatus());

			if (folderId >= 0) {
				qPos.add(folderId);
			}

			qPos.add(groupId);

			if (queryDefinition.getOwnerUserId() > 0) {
				qPos.add(queryDefinition.getOwnerUserId());
				qPos.add(WorkflowConstants.STATUS_IN_TRASH);
			}

			qPos.add(queryDefinition.getStatus());

			if (folderId >= 0) {
				qPos.add(folderId);
			}

			List<Object> models = new ArrayList<>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long curFolderId = (Long)array[0];
				long modelFolder = (Long)array[1];

				Object obj = null;

				if (modelFolder == 1) {
					obj = JournalFolderUtil.findByPrimaryKey(curFolderId);
				}
				else {
					String articleId = (String)array[2];
					double version = (Double)array[3];

					obj = JournalArticleUtil.findByG_A_V(
						groupId, articleId, version);
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

	protected List<Object> doFindF_A_ByG_F_L(
		long groupId, long folderId, Locale locale,
		QueryDefinition<?> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				getFoldersSQL(
					FIND_F_BY_G_F_L, groupId, queryDefinition,
					inlineSQLHelper));
			sb.append(") UNION ALL (");
			sb.append(
				getArticlesSQL(
					FIND_A_BY_G_U_F_L, groupId, queryDefinition,
					inlineSQLHelper));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String sql = updateSQL(sb.toString(), folderId);

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("modelFolderId", Type.LONG);
			q.addScalar("modelFolder", Type.LONG);
			q.addScalar("articleId", Type.STRING);
			q.addScalar("version", Type.DOUBLE);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(queryDefinition.getStatus());

			if (folderId >= 0) {
				qPos.add(folderId);
			}

			qPos.add(groupId);

			if (queryDefinition.getOwnerUserId() > 0) {
				qPos.add(queryDefinition.getOwnerUserId());
				qPos.add(WorkflowConstants.STATUS_IN_TRASH);
			}

			qPos.add(queryDefinition.getStatus());

			if (folderId >= 0) {
				qPos.add(folderId);
			}

			qPos.add(LocaleUtil.toLanguageId(locale));

			List<Object> models = new ArrayList<>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long curFolderId = (Long)array[0];
				long modelFolder = (Long)array[1];

				Object obj = null;

				if (modelFolder == 1) {
					obj = JournalFolderUtil.findByPrimaryKey(curFolderId);
				}
				else {
					String articleId = (String)array[2];
					double version = (Double)array[3];

					obj = JournalArticleUtil.findByG_A_V(
						groupId, articleId, version);
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

	protected String getArticlesSQL(
		String id, long groupId, QueryDefinition<?> queryDefinition,
		boolean inlineSQLHelper) {

		String sql = _customSQL.get(
			getClass(), id, queryDefinition, JournalArticleImpl.TABLE_NAME);

		if (inlineSQLHelper) {
			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, JournalArticle.class.getName(),
				"JournalArticle.resourcePrimKey", groupId);
		}

		return sql;
	}

	protected String getFolderId(long folderId, String tableName) {
		if (folderId < 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(" AND ");
		sb.append(tableName);
		sb.append(".");

		if (tableName.equals(JournalFolderImpl.TABLE_NAME)) {
			sb.append("parentFolderId");
		}
		else {
			sb.append("folderId");
		}

		sb.append(" = ? ");

		return sb.toString();
	}

	protected String getFoldersSQL(
		String id, long groupId, QueryDefinition<?> queryDefinition,
		boolean inlineSQLHelper) {

		String sql = _customSQL.get(
			getClass(), id, queryDefinition, JournalFolderImpl.TABLE_NAME);

		if (inlineSQLHelper) {
			sql = InlineSQLHelperUtil.replacePermissionCheck(
				sql, JournalFolder.class.getName(), "JournalFolder.folderId",
				groupId);
		}

		return sql;
	}

	protected String updateSQL(String sql, long folderId) {
		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ARTICLE_FOLDER_ID$]", "[$FOLDER_PARENT_FOLDER_ID$]"
			},
			new String[] {
				getFolderId(folderId, JournalArticleImpl.TABLE_NAME),
				getFolderId(folderId, JournalFolderImpl.TABLE_NAME)
			});

		return sql;
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private Portal _portal;

}