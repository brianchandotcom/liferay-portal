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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.internal.background.task.CTPublishBackgroundTaskExecutor;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.base.CTProcessLocalServiceBaseImpl;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTProcess",
	service = AopService.class
)
public class CTProcessLocalServiceImpl extends CTProcessLocalServiceBaseImpl {

	@Override
	public CTProcess addCTProcess(long userId, long ctCollectionId)
		throws PortalException {

		CTCollection ctCollection = ctCollectionPersistence.findByPrimaryKey(
			ctCollectionId);

		if (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			throw new IllegalStateException(
				"Change tracking collection is already published " +
					ctCollection);
		}

		ctCollection.setStatus(WorkflowConstants.STATUS_PENDING);

		ctCollection = ctCollectionPersistence.update(ctCollection);

		_ctPreferencesLocalService.resetCTPreferences(
			ctCollection.getCtCollectionId());

		long ctProcessId = counterLocalService.increment(
			CTProcess.class.getName());

		CTProcess ctProcess = ctProcessPersistence.create(ctProcessId);

		ctProcess.setCompanyId(ctCollection.getCompanyId());
		ctProcess.setUserId(userId);
		ctProcess.setCreateDate(new Date());
		ctProcess.setCtCollectionId(ctCollectionId);

		Company company = companyLocalService.getCompany(
			ctCollection.getCompanyId());

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				"ctCollectionId", ctCollectionId
			).put(
				"ctProcessId", ctProcessId
			).build();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

			BackgroundTask backgroundTask =
				_backgroundTaskLocalService.addBackgroundTask(
					userId, company.getGroupId(),
					String.valueOf(ctCollectionId), null,
					CTPublishBackgroundTaskExecutor.class, taskContextMap,
					null);

			ctProcess.setBackgroundTaskId(backgroundTask.getBackgroundTaskId());
		}

		return ctProcessPersistence.update(ctProcess);
	}

	@Override
	public CTProcess deleteCTProcess(CTProcess ctProcess) {
		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(
				ctProcess.getBackgroundTaskId());

		if (backgroundTask != null) {
			try {
				_backgroundTaskLocalService.deleteBackgroundTask(
					backgroundTask);
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}

		return ctProcessPersistence.remove(ctProcess);
	}

	@Override
	public CTProcess fetchLatestCTProcess(long companyId) {
		return ctProcessPersistence.fetchByCompanyId_First(companyId, null);
	}

	@Override
	public List<CTProcess> getCTProcesses(long ctCollectionId) {
		return ctProcessPersistence.findByCollectionId(ctCollectionId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTProcessLocalServiceImpl.class);

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

}