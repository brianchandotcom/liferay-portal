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

import com.liferay.portal.kernel.util.Validator;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author Simon Jiang
 */
public class ComponentAnnotationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<String> importNames = getImportNames(detailAST);

		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST != null) ||
			!importNames.contains(
				"org.osgi.service.component.annotations.Component")) {

			return;
		}

		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			detailAST, "Component");

		if (annotationDetailAST == null) {
			return;
		}

		_checkConfigurationPolicy(detailAST, annotationDetailAST);
		_checkOSGiJaxrsName(annotationDetailAST, importNames);
	}

	private void _checkConfigurationPolicy(
		DetailAST detailAST, DetailAST annotationDetailAST) {

		DetailAST extendsClauseDetailAST = detailAST.findFirstToken(
			TokenTypes.EXTENDS_CLAUSE);

		if (extendsClauseDetailAST == null) {
			return;
		}

		String extendsClassName = getName(extendsClauseDetailAST);

		if (!extendsClassName.equals("BaseAuthVerifierPipelineConfigurator")) {
			return;
		}

		DetailAST annotationMemberValuePairDetailAST =
			getAnnotationMemberValuePairDetailAST(
				annotationDetailAST, "configurationPolicy");

		if (annotationMemberValuePairDetailAST != null) {
			DetailAST expressionDetailAST =
				annotationMemberValuePairDetailAST.findFirstToken(
					TokenTypes.EXPR);

			FullIdent expressionIdent = FullIdent.createFullIdentBelow(
				expressionDetailAST);

			String annotationMemberValue = expressionIdent.getText();

			if (annotationMemberValue.equals("ConfigurationPolicy.REQUIRE")) {
				return;
			}
		}

		log(annotationDetailAST, _MSG_INCORRECT_CONFIGURATION_POLICY);
	}

	private void _checkOSGiJaxrsName(
		DetailAST annotationDetailAST, List<String> importNames) {

		if (!importNames.contains("javax.ws.rs.ext.ExceptionMapper") ||
			!_isExceptionMapperService(annotationDetailAST)) {

			return;
		}

		DetailAST propertyAnnotationMemberValuePairDetailAST =
			getAnnotationMemberValuePairDetailAST(
				annotationDetailAST, "property");

		if (propertyAnnotationMemberValuePairDetailAST == null) {
			return;
		}

		DetailAST annotationArrayInitDetailAST =
			propertyAnnotationMemberValuePairDetailAST.findFirstToken(
				TokenTypes.ANNOTATION_ARRAY_INIT);

		if (annotationArrayInitDetailAST == null) {
			return;
		}

		String osgiJaxrsName = _getOSGiJaxrsName(annotationArrayInitDetailAST);

		if (Validator.isNull(osgiJaxrsName)) {
			return;
		}

		if (!osgiJaxrsName.endsWith(_OSGI_SERVICE_NAME)) {
			log(
				annotationArrayInitDetailAST, _MSG_INCORRECT_OSGI_JAXRS_MAME,
				_OSGI_SERVICE_NAME);
		}
	}

	private String _getOSGiJaxrsName(DetailAST annotationArrayInitDetailAST) {
		List<DetailAST> expressionDetailASTList = getAllChildTokens(
			annotationArrayInitDetailAST, false, TokenTypes.EXPR);

		for (DetailAST expressionDetailAST : expressionDetailASTList) {
			DetailAST firstChildDetailAST = expressionDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.STRING_LITERAL) {
				continue;
			}

			String value = firstChildDetailAST.getText();

			if (value.startsWith("\"osgi.jaxrs.name=")) {
				return value.substring(17, value.length() - 1);
			}
		}

		return null;
	}

	private boolean _isExceptionMapperService(DetailAST annotationDetailAST) {
		DetailAST serviceAnnotationMemberValuePairDetailAST =
			getAnnotationMemberValuePairDetailAST(
				annotationDetailAST, "service");

		if (serviceAnnotationMemberValuePairDetailAST == null) {
			return false;
		}

		DetailAST exprDetailAST =
			serviceAnnotationMemberValuePairDetailAST.findFirstToken(
				TokenTypes.EXPR);

		if (exprDetailAST == null) {
			return false;
		}

		DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.DOT)) {

			return false;
		}

		FullIdent fullIdent = FullIdent.createFullIdent(firstChildDetailAST);

		if (!Objects.equals(
				fullIdent.getText(), _OSGI_SERVICE_NAME + ".class")) {

			return false;
		}

		return true;
	}

	private static final String _MSG_INCORRECT_CONFIGURATION_POLICY =
		"configuration.policy.incorrect";

	private static final String _MSG_INCORRECT_OSGI_JAXRS_MAME =
		"osgi.jaxrs.name.incorrect";

	private static final String _OSGI_SERVICE_NAME = "ExceptionMapper";

}