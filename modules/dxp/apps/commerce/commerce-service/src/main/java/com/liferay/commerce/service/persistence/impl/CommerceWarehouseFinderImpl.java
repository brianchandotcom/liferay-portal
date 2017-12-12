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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.impl.CommerceWarehouseImpl;
import com.liferay.commerce.service.persistence.CommerceWarehouseFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseFinderImpl
	extends CommerceWarehouseFinderBaseImpl implements CommerceWarehouseFinder {

	public static final String COUNT_BY_G_N_D_S_C_Z_C =
		CommerceWarehouseFinder.class.getName() + ".countByG_N_D_S_C_Z_C";

	public static final String FIND_BY_COMMERCE_WAREHOUSE_ITEM_QUANTITY =
		CommerceWarehouseFinder.class.getName() +
			".findByCommerceWarehouseItemQuantity";

	public static final String FIND_BY_G_N_D_S_C_Z_C =
		CommerceWarehouseFinder.class.getName() + ".findByG_N_D_S_C_Z_C";

	@Override
	public int countByKeywords(
		long groupId, String keywords, long commerceCountryId) {

		String[] names = null;
		String[] descriptions = null;
		String[] streets = null;
		String[] cities = null;
		String[] zips = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			streets = CustomSQLUtil.keywords(keywords);
			cities = CustomSQLUtil.keywords(keywords);
			zips = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return countByG_N_D_S_C_Z_C(
			groupId, names, descriptions, streets, cities, zips,
			commerceCountryId, andOperator);
	}

	@Override
	public int countByG_N_D_S_C_Z_C(
		long groupId, String name, String description, String street,
		String city, String zip, long commerceCountryId, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);
		String[] streets = CustomSQLUtil.keywords(street);
		String[] cities = CustomSQLUtil.keywords(city);
		String[] zips = CustomSQLUtil.keywords(zip);

		return countByG_N_D_S_C_Z_C(
			groupId, names, descriptions, streets, cities, zips,
			commerceCountryId, andOperator);
	}

	@Override
	public int countByG_N_D_S_C_Z_C(
		long groupId, String[] names, String[] descriptions, String[] streets,
		String[] cities, String[] zips, long commerceCountryId,
		boolean andOperator) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		streets = CustomSQLUtil.keywords(streets);
		cities = CustomSQLUtil.keywords(cities);
		zips = CustomSQLUtil.keywords(zips);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), COUNT_BY_G_N_D_S_C_Z_C);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.name)", StringPool.LIKE, false,
				names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "CommerceWarehouse.description", StringPool.LIKE, false,
				descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.street1)", StringPool.LIKE, true,
				streets);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.street2)", StringPool.LIKE, true,
				streets);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.street3)", StringPool.LIKE, true,
				streets);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.cities)", StringPool.LIKE, false,
				cities);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.zips)", StringPool.LIKE, false,
				zips);

			if (commerceCountryId < 0) {
				sql = StringUtil.replace(
					sql, _COMMERCE_COUNTRY_ID_SQL, StringPool.BLANK);
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(streets, 6);
			qPos.add(cities, 2);
			qPos.add(zips, 2);

			if (commerceCountryId >= 0) {
				qPos.add(commerceCountryId);
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceWarehouse> findByCommerceWarehouseItemQuantity(
		long cpInstanceId, int quantity, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				getClass(), FIND_BY_COMMERCE_WAREHOUSE_ITEM_QUANTITY);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CommerceWarehouse", CommerceWarehouseImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(cpInstanceId);
			qPos.add(quantity);

			return (List<CommerceWarehouse>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceWarehouse> findByKeywords(
		long groupId, String keywords, long commerceCountryId, int start,
		int end, OrderByComparator<CommerceWarehouse> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		String[] streets = null;
		String[] cities = null;
		String[] zips = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			streets = CustomSQLUtil.keywords(keywords);
			cities = CustomSQLUtil.keywords(keywords);
			zips = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return findByG_N_D_S_C_Z_C(
			groupId, names, descriptions, streets, cities, zips,
			commerceCountryId, andOperator, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWarehouse> findByG_N_D_S_C_Z_C(
		long groupId, String name, String description, String street,
		String city, String zip, long commerceCountryId, boolean andOperator,
		int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);
		String[] streets = CustomSQLUtil.keywords(street);
		String[] cities = CustomSQLUtil.keywords(city);
		String[] zips = CustomSQLUtil.keywords(zip);

		return findByG_N_D_S_C_Z_C(
			groupId, names, descriptions, streets, cities, zips,
			commerceCountryId, andOperator, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWarehouse> findByG_N_D_S_C_Z_C(
		long groupId, String[] names, String[] descriptions, String[] streets,
		String[] cities, String[] zips, long commerceCountryId,
		boolean andOperator, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		streets = CustomSQLUtil.keywords(streets);
		cities = CustomSQLUtil.keywords(cities);
		zips = CustomSQLUtil.keywords(zips);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), FIND_BY_G_N_D_S_C_Z_C);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.name)", StringPool.LIKE, false,
				names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "CommerceWarehouse.description", StringPool.LIKE, false,
				descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.street1)", StringPool.LIKE, true,
				streets);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.street2)", StringPool.LIKE, true,
				streets);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.street3)", StringPool.LIKE, true,
				streets);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.cities)", StringPool.LIKE, false,
				cities);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CommerceWarehouse.zips)", StringPool.LIKE, false,
				zips);

			if (commerceCountryId < 0) {
				sql = StringUtil.replace(
					sql, _COMMERCE_COUNTRY_ID_SQL, StringPool.BLANK);
			}

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CommerceWarehouse", CommerceWarehouseImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(streets, 6);
			qPos.add(cities, 2);
			qPos.add(zips, 2);

			if (commerceCountryId >= 0) {
				qPos.add(commerceCountryId);
			}

			return (List<CommerceWarehouse>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _COMMERCE_COUNTRY_ID_SQL =
		"AND (CommerceWarehouse.commerceCountryId = ?)";

}