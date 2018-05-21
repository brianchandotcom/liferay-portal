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

package com.liferay.commerce.forecast.service.persistence.impl;

import com.liferay.commerce.forecast.model.CommerceForecastValue;
import com.liferay.commerce.forecast.model.impl.CommerceForecastValueImpl;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastValueFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceForecastValueFinderImpl
	extends CommerceForecastValueFinderBaseImpl
	implements CommerceForecastValueFinder {

	public static final String FIND_BY_C_T =
		CommerceForecastValueFinder.class.getName() + ".findByC_T";

	@Override
	public List<CommerceForecastValue> findByC_T(
		long commerceForecastEntryId, long startTime, long endTime) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_T);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				"CommerceForecastValue", CommerceForecastValueImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(commerceForecastEntryId);
			qPos.add(startTime);
			qPos.add(endTime);

			return (List<CommerceForecastValue>)QueryUtil.list(
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