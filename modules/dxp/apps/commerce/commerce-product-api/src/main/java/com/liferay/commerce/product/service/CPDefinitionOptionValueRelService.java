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

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;

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
 * Provides the remote service interface for CPDefinitionOptionValueRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRelServiceUtil
 * @see com.liferay.commerce.product.service.base.CPDefinitionOptionValueRelServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionOptionValueRelServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPDefinitionOptionValueRel"}, service = CPDefinitionOptionValueRelService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionOptionValueRelService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionOptionValueRelServiceUtil} to access the cp definition option value rel remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionOptionValueRelServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId, Map<Locale, String> nameMap,
		double priority, String key, ServiceContext serviceContext)
		throws PortalException;

	public CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws PortalException;

	public CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long groupId, String key, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionOptionValueRelsCount(long cpDefinitionOptionRelId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPDefinitionOptionValueRel> searchCPDefinitionOptionValueRels(
		long companyId, long groupId, long cpDefinitionOptionRelId,
		String keywords, int start, int end, Sort sort)
		throws PortalException;

	public CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId, Map<Locale, String> nameMap,
		double priority, String key, ServiceContext serviceContext)
		throws PortalException;
}