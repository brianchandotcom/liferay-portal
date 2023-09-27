/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_3_1;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.layout.model.LayoutLocalizationTable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author David Truong
 */
public class LayoutLocalizationUpgradeProcess extends UpgradeProcess {

	public LayoutLocalizationUpgradeProcess(
		CTCollectionLocalService ctCollectionLocalService,
		CTEntryLocalService ctEntryLocalService, Portal portal) {

		_ctCollectionLocalService = ctCollectionLocalService;
		_ctEntryLocalService = ctEntryLocalService;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from LayoutLocalization where plid not in (select plid " +
				"from Layout)");

		for (CTCollection ctCollection :
				(List<CTCollection>)_ctCollectionLocalService.dslQuery(
					DSLQueryFactoryUtil.select(
						CTCollectionTable.INSTANCE
					).from(
						CTCollectionTable.INSTANCE
					).where(
						CTCollectionTable.INSTANCE.status.eq(
							WorkflowConstants.STATUS_DRAFT)
					))) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollection.getCtCollectionId())) {

				for (CTEntry ctEntry :
						(List<CTEntry>)_ctEntryLocalService.dslQuery(
							DSLQueryFactoryUtil.select(
								CTEntryTable.INSTANCE
							).from(
								CTEntryTable.INSTANCE
							).where(
								CTEntryTable.INSTANCE.modelClassNameId.eq(
									_portal.getClassNameId(
										LayoutLocalization.class.getName())
								).and(
									CTEntryTable.INSTANCE.modelClassPK.notIn(
										DSLQueryFactoryUtil.select(
											LayoutLocalizationTable.INSTANCE.
												layoutLocalizationId
										).from(
											LayoutLocalizationTable.INSTANCE
										))
								)
							))) {

					_ctEntryLocalService.deleteCTEntry(ctEntry);
				}
			}
		}
	}

	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTEntryLocalService _ctEntryLocalService;
	private final Portal _portal;

}