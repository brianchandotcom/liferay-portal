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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.impl.CommerceCountryImpl;
import com.liferay.commerce.service.persistence.CommerceCountryFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCountryFinderImpl
	extends CommerceCountryFinderBaseImpl implements CommerceCountryFinder {

	public static final String FIND_BY_COMMERCE_WAREHOUSES =
		CommerceCountryFinder.class.getName() + ".findByCommerceWarehouses";

	@Override
	public List<CommerceCountry> findByCommerceWarehouses(
		long groupId, boolean all) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_COMMERCE_WAREHOUSES);

			if (all) {
				sql = StringUtil.replace(sql, _ALL_SQL, StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(sql, _ALL_SQL, _ACTIVE_SQL);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CommerceCountry", CommerceCountryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<CommerceCountry>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _ACTIVE_SQL =
		"AND (CommerceWarehouse.active_ = true)";

	private static final String _ALL_SQL = "[$ALL$]";

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}