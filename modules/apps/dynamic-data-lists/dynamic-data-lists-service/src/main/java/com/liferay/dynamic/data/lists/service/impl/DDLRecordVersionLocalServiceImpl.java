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
 * @author Marcellus Tavares
 */
public class DDLRecordVersionLocalServiceImpl
	extends DDLRecordVersionLocalServiceBaseImpl {

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

	@Override
	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException {

		return ddlRecordVersionPersistence.findByPrimaryKey(recordVersionId);
	}

	@Override
	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException {

		return ddlRecordVersionPersistence.findByR_V(recordId, version);
	}

	@Override
	public List<DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {

		return ddlRecordVersionPersistence.findByRecordId(
			recordId, start, end, orderByComparator);
	}

	@Override
	public int getRecordVersionsCount(long recordId) {
		return ddlRecordVersionPersistence.countByRecordId(recordId);
	}

}