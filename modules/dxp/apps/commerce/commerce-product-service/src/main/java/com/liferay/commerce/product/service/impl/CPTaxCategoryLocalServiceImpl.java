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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.CPTaxCategoryNameException;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.base.CPTaxCategoryLocalServiceBaseImpl;
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
public class CPTaxCategoryLocalServiceImpl
	extends CPTaxCategoryLocalServiceBaseImpl {

	@Override
	public CPTaxCategory addCPTaxCategory(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(nameMap);

		long cpTaxCategoryId = counterLocalService.increment();

		CPTaxCategory cpTaxCategory = cpTaxCategoryPersistence.create(
			cpTaxCategoryId);

		cpTaxCategory.setGroupId(groupId);
		cpTaxCategory.setCompanyId(user.getCompanyId());
		cpTaxCategory.setUserId(user.getUserId());
		cpTaxCategory.setUserName(user.getFullName());
		cpTaxCategory.setNameMap(nameMap);
		cpTaxCategory.setDescriptionMap(descriptionMap);

		cpTaxCategoryPersistence.update(cpTaxCategory);

		return cpTaxCategory;
	}

	@Override
	public void deleteCPTaxCategories(long groupId) {
		cpTaxCategoryPersistence.removeByGroupId(groupId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPTaxCategory deleteCPTaxCategory(CPTaxCategory cpTaxCategory)
		throws PortalException {

		// Commerce product tax category

		cpTaxCategory = cpTaxCategoryPersistence.remove(cpTaxCategory);

		// Commerce product definitions

		cpDefinitionLocalService.updateCPDefinitionsByCPTaxCategoryId(
			cpTaxCategory.getCPTaxCategoryId());

		return cpTaxCategory;
	}

	@Override
	public CPTaxCategory deleteCPTaxCategory(long cpTaxCategoryId)
		throws PortalException {

		CPTaxCategory cpTaxCategory = cpTaxCategoryPersistence.findByPrimaryKey(
			cpTaxCategoryId);

		return cpTaxCategoryLocalService.deleteCPTaxCategory(cpTaxCategory);
	}

	@Override
	public List<CPTaxCategory> getCPTaxCategories(long groupId) {
		return cpTaxCategoryPersistence.findByGroupId(groupId);
	}

	@Override
	public List<CPTaxCategory> getCPTaxCategories(
		long groupId, int start, int end,
		OrderByComparator<CPTaxCategory> orderByComparator) {

		return cpTaxCategoryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPTaxCategoriesCount(long groupId) {
		return cpTaxCategoryPersistence.countByGroupId(groupId);
	}

	@Override
	public CPTaxCategory updateCPTaxCategory(
			long cpTaxCategoryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		CPTaxCategory cpTaxCategory = cpTaxCategoryPersistence.findByPrimaryKey(
			cpTaxCategoryId);

		validate(nameMap);

		cpTaxCategory.setNameMap(nameMap);
		cpTaxCategory.setDescriptionMap(descriptionMap);

		cpTaxCategoryPersistence.update(cpTaxCategory);

		return cpTaxCategory;
	}

	protected void validate(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CPTaxCategoryNameException();
		}
	}

}