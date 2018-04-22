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

import com.liferay.commerce.product.model.CPInstance;

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

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service interface for CPInstance. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPInstanceServiceUtil
 * @see com.liferay.commerce.product.service.base.CPInstanceServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPInstanceServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPInstance"}, service = CPInstanceService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPInstanceService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPInstanceServiceUtil} to access the cp instance remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPInstanceServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPInstance addCPInstance(long cpDefinitionId, java.lang.String sku,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		boolean purchasable, java.lang.String ddmContent, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public CPInstance addCPInstance(long cpDefinitionId, java.lang.String sku,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		boolean purchasable, java.lang.String ddmContent, double width,
		double height, double depth, double weight, BigDecimal cost,
		BigDecimal price, boolean published, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public void buildCPInstances(long cpDefinitionId,
		ServiceContext serviceContext) throws PortalException;

	public CPInstance deleteCPInstance(CPInstance cpInstance)
		throws PortalException;

	public CPInstance deleteCPInstance(long cpInstanceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstance fetchCPInstance(long cpInstanceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPDefinitionInstances(long cpDefinitionId,
		int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPDefinitionInstances(long cpDefinitionId,
		int status, int start, int end,
		OrderByComparator<CPInstance> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionInstancesCount(long cpDefinitionId, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstance getCPInstance(long cpInstanceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPInstances(long groupId, int status, int start,
		int end, OrderByComparator<CPInstance> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPInstancesCount(long groupId, int status)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPInstance> searchCPDefinitionInstances(
		long companyId, long groupId, long cpDefinitionId,
		java.lang.String keywords, int status, int start, int end, Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPInstance> searchCPInstances(long companyId,
		long groupId, java.lang.String keywords, int status, int start,
		int end, Sort sort) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPInstance> searchCPInstances(
		SearchContext searchContext) throws PortalException;

	public CPInstance updateCPInstance(long cpInstanceId, java.lang.String sku,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		boolean purchasable, boolean published, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public CPInstance updateCPInstance(long cpInstanceId, java.lang.String sku,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		boolean purchasable, double width, double height, double depth,
		double weight, BigDecimal cost, BigDecimal price, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public CPInstance updatePricingInfo(long cpInstanceId, BigDecimal cost,
		BigDecimal price, ServiceContext serviceContext)
		throws PortalException;

	public CPInstance updateShippingInfo(long cpInstanceId, double width,
		double height, double depth, double weight,
		ServiceContext serviceContext) throws PortalException;

	public CPInstance updateStatus(long userId, long cpInstanceId, int status,
		ServiceContext serviceContext,
		Map<java.lang.String, Serializable> workflowContext)
		throws PortalException;
}