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

package com.liferay.commerce.tax.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTaxFixedRateAddressRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelService
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelServiceWrapper
	implements CommerceTaxFixedRateAddressRelService,
		ServiceWrapper<CommerceTaxFixedRateAddressRelService> {
	public CommerceTaxFixedRateAddressRelServiceWrapper(
		CommerceTaxFixedRateAddressRelService commerceTaxFixedRateAddressRelService) {
		_commerceTaxFixedRateAddressRelService = commerceTaxFixedRateAddressRelService;
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long cpTaxCategoryId, long commerceCountryId,
		long commerceRegionId, String zip, double rate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelService.addCommerceTaxFixedRateAddressRel(commerceTaxMethodId,
			cpTaxCategoryId, commerceCountryId, commerceRegionId, zip, rate,
			serviceContext);
	}

	@Override
	public void deleteCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceTaxFixedRateAddressRelService.deleteCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelService.fetchCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> getCommerceTaxMethodFixedRateAddressRels(
		long groupId, long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelService.getCommerceTaxMethodFixedRateAddressRels(groupId,
			commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxMethodFixedRateAddressRelsCount(long groupId,
		long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelService.getCommerceTaxMethodFixedRateAddressRelsCount(groupId,
			commerceTaxMethodId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceTaxFixedRateAddressRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId, long commerceCountryId,
		long commerceRegionId, String zip, double rate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateAddressRelService.updateCommerceTaxFixedRateAddressRel(commerceTaxFixedRateAddressRelId,
			commerceCountryId, commerceRegionId, zip, rate);
	}

	@Override
	public CommerceTaxFixedRateAddressRelService getWrappedService() {
		return _commerceTaxFixedRateAddressRelService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxFixedRateAddressRelService commerceTaxFixedRateAddressRelService) {
		_commerceTaxFixedRateAddressRelService = commerceTaxFixedRateAddressRelService;
	}

	private CommerceTaxFixedRateAddressRelService _commerceTaxFixedRateAddressRelService;
}