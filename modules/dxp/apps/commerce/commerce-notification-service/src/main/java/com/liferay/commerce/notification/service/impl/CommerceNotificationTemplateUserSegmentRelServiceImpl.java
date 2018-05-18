/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.notification.service.impl;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;
import com.liferay.commerce.notification.service.base.CommerceNotificationTemplateUserSegmentRelServiceBaseImpl;
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
public class CommerceNotificationTemplateUserSegmentRelServiceImpl
	extends CommerceNotificationTemplateUserSegmentRelServiceBaseImpl {

	@Override
	public CommerceNotificationTemplateUserSegmentRel
			addCommerceNotificationTemplateUserSegmentRel(
				long commerceNotificationTemplateId,
				long commerceUserSegmentEntryId, ServiceContext serviceContext)
		throws PortalException {

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(), commerceNotificationTemplateId,
			ActionKeys.UPDATE);

		return commerceNotificationTemplateUserSegmentRelLocalService.
			addCommerceNotificationTemplateUserSegmentRel(
				commerceNotificationTemplateId, commerceUserSegmentEntryId,
				serviceContext);
	}

	@Override
	public void deleteCommerceNotificationTemplateUserSegmentRel(
			long commerceNotificationTemplateUserSegmentRelId)
		throws PortalException {

		CommerceNotificationTemplateUserSegmentRel
			commerceNotificationTemplateUserSegmentRel =
				commerceNotificationTemplateUserSegmentRelLocalService.
					getCommerceNotificationTemplateUserSegmentRel(
						commerceNotificationTemplateUserSegmentRelId);

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(),
			commerceNotificationTemplateUserSegmentRel.
				getCommerceNotificationTemplateId(),
			ActionKeys.UPDATE);

		commerceNotificationTemplateUserSegmentRelLocalService.
			deleteCommerceNotificationTemplateUserSegmentRel(
				commerceNotificationTemplateUserSegmentRel);
	}

	@Override
	public CommerceNotificationTemplateUserSegmentRel
			fetchCommerceNotificationTemplateUserSegmentRel(
				long commerceNotificationTemplateId,
				long commerceUserSegmentEntryId)
		throws PortalException {

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(), commerceNotificationTemplateId,
			ActionKeys.VIEW);

		return commerceNotificationTemplateUserSegmentRelLocalService.
			fetchCommerceNotificationTemplateUserSegmentRel(
				commerceNotificationTemplateId, commerceUserSegmentEntryId);
	}

	@Override
	public List<CommerceNotificationTemplateUserSegmentRel>
			getCommerceNotificationTemplateUserSegmentRels(
				long commerceNotificationTemplateId, int start, int end,
				OrderByComparator<CommerceNotificationTemplateUserSegmentRel>
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

		return commerceNotificationTemplateUserSegmentRelLocalService.
			getCommerceNotificationTemplateUserSegmentRels(
				commerceNotificationTemplateId, start, end, orderByComparator);
	}

	private static volatile
		ModelResourcePermission<CommerceNotificationTemplate>
			_commerceNotificationTemplateResourcePermission =
				ModelResourcePermissionFactory.getInstance(
					CommerceNotificationTemplateUserSegmentRelServiceImpl.class,
					"_commerceNotificationTemplateResourcePermission",
					CommerceNotificationTemplate.class);

}