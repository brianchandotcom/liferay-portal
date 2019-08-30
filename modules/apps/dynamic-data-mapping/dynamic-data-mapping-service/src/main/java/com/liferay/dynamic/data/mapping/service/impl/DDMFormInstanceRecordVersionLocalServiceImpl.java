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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceRecordVersionException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceRecordVersionLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.util.comparator.FormInstanceRecordVersionVersionComparator;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @see    DDMFormInstanceRecordVersionLocalServiceBaseImpl
 * @see    com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalServiceUtil
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion",
	service = AopService.class
)
public class DDMFormInstanceRecordVersionLocalServiceImpl
	extends DDMFormInstanceRecordVersionLocalServiceBaseImpl {

	@Override
	public DDMFormInstanceRecordVersion fetchLatestFormInstanceRecordVersion(
		long userId, long formInstanceId, String formInstanceVersion,
		int status) {

		return ddmFormInstanceRecordVersionPersistence.fetchByU_F_F_S_Last(
			userId, formInstanceId, formInstanceVersion, status, null);
	}

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
			long ddmFormInstanceRecordVersionId)
		throws PortalException {

		return ddmFormInstanceRecordVersionPersistence.findByPrimaryKey(
			ddmFormInstanceRecordVersionId);
	}

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
			long ddmFormInstanceId, String version)
		throws PortalException {

		return ddmFormInstanceRecordVersionPersistence.findByF_V(
			ddmFormInstanceId, version);
	}

	@Override
	public List<DDMFormInstanceRecordVersion> getFormInstanceRecordVersions(
		long ddmFormInstanceRecordId, int start, int end,
		OrderByComparator<DDMFormInstanceRecordVersion> orderByComparator) {

		return ddmFormInstanceRecordVersionPersistence.
			findByFormInstanceRecordId(
				ddmFormInstanceRecordId, start, end, orderByComparator);
	}

	@Override
	public int getFormInstanceRecordVersionsCount(
		long ddmFormInstanceRecordId) {

		return ddmFormInstanceRecordVersionPersistence.
			countByFormInstanceRecordId(ddmFormInstanceRecordId);
	}

	@Override
	public DDMFormInstanceRecordVersion getLatestFormInstanceRecordVersion(
			long ddmFormInstanceId)
		throws PortalException {

		List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions =
			ddmFormInstanceRecordVersionPersistence.findByFormInstanceRecordId(
				ddmFormInstanceId);

		if (ddmFormInstanceRecordVersions.isEmpty()) {
			throw new NoSuchFormInstanceRecordVersionException(
				"No form instance record versions found for form instance ID " +
					ddmFormInstanceId);
		}

		ddmFormInstanceRecordVersions = ListUtil.copy(
			ddmFormInstanceRecordVersions);

		Collections.sort(
			ddmFormInstanceRecordVersions,
			new FormInstanceRecordVersionVersionComparator());

		return ddmFormInstanceRecordVersions.get(0);
	}

}