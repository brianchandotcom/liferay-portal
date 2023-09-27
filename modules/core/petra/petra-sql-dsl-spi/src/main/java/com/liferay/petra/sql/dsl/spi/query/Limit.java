/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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