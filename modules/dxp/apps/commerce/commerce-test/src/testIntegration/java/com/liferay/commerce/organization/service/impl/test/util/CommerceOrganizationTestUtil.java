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

package com.liferay.commerce.organization.service.impl.test.util;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalServiceUtil;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.commerce.service.CommerceRegionLocalServiceUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.List;

/**
 * @author Luca Pellizzon
 */
public class CommerceOrganizationTestUtil {

	public static Organization addOrganization(
			long groupId, long parentOrganizationId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CommerceOrganizationLocalServiceUtil.addOrganization(
			parentOrganizationId, RandomTestUtil.randomString(),
			CommerceOrganizationConstants.TYPE_ACCOUNT, serviceContext);
	}

	public static Address addOrganizationPrimaryAddress(
			long groupId, long organizationId)
		throws Exception {

		CommerceCountry commerceCountry =
			CommerceCountryLocalServiceUtil.fetchCommerceCountry(groupId, 380);

		CommerceRegion commerceRegion =
			CommerceRegionLocalServiceUtil.getCommerceRegion(
				commerceCountry.getCommerceCountryId(), "VE");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return AddressLocalServiceUtil.addAddress(
			serviceContext.getUserId(), Organization.class.getName(),
			organizationId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), String.valueOf(30133),
			commerceRegion.getCommerceRegionId(),
			commerceCountry.getCommerceCountryId(),
			_getListTypeId(ListTypeConstants.ORGANIZATION_ADDRESS), false, true,
			serviceContext);
	}

	private static long _getListTypeId(String type) throws Exception {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(type);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

}