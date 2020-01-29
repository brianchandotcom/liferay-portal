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

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.query.LimitStep;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Limit extends BaseASTNode implements DefaultDSLQuery {

	public Limit(LimitStep limitStep, int start, int end) {
		super(limitStep);

		_start = start;
		_end = end;
	}

	public int getEnd() {
		return _end;
	}

	public int getStart() {
		return _start;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {
	}

	private final int _end;
	private final int _start;

}