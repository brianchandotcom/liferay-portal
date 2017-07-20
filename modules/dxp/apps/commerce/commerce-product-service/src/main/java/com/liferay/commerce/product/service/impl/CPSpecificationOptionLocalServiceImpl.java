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

import com.liferay.commerce.product.exception.CPSpecificationOptionKeyException;
import com.liferay.commerce.product.exception.CPSpecificationOptionTitleException;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.base.CPSpecificationOptionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class CPSpecificationOptionLocalServiceImpl
	extends CPSpecificationOptionLocalServiceBaseImpl {

	@Override
	public CPSpecificationOption addCPSpecificationOption(
			long cpOptionCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, boolean facetable, String key,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product specification option

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(0, groupId, titleMap, key);

		long cpSpecificationOptionId = counterLocalService.increment();

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.create(cpSpecificationOptionId);

		cpSpecificationOption.setUuid(serviceContext.getUuid());
		cpSpecificationOption.setGroupId(groupId);
		cpSpecificationOption.setCompanyId(user.getCompanyId());
		cpSpecificationOption.setUserId(user.getUserId());
		cpSpecificationOption.setUserName(user.getFullName());
		cpSpecificationOption.setCPOptionCategoryId(cpOptionCategoryId);
		cpSpecificationOption.setTitleMap(titleMap);
		cpSpecificationOption.setDescriptionMap(descriptionMap);
		cpSpecificationOption.setFacetable(facetable);
		cpSpecificationOption.setKey(key);
		cpSpecificationOption.setExpandoBridgeAttributes(serviceContext);

		cpSpecificationOptionPersistence.update(cpSpecificationOption);

		// Resources

		resourceLocalService.addModelResources(
			cpSpecificationOption, serviceContext);

		return cpSpecificationOption;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPSpecificationOption deleteCPSpecificationOption(
			CPSpecificationOption cpSpecificationOption)
		throws PortalException {

		// Commerce product specification option

		cpSpecificationOptionPersistence.remove(cpSpecificationOption);

		// Commerce product definition specification option values

		cpDefinitionSpecificationOptionValueLocalService.
			deleteCPSpecificationOptionDefinitionValues(
				cpSpecificationOption.getCPSpecificationOptionId());

		// Resources

		resourceLocalService.deleteResource(
			cpSpecificationOption.getCompanyId(),
			CPSpecificationOption.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			cpSpecificationOption.getCPSpecificationOptionId());

		// Expando

		expandoRowLocalService.deleteRows(
			cpSpecificationOption.getCPSpecificationOptionId());

		return cpSpecificationOption;
	}

	@Override
	public CPSpecificationOption deleteCPSpecificationOption(
			long cpSpecificationOptionId)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.findByPrimaryKey(
				cpSpecificationOptionId);

		return cpSpecificationOptionLocalService.deleteCPSpecificationOption(
			cpSpecificationOption);
	}

	@Override
	public void deleteCPSpecificationOptions(long groupId)
		throws PortalException {

		List<CPSpecificationOption> cpSpecificationOptions =
			cpSpecificationOptionPersistence.findByGroupId(groupId);

		for (CPSpecificationOption cpSpecificationOption :
				cpSpecificationOptions) {

			cpSpecificationOptionLocalService.deleteCPSpecificationOption(
				cpSpecificationOption);
		}
	}

	@Override
	public CPSpecificationOption updateCPOptionCategoryId(
			long cpSpecificationOptionId, long cpOptionCategoryId)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.findByPrimaryKey(
				cpSpecificationOptionId);

		cpSpecificationOption.setCPOptionCategoryId(cpOptionCategoryId);

		cpSpecificationOptionPersistence.update(cpSpecificationOption);

		return cpSpecificationOption;
	}

	@Override
	public CPSpecificationOption updateCPSpecificationOption(
			long cpSpecificationOptionId, long cpOptionCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			boolean facetable, String key, ServiceContext serviceContext)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.findByPrimaryKey(
				cpSpecificationOptionId);

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(
			cpSpecificationOption.getCPSpecificationOptionId(),
			cpSpecificationOption.getGroupId(), titleMap, key);

		cpSpecificationOption.setCPOptionCategoryId(cpOptionCategoryId);
		cpSpecificationOption.setTitleMap(titleMap);
		cpSpecificationOption.setDescriptionMap(descriptionMap);
		cpSpecificationOption.setFacetable(facetable);
		cpSpecificationOption.setKey(key);
		cpSpecificationOption.setExpandoBridgeAttributes(serviceContext);

		cpSpecificationOptionPersistence.update(cpSpecificationOption);

		return cpSpecificationOption;
	}

	protected void validate(
			long cpSpecificationOptionId, long groupId,
			Map<Locale, String> titleMap, String key)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String title = titleMap.get(locale);

		if (Validator.isNull(title)) {
			throw new CPSpecificationOptionTitleException();
		}

		if (Validator.isNull(key)) {
			throw new CPSpecificationOptionKeyException.MustNotBeNull();
		}

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.fetchByG_K(groupId, key);

		if ((cpSpecificationOption != null) &&
			(cpSpecificationOption.getCPSpecificationOptionId() !=
				cpSpecificationOptionId)) {

			throw new CPSpecificationOptionKeyException.MustNotBeDuplicate(key);
		}
	}

}