/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.util.v1_0;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

/**
 * @author David Truong
 */
public class PublishUtil {

	public static void schedulePublish(
			long ctCollectionId,
			CTCollectionLocalService ctCollectionLocalService,
			CTPreferencesLocalService ctPreferencesLocalService,
			SchedulerEngineHelper schedulerEngineHelper, Date startDate,
			TriggerFactory triggerFactory, long userId)
		throws PortalException {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					CTCollection ctCollection =
						ctCollectionLocalService.getCTCollection(
							ctCollectionId);

					ctCollection.setStatus(WorkflowConstants.STATUS_SCHEDULED);

					ctCollectionLocalService.updateCTCollection(ctCollection);

					ctPreferencesLocalService.resetCTPreferences(
						ctCollectionId);

					Message message = new Message();

					message.put("ctCollectionId", ctCollectionId);
					message.put("userId", userId);

					schedulerEngineHelper.schedule(
						triggerFactory.createTrigger(
							String.valueOf(ctCollectionId),
							_CT_COLLECTION_SCHEDULED_PUBLISH, startDate, null,
							0, null),
						StorageType.PERSISTED, String.valueOf(ctCollectionId),
						_CT_COLLECTION_SCHEDULED_PUBLISH, message);

					return null;
				});
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Throwable throwable) {
			ReflectionUtil.throwException(throwable);
		}
	}

	public static void unschedulePublish(
			long ctCollectionId,
			CTCollectionLocalService ctCollectionLocalService,
			SchedulerEngineHelper schedulerEngineHelper)
		throws PortalException {

		String jobName = String.valueOf(ctCollectionId);

		SchedulerResponse schedulerResponse =
			schedulerEngineHelper.getScheduledJob(
				jobName, _CT_COLLECTION_SCHEDULED_PUBLISH,
				StorageType.PERSISTED);

		if (schedulerResponse == null) {
			return;
		}

		Message message = schedulerResponse.getMessage();

		CTCollection ctCollection = ctCollectionLocalService.fetchCTCollection(
			message.getLong("ctCollectionId"));

		if ((ctCollection == null) ||
			(ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		ctCollection.setStatus(WorkflowConstants.STATUS_DRAFT);

		ctCollectionLocalService.updateCTCollection(ctCollection);

		schedulerEngineHelper.delete(
			jobName, _CT_COLLECTION_SCHEDULED_PUBLISH, StorageType.PERSISTED);
	}

	private static final String _CT_COLLECTION_SCHEDULED_PUBLISH =
		"liferay/ct_collection_scheduled_publish";

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

}