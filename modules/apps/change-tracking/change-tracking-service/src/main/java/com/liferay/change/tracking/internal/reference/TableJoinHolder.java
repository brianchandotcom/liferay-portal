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

	public TableJoinHolder(
		Column<?, Long> fromPKColumn, Column<?, Long> joinPKColumn,
		Function<FromStep, JoinStep> joinFunction) {

		this(fromPKColumn, joinPKColumn, joinFunction, false);
	}

	public Column<?, Long> getChildPKColumn() {
		if (_reverse) {
			return _fromPKColumn;
		}

		return _joinPKColumn;
	}

	public Column<?, Long> getFromPKColumn() {
		return _fromPKColumn;
	}

	public Function<FromStep, JoinStep> getJoinFunction() {
		return _joinFunction;
	}

	public Column<?, Long> getJoinPKColumn() {
		return _joinPKColumn;
	}

	public Column<?, Long> getParentPKColumn() {
		if (_reverse) {
			return _joinPKColumn;
		}

		return _fromPKColumn;
	}

	public TableJoinHolder reverse() {
		return new TableJoinHolder(
			_fromPKColumn, _joinPKColumn, _joinFunction, !_reverse);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{fromTablePrimaryKey=", _fromPKColumn, ", joinTablePrimaryKey=",
			_joinPKColumn, ", joinFunction=", _joinFunction, ", reverse=",
			_reverse, "}");
	}

	private TableJoinHolder(
		Column<?, Long> fromPKColumn, Column<?, Long> joinPKColumn,
		Function<FromStep, JoinStep> joinFunction, boolean reverse) {

		_fromPKColumn = fromPKColumn;
		_joinPKColumn = joinPKColumn;
		_joinFunction = joinFunction;
		_reverse = reverse;
	}

	private final Column<?, Long> _fromPKColumn;
	private final Function<FromStep, JoinStep> _joinFunction;
	private final Column<?, Long> _joinPKColumn;
	private final boolean _reverse;

}