/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionFinder.class)
public class CPDefinitionFinderImpl
	extends CPDefinitionFinderBaseImpl implements CPDefinitionFinder {

	public static final String COUNT_BY_G_P_S =
		CPDefinitionFinder.class.getName() + ".countByG_P_S";

	public static final String FIND_BY_EXPIRATION_DATE =
		CPDefinitionFinder.class.getName() + ".findByExpirationDate";

	public static final String FIND_BY_G_P_S =
		CPDefinitionFinder.class.getName() + ".findByG_P_S";

	@Override
	public int countByG_P_S(
		long groupId, String productTypeName, String languageId,
		QueryDefinition<CPDefinition> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_G_P_S, queryDefinition,
				CPDefinitionImpl.TABLE_NAME);

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(CPDefinition.groupId = ?) AND");
			}

			if (Validator.isNull(productTypeName)) {
				sql = StringUtil.removeSubstring(
					sql, "(CPDefinition.productTypeName = ?) AND");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(languageId);

			if (groupId > 0) {
				queryPos.add(groupId);
			}

			if (Validator.isNotNull(productTypeName)) {
				queryPos.add(productTypeName);
			}

			queryPos.add(queryDefinition.getStatus());

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
	public List<CPDefinition> findByExpirationDate(
		Date expirationDate, QueryDefinition<CPDefinition> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_EXPIRATION_DATE, queryDefinition,
				CPDefinitionImpl.TABLE_NAME);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CPDefinitionImpl.TABLE_NAME, CPDefinitionImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (expirationDate != null) {
				queryPos.add(expirationDate);
			}

			queryPos.add(queryDefinition.getStatus());

			return (List<CPDefinition>)QueryUtil.list(
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

	@Override
	public List<CPDefinition> findByG_P_S(
		long groupId, String productTypeName, String languageId,
		QueryDefinition<CPDefinition> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_G_P_S, queryDefinition,
				CPDefinitionImpl.TABLE_NAME);

			sql = _customSQL.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			if (groupId <= 0) {
				sql = StringUtil.removeSubstring(
					sql, "(CPDefinition.groupId = ?) AND");
			}

			if (Validator.isNull(productTypeName)) {
				sql = StringUtil.removeSubstring(
					sql, "(CPDefinition.productTypeName = ?) AND");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CPDefinitionImpl.TABLE_NAME, CPDefinitionImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(languageId);

			if (groupId > 0) {
				queryPos.add(groupId);
			}

			if (Validator.isNotNull(productTypeName)) {
				queryPos.add(productTypeName);
			}

			queryPos.add(queryDefinition.getStatus());

			return (List<CPDefinition>)QueryUtil.list(
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