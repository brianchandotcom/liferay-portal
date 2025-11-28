/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.change.tracking.store.service.CTSContentLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

/**
 * @author David Truong
 */
public class PublishedCTSContentDataUpgradeProcess extends UpgradeProcess {

	public PublishedCTSContentDataUpgradeProcess(
		CTSContentLocalService ctsContentLocalService, Portal portal) {

		_ctsContentLocalService = ctsContentLocalService;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<Long> ctsContentIds = _ctsContentLocalService.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				CTSContentTable.INSTANCE.ctsContentId
			).from(
				CTSContentTable.INSTANCE
			).where(
				CTSContentTable.INSTANCE.ctsContentId.notIn(
					DSLQueryFactoryUtil.select(
						CTEntryTable.INSTANCE.modelClassPK
					).from(
						CTEntryTable.INSTANCE
					).where(
						CTEntryTable.INSTANCE.modelClassNameId.eq(
							_portal.getClassNameId(CTSContent.class.getName()))
					)
				).or(
					CTSContentTable.INSTANCE.ctCollectionId.eq(
						CTConstants.CT_COLLECTION_ID_PRODUCTION)
				)
			));

		for (long ctsContentId : ctsContentIds) {
			_ctsContentLocalService.deleteCTSContent(ctsContentId);
		}
	}

	private final CTSContentLocalService _ctsContentLocalService;
	private final Portal _portal;

}