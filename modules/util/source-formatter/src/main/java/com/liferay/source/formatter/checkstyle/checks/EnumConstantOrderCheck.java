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

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class EnumConstantOrderCheck extends BaseEnumConstantCheck {

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST nextEnumConstantDefinitionDetailAST =
			getNextEnumConstantDefinitionDetailAST(detailAST);

		if (nextEnumConstantDefinitionDetailAST != null) {
			_checkOrder(detailAST, nextEnumConstantDefinitionDetailAST);
		}
	}

	private void _checkOrder(
		DetailAST enumConstantDefinitionDetailAST1,
		DetailAST enumConstantDefinitionDetailAST2) {

		NaturalOrderStringComparator comparator =
			new NaturalOrderStringComparator();

		String name1 = _getName(enumConstantDefinitionDetailAST1);
		String name2 = _getName(enumConstantDefinitionDetailAST2);

		if (comparator.compare(name1, name2) > 0) {
			log(
				enumConstantDefinitionDetailAST1,
				_MSG_ENUM_CONSTANT_ORDER_INCORRECT, name1, name2);
		}
	}

	private String _getName(DetailAST enumConstantDefinitionDetailAST) {
		DetailAST nameDetailAST =
			enumConstantDefinitionDetailAST.findFirstToken(TokenTypes.IDENT);

		return nameDetailAST.getText();
	}

	private static final String _MSG_ENUM_CONSTANT_ORDER_INCORRECT =
		"enum.constant.incorrect.order";

}