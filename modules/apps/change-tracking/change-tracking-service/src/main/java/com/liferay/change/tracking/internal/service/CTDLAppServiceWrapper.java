/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.service;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceWrapper;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ServiceWrapper.class)
public class CTDLAppServiceWrapper extends DLAppServiceWrapper {

	@Override
	public void cancelCheckOut(long fileEntryId) throws PortalException {
		if (CTCollectionThreadLocal.isProductionMode()) {
			super.cancelCheckOut(fileEntryId);
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_getCtCollectionId(fileEntryId))) {

			super.cancelCheckOut(fileEntryId);
		}
	}

	@Override
	public void checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		if (CTCollectionThreadLocal.isProductionMode()) {
			super.checkOutFileEntry(fileEntryId, serviceContext);
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_getCtCollectionId(fileEntryId))) {

			super.checkOutFileEntry(fileEntryId, serviceContext);
		}
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		if (CTCollectionThreadLocal.isProductionMode()) {
			return super.checkOutFileEntry(
				fileEntryId, owner, expirationTime, serviceContext);
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_getCtCollectionId(fileEntryId))) {

			return super.checkOutFileEntry(
				fileEntryId, owner, expirationTime, serviceContext);
		}
	}

	private long _getCtCollectionId(long fileEntryId) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			DLFileEntry fileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
				fileEntryId);

			if (fileEntry != null) {
				return CTConstants.CT_COLLECTION_ID_PRODUCTION;
			}
		}

		return CTCollectionThreadLocal.getCTCollectionId();
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}