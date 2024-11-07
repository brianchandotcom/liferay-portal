/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.util;

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.internal.conflict.MissingRequirementConflictInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.model.CTSchemaVersion;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTSchemaVersionHelper.class)
public class CTSchemaVersionHelper {

	public void expireCTCollection(CTCollection ctCollection) {
		if (_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
				ctCollection.getSchemaVersionId())) {

			return;
		}

		try {
			Map<Long, List<ConflictInfo>> conflictMap =
				_ctCollectionLocalService.checkConflicts(ctCollection);

			for (Map.Entry<Long, List<ConflictInfo>> entry :
					conflictMap.entrySet()) {

				List<ConflictInfo> conflictInfos = entry.getValue();

				for (ConflictInfo conflictInfo : conflictInfos) {
					if (conflictInfo instanceof
							MissingRequirementConflictInfo) {

						ctCollection.setStatus(
							WorkflowConstants.STATUS_EXPIRED);

						ctCollection =
							_ctCollectionLocalService.updateCTCollection(
								ctCollection);

						return;
					}
				}
			}

			CTSchemaVersion ctSchemaVersion =
				_ctSchemaVersionLocalService.getLatestCTSchemaVersion(
					ctCollection.getCompanyId());

			ctCollection.setSchemaVersionId(
				ctSchemaVersion.getSchemaVersionId());

			ctCollection = _ctCollectionLocalService.updateCTCollection(
				ctCollection);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			ctCollection.setStatus(WorkflowConstants.STATUS_EXPIRED);

			_ctCollectionLocalService.updateCTCollection(ctCollection);
		}
	}

	public void expireCTCollections() {
		for (CTCollection ctCollection :
				_ctCollectionLocalService.<List<CTCollection>>dslQuery(
					DSLQueryFactoryUtil.select(
						CTCollectionTable.INSTANCE
					).from(
						CTCollectionTable.INSTANCE
					).where(
						CTCollectionTable.INSTANCE.status.eq(
							WorkflowConstants.STATUS_DRAFT)
					))) {

			expireCTCollection(ctCollection);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTSchemaVersionHelper.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTSchemaVersionLocalService _ctSchemaVersionLocalService;

}