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

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.BaseStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "service.ranking:Integer=100",
	service = {DefaultStorageAdapter.class, StorageAdapter.class}
)
public class DefaultStorageAdapter extends BaseStorageAdapter {

	@Override
	public String getStorageType() {
		return StorageType.DEFAULT.toString();
	}

	@Override
	protected long doCreate(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(ddmFormValues, serviceContext);

		long primaryKey = _counterLocalService.increment();

		_ddmFieldLocalService.updateDDMFormValues(
			ddmStructureId, primaryKey, ddmFormValues);

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				ddmStructureId);

		_ddmStorageLinkLocalService.addStorageLink(
			_classNameLocalService.getClassNameId(DDMContent.class), primaryKey,
			ddmStructureVersion.getStructureVersionId(), serviceContext);

		return primaryKey;
	}

	@Override
	protected void doDeleteByClass(long classPK) {
		_ddmFieldLocalService.deleteDDMFormValues(classPK);

		_ddmStorageLinkLocalService.deleteClassStorageLink(classPK);
	}

	@Override
	protected void doDeleteByDDMStructure(long ddmStructureId) {
		_ddmFieldLocalService.deleteDDMFields(ddmStructureId);

		_ddmStorageLinkLocalService.deleteStructureStorageLinks(ddmStructureId);
	}

	@Override
	protected DDMFormValues doGetDDMFormValues(long classPK)
		throws PortalException {

		DDMStructure ddmStructure = _getDDMStructure(classPK);

		return _ddmFieldLocalService.getDDMFormValues(
			ddmStructure.createFullHierarchyDDMForm(), classPK);
	}

	@Override
	protected void doUpdate(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(ddmFormValues, serviceContext);

		DDMStructure ddmStructure = _getDDMStructure(classPK);

		_ddmFieldLocalService.updateDDMFormValues(
			ddmStructure.getStructureId(), classPK, ddmFormValues);
	}

	private DDMStructure _getDDMStructure(long storageId)
		throws PortalException {

		DDMStorageLink ddmStorageLink =
			_ddmStorageLinkLocalService.getClassStorageLink(storageId);

		return _ddmStructureLocalService.getDDMStructure(
			ddmStorageLink.getStructureId());
	}

	private void _validate(
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		if (GetterUtil.getBoolean(
				serviceContext.getAttribute("validateDDMFormValues"), true)) {

			_ddmFormValuesValidator.validate(ddmFormValues);
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DDMFieldLocalService _ddmFieldLocalService;

	@Reference
	private DDMFormValuesValidator _ddmFormValuesValidator;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

}