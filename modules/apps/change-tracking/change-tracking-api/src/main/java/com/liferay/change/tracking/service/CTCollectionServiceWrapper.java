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

package com.liferay.change.tracking.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTCollectionService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionService
 * @generated
 */
public class CTCollectionServiceWrapper
	implements CTCollectionService, ServiceWrapper<CTCollectionService> {

	public CTCollectionServiceWrapper(CTCollectionService ctCollectionService) {
		_ctCollectionService = ctCollectionService;
	}

	@Override
	public com.liferay.change.tracking.model.CTCollection addCTCollection(
			long companyId, long userId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctCollectionService.addCTCollection(
			companyId, userId, name, description);
	}

	@Override
	public void deleteCTAutoResolutionInfo(long ctAutoResolutionInfoId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ctCollectionService.deleteCTAutoResolutionInfo(ctAutoResolutionInfoId);
	}

	@Override
	public com.liferay.change.tracking.model.CTCollection deleteCTCollection(
			com.liferay.change.tracking.model.CTCollection ctCollection)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctCollectionService.deleteCTCollection(ctCollection);
	}

	@Override
	public void discardCTEntries(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ctCollectionService.discardCTEntries(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	@Override
	public void discardCTEntry(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ctCollectionService.discardCTEntry(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(
			long companyId, int[] statuses, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTCollection>
					orderByComparator) {

		return _ctCollectionService.getCTCollections(
			companyId, statuses, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTCollection>
		getCTCollections(
			long companyId, int[] statuses, String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTCollection>
					orderByComparator) {

		return _ctCollectionService.getCTCollections(
			companyId, statuses, keywords, start, end, orderByComparator);
	}

	@Override
	public int getCTCollectionsCount(
		long companyId, int[] statuses, String keywords) {

		return _ctCollectionService.getCTCollectionsCount(
			companyId, statuses, keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctCollectionService.getOSGiServiceIdentifier();
	}

	@Override
	public void publishCTCollection(long userId, long ctCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ctCollectionService.publishCTCollection(userId, ctCollectionId);
	}

	@Override
	public com.liferay.change.tracking.model.CTCollection undoCTCollection(
			long ctCollectionId, long userId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctCollectionService.undoCTCollection(
			ctCollectionId, userId, name, description);
	}

	@Override
	public com.liferay.change.tracking.model.CTCollection updateCTCollection(
			long userId, long ctCollectionId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctCollectionService.updateCTCollection(
			userId, ctCollectionId, name, description);
	}

	@Override
	public CTCollectionService getWrappedService() {
		return _ctCollectionService;
	}

	@Override
	public void setWrappedService(CTCollectionService ctCollectionService) {
		_ctCollectionService = ctCollectionService;
	}

	private CTCollectionService _ctCollectionService;

}