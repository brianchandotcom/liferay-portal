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
import com.liferay.change.tracking.service.persistence.CTCollectionFinder;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

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
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CTCollection> filterFindByC_S(
		long companyId, int status, String keywords, int start, int end,
		OrderByComparator<CTCollection> obc) {

		return applyC_S(
			DSLQueryFactoryUtil.select(), companyId, status, keywords, obc,
			start, end);
	}

	protected <T> T applyC_S(
		FromStep fromStep, long companyId, int status, String keywords,
		OrderByComparator<CTCollection> obc, int start, int end) {

		DSLQuery dslQuery = fromStep.from(
			CTCollectionTable.INSTANCE
		).where(
			_getC_SPredicate(companyId, status, keywords)
		).orderBy(
			CTCollectionTable.INSTANCE, obc
		).limit(
			start, end
		);

		return ctCollectionPersistence.dslQuery(dslQuery);
	}

	private Predicate _getC_SPredicate(
		long companyId, int status, String keywords) {

		Predicate predicate = CTCollectionTable.INSTANCE.companyId.eq(
			companyId);

		if (status != WorkflowConstants.STATUS_ANY) {
			predicate = predicate.and(
				CTCollectionTable.INSTANCE.status.eq(status));
		}

		Predicate keywordsPredicate = null;

		for (String keyword :
				_customSQL.keywords(keywords, true, WildcardMode.SURROUND)) {

			if (keyword == null) {
				continue;
			}

			Predicate keywordPredicate = DSLFunctionFactoryUtil.lower(
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
				keywordsPredicate = keywordsPredicate.or(keywordPredicate);
			}
		}

		if (keywordsPredicate != null) {
			predicate = predicate.and(keywordsPredicate.withParentheses());
		}

		return predicate.and(
			_inlineSQLHelper.getPermissionWherePredicate(
				CTCollection.class, CTCollectionTable.INSTANCE.ctCollectionId));
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

}