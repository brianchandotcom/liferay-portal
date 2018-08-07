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

package com.liferay.source.formatter.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class ContractionsCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.STRING_LITERAL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String s = detailAST.getText();

		for (String contraction : _CONTRACTIONS) {
			if (s.matches("(?i).*\\b" + contraction + "\\b.*")) {
				log(detailAST.getLineNo(), _MSG_AVOID_CONTRACTION, contraction);

				return;
			}
		}
	}

	private static final String[] _CONTRACTIONS = {
		"aren't", "can't", "could've", "couldn't", "didn't", "doesn't", "don't",
		"hadn't", "hasn't", "haven't", "how's", "I'd", "I'll", "I've", "isn't",
		"it's", "let's", "shouldn't", "that's", "there's", "wasn't", "we'd",
		"we'll", "we're", "we've", "weren't", "what's", "where's", "would've",
		"wouldn't", "you'd", "you'll", "you're", "you've"
	};

	private static final String _MSG_AVOID_CONTRACTION = "contraction.avoid";

}