/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringUtil;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeSCSSImportsCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		return StringUtil.replace(
			content, "compat/mixins", "clay/cadmin-variables");
	}

	@Override
	protected String[] getValidExtensions() {
		return new String[] {"scss"};
	}

}