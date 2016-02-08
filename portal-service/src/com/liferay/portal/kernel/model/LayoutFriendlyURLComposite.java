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

package com.liferay.portal.kernel.model;

/**
 * @author Sergio González
 */
public class LayoutFriendlyURLComposite {

	public LayoutFriendlyURLComposite(Layout layout, String friendlyURL) {
		_layout = layout;
		_friendlyURL = friendlyURL;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public Layout getLayout() {
		return _layout;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	private String _friendlyURL;
	private Layout _layout;

}