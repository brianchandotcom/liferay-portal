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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassPriceListDisplayContext
	extends BasePricingDisplayContext {

	public CommercePricingClassPriceListDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<CommercePricingClass>
			commercePricingClassModelResourcePermission,
		CommercePricingClassService commercePricingClassService) {

		super(httpServletRequest);

		_commercePricingClassModelResourcePermission =
			commercePricingClassModelResourcePermission;
		_commercePricingClassService = commercePricingClassService;
	}

	public CommercePricingClass getCommercePricingClass()
		throws PortalException {

		long commercePricingClassId = ParamUtil.getLong(
			commercePricingRequestHelper.getRequest(),
			"commercePricingClassId");

		if (commercePricingClassId == 0) {
			return null;
		}

		return _commercePricingClassService.fetchCommercePricingClass(
			commercePricingClassId);
	}

	public boolean hasPermission() throws PortalException {
		CommercePricingClass commercePricingClass = getCommercePricingClass();

		return _commercePricingClassModelResourcePermission.contains(
			commercePricingRequestHelper.getPermissionChecker(),
			commercePricingClass.getCommercePricingClassId(), ActionKeys.VIEW);
	}

	private final ModelResourcePermission<CommercePricingClass>
		_commercePricingClassModelResourcePermission;
	private final CommercePricingClassService _commercePricingClassService;

}