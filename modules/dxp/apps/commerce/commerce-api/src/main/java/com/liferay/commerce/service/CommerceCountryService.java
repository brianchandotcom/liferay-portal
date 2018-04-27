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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceCountry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
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
 * Provides the remote service interface for CommerceCountry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryServiceUtil
 * @see com.liferay.commerce.service.base.CommerceCountryServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceCountryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceCountry"}, service = CommerceCountryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceCountryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCountryServiceUtil} to access the commerce country remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceCountryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceCountry addCommerceCountry(Map<Locale, String> nameMap,
		boolean billingAllowed, boolean shippingAllowed,
		String twoLettersISOCode, String threeLettersISOCode,
		int numericISOCode, boolean subjectToVAT, double priority,
		boolean active, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommerceCountry(long commerceCountryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceCountry> getBillingCommerceCountries(long groupId,
		boolean billingAllowed, boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceCountry> getCommerceCountries(long groupId,
		boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceCountry> getCommerceCountries(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceCountry> getCommerceCountries(long groupId, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceCountriesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceCountriesCount(long groupId, boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceCountry getCommerceCountry(long commerceCountryId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceCountry> getShippingCommerceCountries(long groupId,
		boolean shippingAllowed, boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceCountry> getWarehouseCommerceCountries(long groupId,
		boolean all) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommerceCountry> searchCommerceCountries(
		SearchContext searchContext) throws PortalException;

	public CommerceCountry updateCommerceCountry(long commerceCountryId,
		Map<Locale, String> nameMap, boolean billingAllowed,
		boolean shippingAllowed, String twoLettersISOCode,
		String threeLettersISOCode, int numericISOCode, boolean subjectToVAT,
		double priority, boolean active, ServiceContext serviceContext)
		throws PortalException;
}