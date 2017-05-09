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

import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPInstanceDemoDataCreatorHelper.class)
public class CPInstanceDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void createCPInstance(long userId, long groupId, long cpDefinitionId)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		_cpInstanceLocalService.buildCPInstances(cpDefinitionId, serviceContext);
	}

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}