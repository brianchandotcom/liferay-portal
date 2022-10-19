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