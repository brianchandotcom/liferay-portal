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

package com.liferay.commerce.frontend.taglib.internal.model;

/**
 * @author Marco Leo
 */
public class ClayPaginationEntry {

	public ClayPaginationEntry(String href, int label) {
		_href = href;
		_label = label;
	}

	public String getHref() {
		return _href;
	}

	public int getLabel() {
		return _label;
	}

	private final String _href;
	private final int _label;

}