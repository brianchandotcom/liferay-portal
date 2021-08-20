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

package com.liferay.commerce.tax.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTaxMethodService}.
 *
 * @author Marco Leo
 * @see CommerceTaxMethodService
 * @generated
 */
public class CommerceTaxMethodServiceWrapper
	implements CommerceTaxMethodService,
			   ServiceWrapper<CommerceTaxMethodService> {

	public CommerceTaxMethodServiceWrapper(
		CommerceTaxMethodService commerceTaxMethodService) {

		_commerceTaxMethodService = commerceTaxMethodService;
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxMethod
			addCommerceTaxMethod(
				long groupId, java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String engineKey, boolean percentage, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxMethodService.addCommerceTaxMethod(
			groupId, nameMap, descriptionMap, engineKey, percentage, active);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.tax.model.CommerceTaxMethod
			addCommerceTaxMethod(
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String engineKey, boolean percentage, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxMethodService.addCommerceTaxMethod(
			nameMap, descriptionMap, engineKey, percentage, active,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxMethod
			createCommerceTaxMethod(long groupId, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxMethodService.createCommerceTaxMethod(
			groupId, commerceTaxMethodId);
	}

	@Override
	public void deleteCommerceTaxMethod(long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceTaxMethodService.deleteCommerceTaxMethod(commerceTaxMethodId);
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxMethod
			fetchCommerceTaxMethod(long groupId, String engineKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxMethodService.fetchCommerceTaxMethod(
			groupId, engineKey);
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxMethod
			getCommerceTaxMethod(long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxMethodService.getCommerceTaxMethod(
			commerceTaxMethodId);
	}

	@Override
	public java.util.List<com.liferay.commerce.tax.model.CommerceTaxMethod>
			getCommerceTaxMethods(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxMethodService.getCommerceTaxMethods(groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.tax.model.CommerceTaxMethod>
			getCommerceTaxMethods(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxMethodService.getCommerceTaxMethods(groupId, active);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceTaxMethodService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxMethod setActive(
			long commerceTaxMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxMethodService.setActive(commerceTaxMethodId, active);
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxMethod
			updateCommerceTaxMethod(
				long commerceTaxMethodId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				boolean percentage, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxMethodService.updateCommerceTaxMethod(
			commerceTaxMethodId, nameMap, descriptionMap, percentage, active);
	}

	@Override
	public CommerceTaxMethodService getWrappedService() {
		return _commerceTaxMethodService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxMethodService commerceTaxMethodService) {

		_commerceTaxMethodService = commerceTaxMethodService;
	}

	private CommerceTaxMethodService _commerceTaxMethodService;

}