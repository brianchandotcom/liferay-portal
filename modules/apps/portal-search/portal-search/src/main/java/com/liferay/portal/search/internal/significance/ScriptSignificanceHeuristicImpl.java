/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.significance;

import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.significance.ScriptSignificanceHeuristic;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class ScriptSignificanceHeuristicImpl
	implements ScriptSignificanceHeuristic {

	public ScriptSignificanceHeuristicImpl(Script script) {
		_script = script;
	}

	@Override
	public Script getScript() {
		return _script;
	}

	private final Script _script;

}