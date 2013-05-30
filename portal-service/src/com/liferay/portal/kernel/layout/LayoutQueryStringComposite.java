/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.layout;

import com.liferay.portal.model.Layout;

/**
 * @author Sergio González
 */
public class LayoutQueryStringComposite {

	public LayoutQueryStringComposite(Layout layout, String queryString) {
		_layout = layout;
		_queryString = queryString;
	}

	public Layout getLayout() {
		return _layout;
	}

	public String getQueryString() {
		return _queryString;
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	public void setQueryString(String queryString) {
		_queryString = queryString;
	}

	private Layout _layout;
	private String _queryString;

}