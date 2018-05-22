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

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListUserSegmentEntryRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CommercePriceListUserSegmentEntryRelLocalServiceImpl
	extends CommercePriceListUserSegmentEntryRelLocalServiceBaseImpl {

	@Override
	public CommercePriceListUserSegmentEntryRel
			addCommercePriceListUserSegmentEntryRel(
				long commercePriceListId, long commerceUserSegmentEntryId,
				int order, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commercePriceListUserSegmentEntryRelId =
			counterLocalService.increment();

		CommercePriceListUserSegmentEntryRel
			commercePriceListUserSegmentEntryRel =
				commercePriceListUserSegmentEntryRelPersistence.create(
					commercePriceListUserSegmentEntryRelId);

		commercePriceListUserSegmentEntryRel.setUuid(serviceContext.getUuid());
		commercePriceListUserSegmentEntryRel.setGroupId(groupId);
		commercePriceListUserSegmentEntryRel.setCompanyId(user.getCompanyId());
		commercePriceListUserSegmentEntryRel.setUserId(user.getUserId());
		commercePriceListUserSegmentEntryRel.setUserName(user.getFullName());
		commercePriceListUserSegmentEntryRel.setCommercePriceListId(
			commercePriceListId);
		commercePriceListUserSegmentEntryRel.setCommerceUserSegmentEntryId(
			commerceUserSegmentEntryId);
		commercePriceListUserSegmentEntryRel.setOrder(order);
		commercePriceListUserSegmentEntryRel.setExpandoBridgeAttributes(
			serviceContext);

		//Commerce price list

		reindexCommerceUserSegmentEntry(commercePriceListId);

		//Cache

		commercePriceListLocalService.cleanPriceListCache(groupId);

		return commercePriceListUserSegmentEntryRelPersistence.update(
			commercePriceListUserSegmentEntryRel);
	}

	@Override
	public CommercePriceListUserSegmentEntryRel
			deleteCommercePriceListUserSegmentEntryRel(
				CommercePriceListUserSegmentEntryRel
					commercePriceListUserSegmentEntryRel)
		throws PortalException {

		commercePriceListUserSegmentEntryRelPersistence.remove(
			commercePriceListUserSegmentEntryRel);

		//Commerce price list

		reindexCommerceUserSegmentEntry(
			commercePriceListUserSegmentEntryRel.getCommercePriceListId());

		//Cache

		commercePriceListLocalService.cleanPriceListCache(
			commercePriceListUserSegmentEntryRel.getGroupId());

		return commercePriceListUserSegmentEntryRel;
	}

	@Override
	public CommercePriceListUserSegmentEntryRel
			deleteCommercePriceListUserSegmentEntryRel(
				long commercePriceListUserSegmentEntryRelId)
		throws PortalException {

		CommercePriceListUserSegmentEntryRel
			commercePriceListUserSegmentEntryRel =
				commercePriceListUserSegmentEntryRelPersistence.
					findByPrimaryKey(commercePriceListUserSegmentEntryRelId);

		return commercePriceListUserSegmentEntryRelLocalService.
			deleteCommercePriceListUserSegmentEntryRel(
				commercePriceListUserSegmentEntryRel);
	}

	@Override
	public void deleteCommercePriceListUserSegmentEntryRels(
		long commercePriceListId) {

		commercePriceListUserSegmentEntryRelPersistence.
			removeByCommercePriceListId(commercePriceListId);
	}

	@Override
	public CommercePriceListUserSegmentEntryRel
		fetchCommercePriceListUserSegmentEntryRel(
			long commercePriceListId, long commerceUserSegmentEntryId) {

		return commercePriceListUserSegmentEntryRelPersistence.fetchByC_C(
			commercePriceListId, commerceUserSegmentEntryId);
	}

	@Override
	public List<CommercePriceListUserSegmentEntryRel>
		getCommercePriceListUserSegmentEntryRels(long commercePriceListId) {

		return commercePriceListUserSegmentEntryRelPersistence.
			findByCommercePriceListId(commercePriceListId);
	}

	@Override
	public List<CommercePriceListUserSegmentEntryRel>
		getCommercePriceListUserSegmentEntryRels(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListUserSegmentEntryRel>
				orderByComparator) {

		return commercePriceListUserSegmentEntryRelPersistence.
			findByCommercePriceListId(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListUserSegmentEntryRelsCount(
		long commercePriceListId) {

		return commercePriceListUserSegmentEntryRelPersistence.
			countByCommercePriceListId(commercePriceListId);
	}

	@Override
	public CommercePriceListUserSegmentEntryRel
			updateCommercePriceListUserSegmentEntryRel(
				long commercePriceListUserSegmentEntryRelId, int order,
				ServiceContext serviceContext)
		throws PortalException {

		CommercePriceListUserSegmentEntryRel
			commercePriceListUserSegmentEntryRel =
				commercePriceListUserSegmentEntryRelPersistence.
					findByPrimaryKey(commercePriceListUserSegmentEntryRelId);

		commercePriceListUserSegmentEntryRel.setOrder(order);
		commercePriceListUserSegmentEntryRel.setExpandoBridgeAttributes(
			serviceContext);

		//Commerce price list

		reindexCommerceUserSegmentEntry(
			commercePriceListUserSegmentEntryRel.getCommercePriceListId());

		//Cache

		commercePriceListLocalService.cleanPriceListCache(
			serviceContext.getScopeGroupId());

		return commercePriceListUserSegmentEntryRelPersistence.update(
			commercePriceListUserSegmentEntryRel);
	}

	protected void reindexCommerceUserSegmentEntry(long commercePriceListId)
		throws PortalException {

		Indexer<CommercePriceList> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

		indexer.reindex(CommercePriceList.class.getName(), commercePriceListId);
	}

}