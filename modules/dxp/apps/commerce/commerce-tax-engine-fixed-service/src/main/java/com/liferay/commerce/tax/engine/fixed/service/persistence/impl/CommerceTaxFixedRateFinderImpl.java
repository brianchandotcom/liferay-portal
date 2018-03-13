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

package com.liferay.commerce.tax.engine.fixed.service.persistence.impl;

import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRateFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateFinderImpl
	extends CommerceTaxFixedRateFinderBaseImpl
	implements CommerceTaxFixedRateFinder {

	public static final String GET_COMMERCE_TAX_CATEGORY_IDS =
		CommerceTaxFixedRateFinder.class.getName() +
			".getCommerceTaxCategoryIds";

	@Override
	public List<Long> getCommerceTaxCategoryIds() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				getClass(), GET_COMMERCE_TAX_CATEGORY_IDS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			return (List<Long>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}