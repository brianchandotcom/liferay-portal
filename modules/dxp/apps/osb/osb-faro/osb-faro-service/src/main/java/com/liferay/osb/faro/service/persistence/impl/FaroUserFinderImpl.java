/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.model.impl.FaroUserImpl;
import com.liferay.osb.faro.service.persistence.FaroUserFinder;
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
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Matthew Kong
 */
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

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(channelGroupId);
			qPos.add(workspaceGroupId);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

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

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

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

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("OSBFaro_FaroUser", FaroUserImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(channelGroupId);
			qPos.add(workspaceGroupId);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);

			return (List<FaroUser>)QueryUtil.list(q, getDialect(), start, end);
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

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("OSBFaro_FaroUser", FaroUserImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);
			qPos.add(keywordsArray, 2);

			return (List<FaroUser>)QueryUtil.list(q, getDialect(), start, end);
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

		Stream<Integer> stream = statuses.stream();

		return stream.map(
			Object::toString
		).collect(
			Collectors.joining(StringPool.COMMA)
		);
	}

	private static final String _AVAILABLE_FARO_USER_SQL =
		"Users_Groups.groupId IS NULL";

	private static final String _FARO_USER_SQL =
		"Users_Groups.groupId IS NOT NULL";

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}