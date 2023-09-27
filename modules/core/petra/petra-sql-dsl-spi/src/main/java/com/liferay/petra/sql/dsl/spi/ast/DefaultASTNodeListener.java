/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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