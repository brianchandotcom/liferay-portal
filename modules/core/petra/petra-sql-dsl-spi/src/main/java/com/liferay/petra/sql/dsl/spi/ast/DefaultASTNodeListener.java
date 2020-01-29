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

package com.liferay.petra.sql.dsl.spi.ast;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.ast.ASTNode;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.sql.dsl.spi.expression.ScalarList;
import com.liferay.petra.sql.dsl.spi.query.Limit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class DefaultASTNodeListener implements ASTNodeListener {

	public int getEnd() {
		return _end;
	}

	public List<Object> getScalarValues() {
		return _scalarValues;
	}

	public int getStart() {
		return _start;
	}

	public String[] getTableNames() {
		return _tableNames.toArray(new String[0]);
	}

	@Override
	public void process(ASTNode astNode) {
		if (astNode instanceof Limit) {
			Limit limit = (Limit)astNode;

			_start = limit.getStart();
			_end = limit.getEnd();
		}
		else if (astNode instanceof Scalar) {
			Scalar<?> scalar = (Scalar)astNode;

			_scalarValues.add(scalar.getValue());
		}
		else if (astNode instanceof ScalarList) {
			ScalarList<?> scalarList = (ScalarList)astNode;

			Collections.addAll(_scalarValues, scalarList.getValues());
		}
		else if (astNode instanceof Table<?>) {
			Table<? extends Table<?>> table = (Table<?>)astNode;

			String tableName = table.getTableName();

			if (tableName != null) {
				_tableNames.add(tableName);
			}
		}
	}

	private int _end = -1;
	private final List<Object> _scalarValues = new ArrayList<>();
	private int _start = -1;
	private final Set<String> _tableNames = new LinkedHashSet<>();

}