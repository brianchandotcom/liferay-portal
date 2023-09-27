/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterRegistry;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = DDMStorageEngineManager.class)
public class DDMStorageEngineManagerImpl implements DDMStorageEngineManager {

	@Override
	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(ddmFormValues, serviceContext);

		DDMStorageAdapter ddmStorageAdapter = _getDDMStorageAdapter();

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			ddmStorageAdapter.save(
				DDMStorageAdapterSaveRequest.Builder.newBuilder(
					serviceContext.getUserId(), ddmFormValues
				).withStructureId(
					ddmStructureId
				).withUuid(
					serviceContext.getUuid()
				).withClassName(
					DDMStorageLink.class.getName()
				).build());

		long primaryKey = ddmStorageAdapterSaveResponse.getPrimaryKey();

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				ddmStructureId);

		_ddmStorageLinkLocalService.addStorageLink(
			_portal.getClassNameId(DDMContent.class.getName()), primaryKey,
			ddmStructureVersion.getStructureVersionId(), serviceContext);

		return primaryKey;
	}

	@Override
	public void deleteByClass(long classPK) throws PortalException {
		_deleteStorage(classPK);

		_ddmStorageLinkLocalService.deleteClassStorageLink(classPK);
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK) throws PortalException {
		DDMStorageLink ddmStorageLink =
			_ddmStorageLinkLocalService.getClassStorageLink(classPK);

		DDMStructure ddmStructure = ddmStorageLink.getStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMStorageAdapter ddmStorageAdapter = _getDDMStorageAdapter();

		DDMStorageAdapterGetResponse ddmStorageAdapterGetResponse =
			ddmStorageAdapter.get(
				DDMStorageAdapterGetRequest.Builder.newBuilder(
					classPK, ddmForm
				).build());

		DDMFormValues ddmFormValues =
			ddmStorageAdapterGetResponse.getDDMFormValues();

		ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());

		return ddmFormValues;
	}

	@Override
	public DDMFormValues getDDMFormValues(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		return _ddm.getDDMFormValues(
			ddmStructureId, fieldNamespace, serviceContext);
	}

	@Override
	public void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(ddmFormValues, serviceContext);

		DDMStorageLink ddmStorageLink =
			_ddmStorageLinkLocalService.getClassStorageLink(classPK);

		DDMStorageAdapter ddmStorageAdapter = _getDDMStorageAdapter();

		ddmStorageAdapter.save(
			DDMStorageAdapterSaveRequest.Builder.newBuilder(
				serviceContext.getUserId(), ddmFormValues
			).withStructureId(
				ddmStorageLink.getStructureId()
			).withPrimaryKey(
				classPK
			).build());
	}

	private void _deleteStorage(long storageId) throws StorageException {
		DDMStorageAdapter ddmStorageAdapter = _getDDMStorageAdapter();

		ddmStorageAdapter.delete(
			DDMStorageAdapterDeleteRequest.Builder.newBuilder(
				storageId
			).build());
	}

	private DDMStorageAdapter _getDDMStorageAdapter() {
		return _ddmStorageAdapterRegistry.getDDMStorageAdapter(
			StorageType.DEFAULT.toString());
	}

	private void _validate(
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		boolean validateDDMFormValues = GetterUtil.getBoolean(
			serviceContext.getAttribute("validateDDMFormValues"), true);

		if (!validateDDMFormValues) {
			return;
		}

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesValidator _ddmFormValuesValidator;

	@Reference
	private DDMStorageAdapterRegistry _ddmStorageAdapterRegistry;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private Portal _portal;

}