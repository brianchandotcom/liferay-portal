/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaTerm;

public class JavaAccessModifierCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(String fileName, String absolutePath,
			JavaTerm javaTerm, String fileContent) throws Exception {

		String content = javaTerm.getContent();

		System.out.println(content);

		return null;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_VARIABLE};
	}

}
