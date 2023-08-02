/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_3_2;

import com.liferay.dynamic.data.mapping.internal.upgrade.BaseTemplateUpgradeProcess;
import com.liferay.petra.string.StringPool;

import java.util.regex.Pattern;

/**
 * @author Albert Gomes Cabral
 */
public class BrowserSnifferTemplateUpgradeProcess
	extends BaseTemplateUpgradeProcess {

	@Override
	protected String getTemplateContextVariable() {
		return "browserSniffer";
	}

	@Override
	protected Pattern getTemplatePattern() throws Exception {
		return Pattern.compile(
			"\\w+\\s*\\=\\s*.+com\\.liferay\\.portal\\.kernel\\.servlet\\." +
				"BrowserSnifferUtil\\\"\\)");
	}

	@Override
	protected String getTemplatePatternReplacement() throws Exception {
		return StringPool.BLANK;
	}

}