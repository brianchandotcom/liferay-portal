/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.significance;

import com.liferay.portal.search.significance.GNDSignificanceHeuristic;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class GNDSignificanceHeuristicImpl implements GNDSignificanceHeuristic {

	public GNDSignificanceHeuristicImpl(boolean backgroundIsSuperset) {
		_backgroundIsSuperset = backgroundIsSuperset;
	}

	@Override
	public boolean isBackgroundIsSuperset() {
		return _backgroundIsSuperset;
	}

	private final boolean _backgroundIsSuperset;

}