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
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;
import java.util.Objects;

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

		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			detailAST, "Component");

		if (annotationDetailAST == null) {
			return;
		}

		String fullyQualifiedExceptionMapperName = getFullyQualifiedTypeName(
			_OSGI_SERVICE_NAME, detailAST, true);

		if (Objects.isNull(fullyQualifiedExceptionMapperName) ||
			!StringUtil.equals(
				fullyQualifiedExceptionMapperName,
				"javax.ws.rs.ext.ExceptionMapper")) {

			return;
		}

		_checkComponentAnnotation(annotationDetailAST);
	}

	private void _checkComponentAnnotation(DetailAST annotationDetailAST) {
		DetailAST annotationMemberValuePairPropertyDetailAST =
			getAnnotationMemberValuePairDetailAST(
				annotationDetailAST, "property");

		if (annotationMemberValuePairPropertyDetailAST == null) {
			return;
		}

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
					expressionKeyValue, "\"osgi.jaxrs.name")) {

				DetailAST annotationMemberValuePairServiceDetailAST =
					getAnnotationMemberValuePairDetailAST(
						annotationDetailAST, "service");

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
							"osgi.jaxrs.name.missed.excepionmaaper",
							serviceIdentName);

						return;
					}
				}
			}
		}
	}

	private static final String _OSGI_SERVICE_NAME = "ExceptionMapper";

}