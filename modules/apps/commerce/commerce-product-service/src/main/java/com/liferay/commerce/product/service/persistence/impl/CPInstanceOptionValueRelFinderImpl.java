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

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelImpl;
import com.liferay.commerce.product.service.persistence.CPInstanceOptionValueRelFinder;
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
 * @author Igor Beslic
 */
public class CPInstanceOptionValueRelFinderImpl
	extends CPInstanceOptionValueRelFinderBaseImpl
	implements CPInstanceOptionValueRelFinder {

	public static final String FIND_BY_CP_DEFINITION_ID =
		CPInstanceOptionValueRelFinder.class.getName() +
			".findByCPDefinitionId";

	@Override
	public List<CPInstanceOptionValueRel> findByCPDefinitionId(
		long cpDefinitionId, QueryDefinition<CPInstance> queryDefinition) {

		return doFindByCPDefinitionId(cpDefinitionId, queryDefinition);
	}

	protected List<CPInstanceOptionValueRel> doFindByCPDefinitionId(
		long cpDefinitionId, QueryDefinition<CPInstance> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_CP_DEFINITION_ID, queryDefinition,
				CPInstanceOptionValueRelImpl.TABLE_NAME);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CPInstanceOptionValueRelImpl.TABLE_NAME,
				CPInstanceOptionValueRelImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(cpDefinitionId);
			queryPos.add(queryDefinition.getStatus());

			return (List<CPInstanceOptionValueRel>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
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