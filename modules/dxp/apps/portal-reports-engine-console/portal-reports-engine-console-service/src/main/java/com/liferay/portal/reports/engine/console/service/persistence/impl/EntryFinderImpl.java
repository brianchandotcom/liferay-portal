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
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.model.impl.EntryImpl;
import com.liferay.portal.reports.engine.console.service.persistence.EntryFinder;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = EntryFinder.class)
public class EntryFinderImpl
	extends EntryFinderBaseImpl implements EntryFinder {

	public static final String COUNT_BY_G_CD_N_SN =
		EntryFinder.class.getName() + ".countByG_CD_N_SN";

	public static final String FIND_BY_G_CD_N_SN =
		EntryFinder.class.getName() + ".findByG_CD_N_SN";

	@Override
	public int countByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator) {

		return doCountByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andOperator, false);
	}

	@Override
	public int filterCountByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator) {

		return doCountByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andOperator, true);
	}

	@Override
	public List<Entry> filterFindByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator, int start, int end,
		OrderByComparator<Entry> orderByComparator) {

		return doFindByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andOperator, start, end, orderByComparator, true);
	}

	@Override
	public List<Entry> findByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator, int start, int end,
		OrderByComparator<Entry> orderByComparator) {

		return doFindByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andOperator, start, end, orderByComparator, false);
	}

	protected int doCountByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator, boolean inlineSQLHelper) {

		if (Validator.isNull(definitionName) || Validator.isNull(userName)) {
			andOperator = true;
		}

		String[] definitionNames = _customSQL.keywords(definitionName);
		String[] userNames = _customSQL.keywords(userName);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_CD_N_SN);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Entry.class.getName(), "Reports_Entry.entryId",
					groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(Reports_Entry.groupId = ?) AND");
			}

			if (createDateGT == null) {
				sql = StringUtil.removeSubstring(
					sql, "(Reports_Entry.createDate > ?) AND");
			}

			if (createDateLT == null) {
				sql = StringUtil.removeSubstring(
					sql, "(Reports_Entry.createDate < ?) AND");
			}

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(Reports_Definition.name))",
				StringPool.LIKE, false, definitionNames);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.screenName)", StringPool.LIKE, true,
				userNames);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (createDateGT != null) {
				qPos.add(createDateGT);
			}

			if (createDateLT != null) {
				qPos.add(createDateLT);
			}

			qPos.add(definitionNames, 2);
			qPos.add(userNames, 2);

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

	protected List<Entry> doFindByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator, int start, int end,
		OrderByComparator<Entry> orderByComparator, boolean inlineSQLHelper) {

		if (Validator.isNull(definitionName) || Validator.isNull(userName)) {
			andOperator = true;
		}

		String[] definitionNames = _customSQL.keywords(definitionName);
		String[] userNames = _customSQL.keywords(userName);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_CD_N_SN);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Entry.class.getName(), "Reports_Entry.entryId",
					groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(Reports_Entry.groupId = ?) AND");
			}

			if (createDateGT == null) {
				sql = StringUtil.removeSubstring(
					sql, "(Reports_Entry.createDate > ?) AND ");
			}

			if (createDateLT == null) {
				sql = StringUtil.removeSubstring(
					sql, "(Reports_Entry.createDate < ?) AND ");
			}

			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(Reports_Definition.name))",
				StringPool.LIKE, false, definitionNames);
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(User_.screenName)", StringPool.LIKE, true,
				userNames);
			sql = _customSQL.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				sql = _customSQL.replaceOrderBy(sql, orderByComparator);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Reports_Entry", EntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (createDateGT != null) {
				qPos.add(createDateGT);
			}

			if (createDateLT != null) {
				qPos.add(createDateLT);
			}

			qPos.add(definitionNames, 2);
			qPos.add(userNames, 2);

			return (List<Entry>)QueryUtil.list(q, getDialect(), start, end);
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