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

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.model.impl.FragmentCompositionImpl;
import com.liferay.fragment.model.impl.FragmentEntryImpl;
import com.liferay.fragment.service.persistence.FragmentCompositionUtil;
import com.liferay.fragment.service.persistence.FragmentEntryFinder;
import com.liferay.fragment.service.persistence.FragmentEntryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = FragmentEntryFinder.class)
public class FragmentEntryFinderImpl
	extends FragmentEntryLinkFinderBaseImpl implements FragmentEntryFinder {

	public static final String COUNT_FC_BY_G_FCI =
		FragmentEntryFinder.class.getName() + ".countFC_ByG_FCI";

	public static final String COUNT_FC_BY_G_FCI_N =
		FragmentEntryFinder.class.getName() + ".countFC_ByG_FCI_N";

	public static final String COUNT_FE_BY_G_FCI =
		FragmentEntryFinder.class.getName() + ".countFE_ByG_FCI";

	public static final String COUNT_FE_BY_G_FCI_N =
		FragmentEntryFinder.class.getName() + ".countFE_ByG_FCI_N";

	public static final String FIND_FC_BY_G_FCI =
		FragmentEntryFinder.class.getName() + ".findFC_ByG_FCI";

	public static final String FIND_FC_BY_G_FCI_N =
		FragmentEntryFinder.class.getName() + ".findFC_ByG_FCI_N";

	public static final String FIND_FE_BY_G_FCI =
		FragmentEntryFinder.class.getName() + ".findFE_ByG_FCI";

	public static final String FIND_FE_BY_G_FCI_N =
		FragmentEntryFinder.class.getName() + ".findFE_ByG_FCI_N";

	@Override
	public int countFC_FE_ByG_FCI(
		long groupId, long fragmentCollectionId,
		QueryDefinition<?> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				_customSQL.get(
					getClass(), COUNT_FC_BY_G_FCI, queryDefinition,
					FragmentCompositionImpl.TABLE_NAME));
			sb.append(") UNION ALL (");
			sb.append(
				_customSQL.get(
					getClass(), COUNT_FE_BY_G_FCI, queryDefinition,
					FragmentEntryImpl.TABLE_NAME));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String sql = sb.toString();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(fragmentCollectionId);
			queryPos.add(queryDefinition.getStatus());

			queryPos.add(groupId);
			queryPos.add(fragmentCollectionId);
			queryPos.add(queryDefinition.getStatus());

			int count = 0;

			Iterator<Long> itr = sqlQuery.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countFC_FE_ByG_FCI_N(
		long groupId, long fragmentCollectionId, String name,
		QueryDefinition<?> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				_customSQL.get(
					getClass(), COUNT_FC_BY_G_FCI_N, queryDefinition,
					FragmentCompositionImpl.TABLE_NAME));
			sb.append(") UNION ALL (");
			sb.append(
				_customSQL.get(
					getClass(), COUNT_FE_BY_G_FCI_N, queryDefinition,
					FragmentEntryImpl.TABLE_NAME));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String sql = sb.toString();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			name = _customSQL.keywords(name, false, WildcardMode.SURROUND)[0];

			queryPos.add(groupId);
			queryPos.add(fragmentCollectionId);
			queryPos.add(name);
			queryPos.add(queryDefinition.getStatus());

			queryPos.add(groupId);
			queryPos.add(fragmentCollectionId);
			queryPos.add(name);
			queryPos.add(queryDefinition.getStatus());

			int count = 0;

			Iterator<Long> itr = sqlQuery.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Object> findFC_FE_ByG_FCI(
		long groupId, long fragmentCollectionId,
		QueryDefinition<?> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				_customSQL.get(
					getClass(), FIND_FC_BY_G_FCI, queryDefinition,
					FragmentCompositionImpl.TABLE_NAME));
			sb.append(") UNION ALL (");
			sb.append(
				_customSQL.get(
					getClass(), FIND_FE_BY_G_FCI, queryDefinition,
					FragmentEntryImpl.TABLE_NAME));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String sql = sb.toString();

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("fragmentCompositionId", Type.LONG);
			sqlQuery.addScalar("fragmentEntryId", Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(fragmentCollectionId);
			queryPos.add(queryDefinition.getStatus());

			queryPos.add(groupId);
			queryPos.add(fragmentCollectionId);
			queryPos.add(queryDefinition.getStatus());

			List<Object> models = new ArrayList<>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long fragmentCompositionId = (Long)array[0];
				long fragmentEntryId = (Long)array[1];

				Object obj = null;

				if (fragmentCompositionId > 0) {
					obj = FragmentCompositionUtil.findByPrimaryKey(
						fragmentCompositionId);
				}
				else {
					obj = FragmentEntryUtil.findByPrimaryKey(fragmentEntryId);
				}

				models.add(obj);
			}

			return models;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Object> findFC_FE_ByG_FCI_N(
		long groupId, long fragmentCollectionId, String name,
		QueryDefinition<?> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(
				_customSQL.get(
					getClass(), FIND_FC_BY_G_FCI_N, queryDefinition,
					FragmentCompositionImpl.TABLE_NAME));
			sb.append(") UNION ALL (");
			sb.append(
				_customSQL.get(
					getClass(), FIND_FE_BY_G_FCI_N, queryDefinition,
					FragmentEntryImpl.TABLE_NAME));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String sql = sb.toString();

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("fragmentCompositionId", Type.LONG);
			sqlQuery.addScalar("fragmentEntryId", Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			name = _customSQL.keywords(name, false, WildcardMode.SURROUND)[0];

			queryPos.add(groupId);
			queryPos.add(fragmentCollectionId);
			queryPos.add(name);
			queryPos.add(queryDefinition.getStatus());

			queryPos.add(groupId);
			queryPos.add(fragmentCollectionId);
			queryPos.add(name);
			queryPos.add(queryDefinition.getStatus());

			List<Object> models = new ArrayList<>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long fragmentCompositionId = (Long)array[0];
				long fragmentEntryId = (Long)array[1];

				Object obj = null;

				if (fragmentCompositionId > 0) {
					obj = FragmentCompositionUtil.findByPrimaryKey(
						fragmentCompositionId);
				}
				else {
					obj = FragmentEntryUtil.findByPrimaryKey(fragmentEntryId);
				}

				models.add(obj);
			}

			return models;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}