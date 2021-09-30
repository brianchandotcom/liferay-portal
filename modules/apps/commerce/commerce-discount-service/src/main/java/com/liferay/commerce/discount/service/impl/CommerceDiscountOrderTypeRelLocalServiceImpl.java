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

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRelTable;
import com.liferay.commerce.discount.service.base.CommerceDiscountOrderTypeRelLocalServiceBaseImpl;
import com.liferay.commerce.model.CommerceOrderTypeTable;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CommerceDiscountOrderTypeRelLocalServiceImpl
	extends CommerceDiscountOrderTypeRelLocalServiceBaseImpl {

	@Override
	public CommerceDiscountOrderTypeRel addCommerceDiscountOrderTypeRel(
			long userId, long commerceDiscountId, long commerceOrderTypeId,
			int priority, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			commerceDiscountOrderTypeRelPersistence.create(
				counterLocalService.increment());

		commerceDiscountOrderTypeRel.setCompanyId(user.getCompanyId());
		commerceDiscountOrderTypeRel.setUserId(user.getUserId());
		commerceDiscountOrderTypeRel.setUserName(user.getFullName());
		commerceDiscountOrderTypeRel.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountOrderTypeRel.setCommerceOrderTypeId(
			commerceOrderTypeId);
		commerceDiscountOrderTypeRel.setPriority(priority);
		commerceDiscountOrderTypeRel.setExpandoBridgeAttributes(serviceContext);

		commerceDiscountOrderTypeRel =
			commerceDiscountOrderTypeRelPersistence.update(
				commerceDiscountOrderTypeRel);

		reindexCommerceDiscount(commerceDiscountId);

		return commerceDiscountOrderTypeRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceDiscountOrderTypeRel deleteCommerceDiscountOrderTypeRel(
			CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel)
		throws PortalException {

		commerceDiscountOrderTypeRelPersistence.remove(
			commerceDiscountOrderTypeRel);

		_expandoRowLocalService.deleteRows(
			commerceDiscountOrderTypeRel.getCommerceDiscountOrderTypeRelId());

		reindexCommerceDiscount(
			commerceDiscountOrderTypeRel.getCommerceDiscountId());

		return commerceDiscountOrderTypeRel;
	}

	@Override
	public CommerceDiscountOrderTypeRel deleteCommerceDiscountOrderTypeRel(
			long commerceDiscountOrderTypeRelId)
		throws PortalException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			commerceDiscountOrderTypeRelPersistence.findByPrimaryKey(
				commerceDiscountOrderTypeRelId);

		return commerceDiscountOrderTypeRelLocalService.
			deleteCommerceDiscountOrderTypeRel(commerceDiscountOrderTypeRel);
	}

	@Override
	public void deleteCommerceDiscountOrderTypeRels(long commerceDiscountId) {
		commerceDiscountOrderTypeRelPersistence.removeByCommerceDiscountId(
			commerceDiscountId);
	}

	@Override
	public CommerceDiscountOrderTypeRel fetchCommerceDiscountOrderTypeRel(
		long commerceDiscountId, long commerceOrderTypeId) {

		return commerceDiscountOrderTypeRelPersistence.fetchByCDI_COTI(
			commerceDiscountId, commerceOrderTypeId);
	}

	@Override
	public CommerceDiscountOrderTypeRel getCommerceDiscountOrderTypeRel(
			long commerceDiscountOrderTypeRelId)
		throws PortalException {

		return commerceDiscountOrderTypeRelPersistence.findByPrimaryKey(
			commerceDiscountOrderTypeRelId);
	}

	@Override
	public List<CommerceDiscountOrderTypeRel> getCommerceDiscountOrderTypeRels(
		long commerceDiscountId) {

		return commerceDiscountOrderTypeRelPersistence.findByCommerceDiscountId(
			commerceDiscountId);
	}

	@Override
	public List<CommerceDiscountOrderTypeRel> getCommerceDiscountOrderTypeRels(
			long commerceDiscountId, String name, int start, int end,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws PortalException {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommerceDiscountOrderTypeRelTable.INSTANCE),
				commerceDiscountId, name
			).orderBy(
				CommerceDiscountOrderTypeRelTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));
	}

	@Override
	public int getCommerceDiscountOrderTypeRelsCount(
			long commerceDiscountId, String name)
		throws PortalException {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommerceDiscountOrderTypeRelTable.INSTANCE.
						commerceDiscountOrderTypeRelId),
				commerceDiscountId, name));
	}

	protected void reindexCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		Indexer<CommerceDiscount> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceDiscount.class);

		indexer.reindex(CommerceDiscount.class.getName(), commerceDiscountId);
	}

	private GroupByStep _getGroupByStep(
			FromStep fromStep, Long commerceDiscountId, String keywords)
		throws PortalException {

		JoinStep joinStep = fromStep.from(
			CommerceDiscountOrderTypeRelTable.INSTANCE
		).innerJoinON(
			CommerceOrderTypeTable.INSTANCE,
			CommerceOrderTypeTable.INSTANCE.commerceOrderTypeId.eq(
				CommerceDiscountOrderTypeRelTable.INSTANCE.commerceOrderTypeId)
		);

		return joinStep.where(
			() -> {
				Predicate predicate =
					CommerceDiscountOrderTypeRelTable.INSTANCE.
						commerceDiscountId.eq(commerceDiscountId);

				if (Validator.isNotNull(keywords)) {
					predicate = predicate.and(
						Predicate.withParentheses(
							_customSQL.getKeywordsPredicate(
								DSLFunctionFactoryUtil.lower(
									CommerceOrderTypeTable.INSTANCE.name),
								_customSQL.keywords(keywords, true))));
				}

				return predicate;
			});
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

}