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

package com.liferay.source.formatter.upgrade;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<GradleDependency> getDependencies() {
		return _dependencies;
	}

	public int getDependenciesLastLineNumber() {
		return _dependenciesLastLineNumber;
	}

	public int getDependenciesLineNumber() {
		return _dependenciesLineNumber;
	}

	@Override
	public void visitArgumentlistExpression(
		ArgumentListExpression argumentListExpression) {

		if (!_inDependencies) {
			return;
		}

		List<Expression> expressions = argumentListExpression.getExpressions();

		if ((expressions.size() == 1) &&
			(expressions.get(0) instanceof ConstantExpression)) {

			ConstantExpression constantExpression =
				(ConstantExpression)expressions.get(0);

			String text = constantExpression.getText();

			String[] parts = text.split(":");

			if (parts.length >= 3) {
				GradleDependency dependency = new GradleDependency(
					_configuration, parts[0], parts[1], parts[2],
					_methodCallLineNumber, _methodCallLastLineNumber);

				_dependencies.add(dependency);
			}
		}

		super.visitArgumentlistExpression(argumentListExpression);
	}

	@Override
	public void visitBlockStatement(BlockStatement blockStatement) {
		if (_inDependencies) {
			_numberOfBlocks++;

			super.visitBlockStatement(blockStatement);

			_numberOfBlocks--;
		}
		else {
			super.visitBlockStatement(blockStatement);
		}
	}

	@Override
	public void visitMapExpression(MapExpression mapExpression) {
		if (!_inDependencies) {
			return;
		}

		Map<String, String> keyValues = new HashMap<>();

		boolean gav = false;

		for (MapEntryExpression mapEntryExpression :
				mapExpression.getMapEntryExpressions()) {

			Expression keyExpression = mapEntryExpression.getKeyExpression();
			Expression valueExpression =
				mapEntryExpression.getValueExpression();

			String key = keyExpression.getText();
			String value = valueExpression.getText();

			if (StringUtil.equalsIgnoreCase(key, "group")) {
				gav = true;
			}

			keyValues.put(key, value);
		}

		if (gav) {
			GradleDependency dependency = new GradleDependency(
				_configuration, keyValues.get("group"), keyValues.get("name"),
				keyValues.get("version"), _methodCallLineNumber,
				_methodCallLastLineNumber);

			_dependencies.add(dependency);
		}

		super.visitMapExpression(mapExpression);
	}

	@Override
	public void visitMethodCallExpression(
		MethodCallExpression methodCallExpression) {

		_methodCallLineNumber = methodCallExpression.getLineNumber();
		_methodCallLastLineNumber = methodCallExpression.getLastLineNumber();

		if (_methodCallLineNumber > _dependenciesLastLineNumber) {
			_inDependencies = false;
		}

		String methodName = methodCallExpression.getMethodAsString();

		if (methodName.equals("dependencies")) {
			_inDependencies = true;
			_dependenciesLineNumber = methodCallExpression.getLineNumber();
			_dependenciesLastLineNumber =
				methodCallExpression.getLastLineNumber();
		}

		if (_inDependencies && (_numberOfBlocks > 0)) {
			if (_numberOfBlocks > 1) {

				// Assume all dependencies are initialized within the first
				// level of "dependencies" block

				return;
			}

			_configuration = methodName;

			super.visitMethodCallExpression(methodCallExpression);

			_configuration = null;
		}
		else {
			super.visitMethodCallExpression(methodCallExpression);
		}
	}

	private String _configuration;
	private final List<GradleDependency> _dependencies = new ArrayList<>();
	private int _dependenciesLastLineNumber = -1;
	private int _dependenciesLineNumber = -1;
	private boolean _inDependencies;
	private int _methodCallLastLineNumber = -1;
	private int _methodCallLineNumber = -1;
	private int _numberOfBlocks;

}