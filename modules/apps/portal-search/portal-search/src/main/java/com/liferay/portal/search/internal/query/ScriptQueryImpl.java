/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public Script getScript() {
		return _script;
	}

	private static final long serialVersionUID = 1L;

	private final Script _script;

}