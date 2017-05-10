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

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionOptionRelDemoDataCreatorHelper.class)
public class CPDefinitionOptionRelDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public CPDefinitionOptionRel createCPDefinitionOptionRel(
			long userId, long groupId, long cpDefinitionId, long cpOptionId,
			String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			int priority, boolean facetable, boolean skuContributor)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
				cpDefinitionId, cpOptionId, name, titleMap, descriptionMap,
				ddmFormFieldTypeName, priority, facetable, skuContributor,false,
				false, serviceContext);

		return cpDefinitionOptionRel;
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

}