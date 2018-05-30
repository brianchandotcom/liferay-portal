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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTaxMethodService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxMethodService
 * @generated
 */
@ProviderType
public class CommerceTaxMethodServiceWrapper implements CommerceTaxMethodService,
	ServiceWrapper<CommerceTaxMethodService> {
	public CommerceTaxMethodServiceWrapper(
		CommerceTaxMethodService commerceTaxMethodService) {
		_commerceTaxMethodService = commerceTaxMethodService;
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxMethod addCommerceTaxMethod(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String engineKey, boolean percentage, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxMethodService.addCommerceTaxMethod(nameMap,
			descriptionMap, engineKey, percentage, active, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxMethod createCommerceTaxMethod(
		long commerceTaxMethodId) {
		return _commerceTaxMethodService.createCommerceTaxMethod(commerceTaxMethodId);
	}

	@Override
	public void deleteCommerceTaxMethod(long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceTaxMethodService.deleteCommerceTaxMethod(commerceTaxMethodId);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxMethod fetchCommerceTaxMethod(
		long groupId, String engineKey) {
		return _commerceTaxMethodService.fetchCommerceTaxMethod(groupId,
			engineKey);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxMethod getCommerceTaxMethod(
		long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxMethodService.getCommerceTaxMethod(commerceTaxMethodId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxMethod> getCommerceTaxMethods(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxMethodService.getCommerceTaxMethods(groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxMethod> getCommerceTaxMethods(
		long groupId, boolean active) {
		return _commerceTaxMethodService.getCommerceTaxMethods(groupId, active);
	}

	@Override
	public int getCommerceTaxMethodsCount(long groupId, boolean active) {
		return _commerceTaxMethodService.getCommerceTaxMethodsCount(groupId,
			active);
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
	public com.liferay.commerce.model.CommerceTaxMethod setActive(
		long commerceTaxMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxMethodService.setActive(commerceTaxMethodId, active);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxMethod updateCommerceTaxMethod(
		long commerceTaxMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean percentage, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxMethodService.updateCommerceTaxMethod(commerceTaxMethodId,
			nameMap, descriptionMap, percentage, active);
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