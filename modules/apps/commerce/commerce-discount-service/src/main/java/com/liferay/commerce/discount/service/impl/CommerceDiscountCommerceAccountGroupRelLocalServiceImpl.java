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
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.base.CommerceDiscountCommerceAccountGroupRelLocalServiceBaseImpl;
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
public class CommerceDiscountCommerceAccountGroupRelLocalServiceImpl
	extends CommerceDiscountCommerceAccountGroupRelLocalServiceBaseImpl {

	@Override
	public CommerceDiscountCommerceAccountGroupRel
			addCommerceDiscountCommerceAccountGroupRel(
				long commerceDiscountId, long commerceAccountGroupId,
				ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceDiscountCommerceAccountGroupRelId =
			counterLocalService.increment();

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				commerceDiscountCommerceAccountGroupRelPersistence.create(
					commerceDiscountCommerceAccountGroupRelId);

		commerceDiscountCommerceAccountGroupRel.setCompanyId(
			user.getCompanyId());
		commerceDiscountCommerceAccountGroupRel.setUserId(user.getUserId());
		commerceDiscountCommerceAccountGroupRel.setUserName(user.getFullName());
		commerceDiscountCommerceAccountGroupRel.setCommerceDiscountId(
			commerceDiscountId);
		commerceDiscountCommerceAccountGroupRel.setCommerceAccountGroupId(
			commerceAccountGroupId);

		commerceDiscountCommerceAccountGroupRel =
			commerceDiscountCommerceAccountGroupRelPersistence.update(
				commerceDiscountCommerceAccountGroupRel);

		// Commerce discount

		reindexCommerceDiscount(commerceDiscountId);

		return commerceDiscountCommerceAccountGroupRel;
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRel
			deleteCommerceDiscountCommerceAccountGroupRel(
				CommerceDiscountCommerceAccountGroupRel
					commerceDiscountCommerceAccountGroupRel)
		throws PortalException {

		commerceDiscountCommerceAccountGroupRelPersistence.remove(
			commerceDiscountCommerceAccountGroupRel);

		// Commerce discount

		reindexCommerceDiscount(
			commerceDiscountCommerceAccountGroupRel.getCommerceDiscountId());

		return commerceDiscountCommerceAccountGroupRel;
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRel
			deleteCommerceDiscountCommerceAccountGroupRel(
				long commerceDiscountCommerceAccountGroupRelId)
		throws PortalException {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				commerceDiscountCommerceAccountGroupRelPersistence.
					findByPrimaryKey(commerceDiscountCommerceAccountGroupRelId);

		return commerceDiscountCommerceAccountGroupRelLocalService.
			deleteCommerceDiscountCommerceAccountGroupRel(
				commerceDiscountCommerceAccountGroupRel);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public void
		deleteCommerceDiscountCommerceAccountGroupRelsBycommerceAccountGroupId(
			long commerceAccountGroupId) {

		commerceDiscountCommerceAccountGroupRelPersistence.
			removeByCommerceAccountGroupId(commerceAccountGroupId);
	}

	@Override
	public void
			deleteCommerceDiscountCommerceAccountGroupRelsByCommerceDiscountId(
				long commerceDiscountId)
		throws PortalException {

		List<CommerceDiscountCommerceAccountGroupRel>
			commerceDiscountCommerceAccountGroupRels =
				commerceDiscountCommerceAccountGroupRelPersistence.
					findByCommerceDiscountId(commerceDiscountId);

		for (CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel :
					commerceDiscountCommerceAccountGroupRels) {

			commerceDiscountCommerceAccountGroupRelLocalService.
				deleteCommerceDiscountCommerceAccountGroupRel(
					commerceDiscountCommerceAccountGroupRel);
		}
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRel
		fetchCommerceDiscountCommerceAccountGroupRel(
			long commerceDiscountId, long commerceAccountGroupId) {

		return commerceDiscountCommerceAccountGroupRelPersistence.fetchByC_C(
			commerceDiscountId, commerceAccountGroupId);
	}

	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		getCommerceDiscountCommerceAccountGroupRels(
			long commerceDiscountId, int start, int end,
			OrderByComparator<CommerceDiscountCommerceAccountGroupRel>
				orderByComparator) {

		return commerceDiscountCommerceAccountGroupRelPersistence.
			findByCommerceDiscountId(
				commerceDiscountId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceDiscountCommerceAccountGroupRel>
		getCommerceDiscountCommerceAccountGroupRels(
			long commerceDiscountId, String name, int start, int end) {

		return commerceDiscountCommerceAccountGroupRelFinder.
			findByCommerceDiscountId(commerceDiscountId, name, start, end);
	}

	@Override
	public int getCommerceDiscountCommerceAccountGroupRelsCount(
		long commerceDiscountId) {

		return commerceDiscountCommerceAccountGroupRelPersistence.
			countByCommerceDiscountId(commerceDiscountId);
	}

	@Override
	public int getCommerceDiscountCommerceAccountGroupRelsCount(
		long commerceDiscountId, String name) {

		return commerceDiscountCommerceAccountGroupRelFinder.
			countByCommerceDiscountId(commerceDiscountId, name);
	}

	protected void reindexCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		Indexer<CommerceDiscount> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceDiscount.class);

		indexer.reindex(CommerceDiscount.class.getName(), commerceDiscountId);
	}

}