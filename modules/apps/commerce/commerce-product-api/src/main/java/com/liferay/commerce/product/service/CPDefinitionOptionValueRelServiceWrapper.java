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

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionOptionValueRelService}.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRelService
 * @generated
 */
public class CPDefinitionOptionValueRelServiceWrapper
	implements CPDefinitionOptionValueRelService,
			   ServiceWrapper<CPDefinitionOptionValueRelService> {

	public CPDefinitionOptionValueRelServiceWrapper() {
		this(null);
	}

	public CPDefinitionOptionValueRelServiceWrapper(
		CPDefinitionOptionValueRelService cpDefinitionOptionValueRelService) {

		_cpDefinitionOptionValueRelService = cpDefinitionOptionValueRelService;
	}

	@Override
	public CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
			long cpDefinitionOptionRelId,
			java.util.Map<java.util.Locale, String> nameMap, double priority,
			String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.addCPDefinitionOptionValueRel(
			cpDefinitionOptionRelId, nameMap, priority, key, serviceContext);
	}

	@Override
	public CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	@Override
	public CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			fetchCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	@Override
	public CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
			long cpDefinitionOptionRelId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			fetchCPDefinitionOptionValueRel(cpDefinitionOptionRelId, key);
	}

	@Override
	public CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRel(
			cpDefinitionOptionValueRelId);
	}

	@Override
	public java.util.List<CPDefinitionOptionValueRel>
			getCPDefinitionOptionValueRels(
				long cpDefinitionOptionRelId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			getCPDefinitionOptionValueRels(cpDefinitionOptionRelId, start, end);
	}

	@Override
	public java.util.List<CPDefinitionOptionValueRel>
			getCPDefinitionOptionValueRels(
				long cpDefinitionOptionRelId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			getCPDefinitionOptionValueRels(
				cpDefinitionOptionRelId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<CPDefinitionOptionValueRel>
			getCPDefinitionOptionValueRels(
				long groupId, String key, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			getCPDefinitionOptionValueRels(groupId, key, start, end);
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount(long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			getCPDefinitionOptionValueRelsCount(cpDefinitionOptionRelId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionOptionValueRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CPDefinitionOptionValueRel resetCPInstanceCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			resetCPInstanceCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId);
	}

	/**
	 * @param companyId
	 * @param groupId
	 * @param cpDefinitionOptionRelId
	 * @param keywords
	 * @param start
	 * @param end
	 * @param sort
	 * @return
	 * @throws PortalException
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #searchCPDefinitionOptionValueRels(long, long, long, String,
	 int, int, Sort[])}
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<CPDefinitionOptionValueRel> searchCPDefinitionOptionValueRels(
				long companyId, long groupId, long cpDefinitionOptionRelId,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			searchCPDefinitionOptionValueRels(
				companyId, groupId, cpDefinitionOptionRelId, keywords, start,
				end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<CPDefinitionOptionValueRel> searchCPDefinitionOptionValueRels(
				long companyId, long groupId, long cpDefinitionOptionRelId,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort[] sorts)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			searchCPDefinitionOptionValueRels(
				companyId, groupId, cpDefinitionOptionRelId, keywords, start,
				end, sorts);
	}

	@Override
	public int searchCPDefinitionOptionValueRelsCount(
			long companyId, long groupId, long cpDefinitionOptionRelId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			searchCPDefinitionOptionValueRelsCount(
				companyId, groupId, cpDefinitionOptionRelId, keywords);
	}

	/**
	 * @param cpDefinitionOptionValueRelId
	 * @param nameMap
	 * @param priority
	 * @param key
	 * @param cpInstanceId
	 * @param quantity
	 * @param price
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link
	 #updateCPDefinitionOptionValueRel(long, Map, double, String,
	 long, int, boolean, BigDecimal, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId,
			java.util.Map<java.util.Locale, String> nameMap, double priority,
			String key, long cpInstanceId, int quantity,
			java.math.BigDecimal price,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			updateCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId, nameMap, priority, key,
				cpInstanceId, quantity, price, serviceContext);
	}

	@Override
	public CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId,
			java.util.Map<java.util.Locale, String> nameMap, double priority,
			String key, long cpInstanceId, int quantity, boolean preselected,
			java.math.BigDecimal price,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			updateCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId, nameMap, priority, key,
				cpInstanceId, quantity, preselected, price, serviceContext);
	}

	/**
	 * @param cpDefinitionOptionValueRelId
	 * @param nameMap
	 * @param priority
	 * @param key
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link
	 #updateCPDefinitionOptionValueRel(long, Map, double, String,
	 long, int, boolean, BigDecimal, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId,
			java.util.Map<java.util.Locale, String> nameMap, double priority,
			String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			updateCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId, nameMap, priority, key,
				serviceContext);
	}

	@Override
	public CPDefinitionOptionValueRel
			updateCPDefinitionOptionValueRelPreselected(
				long cpDefinitionOptionValueRelId, boolean preselected)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionOptionValueRelService.
			updateCPDefinitionOptionValueRelPreselected(
				cpDefinitionOptionValueRelId, preselected);
	}

	@Override
	public CPDefinitionOptionValueRelService getWrappedService() {
		return _cpDefinitionOptionValueRelService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionOptionValueRelService cpDefinitionOptionValueRelService) {

		_cpDefinitionOptionValueRelService = cpDefinitionOptionValueRelService;
	}

	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

}