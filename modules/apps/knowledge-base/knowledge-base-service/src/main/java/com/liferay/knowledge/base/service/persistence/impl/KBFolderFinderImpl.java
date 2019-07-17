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

package com.liferay.knowledge.base.service.persistence.impl;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.persistence.KBArticlePersistence;
import com.liferay.knowledge.base.service.persistence.KBFolderFinder;
import com.liferay.knowledge.base.service.persistence.KBFolderPersistence;
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
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = KBFolderFinder.class)
public class KBFolderFinderImpl
	extends KBFolderFinderBaseImpl implements KBFolderFinder {

	public static final String COUNT_A_BY_G_P =
		KBFolderFinder.class.getName() + ".countA_ByG_P";

	public static final String COUNT_F_BY_G_P =
		KBFolderFinder.class.getName() + ".countF_ByG_P";

	public static final String FIND_A_BY_G_P =
		KBFolderFinder.class.getName() + ".findA_ByG_P";

	public static final String FIND_F_BY_G_P =
		KBFolderFinder.class.getName() + ".findF_ByG_P";

	@Override
	public int countF_A_ByG_P(
		long groupId, long parentResourcePrimKey,
		QueryDefinition<?> queryDefinition) {

		return doCountF_A_ByG_P(
			groupId, parentResourcePrimKey, queryDefinition, false);
	}

	@Override
	public int filterCountF_A_ByG_P(
		long groupId, long parentResourcePrimKey,
		QueryDefinition<?> queryDefinition) {

		return doCountF_A_ByG_P(
			groupId, parentResourcePrimKey, queryDefinition, true);
	}

	@Override
	public List<Object> filterFindF_A_ByG_P(
		long groupId, long parentResourcePrimKey,
		QueryDefinition<?> queryDefinition) {

		return doFindF_A_ByG_P(
			groupId, parentResourcePrimKey, queryDefinition, true);
	}

	@Override
	public List<Object> findF_A_ByG_P(
		long groupId, long parentResourcePrimKey,
		QueryDefinition<?> queryDefinition) {

		return doFindF_A_ByG_P(
			groupId, parentResourcePrimKey, queryDefinition, false);
	}

	protected int doCountF_A_ByG_P(
		long groupId, long parentResourcePrimKey,
		QueryDefinition<?> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);

			String sql = _customSQL.get(
				getClass(), COUNT_A_BY_G_P, queryDefinition);

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, KBArticle.class.getName(), "KBArticle.kbArticleId",
					groupId);
			}

			sb.append(sql);
			sb.append(") UNION ALL (");

			sql = _customSQL.get(getClass(), COUNT_F_BY_G_P, queryDefinition);

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, KBFolder.class.getName(), "KBFolder.kbFolderId",
					groupId);
			}

			sb.append(sql);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			SQLQuery q = session.createSynchronizedSQLQuery(sb.toString());

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(parentResourcePrimKey);
			qPos.add(true);
			qPos.add(queryDefinition.getStatus());
			qPos.add(groupId);
			qPos.add(parentResourcePrimKey);

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

	protected List<Object> doFindF_A_ByG_P(
		long groupId, long parentResourcePrimKey,
		QueryDefinition<?> queryDefinition, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append("SELECT * FROM (");

			String sql = _customSQL.get(
				getClass(), FIND_A_BY_G_P, queryDefinition);

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, KBArticle.class.getName(), "KBArticle.kbArticleId",
					groupId);
			}

			sb.append(sql);
			sb.append(" UNION ALL ");

			sql = _customSQL.get(getClass(), FIND_F_BY_G_P, queryDefinition);

			if (inlineSQLHelper) {
				sql = _inlineSQLHelper.replacePermissionCheck(
					sql, KBFolder.class.getName(), "KBFolder.kbFolderId",
					groupId);
			}

			sb.append(sql);
			sb.append(") TEMP_TABLE ORDER BY modelFolder DESC");

			sql = sb.toString();

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("modelId", Type.LONG);
			q.addScalar("modelFolder", Type.LONG);
			q.addScalar("modifiedDate", Type.DATE);
			q.addScalar("priority", Type.DOUBLE);
			q.addScalar("title", Type.STRING);
			q.addScalar("viewCount", Type.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(parentResourcePrimKey);
			qPos.add(true);
			qPos.add(queryDefinition.getStatus());
			qPos.add(groupId);
			qPos.add(parentResourcePrimKey);

			List<Object> models = new ArrayList<>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long modelId = (Long)array[0];
				long modelFolder = (Long)array[1];

				Object obj = null;

				if (modelFolder == 1) {
					obj = _kBFolderPersistence.findByPrimaryKey(modelId);
				}
				else {
					obj = _kBArticlePersistence.findByPrimaryKey(modelId);
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

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

	@Reference
	private KBArticlePersistence _kBArticlePersistence;

	@Reference
	private KBFolderPersistence _kBFolderPersistence;

}