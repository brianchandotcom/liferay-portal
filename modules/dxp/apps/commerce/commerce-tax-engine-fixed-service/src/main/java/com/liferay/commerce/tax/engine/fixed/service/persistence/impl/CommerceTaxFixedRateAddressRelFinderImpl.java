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

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelImpl;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxFixedRateAddressRelFinder;
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
public class CommerceTaxFixedRateAddressRelFinderImpl
	extends CommerceTaxFixedRateAddressRelFinderBaseImpl
	implements CommerceTaxFixedRateAddressRelFinder {

	public static final String FIND_BY_C_C_C_Z =
		CommerceTaxFixedRateAddressRelFinder.class.getName() + ".findByC_C_C_Z";

	@Override
	public CommerceTaxFixedRateAddressRel fetchByC_C_C_Z_First(
		long commerceTaxMethodId, long commerceCountryId, long commerceRegionId,
		String zip) {

		List<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRels =
			findByC_C_C_Z(
				commerceTaxMethodId, commerceCountryId, commerceRegionId, zip);

		if (!commerceTaxFixedRateAddressRels.isEmpty()) {
			return commerceTaxFixedRateAddressRels.get(0);
		}

		return null;
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel> findByC_C_C_Z(
		long commerceTaxMethodId, long commerceCountryId, long commerceRegionId,
		String zip) {

		return findByC_C_C_Z(
			commerceTaxMethodId, commerceCountryId, commerceRegionId, zip,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel> findByC_C_C_Z(
		long commerceTaxMethodId, long commerceCountryId, long commerceRegionId,
		String zip, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_C_C_Z);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				"CommerceTaxFixedRateAddressRel",
				CommerceTaxFixedRateAddressRelImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(commerceTaxMethodId);
			qPos.add(commerceCountryId);
			qPos.add(commerceRegionId);
			qPos.add(zip);

			return (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(
				q, getDialect(), start, end);
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