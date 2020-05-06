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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.model.CTProcessTable;
import com.liferay.change.tracking.service.base.CTProcessServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.LimitStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.background.task.model.BackgroundTaskTable;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
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
@Component(
	property = {
		"json.web.service.context.name=ct",
		"json.web.service.context.path=CTProcess"
	},
	service = AopService.class
)
public class CTProcessServiceImpl extends CTProcessServiceBaseImpl {

	@Override
	public List<CTProcess> getCTProcesses(
		long companyId, long userId, String keywords, int status, int start,
		int end, OrderByComparator<CTProcess> orderByComparator) {

		return _filterFindByC_U_N_D_S(
			companyId, userId, keywords, status, start, end, orderByComparator);
	}

	private List<CTProcess> _filterFindByC_U_N_D_S(
		long companyId, long userId, String keywords, int status, int start,
		int end, OrderByComparator<CTProcess> orderByComparator) {

		Predicate predicate = CTProcessTable.INSTANCE.companyId.eq(companyId);

		if (userId > 0) {
			predicate = predicate.and(
				CTProcessTable.INSTANCE.userId.eq(userId));
		}

		if (status != WorkflowConstants.STATUS_ANY) {
			predicate = predicate.and(
				BackgroundTaskTable.INSTANCE.status.eq(status));
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

		predicate = predicate.and(
			_inlineSQLHelper.getPermissionWherePredicate(
				CTCollection.class, CTCollectionTable.INSTANCE.ctCollectionId));

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			CTProcessTable.INSTANCE
		).from(
			CTProcessTable.INSTANCE
		).innerJoinON(
			CTCollectionTable.INSTANCE,
			CTCollectionTable.INSTANCE.ctCollectionId.eq(
				CTProcessTable.INSTANCE.ctCollectionId)
		).innerJoinON(
			BackgroundTaskTable.INSTANCE,
			BackgroundTaskTable.INSTANCE.backgroundTaskId.eq(
				CTProcessTable.INSTANCE.backgroundTaskId)
		).where(
			predicate
		).orderBy(
			orderByStep -> {
				if (orderByComparator != null) {
					LimitStep limitStep = orderByStep.orderBy(
						CTCollectionTable.INSTANCE, orderByComparator);

					if (limitStep == orderByStep) {
						return orderByStep.orderBy(
							CTProcessTable.INSTANCE, orderByComparator);
					}

					return limitStep;
				}

				return orderByStep.orderBy(
					CTProcessTable.INSTANCE.createDate.descending());
			}
		).limit(
			start, end
		);

		return ctProcessPersistence.dslQuery(dslQuery);
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

}