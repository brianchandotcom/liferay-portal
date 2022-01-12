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

package com.liferay.commerce.price.list.service;

import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.portal.kernel.change.tracking.CTAware;
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

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommercePriceListCommerceAccountGroupRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListCommerceAccountGroupRelServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommercePriceListCommerceAccountGroupRel"
	},
	service = CommercePriceListCommerceAccountGroupRelService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommercePriceListCommerceAccountGroupRelService
	extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListCommerceAccountGroupRelServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce price list commerce account group rel remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommercePriceListCommerceAccountGroupRelServiceUtil} if injection and service tracking are not available.
	 */
	public CommercePriceListCommerceAccountGroupRel
			addCommercePriceListCommerceAccountGroupRel(
				long commercePriceListId, long commerceAccountGroupId,
				int order, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommercePriceListAccountGroupRelsByCommercePriceListId(
			long commercePriceListId)
		throws PortalException;

	public void deleteCommercePriceListCommerceAccountGroupRel(
			long commercePriceListCommerceAccountGroupRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListCommerceAccountGroupRel
			fetchCommercePriceListCommerceAccountGroupRel(
				long commercePriceListId, long commerceAccountGroupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListCommerceAccountGroupRel
			getCommercePriceListCommerceAccountGroupRel(
				long commercePriceListCommerceAccoungGroupRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListCommerceAccountGroupRel>
			getCommercePriceListCommerceAccountGroupRels(
				long commercePriceListId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListCommerceAccountGroupRel>
			getCommercePriceListCommerceAccountGroupRels(
				long commercePriceListId, int start, int end,
				OrderByComparator<CommercePriceListCommerceAccountGroupRel>
					orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListCommerceAccountGroupRel>
		getCommercePriceListCommerceAccountGroupRels(
			long commercePriceListId, String name, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceListCommerceAccountGroupRelsCount(
			long commercePriceListId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceListCommerceAccountGroupRelsCount(
		long commercePriceListId, String name);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommercePriceListCommerceAccountGroupRel
			updateCommercePriceListCommerceAccountGroupRel(
				long commercePriceListCommerceAccountGroupRelId, int order,
				ServiceContext serviceContext)
		throws PortalException;

}