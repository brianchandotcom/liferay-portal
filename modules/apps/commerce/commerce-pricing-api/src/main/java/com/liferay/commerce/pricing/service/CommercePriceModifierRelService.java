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

package com.liferay.commerce.pricing.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
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

/**
 * Provides the remote service interface for CommercePriceModifierRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierRelServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommercePriceModifierRel"
	},
	service = CommercePriceModifierRelService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommercePriceModifierRelService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceModifierRelServiceUtil} to access the commerce price modifier rel remote service. Add custom service methods to <code>com.liferay.commerce.pricing.service.impl.CommercePriceModifierRelServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommercePriceModifierRel addCommercePriceModifierRel(
			long commercePriceModifierId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommercePriceModifierRel(long commercePriceModifierRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceModifierRel fetchCommercePriceModifierRel(
			long commercePriceModifierId, String className, long classPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getClassPKs(long commercePriceModifierRelId, String className)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceModifierRel getCommercePriceModifierRel(
			long commercePriceModifierRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceModifierRel> getCommercePriceModifierRels(
			long commercePriceModifierRelId, String className)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceModifierRel> getCommercePriceModifierRels(
			long commercePriceModifierRelId, String className, int start,
			int end,
			OrderByComparator<CommercePriceModifierRel> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceModifierRelsCount(
			long commercePriceModifierRelId, String className)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

}