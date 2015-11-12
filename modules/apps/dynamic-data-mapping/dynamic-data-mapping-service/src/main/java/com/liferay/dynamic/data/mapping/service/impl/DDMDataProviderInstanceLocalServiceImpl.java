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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.exception.DataProviderInstanceNameException;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.base.DDMDataProviderInstanceLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderInstanceLocalServiceImpl
	extends DDMDataProviderInstanceLocalServiceBaseImpl {

	@Override
	public DDMDataProviderInstance addDataProviderInstance(
			long userId, long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String definition, String type,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		ddmFormValuesValidator.validate(ddmFormValues);

		validateName(nameMap);

		long dataProviderInstanceId = counterLocalService.increment();

		DDMDataProviderInstance ddmDataProviderInstance =
			ddmDataProviderInstancePersistence.create(dataProviderInstanceId);

		ddmDataProviderInstance.setUuid(serviceContext.getUuid());
		ddmDataProviderInstance.setGroupId(groupId);
		ddmDataProviderInstance.setCompanyId(user.getCompanyId());
		ddmDataProviderInstance.setUserId(user.getUserId());
		ddmDataProviderInstance.setUserName(user.getFullName());
		ddmDataProviderInstance.setDefinition(definition);
		ddmDataProviderInstance.setDescriptionMap(descriptionMap);
		ddmDataProviderInstance.setNameMap(nameMap);
		ddmDataProviderInstance.setType(type);

		ddmDataProviderInstancePersistence.update(ddmDataProviderInstance);

		return ddmDataProviderInstance;
	}

	@Override
	public List<DDMDataProviderInstance> search(
		long companyId, long[] groupIds, String keywords, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {

		return ddmDataProviderInstanceFinder.findByKeywords(
			companyId, groupIds, keywords, start, end, orderByComparator);
	}

	@Override
	public List<DDMDataProviderInstance> search(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {

		return ddmDataProviderInstanceFinder.findByC_G_N_D(
			companyId, groupIds, name, description, andOperator, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds, String keywords) {
		return ddmDataProviderInstanceFinder.countByKeywords(
			companyId, groupIds, keywords);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator) {

		return ddmDataProviderInstanceFinder.countByC_G_N_D(
			companyId, groupIds, name, description, andOperator);
	}

	@Override
	public DDMDataProviderInstance updateDataProviderInstance(
			long userId, long dataProviderInstanceId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String definition, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		ddmFormValuesValidator.validate(ddmFormValues);

		validateName(nameMap);

		DDMDataProviderInstance ddmDataProviderInstance =
			ddmDataProviderInstancePersistence.findByPrimaryKey(
				dataProviderInstanceId);

		ddmDataProviderInstance.setUserId(user.getUserId());
		ddmDataProviderInstance.setUserName(user.getFullName());
		ddmDataProviderInstance.setDefinition(definition);
		ddmDataProviderInstance.setDescriptionMap(descriptionMap);
		ddmDataProviderInstance.setNameMap(nameMap);

		ddmDataProviderInstancePersistence.update(ddmDataProviderInstance);

		return ddmDataProviderInstance;
	}

	protected void validateName(Map<Locale, String> nameMap)
		throws PortalException {

		String name = nameMap.get(LocaleUtil.getSiteDefault());

		if (Validator.isNull(name)) {
			throw new DataProviderInstanceNameException();
		}
	}

	@ServiceReference(type = DDMFormValuesValidator.class)
	protected DDMFormValuesValidator ddmFormValuesValidator;

}