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
import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.service.base.CommercePriceListChannelRelLocalServiceBaseImpl;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Riccardo Alberti
 * @see CommercePriceListChannelRelLocalServiceBaseImpl
 */
public class CommercePriceListChannelRelLocalServiceImpl
	extends CommercePriceListChannelRelLocalServiceBaseImpl {

	@Override
	public CommercePriceListChannelRel addCommercePriceListChannelRel(
			long userId, long commercePriceListId, long commerceChannelId,
			int order, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelPersistence.create(
				counterLocalService.increment());

		commercePriceListChannelRel.setCompanyId(user.getCompanyId());
		commercePriceListChannelRel.setUserId(user.getUserId());
		commercePriceListChannelRel.setUserName(user.getFullName());
		commercePriceListChannelRel.setCommerceChannelId(commerceChannelId);
		commercePriceListChannelRel.setCommercePriceListId(commercePriceListId);
		commercePriceListChannelRel.setOrder(order);
		commercePriceListChannelRel.setExpandoBridgeAttributes(serviceContext);

		commercePriceListChannelRel =
			commercePriceListChannelRelPersistence.update(
				commercePriceListChannelRel);

		reindexCommercePriceList(commercePriceListId);

		commercePriceListLocalService.cleanPriceListCache(
			serviceContext.getCompanyId());

		return commercePriceListChannelRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePriceListChannelRel deleteCommercePriceListChannelRel(
			CommercePriceListChannelRel commercePriceListChannelRel)
		throws PortalException {

		commercePriceListChannelRelPersistence.remove(
			commercePriceListChannelRel);

		_expandoRowLocalService.deleteRows(
			commercePriceListChannelRel.getCommercePriceListChannelRelId());

		reindexCommercePriceList(
			commercePriceListChannelRel.getCommercePriceListId());

		commercePriceListLocalService.cleanPriceListCache(
			commercePriceListChannelRel.getCompanyId());

		return commercePriceListChannelRel;
	}

	@Override
	public CommercePriceListChannelRel deleteCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws PortalException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			commercePriceListChannelRelPersistence.findByPrimaryKey(
				commercePriceListChannelRelId);

		return commercePriceListChannelRelLocalService.
			deleteCommercePriceListChannelRel(commercePriceListChannelRel);
	}

	@Override
	public void deleteCommercePriceListChannelRels(long commercePriceListId)
		throws PortalException {

		List<CommercePriceListChannelRel> commercePriceListChannelRels =
			commercePriceListChannelRelPersistence.findByCommercePriceListId(
				commercePriceListId);

		for (CommercePriceListChannelRel commercePriceListChannelRel :
				commercePriceListChannelRels) {

			commercePriceListChannelRelLocalService.
				deleteCommercePriceListChannelRel(commercePriceListChannelRel);
		}
	}

	@Override
	public CommercePriceListChannelRel fetchCommercePriceListChannelRel(
		long commerceChannelId, long commercePriceListId) {

		return commercePriceListChannelRelPersistence.fetchByCCI_CPI(
			commerceChannelId, commercePriceListId);
	}

	@Override
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
		long commercePriceListId) {

		return commercePriceListChannelRelPersistence.findByCommercePriceListId(
			commercePriceListId);
	}

	@Override
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return commercePriceListChannelRelPersistence.findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
		long commercePriceListId, String name, int start, int end) {

		return commercePriceListChannelRelFinder.findByCommercePriceListId(
			commercePriceListId, name, start, end);
	}

	@Override
	public int getCommercePriceListChannelRelsCount(long commercePriceListId) {
		return commercePriceListChannelRelPersistence.
			countByCommercePriceListId(commercePriceListId);
	}

	@Override
	public int getCommercePriceListChannelRelsCount(
		long commercePriceListId, String name) {

		return commercePriceListChannelRelFinder.countByCommercePriceListId(
			commercePriceListId, name);
	}

	protected void reindexCommercePriceList(long commercePriceListId)
		throws PortalException {

		Indexer<CommercePriceList> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

		indexer.reindex(CommercePriceList.class.getName(), commercePriceListId);
	}

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

}