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

package com.liferay.headless.discovery.internal.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 */
@XmlRootElement
public class Hint {

	public Hint() {
	}

	public Hint(String[] allow, String[] formats) {
		_allow = allow;
		_formats = formats;
	}

	public String[] getAllow() {
		return _allow;
	}

	public String[] getFormats() {
		return _formats;
	}

	public void setAllow(String[] allow) {
		_allow = allow;
	}

	public void setFormats(String[] formats) {
		_formats = formats;
	}

	private String[] _allow;
	private String[] _formats;

}