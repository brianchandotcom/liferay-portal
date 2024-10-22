/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.upgrade.v2_0_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Petteri Karttunen
 */
public class SearchPortletUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_deleteDateFacetPortletData();
	}

	private void _deleteDateFacetPortletData() throws Exception {
		String dateFacetPortletKey =
			"com_liferay_portal_search_web_date_facet_portlet_DateFacetPortlet";

		runSQL(
			"delete from PortletPreferences where portletId like '" +
				dateFacetPortletKey + "%'");
		runSQL(
			"delete from ResourceAction where name = '" + dateFacetPortletKey +
				"'");
		runSQL(
			"delete from ResourcePermission where name = '" +
				dateFacetPortletKey + "'");
	}

}