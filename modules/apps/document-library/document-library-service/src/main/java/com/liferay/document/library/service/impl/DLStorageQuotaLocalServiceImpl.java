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

package com.liferay.document.library.service.impl;

import com.liferay.document.library.exception.DLStorageQuotaExceededException;
import com.liferay.document.library.model.DLStorageQuota;
import com.liferay.document.library.service.base.DLStorageQuotaLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.document.library.model.DLStorageQuota",
	service = AopService.class
)
@CTAware
public class DLStorageQuotaLocalServiceImpl
	extends DLStorageQuotaLocalServiceBaseImpl {

	@Override
	public DLStorageQuota getCompanyDLStorageQuota(long companyId)
		throws PortalException {

		return dlStorageQuotaPersistence.findByCompanyId(companyId);
	}

	@BufferedIncrement(incrementClass = NumberIncrement.class)
	@Override
	public void incrementStorageSize(long companyId, long increment) {
		DLStorageQuota dlStorageQuota =
			dlStorageQuotaPersistence.fetchByCompanyId(companyId);

		if (dlStorageQuota == null) {
			dlStorageQuota = dlStorageQuotaLocalService.createDLStorageQuota(
				counterLocalService.increment());

			dlStorageQuota.setCompanyId(companyId);
		}

		dlStorageQuota.setStorageSize(
			dlStorageQuota.getStorageSize() + increment);

		dlStorageQuotaLocalService.updateDLStorageQuota(dlStorageQuota);
	}

	@Override
	public void validateStorageQuota(long companyId, long increment)
		throws PortalException {

		if (PropsValues.DATA_LIMIT_MAX_DL_STORAGE_SIZE <= 0) {
			return;
		}

		long currentStorageSize = _getStorageSize(companyId);

		if ((currentStorageSize + increment) >
				PropsValues.DATA_LIMIT_MAX_DL_STORAGE_SIZE) {

			throw new DLStorageQuotaExceededException(
				"Unable to exceed maximum alowed document library storage " +
					"size");
		}
	}

	private long _getStorageSize(long companyId) {
		DLStorageQuota dlStorageQuota =
			dlStorageQuotaPersistence.fetchByCompanyId(companyId);

		if (dlStorageQuota == null) {
			return 0;
		}

		return dlStorageQuota.getStorageSize();
	}

}