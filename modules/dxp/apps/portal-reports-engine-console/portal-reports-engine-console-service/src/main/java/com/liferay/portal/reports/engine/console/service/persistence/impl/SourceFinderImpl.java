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

package com.liferay.portal.reports.engine.console.service.persistence.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.model.impl.SourceImpl;
import com.liferay.portal.reports.engine.console.service.persistence.SourceFinder;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = SourceFinder.class)
public class SourceFinderImpl
	extends SourceFinderBaseImpl implements SourceFinder {

	public static final String COUNT_BY_G_N_DU =
		SourceFinder.class.getName() + ".countByG_N_DU";

	public static final String FIND_BY_G_N_DU =
		SourceFinder.class.getName() + ".findByG_N_DU";

	@Override
	public int countByG_N_DU(
		long groupId, String name, String driverUrl, boolean andOperator) {

		return doCountByG_N_DU(groupId, name, driverUrl, andOperator, false);
	}

	@Override
	public int filterCountByG_N_DU(
		long groupId, String name, String driverUrl, boolean andOperator) {

		return doCountByG_N_DU(groupId, name, driverUrl, andOperator, true);
	}

	@Override
	public List<Source> filterFindByG_N_DU(
		long groupId, String name, String driverUrl, boolean andOperator,
		int start, int end, OrderByComparator<Source> orderByComparator) {

		return doFindByG_N_DU(
			groupId, name, driverUrl, andOperator, start, end,
			orderByComparator, true);
	}

	@Override
	public List<Source> findByG_N_DU(
		long groupId, String name, String driverUrl, boolean andOperator,
		int start, int end, OrderByComparator<Source> orderByComparator) {

		return doFindByG_N_DU(
			groupId, name, driverUrl, andOperator, start, end,
			orderByComparator, false);
	}

	protected int doCountByG_N_DU(
		long groupId, String name, String driverUrl, boolean andOperator,
		boolean inlineSQLHelper) {

		if (Validator.isNull(name) || Validator.isNull(driverUrl)) {
			andOperator = true;
		}

		String[] names = _customSQL.keywords(name);
		String[] driverUrls = _customSQL.keywords(driverUrl);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_N_DU);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Source.class.getName(), "Reports_Source.sourceId",
					groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(Reports_Source.groupId = ?) AND");
			}

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(Reports_Source.name))", StringPool.LIKE,
				false, names);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(Reports_Source.driverUrl)", StringPool.LIKE, true,
				driverUrls);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			qPos.add(names, 2);
			qPos.add(driverUrls, 2);

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

	protected List<Source> doFindByG_N_DU(
		long groupId, String name, String driverUrl, boolean andOperator,
		int start, int end, OrderByComparator<Source> orderByComparator,
		boolean inlineSQLHelper) {

		if (Validator.isNull(name) || Validator.isNull(driverUrl)) {
			andOperator = true;
		}

		String[] names = _customSQL.keywords(name);
		String[] driverUrls = _customSQL.keywords(driverUrl);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_N_DU);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Source.class.getName(), "Reports_Source.sourceId",
					groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(Reports_Source.groupId = ?) AND");
			}

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(Reports_Source.name))", StringPool.LIKE,
				false, names);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(Reports_Source.driverUrl)", StringPool.LIKE, true,
				driverUrls);
			sql = _customSQL.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				sql = _customSQL.replaceOrderBy(sql, orderByComparator);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Reports_Source", SourceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			qPos.add(names, 2);
			qPos.add(driverUrls, 2);

			return (List<Source>)QueryUtil.list(q, getDialect(), start, end);
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