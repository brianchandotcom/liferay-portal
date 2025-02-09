/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Lee
 */
public class OSGiCommandsCheck extends BaseCheck {

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

		DetailAST implementsClauseDetailAST = detailAST.findFirstToken(
			TokenTypes.IMPLEMENTS_CLAUSE);

		if (implementsClauseDetailAST == null) {
			return;
		}

		List<String> implementedClassNames = getNames(
			implementsClauseDetailAST, false);

		if (!implementedClassNames.contains("OSGiCommands")) {
			return;
		}

		DetailAST objBlockDetailAST = detailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		if (objBlockDetailAST == null) {
			return;
		}

		List<String> importNames = getImportNames(detailAST);

		if (importNames.contains(
				"org.osgi.service.component.annotations.Component")) {

			List<DetailAST> methodDefinitionDetailASTList = getAllChildTokens(
				objBlockDetailAST, false, TokenTypes.METHOD_DEF);

			_checkMissingUnimplementedMethod(
				detailAST, methodDefinitionDetailASTList);
		}

		if (importNames.contains(
				"org.osgi.service.component.annotations.Reference")) {

			List<DetailAST> variableDefinitionDetailASTList = getAllChildTokens(
				objBlockDetailAST, false, TokenTypes.VARIABLE_DEF);

			_checkVariableDeclaration(variableDefinitionDetailASTList);
		}
	}

	private void _checkMissingUnimplementedMethod(
		DetailAST detailAST, List<DetailAST> methodDefinitionDetailASTList) {

		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			detailAST, "Component");

		if (annotationDetailAST == null) {
			return;
		}

		DetailAST annotationMemberValuePairDetailAST =
			getAnnotationMemberValuePairDetailAST(
				annotationDetailAST, "property");

		if (annotationMemberValuePairDetailAST == null) {
			return;
		}

		DetailAST annotationArrayInitDetailAST =
			annotationMemberValuePairDetailAST.findFirstToken(
				TokenTypes.ANNOTATION_ARRAY_INIT);

		if (annotationArrayInitDetailAST == null) {
			return;
		}

		List<String> osgiCommandFunctions = _getOSGiCommandFunctions(
			annotationArrayInitDetailAST);

		if (osgiCommandFunctions.isEmpty()) {
			return;
		}

		for (DetailAST methodDefinitionDetailAST :
				methodDefinitionDetailASTList) {

			osgiCommandFunctions.remove(getName(methodDefinitionDetailAST));
		}

		for (String osgiCommandFunction : osgiCommandFunctions) {
			log(detailAST, _MSG_COMMAND_FUNCTION_MISSING, osgiCommandFunction);
		}
	}

	private void _checkVariableDeclaration(
		List<DetailAST> variableDefinitionDetailASTList) {

		for (DetailAST variableDefinitionDetailAST :
				variableDefinitionDetailASTList) {

			if (!AnnotationUtil.containsAnnotation(
					variableDefinitionDetailAST, "Reference")) {

				continue;
			}

			String typeName = getTypeName(variableDefinitionDetailAST, false);

			if (typeName.endsWith("OSGiCommands")) {
				log(variableDefinitionDetailAST, _MSG_OSGI_REFERENCE_AVOID);
			}
		}
	}

	private List<String> _getOSGiCommandFunctions(
		DetailAST annotationArrayInitDetailAST) {

		List<String> osgiCommandFunctions = new ArrayList<>();

		for (DetailAST expressionDetailAST :
				getAllChildTokens(
					annotationArrayInitDetailAST, false, TokenTypes.EXPR)) {

			DetailAST firstChildDetailAST = expressionDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.STRING_LITERAL) {
				continue;
			}

			String[] property = StringUtil.split(
				StringUtil.unquote(firstChildDetailAST.getText()),
				CharPool.EQUAL);

			if (property[0].equals("osgi.command.function")) {
				osgiCommandFunctions.add(property[1]);
			}
		}

		return osgiCommandFunctions;
	}

	private static final String _MSG_COMMAND_FUNCTION_MISSING =
		"command.function.missing";

	private static final String _MSG_OSGI_REFERENCE_AVOID =
		"osgi.reference.avoid";

}