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

package com.liferay.commerce.pricing.service.impl;

import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.service.base.CommercePriceModifierRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Riccardo Alberti
 */
public class CommercePriceModifierRelLocalServiceImpl
	extends CommercePriceModifierRelLocalServiceBaseImpl {

	@Override
	public CommercePriceModifierRel addCommercePriceModifierRel(
			long commercePriceModifierId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce price modifier rel

		User user = userLocalService.getUser(serviceContext.getUserId());

		long commercePriceModifierRelId = counterLocalService.increment();

		CommercePriceModifierRel commercePriceModifierRel =
			commercePriceModifierRelPersistence.create(
				commercePriceModifierRelId);

		commercePriceModifierRel.setCompanyId(user.getCompanyId());
		commercePriceModifierRel.setUserId(user.getUserId());
		commercePriceModifierRel.setUserName(user.getFullName());
		commercePriceModifierRel.setCommercePriceModifierId(
			commercePriceModifierId);
		commercePriceModifierRel.setClassName(className);
		commercePriceModifierRel.setClassPK(classPK);

		return commercePriceModifierRelPersistence.update(
			commercePriceModifierRel);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePriceModifierRel deleteCommercePriceModifierRel(
			CommercePriceModifierRel commercePriceModifierRel)
		throws PortalException {

		// Commerce price modifier rel

		commercePriceModifierRelPersistence.remove(commercePriceModifierRel);

		return commercePriceModifierRel;
	}

	@Override
	public CommercePriceModifierRel deleteCommercePriceModifierRel(
			long commercePriceModifierRelId)
		throws PortalException {

		CommercePriceModifierRel commercePriceModifierRel =
			commercePriceModifierRelPersistence.findByPrimaryKey(
				commercePriceModifierRelId);

		return commercePriceModifierRelLocalService.
			deleteCommercePriceModifierRel(commercePriceModifierRel);
	}

	@Override
	public void deleteCommercePriceModifierRels(long commercePriceModifierId)
		throws PortalException {

		List<CommercePriceModifierRel> commercePriceModifierRels =
			commercePriceModifierRelPersistence.findByCommercePriceModifierId(
				commercePriceModifierId);

		for (CommercePriceModifierRel commercePriceModifierRel :
				commercePriceModifierRels) {

			commercePriceModifierRelLocalService.deleteCommercePriceModifierRel(
				commercePriceModifierRel);
		}
	}

	@Override
	public void deleteCommercePriceModifierRels(String className, long classPK)
		throws PortalException {

		List<CommercePriceModifierRel> commercePriceModifierRels =
			commercePriceModifierRelPersistence.findByCN_CPK(
				classNameLocalService.getClassNameId(className), classPK);

		for (CommercePriceModifierRel commercePriceModifierRel :
				commercePriceModifierRels) {

			commercePriceModifierRelLocalService.deleteCommercePriceModifierRel(
				commercePriceModifierRel);
		}
	}

	@Override
	public CommercePriceModifierRel fetchCommercePriceModifierRel(
		long commercePriceModifierId, String className, long classPK) {

		return commercePriceModifierRelPersistence.fetchByCPM_CN_CPK(
			commercePriceModifierId,
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public List<CommercePriceModifierRel>
		getCategoriesCommercePriceModifierRels(
			long commercePriceModifierId, String name, int start, int end) {

		return commercePriceModifierRelFinder.
			findCategoriesByCommercePriceModifierId(
				commercePriceModifierId, name, start, end);
	}

	@Override
	public int getCategoriesCommercePriceModifierRelsCount(
		long commercePriceModifierId, String name) {

		return commercePriceModifierRelFinder.
			countCategoriesByCommercePriceModifierId(
				commercePriceModifierId, name);
	}

	@Override
	public long[] getClassPKs(long commercePriceModifierId, String className) {
		return ListUtil.toLongArray(
			commercePriceModifierRelPersistence.findByCPM_CN(
				commercePriceModifierId,
				classNameLocalService.getClassNameId(className)),
			CommercePriceModifierRel::getClassPK);
	}

	@Override
	public List<CommercePriceModifierRel> getCommercePriceModifierRels(
		long commercePriceModifierId, String className) {

		return commercePriceModifierRelPersistence.findByCPM_CN(
			commercePriceModifierId,
			classNameLocalService.getClassNameId(className));
	}

	@Override
	public List<CommercePriceModifierRel> getCommercePriceModifierRels(
		long commercePriceModifierId, String className, int start, int end,
		OrderByComparator<CommercePriceModifierRel> orderByComparator) {

		return commercePriceModifierRelPersistence.findByCPM_CN(
			commercePriceModifierId,
			classNameLocalService.getClassNameId(className), start, end,
			orderByComparator);
	}

	@Override
	public int getCommercePriceModifierRelsCount(
		long commercePriceModifierId, String className) {

		return commercePriceModifierRelPersistence.countByCPM_CN(
			commercePriceModifierId,
			classNameLocalService.getClassNameId(className));
	}

	@Override
	public List<CommercePriceModifierRel> getCommercePriceModifiersRels(
		String className, long classPK) {

		return commercePriceModifierRelPersistence.findByCN_CPK(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public List<CommercePriceModifierRel>
		getCommercePricingClassesCommercePriceModifierRels(
			long commercePriceModifierId, String title, int start, int end) {

		return commercePriceModifierRelFinder.
			findPricingClassesByCommercePriceModifierId(
				commercePriceModifierId, title, start, end);
	}

	@Override
	public int getCommercePricingClassesCommercePriceModifierRelsCount(
		long commercePriceModifierId, String title) {

		return commercePriceModifierRelFinder.
			countPricingClassesByCommercePriceModifierId(
				commercePriceModifierId, title);
	}

	@Override
	public List<CommercePriceModifierRel>
		getCPDefinitionsCommercePriceModifierRels(
			long commercePriceModifierId, String name, String languageId,
			int start, int end) {

		return commercePriceModifierRelFinder.
			findCPDefinitionsByCommercePriceModifierId(
				commercePriceModifierId, name, languageId, start, end);
	}

	@Override
	public int getCPDefinitionsCommercePriceModifierRelsCount(
		long commercePriceModifierId, String name, String languageId) {

		return commercePriceModifierRelFinder.
			countCPDefinitionsByCommercePriceModifierId(
				commercePriceModifierId, languageId, name);
	}

}