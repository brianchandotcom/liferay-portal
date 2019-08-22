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

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceVersionException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceVersionLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.util.comparator.FormInstanceVersionVersionComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion",
	service = AopService.class
)
public class DDMFormInstanceVersionLocalServiceImpl
	extends DDMFormInstanceVersionLocalServiceBaseImpl {

	@Override
	public void deleteByFormInstanceId(long ddmFormInstanceId) {
		ddmFormInstanceVersionPersistence.removeByFormInstanceId(
			ddmFormInstanceId);
	}

	@Override
	public DDMFormInstanceVersion getFormInstanceVersion(
			long ddmFormInstanceVersionId)
		throws PortalException {

		return ddmFormInstanceVersionPersistence.findByPrimaryKey(
			ddmFormInstanceVersionId);
	}

	@Override
	public DDMFormInstanceVersion getFormInstanceVersion(
			long ddmFormInstanceId, String version)
		throws PortalException {

		return ddmFormInstanceVersionPersistence.findByF_V(
			ddmFormInstanceId, version);
	}

	@Override
	public List<DDMFormInstanceVersion> getFormInstanceVersions(
		long ddmFormInstanceId) {

		return ddmFormInstanceVersionPersistence.findByFormInstanceId(
			ddmFormInstanceId);
	}

	@Override
	public List<DDMFormInstanceVersion> getFormInstanceVersions(
		long ddmFormInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return ddmFormInstanceVersionPersistence.findByFormInstanceId(
			ddmFormInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getFormInstanceVersionsCount(long ddmFormInstanceId) {
		return ddmFormInstanceVersionPersistence.countByFormInstanceId(
			ddmFormInstanceId);
	}

	@Override
	public DDMFormInstanceVersion getLatestFormInstanceVersion(
			long ddmFormInstanceId)
		throws PortalException {

		List<DDMFormInstanceVersion> ddmFormInstanceVersions =
			ddmFormInstanceVersionPersistence.findByFormInstanceId(
				ddmFormInstanceId);

		if (ddmFormInstanceVersions.isEmpty()) {
			throw new NoSuchFormInstanceVersionException(
				"No form instance versions found for form instance ID " +
					ddmFormInstanceId);
		}

		ddmFormInstanceVersions = ListUtil.copy(ddmFormInstanceVersions);

		Collections.sort(
			ddmFormInstanceVersions,
			new FormInstanceVersionVersionComparator());

		return ddmFormInstanceVersions.get(0);
	}

	@Override
	public DDMFormInstanceVersion getLatestFormInstanceVersion(
			long formInstanceId, int status)
		throws PortalException {

		List<DDMFormInstanceVersion> formInstanceVersions =
			ddmFormInstanceVersionPersistence.findByF_S(formInstanceId, status);

		if (formInstanceVersions.isEmpty()) {
			throw new NoSuchFormInstanceVersionException(
				StringBundler.concat(
					"No form instance versions found for form instance ID ",
					formInstanceId, " with status ", status));
		}

		formInstanceVersions = ListUtil.copy(formInstanceVersions);

		Collections.sort(
			formInstanceVersions, new FormInstanceVersionVersionComparator());

		return formInstanceVersions.get(0);
	}

}