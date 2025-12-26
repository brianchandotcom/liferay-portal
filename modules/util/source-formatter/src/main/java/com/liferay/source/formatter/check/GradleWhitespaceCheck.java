/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class GradleWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		int index = content.indexOf(" (group: \"");

		if (index != -1) {
			return StringUtil.replaceFirst(
				content, StringPool.SPACE, StringPool.BLANK, index);
		}

		return super.doProcess(fileName, absolutePath, content);
	}

}