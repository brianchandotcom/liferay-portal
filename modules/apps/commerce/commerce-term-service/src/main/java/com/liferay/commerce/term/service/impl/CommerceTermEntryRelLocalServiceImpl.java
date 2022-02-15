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

package com.liferay.commerce.term.service.impl;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceOrderTypeTable;
import com.liferay.commerce.term.exception.DuplicateCommerceTermEntryRelException;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.model.CommerceTermEntryRelTable;
import com.liferay.commerce.term.model.CommerceTermEntryTable;
import com.liferay.commerce.term.service.base.CommerceTermEntryRelLocalServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.term.model.CommerceTermEntryRel",
	service = AopService.class
)
public class CommerceTermEntryRelLocalServiceImpl
	extends CommerceTermEntryRelLocalServiceBaseImpl {

	@Override
	public CommerceTermEntryRel addCommerceTermEntryRel(
			long userId, String className, long classPK,
			long commerceTermEntryId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		_validate(classNameId, classPK, commerceTermEntryId);

		CommerceTermEntryRel commerceTermEntryRel =
			commerceTermEntryRelPersistence.create(
				counterLocalService.increment());

		User user = userLocalService.getUser(userId);

		commerceTermEntryRel.setCompanyId(user.getCompanyId());
		commerceTermEntryRel.setUserId(user.getUserId());
		commerceTermEntryRel.setUserName(user.getFullName());

		commerceTermEntryRel.setClassNameId(classNameId);
		commerceTermEntryRel.setClassPK(classPK);
		commerceTermEntryRel.setCommerceTermEntryId(commerceTermEntryId);

		commerceTermEntryRel = commerceTermEntryRelPersistence.update(
			commerceTermEntryRel);

		reindexCommerceTermEntry(commerceTermEntryId);

		return commerceTermEntryRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceTermEntryRel deleteCommerceTermEntryRel(
			CommerceTermEntryRel commerceTermEntryRel)
		throws PortalException {

		commerceTermEntryRelPersistence.remove(commerceTermEntryRel);

		reindexCommerceTermEntry(commerceTermEntryRel.getCommerceTermEntryId());

		return commerceTermEntryRel;
	}

	@Override
	public CommerceTermEntryRel deleteCommerceTermEntryRel(
			long commerceTermEntryRelId)
		throws PortalException {

		CommerceTermEntryRel commerceTermEntryRel =
			commerceTermEntryRelPersistence.findByPrimaryKey(
				commerceTermEntryRelId);

		return commerceTermEntryRelLocalService.deleteCommerceTermEntryRel(
			commerceTermEntryRel);
	}

	@Override
	public void deleteCommerceTermEntryRels(long commerceTermEntryId)
		throws PortalException {

		List<CommerceTermEntryRel> commerceTermEntryRels =
			commerceTermEntryRelPersistence.findByCommerceTermEntryId(
				commerceTermEntryId);

		for (CommerceTermEntryRel commerceTermEntryRel :
				commerceTermEntryRels) {

			commerceTermEntryRelLocalService.deleteCommerceTermEntryRel(
				commerceTermEntryRel);
		}
	}

	@Override
	public void deleteCommerceTermEntryRels(
			String className, long commerceTermEntryId)
		throws PortalException {

		List<CommerceTermEntryRel> commerceTermEntryRels =
			commerceTermEntryRelPersistence.findByC_C(
				classNameLocalService.getClassNameId(className),
				commerceTermEntryId);

		for (CommerceTermEntryRel commerceTermEntryRel :
				commerceTermEntryRels) {

			commerceTermEntryRelLocalService.deleteCommerceTermEntryRel(
				commerceTermEntryRel);
		}
	}

	@Override
	public CommerceTermEntryRel fetchCommerceTermEntryRel(
		String className, long classPK, long commerceTermEntryId) {

		return commerceTermEntryRelPersistence.fetchByC_C_C(
			classNameLocalService.getClassNameId(className), classPK,
			commerceTermEntryId);
	}

	@Override
	public List<CommerceTermEntryRel> getCommerceOrderTypeCommerceTermEntryRels(
		long commerceTermEntryId, String keywords, int start, int end) {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceTermEntryRelTable.INSTANCE),
				CommerceOrderTypeTable.INSTANCE,
				CommerceOrderTypeTable.INSTANCE.commerceOrderTypeId.eq(
					CommerceTermEntryRelTable.INSTANCE.classPK),
				commerceTermEntryId, CommerceOrderType.class.getName(),
				keywords, CommerceOrderTypeTable.INSTANCE.name
			).limit(
				start, end
			));
	}

	@Override
	public int getCommerceOrderTypeCommerceTermEntryRelsCount(
		long commerceTermEntryId, String keywords) {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceTermEntryRelTable.INSTANCE.commerceTermEntryRelId),
				CommerceOrderTypeTable.INSTANCE,
				CommerceOrderTypeTable.INSTANCE.commerceOrderTypeId.eq(
					CommerceTermEntryRelTable.INSTANCE.classPK),
				commerceTermEntryId, CommerceOrderType.class.getName(),
				keywords, CommerceOrderTypeTable.INSTANCE.name));
	}

	@Override
	public List<CommerceTermEntryRel> getCommerceTermEntryRels(
		long commerceTermEntryId) {

		return commerceTermEntryRelPersistence.findByCommerceTermEntryId(
			commerceTermEntryId);
	}

	@Override
	public List<CommerceTermEntryRel> getCommerceTermEntryRels(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return commerceTermEntryRelPersistence.findByCommerceTermEntryId(
			commerceTermEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTermEntryRelsCount(long commerceTermEntryId) {
		return commerceTermEntryRelPersistence.countByCommerceTermEntryId(
			commerceTermEntryId);
	}

	protected void reindexCommerceTermEntry(long commerceTermEntryId)
		throws PortalException {

		Indexer<CommerceTermEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceTermEntry.class);

		indexer.reindex(CommerceTermEntry.class.getName(), commerceTermEntryId);
	}

	private GroupByStep _getGroupByStep(
		FromStep fromStep, Table innerJoinTable, Predicate innerJoinPredicate,
		Long commerceTermEntryId, String className, String keywords,
		Expression<String> keywordsPredicateExpression) {

		JoinStep joinStep = fromStep.from(
			CommerceTermEntryRelTable.INSTANCE
		).innerJoinON(
			CommerceTermEntryTable.INSTANCE,
			CommerceTermEntryTable.INSTANCE.commerceTermEntryId.eq(
				CommerceTermEntryRelTable.INSTANCE.commerceTermEntryId)
		).innerJoinON(
			innerJoinTable, innerJoinPredicate
		);

		return joinStep.where(
			() -> CommerceTermEntryRelTable.INSTANCE.commerceTermEntryId.eq(
				commerceTermEntryId
			).and(
				CommerceTermEntryRelTable.INSTANCE.classNameId.eq(
					classNameLocalService.getClassNameId(className))
			).and(
				() -> {
					if (Validator.isNotNull(keywords)) {
						return Predicate.withParentheses(
							_customSQL.getKeywordsPredicate(
								DSLFunctionFactoryUtil.lower(
									keywordsPredicateExpression),
								_customSQL.keywords(keywords, true)));
					}

					return null;
				}
			));
	}

	private void _validate(
			long classNameId, long classPK, long commerceTermEntryId)
		throws PortalException {

		CommerceTermEntryRel commerceTermEntryRel =
			commerceTermEntryRelPersistence.fetchByC_C_C(
				classNameId, classPK, commerceTermEntryId);

		if (commerceTermEntryRel != null) {
			throw new DuplicateCommerceTermEntryRelException();
		}
	}

	@Reference
	private CustomSQL _customSQL;

}