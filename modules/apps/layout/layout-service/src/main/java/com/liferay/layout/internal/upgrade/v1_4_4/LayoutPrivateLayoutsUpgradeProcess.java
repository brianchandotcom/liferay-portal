/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_4;

import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portlet.PortalPreferencesWrapper;
import com.liferay.release.feature.flag.ReleaseFeatureFlag;
import com.liferay.release.feature.flag.ReleaseFeatureFlagManager;
import com.liferay.release.feature.flag.ReleaseFeatureFlagManagerUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutPrivateLayoutsUpgradeProcess extends UpgradeProcess {

	public LayoutPrivateLayoutsUpgradeProcess(
		CompanyLocalService companyLocalService,
		PortalPreferencesLocalService portalPreferencesLocalService,
		ReleaseFeatureFlagManager releaseFeatureFlagManager) {

		_companyLocalService = companyLocalService;
		_portalPreferencesLocalService = portalPreferencesLocalService;
		_releaseFeatureFlagManager = releaseFeatureFlagManager;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String value;

			if (ReleaseFeatureFlagManagerUtil.isEnabled(
					ReleaseFeatureFlag.DISABLE_PRIVATE_LAYOUTS)) {

				value = Boolean.FALSE.toString();
			}
			else {
				value = Boolean.TRUE.toString();
			}

			_companyLocalService.forEachCompanyId(
				companyId -> {
					try {
						PortalPreferencesWrapper portalPreferencesWrapper =
							(PortalPreferencesWrapper)
								_portalPreferencesLocalService.getPreferences(
									companyId,
									PortletKeys.PREFS_OWNER_TYPE_COMPANY);

						PortalPreferences portalPreferences =
							portalPreferencesWrapper.getPortalPreferencesImpl();

						portalPreferences.setValue(
							FeatureFlagConstants.PREFERENCE_NAMESPACE,
							"LPD-38869", value);

						_portalPreferencesLocalService.updatePreferences(
							companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
							portalPreferences);
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				});
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPrivateLayoutsUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final PortalPreferencesLocalService _portalPreferencesLocalService;
	private final ReleaseFeatureFlagManager _releaseFeatureFlagManager;

}