/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.rule;

import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.Objects;

import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.runners.model.Statement;

/**
 * @author Matthew Tambara
 */
public abstract class StatementWrapper extends Statement {

	public StatementWrapper(Statement statement) {
		this.statement = statement;
	}

	public Statement getStatement() {
		return statement;
	}

	public Object inspectTarget(Statement statement) {
		while (statement instanceof StatementWrapper) {
			StatementWrapper statementWrapper = (StatementWrapper)statement;

			statement = statementWrapper.getStatement();
		}

		if (statement instanceof InvokeMethod ||
			statement instanceof RunAfters || statement instanceof RunBefores) {

			return ReflectionTestUtil.getFieldValue(statement, "target");
		}

		Class<?> clazz = statement.getClass();

		if ((statement instanceof ExpectException) ||
			Objects.equals(
				clazz.getName(),
				"org.junit.rules." +
					"ExpectedException$ExpectedExceptionStatement")) {

			return inspectTarget(
				ReflectionTestUtil.<Statement>getFieldValue(statement, "next"));
		}
		else if (statement instanceof FailOnTimeout) {
			return inspectTarget(
				ReflectionTestUtil.<Statement>getFieldValue(
					statement, "originalStatement"));
		}

		throw new IllegalStateException("Unknow statement " + statement);
	}

	protected final Statement statement;

}