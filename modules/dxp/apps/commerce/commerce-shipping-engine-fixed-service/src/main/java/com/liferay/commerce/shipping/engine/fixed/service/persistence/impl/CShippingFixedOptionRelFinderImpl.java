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

import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CShippingFixedOptionRelImpl;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CShippingFixedOptionRelFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CShippingFixedOptionRelFinderImpl
	extends CShippingFixedOptionRelFinderBaseImpl
	implements CShippingFixedOptionRelFinder {

	public static final String FIND_BY_C_C_C_Z_W =
		CShippingFixedOptionRelFinder.class.getName() + ".findByC_C_C_Z_W";

	@Override
	public CShippingFixedOptionRel fetchByC_C_C_Z_W_First(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, String zip, double weight) {

		List<CShippingFixedOptionRel> cShippingFixedOptionRels =
			findByC_C_C_Z_W(
				commerceShippingFixedOptionId, commerceCountryId,
				commerceRegionId, zip, weight);

		if (!cShippingFixedOptionRels.isEmpty()) {
			return cShippingFixedOptionRels.get(0);
		}

		return null;
	}

	@Override
	public List<CShippingFixedOptionRel> findByC_C_C_Z_W(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, String zip, double weight) {

		return findByC_C_C_Z_W(
			commerceShippingFixedOptionId, commerceCountryId, commerceRegionId,
			zip, weight, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CShippingFixedOptionRel> findByC_C_C_Z_W(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, String zip, double weight, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), FIND_BY_C_C_C_Z_W);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				"CShippingFixedOptionRel", CShippingFixedOptionRelImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(commerceShippingFixedOptionId);
			qPos.add(commerceCountryId);
			qPos.add(commerceRegionId);
			qPos.add(zip);
			qPos.add(weight);

			return (List<CShippingFixedOptionRel>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}