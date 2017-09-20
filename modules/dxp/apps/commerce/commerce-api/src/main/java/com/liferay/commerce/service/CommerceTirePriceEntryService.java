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

import com.liferay.commerce.model.CommerceTirePriceEntry;

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

/**
 * Provides the remote service interface for CommerceTirePriceEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTirePriceEntryServiceUtil
 * @see com.liferay.commerce.service.base.CommerceTirePriceEntryServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTirePriceEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceTirePriceEntry"}, service = CommerceTirePriceEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceTirePriceEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTirePriceEntryServiceUtil} to access the commerce tire price entry remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTirePriceEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceTirePriceEntry addCommerceTirePriceEntry(
		long commercePriceEntryId, double price, int minQuantity,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommerceTirePriceEntry(long commerceTirePriceEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTirePriceEntry fetchCommerceTirePriceEntry(
		long commerceTirePriceEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTirePriceEntriesCount(long commercePriceEntryId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommerceTirePriceEntry> searchCommerceTirePriceEntries(
		long companyId, long groupId, long commercePriceEntryId,
		java.lang.String keywords, int start, int end, Sort sort)
		throws PortalException;

	public CommerceTirePriceEntry updateCommerceTirePriceEntry(
		long commerceTirePriceEntryId, double price, int minQuantity,
		ServiceContext serviceContext) throws PortalException;
}