/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.persistence.impl;

import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.impl.JournalFeedImpl;
import com.liferay.journal.service.persistence.JournalFeedFinder;
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
 * @author Raymond Augé
 * @author Connor McKay
 */
@Component(service = JournalFeedFinder.class)
public class JournalFeedFinderImpl
	extends JournalFeedFinderBaseImpl implements JournalFeedFinder {

	public static final String COUNT_BY_C_G_F_N_D =
		JournalFeedFinder.class.getName() + ".countByC_G_F_N_D";

	public static final String FIND_BY_C_G_F_N_D =
		JournalFeedFinder.class.getName() + ".findByC_G_F_N_D";

	@Override
	public int countByKeywords(long companyId, long groupId, String keywords) {
		String[] feedIds = null;
		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			feedIds = _customSQL.keywords(keywords, false);
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return countByC_G_F_N_D(
			companyId, groupId, feedIds, names, descriptions, andOperator);
	}

	@Override
	public int countByC_G_F_N_D(
		long companyId, long groupId, String[] feedIds, String[] names,
		String[] descriptions, boolean andOperator) {

		feedIds = _customSQL.keywords(feedIds, false);
		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_G_F_N_D);

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(sql, "(groupId = ?) AND");
			}

			sql = _customSQL.replaceKeywords(
				sql, "feedId", StringPool.LIKE, false, feedIds);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(name)", StringPool.LIKE, false, names);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(description)", StringPool.LIKE, true, descriptions);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (groupId > 0) {
				queryPos.add(groupId);
			}

			queryPos.add(feedIds, 2);
			queryPos.add(names, 2);
			queryPos.add(descriptions, 2);

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
	public List<JournalFeed> findByKeywords(
		long companyId, long groupId, String keywords, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator) {

		String[] feedIds = null;
		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			feedIds = _customSQL.keywords(keywords, false);
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return findByC_G_F_N_D(
			companyId, groupId, feedIds, names, descriptions, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<JournalFeed> findByC_G_F_N_D(
		long companyId, long groupId, String[] feedIds, String[] names,
		String[] descriptions, boolean andOperator, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator) {

		feedIds = _customSQL.keywords(feedIds, false);
		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_G_F_N_D);

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(sql, "(groupId = ?) AND");
			}

			sql = _customSQL.replaceKeywords(
				sql, "feedId", StringPool.LIKE, false, feedIds);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(name)", StringPool.LIKE, false, names);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(description)", StringPool.LIKE, true, descriptions);

			sql = _customSQL.replaceAndOperator(sql, andOperator);
			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("JournalFeed", JournalFeedImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (groupId > 0) {
				queryPos.add(groupId);
			}

			queryPos.add(feedIds, 2);
			queryPos.add(names, 2);
			queryPos.add(descriptions, 2);

			return (List<JournalFeed>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
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