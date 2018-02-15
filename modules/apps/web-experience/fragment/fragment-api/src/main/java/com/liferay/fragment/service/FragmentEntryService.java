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

package com.liferay.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.model.FragmentEntry;

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
 * Provides the remote service interface for FragmentEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryServiceUtil
 * @see com.liferay.fragment.service.base.FragmentEntryServiceBaseImpl
 * @see com.liferay.fragment.service.impl.FragmentEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=fragment", "json.web.service.context.path=FragmentEntry"}, service = FragmentEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface FragmentEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentEntryServiceUtil} to access the fragment entry remote service. Add custom service methods to {@link com.liferay.fragment.service.impl.FragmentEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public FragmentEntry addFragmentEntry(long groupId,
		long fragmentCollectionId, java.lang.String name, int status,
		ServiceContext serviceContext) throws PortalException;

	public FragmentEntry addFragmentEntry(long groupId,
		long fragmentCollectionId, java.lang.String fragmentEntryKey,
		java.lang.String name, int status, ServiceContext serviceContext)
		throws PortalException;

	public FragmentEntry addFragmentEntry(long groupId,
		long fragmentCollectionId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js, int status,
		ServiceContext serviceContext) throws PortalException;

	public FragmentEntry addFragmentEntry(long groupId,
		long fragmentCollectionId, java.lang.String fragmentEntryKey,
		java.lang.String name, java.lang.String css, java.lang.String html,
		java.lang.String js, int status, ServiceContext serviceContext)
		throws PortalException;

	public void deleteFragmentEntries(long[] fragmentEntriesIds)
		throws PortalException;

	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentCollectionsCount(long groupId,
		long fragmentCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentCollectionsCount(long groupId,
		long fragmentCollectionId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long groupId,
		long fragmentCollectionId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long groupId,
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTempFileNames(long groupId,
		java.lang.String folderName) throws PortalException;

	public FragmentEntry updateFragmentEntry(long fragmentEntryId,
		java.lang.String name) throws PortalException;

	public FragmentEntry updateFragmentEntry(long fragmentEntryId,
		java.lang.String name, java.lang.String css, java.lang.String html,
		java.lang.String js, int status, ServiceContext serviceContext)
		throws PortalException;
}