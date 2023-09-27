/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionIdSupplier;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CTCollectionIdSupplier.class)
public class CTCollectionIdSupplierImpl implements CTCollectionIdSupplier {

	@Override
	public long getCTCollectionId() {
		long ctCollectionId =
			CTCollectionPreviewThreadLocal.getCTCollectionId();

		if (ctCollectionId > -1) {
			return ctCollectionId;
		}

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				CompanyThreadLocal.getCompanyId(),
				PrincipalThreadLocal.getUserId());

		if (ctPreferences == null) {
			return CTConstants.CT_COLLECTION_ID_PRODUCTION;
		}

		return ctPreferences.getCtCollectionId();
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

}