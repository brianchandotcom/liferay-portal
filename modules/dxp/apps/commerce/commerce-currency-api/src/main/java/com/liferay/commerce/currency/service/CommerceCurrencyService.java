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

package com.liferay.commerce.currency.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.currency.model.CommerceCurrency;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CommerceCurrency. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCurrencyServiceUtil
 * @see com.liferay.commerce.currency.service.base.CommerceCurrencyServiceBaseImpl
 * @see com.liferay.commerce.currency.service.impl.CommerceCurrencyServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceCurrency"}, service = CommerceCurrencyService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceCurrencyService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCurrencyServiceUtil} to access the commerce currency remote service. Add custom service methods to {@link com.liferay.commerce.currency.service.impl.CommerceCurrencyServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceCurrency addCommerceCurrency(String code,
		Map<Locale, String> nameMap, BigDecimal rate, String roundingType,
		boolean primary, double priority, boolean active,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommerceCurrency(long commerceCurrencyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceCurrency fetchPrimaryCommerceCurrency(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceCurrency> getCommerceCurrencies(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceCurrency> getCommerceCurrencies(long groupId,
		int start, int end,
		OrderByComparator<CommerceCurrency> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceCurrenciesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceCurrenciesCount(long groupId, boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceCurrency getCommerceCurrency(long commerceCurrencyId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommerceCurrency setActive(long commerceCurrencyId, boolean active)
		throws PortalException;

	public CommerceCurrency setPrimary(long commerceCurrencyId, boolean primary)
		throws PortalException;

	public CommerceCurrency updateCommerceCurrency(long commerceCurrencyId,
		String code, Map<Locale, String> nameMap, BigDecimal rate,
		String roundingType, boolean primary, double priority, boolean active,
		ServiceContext serviceContext) throws PortalException;

	public void updateExchangeRate(long commerceCurrencyId,
		String exchangeRateProviderKey) throws PortalException;

	public void updateExchangeRates(ServiceContext serviceContext)
		throws PortalException;
}