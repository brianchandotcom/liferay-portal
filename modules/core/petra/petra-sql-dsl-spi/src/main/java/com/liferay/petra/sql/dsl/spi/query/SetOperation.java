/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class SetOperation extends BaseASTNode implements DefaultDSLQuery {

	public SetOperation(
		DSLQuery leftDSLQuery, SetOperationType setOperationType,
		DSLQuery rightDSLQuery) {

		_leftDSLQuery = Objects.requireNonNull(leftDSLQuery);
		_setOperationType = Objects.requireNonNull(setOperationType);
		_rightDSLQuery = Objects.requireNonNull(rightDSLQuery);
	}

	public DSLQuery getLeftDSLQuery() {
		return _leftDSLQuery;
	}

	public DSLQuery getRightDSLQuery() {
		return _rightDSLQuery;
	}

	public SetOperationType getSetOperationType() {
		return _setOperationType;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		_leftDSLQuery.toSQL(consumer, astNodeListener);

		consumer.accept(_setOperationType.getStringWithSpaces());

		_rightDSLQuery.toSQL(consumer, astNodeListener);
	}

	private final DSLQuery _leftDSLQuery;
	private final DSLQuery _rightDSLQuery;
	private final SetOperationType _setOperationType;

}