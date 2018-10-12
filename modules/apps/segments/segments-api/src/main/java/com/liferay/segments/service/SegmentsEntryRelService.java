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

package com.liferay.segments.service;

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

import com.liferay.segments.model.SegmentsEntryRel;

import java.util.List;

/**
 * Provides the remote service interface for SegmentsEntryRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRelServiceUtil
 * @see com.liferay.segments.service.base.SegmentsEntryRelServiceBaseImpl
 * @see com.liferay.segments.service.impl.SegmentsEntryRelServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=segments", "json.web.service.context.path=SegmentsEntryRel"}, service = SegmentsEntryRelService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SegmentsEntryRelService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsEntryRelServiceUtil} to access the segments entry rel remote service. Add custom service methods to {@link com.liferay.segments.service.impl.SegmentsEntryRelServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public SegmentsEntryRel addSegmentsEntryRel(long segmentsEntryId,
		long classNameId, long classPK, ServiceContext serviceContext)
		throws PortalException;

	public void deleteSegmentsEntryRel(long segmentsEntryRelId)
		throws PortalException;

	public void deleteSegmentsEntryRel(long segmentsEntryId, long classNameId,
		long classPK) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SegmentsEntryRel> getSegmentsEntryRels(long segmentsEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SegmentsEntryRel> getSegmentsEntryRels(long segmentsEntryId,
		int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SegmentsEntryRel> getSegmentsEntryRels(long groupId,
		long classNameId, long classPK) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSegmentsEntryRelsCount(long segmentsEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSegmentsEntryRelsCount(long groupId, long classNameId,
		long classPK) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasSegmentsEntryRel(long segmentsEntryId, long classNameId,
		long classPK);
}