/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.util;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;

/**
 * @author Kevin Lee
 */
public class GradleBuildFileVisitor extends CodeVisitorSupport {

	public List<GradleDependency> getBuildScriptDependencies() {
		return _buildScriptDependencies;
	}

	public int getDependenciesLastLineNumber() {
		return _dependenciesLastLineNumber;
	}

	public int getDependenciesLineNumber() {
		return _dependenciesLineNumber;
	}

	public List<GradleDependency> getGradleDependencies() {
		return _gradleDependencies;
	}

	@Override
	public void visitArgumentlistExpression(
		ArgumentListExpression argumentListExpression) {

		if (!_inDependencies && !_inBuildScript) {
			return;
		}

		List<Expression> expressions = argumentListExpression.getExpressions();

		if ((expressions.size() == 1) &&
			(expressions.get(0) instanceof ConstantExpression)) {

			ConstantExpression constantExpression =
				(ConstantExpression)expressions.get(0);

			String text = constantExpression.getText();

			String[] textParts = text.split(":");

			if ((textParts.length >= 3) && _inDependencies) {
				GradleDependency gradleDependency = new GradleDependency(
					_configuration, textParts[0], textParts[1], textParts[2],
					_methodCallLineNumber, _methodCallLastLineNumber);

				if (_inBuildScript) {
					_buildScriptDependencies.add(gradleDependency);
				}
				else {
					_gradleDependencies.add(gradleDependency);
				}
			}
		}

		super.visitArgumentlistExpression(argumentListExpression);
	}

	@Override
	public void visitBlockStatement(BlockStatement blockStatement) {
		if (_inDependencies) {
			_blockStatementStack.push(true);

			super.visitBlockStatement(blockStatement);

			_blockStatementStack.pop();
		}
		else {
			super.visitBlockStatement(blockStatement);
		}
	}

	@Override
	public void visitMapExpression(MapExpression mapExpression) {
		Map<String, String> keyValues = new HashMap<>();

		boolean gav = false;

		for (MapEntryExpression mapEntryExpression :
				mapExpression.getMapEntryExpressions()) {

			Expression keyExpression = mapEntryExpression.getKeyExpression();

			String key = keyExpression.getText();

			Expression valueExpression =
				mapEntryExpression.getValueExpression();

			String value = valueExpression.getText();

			if (StringUtil.equalsIgnoreCase(key, "group")) {
				gav = true;
			}

			keyValues.put(key, value);
		}

		if (gav && _inDependencies) {
			GradleDependency gradleDependency = new GradleDependency(
				_configuration, keyValues.get("group"), keyValues.get("name"),
				keyValues.get("version"), _methodCallLineNumber,
				_methodCallLastLineNumber);

			if (_inBuildScript) {
				_buildScriptDependencies.add(gradleDependency);
			}
			else {
				_gradleDependencies.add(gradleDependency);
			}
		}

		super.visitMapExpression(mapExpression);
	}

	@Override
	public void visitMethodCallExpression(
		MethodCallExpression methodCallExpression) {

		_methodCallLineNumber = methodCallExpression.getLineNumber();
		_methodCallLastLineNumber = methodCallExpression.getLastLineNumber();

		if (_methodCallLineNumber > _buildScriptLastLineNumber) {
			_inBuildScript = false;
		}

		if (_methodCallLineNumber > _dependenciesLastLineNumber) {
			_inDependencies = false;
		}

		String methodName = methodCallExpression.getMethodAsString();

		if (methodName.equals("buildscript")) {
			_inBuildScript = true;
			_buildScriptLastLineNumber =
				methodCallExpression.getLastLineNumber();
		}

		if (methodName.equals("dependencies")) {
			_inDependencies = true;
			_dependenciesLineNumber = methodCallExpression.getLineNumber();
			_dependenciesLastLineNumber =
				methodCallExpression.getLastLineNumber();
		}

		if (_inDependencies &&
			(_blockStatementStack.isEmpty() ? false :
				_blockStatementStack.peek())) {

			_configuration = methodName;
		}

		super.visitMethodCallExpression(methodCallExpression);
	}

	private final Stack<Boolean> _blockStatementStack = new Stack<>();
	private final List<GradleDependency> _buildScriptDependencies =
		new ArrayList<>();
	private int _buildScriptLastLineNumber = -1;
	private String _configuration;
	private int _dependenciesLastLineNumber = -1;
	private int _dependenciesLineNumber = -1;
	private final List<GradleDependency> _gradleDependencies =
		new ArrayList<>();
	private boolean _inBuildScript;
	private boolean _inDependencies;
	private int _methodCallLastLineNumber = -1;
	private int _methodCallLineNumber = -1;

}