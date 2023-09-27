/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.feature.flag.web.internal.configuration.admin.definition;

import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.release.feature.flag.ReleaseFeatureFlag;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"configuration.field.name=disabledReleaseFeatureFlags",
		"configuration.pid=com.liferay.release.feature.flag.web.internal.configuration.ReleaseFeatureFlagConfiguration"
	},
	service = ConfigurationFieldOptionsProvider.class
)
public class ReleaseFeatureFlagConfigurationFieldOptionsProvider
	implements ConfigurationFieldOptionsProvider {

	@Override
	public List<Option> getOptions() {
		return TransformUtil.transformToList(
			ReleaseFeatureFlag.values(),
			releaseFeatureFlag -> new Option() {

				@Override
				public String getLabel(Locale locale) {
					return _language.get(
						locale,
						"release-feature-flag[" + releaseFeatureFlag + "]");
				}

				@Override
				public String getValue() {
					return releaseFeatureFlag.toString();
				}

			});
	}

	@Reference
	private Language _language;

}