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

package com.liferay.commerce.shipping.engine.fixed.service.persistence.impl;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelImpl;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionRelFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionRelFinderImpl
	extends CommerceShippingFixedOptionRelFinderBaseImpl
	implements CommerceShippingFixedOptionRelFinder {

	public static final String FIND_BY_C_C_C_Z_W =
		CommerceShippingFixedOptionRelFinder.class.getName() +
			".findByC_C_C_Z_W";

	@Override
	public CommerceShippingFixedOptionRel fetchByC_C_C_Z_W_First(
		long commerceShippingFixedOptionId, long countryId, long regionId,
		String zip, double weight) {

		List<CommerceShippingFixedOptionRel> commerceShippingFixedOptionRels =
			findByC_C_C_Z_W(
				commerceShippingFixedOptionId, countryId, regionId, zip,
				weight);

		if (!commerceShippingFixedOptionRels.isEmpty()) {
			return commerceShippingFixedOptionRels.get(0);
		}

		return null;
	}

	@Override
	public List<CommerceShippingFixedOptionRel> findByC_C_C_Z_W(
		long commerceShippingFixedOptionId, long countryId, long regionId,
		String zip, double weight) {

		return findByC_C_C_Z_W(
			commerceShippingFixedOptionId, countryId, regionId, zip, weight,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CommerceShippingFixedOptionRel> findByC_C_C_Z_W(
		long commerceShippingFixedOptionId, long countryId, long regionId,
		String zip, double weight, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_C_C_Z_W);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"CommerceShippingFixedOptionRel",
				CommerceShippingFixedOptionRelImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceShippingFixedOptionId);
			queryPos.add(countryId);
			queryPos.add(regionId);
			queryPos.add(zip);
			queryPos.add(weight);
			queryPos.add(weight);

			return (List<CommerceShippingFixedOptionRel>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}