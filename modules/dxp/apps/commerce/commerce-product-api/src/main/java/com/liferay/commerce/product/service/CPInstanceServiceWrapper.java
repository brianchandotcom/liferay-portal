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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPInstanceService}.
 *
 * @author Marco Leo
 * @see CPInstanceService
 * @generated
 */
@ProviderType
public class CPInstanceServiceWrapper implements CPInstanceService,
	ServiceWrapper<CPInstanceService> {
	public CPInstanceServiceWrapper(CPInstanceService cpInstanceService) {
		_cpInstanceService = cpInstanceService;
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance addCPInstance(
		long cpDefinitionId, String sku, String gtin,
		String manufacturerPartNumber, boolean purchasable, String ddmContent,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.addCPInstance(cpDefinitionId, sku, gtin,
			manufacturerPartNumber, purchasable, ddmContent, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public void buildCPInstances(long cpDefinitionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpInstanceService.buildCPInstances(cpDefinitionId, serviceContext);
	}

	@Override
	public void deleteCPInstance(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpInstanceService.deleteCPInstance(cpInstanceId);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance fetchCPInstance(
		long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.fetchCPInstance(cpInstanceId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPInstance> getCPDefinitionInstances(
		long cpDefinitionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPInstance> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.getCPDefinitionInstances(cpDefinitionId,
			status, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionInstancesCount(long cpDefinitionId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.getCPDefinitionInstancesCount(cpDefinitionId,
			status);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance getCPInstance(
		long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.getCPInstance(cpInstanceId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPInstance> getCPInstances(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPInstance> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.getCPInstances(groupId, status, start, end,
			orderByComparator);
	}

	@Override
	public int getCPInstancesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.getCPInstancesCount(groupId, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpInstanceService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPDefinitionInstances(
		long companyId, long groupId, long cpDefinitionId, String keywords,
		int status, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.searchCPDefinitionInstances(companyId,
			groupId, cpDefinitionId, keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPInstances(
		long companyId, long groupId, String keywords, int status, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.searchCPInstances(companyId, groupId,
			keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPInstances(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.searchCPInstances(searchContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updateCPInstance(
		long cpInstanceId, String sku, String gtin,
		String manufacturerPartNumber, boolean purchasable, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.updateCPInstance(cpInstanceId, sku, gtin,
			manufacturerPartNumber, purchasable, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updatePricingInfo(
		long cpInstanceId, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice, java.math.BigDecimal cost,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.updatePricingInfo(cpInstanceId, price,
			promoPrice, cost, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updateShippingInfo(
		long cpInstanceId, double width, double height, double depth,
		double weight,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.updateShippingInfo(cpInstanceId, width,
			height, depth, weight, serviceContext);
	}

	@Override
	public CPInstanceService getWrappedService() {
		return _cpInstanceService;
	}

	@Override
	public void setWrappedService(CPInstanceService cpInstanceService) {
		_cpInstanceService = cpInstanceService;
	}

	private CPInstanceService _cpInstanceService;
}