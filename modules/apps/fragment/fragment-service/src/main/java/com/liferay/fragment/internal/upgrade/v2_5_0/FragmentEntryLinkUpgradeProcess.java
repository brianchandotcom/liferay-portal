/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_5_0;

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
					connection,
					"update FragmentEntryLink set rendererKey = ? where " +
						"fragmentEntryLinkId = ?")) {

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
		alterTableAddColumn("FragmentEntryLink", "plid", "LONG");

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