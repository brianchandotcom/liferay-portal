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

package com.liferay.commerce.price.list.service.impl;

import com.liferay.commerce.model.CommerceOrderTypeTable;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRelTable;
import com.liferay.commerce.price.list.service.base.CommercePriceListOrderTypeRelLocalServiceBaseImpl;
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
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListOrderTypeRelLocalServiceImpl
	extends CommercePriceListOrderTypeRelLocalServiceBaseImpl {

	@Override
	public CommercePriceListOrderTypeRel addCommercePriceListOrderTypeRel(
			long userId, long commercePriceListId, long commerceOrderTypeId,
			int priority, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			commercePriceListOrderTypeRelPersistence.create(
				counterLocalService.increment());

		commercePriceListOrderTypeRel.setCompanyId(user.getCompanyId());
		commercePriceListOrderTypeRel.setUserId(user.getUserId());
		commercePriceListOrderTypeRel.setUserName(user.getFullName());
		commercePriceListOrderTypeRel.setCommercePriceListId(
			commercePriceListId);
		commercePriceListOrderTypeRel.setCommerceOrderTypeId(
			commerceOrderTypeId);
		commercePriceListOrderTypeRel.setPriority(priority);
		commercePriceListOrderTypeRel.setExpandoBridgeAttributes(
			serviceContext);

		commercePriceListOrderTypeRel =
			commercePriceListOrderTypeRelPersistence.update(
				commercePriceListOrderTypeRel);

		reindexCommercePriceList(commercePriceListId);

		commercePriceListLocalService.cleanPriceListCache(
			serviceContext.getCompanyId());

		return commercePriceListOrderTypeRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePriceListOrderTypeRel deleteCommercePriceListOrderTypeRel(
			CommercePriceListOrderTypeRel commercePriceListOrderTypeRel)
		throws PortalException {

		commercePriceListOrderTypeRelPersistence.remove(
			commercePriceListOrderTypeRel);

		_expandoRowLocalService.deleteRows(
			commercePriceListOrderTypeRel.getCommercePriceListOrderTypeRelId());

		reindexCommercePriceList(
			commercePriceListOrderTypeRel.getCommercePriceListId());

		commercePriceListLocalService.cleanPriceListCache(
			commercePriceListOrderTypeRel.getCompanyId());

		return commercePriceListOrderTypeRel;
	}

	@Override
	public CommercePriceListOrderTypeRel deleteCommercePriceListOrderTypeRel(
			long commercePriceListOrderTypeRelId)
		throws PortalException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			commercePriceListOrderTypeRelPersistence.findByPrimaryKey(
				commercePriceListOrderTypeRelId);

		return commercePriceListOrderTypeRelLocalService.
			deleteCommercePriceListOrderTypeRel(commercePriceListOrderTypeRel);
	}

	@Override
	public void deleteCommercePriceListOrderTypeRels(long commercePriceListId) {
		commercePriceListOrderTypeRelPersistence.removeByCommercePriceListId(
			commercePriceListId);
	}

	@Override
	public CommercePriceListOrderTypeRel fetchCommercePriceListOrderTypeRel(
		long commercePriceListId, long commerceOrderTypeId) {

		return commercePriceListOrderTypeRelPersistence.fetchByCPI_COTI(
			commercePriceListId, commerceOrderTypeId);
	}

	@Override
	public CommercePriceListOrderTypeRel getCommercePriceListOrderTypeRel(
			long commercePriceListOrderTypeRelId)
		throws PortalException {

		return commercePriceListOrderTypeRelPersistence.findByPrimaryKey(
			commercePriceListOrderTypeRelId);
	}

	@Override
	public List<CommercePriceListOrderTypeRel>
		getCommercePriceListOrderTypeRels(long commercePriceListId) {

		return commercePriceListOrderTypeRelPersistence.
			findByCommercePriceListId(commercePriceListId);
	}

	@Override
	public List<CommercePriceListOrderTypeRel>
			getCommercePriceListOrderTypeRels(
				long commercePriceListId, String name, int start, int end,
				OrderByComparator<CommercePriceListOrderTypeRel>
					orderByComparator)
		throws PortalException {

		return dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListOrderTypeRelTable.INSTANCE),
				commercePriceListId, name
			).orderBy(
				CommercePriceListOrderTypeRelTable.INSTANCE, orderByComparator
			).limit(
				start, end
			));
	}

	@Override
	public int getCommercePriceListOrderTypeRelsCount(
			long commercePriceListId, String name)
		throws PortalException {

		return dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.countDistinct(
					CommercePriceListOrderTypeRelTable.INSTANCE.
						commercePriceListOrderTypeRelId),
				commercePriceListId, name));
	}

	protected void reindexCommercePriceList(long commercePriceListId)
		throws PortalException {

		Indexer<CommercePriceList> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

		indexer.reindex(CommercePriceList.class.getName(), commercePriceListId);
	}

	private GroupByStep _getGroupByStep(
			FromStep fromStep, Long commercePriceListId, String keywords)
		throws PortalException {

		JoinStep joinStep = fromStep.from(
			CommercePriceListOrderTypeRelTable.INSTANCE
		).innerJoinON(
			CommerceOrderTypeTable.INSTANCE,
			CommerceOrderTypeTable.INSTANCE.commerceOrderTypeId.eq(
				CommercePriceListOrderTypeRelTable.INSTANCE.commerceOrderTypeId)
		);

		return joinStep.where(
			() -> {
				Predicate predicate =
					CommercePriceListOrderTypeRelTable.INSTANCE.
						commercePriceListId.eq(commercePriceListId);

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