/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;

/**
 * @author Alan Huang
 */
public class ConsumerTypeAnnotationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<String> importNames = getImportNames(detailAST);

		if (importNames.contains(
				"org.osgi.annotation.versioning.ConsumerType") &&
			AnnotationUtil.containsAnnotation(detailAST, "ConsumerType")) {

			log(detailAST, _MSG_REDUNDANT_DEFAULT_ANNOTATION);
		}
	}

	private static final String _MSG_REDUNDANT_DEFAULT_ANNOTATION =
		"default.annotation.redundant";

}