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

import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;
import com.liferay.commerce.notification.service.base.CommerceNotificationTemplateUserSegmentRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationTemplateUserSegmentRelLocalServiceImpl
	extends CommerceNotificationTemplateUserSegmentRelLocalServiceBaseImpl {

	@Override
	public CommerceNotificationTemplateUserSegmentRel
			addCommerceNotificationTemplateUserSegmentRel(
				long commerceNotificationTemplateId,
				long commerceUserSegmentEntryId, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceNotificationTemplateUserSegmentRelId =
			counterLocalService.increment();

		CommerceNotificationTemplateUserSegmentRel
			commerceNotificationTemplateUserSegmentRel =
				commerceNotificationTemplateUserSegmentRelPersistence.create(
					commerceNotificationTemplateUserSegmentRelId);

		commerceNotificationTemplateUserSegmentRel.setGroupId(groupId);
		commerceNotificationTemplateUserSegmentRel.setCompanyId(
			user.getCompanyId());
		commerceNotificationTemplateUserSegmentRel.setUserId(user.getUserId());
		commerceNotificationTemplateUserSegmentRel.setUserName(
			user.getFullName());
		commerceNotificationTemplateUserSegmentRel.
			setCommerceNotificationTemplateId(commerceNotificationTemplateId);
		commerceNotificationTemplateUserSegmentRel.
			setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);

		commerceNotificationTemplateUserSegmentRelPersistence.update(
			commerceNotificationTemplateUserSegmentRel);

		return commerceNotificationTemplateUserSegmentRel;
	}

	@Override
	public void deleteCNTemplateUserSegmentRelsByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		commerceNotificationTemplateUserSegmentRelPersistence.
			removeByCommerceNotificationTemplateId(
				commerceNotificationTemplateId);
	}

	@Override
	public void deleteCNTemplateUserSegmentRelsByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {

		commerceNotificationTemplateUserSegmentRelPersistence.
			removeByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	@Override
	public CommerceNotificationTemplateUserSegmentRel
		fetchCommerceNotificationTemplateUserSegmentRel(
			long commerceNotificationTemplateId,
			long commerceUserSegmentEntryId) {

		return commerceNotificationTemplateUserSegmentRelPersistence.fetchByC_C(
			commerceNotificationTemplateId, commerceUserSegmentEntryId);
	}

	@Override
	public List<CommerceNotificationTemplateUserSegmentRel>
		getCommerceNotificationTemplateUserSegmentRels(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator<CommerceNotificationTemplateUserSegmentRel>
				orderByComparator) {

		return commerceNotificationTemplateUserSegmentRelPersistence.
			findByCommerceNotificationTemplateId(
				commerceNotificationTemplateId, start, end, orderByComparator);
	}

}