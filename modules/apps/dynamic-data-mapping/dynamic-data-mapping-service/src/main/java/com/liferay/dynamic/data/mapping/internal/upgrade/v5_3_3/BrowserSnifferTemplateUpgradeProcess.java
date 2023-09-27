/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_3_3;

import com.liferay.dynamic.data.mapping.internal.upgrade.BaseTemplateUpgradeProcess;
import com.liferay.petra.string.StringPool;

/**
 * @author Albert Gomes Cabral
 * @author Renato Rego
 */
public class BrowserSnifferTemplateUpgradeProcess
	extends BaseTemplateUpgradeProcess {

	@Override
	protected String getContextVariable() {
		return "browserSniffer";
	}

	@Override
	protected String getDeprecatedClass() {
		return "com.liferay.portal.kernel.servlet.BrowserSnifferUtil";
	}

	@Override
	protected String getDeprecatedClassReplacement() {
		return StringPool.BLANK;
	}

}