/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;
import java.util.List;

/**
 * @author Noor Najjar
 */
public class OutdatedPublishedCTCollectionUpgradeProcess
	extends UpgradeProcess {

	public OutdatedPublishedCTCollectionUpgradeProcess(
		CTCollectionLocalService ctCollectionLocalService) {

		_ctCollectionLocalService = ctCollectionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MONTH, -6);

		List<CTCollection> ctCollections = _ctCollectionLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				CTCollectionTable.INSTANCE
			).from(
				CTCollectionTable.INSTANCE
			).where(
				CTCollectionTable.INSTANCE.status.eq(
					WorkflowConstants.STATUS_APPROVED
				).and(
					CTCollectionTable.INSTANCE.statusDate.lte(
						calendar.getTime())
				)
			));

		for (CTCollection ctCollection : ctCollections) {
			_ctCollectionLocalService.deleteCTCollection(ctCollection);
		}
	}

	private final CTCollectionLocalService _ctCollectionLocalService;

}