/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceNotificationTemplateCommerceAccountGroupRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateCommerceAccountGroupRelService
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelServiceWrapper
	implements CommerceNotificationTemplateCommerceAccountGroupRelService,
			   ServiceWrapper
				   <CommerceNotificationTemplateCommerceAccountGroupRelService> {

	public CommerceNotificationTemplateCommerceAccountGroupRelServiceWrapper() {
		this(null);
	}

	public CommerceNotificationTemplateCommerceAccountGroupRelServiceWrapper(
		CommerceNotificationTemplateCommerceAccountGroupRelService
			commerceNotificationTemplateCommerceAccountGroupRelService) {

		_commerceNotificationTemplateCommerceAccountGroupRelService =
			commerceNotificationTemplateCommerceAccountGroupRelService;
	}

	@Override
	public com.liferay.commerce.notification.model.
		CommerceNotificationTemplateCommerceAccountGroupRel
				addCommerceNotificationTemplateCommerceAccountGroupRel(
					long commerceNotificationTemplateId,
					long commerceAccountGroupId,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateCommerceAccountGroupRelService.
			addCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateId, commerceAccountGroupId,
				serviceContext);
	}

	@Override
	public void deleteCommerceNotificationTemplateCommerceAccountGroupRel(
			long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceNotificationTemplateCommerceAccountGroupRelService.
			deleteCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	@Override
	public com.liferay.commerce.notification.model.
		CommerceNotificationTemplateCommerceAccountGroupRel
				fetchCommerceNotificationTemplateCommerceAccountGroupRel(
					long commerceNotificationTemplateId,
					long commerceAccountGroupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateCommerceAccountGroupRelService.
			fetchCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateId, commerceAccountGroupId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.notification.model.
			CommerceNotificationTemplateCommerceAccountGroupRel>
					getCommerceNotificationTemplateCommerceAccountGroupRels(
						long commerceNotificationTemplateId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.notification.model.
								CommerceNotificationTemplateCommerceAccountGroupRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateCommerceAccountGroupRelService.
			getCommerceNotificationTemplateCommerceAccountGroupRels(
				commerceNotificationTemplateId, start, end, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceNotificationTemplateCommerceAccountGroupRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRelService
		getWrappedService() {

		return _commerceNotificationTemplateCommerceAccountGroupRelService;
	}

	@Override
	public void setWrappedService(
		CommerceNotificationTemplateCommerceAccountGroupRelService
			commerceNotificationTemplateCommerceAccountGroupRelService) {

		_commerceNotificationTemplateCommerceAccountGroupRelService =
			commerceNotificationTemplateCommerceAccountGroupRelService;
	}

	private CommerceNotificationTemplateCommerceAccountGroupRelService
		_commerceNotificationTemplateCommerceAccountGroupRelService;

}