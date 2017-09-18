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

package com.liferay.modern.site.building.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.modern.site.building.fragment.model.MSBFragmentCollection;

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
 * Provides the remote service interface for MSBFragmentCollection. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentCollectionServiceUtil
 * @see com.liferay.modern.site.building.fragment.service.base.MSBFragmentCollectionServiceBaseImpl
 * @see com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=msb", "json.web.service.context.path=MSBFragmentCollection"}, service = MSBFragmentCollectionService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MSBFragmentCollectionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MSBFragmentCollectionServiceUtil} to access the msb fragment collection remote service. Add custom service methods to {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentCollectionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public MSBFragmentCollection addMSBFragmentCollection(long groupId,
		java.lang.String name, java.lang.String description,
		ServiceContext serviceContext) throws PortalException;

	public MSBFragmentCollection deleteMSBFragmentCollection(
		long msbFragmentCollectionId) throws PortalException;

	public List<MSBFragmentCollection> deleteMSBFragmentCollections(
		long[] msbFragmentCollectionIds) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MSBFragmentCollection fetchMSBFragmentCollection(
		long msbFragmentCollectionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MSBFragmentCollection> getMSBFragmentCollections(long groupId,
		int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MSBFragmentCollection> getMSBFragmentCollections(long groupId,
		int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MSBFragmentCollection> getMSBFragmentCollections(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<MSBFragmentCollection> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMSBFragmentCollectionsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMSBFragmentCollectionsCount(long groupId,
		java.lang.String name);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public MSBFragmentCollection updateMSBFragmentCollection(
		long msbFragmentCollectionId, java.lang.String name,
		java.lang.String description) throws PortalException;
}