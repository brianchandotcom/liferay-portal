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
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceVersionLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.util.comparator.FormInstanceVersionVersionComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceVersionLocalServiceImpl
	extends DDMFormInstanceVersionLocalServiceBaseImpl {

	@Override
	public DDMFormInstanceVersion addFormInstanceVersion(
			long ddmStructureVersionId, long userId,
			DDMFormInstance ddmFormInstance, String version,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long ddmFormInstanceVersionId = counterLocalService.increment();

		DDMFormInstanceVersion ddmFormInstanceVersion =
			ddmFormInstanceVersionPersistence.create(ddmFormInstanceVersionId);

		ddmFormInstanceVersion.setUuid(serviceContext.getUuid());
		ddmFormInstanceVersion.setGroupId(ddmFormInstance.getGroupId());
		ddmFormInstanceVersion.setCompanyId(ddmFormInstance.getCompanyId());
		ddmFormInstanceVersion.setUserId(ddmFormInstance.getUserId());
		ddmFormInstanceVersion.setUserName(ddmFormInstance.getUserName());
		ddmFormInstanceVersion.setCreateDate(ddmFormInstance.getModifiedDate());
		ddmFormInstanceVersion.setModifiedDate(
			ddmFormInstance.getModifiedDate());
		ddmFormInstanceVersion.setFormInstanceId(
			ddmFormInstance.getFormInstanceId());
		ddmFormInstanceVersion.setStructureVersionId(ddmStructureVersionId);
		ddmFormInstanceVersion.setVersion(version);
		ddmFormInstanceVersion.setName(ddmFormInstance.getName());
		ddmFormInstanceVersion.setDescription(ddmFormInstance.getDescription());

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_APPROVED);

		ddmFormInstanceVersion.setStatus(status);

		ddmFormInstanceVersion.setStatusByUserId(user.getUserId());
		ddmFormInstanceVersion.setStatusByUserName(user.getFullName());
		ddmFormInstanceVersion.setStatusDate(ddmFormInstance.getModifiedDate());
		ddmFormInstanceVersionPersistence.update(ddmFormInstanceVersion);

		return ddmFormInstanceVersion;
	}

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
	public void updateFormInstanceVersion(
			long ddmStructureVersionId, long userId,
			DDMFormInstance ddmFormInstance, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		DDMFormInstanceVersion ddmFormInstanceVersion =
			ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
				ddmFormInstance.getFormInstanceId());

		ddmFormInstanceVersion.setUserId(ddmFormInstance.getUserId());
		ddmFormInstanceVersion.setUserName(ddmFormInstance.getUserName());
		ddmFormInstanceVersion.setStructureVersionId(ddmStructureVersionId);
		ddmFormInstanceVersion.setName(ddmFormInstance.getName());
		ddmFormInstanceVersion.setDescription(ddmFormInstance.getDescription());

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_APPROVED);

		ddmFormInstanceVersion.setStatus(status);

		ddmFormInstanceVersion.setStatusByUserId(user.getUserId());
		ddmFormInstanceVersion.setStatusByUserName(user.getFullName());
		ddmFormInstanceVersion.setStatusDate(ddmFormInstance.getModifiedDate());

		ddmFormInstanceVersionPersistence.update(ddmFormInstanceVersion);
	}

}