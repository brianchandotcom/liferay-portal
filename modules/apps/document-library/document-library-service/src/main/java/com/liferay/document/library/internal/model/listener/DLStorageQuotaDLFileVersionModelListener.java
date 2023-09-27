/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.model.listener;

import com.liferay.document.library.exception.DLStorageQuotaExceededException;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.service.DLStorageQuotaLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.util.PortalInstances;

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

		_dlStorageQuotaLocalService.incrementStorageSize(
			dlFileVersion.getCompanyId(), dlFileVersion.getSize());
	}

	@Override
	public void onAfterRemove(DLFileVersion dlFileVersion)
		throws ModelListenerException {

		if (PortalInstances.isCurrentCompanyInDeletionProcess()) {
			return;
		}

		_dlStorageQuotaLocalService.incrementStorageSize(
			dlFileVersion.getCompanyId(), -dlFileVersion.getSize());
	}

	@Override
	public void onBeforeCreate(DLFileVersion dlFileVersion)
		throws ModelListenerException {

		try {
			_dlStorageQuotaLocalService.validateStorageQuota(
				dlFileVersion.getCompanyId(), dlFileVersion.getSize());
		}
		catch (DLStorageQuotaExceededException
					dlStorageQuotaExceededException) {

			ReflectionUtil.throwException(dlStorageQuotaExceededException);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private DLStorageQuotaLocalService _dlStorageQuotaLocalService;

}