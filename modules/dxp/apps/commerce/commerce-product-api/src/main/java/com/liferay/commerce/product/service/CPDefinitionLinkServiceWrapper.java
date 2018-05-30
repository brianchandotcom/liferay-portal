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
 * Provides a wrapper for {@link CPDefinitionLinkService}.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkService
 * @generated
 */
@ProviderType
public class CPDefinitionLinkServiceWrapper implements CPDefinitionLinkService,
	ServiceWrapper<CPDefinitionLinkService> {
	public CPDefinitionLinkServiceWrapper(
		CPDefinitionLinkService cpDefinitionLinkService) {
		_cpDefinitionLinkService = cpDefinitionLinkService;
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink deleteCPDefinitionLink(
		long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkService.deleteCPDefinitionLink(cpDefinitionLinkId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink fetchCPDefinitionLink(
		long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkService.fetchCPDefinitionLink(cpDefinitionLinkId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink getCPDefinitionLink(
		long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkService.getCPDefinitionLink(cpDefinitionLinkId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId1, String type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkService.getCPDefinitionLinks(cpDefinitionId1,
			type);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionLink> getCPDefinitionLinks(
		long cpDefinitionId1, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionLink> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkService.getCPDefinitionLinks(cpDefinitionId1,
			type, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionLinksCount(long cpDefinitionId1, String type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkService.getCPDefinitionLinksCount(cpDefinitionId1,
			type);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionLinkService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionLink updateCPDefinitionLink(
		long cpDefinitionLinkId, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLinkService.updateCPDefinitionLink(cpDefinitionLinkId,
			priority, serviceContext);
	}

	@Override
	public void updateCPDefinitionLinks(long cpDefinitionId1,
		long[] cpDefinitionIds2, String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionLinkService.updateCPDefinitionLinks(cpDefinitionId1,
			cpDefinitionIds2, type, serviceContext);
	}

	@Override
	public CPDefinitionLinkService getWrappedService() {
		return _cpDefinitionLinkService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionLinkService cpDefinitionLinkService) {
		_cpDefinitionLinkService = cpDefinitionLinkService;
	}

	private CPDefinitionLinkService _cpDefinitionLinkService;
}