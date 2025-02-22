/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Alan Huang
 */
public class RecordClassCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.RECORD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		log(detailAST, _MSG_AVOID_RECORD_CLASS);
	}

	private static final String _MSG_AVOID_RECORD_CLASS = "record.class.avoid";

}