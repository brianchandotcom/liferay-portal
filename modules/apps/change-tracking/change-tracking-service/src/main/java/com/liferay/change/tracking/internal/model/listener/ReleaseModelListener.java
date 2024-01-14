/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.model.listener;

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
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = ModelListener.class)
public class ReleaseModelListener extends BaseModelListener<Release> {

	@Override
	public void onAfterCreate(Release release) {
		if (!Objects.equals(release.getSchemaVersion(), "0.0.0")) {
			_expireCTCollections();
		}
	}

	@Override
	public void onAfterRemove(Release release) {
		_expireCTCollections();
	}

	@Override
	public void onAfterUpdate(Release originalRelease, Release release) {
		if (!Objects.equals(
				originalRelease.getSchemaVersion(),
				release.getSchemaVersion())) {

			Version version1 = Version.parseVersion(
				originalRelease.getSchemaVersion());
			Version version2 = Version.parseVersion(release.getSchemaVersion());

			if ((version1.getMajor() != version2.getMajor()) ||
				(version1.getMinor() != version2.getMinor())) {

				_expireCTCollections();
			}
		}
	}

	private void _expireCTCollection(CTCollection ctCollection) {
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

	private void _expireCTCollections() {
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

			_expireCTCollection(ctCollection);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseModelListener.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTSchemaVersionLocalService _ctSchemaVersionLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}