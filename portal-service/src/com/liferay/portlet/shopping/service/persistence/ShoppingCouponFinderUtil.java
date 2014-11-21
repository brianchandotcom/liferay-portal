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

package com.liferay.portlet.shopping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ShoppingCouponFinderUtil {
	public static int countByG_C_C_A_DT(long groupId, long companyId,
		java.lang.String code, boolean active, java.lang.String discountType,
		boolean andOperator) {
		return getFinder()
				   .countByG_C_C_A_DT(groupId, companyId, code, active,
			discountType, andOperator);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findByG_C_C_A_DT(
		long groupId, long companyId, java.lang.String code, boolean active,
		java.lang.String discountType, boolean andOperator, int start, int end) {
		return getFinder()
				   .findByG_C_C_A_DT(groupId, companyId, code, active,
			discountType, andOperator, start, end);
	}

	public static ShoppingCouponFinder getFinder() {
		if (_finder == null) {
			_finder = (ShoppingCouponFinder)PortalBeanLocatorUtil.locate(ShoppingCouponFinder.class.getName());

			ReferenceRegistry.registerReference(ShoppingCouponFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(ShoppingCouponFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(ShoppingCouponFinderUtil.class,
			"_finder");
	}

	private static ShoppingCouponFinder _finder;
}