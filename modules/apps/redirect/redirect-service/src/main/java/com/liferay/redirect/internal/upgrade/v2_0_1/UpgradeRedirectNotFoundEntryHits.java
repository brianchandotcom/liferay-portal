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

package com.liferay.redirect.internal.upgrade.v2_0_1;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.view.count.ViewCountManagerUtil;
import com.liferay.redirect.model.RedirectNotFoundEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alejandro Tardín
 */
public class UpgradeRedirectNotFoundEntryHits extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				SQLTransformer.transform(
					"select companyId, hits, redirectNotFoundEntryId from " +
						"RedirectNotFoundEntry"));
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				int hits = rs.getInt("hits");
				long redirectNotFoundEntryId = rs.getLong(
					"redirectNotFoundEntryId");

				ViewCountManagerUtil.incrementViewCount(
					companyId,
					PortalUtil.getClassNameId(RedirectNotFoundEntry.class),
					redirectNotFoundEntryId, hits);
			}
		}
	}

}