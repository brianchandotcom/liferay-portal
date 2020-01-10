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

package com.liferay.portal.kernel.dao.model.dsl.ast.impl;

import com.liferay.portal.kernel.dao.model.Table;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNode;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Scalar;
import com.liferay.portal.kernel.dao.model.dsl.expressions.ScalarList;
import com.liferay.portal.kernel.dao.model.dsl.query.Limit;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.ArrayList;
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

			Object value = scalar.getValue();

			if (Scalar.isScalarValue(value)) {
				_scalarValues.add(value);
			}
		}
		else if (astNode instanceof ScalarList) {
			ScalarList<?> scalarList = (ScalarList)astNode;

			for (Object value : scalarList.getValues()) {
				if (Scalar.isScalarValue(value)) {
					_scalarValues.add(value);
				}
			}
		}
		else if (astNode instanceof Table) {
			Table<?> table = (Table)astNode;

			_tableNames.add(table.getTableName());
		}
	}

	private int _end = QueryUtil.ALL_POS;
	private final List<Object> _scalarValues = new ArrayList<>();
	private int _start = QueryUtil.ALL_POS;
	private final Set<String> _tableNames = new LinkedHashSet<>();

}