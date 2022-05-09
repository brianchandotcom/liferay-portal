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
		"dispatch.task.executor.name=batch-planner-executor-name",
		"dispatch.task.executor.type=batch-planner"
	},
	service = DispatchTaskExecutor.class
)
public class BatchPlannerDispatchTaskExecutor extends BaseDispatchTaskExecutor {

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
		return null;
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	@Reference
	private BatchEngineBroker _batchEngineBroker;

	@Reference
	private BatchPlannerPlanHelper _batchPlannerPlanHelper;

}