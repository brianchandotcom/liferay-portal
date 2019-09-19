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

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for FragmentCollection. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollectionServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface FragmentCollectionService extends BaseService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentCollectionServiceUtil} to access the fragment collection remote service. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentCollectionServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public FragmentCollection addFragmentCollection(
			long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

	public FragmentCollection addFragmentCollection(
			long groupId, String fragmentCollectionKey, String name,
			String description, ServiceContext serviceContext)
		throws PortalException;

	public FragmentCollection deleteFragmentCollection(
			long fragmentCollectionId)
		throws PortalException;

	public void deleteFragmentCollections(long[] fragmentCollectionIds)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentCollection fetchFragmentCollection(long fragmentCollectionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentCollection> getFragmentCollections(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentCollection> getFragmentCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentCollection> getFragmentCollections(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentCollection> getFragmentCollections(
		long[] groupIds, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentCollection> getFragmentCollections(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentCollectionsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentCollectionsCount(long groupId, String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentCollectionsCount(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentCollectionsCount(long[] groupIds, String name);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String[] getTempFileNames(long groupId, String folderName)
		throws PortalException;

	public FragmentCollection updateFragmentCollection(
			long fragmentCollectionId, String name, String description)
		throws PortalException;

}