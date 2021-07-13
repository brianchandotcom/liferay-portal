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

package com.liferay.commerce.inventory.service.persistence.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseItemImpl;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryWarehouseItemFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryWarehouseItemFinderImpl
	extends CommerceInventoryWarehouseItemFinderBaseImpl
	implements CommerceInventoryWarehouseItemFinder {

	public static final String COUNT_ITEMS_BY_COMPANY_ID =
		CommerceInventoryWarehouseItemFinder.class.getName() +
			".countItemsByCompanyId";

	public static final String COUNT_STOCK_QUANTITY_BY_C_S =
		CommerceInventoryWarehouseItemFinder.class.getName() +
			".countStockQuantityByC_S";

	public static final String COUNT_STOCK_QUANTITY_BY_C_G_S =
		CommerceInventoryWarehouseItemFinder.class.getName() +
			".countStockQuantityByC_G_S";

	public static final String COUNT_UPDATED_ITEMS_BY_C_M =
		CommerceInventoryWarehouseItemFinder.class.getName() +
			".countUpdatedItemsByC_M";

	public static final String FIND_ITEMS_BY_COMPANY_ID =
		CommerceInventoryWarehouseItemFinder.class.getName() +
			".findItemsByCompanyId";

	public static final String FIND_UPDATED_ITEMS_BY_C_M =
		CommerceInventoryWarehouseItemFinder.class.getName() +
			".findUpdatedItemsByC_M";

	@Override
	public int countItemsByCompanyId(long companyId, String sku) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_ITEMS_BY_COMPANY_ID);

			String[] keywords = _customSQL.keywords(sku, true);

			if (Validator.isNotNull(sku)) {
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(CIWarehouseItem.sku)", StringPool.LIKE, true,
					keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CIWarehouseItem.sku) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (Validator.isNotNull(sku)) {
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
	public int countStockQuantityByC_S(long companyId, String sku) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_STOCK_QUANTITY_BY_C_S);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("SUM_VALUE", Type.INTEGER);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(sku);

			Iterator<Integer> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Integer sum = iterator.next();

				if (sum != null) {
					return sum.intValue();
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
	public int countStockQuantityByC_G_S(
		long companyId, long commerceChannelGroupId, String sku) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_STOCK_QUANTITY_BY_C_G_S);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("SUM_VALUE", Type.INTEGER);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(commerceChannelGroupId);
			queryPos.add(sku);

			Iterator<Integer> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Integer sum = iterator.next();

				if (sum != null) {
					return sum.intValue();
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
	public int countUpdatedItemsByC_M(
		long companyId, Date startDate, Date endDate) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_UPDATED_ITEMS_BY_C_M);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(startDate);
			queryPos.add(endDate);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			int count = 0;

			Iterator<Long> iterator = sqlQuery.iterate();

			while (iterator.hasNext()) {
				Long l = iterator.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<Object[]> findItemsByCompanyId(
		long companyId, String sku, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String[] keywords = _customSQL.keywords(sku, true);

			String sql = _customSQL.get(getClass(), FIND_ITEMS_BY_COMPANY_ID);

			sql = StringUtil.replace(
				sql, new String[] {"[$COMPANY_ID$]"},
				new String[] {String.valueOf(companyId)});

			if (Validator.isNotNull(sku)) {
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(CIWarehouseItem.sku)", StringPool.LIKE, true,
					keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CIWarehouseItem.sku) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("SKU", Type.STRING);
			sqlQuery.addScalar("SUM_STOCK", Type.INTEGER);
			sqlQuery.addScalar("SUM_BOOKED", Type.INTEGER);
			sqlQuery.addScalar("SUM_AWAITING", Type.INTEGER);

			if (Validator.isNotNull(sku)) {
				QueryPos queryPos = QueryPos.getInstance(sqlQuery);

				queryPos.add(keywords, 2);
			}

			return (List<Object[]>)QueryUtil.list(
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
	public List<CommerceInventoryWarehouseItem> findUpdatedItemsByC_M(
		long companyId, Date startDate, Date endDate, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_UPDATED_ITEMS_BY_C_M);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommerceInventoryWarehouseItemImpl.TABLE_NAME,
				CommerceInventoryWarehouseItemImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(startDate);
			queryPos.add(endDate);

			return (List<CommerceInventoryWarehouseItem>)QueryUtil.list(
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