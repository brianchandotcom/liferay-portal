/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionLinkService}.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkService
 * @generated
 */
public class CPDefinitionLinkServiceWrapper
	implements CPDefinitionLinkService,
			   ServiceWrapper<CPDefinitionLinkService> {

	public CPDefinitionLinkServiceWrapper() {
		this(null);
	}

	public CPDefinitionLinkServiceWrapper(
		CPDefinitionLinkService cpDefinitionLinkService) {

		_cpDefinitionLinkService = cpDefinitionLinkService;
	}

	@Override
	public CPDefinitionLink addCPDefinitionLink(
			long cpDefinitionId, long cProductId, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, double priority, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.addCPDefinitionLink(
			cpDefinitionId, cProductId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, priority,
			type, serviceContext);
	}

	@Override
	public void deleteCPDefinitionLink(long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpDefinitionLinkService.deleteCPDefinitionLink(cpDefinitionLinkId);
	}

	@Override
	public CPDefinitionLink fetchCPDefinitionLink(long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.fetchCPDefinitionLink(
			cpDefinitionLinkId);
	}

	@Override
	public CPDefinitionLink fetchCPDefinitionLink(
			long cpDefinitionId, long cProductId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.fetchCPDefinitionLink(
			cpDefinitionId, cProductId, type);
	}

	@Override
	public CPDefinitionLink getCPDefinitionLink(long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLink(cpDefinitionLinkId);
	}

	@Override
	public java.util.List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinks(cpDefinitionId);
	}

	@Override
	public java.util.List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinks(
			cpDefinitionId, status);
	}

	@Override
	public java.util.List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinks(
			cpDefinitionId, start, end);
	}

	@Override
	public java.util.List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinks(
			cpDefinitionId, status, start, end);
	}

	@Override
	public java.util.List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinks(
			cpDefinitionId, type);
	}

	@Override
	public java.util.List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, String type, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinks(
			cpDefinitionId, type, status);
	}

	@Override
	public java.util.List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, String type, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinks(
			cpDefinitionId, type, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<CPDefinitionLink> getCPDefinitionLinks(
			long cpDefinitionId, String type, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionLink>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinks(
			cpDefinitionId, type, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionLinksCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinksCount(
			cpDefinitionId);
	}

	@Override
	public int getCPDefinitionLinksCount(long cpDefinitionId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinksCount(
			cpDefinitionId, status);
	}

	@Override
	public int getCPDefinitionLinksCount(long cpDefinitionId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinksCount(
			cpDefinitionId, type);
	}

	@Override
	public int getCPDefinitionLinksCount(
			long cpDefinitionId, String type, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.getCPDefinitionLinksCount(
			cpDefinitionId, type, status);
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
	public CPDefinitionLink updateCPDefinitionLink(
			long cpDefinitionLinkId, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, double priority,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionLinkService.updateCPDefinitionLink(
			cpDefinitionLinkId, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, priority,
			serviceContext);
	}

	@Override
	public void updateCPDefinitionLinks(
			long cpDefinitionId, long[] cpDefinitionIds2, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpDefinitionLinkService.updateCPDefinitionLinks(
			cpDefinitionId, cpDefinitionIds2, type, serviceContext);
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