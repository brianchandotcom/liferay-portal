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

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, service = CommerceTaxFixedRateAddressRelFinder.class
)
public class CommerceTaxFixedRateAddressRelFinderImpl
	extends CommerceTaxFixedRateAddressRelFinderBaseImpl
	implements CommerceTaxFixedRateAddressRelFinder {

	public static final String FIND_BY_C_C_R_Z =
		CommerceTaxFixedRateAddressRelFinder.class.getName() + ".findByC_C_R_Z";

	public static final String FIND_BY_C_C_C_R_Z =
		CommerceTaxFixedRateAddressRelFinder.class.getName() +
			".findByC_C_C_R_Z";

	@Override
	public CommerceTaxFixedRateAddressRel fetchByC_C_C_R_Z_First(
		long commerceTaxMethodId, long cpTaxCategoryId, long countryId,
		long regionId, String zip) {

		List<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRels =
			findByC_C_C_R_Z(
				commerceTaxMethodId, cpTaxCategoryId, countryId, regionId, zip,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (!commerceTaxFixedRateAddressRels.isEmpty()) {
			return commerceTaxFixedRateAddressRels.get(0);
		}

		return null;
	}

	@Override
	public CommerceTaxFixedRateAddressRel fetchByC_C_R_Z_First(
		long commerceTaxMethodId, long countryId, long regionId, String zip) {

		List<CommerceTaxFixedRateAddressRel> commerceTaxFixedRateAddressRels =
			findByC_C_R_Z(commerceTaxMethodId, countryId, regionId, zip);

		if (!commerceTaxFixedRateAddressRels.isEmpty()) {
			return commerceTaxFixedRateAddressRels.get(0);
		}

		return null;
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel> findByC_C_R_Z(
		long commerceTaxMethodId, long countryId, long regionId, String zip) {

		return findByC_C_R_Z(
			commerceTaxMethodId, countryId, regionId, zip, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel> findByC_C_R_Z(
		long commerceTaxMethodId, long countryId, long regionId, String zip,
		int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_C_R_Z);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"CommerceTaxFixedRateAddressRel",
				CommerceTaxFixedRateAddressRelImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceTaxMethodId);
			queryPos.add(countryId);
			queryPos.add(regionId);
			queryPos.add(zip);

			return (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceTaxFixedRateAddressRel> findByC_C_C_R_Z(
		long commerceTaxMethodId, long cpTaxCategoryId, long countryId,
		long regionId, String zip, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_C_C_R_Z);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"CommerceTaxFixedRateAddressRel",
				CommerceTaxFixedRateAddressRelImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceTaxMethodId);
			queryPos.add(cpTaxCategoryId);
			queryPos.add(countryId);
			queryPos.add(regionId);
			queryPos.add(zip);

			return (List<CommerceTaxFixedRateAddressRel>)QueryUtil.list(
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

}