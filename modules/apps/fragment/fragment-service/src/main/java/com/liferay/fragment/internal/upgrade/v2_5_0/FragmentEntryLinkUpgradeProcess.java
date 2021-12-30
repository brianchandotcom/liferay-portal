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

package com.liferay.fragment.internal.upgrade.v2_5_0;

import com.liferay.fragment.internal.upgrade.v2_5_0.util.FragmentEntryLinkTable;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeRendererKey();
		_upgratePlid();
	}

	private void _upgradeRendererKey() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fragmentEntryLinkId, rendererKey from " +
					"FragmentEntryLink where rendererKey like " +
						"'BASIC_SECTION%'");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update FragmentEntryLink set rendererKey = ? where " +
							"fragmentEntryLinkId = ?"))) {

			while (resultSet.next()) {
				long fragmentEntryLinkId = resultSet.getLong(
					"fragmentEntryLinkId");

				String rendererKey = resultSet.getString("rendererKey");

				preparedStatement2.setString(
					1,
					_contributedFragmentKeys.getOrDefault(
						rendererKey, rendererKey));

				preparedStatement2.setLong(2, fragmentEntryLinkId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private void _upgratePlid() throws Exception {
		if (!hasColumn("FragmentEntryLink", "plid")) {
			alter(
				FragmentEntryLinkTable.class,
				new AlterTableAddColumn("plid", "LONG"));
		}

		runSQL(
			"update FragmentEntryLink set plid = classPK where classNameId = " +
				PortalUtil.getClassNameId(Layout.class.getName()));
	}

	private static final Map<String, String> _contributedFragmentKeys =
		HashMapBuilder.put(
			"BASIC_SECTION-banner", "FEATURED_CONTENT-banner"
		).put(
			"BASIC_SECTION-banner-center", "FEATURED_CONTENT-banner-center"
		).put(
			"BASIC_SECTION-banner-cover", "FEATURED_CONTENT-banner-cover"
		).put(
			"BASIC_SECTION-banner-cover-center",
			"FEATURED_CONTENT-banner-cover-center"
		).put(
			"BASIC_SECTION-features", "FEATURED_CONTENT-features"
		).put(
			"BASIC_SECTION-footer-nav-dark", "FOOTERS-footer-nav-dark"
		).put(
			"BASIC_SECTION-footer-nav-light", "FOOTERS-footer-nav-dark"
		).put(
			"BASIC_SECTION-header-dark", "NAVIGATION_BARS-header-dark"
		).put(
			"BASIC_SECTION-header-light", "NAVIGATION_BARS-header-light"
		).put(
			"BASIC_SECTION-highlights", "FEATURED_CONTENT-highlights"
		).put(
			"BASIC_SECTION-highlights-circle",
			"FEATURED_CONTENT-highlights-circle"
		).build();

}