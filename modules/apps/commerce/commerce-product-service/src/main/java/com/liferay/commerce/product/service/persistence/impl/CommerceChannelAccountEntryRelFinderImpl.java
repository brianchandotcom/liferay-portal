/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.model.impl.CommerceChannelAccountEntryRelImpl;
import com.liferay.commerce.product.service.persistence.CommerceChannelAccountEntryRelFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = CommerceChannelAccountEntryRelFinder.class)
public class CommerceChannelAccountEntryRelFinderImpl
	extends CommerceChannelAccountEntryRelFinderBaseImpl
	implements CommerceChannelAccountEntryRelFinder {

	public static final String COUNT_BY_C_T =
		CommerceChannelAccountEntryRelFinder.class.getName() + ".countByC_T";

	public static final String FIND_BY_C_T =
		CommerceChannelAccountEntryRelFinder.class.getName() + ".findByC_T";

	@Override
	public int countByC_T(long commerceChannelId, String name, int type) {
		return countByC_T(commerceChannelId, name, type, false);
	}

	@Override
	public int countByC_T(
		long commerceChannelId, String name, int type,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_T);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CommerceChannel.class.getName(),
					"CommerceChannel.commerceChannelId");
			}

			String[] keywords = _customSQL.keywords(name, true);

			if (Validator.isNotNull(name)) {
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(AccountEntry.name)", StringPool.LIKE, true,
					keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(AccountEntry.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceChannelId);
			queryPos.add(type);

			if (Validator.isNotNull(name)) {
				queryPos.add(keywords, 2);
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
	public List<CommerceChannelAccountEntryRel> findByC_T(
		long commerceChannelId, String name, int type, int start, int end) {

		return findByC_T(commerceChannelId, name, type, start, end, false);
	}

	@Override
	public List<CommerceChannelAccountEntryRel> findByC_T(
		long commerceChannelId, String name, int type, int start, int end,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String[] keywords = _customSQL.keywords(name, true);

			String sql = _customSQL.get(getClass(), FIND_BY_C_T);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CommerceChannel.class.getName(),
					"CommerceChannel.commerceChannelId");
			}

			if (Validator.isNotNull(name)) {
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(AccountEntry.name)", StringPool.LIKE, true,
					keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(AccountEntry.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommerceChannelAccountEntryRelImpl.TABLE_NAME,
				CommerceChannelAccountEntryRelImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceChannelId);
			queryPos.add(type);

			if (Validator.isNotNull(name)) {
				queryPos.add(keywords, 2);
			}

			return (List<CommerceChannelAccountEntryRel>)QueryUtil.list(
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

	@Reference
	private Portal _portal;

}