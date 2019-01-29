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

package com.liferay.portal.search.significance;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class NxySignificanceHeuristic
	implements SignificanceHeuristic {

	public NxySignificanceHeuristic(
		boolean backgroundIsSuperset, boolean includeNegatives) {

		_backgroundIsSuperset = backgroundIsSuperset;
		_includeNegatives = includeNegatives;
	}

	public boolean isBackgroundIsSuperset() {
		return _backgroundIsSuperset;
	}

	public boolean isIncludeNegatives() {
		return _includeNegatives;
	}

	private final boolean _backgroundIsSuperset;
	private final boolean _includeNegatives;

}