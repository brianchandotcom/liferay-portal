/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.osb.faro.model.impl.FaroChannelImpl;
import com.liferay.osb.faro.service.persistence.FaroChannelFinder;
import com.liferay.osb.faro.util.FaroPermissionChecker;
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
 * @author André Miranda
 */
@Component(service = FaroChannelFinder.class)
public class FaroChannelFinderImpl
	extends FaroChannelFinderBaseImpl implements FaroChannelFinder {

	public static final String COUNT_BY_G_N_U_PT =
		FaroChannelFinder.class.getName() + ".countByG_N_U_PT";

	public static final String FIND_BY_G_N_U_PT =
		FaroChannelFinder.class.getName() + ".findByG_N_U_PT";

	@Override
	public int countByKeywords(
		long groupId, int permissionType, String query, long userId) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_N_U_PT);

			String[] keywordsArray = _customSQL.keywords(query);

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(OSBFaro_FaroChannel.name)", StringPool.LIKE, true,
				keywordsArray);

			sql = _customSQL.replaceAndOperator(sql, Validator.isNull(query));

			boolean admin = FaroPermissionChecker.isGroupAdmin(groupId);

			if (admin) {
				sql = StringUtil.removeSubstring(
					sql, _PERMISSION_CHECK_JOIN_SQL);
				sql = StringUtil.removeSubstring(sql, _PERMISSION_CHECK_SQL);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(keywordsArray, 2);

			if (!admin) {
				queryPos.add(userId);
				queryPos.add(permissionType);
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

	@Override
	public List<FaroChannel> findByKeywords(
		long groupId, int permissionType, String query, long userId, int start,
		int end, OrderByComparator<FaroChannel> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_N_U_PT);

			String[] keywordsArray = _customSQL.keywords(query);

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(OSBFaro_FaroChannel.name)", StringPool.LIKE, true,
				keywordsArray);

			sql = StringUtil.replace(
				sql, "[$ORDER_BY$]", _getOrderBy(orderByComparator));

			sql = _customSQL.replaceAndOperator(sql, Validator.isNull(query));

			boolean admin = FaroPermissionChecker.isGroupAdmin(groupId);

			if (admin) {
				sql = StringUtil.removeSubstring(
					sql, _PERMISSION_CHECK_JOIN_SQL);
				sql = StringUtil.removeSubstring(sql, _PERMISSION_CHECK_SQL);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("OSBFaro_FaroChannel", FaroChannelImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(keywordsArray, 2);

			if (!admin) {
				queryPos.add(userId);
				queryPos.add(permissionType);
			}

			return (List<FaroChannel>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private String _getOrderBy(
		OrderByComparator<FaroChannel> orderByComparator) {

		if (orderByComparator == null) {
			return StringPool.BLANK;
		}

		return ORDER_BY_CLAUSE + orderByComparator.getOrderBy();
	}

	private static final String _PERMISSION_CHECK_JOIN_SQL =
		"LEFT JOIN UserGroupRole ON OSBFaro_FaroChannel.groupId = " +
			"UserGroupRole.groupId";

	private static final String _PERMISSION_CHECK_SQL =
		"AND ((UserGroupRole.userId = ?) OR " +
			"(OSBFaro_FaroChannel.permissionType = ?))";

	@Reference
	private CustomSQL _customSQL;

}