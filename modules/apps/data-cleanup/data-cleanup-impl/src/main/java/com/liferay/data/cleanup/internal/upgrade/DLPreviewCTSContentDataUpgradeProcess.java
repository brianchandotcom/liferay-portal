/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

/**
 * @author David Truong
 */
public class DLPreviewCTSContentDataUpgradeProcess extends UpgradeProcess {

	public DLPreviewCTSContentDataUpgradeProcess(
		CTCollectionLocalService ctCollectionLocalService,
		CTEntryLocalService ctEntryLocalService, Portal portal) {

		_ctCollectionLocalService = ctCollectionLocalService;
		_ctEntryLocalService = ctEntryLocalService;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<CTEntry> ctEntries = _ctEntryLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				CTEntryTable.INSTANCE
			).from(
				CTEntryTable.INSTANCE
			).innerJoinON(
				CTSContentTable.INSTANCE,
				CTEntryTable.INSTANCE.modelClassNameId.eq(
					_portal.getClassNameId(CTSContent.class)
				).and(
					CTSContentTable.INSTANCE.ctsContentId.eq(
						CTEntryTable.INSTANCE.modelClassPK
					).and(
						CTSContentTable.INSTANCE.ctCollectionId.eq(
							CTEntryTable.INSTANCE.ctCollectionId
						).and(
							CTSContentTable.INSTANCE.path.like(
								"document_preview%"
							).or(
								CTSContentTable.INSTANCE.path.like(
									"document_thumbnail%")
							).withParentheses()
						)
					)
				)
			));

		for (CTEntry ctEntry : ctEntries) {
			_ctCollectionLocalService.discardCTEntry(
				ctEntry.getCtCollectionId(), ctEntry.getModelClassNameId(),
				ctEntry.getModelClassPK(), true);
		}
	}

	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTEntryLocalService _ctEntryLocalService;
	private final Portal _portal;

}