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

package com.liferay.commerce.user.segment.service.impl;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeRegistry;
import com.liferay.commerce.user.segment.exception.CommerceUserSegmentCriterionTypeException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.service.base.CommerceUserSegmentCriterionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceUserSegmentCriterionLocalServiceImpl
	extends CommerceUserSegmentCriterionLocalServiceBaseImpl {

	@Override
	public CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
			long commerceUserSegmentEntryId, String type, String typeSettings,
			double priority, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(type);

		long commerceUserSegmentCriterionId = counterLocalService.increment();

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			commerceUserSegmentCriterionPersistence.create(
				commerceUserSegmentCriterionId);

		commerceUserSegmentCriterion.setGroupId(groupId);
		commerceUserSegmentCriterion.setCompanyId(user.getCompanyId());
		commerceUserSegmentCriterion.setUserId(user.getUserId());
		commerceUserSegmentCriterion.setUserName(user.getFullName());
		commerceUserSegmentCriterion.setCommerceUserSegmentEntryId(
			commerceUserSegmentEntryId);
		commerceUserSegmentCriterion.setType(type);
		commerceUserSegmentCriterion.setTypeSettings(typeSettings);
		commerceUserSegmentCriterion.setPriority(priority);
		commerceUserSegmentCriterion.setExpandoBridgeAttributes(serviceContext);

		commerceUserSegmentCriterionPersistence.update(
			commerceUserSegmentCriterion);

		commerceUserSegmentEntryLocalService.cleanUserSegmentsChache(
			serviceContext.getScopeGroupId());

		return commerceUserSegmentCriterion;
	}

	@Override
	public void deleteCommerceUserSegmentCriteria(
			long commerceUserSegmentEntryId)
		throws PortalException {

		commerceUserSegmentCriterionPersistence.
			removeByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceUserSegmentCriterion deleteCommerceUserSegmentCriterion(
			CommerceUserSegmentCriterion commerceUserSegmentCriterion)
		throws PortalException {

		// Commerce user segment criterion

		commerceUserSegmentCriterionPersistence.remove(
			commerceUserSegmentCriterion);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceUserSegmentCriterion.getCommerceUserSegmentCriterionId());

		// Cache

		commerceUserSegmentEntryLocalService.cleanUserSegmentsChache(
			commerceUserSegmentCriterion.getGroupId());

		return commerceUserSegmentCriterion;
	}

	@Override
	public CommerceUserSegmentCriterion deleteCommerceUserSegmentCriterion(
			long commerceUserSegmentCriterionId)
		throws PortalException {

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			commerceUserSegmentCriterionPersistence.findByPrimaryKey(
				commerceUserSegmentCriterionId);

		return commerceUserSegmentCriterionLocalService.
			deleteCommerceUserSegmentCriterion(commerceUserSegmentCriterion);
	}

	@Override
	public List<CommerceUserSegmentCriterion> getCommerceUserSegmentCriteria(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {

		return commerceUserSegmentCriterionPersistence.
			findByCommerceUserSegmentEntryId(
				commerceUserSegmentEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceUserSegmentCriteriaCount(
		long commerceUserSegmentEntryId) {

		return commerceUserSegmentCriterionPersistence.
			countByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	@Override
	public CommerceUserSegmentCriterion updateCommerceUserSegmentCriterion(
			long commerceUserSegmentCriterionId, String type,
			String typeSettings, double priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			commerceUserSegmentCriterionPersistence.findByPrimaryKey(
				commerceUserSegmentCriterionId);

		validate(type);

		commerceUserSegmentCriterion.setType(type);
		commerceUserSegmentCriterion.setTypeSettings(typeSettings);
		commerceUserSegmentCriterion.setPriority(priority);
		commerceUserSegmentCriterion.setExpandoBridgeAttributes(serviceContext);

		commerceUserSegmentCriterionPersistence.update(
			commerceUserSegmentCriterion);

		commerceUserSegmentEntryLocalService.cleanUserSegmentsChache(
			commerceUserSegmentCriterion.getGroupId());

		return commerceUserSegmentCriterion;
	}

	protected void validate(String type) throws PortalException {
		CommerceUserSegmentCriterionType commerceUserSegmentCriterionType =
			_commerceUserSegmentCriterionTypeRegistry.
				getCommerceUserSegmentCriterionType(type);

		if (commerceUserSegmentCriterionType == null) {
			throw new CommerceUserSegmentCriterionTypeException();
		}
	}

	@ServiceReference(type = CommerceUserSegmentCriterionTypeRegistry.class)
	private CommerceUserSegmentCriterionTypeRegistry
		_commerceUserSegmentCriterionTypeRegistry;

}