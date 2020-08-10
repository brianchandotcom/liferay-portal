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
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.base.CommerceDiscountRelLocalServiceBaseImpl;
import com.liferay.commerce.discount.util.comparator.CommerceDiscountRelCreateDateComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountRelLocalServiceImpl
	extends CommerceDiscountRelLocalServiceBaseImpl {

	@Override
	public CommerceDiscountRel addCommerceDiscountRel(
			long commerceDiscountId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce discount rel

		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceDiscountRelId = counterLocalService.increment();

		CommerceDiscountRel commerceDiscountRel =
			commerceDiscountRelPersistence.create(commerceDiscountRelId);

		commerceDiscountRel.setCompanyId(user.getCompanyId());
		commerceDiscountRel.setUserId(user.getUserId());
		commerceDiscountRel.setUserName(user.getFullName());
		commerceDiscountRel.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountRel.setClassName(className);
		commerceDiscountRel.setClassPK(classPK);

		commerceDiscountRel = commerceDiscountRelPersistence.update(
			commerceDiscountRel);

		// Commerce discount

		reindexCommerceDiscount(commerceDiscountId);

		return commerceDiscountRel;
	}

	@Override
	public CommerceDiscountRel deleteCommerceDiscountRel(
			CommerceDiscountRel commerceDiscountRel)
		throws PortalException {

		// Commerce discount rel

		commerceDiscountRelPersistence.remove(commerceDiscountRel);

		// Commerce discount

		reindexCommerceDiscount(commerceDiscountRel.getCommerceDiscountId());

		return commerceDiscountRel;
	}

	@Override
	public CommerceDiscountRel deleteCommerceDiscountRel(
			long commerceDiscountRelId)
		throws PortalException {

		CommerceDiscountRel commerceDiscountRel =
			commerceDiscountRelPersistence.findByPrimaryKey(
				commerceDiscountRelId);

		return commerceDiscountRelLocalService.deleteCommerceDiscountRel(
			commerceDiscountRel);
	}

	@Override
	public void deleteCommerceDiscountRels(long commerceDiscountId)
		throws PortalException {

		List<CommerceDiscountRel> commerceDiscountRels =
			commerceDiscountRelPersistence.findByCommerceDiscountId(
				commerceDiscountId);

		for (CommerceDiscountRel commerceDiscountRel : commerceDiscountRels) {
			commerceDiscountRelLocalService.deleteCommerceDiscountRel(
				commerceDiscountRel);
		}
	}

	@Override
	public void deleteCommerceDiscountRels(String className, long classPK)
		throws PortalException {

		List<CommerceDiscountRel> commerceDiscountRels =
			commerceDiscountRelPersistence.findByCN_CPK(
				classNameLocalService.getClassNameId(className), classPK);

		for (CommerceDiscountRel commerceDiscountRel : commerceDiscountRels) {
			commerceDiscountRelLocalService.deleteCommerceDiscountRel(
				commerceDiscountRel);
		}
	}

	@Override
	public CommerceDiscountRel fetchCommerceDiscountRel(
		String className, long classPK) {

		return commerceDiscountRelPersistence.fetchByCN_CPK_First(
			classNameLocalService.getClassNameId(className), classPK,
			new CommerceDiscountRelCreateDateComparator());
	}

	@Override
	public List<CommerceDiscountRel> getCategoriesByCommerceDiscountId(
		long commerceDiscountId, String name, int start, int end) {

		return commerceDiscountRelFinder.findCategoriesByCommerceDiscountId(
			commerceDiscountId, name, start, end);
	}

	@Override
	public int getCategoriesByCommerceDiscountIdCount(
		long commerceDiscountId, String name) {

		return commerceDiscountRelFinder.countCategoriesByCommerceDiscountId(
			commerceDiscountId, name);
	}

	@Override
	public long[] getClassPKs(long commerceDiscountId, String className) {
		return ListUtil.toLongArray(
			commerceDiscountRelPersistence.findByCD_CN(
				commerceDiscountId,
				classNameLocalService.getClassNameId(className)),
			CommerceDiscountRel::getClassPK);
	}

	@Override
	public List<CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className) {

		return commerceDiscountRelPersistence.findByCD_CN(
			commerceDiscountId,
			classNameLocalService.getClassNameId(className));
	}

	@Override
	public List<CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className, int start, int end,
		OrderByComparator<CommerceDiscountRel> orderByComparator) {

		return commerceDiscountRelPersistence.findByCD_CN(
			commerceDiscountId, classNameLocalService.getClassNameId(className),
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountRelsCount(
		long commerceDiscountId, String className) {

		return commerceDiscountRelPersistence.countByCD_CN(
			commerceDiscountId,
			classNameLocalService.getClassNameId(className));
	}

	@Override
	public List<CommerceDiscountRel>
		getCommercePricingClassesByCommerceDiscountId(
			long commerceDiscountId, String title, int start, int end) {

		return commerceDiscountRelFinder.findPricingClassesByCommerceDiscountId(
			commerceDiscountId, title, start, end);
	}

	@Override
	public int getCommercePricingClassesByCommerceDiscountIdCount(
		long commerceDiscountId, String title) {

		return commerceDiscountRelFinder.
			countPricingClassesByCommerceDiscountId(commerceDiscountId, title);
	}

	@Override
	public List<CommerceDiscountRel> getCPDefinitionsByCommerceDiscountId(
		long commerceDiscountId, String name, String languageId, int start,
		int end) {

		return commerceDiscountRelFinder.findCPDefinitionsByCommerceDiscountId(
			commerceDiscountId, name, languageId, start, end);
	}

	@Override
	public int getCPDefinitionsByCommerceDiscountIdCount(
		long commerceDiscountId, String name, String languageId) {

		return commerceDiscountRelFinder.countCPDefinitionsByCommerceDiscountId(
			commerceDiscountId, name, languageId);
	}

	protected void reindexCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			commerceDiscountLocalService.getCommerceDiscount(
				commerceDiscountId);

		Indexer<CommerceDiscount> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceDiscount.class);

		indexer.reindex(commerceDiscount);
	}

}