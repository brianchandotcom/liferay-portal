/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionIdSupplier;
import com.liferay.portal.kernel.change.tracking.CTCollectionPreviewThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;

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

		long companyId = CompanyThreadLocal.getCompanyId();

		long userId = PrincipalThreadLocal.getUserId();

		if ((companyId == CompanyConstants.SYSTEM) &&
			(userId == UserConstants.USER_ID_DEFAULT)) {

			return CTConstants.CT_COLLECTION_ID_PRODUCTION;
		}

		CTPreferences ctPreferences = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			ctPreferences = _ctPreferencesLocalService.fetchCTPreferences(
				companyId, userId);

			if ((ctPreferences == null) ||
				(ctPreferences.getCtCollectionId() ==
					CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

				if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-39203")) {
					return CTConstants.CT_COLLECTION_ID_PRODUCTION;
				}

				try {
					userId = _userLocalService.getGuestUserId(companyId);
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(portalException);
					}
				}

				ctPreferences = _ctPreferencesLocalService.getCTPreferences(
					companyId, userId);
			}
		}

		return ctPreferences.getCtCollectionId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTCollectionIdSupplierImpl.class);

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private UserLocalService _userLocalService;

}