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

package com.liferay.dynamic.data.lists.service.impl;

import com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.base.DDLRecordVersionLocalServiceBaseImpl;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordVersionVersionComparator;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the local service for accessing dynamic data list (DDL) record
 * versions.
 *
 * @author Marcellus Tavares
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordVersion",
	service = AopService.class
)
public class DDLRecordVersionLocalServiceImpl
	extends DDLRecordVersionLocalServiceBaseImpl {

	/**
	 * Returns the latest record version matching the user, the record set, the
	 * record set version and workflow status.
	 *
	 * @param  userId the primary key of the user
	 * @param  recordSetId the primary key of the record set
	 * @param  recordSetVersion the version of the record set
	 * @param  status the workflow status
	 * @return the latest matching record version or <code>null</code>
	 */
	@Override
	public DDLRecordVersion fetchLatestRecordVersion(
		long userId, long recordSetId, String recordSetVersion, int status) {

		return ddlRecordVersionPersistence.fetchByU_R_R_S_Last(
			userId, recordSetId, recordSetVersion, status, null);
	}

	/**
	 * Returns the record's latest record version.
	 *
	 * @param  recordId the primary key of the record
	 * @return the latest record version for the given record
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public DDLRecordVersion getLatestRecordVersion(long recordId)
		throws PortalException {

		List<DDLRecordVersion> recordVersions =
			ddlRecordVersionPersistence.findByRecordId(recordId);

		if (recordVersions.isEmpty()) {
			throw new NoSuchRecordVersionException(
				"No record versions found for record ID " + recordId);
		}

		recordVersions = ListUtil.copy(recordVersions);

		Collections.sort(
			recordVersions, new DDLRecordVersionVersionComparator());

		return recordVersions.get(0);
	}

	/**
	 * Returns the record version by its ID.
	 *
	 * @param  recordVersionId the primary key of the record version
	 * @return the record version with the ID
	 * @throws PortalException if a matching record set could not be found
	 */
	@Override
	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException {

		return ddlRecordVersionPersistence.findByPrimaryKey(recordVersionId);
	}

	/**
	 * Returns the record version matching the record and version.
	 *
	 * @param  recordId the primary key of the record
	 * @param  version the record version
	 * @return the record version matching the record primary key and version
	 * @throws PortalException if a matching record set could not be found
	 */
	@Override
	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException {

		return ddlRecordVersionPersistence.findByR_V(recordId, version);
	}

	/**
	 * Returns an ordered range of record versions matching the record's ID.
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
	 * @param  recordId the primary key of the record
	 * @param  start the lower bound of the range of record versions to return
	 * @param  end the upper bound of the range of record versions to return
	 *         (not inclusive)
	 * @param  orderByComparator the comparator used to order the record
	 *         versions
	 * @return the range of matching record versions ordered by the comparator
	 */
	@Override
	public List<DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {

		return ddlRecordVersionPersistence.findByRecordId(
			recordId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of record versions matching the record ID.
	 *
	 * @param  recordId the primary key of the record
	 * @return the number of matching record versions
	 */
	@Override
	public int getRecordVersionsCount(long recordId) {
		return ddlRecordVersionPersistence.countByRecordId(recordId);
	}

}