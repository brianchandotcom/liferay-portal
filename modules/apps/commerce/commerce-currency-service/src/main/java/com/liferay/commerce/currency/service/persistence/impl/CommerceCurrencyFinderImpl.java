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

package com.liferay.commerce.currency.service.persistence.impl;

import com.liferay.commerce.currency.service.persistence.CommerceCurrencyFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CommerceCurrencyFinder.class)
public class CommerceCurrencyFinderImpl
	extends CommerceCurrencyFinderBaseImpl implements CommerceCurrencyFinder {

	public static final String GET_COMPANY_IDS =
		CommerceCurrencyFinder.class.getName() + ".getCompanyIds";

	@Override
	public List<Long> getCompanyIds() {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), GET_COMPANY_IDS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			return (List<Long>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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