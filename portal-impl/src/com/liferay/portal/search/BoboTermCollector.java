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

package com.liferay.portal.search;

import com.browseengine.bobo.api.BrowseFacet;

import com.liferay.portal.kernel.search.facet.collector.TermCollector;

/**
 * @author Raymond Augé
 */
public class BoboTermCollector implements TermCollector {

	public BoboTermCollector(BrowseFacet browseFacet) {
		_browseFacet = browseFacet;
	}

	@Override
	public int getFrequency() {
		return _browseFacet.getFacetValueHitCount();
	}

	@Override
	public String getTerm() {
		return _browseFacet.getValue();
	}

	private final BrowseFacet _browseFacet;

}