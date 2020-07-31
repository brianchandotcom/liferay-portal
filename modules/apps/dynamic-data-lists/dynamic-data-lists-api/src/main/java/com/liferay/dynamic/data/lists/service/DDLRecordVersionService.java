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

package com.liferay.dynamic.data.lists.service;

import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
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
 * Provides the remote service interface for DDLRecordVersion. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface DDLRecordVersionService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.dynamic.data.lists.service.impl.DDLRecordVersionServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the ddl record version remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link DDLRecordVersionServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Returns the record version matching the ID.
	 *
	 * @param recordVersionId the primary key of the record version
	 * @return the record version with the ID
	 * @throws PortalException if the matching record set could not be found or
	 if the user did not have the required permission to access the
	 record set
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException;

	/**
	 * Returns a record version matching the record and version.
	 *
	 * @param recordId the primary key of the record
	 * @param version the version of the record to return
	 * @return the record version macthing the record primary key and version
	 * @throws PortalException if the matching record set is not found or if the
	 user do not have the required permission to access the record set
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException;

	/**
	 * Returns all the record versions matching the record.
	 *
	 * @param recordId the primary key of the record
	 * @return the matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordVersion> getRecordVersions(long recordId)
		throws PortalException;

	/**
	 * Returns an ordered range of record versions matching the record.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	 * full result set.
	 * </p>
	 *
	 * @param recordId the primary key of the record
	 * @param start the lower bound of the range of record versions to return
	 * @param end the upper bound of the range of record versions to return
	 (not inclusive)
	 * @param orderByComparator the comparator used to order the record
	 versions
	 * @return the range of matching record versions ordered by the comparator
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordVersion> getRecordVersions(
			long recordId, int start, int end,
			OrderByComparator<DDLRecordVersion> orderByComparator)
		throws PortalException;

	/**
	 * Returns the number of record versions matching the record.
	 *
	 * @param recordId the primary key of the record
	 * @return the number of matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecordVersionsCount(long recordId) throws PortalException;

}