/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

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

		String name1 = getName(enumConstantDefinitionDetailAST1);
		String name2 = getName(enumConstantDefinitionDetailAST2);

		if (comparator.compare(name1, name2) > 0) {
			log(
				enumConstantDefinitionDetailAST1,
				_MSG_ENUM_CONSTANT_ORDER_INCORRECT, name1, name2);
		}
	}

	private static final String _MSG_ENUM_CONSTANT_ORDER_INCORRECT =
		"enum.constant.incorrect.order";

}