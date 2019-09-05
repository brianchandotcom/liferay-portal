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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordVersionService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionService
 * @generated
 */
public class DDLRecordVersionServiceWrapper
	implements DDLRecordVersionService,
			   ServiceWrapper<DDLRecordVersionService> {

	public DDLRecordVersionServiceWrapper(
		DDLRecordVersionService ddlRecordVersionService) {

		_ddlRecordVersionService = ddlRecordVersionService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddlRecordVersionService.getOSGiServiceIdentifier();
	}

	/**
	 * Returns the record version matching the ID.
	 *
	 * @param recordVersionId the primary key of the record version
	 * @return the record version with the ID
	 * @throws PortalException if the matching record set could not be found or
	 if the user did not have the required permission to access the
	 record set
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			getRecordVersion(long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionService.getRecordVersion(recordVersionId);
	}

	/**
	 * Returns a record version matching the record and version.
	 *
	 * @param recordId the primary key of the record
	 * @param version the version of the record to return
	 * @return the record version macthing the record primary key and version
	 * @throws PortalException if the matching record set is not found or if the
	 user do not have the required permission to access the record set
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			getRecordVersion(long recordId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionService.getRecordVersion(recordId, version);
	}

	/**
	 * Returns all the record versions matching the record.
	 *
	 * @param recordId the primary key of the record
	 * @return the matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
			getRecordVersions(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionService.getRecordVersions(recordId);
	}

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
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
			getRecordVersions(
				long recordId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionService.getRecordVersions(
			recordId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of record versions matching the record.
	 *
	 * @param recordId the primary key of the record
	 * @return the number of matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public int getRecordVersionsCount(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordVersionService.getRecordVersionsCount(recordId);
	}

	@Override
	public DDLRecordVersionService getWrappedService() {
		return _ddlRecordVersionService;
	}

	@Override
	public void setWrappedService(
		DDLRecordVersionService ddlRecordVersionService) {

		_ddlRecordVersionService = ddlRecordVersionService;
	}

	private DDLRecordVersionService _ddlRecordVersionService;

}