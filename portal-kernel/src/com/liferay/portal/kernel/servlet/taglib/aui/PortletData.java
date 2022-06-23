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

package com.liferay.portal.kernel.servlet.taglib.aui;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class PortletData implements Serializable {

	public void add(JSFragment jsFragment) {
		_jsFragments.add(jsFragment);
	}

	public Collection<JSFragment> getJSFragments() {
		return _jsFragments;
	}

	public void mark() {
		_lastMarkIndex = _jsFragments.size();
	}

	public void reset() {
		_jsFragments = new ArrayList<>(_jsFragments.subList(0, _lastMarkIndex));
	}

	private static final long serialVersionUID = 1L;

	private List<JSFragment> _jsFragments = new ArrayList<>();
	private int _lastMarkIndex;

}