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

package com.liferay.commerce.data.integration.apio.internal.util;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.commerce.product.service.CPOptionValueService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true, service = CPOptionValueHelper.class)
public class CPOptionValueHelper {

	public CPOptionValue createCPOptionValue(
			Long cpOptionId, Map<Locale, String> titleMap, String key)
		throws PortalException {

		CPOption cpOption = _cpOptionService.getCPOption(cpOptionId);

		ServiceContext serviceContext = _getServiceContext(cpOption);

		return _cpOptionValueService.addCPOptionValue(
			cpOptionId, titleMap, 0, key, serviceContext);
	}

	public CPOptionValue updateCPOptionValue(
			Long cpOptionValueId, Map<Locale, String> nameMap, String key)
		throws PortalException {

		CPOptionValue cpOptionValue = _cpOptionValueService.getCPOptionValue(
			cpOptionValueId);

		ServiceContext serviceContext = _getServiceContext(
			cpOptionValue.getCPOption());

		return _cpOptionValueService.updateCPOptionValue(
			cpOptionValueId, nameMap, 0, key, serviceContext);
	}

	private ServiceContext _getServiceContext(CPOption cpOption) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(cpOption.getCompanyId());
		serviceContext.setScopeGroupId(cpOption.getGroupId());
		serviceContext.setUserId(cpOption.getUserId());

		return serviceContext;
	}

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private CPOptionValueService _cpOptionValueService;

}