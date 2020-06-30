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
				sql = StringUtil.replace(
					sql,
					" AND (LOWER(CIWarehouseItem.sku) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])",
					StringPool.BLANK);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(_COUNT_VALUE, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (Validator.isNotNull(sku)) {
				qPos.add(keywords, 2);
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
	public int countStockQuantityByC_S(long companyId, String sku) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_STOCK_QUANTITY_BY_C_S);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(_SUM_VALUE, Type.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(sku);

			Iterator<Integer> itr = q.iterate();

			if (itr.hasNext()) {
				Integer sum = itr.next();

				if (sum != null) {
					return sum.intValue();
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
	public int countStockQuantityByC_G_S(
		long companyId, long channelGroupId, String sku) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_STOCK_QUANTITY_BY_C_G_S);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(_SUM_VALUE, Type.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(channelGroupId);
			qPos.add(sku);

			Iterator<Integer> itr = q.iterate();

			if (itr.hasNext()) {
				Integer sum = itr.next();

				if (sum != null) {
					return sum.intValue();
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
	public int countUpdatedItemsByC_M(
		long companyId, Date startDate, Date endDate) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_UPDATED_ITEMS_BY_C_M);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(startDate);
			qPos.add(endDate);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			int count = 0;

			Iterator<Long> itr = q.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
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
				sql = StringUtil.replace(
					sql,
					" AND (LOWER(CIWarehouseItem.sku) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])",
					StringPool.BLANK);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(_SKU, Type.STRING);
			q.addScalar(_SUM_STOCK, Type.INTEGER);
			q.addScalar(_SUM_BOOKED, Type.INTEGER);
			q.addScalar(_SUM_AWAITING, Type.INTEGER);

			if (Validator.isNotNull(sku)) {
				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(keywords, 2);
			}

			return (List<Object[]>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
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

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				CommerceInventoryWarehouseItemImpl.TABLE_NAME,
				CommerceInventoryWarehouseItemImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(startDate);
			qPos.add(endDate);

			return (List<CommerceInventoryWarehouseItem>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _COUNT_VALUE = "COUNT_VALUE";

	private static final String _SKU = "SKU";

	private static final String _SUM_AWAITING = "SUM_AWAITING";

	private static final String _SUM_BOOKED = "SUM_BOOKED";

	private static final String _SUM_STOCK = "SUM_STOCK";

	private static final String _SUM_VALUE = "SUM_VALUE";

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}