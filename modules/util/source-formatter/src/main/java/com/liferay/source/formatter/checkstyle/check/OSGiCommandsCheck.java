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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		List<String> importNames = getImportNames(detailAST);

		if (!importNames.contains(
				"org.osgi.service.component.annotations.Component")) {

			return;
		}

		_checkClassDeclaration(detailAST);

		if (!importNames.contains(
				"org.osgi.service.component.annotations.Reference")) {

			return;
		}

		for (DetailAST variableDefinitionDetailAST :
				getAllChildTokens(detailAST, true, TokenTypes.VARIABLE_DEF)) {

			_checkVariableDeclaration(variableDefinitionDetailAST);
		}
	}

	private void _checkClassDeclaration(DetailAST detailAST) {
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

		boolean osgiCommandsClass = false;
		Set<String> osgiCommandFunctions = new HashSet<>();

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
				osgiCommandsClass = true;
				osgiCommandFunctions.add(property[1]);
			}
		}

		if (!osgiCommandsClass) {
			return;
		}

		String className = getName(detailAST);

		if (!className.endsWith("OSGiCommands")) {
			log(detailAST, _MSG_BAD_CLASS_NAME);
		}

		for (DetailAST methodDefinitionDetailAST :
				getAllChildTokens(detailAST, true, TokenTypes.METHOD_DEF)) {

			osgiCommandFunctions.remove(getName(methodDefinitionDetailAST));
		}

		for (String osgiCommandFunction : osgiCommandFunctions) {
			log(detailAST, _MSG_MISSING_COMMAND_FUNCTION, osgiCommandFunction);
		}
	}

	private void _checkVariableDeclaration(DetailAST detailAST) {
		if (!AnnotationUtil.containsAnnotation(detailAST, "Reference")) {
			return;
		}

		String typeName = getTypeName(detailAST, false);

		if (typeName.endsWith("OSGiCommands")) {
			log(detailAST, _MSG_BAD_OSGI_REFERENCE);
		}
	}

	private static final String _MSG_BAD_CLASS_NAME = "bad.class.name";

	private static final String _MSG_BAD_OSGI_REFERENCE = "bad.osgi.reference";

	private static final String _MSG_MISSING_COMMAND_FUNCTION =
		"missing.command.function";

}