/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.internal.background.task.CTPublishBackgroundTaskExecutor;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.base.CTProcessLocalServiceBaseImpl;
import com.liferay.change.tracking.service.persistence.CTCollectionPersistence;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.CompanyLocalService;
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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CTProcess addCTProcess(long userId, long ctCollectionId)
		throws PortalException {

		return addCTProcess(
			userId, ctCollectionId, CTConstants.CT_COLLECTION_ID_PRODUCTION,
			null);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CTProcess addCTProcess(
			long userId, long fromCTCollectionId, long toCTCollectionId,
			long[] ctEntryIds)
		throws PortalException {

		CTCollection ctCollection = _ctCollectionPersistence.findByPrimaryKey(
			fromCTCollectionId);

		if (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			throw new IllegalStateException(
				"Change tracking collection is already published " +
					ctCollection);
		}

		if (toCTCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			ctCollection.setStatus(WorkflowConstants.STATUS_PENDING);

			ctCollection = _ctCollectionPersistence.update(ctCollection);

			_ctPreferencesLocalService.resetCTPreferences(
				ctCollection.getCtCollectionId());
		}

		long ctProcessId = counterLocalService.increment(
			CTProcess.class.getName());

		CTProcess ctProcess = ctProcessPersistence.create(ctProcessId);

		ctProcess.setCompanyId(ctCollection.getCompanyId());
		ctProcess.setUserId(userId);
		ctProcess.setCreateDate(new Date());
		ctProcess.setCtCollectionId(fromCTCollectionId);

		if (toCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			ctProcess.setType(CTConstants.CT_PROCESS_MOVE);
		}

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				"ctEntryIds", ctEntryIds
			).put(
				"ctProcessId", ctProcessId
			).put(
				"fromCTCollectionId", fromCTCollectionId
			).put(
				"toCTCollectionId", toCTCollectionId
			).build();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			Company company = _companyLocalService.getCompany(
				ctCollection.getCompanyId());

			String name = String.valueOf(fromCTCollectionId);

			if (toCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) {
				name =
					String.valueOf(fromCTCollectionId) + "_" +
						String.valueOf(toCTCollectionId);
			}

			BackgroundTask backgroundTask =
				_backgroundTaskLocalService.addBackgroundTask(
					userId, company.getGroupId(), name, null,
					CTPublishBackgroundTaskExecutor.class, taskContextMap,
					null);

			ctProcess.setBackgroundTaskId(backgroundTask.getBackgroundTaskId());
		}

		return ctProcessPersistence.update(ctProcess);
	}

	@Indexable(type = IndexableType.DELETE)
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
				_log.error(portalException);
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
		return ctProcessPersistence.findByCtCollectionId(ctCollectionId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTProcessLocalServiceImpl.class);

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CTCollectionPersistence _ctCollectionPersistence;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

}