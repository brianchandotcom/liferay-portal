/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceRecordVersionException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Objects;

/**
 * @author Joao Victor Alves
 */
public class DDMFormInstanceRecordStagedModelRepositoryUtil {

	public static DDMFormInstanceRecord addStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstanceRecord ddmFormInstanceRecord,
			DDMFormValues ddmFormValues)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			ddmFormInstanceRecord.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddmFormInstanceRecord);

		serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(ddmFormInstanceRecord.getUuid());
		}

		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService =
			_ddmFormInstanceRecordLocalServiceSnapshot.get();

		DDMFormInstanceRecord importedDDMFormInstanceRecord =
			ddmFormInstanceRecordLocalService.addFormInstanceRecord(
				userId, ddmFormInstanceRecord.getGroupId(),
				ddmFormInstanceRecord.getFormInstanceId(), ddmFormValues,
				serviceContext);

		_updateVersions(
			importedDDMFormInstanceRecord, ddmFormInstanceRecord.getVersion());

		return importedDDMFormInstanceRecord;
	}

	public static DDMFormInstanceRecord updateStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstanceRecord ddmFormInstanceRecord,
			DDMFormValues ddmFormValues)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			ddmFormInstanceRecord.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddmFormInstanceRecord);

		serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);

		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService =
			_ddmFormInstanceRecordLocalServiceSnapshot.get();

		DDMFormInstanceRecord importedDDMFormInstanceRecord =
			ddmFormInstanceRecordLocalService.updateFormInstanceRecord(
				userId, ddmFormInstanceRecord.getFormInstanceRecordId(), false,
				ddmFormValues, serviceContext);

		_updateVersions(
			importedDDMFormInstanceRecord, ddmFormInstanceRecord.getVersion());

		return importedDDMFormInstanceRecord;
	}

	private static void _updateVersions(
			DDMFormInstanceRecord importedDDMFormInstanceRecord, String version)
		throws PortalException {

		if (Objects.equals(
				importedDDMFormInstanceRecord.getVersion(), version)) {

			return;
		}

		DDMFormInstanceRecordVersionLocalService
			ddmFormInstanceRecordVersionLocalService =
				_ddmFormInstanceRecordVersionLocalServiceSnapshot.get();

		try {
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				ddmFormInstanceRecordVersionLocalService.
					getFormInstanceRecordVersion(
						importedDDMFormInstanceRecord.getFormInstanceRecordId(),
						version);

			ddmFormInstanceRecordVersionLocalService.
				deleteDDMFormInstanceRecordVersion(
					ddmFormInstanceRecordVersion);
		}
		catch (NoSuchFormInstanceRecordVersionException
					noSuchFormInstanceRecordVersionException) {

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFormInstanceRecordVersionException);
			}
		}

		DDMFormInstanceRecordVersion importedDDMFormInstanceRecordVersion =
			importedDDMFormInstanceRecord.getFormInstanceRecordVersion();

		importedDDMFormInstanceRecordVersion.setVersion(version);

		ddmFormInstanceRecordVersionLocalService.
			updateDDMFormInstanceRecordVersion(
				importedDDMFormInstanceRecordVersion);

		importedDDMFormInstanceRecord.setVersion(version);

		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService =
			_ddmFormInstanceRecordLocalServiceSnapshot.get();

		ddmFormInstanceRecordLocalService.updateDDMFormInstanceRecord(
			importedDDMFormInstanceRecord);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordStagedModelRepositoryUtil.class);

	private static final Snapshot<DDMFormInstanceRecordLocalService>
		_ddmFormInstanceRecordLocalServiceSnapshot = new Snapshot<>(
			DDMFormInstanceRecordStagedModelRepositoryUtil.class,
			DDMFormInstanceRecordLocalService.class);
	private static final Snapshot<DDMFormInstanceRecordVersionLocalService>
		_ddmFormInstanceRecordVersionLocalServiceSnapshot = new Snapshot<>(
			DDMFormInstanceRecordStagedModelRepositoryUtil.class,
			DDMFormInstanceRecordVersionLocalService.class);

}