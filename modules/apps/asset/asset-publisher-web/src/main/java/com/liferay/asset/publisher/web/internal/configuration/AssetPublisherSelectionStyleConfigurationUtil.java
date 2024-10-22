/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.configuration;

import com.liferay.asset.publisher.web.internal.constants.AssetPublisherSelectionStyleConstants;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.internal.configuration.AssetPublisherSelectionStyleConfiguration",
	service = {}
)
public class AssetPublisherSelectionStyleConfigurationUtil {

	public static String defaultSelectionStyle() {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-39304")) {
			return AssetPublisherSelectionStyleConstants.TYPE_ASSET_LIST;
		}

		return _assetPublisherSelectionStyleConfiguration.
			defaultSelectionStyle();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_assetPublisherSelectionStyleConfiguration =
			ConfigurableUtil.createConfigurable(
				AssetPublisherSelectionStyleConfiguration.class, properties);
	}

	private static volatile AssetPublisherSelectionStyleConfiguration
		_assetPublisherSelectionStyleConfiguration;

}