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

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class ConstantNameCheck
	extends com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck {

	public static final String MSG_INVALID_PRIVATE_NAME =
		"name.invalidPrivatePattern";

	public static final String MSG_INVALID_PROTECTED_PUBLIC_NAME =
		"name.invalidProtectedPublicPattern";

	public static final String MSG_PRIVATE_COLLECTION =
		"name.collectionPrivatePattern";

	public static final String MSG_PROTECTED_PUBLIC_COLLECTION =
		"name.collectionProtectedPublicPattern";

	@Override
	public void visitToken(DetailAST detailAST) {
		if (!mustCheckName(detailAST)) {
			return;
		}

		String message = null;
		String regex = null;

		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (modifiersAST.branchContains(TokenTypes.LITERAL_PRIVATE)) {
			if (DetailASTUtil.isCollection(detailAST)) {
				message = MSG_PRIVATE_COLLECTION;
				regex = "^_[a-z0-9][_a-zA-Z0-9]*$";
			}
			else {
				message = MSG_INVALID_PRIVATE_NAME;
				regex = "^_[_a-zA-Z0-9]*$";
			}
		}
		else if (modifiersAST.branchContains(TokenTypes.LITERAL_PROTECTED) ||
				 modifiersAST.branchContains(TokenTypes.LITERAL_PUBLIC)) {

			if (DetailASTUtil.isCollection(detailAST)) {
				message = MSG_PROTECTED_PUBLIC_COLLECTION;
				regex = "^[a-z0-9][_a-zA-Z0-9]*$";
			}
			else {
				message = MSG_INVALID_PROTECTED_PUBLIC_NAME;
				regex = "^[a-zA-Z0-9][_a-zA-Z0-9]*$";
			}
		}
		else {
			return;
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(nameAST.getText());

		if (!matcher.find()) {
			log(nameAST.getLineNo(), message, nameAST.getText(), regex);
		}
	}

}