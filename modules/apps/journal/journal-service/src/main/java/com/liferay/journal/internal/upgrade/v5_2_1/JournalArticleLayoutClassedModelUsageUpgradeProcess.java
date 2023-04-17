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

package com.liferay.journal.internal.upgrade.v5_2_1;

import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleLayoutClassedModelUsageUpgradeProcess
	extends UpgradeProcess {

	public JournalArticleLayoutClassedModelUsageUpgradeProcess(
		LayoutClassedModelUsageLocalService layoutClassedModelUsageLocalService,
		LayoutRevisionLocalService layoutRevisionLocalService) {

		_layoutClassedModelUsageLocalService =
			layoutClassedModelUsageLocalService;
		_layoutRevisionLocalService = layoutRevisionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<LayoutRevision> layoutRevisions =
			_layoutRevisionLocalService.getLayoutRevisions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (LayoutRevision layoutRevision : layoutRevisions) {
			List<LayoutClassedModelUsage> layoutClassedModelUsages =
				_layoutClassedModelUsageLocalService.
					getLayoutClassedModelUsagesByPlid(
						layoutRevision.getLayoutRevisionId());

			for (LayoutClassedModelUsage layoutClassedModelUsage :
					layoutClassedModelUsages) {

				LayoutClassedModelUsage existingLayoutClassedModelUsage =
					_layoutClassedModelUsageLocalService.
						fetchLayoutClassedModelUsage(
							layoutClassedModelUsage.getClassNameId(),
							layoutClassedModelUsage.getClassPK(),
							layoutClassedModelUsage.getContainerKey(),
							layoutClassedModelUsage.getContainerType(),
							layoutRevision.getPlid());

				if (existingLayoutClassedModelUsage != null) {
					_layoutClassedModelUsageLocalService.
						deleteLayoutClassedModelUsage(
							layoutClassedModelUsage.
								getLayoutClassedModelUsageId());

					continue;
				}

				layoutClassedModelUsage.setPlid(layoutRevision.getPlid());

				_layoutClassedModelUsageLocalService.
					updateLayoutClassedModelUsage(layoutClassedModelUsage);
			}
		}
	}

	private final LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;
	private final LayoutRevisionLocalService _layoutRevisionLocalService;

}