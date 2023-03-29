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

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;
import java.util.List;

/**
 * @author Noor Najjar
 */
public class OutdatedPublishedCTCollectionUpgradeProcess
	extends UpgradeProcess {

	public OutdatedPublishedCTCollectionUpgradeProcess(
		CTCollectionLocalService ctCollectionLocalService, Portal portal) {

		_ctCollectionLocalService = ctCollectionLocalService;
		_portal = portal;
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
	private final Portal _portal;

}