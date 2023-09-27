/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Kevin Lee
 */
public class ResourcePermissionFactoryCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (!absolutePath.contains("/modules/")) {
			return;
		}

		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotDetailAST == null) {
			return;
		}

		List<String> names = getNames(dotDetailAST, false);

		if (names.size() != 2) {
			return;
		}

		String className = names.get(0);
		String methodName = names.get(1);

		if ((className.equals("ModelResourcePermissionFactory") ||
			 className.equals("PortletResourcePermissionFactory")) &&
			methodName.equals("getInstance")) {

			log(detailAST, _MSG_REPLACE_GET_INSTANCE_USAGE, className);
		}
	}

	private static final String _MSG_REPLACE_GET_INSTANCE_USAGE =
		"deprecated.get.instance.usage";

}