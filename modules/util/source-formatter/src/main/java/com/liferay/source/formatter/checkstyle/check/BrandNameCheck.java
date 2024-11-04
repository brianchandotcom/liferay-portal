/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * @author Alan Huang
 */
public class BrandNameCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF,
			TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF,
			TokenTypes.RESOURCE, TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if ((detailAST.getType() == TokenTypes.METHOD_DEF) &&
			AnnotationUtil.containsAnnotation(detailAST, "Override")) {

			return;
		}

		String name = getName(detailAST);
		String tokenTypeName = getTokenTypeName(detailAST);

		for (String brandName : _BRAND_NAMES) {
			String lowerCaseBrandName = StringUtil.toLowerCase(brandName);

			if ((detailAST.getType() == TokenTypes.PARAMETER_DEF) ||
				(detailAST.getType() == TokenTypes.RESOURCE) ||
				(detailAST.getType() == TokenTypes.VARIABLE_DEF)) {

				String lowerCaseTypeName = StringUtil.toLowerCase(
					getTypeName(detailAST, false));

				if (lowerCaseTypeName.contains(lowerCaseBrandName)) {
					continue;
				}
			}

			if (name.startsWith(lowerCaseBrandName)) {
				String newName =
					StringUtil.lowerCaseFirstLetter(brandName) +
						name.substring(lowerCaseBrandName.length());

				log(detailAST, _MSG_RENAME, tokenTypeName, name, newName);

				return;
			}

			String upperCaseBrandName = StringUtil.upperCaseFirstLetter(
				lowerCaseBrandName);

			int x = name.indexOf(upperCaseBrandName);

			if (x != -1) {
				String newName =
					name.substring(0, x) + brandName +
						name.substring(x + brandName.length());

				log(detailAST, _MSG_RENAME, tokenTypeName, name, newName);

				return;
			}
		}
	}

	private static final String[] _BRAND_NAMES = {"FreeMarker", "JavaScript"};

	private static final String _MSG_RENAME = "rename";

}