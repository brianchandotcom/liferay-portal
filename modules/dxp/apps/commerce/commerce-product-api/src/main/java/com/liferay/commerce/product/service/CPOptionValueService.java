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

import com.liferay.commerce.product.model.CPOptionValue;

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
 * Provides the remote service interface for CPOptionValue. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionValueServiceUtil
 * @see com.liferay.commerce.product.service.base.CPOptionValueServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPOptionValue"}, service = CPOptionValueService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPOptionValueService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPOptionValueServiceUtil} to access the cp option value remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPOptionValue addCPOptionValue(long cpOptionId,
		Map<Locale, String> titleMap, double priority, String key,
		ServiceContext serviceContext) throws PortalException;

	public CPOptionValue deleteCPOptionValue(CPOptionValue cpOptionValue)
		throws PortalException;

	public CPOptionValue deleteCPOptionValue(long cpOptionValueId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOptionValue fetchCPOptionValue(long cpOptionValueId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOptionValue getCPOptionValue(long cpOptionValueId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOptionValue> getCPOptionValues(long cpOptionId, int start,
		int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOptionValue> getCPOptionValues(long cpOptionId, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPOptionValuesCount(long cpOptionId)
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
	public BaseModelSearchResult<CPOptionValue> searchCPOptionValues(
		long companyId, long groupId, long cpOptionId, String keywords,
		int start, int end, Sort sort) throws PortalException;

	public CPOptionValue updateCPOptionValue(long cpOptionValueId,
		Map<Locale, String> titleMap, double priority, String key,
		ServiceContext serviceContext) throws PortalException;
}