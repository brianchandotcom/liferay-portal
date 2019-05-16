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

package com.liferay.batch.engine.fileimport.internal.messaging;

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.BatchStatus;
import com.liferay.batch.engine.core.BatchJob;
import com.liferay.batch.engine.core.configuration.BatchJobRegistry;
import com.liferay.batch.engine.core.launch.BatchJobLauncher;
import com.liferay.batch.engine.fileimport.BatchFileImportType;
import com.liferay.batch.engine.fileimport.configuration.BatchFileImportJobNameMapper;
import com.liferay.batch.engine.fileimport.internal.util.JobSettingsProperties;
import com.liferay.batch.engine.model.BatchFileImport;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.batch.engine.service.BatchFileImportLocalService;
import com.liferay.batch.engine.service.BatchJobExecutionLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true, property = {"concurrent.import.threads=4", "interval=15"},
	service = CheckBatchFileImportsListener.class
)
public class CheckBatchFileImportsListener extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		if (_clusterMasterExecutor.isEnabled() &&
			!_clusterMasterExecutor.isMaster()) {

			return;
		}

		_concurrentImportThreads = GetterUtil.getInteger(
			properties.get("concurrent.import.threads"));

		_checkUnfinishedBatchFileImports();

		_activateSchedulerEngine(
			GetterUtil.getInteger(properties.get("interval")));
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		if (_batchFileImportLocalService.countBatchFileImports(
				BatchStatus.STARTED) > 0) {

			return;
		}

		List<BatchFileImport> batchFileImports =
			_batchFileImportLocalService.getFirstBatchFileImports(
				BatchStatus.UNKNOWN, _concurrentImportThreads);

		for (BatchFileImport batchFileImport : batchFileImports) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(batchFileImport.getCompanyId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			try {
				_importFile(batchFileImport);
			}
			finally {
				ServiceContextThreadLocal.popServiceContext();
			}
		}
	}

	private void _activateSchedulerEngine(int interval) {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null, interval, TimeUnit.SECOND);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	private void _checkUnfinishedBatchFileImports() throws Exception {
		List<BatchFileImport> batchFileImports =
			_batchFileImportLocalService.getBatchFileImports(
				BatchStatus.STARTED);

		for (BatchFileImport batchFileImport : batchFileImports) {
			_invokeTransaction(() -> _resetBatchStatuses(batchFileImport));
		}
	}

	private Void _importFile(BatchFileImport batchFileImport)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug("CheckBatchFileImportsListener is executing");
		}

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			batchFileImport.getFileEntryId());

		BatchJob batchJob = _batchJobRegistry.getBatchJob(
			_batchFileImportJobNameMapper.getJobName(
				batchFileImport.getDomainName(), batchFileImport.getVersion(),
				BatchFileImportType.valueOf(
					StringUtil.upperCase(fileEntry.getExtension())),
				BatchFileImportOperation.valueOf(
					batchFileImport.getOperation())));

		try {
			_batchJobLauncher.runAsync(
				batchJob,
				JobSettingsProperties.getJobSettingsProperties(
					batchFileImport, fileEntry));
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}

		return null;
	}

	private void _invokeTransaction(Callable<Void> callable) throws Exception {
		try {
			TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable t) {
			throw new Exception(t);
		}
	}

	private Void _resetBatchStatuses(BatchFileImport batchFileImport)
		throws PortalException {

		batchFileImport.setStatus(BatchStatus.UNKNOWN.toString());

		_batchFileImportLocalService.updateBatchFileImport(batchFileImport);

		BatchJobExecution batchJobExecution =
			_batchJobExecutionLocalService.getBatchJobExecution(
				batchFileImport.getBatchJobExecutionId());

		batchJobExecution.setStatus(BatchStatus.FAILED.toString());

		_batchJobExecutionLocalService.updateBatchJobExecution(
			batchJobExecution);

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckBatchFileImportsListener.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private BatchFileImportJobNameMapper _batchFileImportJobNameMapper;

	@Reference
	private BatchFileImportLocalService _batchFileImportLocalService;

	@Reference
	private BatchJobExecutionLocalService _batchJobExecutionLocalService;

	@Reference
	private BatchJobLauncher _batchJobLauncher;

	@Reference
	private BatchJobRegistry _batchJobRegistry;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	private int _concurrentImportThreads;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}