/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.convert.document.library;

import com.liferay.adaptive.media.image.internal.storage.AMStoreUtil;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DLStoreConvertProcess.class)
public class AMDLStoreConvertProcess implements DLStoreConvertProcess {

	@Override
	public void copy(Store sourceStore, Store targetStore)
		throws PortalException {

		_transfer(sourceStore, targetStore, false);
	}

	@Override
	public void move(Store sourceStore, Store targetStore)
		throws PortalException {

		_transfer(sourceStore, targetStore, true);
	}

	private ActionableDynamicQuery _getActionableDynamicQuery(
		long companyId, Store sourceStore, Store targetStore, boolean delete) {

		ActionableDynamicQuery actionableDynamicQuery =
			_amImageEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq("companyId", companyId)));

		actionableDynamicQuery.setPerformActionMethod(
			(AMImageEntry amImageEntry) -> {
				FileVersion fileVersion = null;

				try {
					fileVersion = _dlAppLocalService.getFileVersion(
						amImageEntry.getFileVersionId());
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to migrate file version id: " +
							amImageEntry.getFileVersionId(),
						portalException);
				}

				if (fileVersion == null) {
					return;
				}

				String fileVersionPath = AMStoreUtil.getFileVersionPath(
					fileVersion, amImageEntry.getConfigurationUuid());

				for (String versionLabel :
						sourceStore.getFileVersions(
							amImageEntry.getCompanyId(),
							CompanyConstants.SYSTEM, fileVersionPath)) {

					try {
						transferFile(
							sourceStore, targetStore,
							amImageEntry.getCompanyId(),
							CompanyConstants.SYSTEM, fileVersionPath,
							versionLabel, delete);
					}
					catch (Exception exception) {
						_log.error(
							"Unable to migrate " + fileVersionPath, exception);
					}
				}
			});

		return actionableDynamicQuery;
	}

	private long _getCount(long companyId) {
		DynamicQuery dynamicQuery = _amImageEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));

		return _amImageEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	private void _transfer(Store sourceStore, Store targetStore, boolean delete)
		throws PortalException {

		_companyLocalService.forEachCompanyId(
			companyId -> {
				MaintenanceUtil.appendStatus(
					String.format(
						"Migrating images in %d adaptive media image entries " +
							"for company %d",
						_getCount(companyId), companyId));

				ActionableDynamicQuery actionableDynamicQuery =
					_getActionableDynamicQuery(
						companyId, sourceStore, targetStore, delete);

				actionableDynamicQuery.performActions();
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMDLStoreConvertProcess.class);

	@Reference
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}