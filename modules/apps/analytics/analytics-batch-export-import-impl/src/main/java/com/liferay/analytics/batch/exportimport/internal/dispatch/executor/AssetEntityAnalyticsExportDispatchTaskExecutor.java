/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.dispatch.executor;

import com.liferay.analytics.dxp.entity.rest.dto.v1_0.AssetEntity;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + AssetEntityAnalyticsExportDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + AssetEntityAnalyticsExportDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class AssetEntityAnalyticsExportDispatchTaskExecutor
	extends BaseAnalyticsDispatchTaskExecutor {

	public static final String KEY = "export-analytics-asset-entities";

	@Override
	public String getName() {
		return KEY;
	}

	@Override
	public boolean isHiddenInUI() {
		return !_featureFlagManager.isEnabled("LRAC-14771");
	}

	@Override
	protected List<String> getBatchEngineExportTaskItemDelegateNames() {
		return _batchEngineExportTaskItemDelegateNames;
	}

	@Override
	protected String getResourceName() {
		return AssetEntity.class.getName();
	}

	private static final List<String> _batchEngineExportTaskItemDelegateNames =
		Arrays.asList("asset-entry-analytics-dxp-entities");

	@Reference
	private FeatureFlagManager _featureFlagManager;

}