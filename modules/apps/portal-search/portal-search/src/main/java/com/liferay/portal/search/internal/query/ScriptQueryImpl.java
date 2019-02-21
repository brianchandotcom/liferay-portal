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

package com.liferay.portal.search.internal.query;

import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.ScriptQuery;
import com.liferay.portal.search.script.Script;

/**
 * @author Michael C. Han
 */
public class ScriptQueryImpl extends BaseQueryImpl implements ScriptQuery {

	public ScriptQueryImpl(Script script) {
		_script = script;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public Script getScript() {
		return _script;
	}

	private static final long serialVersionUID = 1L;

	private final Script _script;

}