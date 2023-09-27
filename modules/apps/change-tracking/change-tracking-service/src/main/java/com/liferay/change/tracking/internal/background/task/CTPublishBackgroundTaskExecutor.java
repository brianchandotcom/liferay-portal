/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.internal.CTServiceRegistry;
import com.liferay.change.tracking.internal.background.task.display.CTPublishBackgroundTaskDisplay;
import com.liferay.change.tracking.internal.helper.CTTableMapperHelper;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 * @author Daniel Kocsis
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.change.tracking.internal.background.task.CTPublishBackgroundTaskExecutor",
	service = AopService.class
)
public class CTPublishBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor implements AopService {

	public CTPublishBackgroundTaskExecutor() {
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return _backgroundTaskExecutor;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class
	)
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long fromCTCollectionId = GetterUtil.getLong(
			taskContextMap.get("fromCTCollectionId"));

		CTCollection fromCTCollection =
			_ctCollectionLocalService.getCTCollection(fromCTCollectionId);

		String fromCTCollectionName = fromCTCollection.getName();

		long toCTCollectionId = GetterUtil.getLong(
			taskContextMap.get("toCTCollectionId"));

		String toCTCollectionName;

		if (toCTCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			toCTCollectionName = "Production";
		}
		else {
			CTCollection toCTCollection =
				_ctCollectionLocalService.getCTCollection(toCTCollectionId);

			toCTCollectionName = toCTCollection.getName();
		}

		if (!_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				fromCTCollection.getSchemaVersionId())) {

			throw new IllegalArgumentException(
				StringBundler.concat(
					"Unable to publish from ", fromCTCollectionName, " to ",
					toCTCollectionName,
					" because it is out of date with the current release"));
		}

		if (toCTCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						fromCTCollectionId)) {

				_ctServiceRegistry.onBeforePublish(fromCTCollectionId);
			}
		}

		List<CTEntry> ctEntries = null;

		long[] ctEntryIds = (long[])taskContextMap.get("ctEntryIds");

		if (ctEntryIds != null) {
			ctEntries = _ctCollectionLocalService.getRelatedCTEntries(
				fromCTCollectionId, ctEntryIds);
		}
		else {
			ctEntries = _ctEntryLocalService.getCTCollectionCTEntries(
				fromCTCollectionId);
		}

		Map<Long, List<ConflictInfo>> conflictInfosMap =
			_ctCollectionLocalService.checkConflicts(
				fromCTCollection.getCompanyId(), ctEntries, fromCTCollectionId,
				fromCTCollectionName, toCTCollectionId, toCTCollectionName);

		if (!conflictInfosMap.isEmpty()) {
			List<ConflictInfo> unresolvedConflictInfos = new ArrayList<>();

			for (Map.Entry<Long, List<ConflictInfo>> entry :
					conflictInfosMap.entrySet()) {

				for (ConflictInfo conflictInfo : entry.getValue()) {
					if (!conflictInfo.isResolved()) {
						unresolvedConflictInfos.add(conflictInfo);
					}
				}
			}

			if (!unresolvedConflictInfos.isEmpty()) {
				throw new SystemException(
					StringBundler.concat(
						"Unable to publish ", fromCTCollectionName, " to ",
						toCTCollectionName,
						" because of unresolved conflicts: ",
						unresolvedConflictInfos));
			}
		}

		Map<Long, CTServicePublisher<?>> ctServicePublishers = new HashMap<>();

		for (CTEntry ctEntry : ctEntries) {
			CTServicePublisher<?> ctServicePublisher =
				ctServicePublishers.computeIfAbsent(
					ctEntry.getModelClassNameId(),
					modelClassNameId -> {
						CTService<?> ctService =
							_ctServiceRegistry.getCTService(modelClassNameId);

						if (ctService != null) {
							return new CTServicePublisher<>(
								_ctEntryLocalService, ctService,
								modelClassNameId, fromCTCollectionId,
								toCTCollectionId);
						}

						throw new SystemException(
							StringBundler.concat(
								"Unable to publish from ", fromCTCollectionName,
								" to ", toCTCollectionName,
								" because service for ", modelClassNameId,
								" is missing"));
					});

			ctServicePublisher.addCTEntry(ctEntry);
		}

		for (CTServicePublisher<?> ctServicePublisher :
				ctServicePublishers.values()) {

			ctServicePublisher.publish();
		}

		for (CTTableMapperHelper ctTableMapperHelper :
				_ctServiceRegistry.getCTTableMapperHelpers()) {

			ctTableMapperHelper.publish(
				fromCTCollectionId, toCTCollectionId,
				_multiVMPool.getPortalCacheManager());
		}

		if (toCTCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			Date modifiedDate = new Date();

			fromCTCollection.setModifiedDate(modifiedDate);

			fromCTCollection.setStatus(WorkflowConstants.STATUS_APPROVED);
			fromCTCollection.setStatusByUserId(backgroundTask.getUserId());
			fromCTCollection.setStatusDate(modifiedDate);

			_ctCollectionLocalService.updateCTCollection(fromCTCollection);

			_ctServiceRegistry.onAfterPublish(fromCTCollectionId);
		}
		else {
			for (CTEntry ctEntry : ctEntries) {
				ctEntry.setCtCollectionId(toCTCollectionId);

				_ctEntryLocalService.updateCTEntry(ctEntry);
			}
		}

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {BackgroundTaskExecutor.class};
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return new CTPublishBackgroundTaskDisplay(backgroundTask);
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		_backgroundTaskExecutor = (BackgroundTaskExecutor)aopProxy;
	}

	private BackgroundTaskExecutor _backgroundTaskExecutor;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTSchemaVersionLocalService _ctSchemaVersionLocalService;

	@Reference
	private CTServiceRegistry _ctServiceRegistry;

	@Reference
	private MultiVMPool _multiVMPool;

}