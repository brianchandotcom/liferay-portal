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

package com.liferay.app.builder.service.persistence.impl;

import com.liferay.app.builder.service.persistence.AppBuilderAppFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = AppBuilderAppFinder.class)
public class AppBuilderAppFinderImpl
	extends AppBuilderAppFinderBaseImpl implements AppBuilderAppFinder {

	public static final String FIND_BY_S_T =
		AppBuilderAppFinder.class.getName() + ".findByS_T";

	@Override
	public List<Long> findByS_T(int status, String type) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_S_T);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("appBuilderAppId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(status);
			qPos.add(type);

			return (List<Long>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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