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

package com.liferay.batch.engine.internal.component;

import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.internal.unit.BatchEngineUnitProcessorImpl;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegateAdaptorFactory;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.felix.scr.ext.annotation.DSExt;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(factory = "batch.engine.import.task.component", service = {})
@DSExt.PersistentFactoryComponent
public class BatchEngineImportTaskComponent {

	@Activate
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		ComponentInstance<?> componentInstance =
			componentContext.getComponentInstance();

		BatchEngineUnitConfiguration batchEngineUnitConfiguration =
			(BatchEngineUnitConfiguration)properties.get(
				"batchEngineUnitConfiguration");

		BatchEngineUnit batchEngineUnit = (BatchEngineUnit)properties.get(
			"batchEngineUnit");

		byte[] content = (byte[])properties.get("content");
		String contentType = (String)properties.get("contentType");
		AtomicBoolean disposed = (AtomicBoolean)properties.get("disposed");

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineUnitProcessorImpl.class.getName());

		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate =
			_vulcanBatchEngineTaskItemDelegateAdaptorFactory.create(
				_vulcanBatchEngineTaskItemDelegate.getValue());

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				null, batchEngineUnitConfiguration.getCompanyId(),
				batchEngineUnitConfiguration.getUserId(), 100,
				batchEngineUnitConfiguration.getCallbackURL(),
				batchEngineUnitConfiguration.getClassName(), content,
				StringUtil.toUpperCase(contentType),
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				batchEngineUnitConfiguration.getFieldNameMappingMap(),
				BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL,
				BatchEngineTaskOperation.CREATE.name(),
				batchEngineUnitConfiguration.getParameters(),
				batchEngineUnitConfiguration.getTaskItemDelegateName(),
				batchEngineTaskItemDelegate);

		executorService.submit(
			() -> {
				_batchEngineImportTaskExecutor.execute(
					batchEngineImportTask, batchEngineTaskItemDelegate);

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Successfully deployed batch engine file ",
							batchEngineUnit.getFileName(), " ",
							batchEngineUnit.getDataFileName()));
				}
			});

		disposed.set(true);

		componentInstance.dispose();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportTaskComponent.class);

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	@SuppressWarnings("rawtypes")
	private Map.Entry<Map<String, Object>, VulcanBatchEngineTaskItemDelegate>
		_vulcanBatchEngineTaskItemDelegate;

	@Reference
	private VulcanBatchEngineTaskItemDelegateAdaptorFactory
		_vulcanBatchEngineTaskItemDelegateAdaptorFactory;

}