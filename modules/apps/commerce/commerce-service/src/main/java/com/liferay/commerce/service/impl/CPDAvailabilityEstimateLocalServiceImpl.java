/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.NoSuchAvailabilityEstimateException;
import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.service.base.CPDAvailabilityEstimateLocalServiceBaseImpl;
import com.liferay.commerce.service.persistence.CommerceAvailabilityEstimatePersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CPDAvailabilityEstimate",
	service = AopService.class
)
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

	@Override
	public CPDAvailabilityEstimate fetchCPDAvailabilityEstimateByCProductId(
		long cProductId) {

		return cpdAvailabilityEstimatePersistence.fetchByCProductId(cProductId);
	}

	@Override
	public CPDAvailabilityEstimate updateCPDAvailabilityEstimateByCProductId(
			long userId, long cpdAvailabilityEstimateId, long cProductId,
			long commerceAvailabilityEstimateId)
		throws PortalException {

		_validate(commerceAvailabilityEstimateId);

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

		return _addCPDAvailabilityEstimateByCProductId(
			userId, cProductId, commerceAvailabilityEstimateId);
	}

	private CPDAvailabilityEstimate _addCPDAvailabilityEstimateByCProductId(
			long userId, long cProductId, long commerceAvailabilityEstimateId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

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

	private void _validate(long commerceAvailabilityEstimateId)
		throws PortalException {

		if (commerceAvailabilityEstimateId > 0) {
			CommerceAvailabilityEstimate commerceAvailabilityEstimate =
				_commerceAvailabilityEstimatePersistence.fetchByPrimaryKey(
					commerceAvailabilityEstimateId);

			if (commerceAvailabilityEstimate == null) {
				throw new NoSuchAvailabilityEstimateException();
			}
		}
	}

	@Reference
	private CommerceAvailabilityEstimatePersistence
		_commerceAvailabilityEstimatePersistence;

	@Reference
	private UserLocalService _userLocalService;

}