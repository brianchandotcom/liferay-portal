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
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.document.library.model.DLStorageQuota;
import com.liferay.document.library.service.base.DLStorageQuotaLocalServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.util.PropsValues;

import java.util.Iterator;

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
			if (increment <= 0) {
				return;
			}

			dlStorageQuota = _getDLStorageQuota(companyId);
		}

		dlStorageQuota.setStorageSize(
			dlStorageQuota.getStorageSize() + increment);

		dlStorageQuotaLocalService.updateDLStorageQuota(dlStorageQuota);
	}

	@Override
	public void updateStorageSize(long companyId) {
		DLStorageQuota dlStorageQuota = _getDLStorageQuota(companyId);

		dlStorageQuota.setStorageSize(_getActualStorageSize(companyId));

		dlStorageQuotaLocalService.updateDLStorageQuota(dlStorageQuota);
	}

	@Override
	public void validateStorageQuota(long companyId, long increment)
		throws PortalException {

		if (PropsValues.DATA_LIMIT_DL_STORAGE_MAX_SIZE <= 0) {
			return;
		}

		long currentStorageSize = _getStorageSize(companyId);

		if ((currentStorageSize + increment) >
				PropsValues.DATA_LIMIT_DL_STORAGE_MAX_SIZE) {

			throw new DLStorageQuotaExceededException(
				"Unable to exceed maximum alowed document library storage " +
					"size");
		}
	}

	private long _getActualStorageSize(long companyId) {
		Iterable<Long> iterable = dslQuery(
			DSLQueryFactoryUtil.select(
				DSLFunctionFactoryUtil.sum(
					DLFileVersionTable.INSTANCE.size
				).as(
					"SUM_VALUE"
				)
			).from(
				DLFileVersionTable.INSTANCE
			).where(
				DLFileVersionTable.INSTANCE.companyId.eq(companyId)
			));

		Iterator<Long> iterator = iterable.iterator();

		return iterator.next();
	}

	private DLStorageQuota _getDLStorageQuota(long companyId) {
		DLStorageQuota dlStorageQuota =
			dlStorageQuotaPersistence.fetchByCompanyId(companyId);

		if (dlStorageQuota != null) {
			return dlStorageQuota;
		}

		dlStorageQuota = dlStorageQuotaLocalService.createDLStorageQuota(
			counterLocalService.increment());

		dlStorageQuota.setCompanyId(companyId);

		return dlStorageQuota;
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