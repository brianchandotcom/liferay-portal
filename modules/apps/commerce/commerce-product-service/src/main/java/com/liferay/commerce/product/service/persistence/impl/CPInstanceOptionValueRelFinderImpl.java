/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(service = CPInstanceOptionValueRelFinder.class)
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

	@Reference
	private CustomSQL _customSQL;

}