/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Objects;

/**
 * @author Joao Victor Alves
 */
public class DDLRecordStagedModelRepositoryUtil {

	public static DDLRecord addStagedModel(
			PortletDataContext portletDataContext, DDLRecord ddlRecord,
			DDMFormValues ddmFormValues)
		throws PortalException {

		long userId = portletDataContext.getUserId(ddlRecord.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddlRecord);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(ddlRecord.getUuid());
		}

		DDLRecordLocalService ddlRecordLocalService =
			_ddlRecordLocalServiceSnapshot.get();

		DDLRecord importedRecord = ddlRecordLocalService.addRecord(
			userId, ddlRecord.getGroupId(), ddlRecord.getRecordSetId(),
			ddlRecord.getDisplayIndex(), ddmFormValues, serviceContext);

		_updateVersions(importedRecord, ddlRecord.getVersion());

		return importedRecord;
	}

	public static DDLRecord updateStagedModel(
			PortletDataContext portletDataContext, DDLRecord ddlRecord,
			DDMFormValues ddmFormValues)
		throws PortalException {

		long userId = portletDataContext.getUserId(ddlRecord.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddlRecord);

		DDLRecordLocalService ddlRecordLocalService =
			_ddlRecordLocalServiceSnapshot.get();

		DDLRecord importedRecord = ddlRecordLocalService.updateRecord(
			userId, ddlRecord.getRecordId(), false, ddlRecord.getDisplayIndex(),
			ddmFormValues, serviceContext);

		_updateVersions(importedRecord, ddlRecord.getVersion());

		return importedRecord;
	}

	private static void _updateVersions(
			DDLRecord importedRecord, String version)
		throws PortalException {

		if (Objects.equals(importedRecord.getVersion(), version)) {
			return;
		}

		DDLRecordVersion importedRecordVersion =
			importedRecord.getRecordVersion();

		importedRecordVersion.setVersion(version);

		DDLRecordVersionLocalService ddlRecordVersionLocalService =
			_ddlRecordVersionLocalServiceSnapshot.get();

		ddlRecordVersionLocalService.updateDDLRecordVersion(
			importedRecordVersion);

		importedRecord.setVersion(version);

		DDLRecordLocalService ddlRecordLocalService =
			_ddlRecordLocalServiceSnapshot.get();

		ddlRecordLocalService.updateDDLRecord(importedRecord);
	}

	private static final Snapshot<DDLRecordLocalService>
		_ddlRecordLocalServiceSnapshot = new Snapshot<>(
			DDLRecordStagedModelRepositoryUtil.class,
			DDLRecordLocalService.class);
	private static final Snapshot<DDLRecordVersionLocalService>
		_ddlRecordVersionLocalServiceSnapshot = new Snapshot<>(
			DDLRecordStagedModelRepositoryUtil.class,
			DDLRecordVersionLocalService.class);

}