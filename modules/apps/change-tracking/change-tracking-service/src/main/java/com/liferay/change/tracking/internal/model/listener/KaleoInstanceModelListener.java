/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTable;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = ModelListener.class)
public class KaleoInstanceModelListener
	extends BaseModelListener<KaleoInstance> {

	@Override
	public void onAfterCreate(KaleoInstance kaleoInstance) {
		long ctCollectionId = kaleoInstance.getCtCollectionId();

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return;
		}

		try {
			CTCollection ctCollection =
				_ctCollectionLocalService.fetchCTCollection(ctCollectionId);

			if (ctCollection.getStatus() == WorkflowConstants.STATUS_DRAFT) {
				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setProductionModeWithSafeCloseable()) {

					ctCollection.setStatus(WorkflowConstants.STATUS_INCOMPLETE);

					_ctCollectionLocalService.updateCTCollection(ctCollection);
				}
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterUpdate(
		KaleoInstance originalKaleoInstance, KaleoInstance kaleoInstance) {

		long ctCollectionId = kaleoInstance.getCtCollectionId();

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return;
		}

		try {
			if (kaleoInstance.isCompleted() &&
				!_hasUnapprovedWorkflowChanges(ctCollectionId, kaleoInstance)) {

				CTCollection ctCollection =
					_ctCollectionLocalService.fetchCTCollection(ctCollectionId);

				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setProductionModeWithSafeCloseable()) {

					ctCollection.setStatus(WorkflowConstants.STATUS_DRAFT);

					_ctCollectionLocalService.updateCTCollection(ctCollection);
				}
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private boolean _hasUnapprovedWorkflowChanges(
		long ctCollectionId, KaleoInstance kaleoInstance) {

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId);
			SafeCloseable safeCloseable2 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
					CTSQLModeThreadLocal.CTSQLMode.CT_ONLY)) {

			List<Long> kaleoInstanceIds = _kaleoInstanceLocalService.dslQuery(
				DSLQueryFactoryUtil.selectDistinct(
					KaleoInstanceTable.INSTANCE.kaleoInstanceId
				).from(
					KaleoInstanceTable.INSTANCE
				).where(
					KaleoInstanceTable.INSTANCE.completed.eq(
						false
					).and(
						KaleoInstanceTable.INSTANCE.kaleoInstanceId.neq(
							kaleoInstance.getKaleoInstanceId())
					)
				));

			return !kaleoInstanceIds.isEmpty();
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

}