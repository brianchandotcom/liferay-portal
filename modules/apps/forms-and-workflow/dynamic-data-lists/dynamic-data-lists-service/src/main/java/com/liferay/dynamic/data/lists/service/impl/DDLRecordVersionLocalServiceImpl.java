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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * dynamic data list (DDL) record versions.
 *
 * @author Marcellus Tavares
 */
public class DDLRecordVersionLocalServiceImpl
	extends DDLRecordVersionLocalServiceBaseImpl {

	/**
	 * Returns the latest record version for the given record
	 *
	 * @param  recordId the primary key of the record
	 * @return the latest record version for the given record
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
	 * Returns a record version with the ID.
	 *
	 * @param  recordVersionId the primary key of the record version
	 * @return the record version with the ID
	 * @throws PortalException if the mathing record set is not found
	 */
	@Override
	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException {

		return ddlRecordVersionPersistence.findByPrimaryKey(recordVersionId);
	}

	/**
	 * Returns a record version matching the record's primary key and the version.
	 *
	 * @param  recordId the primary key of the record
	 * @param  version the record version
	 * @return the record version macthing the record primary key and version
	 * @throws PortalException if the mathing record set is not found
	 */
	@Override
	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException {

		return ddlRecordVersionPersistence.findByR_V(recordId, version);
	}

	/**
	 * Returns a range of all record versions matching the record.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  recordId the primary key of the record
	 * @param  start the lower bound of the range of record versions to
	 *         return
	 * @param  end the upper bound of the range of record versions to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the record versions
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
	 * Returns the number of record versions matching the record.
	 *
	 * @param  recordId the primary key of the record
	 * @return the number of matching record versions
	 */
	@Override
	public int getRecordVersionsCount(long recordId) {
		return ddlRecordVersionPersistence.countByRecordId(recordId);
	}

}