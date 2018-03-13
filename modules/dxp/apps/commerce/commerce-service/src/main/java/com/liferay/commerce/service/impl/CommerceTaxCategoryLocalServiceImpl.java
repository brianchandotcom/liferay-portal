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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceTaxCategoryNameException;
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.service.base.CommerceTaxCategoryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxCategoryLocalServiceImpl
	extends CommerceTaxCategoryLocalServiceBaseImpl {

	@Override
	public CommerceTaxCategory addCommerceTaxCategory(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(nameMap);

		long commerceTaxCategoryId = counterLocalService.increment();

		CommerceTaxCategory commerceTaxCategory =
			commerceTaxCategoryPersistence.create(commerceTaxCategoryId);

		commerceTaxCategory.setGroupId(groupId);
		commerceTaxCategory.setCompanyId(user.getCompanyId());
		commerceTaxCategory.setUserId(user.getUserId());
		commerceTaxCategory.setUserName(user.getFullName());
		commerceTaxCategory.setNameMap(nameMap);
		commerceTaxCategory.setDescriptionMap(descriptionMap);

		commerceTaxCategoryPersistence.update(commerceTaxCategory);

		return commerceTaxCategory;
	}

	@Override
	public void deleteCommerceTaxCategories(long groupId) {
		commerceTaxCategoryPersistence.removeByGroupId(groupId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceTaxCategory deleteCommerceTaxCategory(
		CommerceTaxCategory commerceTaxCategory) {

		// Commerce tax category

		commerceTaxCategoryPersistence.remove(commerceTaxCategory);

		// Commerce tax category rels

		commerceTaxCategoryRelLocalService.deleteCommerceTaxCategoryRels(
			commerceTaxCategory.getCommerceTaxCategoryId());

		return commerceTaxCategory;
	}

	@Override
	public CommerceTaxCategory deleteCommerceTaxCategory(
			long commerceTaxCategoryId)
		throws PortalException {

		CommerceTaxCategory commerceTaxCategory =
			commerceTaxCategoryPersistence.findByPrimaryKey(
				commerceTaxCategoryId);

		return commerceTaxCategoryLocalService.deleteCommerceTaxCategory(
			commerceTaxCategory);
	}

	@Override
	public List<CommerceTaxCategory> getCommerceTaxCategories(long groupId) {
		return commerceTaxCategoryPersistence.findByGroupId(groupId);
	}

	@Override
	public List<CommerceTaxCategory> getCommerceTaxCategories(
		long groupId, int start, int end,
		OrderByComparator<CommerceTaxCategory> orderByComparator) {

		return commerceTaxCategoryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxCategoriesCount(long groupId) {
		return commerceTaxCategoryPersistence.countByGroupId(groupId);
	}

	@Override
	public CommerceTaxCategory updateCommerceTaxCategory(
			long commerceTaxCategoryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		CommerceTaxCategory commerceTaxCategory =
			commerceTaxCategoryPersistence.findByPrimaryKey(
				commerceTaxCategoryId);

		validate(nameMap);

		commerceTaxCategory.setNameMap(nameMap);
		commerceTaxCategory.setDescriptionMap(descriptionMap);

		commerceTaxCategoryPersistence.update(commerceTaxCategory);

		return commerceTaxCategory;
	}

	protected void validate(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CommerceTaxCategoryNameException();
		}
	}

}