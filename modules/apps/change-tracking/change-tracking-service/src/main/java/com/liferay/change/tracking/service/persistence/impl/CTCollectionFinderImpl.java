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

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.model.impl.CTCollectionImpl;
import com.liferay.change.tracking.service.persistence.CTCollectionFinder;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CTCollectionFinder.class)
public class CTCollectionFinderImpl
	extends CTCollectionFinderBaseImpl implements CTCollectionFinder {

	@Override
	public int filterCountByC_S(long companyId, int status, String keywords) {
		return applyC_S(
			DSLQueryFactoryUtil.count(), companyId, status, keywords, null,
			sqlQuery -> {
				sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				Iterator<Long> itr = sqlQuery.iterate();

				if (itr.hasNext()) {
					Long count = itr.next();

					if (count != null) {
						return count.intValue();
					}
				}

				return 0;
			});
	}

	@Override
	public List<CTCollection> filterFindByC_S(
		long companyId, int status, String keywords, int start, int end,
		OrderByComparator<CTCollection> obc) {

		return applyC_S(
			DSLQueryFactoryUtil.select(), companyId, status, keywords, obc,
			sqlQuery -> {
				sqlQuery.addEntity(
					CTCollectionTable.INSTANCE.getName(),
					CTCollectionImpl.class);

				return (List<CTCollection>)QueryUtil.list(
					sqlQuery, getDialect(), start, end);
			});
	}

	protected <T> T applyC_S(
		FromStep fromStep, long companyId, int status, String keywords,
		OrderByComparator<CTCollection> obc,
		Function<SQLQuery, T> sqlQueryFunction) {

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				fromStep.from(
					CTCollectionTable.INSTANCE
				).where(
					() -> {
						Predicate predicate =
							CTCollectionTable.INSTANCE.companyId.eq(companyId);

						if (status != WorkflowConstants.STATUS_ANY) {
							predicate = predicate.and(
								CTCollectionTable.INSTANCE.status.eq(status));
						}

						Predicate keywordsPredicate = null;

						for (String keyword :
								_customSQL.keywords(
									keywords, true, WildcardMode.SURROUND)) {

							if (keyword == null) {
								continue;
							}

							Predicate keywordPredicate =
								DSLFunctionFactoryUtil.lower(
									CTCollectionTable.INSTANCE.name
								).like(
									keyword
								).or(
									DSLFunctionFactoryUtil.lower(
										CTCollectionTable.INSTANCE.description
									).like(
										keyword
									)
								);

							if (keywordsPredicate == null) {
								keywordsPredicate = keywordPredicate;
							}
							else {
								keywordsPredicate = keywordsPredicate.or(
									keywordPredicate);
							}
						}

						if (keywordsPredicate != null) {
							predicate = predicate.and(
								keywordsPredicate.withParentheses());
						}

						return predicate.and(
							_inlineSQLHelper.getPermissionWherePredicate(
								CTCollection.class,
								CTCollectionTable.INSTANCE.ctCollectionId));
					}
				).orderBy(
					CTCollectionTable.INSTANCE, obc
				));

			return sqlQueryFunction.apply(sqlQuery);
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

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

}