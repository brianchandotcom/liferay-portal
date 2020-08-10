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

package com.liferay.commerce.account.service.persistence.impl;

import com.liferay.commerce.account.service.persistence.CommerceAccountGroupFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collections;
import java.util.List;

/**
 * @author Luca Pellizzon
 */
public class CommerceAccountGroupFinderImpl
	extends CommerceAccountGroupFinderBaseImpl
	implements CommerceAccountGroupFinder {

	public static final String FIND_ACCOUNT_USER_IDS_FROM_ACCOUNT_GROUP_IDS =
		CommerceAccountGroupFinder.class.getName() +
			".findAccountUserIdsFromAccountGroupIds";

	@Override
	public List<Long> findAccountUserIdsFromAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end) {

		if (ArrayUtil.isEmpty(commerceAccountGroupIds)) {
			return Collections.emptyList();
		}

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_ACCOUNT_USER_IDS_FROM_ACCOUNT_GROUP_IDS);

			sql = StringUtil.replace(
				sql, "[$ACCOUNT_GROUP_IDS$]",
				StringUtil.merge(commerceAccountGroupIds));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			return (List<Long>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}