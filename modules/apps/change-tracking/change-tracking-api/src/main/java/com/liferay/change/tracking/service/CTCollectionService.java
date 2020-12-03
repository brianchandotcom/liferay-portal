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

package com.liferay.change.tracking.service;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CTCollection. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CTCollectionService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTCollectionServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the ct collection remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CTCollectionServiceUtil} if injection and service tracking are not available.
	 */
	public CTCollection addCTCollection(
			long companyId, long userId, String name, String description)
		throws PortalException;

	public void deleteCTAutoResolutionInfo(long ctAutoResolutionInfoId)
		throws PortalException;

	public CTCollection deleteCTCollection(CTCollection ctCollection)
		throws PortalException;

	public void discardCTEntries(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws PortalException;

	public void discardCTEntry(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTCollections(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<CTCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTCollection> getCTCollections(
		long companyId, int[] statuses, String keywords, int start, int end,
		OrderByComparator<CTCollection> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTCollectionsCount(
		long companyId, int[] statuses, String keywords);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public void publishCTCollection(long userId, long ctCollectionId)
		throws PortalException;

	public CTCollection undoCTCollection(
			long ctCollectionId, long userId, String name, String description)
		throws PortalException;

	public CTCollection updateCTCollection(
			long userId, long ctCollectionId, String name, String description)
		throws PortalException;

}