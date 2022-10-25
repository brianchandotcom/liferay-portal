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

package com.liferay.release.feature.flag.web.internal.configuration.admin.definition;

import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.kernel.language.Language;
import com.liferay.release.feature.flag.ReleaseFeatureFlag;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
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
		return Stream.of(
			ReleaseFeatureFlag.values()
		).map(
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

			}
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private Language _language;

}