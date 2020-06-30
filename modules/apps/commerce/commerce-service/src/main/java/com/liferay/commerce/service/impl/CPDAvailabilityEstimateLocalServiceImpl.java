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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.NoSuchAvailabilityEstimateException;
import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.service.base.CPDAvailabilityEstimateLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.spring.extender.service.ServiceReference;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
public class CPDAvailabilityEstimateLocalServiceImpl
	extends CPDAvailabilityEstimateLocalServiceBaseImpl {

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDAvailabilityEstimate deleteCPDAvailabilityEstimate(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		return cpdAvailabilityEstimatePersistence.remove(
			cpdAvailabilityEstimate);
	}

	@Override
	public CPDAvailabilityEstimate deleteCPDAvailabilityEstimate(
			long cpdAvailabilityEstimateId)
		throws PortalException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			cpdAvailabilityEstimatePersistence.findByPrimaryKey(
				cpdAvailabilityEstimateId);

		return cpdAvailabilityEstimateLocalService.
			deleteCPDAvailabilityEstimate(cpdAvailabilityEstimate);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public void deleteCPDAvailabilityEstimateByCPDefinitionId(
		long cpDefinitionId) {

		CPDefinition cpDefinition = _cpDefinitionLocalService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition != null) {
			cpdAvailabilityEstimateLocalService.
				deleteCPDAvailabilityEstimateByCProductId(
					cpDefinition.getCProductId());
		}
	}

	@Override
	public void deleteCPDAvailabilityEstimateByCProductId(long cProductId) {
		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			cpdAvailabilityEstimateLocalService.
				fetchCPDAvailabilityEstimateByCProductId(cProductId);

		if (cpdAvailabilityEstimate != null) {
			cpdAvailabilityEstimateLocalService.deleteCPDAvailabilityEstimate(
				cpdAvailabilityEstimate);
		}
	}

	@Override
	public void deleteCPDAvailabilityEstimates(
		long commerceAvailabilityEstimateId) {

		cpdAvailabilityEstimatePersistence.
			removeByCommerceAvailabilityEstimateId(
				commerceAvailabilityEstimateId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CPDAvailabilityEstimate fetchCPDAvailabilityEstimateByCPDefinitionId(
		long cpDefinitionId) {

		CPDefinition cpDefinition = _cpDefinitionLocalService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition == null) {
			return null;
		}

		return cpdAvailabilityEstimateLocalService.
			fetchCPDAvailabilityEstimateByCProductId(
				cpDefinition.getCProductId());
	}

	@Override
	public CPDAvailabilityEstimate fetchCPDAvailabilityEstimateByCProductId(
		long cProductId) {

		return cpdAvailabilityEstimatePersistence.fetchByCProductId(cProductId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CPDAvailabilityEstimate updateCPDAvailabilityEstimate(
			long cpdAvailabilityEstimateId, long cpDefinitionId,
			long commerceAvailabilityEstimateId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		return cpdAvailabilityEstimateLocalService.
			updateCPDAvailabilityEstimateByCProductId(
				cpdAvailabilityEstimateId, cpDefinition.getCProductId(),
				commerceAvailabilityEstimateId, serviceContext);
	}

	@Override
	public CPDAvailabilityEstimate updateCPDAvailabilityEstimateByCProductId(
			long cpdAvailabilityEstimateId, long cProductId,
			long commerceAvailabilityEstimateId, ServiceContext serviceContext)
		throws PortalException {

		validate(commerceAvailabilityEstimateId);

		if (cpdAvailabilityEstimateId > 0) {
			CPDAvailabilityEstimate cpdAvailabilityEstimate =
				cpdAvailabilityEstimatePersistence.findByPrimaryKey(
					cpdAvailabilityEstimateId);

			cpdAvailabilityEstimate.setCommerceAvailabilityEstimateId(
				commerceAvailabilityEstimateId);

			return cpdAvailabilityEstimatePersistence.update(
				cpdAvailabilityEstimate);
		}

		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			fetchCPDAvailabilityEstimateByCProductId(cProductId);

		if (cpdAvailabilityEstimate != null) {
			cpdAvailabilityEstimate.setCommerceAvailabilityEstimateId(
				commerceAvailabilityEstimateId);

			return cpdAvailabilityEstimatePersistence.update(
				cpdAvailabilityEstimate);
		}

		return addCPDAvailabilityEstimateByCProductId(
			cProductId, commerceAvailabilityEstimateId, serviceContext);
	}

	protected CPDAvailabilityEstimate addCPDAvailabilityEstimateByCProductId(
			long cProductId, long commerceAvailabilityEstimateId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		long cpdAvailabilityEstimateId = counterLocalService.increment();

		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			cpdAvailabilityEstimatePersistence.create(
				cpdAvailabilityEstimateId);

		cpdAvailabilityEstimate.setCompanyId(user.getCompanyId());
		cpdAvailabilityEstimate.setUserId(user.getUserId());
		cpdAvailabilityEstimate.setUserName(user.getFullName());
		cpdAvailabilityEstimate.setCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId);
		cpdAvailabilityEstimate.setCProductId(cProductId);

		return cpdAvailabilityEstimatePersistence.update(
			cpdAvailabilityEstimate);
	}

	protected void validate(long commerceAvailabilityEstimateId)
		throws PortalException {

		if (commerceAvailabilityEstimateId > 0) {
			CommerceAvailabilityEstimate commerceAvailabilityEstimate =
				commerceAvailabilityEstimateLocalService.
					fetchCommerceAvailabilityEstimate(
						commerceAvailabilityEstimateId);

			if (commerceAvailabilityEstimate == null) {
				throw new NoSuchAvailabilityEstimateException();
			}
		}
	}

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

}