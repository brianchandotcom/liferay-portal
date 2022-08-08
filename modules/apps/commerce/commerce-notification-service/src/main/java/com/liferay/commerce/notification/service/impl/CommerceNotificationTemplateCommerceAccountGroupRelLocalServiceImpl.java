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

import com.liferay.commerce.notification.model.CommerceNotificationTemplateCommerceAccountGroupRel;
import com.liferay.commerce.notification.service.base.CommerceNotificationTemplateCommerceAccountGroupRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.notification.model.CommerceNotificationTemplateCommerceAccountGroupRel",
	service = AopService.class
)
public class CommerceNotificationTemplateCommerceAccountGroupRelLocalServiceImpl
	extends CommerceNotificationTemplateCommerceAccountGroupRelLocalServiceBaseImpl {

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
			addCommerceNotificationTemplateCommerceAccountGroupRel(
				long commerceNotificationTemplateId,
				long commerceAccountGroupId, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceNotificationTemplateCommerceAccountGroupRelId =
			counterLocalService.increment();

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				commerceNotificationTemplateCommerceAccountGroupRelPersistence.
					create(
						commerceNotificationTemplateCommerceAccountGroupRelId);

		commerceNotificationTemplateCommerceAccountGroupRel.setGroupId(groupId);
		commerceNotificationTemplateCommerceAccountGroupRel.setCompanyId(
			user.getCompanyId());
		commerceNotificationTemplateCommerceAccountGroupRel.setUserId(
			user.getUserId());
		commerceNotificationTemplateCommerceAccountGroupRel.setUserName(
			user.getFullName());
		commerceNotificationTemplateCommerceAccountGroupRel.
			setCommerceNotificationTemplateId(commerceNotificationTemplateId);
		commerceNotificationTemplateCommerceAccountGroupRel.
			setCommerceAccountGroupId(commerceAccountGroupId);

		return commerceNotificationTemplateCommerceAccountGroupRelPersistence.
			update(commerceNotificationTemplateCommerceAccountGroupRel);
	}

	@Override
	public void
		deleteCNTemplateCommerceAccountGroupRelsBycommerceAccountGroupId(
			long commerceAccountGroupId) {

		commerceNotificationTemplateCommerceAccountGroupRelPersistence.
			removeByCommerceAccountGroupId(commerceAccountGroupId);
	}

	@Override
	public void
		deleteCNTemplateCommerceAccountGroupRelsByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId) {

		commerceNotificationTemplateCommerceAccountGroupRelPersistence.
			removeByCommerceNotificationTemplateId(
				commerceNotificationTemplateId);
	}

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchCommerceNotificationTemplateCommerceAccountGroupRel(
			long commerceNotificationTemplateId, long commerceAccountGroupId) {

		return commerceNotificationTemplateCommerceAccountGroupRelPersistence.
			fetchByC_C(commerceNotificationTemplateId, commerceAccountGroupId);
	}

	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
		getCommerceNotificationTemplateCommerceAccountGroupRels(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return commerceNotificationTemplateCommerceAccountGroupRelPersistence.
			findByCommerceNotificationTemplateId(
				commerceNotificationTemplateId, start, end, orderByComparator);
	}

	@Reference
	private UserLocalService _userLocalService;

}