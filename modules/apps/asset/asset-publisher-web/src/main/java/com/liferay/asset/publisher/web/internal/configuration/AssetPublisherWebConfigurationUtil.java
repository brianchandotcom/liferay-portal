/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.configuration;

import com.liferay.asset.publisher.web.internal.util.AssetPublisherCustomizer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration",
	service = AssetPublisherCustomizer.class
)
public class AssetPublisherWebConfigurationUtil {

	public static String checkCronExpression() {
		return _assetPublisherWebConfiguration.checkCronExpression();
	}

	public static int checkInterval() {
		return _assetPublisherWebConfiguration.checkInterval();
	}

	public static boolean dynamicExportEnabled() {
		return _assetPublisherWebConfiguration.dynamicExportEnabled();
	}

	public static int dynamicExportLimit() {
		return _assetPublisherWebConfiguration.dynamicExportLimit();
	}

	public static boolean enableAutoscroll() {
		return _assetPublisherWebConfiguration.enableAutoscroll();
	}

	public static boolean manualExportEnabled() {
		return _assetPublisherWebConfiguration.manualExportEnabled();
	}

	public static boolean permissionCheckingConfigurable() {
		return _assetPublisherWebConfiguration.permissionCheckingConfigurable();
	}

	public static boolean searchWithIndex() {
		return _assetPublisherWebConfiguration.searchWithIndex();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_assetPublisherWebConfiguration = ConfigurableUtil.createConfigurable(
			AssetPublisherWebConfiguration.class, properties);
	}

	private static volatile AssetPublisherWebConfiguration
		_assetPublisherWebConfiguration;

}