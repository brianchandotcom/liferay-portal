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

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Simon Jiang
 */
public class ExceptionMapperAnnotationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		List<DetailAST> detailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.ANNOTATION);

		for (DetailAST curDetailAST : detailASTList) {
			_checkComponentAnnotation(curDetailAST);
		}
	}

	private void _checkComponentAnnotation(DetailAST detailAST) {
		DetailAST annotationDetailAST = detailAST.findFirstToken(
			TokenTypes.IDENT);

		if (!StringUtil.equals(
				annotationDetailAST.getText(), _COMPONENT_ANNOTATION_NAME)) {

			return;
		}

		DetailAST annotationMemberValuePairPropertyDetailAST =
			getAnnotationMemberValuePairDetailAST(
				detailAST, _COMPONENT_ANNOTATION_PROPERTY_KEY_NAME);

		List<DetailAST> propertyAnnotationExprList = getAllChildTokens(
			annotationMemberValuePairPropertyDetailAST, true, TokenTypes.EXPR);

		if (propertyAnnotationExprList == null) {
			return;
		}

		for (DetailAST expressPropertyDetailAST : propertyAnnotationExprList) {
			FullIdent expressionDetailAST = FullIdent.createFullIdentBelow(
				expressPropertyDetailAST);

			String expressionKeyValue = expressionDetailAST.getText();

			if (StringUtil.startsWith(
					expressionKeyValue, _OSGI_JAXRS_NAME_VALUE)) {

				DetailAST annotationMemberValuePairServiceDetailAST =
					getAnnotationMemberValuePairDetailAST(
						detailAST, _COMPONENT_ANNOTATION_SERVICE_KEY_NAME);

				List<DetailAST> serviceAnnotationMemberExprList =
					getAllChildTokens(
						annotationMemberValuePairServiceDetailAST, true,
						TokenTypes.EXPR);

				if ((serviceAnnotationMemberExprList == null) ||
					(serviceAnnotationMemberExprList.size() > 1)) {

					return;
				}

				DetailAST expressSeviceDetailAST =
					serviceAnnotationMemberExprList.get(0);

				List<DetailAST> childIdenTokenDetailASTs = getAllChildTokens(
					expressSeviceDetailAST, true, TokenTypes.IDENT);

				for (DetailAST childIdentDetailAST : childIdenTokenDetailASTs) {
					String serviceIdentName = childIdentDetailAST.getText();

					if (StringUtil.equals(
							serviceIdentName, _OSGI_SERVICE_NAME) &&
						!StringUtil.endsWith(
							expressionKeyValue, serviceIdentName + "\"")) {

						log(
							expressPropertyDetailAST,
							_MSG_OSGI_JAXRS_MAME_MISSED_EXCEPTIONMAPPER,
							serviceIdentName);

						return;
					}
				}
			}
		}
	}

	private static final String _COMPONENT_ANNOTATION_NAME = "Component";

	private static final String _COMPONENT_ANNOTATION_PROPERTY_KEY_NAME =
		"property";

	private static final String _COMPONENT_ANNOTATION_SERVICE_KEY_NAME =
		"service";

	private static final String _MSG_OSGI_JAXRS_MAME_MISSED_EXCEPTIONMAPPER =
		"osgi.jaxrs.name.missed.excepionmaaper";

	private static final String _OSGI_JAXRS_NAME_VALUE = "\"osgi.jaxrs.name";

	private static final String _OSGI_SERVICE_NAME = "ExceptionMapper";

}