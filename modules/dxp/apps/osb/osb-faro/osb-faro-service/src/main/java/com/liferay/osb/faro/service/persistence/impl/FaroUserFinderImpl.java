/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.model.impl.FaroUserImpl;
import com.liferay.osb.faro.service.persistence.FaroUserFinder;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(service = FaroUserFinder.class)
public class FaroUserFinderImpl
	extends FaroUserFinderBaseImpl implements FaroUserFinder {

	public static final String COUNT_BY_CHANNEL_G_EA_EA_FN_LN =
		FaroUserFinder.class.getName() + ".countByChannelG_EA_EA_FN_LN";

	public static final String COUNT_BY_G_EA_EA_FN_LN_S =
		FaroUserFinder.class.getName() + ".countByG_EA_EA_FN_LN_S";

	public static final String FIND_BY_CHANNEL_G_EA_EA_FN_LN =
		FaroUserFinder.class.getName() + ".findByChannelG_EA_EA_FN_LN";

	public static final String FIND_BY_G_EA_EA_FN_LN_S =
		FaroUserFinder.class.getName() + ".findByG_EA_EA_FN_LN_S";

	@Override
	public int countByChannelKeywords(
		long channelGroupId, boolean available, String query,
		List<Integer> statuses, long workspaceGroupId) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_CHANNEL_G_EA_EA_FN_LN);

			sql = StringUtil.replace(
				sql, "[$STATUSES$]", getStatuses(statuses));

			String[] keywordsArray = _customSQL.keywords(query);

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(OSBFaro_FaroUser.emailAddress)", StringPool.LIKE,
				false, keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.emailAddress)", StringPool.LIKE, false,
				keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.firstName)", StringPool.LIKE, false,
				keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.lastName)", StringPool.LIKE, true,
				keywordsArray);

			sql = _customSQL.replaceAndOperator(sql, Validator.isNull(query));

			if (available) {
				sql = StringUtil.replace(
					sql, _FARO_USER_SQL, _AVAILABLE_FARO_USER_SQL);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(channelGroupId);
			queryPos.add(workspaceGroupId);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);

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

	@Override
	public int countByKeywords(
		long groupId, String query, List<Integer> statuses) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_EA_EA_FN_LN_S);

			sql = StringUtil.replace(
				sql, "[$STATUSES$]", getStatuses(statuses));

			String[] keywordsArray = _customSQL.keywords(query);

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(OSBFaro_FaroUser.emailAddress)", StringPool.LIKE,
				false, keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.emailAddress)", StringPool.LIKE, false,
				keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.firstName)", StringPool.LIKE, false,
				keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.lastName)", StringPool.LIKE, true,
				keywordsArray);

			sql = _customSQL.replaceAndOperator(sql, Validator.isNull(query));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);

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

	@Override
	public List<FaroUser> findByChannelKeywords(
		long channelGroupId, boolean available, String query,
		List<Integer> statuses, long workspaceGroupId, int start, int end,
		OrderByComparator<FaroUser> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_CHANNEL_G_EA_EA_FN_LN);

			sql = StringUtil.replace(
				sql, "[$STATUSES$]", getStatuses(statuses));

			String[] keywordsArray = _customSQL.keywords(query);

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(OSBFaro_FaroUser.emailAddress)", StringPool.LIKE,
				false, keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.emailAddress)", StringPool.LIKE, false,
				keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.firstName)", StringPool.LIKE, false,
				keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.lastName)", StringPool.LIKE, true,
				keywordsArray);

			sql = StringUtil.replace(
				sql, "[$ORDER_BY$]", getOrderBy(orderByComparator));

			sql = _customSQL.replaceAndOperator(sql, Validator.isNull(query));

			if (available) {
				sql = StringUtil.replace(
					sql, _FARO_USER_SQL, _AVAILABLE_FARO_USER_SQL);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("OSBFaro_FaroUser", FaroUserImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(channelGroupId);
			queryPos.add(workspaceGroupId);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);

			return (List<FaroUser>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<FaroUser> findByKeywords(
		long groupId, String query, List<Integer> statuses, int start, int end,
		OrderByComparator<FaroUser> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_EA_EA_FN_LN_S);

			sql = StringUtil.replace(
				sql, "[$STATUSES$]", getStatuses(statuses));

			String[] keywordsArray = _customSQL.keywords(query);

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(OSBFaro_FaroUser.emailAddress)", StringPool.LIKE,
				false, keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.emailAddress)", StringPool.LIKE, false,
				keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.firstName)", StringPool.LIKE, false,
				keywordsArray);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.lastName)", StringPool.LIKE, true,
				keywordsArray);

			sql = StringUtil.replace(
				sql, "[$ORDER_BY$]", getOrderBy(orderByComparator));

			sql = _customSQL.replaceAndOperator(sql, Validator.isNull(query));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("OSBFaro_FaroUser", FaroUserImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);
			queryPos.add(keywordsArray, 2);

			return (List<FaroUser>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getOrderBy(OrderByComparator<FaroUser> orderByComparator) {
		if (orderByComparator == null) {
			return StringPool.BLANK;
		}

		return ORDER_BY_CLAUSE + orderByComparator.getOrderBy();
	}

	protected String getStatuses(List<Integer> statuses) {
		if (statuses.isEmpty()) {
			return StringPool.BLANK;
		}

		return StringUtil.merge(
			TransformUtil.transform(statuses, String::valueOf), ", ");
	}

	private static final String _AVAILABLE_FARO_USER_SQL =
		"Users_Groups.groupId IS NULL";

	private static final String _FARO_USER_SQL =
		"Users_Groups.groupId IS NOT NULL";

	@Reference
	private CustomSQL _customSQL;

}