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

package com.liferay.portal.security.service.access.policy.service;

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for SAPEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryServiceUtil
 * @see com.liferay.portal.security.service.access.policy.service.base.SAPEntryServiceBaseImpl
 * @see com.liferay.portal.security.service.access.policy.service.impl.SAPEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=sap", "json.web.service.context.path=SAPEntry"}, service = SAPEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SAPEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SAPEntryServiceUtil} to access the sap entry remote service. Add custom service methods to {@link com.liferay.portal.security.service.access.policy.service.impl.SAPEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public SAPEntry addSAPEntry(java.lang.String allowedServiceSignatures,
		boolean defaultSAPEntry, boolean enabled, java.lang.String name,
		Map<Locale, java.lang.String> titleMap, ServiceContext serviceContext)
		throws PortalException;

	public SAPEntry deleteSAPEntry(SAPEntry sapEntry) throws PortalException;

	public SAPEntry deleteSAPEntry(long sapEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry fetchSAPEntry(long companyId, java.lang.String name)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry getSAPEntry(long companyId, java.lang.String name)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry getSAPEntry(long sapEntryId) throws PortalException;

	public SAPEntry updateSAPEntry(long sapEntryId,
		java.lang.String allowedServiceSignatures, boolean defaultSAPEntry,
		boolean enabled, java.lang.String name,
		Map<Locale, java.lang.String> titleMap, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanySAPEntriesCount(long companyId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SAPEntry> getCompanySAPEntries(long companyId, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SAPEntry> getCompanySAPEntries(long companyId, int start,
		int end, OrderByComparator<SAPEntry> obc);
}