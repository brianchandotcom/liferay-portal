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
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceRecordVersionLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterRegistry;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.comparator.FormInstanceRecordVersionVersionComparator;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMFormInstanceRecordVersion deleteDDMFormInstanceRecordVersion(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion)
		throws PortalException {

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecordVersion.getFormInstance();

		_deleteStorage(
			ddmFormInstanceRecordVersion.getStorageId(),
			ddmFormInstance.getStorageType());

		_ddmStorageLinkLocalService.deleteClassStorageLink(
			ddmFormInstanceRecordVersion.getStorageId());

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			ddmFormInstanceRecordVersion.getCompanyId(),
			ddmFormInstanceRecordVersion.getGroupId(),
			DDMFormInstanceRecord.class.getName(),
			ddmFormInstanceRecordVersion.getPrimaryKey());

		return super.deleteDDMFormInstanceRecordVersion(
			ddmFormInstanceRecordVersion);
	}

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
	public List<DDMFormInstanceRecordVersion> getFormInstanceRecordVersions(
		long userId, long formInstanceId) {

		return ddmFormInstanceRecordVersionPersistence.findByU_F(
			userId, formInstanceId);
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

	@Override
	public DDMFormInstanceRecordVersion getLatestFormInstanceRecordVersion(
			long ddmFormInstanceRecordId, int status)
		throws PortalException {

		List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions =
			ddmFormInstanceRecordVersionPersistence.findByF_S(
				ddmFormInstanceRecordId, status);

		if (ddmFormInstanceRecordVersions.isEmpty()) {
			throw new NoSuchFormInstanceRecordVersionException(
				"No dynamic data mapping form instance record versions found " +
					"for dynamic data mapping form instance ID " +
						ddmFormInstanceRecordId);
		}

		ddmFormInstanceRecordVersions = ListUtil.copy(
			ddmFormInstanceRecordVersions);

		Collections.sort(
			ddmFormInstanceRecordVersions,
			new FormInstanceRecordVersionVersionComparator());

		return ddmFormInstanceRecordVersions.get(0);
	}

	private void _deleteStorage(long storageId, String storageType)
		throws StorageException {

		if (StringUtil.equals(storageType, "object")) {
			return;
		}

		DDMStorageAdapter ddmStorageAdapter =
			_ddmStorageAdapterRegistry.getDDMStorageAdapter(
				GetterUtil.getString(
					storageType, StorageType.DEFAULT.toString()));

		ddmStorageAdapter.delete(
			DDMStorageAdapterDeleteRequest.Builder.newBuilder(
				storageId
			).build());
	}

	@Reference
	private DDMStorageAdapterRegistry _ddmStorageAdapterRegistry;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}