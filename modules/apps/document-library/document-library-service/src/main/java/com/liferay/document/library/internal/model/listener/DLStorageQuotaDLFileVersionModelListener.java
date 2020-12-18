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

package com.liferay.document.library.internal.model.listener;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.service.DLStorageQuotaLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ModelListener.class)
public class DLStorageQuotaDLFileVersionModelListener
	extends BaseModelListener<DLFileVersion> {

	@Override
	public void onAfterCreate(DLFileVersion dlFileVersion)
		throws ModelListenerException {

		super.onAfterCreate(dlFileVersion);

		_dlStorageQuotaLocalService.incrementStorageSize(
			dlFileVersion.getCompanyId(), dlFileVersion.getSize());
	}

	@Override
	public void onAfterRemove(DLFileVersion dlFileVersion)
		throws ModelListenerException {

		super.onAfterRemove(dlFileVersion);

		_dlStorageQuotaLocalService.incrementStorageSize(
			dlFileVersion.getCompanyId(), -dlFileVersion.getSize());
	}

	@Override
	public void onBeforeCreate(DLFileVersion dlFileVersion)
		throws ModelListenerException {

		try {
			super.onBeforeCreate(dlFileVersion);

			_dlStorageQuotaLocalService.validateStorageQuota(
				dlFileVersion.getCompanyId(), dlFileVersion.getSize());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private DLStorageQuotaLocalService _dlStorageQuotaLocalService;

}