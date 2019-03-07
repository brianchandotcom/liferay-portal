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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.model.impl.CTEntryAggregateImpl;
import com.liferay.change.tracking.service.persistence.CTEntryAggregateFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Gergely Mathe
 */
public class CTEntryAggregateFinderImpl
	extends CTEntryAggregateFinderBaseImpl implements CTEntryAggregateFinder {

	public static final String FIND_BY_C_O =
		CTEntryAggregateFinder.class.getName() + ".findByC_O";

	@Override
	public List<CTEntryAggregate> findByC_O(
		long ctCollectionId, long ownerCTEntryId,
		QueryDefinition<CTEntryAggregate> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_O);

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CTEntryAggregate", CTEntryAggregateImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(ctCollectionId);
			qPos.add(ownerCTEntryId);

			return (List<CTEntryAggregate>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
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