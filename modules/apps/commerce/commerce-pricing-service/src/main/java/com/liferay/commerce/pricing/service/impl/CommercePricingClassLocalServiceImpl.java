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

import com.liferay.commerce.pricing.exception.CommercePricingClassTitleException;
import com.liferay.commerce.pricing.exception.NoSuchPricingClassException;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.base.CommercePricingClassLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Riccardo Alberti
 * @see CommercePricingClassLocalServiceBaseImpl
 */
public class CommercePricingClassLocalServiceImpl
	extends CommercePricingClassLocalServiceBaseImpl {

	@Override
	public CommercePricingClass addCommercePricingClass(
			long userId, long groupId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(title);

		long commercePricingClassId = counterLocalService.increment();

		CommercePricingClass commercePricingClass =
			commercePricingClassPersistence.create(commercePricingClassId);

		commercePricingClass.setGroupId(groupId);
		commercePricingClass.setCompanyId(serviceContext.getCompanyId());
		commercePricingClass.setUserId(user.getUserId());
		commercePricingClass.setUserName(user.getFullName());
		commercePricingClass.setTitle(title);
		commercePricingClass.setDescription(description);

		return commercePricingClassPersistence.update(commercePricingClass);
	}

	@Override
	public CommercePricingClass deleteCommercePricingClass(
			CommercePricingClass commercePricingClass)
		throws PortalException {

		commercePricingClassCPDefinitionRelLocalService.
			deleteCommercePricingClassCPDefinitionRels(
				commercePricingClass.getCommercePricingClassId());

		return commercePricingClassPersistence.remove(
			commercePricingClass.getCommercePricingClassId());
	}

	@Override
	public CommercePricingClass deleteCommercePricingClass(
			long commercePricingClassId)
		throws PortalException {

		CommercePricingClass commercePricingClass =
			commercePricingClassPersistence.findByPrimaryKey(
				commercePricingClassId);

		return commercePricingClassLocalService.deleteCommercePricingClass(
			commercePricingClass);
	}

	@Override
	public void deleteCommercePricingClasses(long companyId)
		throws PortalException {

		List<CommercePricingClass> commercePricingClasses =
			commercePricingClassPersistence.findByCompanyId(companyId);

		for (CommercePricingClass commercePricingClass :
				commercePricingClasses) {

			commercePricingClassLocalService.deleteCommercePricingClass(
				commercePricingClass);
		}
	}

	@Override
	public CommercePricingClass fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commercePricingClassPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public long[] getCommercePricingClassByCPDefinition(long cpDefinitionId) {
		List<CommercePricingClassCPDefinitionRel>
			commercePricingClassCPDefinitionRels =
				commercePricingClassCPDefinitionRelLocalService.
					getCommercePricingClassByCPDefinitionId(cpDefinitionId);

		Stream<CommercePricingClassCPDefinitionRel> stream =
			commercePricingClassCPDefinitionRels.stream();

		LongStream longStream = stream.mapToLong(
			CommercePricingClassCPDefinitionRel::getCommercePricingClassId);

		return longStream.toArray();
	}

	@Override
	public List<CommercePricingClass> getCommercePricingClasses(
		long companyId, int start, int end,
		OrderByComparator<CommercePricingClass> orderByComparator) {

		return commercePricingClassPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePricingClassesCount(long companyId) {
		return commercePricingClassPersistence.countByCompanyId(companyId);
	}

	@Override
	public CommercePricingClass updateCommercePricingClass(
			long commercePricingClassId, long userId, long groupId,
			String title, String description, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommercePricingClass commercePricingClass =
			commercePricingClassPersistence.findByPrimaryKey(
				commercePricingClassId);

		validate(title);

		commercePricingClass.setGroupId(groupId);
		commercePricingClass.setCompanyId(serviceContext.getCompanyId());
		commercePricingClass.setUserId(user.getUserId());
		commercePricingClass.setUserName(user.getFullName());
		commercePricingClass.setTitle(title);
		commercePricingClass.setDescription(description);

		return commercePricingClassPersistence.update(commercePricingClass);
	}

	@Override
	public CommercePricingClass upsertCommercePricingClass(
			long commercePricingClassId, long userId, long groupId,
			String title, String description, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		if (commercePricingClassId > 0) {
			try {
				return commercePricingClassLocalService.
					updateCommercePricingClass(
						commercePricingClassId, userId, groupId, title,
						description, serviceContext);
			}
			catch (NoSuchPricingClassException nspc) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find pricing class with ID: " +
							commercePricingClassId);
				}
			}
		}

		if (!Validator.isBlank(externalReferenceCode)) {
			CommercePricingClass commercePricingClass =
				commercePricingClassPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (commercePricingClass != null) {
				return commercePricingClassLocalService.
					updateCommercePricingClass(
						commercePricingClassId, userId, groupId, title,
						description, serviceContext);
			}
		}

		return commercePricingClassLocalService.addCommercePricingClass(
			userId, groupId, title, description, serviceContext);
	}

	protected void validate(String title) throws PortalException {
		if (Validator.isNull(title)) {
			throw new CommercePricingClassTitleException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePricingClassLocalServiceImpl.class);

}