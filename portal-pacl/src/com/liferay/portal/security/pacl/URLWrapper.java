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

package com.liferay.portal.security.pacl;

import java.net.URL;

/**
 * @author Raymond Augé
 */
public class URLWrapper {

	public URLWrapper(URL url) {
		this.url = url;
		this.stringURL = this.url.toString();
		this.hashCode = this.stringURL.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof URLWrapper)) {
			return false;
		}

		URLWrapper urlKeyWrapper = (URLWrapper)obj;

		if (url == urlKeyWrapper.url) {
			return true;
		}

		return stringURL.equals(urlKeyWrapper.stringURL);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	protected int hashCode;
	protected String stringURL;
	protected URL url;

}