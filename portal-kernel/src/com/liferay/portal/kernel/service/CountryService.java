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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for Country. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CountryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CountryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.service.impl.CountryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the country remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CountryServiceUtil} if injection and service tracking are not available.
	 */
	public Country addCountry(
			String name, String a2, String a3, String number, String idd,
			boolean active)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Country fetchCountry(long countryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Country fetchCountryByA2(String a2);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Country fetchCountryByA3(String a3);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Country> getCountries();

	@AccessControlled(guestAccessEnabled = true)
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Country> getCountries(boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Country getCountry(long countryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Country getCountryByA2(String a2) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Country getCountryByA3(String a3) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Country getCountryByName(String name) throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

}