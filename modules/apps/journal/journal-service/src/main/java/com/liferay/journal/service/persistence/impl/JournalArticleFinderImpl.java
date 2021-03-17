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

import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.impl.JournalArticleImpl;
import com.liferay.journal.service.persistence.JournalArticleFinder;
import com.liferay.journal.service.persistence.JournalArticleUtil;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Connor McKay
 */
@Component(service = JournalArticleFinder.class)
public class JournalArticleFinderImpl
	extends JournalArticleFinderBaseImpl implements JournalArticleFinder {

	public static final String COUNT_BY_G_F =
		JournalArticleFinder.class.getName() + ".countByG_F";

	public static final String COUNT_BY_G_ST =
		JournalArticleFinder.class.getName() + ".countByG_ST";

	public static final String COUNT_BY_G_C_S =
		JournalArticleFinder.class.getName() + ".countByG_C_S";

	public static final String COUNT_BY_G_U_F_C =
		JournalArticleFinder.class.getName() + ".countByG_U_F_C";

	public static final String FIND_BY_EXPIRATION_DATE =
		JournalArticleFinder.class.getName() + ".findByExpirationDate";

	public static final String FIND_BY_NO_ASSETS =
		JournalArticleFinder.class.getName() + ".findByNoAssets";

	public static final String FIND_BY_NO_PERMISSIONS =
		JournalArticleFinder.class.getName() + ".findByNoPermissions";

	public static final String FIND_BY_REVIEW_DATE =
		JournalArticleFinder.class.getName() + ".findByReviewDate";

	public static final String FIND_BY_R_D =
		JournalArticleFinder.class.getName() + ".findByR_D";

	public static final String FIND_BY_G_F =
		JournalArticleFinder.class.getName() + ".findByG_F";

	public static final String FIND_BY_G_ST =
		JournalArticleFinder.class.getName() + ".findByG_ST";

	public static final String FIND_BY_G_ST_L =
		JournalArticleFinder.class.getName() + ".findByG_ST_L";

	public static final String FIND_BY_G_F_L =
		JournalArticleFinder.class.getName() + ".findByG_F_L";

	public static final String FIND_BY_G_C_S =
		JournalArticleFinder.class.getName() + ".findByG_C_S";

	public static final String FIND_BY_G_C_S_L =
		JournalArticleFinder.class.getName() + ".findByG_C_S_L";

	public static final String FIND_BY_G_U_F_C =
		JournalArticleFinder.class.getName() + ".findByG_U_F_C";

	public static final String FIND_BY_G_U_F_C_L =
		JournalArticleFinder.class.getName() + ".findByG_U_F_C_L";

	@Override
	public int countByG_F(
		long groupId, List<Long> folderIds,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doCountByG_F(groupId, folderIds, queryDefinition, false);
	}

	@Override
	public int countByG_ST(
		long groupId, int status,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doCountByG_ST(groupId, status, queryDefinition, false);
	}

	@Override
	public int countByG_C_S(
		long groupId, long classNameId, String ddmStructureKey,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doCountByG_C_S(
			groupId, classNameId, new String[] {ddmStructureKey},
			queryDefinition, false);
	}

	@Override
	public int countByG_F_C(
		long groupId, List<Long> folderIds, long classNameId,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doCountByG_F_C(
			groupId, folderIds, classNameId, queryDefinition, false);
	}

	@Override
	public int filterCountByG_F(
		long groupId, List<Long> folderIds,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doCountByG_F(groupId, folderIds, queryDefinition, true);
	}

	@Override
	public int filterCountByG_ST(
		long groupId, int status,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doCountByG_ST(groupId, status, queryDefinition, true);
	}

	@Override
	public int filterCountByG_C_S(
		long groupId, long classNameId, String ddmStructureKey,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doCountByG_C_S(
			groupId, classNameId, new String[] {ddmStructureKey},
			queryDefinition, true);
	}

	@Override
	public int filterCountByG_F_C(
		long groupId, List<Long> folderIds, long classNameId,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doCountByG_F_C(
			groupId, folderIds, classNameId, queryDefinition, true);
	}

	@Override
	public List<JournalArticle> filterFindByG_ST(
		long groupId, int status,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_ST(groupId, status, queryDefinition, true);
	}

	@Override
	public List<JournalArticle> filterFindByG_ST_L(
		long groupId, int status, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_ST_L(groupId, status, locale, queryDefinition, true);
	}

	@Override
	public List<JournalArticle> filterFindByG_F_L(
		long groupId, List<Long> folderIds, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_F_L(groupId, folderIds, locale, queryDefinition, true);
	}

	@Override
	public List<JournalArticle> filterFindByG_C_S(
		long groupId, long classNameId, String ddmStructureKey,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_C_S(
			groupId, classNameId, new String[] {ddmStructureKey},
			queryDefinition, true);
	}

	@Override
	public List<JournalArticle> filterFindByG_C_S(
		long groupId, long classNameId, String[] ddmStructureKeys,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_C_S(
			groupId, classNameId, ddmStructureKeys, queryDefinition, true);
	}

	@Override
	public List<JournalArticle> filterFindByG_F_C(
		long groupId, List<Long> folderIds, long classNameId,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_F_C(
			groupId, folderIds, classNameId, queryDefinition, true);
	}

	@Override
	public List<JournalArticle> filterFindByG_C_S_L(
		long groupId, long classNameId, String ddmStructureKey, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_C_S_L(
			groupId, classNameId, new String[] {ddmStructureKey}, locale,
			queryDefinition, true);
	}

	@Override
	public List<JournalArticle> filterFindByG_C_S_L(
		long groupId, long classNameId, String[] ddmStructureKeys,
		Locale locale, QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_C_S_L(
			groupId, classNameId, ddmStructureKeys, locale, queryDefinition,
			true);
	}

	@Override
	public List<JournalArticle> filterFindByG_F_C_L(
		long groupId, List<Long> folderIds, long classNameId, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_F_C_L(
			groupId, folderIds, classNameId, locale, queryDefinition, true);
	}

	@Override
	public List<JournalArticle> findByExpirationDate(
		long classNameId, Date expirationDateLT,
		QueryDefinition<JournalArticle> queryDefinition) {

		Timestamp expirationDateLT_TS = CalendarUtil.getTimestamp(
			expirationDateLT);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_EXPIRATION_DATE, queryDefinition);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);
			queryPos.add(queryDefinition.getStatus());
			queryPos.add(expirationDateLT_TS);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<JournalArticle> findByNoAssets() {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_NO_ASSETS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("JournalArticle", JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(_portal.getClassNameId(JournalArticle.class));

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<JournalArticle> findByNoPermissions() {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_NO_PERMISSIONS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("JournalArticle", JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(ResourceConstants.SCOPE_INDIVIDUAL);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<JournalArticle> findByReviewDate(
		long classNameId, Date reviewDateLT, Date reviewDateGT) {

		Timestamp reviewDateLT_TS = CalendarUtil.getTimestamp(reviewDateLT);
		Timestamp reviewDateGT_TS = CalendarUtil.getTimestamp(reviewDateGT);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_REVIEW_DATE);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);
			queryPos.add(reviewDateGT_TS);
			queryPos.add(reviewDateLT_TS);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public JournalArticle findByR_D(long resourcePrimKey, Date displayDate)
		throws NoSuchArticleException {

		Timestamp displayDate_TS = CalendarUtil.getTimestamp(displayDate);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_R_D);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(resourcePrimKey);
			queryPos.add(displayDate_TS);

			List<JournalArticle> articles = sqlQuery.list();

			if (!articles.isEmpty()) {
				return articles.get(0);
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}

		StringBundler sb = new StringBundler(5);

		sb.append("No JournalArticle exists with the key {resourcePrimKey=");
		sb.append(resourcePrimKey);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append("}");

		throw new NoSuchArticleException(sb.toString());
	}

	@Override
	public List<JournalArticle> findByG_ST(
		long groupId, int status,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_ST(groupId, status, queryDefinition, false);
	}

	@Override
	public List<JournalArticle> findByG_ST_L(
		long groupId, int status, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_ST_L(groupId, status, locale, queryDefinition, false);
	}

	@Override
	public List<JournalArticle> findByG_F_L(
		long groupId, List<Long> folderIds, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_F_L(
			groupId, folderIds, locale, queryDefinition, false);
	}

	@Override
	public List<JournalArticle> findByG_C_S(
		long groupId, long classNameId, String ddmStructureKey,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_C_S(
			groupId, classNameId, new String[] {ddmStructureKey},
			queryDefinition, false);
	}

	@Override
	public List<JournalArticle> findByG_C_S(
		long groupId, long classNameId, String[] ddmStructureKeys,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_C_S(
			groupId, classNameId, ddmStructureKeys, queryDefinition, false);
	}

	@Override
	public List<JournalArticle> findByG_F_C(
		long groupId, List<Long> folderIds, long classNameId,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_F_C(
			groupId, folderIds, classNameId, queryDefinition, false);
	}

	@Override
	public List<JournalArticle> findByG_C_S_L(
		long groupId, long classNameId, String ddmStructureKey, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_C_S_L(
			groupId, classNameId, new String[] {ddmStructureKey}, locale,
			queryDefinition, false);
	}

	@Override
	public List<JournalArticle> findByG_C_S_L(
		long groupId, long classNameId, String[] ddmStructureKeys,
		Locale locale, QueryDefinition<JournalArticle> queryDefinition) {

		return doFindByG_C_S_L(
			groupId, classNameId, ddmStructureKeys, locale, queryDefinition,
			false);
	}

	protected int doCountByG_F(
		long groupId, List<Long> folderIds,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_G_F, queryDefinition, "JournalArticle");

			sql = replaceStatusJoin(sql, queryDefinition);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			sql = StringUtil.replace(
				sql, "[$FOLDER_ID$]",
				getFolderIds(folderIds, JournalArticleImpl.TABLE_NAME));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(queryDefinition.getStatus());

			for (Long folderId : folderIds) {
				queryPos.add(folderId);
			}

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByG_ST(
		long groupId, int status,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_G_ST, queryDefinition, "JournalArticle");

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(status);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByG_C_S(
		long groupId, long classNameId, String[] ddmStructureKeys,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_G_C_S, queryDefinition, "JournalArticle");

			sql = replaceStatusJoin(sql, queryDefinition);

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(JournalArticle.groupId = ?) AND");
			}

			sql = StringUtil.replace(
				sql, "[$DDM_STRUCTURE_KEY$]",
				getDDMStructureKeys(
					ddmStructureKeys, JournalArticleImpl.TABLE_NAME));

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (groupId > 0) {
				queryPos.add(groupId);
			}

			queryPos.add(classNameId);
			queryPos.add(ddmStructureKeys);
			queryPos.add(queryDefinition.getStatus());

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByG_F_C(
		long groupId, List<Long> folderIds, long classNameId,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_G_U_F_C, queryDefinition,
				"JournalArticle");

			sql = replaceStatusJoin(sql, queryDefinition);

			if (folderIds.isEmpty()) {
				sql = StringUtil.removeSubstring(sql, "([$FOLDER_ID$]) AND");
			}
			else {
				sql = StringUtil.replace(
					sql, "[$FOLDER_ID$]",
					getFolderIds(folderIds, JournalArticleImpl.TABLE_NAME));
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(classNameId);

			for (long folderId : folderIds) {
				queryPos.add(folderId);
			}

			if (queryDefinition.getOwnerUserId() > 0) {
				queryPos.add(queryDefinition.getOwnerUserId());

				if (queryDefinition.isIncludeOwner()) {
					queryPos.add(WorkflowConstants.STATUS_IN_TRASH);
				}
			}

			queryPos.add(queryDefinition.getStatus());

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<JournalArticle> doFindByG_F(
		long groupId, List<Long> folderIds,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_F, queryDefinition, "JournalArticle");

			sql = replaceStatusJoin(sql, queryDefinition);

			OrderByComparator<JournalArticle> orderByComparator =
				queryDefinition.getOrderByComparator();

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			sql = StringUtil.replace(
				sql, "[$FOLDER_ID$]",
				getFolderIds(folderIds, JournalArticleImpl.TABLE_NAME));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (_isOrderByTitle(orderByComparator)) {
				queryPos.add(1);
			}
			else {
				queryPos.add(0);
			}

			queryPos.add(groupId);
			queryPos.add(queryDefinition.getStatus());

			for (Long folderId : folderIds) {
				queryPos.add(folderId);
			}

			return (List<JournalArticle>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<JournalArticle> doFindByG_ST(
		long groupId, int status,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_ST, queryDefinition, "JournalArticle");

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<JournalArticle> doFindByG_ST_L(
		long groupId, int status, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_ST_L, queryDefinition, "JournalArticle");

			OrderByComparator<JournalArticle> orderByComparator =
				queryDefinition.getOrderByComparator();

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (_isOrderByTitle(orderByComparator)) {
				queryPos.add(1);
			}
			else {
				queryPos.add(0);
			}

			queryPos.add(LocaleUtil.toLanguageId(locale));
			queryPos.add(groupId);
			queryPos.add(status);

			return (List<JournalArticle>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<JournalArticle> doFindByG_F_L(
		long groupId, List<Long> folderIds, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_F_L, queryDefinition, "JournalArticle");

			sql = replaceStatusJoin(sql, queryDefinition);

			OrderByComparator<JournalArticle> orderByComparator =
				queryDefinition.getOrderByComparator();

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			sql = StringUtil.replace(
				sql, "[$FOLDER_ID$]",
				getFolderIds(folderIds, JournalArticleImpl.TABLE_NAME));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (_isOrderByTitle(orderByComparator)) {
				queryPos.add(1);
			}
			else {
				queryPos.add(0);
			}

			queryPos.add(LocaleUtil.toLanguageId(locale));

			queryPos.add(groupId);
			queryPos.add(queryDefinition.getStatus());

			for (Long folderId : folderIds) {
				queryPos.add(folderId);
			}

			return (List<JournalArticle>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<JournalArticle> doFindByG_C_S(
		long groupId, long classNameId, String[] ddmStructureKeys,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_C_S, queryDefinition, "JournalArticle");

			sql = replaceStatusJoin(sql, queryDefinition);

			OrderByComparator<JournalArticle> orderByComparator =
				queryDefinition.getOrderByComparator();

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(JournalArticle.groupId = ?) AND");
			}

			sql = StringUtil.replace(
				sql, "[$DDM_STRUCTURE_KEY$]",
				getDDMStructureKeys(
					ddmStructureKeys, JournalArticleImpl.TABLE_NAME));

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (_isOrderByTitle(orderByComparator)) {
				queryPos.add(1);
			}
			else {
				queryPos.add(0);
			}

			if (groupId > 0) {
				queryPos.add(groupId);
			}

			queryPos.add(classNameId);
			queryPos.add(ddmStructureKeys);
			queryPos.add(queryDefinition.getStatus());

			return (List<JournalArticle>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<JournalArticle> doFindByG_F_C(
		long groupId, List<Long> folderIds, long classNameId,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_U_F_C, queryDefinition, "JournalArticle");

			sql = replaceStatusJoin(sql, queryDefinition);

			OrderByComparator<JournalArticle> orderByComparator =
				queryDefinition.getOrderByComparator();

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			if (folderIds.isEmpty()) {
				sql = StringUtil.removeSubstring(sql, "([$FOLDER_ID$]) AND");
			}
			else {
				sql = StringUtil.replace(
					sql, "[$FOLDER_ID$]",
					getFolderIds(folderIds, JournalArticleImpl.TABLE_NAME));
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (_isOrderByTitle(orderByComparator)) {
				queryPos.add(1);
			}
			else {
				queryPos.add(0);
			}

			queryPos.add(groupId);
			queryPos.add(classNameId);

			if (queryDefinition.getOwnerUserId() > 0) {
				queryPos.add(queryDefinition.getOwnerUserId());

				if (queryDefinition.isIncludeOwner()) {
					queryPos.add(WorkflowConstants.STATUS_IN_TRASH);
				}
			}

			for (long folderId : folderIds) {
				queryPos.add(folderId);
			}

			queryPos.add(queryDefinition.getStatus());

			return (List<JournalArticle>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<JournalArticle> doFindByG_C_S_L(
		long groupId, long classNameId, String[] ddmStructureKeys,
		Locale locale, QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_C_S_L, queryDefinition, "JournalArticle");

			sql = replaceStatusJoin(sql, queryDefinition);

			OrderByComparator<JournalArticle> orderByComparator =
				queryDefinition.getOrderByComparator();

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(JournalArticle.groupId = ?) AND");
			}

			sql = StringUtil.replace(
				sql, "[$DDM_STRUCTURE_KEY$]",
				getDDMStructureKeys(
					ddmStructureKeys, JournalArticleImpl.TABLE_NAME));

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (_isOrderByTitle(orderByComparator)) {
				queryPos.add(1);
			}
			else {
				queryPos.add(0);
			}

			queryPos.add(LocaleUtil.toLanguageId(locale));

			if (groupId > 0) {
				queryPos.add(groupId);
			}

			queryPos.add(classNameId);
			queryPos.add(ddmStructureKeys);
			queryPos.add(queryDefinition.getStatus());

			return (List<JournalArticle>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<JournalArticle> doFindByG_F_C_L(
		long groupId, List<Long> folderIds, long classNameId, Locale locale,
		QueryDefinition<JournalArticle> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_U_F_C_L, queryDefinition,
				"JournalArticle");

			sql = replaceStatusJoin(sql, queryDefinition);

			OrderByComparator<JournalArticle> orderByComparator =
				queryDefinition.getOrderByComparator();

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			if (folderIds.isEmpty()) {
				sql = StringUtil.removeSubstring(sql, "([$FOLDER_ID$]) AND");
			}
			else {
				sql = StringUtil.replace(
					sql, "[$FOLDER_ID$]",
					getFolderIds(folderIds, JournalArticleImpl.TABLE_NAME));
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				JournalArticleImpl.TABLE_NAME, JournalArticleImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (_isOrderByTitle(orderByComparator)) {
				queryPos.add(1);
			}
			else {
				queryPos.add(0);
			}

			queryPos.add(LocaleUtil.toLanguageId(locale));
			queryPos.add(groupId);
			queryPos.add(classNameId);

			for (long folderId : folderIds) {
				queryPos.add(folderId);
			}

			if (queryDefinition.getOwnerUserId() > 0) {
				queryPos.add(queryDefinition.getOwnerUserId());

				if (queryDefinition.isIncludeOwner()) {
					queryPos.add(WorkflowConstants.STATUS_IN_TRASH);
				}
			}

			queryPos.add(queryDefinition.getStatus());

			return (List<JournalArticle>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getDDMStructureKeys(
		String[] ddmStructureKeys, String tableName) {

		if (ArrayUtil.isEmpty(ddmStructureKeys)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((ddmStructureKeys.length * 3) + 1);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < ddmStructureKeys.length; i++) {
			sb.append(tableName);
			sb.append(".DDMStructureKey = ? ");
			sb.append(WHERE_OR);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected String getFolderIds(List<Long> folderIds, String tableName) {
		if (folderIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((folderIds.size() * 3) + 1);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < folderIds.size(); i++) {
			sb.append(tableName);
			sb.append(".folderId = ? ");

			if ((i + 1) != folderIds.size()) {
				sb.append(WHERE_OR);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected JournalArticle getLatestArticle(
		long groupId, String articleId, int status) {

		List<JournalArticle> articles = null;

		if (status == WorkflowConstants.STATUS_ANY) {
			articles = JournalArticleUtil.findByG_A(groupId, articleId, 0, 1);
		}
		else {
			articles = JournalArticleUtil.findByG_A_ST(
				groupId, articleId, status, 0, 1);
		}

		if (articles.isEmpty()) {
			return null;
		}

		return articles.get(0);
	}

	protected boolean isdatabaseContentKeywordSearchEnabled(long companyId) {
		JournalServiceConfiguration journalServiceConfiguration = null;

		try {
			journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class, companyId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		if (journalServiceConfiguration == null) {
			return false;
		}

		return journalServiceConfiguration.
			databaseContentKeywordSearchEnabled();
	}

	protected boolean isNullArray(Object[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return true;
		}

		for (Object object : array) {
			if (Validator.isNotNull(object)) {
				return false;
			}
		}

		return true;
	}

	protected String replaceStatusJoin(
		String sql, QueryDefinition<JournalArticle> queryDefinition) {

		if (queryDefinition.getStatus() == WorkflowConstants.STATUS_ANY) {
			return StringUtil.removeSubstring(sql, "[$STATUS_JOIN$] AND");
		}

		if (queryDefinition.isExcludeStatus()) {
			StringBundler sb = new StringBundler(5);

			sb.append("(JournalArticle.status != ");
			sb.append(queryDefinition.getStatus());
			sb.append(") AND (tempJournalArticle.status != ");
			sb.append(queryDefinition.getStatus());
			sb.append(")");

			sql = StringUtil.replace(sql, "[$STATUS_JOIN$]", sb.toString());
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append("(JournalArticle.status = ");
			sb.append(queryDefinition.getStatus());
			sb.append(") AND (tempJournalArticle.status = ");
			sb.append(queryDefinition.getStatus());
			sb.append(")");

			sql = StringUtil.replace(sql, "[$STATUS_JOIN$]", sb.toString());
		}

		return sql;
	}

	protected String replaceStructureTemplate(
		String sql, String[] ddmStructureKeys, String[] ddmTemplateKeys) {

		if (isNullArray(ddmStructureKeys) && isNullArray(ddmTemplateKeys)) {
			return StringUtil.removeSubstring(
				sql, "([$STRUCTURE_TEMPLATE$]) AND");
		}

		StringBundler sb = new StringBundler();

		if (!isNullArray(ddmStructureKeys)) {
			sb.append("(");

			for (int i = 0; i < ddmStructureKeys.length; i++) {
				sb.append(_DDM_STRUCTURE_KEY_SQL);
				sb.append("OR ");
			}

			sb.setIndex(sb.index() - 1);

			sb.append(")");
		}

		if (!isNullArray(ddmTemplateKeys)) {
			if (!isNullArray(ddmStructureKeys)) {
				sb.append(_AND_OR_CONNECTOR);
			}

			sb.append("(");

			for (int i = 0; i < ddmTemplateKeys.length; i++) {
				sb.append(_DDM_TEMPLATE_KEY_SQL);
				sb.append("OR ");
			}

			sb.setIndex(sb.index() - 1);

			sb.append(")");
		}

		return StringUtil.replace(sql, "[$STRUCTURE_TEMPLATE$]", sb.toString());
	}

	private boolean _isOrderByTitle(
		OrderByComparator<JournalArticle> orderByComparator) {

		if ((orderByComparator != null) &&
			(StringUtil.containsIgnoreCase(
				orderByComparator.getOrderBy(), _TITLE_FIELD,
				StringPool.COMMA) ||
			 StringUtil.containsIgnoreCase(
				 orderByComparator.getOrderBy(), _TITLE_FIELD + " ASC",
				 StringPool.COMMA) ||
			 StringUtil.containsIgnoreCase(
				 orderByComparator.getOrderBy(), _TITLE_FIELD + " DESC",
				 StringPool.COMMA))) {

			return true;
		}

		return false;
	}

	private static final String _AND_OR_CONNECTOR = "[$AND_OR_CONNECTOR$] ";

	private static final String _DDM_STRUCTURE_KEY_SQL =
		"(JournalArticle.DDMStructureKey LIKE ? [$AND_OR_NULL_CHECK$]) ";

	private static final String _DDM_TEMPLATE_KEY_SQL =
		"(JournalArticle.DDMTemplateKey LIKE ? [$AND_OR_NULL_CHECK$]) ";

	private static final String _TITLE_FIELD =
		"JournalArticleLocalization.title";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleFinderImpl.class);

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private Portal _portal;

}