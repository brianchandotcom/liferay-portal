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

package com.liferay.commerce.user.segment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;

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
 * Provides the remote service interface for CommerceUserSegmentCriterion. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterionServiceUtil
 * @see com.liferay.commerce.user.segment.service.base.CommerceUserSegmentCriterionServiceBaseImpl
 * @see com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentCriterionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceUserSegmentCriterion"}, service = CommerceUserSegmentCriterionService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceUserSegmentCriterionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceUserSegmentCriterionServiceUtil} to access the commerce user segment criterion remote service. Add custom service methods to {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentCriterionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
		long commerceUserSegmentEntryId, String type, String typeSettings,
		double priority, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceUserSegmentCriterion> getCommerceUserSegmentCriteria(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceUserSegmentCriteriaCount(
		long commerceUserSegmentEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceUserSegmentCriterion getCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommerceUserSegmentCriterion updateCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId, String type, String typeSettings,
		double priority, ServiceContext serviceContext)
		throws PortalException;
}