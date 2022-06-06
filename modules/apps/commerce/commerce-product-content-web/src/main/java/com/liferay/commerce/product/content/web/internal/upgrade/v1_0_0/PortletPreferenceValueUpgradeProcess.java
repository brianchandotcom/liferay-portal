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