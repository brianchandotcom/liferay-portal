/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class JavaDeprecatedKernelClassesCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return StringUtil.replace(
			content,
			new String[] {
				"com.liferay.portal.kernel.util.CharPool",
				"com.liferay.portal.kernel.util.StringPool"
			},
			new String[] {
				"com.liferay.petra.string.CharPool",
				"com.liferay.petra.string.StringPool"
			});
	}

}