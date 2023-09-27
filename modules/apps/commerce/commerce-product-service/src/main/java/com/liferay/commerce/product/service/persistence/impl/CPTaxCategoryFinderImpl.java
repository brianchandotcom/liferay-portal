/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.model.impl.CPTaxCategoryImpl;
import com.liferay.commerce.product.service.persistence.CPTaxCategoryFinder;
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

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(service = CPTaxCategoryFinder.class)
public class CPTaxCategoryFinderImpl
	extends CPTaxCategoryFinderBaseImpl implements CPTaxCategoryFinder {

	public static final String COUNT_CP_TAX_CATEGORIES_BY_COMPANY_ID =
		CPTaxCategoryFinder.class.getName() +
			".countCPTaxCategoriesByCompanyId";

	public static final String FIND_CP_TAX_CATEGORIES_BY_COMPANY_ID =
		CPTaxCategoryFinder.class.getName() + ".findCPTaxCategoriesByCompanyId";

	@Override
	public int countCPTaxCategoriesByCompanyId(long companyId, String keyword) {
		Session session = null;

		try {
			session = openSession();

			String[] keywords = _customSQL.keywords(keyword, true);

			String sql = _customSQL.get(
				getClass(), COUNT_CP_TAX_CATEGORIES_BY_COMPANY_ID);

			sql = StringUtil.replace(
				sql, new String[] {"[$COMPANY_ID$]"},
				new String[] {String.valueOf(companyId)});

			if (Validator.isNotNull(keyword)) {
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(CPTaxCategory.name)", StringPool.LIKE, true,
					keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CPTaxCategory.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			if (Validator.isNotNull(keyword)) {
				QueryPos queryPos = QueryPos.getInstance(sqlQuery);

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
	public List<CPTaxCategory> findCPTaxCategoriesByCompanyId(
		long companyId, String keyword, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String[] keywords = _customSQL.keywords(keyword, true);

			String sql = _customSQL.get(
				getClass(), FIND_CP_TAX_CATEGORIES_BY_COMPANY_ID);

			sql = StringUtil.replace(
				sql, new String[] {"[$COMPANY_ID$]"},
				new String[] {String.valueOf(companyId)});

			if (Validator.isNotNull(keyword)) {
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(CPTaxCategory.name)", StringPool.LIKE, true,
					keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CPTaxCategory.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CPTaxCategoryImpl.TABLE_NAME, CPTaxCategoryImpl.class);

			if (Validator.isNotNull(keyword)) {
				QueryPos queryPos = QueryPos.getInstance(sqlQuery);

				queryPos.add(keywords, 2);
			}

			return (List<CPTaxCategory>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
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