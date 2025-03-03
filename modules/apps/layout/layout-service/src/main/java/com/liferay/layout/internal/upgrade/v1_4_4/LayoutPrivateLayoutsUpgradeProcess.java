/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_4;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portlet.PortalPreferencesWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Dictionary;
import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutPrivateLayoutsUpgradeProcess extends UpgradeProcess {

	public LayoutPrivateLayoutsUpgradeProcess(
		CompanyLocalService companyLocalService,
		ConfigurationAdmin configurationAdmin,
		PortalPreferencesLocalService portalPreferencesLocalService) {

		_companyLocalService = companyLocalService;
		_configurationAdmin = configurationAdmin;
		_portalPreferencesLocalService = portalPreferencesLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String value = _getValue();

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

	private String _getValue() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(", Constants.SERVICE_PID,
				"=com.liferay.release.feature.flag.web.internal.configuration.",
				"ReleaseFeatureFlagConfiguration)"));

		if (ArrayUtil.isEmpty(configurations)) {
			if (!_hasReleaseSchemaVersion()) {
				return Boolean.TRUE.toString();
			}

			return Boolean.FALSE.toString();
		}

		Configuration configuration = configurations[0];

		Dictionary<String, Object> dictionary = configuration.getProperties();

		String[] disabledReleaseFeatureFlags = (String[])dictionary.get(
			"disabledReleaseFeatureFlags");

		if (ArrayUtil.isNotEmpty(disabledReleaseFeatureFlags) &&
			Objects.equals(
				disabledReleaseFeatureFlags[0], _DISABLE_PRIVATE_LAYOUTS)) {

			return Boolean.TRUE.toString();
		}

		return Boolean.FALSE.toString();
	}

	private boolean _hasReleaseSchemaVersion() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select schemaVersion from Release_ where servletContextName " +
					"= 'com.liferay.release.feature.flag.web'")) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					return false;
				}

				Version version = Version.parseVersion(
					resultSet.getString("schemaVersion"));

				if (_VERSION.compareTo(version) <= 0) {
					return true;
				}
			}
		}

		return false;
	}

	private static final String _DISABLE_PRIVATE_LAYOUTS =
		"DISABLE_PRIVATE_LAYOUTS";

	private static final Version _VERSION = Version.parseVersion("1.0.0");

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPrivateLayoutsUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ConfigurationAdmin _configurationAdmin;
	private final PortalPreferencesLocalService _portalPreferencesLocalService;

}