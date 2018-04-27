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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CPDefinitionOptionRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRelServiceUtil
 * @see com.liferay.commerce.product.service.base.CPDefinitionOptionRelServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionOptionRelServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPDefinitionOptionRel"}, service = CPDefinitionOptionRelService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionOptionRelService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionOptionRelServiceUtil} to access the cp definition option rel remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionRelServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPDefinitionOptionRel addCPDefinitionOptionRel(long cpDefinitionId,
		long cpOptionId, boolean importOptionValue,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinitionOptionRel addCPDefinitionOptionRel(long cpDefinitionId,
		long cpOptionId, Map<Locale, String> titleMap,
		Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
		double priority, boolean facetable, boolean required,
		boolean skuContributor, boolean importOptionValue,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinitionOptionRel addCPDefinitionOptionRel(long cpDefinitionId,
		long cpOptionId, ServiceContext serviceContext)
		throws PortalException;

	public CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		CPDefinitionOptionRel cpDefinitionOptionRel) throws PortalException;

	public CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		long cpDefinitionOptionRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionOptionRel fetchCPDefinitionOptionRel(
		long cpDefinitionOptionRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionOptionRel getCPDefinitionOptionRel(
		long cpDefinitionOptionRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, boolean skuContributor) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId,
		boolean skuContributor) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPDefinitionOptionRel> searchCPDefinitionOptionRels(
		long companyId, long groupId, long cpDefinitionId, String keywords,
		int start, int end, Sort sort) throws PortalException;

	public CPDefinitionOptionRel updateCPDefinitionOptionRel(
		long cpDefinitionOptionRelId, long cpOptionId,
		Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
		String ddmFormFieldTypeName, double priority, boolean facetable,
		boolean required, boolean skuContributor, ServiceContext serviceContext)
		throws PortalException;
}