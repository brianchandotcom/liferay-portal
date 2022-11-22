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

package com.liferay.client.extension.type.annotation;

/**
 * This enum describes property data types for CET fields.
 *
 * It can be used for multiple purposes like, for example, rendering the display
 * of the data in the view client extension page.
 *
 * @author Iván Zaera Avellón
 */
public enum CETPropertyType {

	Boolean, String, StringList, URL(true), URLList(true);

	/**
	 * Whether or not the values contained in the property are to be
	 * interpreted as URLs.
	 *
	 * URLs may have interpolation tokens inside them that must be replaced by
	 * their actual values during build or runtime.
	 *
	 * @review
	 */
	public boolean isURL() {
		return _url;
	}

	private CETPropertyType() {
		this(false);
	}

	private CETPropertyType(boolean url) {
		_url = url;
	}

	private boolean _url;

}