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

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseImpl;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryWarehouseFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryWarehouseFinderImpl
	extends CommerceInventoryWarehouseFinderBaseImpl
	implements CommerceInventoryWarehouseFinder {

	public static final String FIND_BY_G_S =
		CommerceInventoryWarehouseFinder.class.getName() + ".findByG_S";

	public static final String FIND_BY_C_G_A =
		CommerceInventoryWarehouseFinder.class.getName() + ".findByC_G_A";

	@Override
	public List<CommerceInventoryWarehouse> findByG_S(
		long groupId, String sku) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_S);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				CommerceInventoryWarehouseImpl.TABLE_NAME,
				CommerceInventoryWarehouseImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(sku);

			return (List<CommerceInventoryWarehouse>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceInventoryWarehouse> findByC_G_A(
		long companyId, long groupId, boolean active) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_G_A);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				CommerceInventoryWarehouseImpl.TABLE_NAME,
				CommerceInventoryWarehouseImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(active);

			return (List<CommerceInventoryWarehouse>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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