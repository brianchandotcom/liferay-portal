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

import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.base.CommercePricingClassCPDefinitionRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * @author Riccardo Alberti
 * @see CommercePricingClassCPDefinitionRelServiceBaseImpl
 */
public class CommercePricingClassCPDefinitionRelServiceImpl
	extends CommercePricingClassCPDefinitionRelServiceBaseImpl {

	@Override
	public CommercePricingClassCPDefinitionRel
			addCommercePricingClassCPDefinitionRel(
				long commercePricingClassId, long cpDefinitionId,
				ServiceContext serviceContext)
		throws PortalException {

		_commercePricingClassCPDefinitionRelResourcePermission.check(
			getPermissionChecker(), commercePricingClassId, ActionKeys.UPDATE);

		return commercePricingClassCPDefinitionRelLocalService.
			addCommercePricingClassCPDefinitionRel(
				commercePricingClassId, cpDefinitionId, serviceContext);
	}

	@Override
	public List<CommercePricingClassCPDefinitionRel>
			getCommercePricingClassCPDefinitionRelByClassId(
				long commercePricingClassId)
		throws PortalException {

		_commercePricingClassCPDefinitionRelResourcePermission.check(
			getPermissionChecker(), commercePricingClassId, ActionKeys.VIEW);

		return commercePricingClassCPDefinitionRelLocalService.
			getCommercePricingClassCPDefinitionRels(commercePricingClassId);
	}

	@Override
	public long[] getCPDefinitionIds(long commercePricingClassId)
		throws PortalException {

		_commercePricingClassCPDefinitionRelResourcePermission.check(
			getPermissionChecker(), commercePricingClassId, ActionKeys.UPDATE);

		return commercePricingClassCPDefinitionRelLocalService.
			getCPDefinitionIds(commercePricingClassId);
	}

	private static volatile ModelResourcePermission
		<CommercePricingClassCPDefinitionRel>
			_commercePricingClassCPDefinitionRelResourcePermission =
				ModelResourcePermissionFactory.getInstance(
					CommercePricingClassCPDefinitionRelServiceImpl.class,
					"_commercePricingClassCPDefinitionRelResourcePermission",
					CommercePricingClassCPDefinitionRel.class);

}