/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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