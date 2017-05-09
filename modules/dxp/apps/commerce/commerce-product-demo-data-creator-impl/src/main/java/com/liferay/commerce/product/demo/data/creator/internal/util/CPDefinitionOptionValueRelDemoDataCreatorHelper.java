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

import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionOptionValueRelDemoDataCreatorHelper.class)
public class CPDefinitionOptionValueRelDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void createCPDefinitionOptionValueRel(
			long userId, long groupId, long cpDefinitionOptionRelId,
			String name, Map<Locale, String> titleMap, int priority)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		_cpDefinitionOptionValueRelLocalService.addCPDefinitionOptionValueRel(
			cpDefinitionOptionRelId, name, titleMap, priority, serviceContext);
	}

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

}