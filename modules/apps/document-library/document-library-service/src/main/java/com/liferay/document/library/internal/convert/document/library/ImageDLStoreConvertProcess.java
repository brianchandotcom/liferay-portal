/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.convert.document.library;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLStoreConvertProcess.class)
public class ImageDLStoreConvertProcess implements DLStoreConvertProcess {

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
			_imageLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq("companyId", companyId)));

		actionableDynamicQuery.setPerformActionMethod(
			(Image image) -> {
				String fileName =
					image.getImageId() + StringPool.PERIOD + image.getType();

				try {
					transferFile(
						sourceStore, targetStore, image.getCompanyId(),
						_REPOSITORY_ID, fileName, Store.VERSION_DEFAULT,
						delete);
				}
				catch (Exception exception) {
					_log.error("Unable to migrate " + fileName, exception);
				}
			});

		return actionableDynamicQuery;
	}

	private long _getCount(long companyId) {
		DynamicQuery dynamicQuery = _imageLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));

		return _imageLocalService.dynamicQueryCount(dynamicQuery);
	}

	private void _transfer(Store sourceStore, Store targetStore, boolean delete)
		throws PortalException {

		_companyLocalService.forEachCompanyId(
			companyId -> {
				MaintenanceUtil.appendStatus(
					String.format(
						"Migrating %d images for company %d",
						_getCount(companyId), companyId));

				ActionableDynamicQuery actionableDynamicQuery =
					_getActionableDynamicQuery(
						companyId, sourceStore, targetStore, delete);

				actionableDynamicQuery.performActions();
			});
	}

	private static final long _REPOSITORY_ID = 0L;

	private static final Log _log = LogFactoryUtil.getLog(
		ImageDLStoreConvertProcess.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

}