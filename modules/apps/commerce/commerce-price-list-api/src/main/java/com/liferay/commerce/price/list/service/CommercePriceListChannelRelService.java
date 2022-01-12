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

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
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
 * Provides the remote service interface for CommercePriceListChannelRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListChannelRelServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommercePriceListChannelRel"
	},
	service = CommercePriceListChannelRelService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommercePriceListChannelRelService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListChannelRelServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce price list channel rel remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommercePriceListChannelRelServiceUtil} if injection and service tracking are not available.
	 */
	public CommercePriceListChannelRel addCommercePriceListChannelRel(
			long commercePriceListId, long commerceChannelId, int order,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws PortalException;

	public void deleteCommercePriceListChannelRelsByCommercePriceListId(
			long commercePriceListId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListChannelRel fetchCommercePriceListChannelRel(
			long commerceChannelId, long commercePriceListId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListChannelRel getCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
			long commercePriceListId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListChannelRel> getCommercePriceListChannelRels(
		long commercePriceListId, String name, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceListChannelRelsCount(long commercePriceListId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceListChannelRelsCount(
		long commercePriceListId, String name);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

}