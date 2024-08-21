/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.rest.internal.resource.v1_0;

import com.liferay.analytics.reports.rest.resource.v1_0.AssetMetricResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Marcos Martins
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/asset-metric.properties",
	scope = ServiceScope.PROTOTYPE, service = AssetMetricResource.class
)
public class AssetMetricResourceImpl extends BaseAssetMetricResourceImpl {
}