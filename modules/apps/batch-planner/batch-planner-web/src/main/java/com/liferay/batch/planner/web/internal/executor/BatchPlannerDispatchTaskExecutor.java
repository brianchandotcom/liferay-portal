/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.web.internal.executor;

import com.liferay.batch.planner.batch.engine.broker.BatchEngineBroker;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.web.internal.helper.BatchPlannerPlanHelper;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + BatchPlannerDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=batch-planner"
	},
	service = DispatchTaskExecutor.class
)
public class BatchPlannerDispatchTaskExecutor extends BaseDispatchTaskExecutor {

	public static final String KEY = "batch-planner-executor-name";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		ExpandoBridge expandoBridge = dispatchTrigger.getExpandoBridge();

		UnicodeProperties dispatchTaskSettingsUnicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		long batchPlannerPlanId = GetterUtil.getLong(
			expandoBridge.getAttribute("batchPlannerPlanId", false),
			GetterUtil.getLong(
				dispatchTaskSettingsUnicodeProperties.getProperty(
					"batchPlannerPlanId")));

		String externalFileURL =
			dispatchTaskSettingsUnicodeProperties.getProperty(
				"external-file-url");

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					BatchPlannerPlan batchPlannerPlan =
						_batchPlannerPlanHelper.copyBatchPlannerPlan(
							dispatchTrigger.getUserId(), batchPlannerPlanId,
							externalFileURL,
							StringBundler.concat(
								"Triggered by ", dispatchTrigger.getName(),
								StringPool.COMMA_AND_SPACE,
								System.currentTimeMillis()));

					_batchEngineBroker.submit(
						batchPlannerPlan.getBatchPlannerPlanId());

					return null;
				});
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
	}

	@Override
	public String getName() {
		return KEY;
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	@Reference
	private BatchEngineBroker _batchEngineBroker;

	@Reference
	private BatchPlannerPlanHelper _batchPlannerPlanHelper;

}