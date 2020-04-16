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

package com.liferay.change.tracking.internal.reference;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.string.StringBundler;

import java.util.function.Function;

/**
 * @author Preston Crary
 */
public class TableJoinHolder {

	public static TableJoinHolder reverse(TableJoinHolder tableJoinHolder) {
		return new TableJoinHolder(
			tableJoinHolder.getChildPKColumn(),
			tableJoinHolder.getParentPKColumn(),
			tableJoinHolder.getJoinFunction());
	}

	public TableJoinHolder(
		Column<?, Long> parentPKColumn, Column<?, Long> childPKColumn,
		Function<FromStep, JoinStep> joinFunction) {

		_parentPKColumn = parentPKColumn;
		_childPKColumn = childPKColumn;
		_joinFunction = joinFunction;
	}

	public Column<?, Long> getChildPKColumn() {
		return _childPKColumn;
	}

	public Function<FromStep, JoinStep> getJoinFunction() {
		return _joinFunction;
	}

	public Column<?, Long> getParentPKColumn() {
		return _parentPKColumn;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{childPKColumn=", _childPKColumn, ", joinFunction=", _joinFunction,
			", parentPKColumn=", _parentPKColumn, "}");
	}

	private final Column<?, Long> _childPKColumn;
	private final Function<FromStep, JoinStep> _joinFunction;
	private final Column<?, Long> _parentPKColumn;

}