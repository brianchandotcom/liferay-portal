/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Danny Situ
 */
public class PortletPreferenceValueUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("PortletPreferenceValue")) {
			return;
		}

		runSQL(
			StringBundler.concat(
				"update PortletPreferenceValue set smallValue = ",
				"'list-default' where name = 'cpContentListRendererKey' and ",
				"smallValue = 'list-minium'"));

		runSQL(
			StringBundler.concat(
				"update PortletPreferenceValue set smallValue = ",
				"'list-entry-default' where name in(",
				"'grouped--cpTypeListEntryRendererKey', ",
				"'simple--cpTypeListEntryRendererKey', ",
				"'virtual--cpTypeListEntryRendererKey') and smallValue = ",
				"'list-entry-minium'"));

		runSQL(
			StringBundler.concat(
				"update PortletPreferenceValue set smallValue = 'default'",
				"where name in('grouped--cpTypeRendererKey', ",
				"'simple--cpTypeRendererKey', 'virtual--cpTypeRendererKey' )",
				"and smallValue = 'minium'"));

		runSQL(
			"delete from PortletPreferenceValue where smallValue = " +
				"'compare-list-minium'");
	}

}