/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.dispatch.executor;

import com.liferay.analytics.batch.exportimport.manager.AnalyticsBatchExportImportManager;
import com.liferay.analytics.dxp.entity.rest.dto.v1_0.AssetEntity;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

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
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		if (!_analyticsConfigurationRegistry.isActive()) {
			return;
		}

		_analyticsBatchExportImportManager.exportToAnalyticsCloud(
			"asset-entry-analytics-dxp-entities",
			dispatchTrigger.getCompanyId(), null, null,
			getNotificationUnsafeConsumer(
				dispatchTrigger.getDispatchTriggerId(),
				dispatchTaskExecutorOutput),
			getResourceLastModifiedDate(dispatchTrigger.getDispatchTriggerId()),
			AssetEntity.class.getName(), dispatchTrigger.getUserId());
	}

	@Override
	public String getName() {
		return KEY;
	}

	@Override
	public boolean isHiddenInUI() {
		return !FeatureFlagManagerUtil.isEnabled("LRAC-14771");
	}

	@Reference
	private AnalyticsBatchExportImportManager
		_analyticsBatchExportImportManager;

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

}