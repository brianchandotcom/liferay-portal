/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.impl.CPInstanceImpl;
import com.liferay.commerce.product.service.persistence.CPInstanceFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstanceFinderImpl
	extends CPInstanceFinderBaseImpl implements CPInstanceFinder {

	public static final String FIND_BY_EXPIRATION_DATE =
		CPInstanceFinder.class.getName() + ".findByExpirationDate";

	@Override
	public List<CPInstance> findByExpirationDate(
		Date expirationDate, QueryDefinition<CPInstance> queryDefinition) {

		return doFindByExpirationDate(expirationDate, queryDefinition, false);
	}

	protected List<CPInstance> doFindByExpirationDate(
		Date expirationDate, QueryDefinition<CPInstance> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_EXPIRATION_DATE, queryDefinition,
				CPInstanceImpl.TABLE_NAME);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(CPInstanceImpl.TABLE_NAME, CPInstanceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (expirationDate != null) {
				qPos.add(expirationDate);
			}

			qPos.add(queryDefinition.getStatus());

			return (List<CPInstance>)QueryUtil.list(
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