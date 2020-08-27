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

package com.liferay.commerce.notification.service.impl;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateCommerceAccountGroupRel;
import com.liferay.commerce.notification.service.base.CommerceNotificationTemplateCommerceAccountGroupRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelServiceImpl
	extends CommerceNotificationTemplateCommerceAccountGroupRelServiceBaseImpl {

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
			addCommerceNotificationTemplateCommerceAccountGroupRel(
				long commerceNotificationTemplateId,
				long commerceAccountGroupId, ServiceContext serviceContext)
		throws PortalException {

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(), commerceNotificationTemplateId,
			ActionKeys.UPDATE);

		return commerceNotificationTemplateCommerceAccountGroupRelLocalService.
			addCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateId, commerceAccountGroupId,
				serviceContext);
	}

	@Override
	public void deleteCommerceNotificationTemplateCommerceAccountGroupRel(
			long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws PortalException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				commerceNotificationTemplateCommerceAccountGroupRelLocalService.
					getCommerceNotificationTemplateCommerceAccountGroupRel(
						commerceNotificationTemplateCommerceAccountGroupRelId);

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(),
			commerceNotificationTemplateCommerceAccountGroupRel.
				getCommerceNotificationTemplateId(),
			ActionKeys.UPDATE);

		commerceNotificationTemplateCommerceAccountGroupRelLocalService.
			deleteCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateCommerceAccountGroupRel);
	}

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
			fetchCommerceNotificationTemplateCommerceAccountGroupRel(
				long commerceNotificationTemplateId,
				long commerceAccountGroupId)
		throws PortalException {

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(), commerceNotificationTemplateId,
			ActionKeys.VIEW);

		return commerceNotificationTemplateCommerceAccountGroupRelLocalService.
			fetchCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateId, commerceAccountGroupId);
	}

	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
			getCommerceNotificationTemplateCommerceAccountGroupRels(
				long commerceNotificationTemplateId, int start, int end,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws PortalException {

		CommerceNotificationTemplate commerceNotificationTemplate =
			commerceNotificationTemplateLocalService.
				fetchCommerceNotificationTemplate(
					commerceNotificationTemplateId);

		if (commerceNotificationTemplate != null) {
			_commerceNotificationTemplateResourcePermission.check(
				getPermissionChecker(), commerceNotificationTemplateId,
				ActionKeys.UPDATE);
		}

		return commerceNotificationTemplateCommerceAccountGroupRelLocalService.
			getCommerceNotificationTemplateCommerceAccountGroupRels(
				commerceNotificationTemplateId, start, end, orderByComparator);
	}

	private static volatile ModelResourcePermission
		<CommerceNotificationTemplate>
			_commerceNotificationTemplateResourcePermission =
				ModelResourcePermissionFactory.getInstance(
					CommerceNotificationTemplateCommerceAccountGroupRelServiceImpl.class,
					"_commerceNotificationTemplateResourcePermission",
					CommerceNotificationTemplate.class);

}