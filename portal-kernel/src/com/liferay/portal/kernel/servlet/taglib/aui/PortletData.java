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
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PortletData implements Serializable {

	public void add(PortletDataPart portletDataPart) {
		_portletDataParts.add(portletDataPart);
	}

	public List<PortletDataPart> getPortletDataParts() {
		return _portletDataParts;
	}

	public <T extends PortletDataPart> boolean hasPortletDataParts(
		Class<T> portletDataPartClass) {

		for (PortletDataPart portletDataPart : _portletDataParts) {
			if (portletDataPartClass.equals(portletDataPart.getClass())) {
				return true;
			}
		}

		return false;
	}

	public void mark() {
		_markIndex = _portletDataParts.size();
	}

	public void reset() {
		if (_markIndex >= 0) {
			_portletDataParts = _portletDataParts.subList(0, _markIndex);
		}
	}

	private static final long serialVersionUID = 1L;

	private int _markIndex = -1;
	private List<PortletDataPart> _portletDataParts = new ArrayList<>();

}